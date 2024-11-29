import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class BasicGame implements GameLoop {
    private int squareX = 500; // X position of the square
    private int squareY = 500; // Y position of the square
    private final int squareSize = 50; // Size of the square
    private final int moveSpeed = 4; // Speed of horizontal movement

    private final int groundLevel = 500; // Ground level Y position
    private int verticalVelocity = 0; // Vertical velocity for jumping and gravity
    private final double gravity = 1.6; // Gravity strength

    private boolean isJumping = false; // Track if the square is jumping
    private boolean canJump = true; // Cooldown flag for jumping
    private long lastJumpTime = 0; // Time of the last jump

    private final Set<Integer> activeKeys = new HashSet<>(); // Set to track active keys

    // Stamina mechanics
    private int stamina = 100; // Max stamina
    private final int maxStamina = 100;
    private final int staminaDepletion = 20; // Amount of stamina used per jump
    private long lastStaminaChange = 0; // Time of the last stamina change
    private final int staminaRegenDelay = 3000; // Stamina regeneration delay in ms

    // Health mechanics
    private int health = 100; // Max health
    private final int maxHealth = 100;

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1000, 1000, 10);
    }

    @Override
    public void init() {
        SaxionApp.printLine("Use A (left), D (right), and W (jump) keys to control the square.");
    }

    @Override
    public void loop() {
        // Clear the screen
        SaxionApp.clear();

        // Draw health bar
        drawHealthBar();

        // Draw stamina bar
        drawStaminaBar();

        // Horizontal movement
        if (activeKeys.contains(KeyEvent.VK_A)) {
            squareX -= moveSpeed; // Move left
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            squareX += moveSpeed; // Move right
        }

        // Handle vertical movement (jumping and gravity)
        if (isJumping) {
            squareY += verticalVelocity; // Update vertical position based on velocity
            verticalVelocity += gravity; // Apply gravity

            // Check if the square lands back on the ground
            if (squareY >= groundLevel) {
                squareY = groundLevel; // Reset to ground level
                isJumping = false; // Stop jumping
                verticalVelocity = 0; // Reset vertical velocity
                lastJumpTime = System.currentTimeMillis(); // Record time when the jump ends
                canJump = false; // Start cooldown
            }
        }

        // Handle jump cooldown
        if (!canJump) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastJumpTime >= 500) { // 0.5 second cooldown
                canJump = true;
            }
        }

        // Gradual stamina regeneration logic (10 points per 0.2 seconds after 3 seconds of inactivity)
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStaminaChange >= staminaRegenDelay && stamina < maxStamina) {
            if (currentTime - lastStaminaChange >= 200) { // Check every 0.2 seconds
                stamina += 1; // Regenerate 10 stamina points
                if (stamina > maxStamina) {
                    stamina = maxStamina; // Cap stamina at max
                }
                lastStaminaChange += 10; // Increment by 0.01 seconds instead of resetting completely
            }
        }

        // Draw the square
        SaxionApp.drawRectangle(squareX, squareY, squareSize, squareSize);
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode); // Add the key to active keys

            // Handle jump when W is pressed
            if (keyCode == KeyEvent.VK_W && !isJumping && canJump && stamina >= staminaDepletion) {
                isJumping = true; // Start jumping
                verticalVelocity = -20; // Initial upward velocity
                stamina -= staminaDepletion; // Decrease stamina
                lastStaminaChange = System.currentTimeMillis(); // Update stamina change time
            }
        } else {
            activeKeys.remove(keyCode); // Remove the key from active keys
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        // No mouse interaction needed, so leave this method empty
    }

    private void drawHealthBar() {
        int barWidth = 200; // Max width of the health bar
        int barHeight = 20; // Height of the health bar
        int filledWidth = (int) ((health / (double) maxHealth) * barWidth); // Calculate filled portion

        // Draw the background of the health bar (dark gray)
        SaxionApp.setFill(new Color(50, 50, 50)); // Dark gray
        SaxionApp.drawRectangle(10, 10, barWidth, barHeight);

        // Draw the filled portion (red)
        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(10, 10, filledWidth, barHeight);
    }

    private void drawStaminaBar() {
        int barWidth = 200; // Max width of the stamina bar
        int barHeight = 20; // Height of the stamina bar
        int filledWidth = (int) ((stamina / (double) maxStamina) * barWidth); // Calculate filled portion

        // Draw the background of the stamina bar (gray)
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(10, 40, barWidth, barHeight); // Stamina bar is placed below the health bar

        // Draw the filled portion (yellow)
        SaxionApp.setFill(Color.YELLOW);
        SaxionApp.drawRectangle(10, 40, filledWidth, barHeight);
    }
}
