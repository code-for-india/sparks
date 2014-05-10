package com.droidboosters.teachaids.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.droidboosters.teachaids.R;

import com.droidboosters.teachaids.controllers.SharedPreferencesController;

public class SplashActivity extends Activity {

    // Variable declarations
    long SPLASH_SCREEN_TIME_OUT = 4000;
    private Handler handler;
    private Runnable runnable;
    private TextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen activity
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        setUpUI();
        launchMainActivity();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teach_aid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //UI setup related stuff
    private void setUpUI()
    {
        companyName = (TextView)  findViewById(R.id.company_name);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        companyName.setTypeface(tf);
    }



    private void launchMainActivity()
    {
        runnable = new Runnable() {
            @Override
            public void run() {
                //Call main activ ity
                Intent i = new Intent(SplashActivity.this, TeachAIDSActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);			   }
        };

        handler=new Handler();
        handler.postDelayed(runnable, SPLASH_SCREEN_TIME_OUT);

    }
}
