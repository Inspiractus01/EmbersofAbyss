package entity;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private int x = 500;
    private int y = 500;
    private final int size = 50;
    private final int moveSpeed = 4;
    private int verticalVelocity = 0;
    private final double gravity = 1.6;
    private boolean isJumping = false;
    private final Set<Integer> activeKeys = new HashSet<>();
    private int health = 100;

    // Stamina and attack properties
    private int stamina = 100;
    private final int maxStamina = 100;
    private final int staminaDepletion = 20;
    private boolean isAttacking = false;
    private final int attackRange = 20;
    private final int attackWidth = 40;
    private final int attackHeight = 50;
    private final int attackCooldown = 500;
    private long lastAttackTime = 0;
    private String path = "assets/images/main.png";

    public void update(List<Enemy> enemies, List<entity.Tile> tiles) {
        // Handle horizontal movement
        if (activeKeys.contains(KeyEvent.VK_A)) {
            x -= moveSpeed;
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            x += moveSpeed;
        }

        // Handle jumping and gravity
        if (isJumping) {
            y += verticalVelocity;
            verticalVelocity += gravity;
            for (entity.Tile tile : tiles) {
                Rectangle playerBounds = new Rectangle(x, y, size, size);
                if (playerBounds.intersects(tile.getBounds()) && tile.isSolid()) {
                    if (verticalVelocity > 0) {
                        y = tile.getBounds().y - size;
                        isJumping = false;
                        verticalVelocity = 0;
                    }
                    break;
                }
            }
        }

        if (activeKeys.contains(KeyEvent.VK_W) || activeKeys.contains(KeyEvent.VK_SPACE)) {
            if (!isJumping) {
                isJumping = true;
                verticalVelocity = -20;
            }
        }

        // Handle attack
        if (activeKeys.contains(KeyEvent.VK_K) && System.currentTimeMillis() - lastAttackTime > attackCooldown) {
            attack(enemies);
        }
    }

    public void attack(List<Enemy> enemies) {
        isAttacking = true;
        lastAttackTime = System.currentTimeMillis();

        // Define attack hitbox
        Rectangle attackHitbox = new Rectangle(x + (size / 2), y, attackWidth, attackHeight);

        // Check enemy collisions
        for (Enemy enemy : enemies) {
            if (attackHitbox.intersects(enemy.getBounds())) {
                enemy.takeDamage(10);
            }
        }

        // Visualize attack hitbox
        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(attackHitbox.x, attackHitbox.y, attackHitbox.width, attackHitbox.height);

        isAttacking = false;
    }

    public void render() {
        SaxionApp.drawImage(path, x, y, size, size);
    }

    public void handleKeyboard(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode);
        } else {
            activeKeys.remove(keyCode);
        }
    }

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }
}