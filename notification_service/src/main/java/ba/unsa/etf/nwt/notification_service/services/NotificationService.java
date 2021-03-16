package ba.unsa.etf.nwt.notification_service.services;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
