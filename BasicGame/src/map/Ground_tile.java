package map;
import nl.saxion.app.SaxionApp;
import game.Camera;

public class Ground_tile extends Tile {
    public Ground_tile(int x, int y, int width, int height, boolean solid) {
        super(x, y, width, height, solid);
    }

    String path = "assets/images/tiles/ground_new.png";

    @Override
    public void draw(Camera camera) {
        SaxionApp.drawImage(path, x - camera.getX(), y - camera.getY(), width, height);
    }
}