package jwt.service;

import jwt.domain.Authority;
import jwt.domain.ExtendUser;
import jwt.domain.Permission;
import jwt.domain.Profile;
import jwt.repository.AuthorityRepository;
import jwt.repository.ExtendUserRepository;
import jwt.repository.ProfileRepository;
import jwt.repository.UserRepository;
import jwt.service.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class profileAutoUpdateUser {

    ProfileRepository profileRepository;

    UserService userService;

    AuthorityRepository authorityRepository;

    ExtendUserRepository extendUserRepository;


    public profileAutoUpdateUser(ProfileRepository profileRepository, UserService userService, AuthorityRepository authorityRepository, ExtendUserRepository extendUserRepository) {
        this.profileRepository = profileRepository;
        this.authorityRepository = authorityRepository;
        this.userService = userService;
        this.extendUserRepository = extendUserRepository;
    }


    public void updateUserAuthority(ExtendUser extendUser) {
        //Update user Authority with Profile info
        List<Permission> permissionList = new ArrayList<Permission>(profileRepository.findOneWithEagerRelationships(extendUser.getProfile().getId()).get().getPermissions());
        Set<String> permissionSet = new HashSet<>();
        Set<Authority> authoritySet = new HashSet<>();
        for (Permission permit : permissionList) {
            permissionSet.add(permit.getName());
        }
        for (String name : permissionSet) {
            authoritySet.add(authorityRepository.findByName(name));
        }

        extendUser.getUser().setAuthorities(authoritySet);

        UserDTO userDTO = new UserDTO(extendUser.getUser());

        userService.updateUser(userDTO);
        //---------------------------------------------------------------------------------------
    }

    public void autoUpdateExtendUserProfile(Profile profile) {
        Set<ExtendUser> extendUserSet = extendUserRepository.findALlByProfileId(profile.getId());
        for (ExtendUser user: extendUserSet) {
            this.updateUserAuthority(user);
            extendUserRepository.save(user);
        }
    }
}
