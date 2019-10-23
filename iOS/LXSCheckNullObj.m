//
//  LXSCheckNullObj.m
//  LXSBases
//
//  Created by Luke on 2019/7/16.
//  Copyright © 2019 Luke. All rights reserved.
//

#import "LXSCheckNullObj.h"

@implementation LXSCheckNullObj

+ (NSString *)notNullString:(NSString *)string
{
    return [self notNullObject:string withDefaultObject:@""];
}

+ (NSNumber *)notNullNumber:(NSNumber *)number
{
    return [self notNullObject:number withDefaultObject:@0];
}

+ (NSArray *)notNullArray:(NSArray *)array
{
    return [self notNullObject:array withDefaultObject:[NSArray array]];
}

+ (id)notNullObject:(id)object withDefaultObject:(id)defObject
{
    if ([object isKindOfClass:[NSNull class]]) {
        return defObject;
    }
    
    return object ? object : defObject;
}

@end
