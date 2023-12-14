import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class P_PlayerTest {

    @Test
    void Trail_Increase_Decrease_balance(){
        P_Player player = new P_Player();
        P_Grid grid = new P_Grid(20);
        //Trail function are private so we have to use movePlayer to test them


        //Testing increase trail
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'r');
        grid.setGrid(player.getXPos()[0]-1,player.getYPos()[0],'r');
        grid.setGrid(player.getXPos()[0],player.getYPos()[0]+1,'r');
        grid.setGrid(player.getXPos()[0],player.getYPos()[0]-1,'r');
        player.keyRel =1;
        player.movePlayer(true,'d',grid);
            //Trail should be behind player
            int[] compare = {player.getXPos()[0],player.getXPos()[0]-1,-1};
            assertArrayEquals(compare,player.getXPos());
            assertEquals(compare.length,player.getXPos().length);

            //Player position should have three entries after increaseTrail() being called twice
            grid.setGrid(player.getXPos()[0],player.getYPos()[0],'r');
            player.movePlayer(true,'d',grid);
            assertEquals(3,player.getXPos().length);
            assertEquals(3,player.getTrailLength());



        //Testing decrease trail
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'u');
        player.keyRel = 1;
        player.movePlayer(true,'d',grid);


        int[] compare2 = {1,1};
        assertEquals(compare2.length,player.getXPos().length);
        assertEquals(2,player.getTrailLength());





    }

    @Test
    void keyReleaseRemoval(){
        //Checks if is key hold is released after being pressed
        P_Player player = new P_Player();
        P_Grid grid = new P_Grid(20);
        player.keyRel =1;
        player.movePlayer(true,'d',grid);
        assertEquals(0,player.keyRel);

    }

    @Test
    void rewardWorking(){
        P_Grid grid = new P_Grid(20);
        P_Player player = new P_Player();
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'r');
        player.keyRel =1;
        player.movePlayer(true,'d',grid);
        //Check if regular reward works
        assertEquals(1, player.getRewards());

        //Check if super reward works
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'b');
        player.keyRel =1;
        player.movePlayer(true,'d',grid);
        assertEquals(1, player.getRewards());

        //Check if punishment works
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'u');
        player.keyRel =1;
        player.movePlayer(true,'d',grid);
        assertEquals(2, player.getTrailLength());

    }

    @Test
    void setter_getterTest(){

        P_Player player = new P_Player();
        player.setScore(10);
        player.setXPos(11,0);
        player.setYPos(11,0);

        assertEquals(10,player.getScore());
        assertEquals(11,player.getXPos()[0]);
        assertEquals(11,player.getYPos()[0]);
        assertNotNull(player.getExplosion());
        assertNotNull(player.getRewards());
        assertNotNull(player.getTrailLength());


    }

    @Test
    void playermoveTest(){

        P_Player player = new P_Player();
        P_Grid grid = new P_Grid(20);
        grid.setGrid(player.getXPos()[0]+1,player.getYPos()[0],'p');
        grid.setGrid(player.getXPos()[0]-1,player.getYPos()[0],'p');
        grid.setGrid(player.getXPos()[0],player.getYPos()[0]+1,'p');
        grid.setGrid(player.getXPos()[0],player.getYPos()[0]-1,'p');
        int tempx = player.getXPos()[0];
        int tempy = player.getYPos()[0];
        player.keyRel = 1;
        player.movePlayer(true,'w',grid);
        player.keyRel = 1;
        player.movePlayer(true,'s',grid);
        player.keyRel = 1;
        player.movePlayer(true,'d',grid);
        player.keyRel = 1;
        player.movePlayer(true,'a',grid);
        assertEquals(tempx,player.getXPos()[0]);
        assertEquals(tempy,player.getYPos()[0]);


    }
}