package FactoryRequest;

import io.restassured.response.Response;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.GetProperties;
import org.json.simple.JSONObject;



import static io.restassured.RestAssured.given;

public class RequestTOKEN implements IRequestToken{
    @Override
    public Response send(RequestInfo info) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(info.getBody());

            String userName = jsonObject.get("Email").toString();
            String userPassword = jsonObject.get("Password").toString();

            Response response =given()
                    .auth()
                    .preemptive()
                    .basic(userName,userPassword)
                    .log().all()
                    .when()
                    .get(info.getUrl());
            response.then().log().all();
            return response;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



    }
}
