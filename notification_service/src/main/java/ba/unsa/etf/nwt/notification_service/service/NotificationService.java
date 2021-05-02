package ba.unsa.etf.nwt.notification_service.service;

import ba.unsa.etf.nwt.notification_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.notification_service.exception.WrongInputException;
import ba.unsa.etf.nwt.notification_service.model.Notification;
import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.response.ResponseMessage;
import ba.unsa.etf.nwt.notification_service.security.CurrentUser;
import ba.unsa.etf.nwt.notification_service.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CommunicationsService communicationsService;

    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }

    public ResponseMessage addNotification(Notification notification, @CurrentUser UserPrincipal currentUser) {
        notification.setCreatedAt(new Date());
        try {
            RestTemplate restTemplate = new RestTemplate();
            //Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
            //notification.setUserID(userId);
            notification.setUserID(currentUser.getId());
            notificationRepository.save(notification);
            return new ResponseMessage(true, HttpStatus.OK,"Notification added successfully!!");
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
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

    public List<Notification> getUserNotification() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
            try {
                List<Notification> notifications = notificationRepository.findAllByUserID(userId);
                return notifications;
            }
            catch (Exception ex){
                List<Notification> notifications = new ArrayList<>();
                return notifications;
            }
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
        }
    }

    public List<Notification> getUnreadUserNotification() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Long userId = restTemplate.getForObject(communicationsService.getUri("user_service") + "/user/me/id", Long.class);
            try {
                List<Notification> notifications = notificationRepository.findAllByUserIDAndRead(userId, false);
                return notifications;
            }
            catch (Exception e){
                List<Notification> notifications = new ArrayList<>();
                return notifications;
            }
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Can't connect to user_service!!");
        }
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

    public Notification saveNotification(Notification n){
        return notificationRepository.save(n);
    }

}
