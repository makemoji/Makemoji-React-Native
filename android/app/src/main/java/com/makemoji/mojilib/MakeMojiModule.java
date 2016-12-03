package com.makemoji.mojilib;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.makemoji.mojilib.model.MojiModel;
import com.makemoji.mojilib.model.ReactionsData;
import com.makemoji.mojilib.wall.MojiWallActivity;

import org.json.JSONObject;

/**
 * Created by Makemoji on 9/11/16.
 */
public class MakeMojiModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    Application app;
    ReactApplicationContext context;
    public MakeMojiModule(ReactApplicationContext reactContext, Application app) {
        super(reactContext);
        this.app = app;
        context = reactContext;
        reactContext.addActivityEventListener(this);
    }
    @Override
    public String getName() {
        return "MakemojiManager";
    }
    @ReactMethod
    public void setChannel(String channel) {
        Moji.setChannel(channel);
    }
    @ReactMethod
    public void init(String key){
        Moji.initialize(app,key);
    }

    @ReactMethod
    public void openWall(){
        Intent intent = new Intent(context, MojiWallActivity.class);
        //intent.putExtra(MojiWallActivity.EXTRA_THEME,R.style.MojiWallDefaultStyle_Light); //to theme it
        intent.putExtra(MojiWallActivity.EXTRA_SHOWRECENT,true);//show recently used emojis as a tab
        intent.putExtra(MojiWallActivity.EXTRA_SHOWUNICODE,true);//show unicode emojis as a tab
        context.startActivityForResult(intent,IMojiSelected.REQUEST_MOJI_MODEL,null);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (ReactionsData.onActivityResult(requestCode,resultCode,data)){
            return;
        }
        if (requestCode == IMojiSelected.REQUEST_MOJI_MODEL && resultCode== Activity.RESULT_OK){
            try{
                String json = data.getStringExtra(Moji.EXTRA_JSON);
                MojiModel model = MojiModel.fromJson(new JSONObject(json));
                WritableMap eventData = Arguments.createMap();
                eventData.putInt("emoji_id",model.id);
                if (model.character==null || model.character.isEmpty())eventData.putString("emoji_type","makemoji");
                else    {
                    eventData.putString("emoji_type","native");
                    eventData.putString("unicode_character",model.character);
                }
                eventData.putString("image_url",model.image_url);
                eventData.putString("name",model.name);

                context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onEmojiWallSelect", eventData);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
