package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.Token;
import jwt.repository.TokenRepository;
import jwt.service.TokenService;
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
 * Integration tests for the {@link TokenResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class TokenResourceIT {

    private static final String DEFAULT_WALLET_ID = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TPAN = "AAAAAAAAAA";
    private static final String UPDATED_TPAN = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_KEY = "AAAAAAAAAA";
    private static final String UPDATED_HASH_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MASK_PAN = "AAAAAAAAAA";
    private static final String UPDATED_MASK_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_EN_PAN = "AAAAAAAAAA";
    private static final String UPDATED_EN_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

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

    private MockMvc restTokenMockMvc;

    private Token token;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TokenResource tokenResource = new TokenResource(tokenService);
        this.restTokenMockMvc = MockMvcBuilders.standaloneSetup(tokenResource)
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
    public static Token createEntity(EntityManager em) {
        Token token = new Token()
            .walletId(DEFAULT_WALLET_ID)
            .tpan(DEFAULT_TPAN)
            .hashKey(DEFAULT_HASH_KEY)
            .tokenExpiry(DEFAULT_TOKEN_EXPIRY)
            .tokenStatus(DEFAULT_TOKEN_STATUS)
            .maskPan(DEFAULT_MASK_PAN)
            .enPan(DEFAULT_EN_PAN)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .version(DEFAULT_VERSION);
        return token;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Token createUpdatedEntity(EntityManager em) {
        Token token = new Token()
            .walletId(UPDATED_WALLET_ID)
            .tpan(UPDATED_TPAN)
            .hashKey(UPDATED_HASH_KEY)
            .tokenExpiry(UPDATED_TOKEN_EXPIRY)
            .tokenStatus(UPDATED_TOKEN_STATUS)
            .maskPan(UPDATED_MASK_PAN)
            .enPan(UPDATED_EN_PAN)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .version(UPDATED_VERSION);
        return token;
    }

    @BeforeEach
    public void initTest() {
        token = createEntity(em);
    }

    @Test
    @Transactional
    public void createToken() throws Exception {
        int databaseSizeBeforeCreate = tokenRepository.findAll().size();

        // Create the Token
        restTokenMockMvc.perform(post("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isCreated());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeCreate + 1);
        Token testToken = tokenList.get(tokenList.size() - 1);
        assertThat(testToken.getWalletId()).isEqualTo(DEFAULT_WALLET_ID);
        assertThat(testToken.getTpan()).isEqualTo(DEFAULT_TPAN);
        assertThat(testToken.getHashKey()).isEqualTo(DEFAULT_HASH_KEY);
        assertThat(testToken.getTokenExpiry()).isEqualTo(DEFAULT_TOKEN_EXPIRY);
        assertThat(testToken.getTokenStatus()).isEqualTo(DEFAULT_TOKEN_STATUS);
        assertThat(testToken.getMaskPan()).isEqualTo(DEFAULT_MASK_PAN);
        assertThat(testToken.getEnPan()).isEqualTo(DEFAULT_EN_PAN);
        assertThat(testToken.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testToken.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tokenRepository.findAll().size();

        // Create the Token with an existing ID
        token.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenMockMvc.perform(post("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isBadRequest());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTokens() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        // Get all the tokenList
        restTokenMockMvc.perform(get("/api/tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(token.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletId").value(hasItem(DEFAULT_WALLET_ID)))
            .andExpect(jsonPath("$.[*].tpan").value(hasItem(DEFAULT_TPAN)))
            .andExpect(jsonPath("$.[*].hashKey").value(hasItem(DEFAULT_HASH_KEY)))
            .andExpect(jsonPath("$.[*].tokenExpiry").value(hasItem(DEFAULT_TOKEN_EXPIRY)))
            .andExpect(jsonPath("$.[*].tokenStatus").value(hasItem(DEFAULT_TOKEN_STATUS)))
            .andExpect(jsonPath("$.[*].maskPan").value(hasItem(DEFAULT_MASK_PAN)))
            .andExpect(jsonPath("$.[*].enPan").value(hasItem(DEFAULT_EN_PAN)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }
    
    @Test
    @Transactional
    public void getToken() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        // Get the token
        restTokenMockMvc.perform(get("/api/tokens/{id}", token.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(token.getId().intValue()))
            .andExpect(jsonPath("$.walletId").value(DEFAULT_WALLET_ID))
            .andExpect(jsonPath("$.tpan").value(DEFAULT_TPAN))
            .andExpect(jsonPath("$.hashKey").value(DEFAULT_HASH_KEY))
            .andExpect(jsonPath("$.tokenExpiry").value(DEFAULT_TOKEN_EXPIRY))
            .andExpect(jsonPath("$.tokenStatus").value(DEFAULT_TOKEN_STATUS))
            .andExpect(jsonPath("$.maskPan").value(DEFAULT_MASK_PAN))
            .andExpect(jsonPath("$.enPan").value(DEFAULT_EN_PAN))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingToken() throws Exception {
        // Get the token
        restTokenMockMvc.perform(get("/api/tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateToken() throws Exception {
        // Initialize the database
        tokenService.save(token);

        int databaseSizeBeforeUpdate = tokenRepository.findAll().size();

        // Update the token
        Token updatedToken = tokenRepository.findById(token.getId()).get();
        // Disconnect from session so that the updates on updatedToken are not directly saved in db
        em.detach(updatedToken);
        updatedToken
            .walletId(UPDATED_WALLET_ID)
            .tpan(UPDATED_TPAN)
            .hashKey(UPDATED_HASH_KEY)
            .tokenExpiry(UPDATED_TOKEN_EXPIRY)
            .tokenStatus(UPDATED_TOKEN_STATUS)
            .maskPan(UPDATED_MASK_PAN)
            .enPan(UPDATED_EN_PAN)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .version(UPDATED_VERSION);

        restTokenMockMvc.perform(put("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedToken)))
            .andExpect(status().isOk());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeUpdate);
        Token testToken = tokenList.get(tokenList.size() - 1);
        assertThat(testToken.getWalletId()).isEqualTo(UPDATED_WALLET_ID);
        assertThat(testToken.getTpan()).isEqualTo(UPDATED_TPAN);
        assertThat(testToken.getHashKey()).isEqualTo(UPDATED_HASH_KEY);
        assertThat(testToken.getTokenExpiry()).isEqualTo(UPDATED_TOKEN_EXPIRY);
        assertThat(testToken.getTokenStatus()).isEqualTo(UPDATED_TOKEN_STATUS);
        assertThat(testToken.getMaskPan()).isEqualTo(UPDATED_MASK_PAN);
        assertThat(testToken.getEnPan()).isEqualTo(UPDATED_EN_PAN);
        assertThat(testToken.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testToken.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingToken() throws Exception {
        int databaseSizeBeforeUpdate = tokenRepository.findAll().size();

        // Create the Token

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenMockMvc.perform(put("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isBadRequest());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteToken() throws Exception {
        // Initialize the database
        tokenService.save(token);

        int databaseSizeBeforeDelete = tokenRepository.findAll().size();

        // Delete the token
        restTokenMockMvc.perform(delete("/api/tokens/{id}", token.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
