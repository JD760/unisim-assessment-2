package y111studios.achievements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.World;


public class SatisfactionAchievementTest {

    private SatisfactionAchievement satisfactionAchievement;
    private AchievementManager achievementManager;
    private World dummyWorld;
    public GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState(100, 100, null);
        dummyWorld = new World(null, gameState, null); // Initialize the dummy world
        achievementManager = new AchievementManager(dummyWorld); // Create the AchievementManager
        satisfactionAchievement = new SatisfactionAchievement("satisfactionAchievement", 
            achievementManager, dummyWorld, 
            AssetPaths.ACHIEVEMENT_NOTIFICATION); // Create the SatisfactionAchievement
    }
    
    @Test
    void testConditionNotMet() {
        // Set satisfaction to a value below 40%
        dummyWorld.getGameState().getStudentSatisfaction().setSatisfaction(30);
        assertFalse(satisfactionAchievement.condition(), 
            "Condition should not be met when satisfaction is below 40%");
    }

    @Test
    void testConditionMet() {
        // Set satisfaction to a value above 40%
        dummyWorld.getGameState().getStudentSatisfaction().setSatisfaction(50);
        assertTrue(satisfactionAchievement.condition(), 
            "Condition should be met when satisfaction is above 40%");
    }

}