package ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    int barWidth = 200;
    int barHeight = 20;

    private ArrayList<String> healthBarImages;
    private ArrayList<String> soulsBarImages;
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

        soulsBarImages = new ArrayList<>();
        for (int i = 1; i <= 29; i++) {
            soulsBarImages.add("assets/images/ui/souls/s" + i + ".png");
        }
    }

    public void drawHealthBar(int health) {
        int index = Math.max(0, Math.min(19, (int) Math.ceil((100 - health) / 5.0)));
        String imagePath = healthBarImages.get(index);

        SaxionApp.drawImage(imagePath, 70, 10);
    }

    public void drawSoulsBar(int souls) {
        int index = souls * 1;
        String imagePath = soulsBarImages.get(index);

        SaxionApp.drawImage(imagePath, 5, 10,61,70);
    }

    public void drawStaminaBar(int stamina, int maxStamina) {
        int index = Math.max(0, Math.min(20, (int) Math.ceil((100 - (stamina * 100.0 / maxStamina)) / 5.0)));
        String imagePath = staminaBarImages.get(index);

        SaxionApp.drawImage(imagePath, 70, 45);
    }


}