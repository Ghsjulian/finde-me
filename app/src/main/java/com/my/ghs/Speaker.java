package com.my.ghs ;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import java.util.Locale;

public class Speaker implements OnInitListener {
	private TextToSpeech textToSpeech;
	private boolean isTTSInitialized = false;
	
	public Speaker(Context context) {
		textToSpeech = new TextToSpeech(context, this);
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = textToSpeech.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language not supported");
				} else {
				isTTSInitialized = true;
			}
			} else {
			Log.e("TTS", "Initialization failed");
		}
	}
	
	public void speak(String text) {
		if (isTTSInitialized) {
			textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
			} else {
			Log.e("TTS", "TextToSpeech not initialized");
		}
	}
	
	public void shutdown() {
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	}
}