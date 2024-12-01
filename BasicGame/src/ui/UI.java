package ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;

public class UI {
    public void drawHealthBar(int health) {
        int barWidth = 200;
        int barHeight = 20;
        int filledWidth = (int) ((health / 100.0) * barWidth);

        SaxionApp.setFill(new Color(50, 50, 50));
        SaxionApp.drawRectangle(10, 10, barWidth, barHeight);

        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(10, 10, filledWidth, barHeight);
    }

    public void drawStaminaBar(int stamina, int maxStamina) {
        int barWidth = 200;
        int barHeight = 20;
        int filledWidth = (int) ((stamina / (double) maxStamina) * barWidth);

        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(10, 40, barWidth, barHeight);

        SaxionApp.setFill(Color.YELLOW);
        SaxionApp.drawRectangle(10, 40, filledWidth, barHeight);
    }
}