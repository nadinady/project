package org.example;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import java.util.Map;


public class AppTest
{  String baseURL = "https://swapi.dev/api/";
    // Получить список всех сущностей
    @Test
    public void getAllMethods () {
        Response response;
        Map<String,String> result;
        response = getResponse(baseURL);
        checkResponse(response);
        JsonPath jsonPath= response.jsonPath();
        result = jsonPath.getMap("$");
        for (Map.Entry element: result.entrySet()) {
            System.out.println(element);
        }
    }
    // Получить список фильмов (films)

    @Test
    public void getFilmList () {
        Response response;
        response = getResponse(baseURL + "/films");
        checkResponse(response);
        JsonPath jsonPath= response.jsonPath();
        System.out.println("\n\n"+ jsonPath.getList("results.title") + "\n\n");
    }

    // Получить инфо по фильму 6
    @Test
    public void getFilmInfo () {
        Response response;
        Map<String,String> result;
        response = getResponse(baseURL + "/films/6");
        checkResponse(response);
        JsonPath jsonPath= response.jsonPath();
        result = jsonPath.getMap("$");
        for (Map.Entry element: result.entrySet()) {
            System.out.println(element);
        }
    }

   //Получить список планет (planets)
    @Test
    public void getPlanetList () {
        Response response;
        response = getResponse(baseURL + "/planets");
        checkResponse(response);
        JsonPath jsonPath= response.jsonPath();
        System.out.println("\n\n"+ jsonPath.getList("results.name") + "\n\n");
    }

    //Получить инфо по планете 3 из фильма 1
    @Test
    public void getPlanetInfo() {
        Response response;
        Map<String,String> result;
        response = getResponse(baseURL + "/planets/3");
        checkResponse(response);
        JsonPath jsonPath= response.jsonPath();
        result = jsonPath.getMap("");
        for (Map.Entry element: result.entrySet()) {
            System.out.println(element);
       }
    }
    public Response getResponse(String url) {
        return  RestAssured
                .given().log().all()
                .get(url);
    }
    public void checkResponse (Response response) {
        response
                .then()
                //.log().body()
                .statusCode(200);
    }
}
