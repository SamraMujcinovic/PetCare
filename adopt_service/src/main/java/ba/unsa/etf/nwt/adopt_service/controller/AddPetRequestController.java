package ba.unsa.etf.nwt.adopt_service.controller;

import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.service.AddPetRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class AddPetRequestController {
    private final AddPetRequestService addPetRequestService;

    @GetMapping("/add-pet-request")
    public List<AddPetRequest> getAddPetRequests() {
        return addPetRequestService.getAddPetRequest();
    }

    @PostMapping("/add-pet-request")
    public ResponseMessage addAddPetRequest(@Valid @RequestBody AddPetRequest addPetRequest) {
        return addPetRequestService.addAddPetRequest(addPetRequest);
    }

    @GetMapping("/add-pet-request/user/{userID}")
    public List<AddPetRequest> getAddPetRequestByUserID(@PathVariable Long userID) {
        return addPetRequestService.getAddPetRequestByUserID(userID);
    }

    @GetMapping("/add-pet-request/pet/{newPetID}")
    public List<AddPetRequest> getAddPetRequestByNewPetID(@PathVariable Long newPetID) {
        return addPetRequestService.getAddPetRequestByNewPetID(newPetID);
    }

    @GetMapping("/add-pet-request/approved")
    public List<AddPetRequest> getApprovedAddPetRequests() {
        return addPetRequestService.getApprovedAddPetRequests();
    }

    @GetMapping("/add-pet-request/not-approved")
    public List<AddPetRequest> getNotApprovedAddPetRequests() {
        return addPetRequestService.getNotApprovedAddPetRequests();
    }

    @DeleteMapping("/add-pet-request/{id}")
    public ResponseMessage deleteAddPetRequestByID(@PathVariable Long id) {
        return addPetRequestService.deleteAddPetRequestByID(id);
    }

    @DeleteMapping("/add-pet-request/user/{userID}")
    public ResponseMessage deleteAddPetRequestsByUserID(@PathVariable Long userID) {
        return addPetRequestService.deleteAddPetRequestsByUserID(userID);
    }

    @DeleteMapping("/add-pet-request/pet/{newPetID}")
    public ResponseMessage deleteAddPetRequestsByNewPetID(@PathVariable Long newPetID) {
        return addPetRequestService.deleteAddPetRequestsByNewPetID(newPetID);
    }
}
