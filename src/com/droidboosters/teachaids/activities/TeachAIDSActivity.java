package com.droidboosters.teachaids.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droidboosters.teachaids.R;
import com.droidboosters.teachaids.activities.interfaces.FragmentToActivity;
import com.droidboosters.teachaids.adapters.DrawerListAdapter;
import com.droidboosters.teachaids.application.AppConstants;
import com.droidboosters.teachaids.controllers.SharedPreferencesController;
import com.droidboosters.teachaids.customviews.RoundedImageView;
import com.droidboosters.teachaids.customviews.TypefaceSpan;
import com.droidboosters.teachaids.fragments.QuizFragment;
import com.droidboosters.teachaids.fragments.IntroductionFragment;
import com.droidboosters.teachaids.fragments.VideosFragment;
import com.droidboosters.teachaids.fragments.AudiosFragment;
import com.droidboosters.teachaids.fragments.HomeFragment;
import com.droidboosters.teachaids.fragments.FactsFragment;
import com.droidboosters.teachaids.fragments.PreventionFragment;
import com.droidboosters.teachaids.models.NavDrawerItem;
import com.droidboosters.teachaids.models.VideosModel;
import com.droidboosters.teachaids.utils.PullDataTask;
import com.droidboosters.teachaids.utils.PullDataTask.DownloadTaskListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Code is written as part of Code for India Hackathon at Google, Bangalore
 * Dated 10/05/2014
 * This app is aimed at helping TeachAIDS NGO 
 * @author Karthi
 *
 */


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TeachAIDSActivity extends FragmentActivity implements
		FragmentToActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private PullDataTask getPhotosTask;
	public String currentLanguage = "en";

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// Language settings
	Typeface hindiFont, englishFont, tamilFont;
	public static SharedPreferences sharedPreferences = null;
	public static SharedPreferences.Editor editor = null;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	Gson gson = new Gson();
	private ArrayList<NavDrawerItem> navDrawerItems;
	private DrawerListAdapter adapter;
	private JSONArray jsonarray = null;
	private JSONObject jsonobject = null;
	/**
	 * This class describes all device configuration information that can impact
	 * the resources the application retrieves. This includes both
	 * user-specified configuration options (locale and scaling) as well as
	 * device configurations (such as input modes, screen size and screen
	 * orientation).
	 */
	Configuration config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_deals);
		// setUpActionBar();

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		initSharedPreferences();
		setUpNavDrawerItems();

		// initialize font typeface
		hindiFont = Typeface.createFromAsset(getAssets(), "fonts/mangal.ttf");
		callTasks();
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(1);
			changeLang(1);
		}
	}

	private void setUpNavDrawerItems() {
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.home), navMenuIcons.getResourceId(0, -1)));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.introduction), navMenuIcons.getResourceId(1, -1)));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.facts), navMenuIcons.getResourceId(2, -1)));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.quiz), navMenuIcons.getResourceId(3, -1), true, "22"));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.videos), navMenuIcons.getResourceId(4, -1)));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.audios), navMenuIcons.getResourceId(5, -1), true,
				"50+"));

		navDrawerItems.add(new NavDrawerItem(getResources().getString(
				R.string.prevention), navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new DrawerListAdapter(getApplicationContext(),
				navDrawerItems, "hi");
		setUpHeaderView();
		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for //
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		setTitle(getResources().getString(
				R.string.home));
	}

	private void setUpHeaderView() {
		try {
			mDrawerList.removeAllViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		View header = inflater
				.inflate(R.layout.drawer_list_header, null, false);
		RoundedImageView iconImage = (RoundedImageView) header
				.findViewById(R.id.userIcon);
		iconImage.setImageDrawable(getResources().getDrawable(
				R.drawable.aids_rounded));
		SharedPreferencesController sharedPreferencesController = SharedPreferencesController
				.getSharedPreferencesController(this);
		TextView userName = (TextView) header.findViewById(R.id.userName);
		userName.setText(sharedPreferencesController.getSharedPreferences()
				.getString("FB_FIRST_NAME", "")
				+ " "
				+ sharedPreferencesController.getSharedPreferences().getString(
						"FB_LAST_NAME", ""));
		mDrawerList.addHeaderView(header);
	}

	@Override
	public void callFragment(int fragmentId) {
		switch (fragmentId) {
		case 0:
			displayView(1);
			break;
		case 1:
			displayView(6);
			break;
		case 2:
			displayView(5);
			break;
		case 3:
			displayView(4);
			break;
		case 4:
			displayView(3);
			break;
		case 5:
			displayView(2);
			break;
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teach_aid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_english:
			changeLang(0);
			return true;
		case R.id.action_hindi:
			changeLang(1);
			return true;
		case R.id.action_tamil:
			changeLang(2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void changeLang(int lang) {
		if (lang == 0) {
			/**
			 * "en" is the localization code for our English language.
			 */
			currentLanguage = "en";
			displayView(1);
			Locale locale = new Locale(currentLanguage);
			Locale.setDefault(locale);
			config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,
					getBaseContext().getResources().getDisplayMetrics());

			adapter.notifyDataSetChanged();

			// setting the nav drawer list adapter
			adapter = new DrawerListAdapter(getApplicationContext(),
					navDrawerItems, currentLanguage);
			// setUpHeaderView();
			mDrawerList.setAdapter(adapter);

			invalidateOptionsMenu();
			setTitle(mTitle);
		}

		if (lang == 1) {

			/**
			 * "hi" is the localization code for our Hindi language.
			 */
			currentLanguage = "hi";
			displayView(1);
			Locale locale = new Locale(currentLanguage);
			Locale.setDefault(locale);

			config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,
					getBaseContext().getResources().getDisplayMetrics());

			adapter.notifyDataSetChanged();

			// setting the nav drawer list adapter
			adapter = new DrawerListAdapter(getApplicationContext(),
					navDrawerItems, currentLanguage);
			// setUpHeaderView();
			mDrawerList.setAdapter(adapter);
			invalidateOptionsMenu();
			setTitle(mTitle);
		}

		if (lang == 2) {

			/**
			 * "ta" is the localization code for our Tamil language.
			 */
			currentLanguage = "ta";
			displayView(1);
			Locale locale = new Locale(currentLanguage);
			Locale.setDefault(locale);

			config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,
					getBaseContext().getResources().getDisplayMetrics());

			adapter.notifyDataSetChanged();

			// setting the nav drawer list adapter
			adapter = new DrawerListAdapter(getApplicationContext(),
					navDrawerItems, currentLanguage);
			// setUpHeaderView();
			mDrawerList.setAdapter(adapter);
			invalidateOptionsMenu();
			setTitle(mTitle);
		}
	}

	// Method to setup custom action bar
	private void setUpActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.actionbar_common, null);
		TextView viewName = (TextView) v.findViewById(R.id.actionbar_text);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		viewName.setTypeface(tf);
		viewName.setText(getString(R.string.app_name));
		actionBar.setCustomView(v);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	public String getLanguage() {
		return currentLanguage;
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 1:
			fragment = new HomeFragment();
			setTitle(getResources().getString(R.string.home));
			break;
		case 2:
			fragment = new IntroductionFragment();
			setTitle(getResources().getString(R.string.introduction));
			break;
		case 3:
			fragment = new FactsFragment();
			setTitle(getResources().getString(R.string.facts));
			break;
		case 4:
			fragment = new QuizFragment();
			setTitle(getResources().getString(R.string.quiz));
			break;
		case 5:
			fragment = new VideosFragment();
			setTitle(getResources().getString(R.string.videos));
			break;
		case 6:
			fragment = new AudiosFragment();
			setTitle(getResources().getString(R.string.audios));
			break;
		case 7:
			fragment = new PreventionFragment();
			setTitle(getResources().getString(R.string.prevention));
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			// setTitle(navMenuTitles[position - 1]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = mDrawerTitle = title;
		
		int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		TextView actionBarTextView = (TextView) findViewById(titleId);
		if (currentLanguage.equals("ta")) {
			Typeface tf = Typeface.createFromAsset(getAssets(),
					"fonts/Bamini.ttf");
			actionBarTextView.setTypeface(tf);
			// actionBarTextView.setText(mTitle);
		} else {
			if (currentLanguage.equals("hi")) {
				Typeface tf = Typeface.createFromAsset(getAssets(),
						"fonts/mangal.ttf");
				actionBarTextView.setTypeface(tf);
				// actionBarTextView.setText(mTitle);
			}
		}
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	void initSharedPreferences() {
		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		editor = sharedPreferences.edit();
	}
	
	/**
	 * Method to call the async tasks to pull data from server
	 */
	public void callTasks() {

	

		String photourljson = sharedPreferences.getString("photourldata",
				"hell");
		if (photourljson.equals("hell")) {
			getPhotosTask = new PullDataTask();

			getPhotosTask.execute(getResources()
					.getString(R.string.domain_name)
					+ getResources().getString(R.string.api_path)
					+ "?name="
					+ getResources().getString(R.string.pass_name));

			getPhotosTask.setDownloadListener(new DownloadTaskListener() {

				@Override
				public void setDownloadedData(JSONObject data) {
					try {
						AppConstants.imageThumbUrls.clear();
						AppConstants.imageUrls.clear();
						AppConstants.videosList.clear();
						AppConstants.trailervideosList.clear();


						jsonarray = data.getJSONArray("videos");
						for (int i = 0; i < jsonarray.length(); i++) {
							jsonobject = jsonarray.getJSONObject(i);
							System.out.println("video url "+jsonobject.getString("video_title"));
							AppConstants.videosList.add(new VideosModel(
									jsonobject.getString("video_id"),
									jsonobject.getString("video_url"),
									jsonobject.getString("video_thumb_url"),
									jsonobject.getString("video_title")));

						}

						String jsonVideoList = gson
								.toJson(AppConstants.videosList);
						ArrayList<String> list = AppConstants.imageUrls;
						ArrayList<String> list1 = AppConstants.imageThumbUrls;

						JSONArray a = new JSONArray();
						JSONArray a1 = new JSONArray();

						for (int i = 0; i < list.size(); i++) {
							a.put(list.get(i));
							a1.put(list1.get(i));
						}
						if (!list.isEmpty()) {
							editor.putString("photourldata", a.toString());
							editor.putString("photothumburldata", a1.toString());
							editor.putString("photovideodata", jsonVideoList);
						} else {
							editor.putString("photourldata", null);
							editor.putString("photothumburldata", null);
							editor.putString("photovideodata", null);
						}
						editor.commit();

					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"Issue in loading profile data!",
								Toast.LENGTH_SHORT).show();

						e.printStackTrace();
					}
				}

			});
		} else {
			AppConstants.imageThumbUrls.clear();
			AppConstants.imageUrls.clear();
			AppConstants.videosList.clear();
			AppConstants.trailervideosList.clear();

			ArrayList<String> urls = new ArrayList<String>();
			ArrayList<String> urls1 = new ArrayList<String>();
			if (!photourljson.equals("celebrity")) {
				try {
					JSONArray a = new JSONArray(photourljson);

					for (int i = 0; i < a.length(); i++) {
						String url = a.optString(i);
						urls.add(url);
						AppConstants.imageUrls.add(i, url);
					}
					// AppConstants.imageUrls = urls.toArray(new
					// String[urls.size()]);

					String photourlthumbjson = sharedPreferences.getString(
							"photothumburldata", "celebrity");

					if (!photourlthumbjson.equals("celebrity")) {
						JSONArray b = new JSONArray(photourlthumbjson);
						for (int i = 0; i < b.length(); i++) {
							String url = b.optString(i);
							urls1.add(url);
							AppConstants.imageThumbUrls.add(i, url);
						}
						// AppConstants.imageThumbUrls = urls1.toArray(new
						// String[urls1.size()]);
					}
					// load video data
					String videourljson = sharedPreferences.getString(
							"photovideodata", "celebrity");
					if (!videourljson.equals("celebrity")) {
						AppConstants.videosList = gson.fromJson(videourljson,
								new TypeToken<ArrayList<VideosModel>>() {
								}.getType());
					}
					// load trailer video data
					String trailervideourljson = sharedPreferences.getString(
							"phototrailervideodata", "celebrity");
					if (!trailervideourljson.equals("celebrity")) {
						AppConstants.trailervideosList = gson.fromJson(
								trailervideourljson,
								new TypeToken<ArrayList<VideosModel>>() {
								}.getType());
					}
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(),
							"Issue in loading profile data!",
							Toast.LENGTH_SHORT).show();

					e.printStackTrace();
				}
			}
		}
	} // callTasks


}
