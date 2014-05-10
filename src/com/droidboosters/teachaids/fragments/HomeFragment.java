package com.droidboosters.teachaids.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capricorn.ArcMenu;
import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.activities.TeachAIDSActivity;
import com.droidboosters.teachaids.activities.interfaces.FragmentToActivity;
import com.droidboosters.teachaids.customviews.RoundedImageView;

public class HomeFragment extends Fragment {

    private TextView headerText;
    private TextView textWidget;
    FragmentToActivity fragmentToActivity;
    public HomeFragment(){}

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

      int[] ITEM_DRAWABLES = { R.drawable.arc_ic_home, R.drawable.arc_ic_friends,
                  R.drawable.arc_ic_clicknshare, R.drawable.arc_ic_alldeals,
              R.drawable.arc_ic_mydeals, R.drawable.arc_ic_checkin };


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //RoundedImageView iconImage = (RoundedImageView)rootView.findViewById(R.id.userIcon);
        //iconImage.setImageDrawable(getResources().getDrawable(R.drawable.karthi));
        //headerText = (TextView) rootView.findViewById(R.id.txtLabel);
        textWidget = (TextView) rootView.findViewById(R.id.textWidget);
        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
        //headerText.setTypeface(tf);
        
        if(((TeachAIDSActivity)getActivity()).getLanguage().equals("hi"))
        {
        	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mangal.ttf");
        	textWidget.setTypeface(tf);
        }
        else
        {
        	 if(((TeachAIDSActivity)getActivity()).getLanguage().equals("ta"))
             {
             	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bamini.ttf");
             	textWidget.setTypeface(tf);
             }
        }
        
        textWidget.setSelected(true);

        ArcMenu arcMenu = (ArcMenu) rootView.findViewById(R.id.arc_menu);

        initArcMenu(arcMenu, ITEM_DRAWABLES);



        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentToActivity = (FragmentToActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement fragmentToActivity");
        }
    }


    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(getActivity());
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    fragmentToActivity.callFragment(position);
                    Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
