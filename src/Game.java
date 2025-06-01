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
import javax.swing.Timer;

//-------------------------------------------------//
//                     Game                        //
//-------------------------------------------------// 

public class Game implements ActionListener{

    ///////////////
    //Properties
    ///////////////
    public static boolean inDebugMode = false;
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
    int[] entityIndices;
    EntitySort esort = new EntitySort();
    // Bounds
    public static final int xMin = 0;
    public static final int xMax = 4080;
    public static final int yMin = 0;
    public static final int yMax = 4080;
    Rectangle levelEndRect = new Rectangle(0, 4 * Gui.CHUNK_WIDTH, Gui.TILE_SIZE,(int)( Gui.TILE_SIZE * 3 * Gui.HEIGHT_SCALE));
    private int fadeStart = -1;
    private int levelNum = 1;
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{

        this.player1 = new Player(750.0, 300.0, 100, 10, 1);
        this.player2 = new Player(500.0, 500.0, 100, 10, 2);
        players.add(player1);
        players.add(player2);
        this.input = new Input();
        // Use the slime constructorsdds
        enemies.add(new Slime(400,400));
        random = new Random();
        gui = new Gui(1280, 720, input);
        map = new Map("Maps/map1.map", "Maps/map1Env.map");

        //How to say if its in desert do wind if in grass do country
        if(levelNum == 2){
            Sounds.playSound("WindBackground");
        }
        if(levelNum == 1){
            Sounds.playSound("Countryside");
        }
        // Only these four lines should happen after this comment otherwise stuff will break
        gameTimer = new Timer(11, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    @Override


    /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    /// TODO: Simplify/despaghettify this!!!!!
    /////////////////////////////////////////



     public void actionPerformed(ActionEvent e) {
        
        
        /////////////////////
        //// CALCULATE FPS
        ////////////////////
        
        // TODO: Make this a seperate method (readability)
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
        // ////////////////
        // updatePlayer(player1);
        // updatePlayer(player2);
        player1.updateMovement(Input.getKeys(), Input.getShifts());
        player2.updateMovement(Input.getKeys(), Input.getShifts());
        player1.updateCollision(enemies,map.getAllEnvObjects());
        player2.updateCollision(enemies,map.getAllEnvObjects());
        player1.updateAttack();
        player2.updateAttack();
        player1.updateBlock();
        player2.updateBlock();
        // Update enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemies enemy = enemies.get(i);
            enemy.update(players);
    
            ////////////////
            /// COLLISION
            ///////////////
            
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
        entities.addAll(map.getAllEnvObjects());
        entities.addAll(players);
        // Draw the background (happens before all other draw commands)
        gui.background(0, 0, 0);
        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawChunk(map.getChunk(c));
        }
    
        // Update camera position
        gui.shiftCamera(
            ((player1.getX() + player2.getX()) / 2 - gui.cameraX()) / 10,
            (((player1.getY() + player2.getY()) / 2) * Gui.HEIGHT_SCALE - gui.cameraY()) / 10
        );

        ///////////
        /// CHECK DEATH
        ///////////
        checkDeath(this.enemies,this.players);

        // Create array of indices
        entityIndices = new int[entities.size()];
        // Populate array
        for(int i = 0; i < entities.size(); i++) entityIndices[i] = i;
        // Sort entities
        esort.sort(entityIndices, entities, 0, entities.size() - 1);
        // Draw all Entities (players, enemies, envObjects, etc.)
        
        // TODO: Maybe have a draw() method in each entity

        for(int i = 0; i < entities.size(); i++){
            if(entityIndices[i] < entities.size() - 2){
                gui.drawEntity(entities.get(entityIndices[i])); 
            } else {
                gui.drawPlayer(players.get(entityIndices[i] - (entities.size() - 2)));
            }
        }
        gui.drawShadowLayer();
        gui.drawEntityLayer();
    
        // Draw dash bars
        // TODO: Make a GUI method for this~!
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

        if(player1.getAbsHitbox().intersects(levelEndRect) && player2.getAbsHitbox().intersects(levelEndRect)){
            goToLevel2();
        }
        if(fadeStart > (int)System.currentTimeMillis() - 5000)
            gui.drawLevelTransition(fadeStart);
        gui.displayFPS((int) frameRate);
        gui.repaint();
        now = System.currentTimeMillis();
        
        entities.clear();
    }

    private void checkDeath(ArrayList<Enemies> enemies, ArrayList<Player> player){
        for(int i = 0; i < enemies.size(); i++){
            if(!enemies.get(i).getIsAlive()){
                enemies.remove(i);
            }
        }

        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).getIsAlive()){
                players.remove(i);
            }
        }
    }
    
        
    private void handlePlayerDash(Player player, int[] playerKeys) {
    
        int dashKey = Input.getDash();
        for (int key : playerKeys) {
            if (key == dashKey) {
                player.dash(dashKey);
                break;
            }
        }
    }



    // TODO: Make this part of player "update movement method"
    private void updatePlayerMovement(Player player, int[] playerKeys, boolean shift, boolean[] keys) {
        // w a s d OR i j k l
        boolean[] movement = new boolean[] {
            keys[playerKeys[0]], // Up
            keys[playerKeys[1]], // Left
            keys[playerKeys[2]], // Down
            keys[playerKeys[3]]  // Right
        };
        player.move(movement, shift);
    }

    public static boolean inDebugMode(){
        return inDebugMode;
    }
    // TODO: Gui should handle this
    public void goToLevel2(){
        // Change mapfile and env file to be map2.map
        if(fadeStart == -1){
            
        fadeStart = (int)System.currentTimeMillis();
        }
        if((int)System.currentTimeMillis() - fadeStart > 1500 && !map.getTilePath().contains("2")){
            player1.setPos(350, 200);
            player2.setPos(150, 300);
            map = new Map("Maps/map2.map", "Maps/map2Env.map");
        }
    }

}
