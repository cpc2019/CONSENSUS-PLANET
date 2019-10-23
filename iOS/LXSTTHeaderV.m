//
//  LXSTTHeaderV.m
//  LXSApp
//  天梯模块tabbleView分区头
//  Created by qql on 2019/7/23.
//  Copyright © 2019 Luke. All rights reserved.
//

#import "LXSTTHeaderV.h"
#import "LXSTTModel.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "UIImage+OMOColor.h"
#import "LXSTTEnum.h"
#import "LXSTTHeaderVItem.h"
#import "UIView+RoundedRect.h"
#import "LXSTTViewModel.h"


#define LXSTTHeaderVItemWidth ((ScreenWidth - 2*15)/3.0)

@interface LXSTTHeaderV ()
/**
 头像
 */
@property (nonatomic, strong) UIImageView *iconIV;
/**
 计算规则提示语
 */
@property (nonatomic, strong) UILabel *rulesLb;
/**
 查看昨日 || 上周 || 上月
 */
@property (nonatomic, strong) UILabel *watchLb;
/**
 排行图(前3名)
 */
@property (nonatomic, strong)UIView *rankV;

/**
 天梯列表模型(可以私有化)
 */
@property (nonatomic, strong)LXSTTViewModel *dataSource;
@end


@implementation LXSTTHeaderV
#pragma mark ------ init
- (instancetype)initWithFrame:(CGRect)frame Data:(LXSTTViewModel *)data {
    
    self = [super initWithFrame:frame];
    if (self) {
        
        _dataSource = data;
        [self initSetting];
        [self setupUI];
    }
    
    return self;
}
- (void)initSetting {
    
    self.backgroundColor = LXSBackgroundColor;//[UIColor redColor]
}
- (void)setupUI {
    
    [self addSubview:self.iconIV];
    [self addSubview:self.rulesLb];
    [self addSubview:self.watchLb];
    [self addSubview:self.rankV];
}


#pragma mark ------ privateMethod
#pragma mark - 查看榜单的手势
- (void)watchLbTap {
    
    if (self.delegate && [self.delegate respondsToSelector:@selector(watchLastRank)]) {
        
        [self.delegate watchLastRank];
    }
}

#pragma mark - 进入主播房间
- (void)headerVItemTap:(UITapGestureRecognizer *)tap {
    
    NSInteger roleType = [self.rankDic[@"roleType"] integerValue];
    if (roleType == LXSTTRoleTypeAnchor) {
        
        UIView *view = tap.view;
        if ([view isKindOfClass:[LXSTTHeaderVItem class]]) {
            
            if (self.delegate && [self.delegate respondsToSelector:@selector(selectAnchorWithIndex:)]) {
                
                if ([[(LXSTTHeaderVItem *)view TTModel] userId].length > 0) {
                    
                    [self.delegate selectAnchorWithIndex:view.tag - 100];
                }
            }
        }
    }
}


#pragma mark ------ publicMethod
- (void)updateUI {
    
    self.rulesLb.text = self.dataSource.dateString;
    if (self.isLastTime) {
     
        self.watchLb.hidden = YES;
    }else {
        
        self.watchLb.hidden = NO;
        NSInteger rankType = [self.rankDic[@"rankType"] integerValue];
        NSString *watch = [self watchStringWithRankType:rankType];
        self.watchLb.text = watch;
    }
}
- (NSString *)watchStringWithRankType:(NSInteger)rankType {
    
    NSString *watch = @"";
    switch (rankType) {
        case LXSTTRankTypedDaily:
    
            watch = LLWSAppLocalized(@"LXS.TTWatchDaily", @"查看昨日榜单");
            break;
        case LXSTTRankTypedWeekly:
           
            watch = LLWSAppLocalized(@"LXS.TTWatchWeekly", @"查看上周榜单");
            break;
        case LXSTTRankTypedMonthly:
  
            watch = LLWSAppLocalized(@"LXS.TTWatchMonthly", @"查看上月榜单");
            break;
    }
    
    return watch;
}



