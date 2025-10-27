package com.makeramen.roundedimageview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import com.squareup.picasso.Transformation;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class RoundedTransformationBuilder {
    private float[] mCornerRadii = {0.0f, 0.0f, 0.0f, 0.0f};
    private boolean mOval = false;
    private float mBorderWidth = 0.0f;
    private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
    private final DisplayMetrics mDisplayMetrics = Resources.getSystem().getDisplayMetrics();

    public RoundedTransformationBuilder borderColor(int i2) {
        this.mBorderColor = ColorStateList.valueOf(i2);
        return this;
    }

    public RoundedTransformationBuilder borderWidth(float f2) {
        this.mBorderWidth = f2;
        return this;
    }

    public RoundedTransformationBuilder borderWidthDp(float f2) {
        this.mBorderWidth = TypedValue.applyDimension(1, f2, this.mDisplayMetrics);
        return this;
    }

    public Transformation build() {
        return new Transformation() { // from class: com.makeramen.roundedimageview.RoundedTransformationBuilder.1
            @Override // com.squareup.picasso.Transformation
            public String key() {
                return "r:" + Arrays.toString(RoundedTransformationBuilder.this.mCornerRadii) + "b:" + RoundedTransformationBuilder.this.mBorderWidth + "c:" + RoundedTransformationBuilder.this.mBorderColor + "o:" + RoundedTransformationBuilder.this.mOval;
            }

            @Override // com.squareup.picasso.Transformation
            public Bitmap transform(Bitmap bitmap) {
                Bitmap bitmap2 = RoundedDrawable.fromBitmap(bitmap).setScaleType(RoundedTransformationBuilder.this.mScaleType).setCornerRadius(RoundedTransformationBuilder.this.mCornerRadii[0], RoundedTransformationBuilder.this.mCornerRadii[1], RoundedTransformationBuilder.this.mCornerRadii[2], RoundedTransformationBuilder.this.mCornerRadii[3]).setBorderWidth(RoundedTransformationBuilder.this.mBorderWidth).setBorderColor(RoundedTransformationBuilder.this.mBorderColor).setOval(RoundedTransformationBuilder.this.mOval).toBitmap();
                if (!bitmap.equals(bitmap2)) {
                    bitmap.recycle();
                }
                return bitmap2;
            }
        };
    }

    public RoundedTransformationBuilder cornerRadius(float f2) {
        float[] fArr = this.mCornerRadii;
        fArr[0] = f2;
        fArr[1] = f2;
        fArr[2] = f2;
        fArr[3] = f2;
        return this;
    }

    public RoundedTransformationBuilder cornerRadiusDp(float f2) {
        return cornerRadius(TypedValue.applyDimension(1, f2, this.mDisplayMetrics));
    }

    public RoundedTransformationBuilder oval(boolean z2) {
        this.mOval = z2;
        return this;
    }

    public RoundedTransformationBuilder scaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
        return this;
    }

    public RoundedTransformationBuilder borderColor(ColorStateList colorStateList) {
        this.mBorderColor = colorStateList;
        return this;
    }

    public RoundedTransformationBuilder cornerRadiusDp(int i2, float f2) {
        return cornerRadius(i2, TypedValue.applyDimension(1, f2, this.mDisplayMetrics));
    }

    public RoundedTransformationBuilder cornerRadius(int i2, float f2) {
        this.mCornerRadii[i2] = f2;
        return this;
    }
}
