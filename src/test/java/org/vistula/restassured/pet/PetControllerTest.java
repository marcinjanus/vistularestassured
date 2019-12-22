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

public class PetControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().log().all().get("/pet") //dodałem .log().all()
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    public void shouldGetFirstPet() {
        given().get("/pet/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", equalTo("Cow"));
    }

    @Test
    public void shouldntGetPet100() {
        given().get("/pet/100")
                .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("There is no Pet with such id"))
                .extract().statusCode();  //3 czesc testu - wyciagnij do
    }

    //OPTION + COMMAND + V - wyciąganie do zmiennej

    @Test
    public void shouldntGetPet200() {
        int statusCode = given().get("/pet/200")
                .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("There is not Pet with such id"))
                .extract().statusCode();//3 czesc testu - wyciagnij czesc odppwiedzi do zmiennej

        assertThat(statusCode).isEqualTo(404); //import atatic metod i wybieramy "assertjcode"
        //testujemy geta po id - test powiniens tworzyc dany zasob, wziac dany id i sprawdzic po id
    }


    //POST
    @Test
    public void shouldCreateNewPet() { //nowy test
        JSONObject requestParams = new JSONObject(); //nowy object klasy JsonObject (dodana dependencja orgJSON jsaon
        requestParams.put("id", 5);
        requestParams.put("name", "Kura");

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }

    //DELETE
    @Test
    public void shouldRemovePet() { //nowy test
        given().delete("/pet/4")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldGetSecondPet() {
        Object name = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"))
                .extract().path("name");

        assertThat(name).isEqualTo("Dog");
        // 1 napisalismy given i then
        //2 napisalismy extract.path name
        //3 wyciagnelismy responsa do zmiennej (skrót klawiaturowy)
        //4 asercja czy name 2 Dog
    }

    @Test
    public void shouldGetSecondPet2() {
        Object name = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"))
                .extract().path(".");

        assertThat(name).isEqualTo(2);
        // 1 napisalismy given i then
        //2 napisalismy extract.path name
        //3 wyciagnelismy responsa do zmiennej . (skrót klawiaturowy)
        //4 asercja czy name 2 Dog
    }

    @Test
    public void shouldGetSecondPet3() {
        Pet pet = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .extract().body().as(Pet.class);

        assertThat(pet.getId()).isEqualTo(2);
        assertThat(pet.getName()).isEqualTo("Dog");
        // dodalismu jacsonaktory wywlolal nam construktor - przetlumaczylsmy jsona na object javowy)
        // Pet pet = new Pet(1, Cow)
        // 1 napisalismy given i then
        //2 napisalismy extract.path name
        //3 wyciagnelismy responsa do zmiennej (skrót klawiaturowy)
        //4 asercja czy name 2 Dog
        //Java POJO
        //Java DTO
    }

    //POST
    @Test
    public void shouldCreateRemoveNewPet() { //nowy test
        JSONObject requestParams = new JSONObject(); //nowy object klasy JsonObject (dodana dependencja orgJSON jsaon
        int value = 4;
        requestParams.put("id", value);
        requestParams.put("name", "Kura");

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);

        given().delete("/pet/"+value)
                .then()
                .log().all()
                .statusCode(204);

    }

    //POST
    @Test
    public void shouldCreateRemoveNewPet2() { //nowy test
        JSONObject requestParams = new JSONObject(); //nowy object klasy JsonObject (dodana dependencja orgJSON jsaon
//        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE); //losowa wartosc
        requestParams.put("id", value);
        requestParams.put("name", RandomStringUtils.randomAlphabetic(10)); //losowa nazwa

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);

        given().delete("/pet/"+value)
                .then()
                .log().all()
                .statusCode(204);

    }
}
