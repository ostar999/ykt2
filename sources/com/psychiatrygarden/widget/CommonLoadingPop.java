package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\fH\u0014J\b\u0010\u000e\u001a\u00020\fH\u0014R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/widget/CommonLoadingPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "animationDrawable", "Landroid/graphics/drawable/AnimationDrawable;", "mImgLoading", "Landroid/widget/ImageView;", "getImplLayoutId", "", "onCreate", "", "onDismiss", "onShow", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CommonLoadingPop extends CenterPopupView {

    @Nullable
    private AnimationDrawable animationDrawable;

    @Nullable
    private ImageView mImgLoading;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommonLoadingPop(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_common_loading;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.mImgLoading = (ImageView) findViewById(R.id.iv_loading);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        ImageView imageView = this.mImgLoading;
        if (imageView != null) {
            imageView.setBackgroundResource(SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.loading_night : R.drawable.loading);
        }
        ImageView imageView2 = this.mImgLoading;
        Drawable background = imageView2 != null ? imageView2.getBackground() : null;
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        AnimationDrawable animationDrawable = (AnimationDrawable) background;
        this.animationDrawable = animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }
}
