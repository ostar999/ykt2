package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CustomerLivingLoading extends Dialog {
    public TextView messageTv;
    private String msg;

    public CustomerLivingLoading(Context context) {
        this(context, R.style.MyDialogStyle, "加载中...");
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    public CustomerLivingLoading(Context context, String string) {
        this(context, R.style.MyDialogStyle, string);
        this.msg = string;
    }

    public CustomerLivingLoading(Context context, int theme, String string) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        this.msg = string;
        setContentView(R.layout.layout_customer_living_loading);
        if (getWindow() != null && getWindow().getAttributes() != null) {
            getWindow().getAttributes().gravity = 17;
            getWindow().getAttributes().dimAmount = 0.0f;
        }
        ImageView imageView = (ImageView) findViewById(R.id.iv_loading);
        imageView.setBackgroundResource(SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.loading_night : R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        TextView textView = (TextView) findViewById(R.id.tv_loading);
        this.messageTv = textView;
        textView.setText(this.msg);
    }
}
