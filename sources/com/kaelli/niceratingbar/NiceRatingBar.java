package com.kaelli.niceratingbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
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

    public NiceRatingBar(Context context) {
        this(context, null);
    }

    private float calculateRating(float f2) {
        float f3;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mStarTotal - 1) {
                f3 = 0.0f;
                break;
            }
            if (f2 >= this.mBoundaryList.get(i2).intValue()) {
                int i3 = i2 + 1;
                if (f2 <= this.mBoundaryList.get(i3).intValue()) {
                    f3 = (this.mStarHalfDrawable == null || f2 >= ((float) ((this.mBoundaryList.get(i2).intValue() + this.mBoundaryList.get(i3).intValue()) / 2))) ? i3 : i2 + 0.5f;
                }
            }
            i2++;
        }
        if (f3 == 0.0f) {
            List<Integer> list = this.mBoundaryList;
            if (f2 >= list.get(list.size() - 1).intValue()) {
                List<Integer> list2 = this.mBoundaryList;
                f3 = f2 < ((float) list2.get(list2.size() + (-1)).intValue()) + (this.mStarWidth / 2.0f) ? this.mStarTotal - 0.5f : this.mStarTotal;
            }
        }
        if (f3 == 0.0f) {
            return this.mStarHalfDrawable == null ? 1.0f : 0.5f;
        }
        return f3;
    }

    private ImageView createStarImageView(boolean z2) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(this.mStarWidth), Math.round(this.mStarHeight));
        layoutParams.setMargins(0, 0, z2 ? 0 : Math.round(this.mStarPadding), 0);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    private void init(AttributeSet attributeSet) {
        setOrientation(0);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NiceRatingBar);
        this.mRatingStatus = RatingStatus.getStatus(typedArrayObtainStyledAttributes.getInt(R.styleable.NiceRatingBar_nrb_ratingStatus, RatingStatus.Disable.mStatus));
        this.mStarFullDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NiceRatingBar_nrb_starFullResource);
        this.mStarEmptyDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NiceRatingBar_nrb_starEmptyResource);
        this.mStarHalfDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NiceRatingBar_nrb_starHalfResource);
        if (this.mStarFullDrawable == null || this.mStarEmptyDrawable == null) {
            throw new IllegalArgumentException("NiceRatingBar Error: You must declare starFullResource and starEmptyResource!");
        }
        this.mStarWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.NiceRatingBar_nrb_starImageWidth, 24.0f);
        this.mStarHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.NiceRatingBar_nrb_starImageHeight, 24.0f);
        this.mStarPadding = typedArrayObtainStyledAttributes.getDimension(R.styleable.NiceRatingBar_nrb_starImagePadding, 4.0f);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.NiceRatingBar_nrb_starTotal, 5);
        this.mStarTotal = i2;
        if (i2 <= 0) {
            throw new IllegalArgumentException("NiceRatingBar Error: starTotal must be positive!");
        }
        this.mRating = typedArrayObtainStyledAttributes.getFloat(R.styleable.NiceRatingBar_nrb_rating, 5.0f);
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
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mBoundaryList.isEmpty()) {
            int i6 = 0;
            while (i6 < this.mStarTotal) {
                List<Integer> list = this.mBoundaryList;
                list.add(Integer.valueOf(i6 == 0 ? getLeft() : list.get(i6 - 1).intValue() + Math.round(this.mStarWidth) + Math.round(this.mStarPadding)));
                i6++;
            }
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mRatingStatus == RatingStatus.Enable && !this.mBoundaryList.isEmpty()) {
            setRating(calculateRating(motionEvent.getRawX()));
            if (motionEvent.getAction() == 0) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnRatingChangedListener(OnRatingChangedListener onRatingChangedListener) {
        this.mOnRatingChangedListener = onRatingChangedListener;
    }

    public void setRating(float f2) {
        int i2 = this.mStarTotal;
        if (f2 > i2) {
            f2 = i2;
        }
        this.mRating = f2;
        OnRatingChangedListener onRatingChangedListener = this.mOnRatingChangedListener;
        if (onRatingChangedListener != null) {
            onRatingChangedListener.onRatingChanged(f2);
        }
        int iFloor = (int) Math.floor(f2);
        float fFloatValue = new BigDecimal(String.valueOf(f2)).subtract(new BigDecimal(String.valueOf(iFloor))).floatValue();
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

    public void setRatingStatus(RatingStatus ratingStatus) {
        this.mRatingStatus = ratingStatus;
    }

    public NiceRatingBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public NiceRatingBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mBoundaryList = new ArrayList(5);
        if (attributeSet != null) {
            init(attributeSet);
            return;
        }
        throw new RuntimeException("NiceRatingBar Error: You must use NiceRatingBar in layout file!");
    }
}
