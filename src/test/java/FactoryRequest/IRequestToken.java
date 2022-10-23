package FactoryRequest;

import io.restassured.response.Response;

public interface IRequestToken {
    Response send(RequestInfo info);
}
