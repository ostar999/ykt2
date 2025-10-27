package com.aliyun.svideo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class AlivcCircleLoadingDialog extends Dialog {
    private Context context;
    private final int mHeight;
    private ImageView mImageView;

    public AlivcCircleLoadingDialog(Context context, int i2) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mHeight = i2;
    }

    private void init() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.alivc_common_dialog_circle_progress, (ViewGroup) null, false);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_progress);
        this.mImageView = imageView;
        imageView.setImageResource(R.mipmap.alivc_common_icon_circle_progress);
        setAnimation();
        setContentView(viewInflate);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        attributes.width = displayMetrics.widthPixels;
        int i2 = this.mHeight;
        if (i2 == 0) {
            attributes.height = displayMetrics.heightPixels;
        } else {
            attributes.height = i2;
        }
        attributes.gravity = 49;
        window.setAttributes(attributes);
        setCancelable(false);
    }

    private void setAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(800L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatMode(1);
        rotateAnimation.setRepeatCount(-1);
        if (this.mImageView.getAnimation() == null) {
            this.mImageView.startAnimation(rotateAnimation);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.clearAnimation();
        }
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        if (this.mImageView != null) {
            setAnimation();
        }
    }
}