#pragma mark ------ lazyLoading
- (UIImageView *)iconIV {
    
    if (!_iconIV) {
        
        _iconIV = [[UIImageView alloc] init];
        _iconIV.frame = CGRectMake(15, 15, 10.0, 10.0);
        _iconIV.image = [LXSAPPBundle imageWithImageName:@"LXSTTHeaderV_rule"];
    }
    
    return _iconIV;
}

- (UILabel *)rulesLb {
    
    if (!_rulesLb) {
        
        _rulesLb = [[UILabel alloc] init];
        _rulesLb.frame = CGRectMake(self.iconIV.right+4.0, 15, ScreenWidth/3.0*2.0-15.0-10.0-4.0, 10.0);
        _rulesLb.textColor = HEXCOLOR(0xA3ABCC);
        _rulesLb.font = [UIFont systemFontOfSize:10.];
        _rulesLb.textAlignment = NSTextAlignmentLeft;
    }
    
    return _rulesLb;
}


- (UILabel *)watchLb {
    
    if (!_watchLb) {
        
        _watchLb = [[UILabel alloc] init];
        _watchLb.frame = CGRectMake(ScreenWidth/2.0, 15, ScreenWidth/2.0-15.0, 10.0);
        _watchLb.textColor = HEXCOLOR(0x607FFF);
        _watchLb.font = [UIFont systemFontOfSize:10.];
        _watchLb.textAlignment = NSTextAlignmentRight;
        _watchLb.userInteractionEnabled = YES;
        
        UIGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(watchLbTap)];
        [_watchLb addGestureRecognizer:tap];
    }
    
    return _watchLb;
}

- (UIView *)rankV {
    
    if (!_rankV) {
        
        _rankV = [[UIView alloc] init];
        _rankV.frame = CGRectMake(15, 40, ScreenWidth-2*15.0, 160.0);
//        _rankV.backgroundColor = [UIColor blackColor];
        for (int i = 0; i < 3; i++) {
            
            CGFloat x = i * LXSTTHeaderVItemWidth;
            CGFloat y = 20;
            CGFloat h = 140;
            CGSize roundSize = CGSizeZero;
            UIRectCorner rectCorner = 0;
            //排名
            int tag = 0;
            LXSTTModel *data = [LXSTTModel new];
            if (i == 0) {
                if (self.dataSource.dataList.count >= 3) {
                    data = self.dataSource.dataList[1];
                }
                //---------->>不论是否有数据 得显示1、2、3名的排名<<-------
                data.rank = 2;
                roundSize = CGSizeMake(4, 0);
                rectCorner = UIRectCornerTopLeft | UIRectCornerBottomLeft;
                tag = 103;
            }else if (i == 1) {
                
                y = 0;
                h = 160;
                if (self.dataSource.dataList.count >= 1) {
                     data = self.dataSource.dataList[0];
                }
                data.rank = 1;
                roundSize = CGSizeMake(4, 0);
                rectCorner = UIRectCornerTopLeft | UIRectCornerTopRight;
                tag = 101;
            }else if (i == 2) {
               
                if (self.dataSource.dataList.count >= 2) {
                    data = self.dataSource.dataList[2];
                }
                data.rank = 3;
                roundSize = CGSizeMake(4, 0);
                rectCorner = UIRectCornerTopRight | UIRectCornerBottomRight;
                tag = 102;
            }
            LXSTTHeaderVItem *item = [[LXSTTHeaderVItem alloc] initWithFrame:CGRectMake(x, y, LXSTTHeaderVItemWidth, h) Data:data];
            item.tag = tag;
            [item roundedRectWithCornerRadii:roundSize byRoundingCorners:rectCorner];
            
            UIGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(headerVItemTap:)];
            [item addGestureRecognizer:tap];
            
            [_rankV addSubview:item];
        }
    }
    
    return _rankV;
}
@end
