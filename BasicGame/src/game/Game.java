package game;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import ui.UI;
import entity.Player;
import nl.saxion.app.SaxionApp;

public class Game implements GameLoop {
    private final Player player = new Player();
    private final UI ui = new UI();

    @Override
    public void init() {
        SaxionApp.printLine("Use A (left), D (right), and W (jump) keys to control the square."); // USELESS BTW
    }

    @Override
    public void loop() {
        SaxionApp.clear();

        // Update and render the player
        player.update();
        player.render();

        // Draw the UI elements
        ui.drawHealthBar(player.getHealth());
        ui.drawStaminaBar(player.getStamina(), player.getMaxStamina());
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