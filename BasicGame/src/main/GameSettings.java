package main;

public class GameSettings {
    // Core settings
    public static final int originalTileSize = 16; // Default tile size
    public static int scale = 3; // Scale factor (can be updated dynamically)
    public static int tileSize = originalTileSize * scale; // Final tile size (scaled) 48 is default size

    // Screen settings
    public static final int maxScreenCol = 16; // Number of columns
    public static final int maxScreenRow = 12; // Number of rows
    public static int screenWidth = tileSize * maxScreenCol; // Total screen width
    public static int screenHeight = tileSize * maxScreenRow; // Total screen height
}