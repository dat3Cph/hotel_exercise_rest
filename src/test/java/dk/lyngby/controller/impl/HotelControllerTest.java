package dk.lyngby.controller.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.config.Populate;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class HotelControllerTest {

    private static Javalin app;
    private static EntityManagerFactory emfTest;
    private static final String BASE_URL = "http://localhost:7777/api/v1";

    @BeforeAll
    static void beforeAll()
    {
        // Setup test database
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();

        // Start server
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
        Populate.populateData(emfTest);
    }
    @BeforeEach
    void setUp() {
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void read() {
    }

    @Test
    @DisplayName("Read all hotels")
    void readAll() {
        // given
        int listSize = 2;

        // when
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/hotels")
                .then()
                .assertThat()
                .statusCode(200)
                // then
                .body("size()", equalTo(listSize));
    }

    @Test
    void create() {

        // given

        // when

        // then
    }

    @Test
    void update() {

        // given

        // when

        // then
    }

    @Test
    void delete() {

        // given

        // when

        // then
    }

    @Test
    void validatePrimaryKey() {

        // given

        // when

        // then
    }

    @Test
    void validateEntity() {

        // given

        // when

        // then
    }
}