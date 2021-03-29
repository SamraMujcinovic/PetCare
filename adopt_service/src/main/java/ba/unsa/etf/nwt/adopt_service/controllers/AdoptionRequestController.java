package ba.unsa.etf.nwt.adopt_service.controllers;

import ba.unsa.etf.nwt.adopt_service.models.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.services.AdoptionRequestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RefreshScope
public class AdoptionRequestController {
    private final AdoptionRequestService adoptionRequestService;

    @Value("${my.variable: default value}")
    private String variable;

    @GetMapping("/configserver/test")
    public String getVariable() {
        return "RETURNED VARIABLE: " + variable;
    }


    @GetMapping("/adoption-request")
    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequestService.getAdoptionRequest();
    }

    @PostMapping("/adoption-request")
    public ResponseMessage addAdoptionRequest(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequest(adoptionRequest);
    }

    @GetMapping("/adoption-request/{userID}")
    public List<AdoptionRequest> getAdoptionRequestByUserID(@PathVariable Long userID) {
        return adoptionRequestService.getAdoptionRequestByUserID(userID);
    }

    @GetMapping("/adoption-request/{petID}")
    public List<AdoptionRequest> getAdoptionRequestByPetID(@PathVariable Long petID) {
        return adoptionRequestService.getAdoptionRequestByPetID(petID);
    }

    @GetMapping("/adoption-request/approved")
    public List<AdoptionRequest> getApprovedAdoptionRequests() {
        return adoptionRequestService.getApprovedAdoptionRequests();
    }

    @GetMapping("/adoption-request/not-approved")
    public List<AdoptionRequest> getNotApprovedAdoptionRequests() {
        return adoptionRequestService.getNotApprovedAdoptionRequests();
    }

    @DeleteMapping("/adoption-request/{id}")
    public ResponseMessage deleteAdoptionRequestByID(@PathVariable Long id) {
        return adoptionRequestService.deleteAdoptionRequestByID(id);
    }

    @DeleteMapping("/adoption-request/{userID}")
    public ResponseMessage deleteAdoptionRequestsByUserID(@PathVariable Long userID) {
        return adoptionRequestService.deleteAdoptionRequestsByUserID(userID);
    }

    @DeleteMapping("/adoption-request/{petID}")
    public ResponseMessage deleteAdoptionRequestsByPetID(@PathVariable Long petID) {
        return adoptionRequestService.deleteAdoptionRequestsByPetID(petID);
    }
}
