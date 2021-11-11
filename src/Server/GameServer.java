package Server;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GameServer {

    static ServerSocket ss;
    int numPort = 9933;
    static int numPlayers;
    int maxPlayers;

    Socket p1Socket;
    Socket p2Socket;
    static ReadFromClient p1ReadRunnable, p2ReadRunnable;
    static WriteToClient p1WriteRunnable, p2WriteRunnable;

    public static double p1x, p1y, p2x, p2y;


    public GameServer() {
        // define some ints
        System.out.println("=== GAME SERVER ===");
        numPlayers = 0;
        maxPlayers = 2;

        p1x = 100;
        p1y = 200;
        p2x = 200;
        p2y = 200;

        try {

            ss = new ServerSocket(numPort);

        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }

    // accept the players
    public void acceptClients() {

        while (numPlayers < maxPlayers) {
            try {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " connected");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }

            } catch (IOException e) {
                System.out.println("IOException from acceptClient()");
            }
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));

        System.out.println("--The GAME begins!--");
    }


    public static void end() {
        numPlayers = 0;

        try {
            ss.close();
        } catch (IOException e) {
            System.out.println("IOException from close");
        }
        System.out.println("\n");
    }


    // read the clients msg
    private static class ReadFromClient implements Runnable {

        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + "Runnable created");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                    } else {
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from RFC run()");
            }
            try {
                dataIn.close();
            } catch (IOException e) {
                System.out.println("end");
            }
        }
    }

    // write the clients msg
    private static class WriteToClient implements Runnable {

        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream in) {
            playerID = pid;
            dataOut = in;
            System.out.println("WTC" + playerID + "Runnable created");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                    } else {
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                    }
                    dataOut.flush();
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }

            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
                numPlayers --;
            }
            try {
                dataOut.close();
            } catch (IOException e) {
                System.out.println("end");
            }
        }

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("--- GO! ---");
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }

    }

    // main methode
    public static void main(String[] args) {
        boolean runAgain = true;
        while (runAgain) {

            GameServer gs = new GameServer();
            gs.acceptClients();
            while (numPlayers != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException from Thread.sleep");
                }
            }
            end();
        }
        System.out.println("while in main interrupted");
    }


}
