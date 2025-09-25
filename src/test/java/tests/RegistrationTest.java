package tests;

import config.TestBase;
import data.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;
import pages.RegisterPage;

public class RegistrationTest extends TestBase {

    // Метод для создания случайного пользователя и регистрации
    @Step("Регистрация пользователя с email: {email}, password: {password}")
    private void registerUserAndCheckSuccess(String name, String email, String password) {
        // Вместо указания конкретного браузера, берем его из конфигурации TestBase
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();  // Открываем страницу регистрации

        registerPage.register(name, email, password);  // Регистрируем пользователя
        registerPage.waitForRegistrationResult();      // Ждём результат
        Assert.assertTrue("Регистрация не прошла", registerPage.isRegistrationSuccessful());
    }

    @Test
    @Description("Успешная регистрация нового пользователя с браузером, заданным в config.properties")
    @Step("Тест успешной регистрации нового пользователя")
    public void testSuccessfulRegistration() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();

        registerUserAndCheckSuccess(name, email, password);
    }

    @Test
    @Description("Ошибка при регистрации с коротким паролем с браузером, заданным в config.properties")
    @Step("Тест ошибки регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String shortPassword = TestData.shortPassword;

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(name, email, shortPassword);

        // Ждём результата регистрации (появление ошибки)
        registerPage.waitForRegistrationResult();

        Assert.assertTrue("Ожидалась ошибка 'Пароль слишком короткий'", registerPage.isPasswordErrorVisible());
    }
}
