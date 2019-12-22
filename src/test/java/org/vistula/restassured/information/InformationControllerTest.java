package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;


public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void shouldAddNewFootballer() { //nowy test
        JSONObject requestParams = new JSONObject(); //nowy object klasy JsonObject (dodana dependencja orgJSON jsaon
        String name = RandomStringUtils.randomAlphabetic(10);
        String nationality = "Poland";
        int randomSalary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE); //losowa wartosc
        requestParams.put("name", name);
        requestParams.put("nationality", nationality);
        requestParams.put("salary", randomSalary);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("nationality", equalTo(nationality))
                .body("salary", equalTo(randomSalary))
                .body("id", greaterThan(0));
    }

    @Test
    public void shouldRemoveFootballer() { //nowy test

        given().delete("/information/5")
                .then()
                .log().all()
                .statusCode(204);
    }


}
