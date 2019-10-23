//
//  LXSCheckNullObj.h
//  LXSBases
//
//  Created by Luke on 2019/7/16.
//  Copyright © 2019 Luke. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface LXSCheckNullObj : NSObject
//判空处理--->>返回非nil字符串
+ (NSString *)notNullString:(NSString *)string;

+ (NSNumber *)notNullNumber:(NSNumber *)number;

+ (NSArray *)notNullArray:(NSArray *)array;

+ (id)notNullObject:(id)object withDefaultObject:(id)defObject;

@end

NS_ASSUME_NONNULL_END
