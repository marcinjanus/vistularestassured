package org.vistula.restassured.pet;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class PetPostGetScenarioTest extends RestAssuredTest {

    //POST
    @Test
    public void shouldGetPet() { //nowy test
        JSONObject requestParams = new JSONObject(); //nowy object klasy JsonObject (dodana dependencja orgJSON jsaon
        int randomId = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE); //losowa wartosc
        String randomName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("id", randomId);
        requestParams.put("name", randomName); //losowa nazwa

        createNewPet(requestParams);

//        given().header("Content-Type", "application/json")
//                .body(requestParams.toString())
//                .post("/pet")
//                .then()
//                .log().all()
//                .statusCode(201);

        // tworzenie metody option xommand M


        getNewPet(randomId, randomName); //getItem

//        given().get("/pet/"+randomId)
//                .then()
//                .log().all()
//                .statusCode(200)
//                .body("id", is(randomId))
//                .body("name" ,is(randomName));

        // tworzenie metody option xommand M

        removeNewPet(randomId);

//        given().delete("/pet/"+randomId)
//                .then()
//                .log().all()
//                .statusCode(204);
    }

    private void removeNewPet(int randomId) {
        given().delete("/pet/" + randomId)
                .then()
                .log().all()
                .statusCode(204);
    }

    private void getNewPet(int randomId, String randomName) {
        given().get("/pet/"+randomId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(randomId))
                .body("name" ,is(randomName));
    }

    private void createNewPet(JSONObject requestParams) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }
}
