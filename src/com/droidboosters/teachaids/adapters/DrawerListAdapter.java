package com.droidboosters.teachaids.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidboosters.teachaids.R;

import java.util.ArrayList;

import com.droidboosters.teachaids.models.NavDrawerItem;

public class DrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private String lang;
	private Typeface font;
	// slide menu items
	private String[] navMenuTitles;
	
	public DrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems, String lang){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		this.lang = lang;
		if(lang.equals("hi"))
			font = Typeface.createFromAsset(context.getAssets(), "fonts/DevLysThin.ttf");
		if(lang.equals("ta"))
			font = Typeface.createFromAsset(context.getAssets(), "fonts/DevLysThin.ttf");
		
		// load slide menu items
		navMenuTitles = context.getResources().getStringArray(R.array.nav_drawer_items);
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
         
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        if(this.lang.equals("hi"))
        {
        	txtTitle.setText(navMenuTitles[position]);
        	txtTitle.setTypeface(font);
        }
        else
        {
        	if(this.lang.equals("ta"))
        	{
        		txtTitle.setText(navMenuTitles[position]);
            	txtTitle.setTypeface(font);
        	}
        	else	
             	txtTitle.setText(navMenuTitles[position]);
        }
       
        
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}

}
