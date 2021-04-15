package api;

import api.model.UserModel;
import org.junit.jupiter.api.Test;

import static api.endpoints.Endpoints.USER;
import static io.restassured.RestAssured.given;
import static api.spec.Spec.spec;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserTests {
    @Test
    void userTest() {
        UserModel user = new UserModel();

        user.setUsername("test_username1234");
        user.setPassword("123321");
        user.setFirstName("Testinger");
        user.setLastName("Testingoff");
        user.setEmail("mamilo@mail.oo");
        user.setPhone("78789393993");

        String id = given(spec)
                .body(user)
                .post(USER.getPath())
                .then()
                .statusCode(200)
                .body("message", notNullValue())
                .extract().path("message").toString();

        user.setId(id);

        given(spec)
                .get(USER.addPath("/{username}"), user.getUsername())
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("phone", equalTo(user.getPhone()));

        user.setPhone("000000000");

        given(spec)
                .body(user)
                .log().body()
                .put(USER.addPath("/{username}"), user.getUsername())
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .as(UserModel.class);

        given(spec)
                .get(USER.addPath("/{username}"), user.getUsername())
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("phone", equalTo(user.getPhone()));

        given(spec)
                .delete(USER.addPath("/{username}"), user.getUsername())
                .then()
                .log().body();

    }
}
