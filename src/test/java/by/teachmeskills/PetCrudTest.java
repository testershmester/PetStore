package by.teachmeskills;

import by.teachmeskills.api.client.PetApiClient;
import by.teachmeskills.api.dto.ApiResponse;
import by.teachmeskills.api.dto.pet.Category;
import by.teachmeskills.api.dto.pet.Pet;
import by.teachmeskills.api.dto.pet.Status;
import by.teachmeskills.api.dto.pet.Tag;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PetCrudTest {

    public static final String PET_NOT_FOUND = "Pet not found";

    private final Faker faker = new Faker();

    @Test
    public void createPet() {
        Pet expPet = createPetModel();
        Pet createdPet = new PetApiClient().postAddPet(expPet);

        assertThat(createdPet).as("\"id\" should be generated in response")
                              .usingRecursiveComparison()
                              .ignoringFields("id")
                              .isEqualTo(expPet);
        assertThat(createdPet).as("POST /pet response is correct")
                              .usingRecursiveComparison()
                              .ignoringFields("id")
                              .isEqualTo(expPet);

        Pet actPet = new PetApiClient().getPet(createdPet.getId());

        assertThat(actPet).as("GET /pet/{id} response is correct").isEqualTo(createdPet);
    }

    @Test
    public void updatePet() {
        Pet expPet = createPetModel();
        Pet createdPet = new PetApiClient().postAddPet(expPet);
        String expName = createdPet.getName() + "_updated";
        createdPet.setName(expName);

        Pet actPet = new PetApiClient().putUpdatePet(createdPet);

        assertThat(actPet).as("PUT /pet response is correct. Updated name is correct")
                          .isEqualTo(createdPet);
    }

    @Test
    public void deletePet() {
        Pet expPet = createPetModel();
        Pet createdPet = new PetApiClient().postAddPet(expPet);

        ApiResponse apiResponse = new PetApiClient().deletePet(createdPet.getId());
        assertThat(apiResponse.getMessage()).as("DELETE /pet/{pedId] response contains correct message (petId)")
                                            .isEqualTo(String.valueOf(createdPet.getId()));

        ApiResponse errorResponse = new PetApiClient().getPetResponse(createdPet.getId(), HttpStatus.SC_NOT_FOUND);
        assertThat(errorResponse.getMessage()).as("Message in response is correct")
                                         .isEqualTo(PET_NOT_FOUND);

    }

    private Pet createPetModel() {
        return Pet.builder()
                  .id(0)
                  .name(faker.animal().name())
                  .status(Status.available)
                  .photoUrls(List.of(faker.internet().image()))
                  .category(new Category(faker.number().numberBetween(0, 5), faker.funnyName().name()))
                  .tags(List.of(new Tag(1, "dog")))
                  .build();
    }
}
