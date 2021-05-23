package ba.unsa.etf.nwt.adopt_service.service;

import ba.unsa.etf.nwt.adopt_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import ba.unsa.etf.nwt.adopt_service.rabbitmq.MessagingConfig;
import ba.unsa.etf.nwt.adopt_service.rabbitmq.NotificationAdoptServiceMessage;
import ba.unsa.etf.nwt.adopt_service.repository.AdoptionRequestRepository;
import ba.unsa.etf.nwt.adopt_service.request.PetForAdoptRequest;
import ba.unsa.etf.nwt.adopt_service.response.ResponseMessage;
import ba.unsa.etf.nwt.adopt_service.security.CurrentUser;
import ba.unsa.etf.nwt.adopt_service.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdoptionRequestService {
    private final AdoptionRequestRepository adoptionRequestRepository;
    private final CommunicationsService communicationsService;

    private List<AdoptionRequest> allRequestsForDelete;

    //@Value("${adopt.isAlreadyNotApprovedAdopt: 0}")
    //private int isAlreadyNotApprovedAdopt;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<AdoptionRequest> getAdoptionRequest() {
        return adoptionRequestRepository.findAll();
    }

    public ResponseMessage addAdoptionRequest(String token, Long id, PetForAdoptRequest petForAdoptRequest, @CurrentUser UserPrincipal currentUser) {

        AdoptionRequest adoptionRequest = new AdoptionRequest();

        RestTemplate restTemplate = new RestTemplate();
            //try {
                //ovo bi trebalo baciti izuzetak ako nema usera
                //Long userID = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
                //adoptionRequest.setUserID(userID);
        adoptionRequest.setUserID(currentUser.getId());
            /*} catch (Exception e) {
                if(e.getMessage().equals("URI is not absolute")) {
                    throw new ResourceNotFoundException("Can't connect to user_service!");
                }
                throw new ResourceNotFoundException("No user with ID " + adoptionRequest.getUserID());
            }*/
        adoptionRequest.setPetID(id);
        adoptionRequest.setApproved(false);
        adoptionRequest.setMessage(petForAdoptRequest.getMessage());
        adoptionRequestRepository.save(adoptionRequest);

        //try {

            //posto se adopt radi iz peta, korisnik ce imati id peta i nepotrebano je pozivati pet service al eto...
            //ovaj poziv pet_category_service sluzi samo da se potvrdi da taj pet idalje postoji

            //i ovo bi trebalo baciti izuzetak ako nema peta
            /*Long petID = restTemplate.getForObject(communicationsService.getUri("pet_category_service") +
                    "/current/pet/petID/" + adoptionRequest.getPetID(), Long.class);
            adoptionRequest.setPetID(petID);

            adoptionRequestRepository.save(adoptionRequest);*/

            try {

                //sinhrono
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                        + "/notifications/add/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                        HttpMethod.GET ,entityReq, ResponseMessage.class);

                System.out.println(responseMessage.getBody().getMessage());
            } catch (Exception ue){
                throw new ResourceNotFoundException("Can't connect to notification_service!");
                //System.out.println("Can't connect to notification_service!");
            }

        //}
        //catch (Exception e){
            //ako se ne moze spojiti na pet service
          //  if(e.getMessage().equals("URI is not absolute")) {
            //        throw new ResourceNotFoundException("Can't connect to pet_category_service!!");
           // }
            //u suprotnom znaci da nije pronadjen pet sa tim id-em
            //throw new ResourceNotFoundException("No pet with ID " + adoptionRequest.getPetID());
        //}

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

    public ResponseMessage deleteAdoptionRequestByID(String token, Long id) {
        try {
            AdoptionRequest adoptionRequest = adoptionRequestRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getId().equals(id))
                    .collect(Collectors.toList()).get(0);

            RestTemplate restTemplate = new RestTemplate();

            if(adoptionRequest.isApproved()) {
                try {

                    //sinhrono
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", token);

                    HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                    ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("pet_category_service")
                                    + "/pet?id=" + adoptionRequest.getPetID(),
                            HttpMethod.DELETE, entityReq, ResponseMessage.class);

                    System.out.println(responseMessage.getBody().getMessage());
                } catch (Exception ue) {
                    throw new ResourceNotFoundException("Can't connect to pet_category_service and delete a pet!");
                }

                //addPetRequestService.findAndDeleteAddPetRequest(token, adoptionRequest.getPetID());

                communicationsService.deleteForAdd(token, adoptionRequest.getPetID());

            } else {

                //if(isAlreadyNotApprovedAdopt != 1) {
                    /*try {

                        HttpHeaders headers = new HttpHeaders();
                        headers.set("Authorization", token);

                        HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                        ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                                        + "/notifications/add/not-approved/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                                HttpMethod.GET ,entityReq, ResponseMessage.class);

                        System.out.println(responseMessage.getBody().getMessage());
                    } catch (Exception ue){
                        System.out.println("Can't connect to notification_service!");
                    }*/

                    //send message to notification_service
                    NotificationAdoptServiceMessage notificationAdoptServiceMessage = new NotificationAdoptServiceMessage(adoptionRequest.getUserID(),
                            adoptionRequest.getId(), false, false);
                    rabbitTemplate.convertAndSend(MessagingConfig.ADOPT_NOTIFICATION_SERVICE_EXCHANGE,
                            MessagingConfig.ADOPT_NOTIFICATION_SERVICE_ROUTING_KEY, notificationAdoptServiceMessage);
                //}

                //adoptionRequestRepository.delete(adoptionRequest);
            }

            adoptionRequestRepository.delete(adoptionRequest);

            if (adoptionRequestRepository.findById(id) != null)
                return new ResponseMessage(true, HttpStatus.OK, "Adoption request with id=" + id + " deleted successfully!");
            return new ResponseMessage(false, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server or database error!");
        } catch (Exception e) {
            return new ResponseMessage(false, HttpStatus.NOT_FOUND, "There is no adoption request with id=" + id + "!");
        }
    }

    /*public ResponseMessage deleteAdoptionRequestsByUserID(Long userID) {
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
    }*/

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

    public ResponseMessage setNotApproved(String token, Long id){
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        /*RestTemplate restTemplate = new RestTemplate();

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                            + "/notifications/add/not-approved/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                    HttpMethod.GET ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            System.out.println("Can't connect to notification_service!");
        }*/

        //send message to notification_service
        NotificationAdoptServiceMessage notificationAdoptServiceMessage = new NotificationAdoptServiceMessage(adoptionRequest.getUserID(),
                adoptionRequest.getId(), false, false);
        rabbitTemplate.convertAndSend(MessagingConfig.ADOPT_NOTIFICATION_SERVICE_EXCHANGE,
                MessagingConfig.ADOPT_NOTIFICATION_SERVICE_ROUTING_KEY, notificationAdoptServiceMessage);

        //isAlreadyNotApprovedAdopt = 1;

        adoptionRequest.setApproved(false);
        adoptionRequestRepository.save(adoptionRequest);

        return new ResponseMessage(true, HttpStatus.OK, "You didn't approve this request!");
    }

    public ResponseMessage setApproved(String token, Long id){
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        //poziva se ruta iz pet servisa da se i tamo odobri..

        RestTemplate restTemplate = new RestTemplate();
        try {

            //sinhrono
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            //brise se pet na pet servisu jer je adoptan
            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("pet_category_service")
                            + "/pets/approve/1/" + adoptionRequest.getPetID(),
                    HttpMethod.PUT ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            throw new ResourceNotFoundException("Can't connect to pet_category_service and approve a pet!");
        }

        //salje se notifikacija korisniku

        /*try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entityReq = new HttpEntity<>("", headers);

            ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                            + "/notifications/add/approved/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                    HttpMethod.GET ,entityReq, ResponseMessage.class);

            System.out.println(responseMessage.getBody().getMessage());
        } catch (Exception ue){
            System.out.println("Can't connect to notification_service!");
        }*/

        //send message to notification_service
        NotificationAdoptServiceMessage notificationAdoptServiceMessage = new NotificationAdoptServiceMessage(adoptionRequest.getUserID(),
                adoptionRequest.getId(), true, false);
        rabbitTemplate.convertAndSend(MessagingConfig.ADOPT_NOTIFICATION_SERVICE_EXCHANGE,
                MessagingConfig.ADOPT_NOTIFICATION_SERVICE_ROUTING_KEY, notificationAdoptServiceMessage);


        adoptionRequest.setApproved(true);
        adoptionRequestRepository.save(adoptionRequest);

        //ResponseMessage responseMessage = deleteAdoptionRequestsByPetID(adoptionRequest.getPetID());

        //adoptionRequestRepository.deleteAllByApprovedAndPetID(false, adoptionRequest.getPetID());

        //kada se odobri neki request za nekog peta, svi ostali zahtjevi za taj petID se brisu
        deleteOtherRequests(token, adoptionRequest.getPetID());

        return new ResponseMessage(true, HttpStatus.OK, "This request has been approved!");
    }

    public AdoptionRequest getAdoptionRequestbyId(Long id){
        return adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));
    }

    public void deleteOtherRequests(String token, Long petID){

        List<AdoptionRequest> adoptionRequests =
                adoptionRequestRepository.findAllByApprovedAndPetID(false, petID);

        for(AdoptionRequest adoptionRequest : adoptionRequests){
            RestTemplate restTemplate = new RestTemplate();

            /*try {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                                + "/notifications/add/not-approved/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                        HttpMethod.GET ,entityReq, ResponseMessage.class);

                System.out.println(responseMessage.getBody().getMessage());
            } catch (Exception ue){
                System.out.println("Can't connect to notification_service!");
            }*/

            //send message to notification_service
            NotificationAdoptServiceMessage notificationAdoptServiceMessage = new NotificationAdoptServiceMessage(adoptionRequest.getUserID(),
                    adoptionRequest.getId(), false, false);
            rabbitTemplate.convertAndSend(MessagingConfig.ADOPT_NOTIFICATION_SERVICE_EXCHANGE,
                    MessagingConfig.ADOPT_NOTIFICATION_SERVICE_ROUTING_KEY, notificationAdoptServiceMessage);


            adoptionRequestRepository.deleteById(adoptionRequest.getId());
        }

    }

    public void findAndDeleteAdoptionRequest(Long id){
        //try {

            List<AdoptionRequest> adoptionRequests =
                    adoptionRequestRepository.findAllByPetID(id);

            //AdoptionRequest adoptionRequest = adoptionRequestRepository.findByPetID(id)
              //      .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

            RestTemplate restTemplate = new RestTemplate();

            for(AdoptionRequest adoptionRequest : adoptionRequests) {

                if (!adoptionRequest.isApproved()) {
                    /*try {

                        HttpHeaders headers = new HttpHeaders();
                        headers.set("Authorization", token);

                        HttpEntity<String> entityReq = new HttpEntity<>("", headers);

                        ResponseEntity<ResponseMessage> responseMessage = restTemplate.exchange(communicationsService.getUri("notification_service")
                                        + "/notifications/add/not-approved/adopt-request/" + adoptionRequest.getUserID() + "/" + adoptionRequest.getId(),
                                HttpMethod.GET, entityReq, ResponseMessage.class);

                        System.out.println(responseMessage.getBody().getMessage());
                    } catch (Exception ue) {
                        System.out.println("Can't connect to notification_service!");
                    }*/

                    //send message to notification_service
                    NotificationAdoptServiceMessage notificationAdoptServiceMessage = new NotificationAdoptServiceMessage(adoptionRequest.getUserID(),
                            adoptionRequest.getId(), false, false);
                    rabbitTemplate.convertAndSend(MessagingConfig.ADOPT_NOTIFICATION_SERVICE_EXCHANGE,
                            MessagingConfig.ADOPT_NOTIFICATION_SERVICE_ROUTING_KEY, notificationAdoptServiceMessage);

                }

                //dodavanje u listu u slucaju da dodje do greske pa da se mogu vratiti...
                allRequestsForDelete.add(adoptionRequest);
                adoptionRequestRepository.deleteById(adoptionRequest.getId());
            }

        //} catch (ResourceNotFoundException e){
            //ne treba nista da se desi
          //  System.out.println(e.getMessage());
        //}
    }

    public void addBackAllDeletedRequests(){
        for(AdoptionRequest adoptionRequest : allRequestsForDelete){
            adoptionRequestRepository.save(adoptionRequest);
        }
    }

}
