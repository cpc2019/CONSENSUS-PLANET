//
//  LXSAPPBundle.m
//  LXSApp
//
//  Created by qql on 2019/7/24.
//  Copyright © 2019 Luke. All rights reserved.
//

#import "LXSAPPBundle.h"


@implementation LXSAPPBundle
+(NSBundle *)current{
    return [NSBundle bundleForClass:[LXSAPPBundle class]];
}

+(UIView *)loadNibWithNibName:(NSString *)nibName{
    return [[[LXSAPPBundle current] loadNibNamed:nibName owner:nil options:nil] firstObject];
}

+(UIImage *)imageWithImageName:(NSString *)imageName{
    return [UIImage imageNamed:imageName inBundle:[LXSAPPBundle current] compatibleWithTraitCollection:nil];
}
@end
