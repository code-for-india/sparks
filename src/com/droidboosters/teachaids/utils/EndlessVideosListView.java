package com.droidboosters.teachaids.utils;

import java.util.List;

import com.droidboosters.teachaids.adapters.VideosAdapter;
import com.droidboosters.teachaids.models.VideosModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;




/**
 * Class for maintaining infinite videos page on scroll
 */
public class EndlessVideosListView extends ListView implements OnScrollListener {

	private View footer;
	private boolean isLoading;
	private EndlessListener listener;
	private VideosAdapter adapter;

	

	public EndlessVideosListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.setOnScrollListener(this);
		
	}

	public EndlessVideosListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnScrollListener(this);
		
	}

	public EndlessVideosListView(Context context) {
		super(context);
		this.setOnScrollListener(this);
		
	}

	public void setListener(EndlessListener listener) {
		this.listener = listener;

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if (getAdapter() == null)
			return;

		if (getAdapter().getCount() == 0)
			return;

		

			int l = visibleItemCount + firstVisibleItem;
			if (l >= totalItemCount && !isLoading) {
				// It is time to add new data. We call the listener
				this.addFooterView(footer);
				isLoading = true;
				listener.loadData();

			
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	public void setLoadingView(int resId) {
		LayoutInflater inflater = (LayoutInflater) super.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footer = (View) inflater.inflate(resId, null);
		this.addFooterView(footer);

	}

	public void setAdapter(VideosAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
		this.removeFooterView(footer);
	}

	// This method adds the new data at the end of the list
	public void addNewData(List<VideosModel> data) {
		this.removeFooterView(footer);
		adapter.addAll(data);
		adapter.setNotifyOnChange(true);
		adapter.notifyDataSetChanged();
		isLoading = false;
	}

	public EndlessListener setListener() {
		return listener;
	}

	// Listner called when the scroll reaches the end
	public static interface EndlessListener {
		public void loadData();
	}

}
