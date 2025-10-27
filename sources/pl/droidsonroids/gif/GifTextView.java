package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import pl.droidsonroids.gif.GifViewUtils;

/* loaded from: classes9.dex */
public class GifTextView extends TextView {
    private GifViewUtils.GifViewAttributes viewAttributes;

    public GifTextView(Context context) {
        super(context);
    }

    private void applyGifViewAttributes() {
        if (this.viewAttributes.mLoopCount < 0) {
            return;
        }
        for (Drawable drawable : getCompoundDrawables()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable);
        }
        for (Drawable drawable2 : getCompoundDrawablesRelative()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable2);
        }
        GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, getBackground());
    }

    private Drawable getGifOrDefaultDrawable(int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            return null;
        }
        Resources resources = getResources();
        String resourceTypeName = resources.getResourceTypeName(i2);
        if (!isInEditMode() && GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
            try {
                return new GifDrawable(resources, i2);
            } catch (Resources.NotFoundException | IOException unused) {
            }
        }
        return resources.getDrawable(i2, getContext().getTheme());
    }

    private void init(AttributeSet attributeSet, int i2, int i3) throws Resources.NotFoundException {
        if (attributeSet != null) {
            Drawable gifOrDefaultDrawable = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable gifOrDefaultDrawable2 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable gifOrDefaultDrawable3 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable gifOrDefaultDrawable4 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable gifOrDefaultDrawable5 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable gifOrDefaultDrawable6 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            if (getLayoutDirection() == 0) {
                if (gifOrDefaultDrawable5 != null) {
                    gifOrDefaultDrawable = gifOrDefaultDrawable5;
                }
                if (gifOrDefaultDrawable6 == null) {
                    gifOrDefaultDrawable6 = gifOrDefaultDrawable3;
                }
            } else {
                if (gifOrDefaultDrawable5 != null) {
                    gifOrDefaultDrawable3 = gifOrDefaultDrawable5;
                }
                if (gifOrDefaultDrawable6 == null) {
                    gifOrDefaultDrawable6 = gifOrDefaultDrawable;
                }
                gifOrDefaultDrawable = gifOrDefaultDrawable3;
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(gifOrDefaultDrawable, gifOrDefaultDrawable2, gifOrDefaultDrawable6, gifOrDefaultDrawable4);
            setBackground(getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", 0)));
            this.viewAttributes = new GifViewUtils.GifViewAttributes(this, attributeSet, i2, i3);
            applyGifViewAttributes();
        }
        this.viewAttributes = new GifViewUtils.GifViewAttributes();
    }

    private void setCompoundDrawablesVisible(boolean z2) {
        setDrawablesVisible(getCompoundDrawables(), z2);
        setDrawablesVisible(getCompoundDrawablesRelative(), z2);
    }

    private static void setDrawablesVisible(Drawable[] drawableArr, boolean z2) {
        for (Drawable drawable : drawableArr) {
            if (drawable != null) {
                drawable.setVisible(z2, false);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setCompoundDrawablesVisible(true);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setCompoundDrawablesVisible(false);
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        Drawable[] compoundDrawables = getCompoundDrawables();
        gifViewSavedState.restoreState(compoundDrawables[0], 0);
        gifViewSavedState.restoreState(compoundDrawables[1], 1);
        gifViewSavedState.restoreState(compoundDrawables[2], 2);
        gifViewSavedState.restoreState(compoundDrawables[3], 3);
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        gifViewSavedState.restoreState(compoundDrawablesRelative[0], 4);
        gifViewSavedState.restoreState(compoundDrawablesRelative[2], 5);
        gifViewSavedState.restoreState(getBackground(), 6);
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        Drawable[] drawableArr = new Drawable[7];
        if (this.viewAttributes.freezesAnimation) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            System.arraycopy(compoundDrawables, 0, drawableArr, 0, compoundDrawables.length);
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            drawableArr[4] = compoundDrawablesRelative[0];
            drawableArr[5] = compoundDrawablesRelative[2];
            drawableArr[6] = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), drawableArr);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        setBackground(getGifOrDefaultDrawable(i2));
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4), getGifOrDefaultDrawable(i5));
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        setCompoundDrawablesWithIntrinsicBounds(getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4), getGifOrDefaultDrawable(i5));
    }

    public void setFreezesAnimation(boolean z2) {
        this.viewAttributes.freezesAnimation = z2;
    }

    public GifTextView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        init(attributeSet, 0, 0);
    }

    public GifTextView(Context context, AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i2);
        init(attributeSet, i2, 0);
    }

    @RequiresApi(21)
    public GifTextView(Context context, AttributeSet attributeSet, int i2, int i3) throws Resources.NotFoundException {
        super(context, attributeSet, i2, i3);
        init(attributeSet, i2, i3);
    }
}
