package com.makemoji.mojilib;

import android.app.Application;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by Makemoji on 9/11/16.
 */
public class MakeMojiModule extends ReactContextBaseJavaModule {

    Application app;
    public MakeMojiModule(ReactApplicationContext reactContext, Application app) {
        super(reactContext);
        this.app = app;
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

}
