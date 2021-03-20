package ba.unsa.etf.nwt.notification_service.controllers;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Notification addNotifications(@RequestBody Notification notification) {
        return notificationService.addNotification(notification);
    }

    @GetMapping("/notifications/{userID}")
    public List<Notification> getAllUserNotifications(Long userID){
        return notificationService.getUserNotification(userID);
    }
}
