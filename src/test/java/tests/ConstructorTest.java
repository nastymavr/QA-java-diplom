package tests;

import config.TestBase;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;

public class ConstructorTest extends TestBase {

    @Test
    @Description("Переход в раздел 'Булки'")
    public void testBunsSection() {
        MainPage mainPage = new MainPage(driver);  // Используем драйвер из TestBase
        mainPage.goToBuns();
        Assert.assertTrue("Раздел 'Булки' не открылся", mainPage.isSectionVisible("Булки"));
    }

    @Test
    @Description("Переход в раздел 'Соусы'")
    public void testSaucesSection() {
        MainPage mainPage = new MainPage(driver);  // Используем драйвер из TestBase
        mainPage.goToSauces();
        Assert.assertTrue("Раздел 'Соусы' не открылся", mainPage.isSectionVisible("Соусы"));
    }

    @Test
    @Description("Переход в раздел 'Начинки'")
    public void testFillingsSection() {
        MainPage mainPage = new MainPage(driver);  // Используем драйвер из TestBase
        mainPage.goToFillings();
        Assert.assertTrue("Раздел 'Начинки' не открылся", mainPage.isSectionVisible("Начинки"));
    }
}
