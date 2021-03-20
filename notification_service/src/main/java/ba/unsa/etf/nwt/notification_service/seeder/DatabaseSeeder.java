package ba.unsa.etf.nwt.notification_service.seeder;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedDatabase();
    }

    private void seedDatabase() {
        Notification n1 = createNotification("Novi zahtjev za dodavanje ljubimca", Long.valueOf(1), true);
        Notification n2 = createNotification("Dodali ste novog ljubimca", Long.valueOf(2), false);
        Notification n3 = createNotification("Vas ljubimac je usvojen", Long.valueOf(3), false);
    }

    private Notification createNotification (String content, Long userID, Boolean read) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setUserID(userID);
        notification.setRead(read);
        notificationService.addNotification(notification);
        return notification;
    }
}
