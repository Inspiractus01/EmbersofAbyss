package ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;

public class UI {
    int barWidth = 200;
    int barHeight = 20;

    public void drawHealthBar(int health) {
        int filledWidth = (int) ((health / 100.0) * barWidth);

        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(10, 10, barWidth, barHeight);

        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(10, 10, filledWidth, barHeight);
    }

    public void drawStaminaBar(int stamina, int maxStamina) {
        int filledWidth = (int) ((stamina / (double) maxStamina) * barWidth);

        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(10, 40, barWidth, barHeight);

        SaxionApp.setFill(Color.YELLOW);
        SaxionApp.drawRectangle(10, 40, filledWidth, barHeight);
    }

    public void drawSoulsCounter(int souls){
        int filledWidth = souls * 7;
        
        if(filledWidth > 200){
            filledWidth = 200;
        }

        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(10, 70, barWidth, barHeight);

        SaxionApp.setFill(Color.MAGENTA);
        SaxionApp.drawRectangle(10, 70, filledWidth, barHeight);
    }
}