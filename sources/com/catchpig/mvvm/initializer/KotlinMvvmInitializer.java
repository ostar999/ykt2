package com.catchpig.mvvm.initializer;

import android.app.Application;
import android.content.Context;
import androidx.startup.Initializer;
import com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.utils.LogUtils;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.toast.Toaster;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.pro.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u0007J\u001a\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00010\n0\tH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0002J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/initializer/KotlinMvvmInitializer;", "Landroidx/startup/Initializer;", "", "()V", "create", d.R, "Landroid/content/Context;", "(Landroid/content/Context;)Ljava/lang/Boolean;", "dependencies", "", "Ljava/lang/Class;", "initContext", "", "applicationContext", "initLog", "initX5", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class KotlinMvvmInitializer implements Initializer<Boolean> {
    private final void initContext(Context applicationContext) {
        ContextManager.INSTANCE.getInstance().init(applicationContext);
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type android.app.Application");
        Application application = (Application) applicationContext;
        ActivityLifeCycleCallbacksImpl.INSTANCE.init(application);
        Toaster.init(application);
    }

    private final void initLog(Context context) {
        LogUtils.INSTANCE.getInstance().init(context);
    }

    private final void initX5(Context context) {
        HashMap map = new HashMap();
        Boolean bool = Boolean.TRUE;
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, bool);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, bool);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(context, new QbSdk.PreInitCallback() { // from class: com.catchpig.mvvm.initializer.KotlinMvvmInitializer.initX5.1
            @Override // com.tencent.smtt.sdk.QbSdk.PreInitCallback
            public void onCoreInitFinished() {
            }

            @Override // com.tencent.smtt.sdk.QbSdk.PreInitCallback
            public void onViewInitFinished(boolean x5) {
                LogExtKt.logd("x5内核初始化完成，是否启用x5：" + x5, "KotlinMvvmInitializer");
            }
        });
    }

    @Override // androidx.startup.Initializer
    @NotNull
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    @NotNull
    public Boolean create(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        initContext(applicationContext);
        DataStoreUtils.INSTANCE.init(applicationContext);
        initLog(applicationContext);
        return Boolean.TRUE;
    }
}
