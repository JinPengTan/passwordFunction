package jwt.service;

import jwt.domain.Cycle;
import jwt.domain.User;
import jwt.repository.CycleRepository;
import jwt.repository.UserRepository;
import jwt.security.jwt.TokenProvider;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class PasswordVerification {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final CycleRepository cycleRepository;

    @Value("${cycle.cycleCount}")
    private int cycleCount;

    @Value("${cycle.expired}")
    private int expired;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    PasswordVerification(TokenProvider tokenProvider, UserRepository userRepository, CycleRepository cycleRepository, PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.cycleRepository = cycleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean firstChecker(String token) {
        token = token.substring(7);
        String username = tokenProvider.getSubject(token);
        User loginUser = userRepository.findOneByLogin(username).orElse(new User());
        if(loginUser.getId() != 0) {
            List<Cycle> cycleList = cycleRepository.findAllByUserLogin(loginUser.getLogin());
            if(cycleRepository.findAllByUserLogin(loginUser.getLogin()).size() <= 1) {
                return true;
            } else {
                return false;
            }

        } else {
            logger.error("FirstChecker: No User Found");
            return null;
        }
    }

    //check lifecycle, value store in properties: cycle.cycle
    public Boolean lifeCycleChecker(String token, String oriClearPassword) {
        try {
            token = token.substring(7);
            String username = tokenProvider.getSubject(token);
            Optional<User> user = userRepository.findOneByLogin(username);
            if (user.isPresent()) {
                List<Cycle> cycleArrayList = cycleRepository.findAllById(user.get().getId());
                int lastCount = cycleArrayList.get(cycleArrayList.size() - 1).getCycleCount();
                for (int i = 0; i < cycleArrayList.size(); i++) {
                    if (!passwordEncoder.matches(oriClearPassword, cycleArrayList.get(i).getCyclePassword())) {
                        cycleArrayList.remove(i);
                        i--;
                    }
                }
                if (cycleArrayList.size() != 0) {
                    Collections.sort(cycleArrayList, new Cycle());
                    return lastCount - cycleArrayList.get(cycleArrayList.size() - 1).getCycleCount() >= cycleCount;
                } else
                    return true;
            }
            return false;
        } catch (Exception ex) {
            logger.error("Life Cycle Checker, Error Message: " + ex.toString());
            return false;
        }
    }

    public Boolean passwordExpiredChecker(String token) {
        try {
            token = token.substring(7);
            String username = tokenProvider.getSubject(token);
            Optional<User> user = userRepository.findOneByLogin(username);
            if(user.isPresent()) {
                List<Cycle> cycleArrayList = cycleRepository.findAllById(user.get().getId());
                Cycle cycle = cycleArrayList.get(cycleArrayList.size() - 1);
                return DAYS.between(cycle.getCycleDatetime(), LocalDate.now()) >= expired;
            }
            return false;
        } catch ( Exception ex) {
            logger.error("Password Expired Checker, Error {}" , ex.toString());
            return false;
        }
    }

    public Boolean passwordChecker(String token) {
        return passwordExpiredChecker(token) || firstChecker(token);
    }


}
