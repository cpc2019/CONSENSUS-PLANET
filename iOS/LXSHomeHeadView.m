

#import "LXSHomeHeadView.h"
#import "UILabel+Create.h"

@interface LXSHomeHeadView ()

@property (nonatomic,strong) UILabel *tipsLabel;

@end

@implementation LXSHomeHeadView

- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {

         _tipsLabel = [UILabel creatLabelWithText:LLWSAppLocalized(@"LXSHomeVC.classTips", @"课堂列表") font:[UIFont systemFontOfSize:16] textColor:TitleTextColor textAlignment:NSTextAlignmentLeft];
         [self addSubview:_tipsLabel];
         [_tipsLabel mas_makeConstraints:^(MASConstraintMaker *make) {
         make.left.top.offset(0);
         }];
        
    }
    return self;
}

@end
