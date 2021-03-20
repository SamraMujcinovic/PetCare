package ba.unsa.etf.nwt.notification_service;

import ba.unsa.etf.nwt.notification_service.models.Notification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NotificationServiceApplicationTests {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void nullNotification() {
        Notification notification = new Notification();
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void notificationOnlyWithUser() {
        Notification notification = new Notification();
        notification.setUserID(2L);
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void notificationOnlyWithContent() {
        Notification notification = new Notification();
        notification.setContent("Error");
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void contentSmallerThan5() {
        Notification notification = new Notification();
        notification.setContent("Test");
        notification.setUserID(1L);
        notification.setRead(false);
        notification.setCreatedAt(new Date());
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void correctNotification() {
        Notification notification = new Notification();
        notification.setContent("Correct");
        notification.setUserID(1L);
        notification.setRead(false);
        notification.setCreatedAt(new Date());
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertTrue(violations.isEmpty());
    }
}
