package Game;

import javax.swing.*;
import java.awt.*; // penis und co. blablabla
import java.awt.*; // penis

import static Game.PlayerServer.playerID;

public class PlayerFrame {

    static PlayerSprite me, enemy;
    static int width = 600;
    static int height = 400;
    static public PlayerFrame.DrawingComponent dc;
    static JFrame frame;
    static boolean up = false, down = false, left = false, right = false;

    public PlayerFrame(){
        GUI();
    }

    // create GUI and set keyListener
    public static void GUI(){
        frame = new JFrame();
        frame.setSize(width, height);
        frame.setTitle("Player #" + playerID);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dc = new PlayerFrame.DrawingComponent();
        frame.add(dc);

        frame.setVisible(true);

        new AnimationHandler();
        frame.addKeyListener(new KeyHandler());
        frame.setFocusable(true);
    }

    public static void createPlayers(){

        if (playerID == 1){
            me = new PlayerSprite(100,200, 50, Color.blue);
            enemy = new PlayerSprite(200,200,50, Color.red);
        } else {
            enemy = new PlayerSprite(100,200, 50, Color.blue);
            me = new PlayerSprite(200,200,50, Color.red);
        }
        System.out.println("playes created");
    }

    public static class DrawingComponent extends JComponent {
        protected void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            me.drawSprite(g2d);
            enemy.drawSprite(g2d);
        }
    }

}
