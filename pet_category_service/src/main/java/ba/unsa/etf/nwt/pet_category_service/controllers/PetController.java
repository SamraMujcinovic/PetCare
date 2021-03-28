package ba.unsa.etf.nwt.pet_category_service.controllers;


import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
public class PetController {
    private final PetService petService;

    @GetMapping("/pets")
    public List<Pet> getPets(){
        return petService.getPets();
    }

    @GetMapping("/pet")
    public Pet getPet(@NotNull @RequestParam Long id){
        return petService.getPet(id);
    }

    @GetMapping("/pets/inRase")
    public List<Pet> getPetsInRase(@NotNull @RequestParam Long id){
        return petService.getPetsInRase(id);
    }

    @GetMapping("/pets/inCategory")
    public List<Pet> getPetsInCategory(@NotNull @RequestParam Long id){
        return petService.getPetsInCategory(id);
    }

    @GetMapping("/pet/byName")
    public Pet getPetByName(@NotNull @RequestParam String name){
        return petService.getPetByName(name);
    }

    @PostMapping("/pet")
    public Response addPet(@Valid @RequestBody PetRequest petRequest){
        return petService.addPet(petRequest);
    }

    //brisanje peta po id-u
    @DeleteMapping("/pet")
    public Response deletePet(@NotNull @RequestParam Long id){
        return petService.deletePet(id);
    }

    @PutMapping("/pet/update/{id}")
    public Pet updatePet(@NotNull @PathVariable Long id, @Valid @RequestBody PetRequest petRequest){
        return petService.updatePet(id, petRequest);
    }

}
