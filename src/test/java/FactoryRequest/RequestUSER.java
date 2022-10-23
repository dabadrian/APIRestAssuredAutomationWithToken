package FactoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestUSER implements IRequest{
    @Override
    public Response send(RequestInfo info) {
        Response response =given()
                .log().all()
                .when()
                .get(info.getUrl());
        response.then().log().all();
        return response;
    }

    @Override
    public Response send(RequestInfo info, String token) {
        Response response =given()
                .header("Token", token)
                .log().all()
                .when()
                .get(info.getUrl());
        response.then().log().all();
        return response;
    }
}
