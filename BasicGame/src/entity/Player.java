package entity;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import entity.Tile;

public class Player {
    private int x = 500;
    private int y = 500;
    private final int size = 50;
    private final int moveSpeed = 4;
    private int verticalVelocity = 0;
    private final double gravity = 1.6;
    private boolean isJumping = false;
    private final Set<Integer> activeKeys = new HashSet<>();
    private final int staminaDepletion = 20;

    private int health = 100;
    private String path = "assets/images/main.png";

    public void update(List<Enemy> enemies, List<Tile> tiles) {
        if (activeKeys.contains(KeyEvent.VK_A)) {
            x -= moveSpeed;
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            x += moveSpeed;
        }

        if (isJumping) {
            y += verticalVelocity;
            verticalVelocity += gravity;

            for (Tile tile : tiles) {
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
        return 100; // Dummy implementation
    }

    public int getMaxStamina() {
        return 100; // Dummy implementation
    }
}