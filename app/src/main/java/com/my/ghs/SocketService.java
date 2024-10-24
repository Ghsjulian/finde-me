package com.my.ghs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketService extends Service {
    private static final String CHANNEL_ID = "SocketServiceChannel";
    private Socket socket;
    private BufferedReader input;
    private OutputStreamWriter output;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, getNotification());
        new Thread(new SocketClient()).start();
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Socket Service")
                .setContentText("Running...")
               // .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Socket Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class SocketClient implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket("127.0.0.1", 8080);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new OutputStreamWriter(socket.getOutputStream());

                // Example: Send a message
                output.write("Hello Server");
                output.flush();

                // Example: Read messages
                String response;
                while ((response = input.readLine()) != null) {
                    // Handle the response from the server
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}