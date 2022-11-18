package org.andcip;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;
import org.andcip.entity.Client;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ClientResourceTest {

    @Test
    public void testList() {
        given()
                .when().get("/clients")
                .then()
                .statusCode(200);

    }

    @Test
    public void testGetClient() {
        given()
                .when().get("/clients/1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateClientError() {
        given()
                .body(new Client(2L,"test", "test@email.com"), ObjectMapperType.JSONB).contentType("application/json").when().post("/clients")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateClient() {
        given()
                .body(new Client("test", "test@email.com"), ObjectMapperType.JSONB).contentType("application/json").when().post("/clients")
                .then()
                .statusCode(201);
    }

}
