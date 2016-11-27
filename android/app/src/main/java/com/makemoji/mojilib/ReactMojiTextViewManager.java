package com.makemoji.mojilib;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
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
import com.facebook.react.views.textinput.ReactEditText;

import java.util.Map;

import csslayout.MyReactTextShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiTextViewManager extends ReactTextViewManager {
    EventDispatcher eventDispatcher;
    @Override
    public String getName() {
        return "ReactMojiText";
    }
    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ReactTextView view) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }

    @Override
    public ReactTextView createViewInstance(final ThemedReactContext reactContext) {
      final ReactTextView rtv =super.createViewInstance(reactContext);
        /*rtv.setTag(com.makemojireactnative.R.id._makemoji_hypermoji_listener_tag_id, new HyperMojiListener() {
            @Override
            public void onClick(String url) {
                eventDispatcher.dispatchEvent(new HyperMojiEvent (rtv.getId(),url));
            }
        });*/
        return rtv;
    }
    @ReactProp(name = "html")
    public void setHtml(ReactTextView view, @Nullable String html) {
        if (html!=null && !html.equals("__VOID__")) {//prop can't be empty
            view.setTag(R.id._makemoji_rn_html_tag_id,html);
            Moji.setText(html, view, true);
        }
    }
    @ReactProp(name = "plaintext")
    public void setPlainText(ReactTextView view, @Nullable String plaintext) {
        if (plaintext!=null && !plaintext.equals("__VOID__"))
            Moji.setText(Moji.plainTextToSpanned(plaintext),view);
    }
    @ReactProp(name = ViewProps.FONT_SIZE, defaultFloat = ViewDefaults.FONT_SIZE_SP)
    public void setFontSize(ReactTextView view, float fontSize) {
        view.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (int) Math.ceil(PixelUtil.toPixelFromSP(fontSize)));
        if (view.getTag(R.id._makemoji_rn_html_tag_id)!=null)
            Moji.setText((String)view.getTag(R.id._makemoji_rn_html_tag_id),view,true);
    }

    @Override
    public ReactTextShadowNode createShadowNodeInstance() {
       return new MyReactTextShadowNode(false);

    }

    @Override
    public void updateExtraData(ReactTextView view, Object extraData) {

    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                HyperMojiEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onHyperMojiPress")
                );
    }

    public static class HyperMojiEvent extends Event<ReactMojiInputLayoutManager.HyperMojiEvent>{
        String url;
        final static String EVENT_NAME = "onHyperMojiPress";
        public HyperMojiEvent(int viewTag,String url){
            super(viewTag);
            this.url = url;
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            eventData.putString("url", url);
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
}
