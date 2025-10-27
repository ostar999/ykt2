package com.petterp.floatingx.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.core.app.NotificationCompat;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.petterp.floatingx.assist.FxDisplayMode;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.listener.IFxConfigStorage;
import com.petterp.floatingx.listener.IFxScrollListener;
import com.petterp.floatingx.listener.IFxViewLifecycle;
import com.petterp.floatingx.util.FxExtKt;
import com.petterp.floatingx.util.FxLog;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\n\u0010\u001a\u001a\u0004\u0018\u00010\tH\u0002J\n\u0010\u001b\u001a\u0004\u0018\u00010\tH\u0002J\u0015\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0012H\u0000¢\u0006\u0002\b\u001eJ\u0014\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160 H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u0019H\u0002J'\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b*J'\u0010+\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b,J\r\u0010-\u001a\u00020\u0019H\u0000¢\u0006\u0002\b.J\u0018\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u0016H\u0002J\b\u00102\u001a\u00020\u0019H\u0014J\u0010\u00103\u001a\u00020\u00192\u0006\u00104\u001a\u000205H\u0014J\b\u00106\u001a\u00020\u0019H\u0014J\u0010\u00107\u001a\u00020)2\u0006\u0010#\u001a\u00020$H\u0016JR\u00108\u001a\u00020\u00192\b\u00109\u001a\u0004\u0018\u00010\t2\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020;2\u0006\u0010>\u001a\u00020;2\u0006\u0010?\u001a\u00020;2\u0006\u0010@\u001a\u00020;2\u0006\u0010A\u001a\u00020;2\u0006\u0010B\u001a\u00020;H\u0016J\u0010\u0010C\u001a\u00020)2\u0006\u0010D\u001a\u00020$H\u0017J\u0010\u0010E\u001a\u00020\u00192\u0006\u0010F\u001a\u00020;H\u0014J\u0018\u0010G\u001a\u00020\u00192\u0006\u0010H\u001a\u00020;2\u0006\u0010I\u001a\u00020;H\u0002J\b\u0010J\u001a\u00020\u0019H\u0002J\u001d\u0010J\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0000¢\u0006\u0002\bKJ\u0018\u0010L\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u0016H\u0002J\u0012\u0010M\u001a\u00020\u00192\b\u0010N\u001a\u0004\u0018\u00010OH\u0016J\u0012\u0010P\u001a\u00020\u00192\b\b\u0002\u0010Q\u001a\u00020)H\u0002J\u0010\u0010R\u001a\u00020\u00192\u0006\u0010D\u001a\u00020$H\u0002J\u0010\u0010S\u001a\u00020\u00192\u0006\u0010D\u001a\u00020$H\u0002J\u001a\u0010T\u001a\u00020\u00192\u0006\u0010D\u001a\u00020$2\b\b\u0002\u0010Q\u001a\u00020)H\u0002J\r\u0010U\u001a\u00020\u0019H\u0000¢\u0006\u0002\bVJ\u0010\u0010W\u001a\u00020\u00192\u0006\u0010D\u001a\u00020$H\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\n\u001a\u0004\u0018\u00010\t8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006X"}, d2 = {"Lcom/petterp/floatingx/view/FxManagerView;", "Landroid/widget/FrameLayout;", "Landroid/view/View$OnLayoutChangeListener;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "_childFxView", "Landroid/view/View;", "childFxView", "getChildFxView", "()Landroid/view/View;", "clickHelper", "Lcom/petterp/floatingx/view/FxClickHelper;", "configHelper", "Lcom/petterp/floatingx/view/FxViewConfigHelper;", "helper", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "restoreHelper", "Lcom/petterp/floatingx/view/FxLocationHelper;", "checkDefaultY", "", "y", "checkOrFixLocation", "", "inflateLayoutId", "inflateLayoutView", "init", "config", "init$floatingx_release", "initDefaultXY", "Lkotlin/Pair;", "initLocation", "initTouchDown", "ev", "Landroid/view/MotionEvent;", "initView", "moveLocation", "x", "useAnimation", "", "moveLocation$floatingx_release", "moveLocationByVector", "moveLocationByVector$floatingx_release", "moveToEdge", "moveToEdge$floatingx_release", "moveToLocation", "moveX", "moveY", "onAttachedToWindow", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onDetachedFromWindow", "onInterceptTouchEvent", "onLayoutChange", "v", "left", "", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "oldLeft", "oldTop", "oldRight", "oldBottom", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "onWindowVisibilityChanged", "visibility", "refreshLocation", "w", "h", "restoreLocation", "restoreLocation$floatingx_release", "saveLocationToStorage", "setOnClickListener", NotifyType.LIGHTS, "Landroid/view/View$OnClickListener;", "touchToCancel", "isFxSelfEvent", "touchToMove", "touchToPointerDown", "touchToPointerUp", "updateDisplayMode", "updateDisplayMode$floatingx_release", "updateLocation", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
@SuppressLint({"ViewConstructor"})
/* loaded from: classes4.dex */
public final class FxManagerView extends FrameLayout implements View.OnLayoutChangeListener {

