package entity;

import nl.saxion.app.SaxionApp;

import java.awt.Color;
import java.awt.Rectangle;

import game.Game;

public class Enemy {
    private int x, y; // Enemy position
    private int size = 50; // Enemy dimensions
    private int health = 30; // Enemy health
    private Game game;
    private boolean isDead = false;

    public Enemy(int startX, int startY, Game game) {
        this.x = startX;
        this.y = startY;
        this.game = game;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        isDead = true; // Mark the enemy as dead
        System.out.println("Enemy marked as dead");
    }

    public boolean isDead() {
        return isDead;
    }
    
    public int getHealth() {
        return health;
    }


    public Rectangle getBounds() {
        if (isDead) {
            return new Rectangle(0, 0, 0, 0); // Return an empty rectangle when dead
        }
        return new Rectangle(x, y, size, size); // Actual hitbox when alive
    }
    

    public void draw() {
        if (!isDead) {
            SaxionApp.setFill(Color.red);
            SaxionApp.drawRectangle(x, y, size, size);
        }
    }
}
