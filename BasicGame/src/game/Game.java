package game;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import ui.UI;

import java.util.ArrayList;
import java.util.List;

import entity.Enemy;
import entity.Player;
import nl.saxion.app.SaxionApp;

public class Game implements GameLoop {
    private final Player player = new Player();
    private final UI ui = new UI();
    private List<Enemy> enemies;

    @Override
    public void init() {
        enemies = new ArrayList<>();
        enemies.add(new Enemy(700, 500, this));
        enemies.add(new Enemy(800, 500, this));
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void loop() {
        SaxionApp.clear();

        // Update and render the player
        player.update(enemies);
        player.render();

        // Draw the UI elements
        ui.drawHealthBar(player.getHealth());
        ui.drawStaminaBar(player.getStamina(), player.getMaxStamina());

        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) { // Skip dead enemies
                enemy.draw();
            }
        }
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        player.handleKeyboard(keyboardEvent);
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        // No mouse interaction needed
    }
}