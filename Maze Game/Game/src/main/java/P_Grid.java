import reward.BonusReward;
import reward.GeneralReward;
import reward.Punishment;
import reward.Reward;
import reward.RewardClient;

import java.util.ArrayList;
import java.util.Random;

/**
 * The P_Grid class represents the game grid and manages grid-related functionalities.
 */
public class P_Grid {
    private final RewardClient rewardClient; // Manages rewards in the grid.
    private char[][] grid; // The 2D grid representing the game map.
    private Random rn = new Random(); // Random number generator for grid setup.
    private int mapsize; // The size of the map.
    private ArrayList<ArrayList<Integer>> graph; // Represents the graph structure of the grid.
    private int number_of_vertices; // Total number of vertices in the grid.
    private int number_of_rewards = 0; // Number of rewards in the grid.

    /**
     * Default constructor for P_Grid class.
     * Initializes the grid, creates rewards, and sets up the map.
     */
    P_Grid(int size) {
        if(size < 6) {
            throw new IllegalArgumentException("Mapsize is too small!");
        }
        rewardClient = RewardClient.getInstance(size); // Initialize the reward client.
        rewardClient.generateRewards(); // Generate rewards for the grid.
        mapsize = size;
        number_of_vertices = size*size;
        grid = makeGrid(); // Create the game grid.
    }

    /**
     * Generates the initial game grid based on specified criteria.
     *
     * @return The 2D character array representing the game map.
     */
    public char[][] makeGrid() {
        grid = new char[mapsize][mapsize];
        //set up boarder
        SetUpBoarder();

        //set up random walls and rewards
        SetUpWalls();
        // create a graph of the paths in the grid
        createGraph();

        // spawn Rewards on the paths
        spawnRewards();

        return grid;
    }

    /**
     * Set up boarder of grid
     */
    private void SetUpBoarder(){
        for (int i = 0; i < mapsize; i++) {
            for (int j = 0; j < mapsize; j++) {
                if ((i == 0)
                        || (i == mapsize - 1)
                        || (j == 1)
                        || (j == mapsize - 1)
                )
                    grid[i][j] = 'w';
                else
                    grid[i][j] = 'p';
                if (j == 0)
                    grid[i][j] = 'm';
            }
        }
    }

    private void SetUpWalls(){
        for (int i = 1; i < mapsize - 2; i++) {
            for (int j = 2; j < mapsize - 1; j++) {
                if (!(i == 1 && j == 2)) { //MAKE INITIAL POSITION CLEAR
                    if (grid[i - 1][j] == 'w') { // one above is a wall
                        if (rn.nextInt(9) + 1 <= 5) {
                            grid[i][j] = 'w';
                        }
                    } else if (!(grid[i - 1][j - 1] == 'w' || grid[i - 1][j + 1] == 'w')) { // walls above on both sides
                        if (rn.nextInt(9) + 1 <= 5) {
                            grid[i][j] = 'w';
                        }
                    }

                }
            }
        }
    }
    /**
     * Creates a graph structure for path navigation in the grid.
     */
    private void createGraph() {
        graph = new ArrayList<ArrayList<Integer>>(number_of_vertices);

        // add a list to track the vertices
        for (int i = 0; i < number_of_vertices; i++) {
            graph.add(new ArrayList<Integer>());
        }
        int count = 0;
        // Step through the grid and find all paths
        for (int j = 0; j < mapsize; j++) {
            for (int i = 0; i < mapsize; i++) {
                //System.out.println("i = " + i + " j = " + j + grid[i][j]);
                if (grid[i][j] == 'p') {  // if it is a path check if it is adjacent
                    
                    if (grid[i + 1][j] == 'p') { // if there is a path to the right
                        graph.get(count).add(count + 1);
                        //printGraph();
                    }
                    if (grid[i - 1][j] == 'p') {  // if there is a path to the left
                        graph.get(count).add(count - 1);
                        //printGraph();
                    }
                    if (grid[i][j + 1] == 'p') {    // if there is a path to the below
                        graph.get(count).add(count + mapsize);
                        //printGraph();
                    }
                    if (grid[i][j - 1] == 'p') {    // if there is a path to the above
                        graph.get(count).add(count - mapsize);
                        //printGraph();
                    }
                }
                count++;
            }
        }
    }

    /**
     * Spawns rewards on the grid based on reward locations and types.
     */
    private void spawnRewards() {
        for (int i = 0; i < mapsize; i++) {
            for (int j = 0; j < mapsize; j++) {
                if (grid[i][j] == 'p') {
                    Reward reward = rewardClient.getRewardAt(i, j);
                    if (reward != null) {
                        if(reward instanceof BonusReward){
                            grid[i][j] = 'b';
                        }
                        if(reward instanceof Punishment){
                            grid[i][j] = 'u';
                        }
                        if (reward instanceof GeneralReward){
                            grid[i][j] = 'r';
                            number_of_rewards++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieves the number of rewards present in the grid.
     *
     * @return The number of rewards on the grid.
     */
    int getNumRewards() {
        return number_of_rewards;
    }

    /**
     * Retrieves the size of the game map.
     *
     * @return The size of the map.
     */
    int getMapSize() {
        return mapsize;
    }

    /**
     * Retrieves the 2D grid representing the game map.
     *
     * @return The 2D grid representing the game map.
     */
    char[][] getGrid() {
        return grid;
    }

    /**
     * Sets a specific cell in the grid to the given value.
     *
     * @param xpos  The x-coordinate of the cell.
     * @param ypos  The y-coordinate of the cell.
     * @param value The value to set in the cell.
     */
    void setGrid(int xpos, int ypos, char value) {
        grid[xpos][ypos] = value;
    }

    ArrayList<ArrayList<Integer>> getGraph(){
        return graph;
    }

    int getNumber_of_vertices(){
        return number_of_vertices;
    }
}
