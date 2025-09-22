package config;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.ApiHelper;

public class TestBase {
    protected WebDriver driver;
    protected String browser;
    protected String accessToken;  // Токен для удаленного удаления пользователя

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
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");

        // Регистрация пользователя через API
        Response registerResponse = ApiHelper.registerNewUser();

        // Логируем ответ от API для поиска проблемы
        System.out.println("Ответ от API: " + registerResponse.asString());

        // Проверяем успешность регистрации
        if (registerResponse.getStatusCode() == 200) {
            // Получаем токен из ответа регистрации для дальнейших запросов (например, для удаления)
            accessToken = registerResponse.jsonPath().getString("accessToken");
            System.out.println("Регистрация прошла успешно, токен: " + accessToken);
        } else {
            throw new RuntimeException("Ошибка регистрации пользователя через API. Код ошибки: "
                    + registerResponse.getStatusCode() + " Ответ: " + registerResponse.asString());
        }
    }

    @After
    public void tearDown() {
        // Удаляем пользователя через API
        if (accessToken != null) {
            Response deleteResponse = ApiHelper.deleteUser(accessToken);
            if (deleteResponse.getStatusCode() == 200) {
                System.out.println("Пользователь успешно удален через API.");
            } else {
                System.out.println("Ошибка при удалении пользователя через API.");
            }
        }

        // Закрытие драйвера
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }

    // Геттер для доступа к драйверу
    public WebDriver getDriver() {
        return driver;
    }
}
