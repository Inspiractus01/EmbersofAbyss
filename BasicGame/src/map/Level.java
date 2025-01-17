package map;

import entity.Enemy;
import game.Game;
import game.Camera;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.GameSettings;

public class Level {
    private final List<Tile> tiles = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final int tileSize = GameSettings.tileSize; // Define the size of each tile, imported from Gamesettings
    private final Game game;

    public Level(String mapFilePath, Game game) {
        this.game = game;
        loadLevelFromFile(mapFilePath);
    }

    private void loadLevelFromFile(String mapFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            int y = 0;

            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char charType = line.charAt(x);

                    switch (charType) {
                        case '1': // Ground tile
                            tiles.add(new Ground_tile(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '2': // Wall
                            tiles.add(new Wall_tile(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '3': // Platform tile
                            tiles.add(new Platform_tile(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '4': // Ground tile
                            tiles.add(new Under_tile(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '5': // Sign
                        tiles.add(new Sign(x * tileSize, y * tileSize, tileSize, tileSize, false));
                            break;

                        case '6': // Fountain
                        tiles.add(new Fountain(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '9': // Enemy
                            int leftBorder = x * tileSize - 100; // Example left border value
                            int rightBorder = x * tileSize + 100; // Example right border value
                            int topBorder = y * tileSize - 50; // Example top border value
                            int bottomBorder = y * tileSize + 50; // Example bottom border value
                            enemies.add(new Enemy(x * tileSize, y * tileSize, leftBorder, rightBorder, topBorder, bottomBorder, game));
                            break;

                        case '0': // Empty Space
                        default:
                            // No action needed for empty space
                            break;
                    }
                }
                y++;
            }
        } catch (IOException e) {
            System.err.println("Failed to load map file: " + e.getMessage());
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void draw(Camera camera) {
        for (Tile tile : tiles) {
            tile.draw(camera);
        }
    }
}