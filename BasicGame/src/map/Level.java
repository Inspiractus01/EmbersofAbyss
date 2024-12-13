package map;

import entity.Enemy;
import game.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<Tile> tiles = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final int tileSize = 50; // Define the size of each tile
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

                        case '3':
                            tiles.add(new Platform_tile(x * tileSize, y * tileSize, tileSize, tileSize, true));
                            break;

                        case '9': // Enemy
                            enemies.add(new Enemy(x * tileSize, y * tileSize, game));
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

    public void draw() {
        for (Tile tile : tiles) {
            tile.draw();
        }
    }
}