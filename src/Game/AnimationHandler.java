package Game; //kek

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationHandler {
    public AnimationHandler() {
        int interval = 10;
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double speed = 5;

                if (PlayerFrame.up) {
                    PlayerFrame.me.moveV(-speed);
                }
                if (PlayerFrame.down) {
                    PlayerFrame.me.moveV(speed);
                }
                if (PlayerFrame.left) {
                    PlayerFrame.me.moveH(-speed);
                }
                if (PlayerFrame.right) {
                    PlayerFrame.me.moveH(speed);
                }
                PlayerFrame.dc.repaint();
            }
        };
        Timer animationTimer = new Timer(interval, al);
        animationTimer.start();
    }
}
