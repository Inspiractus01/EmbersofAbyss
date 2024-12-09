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
    private boolean canJump = true;
    private long lastJumpTime = 0;
    private int direction = 1;
    private final Set<Integer> activeKeys = new HashSet<>();
    private int stamina = 100;
    private final int maxStamina = 100;
    private final int staminaDepletion = 20;
    private long lastStaminaChange = 0;
    private final int staminaRegenDelay = 3000;
    private int health = 100;
    private final int attackCooldown = 500;
    private long lastAttackTime = 0;
    private String path = "assets/images/main.png";

    public void update(List<Enemy> enemies, List<entity.Tile> tiles) {
        // Handle horizontal movement
        if (activeKeys.contains(KeyEvent.VK_A)) {
            if (!willCollide(x - moveSpeed, y, tiles)) {
                x -= moveSpeed; // Move left
                direction = -1; // Face left
            }
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            if (!willCollide(x + moveSpeed, y, tiles)) {
                x += moveSpeed; // Move right
                direction = 1; // Face right
            }
        }

        // Update vertical movement
        if (isJumping || !isOnGround(tiles)) {
            y += verticalVelocity;
            verticalVelocity += gravity; // Simulate gravity
        } else {
            verticalVelocity = 0; // Reset vertical velocity if on ground
        }

        // Resolve landing
        if (isJumping && verticalVelocity > 0) {
            for (Tile tile : tiles) {
                if (tile.isSolid() && new Rectangle(x, y + verticalVelocity, size, size).intersects(tile.getBounds())) {
                    y = tile.getBounds().y - size; // Place player on top of the tile
                    isJumping = false;
                    canJump = false;
                    lastJumpTime = System.currentTimeMillis();
                    break;
                }
            }
        }

        // Cool down jumping
        if (!canJump && System.currentTimeMillis() - lastJumpTime >= 500) {
            canJump = true;
        }

        // Initiate a jump
        if (!isJumping && canJump && (activeKeys.contains(KeyEvent.VK_W) || activeKeys.contains(KeyEvent.VK_SPACE))
                && stamina >= staminaDepletion) {
            isJumping = true;
            verticalVelocity = -20;
            stamina -= staminaDepletion;
            lastStaminaChange = System.currentTimeMillis();
        }

        // Regenerate stamina
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStaminaChange >= staminaRegenDelay && stamina < maxStamina) {
            if (currentTime - lastStaminaChange >= 200) {
                stamina += 1;
                if (stamina > maxStamina) {
                    stamina = maxStamina;
                }
                lastStaminaChange += 10;
            }
        }

        // Handle attack
        if (activeKeys.contains(KeyEvent.VK_K) && System.currentTimeMillis() - lastAttackTime > attackCooldown) {
            performAttack(enemies);
        }
    }

    private boolean willCollide(int futureX, int futureY, List<entity.Tile> tiles) {
        Rectangle futureBounds = new Rectangle(futureX, futureY, size, size);
        for (Tile tile : tiles) {
            if (tile.isSolid() && futureBounds.intersects(tile.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnGround(List<entity.Tile> tiles) {
        Rectangle belowBounds = new Rectangle(x, y + 1, size, size);
        for (Tile tile : tiles) {
            if (tile.isSolid() && belowBounds.intersects(tile.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private void performAttack(List<Enemy> enemies) {
        lastAttackTime = System.currentTimeMillis();
        Rectangle attackHitbox = new Rectangle(x + (size / 2), y, size, size);

        for (Enemy enemy : enemies) {
            if (attackHitbox.intersects(enemy.getBounds())) {
                enemy.takeDamage(10);
            }
        }

        // Draw attack area (optional visualization for debugging)
        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(attackHitbox.x, attackHitbox.y, attackHitbox.width, attackHitbox.height);
    }

    public void render() {
        SaxionApp.drawImage(path, x, y, size, size);
    }

    public void handleKeyboard(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode);
            if ((keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_SPACE) && !isJumping && canJump
                    && stamina >= staminaDepletion) {
                isJumping = true;
                verticalVelocity = -20;
                stamina -= staminaDepletion;
                lastStaminaChange = System.currentTimeMillis();
            }
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