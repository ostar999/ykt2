package com.petterp.floatingx.impl.control;

import android.annotation.NonNull;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.FloatingX;
import com.petterp.floatingx.assist.FxAnimation;
import com.petterp.floatingx.assist.helper.AppHelper;
import com.petterp.floatingx.impl.lifecycle.FxProxyLifecycleCallBackImpl;
import com.petterp.floatingx.listener.IFxViewLifecycle;
import com.petterp.floatingx.listener.control.IFxAppControl;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.util.FxUiExtKt;
import com.petterp.floatingx.view.FxManagerView;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\n\u0010\u0017\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u0018\u001a\u00020\u0011H\u0014J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\u001f\u0010\u001a\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000e2\n\b\u0001\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0001J\u0013\u0010\u001e\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000eH\u0096\u0001J\u0013\u0010\u001f\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000eH\u0096\u0001J\u0013\u0010 \u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000eH\u0096\u0001J\u001d\u0010!\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000e2\b\b\u0001\u0010\u001c\u001a\u00020\u001dH\u0096\u0001J\u0013\u0010\"\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000eH\u0096\u0001J\u0013\u0010#\u001a\u00020\u00112\b\b\u0001\u0010\u001b\u001a\u00020\u000eH\u0096\u0001J\b\u0010$\u001a\u00020\u0011H\u0014J\u0010\u0010%\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010&\u001a\u00020\u00112\u0006\u0010'\u001a\u00020(H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/petterp/floatingx/impl/control/FxAppControlImpl;", "Lcom/petterp/floatingx/impl/control/FxBasisControlImpl;", "Lcom/petterp/floatingx/listener/control/IFxAppControl;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "helper", "Lcom/petterp/floatingx/assist/helper/AppHelper;", "proxyLifecycleImpl", "Lcom/petterp/floatingx/impl/lifecycle/FxProxyLifecycleCallBackImpl;", "(Lcom/petterp/floatingx/assist/helper/AppHelper;Lcom/petterp/floatingx/impl/lifecycle/FxProxyLifecycleCallBackImpl;)V", "windowsInsetsListener", "Landroidx/core/view/OnApplyWindowInsetsListener;", "attach", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "attach$floatingx_release", "clearWindowsInsetsListener", "", d.R, "Landroid/content/Context;", "detach", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "getBindActivity", "initManager", "initWindowsInsetsListener", "onActivityCreated", "p0", "p1", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "onActivityStarted", "onActivityStopped", "reset", "show", "updateView", "view", "Landroid/view/View;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxAppControlImpl extends FxBasisControlImpl implements IFxAppControl, Application.ActivityLifecycleCallbacks {

    @NotNull
    private final AppHelper helper;

    @NotNull
    private final FxProxyLifecycleCallBackImpl proxyLifecycleImpl;

    @NotNull
    private final OnApplyWindowInsetsListener windowsInsetsListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FxAppControlImpl(@NotNull AppHelper helper, @NotNull FxProxyLifecycleCallBackImpl proxyLifecycleImpl) {
        super(helper);
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(proxyLifecycleImpl, "proxyLifecycleImpl");
        this.helper = helper;
        this.proxyLifecycleImpl = proxyLifecycleImpl;
        proxyLifecycleImpl.init(helper, this);
        this.windowsInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.petterp.floatingx.impl.control.a
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return FxAppControlImpl.m97windowsInsetsListener$lambda0(this.f10725a, view, windowInsetsCompat);
            }
        };
    }

    private final void clearWindowsInsetsListener() {
        FxManagerView managerView = getManagerView();
        if (managerView == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(managerView, null);
    }

    private final void initWindowsInsetsListener() {
        FxManagerView managerView = getManagerView();
        if (managerView == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(managerView, this.windowsInsetsListener);
        managerView.requestApplyInsets();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: windowsInsetsListener$lambda-0, reason: not valid java name */
    public static final WindowInsetsCompat m97windowsInsetsListener$lambda0(FxAppControlImpl this$0, View view, WindowInsetsCompat windowInsetsCompat) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int stableInsetTop = windowInsetsCompat.getStableInsetTop();
        AppHelper appHelper = this$0.helper;
        if (appHelper.statsBarHeight != stableInsetTop) {
            FxLog fxLog = appHelper.fxLog;
            if (fxLog != null) {
                fxLog.v("System--StatusBar---old-(" + this$0.helper.statsBarHeight + "),new-(" + stableInsetTop + "))");
            }
            this$0.helper.statsBarHeight = stableInsetTop;
        }
        return windowInsetsCompat;
    }

    public final boolean attach$floatingx_release(@NotNull Activity activity) {
        FxManagerView managerView;
        Unit unit;
        FxLog fxLog;
        Intrinsics.checkNotNullParameter(activity, "activity");
        FrameLayout decorView = FxUiExtKt.getDecorView(activity);
        if (decorView == null) {
            unit = null;
        } else {
            boolean z2 = false;
            if (getContainerGroup() == decorView) {
                return false;
            }
            if (getManagerView() == null) {
                this.helper.updateNavigationBar$floatingx_release(activity);
                this.helper.updateStatsBar$floatingx_release(activity);
                initManagerView();
                z2 = true;
            } else {
                FxManagerView managerView2 = getManagerView();
                if (!(managerView2 != null && managerView2.getVisibility() == 0) && (managerView = getManagerView()) != null) {
                    managerView.setVisibility(0);
                }
                detach();
            }
            setContainerGroup$floatingx_release(decorView);
            FxLog fxLog2 = this.helper.fxLog;
            if (fxLog2 != null) {
                fxLog2.d("fxView-lifecycle-> code->addView");
            }
            IFxViewLifecycle iFxViewLifecycle = this.helper.iFxViewLifecycle;
            if (iFxViewLifecycle != null) {
                iFxViewLifecycle.postAttach();
            }
            ViewGroup containerGroup = getContainerGroup();
            if (containerGroup != null) {
                containerGroup.addView(getManagerView());
            }
            if (z2) {
                AppHelper appHelper = this.helper;
                if (appHelper.enableAnimation && appHelper.fxAnimation != null) {
                    FxLog fxLog3 = appHelper.fxLog;
                    if (fxLog3 != null) {
                        fxLog3.d("fxView->Animation -----start");
                    }
                    FxAnimation fxAnimation = this.helper.fxAnimation;
                    if (fxAnimation != null) {
                        fxAnimation.fromStartAnimator$floatingx_release(getManagerView());
                    }
                }
            }
            unit = Unit.INSTANCE;
        }
        if (unit == null && (fxLog = this.helper.fxLog) != null) {
            fxLog.e("system -> fxParentView==null");
        }
        return true;
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl
    @NotNull
    public Context context() {
        Application context$floatingx_release = FloatingX.INSTANCE.getContext$floatingx_release();
        Intrinsics.checkNotNull(context$floatingx_release);
        return context$floatingx_release;
    }

    @Override // com.petterp.floatingx.listener.control.IFxAppControl
    public void detach(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        FrameLayout decorView = FxUiExtKt.getDecorView(activity);
        if (decorView == null) {
            return;
        }
        detach(decorView);
    }

    @Override // com.petterp.floatingx.listener.control.IFxAppControl
    @Nullable
    public Activity getBindActivity() {
        ViewGroup containerGroup = getContainerGroup();
        Activity topActivity = FxUiExtKt.getTopActivity();
        if (containerGroup == (topActivity == null ? null : FxUiExtKt.getDecorView(topActivity))) {
            return FxUiExtKt.getTopActivity();
        }
        return null;
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl
    public void initManager() {
        clearWindowsInsetsListener();
        super.initManager();
        initWindowsInsetsListener();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NonNull @NotNull Activity p02, @android.annotation.Nullable @Nullable Bundle p12) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityCreated(p02, p12);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NonNull @NotNull Activity p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityDestroyed(p02);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NonNull @NotNull Activity p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityPaused(p02);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NonNull @NotNull Activity p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityResumed(p02);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NonNull @NotNull Activity p02, @NonNull @NotNull Bundle p12) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        Intrinsics.checkNotNullParameter(p12, "p1");
        this.proxyLifecycleImpl.onActivitySaveInstanceState(p02, p12);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NonNull @NotNull Activity p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityStarted(p02);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(@NonNull @NotNull Activity p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        this.proxyLifecycleImpl.onActivityStopped(p02);
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl
    public void reset() {
        clearWindowsInsetsListener();
        super.reset();
        FloatingX.INSTANCE.uninstall$floatingx_release(this.helper.getTag(), this);
    }

    @Override // com.petterp.floatingx.listener.control.IFxAppControl
    public void show(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (this.helper.isCanInstall$floatingx_release(activity) && !isShow() && attach$floatingx_release(activity)) {
            FxManagerView managerView = getManagerView();
            if (managerView != null) {
                show(managerView);
            }
            updateEnableStatus(true);
            FloatingX.checkAppLifecycleInstall$floatingx_release$default(FloatingX.INSTANCE, null, 1, null);
        }
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl, com.petterp.floatingx.listener.control.IFxControl
    public void updateView(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (!(view.getContext() instanceof Application)) {
            throw new IllegalArgumentException("view.context != Application,The global floating window must use application as context!");
        }
        super.updateView(view);
    }

    @Override // com.petterp.floatingx.impl.control.FxBasisControlImpl
    public void detach(@Nullable ViewGroup container) {
        super.detach(container);
        clearContainer();
    }
}
