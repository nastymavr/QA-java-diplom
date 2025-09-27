package praktikum.api;

import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import praktikum.api.config.TestBase;
import praktikum.api.model.RegisterRequest;
import praktikum.api.steps.UserSteps;
import praktikum.api.util.TestData;

import static org.hamcrest.Matchers.*;

public class UserRegistrationTests extends TestBase {

    private final UserSteps userSteps = new UserSteps(api);

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Создание пользователя через API с валидными данными. Проверка success=true и корректного email.")
    public void register_successful() {
        var reg = new RegisterRequest(TestData.uniqueEmail(), TestData.strongPassword(), TestData.randomName());
        userSteps.registerUser(reg)
                .then().statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(reg.email));
    }

    @Test
    @DisplayName("Попытка регистрации существующего пользователя")
    @Description("Создаем пользователя и пытаемся зарегистрировать того же пользователя повторно. Ожидаем 403 и соответствующее сообщение.")
    public void register_userAlreadyExists() {
        String email = TestData.uniqueEmail();
        String pass = TestData.strongPassword();
        String name = TestData.randomName();

        userSteps.registerUser(new RegisterRequest(email, pass, name)).then().statusCode(200);
        userSteps.registerUser(new RegisterRequest(email, pass, name))
                .then().statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("User already exists"));
    }

    @Test
    @DisplayName("Регистрация без email")
    @Description("Попытка регистрации пользователя без email. Ожидаем 403 и сообщение об обязательных полях.")
    public void register_missingEmail_shouldReturn403() {
        userSteps.registerUser(new RegisterRequest("", TestData.strongPassword(), TestData.randomName()))
                .then().statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Регистрация без пароля")
    @Description("Попытка регистрации пользователя без пароля. Проверка ошибки 403 и сообщения об обязательных полях.")
    public void register_missingPassword_shouldReturn403() {
        userSteps.registerUser(new RegisterRequest(TestData.uniqueEmail(), "", TestData.randomName()))
                .then().statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Регистрация без имени")
    @Description("Попытка регистрации пользователя без имени. Проверка ошибки 403 и сообщения об обязательных полях.")
    public void register_missingName_shouldReturn403() {
        userSteps.registerUser(new RegisterRequest(TestData.uniqueEmail(), TestData.strongPassword(), ""))
                .then().statusCode(403)
                .body("success", equalTo(false))
                .body("message", containsString("Email, password and name are required fields"));
    }
}
