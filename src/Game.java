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
        enemies.add(createSlime(100, 300));
        enemies.add(createSlime(700, 400));
        enemies.add(createSlime(750, 5000));
        enemies.add(createSlime(6000, 600));
        enemies.add(createSlime(5000, 5000));
        enemies.add(createSlime(5000, 300));

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
    
        if (now - lastSecond > 1000) {
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond++;
        }
    
        ////////////////
        // Update Player
        ////////////////
        updatePlayer(player1);
        updatePlayer(player2);
    
        // Update enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemies enemy = enemies.get(i);
            for (int p = 0; p < this.players.size(); p++) {
                enemy.scanArea(players.get(p).getLocation());
            }
    
            if (enemy.getAlert()) {
                enemy.moveToward(enemy.getLastSeen());
            } else {
                enemy.idleMove();
            }
    
            ////////////////
            /// COLLISION
            ///////////////
            for (int c = 0; c < map.numLoadedChunks(); c++) {
                EnvObject[] envObjects = map.getChunk(c).getEnvObjects();
                for (int j = 0; j < envObjects.length; j++) {
                    EnvObject obj = envObjects[j];
                    Rectangle eHitbox = enemy.getHitbox();
                    Rectangle objHitbox = obj.getHitbox();
    
                    if (eHitbox.intersects(objHitbox)) {
                        Rectangle2D clip = objHitbox.createIntersection(eHitbox);
    
                        // Horizontal collision
                        if (clip.getHeight() > clip.getWidth()) {
                            // Right collision
                            if (eHitbox.getX() > objHitbox.getX()) {
                                enemy.setxPos(objHitbox.getMaxX());
                            }
                            // Left collision
                            if (eHitbox.getX() < objHitbox.getX()) {
                                enemy.setxPos(objHitbox.getX() - enemy.getWidth());
                            }
                        } else if (clip.getWidth() > clip.getHeight()) {
                            // Top collision
                            if (eHitbox.getY() > objHitbox.getY()) {
                                enemy.setyPos(objHitbox.getMaxY());
                            }
                            // Bottom collision
                            if (eHitbox.getY() < objHitbox.getY()) {
                                enemy.setyPos(objHitbox.getY() - enemy.getHeight());
                            }
                        }
                    }
                }
            }
        }
    
        // Draw the background (happens before all other draw commands)
        gui.background(0, 0, 0);
        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawChunk(map.getChunk(c));
        }
    
        // Draw dash bars
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g) {
                double height1 = (((double) System.currentTimeMillis() - (double) input.getLastp1Dash()) / 5000) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(0, Gui.HEIGHT - (int) height1, 30, (int) height1);
    
                double height2 = (((double) System.currentTimeMillis() - (double) input.getLastp2Dash()) / 5000) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(Gui.WIDTH - 30, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(Gui.WIDTH - 30, Gui.HEIGHT - (int) height2, 30, (int) height2);
            }
        });
    
        // Update camera position
        gui.moveCamera(
            ((players.get(0).getxPos() + players.get(1).getxPos()) / 2 - gui.cameraX()) / 10,
            ((players.get(0).getyPos() + players.get(1).getyPos()) / 2 - gui.cameraY()) / 10
        );
    
        // Draw the first layer of the environment (behind both players)
        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawEnvLayer1(map.getChunk(c), player1.getyPos(), player2.getyPos());
        }
    
        // Draw whichever player is farthest back
        if (player1.getyPos() < player2.getyPos()) {
            gui.drawPlayer(players.get(0), input);
        } else {
            gui.drawPlayer(players.get(1), input);
        }
    
        // Draw the second layer of the environment (between both players)
        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawEnvLayer2(map.getChunk(c), player1.getyPos(), player2.getyPos());
        }
    
        // Draw whichever player is farthest forward
        if (player1.getyPos() < player2.getyPos()) {
            gui.drawPlayer(players.get(1), input);
        } else {
            gui.drawPlayer(players.get(0), input);
        }
    
        // Draw the third layer of the environment (in front of both players)
        for (int f = 0; f < map.numLoadedChunks(); f++) {
            gui.drawEnvLayer3(map.getChunk(f), player1.getyPos(), player2.getyPos());
        }
    
        gui.drawEnemies(this.enemies);
        gui.drawHitboxes(this.players, this.enemies);
        checkHitboxes(this.players, this.enemies);
        despawnSwingHitbox(this.players);
        gui.displayFPS((int) frameRate);
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
    public void inBounds(Enemies enemy){
        if(enemy.getxPos() > this.xMax){
            enemy.setxPos(this.xMax);
        }
        if(enemy.getxPos() < this.xMin){
            enemy.setxPos(this.xMin);
        }
        if(enemy.getyPos() > this.yMax){
            enemy.setyPos(this.yMax);
        }
        if(enemy.getyPos() < this.yMin){
            enemy.setyPos(this.yMin);
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
    public void checkHitboxes(ArrayList<Player> players, ArrayList<Enemies> enemies) {

        for(int p = 0; p < players.size(); p++){
        for (int i = 0; i < enemies.size(); i++) {
            Enemies enemy = enemies.get(i);

            // Check if the swing hitbox intersects the enemy's hitbox
            if (players.get(p).getSwingHitbox().intersects(enemy.getHitbox())) {
                // Deal damage to the enemy
                enemy.takeDamage(players.get(p).getDamage());

                // Optionally, handle enemy death
                if (!enemy.isAlive()) {
                    System.out.println("Enemy defeated!");
                    enemies.remove(i);
                    i--; // Adjust index after removal
                }
            }
        }
    }
    }
    public void despawnSwingHitbox(ArrayList<Player> players){
        for(int p = 0; p < players.size(); p++){
            if((int) System.currentTimeMillis() - players.get(p).getLastAttack() > players.get(p).getAttackLength()){
                players.get(p).getSwingHitbox().setBounds(-1000, 1000, 1, 1);
            }
        }
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
                    player.dash(playerKeys[i], 5);
                    player.setIsDashing(true);
                }else{
                    player.dash(playerKeys[i], 5);
                }
            }
        }
        
    }

}
