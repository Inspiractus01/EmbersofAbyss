package entity;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Player class to manage the player's movement, jumping, stamina, health, and attacking logic.
 */
public class Player {
    private int x = 500; // X position of the player
    private int y = 500; // Y position of the player
    private final int size = 50; // Size of the player square
    private final int moveSpeed = 4; // Horizontal movement speed
    private final int groundLevel = 500; // Y position representing the ground
    private int verticalVelocity = 0; // Vertical velocity for jumping
    private final double gravity = 1.6; // Gravity strength
    private boolean isJumping = false; // Whether the player is currently jumping
    private boolean canJump = true; // Whether the player can jump (cooldown logic)
    private long lastJumpTime = 0; // Timestamp of the last jump
    private int direction = 1; // 1 for facing right, -1 for facing left

    private final Set<Integer> activeKeys = new HashSet<>(); // Tracks currently pressed keys
    private int stamina = 100; // Current stamina level
    private final int maxStamina = 100; // Maximum stamina level
    private final int staminaDepletion = 20; // Stamina cost per jump
    private long lastStaminaChange = 0; // Timestamp of the last stamina consumption
    private final int staminaRegenDelay = 3000; // Delay (in ms) before stamina starts regenerating

    private int health = 100; // Current health level

    private boolean isAttacking = false; // Tracks if the player is attacking
    private final int attackWidth = 40; // Width of the attack hitbox
    private final int attackHeight = 50; // Height of the attack hitbox
    private final int attackCooldown = 500; // Cooldown in milliseconds
    private long lastAttackTime = 0; // Tracks the last attack time
    private String path = "assets/images/main.png";

    /**
     * Updates the player's state, including movement, jumping, gravity, stamina regeneration, and attacks.
     *
     * @param enemies List of enemies to check for collisions during attacks.
     * @param tiles   List of tiles to interact with.
     */
    public void update(List<Enemy> enemies, List<entity.Tile> tiles) {
        // Handle horizontal movement
        if (activeKeys.contains(KeyEvent.VK_A)) {
            x -= moveSpeed; // Move left
            direction = -1; // Face left
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            x += moveSpeed; // Move right
            direction = 1; // Face right
        }

        // Handle jumping and gravity
        if (isJumping) {
            y += verticalVelocity; // Update vertical position based on velocity
            verticalVelocity += gravity; // Apply gravity

            // Checking collision with tiles
            for (entity.Tile tile : tiles) {
                Rectangle playerBounds = new Rectangle(x, y, size, size);
                if (playerBounds.intersects(tile.getBounds()) && tile.isSolid()) {
                    if (verticalVelocity > 0) {
                        y = tile.getBounds().y - size;
                        isJumping = false;
                        verticalVelocity = 0;
                        lastJumpTime = System.currentTimeMillis(); // Record the landing time
                        canJump = false; // Start jump cooldown
                    }
                }
            }
        }

        // Handle jump cooldown
        if (!canJump && System.currentTimeMillis() - lastJumpTime >= 500) {
            canJump = true; // Reset jump availability after 0.5 seconds
        }

        if (!isJumping && canJump && (activeKeys.contains(KeyEvent.VK_W) || activeKeys.contains(KeyEvent.VK_SPACE))
                && stamina >= staminaDepletion) {
            isJumping = true; // Start jumping
            verticalVelocity = -20; // Initial upward velocity
            stamina -= staminaDepletion; // Decrease stamina
            lastStaminaChange = System.currentTimeMillis(); // Record stamina usage time
        }

        // Handle stamina regeneration
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStaminaChange >= staminaRegenDelay && stamina < maxStamina) {
            if (currentTime - lastStaminaChange >= 200) {  // Regenerate every 200ms
                stamina += 1; // Regenerate stamina points
                if (stamina > maxStamina) {
                    stamina = maxStamina; // Cap stamina to max level
                }
                lastStaminaChange += 10; // Update the last stamina change time
            }
        }

        // Handle attack
        if (activeKeys.contains(KeyEvent.VK_K) && System.currentTimeMillis() - lastAttackTime > attackCooldown) {
            attack(enemies);
        }
    }

    /**
     * Executes the player's attack, checking for collisions with enemies.
     *
     * @param enemies List of enemies to check for collisions.
     */
    public void attack(List<Enemy> enemies) {
        isAttacking = true;
        lastAttackTime = System.currentTimeMillis();

        Rectangle attackHitbox = new Rectangle(x + (size / 2), y, attackWidth, attackHeight);

        for (Enemy enemy : enemies) {
            if (attackHitbox.intersects(enemy.getBounds())) {
                enemy.takeDamage(10);
            }
        }

        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(attackHitbox.x, attackHitbox.y, attackHitbox.width, attackHitbox.height);

        isAttacking = false;
    }

    /**
     * Renders the player as a rectangle on the screen.
     */
    public void render() {
        SaxionApp.drawImage(path, x, y, size, size);
    }

    /**
     * Handles keyboard input for player actions such as moving and jumping.
     *
     * @param keyboardEvent The keyboard event triggering this method.
     */
    public void handleKeyboard(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode);

            // Handle jumping
            if ((keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_SPACE) && !isJumping && canJump
                    && stamina >= staminaDepletion) {
                isJumping = true; // Start jumping
                verticalVelocity = -20; // Initial upward velocity
                stamina -= staminaDepletion; // Decrease stamina
                lastStaminaChange = System.currentTimeMillis(); // Record stamina usage time
            }
        } else {
            activeKeys.remove(keyCode);
        }
    }

    /**
     * @return Current health level.
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Current stamina level.
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * @return Maximum stamina level.
     */
    public int getMaxStamina() {
        return maxStamina;
    }
}