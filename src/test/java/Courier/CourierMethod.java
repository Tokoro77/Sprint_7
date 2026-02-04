package Courier;

import Base.BaseMethod;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierMethod extends BaseMethod {
    protected static final String COURIER_URI = API_PREFIX + "courier/";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then().log().all();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(COURIER_URI + "login")
                .then().log().all();
    }

    @Step("Удаление курьера с ID: {courierId}")
    public ValidatableResponse delete(int courierId) {
        return getSpec()
                .when()
                .delete(COURIER_URI + courierId)
                .then().log().all();
    }

    @Step("Получение ID курьера из ответа")
    public int getCourierId(ValidatableResponse response) {
        return response.extract()
                .path("id");
    }
}