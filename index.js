/**
 * @providesModule RNSensorsAnalyticsModule
 * @flow
 */

import { NativeModules, Platform } from 'react-native';

const RNSensorsAnalytics = NativeModules.RNSensorsAnalytics;

/**
 * 初始化 SDK
 *
 * @param properties 初始化参数
 * serverUrl: 数据接收地址
 * configureURL: 配置分发地址
 * debugMode: 调试模式。 0: off, 1: debug, 2: debug and track
 * networkTypes: 同步数据时的网络策略。 ['2G', '3G', '4G', 'WIFI', 'ALL']
 */
function init(properties) {
  RNSensorsAnalytics && RNSensorsAnalytics.init && RNSensorsAnalytics.init(properties);
}

/**
 * 登录
 *
 * @param loginId
 */
function login(loginId) {
  RNSensorsAnalytics && RNSensorsAnalytics.login && RNSensorsAnalytics.login(loginId);
}

/**
 * 注销
 */
function logout() {
  RNSensorsAnalytics && RNSensorsAnalytics.logout && RNSensorsAnalytics.logout();
}

/**
 * 设置用户属性
 *
 * @param profile 用户属性
 * Sex
 * Age
 */
function set(profile) {
  RNSensorsAnalytics && RNSensorsAnalytics.set && RNSensorsAnalytics.set(profile);
}

/**
 * 记录初次设定的属性
 *
 * @param profile
 */
function setOnce(profile) {
  RNSensorsAnalytics && RNSensorsAnalytics.setOnce && RNSensorsAnalytics.setOnce(profile);
}

/**
 * 开启自动追踪
 *
 * @param eventTypeList 自动采集的类型, eg: ['AppStart', 'AppEnd', 'AppClick', 'AppViewScreen']
 */
function enableAutoTrack(eventTypeList) {
  RNSensorsAnalytics && RNSensorsAnalytics.enableAutoTrack && RNSensorsAnalytics.enableAutoTrack(eventTypeList);
}

/**
 * 开启自动追踪,支持 React Native
 *
 * 只支持 Android，iOS 把 Podfile 改成 `pod 'SensorsAnalyticsSDK', :subspecs => ['ENABLE_REACT_NATIVE_APPCLICK']`
 */
function enableReactNativeAutoTrack() {
  RNSensorsAnalytics && RNSensorsAnalytics.enableReactNativeAutoTrack && RNSensorsAnalytics.enableReactNativeAutoTrack();
}

/**
 * 追踪事件
 *
 * @param event
 * @param properties
 */
function track(event, properties) {
  RNSensorsAnalytics && RNSensorsAnalytics.track && RNSensorsAnalytics.track(event, properties);
}

/**
 * 事件开始
 *
 * @param event
 */
function trackBegin(event) {
  RNSensorsAnalytics && RNSensorsAnalytics.trackBegin && RNSensorsAnalytics.trackBegin(event);
}

/**
 * 事件结束
 *
 * @param event
 * @param properties
 */
function trackEnd(event, properties) {
  RNSensorsAnalytics && RNSensorsAnalytics.trackEnd && RNSensorsAnalytics.trackEnd(event, properties);
}

/**
 * 渠道追踪
 *
 * @param event
 * @param properties
 */
function trackInstallation(event, properties = null) {
  RNSensorsAnalytics && RNSensorsAnalytics.trackInstallation && RNSensorsAnalytics.trackInstallation(event, properties);
}

export default {
  init,
  login,
  logout,
  set,
  setOnce,
  enableAutoTrack,
  enableReactNativeAutoTrack,
  track,
  trackBegin,
  trackEnd,
  trackInstallation
};
