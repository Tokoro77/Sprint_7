package Test;

import Order.Order;
import Order.OrderGenerator;
import Order.OrderMethod;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Создание заказов")
@DisplayName("Параметризованные тесты создания заказов")
@RunWith(Parameterized.class)
public class OrderCreateTests {

    private final Order order;
    private final OrderMethod orderMethod = new OrderMethod();
    private Integer createdOrderTrack;

    public OrderCreateTests(Order order, String testName) {
        this.order = order;
    }

    @Parameterized.Parameters(name = "Тест создания заказа: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {OrderGenerator.withBlackColor(), "С BLACK цветом"},
                {OrderGenerator.withGreyColor(), "С GREY цветом"},
                {OrderGenerator.withBothColors(), "С обоими цветами"},
                {OrderGenerator.withoutColor(), "Без указания цвета"}
        });
    }

    @After
    @DisplayName("Отмена созданного заказа")
    @Description("Отменяем созданный заказ после теста")
    @Severity(SeverityLevel.MINOR)
    public void cancelOrder() {
        if (createdOrderTrack != null) {
            try {
                ValidatableResponse cancelResponse = orderMethod.cancelOrder(createdOrderTrack);
                int statusCode = cancelResponse.extract().statusCode();

                // API может возвращать 200 (успешно) или 400 (уже отменен/не найден)
                if (statusCode == 200) {
                    System.out.println("✓ Заказ с треком " + createdOrderTrack + " успешно отменен");
                } else if (statusCode == 400) {
                    // Заказ уже отменен или не найден - это нормально
                    System.out.println("✓ Заказ " + createdOrderTrack + " уже отменен или не найден (код 400)");
                } else {
                    System.out.println("⚠️ Неожиданный код ответа при отмене: " + statusCode);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Не удалось отменить заказ " + createdOrderTrack + ": " + e.getMessage());
            }
        }
    }

    @Test
    @Story("Создание с разными цветами")
    @DisplayName("Создание заказа с разными цветами")
    @Description("Параметризованный тест создания заказов с разными цветами")
    @Severity(SeverityLevel.CRITICAL)
    public void createOrderWithDifferentColors() {
        ValidatableResponse response = orderMethod.create(order);

        response.assertThat().statusCode(201);

        // Проверяем что возвращается трек
        Integer track = response.extract().path("track");
        assertThat(track, notNullValue());

        // Сохраняем трек для отмены
        createdOrderTrack = track;

        System.out.println("✓ Заказ создан. Track: " + track);
    }
}