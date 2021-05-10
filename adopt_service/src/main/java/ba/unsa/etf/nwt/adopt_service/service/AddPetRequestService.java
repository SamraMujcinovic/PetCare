package ba.unsa.etf.nwt.adopt_service.service;

import ba.unsa.etf.nwt.adopt_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.adopt_service.model.AddPetRequest;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.repository.AddPetRequestRepository;
import ba.unsa.etf.nwt.adopt_service.request.PetForAddRequest;
import ba.unsa.etf.nwt.adopt_service.request.PetRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.security.CurrentUser;
import ba.unsa.etf.nwt.adopt_service.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AddPetRequestService {
    private final AddPetRequestRepository addPetRequestRepository;
    private final CommunicationsService communicationsService;

    public List<AddPetRequest> getAddPetRequest() {
        return addPetRequestRepository.findAll();
    }

    public ResponseMessage addAddPetRequest(String token, PetForAddRequest addPetRequest, @CurrentUser UserPrincipal currentUser) {

            RestTemplate restTemplate = new RestTemplate();

            AddPetRequest newRequest = new AddPetRequest();

            //try {
                //prvo provjerimo usera
                /*Long userID = restTemplate.getForObject(communicationsService.getUri("user_service")
                        + "/user/me/id", Long.class);*/
                //newRequest.setUserID(userID);
            /*}
            catch (Exception e){
                throw new ResourceNotFoundException("Current user not found!!");
            }*/

            newRequest.setUserID(currentUser.getId());

            try {
                //sada prvo moramo dodati poslani pet u bazu preko rute POST u pet servisu
                /*Long petID = restTemplate.postForObject(communicationsService.getUri("pet_category_service")
                        + "/petID/forAdopt", addPetRequest.getPetForAdopt(), Long.class);
                newRequest.setNewPetID(petID);*/

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);

                HttpEntity<PetRequest> entityReq = new HttpEntity<PetRequest>(addPetRequest.getPetForAdopt(), headers);

                ResponseEntity<Long> petID = restTemplate.exchange(communicationsService.getUri("pet_category_service")
                                + "/petID/forAdopt", HttpMethod.POST, entityReq, Long.class);

                newRequest.setNewPetID(petID.getBody());
                newRequest.setMessage(addPetRequest.getMessage());
                addPetRequestRepository.save(newRequest);

                //dodavanje notifikacije adminu da je dodan request za novi pet

                try {

                    HttpHeaders headers2 = new HttpHeaders();
                    headers2.set("Authorization", token);

                    HttpEntity<String> entityReq2 = new HttpEntity<>("", headers2);

                    ResponseEntity<ResponseMessage> responseMessage2 = restTemplate.exchange(communicationsService.getUri("notification_service")
                                    + "/notifications/add/add-pet-request/" + newRequest.getUserID() + "/" + newRequest.getId(),
                            HttpMethod.GET ,entityReq2, ResponseMessage.class);

                    System.out.println(responseMessage2.getBody().getMessage());
                } catch (Exception ue){
                    System.out.println("Can't connect to notification_service!");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
                //ako se ne moze spojiti na pet service
                if(e.getMessage().equals("URI is not absolute")) {//ova message se vrati ako nije podignut neki servis
                    throw new ResourceNotFoundException("Can't connect to pet_category_service!!");
                }
                //kad se tamo hendla onaj exception vratit ce nesto bla bla
                //i onda bih trebala provjeriti jel wrong input ili resource not found
                if(e.getMessage().contains("rase")){//ako zadana rasa ne postoji u bazi
                    throw new ResourceNotFoundException("No rase with id " + addPetRequest.getPetForAdopt().getRase_id());
                }
                //ako nije dodalo novog peta uopce
                throw new ResourceNotFoundException("Pet is not added!");

            }

        return new ResponseMessage(true, HttpStatus.OK, "Request to add a new pet added successfully!");
    }

    public ResponseMessage addAddPetRequestLocal(AddPetRequest addPetRequest) {
        AddPetRequest novi = new AddPetRequest();
        novi.setNewPetID(addPetRequest.getNewPetID());
        novi.setUserID(addPetRequest.getUserID());
        novi.setMessage(addPetRequest.getMessage());
        addPetRequestRepository.save(novi);
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

    public ResponseMessage deleteAddPetRequest(String token, Long id){

        AddPetRequest addPetRequest = addPetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        RestTemplate restTemplate = new RestTemplate();

        if(!addPetRequest.isApproved()) {
            try {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("pet_category_service")
                                + "/pet?id=" + addPetRequest.getNewPetID(),
                        HttpMethod.DELETE, entityReq, ResponseMessage.class);

                System.out.println(responseMessage.getBody().getMessage());
            } catch (Exception ue) {
                throw new ResourceNotFoundException("Can't connect to pet_category_service and delete a pet!");
            }

            try {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                                + "/notifications/add/not-approved/add-pet-request/" + addPetRequest.getUserID() + "/" + addPetRequest.getId(),
                        HttpMethod.GET ,entityReq, ResponseMessage.class);

                System.out.println(responseMessage.getBody().getMessage());
            } catch (Exception ue){
                System.out.println("Can't connect to notification_service!");
            }

            //ne bi trebalo da ikad postoji ovih zahtjeva
            //ali za svaki slucaj se brisu
            communicationsService.deleteForAdopt(token, addPetRequest.getNewPetID());

        }// else {

            //adoptionRequestService.findAndDeleteAdoptionRequest(token, addPetRequest.getNewPetID());

            //communicationsService.deleteForAdopt(token, addPetRequest.getNewPetID());
        //}

        addPetRequestRepository.deleteById(id);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully deleted this request!");
    }

    public ResponseMessage setNotApproved(String token, Long id){
        AddPetRequest addPetRequest = addPetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        RestTemplate restTemplate = new RestTemplate();

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                            + "/notifications/add/not-approved/add-pet-request/" + addPetRequest.getUserID() + "/" + addPetRequest.getId(),
                    HttpMethod.GET ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            System.out.println("Can't connect to notification_service!");
        }

        addPetRequest.setApproved(false);
        addPetRequestRepository.save(addPetRequest);

        return new ResponseMessage(true, HttpStatus.OK, "You didn't approve this request!");
    }

    public ResponseMessage setApproved(String token, Long id){
        AddPetRequest addPetRequest = addPetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        //poziva se ruta iz pet servisa da se i tamo odobri..

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("pet_category_service")
                            + "/pets/approve/0/" + addPetRequest.getNewPetID(),
                    HttpMethod.PUT ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            throw new ResourceNotFoundException("Can't connect to pet_category_service and approve a pet!");
        }

        //salje se notifikacija korisniku

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                            + "/notifications/add/approved/add-pet-request/" + addPetRequest.getUserID() + "/" + addPetRequest.getId(),
                    HttpMethod.GET ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            System.out.println("Can't connect to notification_service!");
        }

        addPetRequest.setApproved(true);
        addPetRequestRepository.save(addPetRequest);

        return new ResponseMessage(true, HttpStatus.OK, "This request has been approved!");
    }

    public AddPetRequest getAddPetRequestById(Long id){
        return addPetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));
    }

    public void findAndDeleteAddPetRequest(String token, Long id){
        //try {

            List<AddPetRequest> addPetRequests =
                    addPetRequestRepository.findAllByNewPetID(id);

            //AddPetRequest addPetRequest = addPetRequestRepository.findByNewPetID(id)
              //      .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

            for(AddPetRequest addPetRequest : addPetRequests) {

                if (!addPetRequest.isApproved()) {

                    RestTemplate restTemplate = new RestTemplate();

                    try {

                        HttpHeaders headers = new HttpHeaders();
                        headers.set("Authorization", token);

                        HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                        ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                                        + "/notifications/add/not-approved/add-pet-request/" + addPetRequest.getUserID() + "/" + addPetRequest.getId(),
                                HttpMethod.GET, entityReq, ResponseMessage.class);

                        System.out.println(responseMessage.getBody().getMessage());
                    } catch (Exception ue) {
                        System.out.println("Can't connect to notification_service!");
                    }
                }

                addPetRequestRepository.deleteById(addPetRequest.getId());
            }

        /*} catch (ResourceNotFoundException e){
            //ne treba nista da se desi
            System.out.println(e.getMessage());
        }*/
    }

}
