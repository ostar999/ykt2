package com.petterp.floatingx.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.app.NotificationCompat;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.util.FxAdsorbDirection;
import com.petterp.floatingx.util.FxExtKt;
import com.petterp.floatingx.util.FxLog;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J$\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u0006\u0010'\u001a\u00020 J\u0016\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010,\u001a\u00020)2\u0006\u0010-\u001a\u00020\"J\u000e\u0010.\u001a\u00020 2\u0006\u0010-\u001a\u00020\"J\u000e\u0010/\u001a\u00020 2\u0006\u0010%\u001a\u00020\u0004J\u000e\u00100\u001a\u00020 2\u0006\u0010&\u001a\u00020\u0004J\u000e\u00101\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u0004J\u0016\u00101\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\"J\u000e\u00102\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u0016\u00102\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\"J\u000e\u00103\u001a\u00020)2\u0006\u00104\u001a\u00020 J\u000e\u00105\u001a\u00020 2\u0006\u00106\u001a\u000207J\u001e\u00105\u001a\u00020 2\u0006\u00108\u001a\u00020\u00192\u0006\u00109\u001a\u00020\u00192\u0006\u00106\u001a\u000207R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006:"}, d2 = {"Lcom/petterp/floatingx/view/FxViewConfigHelper;", "", "()V", "downTouchX", "", "downTouchY", "helper", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "mParentHeight", "mParentWidth", "maxHBoundary", "getMaxHBoundary", "()F", "setMaxHBoundary", "(F)V", "maxWBoundary", "getMaxWBoundary", "setMaxWBoundary", "minHBoundary", "getMinHBoundary", "setMinHBoundary", "minWBoundary", "getMinWBoundary", "setMinWBoundary", "scaledTouchSlop", "", "touchDownId", "getTouchDownId", "()I", "setTouchDownId", "(I)V", "checkInterceptedEvent", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "getAdsorbDirectionLocation", "Lkotlin/Pair;", "x", "y", "hasMainPointerId", "initConfig", "", d.R, "Landroid/content/Context;", "initTouchDown", "ev", "isCurrentPointerId", "isNearestLeft", "isNearestTop", "safeX", "safeY", "updateBoundary", "isDownTouchInit", "updateWidgetSize", "view", "Landroid/view/ViewGroup;", "parentW", "parentH", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxViewConfigHelper {
    private float downTouchX;
    private float downTouchY;
    private BasisHelper helper;
    private float mParentHeight;
    private float mParentWidth;
    private float maxHBoundary;
    private float maxWBoundary;
    private float minHBoundary;
    private float minWBoundary;
    private int scaledTouchSlop;
    private int touchDownId = -1;

    public final boolean checkInterceptedEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (isCurrentPointerId(event)) {
            return Math.abs(event.getX() - this.downTouchX) >= ((float) this.scaledTouchSlop) || Math.abs(event.getY() - this.downTouchY) >= ((float) this.scaledTouchSlop);
        }
        return false;
    }

    @Nullable
    public final Pair<Float, Float> getAdsorbDirectionLocation(float x2, float y2) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (basisHelper.enableEdgeAdsorption) {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (basisHelper.adsorbDirection == FxAdsorbDirection.LEFT_OR_RIGHT) {
                return TuplesKt.to(Float.valueOf(isNearestLeft(x2) ? this.minWBoundary : this.maxWBoundary), Float.valueOf(safeY(y2)));
            }
            return TuplesKt.to(Float.valueOf(safeX(x2)), Float.valueOf(isNearestTop(y2) ? this.minHBoundary : this.maxHBoundary));
        }
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (basisHelper.enableEdgeRebound) {
            return TuplesKt.to(Float.valueOf(safeX(x2)), Float.valueOf(safeY(y2)));
        }
        return null;
    }

    public final float getMaxHBoundary() {
        return this.maxHBoundary;
    }

    public final float getMaxWBoundary() {
        return this.maxWBoundary;
    }

    public final float getMinHBoundary() {
        return this.minHBoundary;
    }

    public final float getMinWBoundary() {
        return this.minWBoundary;
    }

    public final int getTouchDownId() {
        return this.touchDownId;
    }

    public final boolean hasMainPointerId() {
        return this.touchDownId != -1;
    }

    public final void initConfig(@NotNull Context context, @NotNull BasisHelper helper) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(helper, "helper");
        this.helper = helper;
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final void initTouchDown(@NotNull MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        this.touchDownId = FxExtKt.getPointerId(ev);
        this.downTouchX = ev.getX();
        this.downTouchY = ev.getY();
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d(Intrinsics.stringPlus("fxView---newTouchDown:", Integer.valueOf(this.touchDownId)));
    }

    public final boolean isCurrentPointerId(@NotNull MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return this.touchDownId != -1 && FxExtKt.getPointerId(ev) == this.touchDownId;
    }

    public final boolean isNearestLeft(float x2) {
        return x2 < this.mParentWidth / ((float) 2);
    }

    public final boolean isNearestTop(float y2) {
        return y2 < this.mParentHeight / ((float) 2);
    }

    public final float safeX(float x2) {
        return FxExtKt.coerceInFx(x2, this.minWBoundary, this.maxWBoundary);
    }

    public final float safeY(float y2) {
        return FxExtKt.coerceInFx(y2, this.minHBoundary, this.maxHBoundary);
    }

    public final void setMaxHBoundary(float f2) {
        this.maxHBoundary = f2;
    }

    public final void setMaxWBoundary(float f2) {
        this.maxWBoundary = f2;
    }

    public final void setMinHBoundary(float f2) {
        this.minHBoundary = f2;
    }

    public final void setMinWBoundary(float f2) {
        this.minWBoundary = f2;
    }

    public final void setTouchDownId(int i2) {
        this.touchDownId = i2;
    }

    public final void updateBoundary(boolean isDownTouchInit) {
        float f2;
        float t2;
        float b3;
        float l2;
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (!basisHelper.enableEdgeRebound) {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            this.minWBoundary = basisHelper.fxBorderMargin.getL();
            float f3 = this.mParentWidth;
            BasisHelper basisHelper2 = this.helper;
            if (basisHelper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            this.maxWBoundary = f3 - basisHelper2.fxBorderMargin.getR();
            BasisHelper basisHelper3 = this.helper;
            if (basisHelper3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            float f4 = basisHelper3.statsBarHeight;
            if (basisHelper3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            this.minHBoundary = f4 + basisHelper3.fxBorderMargin.getT();
            float f5 = this.mParentHeight;
            BasisHelper basisHelper4 = this.helper;
            if (basisHelper4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            float f6 = f5 - basisHelper4.navigationBarHeight;
            if (basisHelper4 != null) {
                this.maxHBoundary = f6 - basisHelper4.fxBorderMargin.getB();
                return;
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
        }
        float r2 = 0.0f;
        if (isDownTouchInit) {
            f2 = 0.0f;
        } else {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            f2 = basisHelper.edgeOffset;
        }
        if (isDownTouchInit) {
            t2 = 0.0f;
        } else {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            t2 = basisHelper.fxBorderMargin.getT() + f2;
        }
        if (isDownTouchInit) {
            b3 = 0.0f;
        } else {
            BasisHelper basisHelper5 = this.helper;
            if (basisHelper5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            b3 = basisHelper5.fxBorderMargin.getB() + f2;
        }
        if (isDownTouchInit) {
            l2 = 0.0f;
        } else {
            BasisHelper basisHelper6 = this.helper;
            if (basisHelper6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            l2 = basisHelper6.fxBorderMargin.getL() + f2;
        }
        if (!isDownTouchInit) {
            BasisHelper basisHelper7 = this.helper;
            if (basisHelper7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            r2 = basisHelper7.fxBorderMargin.getR() + f2;
        }
        this.minWBoundary = l2;
        this.maxWBoundary = this.mParentWidth - r2;
        BasisHelper basisHelper8 = this.helper;
        if (basisHelper8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        this.minHBoundary = basisHelper8.statsBarHeight + t2;
        float f7 = this.mParentHeight;
        if (basisHelper8 != null) {
            this.maxHBoundary = (f7 - basisHelper8.navigationBarHeight) - b3;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
    }

    public final boolean updateWidgetSize(@NotNull ViewGroup view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup == null) {
            return false;
        }
        return updateWidgetSize(viewGroup.getWidth(), viewGroup.getHeight(), view);
    }

    public final float safeX(float x2, @NotNull MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return FxExtKt.coerceInFx((x2 + ev.getX()) - this.downTouchX, this.minWBoundary, this.maxWBoundary);
    }

    public final float safeY(float y2, @NotNull MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return FxExtKt.coerceInFx((y2 + ev.getY()) - this.downTouchY, this.minHBoundary, this.maxHBoundary);
    }

    public final boolean updateWidgetSize(int parentW, int parentH, @NotNull ViewGroup view) {
        Intrinsics.checkNotNullParameter(view, "view");
        float width = parentW - view.getWidth();
        float height = parentH - view.getHeight();
        if (this.mParentHeight == height) {
            if (this.mParentWidth == width) {
                return false;
            }
        }
        BasisHelper basisHelper = this.helper;
        if (basisHelper != null) {
            FxLog fxLog = basisHelper.fxLog;
            if (fxLog != null) {
                fxLog.d("fxView->updateContainerSize: oldW-(" + this.mParentWidth + "),oldH-(" + this.mParentHeight + "),newW-(" + width + "),newH-(" + height + ')');
            }
            this.mParentWidth = width;
            this.mParentHeight = height;
            updateBoundary(false);
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("helper");
        throw null;
    }
}
