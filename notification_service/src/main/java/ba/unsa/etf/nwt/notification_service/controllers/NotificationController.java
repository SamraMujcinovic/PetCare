package ba.unsa.etf.nwt.notification_service.controllers;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseMessage addNotifications(@RequestBody Notification notification) {
        if(notification.getContent().equals("") && notification.getUserID().equals(null)) return new ResponseMessage(false, "Content and user ID can't be blank!!", "BAD_REQUEST");

        if(notification.getContent().length() < 2 || notification.getContent().length() > 150) return new ResponseMessage(false, "Content must be between 2 and 150 characters!!", "BAD_REQUEST");

        if(notification.getContent().equals("")) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(notification.getUserID().equals(null)) return new ResponseMessage(false, "User ID can't be blank!!", "BAD_REQUEST");

        notificationService.addNotification(notification);

        return new ResponseMessage(true, "Notification added successfully!!", "OK");
    }

    @GetMapping("/notifications/user/{userID}")
    public List<Notification> getAllUserNotifications(@PathVariable Long userID){
        return notificationService.getUserNotification(userID);
    }

    @GetMapping("/notifications/user/{userID}/notification/{notificationID}")
    public Notification getOneNotificationForUser(@PathVariable Long notificationID, @PathVariable Long userID){
        return notificationService.getOneUserNotification(notificationID, userID);
    }

    @GetMapping("/notifications/notification/{notificationID}")
    public Optional<Notification> getOneNotification(@PathVariable Long notificationID){
        return notificationService.findById(notificationID);
    }

    @GetMapping("/notifications/user/unread/{userID}")
    public List<Notification> getUnreadUserNotification(@PathVariable Long userID){
        return notificationService.getUnreadUserNotification(userID);
    }
}
