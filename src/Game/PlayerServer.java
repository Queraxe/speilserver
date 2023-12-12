package Game;

import java.io.*;
import java.net.Socket;

import static Game.PlayerFrame.*;

public class PlayerServer {

    static String serverIP = "192.168.1.100";
    //static String serverIP = "localhost";
    static int port = 9933;
    static Socket socket;
    static int playerID;

    static ReadFromServer rfsRunnable;
    static WriteToServer wtsRunnable;

    public PlayerServer(){
        System.out.println("=== PLAYER ===");
    }

    public static void connect(){
        try {
            socket = new Socket(serverIP, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            playerID = in.readInt();
            System.out.println("You are connected");
            System.out.println("You are Player #" + playerID);

            if (playerID == 1){
                System.out.println("Waiting for Player #2");
            }

            createPlayers();

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();

        } catch (IOException ex){
            System.out.println("IOException from connect()");
        }
    }

    private static class ReadFromServer implements Runnable{
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in){
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (enemy != null) {
                        // update the enemy position
                        enemy.setX(dataIn.readDouble());
                        enemy.setY(dataIn.readDouble());
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }
        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);

                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();

            } catch (IOException ex){
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }
    private static class WriteToServer implements Runnable{
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out){
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (me != null) {
                        // send my positions
                        dataOut.writeDouble(me.getX());
                        dataOut.writeDouble(me.getY());
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }

            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }
}
