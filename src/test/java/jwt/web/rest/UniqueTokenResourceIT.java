package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.UniqueToken;
import jwt.repository.UniqueTokenRepository;
import jwt.service.UniqueTokenService;
import jwt.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static jwt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UniqueTokenResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class UniqueTokenResourceIT {

    private static final String DEFAULT_WALLET_ID = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TPAN = "AAAAAAAAAA";
    private static final String UPDATED_TPAN = "BBBBBBBBBB";

    @Autowired
    private UniqueTokenRepository uniqueTokenRepository;

    @Autowired
    private UniqueTokenService uniqueTokenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUniqueTokenMockMvc;

    private UniqueToken uniqueToken;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniqueTokenResource uniqueTokenResource = new UniqueTokenResource(uniqueTokenService);
        this.restUniqueTokenMockMvc = MockMvcBuilders.standaloneSetup(uniqueTokenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniqueToken createEntity(EntityManager em) {
        UniqueToken uniqueToken = new UniqueToken()
            .walletId(DEFAULT_WALLET_ID)
            .tpan(DEFAULT_TPAN);
        return uniqueToken;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniqueToken createUpdatedEntity(EntityManager em) {
        UniqueToken uniqueToken = new UniqueToken()
            .walletId(UPDATED_WALLET_ID)
            .tpan(UPDATED_TPAN);
        return uniqueToken;
    }

    @BeforeEach
    public void initTest() {
        uniqueToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniqueToken() throws Exception {
        int databaseSizeBeforeCreate = uniqueTokenRepository.findAll().size();

        // Create the UniqueToken
        restUniqueTokenMockMvc.perform(post("/api/unique-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueToken)))
            .andExpect(status().isCreated());

        // Validate the UniqueToken in the database
        List<UniqueToken> uniqueTokenList = uniqueTokenRepository.findAll();
        assertThat(uniqueTokenList).hasSize(databaseSizeBeforeCreate + 1);
        UniqueToken testUniqueToken = uniqueTokenList.get(uniqueTokenList.size() - 1);
        assertThat(testUniqueToken.getWalletId()).isEqualTo(DEFAULT_WALLET_ID);
        assertThat(testUniqueToken.getTpan()).isEqualTo(DEFAULT_TPAN);
    }

    @Test
    @Transactional
    public void createUniqueTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uniqueTokenRepository.findAll().size();

        // Create the UniqueToken with an existing ID
        uniqueToken.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniqueTokenMockMvc.perform(post("/api/unique-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueToken)))
            .andExpect(status().isBadRequest());

        // Validate the UniqueToken in the database
        List<UniqueToken> uniqueTokenList = uniqueTokenRepository.findAll();
        assertThat(uniqueTokenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniqueTokens() throws Exception {
        // Initialize the database
        uniqueTokenRepository.saveAndFlush(uniqueToken);

        // Get all the uniqueTokenList
        restUniqueTokenMockMvc.perform(get("/api/unique-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniqueToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletId").value(hasItem(DEFAULT_WALLET_ID)))
            .andExpect(jsonPath("$.[*].tpan").value(hasItem(DEFAULT_TPAN)));
    }
    
    @Test
    @Transactional
    public void getUniqueToken() throws Exception {
        // Initialize the database
        uniqueTokenRepository.saveAndFlush(uniqueToken);

        // Get the uniqueToken
        restUniqueTokenMockMvc.perform(get("/api/unique-tokens/{id}", uniqueToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uniqueToken.getId().intValue()))
            .andExpect(jsonPath("$.walletId").value(DEFAULT_WALLET_ID))
            .andExpect(jsonPath("$.tpan").value(DEFAULT_TPAN));
    }

    @Test
    @Transactional
    public void getNonExistingUniqueToken() throws Exception {
        // Get the uniqueToken
        restUniqueTokenMockMvc.perform(get("/api/unique-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniqueToken() throws Exception {
        // Initialize the database
        uniqueTokenService.save(uniqueToken);

        int databaseSizeBeforeUpdate = uniqueTokenRepository.findAll().size();

        // Update the uniqueToken
        UniqueToken updatedUniqueToken = uniqueTokenRepository.findById(uniqueToken.getId()).get();
        // Disconnect from session so that the updates on updatedUniqueToken are not directly saved in db
        em.detach(updatedUniqueToken);
        updatedUniqueToken
            .walletId(UPDATED_WALLET_ID)
            .tpan(UPDATED_TPAN);

        restUniqueTokenMockMvc.perform(put("/api/unique-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUniqueToken)))
            .andExpect(status().isOk());

        // Validate the UniqueToken in the database
        List<UniqueToken> uniqueTokenList = uniqueTokenRepository.findAll();
        assertThat(uniqueTokenList).hasSize(databaseSizeBeforeUpdate);
        UniqueToken testUniqueToken = uniqueTokenList.get(uniqueTokenList.size() - 1);
        assertThat(testUniqueToken.getWalletId()).isEqualTo(UPDATED_WALLET_ID);
        assertThat(testUniqueToken.getTpan()).isEqualTo(UPDATED_TPAN);
    }

    @Test
    @Transactional
    public void updateNonExistingUniqueToken() throws Exception {
        int databaseSizeBeforeUpdate = uniqueTokenRepository.findAll().size();

        // Create the UniqueToken

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniqueTokenMockMvc.perform(put("/api/unique-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueToken)))
            .andExpect(status().isBadRequest());

        // Validate the UniqueToken in the database
        List<UniqueToken> uniqueTokenList = uniqueTokenRepository.findAll();
        assertThat(uniqueTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniqueToken() throws Exception {
        // Initialize the database
        uniqueTokenService.save(uniqueToken);

        int databaseSizeBeforeDelete = uniqueTokenRepository.findAll().size();

        // Delete the uniqueToken
        restUniqueTokenMockMvc.perform(delete("/api/unique-tokens/{id}", uniqueToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UniqueToken> uniqueTokenList = uniqueTokenRepository.findAll();
        assertThat(uniqueTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
