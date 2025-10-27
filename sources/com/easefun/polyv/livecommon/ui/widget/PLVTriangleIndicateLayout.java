package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PLVTriangleIndicateLayout extends FrameLayout {
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
    public static final int ORIENTATION_BOTTOM_LEFT_TO_TOP_RIGHT = 1;
    public static final int ORIENTATION_BOTTOM_RIGHT_TO_TOP_LEFT = 3;
    public static final int ORIENTATION_BOTTOM_TO_TOP = 2;
    public static final int ORIENTATION_LEFT_TO_RIGHT = 0;
    public static final int ORIENTATION_RIGHT_TO_LEFT = 4;
    public static final int ORIENTATION_TOP_LEFT_TO_BOTTOM_RIGHT = 7;
    public static final int ORIENTATION_TOP_RIGHT_TO_BOTTOM_LEFT = 5;
    public static final int ORIENTATION_TOP_TO_BOTTOM = 6;
    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_LEFT = 2;
    public static final int POSITION_RIGHT = 3;
    public static final int POSITION_TOP = 0;
    private static final String TAG = "PLVTriangleIndicateLayout";
    private String gradientColors;
    private int indicateColor;
    private LinearGradient linearGradient;
    private AttributeSet mAttrs;
    private int orientation;
    private final SparseArray<GradientDrawable.Orientation> orientationMapper;
    private Paint paint;
    private Path path;
    private float radius;
    private ShapeDrawable shapeDrawable;
    private float triangleHeight;
    private float triangleMargin;
    private int triangleMarginType;
    private int trianglePosition;
    private float triangleWidth;

    public PLVTriangleIndicateLayout(Context context) {
        super(context);
        this.mAttrs = null;
        this.orientationMapper = new SparseArray<GradientDrawable.Orientation>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateLayout.1
            {
                put(0, GradientDrawable.Orientation.LEFT_RIGHT);
                put(1, GradientDrawable.Orientation.BL_TR);
                put(2, GradientDrawable.Orientation.BOTTOM_TOP);
                put(3, GradientDrawable.Orientation.BR_TL);
                put(4, GradientDrawable.Orientation.RIGHT_LEFT);
                put(5, GradientDrawable.Orientation.TR_BL);
                put(6, GradientDrawable.Orientation.TOP_BOTTOM);
                put(7, GradientDrawable.Orientation.TL_BR);
            }
        };
        this.orientation = 0;
        this.gradientColors = null;
        this.linearGradient = null;
        init();
    }

    private void init() {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.mAttrs, R.styleable.PLVTriangleIndicateLayout);
        this.triangleWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateLayout_triangleWidth, DpOrPxUtils.dip2px(getContext(), 12.0f));
        this.triangleHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateLayout_triangleHeight, DpOrPxUtils.dip2px(getContext(), 8.0f));
        this.triangleMargin = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateLayout_triangleMargin, DpOrPxUtils.dip2px(getContext(), 0.0f));
        this.trianglePosition = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVTriangleIndicateLayout_trianglePosition, 0);
        this.triangleMarginType = typedArrayObtainStyledAttributes.getInteger(R.styleable.PLVTriangleIndicateLayout_triangleMarginType, 0);
        this.indicateColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVTriangleIndicateLayout_indicateColor, -1);
        this.radius = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVTriangleIndicateLayout_rectRadius, 0.0f);
        int i2 = R.styleable.PLVTriangleIndicateLayout_plvGradientColors;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            this.orientation = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVTriangleIndicateLayout_plvGradientOrientation, this.orientation);
            this.gradientColors = typedArrayObtainStyledAttributes.getString(i2);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initBackground() {
        /*
            Method dump skipped, instructions count: 499
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateLayout.initBackground():void");
    }

    private void parseGradientColor(int width, int height) {
        int i2;
        if (this.gradientColors == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        for (String str : this.gradientColors.replaceAll(" ", "").split(",")) {
            try {
                arrayList.add(Integer.valueOf(Color.parseColor(str)));
            } catch (Exception e2) {
                PLVCommonLog.e(TAG, e2.getMessage());
            }
        }
        if (arrayList.size() < 1) {
            arrayList.add(Integer.valueOf(this.indicateColor));
        }
        if (arrayList.size() < 2) {
            arrayList.add((Integer) arrayList.get(0));
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i4 = 0; i4 < size; i4++) {
            iArr[i4] = ((Integer) arrayList.get(i4)).intValue();
        }
        switch (this.orientation) {
            case 1:
                i2 = 0;
                i3 = width;
                width = 0;
                break;
            case 2:
                width /= 2;
                i2 = 0;
                i3 = width;
                break;
            case 3:
                i2 = 0;
                break;
            case 4:
                height /= 2;
                i2 = height;
                break;
            case 5:
                i2 = height;
                height = 0;
                break;
            case 6:
                width /= 2;
                i2 = height;
                height = 0;
                i3 = width;
                break;
            case 7:
                i2 = height;
                height = 0;
                i3 = width;
                width = 0;
                break;
            default:
                height /= 2;
                i2 = height;
                i3 = width;
                width = 0;
                break;
        }
        this.linearGradient = new LinearGradient(width, height, i3, i2, iArr, (float[]) null, Shader.TileMode.MIRROR);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a8  */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void layoutChildren(int r17, int r18, int r19, int r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 189
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateLayout.layoutChildren(int, int, int, int, boolean):void");
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        layoutChildren(left, top2, right, bottom, false);
        initBackground();
        setBackground(this.shapeDrawable);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i2 = this.trianglePosition;
        if (i2 == 0 || i2 == 1) {
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(heightMeasureSpec) - this.triangleHeight), View.MeasureSpec.getMode(heightMeasureSpec)));
            setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredHeight() + this.triangleHeight));
        } else {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(widthMeasureSpec) - this.triangleHeight), View.MeasureSpec.getMode(widthMeasureSpec)), heightMeasureSpec);
            setMeasuredDimension((int) (getMeasuredWidth() + this.triangleHeight), getMeasuredHeight());
        }
        if (this.gradientColors != null) {
            parseGradientColor(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public PLVTriangleIndicateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAttrs = null;
        this.orientationMapper = new SparseArray<GradientDrawable.Orientation>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateLayout.1
            {
                put(0, GradientDrawable.Orientation.LEFT_RIGHT);
                put(1, GradientDrawable.Orientation.BL_TR);
                put(2, GradientDrawable.Orientation.BOTTOM_TOP);
                put(3, GradientDrawable.Orientation.BR_TL);
                put(4, GradientDrawable.Orientation.RIGHT_LEFT);
                put(5, GradientDrawable.Orientation.TR_BL);
                put(6, GradientDrawable.Orientation.TOP_BOTTOM);
                put(7, GradientDrawable.Orientation.TL_BR);
            }
        };
        this.orientation = 0;
        this.gradientColors = null;
        this.linearGradient = null;
        this.mAttrs = attrs;
        init();
    }

    public PLVTriangleIndicateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAttrs = null;
        this.orientationMapper = new SparseArray<GradientDrawable.Orientation>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateLayout.1
            {
                put(0, GradientDrawable.Orientation.LEFT_RIGHT);
                put(1, GradientDrawable.Orientation.BL_TR);
                put(2, GradientDrawable.Orientation.BOTTOM_TOP);
                put(3, GradientDrawable.Orientation.BR_TL);
                put(4, GradientDrawable.Orientation.RIGHT_LEFT);
                put(5, GradientDrawable.Orientation.TR_BL);
                put(6, GradientDrawable.Orientation.TOP_BOTTOM);
                put(7, GradientDrawable.Orientation.TL_BR);
            }
        };
        this.orientation = 0;
        this.gradientColors = null;
        this.linearGradient = null;
        this.mAttrs = attrs;
        init();
    }
}
