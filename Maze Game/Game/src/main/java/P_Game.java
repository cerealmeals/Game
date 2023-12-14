import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import reward.RewardClient;

import java.util.ArrayList;
import java.util.Random;

/**
 * The P_Game class represents the game and its functionalities.
 */
public class P_Game extends PApplet {
    int mapsize = 20;
    P_Grid grid2 = new P_Grid(mapsize);
    RewardClient rewardClient = RewardClient.getInstance(mapsize);
    P_Player player = new P_Player();

    int released = 1; // Used as a boolean to control key releases
    int gameStarted = 0; // Used as a boolean variable to determine whether the game has started
    int gamePaused = 0; // Used as a boolean to determine if the game is paused
    int levelOver = 0; // Used as a boolean to see if the level is completed
    int gameOver = 0; // Used as a boolean to see if the game is over
    Random rn = new Random();
    int enemy_HP = 4;
    int initial_number_of_enemies = 4;
    int current_number_of_enemies = initial_number_of_enemies; // The default number of enemies
    ArrayList<P_Enemies> enemies = new ArrayList<P_Enemies>(); // Stores all the enemies in one data structure
    long time;
    long time_paused;
    
    /**
     * Sets the size of the game window.
     */
    public void settings() {
        size(800, 800);
        
        
        
    }

    /**
     * Sets up the enemies for the game at the start.
     */
    PImage enemyImage      ;
    PImage exitImage       ;
    PImage pathImage       ;
    PImage playerImage     ;
    PImage punishImage     ;
    PImage rewardImage     ;
    PImage superrewardImage;
    PImage trailImage      ;
    PImage wallImage       ;


    PImage StartImage      ;
    PImage PauseImage      ;

    public void setup() {
        surface.setLocation(0, 0);
        for (int i = 0; i < initial_number_of_enemies; i++) {
            enemies.add(new P_Enemies(enemy_HP, grid2));
        }

        enemyImage       = loadImage("enemy276.png");
        exitImage        = loadImage("exit276.png");
        pathImage        = loadImage("path276.png");
        playerImage      = loadImage("player276.png");
        punishImage      = loadImage("punish276.png");
        rewardImage      = loadImage("reward276.png");
        superrewardImage = loadImage("superreward276.png");
        trailImage       = loadImage("trail276.png");
        wallImage        = loadImage("wall276.png");
        wallImage.filter(INVERT);

        StartImage        = loadImage("Menu1_276.png");
        PauseImage        = loadImage("PauseMenu276.png");



    }
    
    /**
     * Renders the game elements on the screen.
     * Loops the game until it is finished
     */
    public void draw() {
        background(0);
        noStroke();
        PFont myFont;
        myFont = createFont("Arial Black", 32);
        textFont(myFont);

        if (gameStarted == 0) {

            image(StartImage,0,0);
            if (key == '1') {
                time = System.currentTimeMillis();
                gameStarted++;
            }
            if (key == '2')
                exit();
        } else {
            if (gamePaused == 0 && levelOver == 0 && gameOver == 0) {
                
                drawMap();

                drawPlayer();
                
                drawEnemies();

//                PImage photo;
//                photo = loadImage("Phase1UML-1.png");
//                image(photo, 0, 0);
            }
            // Called if the level is completed 
            if (levelOver == 1) {
                drawLevelOver();
            }
            // Called if the player loses the game
            if (gameOver == 1) {
                drawGameOver();
            }
            // Called if the player pauses the game
            if (gamePaused == 1) {
                drawPaused();
                // System.out.println(time_paused);
            }
            // Checks if the player picked up all the rewards
            if (player.getRewards() >= grid2.getNumRewards())
                grid2.setGrid(mapsize-2, mapsize-1, 'e');
            // else
            //     grid2.setGrid(mapsize-2, mapsize-1, 'e');
            // Resumes from being paused
            if (key == 'p' && gamePaused == 0) {
                gamePaused = 1;
                time_paused = System.currentTimeMillis();
            }
            // Ends the level if the player reaches the exit
            if (grid2.getGrid()[player.getXPos()[0]][player.getYPos()[0]] == 'e' && levelOver == 0) {
                levelOver = 1;
                time_paused = System.currentTimeMillis();
            }
            // Loses the game if the player's score is less than zero
            if (player.getScore() < 0) 
                gameOver = 1;
            // Loses the game if the player's caught by an enemy
            for (int i = 0; i < current_number_of_enemies; i++) {
                if ((player.getXPos()[0] == enemies.get(i).getXPos()) && (player.getYPos()[0] == enemies.get(i).getYPos()))
                    gameOver = 1;
                }
            // Exits the game
            if (key == '2')
                exit();
        }
    }

