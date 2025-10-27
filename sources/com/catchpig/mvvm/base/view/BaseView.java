package com.catchpig.mvvm.base.view;

import android.view.View;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.base.view.BaseView;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.tencent.connect.common.Constants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\u0005H&J+\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\u0017\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00050\f¢\u0006\u0002\b\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H&J\b\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0005H&¨\u0006\u0013"}, d2 = {"Lcom/catchpig/mvvm/base/view/BaseView;", "", "getFailedBinding", "Landroidx/viewbinding/ViewBinding;", "hideLoading", "", "loadingDialog", "loadingView", "onFailedReload", "autoFirstLoad", "", "block", "Lkotlin/Function1;", "Landroid/view/View;", "Lkotlin/ExtensionFunctionType;", "removeFailedView", Constants.PARAM_SCOPE, "Landroidx/lifecycle/LifecycleCoroutineScope;", "showFailedView", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface BaseView {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onFailedReload(@NotNull BaseView baseView, boolean z2, @NotNull final Function1<? super View, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            ViewBinding failedBinding = baseView.getFailedBinding();
            if (failedBinding != null) {
                View root = failedBinding.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "viewBinding.root");
                View viewFindViewById = root.findViewById(KotlinMvvmCompiler.INSTANCE.globalConfig().onFailedReloadClickId());
                if (z2) {
                    block.invoke(viewFindViewById);
                }
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: r0.a
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BaseView.DefaultImpls.onFailedReload$lambda$1$lambda$0(block, view);
                    }
                });
            }
        }

        public static /* synthetic */ void onFailedReload$default(BaseView baseView, boolean z2, Function1 function1, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onFailedReload");
            }
            if ((i2 & 1) != 0) {
                z2 = true;
            }
            baseView.onFailedReload(z2, function1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void onFailedReload$lambda$1$lambda$0(Function1 block, View view) {
            Intrinsics.checkNotNullParameter(block, "$block");
            block.invoke(view);
        }
    }

    @Nullable
    ViewBinding getFailedBinding();

    void hideLoading();

    void loadingDialog();

    void loadingView();

    void onFailedReload(boolean autoFirstLoad, @NotNull Function1<? super View, Unit> block);

    void removeFailedView();

    @NotNull
    LifecycleCoroutineScope scope();

    void showFailedView();
}
