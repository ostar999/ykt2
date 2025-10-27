package com.bigkoo.pickerview.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;

/* loaded from: classes2.dex */
public class BasePickerView {
    protected View clickView;
    protected ViewGroup contentContainer;
    private Context context;
    private ViewGroup dialogView;
    private boolean dismissing;
    private Animation inAnim;
    private boolean isShowing;
    private Dialog mDialog;
    protected PickerOptions mPickerOptions;
    private OnDismissListener onDismissListener;
    private Animation outAnim;
    private ViewGroup rootView;
    protected int animGravity = 80;
    private boolean isAnim = true;
    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() { // from class: com.bigkoo.pickerview.view.BasePickerView.4
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i2, KeyEvent keyEvent) {
            if (i2 != 4 || keyEvent.getAction() != 0 || !BasePickerView.this.isShowing()) {
                return false;
            }
            BasePickerView.this.dismiss();
            return true;
        }
    };
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() { // from class: com.bigkoo.pickerview.view.BasePickerView.5
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            BasePickerView.this.dismiss();
            return false;
        }
    };

    public BasePickerView(Context context) {
        this.context = context;
    }

    private void dismissDialog() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private Animation getInAnimation() {
        return AnimationUtils.loadAnimation(this.context, PickerViewAnimateUtil.getAnimationResource(this.animGravity, true));
    }

    private Animation getOutAnimation() {
        return AnimationUtils.loadAnimation(this.context, PickerViewAnimateUtil.getAnimationResource(this.animGravity, false));
    }

    private void onAttached(View view) {
        this.mPickerOptions.decorView.addView(view);
        if (this.isAnim) {
            this.contentContainer.startAnimation(this.inAnim);
        }
    }

    private void showDialog() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.show();
        }
    }

    public void createDialog() {
        if (this.dialogView != null) {
            Dialog dialog = new Dialog(this.context, R.style.custom_dialog2);
            this.mDialog = dialog;
            dialog.setCancelable(this.mPickerOptions.cancelable);
            this.mDialog.setContentView(this.dialogView);
            Window window = this.mDialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(R.style.picker_view_scale_anim);
                window.setGravity(17);
            }
            this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.bigkoo.pickerview.view.BasePickerView.6
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    if (BasePickerView.this.onDismissListener != null) {
                        BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                    }
                }
            });
        }
    }

    public void dismiss() {
        if (isDialog()) {
            dismissDialog();
            return;
        }
        if (this.dismissing) {
            return;
        }
        if (this.isAnim) {
            this.outAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.bigkoo.pickerview.view.BasePickerView.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    BasePickerView.this.dismissImmediately();
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
            this.contentContainer.startAnimation(this.outAnim);
        } else {
            dismissImmediately();
        }
        this.dismissing = true;
    }

    public void dismissImmediately() {
        this.mPickerOptions.decorView.post(new Runnable() { // from class: com.bigkoo.pickerview.view.BasePickerView.3
            @Override // java.lang.Runnable
            public void run() {
                BasePickerView basePickerView = BasePickerView.this;
                basePickerView.mPickerOptions.decorView.removeView(basePickerView.rootView);
                BasePickerView.this.isShowing = false;
                BasePickerView.this.dismissing = false;
                if (BasePickerView.this.onDismissListener != null) {
                    BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                }
            }
        });
    }

    public View findViewById(int i2) {
        return this.contentContainer.findViewById(i2);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public ViewGroup getDialogContainerLayout() {
        return this.contentContainer;
    }

    public void initAnim() {
        this.inAnim = getInAnimation();
        this.outAnim = getOutAnimation();
    }

    public void initEvents() {
    }

    public void initViews() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2, 80);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.context);
        if (isDialog()) {
            ViewGroup viewGroup = (ViewGroup) layoutInflaterFrom.inflate(R.layout.layout_basepickerview, (ViewGroup) null, false);
            this.dialogView = viewGroup;
            viewGroup.setBackgroundColor(0);
            ViewGroup viewGroup2 = (ViewGroup) this.dialogView.findViewById(R.id.content_container);
            this.contentContainer = viewGroup2;
            layoutParams.leftMargin = 30;
            layoutParams.rightMargin = 30;
            viewGroup2.setLayoutParams(layoutParams);
            createDialog();
            this.dialogView.setOnClickListener(new View.OnClickListener() { // from class: com.bigkoo.pickerview.view.BasePickerView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BasePickerView.this.dismiss();
                }
            });
        } else {
            PickerOptions pickerOptions = this.mPickerOptions;
            if (pickerOptions.decorView == null) {
                pickerOptions.decorView = (ViewGroup) ((Activity) this.context).getWindow().getDecorView();
            }
            ViewGroup viewGroup3 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.layout_basepickerview, this.mPickerOptions.decorView, false);
            this.rootView = viewGroup3;
            viewGroup3.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            int i2 = this.mPickerOptions.outSideColor;
            if (i2 != -1) {
                this.rootView.setBackgroundColor(i2);
            }
            ViewGroup viewGroup4 = (ViewGroup) this.rootView.findViewById(R.id.content_container);
            this.contentContainer = viewGroup4;
            viewGroup4.setLayoutParams(layoutParams);
        }
        setKeyBackCancelable(true);
    }

    public boolean isDialog() {
        return false;
    }

    public boolean isShowing() {
        if (isDialog()) {
            return false;
        }
        return this.rootView.getParent() != null || this.isShowing;
    }

    public void setDialogOutSideCancelable() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(this.mPickerOptions.cancelable);
        }
    }

    public void setKeyBackCancelable(boolean z2) {
        ViewGroup viewGroup = isDialog() ? this.dialogView : this.rootView;
        viewGroup.setFocusable(z2);
        viewGroup.setFocusableInTouchMode(z2);
        if (z2) {
            viewGroup.setOnKeyListener(this.onKeyBackListener);
        } else {
            viewGroup.setOnKeyListener(null);
        }
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public BasePickerView setOutSideCancelable(boolean z2) {
        ViewGroup viewGroup = this.rootView;
        if (viewGroup != null) {
            View viewFindViewById = viewGroup.findViewById(R.id.outmost_container);
            if (z2) {
                viewFindViewById.setOnTouchListener(this.onCancelableTouchListener);
            } else {
                viewFindViewById.setOnTouchListener(null);
            }
        }
        return this;
    }

    public void show(View view, boolean z2) {
        this.clickView = view;
        this.isAnim = z2;
        show();
    }

    public void show(boolean z2) {
        show(null, z2);
    }

    public void show(View view) {
        this.clickView = view;
        show();
    }

    public void show() {
        if (isDialog()) {
            showDialog();
        } else {
            if (isShowing()) {
                return;
            }
            this.isShowing = true;
            onAttached(this.rootView);
            this.rootView.requestFocus();
        }
    }
}
