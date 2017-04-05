// RCTMapManager.m

#import <React/RCTViewManager.h>
#import "MESimpleTableViewCell.h"
#import <UIKit/UIKit.h>
#import "RCTMETextInputView.h"

@interface MESimpleTableViewCellManager : RCTViewManager
@end

@implementation MESimpleTableViewCellManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  MESimpleTableViewCell* view=[[MESimpleTableViewCell alloc] init];
  
  return view;
}

RCT_CUSTOM_VIEW_PROPERTY(html, NSString, MESimpleTableViewCell)
{
  [view setHTMLString:[RCTConvert NSString:json]];
}
RCT_CUSTOM_VIEW_PROPERTY(plaintext, NSString, MESimpleTableViewCell)
{
  [view setHTMLString:[METextInputView convertSubstituedToHTML:json]];
}
- (void)hypermojiClick:(NSNotification *)note {
  NSDictionary *theData = [note userInfo];
  if (theData != nil) {
    NSNumber *n = [theData objectForKey:@"MEHypermojiLinkClicked"];
    BOOL isReachable = [n boolValue];
    NSLog(@"reachable: %d", isReachable);
  }
}

@end
