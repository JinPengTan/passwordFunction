package jwt.web.rest;

import jwt.domain.UniqueToken;
import jwt.service.UniqueTokenService;
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
 * REST controller for managing {@link jwt.domain.UniqueToken}.
 */
@RestController
@RequestMapping("/api")
public class UniqueTokenResource {

    private final Logger log = LoggerFactory.getLogger(UniqueTokenResource.class);

    private static final String ENTITY_NAME = "uniqueToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniqueTokenService uniqueTokenService;

    public UniqueTokenResource(UniqueTokenService uniqueTokenService) {
        this.uniqueTokenService = uniqueTokenService;
    }

    /**
     * {@code POST  /unique-tokens} : Create a new uniqueToken.
     *
     * @param uniqueToken the uniqueToken to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uniqueToken, or with status {@code 400 (Bad Request)} if the uniqueToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unique-tokens")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_UNIQUETOKEN_ADD')")
    public ResponseEntity<UniqueToken> createUniqueToken(@RequestBody UniqueToken uniqueToken) throws URISyntaxException {
        log.debug("REST request to save UniqueToken : {}", uniqueToken);
        if (uniqueToken.getId() != null) {
            throw new BadRequestAlertException("A new uniqueToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniqueToken result = uniqueTokenService.save(uniqueToken);
        return ResponseEntity.created(new URI("/api/unique-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unique-tokens} : Updates an existing uniqueToken.
     *
     * @param uniqueToken the uniqueToken to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uniqueToken,
     * or with status {@code 400 (Bad Request)} if the uniqueToken is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uniqueToken couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unique-tokens")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_UNIQUETOKEN_UPDATE')")
    public ResponseEntity<UniqueToken> updateUniqueToken(@RequestBody UniqueToken uniqueToken) throws URISyntaxException {
        log.debug("REST request to update UniqueToken : {}", uniqueToken);
        if (uniqueToken.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UniqueToken result = uniqueTokenService.save(uniqueToken);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uniqueToken.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unique-tokens} : get all the uniqueTokens.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uniqueTokens in body.
     */
    @GetMapping("/unique-tokens")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_UNIQUETOKEN_READ')")
    public ResponseEntity<List<UniqueToken>> getAllUniqueTokens(Pageable pageable) {
        log.debug("REST request to get a page of UniqueTokens");
        Page<UniqueToken> page = uniqueTokenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unique-tokens/:id} : get the "id" uniqueToken.
     *
     * @param id the id of the uniqueToken to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uniqueToken, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unique-tokens/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_UNIQUETOKEN_READ')")
    public ResponseEntity<UniqueToken> getUniqueToken(@PathVariable Long id) {
        log.debug("REST request to get UniqueToken : {}", id);
        Optional<UniqueToken> uniqueToken = uniqueTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uniqueToken);
    }

    /**
     * {@code DELETE  /unique-tokens/:id} : delete the "id" uniqueToken.
     *
     * @param id the id of the uniqueToken to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unique-tokens/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_UNIQUETOKEN_DELETE')")
    public ResponseEntity<Void> deleteUniqueToken(@PathVariable Long id) {
        log.debug("REST request to delete UniqueToken : {}", id);
        uniqueTokenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
