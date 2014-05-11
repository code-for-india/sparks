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
import com.droidboosters.teachaids.adapters.FactsAdapter;
import com.droidboosters.teachaids.adapters.QuizAdapter;
public class FactsFragment extends Fragment {
	
	public FactsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_facts, container, false);
       
	    ViewPager pager=(ViewPager)rootView.findViewById(R.id.pager);

	    pager.setAdapter(buildAdapter());
	    PagerTabStrip pagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.pagerTabSTrip);
	    pagerTabStrip.setDrawFullUnderline(true);
	    pagerTabStrip.setTabIndicatorColor(Color.BLUE);
		return pager;

    }
	
	 private PagerAdapter buildAdapter() {
		    return(new FactsAdapter(getActivity(), getChildFragmentManager()));
		  }
}
