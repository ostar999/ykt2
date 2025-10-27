package com.petterp.floatingx.view;

import android.view.View;
import androidx.annotation.Keep;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.view.FxClickHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bJ\b\u0010\u0013\u001a\u00020\u0004H\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\b\u0010\u0017\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/petterp/floatingx/view/FxClickHelper;", "", "()V", "clickEnable", "", "helper", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "initX", "", "initY", "isClickEvent", "mLastTouchDownTime", "", "checkClickEvent", "", "x", "y", "initConfig", "initDown", "isClickEffective", "performClick", "view", "Lcom/petterp/floatingx/view/FxManagerView;", "reset", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxClickHelper {
    private boolean clickEnable = true;
    private BasisHelper helper;
    private float initX;
    private float initY;
    private boolean isClickEvent;
    private long mLastTouchDownTime;

    private final boolean isClickEffective() {
        if (this.isClickEvent && this.clickEnable) {
            BasisHelper basisHelper = this.helper;
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (basisHelper.enableClickListener) {
                if (basisHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("helper");
                    throw null;
                }
                if (basisHelper.iFxClickListener != null && System.currentTimeMillis() - this.mLastTouchDownTime < 150) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: performClick$lambda-0, reason: not valid java name */
    public static final void m98performClick$lambda0(FxClickHelper this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.clickEnable = true;
    }

    private final void reset() {
        this.initX = 0.0f;
        this.initY = 0.0f;
        this.isClickEvent = false;
        this.mLastTouchDownTime = 0L;
    }

    public final void checkClickEvent(float x2, float y2) {
        if (this.isClickEvent) {
            this.isClickEvent = Math.abs(x2 - this.initX) < 2.0f && Math.abs(y2 - this.initY) < 2.0f;
        }
    }

    public final void initConfig(@NotNull BasisHelper helper) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        reset();
        this.helper = helper;
    }

    public final void initDown(float x2, float y2) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
            throw null;
        }
        if (basisHelper.enableClickListener) {
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (basisHelper.iFxClickListener == null) {
                return;
            }
            this.initX = x2;
            this.initY = y2;
            this.isClickEvent = true;
            this.mLastTouchDownTime = System.currentTimeMillis();
        }
    }

    @Keep
    public final void performClick(@NotNull FxManagerView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (isClickEffective()) {
            BasisHelper basisHelper = this.helper;
            if (basisHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            View.OnClickListener onClickListener = basisHelper.iFxClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            BasisHelper basisHelper2 = this.helper;
            if (basisHelper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            if (basisHelper2.clickTime > 0) {
                this.clickEnable = false;
                Runnable runnable = new Runnable() { // from class: j1.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        FxClickHelper.m98performClick$lambda0(this.f27520c);
                    }
                };
                BasisHelper basisHelper3 = this.helper;
                if (basisHelper3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("helper");
                    throw null;
                }
                view.postDelayed(runnable, basisHelper3.clickTime);
            }
            BasisHelper basisHelper4 = this.helper;
            if (basisHelper4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("helper");
                throw null;
            }
            FxLog fxLog = basisHelper4.fxLog;
            if (fxLog != null) {
                fxLog.d("fxView -> click");
            }
            reset();
        }
    }
}
