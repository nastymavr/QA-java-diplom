package praktikum.api.util;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Arrays;

public class TestData {

    private static final Faker faker = new Faker();

    // Генерация уникального email
    public static String uniqueEmail() {
        return faker.internet().emailAddress();
    }

    // Генерация надежного пароля
    public static String strongPassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    // Генерация случайного имени
    public static String randomName() {
        return faker.name().firstName() + "_" + faker.number().digits(4);
    }

    // Динамический список реальных ингредиентов
    public static List<String> realIngredients() {
        return Arrays.asList(
                "61c0c5a71d1f82001bdaaa71",  // Био-марсианский минеральный бургер
                "61c0c5a71d1f82001bdaaa76",  // Био-марсианский минеральный бургер
                "61c0c5a71d1f82001bdaaa70",  // Метеоритный бургер
                "61c0c5a71d1f82001bdaaa6d",  // Флюоресцентный бургер
                "61c0c5a71d1f82001bdaaa6f"   // Бессмертный флюоресцентный бургер
        );
    }
}
