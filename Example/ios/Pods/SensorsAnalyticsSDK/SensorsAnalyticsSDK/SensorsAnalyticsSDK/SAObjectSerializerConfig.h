//
//  SAObjectSerializerConfig.h
//  SensorsAnalyticsSDK
//
//  Created by 雨晗 on 1/18/16.
//  Copyright (c) 2016年 SensorsData. All rights reserved.
//
/// Copyright (c) 2014 Mixpanel. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SAEnumDescription;
@class SAClassDescription;
@class SATypeDescription;

@interface SAObjectSerializerConfig : NSObject

@property (nonatomic, readonly) NSArray *classDescriptions;
@property (nonatomic, readonly) NSArray *enumDescriptions;

- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

- (SATypeDescription *)typeWithName:(NSString *)name;
- (SAEnumDescription *)enumWithName:(NSString *)name;
- (SAClassDescription *)classWithName:(NSString *)name;

@end
