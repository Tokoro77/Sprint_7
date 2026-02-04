package Test;

import Courier.Courier;
import Courier.CourierGenerator;
import Courier.CourierMethod;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Авторизация курьеров")
@DisplayName("Тесты авторизации курьеров")
public class CourierLoginTests {

    private CourierMethod courierMethod;
    private Courier courier;
    private Integer courierId;

    @Before
    @DisplayName("Подготовка тестовых данных")
    @Description("Создание курьера перед выполнением тестов логина")
    @Severity(SeverityLevel.MINOR)
    public void setUp() {
        courierMethod = new CourierMethod();

        // Генерируем уникальные данные
        String login = CourierGenerator.generateUniqueLogin();
        String password = CourierGenerator.generateUniquePassword();
        String firstName = "Test User";

        courier = new Courier(login, password, firstName);

        // Создаем курьера перед тестами логина
        courierMethod.create(courier).assertThat().statusCode(201);

        // Получаем ID созданного курьера
        ValidatableResponse loginResponse = courierMethod.login(courier);
        loginResponse.assertThat().statusCode(200);
        courierId = loginResponse.extract().path("id");

        System.out.println("✓ Подготовлен курьер с ID: " + courierId);
    }

    @After
    @DisplayName("Очистка тестовых данных")
    @Description("Удаление созданного курьера после выполнения тестов")
    @Severity(SeverityLevel.MINOR)
    public void tearDown() {
        if (courierId != null) {
            courierMethod.delete(courierId).assertThat().statusCode(200);
            System.out.println("✓ Курьер с ID " + courierId + " удален");
        }
    }

    @Test
    @Story("Успешная авторизация")
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void courierCanLogin() {
        ValidatableResponse response = courierMethod.login(courier);
        response.assertThat().statusCode(200);

        Integer id = response.extract().path("id");
        assertThat(id, notNullValue());
        assertThat(id, equalTo(courierId));

        System.out.println("✓ Успешная авторизация с ID: " + id);
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Авторизация с неправильным паролем")
    @Description("Проверка, что нельзя авторизоваться с неправильным паролем")
    @Severity(SeverityLevel.NORMAL)
    public void cannotLoginWithWrongPassword() {
        Courier wrongPasswordCourier = new Courier(courier.getLogin(), "wrong_password", courier.getFirstName());
        courierMethod.login(wrongPasswordCourier)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        System.out.println("✓ Проверка неправильного пароля выполнена");
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Авторизация с неправильным логином")
    @Description("Проверка, что нельзя авторизоваться с неправильным логином")
    @Severity(SeverityLevel.NORMAL)
    public void cannotLoginWithWrongLogin() {
        Courier wrongLoginCourier = new Courier("wrong_login", courier.getPassword(), courier.getFirstName());
        courierMethod.login(wrongLoginCourier)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        System.out.println("✓ Проверка неправильного логина выполнена");
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Авторизация без логина")
    @Description("Проверка, что нельзя авторизоваться без логина")
    @Severity(SeverityLevel.NORMAL)
    public void cannotLoginWithoutLogin() {
        Courier noLoginCourier = new Courier(null, courier.getPassword(), courier.getFirstName());
        courierMethod.login(noLoginCourier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        System.out.println("✓ Проверка авторизации без логина выполнена");
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Авторизация без пароля")
    @Description("Проверка, что нельзя авторизоваться без пароля")
    @Severity(SeverityLevel.NORMAL)
    public void cannotLoginWithoutPassword() {
        Courier noPasswordCourier = new Courier(courier.getLogin(), null, courier.getFirstName());
        courierMethod.login(noPasswordCourier)
                .assertThat()
                .statusCode(400);
                //.body("message", equalTo("Недостаточно данных для входа"));

        System.out.println("✓ Проверка авторизации без пароля выполнена");
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка, что нельзя авторизоваться под несуществующим пользователем")
    @Severity(SeverityLevel.NORMAL)
    public void cannotLoginWithNonExistentCourier() {
        Courier nonExistent = CourierGenerator.random();
        courierMethod.login(nonExistent)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        System.out.println("✓ Проверка несуществующего курьера выполнена");
    }
}