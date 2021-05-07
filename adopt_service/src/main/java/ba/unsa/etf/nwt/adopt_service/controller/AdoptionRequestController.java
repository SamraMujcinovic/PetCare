package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.exception.WrongInputException;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.security.CurrentUser;
import ba.unsa.etf.nwt.adopt_service.security.UserPrincipal;
import ba.unsa.etf.nwt.adopt_service.service.AdoptionRequestService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseMessage addAdoptionRequest(@Valid @RequestBody AdoptionRequest adoptionRequest, @CurrentUser UserPrincipal currentUser) {
        return adoptionRequestService.addAdoptionRequest(adoptionRequest, currentUser);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/adoption-request")
    public ResponseMessage addAdoptionRequestLocal(@Valid @RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.addAdoptionRequestLocal(adoptionRequest);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/adoption-request/user/{userID}")
    public List<AdoptionRequest> getAdoptionRequestByUserID(@PathVariable Long userID, @CurrentUser UserPrincipal currentUser) {

        //pronalazak role trenutnog korisnika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        //svi admini i samo useri ciji je request imaju pravo pregleda istog
        if(hasAdminRole){
            return adoptionRequestService.getAdoptionRequestByUserID(userID);
        }

        //korisnici mogu obrisati samo vlastite requeste
        if (!currentUser.getId().equals(userID)) {
            throw new WrongInputException("Current user is not admin and this request doesn't belong to current user!");
        }

        return adoptionRequestService.getAdoptionRequestByUserID(userID);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
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

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/adoption-request/user/{userID}")
    public ResponseMessage deleteAdoptionRequestsByUserID(@PathVariable Long userID, @CurrentUser UserPrincipal currentUser) {

        //pronalazak role trenutnog korisnika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        //svi admini i samo useri ciji je request imaju pravo brisanja istog
        if(hasAdminRole){
            return adoptionRequestService.deleteAdoptionRequestsByUserID(userID);
        }

        //korisnici mogu obrisati samo vlastite requeste
        if (!currentUser.getId().equals(userID)) {
            throw new WrongInputException("Current user is not admin and this request doesn't belong to current user!");
        }

        return adoptionRequestService.deleteAdoptionRequestsByUserID(userID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/adoption-request/pet/{petID}")
    public ResponseMessage deleteAdoptionRequestsByPetID(@PathVariable Long petID) {
        return adoptionRequestService.deleteAdoptionRequestsByPetID(petID);
    }
}
