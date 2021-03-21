package ba.unsa.etf.nwt.pet_category_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Pet must have a name!")
    @Size(max = 50, message = "Name must have less then 50 characters!")
    private String name;

    @NotBlank(message = "You have to add a pets location!")
    private String location;

    //da li je image string?
    @NotBlank(message = "Please add an image of pet!")
    private String image;

    private String description;

    @Max(value = 100, message = "Pet cannot be older than 100 years!")
    private int age;

    private boolean adopted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rase_id")
    private Rase rase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public void setDescription(String descrtiption) {
        this.description = descrtiption;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public Rase getRase() {
        return rase;
    }

    public void setRase(Rase rase) {
        this.rase = rase;
    }
}
