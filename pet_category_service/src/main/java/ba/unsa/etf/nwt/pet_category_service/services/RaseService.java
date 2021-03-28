package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.exceptions.WrongInputException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.RaseRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RaseService {

    private final RaseRepository raseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public List<Rase> getRases() {
        return raseRepository.findAll();
    }

    public Response addRase(RaseRequest raseRequest) {
        try {
            Rase r = findRaseByName(raseRequest.getName());
            throw new WrongInputException("Rase with that name already exists!");
        }catch (ResourceNotFoundException e) {
            Rase rase = new Rase();
            rase.setName(raseRequest.getName());
            rase.setDescription(raseRequest.getDescription());

            Category category = categoryService.getCategoryById(raseRequest.getCategory_id());
            rase.setCategory(category);
            raseRepository.save(rase);

            return new Response(true, "Rase successfully added!!", HttpStatus.OK);
        }
    }

    public Rase saveRase(Rase rase){
        return raseRepository.save(rase);
    }

    public Rase getRase(Long id) {

        Rase r = getRaseById(id);
        return r;

    }

    public Rase getRaseById(Long id) {
        return raseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with ID " + id));
    }

    public Response deleteRase(Long id) {

        Rase r = getRaseById(id);
        raseRepository.deleteById(id);
        return new Response(true, "Rase successfully deleted", HttpStatus.OK);
    }

    public Rase findRaseByName(String name) {
        return raseRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with name " + name));
    }

    public Rase getRaseByName(String name) {
        //if(name == null) return new RaseResponse(null, "Add a name for search!", "BAD_REQUEST", false);

        Rase r = findRaseByName(name);
        return r;
    }

    public List<Rase> getRasesInCategory(Long id) {

        //trebalo bi provjeriti da li kategorija postoji u bazi
        //ali mozda i ne treba jer ce korisnik kliknuti na tu kategoriju znaci da ako je ponudjena onda i postoji u bazi
        return raseRepository.findByCategory_Id(id);
    }

    public Rase updateRase(Long id, RaseRequest raseRequest) {
        Rase r = getRase(id);

        try {
            Rase rr = getRaseByName(raseRequest.getName());
            throw new WrongInputException("Rase with that name already exists!");
        }catch (ResourceNotFoundException e){
            Category cr = categoryService.getCategory(raseRequest.getCategory_id());
            r.setName(raseRequest.getName());
            r.setDescription(raseRequest.getDescription());
            r.setCategory(cr);
            raseRepository.save(r);
            return r;
        }
    }
}