    @Nullable
    private View _childFxView;

    @NotNull
    private final FxClickHelper clickHelper;

    @NotNull
    private final FxViewConfigHelper configHelper;
    private BasisHelper helper;

    @NotNull
    private final FxLocationHelper restoreHelper;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FxManagerView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ FxManagerView(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    private final float checkDefaultY(float y2) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        int scope = basisHelper.gravity.getScope();
        if (scope == 1) {
            if (this.helper != null) {
                return y2 + r0.statsBarHeight;
            }
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (scope != 3) {
            return y2;
        }
        if (this.helper != null) {
            return y2 - r0.navigationBarHeight;
        }
        Intrinsics.throwUninitializedPropertyAccessException("helper");
        throw null;
    }

    private final void checkOrFixLocation() {
        moveToLocation(this.configHelper.safeX(getX()), this.configHelper.safeY(getY()));
    }

    private final View inflateLayoutId() {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (basisHelper.layoutId == 0) {
            return null;
        }
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView-->init, way:[layoutId]");
        }
        Context context = getContext();
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 != null) {
            return View.inflate(context, basisHelper2.layoutId, this);
        }
        Intrinsics.throwUninitializedPropertyAccessException("helper");
        throw null;
    }

    private final View inflateLayoutView() {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        View view = basisHelper.layoutView;
        if (view == null) {
            return null;
        }
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView-->init, way:[layoutView]");
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
        }
        addView(view, layoutParams);
        return view;
    }

    private final Pair<Float, Float> initDefaultXY() {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (!basisHelper.enableAssistLocation) {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (!basisHelper.gravity.isDefault()) {
                BasisHelper basisHelper2 = this.helper;
                if (basisHelper2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("helper");
                    throw null;
                }
                FxLog fxLog = basisHelper2.fxLog;
                if (fxLog != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("fxView--默认坐标可能初始化异常,如果显示位置异常,请检查您的gravity是否为默认配置，当前gravity:");
                    BasisHelper basisHelper3 = this.helper;
                    if (basisHelper3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("helper");
                        throw null;
                    }
                    sb.append(basisHelper3.gravity);
                    sb.append("。\n如果您要配置gravity,建议您启用辅助定位setEnableAssistDirection(),此方法将更便于定位。");
                    fxLog.e(sb.toString());
                }
            }
        }
        BasisHelper basisHelper4 = this.helper;
        if (basisHelper4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        Float fValueOf = Float.valueOf(basisHelper4.defaultX);
        BasisHelper basisHelper5 = this.helper;
        if (basisHelper5 != null) {
            return TuplesKt.to(fValueOf, Float.valueOf(checkDefaultY(basisHelper5.defaultY)));
        }
        Intrinsics.throwUninitializedPropertyAccessException("helper");
        throw null;
    }

    private final void initLocation() {
        Pair<Float, Float> pairInitDefaultXY;
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxConfigStorage iFxConfigStorage = basisHelper.iFxConfigStorage;
        boolean zHasConfig = iFxConfigStorage == null ? false : iFxConfigStorage.hasConfig();
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FrameLayout.LayoutParams layoutParams = basisHelper2.layoutParams;
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
        }
        if (!zHasConfig) {
            BasisHelper basisHelper3 = this.helper;
            if (basisHelper3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            layoutParams.gravity = basisHelper3.gravity.getValue();
        }
        setLayoutParams(layoutParams);
        if (zHasConfig) {
            Intrinsics.checkNotNull(iFxConfigStorage);
            pairInitDefaultXY = TuplesKt.to(Float.valueOf(iFxConfigStorage.getX()), Float.valueOf(iFxConfigStorage.getY()));
        } else {
            pairInitDefaultXY = initDefaultXY();
        }
        float fFloatValue = pairInitDefaultXY.component1().floatValue();
        float fFloatValue2 = pairInitDefaultXY.component2().floatValue();
        if (!(fFloatValue == -1.0f)) {
            setX(fFloatValue);
        }
        if (!(fFloatValue2 == -1.0f)) {
            setY(fFloatValue2);
        }
        BasisHelper basisHelper4 = this.helper;
        if (basisHelper4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper4.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView->initLocation,isHasConfig-(" + zHasConfig + "),defaultX-(" + fFloatValue + "),defaultY-(" + fFloatValue2 + ')');
    }

    private final void initTouchDown(MotionEvent ev) {
        if (this.configHelper.hasMainPointerId()) {
            return;
        }
        this.clickHelper.initDown(getX(), getY());
        this.configHelper.initTouchDown(ev);
        this.configHelper.updateWidgetSize(this);
        this.configHelper.updateBoundary(true);
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxScrollListener iFxScrollListener = basisHelper.iFxScrollListener;
        if (iFxScrollListener == null) {
            return;
        }
        iFxScrollListener.down();
    }

    private final void initView() {
        View viewInflateLayoutView = inflateLayoutView();
        if (viewInflateLayoutView == null) {
            viewInflateLayoutView = inflateLayoutId();
        }
        this._childFxView = viewInflateLayoutView;
        FxClickHelper fxClickHelper = this.clickHelper;
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        fxClickHelper.initConfig(basisHelper);
        FxLocationHelper fxLocationHelper = this.restoreHelper;
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        fxLocationHelper.initConfig(basisHelper2);
        FxViewConfigHelper fxViewConfigHelper = this.configHelper;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        BasisHelper basisHelper3 = this.helper;
        if (basisHelper3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        fxViewConfigHelper.initConfig(context, basisHelper3);
        if (this._childFxView == null) {
            throw new IllegalStateException("initFxView -> Error,check your layoutId or layoutView.".toString());
        }
        initLocation();
        updateDisplayMode$floatingx_release();
        setBackgroundColor(0);
    }

    public static /* synthetic */ void moveLocation$floatingx_release$default(FxManagerView fxManagerView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        fxManagerView.moveLocation$floatingx_release(f2, f3, z2);
    }

    public static /* synthetic */ void moveLocationByVector$floatingx_release$default(FxManagerView fxManagerView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        fxManagerView.moveLocationByVector$floatingx_release(f2, f3, z2);
    }

    private final void moveToLocation(float moveX, float moveY) {
        if (moveX == getX()) {
            if (moveY == getY()) {
                return;
            }
        }
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView-->moveToEdge---x-(" + getX() + ")，y-(" + getY() + ") ->  moveX-(" + moveX + "),moveY-(" + moveY + ')');
        }
        animate().x(moveX).y(moveY).setDuration(200L).start();
    }

    private final void refreshLocation(int w2, int h2) {
        if (this.configHelper.updateWidgetSize(w2, h2, this)) {
            if (this.restoreHelper.isInitLocation()) {
                checkOrFixLocation();
            } else if (this.restoreHelper.getScreenChanged()) {
                restoreLocation();
            } else {
                moveToEdge$floatingx_release();
            }
        }
    }

    private final void restoreLocation() {
        Pair<Float, Float> location = this.restoreHelper.getLocation(this.configHelper);
        float fFloatValue = location.component1().floatValue();
        float fFloatValue2 = location.component2().floatValue();
        setX(fFloatValue);
        setY(fFloatValue2);
        saveLocationToStorage(fFloatValue, fFloatValue2);
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView--lifecycle-> restoreLocation:[x:" + fFloatValue + ",y:" + fFloatValue2 + ']');
    }

    private final void saveLocationToStorage(float moveX, float moveY) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (basisHelper.enableSaveDirection) {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            IFxConfigStorage iFxConfigStorage = basisHelper.iFxConfigStorage;
            if (iFxConfigStorage == null) {
                if (basisHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("helper");
                    throw null;
                }
                FxLog fxLog = basisHelper.fxLog;
                if (fxLog == null) {
                    return;
                }
                fxLog.e("fxView-->saveDirection---iFxConfigStorageImpl does not exist, save failed!");
                return;
            }
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (iFxConfigStorage != null) {
                iFxConfigStorage.update(moveX, moveY);
            }
            BasisHelper basisHelper2 = this.helper;
            if (basisHelper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            FxLog fxLog2 = basisHelper2.fxLog;
            if (fxLog2 == null) {
                return;
            }
            fxLog2.d("fxView-->saveDirection---x-(" + moveX + ")，y-(" + moveY + ')');
        }
    }

    private final void touchToCancel(boolean isFxSelfEvent) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxScrollListener iFxScrollListener = basisHelper.iFxScrollListener;
        if (iFxScrollListener != null) {
            iFxScrollListener.up();
        }
        this.configHelper.setTouchDownId(-1);
        if (isFxSelfEvent) {
            moveToEdge$floatingx_release();
            this.clickHelper.performClick(this);
        }
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper2.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView---onTouchEvent---MainTouchCancel->");
    }

    public static /* synthetic */ void touchToCancel$default(FxManagerView fxManagerView, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        fxManagerView.touchToCancel(z2);
    }

    private final void touchToMove(MotionEvent event) {
        if (this.configHelper.hasMainPointerId()) {
            BasisHelper basisHelper = this.helper;
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (basisHelper.displayMode == FxDisplayMode.Normal) {
                updateLocation(event);
            }
        }
    }

    private final void touchToPointerDown(MotionEvent event) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView---onTouchEvent--touchToPointerDown--id:" + event.getPointerId(event.getActionIndex()) + "->");
        }
        if (!this.configHelper.hasMainPointerId() && FxExtKt.withIn(event.getX(), 0, Integer.valueOf(getWidth())) && FxExtKt.withIn(event.getY(), 0, Integer.valueOf(getHeight()))) {
            initTouchDown(event);
        }
    }

    private final void touchToPointerUp(MotionEvent event, boolean isFxSelfEvent) {
        if (this.configHelper.isCurrentPointerId(event)) {
            touchToCancel(isFxSelfEvent);
            return;
        }
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView---onTouchEvent--ACTION_POINTER_UP---id:" + FxExtKt.getPointerId(event) + "->");
    }

    public static /* synthetic */ void touchToPointerUp$default(FxManagerView fxManagerView, MotionEvent motionEvent, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        fxManagerView.touchToPointerUp(motionEvent, z2);
    }

    private final void updateLocation(MotionEvent event) {
        float fSafeX = this.configHelper.safeX(getX(), event);
        float fSafeY = this.configHelper.safeY(getY(), event);
        setX(fSafeX);
        setY(fSafeY);
        this.clickHelper.checkClickEvent(fSafeX, fSafeY);
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxScrollListener iFxScrollListener = basisHelper.iFxScrollListener;
        if (iFxScrollListener != null) {
            iFxScrollListener.dragIng(event, fSafeX, fSafeY);
        }
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper2.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.v("fxView---scrollListener--drag-event--x(" + fSafeX + ")-y(" + fSafeY + ')');
    }

    @Nullable
    /* renamed from: getChildFxView, reason: from getter */
    public final View get_childFxView() {
        return this._childFxView;
    }

    public final /* synthetic */ FxManagerView init$floatingx_release(BasisHelper config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.helper = config;
        initView();
        return this;
    }

    public final /* synthetic */ void moveLocation$floatingx_release(float x2, float y2, boolean useAnimation) {
        float fSafeX = this.configHelper.safeX(x2);
        float fSafeY = this.configHelper.safeY(y2);
        if (useAnimation) {
            moveToLocation(fSafeX, fSafeY);
        } else {
            setX(x2);
            setY(y2);
        }
    }

    public final /* synthetic */ void moveLocationByVector$floatingx_release(float x2, float y2, boolean useAnimation) {
        moveLocation$floatingx_release(getX() + x2, getY() + y2, useAnimation);
    }

    public final /* synthetic */ void moveToEdge$floatingx_release() {
        this.configHelper.updateBoundary(false);
        Pair<Float, Float> adsorbDirectionLocation = this.configHelper.getAdsorbDirectionLocation(getX(), getY());
        if (adsorbDirectionLocation == null) {
            return;
        }
        float fFloatValue = adsorbDirectionLocation.component1().floatValue();
        float fFloatValue2 = adsorbDirectionLocation.component2().floatValue();
        moveToLocation(fFloatValue, fFloatValue2);
        saveLocationToStorage(fFloatValue, fFloatValue2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxViewLifecycle iFxViewLifecycle = basisHelper.iFxViewLifecycle;
        if (iFxViewLifecycle != null) {
            iFxViewLifecycle.attach();
        }
        ViewParent parent = getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.addOnLayoutChangeListener(this);
        }
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper2.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView-lifecycle-> onAttachedToWindow");
    }

    @Override // android.view.View
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView--lifecycle-> onConfigurationChanged--->");
        }
        if (this.restoreHelper.updateConfig(newConfig)) {
            float x2 = getX();
            float y2 = getY();
            this.restoreHelper.saveLocation(x2, y2, this.configHelper);
            BasisHelper basisHelper2 = this.helper;
            if (basisHelper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            FxLog fxLog2 = basisHelper2.fxLog;
            if (fxLog2 == null) {
                return;
            }
            fxLog2.d("fxView--lifecycle-> saveLocation:[x:" + x2 + ",y:" + y2 + ']');
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxViewLifecycle iFxViewLifecycle = basisHelper.iFxViewLifecycle;
        if (iFxViewLifecycle != null) {
            iFxViewLifecycle.detached();
        }
        ViewParent parent = getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeOnLayoutChangeListener(this);
        }
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper2.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView-lifecycle-> onDetachedFromWindow");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r6) {
        /*
            r5 = this;
            java.lang.String r0 = "ev"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            int r0 = r6.getAction()
            r1 = 0
            java.lang.String r2 = "helper"
            r3 = 0
            if (r0 == 0) goto L50
            r4 = 1
            if (r0 == r4) goto L3a
            r4 = 2
            if (r0 == r4) goto L19
            r4 = 3
            if (r0 == r4) goto L3a
            goto L61
        L19:
            com.petterp.floatingx.view.FxViewConfigHelper r0 = r5.configHelper
            boolean r3 = r0.checkInterceptedEvent(r6)
            com.petterp.floatingx.assist.helper.BasisHelper r6 = r5.helper
            if (r6 == 0) goto L36
            com.petterp.floatingx.util.FxLog r6 = r6.fxLog
            if (r6 != 0) goto L28
            goto L61
        L28:
            java.lang.String r0 = "fxView---onInterceptTouchEvent-[move], interceptedTouch-"
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r3)
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r1)
            r6.d(r0)
            goto L61
        L36:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        L3a:
            r5.touchToPointerUp(r6, r3)
            com.petterp.floatingx.assist.helper.BasisHelper r6 = r5.helper
            if (r6 == 0) goto L4c
            com.petterp.floatingx.util.FxLog r6 = r6.fxLog
            if (r6 != 0) goto L46
            goto L61
        L46:
            java.lang.String r0 = "fxView---onInterceptTouchEvent-[up]"
            r6.d(r0)
            goto L61
        L4c:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        L50:
            r5.initTouchDown(r6)
            com.petterp.floatingx.assist.helper.BasisHelper r6 = r5.helper
            if (r6 == 0) goto L62
            com.petterp.floatingx.util.FxLog r6 = r6.fxLog
            if (r6 != 0) goto L5c
            goto L61
        L5c:
            java.lang.String r0 = "fxView---onInterceptTouchEvent-[down]"
            r6.d(r0)
        L61:
            return r3
        L62:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petterp.floatingx.view.FxManagerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(@Nullable View v2, int left, int top2, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (v2 == null) {
            return;
        }
        refreshLocation(v2.getWidth(), v2.getHeight());
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0030  */
    @Override // android.view.View
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r5) {
        /*
            r4 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            com.petterp.floatingx.assist.helper.BasisHelper r0 = r4.helper
            r1 = 0
            if (r0 == 0) goto L3d
            com.petterp.floatingx.listener.IFxScrollListener r0 = r0.iFxScrollListener
            if (r0 != 0) goto Lf
            goto L12
        Lf:
            r0.eventIng(r5)
        L12:
            int r0 = r5.getActionMasked()
            if (r0 == 0) goto L35
            r2 = 1
            r3 = 2
            if (r0 == r2) goto L30
            if (r0 == r3) goto L2c
            r2 = 3
            if (r0 == r2) goto L30
            r2 = 5
            if (r0 == r2) goto L28
            r2 = 6
            if (r0 == r2) goto L30
            goto L38
        L28:
            r4.touchToPointerDown(r5)
            goto L38
        L2c:
            r4.touchToMove(r5)
            goto L38
        L30:
            r0 = 0
            touchToPointerUp$default(r4, r5, r0, r3, r1)
            goto L38
        L35:
            r4.initTouchDown(r5)
        L38:
            boolean r5 = super.onTouchEvent(r5)
            return r5
        L3d:
            java.lang.String r5 = "helper"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petterp.floatingx.view.FxManagerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        IFxViewLifecycle iFxViewLifecycle = basisHelper.iFxViewLifecycle;
        if (iFxViewLifecycle != null) {
            iFxViewLifecycle.windowsVisibility(visibility);
        }
        BasisHelper basisHelper2 = this.helper;
        if (basisHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        FxLog fxLog = basisHelper2.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView-lifecycle-> onWindowVisibilityChanged");
    }

    public final /* synthetic */ void restoreLocation$floatingx_release(float x2, float y2) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        }
        ((FrameLayout.LayoutParams) layoutParams).gravity = FxGravity.DEFAULT.getValue();
        setX(x2);
        setY(y2);
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener l2) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        basisHelper.iFxClickListener = l2;
        if (basisHelper != null) {
            basisHelper.enableClickListener = true;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
    }

    public final /* synthetic */ void updateDisplayMode$floatingx_release() {
        BasisHelper basisHelper = this.helper;
        if (basisHelper != null) {
            setClickable(basisHelper.displayMode != FxDisplayMode.DisplayOnly);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FxManagerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.clickHelper = new FxClickHelper();
        this.restoreHelper = new FxLocationHelper();
        this.configHelper = new FxViewConfigHelper();
    }
}
