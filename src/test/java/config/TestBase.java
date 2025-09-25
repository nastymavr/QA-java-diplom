package config;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;  // Импортируем константы HTTP статус-кодов
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.ApiHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestBase {
    protected WebDriver driver;
    protected String browser;
    protected String accessToken;  // Токен для удаленного удаления пользователя

    // Конструктор для задания браузера через файл конфигурации
    public TestBase() {
        this.browser = loadBrowserFromConfig();
    }

    // Метод для загрузки браузера из конфигурационного файла
    private String loadBrowserFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            return properties.getProperty("browser", "chrome");  // Если не найдено, по умолчанию chrome
        } catch (IOException ex) {
            System.out.println("Ошибка при чтении config.properties: " + ex.getMessage());
            return "chrome";  // По умолчанию используем Chrome
        }
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
        if (registerResponse.getStatusCode() == HttpStatus.SC_OK) {  // Используем SC_OK вместо 200
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
            if (deleteResponse.getStatusCode() == HttpStatus.SC_OK) {  // Используем SC_OK вместо 200
                System.out.println("Пользователь успешно удален через API.");
            } else {
                System.out.println("Ошибка при удалении пользователя через API. Код ошибки: "
                        + deleteResponse.getStatusCode());
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
