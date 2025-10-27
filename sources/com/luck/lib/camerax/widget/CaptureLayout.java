package com.luck.lib.camerax.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.luck.lib.camerax.R;
import com.luck.lib.camerax.listener.CaptureListener;
import com.luck.lib.camerax.listener.ClickListener;
import com.luck.lib.camerax.listener.TypeListener;
import com.luck.lib.camerax.utils.DensityUtil;

/* loaded from: classes4.dex */
public class CaptureLayout extends FrameLayout {
    private TypeButton btn_cancel;
    private CaptureButton btn_capture;
    private TypeButton btn_confirm;
    private ReturnButton btn_return;
    private final int button_size;
    private CaptureListener captureListener;
    private int iconLeft;
    private int iconRight;
    private ImageView iv_custom_left;
    private ImageView iv_custom_right;
    private final int layout_height;
    private final int layout_width;
    private ClickListener leftClickListener;
    private ProgressBar progress_bar;
    private ClickListener rightClickListener;
    private TextView txt_tip;
    private TypeListener typeListener;

    public CaptureLayout(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCaptureTip() {
        int buttonFeatures = this.btn_capture.getButtonFeatures();
        return buttonFeatures != 1 ? buttonFeatures != 2 ? getContext().getString(R.string.picture_photo_camera) : getContext().getString(R.string.picture_photo_recording) : getContext().getString(R.string.picture_photo_pictures);
    }

    private void initView() {
        setWillNotDraw(false);
        this.progress_bar = new ProgressBar(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        this.progress_bar.setLayoutParams(layoutParams);
        this.progress_bar.setVisibility(8);
        this.btn_capture = new CaptureButton(getContext(), this.button_size);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams2.gravity = 17;
        this.btn_capture.setLayoutParams(layoutParams2);
        this.btn_capture.setCaptureListener(new CaptureListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.2
            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void changeTime(long j2) {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.changeTime(j2);
                }
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void recordEnd(long j2) {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.recordEnd(j2);
                }
                CaptureLayout.this.startTypeBtnAnimator();
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void recordError() {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.recordError();
                }
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void recordShort(long j2) {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.recordShort(j2);
                }
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void recordStart() {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.recordStart();
                }
                CaptureLayout.this.startAlphaAnimation();
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void recordZoom(float f2) {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.recordZoom(f2);
                }
            }

            @Override // com.luck.lib.camerax.listener.CaptureListener
            public void takePictures() {
                if (CaptureLayout.this.captureListener != null) {
                    CaptureLayout.this.captureListener.takePictures();
                }
                CaptureLayout.this.startAlphaAnimation();
            }
        });
        this.btn_cancel = new TypeButton(getContext(), 1, this.button_size);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams3.gravity = 16;
        layoutParams3.setMargins((this.layout_width / 4) - (this.button_size / 2), 0, 0, 0);
        this.btn_cancel.setLayoutParams(layoutParams3);
        this.btn_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CaptureLayout.this.typeListener != null) {
                    CaptureLayout.this.typeListener.cancel();
                }
            }
        });
        this.btn_confirm = new TypeButton(getContext(), 2, this.button_size);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams4.gravity = 21;
        layoutParams4.setMargins(0, 0, (this.layout_width / 4) - (this.button_size / 2), 0);
        this.btn_confirm.setLayoutParams(layoutParams4);
        this.btn_confirm.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CaptureLayout.this.typeListener != null) {
                    CaptureLayout.this.typeListener.confirm();
                }
            }
        });
        this.btn_return = new ReturnButton(getContext(), (int) (this.button_size / 2.5f));
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams5.gravity = 16;
        layoutParams5.setMargins(this.layout_width / 6, 0, 0, 0);
        this.btn_return.setLayoutParams(layoutParams5);
        this.btn_return.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CaptureLayout.this.leftClickListener != null) {
                    CaptureLayout.this.leftClickListener.onClick();
                }
            }
        });
        this.iv_custom_left = new ImageView(getContext());
        int i2 = this.button_size;
        FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams((int) (i2 / 2.5f), (int) (i2 / 2.5f));
        layoutParams6.gravity = 16;
        layoutParams6.setMargins(this.layout_width / 6, 0, 0, 0);
        this.iv_custom_left.setLayoutParams(layoutParams6);
        this.iv_custom_left.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CaptureLayout.this.leftClickListener != null) {
                    CaptureLayout.this.leftClickListener.onClick();
                }
            }
        });
        this.iv_custom_right = new ImageView(getContext());
        int i3 = this.button_size;
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams((int) (i3 / 2.5f), (int) (i3 / 2.5f));
        layoutParams7.gravity = 21;
        layoutParams7.setMargins(0, 0, this.layout_width / 6, 0);
        this.iv_custom_right.setLayoutParams(layoutParams7);
        this.iv_custom_right.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.widget.CaptureLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CaptureLayout.this.rightClickListener != null) {
                    CaptureLayout.this.rightClickListener.onClick();
                }
            }
        });
        this.txt_tip = new TextView(getContext());
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams8.gravity = 1;
        layoutParams8.setMargins(0, 0, 0, 0);
        this.txt_tip.setText(getCaptureTip());
        this.txt_tip.setTextColor(-1);
        this.txt_tip.setGravity(17);
        this.txt_tip.setLayoutParams(layoutParams8);
        addView(this.btn_capture);
        addView(this.progress_bar);
        addView(this.btn_cancel);
        addView(this.btn_confirm);
        addView(this.btn_return);
        addView(this.iv_custom_left);
        addView(this.iv_custom_right);
        addView(this.txt_tip);
    }

    public void initEvent() {
        this.iv_custom_right.setVisibility(8);
        this.btn_cancel.setVisibility(8);
        this.btn_confirm.setVisibility(8);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        setMeasuredDimension(this.layout_width, this.layout_height);
    }

    public void resetCaptureLayout() {
        this.btn_capture.resetState();
        this.btn_cancel.setVisibility(8);
        this.btn_confirm.setVisibility(8);
        this.btn_capture.setVisibility(0);
        this.txt_tip.setText(getCaptureTip());
        this.txt_tip.setVisibility(0);
        if (this.iconLeft != 0) {
            this.iv_custom_left.setVisibility(0);
        } else {
            this.btn_return.setVisibility(0);
        }
        if (this.iconRight != 0) {
            this.iv_custom_right.setVisibility(0);
        }
    }

    public void setButtonCaptureEnabled(boolean z2) {
        this.progress_bar.setVisibility(z2 ? 8 : 0);
        this.btn_capture.setButtonCaptureEnabled(z2);
    }

    public void setButtonFeatures(int i2) {
        this.btn_capture.setButtonFeatures(i2);
        this.txt_tip.setText(getCaptureTip());
    }

    public void setCaptureListener(CaptureListener captureListener) {
        this.captureListener = captureListener;
    }

    public void setCaptureLoadingColor(int i2) {
        this.progress_bar.getIndeterminateDrawable().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(i2, BlendModeCompat.SRC_IN));
    }

    public void setDuration(int i2) {
        this.btn_capture.setMaxDuration(i2);
    }

    public void setIconSrc(int i2, int i3) {
        this.iconLeft = i2;
        this.iconRight = i3;
        if (i2 != 0) {
            this.iv_custom_left.setImageResource(i2);
            this.iv_custom_left.setVisibility(0);
            this.btn_return.setVisibility(8);
        } else {
            this.iv_custom_left.setVisibility(8);
            this.btn_return.setVisibility(0);
        }
        if (this.iconRight == 0) {
            this.iv_custom_right.setVisibility(8);
        } else {
            this.iv_custom_right.setImageResource(i3);
            this.iv_custom_right.setVisibility(0);
        }
    }

    public void setLeftClickListener(ClickListener clickListener) {
        this.leftClickListener = clickListener;
    }

    public void setMinDuration(int i2) {
        this.btn_capture.setMinDuration(i2);
    }

    public void setProgressColor(int i2) {
        this.btn_capture.setProgressColor(i2);
    }

    public void setRightClickListener(ClickListener clickListener) {
        this.rightClickListener = clickListener;
    }

    public void setTextWithAnimation(String str) {
        this.txt_tip.setText(str);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.txt_tip, "alpha", 0.0f, 1.0f, 1.0f, 0.0f);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.luck.lib.camerax.widget.CaptureLayout.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CaptureLayout.this.txt_tip.setText(CaptureLayout.this.getCaptureTip());
                CaptureLayout.this.txt_tip.setAlpha(1.0f);
            }
        });
        objectAnimatorOfFloat.setDuration(2500L);
        objectAnimatorOfFloat.start();
    }

    public void setTip(String str) {
        this.txt_tip.setText(str);
    }

    public void setTypeListener(TypeListener typeListener) {
        this.typeListener = typeListener;
    }

    public void showTip() {
        this.txt_tip.setVisibility(0);
    }

    public void startAlphaAnimation() {
        this.txt_tip.setVisibility(4);
    }

    public void startTypeBtnAnimator() {
        if (this.iconLeft != 0) {
            this.iv_custom_left.setVisibility(8);
        } else {
            this.btn_return.setVisibility(8);
        }
        if (this.iconRight != 0) {
            this.iv_custom_right.setVisibility(8);
        }
        this.btn_capture.setVisibility(8);
        this.btn_cancel.setVisibility(0);
        this.btn_confirm.setVisibility(0);
        this.btn_cancel.setClickable(false);
        this.btn_confirm.setClickable(false);
        this.iv_custom_left.setVisibility(8);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.btn_cancel, "translationX", this.layout_width / 4, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.btn_confirm, "translationX", (-this.layout_width) / 4, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.lib.camerax.widget.CaptureLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CaptureLayout.this.btn_cancel.setClickable(true);
                CaptureLayout.this.btn_confirm.setClickable(true);
            }
        });
        animatorSet.setDuration(500L);
        animatorSet.start();
    }

    public CaptureLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CaptureLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.iconLeft = 0;
        this.iconRight = 0;
        int screenWidth = DensityUtil.getScreenWidth(getContext());
        if (getResources().getConfiguration().orientation == 1) {
            this.layout_width = screenWidth;
        } else {
            this.layout_width = screenWidth / 2;
        }
        int i3 = (int) (this.layout_width / 4.5f);
        this.button_size = i3;
        this.layout_height = i3 + ((i3 / 5) * 2) + 100;
        initView();
        initEvent();
    }
}
