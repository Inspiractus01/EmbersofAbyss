package ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private ArrayList<String> healthBarImages;
    private ArrayList<String> staminaBarImages;

    public UI() {
        healthBarImages = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            healthBarImages.add("assets/images/ui/healthbar/h" + i + ".png");
        }

        staminaBarImages = new ArrayList<>();
        for (int i = 1; i <= 21; i++) {
            staminaBarImages.add("assets/images/ui/stamina/s" + i + ".png");
        }
    }

    public void drawHealthBar(int health) {
        int index = Math.max(0, Math.min(19, (int) Math.ceil((100 - health) / 5.0)));
        String imagePath = healthBarImages.get(index);

        SaxionApp.drawImage(imagePath, 10, 10);
    }

    public void drawStaminaBar(int stamina, int maxStamina) {
        int index = Math.max(0, Math.min(20, (int) Math.ceil((100 - (stamina * 100.0 / maxStamina)) / 5.0)));
        String imagePath = staminaBarImages.get(index);

        SaxionApp.drawImage(imagePath, 10, 45);
    }
}