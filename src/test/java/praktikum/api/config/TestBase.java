package praktikum.api.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public abstract class TestBase {

    protected static RequestSpecification spec;
    protected static String baseUrl;

    @BeforeClass
    public static void setup() {
        // Получаем base URL из системных свойств
        String base = System.getProperty("baseUrl");
        String DEFAULT_BASE = "https://stellarburgers.nomoreparties.site";

        if (base == null || base.isBlank()) {
            base = DEFAULT_BASE;
            System.out.println("baseUrl not provided, using default: " + DEFAULT_BASE);
        }

        // Проверка валидности base URL
        if (!base.startsWith("http")) {
            throw new IllegalArgumentException("Invalid base URL: " + base);
        }

        // Настройка baseURI для API
        RestAssured.baseURI = base + "/api"; // для API запросов

        // Для браузерных тестов, если будет необходимо
        baseUrl = base; // для работы с UI тестами

        // Настройка спецификации для запросов
        spec = new RequestSpecBuilder()
                .setContentType("application/json")
                .addFilter(new AllureRestAssured()) // логирование запросов/ответов в Allure
                .build();

        RestAssured.requestSpecification = spec;

        System.out.println("Using base URL: " + RestAssured.baseURI);
    }
}
