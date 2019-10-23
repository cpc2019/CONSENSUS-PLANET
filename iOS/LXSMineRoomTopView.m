

#import "LXSMineRoomTopView.h"
#import "UILabel+Create.h"

@interface LXSMineRoomTopView()
@property (nonatomic,strong) UIImageView *iconImageView;
@property (nonatomic,strong) UILabel *tipsLabel;
@end

@implementation LXSMineRoomTopView
- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        [self setUpViews];
        [self layout];
    }
    return self;
}

- (void)setUpViews {
    [self iconImageView];
    [self tipsLabel];
}

- (void)layout {
    [self addSubview:self.iconImageView];
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.offset(0);
        make.size.mas_equalTo(CGSizeMake(20, 20));
    }];
    
    [self addSubview:self.tipsLabel];
    [self.tipsLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.iconImageView.mas_bottom);
        make.left.right.offset(0);
    }];
}

- (UIImageView *)iconImageView {
    if (!_iconImageView) {
        _iconImageView = [[UIImageView alloc] init];
    }
    return _iconImageView;
}

- (UILabel *)tipsLabel {
    if (!_tipsLabel) {
        _tipsLabel = [UILabel creatLabelWithText:@"10人在线" font:[UIFont systemFontOfSize:14] textColor:TitleTextColor textAlignment:NSTextAlignmentCenter];
        _tipsLabel.textAlignment = NSTextAlignmentCenter;
    }
    return _tipsLabel;
}
@end
