package api;

import api.model.PetModel;
import org.junit.jupiter.api.Test;

import static api.endpoints.Endpoints.PET;
import static io.restassured.RestAssured.given;
import static api.spec.Spec.spec;
import static org.hamcrest.Matchers.equalTo;

public class PetTests {
    @Test
    void petCreationAndUpdateTest() {
        final PetModel pet = new PetModel();
        pet.setName("chappyHa");
        pet.setStatus("available");

        final PetModel createdPet = given(spec)
                .body(pet)
                .post(PET.getPath())
                .then()
                .statusCode(200)
                .extract()
                .as(PetModel.class);

        given(spec)
                .get(PET.addPath("/{id}"), createdPet.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(createdPet.getName()));

        pet.setName("Cacao");
        pet.setId(createdPet.getId());

        final PetModel updatedPet = given(spec)
                .body(pet)
                .put(PET.getPath())
                .then()
                .statusCode(200)
                .extract()
                .as(PetModel.class);

        given(spec)
                .get(PET.addPath("/{id}"), updatedPet.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(pet.getName()));
    }

    @Test
    void getNotExistingPetTest() {
        given(spec)
                .get(PET.addPath("/{id}"), -100)
                .then()
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));

    }

}
