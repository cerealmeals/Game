package reward;

import java.util.Random;

/**
 * The abstract Reward class represents the various types of rewards in the game.
 */
public abstract class Reward {
    protected int scoreValue; // The score value of the reward.
    protected boolean isCollected; // Tracks if the reward has been collected.
    protected int x, y; // The x and y coordinates of the reward.
    protected Random random = new Random(); // Random number generator for reward actions.

    /**
     * Constructor for the Reward class.
     *
     * @param scoreValue The value the reward adds to the player's score.
     * @param x          The x-coordinate of the reward's position.
     * @param y          The y-coordinate of the reward's position.
     */
    public Reward(int scoreValue, int x, int y) {
        this.scoreValue = scoreValue;
        this.isCollected = false;
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate of the reward.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the reward.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Defines the action of collecting the reward.
     */
    public void collect(){
        this.isCollected = true;
    }

    /**
     * Retrieves the score value of the reward.
     *
     * @return The score value of the reward.
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Checks if the reward has been collected.
     *
     * @return True if the reward has been collected, false otherwise.
     */
    public boolean isCollected() {
        return isCollected;
    }
}