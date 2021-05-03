package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.service.AdoptionRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class AdoptionRequestController {
    private final AdoptionRequestService adoptionRequestService;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/adoption-request")
    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequestService.getAdoptionRequest();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/eurekaa/adoption-request")
    public ResponseMessage addAdoptionRequest(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequest(adoptionRequest);
    }

    @PostMapping("/adoption-request")
    public ResponseMessage addAdoptionRequestLocal(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequestLocal(adoptionRequest);
    }

    @GetMapping("/adoption-request/user/{userID}")
    public List<AdoptionRequest> getAdoptionRequestByUserID(@PathVariable Long userID) {
        return adoptionRequestService.getAdoptionRequestByUserID(userID);
    }

    @GetMapping("/adoption-request/pet/{petID}")
    public List<AdoptionRequest> getAdoptionRequestByPetID(@PathVariable Long petID) {
        return adoptionRequestService.getAdoptionRequestByPetID(petID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/adoption-request/approved")
    public List<AdoptionRequest> getApprovedAdoptionRequests() {
        return adoptionRequestService.getApprovedAdoptionRequests();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/adoption-request/not-approved")
    public List<AdoptionRequest> getNotApprovedAdoptionRequests() {
        return adoptionRequestService.getNotApprovedAdoptionRequests();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/adoption-request/{id}")
    public ResponseMessage deleteAdoptionRequestByID(@PathVariable Long id) {
        return adoptionRequestService.deleteAdoptionRequestByID(id);
    }

    @DeleteMapping("/adoption-request/user/{userID}")
    public ResponseMessage deleteAdoptionRequestsByUserID(@PathVariable Long userID) {
        return adoptionRequestService.deleteAdoptionRequestsByUserID(userID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/adoption-request/pet/{petID}")
    public ResponseMessage deleteAdoptionRequestsByPetID(@PathVariable Long petID) {
        return adoptionRequestService.deleteAdoptionRequestsByPetID(petID);
    }
}
