package ba.unsa.etf.nwt.adopt_service.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class PetForAdoptRequest {

    @Valid
    private PetRequest petForAdopt;

    private String message;

    public PetRequest getPetForAdopt() {
        return petForAdopt;
    }

    public void setPetForAdopt(PetRequest petForAdopt) {
        this.petForAdopt = petForAdopt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
