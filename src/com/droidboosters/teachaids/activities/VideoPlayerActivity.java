package com.droidboosters.teachaids.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.application.AppConstants;
import com.droidboosters.teachaids.utils.TeachAIDSMediaController;
import com.droidboosters.teachaids.utils.TeachAIDSVideoView;
import com.droidboosters.teachaids.utils.ImageViewLoader;


public class VideoPlayerActivity extends Activity {

	private TeachAIDSVideoView videoView;
	private int videoIndex = 0, lastVideoPosition = 0, videoDuration = 0,
			currVideoIndex = 0;
	private Uri videoUri;
	private ProgressBar progressBar;
	private RelativeLayout postVideoLayout;
	private CountDownTimer timer;
	private TextView counter_text;
	private ImageView next_video_image;
	private ImageViewLoader imageViewLoader;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		// Init ui componenets
		videoView = (TeachAIDSVideoView) findViewById(R.id.videoView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		postVideoLayout = (RelativeLayout) findViewById(R.id.post_video_layout);
		counter_text = (TextView) findViewById(R.id.counter_text);
		next_video_image = (ImageView) findViewById(R.id.nextvideo);
		progressBar.setVisibility(View.VISIBLE);

		imageViewLoader = new ImageViewLoader(getApplicationContext());

		Intent receivedData = getIntent();
		videoIndex = receivedData.getIntExtra("index", 0);
		currVideoIndex = videoIndex;
		

		startPlayer(videoIndex);

	}

	private void startPlayer(int index) {

		videoUri = Uri.parse(AppConstants.videosList.get(index).getUrl());
		videoView.setMediaController(new TeachAIDSMediaController(this));
		videoView.setOnErrorListener(videoViewOnErrorListener);
		videoView.setOnCompletionListener(videoViewOnCompletionListener);
		videoView.setOnPreparedListener(videoviewOnPreparedListener);
		videoView.setVideoURI(videoUri);
		videoView.requestFocus();
		videoView.start();
	}

	private void stopPlayer() {
		// Stop the video player
		if (videoView.isPlaying()) {
			videoView.stopPlayback();
		}
	}

	private void resumePlayer() {
		videoView.setVisibility(View.VISIBLE);
		videoView.seekTo(lastVideoPosition);
		videoView.start();
	}

	/**
	 * Internal Method: this will be called when the app is going to background
	 * 
	 * @param NIL
	 * @return NIL
	 */
	@Override
	protected void onPause() {
		super.onPause();
		lastVideoPosition = videoView.getCurrentPosition();
		videoView.pause();
	}

	/**
	 * Internal Method: this will be called when the app is opened from
	 * background
	 * 
	 * @param NIL
	 * @return NIL
	 */
	@Override
	protected void onResume() {
		super.onResume();
		videoView.seekTo(lastVideoPosition);
		videoView.start();
	}

	private final MediaPlayer.OnPreparedListener videoviewOnPreparedListener = new MediaPlayer.OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mp) {
			progressBar.setVisibility(View.GONE);
			videoDuration = videoView.getDuration();

		}
	};

	private final MediaPlayer.OnErrorListener videoViewOnErrorListener = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			finish();
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			return false;
		}
	};

	private final MediaPlayer.OnCompletionListener videoViewOnCompletionListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			videoView.stopPlayback();
			videoView.setVisibility(View.GONE);
			postVideoLayout.setVisibility(View.VISIBLE);

			currVideoIndex += 1;
			if (currVideoIndex < AppConstants.videosList.size()) {
				imageViewLoader.DisplayImage(
						AppConstants.videosList.get(currVideoIndex)
								.getThumbnail(), next_video_image);

				timer = new CountDownTimer(11000, 1000) {
					@Override
					public void onTick(long l) {
						counter_text.setText("" + l / 1000);
					}

					@Override
					public void onFinish() {
						
						postVideoLayout.setVisibility(View.GONE);
						videoView.setVisibility(View.VISIBLE);
						startPlayer(currVideoIndex);
					}
				};
				timer.start();
			} else {
				Toast.makeText(getApplicationContext(),
						"Come back after sometime!", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		}
	};

}
