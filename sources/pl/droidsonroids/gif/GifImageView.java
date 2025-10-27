package pl.droidsonroids.gif;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import pl.droidsonroids.gif.GifViewUtils;

/* loaded from: classes9.dex */
public class GifImageView extends ImageView {
    private boolean mFreezesAnimation;

    public GifImageView(Context context) {
        super(context);
    }

    private void postInit(GifViewUtils.GifImageViewAttributes gifImageViewAttributes) {
        this.mFreezesAnimation = gifImageViewAttributes.freezesAnimation;
        int i2 = gifImageViewAttributes.mSourceResId;
        if (i2 > 0) {
            super.setImageResource(i2);
        }
        int i3 = gifImageViewAttributes.mBackgroundResId;
        if (i3 > 0) {
            super.setBackgroundResource(i3);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        gifViewSavedState.restoreState(getDrawable(), 0);
        gifViewSavedState.restoreState(getBackground(), 1);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new GifViewSavedState(super.onSaveInstanceState(), this.mFreezesAnimation ? getDrawable() : null, this.mFreezesAnimation ? getBackground() : null);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        if (GifViewUtils.setResource(this, false, i2)) {
            return;
        }
        super.setBackgroundResource(i2);
    }

    public void setFreezesAnimation(boolean z2) {
        this.mFreezesAnimation = z2;
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i2) {
        if (GifViewUtils.setResource(this, true, i2)) {
            return;
        }
        super.setImageResource(i2);
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        if (GifViewUtils.setGifImageUri(this, uri)) {
            return;
        }
        super.setImageURI(uri);
    }

    public GifImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        postInit(GifViewUtils.initImageView(this, attributeSet, 0, 0));
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        postInit(GifViewUtils.initImageView(this, attributeSet, i2, 0));
    }

    @RequiresApi(21)
    public GifImageView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        postInit(GifViewUtils.initImageView(this, attributeSet, i2, i3));
    }
}
