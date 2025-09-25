package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameField = By.cssSelector("input[name='name']");
    private By emailField = By.cssSelector("input[name='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By registerButton = By.cssSelector("button.button_button_type_primary__1O7Bx");
    private By errorMessage = By.cssSelector("p.input__error.text_type_main-default");
    private By loginLink = By.xpath("//a[@href='/login']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открытие страницы регистрации")
    public void open() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
    }

    @Step("Регистрация нового пользователя")
    public void register(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    @Step("Ожидание результата регистрации")
    public void waitForRegistrationResult() {
        wait.until(driver -> driver.getCurrentUrl().contains("/login") || driver.findElements(errorMessage).size() > 0);
    }

    @Step("Проверка успешной регистрации")
    public boolean isRegistrationSuccessful() {
        return driver.getCurrentUrl().contains("/login");
    }

    @Step("Проверка ошибки пароля")
    public boolean isPasswordErrorVisible() {
        return driver.findElements(errorMessage).size() > 0;
    }

    @Step("Переход на страницу логина")
    public void goToLoginPage() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }
}
