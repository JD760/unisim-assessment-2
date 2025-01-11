package y111studios.notification;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import y111studios.AssetPaths;
import y111studios.Main;

public class NotificationManagerTest {
    private Main testGame;
    private Table testTable;
    private NotificationManager notificationManager;

    @BeforeEach
    void setUp() {
        testGame = new Main(); // Initialize the Main class
        testTable = new Table(); // Create a new Table instance
        notificationManager = new NotificationManager(testTable, testGame); // Create NotificationManager
    }

    @Test
    void testCreateNotification() {
        assertEquals(0, notificationManager.queuedNotifications.size, "Initial queued notifications count should be 0.");
        
        // Create a notification
        notificationManager.createNotification(5, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        assertEquals(1, notificationManager.queuedNotifications.size, "Queued notifications count should be 1 after creation.");
        
        notificationManager.createNotification(3, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        assertEquals(2, notificationManager.queuedNotifications.size, "Queued notifications count should be 2 after creation.");
    }

    @Test
    void testTick() {
        notificationManager.createNotification(5, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        notificationManager.tick(); // Tick once to activate the notification
        
        assertEquals(1, notificationManager.activeNotifications.size(), "Active notifications count should be 1 after tick.");
        
        // Advance time and check removal
        for (int i = 0; i < 5; i++) {
            notificationManager.tick();
        }
        
        assertEquals(0, notificationManager.activeNotifications.size(), "Active notifications count should be 0 after max age is reached.");
    }

    @Test
    void testDrawNotifications() {
        notificationManager.createNotification(5, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        notificationManager.tick(); // Tick once to update
        
        assertNotNull(testTable.getCells().get(0).getActor(), "The first cell should have an actor after tick.");
        
        // Advance time
        for (int i = 0; i < 5; i++) {
            notificationManager.tick();
        }
        
        assertNull(testTable.getCells().get(0).getActor(), "The first cell should have no actor after notification expiration.");
    }

    @Test
    void testNotificationComparator() {
        notificationManager.createNotification(5, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        notificationManager.createNotification(2, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        notificationManager.tick(); // Update notifications
        
        assertEquals(2, notificationManager.activeNotifications.size(), "There should be 2 active notifications.");
        
        // Perform an additional tick to allow sorting
        notificationManager.tick(); // Update notifications
        assertEquals(2, notificationManager.activeNotifications.size(), "There should still be 2 active notifications after 1 tick.");
    }

    @Test
    void testNullPathNotificationCreation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationManager.createNotification(5, null);
        });
        assertEquals("Notification must have a non-null asset path", exception.getMessage());
    }

    @Test
    void testNotificationRemoval() {
        notificationManager.createNotification(5, AssetPaths.ACHIEVEMENT_NOTIFICATION);
        notificationManager.tick(); // Activate notification
        
        // Remove the notification
        notificationManager.activeNotifications.get(0).remove(); // This sets age to max
        notificationManager.tick(); // This should remove the notification
        
        assertEquals(0, notificationManager.activeNotifications.size(), "Active notifications count should be 0 after removal.");
    }
}