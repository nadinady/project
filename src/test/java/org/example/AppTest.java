package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import java.util.List;
import java.util.Map;


public class AppTest
{
    // Получить список всех сущностей
    @Test
    public void getAllMethods ()
    { Response response;
        Map<String,String> result;
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
        result = jsonPath.getMap("$");
        for (String keys: result.keySet()){
            System.out.println(keys);
        }
    }
    // Получить список фильмов (films) и информацию по одному фильму
    @Test
    public void getFilmInfo ()
    { Response response;
        Map<String,String> result;
        response = RestAssured
                .given().log().all()
                .get("https://swapi.dev/api/films");
        response
                .then()
                //.log().body()
                .statusCode(200);
        JsonPath jsonPath= response.jsonPath();
        System.out.println("\n\n"+ jsonPath.getList("results.title") + "\n\n");
        result = jsonPath.getMap("results[3]");
        for (Map.Entry element: result.entrySet()) {
            System.out.println(element);
        }
    }

   //Получить список планет (planets) и информацию по планете выбранного вам фильма
    @Test
    public void getPlanetInfo () {
        Response responseFilms;
        Response responsePlanet;
        List<String> planet;
        Map<String,String> result;
        responseFilms = RestAssured
                .given().log().all()
                .get("https://swapi.dev/api/films");
        responseFilms
                .then()
                //.log().body()
                .statusCode(200);
        JsonPath jsonPath= responseFilms.jsonPath();
        planet = jsonPath.getList("results[3].planets");
        responsePlanet = RestAssured
                .given().log().all()
                .get(planet.get(0));
        responsePlanet
                .then()
                //.log().body()
                .statusCode(200);
        JsonPath jsonPathPlanet= responsePlanet.jsonPath();
        System.out.println(planet + "\n");
        result = jsonPathPlanet.getMap("");
        for (Map.Entry element: result.entrySet()) {
            System.out.println(element);
        }
    }

}
