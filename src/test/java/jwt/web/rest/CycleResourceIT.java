package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.Cycle;
import jwt.repository.CycleRepository;
import jwt.service.CycleService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static jwt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CycleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class CycleResourceIT {

    private static final Integer DEFAULT_CYCLE_COUNT = 1;
    private static final Integer UPDATED_CYCLE_COUNT = 2;

    private static final LocalDate DEFAULT_CYCLE_DATETIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CYCLE_DATETIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CYCLE_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_CYCLE_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CycleService cycleService;

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

    private MockMvc restCycleMockMvc;

    private Cycle cycle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CycleResource cycleResource = new CycleResource(cycleService);
        this.restCycleMockMvc = MockMvcBuilders.standaloneSetup(cycleResource)
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
    public static Cycle createEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .cycleCount(DEFAULT_CYCLE_COUNT)
            .cycleDatetime(DEFAULT_CYCLE_DATETIME)
            .cyclePassword(DEFAULT_CYCLE_PASSWORD);
        return cycle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cycle createUpdatedEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .cycleCount(UPDATED_CYCLE_COUNT)
            .cycleDatetime(UPDATED_CYCLE_DATETIME)
            .cyclePassword(UPDATED_CYCLE_PASSWORD);
        return cycle;
    }

    @BeforeEach
    public void initTest() {
        cycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCycle() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isCreated());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate + 1);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getCycleCount()).isEqualTo(DEFAULT_CYCLE_COUNT);
        assertThat(testCycle.getCycleDatetime()).isEqualTo(DEFAULT_CYCLE_DATETIME);
        assertThat(testCycle.getCyclePassword()).isEqualTo(DEFAULT_CYCLE_PASSWORD);
    }

    @Test
    @Transactional
    public void createCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle with an existing ID
        cycle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCycles() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cycleCount").value(hasItem(DEFAULT_CYCLE_COUNT)))
            .andExpect(jsonPath("$.[*].cycleDatetime").value(hasItem(DEFAULT_CYCLE_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].cyclePassword").value(hasItem(DEFAULT_CYCLE_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", cycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cycle.getId().intValue()))
            .andExpect(jsonPath("$.cycleCount").value(DEFAULT_CYCLE_COUNT))
            .andExpect(jsonPath("$.cycleDatetime").value(DEFAULT_CYCLE_DATETIME.toString()))
            .andExpect(jsonPath("$.cyclePassword").value(DEFAULT_CYCLE_PASSWORD));
    }

    @Test
    @Transactional
    public void getNonExistingCycle() throws Exception {
        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);

        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Update the cycle
        Cycle updatedCycle = cycleRepository.findById(cycle.getId()).get();
        // Disconnect from session so that the updates on updatedCycle are not directly saved in db
        em.detach(updatedCycle);
        updatedCycle
            .cycleCount(UPDATED_CYCLE_COUNT)
            .cycleDatetime(UPDATED_CYCLE_DATETIME)
            .cyclePassword(UPDATED_CYCLE_PASSWORD);

        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCycle)))
            .andExpect(status().isOk());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getCycleCount()).isEqualTo(UPDATED_CYCLE_COUNT);
        assertThat(testCycle.getCycleDatetime()).isEqualTo(UPDATED_CYCLE_DATETIME);
        assertThat(testCycle.getCyclePassword()).isEqualTo(UPDATED_CYCLE_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingCycle() throws Exception {
        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Create the Cycle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);

        int databaseSizeBeforeDelete = cycleRepository.findAll().size();

        // Delete the cycle
        restCycleMockMvc.perform(delete("/api/cycles/{id}", cycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
