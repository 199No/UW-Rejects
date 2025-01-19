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
        gui.drawPlayers(this.players);
        gui.drawEnemies(this.enemies);
        gui.drawHitboxes(this.players, this.enemies);
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
    if ((int) System.currentTimeMillis() - input.getLastDash(player1) < player.dashLength) {
        handlePlayerDash(player1, input.getPlayer1Keys());
    }

    if ((int) System.currentTimeMillis() - input.getLastDash(player2) < player.dashLength) {
        handlePlayerDash(player2, input.getPlayer2Keys());
    }
        
        
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
        for (int key : playerKeys) {
            if (key == input.getDash()) {
                if(!player.getIsDashing()){
                    //first instance of dash
                    player.dash(key, 8);
                    player.setIsDashing(true);
                }else{
                    //is dashing, not first instance
                    player.dash(key, 8);
                }
            }
        }
    
        // Check if the player is done dashing
        if ((int) System.currentTimeMillis() - player.getLastDash() > player.dashLength) {
            player.setIsDashing(false);
        }
    }

}
