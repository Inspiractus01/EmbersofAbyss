package main;

import nl.saxion.app.SaxionApp;

public class Menu {
    public static void drawOptions() {
        // Clear the screen for the menu
        SaxionApp.clear();

        // Draw menu options
        SaxionApp.drawText("========== Ember of Abyss ==========", 100, 50, 20);
        SaxionApp.drawText("1. Play Game", 100, 100, 16);
        SaxionApp.drawText("2. Exit", 100, 160, 16);
        SaxionApp.drawText("Press the corresponding number key.", 100, 200, 12);
    }

    private static void startGame() {
        SaxionApp.clear();
        SaxionApp.drawText("Starting the game...", 100, 100, 16);
        // Add the game logic or transition here
    }

    private static void exitGame() {
        SaxionApp.clear();
        SaxionApp.drawText("Exiting the game. Goodbye!", 100, 100, 16);
        try {
            Thread.sleep(2000); // Pause for 2 seconds
        } catch (InterruptedException e) {
            // Handle exception if the sleep is interrupted
            e.printStackTrace();
        }
        System.exit(0);
    }
}