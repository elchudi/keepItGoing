package es.bcn.keepitgoing.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import es.bcn.keepitgoing.activities.GetCurrentMilis;

import android.util.Log;

public class ComunicationOut extends Thread {
    private static final long STEP = 5000;
    private GetCurrentMilis time;
    private long elapsed = 0;

    public ComunicationOut(GetCurrentMilis audioActivity) {
        this.time = audioActivity;
    }

    @Override
    public void run() {
        elapsed = System.currentTimeMillis();
        while (true) {
            if ( System.currentTimeMillis() - elapsed > STEP) {
                DatagramSocket socket = null;
                InetAddress serverAdress = null;

                try {
                    socket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }

                String hola = "" + time.getCurrentMilis();
                Log.d("MI_DEP", "position is: " + hola);
                byte[] buf = hola.getBytes();

                int port = 4451;

                try {
                    serverAdress = InetAddress.getByName("192.168.1.113");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                DatagramPacket packete = new DatagramPacket(buf, buf.length, serverAdress, port);

                try {
                    socket.send(packete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                elapsed = System.currentTimeMillis();
            }
        }
    }

}
