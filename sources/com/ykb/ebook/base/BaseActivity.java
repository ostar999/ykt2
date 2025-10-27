package com.ykb.ebook.base;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.ykb.ebook.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.StatusBarUtil;
import com.ykb.ebook.weight.EbookLoadingPop;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u001a\u001a\u00020\u001bH\u0014J\u0006\u0010\u001c\u001a\u00020\u001bJ\b\u0010\u001d\u001a\u00020\u001bH\u0016J\u0012\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H$J\u0012\u0010!\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010\"\u001a\u00020\u001bH\u0014J\u0012\u0010#\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010$\u001a\u00020\u001bH\u0014J\b\u0010%\u001a\u00020\u001bH\u0014J\u0006\u0010&\u001a\u00020\u001bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00028\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u001a\u0010\r\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\f\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\n \u0017*\u0004\u0018\u00010\u00160\u0016X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lcom/ykb/ebook/base/BaseActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "anim", "Landroid/graphics/drawable/AnimationDrawable;", "binding", "getBinding", "()Landroidx/viewbinding/ViewBinding;", "isInMultiWindow", "", "()Z", "isOnPause", "setOnPause", "(Z)V", "isShow", "loadView", "Landroid/view/View;", "loadingPop", "Lcom/ykb/ebook/weight/EbookLoadingPop;", "logTag", "", "kotlin.jvm.PlatformType", "getLogTag", "()Ljava/lang/String;", "doBusiness", "", "hideLoading", "initStatusBar", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onPause", "onPostCreate", "onResume", "onStop", "showLoading", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    @Nullable
    private AnimationDrawable anim;
    private boolean isOnPause;
    private boolean isShow;
    private View loadView;
    private EbookLoadingPop loadingPop;
    private final String logTag = getClass().getSimpleName();

    public void doBusiness() {
    }

    @NotNull
    public abstract VB getBinding();

    public final String getLogTag() {
        return this.logTag;
    }

    public final void hideLoading() {
        if (this.isShow) {
            View view = this.loadView;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadView");
                view = null;
            }
            ViewExtensionsKt.gone(view);
            AnimationDrawable animationDrawable = this.anim;
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
            this.isShow = false;
        }
    }

    public void initStatusBar() {
        StatusBarUtil.setColor(this, getColor(R.color.color_f7f8fa));
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public final boolean isInMultiWindow() {
        if (Build.VERSION.SDK_INT >= 24) {
            return isInMultiWindowMode();
        }
        return false;
    }

    /* renamed from: isOnPause, reason: from getter */
    public final boolean getIsOnPause() {
        return this.isOnPause;
    }

    public abstract void onActivityCreated(@Nullable Bundle savedInstanceState);

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = null;
        BasePopupView basePopupViewAsCustom = new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new EbookLoadingPop(this, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0));
        Intrinsics.checkNotNull(basePopupViewAsCustom, "null cannot be cast to non-null type com.ykb.ebook.weight.EbookLoadingPop");
        this.loadingPop = (EbookLoadingPop) basePopupViewAsCustom;
        initStatusBar();
        setTheme(R.style.Theme_EBook);
        setContentView(getBinding().getRoot());
        View viewFindViewById = findViewById(android.R.id.content);
        View viewInflate = getLayoutInflater().inflate(R.layout.ebook_loading_pop, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "layoutInflater.inflate(R….ebook_loading_pop, null)");
        this.loadView = viewInflate;
        if (viewFindViewById instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) viewFindViewById;
            if (viewInflate == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadView");
                viewInflate = null;
            }
            frameLayout.addView(viewInflate);
        }
        View view2 = this.loadView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadView");
            view2 = null;
        }
        Drawable background = ((ImageView) view2.findViewById(R.id.iv_loading)).getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        this.anim = (AnimationDrawable) background;
        View view3 = this.loadView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadView");
        } else {
            view = view3;
        }
        ViewExtensionsKt.gone(view);
        onActivityCreated(savedInstanceState);
        this.isOnPause = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.isOnPause = true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        doBusiness();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isOnPause = false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.isOnPause = false;
    }

    public final void setOnPause(boolean z2) {
        this.isOnPause = z2;
    }

    public final void showLoading() {
        if (this.isShow || this.isOnPause) {
            return;
        }
        AnimationDrawable animationDrawable = this.anim;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        View view = this.loadView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadView");
            view = null;
        }
        ViewExtensionsKt.visible(view);
        this.isShow = true;
    }
}
