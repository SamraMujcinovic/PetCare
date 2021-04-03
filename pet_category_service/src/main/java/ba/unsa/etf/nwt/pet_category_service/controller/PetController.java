package ba.unsa.etf.nwt.pet_category_service.controller;


import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.request.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.response.Response;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
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
    public List<Pet> getPetByName(@NotNull @RequestParam String name){
        return petService.getPetByName(name);
    }

    @GetMapping("/pets/name/contains")
    public List<Pet> getPetsNameContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsNameContainsString(substring);
    }

    @GetMapping("/pets/rase/contains")
    public List<Pet> getPetsRaseContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsRaseContainsString(substring);
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