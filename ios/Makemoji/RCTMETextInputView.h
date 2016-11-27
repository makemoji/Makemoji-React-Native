//
//  RCTMETextInputView.h
//  MakeMojiReactNative
//
//  Created by Makemoji on 9/6/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#ifndef RCTMETextInputView_h
#define RCTMETextInputView_h


#endif /* RCTMETextInputView_h */
#import <Foundation/Foundation.h>

#import "RCTComponent.h"
#import "METextInputView.h"

@interface RCTMETextInputView : METextInputView

@property (nonatomic, copy) RCTBubblingEventBlock onSendPress;
@property (nonatomic, copy) RCTBubblingEventBlock onHypermojiPress;
@property (nonatomic, copy) RCTBubblingEventBlock onCameraPress;

@end