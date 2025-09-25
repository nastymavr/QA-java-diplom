package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import data.TestData;
import data.UserRegistrationRequest;

public class ApiHelper {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/auth/";

    // Метод для регистрации нового пользователя
    public static Response registerNewUser() {
        // Генерация данных для регистрации
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();
        String name = TestData.getRandomName();

        // Создаём объект для сериализации
        UserRegistrationRequest user = new UserRegistrationRequest(email, password, name);

        // Создаём ObjectMapper для сериализации объекта в JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Сериализуем объект в JSON строку
            String requestBody = objectMapper.writeValueAsString(user);

            // Отправляем запрос
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .post(BASE_URL + "register");

            // Логируем ответ для диагностики
            System.out.println("Response Body: " + response.getBody().asString());
            System.out.println("Status Code: " + response.getStatusCode());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при сериализации объекта в JSON", e);
        }
    }

    // Метод для удаления пользователя
    public static Response deleteUser(String accessToken) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .delete(BASE_URL + "user");
    }
}
