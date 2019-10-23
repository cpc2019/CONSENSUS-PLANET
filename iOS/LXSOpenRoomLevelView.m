//
//  LXSOpenRoomLevelView.m
//  LXSDAPP
//
//  Created by UGFever on 2019/7/23.
//  Copyright © 2019 mac. All rights reserved.
//

#import "LXSOpenRoomLevelView.h"
#import "UILabel+Create.h"

@interface LXSOpenRoomLevelView (){
    LXSOpenRoomStyle _viewStyle;
}

@property (nonatomic, strong) UILabel *numberLabel;

@property (nonatomic, strong) UILabel *priceLabel;

@property (nonatomic, strong) UIButton *clickBtn;

@property (nonatomic, copy) void(^buttonBlock)(void);

@end

@implementation LXSOpenRoomLevelView

-(instancetype)initWithFrame:(CGRect)frame style:(LXSOpenRoomStyle)style{
    self = [super initWithFrame:frame];
    if (self) {
        [self setupUI];
        [self setStyle:style];
    }
    return self;
}

#pragma mark - setupUI
-(void)setupUI{
    self.backgroundColor = rgba(47,46,78,1);
    self.layer.cornerRadius = 6;
    self.layer.masksToBounds = YES;
    self.layer.borderWidth = 1.0;
    
    [self addSubview:self.numberLabel];
    [self.numberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.offset(0);
        make.top.offset(37);
    }];
    
    [self addSubview:self.priceLabel];
    WeakSelf;
    [self.priceLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.offset(0);
        make.top.equalTo(weakSelf.numberLabel.mas_bottom).offset(10);
    }];
    
    [self addSubview:self.clickBtn];
    [self.clickBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.offset(0);
    }];
}

#pragma mark - public method
-(void)setStyle:(LXSOpenRoomStyle)style{
    _viewStyle = style;
    self.clickBtn.userInteractionEnabled = YES;
    if (style == LXSOpenRoomStyleSelected) {
        self.layer.borderColor = ThemeColor.CGColor;
        self.priceLabel.textColor = ThemeColor;
        self.numberLabel.textColor = rgba(163,171,204,1);
    }else{
        self.layer.borderColor = [UIColor clearColor].CGColor;
        self.priceLabel.textColor = UnSelectedBackgroundColor;
        if (style == LXSOpenRoomStyleNormal) {
            self.numberLabel.textColor = rgba(163,171,204,1);
        }else{
            self.numberLabel.textColor = UnSelectedBackgroundColor;
            self.clickBtn.userInteractionEnabled = NO;
        }
    }
}

-(void)setNumberString:(NSString *)number price:(NSString *)price{
    self.priceLabel.text = price;
    self.numberLabel.text = number;
}

//点击事件的回调
-(void)clickBlock:(void(^)(void))block{
    self.buttonBlock = block;
}

#pragma mark - event method
-(void)clickAction:(UIButton *)button{
    if (self.buttonBlock) {
        self.buttonBlock();
    }
}

#pragma mark - lazzy method
-(UILabel *)numberLabel{
    if (!_numberLabel) {
        _numberLabel = [UILabel creatLabelWithText:@"" font:[UIFont systemFontOfSize:24] textColor:rgba(163,171,204,1) textAlignment:NSTextAlignmentCenter];
    }
    return _numberLabel;
}

-(UILabel *)priceLabel{
    if (!_priceLabel) {
        _priceLabel = [UILabel creatLabelWithText:@"--CXC" font:[UIFont systemFontOfSize:14] textColor:UnSelectedBackgroundColor textAlignment:NSTextAlignmentCenter];
    }
    return _priceLabel;
}

-(UIButton *)clickBtn{
    if (!_clickBtn) {
        _clickBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [_clickBtn addTarget:self action:@selector(clickAction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _clickBtn;
}
@end
