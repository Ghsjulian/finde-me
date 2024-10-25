package com.my.ghs;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {
	private EditText ipAddress, port;
	private Button connect, disconnect;
	GetLocation location;
	
	
	// Saved Ip Address And Port Number 
	private String saveIp = "127.0.0.1";
	private int savePort = 8080;

	// Main Method Here
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ipAddress = findViewById(R.id.ip);
		port = findViewById(R.id.port);
		connect = findViewById(R.id.connect);
		disconnect = findViewById(R.id.disconnect);

		// Get The Cordinator Latitude And Logitute
		location = new GetLocation(this);
		double data[] = location.getLocation();
		String latitude = String.valueOf(data[0]);
		String longitude = String.valueOf(data[1]);
        
		
		/*
		// Call The Connect Button 
		// For Connecting With The Server
		// It Will Start The Foreground Service 
		// The Foreground Service Will Take Some Parameters
		*/
		
		/*
		Call The Foreground Service If The Address Exist
		*/
	     StartForeground(saveIp,savePort,latitude,longitude);
		/*
		*
		*
		*
		*/
		connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
				serviceIntent.putExtra("ip", ipAddress.getText().toString());
				serviceIntent.putExtra("port", Integer.parseInt(port.getText().toString()));
				serviceIntent.putExtra("lat", latitude);
				serviceIntent.putExtra("lon", longitude);
				startService(serviceIntent);
			}
		});

		// For Disconnect The Foreground Service 
		disconnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent stopIntent = new Intent(MyService.ACTION_STOP_SERVICE);
				sendBroadcast(stopIntent);
				showToast("Foreground Service Stopped!");
			}
		});
	}

	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	public void StartForeground(String ip, int port, String latitude, String longitude) {
		Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
		serviceIntent.putExtra("ip", ip);
		serviceIntent.putExtra("port", port);
		serviceIntent.putExtra("lat", latitude);
		serviceIntent.putExtra("lon", longitude);
		startService(serviceIntent);
	}
	
}