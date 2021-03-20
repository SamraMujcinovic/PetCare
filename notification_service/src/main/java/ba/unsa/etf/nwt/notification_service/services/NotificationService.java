package ba.unsa.etf.nwt.notification_service.services;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        return notificationRepository.save(notification);
    }

    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public boolean existsById(Long notificationId) {
        return notificationRepository.existsById(notificationId);
    }

    public Notification buildNotificationResponse(Notification notification){
        return new Notification(notification.getId(),
                notification.getContent(), notification.getUserID(), 
                notification.getRead(), notification.getCreatedAt());
    }
    
    public List<Notification> getUserNotification(Long userID) {
        return notificationRepository
                .findAll()
                .stream()
                .filter(n -> n.getUserID() == userID)
                .map(n -> new Notification(n.getContent(), n.getRead(), n.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public Notification getOneUserNotification (Long notificationID, Long userID){
        Notification notification = notificationRepository.findByIdAndUserID(notificationID, userID);
        if(notification==null){
            throw new ResourceNotFoundException("Wrong notificationId");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getUnreadUserNotification(Long userId) {
        return notificationRepository
                .findAllByUserIDAndRead(userId, false)
                .stream()
                //.map()
                .collect(Collectors.toList());
    }
}
