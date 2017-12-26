//
//  SAClassDescription.h
//  SensorsAnalyticsSDK
//
//  Created by 雨晗 on 1/18/16.
//  Copyright (c) 2016年 SensorsData. All rights reserved.
//
/// Copyright (c) 2014 Mixpanel. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SATypeDescription.h"

@interface SAClassDescription : SATypeDescription

@property (nonatomic, readonly) SAClassDescription *superclassDescription;
@property (nonatomic, readonly) NSArray *propertyDescriptions;
@property (nonatomic, readonly) NSArray *delegateInfos;

- (instancetype)initWithSuperclassDescription:(SAClassDescription *)superclassDescription dictionary:(NSDictionary *)dictionary;

- (BOOL)isDescriptionForKindOfClass:(Class)class;

@end

@interface SADelegateInfo : NSObject

@property (nonatomic, readonly) NSString *selectorName;

- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

@end
