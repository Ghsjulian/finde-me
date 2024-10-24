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

public class MainActivity extends AppCompatActivity {
	private EditText ipAddress, port;
	private Button connect, disconnect;
	GetLocation location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ipAddress = findViewById(R.id.ip);
		port = findViewById(R.id.port);
		connect = findViewById(R.id.connect);
		disconnect = findViewById(R.id.disconnect);
		// Get The Cordinator Lat And Log
		location = new GetLocation(this);
		double data[] = location.getLocation();
		String latitude = String.valueOf(data[0]);
		String longitude = String.valueOf(data[1]);

		// Call the Click Button Here
		connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Step 3: Retrieve the input value
				String inputValue = ipAddress.getText().toString();
				showToast(inputValue);
				// start the service 
				Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
				serviceIntent.putExtra("ip", inputValue);
				serviceIntent.putExtra("lat", latitude);
				serviceIntent.putExtra("lon", longitude);
				serviceIntent.putExtra("port", Integer.parseInt(port.getText().toString()));
				startService(serviceIntent);
			}
		});

		// Disconnect The Service 
		disconnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Send a broadcast to the service to stop it
				Intent stopIntent = new Intent(MyService.ACTION_STOP_SERVICE);
				sendBroadcast(stopIntent);
				showToast("Foreground Service Stopped!");
			}
		});
	}

	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, "Input: " + msg, Toast.LENGTH_SHORT).show();

	}
}