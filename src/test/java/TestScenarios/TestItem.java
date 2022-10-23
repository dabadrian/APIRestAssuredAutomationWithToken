package TestScenarios;

import FactoryRequest.FactoryRequest;
import FactoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import util.ApiConfiguration;

import static org.hamcrest.Matchers.equalTo;

public class TestItem {

    Response response;
    JSONObject body= new JSONObject();
    RequestInfo requestInfo = new RequestInfo();

    @Test
    public void verifyCRUDItem(){

        body.put("Content","item2");
        requestInfo.setUrl(ApiConfiguration.CREATE_ITEM);
        requestInfo.setBody(body.toString());

        response= FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        int idItem=response.then().extract().path("Id");

        body.put("Content","item2Updated2022");
        requestInfo.setUrl(String.format(ApiConfiguration.UPDATE_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("put").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);

        requestInfo.setUrl(String.format(ApiConfiguration.READ_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("get").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);

        requestInfo.setUrl(String.format(ApiConfiguration.DELETE_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("delete").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);

    }

    @Test
    public void verifyProjectItem(){
        body.put("Content","ProjectDemo");
        requestInfo.setUrl(ApiConfiguration.CREATE_PROJECT);
        requestInfo.setBody(body.toString());

        response= FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        int idProj=response.then().extract().path("Id");


        body.put("Content","ItemDemo");
        requestInfo.setUrl(ApiConfiguration.CREATE_ITEM);
        requestInfo.setBody(body.toString());

        response= FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        int idItem=response.then().extract().path("Id");

        body.put("Content","itemDemoUpdated");
        body.put("ProjectId",idProj);
        requestInfo.setUrl(String.format(ApiConfiguration.UPDATE_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("put").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);

    }

}
