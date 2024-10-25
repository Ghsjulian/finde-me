package com.my.ghs;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PermissionHelper {
	
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
	private Activity activity;
	
	public PermissionHelper(Activity activity) {
		this.activity = activity;
	}
	
	public void requestLocationPermission() {
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// Request the permission
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
			} else {
			// Permission already granted
			onPermissionGranted();
		}
	}
	
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission was granted
				onPermissionGranted();
				} else {
				// Permission was denied
				Toast.makeText(activity, "Location permission denied", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void onPermissionGranted() {
		// Handle the case when permission is granted
		Toast.makeText(activity, "Location permission granted", Toast.LENGTH_SHORT).show();
		// You can also call a method to access location here if needed
	}
}