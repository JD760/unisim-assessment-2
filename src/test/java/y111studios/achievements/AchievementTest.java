package y111studios;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import y111studios.AssetPaths;
import y111studios.World;
import y111studios.achievements.Achievement;
import y111studios.achievements.AchievementManager;
import y111studios.screens.MapScreen;

public class AchievementTest {

    private AchievementManager achievementManager;
    private World dummyWorld;

    @BeforeEach
    void setUp() {
        achievementManager = new AchievementManager(dummyWorld); // Create the AchievementManager
    }

    @Test
    void testAchievementCreation() {
        Achievement achievement = new Achievement("testAchievement", achievementManager, 
            "Test Achievement", "This is a test achievement", 100, dummyWorld, 
            AssetPaths.ACHIEVEMENT_NOTIFICATION) {
                
            public boolean condition() {
                return false; // Stubbed condition
            }
        };

        assertNotNull(achievement, "Achievement should be created successfully");
        assertEquals("testAchievement", achievement.getName(), "Achievement name should match");
        assertEquals("Test Achievement", achievement.getDisplayName(), "Display name should match");
        assertEquals("This is a test achievement", achievement.getDescription(), "Description should match");
    }

    @Test
    void testUniqueAchievementName() {
        Achievement achievement1 = new Achievement("testAchievement", achievementManager, 
            "Test Achievement", "This is a test achievement", 100, dummyWorld, 
            AssetPaths.ACHIEVEMENT_NOTIFICATION) {
            
            @Override
            public boolean condition() {
                return false; // Stubbed condition
            }
        };

        achievementManager.add(achievement1); // Add the initial achievement

        // Attempt to create a duplicate achievement
        assertThrows(IllegalArgumentException.class, () -> {
            new Achievement("testAchievement", achievementManager, "Another Achievement", 
                "This is another test achievement", 50, dummyWorld, AssetPaths.ACHIEVEMENT_NOTIFICATION) {
                
                @Override
                public boolean condition() {
                    return false; // Stubbed condition
                }
            };
        }, "Achievement names must be unique");
    }
}
