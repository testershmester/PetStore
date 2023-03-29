package by.teachmeskills.api.client;

import by.teachmeskills.api.dto.ApiResponse;
import by.teachmeskills.api.dto.pet.Pet;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;

import java.util.Map;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

@Log4j2
public class PetApiClient extends BaseApiClient {

    public static final String PET_PATH = "/v2/pet";
    public static final String PET_ID_PATH = "/v2/pet/{petId}";
    public static final String PET_ID = "petId";

    public Pet postAddPet(Pet body) {
        log.info("POST /v2/pet");
        return post(PET_PATH, body).then()
                                   .statusCode(HttpStatus.SC_OK)
                                   .body("id", not(empty()))
                                   .extract()
                                   .body()
                                   .as(Pet.class);
    }

    public Pet getPet(long id) {
        log.info("GET /v2/pet/{petId} request with id = {}", id);
        return get(PET_ID_PATH, Map.of(PET_ID, id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(Pet.class);
    }

    public ApiResponse getPetResponse(long id, int statusCode) {
        log.info("GET /v2/pet/{petId} request with id = {}", id);
        return get(PET_ID_PATH, Map.of(PET_ID, id))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(ApiResponse.class);
    }

    public Pet putUpdatePet(Pet body) {
        log.info("PUT /v2/pet request with id = {}", body.getId());
        return put(PET_PATH, body).then()
                                  .statusCode(HttpStatus.SC_OK)
                                  .extract()
                                  .body()
                                  .as(Pet.class);
    }

    public ApiResponse deletePet(long id) {
        return delete(PET_ID_PATH, Map.of(PET_ID, id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(ApiResponse.class);
    }
}