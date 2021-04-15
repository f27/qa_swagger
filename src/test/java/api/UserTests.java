package api;

import api.model.UserModel;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static api.endpoints.Endpoints.USER;
import static io.restassured.RestAssured.given;
import static api.spec.Spec.spec;
import static org.hamcrest.Matchers.*;

public class UserTests {

    private String registerUser(UserModel user) {
        return given(spec)
                .body(user)
                .post(USER.getPath())
                .then()
                .statusCode(200)
                .body("message", notNullValue())
                .extract().path("message").toString();
    }

    private void deleteUser(UserModel user) {
        given(spec)
                .delete(USER.addPath("/{username}"), user.getUsername())
                .then()
                .log().body();
    }

    @Test
    void userTest() {
        UserModel user = new UserModel();

        user.setUsername("test_name1234");
        user.setPassword("123321");
        user.setFirstName("Testinger");
        user.setLastName("Testingoff");
        user.setEmail("mamilo@mail.oo");
        user.setPhone("78789393993");

        user.setId(registerUser(user));

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

        deleteUser(user);

    }

    @Test
    void loginTest() {
        UserModel user = new UserModel();

        user.setUsername("test_name1234");
        user.setPassword("123321");
        user.setFirstName("Testinger");
        user.setLastName("Testingoff");
        user.setEmail("mamilo@mail.oo");
        user.setPhone("78789393993");

        user.setId(registerUser(user));

        Map<String, String> loginUser = new HashMap<String, String>() {{
            put("username", user.getUsername());
            put("password", user.getPassword());
        }};

        given(spec)
                .queryParams(loginUser)
                .get(USER.addPath("/login"))
                .then()
                .statusCode(200)
                .body("message", containsString("logged in"));

        given(spec)
                .get(USER.addPath("/logout"))
                .then()
                .log().body()
                .statusCode(200);

        deleteUser(user);


    }
}
