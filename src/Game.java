package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import Enemies.*;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;



//-------------------------------------------------//
//                     Game                        //
//-------------------------------------------------// 

public class Game implements ActionListener{

    ///////////////
    //Properties
    ///////////////
    Timer gameTimer;
    Gui gui;
    Player player1;
    Player player2;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    BufferedImage image;
    int pDashing;
    int dashing;
    Map map;
    boolean[] movement = new boolean[4];
    Chunk chunk1;
    Chunk chunk2;
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    private ArrayList<Player>  players = new ArrayList<Player>();


    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{

        this.player1 = new Player(750,300.0,100,10,10,2.5,1);
        this.player2 = new Player(500.0, 500.0, 100, 10, 10, 2.5,2);
        players.add(player1);
        players.add(player2);
        this.input = new Input();
        enemies.add(createSlime(750, 300));
        gui = new Gui(1280, 720, input);
        map = new Map("Maps/map1.map", "Maps/map1Env.map");
        // Only these four lines should happen after this comment otherwise stuff will break
        gameTimer = new Timer(2, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    @Override

    public void actionPerformed(ActionEvent e) {
        // Calculate FPS
        now = System.currentTimeMillis();

        if(now - lastSecond > 1000){

            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;

        } 

        else{

            framesLastSecond ++;

        }

        ////////////////
        /// Input
        ///////////////
    

        // Input updates its copy of the player
        //update Player based on Input Information
        updatePlayer(player1);
        updatePlayer(player2);

        // Update enemies
        for(int i = 0; i < this.enemies.size(); i++){

            for(int j = 0; j < this.players.size(); j++){
                enemies.get(i).scanArea(players.get(j));
            }

            if(enemies.get(i).getAlert()){
                enemies.get(i).moveToward(enemies.get(i).getLastSeen());
            }else{
                enemies.get(i).idleMove();
            }
        }
        //gui.addToQueue(new GraphicsRunnable() {
        //    public void draw(Graphics2D g2d){
        //        g2d.drawImage(image.getScaledInstance(Gui.WIDTH, Gui.HEIGHT, 0), 0, 0, null);
        //    }
        //});
        // Draw the background (Happens before ALL other draw commands)
        // Draw the ground
        
        gui.background(0, 0, 0);
        for(int i = 0; i < map.numLoadedChunks(); i++){
            gui.drawChunk(map.getChunk(i));
        }
        
        // Draw dash bars
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g){
                double height1 = (((double)(int)System.currentTimeMillis() - (double)input.getLastp1Dash()) / 5000) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(0, Gui.HEIGHT - (int)height1, 30, (int)height1);

                double height2 = (((double)(int)System.currentTimeMillis() - (double)input.getLastp2Dash()) / 5000) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(Gui.WIDTH - 30, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(Gui.WIDTH - 30, Gui.HEIGHT - (int)height2, 30, (int)height2);

            }
        });
        gui.moveCamera(((players.get(0).getxPos() + players.get(1).getxPos()) / 2 - gui.cameraX()) / 10, ((players.get(0).getyPos() + players.get(1).getyPos()) / 2 - gui.cameraY()) / 10);
        gui.drawPlayers(players);
        gui.drawEnemies(enemies);
        gui.drawHitboxes(players, enemies);
        gui.displayFPS((int)frameRate);
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public Slime createSlime(double x, double y){
        return new Slime(x,y); //make a slime given a x and y
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public void updatePlayer(Player player){
        
        
        // Get input information
    boolean[] keys = input.getKeys();
    boolean[] shifts = input.getShifts();

    // Update player movement
    updatePlayerMovement(player1, input.getPlayer1Keys(), shifts[0], keys);
    updatePlayerMovement(player2, input.getPlayer2Keys(), shifts[1], keys);

    // Handle player actions
    handlePlayerActions(player1, keys, 67, 88); // Attack: C, Block: X
    handlePlayerActions(player2, keys, 78, 77); // Attack: N, Block: M

    // Handle player dashing
    handlePlayerDash(player1, input.getPlayer1Keys());
    handlePlayerDash(player2, input.getPlayer2Keys());
        
        
        
        /* 
        //get input information
        boolean[] keys   = input.getKeys();
        boolean[] shifts = input.getShifts();
        //move player based on shift and keys pressed

        this.movement = new boolean[]{keys[this.input.getPlayer1Keys()[0]], keys[this.input.getPlayer1Keys()[1]], keys[this.input.getPlayer1Keys()[2]], keys[this.input.getPlayer1Keys()[3]]};  // W A S D
        player1.move(movement, shifts[0]);
        this.movement = new boolean[]{keys[this.input.getPlayer2Keys()[0]], keys[this.input.getPlayer2Keys()[1]], keys[this.input.getPlayer2Keys()[2]], keys[this.input.getPlayer2Keys()[3]]};  // I J K L
        player2.move(movement, shifts[1]);

        //ATTACK
        if(keys[67]){
            //C
            this.player1.attack();
        }
        if(keys[78]){
            //N
            this.player2.attack();
        }
        //BLOCK
        if(keys[88]){
            //X
            this.player1.block();
        }
        if(keys[77]){
            //M
            this.player2.block();
        }
        //DASH
        //can the player dash?

        if((int) System.currentTimeMillis() - input.getLastp1Dash() < player1.dashLength){
            for(int i = 0; i < this.input.getPlayer1Keys().length; i++){
                if(this.input.getPlayer1Keys()[i] == input.getDash() && !this.player1.getIsDashing()){
                    player1.dash(i);
                    this.player1.setIsDashing(true);
                }else if(this.input.getPlayer1Keys()[i] == input.getDash() && this.player1.getIsDashing()){
                    player1.dash(i);
                }
            }
        }

        if((int) System.currentTimeMillis() - input.getLastp2Dash() < player2.dashLength){
            for(int i = 0; i < this.input.getPlayer2Keys().length; i++){
                if(this.input.getPlayer2Keys()[i] == input.getDash() && !this.player2.getIsDashing()){
                    player2.dash(i);
                    this.player2.setIsDashing(true);
                }else if(this.input.getPlayer2Keys()[i] == input.getDash() && this.player2.getIsDashing()){
                    player2.dash(i);
                }
            }
        }

        //is the player done dashing
        if((int) System.currentTimeMillis() - player1.getLastDash() > player1.dashLength){
            this.player1.setIsDashing(false);
        }

        if((int) System.currentTimeMillis() - player2.getLastDash() > player2.dashLength){
            this.player2.setIsDashing(false);
        }
        */
    }

    private void updatePlayerMovement(Player player, int[] playerKeys, boolean shift, boolean[] keys) {
        boolean[] movement = new boolean[] {
            keys[playerKeys[0]], // Up
            keys[playerKeys[1]], // Left
            keys[playerKeys[2]], // Down
            keys[playerKeys[3]]  // Right
        };
        player.move(movement, shift);
    }
    private void handlePlayerActions(Player player, boolean[] keys, int attackKey, int blockKey) {
        if (keys[attackKey]) {
            player.attack();
        }
        if (keys[blockKey]) {
            player.block();
        }
    }

    private void handlePlayerDash(Player player, int[] playerKeys) {
        if ((int) System.currentTimeMillis() - input.getLastDash(player) < player.dashLength) {
            for (int key : playerKeys) {
                if (key == input.getDash()) {
                    if (!player.getIsDashing()) {
                        player.dash(key);
                        player.setIsDashing(true);
                    } else {
                        player.dash(key);
                    }
                }
            }
        }
    
        // Check if the player is done dashing
        if ((int) System.currentTimeMillis() - player.getLastDash() > player.dashLength) {
            player.setIsDashing(false);
        }
    }

}
