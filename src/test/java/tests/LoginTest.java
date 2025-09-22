package tests;

import config.TestBase;
import data.TestData;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import java.time.Duration;

public class LoginTest {

    // Вспомогательный класс для хранения данных пользователя
    private static class User {
        String email;
        String password;

        User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Публичный конструктор без аргументов для JUnit 4
    public LoginTest() {
        // Этот конструктор позволяет JUnit создавать объекты тестов без аргументов
    }

    // Метод для регистрации нового пользователя и возврата его данных
    private User registerNewUser(String browser) {
        TestBase testBase = new TestBase(browser); // Инициализация TestBase с нужным браузером
        testBase.setUp(); // Инициализация драйвера

        String randomEmail = TestData.getRandomEmail();
        String randomName = TestData.getRandomName();
        String randomPassword = TestData.getRandomPassword();

        RegisterPage registerPage = new RegisterPage(testBase.getDriver()); // Используем getDriver()
        registerPage.open();
        registerPage.register(randomName, randomEmail, randomPassword);

        // Ждём редирект на /login после регистрации
        registerPage.waitForRegistrationResult();
        Assert.assertTrue("Регистрация не удалась", registerPage.isRegistrationSuccessful());

        System.out.println("Регистрация завершена, текущий URL: " + testBase.getDriver().getCurrentUrl());

        // Открываем главную
        testBase.getDriver().get("https://stellarburgers.nomoreparties.site/");

        // Ждём появления кнопки "Войти в аккаунт"
        WebDriverWait wait = new WebDriverWait(testBase.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Войти в аккаунт']")));

        System.out.println("Главная страница загружена, кнопка 'Войти в аккаунт' доступна");

        return new User(randomEmail, randomPassword);
    }

    @Test
    @Description("Вход по кнопке 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainPage() {
        User user = registerNewUser("chrome"); // Регистрируем пользователя через API

        TestBase testBase = new TestBase("chrome"); // Инициализация TestBase с нужным браузером
        testBase.setUp(); // Инициализация драйвера

        MainPage mainPage = new MainPage(testBase.getDriver()); // Используем getDriver()
        mainPage.clickLoginAccount();

        LoginPage loginPage = new LoginPage(testBase.getDriver()); // Используем getDriver()
        loginPage.login(user.email, user.password);

        Assert.assertTrue("Вход не выполнен: кнопка 'Оформить заказ' не появилась", loginPage.isLoginSuccessful());
        Assert.assertFalse("Появилась ошибка при входе", loginPage.isErrorMessageVisible());

        testBase.tearDown(); // Завершаем работу с драйвером
    }
}
