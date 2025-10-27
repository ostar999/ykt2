package com.google.android.exoplayer2.text;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class Cue implements Bundleable {
    public static final int ANCHOR_TYPE_END = 2;
    public static final int ANCHOR_TYPE_MIDDLE = 1;
    public static final int ANCHOR_TYPE_START = 0;
    public static final float DIMEN_UNSET = -3.4028235E38f;
    private static final int FIELD_BITMAP = 3;
    private static final int FIELD_BITMAP_HEIGHT = 12;
    private static final int FIELD_LINE = 4;
    private static final int FIELD_LINE_ANCHOR = 6;
    private static final int FIELD_LINE_TYPE = 5;
    private static final int FIELD_MULTI_ROW_ALIGNMENT = 2;
    private static final int FIELD_POSITION = 7;
    private static final int FIELD_POSITION_ANCHOR = 8;
    private static final int FIELD_SHEAR_DEGREES = 16;
    private static final int FIELD_SIZE = 11;
    private static final int FIELD_TEXT = 0;
    private static final int FIELD_TEXT_ALIGNMENT = 1;
    private static final int FIELD_TEXT_SIZE = 10;
    private static final int FIELD_TEXT_SIZE_TYPE = 9;
    private static final int FIELD_VERTICAL_TYPE = 15;
    private static final int FIELD_WINDOW_COLOR = 13;
    private static final int FIELD_WINDOW_COLOR_SET = 14;
    public static final int LINE_TYPE_FRACTION = 0;
    public static final int LINE_TYPE_NUMBER = 1;
    public static final int TEXT_SIZE_TYPE_ABSOLUTE = 2;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL = 0;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL_IGNORE_PADDING = 1;
    public static final int TYPE_UNSET = Integer.MIN_VALUE;
    public static final int VERTICAL_TYPE_LR = 2;
    public static final int VERTICAL_TYPE_RL = 1;

    @Nullable
    public final Bitmap bitmap;
    public final float bitmapHeight;
    public final float line;
    public final int lineAnchor;
    public final int lineType;

    @Nullable
    public final Layout.Alignment multiRowAlignment;
    public final float position;
    public final int positionAnchor;
    public final float shearDegrees;
    public final float size;

    @Nullable
    public final CharSequence text;

    @Nullable
    public final Layout.Alignment textAlignment;
    public final float textSize;
    public final int textSizeType;
    public final int verticalType;
    public final int windowColor;
    public final boolean windowColorSet;
    public static final Cue EMPTY = new Builder().setText("").build();
    public static final Bundleable.Creator<Cue> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.text.a
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return Cue.fromBundle(bundle);
        }
    };

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnchorType {
    }

    public static final class Builder {

        @Nullable
        private Bitmap bitmap;
        private float bitmapHeight;
        private float line;
        private int lineAnchor;
        private int lineType;

        @Nullable
        private Layout.Alignment multiRowAlignment;
        private float position;
        private int positionAnchor;
        private float shearDegrees;
        private float size;

        @Nullable
        private CharSequence text;

        @Nullable
        private Layout.Alignment textAlignment;
        private float textSize;
        private int textSizeType;
        private int verticalType;

        @ColorInt
        private int windowColor;
        private boolean windowColorSet;

        public Cue build() {
            return new Cue(this.text, this.textAlignment, this.multiRowAlignment, this.bitmap, this.line, this.lineType, this.lineAnchor, this.position, this.positionAnchor, this.textSizeType, this.textSize, this.size, this.bitmapHeight, this.windowColorSet, this.windowColor, this.verticalType, this.shearDegrees);
        }

        public Builder clearWindowColor() {
            this.windowColorSet = false;
            return this;
        }

        @Nullable
        @Pure
        public Bitmap getBitmap() {
            return this.bitmap;
        }

        @Pure
        public float getBitmapHeight() {
            return this.bitmapHeight;
        }

        @Pure
        public float getLine() {
            return this.line;
        }

        @Pure
        public int getLineAnchor() {
            return this.lineAnchor;
        }

        @Pure
        public int getLineType() {
            return this.lineType;
        }

        @Pure
        public float getPosition() {
            return this.position;
        }

        @Pure
        public int getPositionAnchor() {
            return this.positionAnchor;
        }

        @Pure
        public float getSize() {
            return this.size;
        }

        @Nullable
        @Pure
        public CharSequence getText() {
            return this.text;
        }

        @Nullable
        @Pure
        public Layout.Alignment getTextAlignment() {
            return this.textAlignment;
        }

        @Pure
        public float getTextSize() {
            return this.textSize;
        }

        @Pure
        public int getTextSizeType() {
            return this.textSizeType;
        }

        @Pure
        public int getVerticalType() {
            return this.verticalType;
        }

        @ColorInt
        @Pure
        public int getWindowColor() {
            return this.windowColor;
        }

        public boolean isWindowColorSet() {
            return this.windowColorSet;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder setBitmapHeight(float f2) {
            this.bitmapHeight = f2;
            return this;
        }

        public Builder setLine(float f2, int i2) {
            this.line = f2;
            this.lineType = i2;
            return this;
        }

        public Builder setLineAnchor(int i2) {
            this.lineAnchor = i2;
            return this;
        }

        public Builder setMultiRowAlignment(@Nullable Layout.Alignment alignment) {
            this.multiRowAlignment = alignment;
            return this;
        }

        public Builder setPosition(float f2) {
            this.position = f2;
            return this;
        }

        public Builder setPositionAnchor(int i2) {
            this.positionAnchor = i2;
            return this;
        }

        public Builder setShearDegrees(float f2) {
            this.shearDegrees = f2;
            return this;
        }

        public Builder setSize(float f2) {
            this.size = f2;
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            this.text = charSequence;
            return this;
        }

        public Builder setTextAlignment(@Nullable Layout.Alignment alignment) {
            this.textAlignment = alignment;
            return this;
        }

        public Builder setTextSize(float f2, int i2) {
            this.textSize = f2;
            this.textSizeType = i2;
            return this;
        }

        public Builder setVerticalType(int i2) {
            this.verticalType = i2;
            return this;
        }

        public Builder setWindowColor(@ColorInt int i2) {
            this.windowColor = i2;
            this.windowColorSet = true;
            return this;
        }

        public Builder() {
            this.text = null;
            this.bitmap = null;
            this.textAlignment = null;
            this.multiRowAlignment = null;
            this.line = -3.4028235E38f;
            this.lineType = Integer.MIN_VALUE;
            this.lineAnchor = Integer.MIN_VALUE;
            this.position = -3.4028235E38f;
            this.positionAnchor = Integer.MIN_VALUE;
            this.textSizeType = Integer.MIN_VALUE;
            this.textSize = -3.4028235E38f;
            this.size = -3.4028235E38f;
            this.bitmapHeight = -3.4028235E38f;
            this.windowColorSet = false;
            this.windowColor = -16777216;
            this.verticalType = Integer.MIN_VALUE;
        }

        private Builder(Cue cue) {
            this.text = cue.text;
            this.bitmap = cue.bitmap;
            this.textAlignment = cue.textAlignment;
            this.multiRowAlignment = cue.multiRowAlignment;
            this.line = cue.line;
            this.lineType = cue.lineType;
            this.lineAnchor = cue.lineAnchor;
            this.position = cue.position;
            this.positionAnchor = cue.positionAnchor;
            this.textSizeType = cue.textSizeType;
            this.textSize = cue.textSize;
            this.size = cue.size;
            this.bitmapHeight = cue.bitmapHeight;
            this.windowColorSet = cue.windowColorSet;
            this.windowColor = cue.windowColor;
            this.verticalType = cue.verticalType;
            this.shearDegrees = cue.shearDegrees;
        }
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineType {
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSizeType {
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface VerticalType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Cue fromBundle(Bundle bundle) {
        Builder builder = new Builder();
        CharSequence charSequence = bundle.getCharSequence(keyForField(0));
        if (charSequence != null) {
            builder.setText(charSequence);
        }
        Layout.Alignment alignment = (Layout.Alignment) bundle.getSerializable(keyForField(1));
        if (alignment != null) {
            builder.setTextAlignment(alignment);
        }
        Layout.Alignment alignment2 = (Layout.Alignment) bundle.getSerializable(keyForField(2));
        if (alignment2 != null) {
            builder.setMultiRowAlignment(alignment2);
        }
        Bitmap bitmap = (Bitmap) bundle.getParcelable(keyForField(3));
        if (bitmap != null) {
            builder.setBitmap(bitmap);
        }
        if (bundle.containsKey(keyForField(4)) && bundle.containsKey(keyForField(5))) {
            builder.setLine(bundle.getFloat(keyForField(4)), bundle.getInt(keyForField(5)));
        }
        if (bundle.containsKey(keyForField(6))) {
            builder.setLineAnchor(bundle.getInt(keyForField(6)));
        }
        if (bundle.containsKey(keyForField(7))) {
            builder.setPosition(bundle.getFloat(keyForField(7)));
        }
        if (bundle.containsKey(keyForField(8))) {
            builder.setPositionAnchor(bundle.getInt(keyForField(8)));
        }
        if (bundle.containsKey(keyForField(10)) && bundle.containsKey(keyForField(9))) {
            builder.setTextSize(bundle.getFloat(keyForField(10)), bundle.getInt(keyForField(9)));
        }
        if (bundle.containsKey(keyForField(11))) {
            builder.setSize(bundle.getFloat(keyForField(11)));
        }
        if (bundle.containsKey(keyForField(12))) {
            builder.setBitmapHeight(bundle.getFloat(keyForField(12)));
        }
        if (bundle.containsKey(keyForField(13))) {
            builder.setWindowColor(bundle.getInt(keyForField(13)));
        }
        if (!bundle.getBoolean(keyForField(14), false)) {
            builder.clearWindowColor();
        }
        if (bundle.containsKey(keyForField(15))) {
            builder.setVerticalType(bundle.getInt(keyForField(15)));
        }
        if (bundle.containsKey(keyForField(16))) {
            builder.setShearDegrees(bundle.getFloat(keyForField(16)));
        }
        return builder.build();
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        Bitmap bitmap;
        Bitmap bitmap2;
        if (this == obj) {
            return true;
        }
        if (obj == null || Cue.class != obj.getClass()) {
            return false;
        }
        Cue cue = (Cue) obj;
        return TextUtils.equals(this.text, cue.text) && this.textAlignment == cue.textAlignment && this.multiRowAlignment == cue.multiRowAlignment && ((bitmap = this.bitmap) != null ? !((bitmap2 = cue.bitmap) == null || !bitmap.sameAs(bitmap2)) : cue.bitmap == null) && this.line == cue.line && this.lineType == cue.lineType && this.lineAnchor == cue.lineAnchor && this.position == cue.position && this.positionAnchor == cue.positionAnchor && this.size == cue.size && this.bitmapHeight == cue.bitmapHeight && this.windowColorSet == cue.windowColorSet && this.windowColor == cue.windowColor && this.textSizeType == cue.textSizeType && this.textSize == cue.textSize && this.verticalType == cue.verticalType && this.shearDegrees == cue.shearDegrees;
    }

    public int hashCode() {
        return Objects.hashCode(this.text, this.textAlignment, this.multiRowAlignment, this.bitmap, Float.valueOf(this.line), Integer.valueOf(this.lineType), Integer.valueOf(this.lineAnchor), Float.valueOf(this.position), Integer.valueOf(this.positionAnchor), Float.valueOf(this.size), Float.valueOf(this.bitmapHeight), Boolean.valueOf(this.windowColorSet), Integer.valueOf(this.windowColor), Integer.valueOf(this.textSizeType), Float.valueOf(this.textSize), Integer.valueOf(this.verticalType), Float.valueOf(this.shearDegrees));
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(keyForField(0), this.text);
        bundle.putSerializable(keyForField(1), this.textAlignment);
        bundle.putSerializable(keyForField(2), this.multiRowAlignment);
        bundle.putParcelable(keyForField(3), this.bitmap);
        bundle.putFloat(keyForField(4), this.line);
        bundle.putInt(keyForField(5), this.lineType);
        bundle.putInt(keyForField(6), this.lineAnchor);
        bundle.putFloat(keyForField(7), this.position);
        bundle.putInt(keyForField(8), this.positionAnchor);
        bundle.putInt(keyForField(9), this.textSizeType);
        bundle.putFloat(keyForField(10), this.textSize);
        bundle.putFloat(keyForField(11), this.size);
        bundle.putFloat(keyForField(12), this.bitmapHeight);
        bundle.putBoolean(keyForField(14), this.windowColorSet);
        bundle.putInt(keyForField(13), this.windowColor);
        bundle.putInt(keyForField(15), this.verticalType);
        bundle.putFloat(keyForField(16), this.shearDegrees);
        return bundle;
    }

    @Deprecated
    public Cue(CharSequence charSequence) {
        this(charSequence, null, -3.4028235E38f, Integer.MIN_VALUE, Integer.MIN_VALUE, -3.4028235E38f, Integer.MIN_VALUE, -3.4028235E38f);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4) {
        this(charSequence, alignment, f2, i2, i3, f3, i4, f4, false, -16777216);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4, int i5, float f5) {
        this(charSequence, alignment, null, null, f2, i2, i3, f3, i4, i5, f5, f4, -3.4028235E38f, false, -16777216, Integer.MIN_VALUE, 0.0f);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4, boolean z2, int i5) {
        this(charSequence, alignment, null, null, f2, i2, i3, f3, i4, Integer.MIN_VALUE, -3.4028235E38f, f4, -3.4028235E38f, z2, i5, Integer.MIN_VALUE, 0.0f);
    }

    private Cue(@Nullable CharSequence charSequence, @Nullable Layout.Alignment alignment, @Nullable Layout.Alignment alignment2, @Nullable Bitmap bitmap, float f2, int i2, int i3, float f3, int i4, int i5, float f4, float f5, float f6, boolean z2, int i6, int i7, float f7) {
        if (charSequence == null) {
            Assertions.checkNotNull(bitmap);
        } else {
            Assertions.checkArgument(bitmap == null);
        }
        if (charSequence instanceof Spanned) {
            this.text = SpannedString.valueOf(charSequence);
        } else if (charSequence != null) {
            this.text = charSequence.toString();
        } else {
            this.text = null;
        }
        this.textAlignment = alignment;
        this.multiRowAlignment = alignment2;
        this.bitmap = bitmap;
        this.line = f2;
        this.lineType = i2;
        this.lineAnchor = i3;
        this.position = f3;
        this.positionAnchor = i4;
        this.size = f5;
        this.bitmapHeight = f6;
        this.windowColorSet = z2;
        this.windowColor = i6;
        this.textSizeType = i5;
        this.textSize = f4;
        this.verticalType = i7;
        this.shearDegrees = f7;
    }
}
