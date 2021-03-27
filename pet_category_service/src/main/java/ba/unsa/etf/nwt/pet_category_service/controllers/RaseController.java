package ba.unsa.etf.nwt.pet_category_service.controllers;

import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.requests.RaseRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.RaseResponse;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.RaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class RaseController {

    private final RaseService raseService;

    @GetMapping("/rases")
    public List<Rase> getRases(){
        return raseService.getRases();
    }

    @GetMapping("/rases/inCategory")
    public List<Rase> getRasesInCategory(@RequestParam Long id){
        return raseService.getRasesInCategory(id);
    }

    @GetMapping("/rase")
    public RaseResponse getRase(@RequestParam Long id){
        return raseService.getRase(id);
    }

    @GetMapping("/rase/byName")
    public RaseResponse getRaseByName(String name){
        return raseService.getRaseByName(name);
    }

    @PostMapping("/rase")
    public ResponseEntity<?> addRase( @RequestBody RaseRequest raseRequest){
       return raseService.addRase(raseRequest);
    }

    @DeleteMapping("/rase")
    public Response deleteRase(@RequestParam Long id){
        return raseService.deleteRase(id);
    }

}
