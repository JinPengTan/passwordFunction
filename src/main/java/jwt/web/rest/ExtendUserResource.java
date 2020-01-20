package jwt.web.rest;

import jwt.domain.Authority;
import jwt.domain.ExtendUser;
import jwt.domain.Permission;
import jwt.domain.Profile;
import jwt.repository.AuthorityRepository;
import jwt.service.ExtendUserService;
import jwt.service.ProfileService;
import jwt.service.UserService;
import jwt.service.dto.UserDTO;
import jwt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.*;

/**
 * REST controller for managing {@link jwt.domain.ExtendUser}.
 */
@RestController
@RequestMapping("/api")
public class ExtendUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtendUserResource.class);

    private static final String ENTITY_NAME = "extendUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtendUserService extendUserService;


    public ExtendUserResource(ExtendUserService extendUserService) {
        this.extendUserService = extendUserService;
    }

    /**
     * {@code POST  /extend-users} : Create a new extendUser.
     *
     * @param extendUser the extendUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extendUser, or with status {@code 400 (Bad Request)} if the extendUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extend-users")
    @PreAuthorize("hasAuthority('ROLE_EXTENDUSER_ADD')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExtendUser> createExtendUser(@RequestBody ExtendUser extendUser) throws URISyntaxException {
        log.debug("REST request to save ExtendUser : {}", extendUser);

        if (extendUser.getId() != null) {
            throw new BadRequestAlertException("A new extendUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(extendUser.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ExtendUser result = extendUserService.save(extendUser);
        return ResponseEntity.created(new URI("/api/extend-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extend-users} : Updates an existing extendUser.
     *
     * @param extendUser the extendUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extendUser,
     * or with status {@code 400 (Bad Request)} if the extendUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extendUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extend-users")
    @PreAuthorize("hasAuthority('ROLE_EXTENDUSER_UPDATE')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExtendUser> updateExtendUser(@RequestBody ExtendUser extendUser) throws URISyntaxException {
        log.debug("REST request to update ExtendUser : {}", extendUser);
        if (extendUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtendUser result = extendUserService.save(extendUser);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extendUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /extend-users} : get all the extendUsers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extendUsers in body.
     */
    @GetMapping("/extend-users")
    @PreAuthorize("hasAuthority('ROLE_EXTENDUSER_READ')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ExtendUser>> getAllExtendUsers(Pageable pageable) {
        log.debug("REST request to get a page of ExtendUsers");
        Page<ExtendUser> page = extendUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extend-users/:id} : get the "id" extendUser.
     *
     * @param id the id of the extendUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extendUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extend-users/{id}")
    @PreAuthorize("hasAuthority('ROLE_EXTENDUSER_READ')" + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExtendUser> getExtendUser(@PathVariable Long id) {
        log.debug("REST request to get ExtendUser : {}", id);
        Optional<ExtendUser> extendUser = extendUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extendUser);
    }

    /**
     * {@code DELETE  /extend-users/:id} : delete the "id" extendUser.
     *
     * @param id the id of the extendUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extend-users/{id}")
    @PreAuthorize("hasAuthority('ROLE_EXTENDUSER_DELETE')"  + " || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteExtendUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtendUser : {}", id);
        extendUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
