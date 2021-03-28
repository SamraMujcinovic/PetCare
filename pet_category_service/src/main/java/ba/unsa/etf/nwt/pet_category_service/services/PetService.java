package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.PetRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public Response addPet(PetRequest petRequest) {
       // try{
            Integer age = petRequest.getAge();
            Pet pet = new Pet();
            pet.setName(petRequest.getName());
            pet.setLocation(petRequest.getLocation());
            pet.setImage(petRequest.getImage());
            pet.setAge(petRequest.getAge());
            pet.setAdopted(petRequest.isAdopted());
            pet.setDescription(petRequest.getDescription());

            Rase r = raseService.getRaseById(petRequest.getRase_id());
            pet.setRase(r);
            petRepository.save(pet);

            return new Response(true, "Pet successfully added!!", HttpStatus.OK);
/*
        }catch (NullPointerException e){
            return new Response(false, "Add the age of the pet!", HttpStatus.BAD_REQUEST);

        }*/

    }

    public void savePet(Pet p) {
        petRepository.save(p);
    }

    public Pet getPetById(Long id) {
        return petRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No pet with ID " + id));
    }

    public Pet getPet(Long id) {

        Pet p = getPetById(id);
        return p;
    }

    public Response deletePet(Long id) {

        Pet p = getPetById(id);
        petRepository.deleteById(id);
        return new Response(true, "Pet successfully deleted!", HttpStatus.OK);
    }

    public Pet findPetByName(String name) {
        return petRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No pet with name " + name));
    }

    public Pet getPetByName(String name) {
        //if(name == null) return new PetResponse(null, "Add a name for search!", "BAD_REQUEST", false);
        Pet p = findPetByName(name);
        return p;
    }

    public List<Pet> getPetsInRase(Long id) {
        return petRepository.findByRase_Id(id);
    }

    public Pet updatePet(Long id, PetRequest petRequest) {
        Pet p = getPet(id);

       // try{
            Integer age = petRequest.getAge();

            Rase r= raseService.getRase(petRequest.getRase_id());

            p.setName(petRequest.getName());
            p.setLocation(petRequest.getLocation());
            p.setDescription(petRequest.getDescription());
            p.setImage(petRequest.getImage());
            p.setAge(petRequest.getAge());
            p.setAdopted(petRequest.isAdopted());
            p.setRase(r);
            petRepository.save(p);
            return p;
    /*    }catch (NullPointerException e){
            return new Response(false, "Add the age of the pet!", HttpStatus.OK);

        }*/
    }

    public List<Pet> getPetsInCategory(Long id) {
        //vraca sve petove koji pripadaju nekoj kategoriji
        //znaci vraca sve cuke, ili sve mace, ili sve ribe i sl
        //find all pets where rase.getCategoryID = id
        return petRepository.findByRase_Category_Id(id);
    }
}
