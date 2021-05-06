package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.exception.WrongInputException;
import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.request.PetForAdoptRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.security.CurrentUser;
import ba.unsa.etf.nwt.adopt_service.security.UserPrincipal;
import ba.unsa.etf.nwt.adopt_service.service.AddPetRequestService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class AddPetRequestController {
    private final AddPetRequestService addPetRequestService;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/add-pet-request")
    public List<AddPetRequest> getAddPetRequests() {
        return addPetRequestService.getAddPetRequest();
    }

    /* @PostMapping("/add-pet-request")
    public ResponseMessage addAddPetRequest(@Valid @RequestBody AddPetRequest addPetRequest) {
        return addPetRequestService.addAddPetRequest(addPetRequest);
    }*/

    //izmijenjeni oblik post metode
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/eurekaa/add-pet-request")
    public ResponseMessage addAddPetRequest(@Valid @RequestBody PetForAdoptRequest addPetRequest, @CurrentUser UserPrincipal currentUser) {
       return addPetRequestService.addAddPetRequest(addPetRequest, currentUser);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/add-pet-request")
    public ResponseMessage addAddPetRequestLocal(@Valid @RequestBody AddPetRequest addPetRequest) {
        return addPetRequestService.addAddPetRequestLocal(addPetRequest);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/add-pet-request/user/{userID}")
    public List<AddPetRequest> getAddPetRequestByUserID(@PathVariable Long userID, @CurrentUser UserPrincipal currentUser) {

        //pronalazak role trenutnog korisnika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        //svi admini i samo useri ciji je request imaju pravo pronalaska istog
        if(hasAdminRole){
            return addPetRequestService.getAddPetRequestByUserID(userID);
        }

        //korisnici mogu pregledati samo vlastite requeste
        if (!currentUser.getId().equals(userID)) {
            throw new WrongInputException("Current user is not admin and this request doesn't belong to current user!");
        }

        return addPetRequestService.getAddPetRequestByUserID(userID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/add-pet-request/pet/{newPetID}")
    public List<AddPetRequest> getAddPetRequestByNewPetID(@PathVariable Long newPetID) {
        return addPetRequestService.getAddPetRequestByNewPetID(newPetID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/add-pet-request/approved")
    public List<AddPetRequest> getApprovedAddPetRequests() {
        return addPetRequestService.getApprovedAddPetRequests();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/add-pet-request/not-approved")
    public List<AddPetRequest> getNotApprovedAddPetRequests() {
        return addPetRequestService.getNotApprovedAddPetRequests();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/add-pet-request/{id}")
    public ResponseMessage deleteAddPetRequestByID(@PathVariable Long id) {
        return addPetRequestService.deleteAddPetRequestByID(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/add-pet-request/user/{userID}")
    public ResponseMessage deleteAddPetRequestsByUserID(@PathVariable Long userID, @CurrentUser UserPrincipal currentUser) {

        //pronalazak role trenutnog korisnika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        //svi admini i samo useri ciji je request imaju pravo brisanja istog
        if(hasAdminRole){
            return addPetRequestService.deleteAddPetRequestsByUserID(userID);
        }

        //korisnici mogu obrisati samo vlastite requeste
        if (!currentUser.getId().equals(userID)) {
            throw new WrongInputException("Current user is not admin and this request doesn't belong to current user!");
        }

        return addPetRequestService.deleteAddPetRequestsByUserID(userID);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/add-pet-request/pet/{newPetID}")
    public ResponseMessage deleteAddPetRequestsByNewPetID(@PathVariable Long newPetID) {
        return addPetRequestService.deleteAddPetRequestsByNewPetID(newPetID);
    }
}
