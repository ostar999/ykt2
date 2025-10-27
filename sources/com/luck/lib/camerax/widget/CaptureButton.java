package com.luck.lib.camerax.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.hjq.permissions.Permission;
import com.luck.lib.camerax.CustomCameraConfig;
import com.luck.lib.camerax.listener.CaptureListener;
import com.luck.lib.camerax.listener.IObtainCameraView;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDescriptionListener;
import com.luck.lib.camerax.permissions.PermissionChecker;
import com.luck.lib.camerax.permissions.PermissionResultCallback;
import com.luck.lib.camerax.permissions.SimpleXPermissionUtil;
import com.luck.lib.camerax.utils.DoubleUtils;
import com.luck.lib.camerax.utils.SimpleXSpUtils;

/* loaded from: classes4.dex */
public class CaptureButton extends View {
    public static final int STATE_BAN = 5;
    public static final int STATE_IDLE = 1;
    public static final int STATE_LONG_PRESS = 3;
    public static final int STATE_PRESS = 2;
    public static final int STATE_RECORDER_ING = 4;
    private final Activity activity;
    private int buttonState;
    private float button_inside_radius;
    private float button_outside_radius;
    private float button_radius;
    private int button_size;
    private CaptureListener captureListener;
    private float center_X;
    private float center_Y;
    private int currentRecordedTime;
    private float event_Y;
    private int inside_reduce_size;
    private boolean isTakeCamera;
    private LongPressRunnable longPressRunnable;
    private Paint mPaint;
    private int maxDuration;
    private int minDuration;
    private int outside_add_size;
    private float progress;
    private int progressColor;
    private RectF rectF;
    private int state;
    private float strokeWidth;
    private RecordCountDownTimer timer;

