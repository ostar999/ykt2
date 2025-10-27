package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CombineQuestionLoadingPop extends CenterPopupView {
    private AnimationDrawable mAnimationDrawable;
    private String message;

    public CombineQuestionLoadingPop(@NonNull Context context, String message) {
        super(context);
        this.message = message;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void beforeDismiss() {
        super.beforeDismiss();
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable == null || !animationDrawable.isRunning()) {
            return;
        }
        this.mAnimationDrawable.stop();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_combine_question_loading;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_loading);
        TextView textView = (TextView) findViewById(R.id.tv_tips);
        TextView textView2 = (TextView) findViewById(R.id.tv_content);
        this.mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        textView.setVisibility(TextUtils.isEmpty(this.message) ? 8 : 0);
        if (TextUtils.isEmpty(this.message)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView2.getLayoutParams();
            layoutParams.topMargin = CommonUtil.dip2px(getContext(), 16.0f);
            textView2.setLayoutParams(layoutParams);
        } else {
            textView.setText(this.message);
        }
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable == null || animationDrawable.isRunning()) {
            return;
        }
        this.mAnimationDrawable.start();
    }

    public void setMessage(String message) {
        this.message = message;
        TextView textView = (TextView) findViewById(R.id.tv_content);
        if (TextUtils.isEmpty(message)) {
            return;
        }
        textView.setText(message);
    }
}
