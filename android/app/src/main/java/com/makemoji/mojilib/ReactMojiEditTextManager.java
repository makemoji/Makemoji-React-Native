package com.makemoji.mojilib;

import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
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
import com.facebook.react.views.textinput.ReactEditText;
import com.facebook.react.views.textinput.ReactTextInputManager;

import java.util.Map;

/**
 * Created by s_baa on 8/26/2016.
 */
public class ReactMojiEditTextManager extends ReactTextInputManager {
    EventDispatcher eventDispatcher;
    @Override
    public String getName() {
        return "MakeMojiAndroidTextInput";
    }

    @Override
    public ReactEditText createViewInstance(ThemedReactContext context) {
        final ReactEditText editText = new ReactMojiEditText(context);
        int inputType = editText.getInputType();
        editText.setInputType(inputType & (~InputType.TYPE_TEXT_FLAG_MULTI_LINE));
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (int) Math.ceil(PixelUtil.toPixelFromSP(ViewDefaults.FONT_SIZE_SP)));
        editText.setTag("defaultTag");
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.requestFocusFromJS();
            }
        });
        return editText;
    }

    @ReactProp(name = "finderTag")
    public void setFinderTag(ReactEditText view, @Nullable String tag) {
        view.setTag(tag);

    }
    @ReactProp(name = ViewProps.FONT_SIZE, defaultFloat = ViewDefaults.FONT_SIZE_SP)
    public void setFontSize(ReactEditText view, float fontSize) {
        String html = Moji.toHtml(view.getText());
        view.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (int) Math.ceil(PixelUtil.toPixelFromSP(fontSize)));
        Moji.setText(html,view,true);
    }
    @Override
    public void updateExtraData(ReactEditText view, Object extraData) {
    if (extraData instanceof float[]){
        super.updateExtraData(view,extraData);
        return;
        }
    }

    @Override
    public Map<String,Integer> getCommandsMap() {
        return MapBuilder.of(
                "requestHtml",
                404);
    }
    @Override
    public void receiveCommand(
            ReactEditText reactEditText,
            int commandId,
            @javax.annotation.Nullable ReadableArray args) {
        switch (commandId) {
            case 404:
                if (args.getBoolean(1)) MojiInputLayout.onSaveInputToRecentAndsBackend(reactEditText.getText());
                eventDispatcher.dispatchEvent(new HtmlEvent(reactEditText.getId(),
                        Moji.toHtml(reactEditText.getText())));
                if (args.getBoolean(0))Moji.setText("",reactEditText,true);
                break;
            case 405:
                Moji.setText(args.getString(0),reactEditText,true);
            default:
                super.receiveCommand(reactEditText,commandId,args);
        }
    }
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                HtmlEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onHtmlGenerated")

        );
    }
    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ReactEditText view) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }
    public static class HtmlEvent extends Event<HtmlEvent> {
        String html;
        final static String EVENT_NAME = "onHtmlGenerated";
        public HtmlEvent(int viewTag,String html){
            super(viewTag);
            this.html = html;
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            eventData.putString("html", html);
            eventData.putString("plainText", Moji.htmlToPlainText(html));
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }

}
