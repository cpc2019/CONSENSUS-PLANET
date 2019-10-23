//
//  LLWSAccountTF.h
//  LLWS
//  账号输入框V
//  Created by qql on 2019/7/16.
//  Copyright © 2019 766tech. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LLWSRequestHandle.h"

@protocol LLWSAccountTFDelegate <NSObject>
/**
 选择国家按钮点击
 */
- (void)chooseCounrtyClick;
/**
 正在编辑的内容

 @param string 正在编辑的内容
 */
- (void)accountTfEditChanged:(NSString *_Nonnull)string;
@end


NS_ASSUME_NONNULL_BEGIN
@interface LLWSAccountTF : UIView
/**
  代理
 */
@property (nonatomic, weak)id<LLWSAccountTFDelegate> delegate;

/**
 编辑结束的block回调
 */
@property(nonatomic, copy)TextFieldEidtEndHanler editEndHandle;

/**
  设置是否最大输入
 */
@property (nonatomic, assign)BOOL hasMax;

/**
 设置最大输入字数
 */
@property (nonatomic, assign)int maxLength;

/**
设置输入框文字
 */
@property(nonatomic, copy)NSString *text;

/**
 国家名字
 */
@property(nonatomic, copy)NSString *countryName;
@end
NS_ASSUME_NONNULL_END
