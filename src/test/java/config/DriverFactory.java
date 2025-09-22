package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriverException;

import java.nio.file.Paths;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        // Автоматическая настройка ChromeDriver через WebDriverManager
        WebDriverManager.chromedriver().setup();

        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("yandex")) {
            // Устанавливаем путь до Яндекс.Браузера
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:/Users/nasty/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");

            // Путь до драйвера Яндекс.Браузера
            System.setProperty("webdriver.chrome.driver", "D:/Nasti/WebDriver/yandexdriver/yandexdriver.exe");

            try {
                return new ChromeDriver(options);
            } catch (WebDriverException e) {
                throw new RuntimeException("Ошибка при инициализации Яндекс.Браузера. Проверьте путь к драйверу.");
            }
        } else {
            throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
    }
}
