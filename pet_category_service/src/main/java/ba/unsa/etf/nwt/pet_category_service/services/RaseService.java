package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.RaseRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RaseService {

    private final RaseRepository raseRepository;
    private final CategoryRepository categoryRepository;

    public List<Rase> getRases() {
        return raseRepository.findAll();
    }

    public Rase addRase(RaseRequest raseRequest) {
        Rase rase = new Rase();
        rase.setName(raseRequest.getName());
        rase.setDescription(raseRequest.getDescription());
        Category category = getCategoryById(raseRequest.getCategory_id());
        rase.setCategory(category);
        return raseRepository.save(rase);
    }

    public Rase saveRase(Rase rase){
        return raseRepository.save(rase);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID " + id));
    }
}
