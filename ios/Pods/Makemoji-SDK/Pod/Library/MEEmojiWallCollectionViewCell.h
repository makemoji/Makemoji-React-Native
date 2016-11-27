//
//  MEEmojiWallCollectionViewCell.h
//  MakemojiSDK
//
//  Created by steve on 4/9/16.
//  Copyright © 2016 Makemoji. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MEEmojiWallCollectionViewCell : UICollectionViewCell <UICollectionViewDelegate, UICollectionViewDataSource>
    @property UICollectionView * emojiCollectionView;
    @property NSArray * emoji;
    @property NSString * selectedCategory;
    -(void)setEmojiData:(NSArray *)emoji;
    @property CGSize itemSize;
    @property BOOL isVideoCollection;
    @property UIColor * videoTextColor;
    @property UIColor * playOverlayTint;
@end
