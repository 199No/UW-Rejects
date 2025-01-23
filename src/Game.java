package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import Enemies.*;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    
    public static final boolean inDebugMode = true;
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
    private ArrayList<EnvObject> envObjects = new ArrayList<EnvObject>();

    // Bounds
    private double xMin = 0;
    private double xMax = 4080;
    private double yMin = 0;
    private double yMax = 4080;

    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{

        this.player1 = new Player(750,300.0,100,10,10,1,2);
        this.player2 = new Player(500.0, 500.0, 100, 10, 10, 1,1);
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
        /////////////////////
        //// CALCULATE FPS
        ////////////////////
        now = System.currentTimeMillis();

        if(now - lastSecond > 1000){

            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;

        } else{

            framesLastSecond ++;

        }

        ////////////////
        //Update Player 
        ////////////////
        updatePlayer(player1);
        updatePlayer(player2);

        // Update enemies
        for(int i = 0; i < this.enemies.size(); i++){

            for(int j = 0; j < this.players.size(); j++){
                enemies.get(i).scanArea(players.get(j).getLocation());
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
        // Update camera position
        gui.moveCamera(((players.get(0).getxPos() + players.get(1).getxPos()) / 2 - gui.cameraX()) / 10, ((players.get(0).getyPos() + players.get(1).getyPos()) / 2 - gui.cameraY()) / 10);
        

        // Draw the first layer of the environment (behind both players)
        for(int i = 0; i < map.numLoadedChunks(); i++){
            gui.drawEnvLayer1(map.getChunk(i), player1.getyPos(), player2.getyPos());
        }

        // Draw whichever player is farthest back
        if(player1.getyPos() < player2.getyPos()){
            gui.drawPlayer(players.get(0), input);
        } else {
            gui.drawPlayer(players.get(1), input);
        }  

        // Draw the second layer of the environment (between both players)
        for(int i = 0; i < map.numLoadedChunks(); i++){
            gui.drawEnvLayer2(map.getChunk(i), player1.getyPos(), player2.getyPos());
        }

        // Draw whichever player is farthest forward
        if(player1.getyPos() < player2.getyPos()){
            gui.drawPlayer(players.get(1), input);
        } else {
            gui.drawPlayer(players.get(0), input);
        }  

        // Draw the third layer of the environemt (in front of both players)
        for(int i = 0; i < map.numLoadedChunks(); i++){
            gui.drawEnvLayer3(map.getChunk(i), player1.getyPos(), player2.getyPos());
        }


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

    public void inBounds(Player player){
        if(player.getxPos() > this.xMax){
            player.setxPos(this.xMax);
        }
        if(player.getxPos() < this.xMin){
            player.setxPos(this.xMin);
        }
        if(player.getyPos() > this.yMax){
            player.setyPos(this.yMax);
        }
        if(player.getyPos() < this.yMin){
            player.setyPos(this.yMin);
        }
    }

    public void updatePlayer(Player player){
            
        // Get input information
        boolean[] keys = input.getKeys();
        boolean[] shifts = input.getShifts();

        // Update player movement with input information
        updatePlayerMovement(player, input.getPlayerKeys(player), shifts[player.playernum -1], keys);

        // Handle    player actions (attack block) with input information
        handlePlayerActions(player, keys, input.getPlayerKeys(player)[5], input.getPlayerKeys(player)[4]); // Attack: 5th key in list, Block: 4th key in list

        // Handle player dashing

        if ((int) System.currentTimeMillis() - input.getLastDash() < player.dashLength) {
            handlePlayerDash(player, input.getPlayerKeys(player));
        } else {
            if(player.getIsDashing()){
                player.setIsDashing(false);
            }
        }

        inBounds(player);
        
    }

    private void updatePlayerMovement(Player player, int[] playerKeys, boolean shift, boolean[] keys) {
        // w a s d OR i j k l
        boolean[] movement = new boolean[] {
            keys[playerKeys[0]], // Up
            keys[playerKeys[1]], // Left
            keys[playerKeys[2]], // Down
            keys[playerKeys[3]]  // Right
        };
        player.move(movement, shift);
        ////////////////
        /// COLLISION
        ///////////////
        for(int i = 0; i < map.numLoadedChunks(); i++){
            EnvObject[] envObjects = map.getChunk(i).getEnvObjects();
            EnvObject obj;
            Rectangle pHitbox, objHitbox;
            Rectangle2D clip;
            for(int k = 0; k < envObjects.length; k++){
                obj = envObjects[k];
                pHitbox = player.getHitbox();
                objHitbox = obj.getHitbox();
                if(player.getHitbox().intersects(obj.getHitbox())){
                    clip = objHitbox.createIntersection(pHitbox);
                    // Horizontal collide
                    if(clip.getHeight() > clip.getWidth()){
                        // Right collide
                        if(pHitbox.getX() > objHitbox.getX()){
                            player.setxPos(objHitbox.getMaxX());
                        }   
                        // Left collide
                        if(pHitbox.getX() < objHitbox.getX()){
                            player.setxPos(objHitbox.getX() - player.getWidth());
                        }
                    }
                    else if (clip.getWidth() > clip.getHeight()){
                        
                        // Right collide
                        if(pHitbox.getY() > objHitbox.getY()){
                            player.setyPos(objHitbox.getMaxY());
                        }   
                        // Left collide
                        if(pHitbox.getY() < objHitbox.getY()){
                            player.setyPos(objHitbox.getY() - player.getHeight());
                        }
                    }
                }
            }
        }

    }

    private void handlePlayerActions(Player player, boolean[] keys, int attackKey, int blockKey) {
         // Check if the attack is currently on cooldown
    if (player.getIsAttacking() && (int) System.currentTimeMillis() - player.getLastAttack() > player.getAttackCooldown()) {
        player.setIsAttacking(false);
    }

    // Restrict attack action
    if (keys[attackKey]) {
        if (!player.getIsAttacking() &&  // Player is not already attacking
            !player.getIsBlocking() &&  // Player is not blocking
            (int) System.currentTimeMillis() - player.getLastAttack() > player.getAttackCooldown()) { // Cooldown check
            player.attack(); // Trigger attack
        }
    }

    // Restrict block action
    if (keys[blockKey]) {
        if (!player.getIsAttacking()) { // Ensure player is not attacking
            player.block(); // Trigger block
        }
    }
    }

    private void handlePlayerDash(Player player, int[] playerKeys) {
        for(int i = 0; i < playerKeys.length; i++){
            if(playerKeys[i] == input.getDash()){
                if(!player.getIsDashing()){
                    player.dash(playerKeys[i], 8);
                    player.setIsDashing(true);
                }else{
                    player.dash(playerKeys[i], 8);
                }
            }
        }
        
    }

}
