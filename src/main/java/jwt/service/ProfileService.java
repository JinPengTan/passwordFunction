package jwt.service;

import jwt.domain.ExtendUser;
import jwt.domain.Profile;
import jwt.repository.ExtendUserRepository;
import jwt.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    ExtendUserRepository extendUserRepository;

    profileAutoUpdateUser profileAutoUpdateUser;

    public ProfileService(ProfileRepository profileRepository, ExtendUserRepository extendUserRepository, profileAutoUpdateUser profileAutoUpdateUser) {
        this.profileRepository = profileRepository;
        this.extendUserRepository = extendUserRepository;
        this.profileAutoUpdateUser = profileAutoUpdateUser;
    }

    /**
     * Save a profile.
     *
     * @param profile the entity to save.
     * @return the persisted entity.
     */
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);

        Profile profil1 = profileRepository.save(profile);
        profileAutoUpdateUser.autoUpdateExtendUserProfile(profile);

        return profil1;
    }

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Profile> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable);
    }

    /**
     * Get all the profiles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Profile> findAllWithEagerRelationships(Pageable pageable) {
        return profileRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Profile> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileAutoUpdateUser.deleteProfileUserAuthority(id);
        profileRepository.deleteById(id);
    }

    public List<Profile> getAll() {
        return profileRepository.findAllProfile();
    }
}
