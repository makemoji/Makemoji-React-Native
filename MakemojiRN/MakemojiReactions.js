import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View, NativeModules } from 'react-native';

var ReactNative = require('ReactNative');
var NativeComponent = requireNativeComponent('RCTMojiReactions', null);
export  default class MakemojiReactions extends React.Component {
    constructor(props) {
        super(props);

    }
    render() {
        return <NativeComponent ref={this.props.contentId} {...this.props}/>;
    }



}
MakemojiReactions.propTypes = {
    ...View.propTypes,

    contentId:React.PropTypes.string,
};

module.exports = MakemojiReactions;