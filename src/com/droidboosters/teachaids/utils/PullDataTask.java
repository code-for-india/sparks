package com.droidboosters.teachaids.utils;

import org.json.JSONObject;

import com.droidboosters.teachaids.utils.JSONParser;

import android.os.AsyncTask;



public class PullDataTask extends AsyncTask<String, Void, JSONObject> {

	private String downloadUrl = "";
	private JSONParser jsonParser;
	private JSONObject jsonObject;
	private DownloadTaskListener taskListener;
	
	
	@Override
	protected void onPreExecute()
	{
		jsonParser = new JSONParser();
	}
	
	
	// Custom download listener method
	public void setDownloadListener(DownloadTaskListener listener) {
		taskListener = listener;
	}
	
	@Override
	protected JSONObject doInBackground(String... parameter) {
		downloadUrl = parameter[0];
		jsonObject = jsonParser.getJSONFromUrl(downloadUrl);
		//System.out.println(""+jsonObject.toString());
		return jsonObject;
	}

	
	@Override
	protected void onPostExecute(JSONObject result)
	{
		taskListener.setDownloadedData(result);
	}
	
	public interface DownloadTaskListener
	{
		void setDownloadedData(JSONObject data);
	}
	
	
}
