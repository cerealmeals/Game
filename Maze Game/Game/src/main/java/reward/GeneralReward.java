package reward;

/**
 * The GeneralReward class represents the regular positive rewards in the game.
 */
public class GeneralReward extends Reward {

    /**
     * Constructor for the GeneralReward class.
     *
     * @param scoreValue The score value associated with this reward.
     * @param x          The x-coordinate of the reward's position.
     * @param y          The y-coordinate of the reward's position.
     */
    public GeneralReward(int scoreValue, int x, int y) {
        super(scoreValue, x, y);
    }

    
}
