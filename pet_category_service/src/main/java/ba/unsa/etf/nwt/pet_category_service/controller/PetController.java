package ba.unsa.etf.nwt.pet_category_service.controller;


import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.request.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.response.ResponseMessage;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import java.util.List;

@AllArgsConstructor
@RestController
public class PetController {
    private final PetService petService;

    //svi
    @GetMapping("/pets")
    public List<Pet> getPets(){
        return petService.getPets();
    }

    //zast
    @GetMapping("/pet/{id}")
    public Pet getPet(@NotNull @PathVariable Long id){
        return petService.getPet(id);
    }

    //svi
    @GetMapping("/pets/inRase")
    public List<Pet> getPetsInRase(@NotNull @RequestParam Long id){
        return petService.getPetsInRase(id);
    }

    //svi
    @GetMapping("/pets/inCategory")
    public List<Pet> getPetsInCategory(@NotNull @RequestParam Long id){
        return petService.getPetsInCategory(id);
    }

    //svi
    @GetMapping("/pet/byName")
    public List<Pet> getPetByName(@NotNull @RequestParam String name){
        return petService.getPetByName(name);
    }

    //svi
    @GetMapping("/pets/name/contains")
    public List<Pet> getPetsNameContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsNameContainsString(substring);
    }

    //svi
    @GetMapping("/pets/rase/contains")
    public List<Pet> getPetsRaseContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsRaseContainsString(substring);
    }

    //zast
    @PostMapping("/pet")
    public ResponseMessage addPet(@Valid @RequestBody PetRequest petRequest){
        return petService.addPet(petRequest);
    }

    //brisanje peta po id-u
    //admin
    @DeleteMapping("/pet")
    public ResponseMessage deletePet(@NotNull @RequestParam Long id){
        return petService.deletePet(id);
    }

    //admin
    @PutMapping("/pet/update/{id}")
    public Pet updatePet(@NotNull @PathVariable Long id, @Valid @RequestBody PetRequest petRequest){
        return petService.updatePet(id, petRequest);
    }

}
