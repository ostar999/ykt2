package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CustomDialog extends Dialog {
    public static final int SINGLE_BUTTON = 1;
    public static final int THREE_BUTTON = 3;
    public static final int TWO_BUTTON = 2;
    private boolean isOutTouchDismiss;
    private Button mBtnNegative;
    private Button mBtnNegativeSingle;
    private Button mBtnPositive;
    private Button mBtnPositiveSingle;
    private TextView mTVMessage;
    private TextView mTitleTv;
    private RelativeLayout relView;
    private int type;
    private View viewRoot;

    public CustomDialog(Context context, int type, int layout) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(layout, (ViewGroup) null);
        this.viewRoot = viewInflate;
        this.type = type;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.mTVMessage = (TextView) viewFindViewById.findViewById(R.id.tv_message);
        this.mTitleTv = (TextView) this.viewRoot.findViewById(R.id.tv_title);
        this.mBtnNegative = (Button) this.viewRoot.findViewById(R.id.btn_negative);
        this.mBtnPositive = (Button) this.viewRoot.findViewById(R.id.btn_positive);
        LinearLayout linearLayout = (LinearLayout) this.viewRoot.findViewById(R.id.ll_operations);
        LinearLayout linearLayout2 = (LinearLayout) this.viewRoot.findViewById(R.id.ll_operation);
        this.mBtnPositiveSingle = (Button) this.viewRoot.findViewById(R.id.btn_positive_single);
        this.mBtnNegativeSingle = (Button) this.viewRoot.findViewById(R.id.btn_negative_single);
        if (type == 1) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(0);
        } else if (type == 2) {
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
        } else {
            if (type != 3) {
                throw new IllegalArgumentException("The type must be SINGLE_BUTTON(0x01) or TWO_BUTTON(0x02)");
            }
            ((TextView) this.viewRoot.findViewById(R.id.quit_tv)).setVisibility(0);
            this.viewRoot.findViewById(R.id.textView1).setVisibility(0);
            this.viewRoot.findViewById(R.id.textView2).setVisibility(0);
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
        }
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels * 2) / 3, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.z4
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f17142c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomDialog.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.b5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16334c.lambda$new$2(view);
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
    public static /* synthetic */ void lambda$new$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
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
    public static /* synthetic */ void lambda$new$4(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
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

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -2;
        getWindow().setAttributes(attributes);
    }

    public CustomDialog setMessage(SpannableString ss) {
        this.mTVMessage.setText(ss);
        return this;
    }

    public CustomDialog setNegativeBtn(int cancelResId, View.OnClickListener listener) {
        int i2 = this.type;
        if (i2 == 1) {
            this.mBtnNegativeSingle.setText(cancelResId);
            this.mBtnNegativeSingle.setOnClickListener(listener);
            this.mBtnNegativeSingle.setVisibility(0);
        } else if (i2 == 2 || i2 == 3) {
            this.mBtnNegative.setText(cancelResId);
            this.mBtnNegative.setOnClickListener(listener);
            this.mBtnNegative.setVisibility(0);
        }
        return this;
    }

    public CustomDialog setPositiveBtn(int okResId, View.OnClickListener listener) {
        int i2 = this.type;
        if (i2 == 1) {
            this.mBtnPositiveSingle.setText(okResId);
            this.mBtnPositiveSingle.setOnClickListener(listener);
            this.mBtnPositiveSingle.setVisibility(0);
        } else if (i2 == 2 || i2 == 3) {
            this.mBtnPositive.setText(okResId);
            this.mBtnPositive.setOnClickListener(listener);
            this.mBtnPositive.setVisibility(0);
        }
        return this;
    }

    public void setTextSize() {
        this.mTVMessage.setTextSize(13.0f);
    }

    public CustomDialog setTitle(String title) {
        TextView textView = this.mTitleTv;
        if (textView != null) {
            textView.setText(title);
        }
        return this;
    }

    public CustomDialog setMessage(int messageResId) {
        this.mTVMessage.setText(messageResId);
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.mTVMessage.setText(message);
        return this;
    }

    public CustomDialog setMessage(SpannableStringBuilder spanBuilder) {
        this.mTVMessage.setText(spanBuilder);
        return this;
    }

    public CustomDialog setNegativeBtn(String cancel, View.OnClickListener listener) {
        int i2 = this.type;
        if (i2 == 1) {
            this.mBtnNegativeSingle.setText(cancel);
            this.mBtnNegativeSingle.setOnClickListener(listener);
            this.mBtnNegativeSingle.setVisibility(0);
        } else if (i2 == 2 || i2 == 3) {
            this.mBtnNegative.setText(cancel);
            this.mBtnNegative.setOnClickListener(listener);
            this.mBtnNegative.setVisibility(0);
        }
        return this;
    }

    public CustomDialog setPositiveBtn(String ok, View.OnClickListener listener) {
        int i2 = this.type;
        if (i2 == 1) {
            this.mBtnPositiveSingle.setText(ok);
            this.mBtnPositiveSingle.setOnClickListener(listener);
            this.mBtnPositiveSingle.setVisibility(0);
        } else if (i2 == 2 || i2 == 3) {
            this.mBtnPositive.setText(ok);
            this.mBtnPositive.setOnClickListener(listener);
            this.mBtnPositive.setVisibility(0);
        }
        return this;
    }

    public CustomDialog(Context context, int type) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.view_dialog_content, (ViewGroup) null);
        this.viewRoot = viewInflate;
        this.type = type;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.mTVMessage = (TextView) viewFindViewById.findViewById(R.id.tv_message);
        this.mBtnNegative = (Button) this.viewRoot.findViewById(R.id.btn_negative);
        this.mBtnPositive = (Button) this.viewRoot.findViewById(R.id.btn_positive);
        LinearLayout linearLayout = (LinearLayout) this.viewRoot.findViewById(R.id.ll_operations);
        LinearLayout linearLayout2 = (LinearLayout) this.viewRoot.findViewById(R.id.ll_operation);
        this.mBtnPositiveSingle = (Button) this.viewRoot.findViewById(R.id.btn_positive_single);
        this.mBtnNegativeSingle = (Button) this.viewRoot.findViewById(R.id.btn_negative_single);
        if (type == 1) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(0);
        } else if (type == 2) {
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
        } else if (type == 3) {
            ((TextView) this.viewRoot.findViewById(R.id.quit_tv)).setVisibility(0);
            this.viewRoot.findViewById(R.id.textView1).setVisibility(0);
            this.viewRoot.findViewById(R.id.textView2).setVisibility(0);
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
        } else {
            throw new IllegalArgumentException("The type must be SINGLE_BUTTON(0x01) or TWO_BUTTON(0x02)");
        }
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels * 2) / 3, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.c5
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16360c.lambda$new$3(layoutParams, dialogInterface);
            }
        });
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomDialog.lambda$new$4(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16427c.lambda$new$5(view);
            }
        });
    }
}
