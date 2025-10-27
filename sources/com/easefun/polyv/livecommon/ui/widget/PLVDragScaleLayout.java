package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVDragScaleLayout extends FrameLayout implements View.OnTouchListener {
    public static final int FLAG_CENTER = 4096;
    public static final int FLAG_CORNER_ALL = 15;
    public static final int FLAG_CORNER_BOTTOM = 12;
    public static final int FLAG_CORNER_LEFT = 6;
    public static final int FLAG_CORNER_LEFT_BOTTOM = 4;
    public static final int FLAG_CORNER_LEFT_TOP = 2;
    public static final int FLAG_CORNER_RIGHT = 9;
    public static final int FLAG_CORNER_RIGHT_BOTTOM = 8;
    public static final int FLAG_CORNER_RIGHT_TOP = 1;
    public static final int FLAG_CORNER_TOP = 3;
    public static final int FLAG_EDGE_ALL = 240;
    public static final int FLAG_EDGE_BOTTOM = 128;
    public static final int FLAG_EDGE_LEFT = 64;
    public static final int FLAG_EDGE_RIGHT = 16;
    public static final int FLAG_EDGE_TOP = 32;
    public static final int FLAG_MULTI_TOUCH = 256;
    private static final String TAG = "PLVDragScaleLayout";
    private float bottom;
    private int edgeResponseSize;
    private int flagDragMode;
    private int flagScaleMode;
    private float left;
    private int maxHeight;
    private int maxWidth;
    private float maxX;
    private float maxY;
    private int minHeight;
    private int minWidth;
    private float minX;
    private float minY;
    private MultiTouchDragGestureDetector multiTouchDragGestureDetector;
    private ScaleGestureDetector multiTouchScaleGestureDetector;
    private float right;
    private SingleTouchScaleGestureDetector singleTouchScaleGestureDetector;

    /* renamed from: top, reason: collision with root package name */
    private float f6551top;
    private int touchPosition;

    public static class MultiTouchDragGestureDetector {
        private static final float DRAG_THRESHOLD = 0.1f;
        private final PointF lastCenterPoint;
        private int lastPointerCount;
        private final OnDragListener onDragListener;
        private final View view;

        public static class Builder {
            private OnDragListener onDragListener;
            private View view;

            public MultiTouchDragGestureDetector build() {
                return new MultiTouchDragGestureDetector(this.view, this.onDragListener);
            }

            public Builder onDragListener(OnDragListener onDragListener) {
                this.onDragListener = onDragListener;
                return this;
            }

            public Builder view(View view) {
                this.view = view;
                return this;
            }
        }

        public interface OnDragListener {
            void onDrag(float dx, float dy, int pointCount);
        }

        private PointF getCenterPoint(MotionEvent event) {
            int pointerCount = event.getPointerCount();
            float x2 = 0.0f;
            float y2 = 0.0f;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                x2 += event.getX(i2);
                y2 += event.getY(i2);
            }
            float f2 = pointerCount;
            float f3 = x2 / f2;
            float f4 = y2 / f2;
            View view = this.view;
            if (view != null) {
                view.getLocationOnScreen(new int[2]);
                f3 += r0[0];
                f4 += r0[1];
            }
            return new PointF(f3, f4);
        }

        public void onTouchEvent(MotionEvent event) {
            if (event.getAction() == 0) {
                this.lastCenterPoint.set(getCenterPoint(event));
            }
            if (event.getAction() == 2) {
                if (this.lastPointerCount != event.getPointerCount()) {
                    this.lastPointerCount = event.getPointerCount();
                    this.lastCenterPoint.set(getCenterPoint(event));
                    return;
                }
                PointF centerPoint = getCenterPoint(event);
                float f2 = centerPoint.x;
                PointF pointF = this.lastCenterPoint;
                float f3 = f2 - pointF.x;
                float f4 = centerPoint.y - pointF.y;
                OnDragListener onDragListener = this.onDragListener;
                if (onDragListener != null) {
                    onDragListener.onDrag(f3, f4, event.getPointerCount());
                }
                this.lastCenterPoint.set(centerPoint);
            }
        }

        private MultiTouchDragGestureDetector(View view, OnDragListener onDragListener) {
            this.lastCenterPoint = new PointF();
            this.lastPointerCount = 0;
            this.view = view;
            this.onDragListener = onDragListener;
        }
    }

    public static class SingleTouchScaleGestureDetector {
        private int edgeResponseSize;
        private float lastX;
        private float lastY;
        private final OnScaleListener onScaleListener;
        private int touchPosition;
        private final View view;

        public static class Builder {
            private int edgeResponseSize;
            private OnScaleListener onScaleListener;
            private View view;

            public SingleTouchScaleGestureDetector build() {
                return new SingleTouchScaleGestureDetector(this.view, this.onScaleListener, this.edgeResponseSize);
            }

            public Builder edgeResponseSize(int edgeResponseSize) {
                this.edgeResponseSize = edgeResponseSize;
                return this;
            }

            public Builder onScaleListener(OnScaleListener onScaleListener) {
                this.onScaleListener = onScaleListener;
                return this;
            }

            public Builder view(View view) {
                this.view = view;
                return this;
            }
        }

        public interface OnScaleListener {
            void onScale(float leftDx, float rightDx, float topDy, float bottomDy);
        }

        private void notifyOnScale(float leftDx, float rightDx, float topDy, float bottomDy) {
            OnScaleListener onScaleListener = this.onScaleListener;
            if (onScaleListener != null) {
                onScaleListener.onScale(leftDx, rightDx, topDy, bottomDy);
            }
        }

        private void processScale(float dx, float dy) {
            int i2 = this.touchPosition;
            if (i2 == 1) {
                notifyOnScale(0.0f, dx, dy, 0.0f);
                return;
            }
            if (i2 == 2) {
                notifyOnScale(dx, 0.0f, dy, 0.0f);
                return;
            }
            if (i2 == 4) {
                notifyOnScale(dx, 0.0f, 0.0f, dy);
                return;
            }
            if (i2 == 8) {
                notifyOnScale(0.0f, dx, 0.0f, dy);
                return;
            }
            if (i2 == 16) {
                notifyOnScale(0.0f, dx, 0.0f, 0.0f);
                return;
            }
            if (i2 == 32) {
                notifyOnScale(0.0f, 0.0f, dy, 0.0f);
            } else if (i2 == 64) {
                notifyOnScale(dx, 0.0f, 0.0f, 0.0f);
            } else {
                if (i2 != 128) {
                    return;
                }
                notifyOnScale(0.0f, 0.0f, 0.0f, dy);
            }
        }

        public void onTouchEvent(MotionEvent event) {
            if (event.getPointerCount() > 1) {
                return;
            }
            if (event.getAction() == 0) {
                this.touchPosition = PLVDragScaleLayout.parseTouchPosition(this.view, (int) event.getX(), (int) event.getY(), this.edgeResponseSize);
                this.lastX = event.getRawX();
                this.lastY = event.getRawY();
            }
            if (event.getAction() == 2) {
                float rawX = event.getRawX() - this.lastX;
                float rawY = event.getRawY() - this.lastY;
                this.lastX = event.getRawX();
                this.lastY = event.getRawY();
                processScale(rawX, rawY);
            }
        }

        public void setEdgeResponseSize(int edgeResponseSize) {
            this.edgeResponseSize = edgeResponseSize;
        }

        private SingleTouchScaleGestureDetector(View view, OnScaleListener onScaleListener, int edgeResponseSize) {
            this.view = view;
            this.onScaleListener = onScaleListener;
            this.edgeResponseSize = edgeResponseSize;
        }
    }

    public PLVDragScaleLayout(@NonNull Context context) {
        this(context, null);
    }

    private void checkFlagsConflict() {
        int i2 = ((this.flagDragMode & this.flagScaleMode) | 256) ^ 256;
        if (i2 != 0) {
            PLVCommonLog.e(TAG, "不能将同一flag同时应用在拖拽和缩放模式上");
            this.flagDragMode -= i2;
            this.flagScaleMode -= i2;
        }
    }

    private void initMultiTouchDragGestureDetector() {
        this.multiTouchDragGestureDetector = new MultiTouchDragGestureDetector.Builder().view(this).onDragListener(new MultiTouchDragGestureDetector.OnDragListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVDragScaleLayout.1
            @Override // com.easefun.polyv.livecommon.ui.widget.PLVDragScaleLayout.MultiTouchDragGestureDetector.OnDragListener
            public void onDrag(float dx, float dy, int pointCount) {
                if (pointCount == 1 && (PLVDragScaleLayout.this.flagDragMode & PLVDragScaleLayout.this.touchPosition) != 0) {
                    PLVDragScaleLayout.this.processDrag(dx, dy);
                } else {
                    if (pointCount <= 1 || (PLVDragScaleLayout.this.flagDragMode & 256) == 0) {
                        return;
                    }
                    PLVDragScaleLayout.this.processDrag(dx, dy);
                }
            }
        }).build();
    }

    private void initMultiTouchScaleGestureDetector() {
        this.multiTouchScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVDragScaleLayout.3
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float f2 = PLVDragScaleLayout.this.right - PLVDragScaleLayout.this.left;
                float f3 = PLVDragScaleLayout.this.bottom - PLVDragScaleLayout.this.f6551top;
                float f4 = ((f2 * scaleFactor) - f2) / 2.0f;
                float f5 = ((scaleFactor * f3) - f3) / 2.0f;
                PLVDragScaleLayout.this.processScale(-f4, f4, -f5, f5);
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return (PLVDragScaleLayout.this.flagScaleMode & 256) != 0;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        });
    }

    private void initSingleTouchScaleGestureDetector() {
        this.singleTouchScaleGestureDetector = new SingleTouchScaleGestureDetector.Builder().view(this).edgeResponseSize(this.edgeResponseSize).onScaleListener(new SingleTouchScaleGestureDetector.OnScaleListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVDragScaleLayout.2
            @Override // com.easefun.polyv.livecommon.ui.widget.PLVDragScaleLayout.SingleTouchScaleGestureDetector.OnScaleListener
            public void onScale(float leftDx, float rightDx, float topDy, float bottomDy) {
                if ((PLVDragScaleLayout.this.flagScaleMode & PLVDragScaleLayout.this.touchPosition) != 0) {
                    PLVDragScaleLayout.this.processScale(leftDx, rightDx, topDy, bottomDy);
                }
            }
        }).build();
    }

    private void initView(@Nullable AttributeSet attrs) {
        parseAttrSet(attrs);
        initMultiTouchDragGestureDetector();
        initSingleTouchScaleGestureDetector();
        initMultiTouchScaleGestureDetector();
        setOnTouchListener(this);
        setClickable(true);
    }

    private void parseAttrSet(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVDragScaleLayout);
        int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVDragScaleLayout_plvDrag, 0);
        int integer2 = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVDragScaleLayout_plvScale, 0);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVDragScaleLayout_plvMinX, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVDragScaleLayout_plvMaxX, 0.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVDragScaleLayout_plvMinY, 0.0f);
        float dimension4 = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVDragScaleLayout_plvMaxY, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        setDragRange(dimension, dimension2, dimension3, dimension4);
        setDragScaleMode(integer, integer2);
    }

    public static int parseTouchPosition(View v2, int x2, int y2, int edgeResponseSize) {
        int top2 = v2.getTop();
        int bottom = v2.getBottom();
        int right = v2.getRight() - v2.getLeft();
        int i2 = bottom - top2;
        if (x2 < edgeResponseSize && y2 < edgeResponseSize) {
            return 2;
        }
        int i3 = right - edgeResponseSize;
        if (x2 > i3 && y2 < edgeResponseSize) {
            return 1;
        }
        if (x2 < edgeResponseSize && y2 > i2 - edgeResponseSize) {
            return 4;
        }
        if (x2 > i3 && y2 > i2 - edgeResponseSize) {
            return 8;
        }
        if (x2 < edgeResponseSize) {
            return 64;
        }
        if (y2 < edgeResponseSize) {
            return 32;
        }
        if (x2 > i3) {
            return 16;
        }
        return y2 > i2 - edgeResponseSize ? 128 : 4096;
    }

    public void dispatchTouchEventToChildren(MotionEvent ev) {
        ev.getAction();
        int x2 = (int) ev.getX();
        int y2 = (int) ev.getY();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() != 8 && x2 >= childAt.getLeft() && x2 < childAt.getRight() && y2 >= childAt.getTop() && y2 < childAt.getBottom() && childAt.dispatchTouchEvent(ev)) {
                return;
            }
        }
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public float getMinX() {
        return this.minX;
    }

    public float getMinY() {
        return this.minY;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v2, MotionEvent event) {
        if (event.getAction() == 0) {
            this.touchPosition = parseTouchPosition(this, (int) event.getX(), (int) event.getY(), this.edgeResponseSize);
            this.left = getLeft();
            this.right = getRight();
            this.f6551top = getTop();
            this.bottom = getBottom();
        }
        this.multiTouchScaleGestureDetector.onTouchEvent(event);
        this.singleTouchScaleGestureDetector.onTouchEvent(event);
        this.multiTouchDragGestureDetector.onTouchEvent(event);
        updateLayout();
        dispatchTouchEventToChildren(event);
        return true;
    }

    public void processDrag(float dx, float dy) {
        float f2 = this.left;
        float f3 = f2 + dx;
        float f4 = this.minX;
        float f5 = f3 < f4 ? f4 - f2 : dx;
        float f6 = this.right;
        float f7 = dx + f6;
        float f8 = this.maxX;
        if (f7 > f8) {
            f5 = f8 - f6;
        }
        scaleLeft(f5);
        scaleRight(f5);
        float f9 = this.f6551top;
        float f10 = f9 + dy;
        float f11 = this.minY;
        float f12 = f10 < f11 ? f11 - f9 : dy;
        float f13 = this.bottom;
        float f14 = dy + f13;
        float f15 = this.maxY;
        if (f14 > f15) {
            f12 = f15 - f13;
        }
        scaleTop(f12);
        scaleBottom(f12);
    }

    public void processScale(float left, float right, float top2, float bottom) {
        scaleLeft(left);
        scaleRight(right);
        scaleTop(top2);
        scaleBottom(bottom);
    }

    public void scaleBottom(float size) {
        float f2 = this.bottom + size;
        this.bottom = f2;
        float fMin = Math.min(f2, this.maxY);
        this.bottom = fMin;
        float fMax = Math.max(fMin, this.f6551top + this.minHeight);
        this.bottom = fMax;
        float f3 = this.f6551top;
        float f4 = fMax - f3;
        int i2 = this.maxHeight;
        if (f4 > i2) {
            this.bottom = f3 + i2;
        }
    }

    public void scaleLeft(float size) {
        float f2 = this.left + size;
        this.left = f2;
        float fMax = Math.max(f2, this.minX);
        this.left = fMax;
        float fMin = Math.min(fMax, this.right - this.minWidth);
        this.left = fMin;
        float f3 = this.right;
        float f4 = f3 - fMin;
        int i2 = this.maxWidth;
        if (f4 > i2) {
            this.left = f3 - i2;
        }
    }

    public void scaleRight(float size) {
        float f2 = this.right + size;
        this.right = f2;
        float fMin = Math.min(f2, this.maxX);
        this.right = fMin;
        float fMax = Math.max(fMin, this.left + this.minWidth);
        this.right = fMax;
        float f3 = this.left;
        float f4 = fMax - f3;
        int i2 = this.maxWidth;
        if (f4 > i2) {
            this.right = f3 + i2;
        }
    }

    public void scaleTop(float size) {
        float f2 = this.f6551top + size;
        this.f6551top = f2;
        float fMax = Math.max(f2, this.minY);
        this.f6551top = fMax;
        float fMin = Math.min(fMax, this.bottom - this.minHeight);
        this.f6551top = fMin;
        float f3 = this.bottom;
        float f4 = f3 - fMin;
        int i2 = this.maxHeight;
        if (f4 > i2) {
            this.f6551top = f3 - i2;
        }
    }

    public void setDragRange(float minX, float maxX, float minY, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void setDragScaleMode(int dragFlags, int scaleFlags) {
        this.flagDragMode = dragFlags;
        this.flagScaleMode = scaleFlags;
        checkFlagsConflict();
    }

    public void setEdgeResponseSize(int edgeResponseSize) {
        this.edgeResponseSize = edgeResponseSize;
        this.singleTouchScaleGestureDetector.setEdgeResponseSize(edgeResponseSize);
    }

    public void setMaxSize(@Px int maxWidth, @Px int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public void setMinSize(@Px int minWidth, @Px int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public void updateLayout() {
        float f2 = this.left;
        float f3 = this.f6551top;
        updateLayoutParam((int) f2, (int) f3, (int) (this.right - f2), (int) (this.bottom - f3));
    }

    public void updateLayoutParam(int leftMargin, int topMargin, int width, int height) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.leftMargin = leftMargin;
        marginLayoutParams.topMargin = topMargin;
        marginLayoutParams.width = width;
        marginLayoutParams.height = height;
        setLayoutParams(marginLayoutParams);
    }

    public PLVDragScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVDragScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.edgeResponseSize = ConvertUtils.dp2px(24.0f);
        this.minWidth = ConvertUtils.dp2px(144.0f);
        this.minHeight = ConvertUtils.dp2px(81.0f);
        this.maxWidth = Integer.MAX_VALUE;
        this.maxHeight = Integer.MAX_VALUE;
        initView(attrs);
    }
}
