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
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@Feature("Обязательные поля курьера")
@DisplayName("Тесты валидации обязательных полей курьера")
public class CourierRequiredFieldsTests {

    private CourierMethod client = new CourierMethod();
    private CourierGenerator generator = new CourierGenerator();

    @Test
    @Story("Валидация создания")
    @DisplayName("Создание курьера без логина")
    @Description("Проверка, что нельзя создать курьера без логина")
    @Severity(SeverityLevel.NORMAL)
    public void createCourierWithoutLogin() {
        Courier courier = generator.withoutLogin();
        client.create(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println("✓ Проверка создания без логина выполнена");
    }

    @Test
    @Story("Валидация создания")
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что нельзя создать курьера без пароля")
    @Severity(SeverityLevel.NORMAL)
    public void createCourierWithoutPassword() {
        Courier courier = generator.withoutPassword();
        client.create(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println("✓ Проверка создания без пароля выполнена");
    }

    @Test
    @Story("Валидация создания")
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка, что нельзя создать курьера без логина и пароля")
    @Severity(SeverityLevel.NORMAL)
    public void createCourierWithoutLoginAndPassword() {
        Courier courier = new Courier(null, null, "Test Name");
        client.create(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println("✓ Проверка создания без логина и пароля выполнена");
    }
}