package config;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ApiHelper;
import config.DriverFactory;

public class TestBase {
    protected WebDriver driver;
    protected String browser;
    protected String accessToken;  // Токен для удаленного удаления пользователя
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);  // Логгер для удобного логирования

    // Конструктор для задания браузера (chrome или yandex)
    public TestBase(String browser) {
        this.browser = browser;
    }

    // Публичный конструктор без аргументов для JUnit 4
    public TestBase() {
        // Устанавливаем браузер по умолчанию
        this("chrome");
    }

    @Before
    public void setUp() {
        // Инициализация драйвера через фабрику
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");

        // Регистрация пользователя через API
        Response registerResponse = ApiHelper.registerNewUser();

        // Логируем ответ от API для поиска проблемы
        logger.info("Ответ от API: " + registerResponse.asString());

        // Проверяем успешность регистрации
        if (registerResponse.getStatusCode() == 200) {
            // Получаем токен из ответа регистрации для дальнейших запросов (например, для удаления)
            accessToken = registerResponse.jsonPath().getString("accessToken");
            logger.info("Регистрация прошла успешно, токен: " + accessToken);
        } else {
            logger.error("Ошибка регистрации пользователя через API. Код ошибки: "
                    + registerResponse.getStatusCode() + " Ответ: " + registerResponse.asString());
            throw new RuntimeException("Ошибка регистрации пользователя через API. Код ошибки: "
                    + registerResponse.getStatusCode() + " Ответ: " + registerResponse.asString());
        }
    }

    @After
    public void tearDown() {
        // Удаление пользователя через API
        if (accessToken != null) {
            Response deleteResponse = ApiHelper.deleteUser(accessToken);
            if (deleteResponse.getStatusCode() == 200) {
                logger.info("Пользователь успешно удален через API.");
            } else {
                logger.error("Ошибка при удалении пользователя через API.");
            }
        }

        // Закрытие драйвера
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.error("Ошибка при закрытии драйвера: " + e.getMessage(), e);
            }
        }
    }

    // Геттер для доступа к драйверу
    public WebDriver getDriver() {
        return driver;
    }
}
