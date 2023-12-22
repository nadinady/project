package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;



public class AppTest
{
    // Получить список всех сущностей
    @Test
    public void getAllMethods ()
    { Response response;
       response = RestAssured
                .given().log().all()
                .get("https://swapi.dev/api/");
                response
                       .then()
                        //.log().body()
                        .statusCode(200);
        //String responseBody= response.getBody().asString();
        //System.out.println(responseBody);
        JsonPath jsonPath= response.jsonPath();
        System.out.println(jsonPath.getString("$"));
    }
    // Получить список фильмов (films) и информацию по одному фильму2
    @Test
    public void getFilmInfo ()
    { Response response;
        response = RestAssured
                .given().log().all()
                .get("https://swapi.dev/api/films");
        response.then().statusCode(200);

    }


}
