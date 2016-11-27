//
//  RCTMojiInputLayoutManager.m
//  MakeMojiReactNative
//
//  Created by Makemoji on 9/6/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MakemojiSDK.h"
#import "RCTViewManager.h"
#import <UIKit/UIKit.h>
#import "RCTMETextInputView.h"
#import "RCTBridge.h"
#import "RCTUIManager.h"

@interface RCTMojiInputLayoutManager : RCTViewManager <METextInputViewDelegate>
@end

@implementation RCTMojiInputLayoutManager

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(onSendPress, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onHypermojiPress, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onCameraPress, RCTBubblingEventBlock)

@synthesize bridge = _bridge;

- (UIView *)view
{
  RCTMETextInputView *view = [RCTMETextInputView new];
  view.delegate = self;
  return view;
}

// send button was pressed
-(void)meTextInputView:(RCTMETextInputView *)inputView didTapSend:(NSDictionary *)message {
  inputView.onSendPress(@{
                          @"html": [message valueForKeyPath:@"html"],
                          @"plaintext":[message valueForKeyPath:@"substitute"]});
}

// handle camera action
-(void)meTextInputView:(RCTMETextInputView *)inputView didTapCameraButton:(UIButton*)cameraButton {
  inputView.onCameraPress(NULL);
}

// handle tapping on linked text
-(void)meTextInputView:(RCTMETextInputView *)inputView didTapHypermoji:(NSString*)urlString {
  //inputView.onHypermojiPress(@{@"url":urlString});
}

RCT_CUSTOM_VIEW_PROPERTY(cameraVisible, BOOL, RCTMETextInputView)
{
  [view setDisplayCameraButton:[RCTConvert BOOL:json]];
}
RCT_CUSTOM_VIEW_PROPERTY(sendButtonVisible, BOOL, RCTMETextInputView)
{
  [view setDisplaySendButton:[RCTConvert BOOL:json]];
}

RCT_CUSTOM_VIEW_PROPERTY(textInputTextColor, UIColor, RCTMETextInputView)
{
  [view setTextInputTextColor:[RCTConvert UIColor:json]];
}
RCT_CUSTOM_VIEW_PROPERTY(placeholderTextColor, UIColor, RCTMETextInputView)
{
  view.placeholderLabel.textColor=[RCTConvert UIColor:json];
}

RCT_CUSTOM_VIEW_PROPERTY(textSolidBackgroundColor, UIColor, RCTMETextInputView)
{
  view.textSolidBackgroundView.backgroundColor=[RCTConvert UIColor:json];
}
RCT_CUSTOM_VIEW_PROPERTY(textInputContainerColor, UIColor, RCTMETextInputView)
{
  view.textInputContainerView.backgroundColor=[RCTConvert UIColor:json];
}

RCT_CUSTOM_VIEW_PROPERTY(navigationBackgroundColor, UIColor, RCTMETextInputView)
{
  [view.meAccessory setNavigationBackgroundColor:[RCTConvert UIColor:json]];
}
RCT_CUSTOM_VIEW_PROPERTY(navigationHighlightColor, UIColor, RCTMETextInputView)
{
  [view.meAccessory setNavigationHighlightColor:[RCTConvert UIColor:json]];
}
RCT_CUSTOM_VIEW_PROPERTY(accessoryBackgroundColor, UIColor, RCTMETextInputView)
{
  view.meAccessory.backgroundColor=[RCTConvert UIColor:json];
}
RCT_CUSTOM_VIEW_PROPERTY(flashtagCollectionViewBackgroundColor, UIColor, RCTMETextInputView)
{
  view.meAccessory.flashtagCollectionView.backgroundColor=[RCTConvert UIColor:json];
}
RCT_CUSTOM_VIEW_PROPERTY(emojiPageBackgroundColor, UIColor, RCTMETextInputView)
{
  view.meAccessory.meInputView.backgroundColor=[RCTConvert UIColor:json];
}
RCT_CUSTOM_VIEW_PROPERTY(emojiCollectionBackgroundColor, UIColor, RCTMETextInputView)
{
  view.meAccessory.meInputView.emojiView.backgroundColor=[RCTConvert UIColor:json];
}
RCT_CUSTOM_VIEW_PROPERTY(categoriesBackgroundColor, UIColor, RCTMETextInputView)
{
  view.meAccessory.meInputView.collectionView.backgroundColor=[RCTConvert UIColor:json];
}

   


@end

