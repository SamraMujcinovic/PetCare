package ba.unsa.etf.nwt.pet_category_service.controllers;


import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
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

    @PostMapping("/pet")
    public ResponseEntity<?> addPet( @RequestBody PetRequest petRequest){
        if(petRequest.getName().equals("")) return new ResponseEntity(new Response(false, "Pet's name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getName().length()<2 || petRequest.getName().length()>50) return new ResponseEntity(new Response(false, "Pet's name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getLocation().equals("")) return new ResponseEntity(new Response(false, "Pet's location can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getImage().equals("")) return new ResponseEntity(new Response(false, "Add an image of a pet!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getAge() > 100) return new ResponseEntity(new Response(false, "Pet can't be older than 100 years!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        petService.addPet(petRequest);
        return new ResponseEntity(new Response(true, "Pet added successfully!", "OK"), HttpStatus.OK);

    }


}
