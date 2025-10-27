package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes9.dex */
final class GifViewUtils {
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final List<String> SUPPORTED_RESOURCE_TYPE_NAMES = Arrays.asList("raw", "drawable", "mipmap");

    private GifViewUtils() {
    }

    public static void applyLoopCount(int i2, Drawable drawable) {
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).setLoopCount(i2);
        }
    }

    public static float getDensityScale(@NonNull Resources resources, @DrawableRes @RawRes int i2) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(i2, typedValue, true);
        int i3 = typedValue.density;
        if (i3 == 0) {
            i3 = 160;
        } else if (i3 == 65535) {
            i3 = 0;
        }
        int i4 = resources.getDisplayMetrics().densityDpi;
        if (i3 <= 0 || i4 <= 0) {
            return 1.0f;
        }
        return i4 / i3;
    }

    public static GifImageViewAttributes initImageView(ImageView imageView, AttributeSet attributeSet, int i2, int i3) {
        if (attributeSet == null || imageView.isInEditMode()) {
            return new GifImageViewAttributes();
        }
        GifImageViewAttributes gifImageViewAttributes = new GifImageViewAttributes(imageView, attributeSet, i2, i3);
        int i4 = gifImageViewAttributes.mLoopCount;
        if (i4 >= 0) {
            applyLoopCount(i4, imageView.getDrawable());
            applyLoopCount(i4, imageView.getBackground());
        }
        return gifImageViewAttributes;
    }

    public static boolean setGifImageUri(ImageView imageView, Uri uri) {
        if (uri == null) {
            return false;
        }
        try {
            imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean setResource(ImageView imageView, boolean z2, int i2) throws Resources.NotFoundException {
        Resources resources = imageView.getResources();
        if (resources != null) {
            try {
                if (!SUPPORTED_RESOURCE_TYPE_NAMES.contains(resources.getResourceTypeName(i2))) {
                    return false;
                }
                GifDrawable gifDrawable = new GifDrawable(resources, i2);
                if (z2) {
                    imageView.setImageDrawable(gifDrawable);
                    return true;
                }
                imageView.setBackground(gifDrawable);
                return true;
            } catch (Resources.NotFoundException | IOException unused) {
            }
        }
        return false;
    }

    public static class GifImageViewAttributes extends GifViewAttributes {
        final int mBackgroundResId;
        final int mSourceResId;

        public GifImageViewAttributes(ImageView imageView, AttributeSet attributeSet, int i2, int i3) {
            super(imageView, attributeSet, i2, i3);
            this.mSourceResId = getResourceId(imageView, attributeSet, true);
            this.mBackgroundResId = getResourceId(imageView, attributeSet, false);
        }

        private static int getResourceId(ImageView imageView, AttributeSet attributeSet, boolean z2) {
            int attributeResourceValue = attributeSet.getAttributeResourceValue(GifViewUtils.ANDROID_NS, z2 ? "src" : "background", 0);
            if (attributeResourceValue > 0) {
                if (GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(imageView.getResources().getResourceTypeName(attributeResourceValue)) && !GifViewUtils.setResource(imageView, z2, attributeResourceValue)) {
                    return attributeResourceValue;
                }
            }
            return 0;
        }

        public GifImageViewAttributes() {
            this.mSourceResId = 0;
            this.mBackgroundResId = 0;
        }
    }

    public static class GifViewAttributes {
        boolean freezesAnimation;
        final int mLoopCount;

        public GifViewAttributes(View view, AttributeSet attributeSet, int i2, int i3) {
            TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.GifView, i2, i3);
            this.freezesAnimation = typedArrayObtainStyledAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
            this.mLoopCount = typedArrayObtainStyledAttributes.getInt(R.styleable.GifView_loopCount, -1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public GifViewAttributes() {
            this.freezesAnimation = false;
            this.mLoopCount = -1;
        }
    }
}
