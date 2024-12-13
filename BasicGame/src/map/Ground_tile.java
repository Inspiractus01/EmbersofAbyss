package map;
import nl.saxion.app.SaxionApp;

public class Ground_tile extends Tile {
    public Ground_tile(int x, int y, int width, int height, boolean solid) {
        super(x, y, width, height, solid);
    }

    String path = "assets/images/tiles/ground_tile2.png";

    @Override
    public void draw() {
        SaxionApp.drawImage(path, x, y, width, height);
    }
}
