package tests;

import config.TestBase;
import data.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import java.time.Duration;

public class LoginTest extends TestBase {

    private static class User {
        String email;
        String password;

        User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Метод для регистрации нового пользователя
    private User registerNewUser() {
        // setUp();  // Не нужно здесь, браузер уже инициализирован в методе @Before

        String randomEmail = TestData.getRandomEmail();
        String randomName = TestData.getRandomName();
        String randomPassword = TestData.getRandomPassword();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(randomName, randomEmail, randomPassword);

        registerPage.waitForRegistrationResult();
        Assert.assertTrue("Регистрация не удалась", registerPage.isRegistrationSuccessful());

        driver.get("https://stellarburgers.nomoreparties.site/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Войти в аккаунт']")));

        return new User(randomEmail, randomPassword);
    }

    @Test
    @Description("Вход по кнопке 'Войти в аккаунт' на главной странице")
    @Step("Тестирование входа по кнопке 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainPage() {
        User user = registerNewUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.waitForLoginButton();  // Ожидаем появления кнопки "Войти в аккаунт"

        mainPage.clickLoginAccount();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(user.email, user.password);

        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }

    @Test
    @Description("Вход через кнопку 'Личный кабинет'")
    @Step("Тестирование входа через кнопку 'Личный кабинет'")
    public void testLoginFromPersonalAccount() {
        // Регистрируем нового пользователя
        User user = registerNewUser();

        // Создаем объект страницы логина
        LoginPage loginPage = new LoginPage(driver);

        // Кликаем по кнопке "Конструктор" перед входом в личный кабинет
        loginPage.clickConstructor();

        // Ожидаем появления кнопки "Личный кабинет"
        loginPage.clickPersonalCabinet();

        // Логинимся с помощью нового пользователя
        loginPage.login(user.email, user.password);

        // Проверяем, что вход успешный
        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());

        // Проверяем, что ошибки при входе нет
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }


    @Test
    @Description("Вход через кнопку в форме регистрации")
    @Step("Тестирование входа через кнопку в форме регистрации")
    public void testLoginFromRegisterPage() {
        User user = registerNewUser();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.goToLoginPage();  // Переходим на страницу логина

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(user.email, user.password);

        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }

    @Test
    @Description("Вход через форму восстановления пароля → кнопка 'Войти'")
    @Step("Тестирование входа через форму восстановления пароля")
    public void testLoginFromRecoverPage() {
        User user = registerNewUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginAccount();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickRecover();

        loginPage.clickRecoverLoginButton();

        loginPage.login(user.email, user.password);

        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }
}
