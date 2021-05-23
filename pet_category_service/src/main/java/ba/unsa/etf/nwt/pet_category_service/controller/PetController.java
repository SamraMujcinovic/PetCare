package ba.unsa.etf.nwt.pet_category_service.controller;


import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.request.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.response.ResponseMessage;
import ba.unsa.etf.nwt.pet_category_service.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
public class PetController {
    private final PetService petService;

    //svi petovi
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/all/pets")
    public List<Pet> getPets(){
        return petService.getPets();
    }

    //svi approveani petovi
    @GetMapping("/pets")
    public List<Pet> getAllApprovedPets(){
        return petService.findAllApprovedPets();
    }

    @GetMapping("/pet/{id}")
    public Pet getPet(@NotNull @PathVariable Long id){
        return petService.getPet(id);
    }

    @GetMapping("/pets/inRase")
    public List<Pet> getPetsInRase(@NotNull @RequestParam Long id){
        return petService.getPetsInRase(id);
    }

    //tehnicki nam ni ne treba...
    @GetMapping("/pets/inCategory")
    public List<Pet> getPetsInCategory(@NotNull @RequestParam Long id){
        return petService.getPetsInCategory(id);
    }

    @GetMapping("/pet/byName")
    public List<Pet> getPetByName(@NotNull @RequestParam String name){
        return petService.getPetByName(name);
    }

    //search po name
    @GetMapping("/pets/name/contains")
    public List<Pet> getPetsNameContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsNameContainsString(substring);
    }

    //search po name za tu rasu
    @GetMapping("/pets/name/contains/thisRase/{id}")
    public List<Pet> getPetsNameContainsStringinRase(@PathVariable Long id, @NotNull @RequestParam String substring){
        return petService.getPetsNameContainsStringinRase(id, substring);
    }

    //search
    //filter za potragu po nazivu rase
    @GetMapping("/pets/rase/contains")
    public List<Pet> getPetsRaseContainsString(@NotNull @RequestParam String substring){
        return petService.getPetsRaseContainsString(substring);
    }

    //obicna ruta za dodavanje od strane admina, pettovi su approved
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/pet")
    public ResponseMessage addPet(@Valid @RequestBody PetRequest petRequest){
        return petService.addPet(petRequest);
    }

    //brisanje peta po id-u
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/pet/delete")
    public ResponseMessage deletePet(@NotNull @RequestParam Long id){
        return petService.deletePetById(id);
    }

    //approved opcija se rucno ne mijenja kroz update pet...
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/pet/update/{id}")
    public Pet updatePet(@NotNull @PathVariable Long id, @Valid @RequestBody PetRequest petRequest){
        return petService.updatePet(id, petRequest);
    }

}
