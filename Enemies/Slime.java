package Enemies;

import src.Player;
import src.Images;
import src.Sounds;
import src.Animation;
import src.Gui;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Slime extends Enemies {

    private Animation slimeAnimation;

    public Slime(double x, double y) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle((int)x, (int)y, Gui.TILE_SIZE, Gui.TILE_SIZE));
        
        this.health = 100.0;
        this.damage = 10.0;
        this.speed = 1.5;
        this.range = 250.0;

        this.slimeAnimation = new Animation(
            new Images("Images/Enemies", Transparency.BITMASK).getImage("slime"),
            4, 2, 7, 100, true
        );
        this.slimeAnimation.start();
    }

    @Override
    public void update(ArrayList<Player> players) {
        super.update(players);
        //slimeAnimation.update();
    }

    @Override
    public void idle() {
        int interval = now - lastNewDirection;
        double newspeed = speed * 0.5;

        if (interval > 2000 + Math.random() * 3000) {
            double chance = Math.random();
            if (chance < 0.5) {
                dx = 0;
                dy = 0;
            } else {
                double angle = Math.random() * 2 * Math.PI;
                dx = Math.cos(angle) * newspeed * 1.5;
                dy = Math.sin(angle) * newspeed * 1.5;
            }
            lastNewDirection = now;
        }

        move();
    }

    @Override
    public void findTarget(ArrayList<Player> players) {
        if (players.isEmpty()) return;

        Player closest = players.get(0);
        double closestDistance = calcDistance(closest);

        for (int i = 1; i < players.size(); i++) {
            double distance = calcDistance(players.get(i));
            if (distance < closestDistance) {
                closest = players.get(i);
                closestDistance = distance;
            }
        }

        target = closest;
    }

    @Override
    public void chase(ArrayList<Player> players) {
        if (target == null) return;

        double tx = target.getX();
        double ty = target.getY();
        double dx = tx - this.getX();
        double dy = ty - this.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance != 0) {
            dx /= distance;
            dy /= distance;
        }

        this.dx = dx * speed;
        this.dy = dy * speed;

        move();
    }

    @Override
    public void move() {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    @Override
    public void die() {
        Sounds.playSound("SlimeDeath");
        this.alive = false;
    }

    @Override
    public BufferedImage getImage() {
        return slimeAnimation.getFrame();
    }

    @Override
    public BufferedImage getShadowImage() {
        return Gui.toShadow(getImage());
    }
}