package entity;

import nl.saxion.app.SaxionApp;

import java.awt.Color;
import java.awt.Rectangle;

public class Enemy {
    private int x, y; // Enemy position
    private int width = 20, height = 20; // Enemy dimensions
    private int health = 30; // Enemy health

    public Enemy(int startX, int startY) {
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
        System.out.println("Enemy defeated!");
        // Additional logic for removing the enemy from the game can go here
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw() {
        SaxionApp.setFill(Color.red);
        SaxionApp.drawRectangle(x, y, width, height);
    }
}
