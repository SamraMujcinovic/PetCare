package ba.unsa.etf.nwt.adopt_service.service;

import ba.unsa.etf.nwt.adopt_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.adopt_service.exception.WrongInputException;
import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.repository.AddPetRequestRepository;
import ba.unsa.etf.nwt.adopt_service.request.PetForAdoptRequest;
import ba.unsa.etf.nwt.adopt_service.response.ErrorResponse;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import com.google.inject.internal.ErrorsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AddPetRequestService {
    private final AddPetRequestRepository addPetRequestRepository;

    public List<AddPetRequest> getAddPetRequest() {
        return addPetRequestRepository.findAll();
    }

    public ResponseMessage addAddPetRequest(PetForAdoptRequest addPetRequest) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            AddPetRequest newRequest = new AddPetRequest();

            //prvo provjerimo usera
            Long userID = restTemplate.getForObject("http://localhost:8080/user/me/id", Long.class);
            newRequest.setUserID(userID);

            //sada prvo moramo dodati poslani pet u bazu preko rute POST u pet servisu
            Long petID = restTemplate.postForObject("http://localhost:8084/petID/forAdopt", addPetRequest.getPetForAdopt(), Long.class);
            newRequest.setNewPetID(petID);

            newRequest.setMessage(addPetRequest.getMessage());

            addPetRequestRepository.save(newRequest);
        }catch (RuntimeException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        return new ResponseMessage(true, HttpStatus.OK, "Request to add a new pet added successfully!");
    }

    public List<AddPetRequest> getAddPetRequestByUserID(Long userID) {
        return addPetRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID))
                .collect(Collectors.toList());
    }

    public List<AddPetRequest> getAddPetRequestByNewPetID(Long newPetID) {
        return addPetRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.getNewPetID().equals(newPetID))
                .collect(Collectors.toList());
    }

    public List<AddPetRequest> getApprovedAddPetRequests() {
        return addPetRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.isApproved())
                .collect(Collectors.toList());
    }

    public List<AddPetRequest> getNotApprovedAddPetRequests() {
        return addPetRequestRepository
                .findAll()
                .stream()
                .filter(n -> !n.isApproved())
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteAddPetRequestByID(Long id) {
        try {
            AddPetRequest addPetRequest = addPetRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getId().equals(id))
                    .collect(Collectors.toList()).get(0);
            addPetRequestRepository.delete(addPetRequest);
            if (addPetRequestRepository.findById(id) != null)
                return new ResponseMessage(true, HttpStatus.OK, "Request to add a pet with id=" + id + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There is no request to add a pet with id=" + id + "!");
        }
    }

    public ResponseMessage deleteAddPetRequestsByUserID(Long userID) {
        try {
            List<AddPetRequest> addPetRequestListUser = addPetRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getUserID().equals(userID))
                    .collect(Collectors.toList());
            for (AddPetRequest addPetRequest : addPetRequestListUser) {
                addPetRequestRepository.delete(addPetRequest);
            }
            if (addPetRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getUserID().equals(userID))
                    .collect(Collectors.toList()).size() == 0)
                return new ResponseMessage(true, HttpStatus.OK, "Requests to add a pet with user id=" + userID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no requests to add a pet with user id=" + userID + "!");
        }
    }

    public ResponseMessage deleteAddPetRequestsByNewPetID(Long newPetID) {
        try {
            List<AddPetRequest> addPetRequestListPet = addPetRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getNewPetID().equals(newPetID))
                    .collect(Collectors.toList());
            for (AddPetRequest addPetRequest : addPetRequestListPet) {
                addPetRequestRepository.delete(addPetRequest);
            }
            if (addPetRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getNewPetID().equals(newPetID))
                    .collect(Collectors.toList()).size() == 0)
                return new ResponseMessage(true, HttpStatus.OK, "Request to add a pet with pet id=" + newPetID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no requests to add a pet with pet id=" + newPetID + "!");
        }
    }
}
