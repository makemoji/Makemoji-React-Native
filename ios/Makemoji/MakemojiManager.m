//
//  RCTHypermojiModule.m
//  MakeMojiReactNative
//
//  Created by Makemoji on 9/11/16.
//

#import <Foundation/Foundation.h>

#import "RCTBridgeModule.h"
#import "MakemojiManager.h"
#import "MakemojiSDK.h"
#import "MEEmojiWall.h"
#import "RCTEventDispatcher.h"
#import "RCTBridge.h"

#import "RCTUIManager.h"
#import "RCTMETextInputView.h"
#import "RCTEventEmitter.h"

#import "AppDelegate.h"


@implementation MakemojiManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(init:(NSString *)key)
{
  
  [MakemojiSDK setSDKKey:key];
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(hypermojiClick:)
                                               name:@"MEHypermojiLinkClicked"
                                             object:nil];
}
RCT_EXPORT_METHOD(openWall){
  MEEmojiWall * emojiWall = [[MEEmojiWall alloc] init];
  emojiWall.delegate = self;
  emojiWall.modalPresentationStyle = UIModalPresentationOverCurrentContext;
  
  UINavigationController *navigationController =
  [[UINavigationController alloc] initWithRootViewController:emojiWall];
  
  AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  // present the emoji wall as a modal
  [delegate.window.rootViewController presentViewController:navigationController animated:YES completion:nil];
}
-(void)meEmojiWall:(MEEmojiWall *)emojiWall didSelectEmoji:(NSDictionary*)emoji {
  [emojiWall dismissViewControllerAnimated:YES completion:nil];
  NSMutableDictionary *myDictionary = [emoji mutableCopy];
  [myDictionary removeObjectForKey:@"image_object"];
  [self sendEventWithName: @"onEmojiWallSelect"
                                               body:myDictionary];
  NSLog(@"%@", emoji);
}
- (NSArray<NSString *> *)supportedEvents {
  return @[@"onEmojiWallSelect",@"onHypermojiPress"];
}

RCT_EXPORT_METHOD(setChannel:(NSString *)channel)
{
  [MakemojiSDK setChannel:channel];
}

- (void)hypermojiClick:(NSNotification *)note {
  NSDictionary *theData = [note userInfo];
  if (theData != nil) {
    NSString *n = [theData objectForKey:@"url"];
    [self sendEventWithName:@"onHypermojiPress"
                                                 body:@{@"url": n}];
  }
}

//not working currently
RCT_EXPORT_METHOD(detatch:(nonnull NSNumber *)mojiInputTag :(nonnull NSNumber *)detatchTag ){
  
  dispatch_async(RCTGetUIManagerQueue(),^{
  [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager,NSDictionary<NSNumber *, UIView *> *viewRegistry) {
    RCTMETextInputView* view = viewRegistry[mojiInputTag];
    UIView *otherView = viewRegistry[detatchTag];
    [view setTextInputContainerView:otherView];
    [view detachTextInputView:YES];
    
  }];
  });
  
  
}

@end
