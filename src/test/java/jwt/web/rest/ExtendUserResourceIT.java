package jwt.web.rest;

import jwt.JhipsterApp;
import jwt.domain.ExtendUser;
import jwt.domain.User;
import jwt.repository.ExtendUserRepository;
import jwt.service.ExtendUserService;
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
 * Integration tests for the {@link ExtendUserResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class ExtendUserResourceIT {

    @Autowired
    private ExtendUserRepository extendUserRepository;

    @Autowired
    private ExtendUserService extendUserService;

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

    private MockMvc restExtendUserMockMvc;

    private ExtendUser extendUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExtendUserResource extendUserResource = new ExtendUserResource(extendUserService);
        this.restExtendUserMockMvc = MockMvcBuilders.standaloneSetup(extendUserResource)
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
    public static ExtendUser createEntity(EntityManager em) {
        ExtendUser extendUser = new ExtendUser();
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        extendUser.setUser(user);
        return extendUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendUser createUpdatedEntity(EntityManager em) {
        ExtendUser extendUser = new ExtendUser();
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        extendUser.setUser(user);
        return extendUser;
    }

    @BeforeEach
    public void initTest() {
        extendUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtendUser() throws Exception {
        int databaseSizeBeforeCreate = extendUserRepository.findAll().size();

        // Create the ExtendUser
        restExtendUserMockMvc.perform(post("/api/extend-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendUser)))
            .andExpect(status().isCreated());

        // Validate the ExtendUser in the database
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExtendUser testExtendUser = extendUserList.get(extendUserList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        assertThat(testExtendUser.getId()).isEqualTo(testExtendUser.getUser().getId());
    }

    @Test
    @Transactional
    public void createExtendUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extendUserRepository.findAll().size();

        // Create the ExtendUser with an existing ID
        extendUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtendUserMockMvc.perform(post("/api/extend-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendUser)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendUser in the database
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateExtendUserMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        extendUserService.save(extendUser);
        int databaseSizeBeforeCreate = extendUserRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the extendUser
        ExtendUser updatedExtendUser = extendUserRepository.findById(extendUser.getId()).get();
        // Disconnect from session so that the updates on updatedExtendUser are not directly saved in db
        em.detach(updatedExtendUser);

        // Update the User with new association value
        updatedExtendUser.setUser(user);

        // Update the entity
        restExtendUserMockMvc.perform(put("/api/extend-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtendUser)))
            .andExpect(status().isOk());

        // Validate the ExtendUser in the database
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeCreate);
        ExtendUser testExtendUser = extendUserList.get(extendUserList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testExtendUser.getId()).isEqualTo(testExtendUser.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllExtendUsers() throws Exception {
        // Initialize the database
        extendUserRepository.saveAndFlush(extendUser);

        // Get all the extendUserList
        restExtendUserMockMvc.perform(get("/api/extend-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendUser.getId().intValue())));
    }

    @Test
    @Transactional
    public void getExtendUser() throws Exception {
        // Initialize the database
        extendUserRepository.saveAndFlush(extendUser);

        // Get the extendUser
        restExtendUserMockMvc.perform(get("/api/extend-users/{id}", extendUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extendUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExtendUser() throws Exception {
        // Get the extendUser
        restExtendUserMockMvc.perform(get("/api/extend-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtendUser() throws Exception {
        // Initialize the database
        extendUserService.save(extendUser);

        int databaseSizeBeforeUpdate = extendUserRepository.findAll().size();

        // Update the extendUser
        ExtendUser updatedExtendUser = extendUserRepository.findById(extendUser.getId()).get();
        // Disconnect from session so that the updates on updatedExtendUser are not directly saved in db
        em.detach(updatedExtendUser);

        restExtendUserMockMvc.perform(put("/api/extend-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtendUser)))
            .andExpect(status().isOk());

        // Validate the ExtendUser in the database
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeUpdate);
        ExtendUser testExtendUser = extendUserList.get(extendUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingExtendUser() throws Exception {
        int databaseSizeBeforeUpdate = extendUserRepository.findAll().size();

        // Create the ExtendUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtendUserMockMvc.perform(put("/api/extend-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendUser)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendUser in the database
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtendUser() throws Exception {
        // Initialize the database
        extendUserService.save(extendUser);

        int databaseSizeBeforeDelete = extendUserRepository.findAll().size();

        // Delete the extendUser
        restExtendUserMockMvc.perform(delete("/api/extend-users/{id}", extendUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtendUser> extendUserList = extendUserRepository.findAll();
        assertThat(extendUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
