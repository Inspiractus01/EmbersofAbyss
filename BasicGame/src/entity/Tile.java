package entity;

import nl.saxion.app.SaxionApp;
import java.awt.Color;
import java.awt.Rectangle;

public class Tile {
    private final int x, y, width, height;
    private final boolean solid;

    public Tile(int x, int y, int width, int height, boolean solid) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
    }

    public void draw() {
        SaxionApp.setFill(solid ? Color.GRAY : Color.LIGHT_GRAY);
        SaxionApp.drawRectangle(x, y, width, height);
    }

    public boolean isSolid() {
        return solid;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}