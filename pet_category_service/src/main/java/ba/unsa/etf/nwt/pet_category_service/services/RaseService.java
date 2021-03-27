package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.exceptions.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import ba.unsa.etf.nwt.pet_category_service.repository.RaseRepository;
import ba.unsa.etf.nwt.pet_category_service.requests.RaseRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.CategoryResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.RaseResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RaseService {

    private final RaseRepository raseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public List<Rase> getRases() {
        return raseRepository.findAll();
    }

    public ResponseEntity<Response> addRase(RaseRequest raseRequest) {
        if(raseRequest.getName().equals("")) return new ResponseEntity(new Response(false, "Rase name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(raseRequest.getName().length()<2 || raseRequest.getName().length()>50) return new ResponseEntity(new Response(false, "Rase name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        Rase r = findRaseByName(raseRequest.getName());
        if(r != null) return new ResponseEntity(new Response(false, "Rase with that name already exists!", "BAD REQUEST"), HttpStatus.BAD_REQUEST);

        Rase rase = new Rase();
        rase.setName(raseRequest.getName());
        rase.setDescription(raseRequest.getDescription());
        try{
            Category category = categoryService.getCategoryById(raseRequest.getCategory_id());
            rase.setCategory(category);
            raseRepository.save(rase);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity(new Response(false, "There is no rase with that ID!!", "NOT_FOUND"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(new Response(true, "Rase successfully added!!", "OK"), HttpStatus.OK);

    }

    public Rase saveRase(Rase rase){
        return raseRepository.save(rase);
    }



    public RaseResponse getRase(Long id) {
        try{
            Rase r = getRaseById(id);
            return new RaseResponse(r, "Rase OK!", "OK", true);
        }catch (ResourceNotFoundException e){
            return new RaseResponse(null, "Rase with that ID not found", "NOT FOUND", false);
        }
    }

    public Rase getRaseById(Long id) {
        return raseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with ID " + id));
    }

    public Response deleteRase(Long id) {
        try{
            Rase r = getRaseById(id);
             //ako ne bude brisalo sve petove koji imaju ovu rasu
            //moram dodati filtriranje petova i rasa
            //i onda pozvati tu metodu i ona mi vrati sve one koji imaju tu rasu ili kategoriju i njih brisem iz baze
            raseRepository.deleteById(id);
            return new Response(true, "Rase successfully deleted", "OK");
        }catch (ResourceNotFoundException e){
            return  new Response(false, "Rase with that ID not found!", "NOT FOUND");
        }
    }

    public Rase findRaseByName(String name) {
        return raseRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No rase with name " + name));
    }

    public RaseResponse getRaseByName(String name) {
        if(name == null) return new RaseResponse(null, "Add a name for search!", "BAD_REQUEST", false);
        try {
            Rase r = findRaseByName(name);
            return new RaseResponse(r, "Rase found!", "OK", true);

        }catch (ResourceNotFoundException e){
            return new RaseResponse(null, "Rase with that name not found!", "NOT FOUND", false);

        }
    }

    public List<Rase> getRasesInCategory(Long id) {

        //trebalo bi provjeriti da li kategorija postoji u bazi
        //ali mozda i ne treba jer ce korisnik kliknuti na tu kategoriju znaci da ako je ponudjena onda i postoji u bazi
        return raseRepository.findByCategory_Id(id);
    }
}
