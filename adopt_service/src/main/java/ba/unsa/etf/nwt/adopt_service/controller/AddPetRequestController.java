package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.exception.WrongInputException;
import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.request.PetForAddRequest;
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

    //ruta vjerovatno nece biti koristena
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
    public ResponseMessage addAddPetRequest(@RequestHeader("Authorization") String token,
                                            @Valid @RequestBody PetForAddRequest addPetRequest,
                                            @CurrentUser UserPrincipal currentUser) {
       return addPetRequestService.addAddPetRequest(token, addPetRequest, currentUser);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/add-pet-request")
    public ResponseMessage addAddPetRequestLocal(@Valid @RequestBody AddPetRequest addPetRequest) {
        return addPetRequestService.addAddPetRequestLocal(addPetRequest);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/add-pet-request/user/{userID}")
    public List<AddPetRequest> getAddPetRequests(@PathVariable Long userID, @CurrentUser UserPrincipal currentUser) {

        //pronalazak role trenutnog korisnika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        //svim adminima se vracaju svi requestovi
        if(hasAdminRole){
            return addPetRequestService.getAddPetRequest();
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

    //brisanje requesta (samo admini to mogu)
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/add-pet-request/{id}")
    public ResponseMessage deleteAddPetRequestByID(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        //return addPetRequestService.deleteAddPetRequestByID(id);
        return addPetRequestService.deleteAddPetRequest(token, id);
    }

    //omoguceno je useru da svoje requeste brise (SVE)
    //nije spojeno sa pet_category_service pa se nece koristiti
    /*@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
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
    }*/

    //brisanje svih requesta ukoliko je vezan za istog peta
    //vjerovatno se nece koristiti
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/add-pet-request/pet/{newPetID}")
    public ResponseMessage deleteAddPetRequestsByNewPetID(@PathVariable Long newPetID) {
        return addPetRequestService.deleteAddPetRequestsByNewPetID(newPetID);
    }

    //brisanje requesta
    /*@RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/add-pet-request/delete/{id}")
    public ResponseMessage deleteAddPetRequest(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return addPetRequestService.deleteAddPetRequest(token, id);
    }*/

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/add-pet-request/not-approved/{id}")
    public ResponseMessage setAddPetRequestNotApproved(@RequestHeader("Authorization") String token, @PathVariable Long id){
        return addPetRequestService.setNotApproved(token, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/add-pet-request/approved/{id}")
    public ResponseMessage setAddPetRequestApprove(@RequestHeader("Authorization") String token, @PathVariable Long id){
        return addPetRequestService.setApproved(token, id);
    }

    //vrati request po id-u, bitno da bi se moglo iz notifikacije doci do requesta da se kasnije approvea
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/add-pet-request/{id}")
    public AddPetRequest getAddPetRequest(@PathVariable Long id){
        return addPetRequestService.getAddPetRequestById(id);
    }
}
