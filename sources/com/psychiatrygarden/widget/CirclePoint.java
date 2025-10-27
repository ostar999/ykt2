package com.psychiatrygarden.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CirclePoint extends LinearLayout {
    private boolean appSix;
    private AttributeSet attrs;
    private LinearLayout center;
    private RelativeLayout centerLayout;
    private Context context;
    private int count;
    private int mPointMargin;
    private LinearLayout pictureLayout;
    private int point_selected_color;
    private int point_unselected_color;
    private GradientDrawable selected_point;
    private float size;
    private GradientDrawable unselected_point;
    private ImageView whitePoint;

    public CirclePoint(Context context) {
        super(context);
        this.appSix = false;
        this.count = 0;
    }

    private void handlePicture() {
        handlePictureOn();
    }

    private void handlePictureBefore() {
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.selected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_selected);
            if (this.appSix) {
                this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_gray_normal);
                return;
            } else {
                this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_normal);
                return;
            }
        }
        this.selected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_selected_night);
        if (this.appSix) {
            this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_normal_night);
        } else {
            this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_normal_night);
        }
    }

    @TargetApi(21)
    private void handlePictureOn() {
        if (SkinManager.getCurrentSkinType(this.context) != 0) {
            this.selected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_selected_night, null);
            this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_normal_night, null);
            return;
        }
        this.selected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_selected, null);
        if (this.appSix) {
            this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_gray_normal, null);
        } else {
            this.unselected_point = (GradientDrawable) getResources().getDrawable(R.drawable.shape_point_normal, null);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        setColor();
        this.centerLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        this.centerLayout.setGravity(1);
        this.pictureLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -2));
        this.pictureLayout.setOrientation(0);
        initImage(this.count, context);
        this.centerLayout.addView(this.pictureLayout);
        this.centerLayout.addView(this.whitePoint);
        this.center = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        layoutParams.gravity = 1;
        this.center.setLayoutParams(layoutParams);
        this.center.addView(this.centerLayout);
        addView(this.center);
        setPoint();
    }

    private void setColor() {
        int i2 = this.point_selected_color;
        if (i2 != 0) {
            this.selected_point.setColor(i2);
        }
        int i3 = this.point_unselected_color;
        if (i3 != 0) {
            this.unselected_point.setColor(i3);
        }
    }

    private void setPoint() {
        if (this.pictureLayout.getChildCount() > 1) {
            this.whitePoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.CirclePoint.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    CirclePoint circlePoint = CirclePoint.this;
                    circlePoint.mPointMargin = circlePoint.pictureLayout.getChildAt(1).getLeft() - CirclePoint.this.pictureLayout.getChildAt(0).getLeft();
                    CirclePoint.this.whitePoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    public int getCount() {
        return this.count;
    }

    public void initImage(int count, Context context) {
        this.whitePoint.setImageDrawable(this.selected_point);
        int i2 = (int) this.size;
        this.whitePoint.setLayoutParams(new RelativeLayout.LayoutParams(i2, i2));
        for (int i3 = 0; i3 < count; i3++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(this.unselected_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
            if (i3 > 0) {
                layoutParams.leftMargin = (int) (this.size * 1.5d);
            }
            imageView.setLayoutParams(layoutParams);
            this.pictureLayout.addView(imageView);
        }
    }

    public void initLayout(Context context) {
        this.centerLayout = new RelativeLayout(context);
        this.pictureLayout = new LinearLayout(context);
        this.whitePoint = new ImageView(context);
    }

    public void initViewData() {
        handlePicture();
        initLayout(this.context);
        init(this.context, this.attrs);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setonPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int i2 = (int) (this.mPointMargin * (position + positionOffset));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.whitePoint.getLayoutParams();
        layoutParams.leftMargin = i2;
        this.whitePoint.setLayoutParams(layoutParams);
    }

    public CirclePoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.appSix = false;
        this.count = 0;
        setOrientation(1);
        this.context = context;
        this.attrs = attrs;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CirclePoint);
        this.size = typedArrayObtainStyledAttributes.getDimension(4, 15.0f);
        this.appSix = typedArrayObtainStyledAttributes.getBoolean(0, false);
        this.point_selected_color = typedArrayObtainStyledAttributes.getColor(2, 0);
        this.point_unselected_color = typedArrayObtainStyledAttributes.getColor(3, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public CirclePoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.appSix = false;
        this.count = 0;
    }
}
