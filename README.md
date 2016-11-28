# Makemoji-React-Native

A React Native wrapper for the MakeMoji SDK. iOS and Android

## Installation
Copy the folder [MakeMojiRN](MakeMojiRN) for the js files.
Call NativeModules.MakemojiManager.init("YourSdkKey"); when your application will mount.
###Android
Copy the folder [com/makemoji/mojilib](android/app/src/main/java/com/makemoji/mojilib) into your android/app/src/main/java folder.

In your MainApplication.java add the MakeMojiReactPackage to the list of packages.
```java
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(), new MakeMojiReactPackage(MainApplication.this)
      );
    }
  };
```
Add the latest version of the sdk to your app's build.gradle 

[ ![MakeMoji SDK Version](https://api.bintray.com/packages/mm/maven/com.makemoji%3Amakemoji-sdk-android/images/download.svg) ](https://bintray.com/mm/maven/com.makemoji%3Amakemoji-sdk-android/_latestVersion)
``` compile "com.makemoji:makemoji-sdk-android:x.x.xxx" ```

###iOS
Copy the folder [Makemoji](ios/Makemoji) to your xcode project. Right click on your project
and select 'Add Files' and select all the files in the folder.
Add 'pod "Makemoji-SDK"' to your podfile, and run 'pod install'.


## Usage
Add the MakemojiTextInput component to your layout, with props pointing to functions to handle the camera button press and the send button being pressed.
The result object contains an html field and a plaintext field, either of which can be rendered.
Add one to a datasource to render in a list.
```javascript

          <MakemojiTextInput style={styles.moji} onSendPress={this.sendPressed.bind(this)} 
                             sendButtonVisible={true} cameraVisible={true} onCameraPress={this.log}
          />
            sendPressed(sendObject){
              console.log('send pressed', sendObject);
              var htmlMessages = [...this.state.htmlMessages,sendObject.html];
              var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
              this.setState({htmlMessages:htmlMessages,dataSource:ds.cloneWithRows(htmlMessages)});
            }
            /* sample send object
            {
            html:"<p dir="auto" style="margin-bottom:16px;font-family:'.Helvetica Neue Interface';font-size:16px;"><span style="color:#000000;"><img style="vertical-align:text-bottom;width:20px;height:20px;" id="734"src="https://d1tvcfe0bfyi6u.cloudfront.net/emoji/734-large@2x.png" name="Street Fighter" link=""><img style="vertical-align:text-bottom;width:20px;height:20px;" id="9927"src="https://d1tvcfe0bfyi6u.cloudfront.net/emoji/9927-large@2x.png" name="Chill" link=""> watching</p>"
            plaintext:"[Street Fighter.Bq][Chill.2a7] watching"
            }
             */
```

### Displaying Messages
Use either the html prop OR the plaintext prop to render the message. On ios, use the MakemojiTextCelliOS component, and on android, use MakemojiTextAndroid.
```javascript
<MakemojiTextCelliOS style={styles.stretch} html={rowData}/>

<MakemojiTextAndroid style={styles.instructions} html={rowData}/>

```

### Hypermoji
Hypermoji are flashing emojis associated with a url.
 Presses are sent as an event that can be listened for with the event emitter
```javascript
    componentDidMount(){
        var emitter = new NativeEventEmitter(NativeModules.MakemojiManager);
        this.subscription = emitter.addListener(
            'onHypermojiPress',
            (event) => console.log(event.url)
        );
    }
    componentWillUnmount(){
        this.subscription.remove();
    }
 ```

### Emoji Wall
To show the user a full screen modal or activity to choose one emoji, call
```javascript
 NativeModules.MakemojiManager.openWall() 
 ```
 and listen for the result

```javascript
    componentDidMount(){
        var emitter = new NativeEventEmitter(NativeModules.MakemojiManager);
        this.wallSubscription = emitter.addListener(
            'onEmojiWallSelect',
            (event) => console.log(event)
        );
    }
    componentWillUnmount(){
        this.wallSubscription.remove();
    }
    /* Sample output
    {
        "emoji_id" = 935;
        "emoji_type" = "makemoji";
        "image_url" = "http://d1tvcfe0bfyi6u.cloudfront.net/emoji/935-large@2x.png";
        "name" = "Amused";
    }
 */
```
On android, add the wall activity to your manifest first.
```xml
    <activity
        android:name="com.makemoji.mojilib.wall.MojiWallActivity"
        android:label="Emoji Wall Activity">
    </activity>
```
 
### Detatched Input
If you want to use an input target other than MakemojiTextInput on android, things are a bit trickier. A MakemojiEditTextAndroid is required
to ensure keyboard, backspace, and copy paste compatibility. Assign the view a static unique identifier in the finderTag prop.

```javascript
      <MakemojiEditTextAndroid keyboardShouldPersistTaps={false} style={[styles.editText,{fontSize:this.state.textSize}]}
                               finderTag={'topEditText'} ref={'topEditText'} onHtmlGenerated={this.sendPressed.bind(this)}/>
```
Then, after mount, assign MakemojiTextInput the prop outsideEditText with the same finderTag value.
 This will cause the input field, camera button, and send button to be hidden from MakemojiTextInput.
 Set outsideEditText={null} to reverse
this. 
```javascript
this.setState({outsideEditText:'topEditText'})
...
<MakemojiTextInput outsideEditText={this.state.outsideEditText} .../>

```

To get the {html,plaintext} value of the MakemojiEditTextAndroid, call requestHtml(shouldClear,shouldSendAnalytics) on the view. 
The result will be returned asynchronously through the function given to the onHtmlGenerated prop.
If you need to prepopulate a MakemojiEditTextAndroid, use its setText function.
Detatched input on ios coming soon™.

## Customization
MakemojiTextAndroid and MakemojiEditTextAndroid are copy paste extensions of the Text and TextInput components and respond to standard styling
and custmization. Descriptions of the styling available for MakemojiTextInput can be found next to the proptypes of
[MakemojiTextInput.js](MakemojiRN/MakemojiTextInput.js). Any other styling is available on request.
