package com.droidboosters.teachaids.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 
 * Custom class for Video view
 */

public class TeachAIDSVideoView extends VideoView {

	private PlayPauseListener mListener;

	public TeachAIDSVideoView(Context context) {
		super(context);
	}

	public TeachAIDSVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TeachAIDSVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// Custom play/pause listener method
	public void setPlayPauseListener(PlayPauseListener listener) {
		mListener = listener;
	}

	@Override
	public void pause() {
		super.pause();
		if (mListener != null) {
			mListener.onPause();
		}
	}

	@Override
	public void start() {
		super.start();
		if (mListener != null) {
			mListener.onPlay();
		}
	}

	/**
	 * Interface for the play/pause/seek to listener
	 * 
	 * 
	 * 
	 */
	public interface PlayPauseListener {
		void onPlay();

		void onPause();

		void onSeekTo(boolean ffwdrwd);
	}

	/**
	 * Listener when fast forward or rewind button is called.
	 * 
	 * @param Current
	 *            seek potion
	 * @return NIL. Calls the listener with a boolean value true if rewind/false
	 *         if forward
	 */
	@Override
	public void seekTo(int pos) {

		boolean ffwdrwd = false;
		if (super.getCurrentPosition() <= pos)
			ffwdrwd = false;
		else
			ffwdrwd = true;

		if (mListener != null) {
			mListener.onSeekTo(ffwdrwd);
		}

		super.seekTo(pos);
	}

}
