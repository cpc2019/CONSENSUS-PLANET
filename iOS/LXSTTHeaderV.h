//
//  LXSTTHeaderV.h
//  LXSApp
//  天梯模块tabbleView分区头
//  Created by qql on 2019/7/23.
//  Copyright © 2019 Luke. All rights reserved.
//

#import <UIKit/UIKit.h>


NS_ASSUME_NONNULL_BEGIN
@protocol LXSTTHeaderVDelegate <NSObject>
@optional
/**
 选中对应下标的主播(魅力主播才有)

 @param index 主播的对应下标
 */
- (void)selectAnchorWithIndex:(NSInteger)index;
@required
/**
 查看昨日 || 上周 || 上月
 */
- (void)watchLastRank;
@end


@class LXSTTViewModel;
@class LXSTTEnum;
@interface LXSTTHeaderV : UIView
/**
 排行榜类型（决定url）&&  角色类型  &&  时间类型
 */
@property (nonatomic, strong)NSDictionary *rankDic;
/**
 是否昨日 | 上周 | 上月
 */
@property (nonatomic, assign)BOOL isLastTime;

/**
 代理方法
 */
@property (nonatomic, weak)id<LXSTTHeaderVDelegate> delegate;

/**
 初始化方法

 @param frame frame
 @param data data
 @return 实例
 */
- (instancetype)initWithFrame:(CGRect)frame Data:(LXSTTViewModel *)data;
/**
 更新UI（根据rankDic）
 */
- (void)updateUI;
@end

NS_ASSUME_NONNULL_END
