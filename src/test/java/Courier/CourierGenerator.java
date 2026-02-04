package Courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    // Генерация случайного курьера со всеми полями
    public static Courier random() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(8) // Добавляем имя
        );
    }

    // Курьер без логина
    public static Courier withoutLogin() {
        return new Courier(
                null,
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    // Курьер без пароля
    public static Courier withoutPassword() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                null,
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    // Курьер без имени
    public static Courier withoutFirstName() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                null
        );
    }

    // Курьер с существующими данными (для теста дублирования)
    public static Courier existing() {
        return new Courier("existing_user", "password123", "Existing User");
    }

    // Генерация уникального логина с временной меткой
    public static String generateUniqueLogin() {
        return "courier_" + System.currentTimeMillis() + "_" + RandomStringUtils.randomNumeric(4);
    }

    // Генерация уникального пароля
    public static String generateUniquePassword() {
        return "pass_" + System.currentTimeMillis() + "_" + RandomStringUtils.randomNumeric(4);
    }
}