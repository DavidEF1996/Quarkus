package org.example.fullstack.controller;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;

@QuarkusTest
class UserResourceTest {

    @Test
    void listaUsuariosAutorizada(){
        given()
                .header(new Header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6IkZlcm5hbmRvRWcyMSIsImdyb3VwcyI6WyJhZG1pbiIsInVzZXIiXSwiaWF0IjoxNjg3MjAzODUyLCJleHAiOjE2ODcyMDc0NTIsImp0aSI6IjQzNjFkYTMyLTM1NWYtNDE5Yy04ODZiLWY1ZjQzM2Y5YmU3OSJ9.fnnZorQXPFBEUkUIh9Mj7z9B6L1QYGVAnXAeRXoIm7XS1bGHUl2ILQd9lOyKLy3AZAGhJweK2gRxgwqGW5tEtOwDCd2R5w73sp_p__JwJUm_pKIxTpsNxzrmXUa7PYo3Lcj-hB8xba142_x_zspo55kS_lMFtr-Ok4W6Lg80SjSyiRikyjJOTMTnW3ojfW2dy5593c7PwKPMPWK3nhseBtPHeWe4ezeCL54WnM4tAFRSXGYGwVEkUkG46TfZFAZc93-D2rnatOhdWRscm3XzOIWchVudDTQRWBDWnINq9piFrYvb89stKVCFyEsQ6lcT1zCKfGJNkZLMoQP_dU62GQ"))
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/users")
                .then()
                .statusCode(200)
                .body("[0].name", is("admin"));

    }


    @Test
    void listaUsuariosNoAutorizada(){
        given()
                .header(new Header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6IkZlcm5hbmRvRWcyMCIsImdyb3VwcyI6WyJ1c2VyIl0sImlhdCI6MTY4NzE5ODUwMywiZXhwIjoxNjg3MjAyMTAzLCJqdGkiOiIzOTVjMjY0NC0xZWQ4LTQ2NzQtODVmMC04ZjhkMGNmODIwYTIifQ.5bjsAf3Wc-IH5F4cbATwpyh6TdkjYb3pJq3-Nlp53rfdTI6PCZ0wXT2j8bZBVCsUK68xKM0IRmquV1zDQtxizojFLK7m57x85aEDCP4PUTbf4Nr9VL8bev8OAsAz28dZ01KkV34cGgEQ8umNAnVh0YXvAVyyT9uezM1RngZFd2YXcqLpejyejZpoWIWo-1uV4wtNfv1Xd86M91OZp_JeNYj1LTc3b7Mx2G5Bqnl_oUc_qHnu1psT6nxWdtzi1EUvl0XV8sBE4PMQUyAM0aX_85LTiG5VJXG6WAWSdthA9GPQs0L9kQ_mP7Aa4PjJOH9Jx5eR5gIh_a7qPt9re1S-BA"))
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/users")
                .then()
                .statusCode(401);


    }
}