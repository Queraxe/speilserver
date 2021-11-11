package Game;

public class PlayerMain {

    public static void main(String[] args) {

        PlayerServer.connect();

        new PlayerServer();

        PlayerFrame.GUI();

    }

}
