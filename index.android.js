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
  NativeModules

} from 'react-native';
var MakemojiTextInput = require('./MakemojiRN/MakemojiTextInput');// MakemojiTextInput from './MakemojiRN/MakemojiTextInput'
var MakemojiReactions = require('./MakemojiRN/MakemojiReactions');
import MakemojiEditTextAndroid from './MakemojiRN/MakemojiEditTextAndroid'
import MakemojiTextAndroid from './MakemojiRN/MakemojiTextAndroid'

const NativeEventEmitter = require('NativeEventEmitter');
const showDetatchControls = true; // enable to show example of detached input.
class MakemojiReactNative extends Component {

    constructor(props){
        super(props);

        var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {htmlMessages:[],
    dataSource:ds.cloneWithRows([]),
    outsideEditText:' ',
    showReactions:false,
    textSize:17.0};
        BackAndroid.addEventListener('hardwareBackPress', () => {
            if (this.refs.mojiInput.canGoBack()){
                this.refs.mojiInput.onBackPressed();
                return true;//back handled
            }
            return false;
        });

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
    componentWillUnmount(){
        this.subscription.remove();
        this.wallSubscription.remove();
    }
    componentWillMount(){
        NativeModules.MakemojiManager.init("940ced93abf2ca4175a4a865b38f1009d8848a58");
    }
  render() {
    return (
      <View keyboardShouldPersistTaps={false} style={styles.container}>
          {showDetatchControls? <View>
          <MakemojiEditTextAndroid keyboardShouldPersistTaps={false} style={[styles.editText,{fontSize:this.state.textSize}]}
                                   finderTag={'topEditText'} ref={'topEditText'} onHtmlGenerated={this.sendPressed.bind(this)}/>

          <TouchableHighlight onPress={this.genHtml.bind(this)}>
             <Text style={styles.welcome} selectable={true}>
                  Grab Text from top edit text.
             </Text>
              </TouchableHighlight>

          <TouchableHighlight onPress={() =>this.setState({outsideEditText:'topEditText'})}>
            <Text style={styles.instructions}>
              Attatch Edit Text
            </Text>
          </TouchableHighlight>
          <TouchableHighlight onPress={() => this.setState({outsideEditText:null})}>
              <Text style={styles.instructions}>
                  Detatch Edit Text
              </Text>

          </TouchableHighlight>
              <View style={{flexDirection:'row',justifyContent:'center'}}>
                  <TouchableHighlight onPress={() => NativeModules.MakemojiManager.openWall()}>
                      <Text style={[{marginTop:5},styles.instructions]}>
                          Wall
                      </Text>
                  </TouchableHighlight>
                  <TouchableHighlight onPress={() => this.setState({showReactions:!this.state.showReactions})}>
                      <Text style={[{marginTop:5,marginLeft:30},styles.instructions]}>
                          {this.state.showReactions?'-Reactions':'+Reactions'}
                      </Text>
                  </TouchableHighlight>
              </View>
              </View> :null}
          <ListView style={{flex:1,alignSelf:'stretch'}}
                    dataSource={this.state.dataSource}
                    enableEmptySections={true}
                    renderRow={(rowData) => <View style={{flexDirection:'column'}}>
                        <MakemojiTextAndroid style={styles.instructions} html={rowData}/>
                       {this.state.showReactions? <MakemojiReactions style={styles.reaction} contentId={rowData}/> :null}
                    </View>}
          />
        <MakemojiTextInput outsideEditText={this.state.outsideEditText} ref={'mojiInput'} style={styles.moji} minSendLength={0} alwaysShowEmojiBar={false}
                           onSendPress={this.sendPressed.bind(this)} onCameraPress={this.log}/>
      </View>
    );
  }
  genHtml(){
      this.refs.topEditText.requestHtml(true,true);//args:should clear input;should send text to analytics
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
    reaction:{
        height:30,
        alignSelf: 'stretch',
    },
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
  }
});

AppRegistry.registerComponent('MakemojiReactNative', () => MakemojiReactNative);
