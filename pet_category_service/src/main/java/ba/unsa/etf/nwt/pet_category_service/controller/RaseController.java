package ba.unsa.etf.nwt.pet_category_service.controller;

import ba.unsa.etf.nwt.pet_category_service.model.Rase;
import ba.unsa.etf.nwt.pet_category_service.request.RaseRequest;
import ba.unsa.etf.nwt.pet_category_service.response.ResponseMessage;
import ba.unsa.etf.nwt.pet_category_service.service.RaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public List<Rase> getRasesInCategory(@NotNull @RequestParam Long id){
        return raseService.getRasesInCategory(id);
    }

    @GetMapping("/rase/{id}")
    public Rase getRase(@NotNull @PathVariable Long id){
        return raseService.getRase(id);
    }

    @GetMapping("/rase/byName")
    public Rase getRaseByName(@NotNull @RequestParam String name){
        return raseService.getRaseByName(name);
    }

    @GetMapping("/current/rase/raseID/{id}")
    public Long getCurrentRaseID(@NotNull @PathVariable Long id){
        Rase rase = raseService.getRaseById(id);
        return rase.getId();
    }

    @PostMapping("/rase")
    public ResponseMessage addRase(@Valid @RequestBody RaseRequest raseRequest){
       return raseService.addRase(raseRequest);
    }

    @DeleteMapping("/rase")
    public ResponseMessage deleteRase(@NotNull @RequestParam Long id){
        return raseService.deleteRase(id);
    }

    @PutMapping("/rase/update/{id}")
    public Rase updateRase(@NotNull @PathVariable Long id, @Valid @RequestBody RaseRequest raseRequest){
        return raseService.updateRase(id, raseRequest);
    }

}
