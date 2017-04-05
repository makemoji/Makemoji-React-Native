import { PropTypes } from 'react';
const ColorPropType = require('ColorPropType');
import React, { Component } from 'react';
import { requireNativeComponent, findNodeHandle, View, BackAndroid, NativeModules } from 'react-native';

var ReactNative = require('ReactNative');
const UIManager = require('UIManager');
var NativeComponent = requireNativeComponent('RCTMojiInputLayout', null);
export  default class MakemojiTextInput extends React.Component {
    constructor(props) {
        super(props);
        this.state={canGoBack:false};
        this.canGoBack = this.canGoBack.bind(this);
        this.onCanGoBackChanged = this.onCanGoBackChanged.bind(this);
        this.onBackPressed = this.onBackPressed.bind(this);
        this.onSendPress = this.onSendPress.bind(this);
        this.onCameraPress = this.onCameraPress.bind(this);
        this.onHypermojiPress = this.onHypermojiPress.bind(this);
        this.props.tag = this.props.tag || 'MakemojiTextInput';
        this.detatch= this.detatch.bind(this);


    }
    onSendPress(e) {
        if (this.props.onSendPress) {
            this.props.onSendPress(e.nativeEvent);
        }
    }
    onCameraPress(e){
        if (this.props.onCameraPress) {
            this.props.onCameraPress(e.nativeEvent);
        }
    };
    onHypermojiPress(e) {
        if (this.props.onHypermojiPress) {
            this.props.onHypermojiPress(e.nativeEvent);
        }
    };

    onCanGoBackChanged(e){
        this.setState({canGoBack:e.nativeEvent.canGoBack});
    }
    canGoBack(){
        return this.state.canGoBack;
    }
    onBackPressed(){
        UIManager.dispatchViewManagerCommand(
            ReactNative.findNodeHandle(this),
            85,[]
        );
    }

    render() {
        return <NativeComponent {...this.props} onHypermojiPress={this.onHypermojiPress} ref={this.props.tag}
                                    onHyperlinkPress={this.onHyperlinkPress}
                                   onSendPress={this.onSendPress} onCameraPress={this.onCameraPress}
                                onCanGoBackChanged={this.onCanGoBackChanged} />;
    }

    detatch(tag){

            NativeModules.MakemojiManager.detatch(findNodeHandle(this.refs[this.props.tag]),tag);
    }
    getHandle (){
        return ReactNative.findNodeHandle(this.refs[this.props.ref]);
    };

}
MakemojiTextInput.propTypes = {
    ...View.propTypes,
    //ios+Android

    onSendPress: React.PropTypes.func,
    onCameraPress: React.PropTypes.func,
    //onHypermojiPress: React.PropTypes.func,//Use event emitter listener instead
    cameraVisible:React.PropTypes.bool,//show the camera button

    //ios only
    sendButtonVisible:React.PropTypes.bool,

    textInputTextColor:ColorPropType,//color of input text
    placeholderTextColor:ColorPropType, //color of text hint
    textSolidBackgroundColor:ColorPropType, //color of bg for text input view
    textInputContainerColor:ColorPropType, //color of bg behind input, camera, send
    //barBackgroundColor:ColorPropType,
    navigationBackgroundColor:ColorPropType,//left button bg color
    navigationHighlightColor:ColorPropType,//bg color of selected button
    accessoryBackgroundColor:ColorPropType,
    flashtagCollectionViewBackgroundColor:ColorPropType,//bg during !flashtag search
    emojiViewBackgroundColor:ColorPropType,//bg of emoji bar
    emojiPageBackgroundColor:ColorPropType,//bg of emoji pages
    emojiCollectionBackgroundColor:ColorPropType,//bg of grid of emojis
    categoriesBackgroundColor:ColorPropType,//bg of categories page
    detatchedInputId:React.PropTypes.number,

    //Android only
    cameraDrawable:React.PropTypes.string,//name of asset in drawables folder
    backspaceDrawable:React.PropTypes.string,//name of asset in drawables folder
    buttonContainerDrawable:React.PropTypes.string,//the background drawable name behind the left buttons
    topBarDrawable:React.PropTypes.string, //Background resource of top [camera,text,sendButton] view
    bottomPageDrawable:React.PropTypes.string, //background res of emoji bar and category pages
    phraseBgColor:React.PropTypes.string,//color of phrase category backing, in hex
    headerTextColor:React.PropTypes.string,//color of page header text, hex
    alwaysShowEmojiBar:React.PropTypes.bool,//always shows bar even when kb isn't up
    minSendLength:React.PropTypes.number,//number of characters needed to enable send button
    outsideEditText:React.PropTypes.string,//the tagFinder prop of a MakeMojiEditTextAndroid to use as input target, null if none
};

module.exports = MakemojiTextInput