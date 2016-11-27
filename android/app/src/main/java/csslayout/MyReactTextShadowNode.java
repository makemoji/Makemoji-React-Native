package csslayout;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.views.text.ReactTextShadowNode;

/**
 * Created by s_baa on 8/22/2016.
 */
public class MyReactTextShadowNode extends ReactTextShadowNode {

    public MyReactTextShadowNode(boolean isVirtual) {
        super(isVirtual);
    }
    public float getFontSize(){
        return mFontSize;
    }
}
