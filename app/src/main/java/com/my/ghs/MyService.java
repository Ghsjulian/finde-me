package com.my.ghs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyService extends Service {
	private static final String CHANNEL_ID = "SocketServiceChannel";
	public static final String ACTION_STOP_SERVICE = "com.my.ghs.ACTION_STOP_SERVICE";
	private Socket socket;
	private String serverIp;
	private int serverPort;

	@Override
	public void onCreate() {
		super.onCreate();
		createNotificationChannel();
		startForeground(1, getNotification());
		registerReceiver(stopReceiver, new IntentFilter(ACTION_STOP_SERVICE));
	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Socket Service Channel",
					NotificationManager.IMPORTANCE_DEFAULT);
			NotificationManager manager = getSystemService(NotificationManager.class);
			manager.createNotificationChannel(serviceChannel);
		}
	}

	private Notification getNotification() {
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		return new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Socket Service")
				.setContentText("Running...")
				//.setSmallIcon(R.drawable.ic_notification) // Replace with your icon
				.setContentIntent(pendingIntent).build();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		serverIp = intent.getStringExtra("ip");
		serverPort = intent.getIntExtra("port", 0);

		if (serverIp == null || serverPort == 0) {
			// Handle error: IP or port not provided
			stopSelf();
			return START_NOT_STICKY;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = new Socket(serverIp, serverPort);
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream();

					// Handle communication with the server here
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		unregisterReceiver(stopReceiver);
	}

	private final BroadcastReceiver stopReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			stopSelf(); // Stop the service
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null; // We don't provide binding
	}

	public void showToast(String msg) {
		Toast.makeText(this, "Message  :  " + msg, Toast.LENGTH_SHORT).show();

	}
}