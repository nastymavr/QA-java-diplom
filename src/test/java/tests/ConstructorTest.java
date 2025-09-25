package tests;

import config.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;

public class ConstructorTest extends TestBase {

    @Test
    @Description("Переход в раздел 'Булки' с браузером, заданным в config.properties")
    @Step("Переход в раздел 'Булки'")
    public void testBunsSection() {
        // Убираем жестко прописанные параметры браузера, теперь он берется из конфигурации
        MainPage mainPage = new MainPage(driver);
        mainPage.goToBuns();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Булки' не открылся", mainPage.isSectionVisible("Булки"));
    }

    @Test
    @Description("Переход в раздел 'Соусы' с браузером, заданным в config.properties")
    @Step("Переход в раздел 'Соусы'")
    public void testSaucesSection() {
        // Тот же браузер, что и в конфигурации
        MainPage mainPage = new MainPage(driver);
        mainPage.goToSauces();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Соусы' не открылся", mainPage.isSectionVisible("Соусы"));
    }

    @Test
    @Description("Переход в раздел 'Начинки' с браузером, заданным в config.properties")
    @Step("Переход в раздел 'Начинки'")
    public void testFillingsSection() {
        // Тот же браузер, что и в конфигурации
        MainPage mainPage = new MainPage(driver);
        mainPage.goToFillings();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Начинки' не открылся", mainPage.isSectionVisible("Начинки"));
    }
}
