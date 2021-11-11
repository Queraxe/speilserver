package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            PlayerFrame.up = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            PlayerFrame.down = true;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            PlayerFrame.left = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            PlayerFrame.right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            PlayerFrame.up = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            PlayerFrame.down = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            PlayerFrame.left = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            PlayerFrame.right = false;
        }
    }
}

