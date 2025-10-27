package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.yikaobang.yixue.R;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class NiceRatingBar extends LinearLayout {
    private List<Integer> mBoundaryList;
    private OnRatingChangedListener mOnRatingChangedListener;
    private float mRating;
    private RatingStatus mRatingStatus;
    private Drawable mStarEmptyDrawable;
    private Drawable mStarFullDrawable;
    private Drawable mStarHalfDrawable;
    private float mStarHeight;
    private float mStarPadding;
    private int mStarTotal;
    private float mStarWidth;

    public interface OnRatingChangedListener {
        void onRatingChanged(float rating);
    }

    public enum RatingStatus {
        Disable(0),
        Enable(1);

        final int mStatus;

        RatingStatus(int statusValue) {
            this.mStatus = statusValue;
        }

        public static RatingStatus getStatus(int status) {
            RatingStatus ratingStatus = Disable;
            return status == ratingStatus.mStatus ? ratingStatus : Enable;
        }
    }

    public NiceRatingBar(Context context) {
        this(context, null);
    }

    private float calculateRating(float touchX) {
        float f2;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mStarTotal - 1) {
                f2 = 0.0f;
                break;
            }
            if (touchX >= this.mBoundaryList.get(i2).intValue()) {
                int i3 = i2 + 1;
                if (touchX <= this.mBoundaryList.get(i3).intValue()) {
                    f2 = (this.mStarHalfDrawable == null || touchX >= ((float) (this.mBoundaryList.get(i2).intValue() + this.mBoundaryList.get(i3).intValue())) / 2.0f) ? i3 : i2 + 0.5f;
                }
            }
            i2++;
        }
        if (f2 == 0.0f) {
            List<Integer> list = this.mBoundaryList;
            if (touchX >= list.get(list.size() - 1).intValue()) {
                List<Integer> list2 = this.mBoundaryList;
                f2 = touchX < ((float) list2.get(list2.size() + (-1)).intValue()) + (this.mStarWidth / 2.0f) ? this.mStarTotal - 0.5f : this.mStarTotal;
            }
        }
        if (f2 == 0.0f) {
            return this.mStarHalfDrawable == null ? 1.0f : 0.5f;
        }
        return f2;
    }

    private ImageView createStarImageView(boolean isLast) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(this.mStarWidth), Math.round(this.mStarHeight));
        layoutParams.setMargins(0, 0, isLast ? 0 : Math.round(this.mStarPadding), 0);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    private void init(AttributeSet attrs) {
        setOrientation(0);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.NiceRatingBar);
        this.mRatingStatus = RatingStatus.getStatus(typedArrayObtainStyledAttributes.getInt(1, RatingStatus.Disable.mStatus));
        this.mStarFullDrawable = typedArrayObtainStyledAttributes.getDrawable(3);
        this.mStarEmptyDrawable = typedArrayObtainStyledAttributes.getDrawable(2);
        this.mStarHalfDrawable = typedArrayObtainStyledAttributes.getDrawable(4);
        if (this.mStarFullDrawable == null || this.mStarEmptyDrawable == null) {
            throw new IllegalArgumentException("NiceRatingBar Error: You must declare starFullResource and starEmptyResource!");
        }
        this.mStarWidth = typedArrayObtainStyledAttributes.getDimension(7, 24.0f);
        this.mStarHeight = typedArrayObtainStyledAttributes.getDimension(5, 24.0f);
        this.mStarPadding = typedArrayObtainStyledAttributes.getDimension(6, 4.0f);
        int i2 = typedArrayObtainStyledAttributes.getInt(8, 5);
        this.mStarTotal = i2;
        if (i2 <= 0) {
            this.mStarTotal = 5;
        }
        this.mRating = typedArrayObtainStyledAttributes.getFloat(0, 5.0f);
        typedArrayObtainStyledAttributes.recycle();
        int i3 = 0;
        while (true) {
            int i4 = this.mStarTotal;
            if (i3 >= i4) {
                setRating(this.mRating);
                return;
            } else {
                addView(createStarImageView(i3 == i4 + (-1)));
                i3++;
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.mBoundaryList.isEmpty()) {
            int i2 = 0;
            while (i2 < this.mStarTotal) {
                List<Integer> list = this.mBoundaryList;
                list.add(Integer.valueOf(i2 == 0 ? 0 : list.get(i2 - 1).intValue() + Math.round(this.mStarWidth) + Math.round(this.mStarPadding)));
                i2++;
            }
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mRatingStatus == RatingStatus.Enable && !this.mBoundaryList.isEmpty()) {
            setRating(calculateRating(event.getX()));
            if (event.getAction() == 0) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnRatingChangedListener(OnRatingChangedListener listener) {
        this.mOnRatingChangedListener = listener;
    }

    public void setRating(float rating) {
        int i2 = this.mStarTotal;
        if (rating > i2) {
            rating = i2;
        }
        this.mRating = rating;
        OnRatingChangedListener onRatingChangedListener = this.mOnRatingChangedListener;
        if (onRatingChangedListener != null) {
            onRatingChangedListener.onRatingChanged(rating);
        }
        int iFloor = (int) Math.floor(rating);
        float fFloatValue = new BigDecimal(String.valueOf(rating)).subtract(new BigDecimal(String.valueOf(iFloor))).floatValue();
        for (int i3 = 0; i3 < iFloor; i3++) {
            ((ImageView) getChildAt(i3)).setImageDrawable(this.mStarFullDrawable);
        }
        for (int i4 = iFloor; i4 < this.mStarTotal; i4++) {
            ((ImageView) getChildAt(i4)).setImageDrawable(this.mStarEmptyDrawable);
        }
        double d3 = fFloatValue;
        if (d3 >= 0.25d) {
            if (d3 < 0.75d && this.mStarHalfDrawable != null) {
                ((ImageView) getChildAt(iFloor)).setImageDrawable(this.mStarHalfDrawable);
            } else if (d3 >= 0.75d) {
                ((ImageView) getChildAt(iFloor)).setImageDrawable(this.mStarFullDrawable);
            }
        }
    }

    public void setRatingStatus(RatingStatus status) {
        this.mRatingStatus = status;
    }

    public NiceRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NiceRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBoundaryList = new ArrayList(5);
        if (attrs != null) {
            init(attrs);
            return;
        }
        throw new RuntimeException("NiceRatingBar Error: You must use NiceRatingBar in layout file!");
    }
}
