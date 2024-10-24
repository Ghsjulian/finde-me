package com.my.ghs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	private EditText ipAddress, port;
	private Button connect, disconnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ipAddress = findViewById(R.id.ip);
		port = findViewById(R.id.port);
		connect = findViewById(R.id.connect);
		disconnect = findViewById(R.id.disconnect);

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

		// Start The Intent Here 
		//	Intent serviceIntent = new Intent(this, SocketService.class);
		//	startService(serviceIntent);
	}

	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, "Input: " + msg, Toast.LENGTH_SHORT).show();

	}
}