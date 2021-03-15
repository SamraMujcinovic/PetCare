package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RaseService {

    private final RaseRepository raseRepository;

    public List<Rase> getRases() {
        return raseRepository.findAll();
    }

    public Rase addRase(Rase rase) {
        return raseRepository.save(rase);
    }
}
