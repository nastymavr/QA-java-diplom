package praktikum.api.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public abstract class TestBase {

    protected static RequestSpecification spec;

    @BeforeClass
    public static void setup() {
        String base = System.getProperty("baseUrl");
        String DEFAULT_BASE = "https://stellarburgers.nomoreparties.site";
        if (base == null || base.isBlank()) {
            base = DEFAULT_BASE;
            System.out.println("baseUrl not provided, using default: " + DEFAULT_BASE);
        }

        RestAssured.baseURI = base + "/api";

        spec = new RequestSpecBuilder()
                .setContentType("application/json")
                .addFilter(new AllureRestAssured()) // логирование запросов/ответов в Allure
                .build();

        RestAssured.requestSpecification = spec;
    }
}
