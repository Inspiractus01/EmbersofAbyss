package entity;

import nl.saxion.app.SaxionApp;
import java.awt.Color;
import java.awt.Rectangle;
import game.Game;
import main.GameSettings;
import game.Camera;
import map.Tile;

import java.util.List;

public class Enemy {
    private int x, y;
    private int size = GameSettings.tileSize;
    private int health = 30;
    private boolean isDead = false;
    private int moveSpeed = 1;
    private int runSpeed = 2; // Speed when running towards the player
    private int leftBorder, rightBorder, topBorder, bottomBorder;
    private long lastAttackTime = 0;
    private final int attackCooldown = 2000; // Time between attacks in milliseconds
    private final int attackRange = size; // Distance within which the enemy will attack the player
    private final int detectionRange = 200; // Distance within which the enemy will detect the player
    private final int gravity = 3;
    private int verticalVelocity = 0;
    private Game game;

    public Enemy(int startX, int startY, int leftBorder, int rightBorder, int topBorder, int bottomBorder, Game game) {
        this.x = startX;
        this.y = startY;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.game = game;
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

    public void update(Player player, List<Tile> tiles) {
        if (isDead) {
            return;
        }

        // Apply gravity
        y += verticalVelocity / 1.7;
        verticalVelocity += gravity / 3;

        // Check for collision when moving downwards
        if (verticalVelocity > 0) {
            for (Tile tile : tiles) {
                Rectangle tileBounds = tile.getBounds();
                if (tile.isSolid() && getCollisionBoxWithOffset(0, verticalVelocity).intersects(tileBounds)) {
                    y = tileBounds.y - size;
                    verticalVelocity = 0;
                    break;
                }
            }
        }

        // Check if player is within detection range
        if (Math.abs(player.getX() - x) < detectionRange && Math.abs(player.getY() - y) < detectionRange) {
            // Move towards the player
            if (player.getX() > x) {
                x += runSpeed;
            } else if (player.getX() < x) {
                x -= runSpeed;
            }

            if (player.getY() > y) {
                y += runSpeed;
            } else if (player.getY() < y) {
                y -= runSpeed;
            }

            // Check if player is within attack range
            if (Math.abs(player.getX() - x) < attackRange && Math.abs(player.getY() - y) < attackRange) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastAttackTime >= attackCooldown) {
                    player.takeDamage(5); // Attack the player
                    lastAttackTime = currentTime;
                }
            }
        } else {
            // Wandering logic
            if (x < leftBorder || x > rightBorder) {
                moveSpeed = -moveSpeed; // Change direction when hitting borders
            }
            x += moveSpeed;
        }
    }

    private Rectangle getCollisionBoxWithOffset(int offsetX, int offsetY) {
        return new Rectangle(x + offsetX, y + offsetY, size, size);
    }

    public void draw(Camera camera) {
        if (!isDead) {
            SaxionApp.setFill(Color.red);
            SaxionApp.drawRectangle(x - camera.getX(), y - camera.getY(), size, size);
        }
    }
}