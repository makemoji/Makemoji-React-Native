#import <MapKit/MapKit.h>

#import "RCTViewManager.h"
#import <UIKit/UIKit.h>
#import "METextInputView.h"
#import "RCTMoji.h"

#import "UIView+React.h"


@interface RCTMojiInputLayout : RCTViewManager
@end

@interface RCTMojiInputLayout () <METextInputViewDelegate>

@end


@implementation RCTMojiInputLayout

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(onCameraPressed, RCTBubblingEventBlock)

- (UIView *)view
{
  METextInputView *view = [[METextInputView alloc] init];
  view.delegate = self;
  return view;
}

RCT_EXPORT_VIEW_PROPERTY(displaySendButton, BOOL)
RCT_EXPORT_VIEW_PROPERTY(displayCameraButton, BOOL)

RCT_CUSTOM_VIEW_PROPERTY(inputTextColor, UIColor, METextInputView){
  NSLog(@"custom view prop");
  if (json){
    [view setTextInputTextColor:[RCTConvert UIColor:json]];
    
  }
}
RCT_CUSTOM_VIEW_PROPERTY(abc, UIColor, METextInputView){
  NSLog(@"custom view prop");
  if (json){
    [view setTextInputTextColor:[RCTConvert UIColor:json]];
    
  }
}

#pragma mark METextInputViewDelegate
-(void)meTextInputView:(METextInputView *)inputView didTapCameraButton:(UIButton*)cameraButton {
  NSLog(@"camera");
  //((RCTMoji *) inputView).onCameraPressed(nil);
}
-(void)meTextInputView:(METextInputView *)inputView didTapHypermoji:(NSString*)urlString {
  // open webview here
}
-(void)meTextInputView:(METextInputView *)inputView didTapSend:(NSDictionary *)message {
  NSLog(@"%@", message);
  
}
-(void)meTextInputView:(METextInputView *)inputView didTapHyperlink:(NSString*)urlString {
  // open webview here
}

-(void)meTextInputView:(METextInputView *)inputView didChangeFrame:(CGRect)frame{
  
}

@end