package ba.unsa.etf.nwt.notification_service.controller;

import ba.unsa.etf.nwt.notification_service.exception.WrongInputException;
import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.security.CurrentUser;
import ba.unsa.etf.nwt.notification_service.security.UserPrincipal;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    //todo notifikacije rade asinhrono!!

    /*@RolesAllowed("ROLE_ADMIN")
    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return notificationService.getNotification();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/notifications")
    public ResponseMessage addNotifications(@Valid @RequestBody Notification notification, @CurrentUser UserPrincipal currentUser) {
        return notificationService.addNotification(notification, currentUser);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/notifications/user")
    public List<Notification> getAllUserNotifications(@CurrentUser UserPrincipal currentUser){

        //todo kako razgraniciti da li je notifikacija za usera, a da li za admina, jer su drugacije u zavisnosti koja je rola usera?

        return notificationService.getUserNotification(currentUser);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/notifications/{notificationID}")
    public Notification getOneNotification(@PathVariable Long notificationID){
        return notificationService.findById(notificationID);
    }

    //sve notifikacije koje nisu procitane
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/notifications/unread/user")
    public List<Notification> getUnreadUserNotification(@CurrentUser UserPrincipal currentUser){
        return notificationService.getUnreadUserNotification(currentUser);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/notifications/{notificationID}")
    public ResponseMessage deleteNotification(@PathVariable Long notificationID){
        return notificationService.deleteNotification(notificationID);
    }

    //sve notifikacije koje nisu procitane, postavljaju se na procitane kada se otvore
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/notifications/setRead")
    public ResponseMessage setNotificationsOnRead(@CurrentUser UserPrincipal currentUser){
        return notificationService.setOnRead(currentUser);
    }*/



    //NOVIIII


    //todo obrisati!!
    /*@RolesAllowed("ROLE_ADMIN")
    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return notificationService.getNotification();
    }*/

    @RolesAllowed("ROLE_USER")
    @GetMapping("/notifications/unread/user/{userID}")
    public List<Notification> getAllUnreadUserNotifications(@PathVariable(value = "userID") Long userID, @CurrentUser UserPrincipal currentUser){

        //svaki user moze gledati samo svoje notifikacije
        if(!currentUser.getId().equals(userID)){
            throw new WrongInputException("This notification doesn't belong to current user!");
        }

        return notificationService.getAllUnreadNotificationsForUser(userID, false, false);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/notifications/all/user/{userID}")
    public List<Notification> getAllUserNotifications(@PathVariable(value = "userID") Long userID, @CurrentUser UserPrincipal currentUser){

        //svaki user moze gledati samo svoje notifikacije
        if(!currentUser.getId().equals(userID)){
            throw new WrongInputException("This notification doesn't belong to current user!");
        }

        return notificationService.getAllNotificationsForUser(userID, false);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/notifications/unread/admin")
    public List<Notification> getAllUnreadAdminNotifications(@CurrentUser UserPrincipal currentUser){
        return notificationService.getAllUnreadAdminNotifications(currentUser);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/notifications/all/admin")
    public List<Notification> getAllAdminNotifications(@CurrentUser UserPrincipal currentUser){
        return notificationService.getAllAdminNotifications(currentUser);
    }

    //sve notifikacije koje nisu procitane, postavljaju se na procitane kada se otvore
    @RolesAllowed("ROLE_USER")
    @PutMapping("/notifications/user/setRead/{userID}")
    public ResponseMessage setNotificationsOnReadUser(@PathVariable(value = "userID") Long userID, @CurrentUser UserPrincipal currentUser){
        return notificationService.setOnReadUser(userID, false, false);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/notifications/admin/setRead")
    public ResponseMessage setNotificationsOnReadAdmin(@CurrentUser UserPrincipal currentUser){
        return notificationService.setOnReadAdmin(currentUser);
    }

}
