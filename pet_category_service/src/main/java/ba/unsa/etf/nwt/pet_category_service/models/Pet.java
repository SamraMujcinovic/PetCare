package ba.unsa.etf.nwt.pet_category_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotBlank(message = "Pet name can't be blank!")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Pet location can't be blank!")
    private String location;

    //da li je image string?
    @NotBlank(message = "Pet image can't be blank!")
    private String image;

    @Column(columnDefinition = "text")
    private String description;

    @NotNull(message = "Pet age can't be blank!")
    @Max(value = 100, message = "Pet can't be older than 100 years!")
    private Integer age;

    private boolean adopted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rase_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public Integer getAge() {
        return age;
    }

    public void setAge(@NotNull Integer age) {
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
