package org.vistula.restassured;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class RestAssuredTest {
    // super klasa (nadrzedna)
    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9999;
    }
}
