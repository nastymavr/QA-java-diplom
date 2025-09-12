package praktikum.api;

import io.qameta.allure.Step;
import org.junit.Test;
import praktikum.api.client.ApiClient;
import praktikum.api.config.TestBase;
import praktikum.api.model.LoginRequest;
import praktikum.api.model.RegisterRequest;
import praktikum.api.util.TestData;

import static org.hamcrest.Matchers.*;

public class UserTests extends TestBase {

    private final ApiClient api = new ApiClient();

    @Test
    @Step("Регистрация нового пользователя с email: {0}")
    public void register_successful() {
        var reg = new RegisterRequest(TestData.uniqueEmail(), TestData.strongPassword(), TestData.randomName());
        api.registerUser(reg)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(reg.email));
    }

    @Test
    @Step("Попытка регистрации с уже существующим пользователем. Ожидаемый статус: 403")
    public void register_userAlreadyExists() {
        String email = TestData.uniqueEmail();
        String pass = TestData.strongPassword();
        String name = TestData.randomName();

        api.registerUser(new RegisterRequest(email, pass, name)).then().statusCode(200);

        api.registerUser(new RegisterRequest(email, pass, name))
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("User already exists"));
    }

    @Test
    @Step("Попытка регистрации с отсутствующим полем. Ожидаемый статус: 403")
    public void register_missingField_shouldReturn403() {
        String email = TestData.uniqueEmail();
        api.registerUser(new RegisterRequest(email, "password", ""))
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("Email, password and name are required fields"));
    }

    @Test
    @Step("Успешный логин и логин с неправильным паролем. Ожидаемый статус: 401")
    public void login_success_and_wrongPassword_failure() {
        String email = TestData.uniqueEmail();
        String password = TestData.strongPassword();
        api.registerUser(new RegisterRequest(email, password, TestData.randomName())).then().statusCode(200);

        api.loginUser(new LoginRequest(email, password))
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());

        api.loginUser(new LoginRequest(email, "wrongPass"))
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
