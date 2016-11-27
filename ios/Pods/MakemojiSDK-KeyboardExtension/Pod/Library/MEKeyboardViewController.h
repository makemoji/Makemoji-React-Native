//
//  KeyboardViewController.h
//  Makemoji Keyboard
//
//  Created by steve on 12/26/15.
//  Copyright © 2015 Makemoji. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MEKeyboardNativeView.h"

@interface MEKeyboardViewController : UIInputViewController <UICollectionViewDataSource, UICollectionViewDelegate, MEKeyboardNativeViewDelegate>

@property UICollectionView * navigationCollectionView;
@property UICollectionView * emojiCollectionView;
@property UICollectionView * searchCollectionView;
@property UILabel * sectionLabel;
@property UIButton * shareButton;
@property UIButton * backspaceButton;
@property UIView * alertContainerView;
@property UILabel * alertLabel;
@property UIImageView * alertImageView;
@property MEKeyboardNativeView * keyboardView;
@property NSString * shareText;
@property CGSize outputSize;
@property CGSize emojiInnerSize;
@property NSMutableArray * categories;
@property NSIndexPath * selectedCategoryIndexPath;
@property (nonatomic, strong) UIButton *nextKeyboardButton;
@property NSString * navigationCellClass;
@property UIColor * mainBackgroundColor;
@property BOOL displayVideoCollection;
@property BOOL enableUpdates;
@property BOOL enableTrending;
@property BOOL enableUsed;
@property NSString * keyboardImageName;

-(void)setupLayoutWithSize:(CGSize)size;
-(void)showKeyboard:(BOOL)show;
-(void)didSelectCategory:(NSDictionary *)category atIndexPath:(NSIndexPath *)indexPath;
-(void)loadedCategoryData;

@end
