package entity;
import java.awt.*;

public class Enemy {
    private int x; // X position of the enemy
    private int y; // Y position of the enemy
    private final int speed; // Speed of the enemy
    private final int size; // Size of the enemy square
    private final int groundLevel; // Ground level to keep enemy at the same vertical level

    // Constructor
    public Enemy(int startX, int groundLevel, int speed, int size) {
        this.x = startX; // Starting X position
        this.y = groundLevel; // Starting Y position at the ground level
        this.speed = speed; // Movement speed
        this.size = size; // Size of the enemy square
        this.groundLevel = groundLevel; // Ground level position
    }

    // Method to move the enemy toward the player
    public void moveTowards(int playerX, int playerY) {
        // Calculate direction vector
        int dx = playerX - x;
        int dy = playerY - y;

        // Normalize direction vector and move enemy
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length > 0) {
            x += (int) (speed * (dx / length)); // Move in X direction
            y = groundLevel; // Keep the enemy on the ground level
        }
    }

    // Method to draw the enemy on the screen
    public void draw(Graphics g) {
        g.setColor(Color.RED); // Enemy color
        g.fillRect(x, y, size, size); // Draw the enemy as a square
    }

    // Getters for position (if needed in other parts of the program)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
