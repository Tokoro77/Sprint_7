package Test;

import Order.OrderMethod;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Получение заказов")
@DisplayName("Тесты получения списка заказов")
public class OrderListTest {

    private final OrderMethod orderMethod = new OrderMethod();

    @Test
    @Story("Получение списка")
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    @Severity(SeverityLevel.NORMAL)
    public void getOrdersList() {
        ValidatableResponse response = orderMethod.getOrderList();

        response.assertThat().statusCode(200);

        // Проверяем, что в ответе есть список заказов
        Object orders = response.extract().path("orders");
        assertThat(orders, notNullValue());

        // Дополнительная проверка на структуру ответа
        response.body("orders", notNullValue());

        // Логируем количество заказов
        int ordersCount = response.extract().path("orders.size()");
        System.out.println("✅ Получен список заказов. Количество: " + ordersCount);
    }
}