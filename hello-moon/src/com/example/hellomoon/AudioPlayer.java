package com.example.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {

	private MediaPlayer mPlayer;

	public void stop() {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
	}

	public void play(Context context) {
		stop();

		mPlayer = MediaPlayer.create(context, R.raw.one_small_step);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
			}
		});

		mPlayer.start();
	}

}
