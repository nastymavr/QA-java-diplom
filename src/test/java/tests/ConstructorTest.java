package tests;

import config.TestBase;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;

public class ConstructorTest {

    @Test
    @Description("Переход в раздел 'Булки' с браузером Chrome")
    public void testBunsSectionChrome() {
        TestBase testBase = new TestBase("chrome"); // Указываем Chrome
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToBuns();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Булки' не открылся", mainPage.isSectionVisible("Булки"));

        testBase.tearDown();
    }

    @Test
    @Description("Переход в раздел 'Булки' с браузером Yandex")
    public void testBunsSectionYandex() {
        TestBase testBase = new TestBase("yandex"); // Указываем Yandex
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToBuns();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Булки' не открылся", mainPage.isSectionVisible("Булки"));

        testBase.tearDown();
    }

    @Test
    @Description("Переход в раздел 'Соусы' с браузером Chrome")
    public void testSaucesSectionChrome() {
        TestBase testBase = new TestBase("chrome"); // Указываем Chrome
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToSauces();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Соусы' не открылся", mainPage.isSectionVisible("Соусы"));

        testBase.tearDown();
    }

    @Test
    @Description("Переход в раздел 'Соусы' с браузером Yandex")
    public void testSaucesSectionYandex() {
        TestBase testBase = new TestBase("yandex"); // Указываем Yandex
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToSauces();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Соусы' не открылся", mainPage.isSectionVisible("Соусы"));

        testBase.tearDown();
    }

    @Test
    @Description("Переход в раздел 'Начинки' с браузером Chrome")
    public void testFillingsSectionChrome() {
        TestBase testBase = new TestBase("chrome"); // Указываем Chrome
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToFillings();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Начинки' не открылся", mainPage.isSectionVisible("Начинки"));

        testBase.tearDown();
    }

    @Test
    @Description("Переход в раздел 'Начинки' с браузером Yandex")
    public void testFillingsSectionYandex() {
        TestBase testBase = new TestBase("yandex"); // Указываем Yandex
        testBase.setUp();

        MainPage mainPage = new MainPage(testBase.getDriver());
        mainPage.goToFillings();  // Используем метод из MainPage
        Assert.assertTrue("Раздел 'Начинки' не открылся", mainPage.isSectionVisible("Начинки"));

        testBase.tearDown();
    }
}
