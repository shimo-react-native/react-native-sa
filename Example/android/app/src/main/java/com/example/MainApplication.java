package com.example;

import android.app.Application;

import com.facebook.react.ReactApplication;
import im.shimo.sa.SensorsAnalyticsPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
            new SensorsAnalyticsPackage()
      );
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initSensorsDataAPI();
  }


  /**
   * Sensors Analytics 采集数据的地址
   */
  private final static String SA_SERVER_URL = "http://test2-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=386e4bed00b5701e";

  /**
   * Sensors Analytics 配置分发的地址
   */
  //private final static String SA_CONFIGURE_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/config/?project=yangzhankun";

  /**
   * Sensors Analytics DEBUG 模式
   * SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
   * SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
   * SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
   * 注意！请不要在正式发布的 App 中使用 Debug 模式！
   */
  private final SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;

  /**
   * 初始化 Sensors Analytics SDK
   */
  private void initSensorsDataAPI() {

    SensorsDataAPI.sharedInstance(
            this,                               // 传入 Context
            SA_SERVER_URL,                      // 数据接收的 URL
            SA_DEBUG_MODE);                     // Debug 模式选项

    //开启自动采集
    List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
    eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
    eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
    eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
    eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
    SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);
    //开启 React Native 控件点击事件的自动采集（$AppClick）
    SensorsDataAPI.sharedInstance().enableReactNativeAutoTrack();
    SensorsDataAPI.sharedInstance().enableLog(true);
  }
}
