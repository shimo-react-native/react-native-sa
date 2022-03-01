#import "RNSensorsAnalytics.h"
#import "SensorsAnalyticsSDK.h"

#import <React/RCTUtils.h>

@implementation RNSensorsAnalytics

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(init:(NSDictionary *)properties
                  : (RCTPromiseResolveBlock)resolve
                      rejecter
                  : (RCTPromiseRejectBlock)reject) {
    
    RCTExecuteOnMainQueue(^{
        NSString *serverUrl = [properties objectForKey:@"serverUrl"];
        NSDictionary *andLaunchOptions = [properties objectForKey:@"andLaunchOptions"];
        NSNumber *debugMode = [properties objectForKey:@"debugMode"];
        
        SensorsAnalyticsSDK *instance = [SensorsAnalyticsSDK sharedInstanceWithServerURL:serverUrl andLaunchOptions:andLaunchOptions andDebugMode:(SensorsAnalyticsDebugMode)[debugMode integerValue]];
        
        // networkType
        NSArray *networkTypes = [properties objectForKey:@"networkTypes"];
        if ([networkTypes isKindOfClass:[NSArray class]]) {
            __block SensorsAnalyticsNetworkType aNetworkTypes = SensorsAnalyticsNetworkTypeNONE;
            [networkTypes enumerateObjectsUsingBlock:^(NSString *networkType, NSUInteger idx, BOOL * _Nonnull stop) {
                if ([networkType isEqualToString:@"2G"]) {
                    aNetworkTypes |= SensorsAnalyticsNetworkType2G;
                } else if ([networkType isEqualToString:@"3G"]) {
                    aNetworkTypes |= SensorsAnalyticsNetworkType3G;
                } else if ([networkType isEqualToString:@"4G"]) {
                    aNetworkTypes |= SensorsAnalyticsNetworkType4G;
                } else if ([networkType isEqualToString:@"WIFI"]) {
                    aNetworkTypes |= SensorsAnalyticsNetworkTypeWIFI;
                } else if ([networkType isEqualToString:@"ALL"]) {
                    aNetworkTypes = SensorsAnalyticsNetworkTypeALL;
                    *stop = YES;
                }
            }];
            [instance setFlushNetworkPolicy:aNetworkTypes];
        }
        
        // superProperties
        NSDictionary *superProperties = [properties objectForKey:@"superProperties"];
        if ([superProperties isKindOfClass:[NSDictionary class]]) {
            [instance registerSuperProperties:superProperties];
        }
        
        resolve(nil);
    });
}
                  
#pragma mark - user

RCT_EXPORT_METHOD(login:(NSString *)loginId) {
    [[SensorsAnalyticsSDK sharedInstance] login:loginId];
}

RCT_EXPORT_METHOD(logout) {
    [[SensorsAnalyticsSDK sharedInstance] logout];
}

RCT_EXPORT_METHOD(set:(NSDictionary *)profile) {
    [[SensorsAnalyticsSDK sharedInstance] set:profile];
}

RCT_EXPORT_METHOD(setOnce:(NSDictionary *)profile) {
    [[SensorsAnalyticsSDK sharedInstance] setOnce:profile];
}

RCT_EXPORT_METHOD(getDistinctId
                  : (RCTPromiseResolveBlock)resolve
                      rejecter
                  : (RCTPromiseRejectBlock)reject) {
    resolve([SensorsAnalyticsSDK sharedInstance].distinctId);
}

#pragma mark - track

RCT_EXPORT_METHOD(enableAutoTrack:(NSArray *)eventTypeList) {
    [eventTypeList enumerateObjectsUsingBlock:^(NSString *eventType, NSUInteger idx, BOOL *_Nonnull stop) {
        if ([eventType isEqualToString:@"AppStart"]) {
            [[SensorsAnalyticsSDK sharedInstance] enableAutoTrack:SensorsAnalyticsEventTypeAppStart];
        } else if ([eventType isEqualToString:@"AppEnd"]) {
            [[SensorsAnalyticsSDK sharedInstance] enableAutoTrack:SensorsAnalyticsEventTypeAppEnd];
        } else if ([eventType isEqualToString:@"AppClick"]) {
            [[SensorsAnalyticsSDK sharedInstance] enableAutoTrack:SensorsAnalyticsEventTypeAppClick];
        } else if ([eventType isEqualToString:@"AppViewScreen"]) {
            [[SensorsAnalyticsSDK sharedInstance] enableAutoTrack:SensorsAnalyticsEventTypeAppViewScreen];
        }
    }];
}

RCT_EXPORT_METHOD(registerSuperProperties:(NSDictionary *)properties) {
    [[SensorsAnalyticsSDK sharedInstance] registerSuperProperties:properties];
}

RCT_EXPORT_METHOD(unregisterSuperProperty:(NSString *)property) {
    [[SensorsAnalyticsSDK sharedInstance] unregisterSuperProperty:property];
}

RCT_EXPORT_METHOD(track:(NSString *)event properties:(NSDictionary *)properties) {
    [[SensorsAnalyticsSDK sharedInstance] track:event withProperties:properties];
}

RCT_EXPORT_METHOD(trackBegin:(NSString *)event) {
    [[SensorsAnalyticsSDK sharedInstance] trackTimer:event
                                        withTimeUnit:SensorsAnalyticsTimeUnitMilliseconds];
}

RCT_EXPORT_METHOD(trackEnd:(NSString *)event properties:(NSDictionary *)properties) {
    [[SensorsAnalyticsSDK sharedInstance] trackTimerEnd:event withProperties:properties];
}

RCT_EXPORT_METHOD(trackInstallation:(NSString *)event properties:(NSDictionary *)properties) {
    if (properties) {
        [[SensorsAnalyticsSDK sharedInstance] trackInstallation:event withProperties:properties];
    } else {
        [[SensorsAnalyticsSDK sharedInstance] trackInstallation:event];
    }
}

@end
