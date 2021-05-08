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

        Notification n1 = createNotification("There is a new registered user, check the list of users!", 1L, false, true, true, -1L, date);
        Notification n2 = createNotification("Someone filled contact us form, check email!", -1L, true, true, true, -1L, date);
        Notification n3 = createNotification("New request to add a pet!", 3L, false, true, true, 1L, date);
        Notification n4 = createNotification("New request to adopt a pet!", 5L, true, true, false, 1L, date);
        Notification n5 = createNotification("Your request for adding a pet has been approved!", 3L, false, false, true, 1L, date);
        Notification n6 = createNotification("Your request for adopting a pet has not been approved!", 5L, true, false, false, 1L, date);

        //Notification n1 = createNotification("Novi zahtjev za dodavanje ljubimca", 1L, true, date);
        //Notification n2 = createNotification("Dodali ste novog ljubimca", 2L, false, date);
        //Notification n3 = createNotification("Vas ljubimac je usvojen", 3L, false, date);
    }

    private Notification createNotification (String content, Long userID, Boolean read, Boolean isForAdmin, Boolean isAddRequest, Long requestId, Date date) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setUserID(userID);
        notification.setRead(read);
        notification.setForAdmin(isForAdmin);
        notification.setAddPetRequest(isAddRequest);
        notification.setRequestId(requestId);
        notification.setCreatedAt(date);
        notificationService.saveNotification(notification);
        return notification;
    }
}
