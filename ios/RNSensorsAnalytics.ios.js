/**
 * @providesModule RNSensorsAnalytics
 * @flow
 */
'use strict';

var NativeRNSensorsAnalytics = require('NativeModules').RNSensorsAnalytics;

/**
 * High-level docs for the RNSensorsAnalytics iOS API can be written here.
 */

var RNSensorsAnalytics = {
  test: function() {
    NativeRNSensorsAnalytics.test();
  }
};

module.exports = RNSensorsAnalytics;
