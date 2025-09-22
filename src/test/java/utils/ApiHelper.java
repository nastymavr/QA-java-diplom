package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;

public class ApiHelper {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/auth/";

    // Метод для генерации случайных данных для регистрации
    public static String generateRandomEmail() {
        return "test" + UUID.randomUUID().toString() + "@yandex.ru"; // Генерация уникального email
    }

    public static String generateRandomPassword() {
        return "password" + (int)(Math.random() * 10000); // Генерация случайного пароля
    }

    public static String generateRandomName() {
        return "Username" + UUID.randomUUID().toString().substring(0, 5); // Генерация уникального имени
    }

    // Метод для регистрации нового пользователя
    public static Response registerNewUser() {
        String email = generateRandomEmail();
        String password = generateRandomPassword();
        String name = generateRandomName();

        String requestBody = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"name\": \"" + name + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_URL + "register");

        // Логируем ответ для диагностики
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());

        return response;
    }

    // Метод для удаления пользователя
    public static Response deleteUser(String accessToken) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .delete(BASE_URL + "user");
    }
}
