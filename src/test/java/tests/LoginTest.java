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

    // Вспомогательный класс для хранения данных пользователя
    private static class User {
        String email;
        String password;

        User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Метод для регистрации нового пользователя и возврата его данных
    @Step("Регистрация нового пользователя с email: {email} и паролем: {password}")
    private User registerNewUser() {
        String randomEmail = TestData.getRandomEmail();
        String randomName = TestData.getRandomName();
        String randomPassword = TestData.getRandomPassword();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(randomName, randomEmail, randomPassword);

        // Ждём редирект на /login после регистрации
        registerPage.waitForRegistrationResult();
        Assert.assertTrue("Регистрация не удалась", registerPage.isRegistrationSuccessful());

        System.out.println("Регистрация завершена, текущий URL: " + driver.getCurrentUrl());

        // Открываем главную
        driver.get("https://stellarburgers.nomoreparties.site/");

        // Ждём появления кнопки "Войти в аккаунт"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Войти в аккаунт']")));

        System.out.println("Главная страница загружена, кнопка 'Войти в аккаунт' доступна");

        return new User(randomEmail, randomPassword);
    }

    @Test
    @Description("Вход через кнопку 'Личный кабинет' после регистрации и перехода через Конструктор")
    @Step("Тестирование входа через кнопку 'Личный кабинет'")
    public void testLoginFromPersonalCabinet() {
        // Регистрируем нового пользователя
        User user = registerNewUser();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.goToLoginPage();

        // Создаем объект mainPage
        MainPage mainPage = new MainPage(driver);

        // Переходим на главную страницу через кнопку "Конструктор"
        mainPage.goToConstructor();

        // Теперь кликаем на "Личный кабинет"
        mainPage.clickPersonalCabinet();

        // Переходим на страницу входа
        LoginPage loginPage = new LoginPage(driver);

        // Вводим email и пароль для входа
        loginPage.login(user.email, user.password);

        // Проверяем успешность входа
        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }

    @Test
    @Description("Вход по кнопке 'Войти в аккаунт' на главной странице")
    @Step("Тестирование входа по кнопке 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainPage() {
        User user = registerNewUser();

        // Открываем главную страницу и ждем кнопку "Войти в аккаунт"
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForLoginButton(); // Ожидаем появления кнопки "Войти в аккаунт"

        // Нажимаем на кнопку "Войти в аккаунт"
        mainPage.clickLoginAccount();

        // Переходим на страницу логина
        LoginPage loginPage = new LoginPage(driver);

        // Вводим email и пароль для входа
        loginPage.login(user.email, user.password);

        // Проверяем успешность входа
        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    @Step("Тестирование входа через кнопку в форме регистрации")
    public void testLoginFromRegisterPage() {
        User user = registerNewUser();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.goToLoginPage();

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

        // Открываем главную страницу и нажимаем "Войти в аккаунт"
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginAccount();  // Нажимаем на кнопку "Войти в аккаунт"

        // Переходим на страницу логина
        LoginPage loginPage = new LoginPage(driver);

        // Нажимаем на ссылку "Восстановить пароль" для перехода на страницу восстановления
        loginPage.clickRecover();  // Клик по кнопке "Восстановить пароль"

        // Нажимаем кнопку "Войти" на странице восстановления пароля, чтобы вернуться на страницу входа
        loginPage.clickRecoverLoginButton();  // Клик по кнопке "Войти" на странице восстановления пароля

        // Вводим данные для входа (email и пароль)
        loginPage.login(user.email, user.password);  // Вводим email и пароль и выполняем вход

        // Проверяем, что вход выполнен успешно
        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());
    }
}
