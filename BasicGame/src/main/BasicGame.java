package main;

import game.Game;
import nl.saxion.app.SaxionApp;

/**
 * Entry point for the game. Starts the game loop.
 */
public class BasicGame {
    public static void main(String[] args) {
        Game game = new Game();
        SaxionApp.startGameLoop(game, GameSettings.screenWidth, GameSettings.screenHeight, 10); // Start the game loop
    }
}