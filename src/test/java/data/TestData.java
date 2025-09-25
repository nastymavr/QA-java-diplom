package data;

import com.github.javafaker.Faker;

public class TestData {

    private static Faker faker = new Faker();  // Создаем объект Faker

    public static String getRandomEmail() {
        return faker.internet().emailAddress();  // Генерируем случайный email
    }

    public static String getRandomName() {
        return faker.name().firstName();  // Генерируем случайное имя
    }

    public static String getRandomLastName() {
        return faker.name().lastName();  // Генерируем случайную фамилию
    }

    public static String getRandomPassword() {
        return "Pwd" + faker.internet().password(8, 12);  // Генерация пароля с длиной от 8 до 12 символов
    }

    public static String shortPassword = "123";
    public static String existingEmail = "existing_user@mail.com";
}
