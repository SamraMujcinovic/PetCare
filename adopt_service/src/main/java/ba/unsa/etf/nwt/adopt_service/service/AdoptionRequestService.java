package ba.unsa.etf.nwt.adopt_service.service;

import ba.unsa.etf.nwt.adopt_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.adopt_service.exception.WrongInputException;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.repository.AdoptionRequestRepository;
import ba.unsa.etf.nwt.adopt_service.response.ErrorResponse;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdoptionRequestService {
    private final AdoptionRequestRepository adoptionRequestRepository;
    private final CommunicationsService communicationsService;

    public List<AdoptionRequest> getAdoptionRequest() {
        return adoptionRequestRepository.findAll();
    }

    public ResponseMessage addAdoptionRequest(AdoptionRequest adoptionRequest) {

            RestTemplate restTemplate = new RestTemplate();
            try {
                //ovo bi trebalo baciti izuzetak ako nema usera
                Long userID = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
                adoptionRequest.setUserID(userID);
            } catch (Exception e) {
                if(e.getMessage().equals("URI is not absolute")) {
                    throw new ResourceNotFoundException("Can't connect to user_service!");
                }
                throw new ResourceNotFoundException("No user with ID " + adoptionRequest.getUserID());
            }

            try {
                //i ovo bi trebalo baciti izuzetak ako nema peta
                Long petID = restTemplate.getForObject(communicationsService.getUri("pet_category_service") + "/current/pet/petID/" + adoptionRequest.getPetID(), Long.class);
                adoptionRequest.setPetID(petID);


                adoptionRequestRepository.save(adoptionRequest);
            }
            catch (Exception e){
                //ako se ne moze spojiti na pet service
                if(e.getMessage().equals("URI is not absolute")) {
                    throw new ResourceNotFoundException("Can't connect to pet_category_service!!");
                }
                //u suprotnom znaci da nije pronadjen pet sa tim id-em
                throw new ResourceNotFoundException("No pet with ID " + adoptionRequest.getPetID());
            }

        return new ResponseMessage(true, HttpStatus.OK, "Request to adopt a pet with ID=" + adoptionRequest.getPetID() + " added successfully!");

    }

    public ResponseMessage addAdoptionRequestLocal(AdoptionRequest adoptionRequest) {
        AdoptionRequest novi = new AdoptionRequest();
        novi.setUserID(adoptionRequest.getUserID());
        novi.setPetID(adoptionRequest.getPetID());
        novi.setMessage(adoptionRequest.getMessage());
        adoptionRequestRepository.save(novi);
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
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There is no adoption request with id=" + id + "!");
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
                    .collect(Collectors.toList()).size() == 0)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with user id=" + userID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no adoption requests with user id=" + userID + "!");
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
                    .collect(Collectors.toList()).size() == 0)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with pet id=" + petID + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There are no adoption requests with pet id=" + petID + "!");
        }
    }


}
