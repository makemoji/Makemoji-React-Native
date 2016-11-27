package csslayout;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;

/**
 * Created by s_baa on 8/22/2016.
 */
public class MyShadowNode extends LayoutShadowNode {
    @Override
    public void markUpdated(){
        super.markUpdated();
        if (hasNewLayout()) markLayoutSeen();
        dirty();
    }
    @Override
    public boolean isDirty(){
        return true;
    }


}
