package Test;

import Courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CourierCreateTests {

    private CourierMethod client = new CourierMethod();
    private Courier courier;
    private Integer courierId;

    @After
    @DisplayName("Удаление тестового курьера")
    @Description("Удаляем созданного курьера после каждого теста")
    public void deleteCourier() {
        if (courierId != null) {
            try {
                client.delete(courierId).assertThat().statusCode(200);
                System.out.println("✓ Курьер с ID " + courierId + " удален");
            } catch (Exception e) {
                System.out.println("⚠️ Не удалось удалить курьера: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка, что курьер может быть создан с валидными данными")
    public void createdCourierTest() {
        // Генерируем уникальные данные напрямую
        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "password_" + System.currentTimeMillis(),
                "Test User"
        );

        ValidatableResponse createResponse = client.create(courier);
        createResponse.assertThat()
                .statusCode(201)
                .body("ok", is(true));

        // Получаем ID для удаления
        ValidatableResponse loginResponse = client.login(courier);
        courierId = loginResponse.extract().path("id");

        System.out.println("✓ Создан курьер с ID: " + courierId);
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void doubleCreatedTest() {
        // Создаем уникального курьера
        String login = "test_login_" + System.currentTimeMillis();
        String password = "test_password_" + System.currentTimeMillis();
        courier = new Courier(login, password, "Test User");

        // Сначала создаем курьера
        client.create(courier).assertThat().statusCode(201);

        // Получаем ID первого курьера для очистки
        ValidatableResponse loginResponse = client.login(courier);
        courierId = loginResponse.extract().path("id");

        System.out.println("✓ Первый курьер создан с ID: " + courierId);

        // Пытаемся создать с теми же данными
        client.create(courier)
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        System.out.println("✓ Проверка дубликата выполнена");
    }
}