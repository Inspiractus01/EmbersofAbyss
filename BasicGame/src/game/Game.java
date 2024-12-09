package game;

import entity.Enemy;
import entity.Player;
import entity.Tile;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import ui.UI;

import java.util.ArrayList;
import java.util.List;


public class Game implements GameLoop {
    private final Player player = new Player();
    private final UI ui = new UI();
    private List<Enemy> enemies;
    private Level level;

    @Override
    public void init() {
        level = new Level("BasicGame/src/game/level1.map", this);
        enemies = level.getEnemies(); // Initialize enemies from the level
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void loop() {
        SaxionApp.clear();

        // Draw the level
        level.draw();

        // Update and render the player
        player.update(enemies, level.getTiles());
        player.render();

        // Draw the UI elements
        ui.drawHealthBar(player.getHealth());
        ui.drawStaminaBar(player.getStamina(), player.getMaxStamina());

        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) {
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