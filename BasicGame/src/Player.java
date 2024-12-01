import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private int x = 500;
    private int y = 500;
    private final int size = 50;
    private final int moveSpeed = 4;
    private final int groundLevel = 500;
    private int verticalVelocity = 0;
    private final double gravity = 1.6;
    private boolean isJumping = false;
    private boolean canJump = true;
    private long lastJumpTime = 0;

    private final Set<Integer> activeKeys = new HashSet<>();
    private int stamina = 100;
    private final int maxStamina = 100;
    private final int staminaDepletion = 20;
    private long lastStaminaChange = 0;
    private final int staminaRegenDelay = 3000;

    private int health = 100;

    public void update() {
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
            if (y >= groundLevel) {
                y = groundLevel;
                isJumping = false;
                verticalVelocity = 0;
                lastJumpTime = System.currentTimeMillis();
                canJump = false;
            }
        }

        // Jump cooldown
        if (!canJump && System.currentTimeMillis() - lastJumpTime >= 500) {
            canJump = true;
        }

        // Regenerate stamina
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStaminaChange >= staminaRegenDelay && stamina < maxStamina) {
            stamina = Math.min(stamina + 1, maxStamina);
            lastStaminaChange = currentTime;
        }
    }

    public void render() {
        SaxionApp.drawRectangle(x, y, size, size);
    }

    public void handleKeyboard(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode);
            if (keyCode == KeyEvent.VK_W && !isJumping && canJump && stamina >= staminaDepletion) {
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