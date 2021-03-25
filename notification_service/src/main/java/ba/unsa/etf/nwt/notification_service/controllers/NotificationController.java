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

        Integer value = notificationService.addNotification(notification);

        if(value == 1) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(value == 2) return new ResponseMessage(false, "Content must be between 2 and 150 characters!!", "BAD_REQUEST");

        if(value == 13) return new ResponseMessage(true, "Notification added successfully!!", "OK");

        return new ResponseMessage(false, "Notification isn't added!!", "NOT_FOUND");

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
        if (notificationService.deleteNotification(notificationID))
            return new ResponseMessage(true, "Notification deleted successfully!!", "OK");
        return new ResponseMessage(false, "Notification isn't deleted!!", "NOT_FOUND");

    }
}
