package jwt.web.rest;

import jwt.domain.ApiKey;
import jwt.service.ApiKeyService;
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
 * REST controller for managing {@link jwt.domain.ApiKey}.
 */
@RestController
@RequestMapping("/api")
public class ApiKeyResource {

    private final Logger log = LoggerFactory.getLogger(ApiKeyResource.class);

    private static final String ENTITY_NAME = "apiKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiKeyService apiKeyService;

    public ApiKeyResource(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    /**
     * {@code POST  /api-keys} : Create a new apiKey.
     *
     * @param apiKey the apiKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiKey, or with status {@code 400 (Bad Request)} if the apiKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-keys")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_APIKEY_ADD')")
    public ResponseEntity<ApiKey> createApiKey(@RequestBody ApiKey apiKey) throws URISyntaxException {
        log.debug("REST request to save ApiKey : {}", apiKey);
        if (apiKey.getId() != null) {
            throw new BadRequestAlertException("A new apiKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiKey result = apiKeyService.save(apiKey);
        return ResponseEntity.created(new URI("/api/api-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-keys} : Updates an existing apiKey.
     *
     * @param apiKey the apiKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiKey,
     * or with status {@code 400 (Bad Request)} if the apiKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-keys")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_APIKEY_UPDATE')")
    public ResponseEntity<ApiKey> updateApiKey(@RequestBody ApiKey apiKey) throws URISyntaxException {
        log.debug("REST request to update ApiKey : {}", apiKey);
        if (apiKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiKey result = apiKeyService.save(apiKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-keys} : get all the apiKeys.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiKeys in body.
     */
    @GetMapping("/api-keys")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_APIKEY_READ')")
    public ResponseEntity<List<ApiKey>> getAllApiKeys(Pageable pageable) {
        log.debug("REST request to get a page of ApiKeys");
        Page<ApiKey> page = apiKeyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /api-keys/:id} : get the "id" apiKey.
     *
     * @param id the id of the apiKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-keys/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_APIKEY_READ')")
    public ResponseEntity<ApiKey> getApiKey(@PathVariable Long id) {
        log.debug("REST request to get ApiKey : {}", id);
        Optional<ApiKey> apiKey = apiKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiKey);
    }

    /**
     * {@code DELETE  /api-keys/:id} : delete the "id" apiKey.
     *
     * @param id the id of the apiKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-keys/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_APIKEY_DELETE')")
    public ResponseEntity<Void> deleteApiKey(@PathVariable Long id) {
        log.debug("REST request to delete ApiKey : {}", id);
        apiKeyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
