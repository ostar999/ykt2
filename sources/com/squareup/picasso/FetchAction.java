package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso;

/* loaded from: classes6.dex */
class FetchAction extends Action<Object> {
    private final Object target;

    public FetchAction(Picasso picasso, Request request, boolean z2, String str, Object obj) {
        super(picasso, null, request, z2, false, 0, null, str, obj);
        this.target = new Object();
    }

    @Override // com.squareup.picasso.Action
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
    }

    @Override // com.squareup.picasso.Action
    public void error() {
    }

    @Override // com.squareup.picasso.Action
    public Object getTarget() {
        return this.target;
    }
}
