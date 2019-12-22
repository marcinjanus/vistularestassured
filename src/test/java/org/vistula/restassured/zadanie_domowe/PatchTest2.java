package org.vistula.restassured.zadanie_domowe;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PatchTest2 extends RestAssuredTest {

    @Test
    public void shouldUpdateByPatch() {
        JSONObject requestParams = new JSONObject();
        String name = RandomStringUtils.randomAlphabetic(10);
        String nationality = "Poland";
        int salary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE);
        requestParams.put("name", name);
        requestParams.put("nationality", nationality);
        requestParams.put("salary", salary);

        Object idSoccer = addNewPlayer(requestParams, name, nationality, salary);

        String patchName = RandomStringUtils.randomAlphabetic(10);
        String patchNationality = "Germany";
        int patchSalary = ThreadLocalRandom.current().nextInt(6, Integer.MAX_VALUE);
        requestParams.put("name", patchName);
        requestParams.put("nationality", patchNationality);
        requestParams.put("salary", patchSalary);

        patchNameNationalitySalary(requestParams, patchSalary, idSoccer, patchName, patchNationality);

        deletePlayer(idSoccer);
    }


    private Object addNewPlayer(JSONObject requestParams, String name, String nationality, int salary) {
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

    private void patchNameNationalitySalary(JSONObject requestParams, int patchSalary, Object idSoccer, String patchName, String patchNationality) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .patch("/information/" + idSoccer)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(patchName))
                .body("nationality", equalTo(patchNationality))
                .body("salary", equalTo(patchSalary))
                .body("id", equalTo(idSoccer));
    }

    private void deletePlayer(Object idSoccer) {
        given().delete("/information/" +idSoccer)
                .then()
                .log().all()
                .statusCode(204);
    }
}
