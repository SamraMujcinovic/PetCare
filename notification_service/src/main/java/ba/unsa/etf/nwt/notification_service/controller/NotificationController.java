package ba.unsa.etf.nwt.notification_service.controller;

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

    @RolesAllowed("ROLE_ADMIN")
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
    }
}
