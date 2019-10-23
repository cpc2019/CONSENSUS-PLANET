//
//  LLWSAccountTF.m
//  LLWS
//  账号输入框V
//  Created by qql on 2019/7/16.
//  Copyright © 2019 766tech. All rights reserved.
//

#import "LLWSAccountTF.h"
#import "LXSBaseUtil.h"                 //工具类
#import "UIButton+ChangePosition.h"     //位置按钮
#import "LLWSCountryView.h"             //国家选择|国家区号（+86）V
#import "LXSCommonMacro.h"
#import "NSString+Calculate.h"
#import "UIView+Additions.h"

#define LoginVCWidthAspect 30                               //距离左右边的间距
#define LoginVCTFWidth     ScreenWidth-2*LoginVCWidthAspect //输入框和登录按钮宽度


@interface LLWSAccountTF()<UITextFieldDelegate ,LLWSCountryViewDelegate> {
    
    int _maxInputLength;
    BOOL _need;
}
/**
 背景V
 */
@property (nonatomic, strong)UIView *backgoundV;
/**
 国家区号（+86）V
 */
@property (nonatomic, strong)LLWSCountryView *countryV;
/**
 输入框Tf
 */
@property(nonatomic, strong)UITextField *textField;
@end


@implementation LLWSAccountTF
#pragma mark ------ init
- (instancetype)initWithFrame:(CGRect)frame {
    
    self = [super initWithFrame:frame];
    if (self) {
        
        //设置
        [self initSetting];
        
        //UI
        [self initSubviews];
    }
    return self;
}
#pragma mark - initSetting
- (void)initSetting {
    
    self.backgroundColor = [UIColor clearColor];
    
    //默认需要
    _need = YES;
    //默认20字长 （目前手机号没有到20所以跟密码统一限定）
    _maxInputLength = 30;
}
#pragma mark - initSubviews
- (void)initSubviews {
    
    [self addSubview:self.backgoundV];
    [self.backgoundV addSubview:self.countryV];
    [self.backgoundV addSubview:self.textField];
    
    //字数输入限制
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(textFiledEditChanged:) name:UITextFieldTextDidChangeNotification
                                               object:self.textField];
}


#pragma mark ------ setter && getter
- (void)setHasMax:(BOOL)hasMax {
    
    _need = hasMax;
    if(!_need){
        
        [[NSNotificationCenter defaultCenter] removeObserver:self name:UITextFieldTextDidChangeNotification object:self.textField];
    }
}

- (void)setMaxLength:(int)maxLength {
   
    _maxInputLength = maxLength;
}

- (void)setText:(NSString *)text {
    
    _textField.text = text;
}

- (void)setCountryName:(NSString *)countryName {
    
    _countryName = countryName;
    //更新UI及布局
    [_countryV updateUIWithCountry:countryName];
}


#pragma mark ------ eventMetgod


#pragma mark ------ Delegate
#pragma mark - UITextFieldDelegate
- (void)textFieldDidEndEditing:(UITextField *)textField {
    
    //去除前后空格
    textField.text = [LXSBaseUtil deleteBeginEndSpaceOrEnterWithString:textField.text];
    //结束编辑block
    if (self.editEndHandle) {
        self.editEndHandle(textField);
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    
    [textField endEditing:YES];
    return YES;
}

#pragma mark - LLWSCountryViewDelegate
- (void)chooseCounrty {
    
    if (self.delegate && [self.delegate respondsToSelector:@selector(chooseCounrtyClick)]) {
        [self.delegate chooseCounrtyClick];
    }
}


#pragma mark ------ notification
- (void)textFiledEditChanged:(NSNotification *)notifi {
    
    UITextField *textField = (UITextField *)notifi.object;
    //截取限制字数内的字符串
    [LXSBaseUtil texthandleTextField:textField limitMaxLength:_maxInputLength];
    
    //用于激活按钮
    if (self.delegate && [self.delegate respondsToSelector:@selector(accountTfEditChanged:)]) {
        [self.delegate accountTfEditChanged:textField.text];
    }
}


#pragma mark ------ lazyLoading
- (UIView *)backgoundV {
    
    if (!_backgoundV) {
        
        _backgoundV = [[UIView alloc] initWithFrame:CGRectMake(0, 0, LoginVCTFWidth, 30+14)];
        _backgoundV.backgroundColor = [UIColor clearColor];
        
        UIView *lineV = [[UIView alloc] initWithFrame:CGRectMake(0, 43, LoginVCTFWidth, 1)];
        lineV.backgroundColor = HEXCOLOR(0x2F2E4E);
        [_backgoundV addSubview:lineV];
    }
    
    return _backgoundV;
}

- (LLWSCountryView *)countryV {
    
    if (!_countryV) {
        
        CGFloat width = [NSString calculateRowWidth:@"+8654" fontSize:14. height:14.];
        _countryV = [[LLWSCountryView alloc] initWithFrame:CGRectMake(0, 15, width+(5+8), 14.) Type:LLWSCountryViewTypeCountryCode];
        _countryV.delegate = self;
    }
    
    return _countryV;
}

- (UITextField *)textField {
    
    if (!_textField) {
        
        CGFloat x = self.countryV.right+10;
        _textField = [[UITextField alloc] initWithFrame:CGRectMake(x, 12, LoginVCTFWidth-x, 20)];//总高44
        _textField.backgroundColor = [UIColor clearColor];
        _textField.textColor = [UIColor whiteColor];
        _textField.font = [UIFont systemFontOfSize:14.];
        _textField.delegate = self;
        _textField.keyboardType = UIKeyboardTypeNumberPad;
        
        _textField.placeholder = LLWSAppLocalized(@"LXS.LoginVCEnterAccount", @"请输入手机号");
        [_textField setValue:[UIFont systemFontOfSize:14.] forKeyPath:@"_placeholderLabel.font"];
        [_textField setValue:HEXCOLOR(0x4D508B) forKeyPath:@"_placeholderLabel.textColor"];
        
        _textField.clearButtonMode = UITextFieldViewModeWhileEditing;
        [[_textField valueForKey:@"_clearButton"] setImage:[LXSBaseUtil LXSImageNamed:@"AccountTF_clear" For:self.class] forState:UIControlStateNormal];
    }

    return _textField;
}
@end
