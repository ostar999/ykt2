package com.petterp.floatingx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.assist.helper.AppHelper;
import com.petterp.floatingx.impl.control.FxAppControlImpl;
import com.petterp.floatingx.impl.lifecycle.FxLifecycleCallbackImpl;
import com.petterp.floatingx.impl.lifecycle.FxProxyLifecycleCallBackImpl;
import com.petterp.floatingx.listener.control.IFxAppControl;
import com.petterp.floatingx.listener.control.IFxConfigControl;
import com.umeng.analytics.pro.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0000¢\u0006\u0002\b\u0017J\u0012\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0006H\u0007J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0006H\u0007J\u0012\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001a\u001a\u00020\u0006H\u0007J\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\b\b\u0002\u0010\u001a\u001a\u00020\u0006H\u0007J\u0019\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110 H\u0000¢\u0006\u0002\b!J\u0010\u0010\"\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0006H\u0002J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020%H\u0007J%\u0010#\u001a\u00020\u001d2\u0017\u0010&\u001a\u0013\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00140'¢\u0006\u0002\b)H\u0086\bø\u0001\u0000J\u0012\u0010*\u001a\u00020+2\b\b\u0002\u0010\u001a\u001a\u00020\u0006H\u0007J\b\u0010,\u001a\u00020\u0014H\u0002J\u001d\u0010-\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0011H\u0000¢\u0006\u0002\b.J\b\u0010/\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\u0010j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00060"}, d2 = {"Lcom/petterp/floatingx/FloatingX;", "", "()V", "FX_DEFAULT_INITIAL_CAPACITY", "", FloatingX.FX_DEFAULT_TAG, "", d.R, "Landroid/app/Application;", "getContext$floatingx_release", "()Landroid/app/Application;", "setContext$floatingx_release", "(Landroid/app/Application;)V", "fxLifecycleCallback", "Lcom/petterp/floatingx/impl/lifecycle/FxLifecycleCallbackImpl;", "fxs", "Ljava/util/HashMap;", "Lcom/petterp/floatingx/impl/control/FxAppControlImpl;", "Lkotlin/collections/HashMap;", "checkAppLifecycleInstall", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "checkAppLifecycleInstall$floatingx_release", "configControl", "Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "tag", "configControlOrNull", SessionDescription.ATTR_CONTROL, "Lcom/petterp/floatingx/listener/control/IFxAppControl;", "controlOrNull", "getFxList", "", "getFxList$floatingx_release", "getTagFxControl", "install", "helper", "Lcom/petterp/floatingx/assist/helper/AppHelper;", "obj", "Lkotlin/Function1;", "Lcom/petterp/floatingx/assist/helper/AppHelper$Builder;", "Lkotlin/ExtensionFunctionType;", "isInstalled", "", "release", "uninstall", "uninstall$floatingx_release", "uninstallAll", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
@SuppressLint({"StaticFieldLeak"})
/* loaded from: classes4.dex */
public final class FloatingX {
    private static final int FX_DEFAULT_INITIAL_CAPACITY = 3;
    public static final /* synthetic */ String FX_DEFAULT_TAG = "FX_DEFAULT_TAG";
    private static /* synthetic */ Application context;

    @Nullable
    private static FxLifecycleCallbackImpl fxLifecycleCallback;

    @NotNull
    public static final FloatingX INSTANCE = new FloatingX();

    @NotNull
    private static HashMap<String, FxAppControlImpl> fxs = new HashMap<>(3);

    private FloatingX() {
    }

    public static /* synthetic */ void checkAppLifecycleInstall$floatingx_release$default(FloatingX floatingX, Activity activity, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            activity = null;
        }
        floatingX.checkAppLifecycleInstall$floatingx_release(activity);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final IFxConfigControl configControl() {
        return configControl$default(null, 1, null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final IFxConfigControl configControl(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return INSTANCE.getTagFxControl(tag).getConfigControl();
    }

    public static /* synthetic */ IFxConfigControl configControl$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = FX_DEFAULT_TAG;
        }
        return configControl(str);
    }

    @JvmStatic
    @JvmOverloads
    @Nullable
    public static final IFxConfigControl configControlOrNull() {
        return configControlOrNull$default(null, 1, null);
    }

    @JvmStatic
    @JvmOverloads
    @Nullable
    public static final IFxConfigControl configControlOrNull(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        FxAppControlImpl fxAppControlImpl = fxs.get(tag);
        if (fxAppControlImpl == null) {
            return null;
        }
        return fxAppControlImpl.getConfigControl();
    }

    public static /* synthetic */ IFxConfigControl configControlOrNull$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = FX_DEFAULT_TAG;
        }
        return configControlOrNull(str);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final IFxAppControl control() {
        return control$default(null, 1, null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final IFxAppControl control(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return INSTANCE.getTagFxControl(tag);
    }

    public static /* synthetic */ IFxAppControl control$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = FX_DEFAULT_TAG;
        }
        return control(str);
    }

    @JvmStatic
    @JvmOverloads
    @Nullable
    public static final IFxAppControl controlOrNull() {
        return controlOrNull$default(null, 1, null);
    }

    @JvmStatic
    @JvmOverloads
    @Nullable
    public static final IFxAppControl controlOrNull(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return fxs.get(tag);
    }

    public static /* synthetic */ IFxAppControl controlOrNull$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = FX_DEFAULT_TAG;
        }
        return controlOrNull(str);
    }

    private final FxAppControlImpl getTagFxControl(String tag) {
        String str = "fxs[" + tag + "]==null!,Please check if FloatingX.install() or AppHelper.setTag() is called.";
        FxAppControlImpl fxAppControlImpl = fxs.get(tag);
        if (fxAppControlImpl != null) {
            return fxAppControlImpl;
        }
        throw new NullPointerException(str);
    }

    @JvmStatic
    @JvmOverloads
    public static final boolean isInstalled() {
        return isInstalled$default(null, 1, null);
    }

    @JvmStatic
    @JvmOverloads
    public static final boolean isInstalled(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return fxs.get(tag) != null;
    }

    public static /* synthetic */ boolean isInstalled$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = FX_DEFAULT_TAG;
        }
        return isInstalled(str);
    }

    private final void release() {
        if (fxLifecycleCallback == null && FxLifecycleCallbackImpl.INSTANCE.getTopActivity$floatingx_release() == null) {
            return;
        }
        Application application = context;
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(fxLifecycleCallback);
        }
        FxLifecycleCallbackImpl.INSTANCE.releaseTopActivity$floatingx_release();
        fxLifecycleCallback = null;
    }

    @JvmStatic
    public static final void uninstallAll() {
        if (fxs.isEmpty()) {
            return;
        }
        Set<String> setKeySet = fxs.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "fxs.keys");
        Iterator it = CollectionsKt___CollectionsKt.toList(setKeySet).iterator();
        while (it.hasNext()) {
            FxAppControlImpl fxAppControlImpl = fxs.get((String) it.next());
            if (fxAppControlImpl != null) {
                fxAppControlImpl.cancel();
            }
        }
    }

    public final /* synthetic */ void checkAppLifecycleInstall$floatingx_release(Activity activity) {
        if (fxLifecycleCallback != null) {
            return;
        }
        FxLifecycleCallbackImpl.INSTANCE.updateTopActivity$floatingx_release(activity);
        FxLifecycleCallbackImpl fxLifecycleCallbackImpl = new FxLifecycleCallbackImpl();
        fxLifecycleCallback = fxLifecycleCallbackImpl;
        Application application = context;
        if (application == null) {
            return;
        }
        application.registerActivityLifecycleCallbacks(fxLifecycleCallbackImpl);
    }

    @Nullable
    public final Application getContext$floatingx_release() {
        return context;
    }

    public final /* synthetic */ Map getFxList$floatingx_release() {
        return fxs;
    }

    public final /* synthetic */ IFxAppControl install(Function1<? super AppHelper.Builder, Unit> obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        AppHelper.Builder builder = AppHelper.INSTANCE.builder();
        obj.invoke(builder);
        return install(builder.build());
    }

    public final void setContext$floatingx_release(@Nullable Application application) {
        context = application;
    }

    public final /* synthetic */ void uninstall$floatingx_release(String tag, FxAppControlImpl control) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(control, "control");
        if (fxs.values().contains(control)) {
            fxs.remove(tag);
        }
        if (fxs.isEmpty()) {
            release();
        }
    }

    @JvmStatic
    @NotNull
    public static final IFxAppControl install(@NotNull AppHelper helper) {
        FxAppControlImpl fxAppControlImpl;
        Intrinsics.checkNotNullParameter(helper, "helper");
        if (context == null) {
            throw new NullPointerException("context == null, please call AppHelper.setContext(context) to set context");
        }
        if ((!fxs.isEmpty()) && (fxAppControlImpl = fxs.get(helper.getTag())) != null) {
            fxAppControlImpl.cancel();
        }
        FxAppControlImpl fxAppControlImpl2 = new FxAppControlImpl(helper, new FxProxyLifecycleCallBackImpl());
        fxs.put(helper.getTag(), fxAppControlImpl2);
        if (helper.enableFx) {
            checkAppLifecycleInstall$floatingx_release$default(INSTANCE, null, 1, null);
        }
        return fxAppControlImpl2;
    }
}
