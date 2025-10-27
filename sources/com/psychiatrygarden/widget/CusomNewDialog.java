package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CusomNewDialog extends Dialog implements View.OnClickListener {
    private boolean isOutTouchDismiss;
    private TextView quit_tv;
    private RelativeLayout relView;
    private TextView tv_message;
    private View viewRoot;

    public CusomNewDialog(Context context) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.view_diolog_view, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.quit_tv = (TextView) viewFindViewById.findViewById(R.id.quit_tv);
        this.tv_message = (TextView) this.viewRoot.findViewById(R.id.tv_message);
        ((Button) this.viewRoot.findViewById(R.id.btn_positive_single)).setOnClickListener(this);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels * 2) / 3, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.x4
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f17075c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.y4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17113c.lambda$new$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.viewRoot, Key.ROTATION, -3.0f, 3.5f, -1.5f, 0.0f).setDuration(500L));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(13);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    public void dismissNoAnimaltion() {
        super.dismiss();
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.btn_positive_single) {
            dismissNoAnimaltion();
        }
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }

    public CusomNewDialog setMessage(String message) {
        this.tv_message.setText(message);
        return this;
    }

    public CusomNewDialog setTitle(String title) {
        this.quit_tv.setText(title);
        return this;
    }
}
