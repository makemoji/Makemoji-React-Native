package com.makemoji.mojilib;

import android.support.annotation.Nullable;
import android.util.TypedValue;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewDefaults;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.ReactTextViewManager;
import com.makemoji.mojilib.model.ReactionsData;

import java.util.Map;

import csslayout.MyReactTextShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiReactionsViewManager extends SimpleViewManager<ReactionsLayout> {
    @Override
    public String getName() {
        return "RCTMojiReactions";
    }

    @Override
    public ReactionsLayout createViewInstance(final ThemedReactContext reactContext) {
      final ReactionsLayout reactionsLayout =new MyReactionsLayout(reactContext.getCurrentActivity());
        return reactionsLayout;
    }
    @ReactProp(name = "contentId")
    public void setContentId(ReactionsLayout view, @Nullable String id) {
       if (id!=null) view.setReactionsData(new ReactionsData(id));
    }

}
