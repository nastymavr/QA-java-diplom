package tests;

import config.TestBase;
import data.TestData;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import pages.RegisterPage;

public class RegistrationTest {

    // Метод для создания случайного пользователя и регистрации
    private void registerUserAndCheckSuccess(String browser, String name, String email, String password) {
        TestBase testBase = new TestBase(browser);
        testBase.setUp();

        RegisterPage registerPage = new RegisterPage(testBase.getDriver());
        registerPage.open();  // Открываем страницу регистрации

        registerPage.register(name, email, password);  // Регистрируем пользователя
        registerPage.waitForRegistrationResult();      // Ждём результат
        Assert.assertTrue("Регистрация не прошла", registerPage.isRegistrationSuccessful());

        testBase.tearDown();
    }

    @Test
    @Description("Успешная регистрация нового пользователя с браузером Chrome")
    public void testSuccessfulRegistrationChrome() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();

        registerUserAndCheckSuccess("chrome", name, email, password);
    }

    @Test
    @Description("Успешная регистрация нового пользователя с браузером Yandex")
    public void testSuccessfulRegistrationYandex() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();

        registerUserAndCheckSuccess("yandex", name, email, password);
    }

    @Test
    @Description("Ошибка при регистрации с коротким паролем с браузером Chrome")
    public void testRegistrationWithShortPasswordChrome() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String shortPassword = TestData.shortPassword;

        TestBase testBase = new TestBase("chrome");
        testBase.setUp();

        RegisterPage registerPage = new RegisterPage(testBase.getDriver());
        registerPage.open();
        registerPage.register(name, email, shortPassword);

        // Ждём результата регистрации (появление ошибки)
        registerPage.waitForRegistrationResult();

        Assert.assertTrue("Ожидалась ошибка 'Пароль слишком короткий'", registerPage.isPasswordErrorVisible());

        testBase.tearDown();
    }

    @Test
    @Description("Ошибка при регистрации с коротким паролем с браузером Yandex")
    public void testRegistrationWithShortPasswordYandex() {
        String name = TestData.getRandomName();
        String email = TestData.getRandomEmail();
        String shortPassword = TestData.shortPassword;

        TestBase testBase = new TestBase("yandex");
        testBase.setUp();

        RegisterPage registerPage = new RegisterPage(testBase.getDriver());
        registerPage.open();
        registerPage.register(name, email, shortPassword);

        // Ждём результата регистрации (появление ошибки)
        registerPage.waitForRegistrationResult();

        Assert.assertTrue("Ожидалась ошибка 'Пароль слишком короткий'", registerPage.isPasswordErrorVisible());

        testBase.tearDown();
    }
}