    private void drawMap(){
        for (int i = 0; i < mapsize; i++) {
                    for (int j = 0; j < mapsize; j++) {
                        if (grid2.getGrid()[i][j] == 'w')
                            image(wallImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        if (grid2.getGrid()[i][j] == 'p')
                            image(pathImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        if (grid2.getGrid()[i][j] == 'm') {
                            fill(163 - 100, 124 - 100, 110 - 100);
                            rect(i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        }
                        if (grid2.getGrid()[i][j] == 'e')
                            image(exitImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        if (grid2.getGrid()[i][j] == 'r') {
                            image(pathImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                            image(rewardImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        }
                        if (grid2.getGrid()[i][j] == 'b') {
                            image(pathImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                            image(superrewardImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        }
                        if (grid2.getGrid()[i][j] == 'u') {
                            image(pathImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                            image(punishImage, i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                        }
                        //rect(i * width / mapsize, j * width / mapsize, width / mapsize, width / mapsize);
                    }
                }
    }

    private void drawPlayer(){
        for (int i = 1; i < player.getTrailLength(); i++) {
                    //fill(255 - i * 8, 126 - i * 8, 34);
                    //rect(player.getXPos()[i] * width / mapsize, player.getYPos()[i] * width / mapsize, width / mapsize, width / mapsize);
                    image(trailImage, player.getXPos()[i] * width / mapsize, player.getYPos()[i] * width / mapsize, width / mapsize, width / mapsize);
                }
                //fill(255, 186, 64);
                //rect(player.getXPos()[0] * width / mapsize, player.getYPos()[0] * width / mapsize, width / mapsize, width / mapsize);
                image(playerImage, player.getXPos()[0] * width / mapsize, player.getYPos()[0] * width / mapsize, width / mapsize, width / mapsize);
                player.movePlayer(keyPressed, key, grid2);

                textSize(20);
                fill(255);
                text("POINTS: " + player.getScore(), 40, (float) (0.65 * (width / mapsize)));
                text("TIME: " + msToS(time), 200, (float) (0.65 * (width / mapsize)));
                text("PRESS P TO VIEW CONTROLS", 400, (float) (0.65 * (width / mapsize)));
    }

    private void drawEnemies(){
        for (int i = 0; i < current_number_of_enemies; i++) {
                    //fill(i*10, i*20, 180+i*20);
                    //rect(enemies.get(i).xpos * width / mapsize, enemies.get(i).ypos * width / mapsize, width / mapsize, width / mapsize);
                image(enemyImage, enemies.get(i).getXPos() * width / mapsize, enemies.get(i).getYPos() * width / mapsize, width / mapsize, width / mapsize);
                    boolean flag = enemies.get(i).enemiesMove(player, grid2);
                    if(!flag){
                        enemies.remove(i);
                        current_number_of_enemies--;
                    }
                    
                }
    }

    private void drawLevelOver(){
        fill(0);
        rect(0, 0, width, height);
        fill(255);
        resetPlayer();
        text("SCORE: " + player.getScore(), 40, (float) (3.65 * (width / mapsize)));
        text("PRESS 1 TO START NEW LEVEL", 40, (float) (6.65 * (width / mapsize)));
        if (key == '1') {
            rewardClient.setNumRewards(rewardClient.getNumRewards()+15);
            rewardClient.clear();
            grid2 = new P_Grid(mapsize);
            initial_number_of_enemies = initial_number_of_enemies +4;
            resetEnemies();
            time += getTimeElapsed(time_paused);
            levelOver = 0;
        }
        text("PRESS 2 TO EXIT PROGRAM", 40, (float) (8.65 * (width / mapsize)));
        if (key == '2')
            exit();
    }

    private void drawGameOver(){
        fill(0);
        rect(0, 0, width, height);
        fill(255);
        text("GAME OVER!", 40, (float) (3.65 * (width / mapsize)));
        text("SCORE: " + player.getScore(), 40, (float) (4.65 * (width / mapsize)));
        text("PRESS 1 TO PLAY AGAIN", 40, (float) (6.65 * (width / mapsize)));
        text("PRESS 2 TO EXIT PROGRAM", 40, (float) (8.65 * (width / mapsize)));
        if (key == '1') {
            rewardClient.setNumRewards(15);
            player.setScore(0);
            rewardClient.clear();
            grid2 = new P_Grid(mapsize);
            resetPlayer();
            initial_number_of_enemies=4;
            resetEnemies();
            
            time = System.currentTimeMillis();
            gameOver = 0;
        }
        if (key == '2') {
            exit();
        }
    }

    private void drawPaused(){
//        fill(0);
//        rect(0, 0, width, height);
//        fill(255);
//        text("PRESS R TO RESUME GAME", 40, (float) (6.65 * (width / mapsize)));
//        text("CONTROLS:", 40, (float) (7.65 * (width / mapsize)));
//        text("W --> MOVE UP", 80, (float) (8.65 * (width / mapsize)));
//        text("S --> MOVE DOWN", 80, (float) (9.65 * (width / mapsize)));
//        text("A --> MOVE LEFT", 80, (float) (10.65 * (width / mapsize)));
//        text("D --> MOVE RIGHT", 80, (float) (11.65 * (width / mapsize)));
//        text("2 --> TO EXIT PROGRAM", 80, (float) (12.65 * (width / mapsize)));
        image(PauseImage,0,0);
        if (key == 'r') {
            gamePaused = 0;
            time += getTimeElapsed(time_paused);
        }
    }
    /**
     * Resets the player's position and score.
     */
    private void resetPlayer() {
        int temp = player.getScore();
        player = new P_Player();
        player.setScore(temp);
    }

    /**
     * Resets the enemies in the game.
     */
    private void resetEnemies() {
        enemies.clear();
        current_number_of_enemies = initial_number_of_enemies;
        for (int i = 0; i < initial_number_of_enemies; i++) {
            enemies.add(new P_Enemies(enemy_HP, grid2));
        }
    }

    private long getTimeElapsed(long input) {
        return System.currentTimeMillis() - input;
    }

    private double msToS(long input) {
        double temp = (double) System.currentTimeMillis() - input;
        return (temp/1000);
    }

    /**
     * Overridden keyPressed method to handle key releases.
     */
    @Override
    public void keyPressed() {
        super.keyReleased();
        if (player.keyRel == 0) {
            player.keyRel = 1;

        } else {
            player.keyRel = 0;
        }
    }
}