    public class LongPressRunnable implements Runnable {
        private LongPressRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            CaptureButton.this.state = 3;
            if (PermissionChecker.checkSelfPermission(CaptureButton.this.getContext(), new String[]{Permission.RECORD_AUDIO})) {
                CaptureButton captureButton = CaptureButton.this;
                captureButton.startRecordAnimation(captureButton.button_outside_radius, CaptureButton.this.button_outside_radius + CaptureButton.this.outside_add_size, CaptureButton.this.button_inside_radius, CaptureButton.this.button_inside_radius - CaptureButton.this.inside_reduce_size);
            } else {
                CaptureButton.this.onExplainCallback();
                CaptureButton.this.handlerPressByState();
                PermissionChecker.getInstance().requestPermissions(CaptureButton.this.activity, new String[]{Permission.RECORD_AUDIO}, new PermissionResultCallback() { // from class: com.luck.lib.camerax.widget.CaptureButton.LongPressRunnable.1
                    @Override // com.luck.lib.camerax.permissions.PermissionResultCallback
                    public void onDenied() {
                        OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener;
                        if (CustomCameraConfig.deniedListener == null) {
                            SimpleXPermissionUtil.goIntentSetting(CaptureButton.this.activity, 1103);
                            return;
                        }
                        SimpleXSpUtils.putBoolean(CaptureButton.this.getContext(), Permission.RECORD_AUDIO, true);
                        CustomCameraConfig.deniedListener.onDenied(CaptureButton.this.getContext(), Permission.RECORD_AUDIO, 1103);
                        ViewGroup customCameraView = CaptureButton.this.getCustomCameraView();
                        if (customCameraView == null || (onSimpleXPermissionDescriptionListener = CustomCameraConfig.explainListener) == null) {
                            return;
                        }
                        onSimpleXPermissionDescriptionListener.onDismiss(customCameraView);
                    }

                    @Override // com.luck.lib.camerax.permissions.PermissionResultCallback
                    public void onGranted() {
                        OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener;
                        CaptureButton captureButton2 = CaptureButton.this;
                        captureButton2.postDelayed(captureButton2.longPressRunnable, 500L);
                        ViewGroup customCameraView = CaptureButton.this.getCustomCameraView();
                        if (customCameraView == null || (onSimpleXPermissionDescriptionListener = CustomCameraConfig.explainListener) == null) {
                            return;
                        }
                        onSimpleXPermissionDescriptionListener.onDismiss(customCameraView);
                    }
                });
            }
        }
    }

    public class RecordCountDownTimer extends CountDownTimer {
        public RecordCountDownTimer(long j2, long j3) {
            super(j2, j3);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            CaptureButton.this.recordEnd();
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j2) {
            CaptureButton.this.updateProgress(j2);
        }
    }

    public CaptureButton(Context context) {
        super(context);
        this.progressColor = -300503530;
        this.isTakeCamera = true;
        this.activity = (Activity) context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ViewGroup getCustomCameraView() {
        ComponentCallbacks2 componentCallbacks2 = this.activity;
        if (componentCallbacks2 instanceof IObtainCameraView) {
            return ((IObtainCameraView) componentCallbacks2).getCustomCameraView();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerPressByState() {
        int i2;
        removeCallbacks(this.longPressRunnable);
        int i3 = this.state;
        if (i3 != 2) {
            if ((i3 == 3 || i3 == 4) && PermissionChecker.checkSelfPermission(getContext(), new String[]{Permission.RECORD_AUDIO})) {
                this.timer.cancel();
                recordEnd();
            }
        } else if (this.captureListener == null || !((i2 = this.buttonState) == 1 || i2 == 0)) {
            this.state = 1;
        } else {
            startCaptureAnimation(this.button_inside_radius);
        }
        this.state = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startCaptureAnimation$0(ValueAnimator valueAnimator) {
        this.button_inside_radius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecordAnimation$1(ValueAnimator valueAnimator) {
        this.button_outside_radius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecordAnimation$2(ValueAnimator valueAnimator) {
        this.button_inside_radius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExplainCallback() {
        ViewGroup customCameraView;
        if (CustomCameraConfig.explainListener == null || SimpleXSpUtils.getBoolean(getContext(), Permission.RECORD_AUDIO, false) || (customCameraView = getCustomCameraView()) == null) {
            return;
        }
        CustomCameraConfig.explainListener.onPermissionDescription(getContext(), customCameraView, Permission.RECORD_AUDIO);
    }

    private void resetRecordAnim() {
        this.state = 5;
        this.progress = 0.0f;
        invalidate();
        float f2 = this.button_outside_radius;
        float f3 = this.button_radius;
        startRecordAnimation(f2, f3, this.button_inside_radius, 0.75f * f3);
    }

    private void startCaptureAnimation(float f2) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, 0.75f * f2, f2);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.luck.lib.camerax.widget.c
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f8887c.lambda$startCaptureAnimation$0(valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.luck.lib.camerax.widget.CaptureButton.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (CaptureButton.this.captureListener != null) {
                    CaptureButton.this.captureListener.takePictures();
                }
                CaptureButton.this.state = 5;
            }
        });
        valueAnimatorOfFloat.setDuration(50L);
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecordAnimation(float f2, float f3, float f4, float f5) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f3);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f4, f5);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.luck.lib.camerax.widget.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f8885c.lambda$startRecordAnimation$1(valueAnimator);
            }
        });
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.luck.lib.camerax.widget.b
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f8886c.lambda$startRecordAnimation$2(valueAnimator);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.lib.camerax.widget.CaptureButton.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (DoubleUtils.isFastDoubleClick()) {
                    return;
                }
                if (CaptureButton.this.state != 3) {
                    CaptureButton.this.state = 1;
                    return;
                }
                if (CaptureButton.this.captureListener != null) {
                    CaptureButton.this.captureListener.recordStart();
                }
                CaptureButton.this.state = 4;
                CaptureButton.this.timer.start();
            }
        });
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        animatorSet.setDuration(100L);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(long j2) {
        int i2 = this.maxDuration;
        this.currentRecordedTime = (int) (i2 - j2);
        this.progress = 360.0f - ((j2 / i2) * 360.0f);
        invalidate();
        CaptureListener captureListener = this.captureListener;
        if (captureListener != null) {
            captureListener.changeTime(j2);
        }
    }

    public int getButtonFeatures() {
        return this.buttonState;
    }

    public boolean isIdle() {
        return this.state == 1;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(-287515428);
        canvas.drawCircle(this.center_X, this.center_Y, this.button_outside_radius, this.mPaint);
        this.mPaint.setColor(-1);
        canvas.drawCircle(this.center_X, this.center_Y, this.button_inside_radius, this.mPaint);
        if (this.state == 4) {
            this.mPaint.setColor(this.progressColor);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.strokeWidth);
            canvas.drawArc(this.rectF, -90.0f, this.progress, false, this.mPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int i4 = this.button_size;
        int i5 = this.outside_add_size;
        setMeasuredDimension((i5 * 2) + i4, i4 + (i5 * 2));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CaptureListener captureListener;
        int i2;
        if (this.isTakeCamera) {
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1) {
                    handlerPressByState();
                } else if (action == 2 && (captureListener = this.captureListener) != null && this.state == 4 && ((i2 = this.buttonState) == 2 || i2 == 0)) {
                    captureListener.recordZoom(this.event_Y - motionEvent.getY());
                }
            } else if (motionEvent.getPointerCount() <= 1 && this.state == 1) {
                this.event_Y = motionEvent.getY();
                this.state = 2;
                if (this.buttonState != 1) {
                    postDelayed(this.longPressRunnable, 500L);
                }
            }
        }
        return true;
    }

    public void recordEnd() {
        CaptureListener captureListener = this.captureListener;
        if (captureListener != null) {
            int i2 = this.currentRecordedTime;
            if (i2 < this.minDuration) {
                captureListener.recordShort(i2);
            } else {
                captureListener.recordEnd(i2);
            }
        }
        resetRecordAnim();
    }

    public void resetState() {
        this.state = 1;
    }

    public void setButtonCaptureEnabled(boolean z2) {
        this.isTakeCamera = z2;
    }

    public void setButtonFeatures(int i2) {
        this.buttonState = i2;
    }

    public void setCaptureListener(CaptureListener captureListener) {
        this.captureListener = captureListener;
    }

    public void setMaxDuration(int i2) {
        this.maxDuration = i2;
        this.timer = new RecordCountDownTimer(this.maxDuration, r0 / 360);
    }

    public void setMinDuration(int i2) {
        this.minDuration = i2;
    }

    public void setProgressColor(int i2) {
        this.progressColor = i2;
    }

    public CaptureButton(Context context, int i2) {
        super(context);
        this.progressColor = -300503530;
        this.isTakeCamera = true;
        this.activity = (Activity) context;
        this.button_size = i2;
        float f2 = i2 / 2.0f;
        this.button_radius = f2;
        this.button_outside_radius = f2;
        this.button_inside_radius = f2 * 0.75f;
        this.strokeWidth = i2 / 15;
        int i3 = i2 / 8;
        this.outside_add_size = i3;
        this.inside_reduce_size = i3;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.progress = 0.0f;
        this.longPressRunnable = new LongPressRunnable();
        this.state = 1;
        this.buttonState = 0;
        this.maxDuration = CustomCameraConfig.DEFAULT_MAX_RECORD_VIDEO;
        this.minDuration = 1500;
        int i4 = this.button_size;
        int i5 = this.outside_add_size;
        this.center_X = ((i5 * 2) + i4) / 2;
        this.center_Y = (i4 + (i5 * 2)) / 2;
        float f3 = this.center_X;
        float f4 = this.button_radius;
        int i6 = this.outside_add_size;
        float f5 = this.strokeWidth;
        float f6 = this.center_Y;
        this.rectF = new RectF(f3 - ((i6 + f4) - (f5 / 2.0f)), f6 - ((i6 + f4) - (f5 / 2.0f)), f3 + ((i6 + f4) - (f5 / 2.0f)), f6 + ((f4 + i6) - (f5 / 2.0f)));
        this.timer = new RecordCountDownTimer(this.maxDuration, r15 / 360);
    }
}
