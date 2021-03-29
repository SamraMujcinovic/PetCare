package ba.unsa.etf.nwt.notification_service.service;

import ba.unsa.etf.nwt.notification_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.notification_service.exception.WrongInputException;
import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }

    public ResponseMessage addNotification(Notification notification) {
        notification.setCreatedAt(new Date());
        try {
            notificationRepository.save(notification);
            return new ResponseMessage(true, HttpStatus.OK,"Notification added successfully!!");
        }
        catch (Exception e) {
            throw new WrongInputException("Notification isn't added!!");
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
        return notificationRepository.findAllByUserID(userID);
    }

    public List<Notification> getUnreadUserNotification(Long userID) {
        return notificationRepository.findAllByUserIDAndRead(userID, false);
    }

    public ResponseMessage deleteNotification(Long notificationID) {
        try {
            Notification notification = notificationRepository.findById(notificationID)
                    .orElseThrow(() -> new ResourceNotFoundException("Notification not found!"));
            notificationRepository.delete(notification);
            return new ResponseMessage(true, HttpStatus.OK,"Notification deleted successfully!!");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Notification isn't deleted!!");
        }

    }

}
