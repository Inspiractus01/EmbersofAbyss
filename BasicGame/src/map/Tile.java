package map;

import java.awt.Rectangle;
import game.Camera;
import main.GameSettings;

public abstract class Tile {
    protected int x, y, width, height;
    protected boolean solid;

    public Tile(int x, int y, int width, int height, boolean solid) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
    }

    public abstract void draw(Camera camera);

    public boolean isSolid() {
        return solid;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isVisible(Camera camera) {
        int cameraX = camera.getX();
        int cameraY = camera.getY()+100;
        int screenWidth = GameSettings.screenWidth;
        int screenHeight = GameSettings.screenHeight;

        return x + width > cameraX && x < cameraX + screenWidth &&
               y + height > cameraY && y < cameraY + screenHeight;
    }
}