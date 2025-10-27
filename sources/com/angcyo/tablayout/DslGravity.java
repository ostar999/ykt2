package com.angcyo.tablayout;

import android.graphics.Rect;
import android.graphics.RectF;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JR\u0010=\u001a\u00020>2\b\b\u0002\u0010?\u001a\u00020\"2\b\b\u0002\u0010@\u001a\u00020\"26\u0010A\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\bC\u0012\b\bD\u0012\u0004\b\b(E\u0012\u0013\u0012\u00110\u0004¢\u0006\f\bC\u0012\b\bD\u0012\u0004\b\b(F\u0012\u0004\u0012\u00020>0BJ\u000e\u0010G\u001a\u00020>2\u0006\u0010H\u001a\u00020IJ\u000e\u0010G\u001a\u00020>2\u0006\u0010J\u001a\u000201J&\u0010G\u001a\u00020>2\u0006\u0010K\u001a\u00020\"2\u0006\u0010L\u001a\u00020\"2\u0006\u0010M\u001a\u00020\"2\u0006\u0010N\u001a\u00020\"J&\u0010G\u001a\u00020>2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u00042\u0006\u0010M\u001a\u00020\u00042\u0006\u0010N\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010$\"\u0004\b)\u0010&R\u001a\u0010*\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001a\u0010-\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR\u0011\u00100\u001a\u000201¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u001a\u00104\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0006\"\u0004\b6\u0010\bR\u001a\u00107\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0006\"\u0004\b9\u0010\bR\u001a\u0010:\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u001e\"\u0004\b<\u0010 ¨\u0006O"}, d2 = {"Lcom/angcyo/tablayout/DslGravity;", "", "()V", "_gravityBottom", "", "get_gravityBottom", "()I", "set_gravityBottom", "(I)V", "_gravityLeft", "get_gravityLeft", "set_gravityLeft", "_gravityOffsetX", "get_gravityOffsetX", "set_gravityOffsetX", "_gravityOffsetY", "get_gravityOffsetY", "set_gravityOffsetY", "_gravityRight", "get_gravityRight", "set_gravityRight", "_gravityTop", "get_gravityTop", "set_gravityTop", "_horizontalGravity", "get_horizontalGravity", "set_horizontalGravity", "_isCenterGravity", "", "get_isCenterGravity", "()Z", "set_isCenterGravity", "(Z)V", "_targetHeight", "", "get_targetHeight", "()F", "set_targetHeight", "(F)V", "_targetWidth", "get_targetWidth", "set_targetWidth", "_verticalGravity", "get_verticalGravity", "set_verticalGravity", "gravity", "getGravity", "setGravity", "gravityBounds", "Landroid/graphics/RectF;", "getGravityBounds", "()Landroid/graphics/RectF;", "gravityOffsetX", "getGravityOffsetX", "setGravityOffsetX", "gravityOffsetY", "getGravityOffsetY", "setGravityOffsetY", "gravityRelativeCenter", "getGravityRelativeCenter", "setGravityRelativeCenter", "applyGravity", "", "width", "height", com.alipay.sdk.authjs.a.f3170c, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "centerX", "centerY", "setGravityBounds", "rect", "Landroid/graphics/Rect;", "rectF", "left", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DslGravity {
    private int _gravityBottom;
    private int _gravityLeft;
    private int _gravityOffsetX;
    private int _gravityOffsetY;
    private int _gravityRight;
    private int _gravityTop;
    private boolean _isCenterGravity;
    private float _targetHeight;
    private float _targetWidth;
    private int gravityOffsetX;
    private int gravityOffsetY;

    @NotNull
    private final RectF gravityBounds = new RectF();
    private int gravity = 51;
    private boolean gravityRelativeCenter = true;
    private int _horizontalGravity = 3;
    private int _verticalGravity = 48;

    public static /* synthetic */ void applyGravity$default(DslGravity dslGravity, float f2, float f3, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = dslGravity._targetWidth;
        }
        if ((i2 & 2) != 0) {
            f3 = dslGravity._targetHeight;
        }
        dslGravity.applyGravity(f2, f3, function2);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyGravity(float r9, float r10, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> r11) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslGravity.applyGravity(float, float, kotlin.jvm.functions.Function2):void");
    }

    public final int getGravity() {
        return this.gravity;
    }

    @NotNull
    public final RectF getGravityBounds() {
        return this.gravityBounds;
    }

    public final int getGravityOffsetX() {
        return this.gravityOffsetX;
    }

    public final int getGravityOffsetY() {
        return this.gravityOffsetY;
    }

    public final boolean getGravityRelativeCenter() {
        return this.gravityRelativeCenter;
    }

    public final int get_gravityBottom() {
        return this._gravityBottom;
    }

    public final int get_gravityLeft() {
        return this._gravityLeft;
    }

    public final int get_gravityOffsetX() {
        return this._gravityOffsetX;
    }

    public final int get_gravityOffsetY() {
        return this._gravityOffsetY;
    }

    public final int get_gravityRight() {
        return this._gravityRight;
    }

    public final int get_gravityTop() {
        return this._gravityTop;
    }

    public final int get_horizontalGravity() {
        return this._horizontalGravity;
    }

    public final boolean get_isCenterGravity() {
        return this._isCenterGravity;
    }

    public final float get_targetHeight() {
        return this._targetHeight;
    }

    public final float get_targetWidth() {
        return this._targetWidth;
    }

    public final int get_verticalGravity() {
        return this._verticalGravity;
    }

    public final void setGravity(int i2) {
        this.gravity = i2;
    }

    public final void setGravityBounds(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        this.gravityBounds.set(rectF);
    }

    public final void setGravityOffsetX(int i2) {
        this.gravityOffsetX = i2;
    }

    public final void setGravityOffsetY(int i2) {
        this.gravityOffsetY = i2;
    }

    public final void setGravityRelativeCenter(boolean z2) {
        this.gravityRelativeCenter = z2;
    }

    public final void set_gravityBottom(int i2) {
        this._gravityBottom = i2;
    }

    public final void set_gravityLeft(int i2) {
        this._gravityLeft = i2;
    }

    public final void set_gravityOffsetX(int i2) {
        this._gravityOffsetX = i2;
    }

    public final void set_gravityOffsetY(int i2) {
        this._gravityOffsetY = i2;
    }

    public final void set_gravityRight(int i2) {
        this._gravityRight = i2;
    }

    public final void set_gravityTop(int i2) {
        this._gravityTop = i2;
    }

    public final void set_horizontalGravity(int i2) {
        this._horizontalGravity = i2;
    }

    public final void set_isCenterGravity(boolean z2) {
        this._isCenterGravity = z2;
    }

    public final void set_targetHeight(float f2) {
        this._targetHeight = f2;
    }

    public final void set_targetWidth(float f2) {
        this._targetWidth = f2;
    }

    public final void set_verticalGravity(int i2) {
        this._verticalGravity = i2;
    }

    public final void setGravityBounds(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.gravityBounds.set(rect);
    }

    public final void setGravityBounds(int left, int top2, int right, int bottom) {
        this.gravityBounds.set(left, top2, right, bottom);
    }

    public final void setGravityBounds(float left, float top2, float right, float bottom) {
        this.gravityBounds.set(left, top2, right, bottom);
    }
}
