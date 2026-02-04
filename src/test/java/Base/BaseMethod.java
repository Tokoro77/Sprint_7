package Base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseMethod {
    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    protected static final String API_PREFIX = "/api/v1/";

    // Метод для получения готового запроса
    protected RequestSpecification getSpec() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured());
    }
}