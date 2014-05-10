package com.droidboosters.teachaids.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.models.VideosModel;
import com.droidboosters.teachaids.utils.ImageLoader;
import com.droidboosters.teachaids.utils.ImageViewLoader;

/**
 * 
 * Adapter class for videos list fragment
 */
public class VideosAdapter extends ArrayAdapter<VideosModel> {

	VideosModel video;
	// String url;
	LayoutInflater inflater;
	private Context con;
	private List<VideosModel> video_list = new ArrayList<VideosModel>();
	private int layout;
	public ImageViewLoader imageLoader;

	public VideosAdapter(Context context, int layout, List<VideosModel> list) {
		super(context, layout, list);
		this.con = context;
		this.video_list = list;
		this.layout = layout;
		imageLoader = new ImageViewLoader(context);
	}

	@Override
	public int getCount() {

		return video_list.size();
	}

	@Override
	public VideosModel getItem(int position) {

		return video_list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return video_list.get(position).hashCode();
	}

	/**
	 * Static class used to avoid the calling of "findViewById" every time the
	 * getView() method is called, because this can impact to your application
	 * performance when your list is too big. The class is static so it cache
	 * all the things inside once it's created.
	 */
	private static class ViewHolder {

		protected TextView titleText;
		protected ImageView videoThumbnail;
		protected String videoUrl;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		video = video_list.get(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			inflater = (LayoutInflater) con
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, parent, false);
			viewHolder.titleText = (TextView) convertView
					.findViewById(R.id.video_title);
			viewHolder.videoThumbnail = (ImageView) convertView
					.findViewById(R.id.video_thumbnail);

			viewHolder.videoUrl = "";
			// the setTag is used to store the data within this view
			convertView.setTag(viewHolder);
		} else {
			// the getTag returns the viewHolder object set as a tag to the view
			viewHolder = (ViewHolder) convertView.getTag();

		}

		viewHolder.videoUrl = video.getUrl();

		viewHolder.titleText.setText(video.getTitle());
		
		

		imageLoader.DisplayImage(video.getThumbnail(),
				viewHolder.videoThumbnail);

		return convertView;

	}

}
