package com.makemoji.mojilib;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.csslayout.CSSNode;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.textinput.ReactEditText;
import com.makemoji.mojilib.HyperMojiListener;
import com.makemoji.mojilib.Moji;
import com.makemoji.mojilib.MojiInputLayout;
import com.makemoji.mojilib.MyMojiInputLayout;

import java.lang.reflect.Method;
import java.util.Map;

import csslayout.MyShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiInputLayoutManager extends ViewGroupManager<MyMojiInputLayout> {
    EventDispatcher eventDispatcher;
    Method markNewLayout, getShadowNode;

    public ReactMojiInputLayoutManager(){
        super();
        if (markNewLayout == null) {
            try {
                markNewLayout = CSSNode.class.getDeclaredMethod("markHasNewLayout");
                markNewLayout.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
        if (getShadowNode==null){
            getShadowNode = UIImplementation.class.getDeclaredMethod("resolveShadowNode",int.class);
            getShadowNode.setAccessible(true);
        }
        } catch (Exception e) {
        e.printStackTrace();
    }

    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }
    @Override
    public String getName() {
        return "RCTMojiInputLayout";
    }

    @ReactProp(name = "cameraDrawable")
    public void setCameraDrawable(MyMojiInputLayout view, @Nullable String drawableName) {
        if (drawableName!=null)
            view.cameraImageButton.setImageResource(
                            view.getResources().getIdentifier(drawableName,"drawable",view.getContext().getPackageName()));
    }
    @ReactProp(name = "backspaceDrawable")
    public void setBackspaceDrawable(MyMojiInputLayout view, @Nullable String drawableName) {
        if (drawableName!=null)
            view.backSpaceDrawableRes = view.getResources().getIdentifier(drawableName,"drawable",view.getContext().getPackageName());
    }

    @ReactProp(name = "cameraVisible", defaultBoolean = true)
    public void setCameraVisibility(MyMojiInputLayout view,  boolean visible) {
        view.setCameraVisibility(visible);
    }

    @ReactProp(name = "buttonContainerDrawable")
    public void setButtonContainerDrawable(MyMojiInputLayout view, @Nullable String drawableName) {
        if (drawableName!=null)
            view.findViewById(R.id._mm_left_buttons).setBackgroundResource(
                    view.getResources().getIdentifier(drawableName,"drawable",view.getContext().getPackageName()));
    }
    @ReactProp(name = "topBarDrawable")
    public void setTopBarDrawable(MyMojiInputLayout view, @Nullable String drawableName) {
        if (drawableName!=null)
            view.findViewById(R.id._mm_horizontal_ll).setBackgroundResource(
                    view.getResources().getIdentifier(drawableName,"drawable",view.getContext().getPackageName()));
    }
    @ReactProp(name = "bottomPageDrawable")
    public void setBottomPageDrawable(MyMojiInputLayout view, @Nullable String drawableName) {
        if (drawableName!=null) {
            view.rv.setBackgroundResource(
                    view.getResources().getIdentifier(drawableName, "drawable", view.getContext().getPackageName()));
            view.getPageFrame().setBackgroundResource(
                    view.getResources().getIdentifier(drawableName, "drawable", view.getContext().getPackageName()));
        }
    }

    @ReactProp(name = "phraseBgColor")
    public void setPhraseBgColor(MyMojiInputLayout view, @Nullable String color) {
        if (color!=null){
            view.phraseBgColor = Color.parseColor(color);
        }
    }
    @ReactProp(name = "headerTextColor")
    public void setHeaderTextColor(MyMojiInputLayout view, @Nullable String color) {
        if (color!=null){
            view.headerTextColor = Color.parseColor(color);
        }
    }

    @ReactProp(name = "alwaysShowEmojiBar", defaultBoolean = false)
    public void setAlwaysShowEmojiBar(MyMojiInputLayout view,  boolean show) {
        view.tryAlwaysShowBar=show;
        if (show)view.alwaysShowBar = show;
    }
    @ReactProp(name = "minSendLength", defaultInt = 0)
    public void setMinSendLength(MyMojiInputLayout view,  int length) {
        view.minimumSendLength = length;
        if (length==0) view.sendLayout.setEnabled(view.editText.getText().length()>=length);
    }
    @ReactProp(name ="outsideEditText")
    public void setOutsideEditText(final MyMojiInputLayout view, @Nullable String tag){
        if (tag==null || tag.isEmpty()){
            view.detachMojiEditText();
            view.requestRnUpdate();
            return;
        }
        ViewParent parent = view.getParent();
        while (parent!=null && parent instanceof View && parent.getParent() !=null && parent.getParent() instanceof View){
            parent = parent.getParent();
        }
        if (parent!=null) {
            EditText et = (EditText) ((View) parent).findViewWithTag(tag);
            if (et!=null) {
                view.attatchEditText(et);
                view.setTag(R.id._makemoji_request_layout_id,true);
            }
            view.requestRnUpdate();
        }

    }
    @ReactProp(name ="channel")
    public void setChannel(final MyMojiInputLayout view, @Nullable String channel){
        Moji.setChannel(channel);
    }
    @Override
    public void receiveCommand(
            MyMojiInputLayout mojiInputLayout,
            int commandId,
            @javax.annotation.Nullable ReadableArray args) {
        Log.d(getName(),"command "+commandId);
        switch (commandId){
            case 85:
                mojiInputLayout.onBackPressed();
                mojiInputLayout.requestRnUpdate();
                break;
            default:
                super.receiveCommand(mojiInputLayout,commandId,args);
        }
    }

    @Override
    protected MyMojiInputLayout createViewInstance(final ThemedReactContext reactContext) {

        final MyMojiInputLayout mojiInputLayout = new MyMojiInputLayout(reactContext);

        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
        final UIImplementation uiImplementation = uiManager.getUIImplementation();
        final MojiEditText et = (MojiEditText) mojiInputLayout.findViewById(R.id._mm_edit_text);
        OneGridPage.RNDELAY = 100;
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mojiInputLayout.showKeyboard();
            }
        });
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)  et.clearFocus();
            }
        });
        mojiInputLayout.setSendLayoutClickListener(new MojiInputLayout.SendClickListener() {
            @Override
            public boolean onClick(final String html, Spanned spanned) {
                eventDispatcher.dispatchEvent(new SendEvent(mojiInputLayout.getId(),html));
                return true;
            }
        });
       /* mojiInputLayout.setHyperMojiClickListener(new HyperMojiListener() {
            @Override
            public void onClick(String url) {
                eventDispatcher.dispatchEvent(new HyperMojiEvent (mojiInputLayout.getId(),url));
            }
            });*/
        mojiInputLayout.setCameraButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDispatcher.dispatchEvent(new CameraEvent(mojiInputLayout.getId()));
            }
            });

        mojiInputLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mojiInputLayout.requestRnUpdate();//for new lines
            }
        });
        mojiInputLayout.setRnUpdateListener(new MojiInputLayout.RNUpdateListener() {
            MyShadowNode node;
            @Override
            public void needsUpdate() {
                mojiInputLayout.requestLayout();


                Runnable r = new Runnable() {
                    @Override
                    public void run() {

                        if (node ==null){
                            try {
                                node = (MyShadowNode) getShadowNode.invoke(uiImplementation, mojiInputLayout.getId());
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                            if (node != null) {
                                //
                                // Log.d(getName(),""+mojiInputLayout.horizontalLayout.getHeight());
                                node.setWidth(PixelUtil.toDIPFromPixel(mojiInputLayout.getWidth()));
                                int height = (mojiInputLayout.horizontalLayout.getVisibility()==View.VISIBLE? mojiInputLayout.horizontalLayout.getHeight():0)
                                        +(mojiInputLayout.topScroller.getVisibility()==View.VISIBLE?
                                        (int)PixelUtil.toPixelFromDIP(45):0) +
                                     //    mojiInputLayout.topScroller.getMeasuredHeight() : 0) + //height is randomly 0 because react native
                                        (mojiInputLayout.pages.size()>0 ?
                                                (int)  PixelUtil.toPixelFromDIP(250):0);
                                                //mojiInputLayout.getPageFrame().getHeight():0);
                                node.setHeight(PixelUtil.toDIPFromPixel(height));
                               // Log.d(getName(),mojiInputLayout.getWidth()+ " " + mojiInputLayout.getHeight() + " " + mojiInputLayout.getPageFrame().getHeight());
                                if (node.hasNewLayout()) node.markLayoutSeen();
                                ReactShadowNode parent = node.getParent();
                                while (parent != null) {
                                    if (parent.hasNewLayout()) {
                                        try {
                                            markNewLayout.invoke(parent,mojiInputLayout.getId());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        parent.markLayoutSeen();
                                    }
                                    parent = parent.getParent();
                                }
                                eventDispatcher.dispatchEvent(new CanGoBackEvent(mojiInputLayout.getId(),mojiInputLayout.canHandleBack()));
                                node.markUpdated();
                            }
                    }

                };
                reactContext.runOnNativeModulesQueueThread(r);
            }
        });

        return mojiInputLayout;
    }
    public LayoutShadowNode createShadowNodeInstance() {
        return new MyShadowNode();
    }
    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, MyMojiInputLayout view) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                SendEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onSendPress"),
                HyperMojiEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onHyperMojiPress"),
                CameraEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onCameraPress"),
                CanGoBackEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onCanGoBackChanged")

                );
    }
    public static class SendEvent extends Event<SendEvent>{
        String html;
        final static String EVENT_NAME = "onSendPress";
        public SendEvent(int viewTag,String html){
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
            eventData.putString("plaintext", Moji.htmlToPlainText(html));
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
    public static class HyperMojiEvent extends Event<HyperMojiEvent>{
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
    public static class CameraEvent extends Event<CameraEvent>{
        final static String EVENT_NAME = "onCameraPress";
        public CameraEvent(int viewTag){
            super(viewTag);
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
    public static class CanGoBackEvent extends Event<CanGoBackEvent>{
        final static String EVENT_NAME = "onCanGoBackChanged";
        boolean canGoBack;
        public CanGoBackEvent(int viewTag,boolean canGoBack){
            super(viewTag);
            this.canGoBack = canGoBack;
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            eventData.putBoolean("canGoBack",canGoBack);
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
}
