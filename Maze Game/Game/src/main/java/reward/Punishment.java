package reward;

/**
 * The Punishment class represents the negative rewards in the game.
 */
public class Punishment extends Reward {

    /**
     * Constructor for the Punishment class.
     *
     * @param scoreValue The negative score value imposed by the punishment.
     * @param x          The x-coordinate of the punishment's position.
     * @param y          The y-coordinate of the punishment's position.
     */
    public Punishment(int scoreValue, int x, int y) {
        super(scoreValue, x, y);
    }
  
 
}
