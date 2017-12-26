# react-native-sa

神策 React Native SDK

* iOS 参考 [集成 Sensors Analytics SDK](https://www.sensorsdata.cn/manual/ios_sdk.html)
* Android 参考 [Android SDK 使用说明](https://www.sensorsdata.cn/manual/android_sdk.html)

## 安装

```sh
npm i react-native-sa --save
```

iOS

`Podfile` 中添加 `pod 'SensorsAnalyticsSDK'`
再 `pod install`

Android

参考 [Android SDK 使用说明](https://www.sensorsdata.cn/manual/android_sdk.html) 配置 Project 级别的 build.gradle 和主 module 的 build.gradle

## 方法

参考 [index.js 的注释](https://github.com/shimohq/react-native-sa/blob/master/index.js)

`init(properties)` SDK 初始化

* serverUrl: 数据接收地址
* configureURL: 配置分发地址
* debugMode: 调试模式。 0: off, 1: debug, 2: debug and track
* networkTypes: 同步数据时的网络策略。 ['2G', '3G', '4G', 'WIFI', 'ALL']

`login(loginId)` 登录

`logout()` 注销

`set(profile)` 设置用户属性

`setOnce(profile)` 记录初次设定的属性

`enableAutoTrack(eventTypeList)` 开启自动追踪

* eventTypeList 自动采集的类型, eg: ['AppStart', 'AppEnd', 'AppClick', 'AppViewScreen']

`enableReactNativeAutoTrack()` 开启自动追踪,支持 React Native

只支持 Android。iOS 把 Podfile 改成 `pod 'SensorsAnalyticsSDK', :subspecs => ['ENABLE_REACT_NATIVE_APPCLICK']`

`track(event, properties)` 追踪事件

`trackBegin(event)` 事件开始

`trackEnd(event, properties)` 事件结束

`trackInstallation(event, properties = null)` 渠道追踪


## 用例

```js
import SensorsAnalytics from 'react-native-sa';

// 初始化
SensorsAnalytics.init({
  serverUrl: Config.serverUrl,
  configureURL: Config.configureURL,
  debugMode: 0,
  networkTypes: ['WIFI']
});

// 登录
SensorsAnalytics.login(me.id.toString());

// 设置用户数据
SensorsAnalytics.set({
  email: me.email,
  mobile: me.mobile,
  name: me.name
});

// 追踪事件
SensorsAnalytics.track('test', { type: 'doc', status: '2' });
```
