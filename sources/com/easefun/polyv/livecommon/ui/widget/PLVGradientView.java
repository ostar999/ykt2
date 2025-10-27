package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVGradientView extends View {
    public static final int ORIENTATION_BOTTOM_LEFT_TO_TOP_RIGHT = 1;
    public static final int ORIENTATION_BOTTOM_RIGHT_TO_TOP_LEFT = 3;
    public static final int ORIENTATION_BOTTOM_TO_TOP = 2;
    public static final int ORIENTATION_LEFT_TO_RIGHT = 0;
    public static final int ORIENTATION_RIGHT_TO_LEFT = 4;
    public static final int ORIENTATION_TOP_LEFT_TO_BOTTOM_RIGHT = 7;
    public static final int ORIENTATION_TOP_RIGHT_TO_BOTTOM_LEFT = 5;
    public static final int ORIENTATION_TOP_TO_BOTTOM = 6;
    private static final String TAG = "PLVGradientView";
    private int orientation;
    private final SparseArray<GradientDrawable.Orientation> orientationMapper;

    public PLVGradientView(Context context) {
        this(context, null);
    }

    private void initView(AttributeSet attrs) {
        List<Integer> arrayList = new ArrayList<>();
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVGradientView);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.PLVGradientView_plvGradientColors)) {
                parseColors(arrayList, typedArrayObtainStyledAttributes);
            } else {
                parseSimpleColor(arrayList, typedArrayObtainStyledAttributes);
            }
            this.orientation = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVGradientView_plvGradientOrientation, this.orientation);
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
        int i2 = R.styleable.PLVGradientView_plvGradientColors;
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

    private void parseSimpleColor(List<Integer> colorList, TypedArray typedArray) {
        int i2 = R.styleable.PLVGradientView_plvGradientStartColor;
        if (typedArray.hasValue(i2)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i2, 0)));
        }
        int i3 = R.styleable.PLVGradientView_plvGradientMiddleColor;
        if (typedArray.hasValue(i3)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i3, 0)));
        }
        int i4 = R.styleable.PLVGradientView_plvGradientEndColor;
        if (typedArray.hasValue(i4)) {
            colorList.add(Integer.valueOf(typedArray.getColor(i4, 0)));
        }
    }

    public PLVGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.orientationMapper = new SparseArray<GradientDrawable.Orientation>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVGradientView.1
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
        initView(attrs);
    }
}
