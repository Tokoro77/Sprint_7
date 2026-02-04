package Order;

import Base.BaseMethod;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderMethod extends BaseMethod {
    protected static final String ORDER_URI = API_PREFIX + "orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER_URI)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return getSpec()
                .when()
                .get(ORDER_URI)
                .then().log().all();
    }

    @Step("Отмена заказа по треку: {track}")
    public ValidatableResponse cancelOrder(int track) {
        String cancelBody = String.format("{\"track\": %d}", track);
        return getSpec()
                .body(cancelBody)
                .when()
                .put(ORDER_URI + "/cancel")
                .then().log().all();
    }

    @Step("Получение трека из ответа")
    public int getTrack(ValidatableResponse response) {
        return response.extract()
                .path("track");
    }
}