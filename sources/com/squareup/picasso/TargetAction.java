package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso;

/* loaded from: classes6.dex */
final class TargetAction extends Action<Target> {
    public TargetAction(Picasso picasso, Target target, Request request, boolean z2, int i2, Drawable drawable, String str, Object obj) {
        super(picasso, target, request, z2, false, i2, drawable, str, obj);
    }

    @Override // com.squareup.picasso.Action
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        Target target = getTarget();
        if (target != null) {
            target.onBitmapLoaded(bitmap, loadedFrom);
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    @Override // com.squareup.picasso.Action
    public void error() {
        Target target = getTarget();
        if (target != null) {
            if (this.errorResId != 0) {
                target.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
            } else {
                target.onBitmapFailed(this.errorDrawable);
            }
        }
    }
}
