package com.droidboosters.teachaids.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droidboosters.teachaids.R;

public class QuizFragment extends Fragment {
	
	public QuizFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_all_deals, container, false);
         
        return rootView;
    }
}
