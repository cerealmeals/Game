import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class P_GridTest {

    @Test
    public void testGridInitialization() {
        P_Grid grid = new P_Grid(10);
        assertNotNull(grid);
        assertEquals(10, grid.getMapSize());
        assertNotNull(grid.getGrid());
        assertEquals(100, grid.getNumber_of_vertices());
        // Add more assertions if needed
    }

    @Test
    void Test_Grid_Border() {
        int mapsize = 10;
        int number_of_perimeter_walls = 0;

        // The top row of the map is used for the menu  
        int expected_perimeter_walls = (2*(mapsize-1)) + (2*(mapsize-2));
        
        P_Grid grid = new P_Grid(mapsize);

        for(int i = 0; i < mapsize; i++) {
            for(int j = 0; j < mapsize; j++) {
                if ((i == 0)
                        || (i == mapsize - 1)
                        || (j == 1)
                        || (j == mapsize - 1)) {
                            if (grid.getGrid()[i][j] == 'w') {
                                number_of_perimeter_walls++;
                            }
                        }
            }
        }

        assertEquals(expected_perimeter_walls, number_of_perimeter_walls);
    }

    @Test
    void Test_Number_Of_Rewards() {
        int mapsize = 10;
        int rewards_count = 0;
        P_Grid grid = new P_Grid(mapsize);

        for(int i = 0; i < mapsize; i++) {
            for(int j = 0; j < mapsize; j++) {
                if (grid.getGrid()[i][j] == 'r') {
                    rewards_count++;
                }
            }
        }

        assertEquals(grid.getNumRewards(), rewards_count);
    
    }

    @Test
    void Test_Small_Board() {
        int mapsize = 7;
        P_Grid grid = new P_Grid(mapsize);

        assertEquals((mapsize*mapsize), grid.getNumber_of_vertices());
    }

    @Test
    void Test_Too_Small_Mapsize() {

        assertThrows(IllegalArgumentException.class, () -> {
                     P_Grid grid = new P_Grid(5);
        });
    }
}
