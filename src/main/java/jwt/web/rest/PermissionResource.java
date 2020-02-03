package jwt.web.rest;

import jwt.domain.Permission;
import jwt.service.PermissionService;
import jwt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link jwt.domain.Permission}.
 */
@RestController
@RequestMapping("/api")
public class PermissionResource {

    private final Logger log = LoggerFactory.getLogger(PermissionResource.class);

    private static final String ENTITY_NAME = "permission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermissionService permissionService;

    public PermissionResource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * {@code POST  /permissions} : Create a new permission.
     *
     * @param permission the permission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permission, or with status {@code 400 (Bad Request)} if the permission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permissions")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_ADD')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) throws URISyntaxException {
        log.debug("REST request to save Permission : {}", permission);
        if (permission.getId() != null) {
            throw new BadRequestAlertException("A new permission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Permission result = permissionService.save(permission);
        return ResponseEntity.created(new URI("/api/permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permissions} : Updates an existing permission.
     *
     * @param permission the permission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permission,
     * or with status {@code 400 (Bad Request)} if the permission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permissions")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_UPDATE')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission) throws URISyntaxException {
        log.debug("REST request to update Permission : {}", permission);
        if (permission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Permission result = permissionService.save(permission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permissions} : get all the permissions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permissions in body.
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_READ')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Permission>> getAllPermissions(Pageable pageable) {
        log.debug("REST request to get a page of Permissions");
        Page<Permission> page = permissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/allPermissions")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_READ')" + " || hasAuthority('ROLE_ADMIN')")
    public List<Permission> getAllPermissions() {
        log.debug("REST request to get a list of Permissions");
        return permissionService.findAllPermissions();
    }

    /**
     * {@code GET  /permissions/:id} : get the "id" permission.
     *
     * @param id the id of the permission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permissions/{id}")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_READ')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Permission> getPermission(@PathVariable Long id) {
        log.debug("REST request to get Permission : {}", id);
        Optional<Permission> permission = permissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permission);
    }

    /**
     * {@code DELETE  /permissions/:id} : delete the "id" permission.
     *
     * @param id the id of the permission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permissions/{id}")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_DELETE')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        log.debug("REST request to delete Permission : {}", id);
        permissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
