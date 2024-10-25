package com.my.ghs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetInfo {
	
	public String getIpAddress() {
		String ipAddress = null;
		HttpURLConnection urlConnection = null;
		
		try {
			URL url = new URL("https://api.ipify.org/");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			urlConnection.connect();
			
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
				ipAddress = response.toString().trim();
				} else {
				System.err.println("Error: " + urlConnection.getResponseCode());
			}
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return ipAddress;
	}
}