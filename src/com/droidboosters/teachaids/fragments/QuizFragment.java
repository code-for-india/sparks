package com.droidboosters.teachaids.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.adapters.QuizAdapter;

public class QuizFragment extends Fragment {
	
	public QuizFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
	    View result=inflater.inflate(R.layout.fragment_quiz, container, false);
	    ViewPager pager=(ViewPager)result.findViewById(R.id.pager);

	    pager.setAdapter(buildAdapter());
	    PagerTabStrip pagerTabStrip = (PagerTabStrip) result.findViewById(R.id.pagerTabSTrip);
	    pagerTabStrip.setDrawFullUnderline(true);
	    pagerTabStrip.setTabIndicatorColor(Color.RED);
		return pager;

    }
	
	 private PagerAdapter buildAdapter() {
		    return(new QuizAdapter(getActivity(), getChildFragmentManager()));
		  }
}
