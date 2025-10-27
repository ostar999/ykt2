package com.angcyo.tablayout;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010J\u001a\u0004\u0018\u00010\r2\b\u0010K\u001a\u0004\u0018\u00010LJ\u0016\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020\u00132\u0006\u0010P\u001a\u00020\u0004J\u0016\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020\u00132\u0006\u0010P\u001a\u00020\u001fJ\u0018\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020\u00132\b\u0010Q\u001a\u0004\u0018\u00010LJ!\u0010R\u001a\u00020\u00002\u0017\u0010S\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020N0T¢\u0006\u0002\bUH\u0016J\u000e\u0010V\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0013J\u000e\u0010W\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0004J\u000e\u0010X\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0004J\u000e\u0010Y\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0004J\u000e\u0010Z\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0004J\u000e\u0010[\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u0004J.\u0010[\u001a\u00020N2\b\b\u0002\u0010\\\u001a\u00020\u00042\b\b\u0002\u0010]\u001a\u00020\u00042\b\b\u0002\u0010^\u001a\u00020\u00042\b\b\u0002\u0010_\u001a\u00020\u0004J\u0010\u0010`\u001a\u00020N2\u0006\u0010a\u001a\u00020bH\u0016J\u000e\u0010c\u001a\u00020N2\u0006\u0010P\u001a\u00020\u0004J\u000e\u0010c\u001a\u00020N2\u0006\u0010P\u001a\u00020\u001fJ\b\u0010d\u001a\u00020\rH\u0016J\b\u0010e\u001a\u00020fH\u0016J\u0012\u0010g\u001a\u00020N2\b\u0010h\u001a\u0004\u0018\u00010iH\u0016J\u0010\u0010j\u001a\u00020f2\u0006\u0010k\u001a\u00020\rH\u0016J\u0012\u0010l\u001a\u00020N2\b\u0010m\u001a\u0004\u0018\u00010nH\u0016J\n\u0010o\u001a\u0004\u0018\u00010pH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0015\"\u0004\b,\u0010\u0017R\u001a\u0010-\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR \u00100\u001a\u00020\u001fX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b1\u0010\u0002\u001a\u0004\b2\u0010!\"\u0004\b3\u0010#R\u001a\u00104\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010!\"\u0004\b6\u0010#R\u001a\u00107\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010!\"\u0004\b9\u0010#R\u001a\u0010:\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010!\"\u0004\b<\u0010#R \u0010=\u001a\u00020\u001fX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b>\u0010\u0002\u001a\u0004\b?\u0010!\"\u0004\b@\u0010#R\u001a\u0010A\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010!\"\u0004\bC\u0010#R\u001c\u0010D\u001a\u0004\u0018\u00010EX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010I¨\u0006q"}, d2 = {"Lcom/angcyo/tablayout/DslGradientDrawable;", "Lcom/angcyo/tablayout/AbsDslDrawable;", "()V", "gradientCenterX", "", "getGradientCenterX", "()F", "setGradientCenterX", "(F)V", "gradientCenterY", "getGradientCenterY", "setGradientCenterY", "gradientColors", "", "getGradientColors", "()[I", "setGradientColors", "([I)V", "gradientColorsOffsets", "", "getGradientColorsOffsets", "()[F", "setGradientColorsOffsets", "([F)V", "gradientDashGap", "getGradientDashGap", "setGradientDashGap", "gradientDashWidth", "getGradientDashWidth", "setGradientDashWidth", "gradientHeightOffset", "", "getGradientHeightOffset", "()I", "setGradientHeightOffset", "(I)V", "gradientOrientation", "Landroid/graphics/drawable/GradientDrawable$Orientation;", "getGradientOrientation", "()Landroid/graphics/drawable/GradientDrawable$Orientation;", "setGradientOrientation", "(Landroid/graphics/drawable/GradientDrawable$Orientation;)V", "gradientRadii", "getGradientRadii", "setGradientRadii", "gradientRadius", "getGradientRadius", "setGradientRadius", "gradientShape", "getGradientShape$annotations", "getGradientShape", "setGradientShape", "gradientSolidColor", "getGradientSolidColor", "setGradientSolidColor", "gradientStrokeColor", "getGradientStrokeColor", "setGradientStrokeColor", "gradientStrokeWidth", "getGradientStrokeWidth", "setGradientStrokeWidth", "gradientType", "getGradientType$annotations", "getGradientType", "setGradientType", "gradientWidthOffset", "getGradientWidthOffset", "setGradientWidthOffset", "originDrawable", "Landroid/graphics/drawable/Drawable;", "getOriginDrawable", "()Landroid/graphics/drawable/Drawable;", "setOriginDrawable", "(Landroid/graphics/drawable/Drawable;)V", "_fillColor", "colors", "", "_fillRadii", "", "array", "radius", "radii", "configDrawable", "config", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "cornerRadii", "cornerRadiiBottom", "cornerRadiiLeft", "cornerRadiiRight", "cornerRadiiTop", "cornerRadius", "leftTop", "rightTop", "rightBottom", "leftBottom", "draw", "canvas", "Landroid/graphics/Canvas;", "fillRadii", "getState", "isValidConfig", "", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "setState", "stateSet", "setTintList", "tint", "Landroid/content/res/ColorStateList;", "updateOriginDrawable", "Landroid/graphics/drawable/GradientDrawable;", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslGradientDrawable extends AbsDslDrawable {

    @Nullable
    private int[] gradientColors;

    @Nullable
    private float[] gradientColorsOffsets;
    private float gradientDashGap;
    private float gradientDashWidth;
    private int gradientHeightOffset;
    private int gradientShape;
    private int gradientSolidColor;
    private int gradientStrokeColor;
    private int gradientStrokeWidth;
    private int gradientType;
    private int gradientWidthOffset;

    @Nullable
    private Drawable originDrawable;

    @NotNull
    private float[] gradientRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private float gradientCenterX = 0.5f;
    private float gradientCenterY = 0.5f;
    private float gradientRadius = 0.5f;

    @NotNull
    private GradientDrawable.Orientation gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT;

    public static /* synthetic */ void cornerRadius$default(DslGradientDrawable dslGradientDrawable, float f2, float f3, float f4, float f5, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cornerRadius");
        }
        if ((i2 & 1) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 2) != 0) {
            f3 = 0.0f;
        }
        if ((i2 & 4) != 0) {
            f4 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f5 = 0.0f;
        }
        dslGradientDrawable.cornerRadius(f2, f3, f4, f5);
    }

    public static /* synthetic */ void getGradientShape$annotations() {
    }

    public static /* synthetic */ void getGradientType$annotations() {
    }

    @Nullable
    public final int[] _fillColor(@Nullable String colors) {
        if (colors == null || colors.length() == 0) {
            return null;
        }
        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) colors, new String[]{","}, false, 0, 6, (Object) null);
        int size = listSplit$default.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            String str = (String) listSplit$default.get(i2);
            iArr[i2] = StringsKt__StringsJVMKt.startsWith$default(str, DictionaryFactory.SHARP, false, 2, null) ? Color.parseColor(str) : Integer.parseInt(str);
        }
        return iArr;
    }

    public final void _fillRadii(@NotNull float[] array, @Nullable String radii) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (radii == null || radii.length() == 0) {
            return;
        }
        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) radii, new String[]{","}, false, 0, 6, (Object) null);
        if (listSplit$default.size() != 8) {
            throw new IllegalArgumentException("radii 需要8个值.");
        }
        float f2 = Resources.getSystem().getDisplayMetrics().density;
        int size = listSplit$default.size();
        for (int i2 = 0; i2 < size; i2++) {
            array[i2] = Float.parseFloat((String) listSplit$default.get(i2)) * f2;
        }
    }

    @NotNull
    public DslGradientDrawable configDrawable(@NotNull Function1<? super DslGradientDrawable, Unit> config) {
        Intrinsics.checkNotNullParameter(config, "config");
        config.invoke(this);
        updateOriginDrawable();
        return this;
    }

    public final void cornerRadii(@NotNull float[] radii) {
        Intrinsics.checkNotNullParameter(radii, "radii");
        this.gradientRadii = radii;
    }

    public final void cornerRadiiBottom(float radii) {
        float[] fArr = this.gradientRadii;
        fArr[4] = radii;
        fArr[5] = radii;
        fArr[6] = radii;
        fArr[7] = radii;
    }

    public final void cornerRadiiLeft(float radii) {
        float[] fArr = this.gradientRadii;
        fArr[0] = radii;
        fArr[1] = radii;
        fArr[6] = radii;
        fArr[7] = radii;
    }

    public final void cornerRadiiRight(float radii) {
        float[] fArr = this.gradientRadii;
        fArr[2] = radii;
        fArr[3] = radii;
        fArr[4] = radii;
        fArr[5] = radii;
    }

    public final void cornerRadiiTop(float radii) {
        float[] fArr = this.gradientRadii;
        fArr[0] = radii;
        fArr[1] = radii;
        fArr[2] = radii;
        fArr[3] = radii;
    }

    public final void cornerRadius(float radii) {
        Arrays.fill(this.gradientRadii, radii);
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Drawable drawable = this.originDrawable;
        if (drawable != null) {
            drawable.setBounds(getBounds().left - (this.gradientWidthOffset / 2), getBounds().top - (this.gradientHeightOffset / 2), getBounds().right + (this.gradientWidthOffset / 2), getBounds().bottom + (this.gradientHeightOffset / 2));
            drawable.draw(canvas);
        }
    }

    public final void fillRadii(float radius) {
        Arrays.fill(this.gradientRadii, radius);
    }

    public final float getGradientCenterX() {
        return this.gradientCenterX;
    }

    public final float getGradientCenterY() {
        return this.gradientCenterY;
    }

    @Nullable
    public final int[] getGradientColors() {
        return this.gradientColors;
    }

    @Nullable
    public final float[] getGradientColorsOffsets() {
        return this.gradientColorsOffsets;
    }

    public final float getGradientDashGap() {
        return this.gradientDashGap;
    }

    public final float getGradientDashWidth() {
        return this.gradientDashWidth;
    }

    public final int getGradientHeightOffset() {
        return this.gradientHeightOffset;
    }

    @NotNull
    public final GradientDrawable.Orientation getGradientOrientation() {
        return this.gradientOrientation;
    }

    @NotNull
    public final float[] getGradientRadii() {
        return this.gradientRadii;
    }

    public final float getGradientRadius() {
        return this.gradientRadius;
    }

    public final int getGradientShape() {
        return this.gradientShape;
    }

    public final int getGradientSolidColor() {
        return this.gradientSolidColor;
    }

    public final int getGradientStrokeColor() {
        return this.gradientStrokeColor;
    }

    public final int getGradientStrokeWidth() {
        return this.gradientStrokeWidth;
    }

    public final int getGradientType() {
        return this.gradientType;
    }

    public final int getGradientWidthOffset() {
        return this.gradientWidthOffset;
    }

    @Nullable
    public final Drawable getOriginDrawable() {
        return this.originDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    @NotNull
    public int[] getState() {
        Drawable drawable = this.originDrawable;
        int[] state = drawable != null ? drawable.getState() : null;
        if (state != null) {
            return state;
        }
        int[] state2 = super.getState();
        Intrinsics.checkNotNullExpressionValue(state2, "super.getState()");
        return state2;
    }

    public boolean isValidConfig() {
        return (this.gradientSolidColor == 0 && this.gradientStrokeColor == 0 && this.gradientColors == null) ? false : true;
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        Drawable drawable = this.originDrawable;
        if (drawable == null) {
            return;
        }
        drawable.setColorFilter(colorFilter);
    }

    public final void setGradientCenterX(float f2) {
        this.gradientCenterX = f2;
    }

    public final void setGradientCenterY(float f2) {
        this.gradientCenterY = f2;
    }

    public final void setGradientColors(@Nullable int[] iArr) {
        this.gradientColors = iArr;
    }

    public final void setGradientColorsOffsets(@Nullable float[] fArr) {
        this.gradientColorsOffsets = fArr;
    }

    public final void setGradientDashGap(float f2) {
        this.gradientDashGap = f2;
    }

    public final void setGradientDashWidth(float f2) {
        this.gradientDashWidth = f2;
    }

    public final void setGradientHeightOffset(int i2) {
        this.gradientHeightOffset = i2;
    }

    public final void setGradientOrientation(@NotNull GradientDrawable.Orientation orientation) {
        Intrinsics.checkNotNullParameter(orientation, "<set-?>");
        this.gradientOrientation = orientation;
    }

    public final void setGradientRadii(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.gradientRadii = fArr;
    }

    public final void setGradientRadius(float f2) {
        this.gradientRadius = f2;
    }

    public final void setGradientShape(int i2) {
        this.gradientShape = i2;
    }

    public final void setGradientSolidColor(int i2) {
        this.gradientSolidColor = i2;
    }

    public final void setGradientStrokeColor(int i2) {
        this.gradientStrokeColor = i2;
    }

    public final void setGradientStrokeWidth(int i2) {
        this.gradientStrokeWidth = i2;
    }

    public final void setGradientType(int i2) {
        this.gradientType = i2;
    }

    public final void setGradientWidthOffset(int i2) {
        this.gradientWidthOffset = i2;
    }

    public final void setOriginDrawable(@Nullable Drawable drawable) {
        this.originDrawable = drawable;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(@NotNull int[] stateSet) {
        Intrinsics.checkNotNullParameter(stateSet, "stateSet");
        Drawable drawable = this.originDrawable;
        return drawable != null ? drawable.setState(stateSet) : super.setState(stateSet);
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void setTintList(@Nullable ColorStateList tint) {
        super.setTintList(tint);
        Drawable drawable = this.originDrawable;
        if (drawable != null) {
            drawable.setTintList(tint);
        }
    }

    @Nullable
    public GradientDrawable updateOriginDrawable() {
        GradientDrawable gradientDrawable;
        Drawable drawable = this.originDrawable;
        if (drawable == null) {
            gradientDrawable = new GradientDrawable();
        } else if (drawable instanceof GradientDrawable) {
            Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            gradientDrawable = (GradientDrawable) drawable;
        } else {
            gradientDrawable = null;
        }
        if (gradientDrawable != null) {
            gradientDrawable.setBounds(getBounds());
            gradientDrawable.setShape(this.gradientShape);
            gradientDrawable.setStroke(this.gradientStrokeWidth, this.gradientStrokeColor, this.gradientDashWidth, this.gradientDashGap);
            gradientDrawable.setColor(this.gradientSolidColor);
            gradientDrawable.setCornerRadii(this.gradientRadii);
            if (this.gradientColors != null) {
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 24) {
                    gradientDrawable.setGradientCenter(this.gradientCenterX, this.gradientCenterY);
                }
                gradientDrawable.setGradientRadius(this.gradientRadius);
                gradientDrawable.setGradientType(this.gradientType);
                gradientDrawable.setOrientation(this.gradientOrientation);
                if (i2 >= 29) {
                    gradientDrawable.setColors(this.gradientColors, this.gradientColorsOffsets);
                } else {
                    gradientDrawable.setColors(this.gradientColors);
                }
            }
            this.originDrawable = gradientDrawable;
            gradientDrawable.invalidateSelf();
        }
        return gradientDrawable;
    }

    public final void cornerRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        float[] fArr = this.gradientRadii;
        fArr[0] = leftTop;
        fArr[1] = leftTop;
        fArr[2] = rightTop;
        fArr[3] = rightTop;
        fArr[4] = rightBottom;
        fArr[5] = rightBottom;
        fArr[6] = leftBottom;
        fArr[7] = leftBottom;
    }

    public final void fillRadii(int radius) {
        _fillRadii(this.gradientRadii, radius);
    }

    public final void _fillRadii(@NotNull float[] array, float radius) {
        Intrinsics.checkNotNullParameter(array, "array");
        Arrays.fill(array, radius);
    }

    public final void _fillRadii(@NotNull float[] array, int radius) {
        Intrinsics.checkNotNullParameter(array, "array");
        _fillRadii(array, radius);
    }
}
