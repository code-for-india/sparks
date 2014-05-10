package com.droidboosters.teachaids.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Custom class for Media player controls
 */

public class TeachAIDSMediaController extends MediaController {

	MyListener mListener;

	public TeachAIDSMediaController(Context context) {
		super(context);
	}

	public TeachAIDSMediaController(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TeachAIDSMediaController(Context context, boolean useFastForward) {
		super(context, useFastForward);
	}

	@Override
	public void show(int timeout) {
		// This limits the media controls to show only for 3000ms
		super.show(3000);

	}

	@Override
	public void hide() {
		super.hide();
	}

	public interface MyListener {
		public void onSetVisibilityCalled();
	}

	public void registerListener(MyListener myListener) {
		this.mListener = myListener;
	}

	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if (mListener != null)
			mListener.onSetVisibilityCalled();
	}

}
