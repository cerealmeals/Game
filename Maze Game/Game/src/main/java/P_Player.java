import reward.RewardClient;

/**
 * The P_Player class represents the player in the game.
 * It controls player movement and interaction with the game grid elements.
 */
public class P_Player {
    public int keyRel = 0; // Key release state for player movement.
    private int trailLength = 2; // Length of the player's trail.
    private int xpos[]; // Array holding the x-coordinates of the player's positions.
    private int ypos[]; // Array holding the y-coordinates of the player's positions.
    private int score = 0; // Player's score in the game.
    private int rewards = 0; // Count of rewards collected by the player.
    private boolean explosion = false;
    /**
     * Default constructor for P_Player class.
     * Initializes player's initial position and other attributes.
     */
    P_Player() {
        xpos = new int[]{1, -1}; // Default x-coordinates for player positions.
        ypos = new int[]{2, -1}; // Default y-coordinates for player positions.
       
    }

    /**
     * Controls the movement of the player based on user input.
     *
     * @param keyPressed Indicates if a key is pressed.
     * @param key        The character representing the pressed key.
     * @param grid2      The game grid where the player moves.
     */

    void movePlayer(boolean keyPressed, char key, P_Grid grid2) {
        if (keyPressed && (keyRel == 1)) {
            keyRel = 0;
            // Moves the player according to the key input after checking for walls
            if (key == 'd' && grid2.getGrid()[xpos[0] + 1][ypos[0]] != 'w') {
                storePrev();
                xpos[0] += 1;
            } else if (key == 'a' && grid2.getGrid()[xpos[0] - 1][ypos[0]] != 'w') {
                storePrev();
                xpos[0] -= 1;
            } else if (key == 's' && grid2.getGrid()[xpos[0]][ypos[0] + 1] != 'w') {
                storePrev();
                ypos[0] += 1;
            } else if (key == 'w' && grid2.getGrid()[xpos[0]][ypos[0] - 1] != 'w') {
                storePrev();
                ypos[0] -= 1;
            }

            /**
            * Adds rewards to the score of the player accordingly
            * Sets the reward character to a path after it is collected
            */ 
            interact_with_reward(grid2);
        }
    }

    /**
    * Adds rewards to the score of the player accordingly
    * Sets the reward character to a path after it is collected
    */ 
    private void interact_with_reward(P_Grid grid2){
        char cellType = grid2.getGrid()[xpos[0]][ypos[0]];
            if (cellType == 'b' || cellType == 'r' || cellType == 'u') {
                if(cellType == 'r'){
                    rewards++;
                    increasetrail();
                }
                if(cellType == 'u'){
                    decreasetrail();
                }
                if(cellType == 'b'){
                    explosion = true;
                }
                RewardClient rewardClient = RewardClient.getInstance(grid2.getMapSize());
                score += rewardClient.collectReward(xpos[0], ypos[0]);
                // Reset the cell to a path cell.
                grid2.setGrid(xpos[0], ypos[0], 'p');
            }
    }


    /**
     * Stores previous positions of the player.
     */
    void storePrev() {
        int tempx[] = new int[trailLength];
        int tempy[] = new int[trailLength];
        System.arraycopy(xpos, 0, tempx, 0, xpos.length);
        System.arraycopy(ypos, 0, tempy, 0, ypos.length);
        for (int i = 0; i < trailLength-1; i++) {
            xpos[i + 1] = tempx[i];
            ypos[i + 1] = tempy[i];
        }
    }

    private void increasetrail(){
        trailLength++;
        int tempx[] = new int[trailLength];
        int tempy[] = new int[trailLength];
        System.arraycopy(xpos, 0, tempx, 0, xpos.length);
        System.arraycopy(ypos, 0, tempy, 0, ypos.length);
        tempx[trailLength-1] = -1;
        tempy[trailLength-1] = -1;
        xpos = tempx;
        ypos = tempy;
    }

    private void decreasetrail(){
        if(trailLength > 2){
            trailLength--;
        }
        int tempx[] = new int[trailLength];
        int tempy[] = new int[trailLength];
        System.arraycopy(xpos, 0, tempx, 0, tempx.length);
        System.arraycopy(ypos, 0, tempy, 0, tempy.length);
        xpos = tempx;
        ypos = tempy;
    }



    /**
     * Retrieves the array of x-coordinates of the player's positions.
     *
     * @return The array of x-coordinates.
     */
    int[] getXPos() {
        return xpos;
    }

    /**
     * Sets the x-coordinate at a specific index in the player's position array.
     *
     * @param value The value to set.
     * @param index The index where the value is to be set.
     */
    void setXPos(int value, int index) {
        xpos[index] = value;
    }

    /**
     * Retrieves the array of y-coordinates of the player's positions.
     *
     * @return The array of y-coordinates.
     */
    int[] getYPos() {
        return ypos;
    }

    /**
     * Sets the y-coordinate at a specific index in the player's position array.
     *
     * @param value The value to set.
     * @param index The index where the value is to be set.
     */
    void setYPos(int value, int index) {
        ypos[index] = value;
    }

    /**
     * Retrieves the player's score integer.
     *
     * @return The player's score.
     */
    int getScore() {
        return score;
    }

    /**
     * Sets the player's score to the value given.
     *
     * @param value The value to set.
     */
    void setScore(int value) {
        score = value;
    }

    /**
     * Retrieves the number of rewards the player had picked up.
     *
     * @return The number of rewards.
     */
    int getRewards() {
        return rewards;
    }

    /**
     * Retrieves the player's trail length.
     *
     * @return The trail length.
     */
    int getTrailLength() {
        return trailLength;
    }

    boolean getExplosion(){
        return explosion;
    }
}
