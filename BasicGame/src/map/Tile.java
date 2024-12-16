package map;

import java.awt.Rectangle;

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

    public abstract void draw();

    public boolean isSolid() {
        return solid;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}