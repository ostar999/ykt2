package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\fH\u0014J\b\u0010\u000e\u001a\u00020\fH\u0014R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/widget/CutQuestionSuccessAnimPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "onConfirmListener", "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "mIvAnim", "Landroid/widget/ImageView;", "getImplLayoutId", "", "onCreate", "", "onDismiss", "onShow", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CutQuestionSuccessAnimPop extends CenterPopupView {

    @Nullable
    private ImageView mIvAnim;

    @NotNull
    private final OnConfirmListener onConfirmListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CutQuestionSuccessAnimPop(@NotNull Context context, @NotNull OnConfirmListener onConfirmListener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onConfirmListener, "onConfirmListener");
        this.onConfirmListener = onConfirmListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onShow$lambda$1$lambda$0(CutQuestionSuccessAnimPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_cut_question_success_anim;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.mIvAnim = (ImageView) findViewById(R.id.iv_anim);
        Drawable drawable = ContextCompat.getDrawable(getContext(), SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.anim_cut_question_success_night : R.drawable.anim_cut_question_success);
        Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        animationDrawable.setOneShot(false);
        ImageView imageView = this.mIvAnim;
        if (imageView == null) {
            return;
        }
        imageView.setBackground(animationDrawable);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        Drawable background;
        this.onConfirmListener.onConfirm();
        ImageView imageView = this.mIvAnim;
        if (imageView != null && (background = imageView.getBackground()) != null && (background instanceof AnimationDrawable)) {
            ((AnimationDrawable) background).stop();
        }
        super.onDismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        ImageView imageView = this.mIvAnim;
        if (imageView != null) {
            Drawable background = imageView.getBackground();
            if (background != null && (background instanceof AnimationDrawable)) {
                ((AnimationDrawable) background).start();
            }
            imageView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.j5
                @Override // java.lang.Runnable
                public final void run() {
                    CutQuestionSuccessAnimPop.onShow$lambda$1$lambda$0(this.f16609c);
                }
            }, 400L);
        }
    }
}
