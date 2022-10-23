package TestScenarios;

import FactoryRequest.RequestInfo;
import FactoryRequest.FactoryRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.util.Date;
import util.ApiConfiguration;

import static org.hamcrest.Matchers.equalTo;

public class TestProjectToken {

    Response response;
    JSONObject body= new JSONObject();
    RequestInfo requestInfo = new RequestInfo();


    @Test
    public void verifyUserProjectToken(){
        String email = "test_user_"+new Date().getTime()+"@mail.com";
        String password = "MyP@ssworD";
        body.put("Email",email);
        body.put("FullName","test_user");
        body.put("Password",password);
        requestInfo.setUrl(ApiConfiguration.CREATE_USER);
        requestInfo.setBody(body.toString());

        response= FactoryRequest.make("post").send(requestInfo);
        response.then().body("Email",equalTo(body.get("Email"))).statusCode(200);
        int idUser=response.then().extract().path("Id");

        body.clear();
        body.put("Email",email);
        body.put("Password",password);
        requestInfo.setUrl(String.format(ApiConfiguration.READ_TOKEN));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.getToken().send(requestInfo);
        response.then().body("UserEmail",equalTo(body.get("Email"))).statusCode(200);
        String token = response.then().extract().path("TokenString");

        body.clear();
        body.put("Content","Daniel's Project");
        requestInfo.setUrl(ApiConfiguration.CREATE_PROJECT);
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("post").send(requestInfo,token);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        int idProj=response.then().extract().path("Id");

        body.clear();
        body.put("Content","Daniel's Project Updated");
        requestInfo.setUrl(String.format(ApiConfiguration.UPDATE_PROJECT,idProj));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("put").send(requestInfo,token);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);

        requestInfo.setUrl(String.format(ApiConfiguration.DELETE_PROJECT,idProj));
        requestInfo.setBody(body.toString());
        response= FactoryRequest.make("delete").send(requestInfo,token);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
    }
}
