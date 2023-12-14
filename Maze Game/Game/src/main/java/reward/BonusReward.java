package reward;

/**
 * The BonusReward class represents the special bonus rewards in the game.
 */
public class BonusReward extends Reward {

    /**
     * Constructor for the BonusReward class.
     *
     * @param scoreValue The score value associated with this reward.
     * @param x          The x-coordinate of the reward's position.
     * @param y          The y-coordinate of the reward's position.
     */
    public BonusReward(int scoreValue, int x, int y) {
        super(scoreValue, x, y);
    }

    
}
