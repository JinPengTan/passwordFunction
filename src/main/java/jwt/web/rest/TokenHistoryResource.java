package jwt.web.rest;

import jwt.domain.TokenHistory;
import jwt.service.TokenHistoryService;
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
 * REST controller for managing {@link jwt.domain.TokenHistory}.
 */
@RestController
@RequestMapping("/api")
public class TokenHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TokenHistoryResource.class);

    private static final String ENTITY_NAME = "tokenHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TokenHistoryService tokenHistoryService;

    public TokenHistoryResource(TokenHistoryService tokenHistoryService) {
        this.tokenHistoryService = tokenHistoryService;
    }

    /**
     * {@code POST  /token-histories} : Create a new tokenHistory.
     *
     * @param tokenHistory the tokenHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tokenHistory, or with status {@code 400 (Bad Request)} if the tokenHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PostMapping("/token-histories")
//    public ResponseEntity<TokenHistory> createTokenHistory(@RequestBody TokenHistory tokenHistory) throws URISyntaxException {
//        log.debug("REST request to save TokenHistory : {}", tokenHistory);
//        if (tokenHistory.getId() != null) {
//            throw new BadRequestAlertException("A new tokenHistory cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        TokenHistory result = tokenHistoryService.save(tokenHistory);
//        return ResponseEntity.created(new URI("/api/token-histories/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code PUT  /token-histories} : Updates an existing tokenHistory.
     *
     * @param tokenHistory the tokenHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenHistory,
     * or with status {@code 400 (Bad Request)} if the tokenHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tokenHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PutMapping("/token-histories")
//    public ResponseEntity<TokenHistory> updateTokenHistory(@RequestBody TokenHistory tokenHistory) throws URISyntaxException {
//        log.debug("REST request to update TokenHistory : {}", tokenHistory);
//        if (tokenHistory.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        TokenHistory result = tokenHistoryService.save(tokenHistory);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenHistory.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code GET  /token-histories} : get all the tokenHistories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tokenHistories in body.
     */
    @GetMapping("/token-histories")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_HISTORY_READ')")
    public ResponseEntity<List<TokenHistory>> getAllTokenHistories(Pageable pageable) {
        log.debug("REST request to get a page of TokenHistories");
        Page<TokenHistory> page = tokenHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /token-histories/:id} : get the "id" tokenHistory.
     *
     * @param id the id of the tokenHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tokenHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/token-histories/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || hasAuthority('ROLE_HISTORY_READ')")
    public ResponseEntity<TokenHistory> getTokenHistory(@PathVariable Long id) {
        log.debug("REST request to get TokenHistory : {}", id);
        Optional<TokenHistory> tokenHistory = tokenHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tokenHistory);
    }

    /**
     * {@code DELETE  /token-histories/:id} : delete the "id" tokenHistory.
     *
     * @param id the id of the tokenHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
//    @DeleteMapping("/token-histories/{id}")
//    public ResponseEntity<Void> deleteTokenHistory(@PathVariable Long id) {
//        log.debug("REST request to delete TokenHistory : {}", id);
//        tokenHistoryService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//    }
}
