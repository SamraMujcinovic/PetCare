package ba.unsa.etf.nwt.notification_service.repository;

import ba.unsa.etf.nwt.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import org.springframework.stereotype.Repository;

//@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserID(Long userID);
    Notification findByIdAndUserID(Long notificationID, Long userID);
    List<Notification> findAllByUserIDAndRead(Long userID, Boolean read);
}
