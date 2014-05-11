package com.droidboosters.teachaids.fragments;


import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.support.v4.app.Fragment;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.activities.TeachAIDSActivity;



public class PreventionFragment extends Fragment {

	VideoView video;
	TextView listen;
	public PreventionFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_introduction, container, false);
        listen = (TextView) rootView.findViewById(R.id.textView1);
        if(((TeachAIDSActivity)getActivity()).getLanguage().equals("hi"))
        {
        	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mangal.ttf");
        	listen.setTypeface(tf);
        }
        else
        {
        	 if(((TeachAIDSActivity)getActivity()).getLanguage().equals("ta"))
             {
             	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bamini.ttf");
             	listen.setTypeface(tf);
             }
        }
        listen.setText(getActivity().getResources().getString(R.string.listen));
        VideoView view = (VideoView)rootView.findViewById(R.id.videoView1);
        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.aidsprevention;
        view.setVideoURI(Uri.parse(path));
        view.start();
        
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

   

    }

   
}
