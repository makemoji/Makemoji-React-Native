package com.makemoji.mojilib;

import android.app.Application;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.makemoji.mojilib.Moji;
import com.makemoji.mojilib.ReactMojiEditTextManager;
import com.makemoji.mojilib.ReactMojiInputLayoutManager;
import com.makemoji.mojilib.ReactMojiTextViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by s_baa on 8/6/2016.
 */
public class MakeMojiReactPackage implements ReactPackage {
    Application app;
    public MakeMojiReactPackage(Application application){
        super();
        app = application;
    }
    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {


        return Arrays.<ViewManager>asList(
                new ReactMojiInputLayoutManager(), new ReactMojiTextViewManager(), new ReactMojiEditTextManager(),
                new ReactMojiReactionsViewManager()
        );
    }

    @Override
    public List<NativeModule> createNativeModules(
            final ReactApplicationContext reactContext) {
        Moji.setDefaultHyperMojiListener(new HyperMojiListener() {
            @Override
            public void onClick(String s) {
                final DeviceEventManagerModule.RCTDeviceEventEmitter emitter = reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
                WritableMap map = Arguments.createMap();
                map.putString("url",s);
                emitter.emit("onHypermojiPress",map);
            }
        });
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new MakeMojiModule(reactContext,app));


        return modules;
    }
}