package game;

import map.Level;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import ui.UI;

import java.awt.Color;
import java.util.List;
import entity.Enemy;
import entity.Player;
import nl.saxion.app.SaxionApp;

public class Game implements GameLoop {
    private final Player player = new Player();
    private final UI ui = new UI();
    private List<Enemy> enemies;
    private Level level;
    private Camera camera;

    @Override
    public void init() {
        level = new Level("BasicGame/levels/level1.map", this);
        enemies = level.getEnemies(); // Load enemies from level
        camera = new Camera(player.getX(), player.getY());
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
    

    @Override
    public void loop() {
        SaxionApp.clear();

        // Update camera position
        camera.update(player.getX(), player.getY());

        // Draw the level
        level.draw(camera);

        // Update and render the player
        player.update(enemies, level.getTiles(), camera);
        player.render(camera);

        // Draw the UI elements
        SaxionApp.setBorderColor(Color.gray);
        ui.drawHealthBar(player.getHealth());
        ui.drawStaminaBar(player.getStamina(), player.getMaxStamina());
        ui.drawSoulsCounter(player.getSouls());

        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) {
                enemy.update(player, level.getTiles()); // Update enemy logic with player and tiles
                enemy.draw(camera);
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