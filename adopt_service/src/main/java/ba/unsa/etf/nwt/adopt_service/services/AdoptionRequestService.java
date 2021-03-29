package ba.unsa.etf.nwt.adopt_service.services;

import ba.unsa.etf.nwt.adopt_service.models.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.repository.AdoptionRequestRepository;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdoptionRequestService {
    private final AdoptionRequestRepository adoptionRequestRepository;

    public List<AdoptionRequest> getAdoptionRequest() {
        return adoptionRequestRepository.findAll();
    }

    public ResponseMessage addAdoptionRequest(AdoptionRequest adoptionRequest) {
        adoptionRequestRepository.save(adoptionRequest);
        return new ResponseMessage(true, HttpStatus.OK, "Request to adopt a pet with ID=" + adoptionRequest.getPetID() + " added successfully!");

    }

    public List<AdoptionRequest> getAdoptionRequestByUserID(Long userID) {
        return adoptionRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID))
                .collect(Collectors.toList());
    }

    public List<AdoptionRequest> getAdoptionRequestByPetID(Long petID) {
        return adoptionRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.getPetID().equals(petID))
                .collect(Collectors.toList());
    }

    public List<AdoptionRequest> getApprovedAdoptionRequests() {
        return adoptionRequestRepository
                .findAll()
                .stream()
                .filter(n -> n.isApproved())
                .collect(Collectors.toList());
    }

    public List<AdoptionRequest> getNotApprovedAdoptionRequests() {
        return adoptionRequestRepository
                .findAll()
                .stream()
                .filter(n -> (!n.isApproved()))
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteAdoptionRequestByID(Long id) {
        try {
            AdoptionRequest adoptionRequest = adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getId().equals(id))
                    .collect(Collectors.toList()).get(0);
            adoptionRequestRepository.delete(adoptionRequest);
            if (adoptionRequestRepository.findById(id) != null)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with id=" + id + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There is no adoption request with id=" + id + "!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        }
    }

    public ResponseMessage deleteAdoptionRequestsByUserID(Long userID) {
        try {
            List<AdoptionRequest> adoptionRequestListUser = adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getUserID().equals(userID))
                    .collect(Collectors.toList());
            for (AdoptionRequest adoptionRequest : adoptionRequestListUser) {
                adoptionRequestRepository.delete(adoptionRequest);
            }
            if (adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getUserID().equals(userID))
                    .collect(Collectors.toList()).get(0) != null)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with user id=" + userID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no adoption requests with user id=" + userID + "!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        }
    }

    public ResponseMessage deleteAdoptionRequestsByPetID(Long petID) {
        try {
            List<AdoptionRequest> adoptionRequestListPet = adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getPetID().equals(petID))
                    .collect(Collectors.toList());
            for (AdoptionRequest adoptionRequest : adoptionRequestListPet) {
                adoptionRequestRepository.delete(adoptionRequest);
            }
            if (adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getPetID().equals(petID))
                    .collect(Collectors.toList()).get(0) != null)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with pet id=" + petID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no adoption requests with pet id=" + petID + "!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        }
    }
}
