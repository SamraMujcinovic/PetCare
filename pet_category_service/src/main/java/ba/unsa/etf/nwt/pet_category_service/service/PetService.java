package ba.unsa.etf.nwt.pet_category_service.service;

import ba.unsa.etf.nwt.pet_category_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import ba.unsa.etf.nwt.pet_category_service.model.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.PetRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.request.PetRequest;
import ba.unsa.etf.nwt.pet_category_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final RaseService raseService;


    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public ResponseMessage addPet(PetRequest petRequest) {
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

            return new ResponseMessage(true, HttpStatus.OK,"Pet successfully added!!");
/*
        }catch (NullPointerException e){
            return new Response(false, "Add the age of the pet!", HttpStatus.BAD_REQUEST);

        }*/

    }

    public Long addPetForAdopt(PetRequest petRequest) {
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

        return pet.getId();
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

    public ResponseMessage deletePet(Long id) {
        Pet p = getPetById(id);
        petRepository.deleteById(id);
        return new ResponseMessage(true, HttpStatus.OK,"Pet successfully deleted!");
    }

    public List<Pet> findPetByName(String name) {
        List<Pet> petoviSaIstimImenom = petRepository.findPetsByName(name);
        if(petoviSaIstimImenom.isEmpty()) throw new ResourceNotFoundException("No pet with name " + name);
        return petoviSaIstimImenom;
    }

    public List<Pet> getPetByName(String name) {
        //if(name == null) return new PetResponse(null, "Add a name for search!", "BAD_REQUEST", false);
        List<Pet> p = findPetByName(name);
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

    public List<Pet> getPetsNameContainsString(String substring) {
        return petRepository.findByNameContains(substring);
    }

    public List<Pet> getPetsRaseContainsString(String substring) {
        return petRepository.findByRase_NameContains(substring);
    }

    public Long getPetIDForAdopt() {
        List<Pet> sviPetovi = getPets();
        Pet p = sviPetovi.get(sviPetovi.size() - 1);
        return p.getId();
    }
}
