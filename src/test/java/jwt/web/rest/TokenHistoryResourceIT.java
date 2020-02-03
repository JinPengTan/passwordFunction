package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.TokenHistory;
import jwt.repository.TokenHistoryRepository;
import jwt.service.TokenHistoryService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static jwt.web.rest.TestUtil.sameInstant;
import static jwt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TokenHistoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class TokenHistoryResourceIT {

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

    private static final String DEFAULT_TR_ID = "AAAAAAAAAA";
    private static final String UPDATED_TR_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final ZonedDateTime DEFAULT_HISTORY_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HISTORY_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TokenHistoryRepository tokenHistoryRepository;

    @Autowired
    private TokenHistoryService tokenHistoryService;

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

    private MockMvc restTokenHistoryMockMvc;

    private TokenHistory tokenHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TokenHistoryResource tokenHistoryResource = new TokenHistoryResource(tokenHistoryService);
        this.restTokenHistoryMockMvc = MockMvcBuilders.standaloneSetup(tokenHistoryResource)
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
    public static TokenHistory createEntity(EntityManager em) {
        TokenHistory tokenHistory = new TokenHistory()
            .tpan(DEFAULT_TPAN)
            .hashKey(DEFAULT_HASH_KEY)
            .tokenExpiry(DEFAULT_TOKEN_EXPIRY)
            .tokenStatus(DEFAULT_TOKEN_STATUS)
            .maskPan(DEFAULT_MASK_PAN)
            .enPan(DEFAULT_EN_PAN)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .trId(DEFAULT_TR_ID)
            .version(DEFAULT_VERSION)
            .historyDt(DEFAULT_HISTORY_DT);
        return tokenHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenHistory createUpdatedEntity(EntityManager em) {
        TokenHistory tokenHistory = new TokenHistory()
            .tpan(UPDATED_TPAN)
            .hashKey(UPDATED_HASH_KEY)
            .tokenExpiry(UPDATED_TOKEN_EXPIRY)
            .tokenStatus(UPDATED_TOKEN_STATUS)
            .maskPan(UPDATED_MASK_PAN)
            .enPan(UPDATED_EN_PAN)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .trId(UPDATED_TR_ID)
            .version(UPDATED_VERSION)
            .historyDt(UPDATED_HISTORY_DT);
        return tokenHistory;
    }

    @BeforeEach
    public void initTest() {
        tokenHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTokenHistory() throws Exception {
        int databaseSizeBeforeCreate = tokenHistoryRepository.findAll().size();

        // Create the TokenHistory
        restTokenHistoryMockMvc.perform(post("/api/token-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenHistory)))
            .andExpect(status().isCreated());

        // Validate the TokenHistory in the database
        List<TokenHistory> tokenHistoryList = tokenHistoryRepository.findAll();
        assertThat(tokenHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TokenHistory testTokenHistory = tokenHistoryList.get(tokenHistoryList.size() - 1);
        assertThat(testTokenHistory.getTpan()).isEqualTo(DEFAULT_TPAN);
        assertThat(testTokenHistory.getHashKey()).isEqualTo(DEFAULT_HASH_KEY);
        assertThat(testTokenHistory.getTokenExpiry()).isEqualTo(DEFAULT_TOKEN_EXPIRY);
        assertThat(testTokenHistory.getTokenStatus()).isEqualTo(DEFAULT_TOKEN_STATUS);
        assertThat(testTokenHistory.getMaskPan()).isEqualTo(DEFAULT_MASK_PAN);
        assertThat(testTokenHistory.getEnPan()).isEqualTo(DEFAULT_EN_PAN);
        assertThat(testTokenHistory.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testTokenHistory.getTrId()).isEqualTo(DEFAULT_TR_ID);
        assertThat(testTokenHistory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTokenHistory.getHistoryDt()).isEqualTo(DEFAULT_HISTORY_DT);
    }

    @Test
    @Transactional
    public void createTokenHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tokenHistoryRepository.findAll().size();

        // Create the TokenHistory with an existing ID
        tokenHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenHistoryMockMvc.perform(post("/api/token-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TokenHistory in the database
        List<TokenHistory> tokenHistoryList = tokenHistoryRepository.findAll();
        assertThat(tokenHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTokenHistories() throws Exception {
        // Initialize the database
        tokenHistoryRepository.saveAndFlush(tokenHistory);

        // Get all the tokenHistoryList
        restTokenHistoryMockMvc.perform(get("/api/token-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].tpan").value(hasItem(DEFAULT_TPAN)))
            .andExpect(jsonPath("$.[*].hashKey").value(hasItem(DEFAULT_HASH_KEY)))
            .andExpect(jsonPath("$.[*].tokenExpiry").value(hasItem(DEFAULT_TOKEN_EXPIRY)))
            .andExpect(jsonPath("$.[*].tokenStatus").value(hasItem(DEFAULT_TOKEN_STATUS)))
            .andExpect(jsonPath("$.[*].maskPan").value(hasItem(DEFAULT_MASK_PAN)))
            .andExpect(jsonPath("$.[*].enPan").value(hasItem(DEFAULT_EN_PAN)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].trId").value(hasItem(DEFAULT_TR_ID)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].historyDt").value(hasItem(sameInstant(DEFAULT_HISTORY_DT))));
    }
    
    @Test
    @Transactional
    public void getTokenHistory() throws Exception {
        // Initialize the database
        tokenHistoryRepository.saveAndFlush(tokenHistory);

        // Get the tokenHistory
        restTokenHistoryMockMvc.perform(get("/api/token-histories/{id}", tokenHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tokenHistory.getId().intValue()))
            .andExpect(jsonPath("$.tpan").value(DEFAULT_TPAN))
            .andExpect(jsonPath("$.hashKey").value(DEFAULT_HASH_KEY))
            .andExpect(jsonPath("$.tokenExpiry").value(DEFAULT_TOKEN_EXPIRY))
            .andExpect(jsonPath("$.tokenStatus").value(DEFAULT_TOKEN_STATUS))
            .andExpect(jsonPath("$.maskPan").value(DEFAULT_MASK_PAN))
            .andExpect(jsonPath("$.enPan").value(DEFAULT_EN_PAN))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE))
            .andExpect(jsonPath("$.trId").value(DEFAULT_TR_ID))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.historyDt").value(sameInstant(DEFAULT_HISTORY_DT)));
    }

    @Test
    @Transactional
    public void getNonExistingTokenHistory() throws Exception {
        // Get the tokenHistory
        restTokenHistoryMockMvc.perform(get("/api/token-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTokenHistory() throws Exception {
        // Initialize the database
        tokenHistoryService.save(tokenHistory);

        int databaseSizeBeforeUpdate = tokenHistoryRepository.findAll().size();

        // Update the tokenHistory
        TokenHistory updatedTokenHistory = tokenHistoryRepository.findById(tokenHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTokenHistory are not directly saved in db
        em.detach(updatedTokenHistory);
        updatedTokenHistory
            .tpan(UPDATED_TPAN)
            .hashKey(UPDATED_HASH_KEY)
            .tokenExpiry(UPDATED_TOKEN_EXPIRY)
            .tokenStatus(UPDATED_TOKEN_STATUS)
            .maskPan(UPDATED_MASK_PAN)
            .enPan(UPDATED_EN_PAN)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .trId(UPDATED_TR_ID)
            .version(UPDATED_VERSION)
            .historyDt(UPDATED_HISTORY_DT);

        restTokenHistoryMockMvc.perform(put("/api/token-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTokenHistory)))
            .andExpect(status().isOk());

        // Validate the TokenHistory in the database
        List<TokenHistory> tokenHistoryList = tokenHistoryRepository.findAll();
        assertThat(tokenHistoryList).hasSize(databaseSizeBeforeUpdate);
        TokenHistory testTokenHistory = tokenHistoryList.get(tokenHistoryList.size() - 1);
        assertThat(testTokenHistory.getTpan()).isEqualTo(UPDATED_TPAN);
        assertThat(testTokenHistory.getHashKey()).isEqualTo(UPDATED_HASH_KEY);
        assertThat(testTokenHistory.getTokenExpiry()).isEqualTo(UPDATED_TOKEN_EXPIRY);
        assertThat(testTokenHistory.getTokenStatus()).isEqualTo(UPDATED_TOKEN_STATUS);
        assertThat(testTokenHistory.getMaskPan()).isEqualTo(UPDATED_MASK_PAN);
        assertThat(testTokenHistory.getEnPan()).isEqualTo(UPDATED_EN_PAN);
        assertThat(testTokenHistory.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testTokenHistory.getTrId()).isEqualTo(UPDATED_TR_ID);
        assertThat(testTokenHistory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTokenHistory.getHistoryDt()).isEqualTo(UPDATED_HISTORY_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingTokenHistory() throws Exception {
        int databaseSizeBeforeUpdate = tokenHistoryRepository.findAll().size();

        // Create the TokenHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenHistoryMockMvc.perform(put("/api/token-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TokenHistory in the database
        List<TokenHistory> tokenHistoryList = tokenHistoryRepository.findAll();
        assertThat(tokenHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTokenHistory() throws Exception {
        // Initialize the database
        tokenHistoryService.save(tokenHistory);

        int databaseSizeBeforeDelete = tokenHistoryRepository.findAll().size();

        // Delete the tokenHistory
        restTokenHistoryMockMvc.perform(delete("/api/token-histories/{id}", tokenHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TokenHistory> tokenHistoryList = tokenHistoryRepository.findAll();
        assertThat(tokenHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
