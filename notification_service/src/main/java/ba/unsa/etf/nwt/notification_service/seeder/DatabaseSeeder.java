package ba.unsa.etf.nwt.notification_service.seeder;

import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatabaseSeeder {
    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedDatabase();
    }

    private void seedDatabase() {
        Date date = new Date();
        Notification n1 = createNotification("Novi zahtjev za dodavanje ljubimca", Long.valueOf(1), true, date);
        Notification n2 = createNotification("Dodali ste novog ljubimca", Long.valueOf(2), false, date);
        Notification n3 = createNotification("Vas ljubimac je usvojen", Long.valueOf(3), false, date);
    }

    private Notification createNotification (String content, Long userID, Boolean read, Date date) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setUserID(userID);
        notification.setRead(read);
        notification.setCreatedAt(date);
        notificationService.saveNotification(notification);
        return notification;
    }
}
