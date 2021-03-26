package ba.unsa.etf.nwt.pet_category_service.controllers;


import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.PetResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public PetResponse getPet(@RequestParam Long id){
        return petService.getPet(id);
    }

    @PostMapping("/pet")
    public ResponseEntity<?> addPet( @RequestBody PetRequest petRequest){
        return petService.addPet(petRequest);
    }


    //brisanje peta po id-u
    @DeleteMapping("/pet")
    public Response deletePet(@RequestParam Long id){
        return petService.deletePet(id);
    }

}
