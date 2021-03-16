package ba.unsa.etf.nwt.notification_service.repository;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
