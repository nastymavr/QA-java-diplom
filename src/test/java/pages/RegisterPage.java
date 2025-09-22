package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameField = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input");
    private By emailField = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input");
    private By passwordField = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/div/input");
    private By registerButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/button");
    private By errorMessage = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p");
    private By loginLink = By.xpath("//*[@id=\"root\"]/div/main/div/div/p/a");

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
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        nameElement.sendKeys(name);

        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.sendKeys(email);

        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.sendKeys(password);

        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerBtn.click();  // клик без ожиданий результата
    }

    @Step("Ожидание результата регистрации (редирект или ошибка)")
    public void waitForRegistrationResult() {
        WebDriverWait resultWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            resultWait.until(driver ->
                    driver.getCurrentUrl().contains("/login") ||
                            driver.findElements(errorMessage).size() > 0
            );
        } catch (Exception ignored) {}
    }

    @Step("Проверка успешной регистрации")
    public boolean isRegistrationSuccessful() {
        return driver.getCurrentUrl().contains("/login");
    }

    @Step("Проверка видимости ошибки пароля")
    public boolean isPasswordErrorVisible() {
        return driver.findElements(errorMessage).size() > 0;
    }

    @Step("Переход на страницу входа")
    public void goToLoginPage() {
        WebElement loginLinkElement = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginLinkElement);
    }
}
