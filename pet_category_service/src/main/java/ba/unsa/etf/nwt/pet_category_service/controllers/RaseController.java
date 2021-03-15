package ba.unsa.etf.nwt.pet_category_service.controllers;

import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.services.RaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class RaseController {

    private final RaseService raseService;

    @GetMapping("/rases")
    public List<Rase> getRases(){
        return raseService.getRases();
    }

    @PostMapping("/rase")
    public Rase addRase(@RequestBody Rase rase){
        return raseService.addRase(rase);
    }


}
