package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import Enemies.Slime;
import Enemies.Enemies;
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
import java.util.Random;
import java.util.Random;
import javax.swing.Timer;
//unrejar


//-------------------------------------------------//
//                     Game                        //
//-------------------------------------------------// 

public class Game implements ActionListener{

    ///////////////
    //Properties
    ///////////////
    //very unrejar
    public static final boolean inDebugMode = true;
    Random random;
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
    private ArrayList<Enemies> enemies  = new ArrayList<Enemies>();
    private ArrayList<Player>  players  = new ArrayList<Player>();
    private ArrayList<Entity>  entities = new ArrayList<Entity>();
    // Bounds
    private double xMin = 0;
    private double xMax = 4080;
    private double yMin = 0;
    private double yMax = 4080;

    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{

        this.player1 = new Player(750.0, 300.0, 100, 10, 1.3, 2);
        this.player2 = new Player(500.0, 500.0, 100, 10, 1.3, 1);
        players.add(player1);
        players.add(player2);
        this.input = new Input();
        //TODO: find out how to actually make slime like normal
        // Use the slime constructorsdds
        enemies.add(createSlime(100, 300, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle(100, 300, Gui.TILE_SIZE, Gui.TILE_SIZE)));
        random = new Random();
        gui = new Gui(1280, 720, input);
        map = new Map("Maps/map1.map", "Maps/map1Env.map");


        //How to say if its in desert do wind if in grass do country
        if(false){
            Sounds.playSound("WindBackground");
        }
        if(true){
            Sounds.playSound("Countryside");
        }

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
            
            // enemies.add(
            //     new Slime(random.nextInt(0, Gui.WIDTH), random.nextInt(0, Gui.HEIGHT), Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle(0, 0, Gui.TILE_SIZE, Gui.TILE_SIZE))
            // );
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
                enemy.scanArea(new int[]{(int) players.get(p).getLocation()[0],  (int) players.get(p).getLocation()[1]});
            }
    
            if (enemy.getAlert()) {
                enemy.moveToward(enemy.getLastSeen());
            } else {
                enemy.idleMove();
            }
    
