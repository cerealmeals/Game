package reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The RewardClient class manages rewards and their distribution on the game board.
 */
public class RewardClient {
    private static RewardClient instance = null;
    private final int mapSize;
    private final List<Reward> rewards;
    private int number_of_rewards = 15;
    //private char[][] grid;
    Random random;

    // Used to randomly generate rewards and punishments
    float bonusRewardChance = 0.1f;
    float punishmentChance = 0.4f;

    /**
     * Constructor for the RewardClient class.
     *
     * @param mapSize The size of the game board.
     */
    private RewardClient(int mapSize) {
        this.mapSize = mapSize;
        this.rewards = new ArrayList<>();
        random = new Random();
    }

    /**
     * Retrieves the instance of the RewardClient, creating it if it doesn't exist.
     *
     * @param mapSize The size of the game board.
     * @return The instance of RewardClient.
     */
    public static RewardClient getInstance(int mapSize) {
        if (instance == null) {
            instance = new RewardClient(mapSize);
        }
        return instance;
    }

    /**
     * Retrieves the list of objects on the board.
     *
     * @return The list of objects.
     */
    public List<Reward> getRewards() {
        return rewards;
    }

    /**
     * Generates objects and places them on the game board.
     */
    public void generateRewards() {
        // spawn 'number_of_rewards' reward random chance to be different types.
        for (int i = 0; i < number_of_rewards; i++) {
            
            int x = random.nextInt(mapSize-2)+1;
            int y = random.nextInt(mapSize-3)+2;
            while(x==1 && y == 2){
                x = random.nextInt(mapSize-2)+1;
                y = random.nextInt(mapSize-3)+2;
            }
            String type = "";
            int scoreValue = 0;
            float percent = random.nextFloat();
            if (percent < bonusRewardChance) {
                type =  "Bonus";
                scoreValue = 50;     
            }
            else if(percent < punishmentChance){
                type = "Punishment";
                scoreValue = -5;
            }
            else{
                type = "Regular";
                scoreValue = 10;
            }rewards.add(RewardFactory.createReward(type, scoreValue, x, y));
            
        }
    }

    /**
     * Collects the object at a specific position.
     * Awards the player points based on the reward/punishment
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The score value of the collected reward.
     */
    public int collectReward(int x, int y) {
        if (x < 0 || y < 0 || x >= mapSize || y >= mapSize) {
            return 0; // Invalid coordinates
        }

        Reward rewardAtPosition = getRewardAt(x, y);

        if (rewardAtPosition != null) {
            int score = rewardAtPosition.getScoreValue();
            removeRewardAt(x, y); // Remove the reward from the board
            return score;
        }

        return 0;
    }

    /**
     * Retrieves the object at a specific position.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The reward at the given position or null if no reward is present.
     */
    public Reward getRewardAt(int x, int y) {
        for (Reward reward : rewards) {
            if (reward.getX() == x && reward.getY() == y) {
                return reward;
            }
        }
        return null;
    }

    /**
     * Removes the object at a specific position from the board.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    protected void removeRewardAt(int x, int y) {
        Reward rewardAt = getRewardAt(x, y);
        rewards.remove(rewardAt);
    }

    public void clear(){
        rewards.clear();
    }

    public int getNumRewards(){
        return number_of_rewards;
    }

    public void setNumRewards(int n){
        number_of_rewards = n;
    }
}
