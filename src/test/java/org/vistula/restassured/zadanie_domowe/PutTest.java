package org.vistula.restassured.zadanie_domowe;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PutTest extends RestAssuredTest {

    @Test
    public void shouldUpdateByPut() {
        JSONObject requestParams = new JSONObject();
        String name = RandomStringUtils.randomAlphabetic(10);
        String nationality = "Poland";
        int salary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE);
        requestParams.put("name", name);
        requestParams.put("nationality", nationality);
        requestParams.put("salary", salary);

        Object idSoccer = postNewPlayer(requestParams, name, nationality, salary);

        String putName = RandomStringUtils.randomAlphabetic(10);
        String putNationality = "Germany";
        int putSalary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE);
        requestParams.put("name", putName);
        requestParams.put("nationality", putNationality);
        requestParams.put("salary", putSalary);

        putPlayer(requestParams, idSoccer, putName, putNationality, putSalary);

        deletePlayer(idSoccer);
    }

    private Object postNewPlayer(JSONObject requestParams, String name, String nationality, int salary) {
        return given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("nationality", equalTo(nationality))
                .body("salary", equalTo(salary))
                .body("id", greaterThan(0))
                .extract().path("id");
    }

    private void putPlayer(JSONObject requestParams, Object idSoccer, String putName, String putNationality, int putSalary) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/" + idSoccer)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(putName))
                .body("nationality", equalTo(putNationality))
                .body("salary", equalTo(putSalary))
                .body("id", equalTo(idSoccer))
                .extract().path("id");
    }

    private void deletePlayer(Object idSoccer) {
        given().delete("/information/" +idSoccer)
                .then()
                .log().all()
                .statusCode(204);
    }
}
