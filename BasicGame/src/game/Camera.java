package game;

import main.GameSettings;

public class Camera {
    private int x, y;
    private final int screenWidth = GameSettings.screenWidth;
    private final int screenHeight = GameSettings.screenHeight;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int playerX, int playerY) {
        x = playerX - screenWidth / 2;
        y = playerY - screenHeight / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}