package map;
import nl.saxion.app.SaxionApp;

public class Platform_tile extends Tile{
    public Platform_tile(int x, int y, int width, int height, boolean solid) {
        super(x, y + (height / 4), width, height / 2, solid);
    }

    String path = "assets/images/tiles/placeholder.png";

    @Override
    public void draw() {
        SaxionApp.drawImage(path, x, y, width, height);
    }
}
