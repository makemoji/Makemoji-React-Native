//
//  RCTMojiReactions.m
//  MakemojiReactNative
//
//  Created by Makemoji on 11/28/16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <React/RCTViewManager.h>
#import "MESimpleTableViewCell.h"
#import "MEReactionView.h"
#import <UIKit/UIKit.h>
#import "RCTMETextInputView.h"
#import "AppDelegate.h"


@interface RCTMojiReactionsManager : RCTViewManager
@end

@implementation RCTMojiReactionsManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  MEReactionView* view = [[MEReactionView alloc] init];
  AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  [view setViewController:delegate.window.rootViewController];
  return view;
}

RCT_CUSTOM_VIEW_PROPERTY(contentId, NSString, MEReactionView)
{
  view.contentId = [RCTConvert NSString:json];
}
@end

