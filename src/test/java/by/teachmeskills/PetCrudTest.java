package by.teachmeskills;

import by.teachmeskills.api.dto.Category;
import by.teachmeskills.api.dto.Pet;
import by.teachmeskills.api.dto.Status;
import by.teachmeskills.api.dto.Tag;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class PetCrudTest {

    @Test
    public void createPet() {
        Pet expPet = Pet.builder()
                        .id(0)
                        .name("Doggie")
                        .status(Status.available)
                        .photoUrls(List.of("https://avatars.mds.yandex.net/image.png"))
                        .category(new Category(12345, "categoryName"))
                        .tags(List.of(new Tag(1, "dog")))
                        .build();
        Pet createdPet = given()
                .contentType(ContentType.JSON)
                .body(expPet)
                .when()
//              .                  log().ifValidationFails(LogDetail.ALL, true)
                .log().all()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
//              .                  log().ifValidationFails(LogDetail.ALL, true)
                .log().all()
                .statusCode(200)
                .body("id", not(empty()))
                .extract()
                .body()
                .as(Pet.class);

        assertThat(createdPet).as("\"id\" should be generated in response")
                              .usingRecursiveComparison()
                              .ignoringFields("id")
                              .isEqualTo(expPet);
        assertThat(createdPet).as("POST /pet response is correct")
                              .usingRecursiveComparison()
                              .ignoringFields("id")
                              .isEqualTo(expPet);

        Pet actPet = given()
                .contentType(ContentType.JSON)
                .pathParams("id", createdPet.getId())
                .when()
                .get("https://petstore.swagger.io/v2/pet/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Pet.class);

        assertThat(actPet).as("GET /pet/{id} response is correct").isEqualTo(createdPet);
    }
}
