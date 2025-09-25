package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.cssSelector("button.button_button_type_primary__1O7Bx");
    private By errorMessage = By.cssSelector("p.input__error.text_type_main-default");
    private By placeOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private By recoverLink = By.xpath("//a[text()='Восстановить пароль']");
    private By recoverLoginButton = By.xpath("//a[text()='Войти']");
    private By personalCabinetButton = By.xpath("//button[text()='Войти в аккаунт']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Новые методы для тестов ---
    @Step("Открытие страницы логина")
    public void open() {
        driver.get("https://stellarburgers.nomoreparties.site/login");
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public void clickPersonalCabinetButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    // --- Существующие методы ---
    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик по кнопке 'Войти'")
    public void clickLogin() {
        WebElement btn = driver.findElement(loginButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    @Step("Авторизация с email {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        waitForLoginResult();
    }

    @Step("Ожидание результата входа")
    public void waitForLoginResult() {
        wait.until(driver -> driver.findElements(placeOrderButton).size() > 0
                || driver.findElements(errorMessage).size() > 0);
    }

    @Step("Проверка успешного входа")
    public boolean isLoginSuccessful() {
        return driver.findElements(placeOrderButton).size() > 0;
    }

    @Step("Проверка видимости ошибки")
    public boolean isErrorMessageVisible() {
        return driver.findElements(errorMessage).size() > 0;
    }

    @Step("Клик по ссылке 'Восстановить пароль'")
    public void clickRecover() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(recoverLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    @Step("Клик по ссылке 'Войти' на странице восстановления")
    public void clickRecoverLoginButton() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(recoverLoginButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }
}
