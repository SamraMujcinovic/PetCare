package ba.unsa.etf.nwt.notification_service.services;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.responses.ResponseMessage;
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

    public ResponseMessage addNotification(Notification notification) {
        if(notification.getContent().isEmpty()) return new ResponseMessage(false, "Content can't be blank!!", "BAD_REQUEST");

        if(notification.getContent().length() < 2 || notification.getContent().length() > 150) return new ResponseMessage(false, "Content must be between 2 and 150 characters!!", "BAD_REQUEST");

        notification.setCreatedAt(new Date());
        try {
            notificationRepository.save(notification);

            return new ResponseMessage(true, "Notification added successfully!!", "OK");
        }
        catch (Exception e) {
            return new ResponseMessage(false, "Notification isn't added!!", "NOT_FOUND");
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

    public ResponseMessage deleteNotification(Long notificationID) {
        try {
            Notification notification = notificationRepository
                    .findAll()
                    .stream()
                    .filter(n -> n.getId().equals(notificationID))
                    .collect(Collectors.toList()).get(0);
            notificationRepository.delete(notification);
            if (notificationRepository.findById(notificationID) != null) return new ResponseMessage(true, "Notification deleted successfully!!", "OK");
            return new ResponseMessage(false, "Notification isn't deleted!!", "NOT_FOUND");
        } catch (Exception e) {
            return new ResponseMessage(false, "Notification isn't deleted!!", "NOT_FOUND");
        }

    }

}
