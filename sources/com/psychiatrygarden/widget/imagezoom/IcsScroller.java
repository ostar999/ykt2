package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;

/* loaded from: classes6.dex */
public class IcsScroller extends GingerScroller {
    public IcsScroller(Context context) {
        super(context);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.GingerScroller, com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }
}
