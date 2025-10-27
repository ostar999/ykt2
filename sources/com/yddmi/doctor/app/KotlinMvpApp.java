package com.yddmi.doctor.app;

import android.content.Context;
import com.catchpig.download.manager.DownloadManager;
import com.catchpig.mvvm.network.manager.NetManager;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.utils.LogUtils;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.umeng.analytics.pro.d;
import com.yddmi.doctor.R;
import com.yddmi.doctor.app.KotlinMvpApp;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/app/KotlinMvpApp;", "", "()V", "applicationContext", "Landroid/content/Context;", "initDownload", "", "onCreate", d.R, "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class KotlinMvpApp {

    @NotNull
    public static final KotlinMvpApp INSTANCE = new KotlinMvpApp();
    private static Context applicationContext;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() { // from class: p1.a
            @Override // com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
            public final RefreshHeader createRefreshHeader(Context context, RefreshLayout refreshLayout) {
                return KotlinMvpApp._init_$lambda$0(context, refreshLayout);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() { // from class: p1.b
            @Override // com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
            public final RefreshFooter createRefreshFooter(Context context, RefreshLayout refreshLayout) {
                return KotlinMvpApp._init_$lambda$1(context, refreshLayout);
            }
        });
    }

    private KotlinMvpApp() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final RefreshHeader _init_$lambda$0(Context context, RefreshLayout layout) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(layout, "layout");
        layout.setPrimaryColorsId(R.color.color_333);
        return new MaterialHeader(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final RefreshFooter _init_$lambda$1(Context context, RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(refreshLayout, "<anonymous parameter 1>");
        return new ClassicsFooter(context).setDrawableSize(20.0f);
    }

    private final void initDownload() {
        Context context = applicationContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
            context = null;
        }
        File cacheDir = context.getCacheDir();
        Intrinsics.checkNotNull(cacheDir);
        DownloadManager.INSTANCE.setDownloadPath(String.valueOf(cacheDir.getAbsolutePath()));
    }

    public final void onCreate(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        applicationContext = context;
        try {
            ContextManager companion = ContextManager.INSTANCE.getInstance();
            Context context2 = applicationContext;
            Context context3 = null;
            if (context2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                context2 = null;
            }
            companion.init(context2);
            DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
            Context context4 = applicationContext;
            if (context4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
            } else {
                context3 = context4;
            }
            dataStoreUtils.init(context3);
            boolean zIsApkDebugable = AppInformationUtil.isApkDebugable();
            LogExtKt.logd("debug状态 " + zIsApkDebugable, YddConfig.TAG);
            YddHostConfig.INSTANCE.getInstance();
            LogUtils.INSTANCE.getInstance().showLineNumber(zIsApkDebugable);
            NetManager.INSTANCE.getInstance().setDebug(zIsApkDebugable);
            initDownload();
            YddUserManager.INSTANCE.getInstance().registToWx();
        } catch (Throwable th) {
            LogExtKt.loge("onCreate " + th, YddConfig.TAG);
        }
    }
}
