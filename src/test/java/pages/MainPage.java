package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By personalCabinetButton = By.xpath("//a[.//p[text()='Личный Кабинет']]");
    private By constructorButton = By.xpath("//a[.//p[text()='Конструктор']]");
    private By bunsTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Булки']]");
    private By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Соусы']]");
    private By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Начинки']]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginAccount() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("login"));
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public void clickPersonalCabinet() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("account"));
    }

    @Step("Клик по кнопке 'Конструктор'")
    public void goToConstructor() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
    }

    @Step("Переход на вкладку 'Булки'")
    public void goToBuns() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(bunsTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        wait.until(ExpectedConditions.attributeContains(bunsTab, "class", "tab_tab_type_current"));
    }

    @Step("Переход на вкладку 'Соусы'")
    public void goToSauces() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(saucesTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        wait.until(ExpectedConditions.attributeContains(saucesTab, "class", "tab_tab_type_current"));
    }

    @Step("Переход на вкладку 'Начинки'")
    public void goToFillings() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(fillingsTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        wait.until(ExpectedConditions.attributeContains(fillingsTab, "class", "tab_tab_type_current"));
    }

    @Step("Проверка видимости раздела '{sectionName}'")
    public boolean isSectionVisible(String sectionName) {
        By locator = By.xpath("//h2[text()='" + sectionName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    @Step("Ожидание появления кнопки 'Войти в аккаунт'")
    public void waitForLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton));
    }
}
