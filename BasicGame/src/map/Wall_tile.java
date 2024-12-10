package map;
import nl.saxion.app.SaxionApp;

public class Wall_tile extends Tile{
    public Wall_tile(int x, int y, int width, int height, boolean solid) {
        super(x, y, width, height, solid);
    }

    String path = "assets/images/tiles/wall_tile.png";

    @Override
    public void draw() {
        SaxionApp.drawImage(path, x, y, width, height);
    }
}
