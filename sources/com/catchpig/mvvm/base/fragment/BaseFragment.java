package com.catchpig.mvvm.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.base.view.BaseView;
import com.catchpig.mvvm.controller.LoadingViewController;
import com.catchpig.mvvm.databinding.ViewRootBinding;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.SnackbarExtKt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.tencent.connect.common.Constants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\f\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016J!\u0010\u0006\u001a\u00020\u00172\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001aH\u0016J1\u0010\u000b\u001a\u00020\u0017\"\n\b\u0001\u0010\u001b\u0018\u0001*\u00020\u00022\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001aH\u0086\bø\u0001\u0000J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0002H\u0016J\u0006\u0010\u001d\u001a\u00020\u0014J\b\u0010\u001e\u001a\u00020\u0017H\u0016J\b\u0010\u001f\u001a\u00020\u0017H\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J&\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010'\u001a\u0004\u0018\u00010(H\u0017J\u0010\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0012H\u0016J\b\u0010+\u001a\u00020\u0017H\u0016J\b\u0010,\u001a\u00020\u0017H\u0016J\u001a\u0010-\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\"2\b\u0010'\u001a\u0004\u0018\u00010(H\u0016J\b\u0010/\u001a\u00020\u0017H\u0016J!\u0010\u0013\u001a\u00020\u00172\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001aH\u0016J\b\u00100\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0012H\u0016J\b\u00103\u001a\u00020\u0017H\u0016J\u0018\u00104\u001a\u00020\u00172\u0006\u00105\u001a\u0002062\b\b\u0002\u00107\u001a\u000208J\u001a\u00104\u001a\u00020\u00172\b\b\u0001\u00109\u001a\u0002082\b\b\u0002\u00107\u001a\u000208R\u001b\u0010\u0006\u001a\u00028\u00008DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\n\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006:"}, d2 = {"Lcom/catchpig/mvvm/base/fragment/BaseFragment;", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroidx/fragment/app/Fragment;", "Lcom/catchpig/mvvm/base/view/BaseView;", "()V", "bodyBinding", "getBodyBinding", "()Landroidx/viewbinding/ViewBinding;", "bodyBinding$delegate", "Lkotlin/Lazy;", "failedBinding", "loadingViewController", "Lcom/catchpig/mvvm/controller/LoadingViewController;", "getLoadingViewController", "()Lcom/catchpig/mvvm/controller/LoadingViewController;", "loadingViewController$delegate", "mIsViewCreate", "", "rootBinding", "Lcom/catchpig/mvvm/databinding/ViewRootBinding;", "baseActivity", "Lcom/catchpig/mvvm/base/activity/BaseActivity;", "", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "FVB", "getFailedBinding", "getRootBanding", "hideLoading", "loadingDialog", "loadingView", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onFragmentVisible", "isVisibleToUser", "onResume", "onStop", "onViewCreated", "view", "removeFailedView", Constants.PARAM_SCOPE, "Landroidx/lifecycle/LifecycleCoroutineScope;", "setUserVisibleHint", "showFailedView", "snackBar", "text", "", "gravity", "", "textRes", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class BaseFragment<VB extends ViewBinding> extends Fragment implements BaseView {

    @Nullable
    private ViewBinding failedBinding;
    private boolean mIsViewCreate;
    private ViewRootBinding rootBinding;

    /* renamed from: bodyBinding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy bodyBinding = LazyKt__LazyJVMKt.lazy(new Function0<VB>(this) { // from class: com.catchpig.mvvm.base.fragment.BaseFragment.bodyBinding.2
        final /* synthetic */ BaseFragment<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final VB invoke() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Type genericSuperclass = this.this$0.getClass().getGenericSuperclass();
            Intrinsics.checkNotNull(genericSuperclass, "null cannot be cast to non-null type java.lang.reflect.ParameterizedType");
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type java.lang.Class<VB of com.catchpig.mvvm.base.fragment.BaseFragment>");
            Method declaredMethod = ((Class) type).getDeclaredMethod("inflate", LayoutInflater.class);
            BaseFragment<VB> baseFragment = this.this$0;
            Object objInvoke = declaredMethod.invoke(baseFragment, baseFragment.getLayoutInflater());
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type VB of com.catchpig.mvvm.base.fragment.BaseFragment");
            return (VB) objInvoke;
        }
    });

    /* renamed from: loadingViewController$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy loadingViewController = LazyKt__LazyJVMKt.lazy(new Function0<LoadingViewController>(this) { // from class: com.catchpig.mvvm.base.fragment.BaseFragment$loadingViewController$2
        final /* synthetic */ BaseFragment<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final LoadingViewController invoke() {
            FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
            ViewRootBinding viewRootBinding = ((BaseFragment) this.this$0).rootBinding;
            if (viewRootBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
                viewRootBinding = null;
            }
            return new LoadingViewController(fragmentActivityRequireActivity, viewRootBinding);
        }
    });

    private final LoadingViewController getLoadingViewController() {
        return (LoadingViewController) this.loadingViewController.getValue();
    }

    public static /* synthetic */ void snackBar$default(BaseFragment baseFragment, CharSequence charSequence, int i2, int i3, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: snackBar");
        }
        if ((i3 & 2) != 0) {
            i2 = 80;
        }
        baseFragment.snackBar(charSequence, i2);
    }

    @Nullable
    public final BaseActivity<?> baseActivity() {
        if (!(getActivity() instanceof BaseActivity)) {
            return null;
        }
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.catchpig.mvvm.base.activity.BaseActivity<*>");
        return (BaseActivity) activity;
    }

    public void bodyBinding(@NotNull Function1<? super VB, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke(getBodyBinding());
    }

    public final /* synthetic */ <FVB extends ViewBinding> void failedBinding(Function1<? super FVB, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ViewBinding failedBinding = getFailedBinding();
        if (failedBinding != null) {
            Intrinsics.reifiedOperationMarker(1, "FVB");
            block.invoke(failedBinding);
        }
    }

    @NotNull
    public final VB getBodyBinding() {
        return (VB) this.bodyBinding.getValue();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    @Nullable
    public ViewBinding getFailedBinding() {
        if (this.failedBinding == null) {
            IGlobalConfig iGlobalConfigGlobalConfig = KotlinMvvmCompiler.INSTANCE.globalConfig();
            LayoutInflater layoutInflater = getLayoutInflater();
            Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
            this.failedBinding = iGlobalConfigGlobalConfig.getFailedBinding(layoutInflater, this);
        }
        return this.failedBinding;
    }

    @NotNull
    public final ViewRootBinding getRootBanding() {
        ViewRootBinding viewRootBinding = this.rootBinding;
        if (viewRootBinding != null) {
            return viewRootBinding;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
        return null;
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void hideLoading() {
        getLoadingViewController().hideLoading();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void loadingDialog() {
        getLoadingViewController().loadingDialog();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void loadingView() {
        getLoadingViewController().loadingView();
    }

    @Override // androidx.fragment.app.Fragment
    @CallSuper
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewRootBinding viewRootBindingInflate = ViewRootBinding.inflate(inflater, container, false);
        Intrinsics.checkNotNullExpressionValue(viewRootBindingInflate, "inflate(inflater, container, false)");
        this.rootBinding = viewRootBindingInflate;
        ViewRootBinding viewRootBinding = null;
        if (viewRootBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
            viewRootBindingInflate = null;
        }
        viewRootBindingInflate.layoutBody.addView(getBodyBinding().getRoot(), 0, new ViewGroup.LayoutParams(-1, -1));
        ViewRootBinding viewRootBinding2 = this.rootBinding;
        if (viewRootBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
        } else {
            viewRootBinding = viewRootBinding2;
        }
        return viewRootBinding.getRoot();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void onFailedReload(boolean z2, @NotNull Function1<? super View, Unit> function1) {
        BaseView.DefaultImpls.onFailedReload(this, z2, function1);
    }

    public void onFragmentVisible(boolean isVisibleToUser) {
        LogExtKt.logd("onFragmentVisible-isVisibleToUser:" + isVisibleToUser, "BaseFragment");
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onFragmentVisible(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (getUserVisibleHint()) {
            onFragmentVisible(false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.mIsViewCreate = true;
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void removeFailedView() {
        ViewBinding viewBinding = this.failedBinding;
        if (viewBinding != null) {
            ViewRootBinding viewRootBinding = this.rootBinding;
            if (viewRootBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
                viewRootBinding = null;
            }
            viewRootBinding.layoutBody.removeView(viewBinding.getRoot());
        }
    }

    public void rootBinding(@NotNull Function1<? super ViewRootBinding, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ViewRootBinding viewRootBinding = this.rootBinding;
        if (viewRootBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootBinding");
            viewRootBinding = null;
        }
        block.invoke(viewRootBinding);
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    @NotNull
    public LifecycleCoroutineScope scope() {
        return LifecycleOwnerKt.getLifecycleScope(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.mIsViewCreate) {
            onFragmentVisible(isVisibleToUser);
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void showFailedView() {
        final ViewBinding failedBinding = getFailedBinding();
        if (failedBinding != null) {
            rootBinding(new Function1<ViewRootBinding, Unit>() { // from class: com.catchpig.mvvm.base.fragment.BaseFragment$showFailedView$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ViewRootBinding viewRootBinding) {
                    invoke2(viewRootBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull ViewRootBinding rootBinding) {
                    Intrinsics.checkNotNullParameter(rootBinding, "$this$rootBinding");
                    rootBinding.layoutBody.addView(failedBinding.getRoot());
                }
            });
        }
    }

    public final void snackBar(@NotNull CharSequence text, int gravity) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(text, "text");
        View root = getBodyBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "bodyBinding.root");
        SnackbarExtKt.showSnackBar(root, text, R.drawable.snackbar_bg, gravity);
    }

    public static /* synthetic */ void snackBar$default(BaseFragment baseFragment, int i2, int i3, int i4, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: snackBar");
        }
        if ((i4 & 2) != 0) {
            i3 = 80;
        }
        baseFragment.snackBar(i2, i3);
    }

    public final void snackBar(@StringRes int textRes, int gravity) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        View root = getBodyBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "bodyBinding.root");
        SnackbarExtKt.showSnackBar(root, textRes, R.drawable.snackbar_bg, gravity);
    }
}
