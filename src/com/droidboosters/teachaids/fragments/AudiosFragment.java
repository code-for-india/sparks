package com.droidboosters.teachaids.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.activities.TrailerVideoPlayerActivity;
import com.droidboosters.teachaids.adapters.VideosAdapter;
import com.droidboosters.teachaids.application.AppConstants;
import com.droidboosters.teachaids.utils.EndlessVideosListView;
import com.droidboosters.teachaids.utils.TypefaceSpan;

public class AudiosFragment extends Fragment implements
		EndlessVideosListView.EndlessListener {

	private View view;
	private ListView listView;
	private ProgressBar progressBar;

	public AudiosFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/*
	 * Event Handling when the back button is pressed. It will open the previous
	 * fragment which is below on the stack
	 */
//	public void onBackPressed() {
//		FragmentManager fm = getFragmentManager();
//		fm.popBackStackImmediate();
//
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		ActionBar bar = getActivity().getActionBar();
//		SpannableString s = new SpannableString("Trailers");
//		s.setSpan(new TypefaceSpan(getActivity(),
//				"Roboto-Condensed.ttf"), 0, s.length(),
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		bar.setTitle(s);
//		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(
//				R.color.trailer_videos_icon_color)));
		
		view = inflater.inflate(R.layout.fragment_videos, container, false);
		listView = (ListView) view.findViewById(android.R.id.list);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		listView.setAdapter(new VideosAdapter(getActivity(),
				R.layout.videos_item_layout, AppConstants.trailervideosList));

		progressBar.setVisibility(View.GONE);

		// On click listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (isNetworkAvailable()) {

					Intent intent = new Intent(getActivity(),
							TrailerVideoPlayerActivity.class);
					intent.putExtra("index", arg2);
					startActivity(intent);
					getActivity().overridePendingTransition(
							android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);

				} else
					Toast.makeText(getActivity(),
							"You don't have active network connection!",
							Toast.LENGTH_SHORT).show();
			}

		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void loadData() {
		// load more data here

	}

	// Internet connection check
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}
}
