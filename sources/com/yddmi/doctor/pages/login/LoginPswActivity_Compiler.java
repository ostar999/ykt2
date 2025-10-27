package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewStub;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.controller.LoadingViewController;
import com.catchpig.mvvm.controller.StatusBarController;
import com.catchpig.mvvm.controller.TitleBarController;
import com.catchpig.mvvm.entity.StatusBarParam;
import com.catchpig.mvvm.entity.TitleParam;
import com.catchpig.mvvm.ksp.interfaces.ActivityCompiler;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginPswActivity_Compiler;", "Lcom/catchpig/mvvm/ksp/interfaces/ActivityCompiler;", "()V", "statusBar", "Lcom/catchpig/mvvm/entity/StatusBarParam;", "title", "Lcom/catchpig/mvvm/entity/TitleParam;", "inject", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class LoginPswActivity_Compiler implements ActivityCompiler {

    @Nullable
    private final StatusBarParam statusBar = new StatusBarParam(false, false, true);

    @Nullable
    private final TitleParam title;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void inject$lambda$1$lambda$0(BaseActivity baseActivity, TitleParam it, ViewStub viewStub, View view) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(baseActivity, "$baseActivity");
        Intrinsics.checkNotNullParameter(it, "$it");
        TitleBarController titleBarController = new TitleBarController(baseActivity, it);
        Intrinsics.checkNotNullExpressionValue(view, "view");
        titleBarController.initTitleBar(view);
    }

    @Override // com.catchpig.mvvm.ksp.interfaces.ActivityCompiler
    public void inject(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        final BaseActivity baseActivity = (BaseActivity) activity;
        baseActivity.initLoadingViewController(new LoadingViewController(baseActivity, baseActivity.getRootBanding()));
        final TitleParam titleParam = this.title;
        if (titleParam != null) {
            ViewStub viewStub = (ViewStub) baseActivity.findViewById(R.id.title_bar_view_stub);
            viewStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.yddmi.doctor.pages.login.b0
                @Override // android.view.ViewStub.OnInflateListener
                public final void onInflate(ViewStub viewStub2, View view) throws Resources.NotFoundException {
                    LoginPswActivity_Compiler.inject$lambda$1$lambda$0(baseActivity, titleParam, viewStub2, view);
                }
            });
            viewStub.inflate();
        }
        StatusBarController statusBarController = new StatusBarController(baseActivity, this.title, this.statusBar);
        baseActivity.initStatusBarController(statusBarController);
        statusBarController.checkStatusBar();
    }
}
