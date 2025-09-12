package praktikum.api;

import io.qameta.allure.Step;
import org.junit.Test;
import praktikum.api.client.ApiClient;
import praktikum.api.config.TestBase;
import praktikum.api.model.LoginRequest;
import praktikum.api.model.OrderRequest;
import praktikum.api.model.RegisterRequest;
import praktikum.api.util.TestData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class OrderTests extends TestBase {

    private final ApiClient api = new ApiClient();

    @Step("Регистрация и получение токена для пользователя с email: {0}")
    private String registerAndGetToken(String email, String pass) {
        api.registerUser(new RegisterRequest(email, pass, TestData.randomName()))
                .then().statusCode(200);

        String token = api.loginUser(new LoginRequest(email, pass))
                .then().statusCode(200)
                .extract().jsonPath().getString("accessToken");

        if (token == null || token.isBlank()) throw new IllegalStateException("accessToken пустой");
        return token;
    }

    @Test
    @Step("Создание заказа с авторизацией. Ожидаемый статус: 200")
    public void createOrder_withAuth_shouldReturn200() {
        String email = TestData.uniqueEmail();
        String pass = TestData.strongPassword();
        String token = registerAndGetToken(email, pass);
        OrderRequest order = new OrderRequest(TestData.realIngredients().subList(0, 2));

        api.createOrder(order, token)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @Step("Создание заказа без авторизации. Ожидаемый статус: 401")
    public void createOrder_withoutAuth_shouldReturn401() {
        OrderRequest order = new OrderRequest(TestData.realIngredients().subList(0, 1));

        api.createOrder(order, null)
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", containsString("You should be authorised"));
    }

    @Test
    @Step("Создание заказа без ингредиентов. Ожидаемый статус: 400")
    public void createOrder_withoutIngredients_shouldReturn400() {
        String email = TestData.uniqueEmail();
        String pass = TestData.strongPassword();
        String token = registerAndGetToken(email, pass);
        OrderRequest empty = new OrderRequest(new ArrayList<>());

        api.createOrder(empty, token)
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Ingredient ids must be provided"));
    }

    @Test
    @Step("Создание заказа с неправильным хешем ингредиента. Ожидаемый статус: 500")
    public void createOrder_withInvalidIngredientHash_shouldReturnServerError() {
        String email = TestData.uniqueEmail();
        String pass = TestData.strongPassword();
        String token = registerAndGetToken(email, pass);

        List<String> invalid = new ArrayList<>(TestData.realIngredients());
        invalid.set(0, invalid.get(0) + "ZZ");

        api.createOrder(new OrderRequest(invalid), token)
                .then()
                .statusCode(500); // проверяем только код
    }
}
