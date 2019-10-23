//
//  LXSEmptyView.h
//  LXSApp
//  缺省页（可刷新）
//  Created by qql on 2019/7/25.
//  Copyright © 2019 Luke. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef void(^LXSEmptyViewRefreshBlock)(void);

NS_ASSUME_NONNULL_BEGIN

@interface LXSEmptyView : UIView
/**
 提示语（支持换行）
 */
@property (nonatomic, strong)NSString *tipMessage;
/**
 提示语的属性
 */
@property (nonatomic, strong)NSMutableAttributedString *tipsAttributed;
/**
 点击刷新按钮
 */
@property (nonatomic, copy)LXSEmptyViewRefreshBlock refreshBlock;
@end

NS_ASSUME_NONNULL_END
