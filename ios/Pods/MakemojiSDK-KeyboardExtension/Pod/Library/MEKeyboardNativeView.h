//
//  MEKeyboardNativeView.h
//  Makemoji
//
//  Created by steve on 2/19/16.
//  Copyright © 2016 Makemoji. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol MEKeyboardNativeViewDelegate;

@interface MEKeyboardNativeView : UIView
    @property UIView * rowOne;
    @property UIView * rowTwo;
    @property UIView * rowThree;
    @property UIView * rowFour;
    @property NSMutableArray * alphaKeys;
    @property NSMutableArray * numericKeys;
    @property NSMutableArray * symbolKeys;
    @property (assign) id <UITextDocumentProxy> textDocumentProxy;
    @property (assign) id <MEKeyboardNativeViewDelegate> inputViewController;
    @property UIButton * shiftKey;
    @property UIButton * deleteKey;
    @property UIButton * returnKey;
    @property UIButton * spaceKey;
    @property UIButton * numberKey;
    @property UIButton * globeButton;
    @property UIButton * emojiButton;

    @property NSString * keyboardState;
    @property NSTimer * deleteTimer;

    @property UIColor * fontColor;
    @property UIColor * keyColor;
    @property UIColor * borderColor;
    @property UIColor * shiftColor;

    @property CGFloat  rowHeight;
    @property CGFloat betweenRows;
    @property UIEdgeInsets padding;
    @property CGSize defaultKeySize;
    @property CGFloat betweenKeys;
    @property NSString * emojiButtonImageName;

    -(void)updateLayout:(CGRect)frame;
    -(void)updateKeySize;

@end

@protocol MEKeyboardNativeViewDelegate <NSObject>
    -(void)meKeyboardNativeView:(MEKeyboardNativeView *)inputView didTapEmojiButton:(UIButton *)button;
    -(void)meKeyboardNativeView:(MEKeyboardNativeView *)inputView didTapGlobeButton:(UIButton *)button;
    -(void)meKeyboardNativeView:(MEKeyboardNativeView *)inputView didInsertText:(NSString *)text;
@end
