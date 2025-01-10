package menu;

import nl.saxion.app.SaxionApp;

public class Menu {
    public static void drawOptions() {
        // Clear the screen for the menu
        SaxionApp.clear();

        // Draw menu options
        SaxionApp.drawText("========== Ember of Abyss ==========", 100, 50, 20);
        SaxionApp.drawText("1. Play Game", 100, 100, 16);
        SaxionApp.drawText("2. Options", 100, 130, 16);
        SaxionApp.drawText("3. Exit", 100, 160, 16);
        SaxionApp.drawText("Press ENTER to start or ESC to quit.", 100, 200, 12);
    }
}
