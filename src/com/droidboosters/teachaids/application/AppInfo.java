package com.droidboosters.teachaids.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * AppInfo is a singleton to cache application level information
 */
public class AppInfo {

    public static final String apiVersion = "1.0";
    public static final String appType = "android";

    public final String versionName;
    public final int versionCode;
    public final String versionCodeString;
    public final String packageName;
    public final String deviceID;
    public final String deviceModel;
    public final Map<String, String> deviceInfo;
    public final String userAgent;

    private AppInfo() {

        Context application = TeachAIDSApplication.getContext();

        // initialize package and version info
        packageName = application.getPackageName();
        PackageInfo pInfo = null;
        try {
            //noinspection ConstantConditions
            pInfo = application.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pInfo != null && pInfo.versionName != null ? pInfo.versionName : "";
        versionCode = pInfo != null ? pInfo.versionCode : 0;
        versionCodeString = Integer.toString(versionCode);

        // device id
        String androidId = null;
        try {
            androidId = Settings.Secure.getString(TeachAIDSApplication.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e){
            try {
                androidId = Settings.Secure.getString(TeachAIDSApplication.getContext().getContentResolver(),
                        Settings.System.ANDROID_ID);
            } catch (Exception e1){}
        }
        deviceID = androidId != null ? "and1:" + androidId : "";

        // device type
        String deviceBrandAndModel = "";
        try {
            String brand = Build.BRAND != null ? Build.BRAND : "";
            String model = Build.MODEL != null ? Build.MODEL : "";
            deviceBrandAndModel = String.format("%s/%s", brand, model);
        }
        catch (Exception e){}
        deviceModel = deviceBrandAndModel;

        deviceInfo = new HashMap<String, String>();
        deviceInfo.put("device_id", deviceID);
        deviceInfo.put("device_model", deviceModel);

        String systemUserAgent = "";
        try {
            systemUserAgent = System.getProperty("http.agent");
        }
        catch (Exception e){

        }

        userAgent = String.format("%s YourDealsAndroidApp/%s (%s)", systemUserAgent, versionName, versionCodeString);

    }

    private static class SingletonHolder {

        public static final AppInfo INSTANCE = new AppInfo();
    }

    public static AppInfo getInstance() {

        return SingletonHolder.INSTANCE;
    }

}
