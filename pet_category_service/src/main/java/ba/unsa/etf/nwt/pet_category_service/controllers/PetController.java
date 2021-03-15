package ba.unsa.etf.nwt.pet_category_service.controllers;


import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class PetController {
    private final PetService petService;

    @GetMapping("/pets")
    public List<Pet> getPets(){
        return petService.getPets();
    }

    @PostMapping("/pet")
    public Pet addPet(@RequestBody Pet pet){
        return petService.save(pet);
    }


}
