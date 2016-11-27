//
//  RCTMojiInputLayout.h
//  MakeMojiReactNative
//
//  Created by Makemoji on 8/11/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "METextInputView.h"
#import "RCTComponent.h"

@interface RCTMoji :METextInputView
@property (nonatomic, copy) RCTBubblingEventBlock onCameraPressed;

@end