            ////////////////
            /// COLLISION
            ///////////////
            // TODO: Make a collide(Entity e1, Entity e2) method
            for (int c = 0; c < map.numLoadedChunks(); c++) {
                EnvObject[] envObjects = map.getChunk(c).getEnvObjects();
                for (int j = 0; j < envObjects.length; j++) {
                    EnvObject obj = envObjects[j];
                    Rectangle eHitbox = enemy.getRelHitbox();
                    Rectangle objHitbox = obj.getAbsHitbox();
    
                    if (eHitbox.intersects(objHitbox)) {
                        Rectangle2D clip = objHitbox.createIntersection(eHitbox);
    
                        // Horizontal collision
                        if (clip.getHeight() > clip.getWidth()) {
                            // Right collision
                            if (eHitbox.getX() > objHitbox.getX()) {
                                enemy.setX(objHitbox.getMaxX());
                            }
                            // Left collision
                            if (eHitbox.getX() < objHitbox.getX()) {
                                enemy.setX(objHitbox.getX() - enemy.getWidth());
                            }
                        } else if (clip.getWidth() > clip.getHeight()) {
                            // Top collision
                            if (eHitbox.getY() > objHitbox.getY()) {
                                enemy.setY(objHitbox.getMaxY());
                            }
                            // Bottom collision
                            if (eHitbox.getY() < objHitbox.getY()) {
                                enemy.setY(objHitbox.getY() - enemy.getHeight());
                            }
                        }
                    }
                }
            }
        }
        
        // Update entity list
        entities.addAll(enemies);
        entities.addAll(players);
        entities.addAll(map.getAllEnvObjects());
        // Draw the background (happens before all other draw commands)
        gui.background(0, 0, 0);
        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawChunk(map.getChunk(c));
        }
    
        // Draw dash bars
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g) {
                double height1 = (((double)(int) System.currentTimeMillis() - (double) Input.getLastp1Dash()) / (double)Input.DASH_COOLDOWN) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(0, Gui.HEIGHT - (int) height1, 30, (int) height1);
    
                double height2 = (((double)(int) System.currentTimeMillis() - (double) Input.getLastp2Dash()) / (double)Input.DASH_COOLDOWN) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(Gui.WIDTH - 30, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(Gui.WIDTH - 30, Gui.HEIGHT - (int) height2, 30, (int) height2);
            }
        });
    
        // Update camera position
        gui.moveCamera(
            ((players.get(0).getX() + players.get(1).getX()) / 2 - gui.cameraX()) / 10,
            ((players.get(0).getY() + players.get(1).getY()) / 2 - gui.cameraY()) / 10
        );
        // Draw all Entities (players, enemies, envObjects, etc.)
        for(int i = 0; i < entities.size(); i++){
            gui.drawEntity(entities.get(i));
        }

        checkHitboxes(this.players, this.enemies);
        despawnSwingHitbox(this.players);
        gui.displayFPS((int) frameRate);
        gui.repaint();
        now = System.currentTimeMillis();
        
        entities.clear();
    }

    public Slime createSlime(double x, double y, double width, double height, Rectangle hitbox){
        return new Slime(x,y,width,height,hitbox); //make a slime given a x and y
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public void inBounds(Player player){
        if(player.getX() > this.xMax){
            player.setX(this.xMax);
        }
        if(player.getX() < this.xMin){
            player.setX(this.xMin);
        }
        if(player.getY() > this.yMax){
            player.setY(this.yMax);
        }
        if(player.getY() < this.yMin){
            player.setY(this.yMin);
        }
    }
    public void inBounds(Enemies enemy){
        if(enemy.getX() > this.xMax){
            enemy.setX(this.xMax);
        }
        if(enemy.getX() < this.xMin){
            enemy.setX(this.xMin);
        }
        if(enemy.getY() > this.yMax){
            enemy.setY(this.yMax);
        }
        if(enemy.getY() < this.yMin){
            enemy.setY(this.yMin);
        }
    }

    public void updatePlayer(Player player){
            
        // Get input information
        boolean[] keys = Input.getKeys();
        boolean[] shifts = Input.getShifts();

        // Update player movement with input information
        updatePlayerMovement(player, Input.getPlayerKeys(player), shifts[player.playernum -1], keys);

        // Handle player dashing

        if ((int) System.currentTimeMillis() - Input.getLastDash() < player.getDashLength()) {
            handlePlayerDash(player, Input.getPlayerKeys(player));
        } else {
            if(player.getIsDashing()){
                player.setIsDashing(false);
            }
        }

        // Handle player blocking

        if ((int) System.currentTimeMillis() - player.getLastBlock() < player.getBlockLength()) {
           handlePlayerBlock(player, Input.getPlayerKeys(player)[4], keys);
        } else {
            if(player.getIsBlocking()){
                player.setIsBlocking(false);
            }
        }

        // Handle player attack

        if ((int) System.currentTimeMillis() - player.getLastAttack() < player.getAttackLength()) {
            handlePlayerAttack(player, Input.getPlayerKeys(player)[5], keys);
         } else {
             if(player.getIsBlocking()){
                 player.setIsAttacking(false);
             }
         }

        inBounds(player);
        
    }
    //TODO: Make this better
    public void checkHitboxes(ArrayList<Player> players, ArrayList<Enemies> enemies) {

        for(int p = 0; p < players.size(); p++){
        for (int i = 0; i < enemies.size(); i++) {
            Enemies enemy = enemies.get(i);

            if (players.get(p).getSwingHitbox().intersects(enemy.getAbsHitbox())) {
                enemy.takeDamage(players.get(p).getDamage());

                if (!enemy.getIsAlive()) {
                    enemies.remove(i);
                    i--;
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
                pHitbox = player.getAbsHitbox();
                objHitbox = obj.getAbsHitbox();
                if(pHitbox.intersects(objHitbox)){
                    clip = objHitbox.createIntersection(pHitbox);
                    // Horizontal collide
                    if(clip.getHeight() > clip.getWidth()){
                        // Right collide
                        if(pHitbox.getX() > objHitbox.getX()){
                            player.setX(player.getX() + clip.getWidth());
                        }   
                        // Left collide
                        if(pHitbox.getX() < objHitbox.getX()){
                            player.setX(player.getX() - clip.getWidth());
                        }
                    }
                    else if (clip.getWidth() > clip.getHeight()){
                            
                        // Right collide
                        if(pHitbox.getY() > objHitbox.getY()){
                            player.setY(player.getY() + clip.getHeight());
                        }   
                        // Left collide
                        if(pHitbox.getY() < objHitbox.getY()){
                            player.setY(player.getY() - clip.getHeight());
                        }
                    }
                }
            }
        }

    }

    private void handlePlayerAttack(Player player, int attackKey, boolean[] keys){

        if (player.getIsAttacking() && (int) System.currentTimeMillis() - player.getLastAttack() > player.getAttackLength()) {
            player.setIsAttacking(false);
        }

        if (keys[attackKey]) {
            if (!player.getIsAttacking() &&  // Player is not already attacking
                !player.getIsBlocking() &&  // Player is not blocking
                (int) System.currentTimeMillis() - player.getLastAttack() > player.getAttackCooldown()) { // Cooldown check
                player.attack(); // Trigger attack
            }
        }
    }

    private void handlePlayerBlock(Player player, int blockKey, boolean[] keys){

        if (player.getIsBlocking() && (int) System.currentTimeMillis() - player.getLastBlock() > player.getBlockLength()) {
            player.setIsBlocking(false);
        }

        if (keys[blockKey]) {
            if (!player.getIsBlocking() &&  // Player is not already blocking
                !player.getIsAttacking() &&  // Player is not attacking
                (int) System.currentTimeMillis() - player.getLastBlock() > player.getBlockCooldown()) { // Cooldown check
                player.block(); // Trigger block
            }
        }
    }

    private void handlePlayerDash(Player player, int[] playerKeys) {
        for(int i = 0; i < playerKeys.length; i++){
            if(playerKeys[i] == Input.getDash()){
                if(!player.getIsDashing()){
                    player.dash(playerKeys[i]);
                    player.setIsDashing(true);
                }
                else{
                    player.dash(playerKeys[i]);
                }
            }
        }
        
    }

}
