package reward;

/**
 * The RewardFactory class creates different types of rewards based on the given type.
 */
public class RewardFactory {
  /**
     * Creates a reward based on the provided type, score value, and coordinates.
     *
     * @param type       The type of the reward to create (Regular, Bonus, or Punishment).
     * @param scoreValue The score value associated with the reward.
     * @param x          The x-coordinate for the reward's position.
     * @param y          The y-coordinate for the reward's position.
     * @return The created reward object based on the given type.
     * @throws IllegalArgumentException if an unknown reward type is provided.
     */
  public static Reward createReward(String type, int scoreValue, int x, int y) {
    switch (type) {
      case "Regular":
        return new GeneralReward(scoreValue, x, y);
      case "Bonus":
        return new BonusReward(scoreValue, x, y);
      case "Punishment":
        return new Punishment(scoreValue, x, y);
      default:
        throw new IllegalArgumentException("Unknown reward type: " + type);
    }
  }
}
