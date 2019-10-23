//
//  UIFont+Ex.m
//  LLWS
//
//  Created by mac on 2019/3/6.
//  Copyright © 2019 766tech. All rights reserved.
//

#import "UIFont+Ex.h"

@implementation UIFont (Ex)

+ (UIFont *)pingFangSCWithWeight:(FontWeightStyle)fontWeight size:(CGFloat)fontSize{
    if (fontWeight < FontWeightStyleMedium || fontWeight > FontWeightStyleThin) {
        fontWeight = FontWeightStyleRegular;
    }
    
    NSString *fontName = @"PingFangSC-Regular";
    switch (fontWeight) {
        case FontWeightStyleMedium:
            fontName = @"PingFangSC-Medium";
            break;
        case FontWeightStyleSemibold:
            fontName = @"PingFangSC-Semibold";
            break;
        case FontWeightStyleLight:
            fontName = @"PingFangSC-Light";
            break;
        case FontWeightStyleUltralight:
            fontName = @"PingFangSC-Ultralight";
            break;
        case FontWeightStyleRegular:
            fontName = @"PingFangSC-Regular";
            break;
        case FontWeightStyleThin:
            fontName = @"PingFangSC-Thin";
            break;
    }
    
    UIFont *font = [UIFont fontWithName:fontName size:fontSize];
    
    return font ?: [UIFont systemFontOfSize:fontSize];
}


@end
