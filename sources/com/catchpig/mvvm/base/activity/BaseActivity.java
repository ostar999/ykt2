package com.catchpig.mvvm.base.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.base.view.BaseView;
import com.catchpig.mvvm.controller.LoadingViewController;
import com.catchpig.mvvm.controller.StatusBarController;
import com.catchpig.mvvm.databinding.ViewRootBinding;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.SnackbarExtKt;
import com.huawei.hms.support.api.entity.core.CommonCode;
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

@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J!\u0010\u0006\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00150\u0017¢\u0006\u0002\b\u0018H\u0016J\u0006\u0010\u0019\u001a\u00020\u0015J1\u0010\u000b\u001a\u00020\u0015\"\n\b\u0001\u0010\u001a\u0018\u0001*\u00020\u00022\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00150\u0017¢\u0006\u0002\b\u0018H\u0086\bø\u0001\u0000J\n\u0010\u001b\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0006\u0010\u001e\u001a\u00020\u000fJ\b\u0010\u001f\u001a\u00020\u0015H\u0016J\u0010\u0010 \u001a\u00020\u00152\b\b\u0002\u0010!\u001a\u00020\"J\b\u0010#\u001a\u00020\u0015H\u0016J\u000e\u0010$\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010%\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010&\u001a\u00020\u0015H\u0016J\b\u0010'\u001a\u00020\u0015H\u0016J\u0012\u0010(\u001a\u00020\u00152\b\u0010)\u001a\u0004\u0018\u00010*H\u0015J\b\u0010+\u001a\u00020\u0015H\u0014J\u0012\u0010,\u001a\u00020\u00152\b\u0010-\u001a\u0004\u0018\u00010.H\u0015J\b\u0010/\u001a\u00020\u0015H\u0016J\u0006\u00100\u001a\u00020\u0015J!\u0010\u000e\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00150\u0017¢\u0006\u0002\b\u0018H\u0016J\b\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u00020\u0015H\u0016J\u0012\u00104\u001a\u00020\u00152\b\u00105\u001a\u0004\u0018\u000106H\u0016J\b\u00107\u001a\u00020\u0015H\u0016J\u0018\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020:2\b\b\u0002\u0010;\u001a\u00020\"J\u001a\u00108\u001a\u00020\u00152\b\b\u0001\u0010<\u001a\u00020\"2\b\b\u0002\u0010;\u001a\u00020\"J\u0006\u0010=\u001a\u00020\u0015J\u0010\u0010>\u001a\u00020\u00152\b\b\u0001\u0010?\u001a\u00020\"J\u000e\u0010>\u001a\u00020\u00152\u0006\u0010?\u001a\u00020@J\u0010\u0010A\u001a\u00020\u00152\b\b\u0002\u0010B\u001a\u00020CJ\u0010\u0010D\u001a\u00020\u00152\b\b\u0001\u0010?\u001a\u00020\"J\u0010\u0010D\u001a\u00020\u00152\b\u0010E\u001a\u0004\u0018\u00010@J\u000e\u0010F\u001a\u00020\u00152\u0006\u0010G\u001a\u00020\"R\u001b\u0010\u0006\u001a\u00028\u00008DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\n\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006H"}, d2 = {"Lcom/catchpig/mvvm/base/activity/BaseActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/catchpig/mvvm/base/view/BaseView;", "()V", "bodyBinding", "getBodyBinding", "()Landroidx/viewbinding/ViewBinding;", "bodyBinding$delegate", "Lkotlin/Lazy;", "failedBinding", "loadingViewController", "Lcom/catchpig/mvvm/controller/LoadingViewController;", "rootBinding", "Lcom/catchpig/mvvm/databinding/ViewRootBinding;", "getRootBinding", "()Lcom/catchpig/mvvm/databinding/ViewRootBinding;", "rootBinding$delegate", "statusBarController", "Lcom/catchpig/mvvm/controller/StatusBarController;", "", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "closeActivity", "FVB", "getFailedBinding", "getResources", "Landroid/content/res/Resources;", "getRootBanding", "hideLoading", "hideShowTitleBar", "hideShow", "", "initAction", "initLoadingViewController", "initStatusBarController", "loadingDialog", "loadingView", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "removeFailedView", "resetStatusBar", Constants.PARAM_SCOPE, "Landroidx/lifecycle/LifecycleCoroutineScope;", "screenOrientation", "setContentView", "view", "Landroid/view/View;", "showFailedView", "snackBar", "text", "", "gravity", "textRes", "updateStatusBarTransparent", "updateTitle", "title", "", "updateTitleLine", "show", "", "updateTitleRightFirst", "rightTitle", "updateTitleRightFirstTextColor", "color", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements BaseView {

    @Nullable
    private ViewBinding failedBinding;
    private LoadingViewController loadingViewController;
    private StatusBarController statusBarController;

    /* renamed from: bodyBinding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy bodyBinding = LazyKt__LazyJVMKt.lazy(new Function0<VB>(this) { // from class: com.catchpig.mvvm.base.activity.BaseActivity.bodyBinding.2
        final /* synthetic */ BaseActivity<VB> this$0;

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
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type java.lang.Class<VB of com.catchpig.mvvm.base.activity.BaseActivity>");
            Method declaredMethod = ((Class) type).getDeclaredMethod("inflate", LayoutInflater.class);
            BaseActivity<VB> baseActivity = this.this$0;
            Object objInvoke = declaredMethod.invoke(baseActivity, baseActivity.getLayoutInflater());
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type VB of com.catchpig.mvvm.base.activity.BaseActivity");
            return (VB) objInvoke;
        }
    });

    /* renamed from: rootBinding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy rootBinding = LazyKt__LazyJVMKt.lazy(new Function0<ViewRootBinding>(this) { // from class: com.catchpig.mvvm.base.activity.BaseActivity.rootBinding.2
        final /* synthetic */ BaseActivity<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final ViewRootBinding invoke() {
            ViewRootBinding viewRootBindingInflate = ViewRootBinding.inflate(this.this$0.getLayoutInflater());
            Intrinsics.checkNotNullExpressionValue(viewRootBindingInflate, "inflate(layoutInflater)");
            return viewRootBindingInflate;
        }
    });

    private final ViewRootBinding getRootBinding() {
        return (ViewRootBinding) this.rootBinding.getValue();
    }

    public static /* synthetic */ void hideShowTitleBar$default(BaseActivity baseActivity, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: hideShowTitleBar");
        }
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        baseActivity.hideShowTitleBar(i2);
    }

    public static /* synthetic */ void snackBar$default(BaseActivity baseActivity, CharSequence charSequence, int i2, int i3, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: snackBar");
        }
        if ((i3 & 2) != 0) {
            i2 = 80;
        }
        baseActivity.snackBar(charSequence, i2);
    }

    public static /* synthetic */ void updateTitleLine$default(BaseActivity baseActivity, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateTitleLine");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        baseActivity.updateTitleLine(z2);
    }

    public void bodyBinding(@NotNull Function1<? super VB, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke(getBodyBinding());
    }

    public final void closeActivity() {
        finish();
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

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    @NotNull
    public Resources getResources() {
        Resources resources = createConfigurationContext(super.getResources().getConfiguration()).getResources();
        resources.getConfiguration().fontScale = 1.0f;
        if (Build.VERSION.SDK_INT >= 31) {
            resources.getConfiguration().fontWeightAdjustment = 1;
        }
        resources.getDisplayMetrics().scaledDensity = resources.getDisplayMetrics().density * resources.getConfiguration().fontScale;
        Intrinsics.checkNotNullExpressionValue(resources, "config.resources.apply {…ation.fontScale\n        }");
        return resources;
    }

    @NotNull
    public final ViewRootBinding getRootBanding() {
        return getRootBinding();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void hideLoading() {
        LoadingViewController loadingViewController = this.loadingViewController;
        if (loadingViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingViewController");
            loadingViewController = null;
        }
        loadingViewController.hideLoading();
    }

    public final void hideShowTitleBar(int hideShow) {
        RelativeLayout relativeLayout = (RelativeLayout) getRootBinding().getRoot().findViewById(R.id.title_bar);
        if (relativeLayout == null) {
            return;
        }
        relativeLayout.setVisibility(hideShow);
    }

    public void initAction() {
    }

    public final void initLoadingViewController(@NotNull LoadingViewController loadingViewController) {
        Intrinsics.checkNotNullParameter(loadingViewController, "loadingViewController");
        this.loadingViewController = loadingViewController;
    }

    public final void initStatusBarController(@NotNull StatusBarController statusBarController) {
        Intrinsics.checkNotNullParameter(statusBarController, "statusBarController");
        this.statusBarController = statusBarController;
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void loadingDialog() {
        LoadingViewController loadingViewController = this.loadingViewController;
        if (loadingViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingViewController");
            loadingViewController = null;
        }
        loadingViewController.loadingDialog();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void loadingView() {
        LoadingViewController loadingViewController = this.loadingViewController;
        if (loadingViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingViewController");
            loadingViewController = null;
        }
        loadingViewController.loadingView();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, InstantiationException {
        screenOrientation();
        super.setContentView(getRootBinding().getRoot());
        super.onCreate(savedInstanceState);
        setContentView(getBodyBinding().getRoot());
        KotlinMvvmCompiler.INSTANCE.inject(this);
        initAction();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void onFailedReload(boolean z2, @NotNull Function1<? super View, Unit> function1) {
        BaseView.DefaultImpls.onFailedReload(this, z2, function1);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    @CallSuper
    public void onNewIntent(@Nullable Intent intent) {
        super.onNewIntent(intent);
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void removeFailedView() {
        ViewBinding viewBinding = this.failedBinding;
        if (viewBinding != null) {
            getRootBinding().layoutBody.removeView(viewBinding.getRoot());
        }
    }

    public final void resetStatusBar() {
        StatusBarController statusBarController = this.statusBarController;
        if (statusBarController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBarController");
            statusBarController = null;
        }
        statusBarController.checkStatusBar();
    }

    public void rootBinding(@NotNull Function1<? super ViewRootBinding, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke(getRootBinding());
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    @NotNull
    public LifecycleCoroutineScope scope() {
        return LifecycleOwnerKt.getLifecycleScope(this);
    }

    public void screenOrientation() {
        setRequestedOrientation(6);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(@Nullable View view) {
        getRootBinding().layoutBody.addView(view, 0, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.catchpig.mvvm.base.view.BaseView
    public void showFailedView() {
        final ViewBinding failedBinding = getFailedBinding();
        if (failedBinding != null) {
            rootBinding(new Function1<ViewRootBinding, Unit>() { // from class: com.catchpig.mvvm.base.activity.BaseActivity$showFailedView$1$1
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

    public final void updateStatusBarTransparent() {
    }

    public final void updateTitle(@NotNull String title) {
        Intrinsics.checkNotNullParameter(title, "title");
        TextView textView = (TextView) getRootBinding().getRoot().findViewById(R.id.title_text);
        if (textView == null) {
            return;
        }
        textView.setText(title);
    }

    public final void updateTitleLine(boolean show) {
        View viewFindViewById = getRootBinding().getRoot().findViewById(R.id.line);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(show ? 0 : 4);
        } else {
            LogExtKt.logd("标题栏line null!", "BaseActivity");
        }
    }

    public final void updateTitleRightFirst(@StringRes int title) {
        TextView textView = (TextView) getRootBinding().getRoot().findViewById(R.id.rightFirstText);
        if (textView != null) {
            textView.setText(title);
        }
    }

    public final void updateTitleRightFirstTextColor(int color) {
        TextView textView = (TextView) getRootBinding().getRoot().findViewById(R.id.rightFirstText);
        if (textView != null) {
            textView.setTextColor(color);
        }
    }

    public static /* synthetic */ void snackBar$default(BaseActivity baseActivity, int i2, int i3, int i4, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: snackBar");
        }
        if ((i4 & 2) != 0) {
            i3 = 80;
        }
        baseActivity.snackBar(i2, i3);
    }

    public final void snackBar(@StringRes int textRes, int gravity) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        View root = getBodyBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "bodyBinding.root");
        SnackbarExtKt.showSnackBar(root, textRes, R.drawable.snackbar_bg, gravity);
    }

    public final void updateTitle(@StringRes int title) {
        TextView textView = (TextView) getRootBinding().getRoot().findViewById(R.id.title_text);
        if (textView != null) {
            textView.setText(title);
        }
    }

    public final void updateTitleRightFirst(@Nullable String rightTitle) {
        TextView textView = (TextView) getRootBinding().getRoot().findViewById(R.id.rightFirstText);
        textView.setVisibility(0);
        textView.setText(rightTitle);
    }
}
