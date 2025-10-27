package com.easefun.polyv.livecommon.ui.widget.expandmenu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;

/* loaded from: classes3.dex */
public class PLVHorizontalExpandMenu extends RelativeLayout {
    public static final int Left = 1;
    public static final int Right = 0;
    private ExpandMenuAnim anim;
    private float backPathWidth;
    private float buttonBottom;
    private int buttonIconColor;
    private float buttonIconDegrees;
    private Paint buttonIconPaint;
    private float buttonIconSize;
    private float buttonIconStrokeWidth;
    private int buttonRadius;
    private int buttonStyle;
    private float buttonTop;
    private View childView;
    private int defaultHeight;
    private int defaultWidth;
    private float downX;
    private float downY;
    private int expandAnimTime;
    private boolean isAnimEnd;
    private boolean isExpand;
    private Point leftButtonCenter;
    private float leftButtonLeft;
    private float leftButtonRight;
    private Bitmap leftIconBitmap;
    private int leftIconId;
    private AttributeSet mAttrs;
    private Context mContext;
    private float maxBackPathWidth;
    private int menuBackColor;
    private float menuCornerRadius;
    private int menuLeft;
    private int menuRight;
    private int menuStrokeColor;
    private float menuStrokeSize;
    private Path path;
    private Point rightButtonCenter;
    private float rightButtonLeft;
    private float rightButtonRight;
    private int viewHeight;
    private int viewWidth;

    public class ExpandMenuAnim extends Animation {
        public ExpandMenuAnim() {
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float interpolatedTime, Transformation t2) {
            super.applyTransformation(interpolatedTime, t2);
            float f2 = PLVHorizontalExpandMenu.this.menuRight - (PLVHorizontalExpandMenu.this.buttonRadius * 2);
            float f3 = PLVHorizontalExpandMenu.this.menuLeft + (PLVHorizontalExpandMenu.this.buttonRadius * 2);
            if (PLVHorizontalExpandMenu.this.childView != null) {
                PLVHorizontalExpandMenu.this.childView.setVisibility(8);
            }
            if (PLVHorizontalExpandMenu.this.isExpand) {
                PLVHorizontalExpandMenu pLVHorizontalExpandMenu = PLVHorizontalExpandMenu.this;
                pLVHorizontalExpandMenu.backPathWidth = pLVHorizontalExpandMenu.maxBackPathWidth * interpolatedTime;
                PLVHorizontalExpandMenu.this.buttonIconDegrees = interpolatedTime * 90.0f;
                if (PLVHorizontalExpandMenu.this.backPathWidth == PLVHorizontalExpandMenu.this.maxBackPathWidth && PLVHorizontalExpandMenu.this.childView != null) {
                    PLVHorizontalExpandMenu.this.childView.setVisibility(0);
                }
            } else {
                PLVHorizontalExpandMenu pLVHorizontalExpandMenu2 = PLVHorizontalExpandMenu.this;
                pLVHorizontalExpandMenu2.backPathWidth = pLVHorizontalExpandMenu2.maxBackPathWidth - (PLVHorizontalExpandMenu.this.maxBackPathWidth * interpolatedTime);
                PLVHorizontalExpandMenu.this.buttonIconDegrees = 90.0f - (interpolatedTime * 90.0f);
            }
            if (PLVHorizontalExpandMenu.this.buttonStyle == 0) {
                PLVHorizontalExpandMenu pLVHorizontalExpandMenu3 = PLVHorizontalExpandMenu.this;
                pLVHorizontalExpandMenu3.layout((int) (f2 - pLVHorizontalExpandMenu3.backPathWidth), PLVHorizontalExpandMenu.this.getTop(), PLVHorizontalExpandMenu.this.menuRight, PLVHorizontalExpandMenu.this.getBottom());
            } else {
                PLVHorizontalExpandMenu pLVHorizontalExpandMenu4 = PLVHorizontalExpandMenu.this;
                pLVHorizontalExpandMenu4.layout(pLVHorizontalExpandMenu4.menuLeft, PLVHorizontalExpandMenu.this.getTop(), (int) (f3 + PLVHorizontalExpandMenu.this.backPathWidth), PLVHorizontalExpandMenu.this.getBottom());
            }
            PLVHorizontalExpandMenu.this.postInvalidate();
        }
    }

    public PLVHorizontalExpandMenu(Context context) {
        super(context);
        this.downX = -1.0f;
        this.downY = -1.0f;
        this.mContext = context;
        init();
    }

