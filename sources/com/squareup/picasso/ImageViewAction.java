package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/* loaded from: classes6.dex */
class ImageViewAction extends Action<ImageView> {
    Callback callback;

    public ImageViewAction(Picasso picasso, ImageView imageView, Request request, boolean z2, boolean z3, int i2, Drawable drawable, String str, Object obj, Callback callback) {
        super(picasso, imageView, request, z2, z3, i2, drawable, str, obj);
        this.callback = callback;
    }

    @Override // com.squareup.picasso.Action
    public void cancel() {
        super.cancel();
        if (this.callback != null) {
            this.callback = null;
        }
    }

    @Override // com.squareup.picasso.Action
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        ImageView imageView = (ImageView) this.target.get();
        if (imageView == null) {
            return;
        }
        Picasso picasso = this.picasso;
        PicassoDrawable.setBitmap(imageView, picasso.context, bitmap, loadedFrom, this.noFade, picasso.indicatorsEnabled);
        Callback callback = this.callback;
        if (callback != null) {
            callback.onSuccess();
        }
    }

    @Override // com.squareup.picasso.Action
    public void error() {
        ImageView imageView = (ImageView) this.target.get();
        if (imageView == null) {
            return;
        }
        int i2 = this.errorResId;
        if (i2 != 0) {
            imageView.setImageResource(i2);
        } else {
            Drawable drawable = this.errorDrawable;
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        }
        Callback callback = this.callback;
        if (callback != null) {
            callback.onError();
        }
    }
}
