package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVRoundRectGradientTextView extends AppCompatTextView {
    public static final int ORIENTATION_BOTTOM_LEFT_TO_TOP_RIGHT = 1;
    public static final int ORIENTATION_BOTTOM_RIGHT_TO_TOP_LEFT = 3;
    public static final int ORIENTATION_BOTTOM_TO_TOP = 2;
    public static final int ORIENTATION_LEFT_TO_RIGHT = 0;
    public static final int ORIENTATION_RIGHT_TO_LEFT = 4;
    public static final int ORIENTATION_TOP_LEFT_TO_BOTTOM_RIGHT = 7;
    public static final int ORIENTATION_TOP_RIGHT_TO_BOTTOM_LEFT = 5;
    public static final int ORIENTATION_TOP_TO_BOTTOM = 6;
    private static final String TAG = "PLVRoundRectGradientTextView";
    private int height;
    private int orientation;
    private final SparseArray<GradientDrawable.Orientation> orientationMapper;
    private float radiusBottomLeft;
    private float radiusBottomRight;
    private Path radiusPath;
    private float radiusTopLeft;
    private float radiusTopRight;
    private int width;

    public PLVRoundRectGradientTextView(Context context) {
        this(context, null);
    }

    private void initView(AttributeSet attrs) {
        List<Integer> arrayList = new ArrayList<>();
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVRoundRectGradientTextView);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.PLVRoundRectGradientTextView_plvGradientColors)) {
                parseColors(arrayList, typedArrayObtainStyledAttributes);
            } else {
                parseSimpleColor(arrayList, typedArrayObtainStyledAttributes);
            }
            this.orientation = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVRoundRectGradientTextView_plvGradientOrientation, this.orientation);
            parseRadius(typedArrayObtainStyledAttributes);
            typedArrayObtainStyledAttributes.recycle();
        }
        if (arrayList.isEmpty()) {
            arrayList.add(0);
        }
        if (arrayList.size() == 1) {
            arrayList.add(arrayList.get(0));
        }
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = arrayList.get(i2).intValue();
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(this.orientationMapper.get(this.orientation));
        gradientDrawable.setColors(iArr);
        setBackground(gradientDrawable);
    }

    private void parseColors(List<Integer> colorList, TypedArray typedArray) {
        String string;
        int i2 = R.styleable.PLVRoundRectGradientTextView_plvGradientColors;
        if (typedArray.hasValue(i2) && (string = typedArray.getString(i2)) != null) {
            for (String str : string.replaceAll(" ", "").split(",")) {
                try {
                    colorList.add(Integer.valueOf(Color.parseColor(str)));
                } catch (Exception e2) {
                    PLVCommonLog.e(TAG, e2.getMessage());
                }
            }
        }
    }

    private void parseRadius(TypedArray typedArray) {
        float dimension = typedArray.getDimension(R.styleable.PLVRoundRectGradientTextView_plvRadius, 0.0f);
        this.radiusBottomRight = dimension;
        this.radiusBottomLeft = dimension;
        this.radiusTopRight = dimension;
        this.radiusTopLeft = dimension;
        this.radiusTopLeft = typedArray.getDimension(R.styleable.PLVRoundRectGradientTextView_plvTopLeftRadius, dimension);
        this.radiusTopRight = typedArray.getDimension(R.styleable.PLVRoundRectGradientTextView_plvTopRightRadius, this.radiusTopRight);
        this.radiusBottomLeft = typedArray.getDimension(R.styleable.PLVRoundRectGradientTextView_plvBottomLeftRadius, this.radiusBottomLeft);
        this.radiusBottomRight = typedArray.getDimension(R.styleable.PLVRoundRectGradientTextView_plvBottomRightRadius, this.radiusBottomRight);
    }

    private void parseSimpleColor(List<Integer> colorList, TypedArray typedArray) {
        int i2 = R.styleable.PLVRoundRectGradientTextView_plvGradientStartColor;
        if (typedArray.hasValue(i2)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i2, 0)));
        }
        int i3 = R.styleable.PLVRoundRectGradientTextView_plvGradientMiddleColor;
        if (typedArray.hasValue(i3)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i3, 0)));
        }
        int i4 = R.styleable.PLVRoundRectGradientTextView_plvGradientEndColor;
        if (typedArray.hasValue(i4)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i4, 0)));
        }
    }

    private void updateRadiusPathIfNeeded() {
        if (getWidth() == this.width && getHeight() == this.height) {
            return;
        }
        this.width = getWidth();
        this.height = getHeight();
        if (this.radiusTopLeft == 0.0f && this.radiusTopRight == 0.0f && this.radiusBottomLeft == 0.0f && this.radiusBottomRight == 0.0f) {
            this.radiusPath = null;
            return;
        }
        Path path = this.radiusPath;
        if (path == null) {
            this.radiusPath = new Path();
        } else {
            path.reset();
        }
        Path path2 = this.radiusPath;
        RectF rectF = new RectF(0.0f, 0.0f, this.width, this.height);
        float f2 = this.radiusTopLeft;
        float f3 = this.radiusTopRight;
        float f4 = this.radiusBottomRight;
        float f5 = this.radiusBottomLeft;
        path2.addRoundRect(rectF, new float[]{f2, f2, f3, f3, f4, f4, f5, f5}, Path.Direction.CCW);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        updateRadiusPathIfNeeded();
        if (this.radiusPath == null) {
            super.draw(canvas);
            return;
        }
        int iSave = canvas.save();
        canvas.clipPath(this.radiusPath);
        super.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    public void updateBackgroundColor(@ColorInt int... colors) {
        if (colors == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(colors.length);
        for (int i2 : colors) {
            arrayList.add(Integer.valueOf(i2));
        }
        if (arrayList.size() == 0) {
            arrayList.add(0);
        }
        if (arrayList.size() == 1) {
            arrayList.add((Integer) arrayList.get(0));
        }
        int[] iArr = new int[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(this.orientationMapper.get(this.orientation));
        gradientDrawable.setColors(iArr);
        setBackground(gradientDrawable);
    }

    public void updateRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        this.radiusTopLeft = topLeft;
        this.radiusTopRight = topRight;
        this.radiusBottomRight = bottomRight;
        this.radiusBottomLeft = bottomLeft;
        Path path = this.radiusPath;
        if (path == null) {
            this.radiusPath = new Path();
        } else {
            path.reset();
        }
        Path path2 = this.radiusPath;
        RectF rectF = new RectF(0.0f, 0.0f, this.width, this.height);
        float f2 = this.radiusTopLeft;
        float f3 = this.radiusTopRight;
        float f4 = this.radiusBottomRight;
        float f5 = this.radiusBottomLeft;
        path2.addRoundRect(rectF, new float[]{f2, f2, f3, f3, f4, f4, f5, f5}, Path.Direction.CCW);
    }

    public PLVRoundRectGradientTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVRoundRectGradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.orientationMapper = new SparseArray<GradientDrawable.Orientation>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVRoundRectGradientTextView.1
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
        this.radiusTopLeft = 0.0f;
        this.radiusTopRight = 0.0f;
        this.radiusBottomLeft = 0.0f;
        this.radiusBottomRight = 0.0f;
        this.width = 0;
        this.height = 0;
        this.radiusPath = null;
        initView(attrs);
    }
}
