package com.petterp.floatingx.impl.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.ViewParent;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.assist.helper.AppHelper;
import com.petterp.floatingx.impl.control.FxAppControlImpl;
import com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.util.FxUiExtKt;
import com.petterp.floatingx.view.FxManagerView;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\"\u001a\u00020#2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0004J\u0014\u0010%\u001a\u00020\n2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0002J\u001a\u0010'\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001b2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0010\u0010+\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001bH\u0016J\u0010\u0010,\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001bH\u0016J\u0010\u0010-\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001bH\u0016J\u0018\u0010.\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001b2\u0006\u0010/\u001a\u00020*H\u0016J\u0010\u00100\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001bH\u0016J\u0010\u00101\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010\u0013\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0015\u0012\u0004\u0012\u00020\n0\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u001a\u001a\u00020\n*\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\n*\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001cR\u0018\u0010\u001e\u001a\u00020\u001f*\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u00062"}, d2 = {"Lcom/petterp/floatingx/impl/lifecycle/FxProxyLifecycleCallBackImpl;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "()V", "appControl", "Lcom/petterp/floatingx/impl/control/FxAppControlImpl;", "appLifecycleCallBack", "Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "getAppLifecycleCallBack", "()Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "enableFx", "", "getEnableFx", "()Z", "fxLog", "Lcom/petterp/floatingx/util/FxLog;", "getFxLog", "()Lcom/petterp/floatingx/util/FxLog;", "helper", "Lcom/petterp/floatingx/assist/helper/AppHelper;", "insertCls", "", "Ljava/lang/Class;", "getInsertCls", "()Ljava/util/Map;", "insertCls$delegate", "Lkotlin/Lazy;", "isActivityInValid", "Landroid/app/Activity;", "(Landroid/app/Activity;)Z", "isParent", "name", "", "getName", "(Landroid/app/Activity;)Ljava/lang/String;", "init", "", SessionDescription.ATTR_CONTROL, "isInsertActivity", "cls", "onActivityCreated", PushConstants.INTENT_ACTIVITY_NAME, "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxProxyLifecycleCallBackImpl implements Application.ActivityLifecycleCallbacks {

    @Nullable
    private FxAppControlImpl appControl;

    @Nullable
    private AppHelper helper;

    /* renamed from: insertCls$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy insertCls = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<Class<?>, Boolean>>() { // from class: com.petterp.floatingx.impl.lifecycle.FxProxyLifecycleCallBackImpl$special$$inlined$lazyLoad$default$1
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Map<Class<?>, Boolean> invoke() {
            return new LinkedHashMap();
        }
    });

    private final IFxProxyTagActivityLifecycle getAppLifecycleCallBack() {
        AppHelper appHelper = this.helper;
        if (appHelper == null) {
            return null;
        }
        return appHelper.getFxLifecycleExpand();
    }

    private final boolean getEnableFx() {
        AppHelper appHelper = this.helper;
        if (appHelper == null) {
            return false;
        }
        return appHelper.enableFx;
    }

    private final FxLog getFxLog() {
        AppHelper appHelper = this.helper;
        if (appHelper == null) {
            return null;
        }
        return appHelper.fxLog;
    }

    private final Map<Class<?>, Boolean> getInsertCls() {
        return (Map) this.insertCls.getValue();
    }

    private final String getName(Activity activity) {
        String name = activity.getClass().getName();
        Intrinsics.checkNotNullExpressionValue(name, "javaClass.name");
        return (String) CollectionsKt___CollectionsKt.last(StringsKt__StringsKt.split$default((CharSequence) name, new String[]{StrPool.DOT}, false, 0, 6, (Object) null));
    }

    private final boolean isActivityInValid(Activity activity) {
        Class<?> cls = activity.getClass();
        Boolean bool = getInsertCls().get(cls);
        return bool == null ? isInsertActivity(cls) : bool.booleanValue();
    }

    private final boolean isInsertActivity(Class<?> cls) {
        AppHelper appHelper = this.helper;
        if (appHelper == null) {
            return false;
        }
        boolean zIsCanInstall$floatingx_release = appHelper.isCanInstall$floatingx_release(cls);
        getInsertCls().put(cls, Boolean.valueOf(zIsCanInstall$floatingx_release));
        return zIsCanInstall$floatingx_release;
    }

    private final boolean isParent(Activity activity) {
        FxManagerView managerView;
        FxAppControlImpl fxAppControlImpl = this.appControl;
        ViewParent parent = null;
        if (fxAppControlImpl != null && (managerView = fxAppControlImpl.getManagerView()) != null) {
            parent = managerView.getParent();
        }
        return parent == FxUiExtKt.getDecorView(activity);
    }

    public final void init(@NotNull AppHelper helper, @NotNull FxAppControlImpl control) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(control, "control");
        this.helper = helper;
        this.appControl = control;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        IFxProxyTagActivityLifecycle appLifecycleCallBack;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (getEnableFx() && getAppLifecycleCallBack() != null && isActivityInValid(activity) && (appLifecycleCallBack = getAppLifecycleCallBack()) != null) {
            appLifecycleCallBack.onCreated(activity, savedInstanceState);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NotNull Activity activity) {
        FxAppControlImpl fxAppControlImpl;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (getEnableFx()) {
            IFxProxyTagActivityLifecycle appLifecycleCallBack = getAppLifecycleCallBack();
            if (appLifecycleCallBack != null && isActivityInValid(activity)) {
                appLifecycleCallBack.onDestroyed(activity);
            }
            boolean zIsParent = isParent(activity);
            FxLog fxLog = getFxLog();
            if (fxLog != null) {
                fxLog.d("fxApp->detach? isContainActivity-" + isActivityInValid(activity) + "--enableFx-" + getEnableFx() + "---isParent-" + zIsParent);
            }
            if (!zIsParent || (fxAppControlImpl = this.appControl) == null) {
                return;
            }
            fxAppControlImpl.detach(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NotNull Activity activity) {
        IFxProxyTagActivityLifecycle appLifecycleCallBack;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (getEnableFx() && (appLifecycleCallBack = getAppLifecycleCallBack()) != null && isActivityInValid(activity)) {
            appLifecycleCallBack.onPaused(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NotNull Activity activity) {
        FxLog fxLog;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (getEnableFx()) {
            String name = getName(activity);
            FxLog fxLog2 = getFxLog();
            if (fxLog2 != null) {
                fxLog2.d("fxApp->insert, insert [" + name + "] Start ---------->");
            }
            if (!isActivityInValid(activity)) {
                FxLog fxLog3 = getFxLog();
                if (fxLog3 == null) {
                    return;
                }
                fxLog3.d("fxApp->insert, insert [" + name + "] Fail ,This activity is not in the list of allowed inserts.");
                return;
            }
            IFxProxyTagActivityLifecycle appLifecycleCallBack = getAppLifecycleCallBack();
            if (appLifecycleCallBack != null) {
                appLifecycleCallBack.onResumes(activity);
            }
            if (isParent(activity)) {
                FxLog fxLog4 = getFxLog();
                if (fxLog4 == null) {
                    return;
                }
                fxLog4.d("fxApp->insert, insert [" + name + "] Fail ,The current Activity has been inserted.");
                return;
            }
            FxAppControlImpl fxAppControlImpl = this.appControl;
            Unit unit = null;
            if (fxAppControlImpl != null) {
                fxAppControlImpl.attach$floatingx_release(activity);
                FxLog fxLog5 = getFxLog();
                if (fxLog5 != null) {
                    fxLog5.d("fxApp->insert, insert [" + name + "] Success--------------->");
                    unit = Unit.INSTANCE;
                }
            }
            if (unit != null || (fxLog = getFxLog()) == null) {
                return;
            }
            fxLog.d("fxApp->insert, insert [" + name + "] Fail ,appControl = null.");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
        IFxProxyTagActivityLifecycle appLifecycleCallBack;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(outState, "outState");
        if (getEnableFx() && (appLifecycleCallBack = getAppLifecycleCallBack()) != null && isActivityInValid(activity)) {
            appLifecycleCallBack.onSaveInstanceState(activity, outState);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NotNull Activity activity) {
        AppHelper appHelper;
        IFxProxyTagActivityLifecycle fxLifecycleExpand;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (!getEnableFx() || (appHelper = this.helper) == null || (fxLifecycleExpand = appHelper.getFxLifecycleExpand()) == null) {
            return;
        }
        fxLifecycleExpand.onStarted(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(@NotNull Activity activity) {
        IFxProxyTagActivityLifecycle appLifecycleCallBack;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (getEnableFx() && (appLifecycleCallBack = getAppLifecycleCallBack()) != null && isActivityInValid(activity)) {
            appLifecycleCallBack.onStopped(activity);
        }
    }
}
