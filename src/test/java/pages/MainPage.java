package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By bunsTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Булки']]");
    private By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Соусы']]");
    private By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Начинки']]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик по кнопке 'Войти в аккаунт' через JavaScript")
    public void clickLoginAccount() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);

        // Ожидаем изменения URL на /login
        wait.until(ExpectedConditions.urlContains("login"));
    }

    @Step("Переход к разделу 'Булки' через JavaScript")
    public void goToBuns() {
        WebElement bunsSection = wait.until(ExpectedConditions.elementToBeClickable(bunsTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bunsSection);
        wait.until(ExpectedConditions.attributeContains(bunsTab, "class", "tab_tab_type_current"));
    }

    @Step("Переход к разделу 'Соусы' через JavaScript")
    public void goToSauces() {
        WebElement saucesSection = wait.until(ExpectedConditions.elementToBeClickable(saucesTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saucesSection);
        wait.until(ExpectedConditions.attributeContains(saucesTab, "class", "tab_tab_type_current"));
    }

    @Step("Переход к разделу 'Начинки' через JavaScript")
    public void goToFillings() {
        WebElement fillingsSection = wait.until(ExpectedConditions.elementToBeClickable(fillingsTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fillingsSection);
        wait.until(ExpectedConditions.attributeContains(fillingsTab, "class", "tab_tab_type_current"));
    }

    @Step("Проверка видимости раздела")
    public boolean isSectionVisible(String sectionName) {
        By locator = By.xpath("//h2[text()='" + sectionName + "']");
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return section.isDisplayed();
    }

    // Добавляем метод ожидания кнопки "Войти в аккаунт"
    @Step("Ожидание появления кнопки 'Войти в аккаунт'")
    public void waitForLoginButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginAccountButton));
    }
}
