package tests;

import config.TestBase;
import data.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;
import pages.RegisterPage;

public class RegistrationTest extends TestBase {

    // Метод для регистрации пользователя и проверки успешности
    @Step("Регистрация пользователя с именем {name}, email {email}, и паролем {password}")
    private void registerUserAndCheckSuccess(String name, String email, String password) {
        RegisterPage registerPage = new RegisterPage(driver);  // Используем драйвер из TestBase
        registerPage.open();  // Открываем страницу регистрации

        // Регистрируем пользователя
        registerPage.register(name, email, password);
        registerPage.waitForRegistrationResult();  // Ждём результат

        // Проверяем успешность регистрации
        Assert.assertTrue("Регистрация не прошла", registerPage.isRegistrationSuccessful());
    }

    @Test
    @Description("Успешная регистрация нового пользователя с браузером Chrome")
    @Step("Тестирование успешной регистрации с браузером Chrome")
    public void testSuccessfulRegistrationChrome() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();

        // Вызов метода регистрации с проверкой успеха
        registerUserAndCheckSuccess(name, email, password);
    }

    @Test
    @Description("Успешная регистрация нового пользователя с браузером Yandex")
    @Step("Тестирование успешной регистрации с браузером Yandex")
    public void testSuccessfulRegistrationYandex() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();

        // Вызов метода регистрации с проверкой успеха
        registerUserAndCheckSuccess(name, email, password);
    }

    @Test
    @Description("Ошибка при регистрации с коротким паролем")
    @Step("Тестирование регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String shortPassword = TestData.shortPassword;

        RegisterPage registerPage = new RegisterPage(driver);  // Используем драйвер из TestBase
        registerPage.open();  // Открываем страницу регистрации
        registerPage.register(name, email, shortPassword);  // Регистрируем пользователя с коротким паролем

        // Ждём результат регистрации (появление ошибки)
        registerPage.waitForRegistrationResult();

        // Проверяем, что ошибка появилась
        Assert.assertTrue("Ожидалась ошибка 'Пароль слишком короткий'", registerPage.isPasswordErrorVisible());
    }
}
