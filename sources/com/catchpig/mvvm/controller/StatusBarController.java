package com.catchpig.mvvm.controller;

import android.app.Activity;
import com.catchpig.mvvm.entity.StatusBarParam;
import com.catchpig.mvvm.entity.TitleParam;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/catchpig/mvvm/controller/StatusBarController;", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "title", "Lcom/catchpig/mvvm/entity/TitleParam;", "statusBar", "Lcom/catchpig/mvvm/entity/StatusBarParam;", "(Landroid/app/Activity;Lcom/catchpig/mvvm/entity/TitleParam;Lcom/catchpig/mvvm/entity/StatusBarParam;)V", "globalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "checkStatusBar", "", "checkStatusBarHide", "immersionBar", "Lcom/gyf/immersionbar/ImmersionBar;", "checkStatusBarTransparent", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStatusBarController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatusBarController.kt\ncom/catchpig/mvvm/controller/StatusBarController\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,75:1\n18#2,2:76\n1#3:78\n*S KotlinDebug\n*F\n+ 1 StatusBarController.kt\ncom/catchpig/mvvm/controller/StatusBarController\n*L\n31#1:76,2\n31#1:78\n*E\n"})
/* loaded from: classes2.dex */
public final class StatusBarController {

    @NotNull
    private final Activity activity;

    @NotNull
    private final IGlobalConfig globalConfig;

    @Nullable
    private final StatusBarParam statusBar;

    @Nullable
    private final TitleParam title;

    public StatusBarController(@NotNull Activity activity, @Nullable TitleParam titleParam, @Nullable StatusBarParam statusBarParam) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.title = titleParam;
        this.statusBar = statusBarParam;
        this.globalConfig = KotlinMvvmCompiler.INSTANCE.globalConfig();
    }

    private final void checkStatusBarHide(ImmersionBar immersionBar) {
        StatusBarParam statusBarParam = this.statusBar;
        Intrinsics.checkNotNull(statusBarParam);
        if (statusBarParam.getHide()) {
            immersionBar.hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        } else {
            checkStatusBarTransparent(immersionBar);
        }
    }

    private final void checkStatusBarTransparent(ImmersionBar immersionBar) {
        StatusBarParam statusBarParam = this.statusBar;
        Intrinsics.checkNotNull(statusBarParam);
        if (statusBarParam.getTransparent()) {
            immersionBar.transparentStatusBar();
        } else {
            immersionBar.fitsSystemWindows(true);
            immersionBar.autoStatusBarDarkModeEnable(true, 0.2f);
        }
    }

    public final void checkStatusBar() {
        StatusBarParam statusBarParam = this.statusBar;
        if (statusBarParam == null || !statusBarParam.getEnabled()) {
            ImmersionBar immersionBarWith = ImmersionBar.with(this.activity, false);
            Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
            if (this.statusBar == null) {
                immersionBarWith.fitsSystemWindows(true);
                immersionBarWith.autoStatusBarDarkModeEnable(true, 0.2f);
                TitleParam titleParam = this.title;
                if (titleParam == null || titleParam.getBackgroundColor() == -1) {
                    immersionBarWith.statusBarColor(this.globalConfig.getTitleBackground());
                } else {
                    immersionBarWith.statusBarColor(this.title.getBackgroundColor());
                }
            } else {
                checkStatusBarHide(immersionBarWith);
            }
            immersionBarWith.init();
        }
    }
}
