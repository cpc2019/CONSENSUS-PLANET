//
//  LXSEmptyView.m
//  LXSApp
//  缺省页（可刷新）
//  Created by qql on 2019/7/25.
//  Copyright © 2019 Luke. All rights reserved.
//

#import "LXSEmptyView.h"
#import "LLWSLinkLabel.h"
#import "UILabel+YBAttributeTextTapAction.h"

@interface LXSEmptyView ()<YBAttributeTapActionDelegate>
/**
 缺省图片
 */
@property (nonatomic, strong) UIImageView *iconIV;
/**
 提示语（支持换行）
 */
@property (nonatomic, strong) LLWSLinkLabel *tipsLabel;
@end


@implementation LXSEmptyView
#pragma mark -------- init
- (instancetype)initWithFrame:(CGRect)frame {
    
    self = [super initWithFrame:frame];
    if (self) {
        
        [self initSettings];
        [self setupUI];
        [self layoutSubviewsFrame];
    }
    
    return self;
}

- (void)initSettings {
    
    self.backgroundColor = LXSBackgroundColor;
}

- (void)setupUI {
    
    [self addSubview:self.iconIV];
    [self addSubview:self.tipsLabel];
}

- (void)layoutSubviewsFrame {

    [self.tipsLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        
        make.centerX.centerY.equalTo(self);
    }];
    
    [self.iconIV mas_makeConstraints:^(MASConstraintMaker *make) {
        
        make.bottom.equalTo(self.tipsLabel.mas_top).offset(-10);
        make.centerX.equalTo(self);
    }];
}


#pragma mark - YBAttributeTapActionDelegate
- (void)yb_tapAttributeInLabel:(UILabel *)label string:(NSString *)string range:(NSRange)range index:(NSInteger)index
{
    if (self.refreshBlock) {
        
        self.refreshBlock();
    }
}
#pragma mark -------- lazyloadiong
- (UIImageView *)iconIV {
    
    if (!_iconIV) {
        
        _iconIV = [[UIImageView alloc] init];
        _iconIV.image = [LXSAPPBundle imageWithImageName:@"image_search_nodata"];
    }
    
    return _iconIV;
}

- (LLWSLinkLabel *)tipsLabel {
    
    if (!_tipsLabel) {
        
        _tipsLabel = [[LLWSLinkLabel alloc] init];
        _tipsLabel.numberOfLines = 0;
        _tipsLabel.text = LLWSAppLocalized(@"LXS.TTNoData",@"暂无数据，点击刷新");
        _tipsLabel.textColor = [UIColor whiteColor];
        _tipsLabel.font = [UIFont systemFontOfSize:16.];
        _tipsLabel.textAlignment = NSTextAlignmentCenter;
        //点击
        [_tipsLabel yb_addAttributeTapActionWithStrings:@[LLWSAppLocalized(@"LXS.TTClickRefresh",@"点击刷新")] delegate:self];
        [_tipsLabel addClickText:LLWSAppLocalized(@"LXS.TTClickRefresh",@"点击刷新") attributeds:@{NSForegroundColorAttributeName:HEXCOLOR(0x607FFF),NSUnderlineStyleAttributeName:[NSNumber numberWithInteger:NSUnderlineStyleSingle],NSUnderlineColorAttributeName:HEXCOLOR(0x607FFF)} transmitBody:(id)@"点击刷新 被点击了" clickItemBlock:nil];
    }
    
    return _tipsLabel;
}
@end
