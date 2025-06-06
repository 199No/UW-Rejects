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

public class Game implements ActionListener {

    ///////////////
    // Properties
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
    private ArrayList<Enemies> enemies  = new ArrayList<>();
    private ArrayList<Player>  players  = new ArrayList<>();
    private ArrayList<Entity>  entities = new ArrayList<>();
    int[] entityIndices;
    EntitySort esort = new EntitySort();

    // Bounds
    public static final int xMin = 0;
    public static final int xMax = 4080;
    public static final int yMin = 0;
    public static final int yMax = 4080;

    // Rectangle to trigger transition to level 2
    Rectangle levelEndRect = new Rectangle(0, 4 * Gui.CHUNK_WIDTH, Gui.TILE_SIZE, (int)(Gui.TILE_SIZE * 3 * Gui.HEIGHT_SCALE));
    private int fadeStart = -1;
    private int levelNum = 1;

    ///////////////
    // Constructor
    ///////////////
    public Game() throws AWTException, IOException {
        // Initialize players
        this.player1 = new Player(750.0, 300.0, 100, 10, 1);
        this.player2 = new Player(500.0, 500.0, 100, 10, 2);
        players.add(player1);
        players.add(player2);

        // Input and GUI setup
        this.input = new Input();
        gui = new Gui(1280, 720, input);

        // Initialize enemies
        enemies.add(new Slime(400, 400));

        // Load map
        map = new Map("Maps/map1.map", "Maps/map1Env.map");

        // Background music
        if (levelNum == 2) {
            Sounds.playSound("WindBackground");
        } else if (levelNum == 1) {
            Sounds.playSound("Countryside");
        }

        // Start game loop
        gameTimer = new Timer(11, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }

    //-------------------------------------------------//
    //                   Game Loop                     //
    //-------------------------------------------------//

    @Override
    public void actionPerformed(ActionEvent e) {
        updateTime();
        updatePlayers();
        updateEnemies();
        updateEntities();
        renderScene();
        checkLevelTransition();
        gui.repaint();
        now = System.currentTimeMillis();
        entities.clear();  // Clear entity list for the next frame
    }

    /** Handles updating timing values and FPS tracking */
    private void updateTime() {
        now = System.currentTimeMillis();
        if (now - lastSecond > 1000) {
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond++;
        }
    }

    /** Updates both player objects */
    private void updatePlayers() {
        player1.updateMovement(Input.getKeys(), Input.getShifts());
        player2.updateMovement(Input.getKeys(), Input.getShifts());

        player1.updateCollision(enemies, map.getAllEnvObjects());
        player2.updateCollision(enemies, map.getAllEnvObjects());

        player1.updateAttack();
        player2.updateAttack();

        player1.updateBlock();
        player2.updateBlock();
    }

    /** Updates enemies including AI and collision */
    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemies enemy = enemies.get(i);
            enemy.update(players);

            // Collision detection between enemy and environment
            for (int c = 0; c < map.numLoadedChunks(); c++) {
                EnvObject[] envObjects = map.getChunk(c).getEnvObjects();
                for (EnvObject obj : envObjects) {
                    Rectangle eHitbox = enemy.getRelHitbox();
                    Rectangle objHitbox = obj.getAbsHitbox();

                    if (eHitbox.intersects(objHitbox)) {
                        Rectangle2D clip = objHitbox.createIntersection(eHitbox);
                        if (clip.getHeight() > clip.getWidth()) {
                            // Horizontal
                            if (eHitbox.getX() > objHitbox.getX()) {
                                enemy.setX(objHitbox.getMaxX());
                            } else {
                                enemy.setX(objHitbox.getX() - enemy.getWidth());
                            }
                        } else {
                            // Vertical
                            if (eHitbox.getY() > objHitbox.getY()) {
                                enemy.setY(objHitbox.getMaxY());
                            } else {
                                enemy.setY(objHitbox.getY() - enemy.getHeight());
                            }
                        }
                    }
                }
            }
        }
    }

    /** Combines and sorts all entities (players, enemies, environment) for rendering */
    private void updateEntities() {
        entities.addAll(enemies);
        entities.addAll(map.getAllEnvObjects());
        entities.addAll(players);

        entityIndices = new int[entities.size()];
        for (int i = 0; i < entities.size(); i++) entityIndices[i] = i;
        esort.sort(entityIndices, entities, 0, entities.size() - 1);
    }

    /** Renders all visual elements to the GUI */
    private void renderScene() {
        gui.background(0, 0, 0);  // Clear screen

        for (int c = 0; c < map.numLoadedChunks(); c++) {
            gui.drawChunk(map.getChunk(c));
        }

        // Smooth camera movement based on player positions
        gui.shiftCamera(
            ((player1.getX() + player2.getX()) / 2 - gui.cameraX()) / 10,
            (((player1.getY() + player2.getY()) / 2) * Gui.HEIGHT_SCALE - gui.cameraY()) / 10
        );

        checkDeath(enemies, players);

        for (int i = 0; i < entities.size(); i++) {
            if (entityIndices[i] < entities.size() - 2) {
                gui.drawEntity(entities.get(entityIndices[i]));
            } else {
                gui.drawPlayer(players.get(entityIndices[i] - (entities.size() - 2)));
            }
        }

        gui.drawShadowLayer();
        gui.drawEntityLayer();

        drawDashBars();
        gui.displayFPS((int) frameRate);
    }

    /** Draws the dash cooldown bars for each player */
    private void drawDashBars() {
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g) {
                double height1 = ((System.currentTimeMillis() - Input.getLastp1Dash()) / (double) Input.DASH_COOLDOWN) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(0, Gui.HEIGHT - (int) height1, 30, (int) height1);

                double height2 = ((System.currentTimeMillis() - Input.getLastp2Dash()) / (double) Input.DASH_COOLDOWN) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(Gui.WIDTH - 30, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(Gui.WIDTH - 30, Gui.HEIGHT - (int) height2, 30, (int) height2);
            }
        });
    }

    /** Checks level transition conditions */
    private void checkLevelTransition() {
        if (player1.getAbsHitbox().intersects(levelEndRect) && player2.getAbsHitbox().intersects(levelEndRect)) {
            goToLevel2();
        }

        if (fadeStart > (int) System.currentTimeMillis() - 5000) {
            gui.drawLevelTransition(fadeStart);
        }
    }

    /** Checks if any enemies or players have died and removes them */
    private void checkDeath(ArrayList<Enemies> enemies, ArrayList<Player> players) {
        enemies.removeIf(enemy -> !enemy.getIsAlive());
        players.removeIf(player -> (player.getHealth() <= 0));
    }

    /** Triggers the transition to level 2 */
    public void goToLevel2() {
        if (fadeStart == -1) {
            fadeStart = (int) System.currentTimeMillis();
        }

        if ((int) System.currentTimeMillis() - fadeStart > 1500 && !map.getTilePath().contains("2")) {
            player1.setPos(350, 200);
            player2.setPos(150, 300);
            map = new Map("Maps/map2.map", "Maps/map2Env.map");
        }
    }

    public static boolean inDebugMode() {
        return inDebugMode;
    }
}
