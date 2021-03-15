package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import ba.unsa.etf.nwt.pet_category_service.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;


    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }
}