    private void drawLeftIcon(Canvas canvas) {
        if (this.leftIconId != 0) {
            if (this.leftIconBitmap == null) {
                this.leftIconBitmap = BitmapFactory.decodeResource(getResources(), this.leftIconId);
            }
            if (this.leftIconBitmap != null) {
                canvas.drawBitmap(this.leftIconBitmap, (Rect) null, new Rect(0, 0, (int) this.leftButtonRight, (int) this.buttonBottom), this.buttonIconPaint);
                return;
            }
            return;
        }
        this.path.reset();
        Path path = this.path;
        Point point = this.leftButtonCenter;
        path.moveTo(point.x - this.buttonIconSize, point.y);
        Path path2 = this.path;
        Point point2 = this.leftButtonCenter;
        path2.lineTo(point2.x + this.buttonIconSize, point2.y);
        canvas.drawPath(this.path, this.buttonIconPaint);
        canvas.save();
        float f2 = -this.buttonIconDegrees;
        Point point3 = this.leftButtonCenter;
        canvas.rotate(f2, point3.x, point3.y);
        this.path.reset();
        Path path3 = this.path;
        Point point4 = this.leftButtonCenter;
        path3.moveTo(point4.x, point4.y - this.buttonIconSize);
        Path path4 = this.path;
        Point point5 = this.leftButtonCenter;
        path4.lineTo(point5.x, point5.y + this.buttonIconSize);
        canvas.drawPath(this.path, this.buttonIconPaint);
        canvas.restore();
    }

    private void drawRightIcon(Canvas canvas) {
        this.path.reset();
        Path path = this.path;
        Point point = this.rightButtonCenter;
        path.moveTo(point.x - this.buttonIconSize, point.y);
        Path path2 = this.path;
        Point point2 = this.rightButtonCenter;
        path2.lineTo(point2.x + this.buttonIconSize, point2.y);
        canvas.drawPath(this.path, this.buttonIconPaint);
        canvas.save();
        float f2 = this.buttonIconDegrees;
        Point point3 = this.rightButtonCenter;
        canvas.rotate(f2, point3.x, point3.y);
        this.path.reset();
        Path path3 = this.path;
        Point point4 = this.rightButtonCenter;
        path3.moveTo(point4.x, point4.y - this.buttonIconSize);
        Path path4 = this.path;
        Point point5 = this.rightButtonCenter;
        path4.lineTo(point5.x, point5.y + this.buttonIconSize);
        canvas.drawPath(this.path, this.buttonIconPaint);
        canvas.restore();
    }

    private void expandMenu(int time) {
        this.anim.setDuration(time);
        this.isExpand = !this.isExpand;
        startAnimation(this.anim);
        this.isAnimEnd = false;
    }

