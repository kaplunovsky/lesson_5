import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class test {

    RequestSpecification requestSpec = new RequestSpecBuilder().build()
            .given().baseUri("https://rickandmortyapi.com")
            .contentType(ContentType.JSON)
            .log().all();

    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    @Tag("api_1")
    @Test
    @DisplayName("Тест 1. Характер Морти")
    public void morti() {

        Response response1 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .spec(responseSpec).extract().response();

        String resp1 = response1.getBody().asString();
        System.out.println(resp1);
    }

    @Tag("api_1")
    @Test
    @DisplayName("Тест 3. Последний персонаж последнего эпизода")
    public void last_episode_last_person() {

        Response response2 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .spec(responseSpec).extract().response();


        String resp2 = response2.getBody().asString();
        System.out.println("RESULT: " + resp2);

        JSONObject json = new JSONObject(resp2);
        int arrSize = json.getJSONArray("episode").length();
        String episode = json.getJSONArray("episode").getString (arrSize-1);

        //--------------- Получение последнего персонажа
        System.out.println(episode);
        Response response3 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(episode)
                .then()
                .spec(responseSpec).extract().response();

        String resp3 = response3.getBody().asString();
        System.out.println(resp3);

        JSONObject json2 = new JSONObject(resp3);
        int arrSize2 = json2.getJSONArray("characters").length();
        String characters = json2.getJSONArray("characters").getString (arrSize2-1);

        System.out.println("RESULT: " + characters);
    }

    @Tag("api_1")
    @Test
    @DisplayName("Тест 2. Последний эпизод с Морти")
    public void morti_last_episode() {

        Response response2 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .spec(responseSpec).extract().response();

        String resp2 = response2.getBody().asString();
        System.out.println(resp2);

        JSONObject json = new JSONObject(resp2);
        int arrSize = json.getJSONArray("episode").length();
        String episode = json.getJSONArray("episode").getString (arrSize-1);

        System.out.println("RESULT: " + episode);
        //Assertions.assertEquals(json.getJSONArray()  String("name"), "morpheus");

    }


    @Tag("api_2")
    @Test
    @DisplayName("2. Создание пользователя ")
    public void test2() {
        String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Tomato");
        requestBody.put("job", "Eat maket");

        Response response4 = given()
                .baseUri("https://reqres.in/")
                .contentType("application/json;charset=UTF-8")
                .log().all()
                .when()
                .body(requestBody.toString())
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        String resp4 = response4.getBody().asString();
        JSONObject json = new JSONObject(resp4);
        Assertions.assertEquals(json.getString("name"), "Tomato");
        Assertions.assertEquals(json.getString("job"), "Eat maket");
    }
}
