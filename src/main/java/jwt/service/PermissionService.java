package jwt.service;

import jwt.domain.Permission;
import jwt.repository.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Permission}.
 */
@Service
@Transactional
public class PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionService.class);

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Save a permission.
     *
     * @param permission the entity to save.
     * @return the persisted entity.
     */
    public Permission save(Permission permission) {
        log.debug("Request to save Permission : {}", permission);
        return permissionRepository.save(permission);
    }

    /**
     * Get all the permissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Permission> findAll(Pageable pageable) {
        log.debug("Request to get all Permissions");
        return permissionRepository.findAll(pageable);
    }


    /**
     * Get one permission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Permission> findOne(Long id) {
        log.debug("Request to get Permission : {}", id);
        return permissionRepository.findById(id);
    }

    /**
     * Delete the permission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Permission : {}", id);
        permissionRepository.deleteById(id);
    }

    public List<Permission> findAllPermissions() {
        log.debug("Request to get all Permissions");
        return permissionRepository.findAllPermission();
    }
}
