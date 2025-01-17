package ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private ArrayList<String> healthBarImages;

    public UI() {
        healthBarImages = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            healthBarImages.add("assets/images/ui/healthbar/h" + i + ".png");
        }
    }

    public void drawHealthBar(int health) {
        int index = Math.max(0, Math.min(19, (int) Math.ceil((100 - health) / 5.0)));
        String imagePath = healthBarImages.get(index);
        SaxionApp.drawImage(imagePath, 10, 10);
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