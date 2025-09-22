package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private By emailField = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input");
    private By passwordField = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input");
    private By loginButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/button");
    private By errorMessage = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/p");
    private By placeOrderButton = By.xpath("//*[@id=\"root\"]/div/main/section[2]/div/button"); // "Оформить заказ"
    private By recoverButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/a"); // Исправленный локатор для ссылки восстановления пароля
    private By recoverLoginButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/button"); // Кнопка для отправки запроса восстановления

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик по кнопке 'Восстановить' через JavaScript")
    public void clickRecoverLoginButton() {
        WebElement recoverBtn = wait.until(ExpectedConditions.elementToBeClickable(recoverLoginButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recoverBtn);  // Клик через JavaScript
    }

    @Step("Клик по кнопке 'Восстановить пароль' для перехода на страницу восстановления")
    public void clickRecover() {
        WebElement recoverBtn = wait.until(ExpectedConditions.elementToBeClickable(recoverButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recoverBtn);  // Клик по ссылке восстановления
    }

    @Step("Нажатие кнопки Войти через JavaScript")
    public void clickLogin() {
        WebElement loginBtn = driver.findElement(loginButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
    }

    @Step("Ожидание результата входа")
    public void waitForLoginResult() {
        WebDriverWait resultWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Ждём либо кнопку оформления заказа, либо сообщение об ошибке
            resultWait.until(driver ->
                    driver.findElements(placeOrderButton).size() > 0 ||
                            driver.findElements(errorMessage).size() > 0
            );
        } catch (Exception ignored) {}
    }

    @Step("Авторизация с email {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        waitForLoginResult();
    }

    @Step("Проверка успешного входа")
    public boolean isLoginSuccessful() {
        return driver.findElements(placeOrderButton).size() > 0;
    }

    @Step("Проверка видимости ошибки при неверных данных")
    public boolean isErrorMessageVisible() {
        return driver.findElements(errorMessage).size() > 0;
    }
}
