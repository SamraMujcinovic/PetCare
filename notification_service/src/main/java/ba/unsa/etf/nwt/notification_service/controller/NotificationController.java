package ba.unsa.etf.nwt.notification_service.controller;

import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.security.CurrentUser;
import ba.unsa.etf.nwt.notification_service.security.UserPrincipal;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    //sve zasticeno

    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return notificationService.getNotification();
    }

    @PostMapping("/notifications")
    public ResponseMessage addNotifications(@Valid @RequestBody Notification notification, @CurrentUser UserPrincipal currentUser) {
        return notificationService.addNotification(notification, currentUser);
    }

    @GetMapping("/notifications/user")
    public List<Notification> getAllUserNotifications(){
        return notificationService.getUserNotification();
    }

    @GetMapping("/notifications/{notificationID}")
    public Notification getOneNotification(@PathVariable Long notificationID){
        return notificationService.findById(notificationID);
    }

    @GetMapping("/notifications/unread/user")
    public List<Notification> getUnreadUserNotification(){
        return notificationService.getUnreadUserNotification();
    }

    @DeleteMapping("/notifications/{notificationID}")
    public ResponseMessage deleteNotification(@PathVariable Long notificationID){
        return notificationService.deleteNotification(notificationID);
    }
}
