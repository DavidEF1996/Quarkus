package org.example.fullstack.controller;


import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import io.restassured.http.Header;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;



class TaskResourceTest {
    @Test
    @TestSecurity(user = "user", roles = "user")
    void deleteTask() {
        given()
                .header(new Header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6IkZlcm5hbmRvRWcyMSIsImdyb3VwcyI6WyJhZG1pbiIsInVzZXIiXSwiaWF0IjoxNjg3MjA1NDU2LCJleHAiOjE2ODcyMDkwNTYsImp0aSI6ImU1NTVkNzQ2LTkyNzYtNDU0NC04Yzg0LTcxMDA3YmM3MWMxNiJ9.bZKdMRiwsn3PYI-dx6LSrUd2XZknmJQz2y22NWTZt_5CuJgjXU9XD37wEOof7L_7qF0rHxbfSEOso_P2c7xuiJswZfHZ2GcFgnCMo1SquMtjtwSRNMvL3BQCEd6XJyW_bkgdoA3wvfpvjWIJ9IczUFClvEgQL2oo8yX9dE06F9FpRRVtEzDLRZtUlI0tub6DTkcm9Ed2f7YvUIeF0z-5WVvDoLk8d4Z68LGlTaaWmkTskDCRTOGUQbQV6Y5yLF9szzNw1WZln76KclWeg4fH3-u9Kbx85vv9rDZRpDEbNWpjJQfruMgAuJugO6ghyMwsuyTUHKR9YfmjfRuTVlRhwg"))
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/tasks/51")
                .then()
                .statusCode(204);
    }
}