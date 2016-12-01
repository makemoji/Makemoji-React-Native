package com.makemoji.mojilib;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Makemoji on 11/28/16.
 */
public class MyReactionsLayout extends ReactionsLayout {

    public MyReactionsLayout(Context context) {
        super(context);
    }

    public MyReactionsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyReactionsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyReactionsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void requestLayout() {
        super.requestLayout();

        // The toolbar relies on a measure + layout pass happening after it calls requestLayout().
        // Without this, certain calls (e.g. setLogo) only take effect after a second invalidation.
        post(mLayoutRunnable);
    }
    private final Runnable mLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };
}
