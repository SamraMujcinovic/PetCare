package ba.unsa.etf.nwt.notification_service.service;

import ba.unsa.etf.nwt.notification_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.notification_service.exception.WrongInputException;
import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.security.CurrentUser;
import ba.unsa.etf.nwt.notification_service.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CommunicationsService communicationsService;

    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }


    //todo poziva se iz adopt_service asinhrono...
    //kada se posalje zahtjev za add ili adopt dodaje se i notifikacija
    public ResponseMessage addNotification(Notification notification, @CurrentUser UserPrincipal currentUser) {
        notification.setCreatedAt(new Date());
        //try {
            RestTemplate restTemplate = new RestTemplate();
            //Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
            //notification.setUserID(userId);
            notification.setUserID(currentUser.getId());
            notificationRepository.save(notification);
            return new ResponseMessage(true, HttpStatus.OK,"Notification added successfully!!");
        /*}
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
        }*/
    }

    public Notification findById(Long notificationID) {
        Notification notification = notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getId().equals(notificationID))
                .collect(Collectors.toList()).get(0);
        notification.setRead(true);
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getUserNotification(@CurrentUser UserPrincipal currentUser) {
        //try {
            /*RestTemplate restTemplate = new RestTemplate();
            Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") +
                    "/user/me/id", Long.class);*/
            Long userId = currentUser.getId();
            try {
                List<Notification> notifications = notificationRepository.findAllByUserID(userId);
                return notifications;
            }
            catch (Exception ex){
                List<Notification> notifications = new ArrayList<>();
                return notifications;
            }
        /*}
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
        }*/
    }

    public List<Notification> getUnreadUserNotification(@CurrentUser UserPrincipal currentUser) {
        try {
            /*RestTemplate restTemplate = new RestTemplate();
            Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);*/

            Long userId = currentUser.getId();

            try {
                List<Notification> notifications = notificationRepository.findAllByUserIDAndRead(userId, false);
                return notifications;
            }
            catch (Exception e){
                List<Notification> notifications = new ArrayList<>();
                return notifications;
            }
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
        }
    }

    public ResponseMessage deleteNotification(Long notificationID) {
        try {
            Notification notification = notificationRepository.findById(notificationID)
                    .orElseThrow(() -> new ResourceNotFoundException("Notification not found!"));
            notificationRepository.delete(notification);
            return new ResponseMessage(true, HttpStatus.OK,"Notification deleted successfully!!");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Notification isn't deleted!!");
        }
    }

    public Notification saveNotification(Notification n){
        return notificationRepository.save(n);
    }



    //NOVIIIII

    public List<Notification> getAllUnreadAdminsNotifications(@CurrentUser UserPrincipal currentUser){
        return getAllUnreadAdminNotifications(currentUser);
    }

    public List<Notification> getAllUnreadUsersNotifications(Long userID, @CurrentUser UserPrincipal currentUser){

        //svaki user moze gledati samo svoje notifikacije
        if(!currentUser.getId().equals(userID)){
            throw new WrongInputException("This notification doesn't belong to current user!");
        }

        return getAllUnreadNotificationsForUser(userID, false, false);
    }

    public List<Notification> getAllAdminsNotifications(@CurrentUser UserPrincipal currentUser){
        return getAllAdminNotifications(currentUser);
    }

    public List<Notification> getAllUsersNotifications(Long userID, @CurrentUser UserPrincipal currentUser){

        //svaki user moze gledati samo svoje notifikacije
        if(!currentUser.getId().equals(userID)){
            throw new WrongInputException("This notification doesn't belong to current user!");
        }

        return getAllNotificationsForUser(userID, false);
    }

    public ResponseMessage setNotificationsOnReadAdmin(@CurrentUser UserPrincipal currentUser){
        return setOnReadAdmin(currentUser);
    }

    public ResponseMessage setNotificationsOnReadUser(@PathVariable(value = "userID") Long userID, @CurrentUser UserPrincipal currentUser){
        return setOnReadUser(userID, false, false);
    }

    private List<Notification> getAllUnreadNotificationsForUser(Long userID, Boolean read, Boolean isForAdmin){
        return notificationRepository.findAllByUserIDAndReadAndIsForAdmin(userID, read, isForAdmin);
    }

    private List<Notification> getAllNotificationsForUser(Long userID, Boolean isForAdmin){
        return notificationRepository.findAllByUserIDAndIsForAdminOrderByCreatedAtDesc(userID, isForAdmin);
    }

    private List<Notification> getAllUnreadAdminNotifications(@CurrentUser UserPrincipal currentUser){
        //return notificationRepository.findAllByReadAndIsForAdmin(read, isForAdmin);

        List<Notification> returnedNotifications = new ArrayList<>();

        List<Notification> allNotifications = getNotification();
        for(Notification n : allNotifications){
            if(!n.getRead()){
                if(currentUser.getId().equals(n.getUserID())){
                    returnedNotifications.add(n);
                }
                else {
                    if(n.getForAdmin()){
                        returnedNotifications.add(n);
                    }
                }
            }
        }

        return returnedNotifications;
    }

    private List<Notification> getAllAdminNotifications(@CurrentUser UserPrincipal currentUser){

        List<Notification> returnedNotifications = new ArrayList<>();

        List<Notification> allNotifications = getNotification();
        for(Notification n : allNotifications){
            if(currentUser.getId().equals(n.getUserID())){
                returnedNotifications.add(n);
            }
            else {
                if(n.getForAdmin()){
                    returnedNotifications.add(n);
                }
            }
        }

        returnedNotifications.sort((n1,n2) -> n1.getCreatedAt().compareTo(n2.getCreatedAt()));
        Collections.reverse(returnedNotifications);

        return returnedNotifications;
    }

    private ResponseMessage setOnReadUser(Long userID, Boolean read, Boolean isForAdmin){
        List<Notification> notifications = getAllUnreadNotificationsForUser(userID, read, isForAdmin);

        for(Notification n : notifications){
            n.setRead(true);
            notificationRepository.save(n);
        }

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully read all your unread notifications!");
    }

    private ResponseMessage setOnReadAdmin(@CurrentUser UserPrincipal currentUser){
        List<Notification> notifications = getAllUnreadAdminNotifications(currentUser);

        for(Notification n : notifications){
            n.setRead(true);
            notificationRepository.save(n);
        }

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully read all your unread notifications!");
    }

    public ResponseMessage addNotificationForRegistrationAndContactUs(Long userID, String content){

        Notification newNotification = new Notification();
        newNotification.setContent(content);
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(true);
        newNotification.setIsAddPetRequest(true);
        newNotification.setRequestId(-1L);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        if(userID != -1){
            return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for registration!");
        }
        else {
            return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for contact us form!");
        }
    }

    public ResponseMessage addNotificationForNewAdoptRequest(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("New request to adopt a pet!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(true);
        newNotification.setIsAddPetRequest(false);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for new adopt request!");
    }

    public ResponseMessage addNotificationForNewAddRequestNotApproved(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("Your request for adding a pet has not been approved!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(false);
        newNotification.setIsAddPetRequest(true);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for not approving add request!");
    }

    public ResponseMessage addNotificationForNewAddPetRequest(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("New request to add a pet!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(true);
        newNotification.setIsAddPetRequest(true);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for new adopt request!");
    }

    public ResponseMessage addNotificationForNewAdoptRequestNotApproved(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("Your request for adopting a pet has not been approved!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(false);
        newNotification.setIsAddPetRequest(false);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for not approving adopt request!");
    }

    public ResponseMessage addNotificationForNewAddRequestApproved(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("Your request for adding a pet has been approved!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(false);
        newNotification.setIsAddPetRequest(true);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for approving add request!");
    }

    public ResponseMessage addNotificationForNewAdoptRequestApproved(Long userID, Long requestID){
        Notification newNotification = new Notification();
        newNotification.setContent("Your request for adopting a pet has been approved!");
        newNotification.setUserID(userID);
        newNotification.setRead(false);
        newNotification.setIsForAdmin(false);
        newNotification.setIsAddPetRequest(false);
        newNotification.setRequestId(requestID);
        newNotification.setCreatedAt(new Date());

        notificationRepository.save(newNotification);

        return new ResponseMessage(true, HttpStatus.OK, "You have successfully added notification for approving adopt request!");
    }

    public ResponseMessage deleteAdminNotification(Long id, @CurrentUser UserPrincipal currentUser){
        List<Notification> notifications = getAllAdminNotifications(currentUser);

        for(Notification notification : notifications){
            if(notification.getId().equals(id)){
                notificationRepository.deleteById(id);
                return new ResponseMessage(true, HttpStatus.OK, "Notification deleted successfully!");
            }
        }

        throw new ResourceNotFoundException("Notification not found!");
    }

    public ResponseMessage deleteUserNotification(Long userID, Long id, @CurrentUser UserPrincipal currentUser){
        List<Notification> notifications = getAllUsersNotifications(userID, currentUser);

        for(Notification notification : notifications){
            if(notification.getId().equals(id)){
                notificationRepository.deleteById(id);
                return new ResponseMessage(true, HttpStatus.OK, "Notification deleted successfully!");
            }
        }

        throw new ResourceNotFoundException("Notification not found!");

    }
}
