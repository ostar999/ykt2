package com.catchpig.mvvm.controller;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.catchpig.loading.view.LoadingView;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.controller.LoadingViewController;
import com.catchpig.mvvm.databinding.ViewRootBinding;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u000f\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/catchpig/mvvm/controller/LoadingViewController;", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "rootBinding", "Lcom/catchpig/mvvm/databinding/ViewRootBinding;", "(Landroid/app/Activity;Lcom/catchpig/mvvm/databinding/ViewRootBinding;)V", "dialog", "Landroid/app/Dialog;", "globalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "isLoadingInflate", "", "loadingFrame", "Landroid/widget/FrameLayout;", "loadingView", "Lcom/catchpig/loading/view/LoadingView;", "hideLoading", "", "loadingDialog", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class LoadingViewController {

    @NotNull
    private final Activity activity;

    @Nullable
    private Dialog dialog;

    @NotNull
    private final IGlobalConfig globalConfig;
    private boolean isLoadingInflate;
    private FrameLayout loadingFrame;
    private LoadingView loadingView;

    @NotNull
    private final ViewRootBinding rootBinding;

    public LoadingViewController(@NotNull Activity activity, @NotNull ViewRootBinding rootBinding) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(rootBinding, "rootBinding");
        this.activity = activity;
        this.rootBinding = rootBinding;
        this.globalConfig = KotlinMvvmCompiler.INSTANCE.globalConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadingView$lambda$1$lambda$0(LoadingViewController this$0, ViewStub viewStub, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isLoadingInflate = true;
        View viewFindViewById = view.findViewById(R.id.loading_frame);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.loading_frame)");
        FrameLayout frameLayout = (FrameLayout) viewFindViewById;
        this$0.loadingFrame = frameLayout;
        LoadingView loadingView = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingFrame");
            frameLayout = null;
        }
        frameLayout.setVisibility(0);
        FrameLayout frameLayout2 = this$0.loadingFrame;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingFrame");
            frameLayout2 = null;
        }
        frameLayout2.setBackgroundResource(this$0.globalConfig.getLoadingBackground());
        View viewFindViewById2 = view.findViewById(R.id.loading_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "view.findViewById(R.id.loading_view)");
        LoadingView loadingView2 = (LoadingView) viewFindViewById2;
        this$0.loadingView = loadingView2;
        if (loadingView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingView");
        } else {
            loadingView = loadingView2;
        }
        loadingView.setLoadColor(this$0.globalConfig.getLoadingColor());
    }

    public final void hideLoading() {
        Dialog dialog = this.dialog;
        FrameLayout frameLayout = null;
        if (dialog != null) {
            dialog.dismiss();
            this.dialog = null;
        }
        if (this.isLoadingInflate) {
            FrameLayout frameLayout2 = this.loadingFrame;
            if (frameLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingFrame");
            } else {
                frameLayout = frameLayout2;
            }
            frameLayout.setVisibility(8);
        }
    }

    public final void loadingDialog() {
        Dialog dialog = this.dialog;
        if (dialog == null || !dialog.isShowing()) {
            Dialog dialog2 = new Dialog(this.activity, R.style.loading_dialog_theme);
            this.dialog = dialog2;
            dialog2.setCancelable(false);
            dialog2.setContentView(R.layout.syyx_layout_loading);
            FrameLayout frameLayout = (FrameLayout) dialog2.findViewById(R.id.loading_frame);
            LoadingView loadingView = (LoadingView) dialog2.findViewById(R.id.loading_view);
            frameLayout.setVisibility(0);
            loadingView.setLoadColor(this.globalConfig.getLoadingColor());
            dialog2.show();
        }
    }

    public final void loadingView() {
        ViewRootBinding viewRootBinding = this.rootBinding;
        FrameLayout frameLayout = viewRootBinding.layoutBody;
        if (!this.isLoadingInflate) {
            viewRootBinding.loadingViewStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: s0.a
                @Override // android.view.ViewStub.OnInflateListener
                public final void onInflate(ViewStub viewStub, View view) {
                    LoadingViewController.loadingView$lambda$1$lambda$0(this.f28201a, viewStub, view);
                }
            });
            this.rootBinding.loadingViewStub.inflate();
            return;
        }
        FrameLayout frameLayout2 = this.loadingFrame;
        LoadingView loadingView = null;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingFrame");
            frameLayout2 = null;
        }
        frameLayout2.setVisibility(0);
        FrameLayout frameLayout3 = this.loadingFrame;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingFrame");
            frameLayout3 = null;
        }
        frameLayout3.setBackgroundResource(this.globalConfig.getLoadingBackground());
        LoadingView loadingView2 = this.loadingView;
        if (loadingView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingView");
        } else {
            loadingView = loadingView2;
        }
        loadingView.setLoadColor(this.globalConfig.getLoadingColor());
    }
}
