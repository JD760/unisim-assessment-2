package y111studios.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class NotificationTest {
    Notification notification = new Notification(5, null, null);
    
   

    @Test
    void testNotificationCreation() {
        assertNotNull(notification, "Notification should be created.");
        assertEquals(0, notification.getAge(), "Initial age should be 0.");
        assertFalse(notification.isActive(), "Notification should not be active initially.");
        assertEquals(5, notification.getMaxAge(), "Max age should be set correctly.");
    }

    @Test
    void testSetActive() {
        notification.setActive();
        assertTrue(notification.isActive(), "Notification should be active after calling setActive.");
    }

    @Test
    void testTick() {
        // Test ticking
        assertTrue(notification.tick(), "Notification should still be active after 1 tick.");
        assertEquals(1, notification.getAge(), "Age should be incremented to 1.");

        // Tick until max age
        for (int i = 1; i < 5; i++) {
            notification.tick();
        }
        assertFalse(notification.tick(), "Notification should not be active after exceeding max age.");
        assertEquals(6, notification.getAge(), "Age should be incremented to 6 after exceeding max age.");
    }

    @Test
    void testRemove() {
        notification.remove();
        assertEquals(5, notification.getAge(), "Age should be set to maxAge after remove is called.");
    }

}