    private void init() {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(this.mAttrs, R.styleable.PLVHorizontalExpandMenu);
        this.defaultWidth = DpOrPxUtils.dip2px(this.mContext, 200.0f);
        this.defaultHeight = DpOrPxUtils.dip2px(this.mContext, 40.0f);
        this.menuBackColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVHorizontalExpandMenu_back_color, -1);
        this.menuStrokeSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVHorizontalExpandMenu_stroke_size, 1.0f);
        this.menuStrokeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVHorizontalExpandMenu_stroke_color, -7829368);
        this.menuCornerRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVHorizontalExpandMenu_corner_radius, DpOrPxUtils.dip2px(this.mContext, 20.0f));
        this.buttonStyle = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVHorizontalExpandMenu_button_style, 0);
        this.buttonIconDegrees = 90.0f;
        this.buttonIconSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVHorizontalExpandMenu_button_icon_size, DpOrPxUtils.dip2px(this.mContext, 8.0f));
        this.buttonIconStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVHorizontalExpandMenu_button_icon_stroke_width, 8.0f);
        this.buttonIconColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVHorizontalExpandMenu_button_icon_color, -7829368);
        this.expandAnimTime = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVHorizontalExpandMenu_expand_time, 200);
        this.leftIconId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.PLVHorizontalExpandMenu_left_icon_id, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.isExpand = true;
        this.isAnimEnd = false;
        Paint paint = new Paint();
        this.buttonIconPaint = paint;
        paint.setColor(this.buttonIconColor);
        this.buttonIconPaint.setStyle(Paint.Style.STROKE);
        this.buttonIconPaint.setStrokeWidth(this.buttonIconStrokeWidth);
        this.buttonIconPaint.setAntiAlias(true);
        this.path = new Path();
        this.leftButtonCenter = new Point();
        this.rightButtonCenter = new Point();
        ExpandMenuAnim expandMenuAnim = new ExpandMenuAnim();
        this.anim = expandMenuAnim;
        expandMenuAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.easefun.polyv.livecommon.ui.widget.expandmenu.widget.PLVHorizontalExpandMenu.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                PLVHorizontalExpandMenu.this.isAnimEnd = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    private void layoutRootButton() {
        this.buttonTop = 0.0f;
        int i2 = this.viewHeight;
        this.buttonBottom = i2;
        Point point = this.rightButtonCenter;
        int i3 = this.viewWidth;
        int i4 = this.buttonRadius;
        point.x = i3 - i4;
        point.y = i2 / 2;
        this.rightButtonLeft = r2 - i4;
        this.rightButtonRight = r2 + i4;
        Point point2 = this.leftButtonCenter;
        point2.x = i4;
        point2.y = i2 / 2;
        this.leftButtonLeft = i4 - i4;
        this.leftButtonRight = i4 + i4;
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        return mode == 1073741824 ? size : mode == Integer.MIN_VALUE ? Math.min(defaultSize, size) : defaultSize;
    }

    private void setMenuBackground() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.menuBackColor);
        gradientDrawable.setStroke((int) this.menuStrokeSize, this.menuStrokeColor);
        gradientDrawable.setCornerRadius(this.menuCornerRadius);
        setBackground(gradientDrawable);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        layoutRootButton();
        if (this.buttonStyle == 0) {
            drawRightIcon(canvas);
        } else {
            drawLeftIcon(canvas);
        }
        super.onDraw(canvas);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        this.menuLeft = getLeft();
        this.menuRight = getRight();
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            this.childView = childAt;
            if (this.isExpand) {
                if (this.buttonStyle == 0) {
                    childAt.layout(this.leftButtonCenter.x, (int) this.buttonTop, (int) this.rightButtonLeft, (int) this.buttonBottom);
                } else {
                    childAt.layout((int) this.leftButtonRight, (int) this.buttonTop, this.rightButtonCenter.x, (int) this.buttonBottom);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.viewWidth, this.viewHeight);
                layoutParams.setMargins(0, 0, this.buttonRadius * 3, 0);
                this.childView.setLayoutParams(layoutParams);
            } else {
                childAt.setVisibility(8);
            }
        }
        if (getChildCount() > 1) {
            throw new IllegalStateException("HorizontalExpandMenu can host only one direct child");
        }
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iMeasureSize = measureSize(this.defaultHeight, heightMeasureSpec);
        int iMeasureSize2 = measureSize(this.defaultWidth, widthMeasureSpec);
        this.viewHeight = iMeasureSize;
        this.viewWidth = iMeasureSize2;
        this.buttonRadius = iMeasureSize / 2;
        layoutRootButton();
        setMeasuredDimension(this.viewWidth, this.viewHeight);
        float f2 = this.viewWidth - (this.buttonRadius * 2);
        this.maxBackPathWidth = f2;
        this.backPathWidth = f2;
        if (getBackground() == null) {
            setMenuBackground();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.viewWidth = w2;
        if (this.isAnimEnd) {
            if (this.buttonStyle == 0) {
                if (this.isExpand) {
                    return;
                }
                layout(this.menuRight - (this.buttonRadius * 2), getTop(), this.menuRight, getBottom());
            } else {
                if (this.isExpand) {
                    return;
                }
                layout(this.menuLeft, getTop(), this.menuLeft + (this.buttonRadius * 2), getBottom());
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x2 = event.getX();
        float y2 = event.getY();
        int action = event.getAction();
        if (action == 0) {
            this.downX = event.getX();
            this.downY = event.getY();
        } else if (action == 1) {
            float f2 = this.backPathWidth;
            if (f2 == this.maxBackPathWidth || f2 == 0.0f) {
                int i2 = this.buttonStyle;
                if (i2 != 0) {
                    if (i2 == 1 && x2 == this.downX && y2 == this.downY && y2 >= this.buttonTop && y2 <= this.buttonBottom && x2 >= this.leftButtonLeft && x2 <= this.leftButtonRight) {
                        expandMenu(this.expandAnimTime);
                    }
                } else if (x2 == this.downX && y2 == this.downY && y2 >= this.buttonTop && y2 <= this.buttonBottom && x2 >= this.rightButtonLeft && x2 <= this.rightButtonRight) {
                    expandMenu(this.expandAnimTime);
                }
            }
        }
        return true;
    }

    public PLVHorizontalExpandMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.downX = -1.0f;
        this.downY = -1.0f;
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }
}
