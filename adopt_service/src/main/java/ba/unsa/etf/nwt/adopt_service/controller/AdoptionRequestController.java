package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.service.AdoptionRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class AdoptionRequestController {
    private final AdoptionRequestService adoptionRequestService;

    //admin
    @GetMapping("/adoption-request")
    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequestService.getAdoptionRequest();
    }

    //admin
    @PostMapping("/eureka/adoption-request")
    public ResponseMessage addAdoptionRequest(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequest(adoptionRequest);
    }

    //zast
    @PostMapping("/adoption-request")
    public ResponseMessage addAdoptionRequestLocal(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequestLocal(adoptionRequest);
    }

    //zast
    @GetMapping("/adoption-request/user/{userID}")
    public List<AdoptionRequest> getAdoptionRequestByUserID(@PathVariable Long userID) {
        return adoptionRequestService.getAdoptionRequestByUserID(userID);
    }

    //zast
    @GetMapping("/adoption-request/pet/{petID}")
    public List<AdoptionRequest> getAdoptionRequestByPetID(@PathVariable Long petID) {
        return adoptionRequestService.getAdoptionRequestByPetID(petID);
    }

    //admin
    @GetMapping("/adoption-request/approved")
    public List<AdoptionRequest> getApprovedAdoptionRequests() {
        return adoptionRequestService.getApprovedAdoptionRequests();
    }

    //admin
    @GetMapping("/adoption-request/not-approved")
    public List<AdoptionRequest> getNotApprovedAdoptionRequests() {
        return adoptionRequestService.getNotApprovedAdoptionRequests();
    }

    //admin
    @DeleteMapping("/adoption-request/{id}")
    public ResponseMessage deleteAdoptionRequestByID(@PathVariable Long id) {
        return adoptionRequestService.deleteAdoptionRequestByID(id);
    }

    //zast
    @DeleteMapping("/adoption-request/user/{userID}")
    public ResponseMessage deleteAdoptionRequestsByUserID(@PathVariable Long userID) {
        return adoptionRequestService.deleteAdoptionRequestsByUserID(userID);
    }

    //admin
    @DeleteMapping("/adoption-request/pet/{petID}")
    public ResponseMessage deleteAdoptionRequestsByPetID(@PathVariable Long petID) {
        return adoptionRequestService.deleteAdoptionRequestsByPetID(petID);
    }
}
