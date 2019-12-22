package org.vistula.restassured.zadanie_domowe;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PatchTest1 extends RestAssuredTest {
    @Test
    public void shouldUpdateByPatch() {
        JSONObject requestParams = new JSONObject();
        String name = RandomStringUtils.randomAlphabetic(10);
        String nationality = "Poland";
        int salary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE);
        requestParams.put("name", name);
        requestParams.put("nationality", nationality);
        requestParams.put("salary", salary);

        Object idSoccer = postNewPlayer(requestParams, name, nationality, salary);

        String patchName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", patchName);

        patchNewPlayer(requestParams, nationality, salary, idSoccer, patchName);

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

    private void patchNewPlayer(JSONObject requestParams, String nationality, int randomSalary, Object idSoccer, String patchName) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .patch("/information/" + idSoccer)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(patchName))
                .body("nationality", equalTo(nationality))
                .body("salary", equalTo(randomSalary))
                .body("id", equalTo(idSoccer));
    }

    private void deletePlayer(Object idSoccer) {
        given().delete("/information/" +idSoccer)
                .then()
                .log().all()
                .statusCode(204);
    }
}
