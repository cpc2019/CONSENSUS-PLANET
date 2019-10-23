//
//  UIViewController+Swizzling.m
//  LXSApp
//
//  Created by Luke on 2019/7/27.
//  Copyright © 2019 Luke. All rights reserved.
//

#import "UIViewController+Swizzling.h"
#import "SwizzlingDefine.h"
#import "SVProgressHUD.h"

@implementation UIViewController (Swizzling)
+(void)load
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        swizzling_exchangeMethod([UIViewController class] ,@selector(viewWillAppear:), @selector(swizzling_viewWillAppear:));
        swizzling_exchangeMethod([UIViewController class] ,@selector(viewDidAppear:), @selector(swizzling_viewDidAppear:));
        swizzling_exchangeMethod([UIViewController class] ,@selector(viewWillDisappear:), @selector(swizzling_viewWillDisappear:));
        swizzling_exchangeMethod([UIViewController class] ,@selector(viewDidDisappear:), @selector(swizzling_viewDidDisappear:));
        swizzling_exchangeMethod([UIViewController class] ,@selector(viewDidLoad),    @selector(swizzling_viewDidLoad));
    });
}

- (void)backAction
{
    [self.navigationController popViewControllerAnimated:YES];
}
#pragma mark - ViewDidLoad
- (void)swizzling_viewDidLoad{
    [self swizzling_viewDidLoad];
    
    [WRNavigationBar wr_setDefaultNavBarBarTintColor:VCBackgroundColor];
    [WRNavigationBar wr_setDefaultNavBarTitleColor:TitleTextColor];
    
    if (self.navigationController && self.navigationController.childViewControllers.count > 1) {
        
        UIImage *image =[UIImage imageNamed:@"nav_back"];
        image =[image imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
        UIBarButtonItem* backItem =  [[UIBarButtonItem alloc] initWithImage:image style:UIBarButtonItemStylePlain target:self action:@selector(backAction)];
        self.navigationItem.leftBarButtonItem = backItem;
        
        
        /*
         UIImage *buttonNormal = [[UIImage imageNamed:@"nav_back"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
         [self.navigationController.navigationBar setBackIndicatorImage:buttonNormal];
         [self.navigationController.navigationBar setBackIndicatorTransitionMaskImage:buttonNormal];
         //  self.navigationController.navigationBar.tintColor = [UIColor blackColor];
         UIBarButtonItem *backItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
         self.navigationItem.backBarButtonItem = backItem;
         */
        
        
    }
}


static char UIFirstResponderViewAddress;

- (void)swizzling_viewWillAppear:(BOOL)animated{
    
    [self swizzling_viewWillAppear:animated];
    
    
}

#pragma mark - ViewDidAppear
- (void)swizzling_viewDidAppear:(BOOL)animated{
    
    [self swizzling_viewDidAppear:animated];
   
    //    if (![self isKindOfClass:[UINavigationController class]]) {
    //
    //        self.hidesBottomBarWhenPushed = YES;
    //    }
    
}


#pragma mark - ViewWillDisappear

- (void)swizzling_viewWillDisappear:(BOOL)animated{
    [self swizzling_viewWillDisappear:animated];

    if([SVProgressHUD isVisible]){
        [SVProgressHUD dismiss];
    }
    
}

-(void) swizzling_viewDidDisappear:(BOOL) animated
{
    [self swizzling_viewDidDisappear:animated];
    
    //    if (![self isKindOfClass:[UINavigationController class]]) {
    //
    //    }
    
    //    if (![self isKindOfClass:[UINavigationController class]]) {
    //        if(self.navigationController.viewControllers.count == 1){
    //            self.hidesBottomBarWhenPushed = NO;
    //        }
    //    }
}


@end
