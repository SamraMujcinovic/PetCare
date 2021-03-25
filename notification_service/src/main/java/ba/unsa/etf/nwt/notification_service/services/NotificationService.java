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

    public Integer addNotification(Notification notification) {
        if(notification.getContent().isEmpty()) return 1;
        if(notification.getContent().length() < 2 || notification.getContent().length() > 150) return 2;

        notification.setCreatedAt(new Date());
        try {
            notificationRepository.save(notification);
            return 13;
        }
        catch (Exception e) {
            return 3;
        }
    }

    public Notification findById(Long notificationID) {
        Notification notification = notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getId().equals(notificationID))
                .collect(Collectors.toList()).get(0);
        notification.setRead(true);
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getUserNotification(Long userID) {
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID))
                .collect(Collectors.toList());
    }

    public List<Notification> getUnreadUserNotification(Long userID) {
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID().equals(userID) && n.getRead().equals(false))
                .collect(Collectors.toList());
    }

    public Boolean deleteNotification(Long notificationID) {
        try {
            Notification notification = notificationRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getId().equals(notificationID))
                    .collect(Collectors.toList()).get(0);
            notificationRepository.delete(notification);
            if (notificationRepository.findById(notificationID) != null) return true;
            return false;
        } catch (Exception e) {
            return false;
        }

    }

}
