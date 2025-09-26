package utils;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import data.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiHelper {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/auth/";
    private static final Logger logger = LoggerFactory.getLogger(ApiHelper.class);  // Логгер для диагностических сообщений

    // Метод для регистрации нового пользователя с использованием сериализации
    public static Response registerNewUser() {
        // Используем уже готовые методы из TestData для генерации данных
        String email = TestData.getRandomEmail();
        String password = TestData.getRandomPassword();
        String name = TestData.getRandomName();

        // Создаём объект для запроса
        User user = new User(email, password, name);

        // Сериализация объекта в JSON с помощью Gson
        String requestBody = new Gson().toJson(user);

        // Логируем данные запроса
        logger.info("Request Body: " + requestBody);

        // Выполняем запрос на регистрацию пользователя
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_URL + "register");

        // Логируем ответ для диагностики
        logger.info("Response Body: " + response.getBody().asString());
        logger.info("Status Code: " + response.getStatusCode());

        return response;
    }

    // Метод для удаления пользователя
    public static Response deleteUser(String accessToken) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .delete(BASE_URL + "user");
    }

    // Вспомогательный класс для сериализации данных пользователя
    public static class User {
        private String email;
        private String password;
        private String name;

        // Конструктор
        public User(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

        // Получаем email
        public String getEmail() {
            return email;
        }

        // Получаем пароль
        public String getPassword() {
            return password;
        }

        // Получаем имя
        public String getName() {
            return name;
        }
    }
}
