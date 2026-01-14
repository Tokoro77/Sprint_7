package Order;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class OrderGenerator {

    // Генерация уникального заказа с разными цветами
    public static Order withColors(String[] colors) {
        return new Order(
                "Иван_" + RandomStringUtils.randomNumeric(4),
                "Иванов_" + RandomStringUtils.randomNumeric(4),
                "ул. Ленина, д. " + (10 + (int)(Math.random() * 90)),
                String.valueOf((int)(Math.random() * 20) + 1),
                "+7999" + RandomStringUtils.randomNumeric(7),
                1 + (int)(Math.random() * 7),
                LocalDate.now().plusDays(3 + (int)(Math.random() * 7))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "Комментарий_" + RandomStringUtils.randomAlphanumeric(8),
                colors != null ? Arrays.asList(colors) : null
        );
    }

    // Заказ с цветом BLACK
    public static Order withBlackColor() {
        return withColors(new String[]{"BLACK"});
    }

    // Заказ с цветом GREY
    public static Order withGreyColor() {
        return withColors(new String[]{"GREY"});
    }

    // Заказ с обоими цветами
    public static Order withBothColors() {
        return withColors(new String[]{"BLACK", "GREY"});
    }

    // Заказ без указания цвета
    public static Order withoutColor() {
        return withColors(null);
    }
}