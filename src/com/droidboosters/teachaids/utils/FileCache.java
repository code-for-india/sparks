package com.droidboosters.teachaids.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

public class FileCache {
	private File cacheDir;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Celebrity");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

//	public File getFile(final Context context, final String url)
//			throws FileNotFoundException {
//		// retrieve filename/location from shared preferences based on the the
//		// url
//		final SharedPreferences settings = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		String filename = settings.getString(url, null);
//
//		if (url == null) {
//			throw new FileNotFoundException();
//		}
//
//		if (filename == null)
//			return null;
//
//		final File f = new File(this.cacheDir, filename);
//		return f;
//	}
//
//	public void downloadAndCache(final Context context, final String url,
//			Bitmap bitmap) {
//		// TODO: download the file and save to the filesystem
//		// TODO: generate a the filename and push into saved preferences
//
//		String filename = "";
//		try {
//			filename = new File(new URI(url).getPath()).getName();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		File image = new File(this.cacheDir, filename);
//
//		// Encode the file as a PNG image.
//		FileOutputStream outStream;
//		try {
//
//			outStream = new FileOutputStream(image);
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//			/* 100 to keep full quality of the image */
//
//			outStream.flush();
//			outStream.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// save file into the share preferences so we can get it back late
//		saveFileToMap(context, url, filename);
//	}
//
//	private void saveFileToMap(final Context context, final String url,
//			final String filename) {
//
//		final SharedPreferences settings = PreferenceManager
//				.getDefaultSharedPreferences(context);
//
//		// save the pair into shared preferences
//		final Editor editor = settings.edit();
//		editor.putString(url, filename);
//		editor.commit();
//	}

}
