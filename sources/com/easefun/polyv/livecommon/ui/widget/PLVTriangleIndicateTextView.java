package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;

/* loaded from: classes3.dex */
public class PLVTriangleIndicateTextView extends AppCompatTextView {
    private static final int DEFAULT_TRIANGLE_COLOR = -1;
    private static final int DEFAULT_TRIANGLE_HEIGHT = 8;
    private static final int DEFAULT_TRIANGLE_MARGIN = 0;
    private static final int DEFAULT_TRIANGLE_MARGIN_TYPE = 0;
    private static final int DEFAULT_TRIANGLE_POSITION = 0;
    private static final int DEFAULT_TRIANGLE_WIDTH = 12;
    public static final int MARGIN_TYPE_BOTTOM = 3;
    public static final int MARGIN_TYPE_LEFT = 0;
    public static final int MARGIN_TYPE_RIGHT = 1;
    public static final int MARGIN_TYPE_TOP = 2;
    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_LEFT = 2;
    public static final int POSITION_RIGHT = 3;
    public static final int POSITION_TOP = 0;
    private int indicateColor;
    private int indicateEndColor;
    private AttributeSet mAttrs;
    private Paint paint;
    private Path path;
    private float radius;
    private boolean triangleCenter;
    private float triangleHeight;
    private float triangleMargin;
    private int triangleMarginType;
    private int trianglePosition;
    private float triangleWidth;

    public PLVTriangleIndicateTextView(Context context) {
        super(context);
        this.mAttrs = null;
        this.indicateEndColor = -1;
        init();
    }

    private void init() {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.mAttrs, R.styleable.PLVTriangleIndicateTextView);
        this.triangleWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateTextView_triangleWidth, DpOrPxUtils.dip2px(getContext(), 12.0f));
        this.triangleHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateTextView_triangleHeight, DpOrPxUtils.dip2px(getContext(), 8.0f));
        this.triangleMargin = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateTextView_triangleMargin, DpOrPxUtils.dip2px(getContext(), 0.0f));
        this.trianglePosition = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVTriangleIndicateTextView_trianglePosition, 0);
        this.triangleMarginType = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVTriangleIndicateTextView_triangleMarginType, 0);
        this.indicateColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVTriangleIndicateTextView_indicateColor, -1);
        this.indicateEndColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVTriangleIndicateTextView_indicateEndColor, -1);
        this.triangleCenter = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVTriangleIndicateTextView_triangleCenter, false);
        this.radius = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateTextView_rectRadius, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initBackground() {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView.initBackground():void");
    }

    public int getTrianglePosition() {
        return this.trianglePosition;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.paint == null || this.path == null) {
            initBackground();
        }
        canvas.drawPath(this.path, this.paint);
        int i2 = this.trianglePosition;
        if (i2 == 0) {
            canvas.translate(0.0f, this.triangleHeight);
        } else if (i2 == 2) {
            canvas.translate(this.triangleHeight, 0.0f);
        }
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i2 = this.trianglePosition;
        if (i2 != 0 && i2 != 1) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(widthMeasureSpec) - this.triangleHeight), View.MeasureSpec.getMode(widthMeasureSpec)), heightMeasureSpec);
            setMeasuredDimension((int) (getMeasuredWidth() + this.triangleHeight), getMeasuredHeight());
        } else {
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(heightMeasureSpec) - this.triangleHeight), View.MeasureSpec.getMode(heightMeasureSpec)));
            setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredHeight() + this.triangleHeight));
        }
    }

    public void setColor(int indicateColor, int indicateEndColor) {
        this.indicateColor = indicateColor;
        this.indicateEndColor = indicateEndColor;
        this.paint = null;
        this.path = null;
        invalidate();
    }

    public PLVTriangleIndicateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.indicateEndColor = -1;
        this.mAttrs = attrs;
        init();
    }

    public PLVTriangleIndicateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.indicateEndColor = -1;
        this.mAttrs = attrs;
        init();
    }
}
