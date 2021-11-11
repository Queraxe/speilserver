package Game;

public class PlayerMain {

    // main things
    public static void main(String[] args) {

        PlayerServer.connect();

        new PlayerServer();

        PlayerFrame.GUI();

    }

}
