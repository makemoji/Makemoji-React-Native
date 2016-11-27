// MapView.js
import React from 'react';
import { requireNativeComponent,View } from 'react-native';

class MakemojiTextCelliOS extends React.Component {
    render() {
        return <RCTTextCell html={this.props.html} {...this.props} />;
    }
}

MakemojiTextCelliOS.propTypes = {
    ...View.propTypes,
    html: React.PropTypes.string,
    plaintext:React.PropTypes.string,
};

var RCTTextCell = requireNativeComponent('MESimpleTableViewCell', MakemojiTextCelliOS);

module.exports = MakemojiTextCelliOS;