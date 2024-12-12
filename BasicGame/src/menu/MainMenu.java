package menu;

import nl.saxion.app.SaxionApp;

public class MainMenu implements Runnable {
    private static final int SCREEN_WIDTH = 800; // Screen width
    private static final int SCREEN_HEIGHT = 600; // Screen height
    private static final String BACKGROUND_IMAGE_PATH = "EmbersofAbyss/BasicGame/assets/images/ambersabyss_logo.png"; // Path to background image

    public static void main(String[] args) {
        // Start the SaxionApp with MainMenu as a Runnable
        SaxionApp.start(new MainMenu(), SCREEN_WIDTH, SCREEN_HEIGHT); // Specify window dimensions
    }

    @Override
    public void run() {
        // Display the menu when the application starts
        displayMenu();
    }

    // Method to display the menu
    public void displayMenu() {
        boolean running = true;

        while (running) {
            // Clear the screen
            SaxionApp.clear();

            // Draw the background image
            SaxionApp.drawImage(BACKGROUND_IMAGE_PATH, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            int fontSize = 20;

            // Draw the menu options, centered horizontally
            SaxionApp.drawText("=== Main Menu ===", centerText("=== Main Menu ===", fontSize), 50, fontSize);
            SaxionApp.drawText("1. Start Game", centerText("1. Start Game", fontSize), 100, fontSize);
            SaxionApp.drawText("2. Options", centerText("2. Options", fontSize), 150, fontSize);
            SaxionApp.drawText("3. Exit", centerText("3. Exit", fontSize), 200, fontSize);

            // Prompt user for input
            SaxionApp.drawText("Choose an option (1-3):", centerText("Choose an option (1-3):", fontSize), 300, fontSize);
            int choice = SaxionApp.readInt();

            // Handle the choice
            switch (choice) {
                case 1 -> startGame();
                case 2 -> showOptions();
                case 3 -> {
                    SaxionApp.printLine("Exiting game. Goodbye!");
                    running = false;
                }
                default -> SaxionApp.printLine("Invalid choice. Please select a valid option (1-3).");
            }
        }
    }

    // Helper method to calculate the centered x-coordinate for text
    private int centerText(String text, int fontSize) {
        int textWidth = text.length() * fontSize / 2;
        return (SCREEN_WIDTH - textWidth) / 2; // Calculate the centered x-coordinate
    }

    // Stub method to start the game
    private void startGame() {
        SaxionApp.clear();
        SaxionApp.printLine("Starting the game...");
        SaxionApp.pause(); // Pause for 2 seconds
    }

    // Stub method to show options
    private void showOptions() {
        SaxionApp.clear();
        SaxionApp.printLine("Options menu not implemented yet.");
        SaxionApp.printLine("Returning to Main Menu...");
        SaxionApp.pause(); // Pause for 2 seconds
    }
}
