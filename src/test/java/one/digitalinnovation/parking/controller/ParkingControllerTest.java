package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.awt.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth()
                .basic("user", "Dio@123456")
                .when()
                .get("/parking")
                .then()
                .body("license[0]", Matchers.equalTo("DMS-1111"));
    }

    @Test
    void whenCreateThenCheckIsCreated() {
        var parkingDto = new ParkingDTO();
        parkingDto.setColor("ROXO");
        parkingDto.setLicense("WRT-5555");
        parkingDto.setState("MG");

        RestAssured.given()
                .auth()
                .basic("user", "Dio@123456")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingDto)
                .post("/parking")
                .then()
                .statusCode(201)
                .body("license", Matchers.equalTo("WRT-5555"));
    }
}