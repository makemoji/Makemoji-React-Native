/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    ViewPagerAndroid,
    TouchableHighlight,
    ListView,
    TextInput,
    BackAndroid,
    NativeAppEventEmitter,
    NativeModules,
    processColor,
findNodeHandle

} from 'react-native';
var MakemojiTextInput = require('./MakemojiRN/MakemojiTextInput');

const NativeEventEmitter = require('NativeEventEmitter');
import MakemojiTextCelliOS from './MakemojiRN/MakemojiTextCelliOS'

var ReactNative = require('ReactNative');
const showDetatchControls = true; // not avaible on ios currently
class MakemojiReactNative extends Component {

  constructor(props){
    super(props);

    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    var subscription;
    this.state = {htmlMessages:[],
      dataSource:ds.cloneWithRows([]),
      detatchedInputId:null,
      textSize:17.0};

  }
  componentDidMount(){
    var emitter = new NativeEventEmitter(NativeModules.MakemojiManager);
    this.subscription = emitter.addListener(
        'onHypermojiPress',
        (event) => console.log(event.url)
    );
      this.wallSubscription = emitter.addListener(
          'onEmojiWallSelect',
          (event) => console.log(event)
      );
  }
  componentWillMount(){
    NativeModules.MakemojiManager.init("bb0b5cf8d6a9e73fb2800202d204a15ef5a578d3");//ios only
  }
  componentWillUnmount(){
      this.subscription.remove();
      this.wallSubscription.remove();
  }
  render() {
    return (
        <View keyboardShouldPersistTaps={false} style={styles.container}>

          {showDetatchControls? <View>
            <TouchableHighlight onPress={() => NativeModules.MakemojiManager.openWall()}>
              <Text style={[{marginTop:20},styles.instructions]}>
                Wall
              </Text>
            </TouchableHighlight>
          </View> :null}
          <ListView style={{flex:1,alignSelf:'stretch',marginTop:50}}
                    dataSource={this.state.dataSource}
                    enableEmptySections={true}
                    renderRow={(rowData) => <MakemojiTextCelliOS style={styles.stretch} html={rowData}/>}
          />
          <MakemojiTextInput detatchedInputId={this.state.detatchedInputId} tag={'mojiInput'} ref={'mojiInput'} style={styles.moji}
                             onSendPress={this.sendPressed.bind(this)} sendButtonVisible={true} cameraVisible={true} onCameraPress={this.log}
          />
        </View>
    );
  }
  genHtml(){
    //this.refs.topEditText.requestHtml(true,true);//args:should clear input;should send text to analytics
  }
  sendPressed(sendObject){
    console.log('send pressed', sendObject);
    var htmlMessages = [...this.state.htmlMessages,sendObject.html];
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.setState({htmlMessages:htmlMessages,dataSource:ds.cloneWithRows(htmlMessages)});
    //this.refs.topEditText.setText(sendObject.plaintext);
  }
  log(event){
    console.log('',event);
  }

}

const styles = StyleSheet.create({
  editText:{
    height:50,
    alignSelf: 'stretch',
  },
  container: {
    flex: 1,
    flexDirection:'column',
    justifyContent: 'flex-end',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
    fontSize:18,
    height:30
  },
  moji:{

    height:100,
    justifyContent: 'flex-end',
    alignSelf: 'stretch'
  },
  stretch:{
    alignSelf: 'stretch',
    height:30
  }
});

AppRegistry.registerComponent('MakemojiReactNative', () => MakemojiReactNative);
