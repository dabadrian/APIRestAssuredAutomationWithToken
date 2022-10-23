package FactoryRequest;

import io.restassured.response.Response;

public interface IRequest {
    Response send(RequestInfo info);
    Response send(RequestInfo info, String token);
}
