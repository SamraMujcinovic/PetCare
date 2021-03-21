package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.PetRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.PetRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final RaseRepository raseRepository;


    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet addPet(PetRequest petRequest) {
        Pet pet = new Pet();
        pet.setName(petRequest.getName());
        pet.setLocation(petRequest.getLocation());
        pet.setImage(petRequest.getImage());
        pet.setAge(petRequest.getAge());
        pet.setAdopted(petRequest.isAdopted());
        pet.setDescription(petRequest.getDescription());
        Rase r = getRaseById(petRequest.getRase_id());
        pet.setRase(r);
        return petRepository.save(pet);
    }

    public void savePet(Pet p) {
        petRepository.save(p);
    }

    public Rase getRaseById(Long id) {
        return raseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with ID " + id));
    }
}
