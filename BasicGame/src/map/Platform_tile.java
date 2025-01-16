package map;
import nl.saxion.app.SaxionApp;
import game.Camera;

public class Platform_tile extends Tile{
    public Platform_tile(int x, int y, int width, int height, boolean solid) {
        super(x, y + (height / 4), width, height / 2, solid);
    }

    String path = "assets/images/tiles/platform1.png";

    @Override
    public void draw(Camera camera) {
        SaxionApp.drawImage(path, x - camera.getX(), y - camera.getY(), width, height);
    }
}