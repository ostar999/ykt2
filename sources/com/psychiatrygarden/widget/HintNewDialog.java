package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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
public class HintNewDialog extends Dialog implements View.OnClickListener {
    private boolean isOutTouchDismiss;
    private RelativeLayout relView;
    private boolean showHighLight;
    private View viewRoot;

    public interface OperationListener {
        void cancel();

        void confirm();
    }

    public HintNewDialog(Context context, String message, String cancelStr, final OperationListener listener) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.hint_dialog_content, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        ((TextView) viewFindViewById.findViewById(R.id.tv_content)).setText(message);
        if (this.showHighLight) {
            this.viewRoot.findViewById(R.id.tv_title).setVisibility(0);
        }
        Button button = (Button) this.viewRoot.findViewById(R.id.btn_negative_single);
        this.viewRoot.findViewById(R.id.btn_positive_single).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17043c.lambda$new$0(listener, view);
            }
        });
        if (!TextUtils.isEmpty(cancelStr)) {
            button.setText(cancelStr);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17090c.lambda$new$1(listener, view);
            }
        });
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels * 2) / 3, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.y9
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f17121c.lambda$new$2(layoutParams, dialogInterface);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.z9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17149c.lambda$new$3(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(OperationListener operationListener, View view) {
        operationListener.confirm();
        dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(OperationListener operationListener, View view) {
        dismissNoAnimaltion();
        operationListener.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
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
    public /* synthetic */ void lambda$new$3(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(OperationListener operationListener, View view) {
        operationListener.confirm();
        dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(OperationListener operationListener, View view) {
        dismissNoAnimaltion();
        operationListener.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
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
    public /* synthetic */ void lambda$new$7(View view) {
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

    public boolean isShowHighLight() {
        return this.showHighLight;
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

    public void setShowHighLight(boolean showHighLight) {
        this.showHighLight = showHighLight;
    }

    public HintNewDialog(Context context, String message, final OperationListener listener) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.hint_dialog_content, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        ((TextView) viewFindViewById.findViewById(R.id.tv_content)).setText(message);
        View viewFindViewById2 = this.viewRoot.findViewById(R.id.btn_negative_single);
        this.viewRoot.findViewById(R.id.btn_positive_single).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16896c.lambda$new$4(listener, view);
            }
        });
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16928c.lambda$new$5(listener, view);
            }
        });
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels * 2) / 3, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.u9
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16952c.lambda$new$6(layoutParams, dialogInterface);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16992c.lambda$new$7(view);
            }
        });
    }
}
