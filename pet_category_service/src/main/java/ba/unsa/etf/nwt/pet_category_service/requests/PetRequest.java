package ba.unsa.etf.nwt.pet_category_service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {


    @NotEmpty(message = "Pet must have a name!")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "You have to add a pets location!")
    private String location;

    //da li je image string?
    @NotBlank(message = "Please add an image of pet!")
    private String image;

    private String description;

    @NotNull(message = "Add age for pet!")
    @Max(value = 100, message = "Pet cannot be older than 100 years!")
    private Integer age;

    private boolean adopted;

    private Long rase_id;

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(@NotNull Integer age) {
        this.age = age;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public Long getRase_id() {
        return rase_id;
    }

    public void setRase_id(Long rase_id) {
        this.rase_id = rase_id;
    }
}
