package praktikum.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private RequestSpecification spec() {
        return given().spec(io.restassured.RestAssured.requestSpecification);
    }

    // --- AUTH / USER ---
    @Step("Регистрация пользователя с телом {body}")
    public Response registerUser(Object body) {
        return spec()
                .body(body)
                .when()
                .post("/auth/register");
    }

    @Step("Авторизация пользователя с телом {body}")
    public Response loginUser(Object body) {
        return spec()
                .body(body)
                .when()
                .post("/auth/login");
    }

    // --- INGREDIENTS ---
    @Step("Получение всех ингредиентов")
    public Response getIngredients() {
        return spec()
                .when()
                .get("/ingredients");
    }

    @Step("Получение списка всех ID ингредиентов")
    public List<String> getIngredientIds() {
        Response r = getIngredients().then().extract().response();
        return r.jsonPath().getList("data._id");
    }

    // --- ORDERS ---
    @Step("Создание заказа с телом {body} и токеном {authorizationHeaderValue}")
    public Response createOrder(Object body, String authorizationHeaderValue) {
        Response response = spec().body(body)
                .header("Authorization", authorizationHeaderValue)
                .when()
                .post("/orders");

        // Проверка, что код статуса либо 200, либо 202
        int statusCode = response.getStatusCode();
        if (statusCode != 200 && statusCode != 202) {
            throw new AssertionError("Expected status code 200 or 202 but got " + statusCode);
        }

        return response;
    }

    @Step("Получение заказов пользователя с токеном {authorizationHeaderValue}")
    public Response getUserOrders(String authorizationHeaderValue) {
        RequestSpecification req = spec();
        if (authorizationHeaderValue != null && !authorizationHeaderValue.isBlank()) {
            req.header("Authorization", authorizationHeaderValue);
        }
        return req.when().get("/orders");
    }

    // --- DELETE USER ---
    @Step("Удаление пользователя с токеном {authorizationHeaderValue}")
    public Response deleteUser(String authorizationHeaderValue) {
        return spec()
                .header("Authorization", authorizationHeaderValue)
                .when()
                .delete("/auth/user");
    }
}
