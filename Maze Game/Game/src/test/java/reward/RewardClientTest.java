package reward;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link RewardClient} class.
 */
class RewardClientTest {
    RewardClient driver;

    @BeforeEach
    void setUp() {
        driver = RewardClient.getInstance(10);
    }

    @AfterEach
    void tearDown() {
        driver = null;
    }

    /**
     * Tests the singleton so that multiple instances created with the same map size refer to the same object.
     */
    @Test
    public void testInstanceSingleton() {
        RewardClient newClient = RewardClient.getInstance(8);

        assertSame(driver, newClient);
    }

    /**
     * Rewards are generated and added to the list of rewards.
     */
    @Test
    public void testGenerateRewards() {
        driver.generateRewards();

        assertFalse(driver.getRewards().isEmpty());
    }

    /**
     * Generate rewards, collect one of them, and check if the collected score matches the value for the reward.
     */
    @Test
    public void testCollectReward() {
        driver.generateRewards();

        List<Reward> rewards = driver.getRewards();
        Reward rewardToCollect = rewards.get(0);

        int x = rewardToCollect.getX();
        int y = rewardToCollect.getY();
        int score = driver.collectReward(x, y);

        assertEquals(rewardToCollect.getScoreValue(), score);
    }

    /**
     * Collecting a reward at an invalid coordinate returns a score of 0.
     */
    @Test
    public void testCollectRewardInvalidCoordinates() {
        int invalidX = -1;
        int invalidY = 5;
        int score = driver.collectReward(invalidX, invalidY);

        assertEquals(0, score);
    }

    /**
     * Adda a reward and remove it, then check if it's successfully removed from the rewards list.
     */
    @Test
    public void testRemoveReward() {
        Reward reward = RewardFactory.createReward("Regular", 10, 4, 4);

        driver.getRewards().add(reward);

        int x = 4;
        int y = 4;
        driver.removeRewardAt(x, y);

        assertNull(driver.getRewardAt(x, y));
    }

    /**
     * Generate rewards then clear the list and check if the list is empty.
     */
    @Test
    public void testClearRewards() {
        driver.generateRewards();
        driver.clear();

        assertTrue(driver.getRewards().isEmpty());
    }

    /**
     * Generate rewards in the same position, and check that only one was placed by evaluating the score.
     */
    @Test
    public void testMultipleRewardsAtSamePosition() {
        RewardClient client = RewardClient.getInstance(10);

        Reward reward1 = RewardFactory.createReward("Regular", 10, 5, 5);
        Reward reward2 = RewardFactory.createReward("Bonus", 50, 5, 5);

        client.getRewards().add(reward1);
        client.getRewards().add(reward2);

        int x = 5;
        int y = 5;
        int score = client.collectReward(x, y);

        // Only the first reward added should be on the board, so the collection should be its score value.
        assertEquals(10, score);
    }

    /**
     * Generate rewards then collect all, and check if the rewards list is empty.
     */
    @Test
    public void testCollectRewardsUntilEmpty() {
        driver.generateRewards();

        // Collect rewards until the board is empty
        while (!driver.getRewards().isEmpty()) {
            int rewardX = driver.getRewards().get(0).getX();
            int rewardY = driver.getRewards().get(0).getY();

            driver.collectReward(rewardX, rewardY);
        }

        assertEquals(0, driver.getRewards().size());
    }
}