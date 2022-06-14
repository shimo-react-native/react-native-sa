package im.shimo.sa;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI.DebugMode;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI.NetworkType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bell on 2017/12/25.
 */

public class SensorsAnalyticsModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private JSONObject mJsObject;
    private String mJsEvent;

    public SensorsAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNSensorsAnalytics";
    }

    @ReactMethod
    public void init(ReadableMap properties, final Promise promise) {
        try {
            initSensorsAnalytics(properties);
            promise.resolve(null);
        } catch (Throwable throwable) {
            promise.reject(throwable);
        }
    }

    public void initSensorsAnalytics(ReadableMap properties) {
        Context applicationContext = getReactApplicationContext().getApplicationContext();

        // serverUrl & debugMode
        String serverUrl = properties.getString("serverUrl");
        int debugMode = properties.getInt("debugMode");
        DebugMode mode = DebugMode.DEBUG_OFF;
        if (debugMode == 1) {
            mode = DebugMode.DEBUG_ONLY;
        } else if (debugMode == 2) {
            mode = DebugMode.DEBUG_AND_TRACK;
        }
        final SensorsDataAPI instance = SensorsDataAPI.sharedInstance(applicationContext, serverUrl, mode);
        // 修复 debug 模式 404 报错
        try {
            Field field = instance.getClass().getDeclaredField("mServerUrl");
            field.setAccessible(true);
            field.set(instance, serverUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // networkType
        ReadableArray networkTypes = properties.getArray("networkTypes");
        if (networkTypes != null) {
            int aNetworkTypes = 0;
            for (int i = 0; i < networkTypes.size(); i++) {
                String networkType = networkTypes.getString(i);
                if (networkType.equals("ALL")) {
                    aNetworkTypes = NetworkType.TYPE_ALL;
                    break;
                }
                switch (networkType) {
                    case "2G":
                        aNetworkTypes |= NetworkType.TYPE_2G;
                        break;
                    case "3G":
                        aNetworkTypes |= NetworkType.TYPE_3G;
                        break;
                    case "4G":
                        aNetworkTypes |= NetworkType.TYPE_4G;
                        break;
                    case "WIFI":
                        aNetworkTypes |= NetworkType.TYPE_WIFI;
                        break;
                }
            }
            instance.setFlushNetworkPolicy(aNetworkTypes);
        }

        // superProperties
        ReadableMap superProperties = properties.getMap("superProperties");
        if (superProperties != null) {
            instance.registerSuperProperties(new JSONObject(superProperties.toHashMap()));
        }

        // autoTrackList
        ReadableArray autoTrackList = properties.getArray("autoTrackList");
        if (autoTrackList != null) {
            List<SensorsDataAPI.AutoTrackEventType> list = new ArrayList<>();
            for (Object autoTrack : autoTrackList.toArrayList()) {
                if (autoTrack instanceof String) {
                    list.add(SensorsDataAPI.AutoTrackEventType.autoTrackEventTypeFromEventName((String) autoTrack));
                }
            }
            instance.enableAutoTrack(list);
        }
    }

    @ReactMethod
    public void login(String loginId) {
        SensorsDataAPI.sharedInstance(mContext).login(loginId);
    }

    @ReactMethod
    public void logout() {
        SensorsDataAPI.sharedInstance(mContext).logout();
    }

    @ReactMethod
    public void set(ReadableMap properties) {
        try {
            SensorsDataAPI.sharedInstance(mContext).profileSet(convertMapToJson(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void setOnce(ReadableMap properties) {
        try {
            SensorsDataAPI.sharedInstance(mContext).profileSetOnce(convertMapToJson(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void getDistinctId(final Promise promise) {
        promise.resolve(SensorsDataAPI.sharedInstance(mContext).getAnonymousId());
    }

    @ReactMethod
    public void enableAutoTrack(ReadableArray eventTypeList) {
        List<AutoTrackEventType> list = new ArrayList<>();

        for (int i = 0; i < eventTypeList.size(); i++) {
            String eventType = eventTypeList.getString(i);
            switch (eventType) {
                case "AppStart":
                    list.add(AutoTrackEventType.APP_START);
                    break;
                case "AppEnd":
                    list.add(AutoTrackEventType.APP_END);
                    break;
                case "AppClick":
                    list.add(AutoTrackEventType.APP_CLICK);
                    break;
                case "AppViewScreen":
                    list.add(AutoTrackEventType.APP_VIEW_SCREEN);
                    break;
            }
        }
        SensorsDataAPI.sharedInstance(mContext).enableAutoTrack(list);
    }

    @ReactMethod
    public void enableReactNativeAutoTrack() {
        SensorsDataAPI.sharedInstance(mContext).enableReactNativeAutoTrack();
    }

    @ReactMethod
    public void registerSuperProperties(ReadableMap properties) {
        try {
            SensorsDataAPI.sharedInstance(mContext).registerSuperProperties(convertMapToJson(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unregisterSuperProperty(String property) {
        SensorsDataAPI.sharedInstance(mContext).unregisterSuperProperty(property);
    }

    @ReactMethod
    public void track(String event, ReadableMap properties) {
        try {
            SensorsDataAPI.sharedInstance(mContext).track(event, convertMapToJson(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void trackBegin(String event) {
        SensorsDataAPI.sharedInstance(mContext).trackTimerBegin(event);
    }

    @ReactMethod
    public void trackEnd(String event, ReadableMap properties) {
        try {
            SensorsDataAPI.sharedInstance(mContext).trackTimerEnd(event, convertMapToJson(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void trackInstallation(String event, ReadableMap properties) {
        try {
            JSONObject jsonObject = convertMapToJson(properties);
            SensorsDataAPI sensorsDataAPI = SensorsDataAPI.sharedInstance(mContext);
            if (sensorsDataAPI != null) {
                if (jsonObject == null) {
                    sensorsDataAPI.trackInstallation(event);
                } else {
                    sensorsDataAPI.trackInstallation(event, jsonObject);
                }
            } else {
                Log.v("SensorsAnalyticsModule", "wait SensorsDataAPI init");
                mJsObject = jsonObject;
                mJsEvent = event;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void trackInstallation() {
        if (mJsEvent == null) return;
        if (mJsObject == null) {
            SensorsDataAPI.sharedInstance(mContext).trackInstallation(mJsEvent);
        } else {
            SensorsDataAPI.sharedInstance(mContext).trackInstallation(mJsEvent, mJsObject);
        }
        mJsObject = null;
        mJsEvent = null;
    }

    public static JSONObject convertMapToJson(ReadableMap readableMap) throws JSONException {
        if (readableMap == null) {
            return null;
        }
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertArrayToJson(readableMap.getArray(key)));
                    break;
                default:
                    break;
            }
        }
        return object;
    }

    public static JSONArray convertArrayToJson(ReadableArray readableArray) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
                case Boolean:
                    array.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.put(readableArray.getDouble(i));
                    break;
                case String:
                    array.put(readableArray.getString(i));
                    break;
                case Map:
                    array.put(convertMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertArrayToJson(readableArray.getArray(i)));
                    break;
                default:
                    break;
            }
        }
        return array;
    }
}
