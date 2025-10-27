package com.app.hubert.guide.core;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.listener.AnimationListenerAdapter;
import com.app.hubert.guide.listener.OnHighlightDrewListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import com.app.hubert.guide.model.RelativeGuide;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class GuideLayout extends FrameLayout {
    public static final int DEFAULT_BACKGROUND_COLOR = -1308622848;
    private Controller controller;
    private float downX;
    private float downY;
    public GuidePage guidePage;
    private OnGuideLayoutDismissListener listener;
    private Paint mPaint;
    private int touchSlop;

    /* renamed from: com.app.hubert.guide.core.GuideLayout$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$app$hubert$guide$model$HighLight$Shape;

        static {
            int[] iArr = new int[HighLight.Shape.values().length];
            $SwitchMap$com$app$hubert$guide$model$HighLight$Shape = iArr;
            try {
                iArr[HighLight.Shape.CIRCLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$app$hubert$guide$model$HighLight$Shape[HighLight.Shape.OVAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$app$hubert$guide$model$HighLight$Shape[HighLight.Shape.ROUND_RECTANGLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$app$hubert$guide$model$HighLight$Shape[HighLight.Shape.RECTANGLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public interface OnGuideLayoutDismissListener {
        void onGuideLayoutDismiss(GuideLayout guideLayout);
    }

    public GuideLayout(Context context, GuidePage guidePage, Controller controller) {
        super(context);
        init();
        setGuidePage(guidePage);
        this.controller = controller;
    }

    private void addCustomToLayout(GuidePage guidePage) {
        removeAllViews();
        int layoutResId = guidePage.getLayoutResId();
        if (layoutResId != 0) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(layoutResId, (ViewGroup) this, false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            int[] clickToDismissIds = guidePage.getClickToDismissIds();
            if (clickToDismissIds != null && clickToDismissIds.length > 0) {
                for (int i2 : clickToDismissIds) {
                    View viewFindViewById = viewInflate.findViewById(i2);
                    if (viewFindViewById != null) {
                        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.app.hubert.guide.core.GuideLayout.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                GuideLayout.this.remove();
                            }
                        });
                    } else {
                        Log.w(NewbieGuide.TAG, "can't find the view by id : " + i2 + " which used to remove guide page");
                    }
                }
            }
            OnLayoutInflatedListener onLayoutInflatedListener = guidePage.getOnLayoutInflatedListener();
            if (onLayoutInflatedListener != null) {
                onLayoutInflatedListener.onLayoutInflated(viewInflate, this.controller);
            }
            addView(viewInflate, layoutParams);
        }
        List<RelativeGuide> relativeGuides = guidePage.getRelativeGuides();
        if (relativeGuides.size() > 0) {
            Iterator<RelativeGuide> it = relativeGuides.iterator();
            while (it.hasNext()) {
                addView(it.next().getGuideLayout((ViewGroup) getParent(), this.controller));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
            OnGuideLayoutDismissListener onGuideLayoutDismissListener = this.listener;
            if (onGuideLayoutDismissListener != null) {
                onGuideLayoutDismissListener.onGuideLayoutDismiss(this);
            }
        }
    }

    private void drawHighlights(Canvas canvas) {
        List<HighLight> highLights = this.guidePage.getHighLights();
        if (highLights != null) {
            for (HighLight highLight : highLights) {
                RectF rectF = highLight.getRectF((ViewGroup) getParent());
                int i2 = AnonymousClass4.$SwitchMap$com$app$hubert$guide$model$HighLight$Shape[highLight.getShape().ordinal()];
                if (i2 == 1) {
                    canvas.drawCircle(rectF.centerX(), rectF.centerY(), highLight.getRadius(), this.mPaint);
                } else if (i2 == 2) {
                    canvas.drawOval(rectF, this.mPaint);
                } else if (i2 != 3) {
                    canvas.drawRect(rectF, this.mPaint);
                } else {
                    canvas.drawRoundRect(rectF, highLight.getRound(), highLight.getRound(), this.mPaint);
                }
                notifyDrewListener(canvas, highLight, rectF);
            }
        }
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mPaint.setMaskFilter(new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.INNER));
        setLayerType(1, null);
        setWillNotDraw(false);
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void notifyClickListener(HighLight highLight) {
        View.OnClickListener onClickListener;
        HighlightOptions options = highLight.getOptions();
        if (options == null || (onClickListener = options.onClickListener) == null) {
            return;
        }
        onClickListener.onClick(this);
    }

    private void notifyDrewListener(Canvas canvas, HighLight highLight, RectF rectF) {
        OnHighlightDrewListener onHighlightDrewListener;
        HighlightOptions options = highLight.getOptions();
        if (options == null || (onHighlightDrewListener = options.onHighlightDrewListener) == null) {
            return;
        }
        onHighlightDrewListener.onHighlightDrew(canvas, rectF);
    }

    private void setGuidePage(GuidePage guidePage) {
        this.guidePage = guidePage;
        setOnClickListener(new View.OnClickListener() { // from class: com.app.hubert.guide.core.GuideLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (GuideLayout.this.guidePage.isEverywhereCancelable()) {
                    GuideLayout.this.remove();
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        addCustomToLayout(this.guidePage);
        Animation enterAnimation = this.guidePage.getEnterAnimation();
        if (enterAnimation != null) {
            startAnimation(enterAnimation);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int backgroundColor = this.guidePage.getBackgroundColor();
        if (backgroundColor == 0) {
            backgroundColor = DEFAULT_BACKGROUND_COLOR;
        }
        canvas.drawColor(backgroundColor);
        drawHighlights(canvas);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = motionEvent.getX();
            this.downY = motionEvent.getY();
        } else if (action == 1 || action == 3) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            if (Math.abs(x2 - this.downX) < this.touchSlop && Math.abs(y2 - this.downY) < this.touchSlop) {
                for (HighLight highLight : this.guidePage.getHighLights()) {
                    if (highLight.getRectF((ViewGroup) getParent()).contains(x2, y2)) {
                        notifyClickListener(highLight);
                        return true;
                    }
                }
                performClick();
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    public void remove() {
        Animation exitAnimation = this.guidePage.getExitAnimation();
        if (exitAnimation == null) {
            dismiss();
        } else {
            exitAnimation.setAnimationListener(new AnimationListenerAdapter() { // from class: com.app.hubert.guide.core.GuideLayout.3
                @Override // com.app.hubert.guide.listener.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    GuideLayout.this.dismiss();
                }
            });
            startAnimation(exitAnimation);
        }
    }

    public void setOnGuideLayoutDismissListener(OnGuideLayoutDismissListener onGuideLayoutDismissListener) {
        this.listener = onGuideLayoutDismissListener;
    }

    private GuideLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private GuideLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
