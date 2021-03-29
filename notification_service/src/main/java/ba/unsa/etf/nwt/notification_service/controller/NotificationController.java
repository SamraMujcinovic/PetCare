package ba.unsa.etf.nwt.notification_service.controller;

import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
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

    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return notificationService.getNotification();
    }

    @PostMapping("/notifications")
    public ResponseMessage addNotifications(@Valid @RequestBody Notification notification) {
        return notificationService.addNotification(notification);
    }

    @GetMapping("/notifications/user/{userID}")
    public List<Notification> getAllUserNotifications(@PathVariable Long userID){
        return notificationService.getUserNotification(userID);
    }

    @GetMapping("/notifications/{notificationID}")
    public Notification getOneNotification(@PathVariable Long notificationID){
        return notificationService.findById(notificationID);
    }

    @GetMapping("/notifications/unread/user/{userID}")
    public List<Notification> getUnreadUserNotification(@PathVariable Long userID){
        return notificationService.getUnreadUserNotification(userID);
    }

    @DeleteMapping("/notifications/{notificationID}")
    public ResponseMessage deleteNotification(@PathVariable Long notificationID){
        return notificationService.deleteNotification(notificationID);
    }
}
