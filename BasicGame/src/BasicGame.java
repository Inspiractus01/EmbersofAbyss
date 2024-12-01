import nl.saxion.app.SaxionApp;

public class BasicGame {
    public static void main(String[] args) {
        Game game = new Game();
        SaxionApp.startGameLoop(game, 1000, 1000, 10);
    }
}