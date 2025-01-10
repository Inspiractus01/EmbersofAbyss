package menu;
import nl.saxion.app.SaxionApp;

public class Menu {

    private static String currentLanguage = "English";
    private static String selectedRegion = "North America";

    public static void main(String[] args) {
        // Initialize the application (no explicit start method in SaxionApp)

        // Display the title
        SaxionApp.printLine("========== Ember of Abyss ==========");

        while (true) {
            // Display the main menu options
            SaxionApp.printLine("1. Play Game");
            SaxionApp.printLine("2. Options");
            SaxionApp.printLine("3. Exit");

            // Get user choice
            int choice = SaxionApp.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> startGame();
                case 2 -> showOptions();
                case 3 -> exitGame();
                default -> SaxionApp.printLine("Invalid choice. Please try again.");
            }
        }
    }

    private static void startGame() {
        SaxionApp.printLine("Starting the game in " + selectedRegion + "...");
    }

    public static void drawOptions(){
        SaxionApp.drawText("fdsfds Options -=f90-90-9", 0, 20, 0);
    }
    private static void showOptions() {
        while (true) {
            SaxionApp.printLine("\n========== Options ==========");
            SaxionApp.printLine("1. Settings");
            SaxionApp.printLine("2. Language");
            SaxionApp.printLine("3. Controls");
            SaxionApp.printLine("4. Back to Main Menu");

            int choice = SaxionApp.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> showSettings();
                case 2 -> showLanguageOptions();
                case 3 -> showControls();
                case 4 -> {
                    return;
                }
                default -> SaxionApp.printLine("Invalid choice. Please try again.");
            }
        }
    }

    private static void showSettings() {
        SaxionApp.printLine("\n========== Settings ==========");
        SaxionApp.printLine("Current Region: " + selectedRegion);
        SaxionApp.printLine("1. North America");
        SaxionApp.printLine("2. Europe");
        SaxionApp.printLine("3. Asia");
        SaxionApp.printLine("4. Oceania");
        SaxionApp.printLine("5. South America");
        SaxionApp.printLine("6. Africa");

        int regionChoice = SaxionApp.readInt("Select your region: ");
        switch (regionChoice) {
            case 1 -> selectedRegion = "North America";
            case 2 -> selectedRegion = "Europe";
            case 3 -> selectedRegion = "Asia";
            case 4 -> selectedRegion = "Oceania";
            case 5 -> selectedRegion = "South America";
            case 6 -> selectedRegion = "Africa";
            default -> SaxionApp.printLine("Invalid choice. Region unchanged.");
        }

        SaxionApp.printLine("Region updated to: " + selectedRegion);
    }

    private static void showLanguageOptions() {
        SaxionApp.printLine("\n========== Language Options ==========");
        SaxionApp.printLine("1. English");
        SaxionApp.printLine("2. Slovak");
        SaxionApp.printLine("3. Spanish");
        SaxionApp.printLine("4. French");

        int languageChoice = SaxionApp.readInt("Select your language: ");
        switch (languageChoice) {
            case 1 -> currentLanguage = "English";
            case 2 -> currentLanguage = "Slovak";
            case 3 -> currentLanguage = "Spanish";
            case 4 -> currentLanguage = "French";
            default -> SaxionApp.printLine("Invalid choice. Language unchanged.");
        }

        SaxionApp.printLine("Language updated to: " + currentLanguage);
    }

    private static void showControls() {
        SaxionApp.printLine("\n========== Controls ==========");
        SaxionApp.printLine("Movement:");
        SaxionApp.printLine("  W: Jump");
        SaxionApp.printLine("  A: Move Left");
        SaxionApp.printLine("  D: Move Right");
        SaxionApp.printLine("  S: Crouch");

        SaxionApp.printLine("Actions:");
        SaxionApp.printLine("  J: Attack");
        SaxionApp.printLine("  K: Special Attack");
        SaxionApp.printLine("  Space: Interact");

        SaxionApp.printLine("Misc:");
        SaxionApp.printLine("  Esc: Pause Menu");
    }

    private static void exitGame() {
        SaxionApp.printLine("Exiting the game. Goodbye!");
        System.exit(0);
    }
}
