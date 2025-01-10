package game;

import map.Level;
import menu.Menu;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import ui.UI;

import java.util.List;
import entity.Enemy;
import entity.Player;
import nl.saxion.app.SaxionApp;

public class Game implements GameLoop {
    private final Player player = new Player();
    private final UI ui = new UI();
    private List<Enemy> enemies;
    private Level level;
    private String gameState;

    @Override
    public void init() {
        level = new Level("BasicGame/levels/level1.map", this);
        enemies = level.getEnemies(); // Load enemies from level
        gameState="Menu";
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void loop() {
        SaxionApp.clear();
        if(gameState.equalsIgnoreCase("menu")){
            Menu.drawOptions();
        }else if(gameState.equalsIgnoreCase("Play")){
            drawGame();
        }
        // Draw the level
    }
    public void drawGame(){
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
        if (gameState.equalsIgnoreCase("menu")) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER) {
                gameState = "Play";
            } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
                SaxionApp.quit();
            }
        }
        player.handleKeyboard(keyboardEvent);
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        // No mouse interaction needed
    }
}