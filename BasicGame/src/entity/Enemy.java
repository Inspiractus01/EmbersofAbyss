package entity;

import nl.saxion.app.SaxionApp;
import java.awt.Color;
import java.awt.Rectangle;
import game.Game;
import main.GameSettings;

public class Enemy {
    private int x, y;
    private int size = GameSettings.tileSize;
    private int health = 30;
    private boolean isDead = false;

    public Enemy(int startX, int startY, Game game) {
        this.x = startX;
        this.y = startY;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        isDead = true;
        System.out.println("Enemy marked as dead");
    }

    public boolean isDead() {
        return isDead;
    }

    public Rectangle getBounds() {
        if (isDead) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, size, size);
    }

    public void draw() {
        if (!isDead) {
            SaxionApp.setFill(Color.red);
            SaxionApp.drawRectangle(x, y, size, size);
        }
    }
}