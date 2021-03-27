package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.PetRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.PetResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.RaseResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final RaseRepository raseRepository;
    private final RaseService raseService;


    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public ResponseEntity<Response> addPet(PetRequest petRequest) {
        if(petRequest.getName().equals("")) return new ResponseEntity(new Response(false, "Pet's name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getName().length()<2 || petRequest.getName().length()>50) return new ResponseEntity(new Response(false, "Pet's name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getLocation().equals("")) return new ResponseEntity(new Response(false, "Pet's location can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(petRequest.getImage().equals("")) return new ResponseEntity(new Response(false, "Add an image of a pet!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        try{
            Integer age = petRequest.getAge();
            if(petRequest.getAge() > 100) return new ResponseEntity(new Response(false, "Pet can't be older than 100 years!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

            Pet pet = new Pet();
            pet.setName(petRequest.getName());
            pet.setLocation(petRequest.getLocation());
            pet.setImage(petRequest.getImage());
            pet.setAge(petRequest.getAge());
            pet.setAdopted(petRequest.isAdopted());
            pet.setDescription(petRequest.getDescription());
            try{
                Rase r = raseService.getRaseById(petRequest.getRase_id());
                pet.setRase(r);
                petRepository.save(pet);
            }
            catch (ResourceNotFoundException e){
                return new ResponseEntity(new Response(false, "There is no rase with that ID!!", "NOT_FOUND"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new Response(true, "Pet successfully added!!", "OK"), HttpStatus.OK);

        }catch (NullPointerException e){
            return new ResponseEntity(new Response(false, "Add the age of the pet!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        }

    }

    public void savePet(Pet p) {
        petRepository.save(p);
    }

    public Pet getPetById(Long id) {
        return petRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No pet with ID " + id));
    }

    public PetResponse getPet(Long id) {

        try{
            Pet p = getPetById(id);
            return new PetResponse(p, "Pet OK!", "OK", true);
        }catch (ResourceNotFoundException e){
            return new PetResponse(null, "Pet with that ID not found", "NOT FOUND",false);
        }
    }

    public Response deletePet(Long id) {
        try{
            Pet p = getPetById(id);
            petRepository.deleteById(id);
            return new Response(true, "Pet successfully deleted!", "OK");
        }catch (ResourceNotFoundException e){
            return new Response(false, "Pet with that ID not found", "NOT FOUND");
        }
    }

    public Pet findPetByName(String name) {
        return petRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with name " + name));
    }

    public PetResponse getPetByName(String name) {
        if(name == null) return new PetResponse(null, "Add a name for search!", "BAD_REQUEST", false);
        try {
            Pet p = findPetByName(name);
            return new PetResponse(p, "Pet found!", "OK", true);

        }catch (ResourceNotFoundException e){
            return new PetResponse(null, "Pet with that name not found!", "NOT FOUND", false);

        }
    }

    public List<Pet> getPetsInRase(Long id) {
        return petRepository.findByRase_Id(id);
    }
}
