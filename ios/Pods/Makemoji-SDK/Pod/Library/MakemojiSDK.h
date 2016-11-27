//
//  MakemojiSDK.h
//  MakemojiSDK
//
//  Copyright (c) 2015 Makemoji. All rights reserved.
//

#import <Foundation/Foundation.h>
static NSString *const MECategoryUnlockedSuccessNotification = @"MECategoryUnlockedSuccessNotification";
static NSString *const MECategoryUnlockedFailedNotification = @"MECategoryUnlockedFailedNotification";
static NSString *const MEHypermojiLinkClicked = @"MEHypermojiLinkClicked";
static NSString *const MEHyperlinkClicked = @"MEHyperlinkClicked";

@interface MakemojiSDK : NSObject

+(void)setSDKKey:(NSString *)sdkKey;
+(void)setChannel:(NSString *)channel;
+(void)unlockCategory:(NSString *)category;
+(NSArray *)unlockedGroups;
@end
