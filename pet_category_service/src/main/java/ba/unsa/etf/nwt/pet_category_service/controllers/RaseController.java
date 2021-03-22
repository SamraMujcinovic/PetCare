package ba.unsa.etf.nwt.pet_category_service.controllers;

import ba.unsa.etf.nwt.pet_category_service.models.Rase;
import ba.unsa.etf.nwt.pet_category_service.requests.RaseRequest;
import ba.unsa.etf.nwt.pet_category_service.responses.Response;
import ba.unsa.etf.nwt.pet_category_service.services.RaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/rase")
    public ResponseEntity<?> addRase( @RequestBody RaseRequest raseRequest){
        if(raseRequest.getName().equals("")) return new ResponseEntity(new Response(false, "Rase name can't be blank!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        if(raseRequest.getName().length()<2 || raseRequest.getName().length()>50) return new ResponseEntity(new Response(false, "Rase name must be between 2 and 50 characters!", "BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        Response response = raseService.addRase(raseRequest);
        if(response.getSuccess()) return new ResponseEntity(new Response(true, "Rase added successfully!", "OK"), HttpStatus.OK);
        return new ResponseEntity(new Response(response), HttpStatus.NOT_FOUND);
    }


}
