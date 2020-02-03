package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.ApiKey;
import jwt.repository.ApiKeyRepository;
import jwt.service.ApiKeyService;
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
 * Integration tests for the {@link ApiKeyResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class ApiKeyResourceIT {

    private static final String DEFAULT_REQUESTOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_API_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_API_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiKeyService apiKeyService;

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

    private MockMvc restApiKeyMockMvc;

    private ApiKey apiKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiKeyResource apiKeyResource = new ApiKeyResource(apiKeyService);
        this.restApiKeyMockMvc = MockMvcBuilders.standaloneSetup(apiKeyResource)
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
    public static ApiKey createEntity(EntityManager em) {
        ApiKey apiKey = new ApiKey()
            .requestorType(DEFAULT_REQUESTOR_TYPE)
            .requestorId(DEFAULT_REQUESTOR_ID)
            .apiKey(DEFAULT_API_KEY)
            .apiStatus(DEFAULT_API_STATUS)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE);
        return apiKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKey createUpdatedEntity(EntityManager em) {
        ApiKey apiKey = new ApiKey()
            .requestorType(UPDATED_REQUESTOR_TYPE)
            .requestorId(UPDATED_REQUESTOR_ID)
            .apiKey(UPDATED_API_KEY)
            .apiStatus(UPDATED_API_STATUS)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        return apiKey;
    }

    @BeforeEach
    public void initTest() {
        apiKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiKey() throws Exception {
        int databaseSizeBeforeCreate = apiKeyRepository.findAll().size();

        // Create the ApiKey
        restApiKeyMockMvc.perform(post("/api/api-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiKey)))
            .andExpect(status().isCreated());

        // Validate the ApiKey in the database
        List<ApiKey> apiKeyList = apiKeyRepository.findAll();
        assertThat(apiKeyList).hasSize(databaseSizeBeforeCreate + 1);
        ApiKey testApiKey = apiKeyList.get(apiKeyList.size() - 1);
        assertThat(testApiKey.getRequestorType()).isEqualTo(DEFAULT_REQUESTOR_TYPE);
        assertThat(testApiKey.getRequestorId()).isEqualTo(DEFAULT_REQUESTOR_ID);
        assertThat(testApiKey.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testApiKey.getApiStatus()).isEqualTo(DEFAULT_API_STATUS);
        assertThat(testApiKey.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testApiKey.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createApiKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiKeyRepository.findAll().size();

        // Create the ApiKey with an existing ID
        apiKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiKeyMockMvc.perform(post("/api/api-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiKey)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKey in the database
        List<ApiKey> apiKeyList = apiKeyRepository.findAll();
        assertThat(apiKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApiKeys() throws Exception {
        // Initialize the database
        apiKeyRepository.saveAndFlush(apiKey);

        // Get all the apiKeyList
        restApiKeyMockMvc.perform(get("/api/api-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestorType").value(hasItem(DEFAULT_REQUESTOR_TYPE)))
            .andExpect(jsonPath("$.[*].requestorId").value(hasItem(DEFAULT_REQUESTOR_ID)))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY)))
            .andExpect(jsonPath("$.[*].apiStatus").value(hasItem(DEFAULT_API_STATUS)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getApiKey() throws Exception {
        // Initialize the database
        apiKeyRepository.saveAndFlush(apiKey);

        // Get the apiKey
        restApiKeyMockMvc.perform(get("/api/api-keys/{id}", apiKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiKey.getId().intValue()))
            .andExpect(jsonPath("$.requestorType").value(DEFAULT_REQUESTOR_TYPE))
            .andExpect(jsonPath("$.requestorId").value(DEFAULT_REQUESTOR_ID))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY))
            .andExpect(jsonPath("$.apiStatus").value(DEFAULT_API_STATUS))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingApiKey() throws Exception {
        // Get the apiKey
        restApiKeyMockMvc.perform(get("/api/api-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiKey() throws Exception {
        // Initialize the database
        apiKeyService.save(apiKey);

        int databaseSizeBeforeUpdate = apiKeyRepository.findAll().size();

        // Update the apiKey
        ApiKey updatedApiKey = apiKeyRepository.findById(apiKey.getId()).get();
        // Disconnect from session so that the updates on updatedApiKey are not directly saved in db
        em.detach(updatedApiKey);
        updatedApiKey
            .requestorType(UPDATED_REQUESTOR_TYPE)
            .requestorId(UPDATED_REQUESTOR_ID)
            .apiKey(UPDATED_API_KEY)
            .apiStatus(UPDATED_API_STATUS)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restApiKeyMockMvc.perform(put("/api/api-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiKey)))
            .andExpect(status().isOk());

        // Validate the ApiKey in the database
        List<ApiKey> apiKeyList = apiKeyRepository.findAll();
        assertThat(apiKeyList).hasSize(databaseSizeBeforeUpdate);
        ApiKey testApiKey = apiKeyList.get(apiKeyList.size() - 1);
        assertThat(testApiKey.getRequestorType()).isEqualTo(UPDATED_REQUESTOR_TYPE);
        assertThat(testApiKey.getRequestorId()).isEqualTo(UPDATED_REQUESTOR_ID);
        assertThat(testApiKey.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testApiKey.getApiStatus()).isEqualTo(UPDATED_API_STATUS);
        assertThat(testApiKey.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testApiKey.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApiKey() throws Exception {
        int databaseSizeBeforeUpdate = apiKeyRepository.findAll().size();

        // Create the ApiKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiKeyMockMvc.perform(put("/api/api-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiKey)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKey in the database
        List<ApiKey> apiKeyList = apiKeyRepository.findAll();
        assertThat(apiKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiKey() throws Exception {
        // Initialize the database
        apiKeyService.save(apiKey);

        int databaseSizeBeforeDelete = apiKeyRepository.findAll().size();

        // Delete the apiKey
        restApiKeyMockMvc.perform(delete("/api/api-keys/{id}", apiKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiKey> apiKeyList = apiKeyRepository.findAll();
        assertThat(apiKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
