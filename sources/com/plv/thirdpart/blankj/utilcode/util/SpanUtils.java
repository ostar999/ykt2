package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public final class SpanUtils {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    public static final int ALIGN_TOP = 3;
    private static final int COLOR_DEFAULT = -16777217;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private int alignIconMargin;
    private int alignImage;
    private int alignLine;
    private Layout.Alignment alignment;
    private int backgroundColor;
    private float blurRadius;
    private int bulletColor;
    private int bulletGapWidth;
    private int bulletRadius;
    private ClickableSpan clickSpan;
    private int first;
    private int flag;
    private String fontFamily;
    private int fontSize;
    private boolean fontSizeIsDp;
    private int foregroundColor;
    private Bitmap iconMarginBitmap;
    private Drawable iconMarginDrawable;
    private int iconMarginGapWidth;
    private int iconMarginResourceId;
    private Uri iconMarginUri;
    private Bitmap imageBitmap;
    private Drawable imageDrawable;
    private int imageResourceId;
    private Uri imageUri;
    private boolean isBold;
    private boolean isBoldItalic;
    private boolean isItalic;
    private boolean isStrikethrough;
    private boolean isSubscript;
    private boolean isSuperscript;
    private boolean isUnderline;
    private int lineHeight;
    private int mType;
    private float proportion;
    private int quoteColor;
    private int quoteGapWidth;
    private int rest;
    private Shader shader;
    private int shadowColor;
    private float shadowDx;
    private float shadowDy;
    private float shadowRadius;
    private int spaceColor;
    private int spaceSize;
    private Object[] spans;
    private int stripeWidth;
    private BlurMaskFilter.Blur style;
    private Typeface typeface;
    private String url;
    private float xProportion;
    private final int mTypeCharSequence = 0;
    private final int mTypeImage = 1;
    private final int mTypeSpace = 2;
    private SpannableStringBuilder mBuilder = new SpannableStringBuilder();
    private CharSequence mText = "";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    public class CustomBulletSpan implements LeadingMarginSpan {
        private final int color;
        private final int gapWidth;
        private final int radius;
        private Path sBulletPath;

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i2, int i3, int i4, int i5, int i6, CharSequence charSequence, int i7, int i8, boolean z2, Layout layout) {
            if (((Spanned) charSequence).getSpanStart(this) == i7) {
                Paint.Style style = paint.getStyle();
                int color = paint.getColor();
                paint.setColor(this.color);
                paint.setStyle(Paint.Style.FILL);
                if (canvas.isHardwareAccelerated()) {
                    if (this.sBulletPath == null) {
                        Path path = new Path();
                        this.sBulletPath = path;
                        path.addCircle(0.0f, 0.0f, this.radius, Path.Direction.CW);
                    }
                    canvas.save();
                    canvas.translate(i2 + (i3 * this.radius), (i4 + i6) / 2.0f);
                    canvas.drawPath(this.sBulletPath, paint);
                    canvas.restore();
                } else {
                    canvas.drawCircle(i2 + (i3 * r10), (i4 + i6) / 2.0f, this.radius, paint);
                }
                paint.setColor(color);
                paint.setStyle(style);
            }
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z2) {
            return (this.radius * 2) + this.gapWidth;
        }

        private CustomBulletSpan(int i2, int i3, int i4) {
            this.sBulletPath = null;
            this.color = i2;
            this.radius = i3;
            this.gapWidth = i4;
        }
    }

    public abstract class CustomDynamicDrawableSpan extends ReplacementSpan {
        static final int ALIGN_BASELINE = 1;
        static final int ALIGN_BOTTOM = 0;
        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP = 3;
        private WeakReference<Drawable> mDrawableRef;
        final int mVerticalAlignment;

        private Drawable getCachedDrawable() {
            WeakReference<Drawable> weakReference = this.mDrawableRef;
            if ((weakReference != null ? weakReference.get() : null) == null) {
                this.mDrawableRef = new WeakReference<>(getDrawable());
            }
            return getDrawable();
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i2, int i3, float f2, int i4, int i5, int i6, @NonNull Paint paint) {
            float f3;
            float fHeight;
            Drawable cachedDrawable = getCachedDrawable();
            Rect bounds = cachedDrawable.getBounds();
            canvas.save();
            float f4 = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
            int i7 = i6 - bounds.bottom;
            if (bounds.height() < f4) {
                int i8 = this.mVerticalAlignment;
                if (i8 == 1) {
                    i7 -= paint.getFontMetricsInt().descent;
                } else {
                    if (i8 == 2) {
                        f3 = i7;
                        fHeight = (f4 - bounds.height()) / 2.0f;
                    } else if (i8 == 3) {
                        f3 = i7;
                        fHeight = f4 - bounds.height();
                    }
                    i7 = (int) (f3 - fHeight);
                }
            }
            canvas.translate(f2, i7);
            cachedDrawable.draw(canvas);
            canvas.restore();
        }

        public abstract Drawable getDrawable();

        @Override // android.text.style.ReplacementSpan
        public int getSize(@NonNull Paint paint, CharSequence charSequence, int i2, int i3, Paint.FontMetricsInt fontMetricsInt) {
            Rect bounds = getCachedDrawable().getBounds();
            int i4 = (int) (paint.getFontMetrics().descent - paint.getFontMetrics().ascent);
            if (fontMetricsInt != null && bounds.height() > i4) {
                int i5 = this.mVerticalAlignment;
                if (i5 == 3) {
                    fontMetricsInt.descent += bounds.height() - i4;
                } else if (i5 == 2) {
                    fontMetricsInt.ascent -= (bounds.height() - i4) / 2;
                    fontMetricsInt.descent += (bounds.height() - i4) / 2;
                } else {
                    fontMetricsInt.ascent -= bounds.height() - i4;
                }
            }
            return bounds.right;
        }

        private CustomDynamicDrawableSpan() {
            this.mVerticalAlignment = 0;
        }

        private CustomDynamicDrawableSpan(int i2) {
            this.mVerticalAlignment = i2;
        }
    }

    public class CustomLineHeightSpan extends CharacterStyle implements LineHeightSpan {
        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP = 3;
        private final int height;
        final int mVerticalAlignment;

        public CustomLineHeightSpan(int i2, int i3) {
            this.height = i2;
            this.mVerticalAlignment = i3;
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence charSequence, int i2, int i3, int i4, int i5, Paint.FontMetricsInt fontMetricsInt) {
            int i6 = this.height;
            int i7 = fontMetricsInt.descent;
            int i8 = fontMetricsInt.ascent;
            int i9 = i6 - (((i5 + i7) - i8) - i4);
            if (i9 > 0) {
                int i10 = this.mVerticalAlignment;
                if (i10 == 3) {
                    fontMetricsInt.descent = i7 + i9;
                } else if (i10 == 2) {
                    int i11 = i9 / 2;
                    fontMetricsInt.descent = i7 + i11;
                    fontMetricsInt.ascent = i8 - i11;
                } else {
                    fontMetricsInt.ascent = i8 - i9;
                }
            }
            int i12 = fontMetricsInt.bottom;
            int i13 = fontMetricsInt.top;
            int i14 = i6 - (((i5 + i12) - i13) - i4);
            if (i14 > 0) {
                int i15 = this.mVerticalAlignment;
                if (i15 == 3) {
                    fontMetricsInt.top = i13 + i14;
                } else {
                    if (i15 != 2) {
                        fontMetricsInt.top = i13 - i14;
                        return;
                    }
                    int i16 = i14 / 2;
                    fontMetricsInt.bottom = i12 + i16;
                    fontMetricsInt.top = i13 - i16;
                }
            }
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
        }
    }

    public class CustomQuoteSpan implements LeadingMarginSpan {
        private final int color;
        private final int gapWidth;
        private final int stripeWidth;

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i2, int i3, int i4, int i5, int i6, CharSequence charSequence, int i7, int i8, boolean z2, Layout layout) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(this.color);
            canvas.drawRect(i2, i4, i2 + (this.stripeWidth * i3), i6, paint);
            paint.setStyle(style);
            paint.setColor(color);
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z2) {
            return this.stripeWidth + this.gapWidth;
        }

        private CustomQuoteSpan(int i2, int i3, int i4) {
            this.color = i2;
            this.stripeWidth = i3;
            this.gapWidth = i4;
        }
    }

    @SuppressLint({"ParcelCreator"})
    public class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        private void apply(Paint paint, Typeface typeface) {
            Typeface typeface2 = paint.getTypeface();
            int style = (typeface2 == null ? 0 : typeface2.getStyle()) & (~typeface.getStyle());
            if ((style & 1) != 0) {
                paint.setFakeBoldText(true);
            }
            if ((style & 2) != 0) {
                paint.setTextSkewX(-0.25f);
            }
            paint.getShader();
            paint.setTypeface(typeface);
        }

        @Override // android.text.style.TypefaceSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            apply(textPaint, this.newType);
        }

        @Override // android.text.style.TypefaceSpan, android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            apply(textPaint, this.newType);
        }

        private CustomTypefaceSpan(Typeface typeface) {
            super("");
            this.newType = typeface;
        }
    }

    public class ShaderSpan extends CharacterStyle implements UpdateAppearance {
        private Shader mShader;

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShader(this.mShader);
        }

        private ShaderSpan(Shader shader) {
            this.mShader = shader;
        }
    }

    public class ShadowSpan extends CharacterStyle implements UpdateAppearance {
        private float dx;
        private float dy;
        private float radius;
        private int shadowColor;

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShadowLayer(this.radius, this.dx, this.dy, this.shadowColor);
        }

        private ShadowSpan(float f2, float f3, float f4, int i2) {
            this.radius = f2;
            this.dx = f3;
            this.dy = f4;
            this.shadowColor = i2;
        }
    }

    public class SpaceSpan extends ReplacementSpan {
        private final int color;
        private final int width;

        @Override // android.text.style.ReplacementSpan
        public void draw(@NonNull Canvas canvas, CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, float f2, int i4, int i5, int i6, @NonNull Paint paint) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(this.color);
            canvas.drawRect(f2, i4, f2 + this.width, i6, paint);
            paint.setStyle(style);
            paint.setColor(color);
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(@NonNull Paint paint, CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @Nullable Paint.FontMetricsInt fontMetricsInt) {
            return this.width;
        }

        private SpaceSpan(SpanUtils spanUtils, int i2) {
            this(i2, 0);
        }

        private SpaceSpan(int i2, int i3) {
            this.width = i2;
            this.color = i3;
        }
    }

    public SpanUtils() {
        setDefault();
    }

    private void apply(int i2) {
        applyLast();
        this.mType = i2;
    }

    private void applyLast() {
        int i2 = this.mType;
        if (i2 == 0) {
            updateCharCharSequence();
        } else if (i2 == 1) {
            updateImage();
        } else if (i2 == 2) {
            updateSpace();
        }
        setDefault();
    }

    private void setDefault() {
        this.flag = 33;
        this.foregroundColor = COLOR_DEFAULT;
        this.backgroundColor = COLOR_DEFAULT;
        this.lineHeight = -1;
        this.quoteColor = COLOR_DEFAULT;
        this.first = -1;
        this.bulletColor = COLOR_DEFAULT;
        this.iconMarginBitmap = null;
        this.iconMarginDrawable = null;
        this.iconMarginUri = null;
        this.iconMarginResourceId = -1;
        this.iconMarginGapWidth = -1;
        this.fontSize = -1;
        this.proportion = -1.0f;
        this.xProportion = -1.0f;
        this.isStrikethrough = false;
        this.isUnderline = false;
        this.isSuperscript = false;
        this.isSubscript = false;
        this.isBold = false;
        this.isItalic = false;
        this.isBoldItalic = false;
        this.fontFamily = null;
        this.typeface = null;
        this.alignment = null;
        this.clickSpan = null;
        this.url = null;
        this.blurRadius = -1.0f;
        this.shader = null;
        this.shadowRadius = -1.0f;
        this.spans = null;
        this.imageBitmap = null;
        this.imageDrawable = null;
        this.imageUri = null;
        this.imageResourceId = -1;
        this.spaceSize = -1;
    }

    private void updateCharCharSequence() {
        if (this.mText.length() == 0) {
            return;
        }
        int length = this.mBuilder.length();
        this.mBuilder.append(this.mText);
        int length2 = this.mBuilder.length();
        if (this.foregroundColor != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), length, length2, this.flag);
        }
        if (this.backgroundColor != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), length, length2, this.flag);
        }
        if (this.first != -1) {
            this.mBuilder.setSpan(new LeadingMarginSpan.Standard(this.first, this.rest), length, length2, this.flag);
        }
        int i2 = this.quoteColor;
        if (i2 != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new CustomQuoteSpan(i2, this.stripeWidth, this.quoteGapWidth), length, length2, this.flag);
        }
        int i3 = this.bulletColor;
        if (i3 != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new CustomBulletSpan(i3, this.bulletRadius, this.bulletGapWidth), length, length2, this.flag);
        }
        int i4 = this.iconMarginGapWidth;
        if (i4 != -1) {
            Bitmap bitmap = this.iconMarginBitmap;
            if (bitmap != null) {
                this.mBuilder.setSpan(new CustomIconMarginSpan(bitmap, i4, this.alignIconMargin), length, length2, this.flag);
            } else {
                Drawable drawable = this.iconMarginDrawable;
                if (drawable != null) {
                    this.mBuilder.setSpan(new CustomIconMarginSpan(drawable, i4, this.alignIconMargin), length, length2, this.flag);
                } else {
                    Uri uri = this.iconMarginUri;
                    if (uri != null) {
                        this.mBuilder.setSpan(new CustomIconMarginSpan(uri, i4, this.alignIconMargin), length, length2, this.flag);
                    } else {
                        int i5 = this.iconMarginResourceId;
                        if (i5 != -1) {
                            this.mBuilder.setSpan(new CustomIconMarginSpan(i5, i4, this.alignIconMargin), length, length2, this.flag);
                        }
                    }
                }
            }
        }
        if (this.fontSize != -1) {
            this.mBuilder.setSpan(new AbsoluteSizeSpan(this.fontSize, this.fontSizeIsDp), length, length2, this.flag);
        }
        if (this.proportion != -1.0f) {
            this.mBuilder.setSpan(new RelativeSizeSpan(this.proportion), length, length2, this.flag);
        }
        if (this.xProportion != -1.0f) {
            this.mBuilder.setSpan(new ScaleXSpan(this.xProportion), length, length2, this.flag);
        }
        if (this.lineHeight != -1) {
            this.mBuilder.setSpan(new CustomLineHeightSpan(this.lineHeight, this.alignLine), length, length2, this.flag);
        }
        if (this.isStrikethrough) {
            this.mBuilder.setSpan(new StrikethroughSpan(), length, length2, this.flag);
        }
        if (this.isUnderline) {
            this.mBuilder.setSpan(new UnderlineSpan(), length, length2, this.flag);
        }
        if (this.isSuperscript) {
            this.mBuilder.setSpan(new SuperscriptSpan(), length, length2, this.flag);
        }
        if (this.isSubscript) {
            this.mBuilder.setSpan(new SubscriptSpan(), length, length2, this.flag);
        }
        if (this.isBold) {
            this.mBuilder.setSpan(new StyleSpan(1), length, length2, this.flag);
        }
        if (this.isItalic) {
            this.mBuilder.setSpan(new StyleSpan(2), length, length2, this.flag);
        }
        if (this.isBoldItalic) {
            this.mBuilder.setSpan(new StyleSpan(3), length, length2, this.flag);
        }
        if (this.fontFamily != null) {
            this.mBuilder.setSpan(new TypefaceSpan(this.fontFamily), length, length2, this.flag);
        }
        if (this.typeface != null) {
            this.mBuilder.setSpan(new CustomTypefaceSpan(this.typeface), length, length2, this.flag);
        }
        if (this.alignment != null) {
            this.mBuilder.setSpan(new AlignmentSpan.Standard(this.alignment), length, length2, this.flag);
        }
        ClickableSpan clickableSpan = this.clickSpan;
        if (clickableSpan != null) {
            this.mBuilder.setSpan(clickableSpan, length, length2, this.flag);
        }
        if (this.url != null) {
            this.mBuilder.setSpan(new URLSpan(this.url), length, length2, this.flag);
        }
        if (this.blurRadius != -1.0f) {
            this.mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(this.blurRadius, this.style)), length, length2, this.flag);
        }
        if (this.shader != null) {
            this.mBuilder.setSpan(new ShaderSpan(this.shader), length, length2, this.flag);
        }
        if (this.shadowRadius != -1.0f) {
            this.mBuilder.setSpan(new ShadowSpan(this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor), length, length2, this.flag);
        }
        Object[] objArr = this.spans;
        if (objArr != null) {
            for (Object obj : objArr) {
                this.mBuilder.setSpan(obj, length, length2, this.flag);
            }
        }
    }

    private void updateImage() {
        int length = this.mBuilder.length();
        this.mBuilder.append((CharSequence) "<img>");
        int i2 = length + 5;
        if (this.imageBitmap != null) {
            this.mBuilder.setSpan(new CustomImageSpan(this.imageBitmap, this.alignImage), length, i2, this.flag);
            return;
        }
        if (this.imageDrawable != null) {
            this.mBuilder.setSpan(new CustomImageSpan(this.imageDrawable, this.alignImage), length, i2, this.flag);
        } else if (this.imageUri != null) {
            this.mBuilder.setSpan(new CustomImageSpan(this.imageUri, this.alignImage), length, i2, this.flag);
        } else if (this.imageResourceId != -1) {
            this.mBuilder.setSpan(new CustomImageSpan(this.imageResourceId, this.alignImage), length, i2, this.flag);
        }
    }

    private void updateSpace() {
        int length = this.mBuilder.length();
        this.mBuilder.append((CharSequence) "< >");
        this.mBuilder.setSpan(new SpaceSpan(this.spaceSize, this.spaceColor), length, length + 3, this.flag);
    }

    public SpanUtils append(@NonNull CharSequence charSequence) {
        apply(0);
        this.mText = charSequence;
        return this;
    }

    public SpanUtils appendImage(@NonNull Bitmap bitmap) {
        return appendImage(bitmap, 0);
    }

    public SpanUtils appendLine() {
        apply(0);
        this.mText = LINE_SEPARATOR;
        return this;
    }

    public SpanUtils appendSpace(@IntRange(from = 0) int i2) {
        return appendSpace(i2, 0);
    }

    public SpannableStringBuilder create() {
        applyLast();
        return this.mBuilder;
    }

    public SpanUtils setAlign(@NonNull Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public SpanUtils setBackgroundColor(@ColorInt int i2) {
        this.backgroundColor = i2;
        return this;
    }

    public SpanUtils setBlur(@FloatRange(from = 0.0d, fromInclusive = false) float f2, BlurMaskFilter.Blur blur) {
        this.blurRadius = f2;
        this.style = blur;
        return this;
    }

    public SpanUtils setBold() {
        this.isBold = true;
        return this;
    }

    public SpanUtils setBoldItalic() {
        this.isBoldItalic = true;
        return this;
    }

    public SpanUtils setBullet(@IntRange(from = 0) int i2) {
        return setBullet(0, 3, i2);
    }

    public SpanUtils setClickSpan(@NonNull ClickableSpan clickableSpan) {
        this.clickSpan = clickableSpan;
        return this;
    }

    public SpanUtils setFlag(int i2) {
        this.flag = i2;
        return this;
    }

    public SpanUtils setFontFamily(@NonNull String str) {
        this.fontFamily = str;
        return this;
    }

    public SpanUtils setFontProportion(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this.proportion = f2;
        return this;
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2) {
        return setFontSize(i2, false);
    }

    public SpanUtils setFontXProportion(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this.xProportion = f2;
        return this;
    }

    public SpanUtils setForegroundColor(@ColorInt int i2) {
        this.foregroundColor = i2;
        return this;
    }

    public SpanUtils setIconMargin(Bitmap bitmap) {
        return setIconMargin(bitmap, 0, 2);
    }

    public SpanUtils setItalic() {
        this.isItalic = true;
        return this;
    }

    public SpanUtils setLeadingMargin(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        this.first = i2;
        this.rest = i3;
        return this;
    }

    public SpanUtils setLineHeight(@IntRange(from = 0) int i2) {
        return setLineHeight(i2, 2);
    }

    public SpanUtils setQuoteColor(@ColorInt int i2) {
        return setQuoteColor(i2, 2, 2);
    }

    public SpanUtils setShader(@NonNull Shader shader) {
        this.shader = shader;
        return this;
    }

    public SpanUtils setShadow(@FloatRange(from = 0.0d, fromInclusive = false) float f2, float f3, float f4, int i2) {
        this.shadowRadius = f2;
        this.shadowDx = f3;
        this.shadowDy = f4;
        this.shadowColor = i2;
        return this;
    }

    public SpanUtils setSpans(@NonNull Object... objArr) {
        if (objArr.length > 0) {
            this.spans = objArr;
        }
        return this;
    }

    public SpanUtils setStrikethrough() {
        this.isStrikethrough = true;
        return this;
    }

    public SpanUtils setSubscript() {
        this.isSubscript = true;
        return this;
    }

    public SpanUtils setSuperscript() {
        this.isSuperscript = true;
        return this;
    }

    public SpanUtils setTypeface(@NonNull Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public SpanUtils setUnderline() {
        this.isUnderline = true;
        return this;
    }

    public SpanUtils setUrl(@NonNull String str) {
        this.url = str;
        return this;
    }

    public SpanUtils appendImage(@NonNull Bitmap bitmap, int i2) {
        apply(1);
        this.imageBitmap = bitmap;
        this.alignImage = i2;
        return this;
    }

    public SpanUtils appendSpace(@IntRange(from = 0) int i2, @ColorInt int i3) {
        apply(2);
        this.spaceSize = i2;
        this.spaceColor = i3;
        return this;
    }

    public SpanUtils setBullet(@ColorInt int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4) {
        this.bulletColor = i2;
        this.bulletRadius = i3;
        this.bulletGapWidth = i4;
        return this;
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2, boolean z2) {
        this.fontSize = i2;
        this.fontSizeIsDp = z2;
        return this;
    }

    public SpanUtils setIconMargin(Bitmap bitmap, int i2, int i3) {
        this.iconMarginBitmap = bitmap;
        this.iconMarginGapWidth = i2;
        this.alignIconMargin = i3;
        return this;
    }

    public SpanUtils setLineHeight(@IntRange(from = 0) int i2, int i3) {
        this.lineHeight = i2;
        this.alignLine = i3;
        return this;
    }

    public SpanUtils setQuoteColor(@ColorInt int i2, @IntRange(from = 1) int i3, @IntRange(from = 0) int i4) {
        this.quoteColor = i2;
        this.stripeWidth = i3;
        this.quoteGapWidth = i4;
        return this;
    }

    public SpanUtils appendLine(@NonNull CharSequence charSequence) {
        apply(0);
        this.mText = ((Object) charSequence) + LINE_SEPARATOR;
        return this;
    }

    public class CustomIconMarginSpan implements LeadingMarginSpan, LineHeightSpan {
        static final int ALIGN_CENTER = 2;
        static final int ALIGN_TOP = 3;
        private boolean flag;
        private int lineHeight;
        Bitmap mBitmap;
        private int mPad;
        final int mVerticalAlignment;
        private int need0;
        private int need1;
        private int totalHeight;

        private Bitmap drawable2Bitmap(Drawable drawable) {
            Bitmap bitmapCreateBitmap;
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmapCreateBitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }

        private Bitmap resource2Bitmap(int i2) {
            Drawable drawable = ContextCompat.getDrawable(Utils.getApp(), i2);
            Canvas canvas = new Canvas();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmapCreateBitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }

        private Bitmap uri2Bitmap(Uri uri) {
            try {
                return MediaStore.Images.Media.getBitmap(Utils.getApp().getContentResolver(), uri);
            } catch (IOException e2) {
                e2.printStackTrace();
                return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            }
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence charSequence, int i2, int i3, int i4, int i5, Paint.FontMetricsInt fontMetricsInt) {
            if (this.lineHeight == 0) {
                this.lineHeight = i5 - i4;
            }
            if (this.need0 == 0 && i3 == ((Spanned) charSequence).getSpanEnd(this)) {
                int height = this.mBitmap.getHeight();
                this.need0 = height - (((fontMetricsInt.descent + i5) - fontMetricsInt.ascent) - i4);
                this.need1 = height - (((fontMetricsInt.bottom + i5) - fontMetricsInt.top) - i4);
                this.totalHeight = (i5 - i4) + this.lineHeight;
                return;
            }
            if (this.need0 > 0 || this.need1 > 0) {
                int i6 = this.mVerticalAlignment;
                if (i6 == 3) {
                    if (i3 == ((Spanned) charSequence).getSpanEnd(this)) {
                        int i7 = this.need0;
                        if (i7 > 0) {
                            fontMetricsInt.descent += i7;
                        }
                        int i8 = this.need1;
                        if (i8 > 0) {
                            fontMetricsInt.bottom += i8;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (i6 != 2) {
                    if (i2 == ((Spanned) charSequence).getSpanStart(this)) {
                        int i9 = this.need0;
                        if (i9 > 0) {
                            fontMetricsInt.ascent -= i9;
                        }
                        int i10 = this.need1;
                        if (i10 > 0) {
                            fontMetricsInt.top -= i10;
                            return;
                        }
                        return;
                    }
                    if (this.flag) {
                        return;
                    }
                    int i11 = this.need0;
                    if (i11 > 0) {
                        fontMetricsInt.ascent += i11;
                    }
                    int i12 = this.need1;
                    if (i12 > 0) {
                        fontMetricsInt.top += i12;
                    }
                    this.flag = true;
                    return;
                }
                Spanned spanned = (Spanned) charSequence;
                if (i2 == spanned.getSpanStart(this)) {
                    int i13 = this.need0;
                    if (i13 > 0) {
                        fontMetricsInt.ascent -= i13 / 2;
                    }
                    int i14 = this.need1;
                    if (i14 > 0) {
                        fontMetricsInt.top -= i14 / 2;
                    }
                } else if (!this.flag) {
                    int i15 = this.need0;
                    if (i15 > 0) {
                        fontMetricsInt.ascent += i15 / 2;
                    }
                    int i16 = this.need1;
                    if (i16 > 0) {
                        fontMetricsInt.top += i16 / 2;
                    }
                    this.flag = true;
                }
                if (i3 == spanned.getSpanEnd(this)) {
                    int i17 = this.need0;
                    if (i17 > 0) {
                        fontMetricsInt.descent += i17 / 2;
                    }
                    int i18 = this.need1;
                    if (i18 > 0) {
                        fontMetricsInt.bottom += i18 / 2;
                    }
                }
            }
        }

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i2, int i3, int i4, int i5, int i6, CharSequence charSequence, int i7, int i8, boolean z2, Layout layout) {
            int lineTop = layout.getLineTop(layout.getLineForOffset(((Spanned) charSequence).getSpanStart(this)));
            if (i3 < 0) {
                i2 -= this.mBitmap.getWidth();
            }
            if (this.totalHeight - this.mBitmap.getHeight() <= 0) {
                canvas.drawBitmap(this.mBitmap, i2, lineTop, paint);
                return;
            }
            int i9 = this.mVerticalAlignment;
            if (i9 == 3) {
                canvas.drawBitmap(this.mBitmap, i2, lineTop, paint);
            } else if (i9 == 2) {
                canvas.drawBitmap(this.mBitmap, i2, lineTop + (r4 / 2), paint);
            } else {
                canvas.drawBitmap(this.mBitmap, i2, lineTop + r4, paint);
            }
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z2) {
            return this.mBitmap.getWidth() + this.mPad;
        }

        private CustomIconMarginSpan(Bitmap bitmap, int i2, int i3) {
            this.mBitmap = bitmap;
            this.mPad = i2;
            this.mVerticalAlignment = i3;
        }

        private CustomIconMarginSpan(Drawable drawable, int i2, int i3) {
            this.mBitmap = drawable2Bitmap(drawable);
            this.mPad = i2;
            this.mVerticalAlignment = i3;
        }

        private CustomIconMarginSpan(Uri uri, int i2, int i3) {
            this.mBitmap = uri2Bitmap(uri);
            this.mPad = i2;
            this.mVerticalAlignment = i3;
        }

        private CustomIconMarginSpan(int i2, int i3, int i4) {
            this.mBitmap = resource2Bitmap(i2);
            this.mPad = i3;
            this.mVerticalAlignment = i4;
        }
    }

    public class CustomImageSpan extends CustomDynamicDrawableSpan {
        private Uri mContentUri;
        private Drawable mDrawable;
        private int mResourceId;

        @Override // com.plv.thirdpart.blankj.utilcode.util.SpanUtils.CustomDynamicDrawableSpan
        public Drawable getDrawable() throws IOException {
            Drawable drawable;
            InputStream inputStreamOpenInputStream;
            BitmapDrawable bitmapDrawable;
            Drawable drawable2 = this.mDrawable;
            if (drawable2 != null) {
                return drawable2;
            }
            BitmapDrawable bitmapDrawable2 = null;
            if (this.mContentUri == null) {
                try {
                    drawable = ContextCompat.getDrawable(Utils.getApp(), this.mResourceId);
                } catch (Exception unused) {
                    drawable = null;
                }
                try {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                } catch (Exception unused2) {
                    Log.e("sms", "Unable to find resource: " + this.mResourceId);
                    return drawable;
                }
            }
            try {
                inputStreamOpenInputStream = Utils.getApp().getContentResolver().openInputStream(this.mContentUri);
                bitmapDrawable = new BitmapDrawable(Utils.getApp().getResources(), BitmapFactory.decodeStream(inputStreamOpenInputStream));
            } catch (Exception e2) {
                e = e2;
            }
            try {
                bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
                return bitmapDrawable;
            } catch (Exception e3) {
                e = e3;
                bitmapDrawable2 = bitmapDrawable;
                Log.e("sms", "Failed to loaded content " + this.mContentUri, e);
                return bitmapDrawable2;
            }
        }

        private CustomImageSpan(Bitmap bitmap, int i2) {
            super(i2);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(Utils.getApp().getResources(), bitmap);
            this.mDrawable = bitmapDrawable;
            bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(Drawable drawable, int i2) {
            super(i2);
            this.mDrawable = drawable;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(Uri uri, int i2) {
            super(i2);
            this.mContentUri = uri;
        }

        private CustomImageSpan(int i2, @DrawableRes int i3) {
            super(i3);
            this.mResourceId = i2;
        }
    }

    public SpanUtils appendImage(@NonNull Drawable drawable) {
        return appendImage(drawable, 0);
    }

    public SpanUtils setIconMargin(Drawable drawable) {
        return setIconMargin(drawable, 0, 2);
    }

    public SpanUtils appendImage(@NonNull Drawable drawable, int i2) {
        apply(1);
        this.imageDrawable = drawable;
        this.alignImage = i2;
        return this;
    }

    public SpanUtils setIconMargin(Drawable drawable, int i2, int i3) {
        this.iconMarginDrawable = drawable;
        this.iconMarginGapWidth = i2;
        this.alignIconMargin = i3;
        return this;
    }

    public SpanUtils appendImage(@NonNull Uri uri) {
        return appendImage(uri, 0);
    }

    public SpanUtils setIconMargin(Uri uri) {
        return setIconMargin(uri, 0, 2);
    }

    public SpanUtils appendImage(@NonNull Uri uri, int i2) {
        apply(1);
        this.imageUri = uri;
        this.alignImage = i2;
        return this;
    }

    public SpanUtils setIconMargin(Uri uri, int i2, int i3) {
        this.iconMarginUri = uri;
        this.iconMarginGapWidth = i2;
        this.alignIconMargin = i3;
        return this;
    }

    public SpanUtils appendImage(@DrawableRes int i2) {
        return appendImage(i2, 0);
    }

    public SpanUtils setIconMargin(@DrawableRes int i2) {
        return setIconMargin(i2, 0, 2);
    }

    public SpanUtils appendImage(@DrawableRes int i2, int i3) {
        apply(1);
        this.imageResourceId = i2;
        this.alignImage = i3;
        return this;
    }

    public SpanUtils setIconMargin(@DrawableRes int i2, int i3, int i4) {
        this.iconMarginResourceId = i2;
        this.iconMarginGapWidth = i3;
        this.alignIconMargin = i4;
        return this;
    }
}
