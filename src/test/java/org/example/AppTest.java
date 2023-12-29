package org.example;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.*;


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
        boolean flag = true;
        List<String> result = new ArrayList<>() {
        };
        Response response;
        int page = 1;
        do {
            response = getResponse(baseURL + "/planets/?page=" + page);
            checkResponse(response);
            JsonPath jsonPath = response.jsonPath();
            result.addAll(jsonPath.getList("results.name"));
            if(jsonPath.getString("next") == null) {
                flag = false;
            }
            page++;

        }
        while (flag);
        for (String element : result) {
            System.out.println(element);
        }
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

    //Получить список рас планеты 14 из фильма 6 (выбираем планету 14, дергаем из нее людей, проверяем принадлежность к фильму, записываем расу)
    @Test
    public void getSpeciesList() {
        Response response;
        Response peopleResponce;
        List<String> result = new ArrayList<>();
        List<String> peoples;
        String species;
        response = getResponse(baseURL + "planets/14/");
        checkResponse(response);
        JsonPath jsonPath = response.jsonPath();
        peoples = jsonPath.getList("residents");
        for (String element : peoples) {
            peopleResponce = getResponse(element);
            checkResponse(peopleResponce);
            JsonPath jsonPathPeople = peopleResponce.jsonPath();
            if (jsonPathPeople.getList("films").contains(baseURL + "films/6/")) {
                species = jsonPathPeople.getString("species");
                species = species.substring(1, species.length() - 1);
                if (species.length() != 0) {
                if (!result.contains(species)) {
                        result.add(species);
                    }
                }
            }
        }
        for (String element : result) {
            System.out.println(element);
        }
    }
//Когда получить список пилотов корабля 10 из фильма 3
        @Test
        public void getPilotsList() {
            Response response;
            Response pilotsResponce;
            List<String> pilots;
            List<String> results = new ArrayList<>();
            response = getResponse(baseURL + "starships/10/");
            checkResponse(response);
            JsonPath jsonPath = response.jsonPath();
            pilots = jsonPath.getList("pilots");
            for (String element : pilots) {
                pilotsResponce = getResponse(element);
                checkResponse(pilotsResponce);
                JsonPath jsonPathPeople = pilotsResponce.jsonPath();
                if (jsonPathPeople.getList("films").contains(baseURL + "films/3/")) {
                     if (!results.contains(element)) {
                        results.add(element);
                     }

                }
            }
            for (String element : results) {
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