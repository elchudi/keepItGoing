package es.bcn.keepitgoing.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.media.MediaPlayer;
import android.util.Log;

public class ComunicationIn extends Thread {

    private String received;
    private MediaPlayer mPlayer;
    private boolean flag = true;
    private long cost;

    public ComunicationIn(MediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    @Override
    public void run() {

        DatagramSocket mySocket = null;

        try {

            mySocket = new DatagramSocket(4451);

        } catch (SocketException e) {
            e.printStackTrace();
        }

        Log.d("MI_DEP", "Activo! esperando...");

        byte[] buf = new byte[10];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        while (true) {
            
            try {
                mySocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            mPlayer.start();
            received = new String(packet.getData(), 0, packet.getLength());
            mPlayer.seekTo(Integer.parseInt(received));
            Log.d("MI_DEP", "coste " + (System.currentTimeMillis() - cost));
            Log.d("MI_DEP", "posicion " + received);
        }

    }

}
