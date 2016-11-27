//
//  MEReactionView.h
//  MakemojiSDK
//
//  Created by steve on 5/11/16.
//  Copyright © 2016 Makemoji. All rights reserved.
//

#import <UIKit/UIKit.h>

// notification name when a reaction is chosen
static NSString * const MEReactionNotification = @"MEReactionNotification";

@interface MEReactionView : UIView

// your unique content id for this reaction set. reaction data is retreieved when this value is set or changes.
@property (nonatomic, retain) NSString * contentId;

// the emoji wall trigger button
@property (nonatomic, retain) UIButton * wallTriggerView;

// current set of reactions, including defaults
@property (nonatomic, retain) NSMutableArray * reactions;

// the current user reaction
@property (nonatomic, retain) NSDictionary * currentUserReaction;

// the collection view for showing reactions
@property (nonatomic, retain) UICollectionView * reactionCollectionView;

// border and text color when a user has selected a reaction, defaults to light blue
@property (nonatomic, retain) UIColor * cellHighlightColor;

// cell border color for reactions, defaults to light gray
@property (nonatomic, retain) UIColor * cellBorderColor;

// default cell text color, defaults to gray
@property (nonatomic, retain) UIColor * cellTextColor;

// your view controller for displaying the emoji wall
@property (assign) id viewController;

// convenience init method
-(instancetype)initWithFrame:(CGRect)frame contentId:(NSString *)contentId;

@end
