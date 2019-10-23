//
//  LXSOpenRoomLevelView.h
//  LXSDAPP
//
//  Created by UGFever on 2019/7/23.
//  Copyright © 2019 mac. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger, LXSOpenRoomStyle) {
    LXSOpenRoomStyleNormal,         //普通样式
    LXSOpenRoomStyleUnSelect,       //不可选
    LXSOpenRoomStyleSelected        //选中样式
};

@interface LXSOpenRoomLevelView : UIView

@property (nonatomic, assign) BOOL isSelected;

-(instancetype)initWithFrame:(CGRect)frame style:(LXSOpenRoomStyle)style;

-(void)setStyle:(LXSOpenRoomStyle)style;

-(void)setNumberString:(NSString *)number price:(NSString *)price;

//点击事件的回调
-(void)clickBlock:(void(^)(void))block;

@end

NS_ASSUME_NONNULL_END
