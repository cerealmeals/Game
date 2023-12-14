import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class P_EnemiesTest {
    
    @Test
    public void testEnemiesInitialization() {
        P_Grid grid = new P_Grid(10);
        P_Enemies enemies = new P_Enemies(3, grid);
        assertNotNull(enemies);
        assertEquals(3, enemies.getHealth());
        // Add more assertions if needed
    }

    @Test
    void Test_Spawn_Away_From_Player() {
        int mapsize = 10;
        P_Grid grid = new P_Grid(mapsize);
        P_Enemies enemy = new P_Enemies(1, grid);
        Boolean enemyClose = false;

        if(enemy.getXPos() < 4 || enemy.getYPos() < 4) {
            enemyClose = true;
        }

        assertFalse(enemyClose);
    }

    @Test
    public void testEnemiesMove() {
        // Set up a larger grid for testing
        boolean enemyMoved = false;
        P_Grid grid = new P_Grid(20);

        // Create enemy and place them on the grid
        P_Enemies enemy = new P_Enemies(3, grid);

        // Mock the player object
        P_Player player = new P_Player();
        player.setXPos(1, 0);
        player.setYPos(2, 0);

        // Initial positions of enemies
        int initialX = enemy.getXPos();
        int initialY = enemy.getYPos();

        // Invoke the move method
        long timeStart = System.currentTimeMillis();
        boolean result = false;

        // Make sure enough time passed for enemy to move
        while(System.currentTimeMillis()-timeStart < 1601){
            result = enemy.enemiesMove(player, grid);
            // System.out.println(timeStart);
        }

        // Enemy start locations are random so can't tell if x or y moved
        if(enemy.getXPos() != initialX || enemy.getYPos() != initialY) {
            enemyMoved = true;
        }

        // Assert that the enemies have moved
        assertTrue(result);
        assertTrue(enemyMoved);
    }

    @Test
    public void TestBFS() {
        P_Grid grid = new P_Grid(20);

        // Create enemy and place them on the grid
        P_Enemies enemy = new P_Enemies(3, grid);

        // Mock the player object
        P_Player player = new P_Player();
        player.setXPos(1, 0);
        player.setYPos(2, 0);

        // Initial distance between enemy and player
        int inital_distance = Math.abs(player.getXPos()[0] - enemy.getXPos()) + Math.abs(player.getYPos()[0] - enemy.getYPos());

        // Invoke the move method
        long timeStart = System.currentTimeMillis();

        // Make sure enough time passed for enemy to move
        while(System.currentTimeMillis()-timeStart < 3201){
            Boolean result = enemy.enemiesMove(player, grid);
        }

        // Calculate the distance again
        int new_distance = Math.abs(player.getXPos()[0] - enemy.getXPos()) + Math.abs(player.getYPos()[0] - enemy.getYPos());

        // Assert that the enemies have moved closer
        assertTrue(inital_distance>new_distance);
    }
    
}
