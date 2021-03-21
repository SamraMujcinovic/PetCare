package ba.unsa.etf.nwt.notification_service.services;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }

    public Notification addNotification(Notification notification) {
        notification.setCreatedAt(new Date());
        return notificationRepository.save(notification);
    }

    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public boolean existsById(Long notificationId) {
        return notificationRepository.existsById(notificationId);
    }

    public List<Notification> getUserNotification(Long userID) {
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID))
                .collect(Collectors.toList());
    }

    public Notification getOneUserNotification(Long notificationID, Long userID){
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID) && n.getId().equals(notificationID))
                .collect(Collectors.toList()).get(0);

    }

    public List<Notification> getUnreadUserNotification(Long userID) {
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID) && n.getRead().equals(false))
                .collect(Collectors.toList());
    }
}
