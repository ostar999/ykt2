package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.yikaobang.yixue.R;
import java.math.BigDecimal;

/* loaded from: classes6.dex */
public class RatingBar extends LinearLayout {
    private boolean mClickable;
    private OnRatingChangeListener onRatingChangeListener;
    private int starCount;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;
    private float starImageSize;

    public interface OnRatingChangeListener {
        void onRatingChange(int RatingCount);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        this.starImageSize = typedArrayObtainStyledAttributes.getDimension(5, 20.0f);
        this.starCount = typedArrayObtainStyledAttributes.getInteger(1, 5);
        this.starEmptyDrawable = typedArrayObtainStyledAttributes.getDrawable(2);
        this.starFillDrawable = typedArrayObtainStyledAttributes.getDrawable(3);
        this.mClickable = typedArrayObtainStyledAttributes.getBoolean(0, true);
        for (int i2 = 0; i2 < this.starCount; i2++) {
            ImageView starImageView = getStarImageView(context, attrs);
            starImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.RatingBar.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (RatingBar.this.mClickable) {
                        RatingBar.this.setStar(r0.indexOfChild(v2) + 1);
                        if (RatingBar.this.onRatingChangeListener != null) {
                            RatingBar.this.onRatingChangeListener.onRatingChange(RatingBar.this.indexOfChild(v2) + 1);
                        }
                    }
                }
            });
            addView(starImageView);
        }
    }

    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Math.round(this.starImageSize), Math.round(this.starImageSize)));
        imageView.setPadding(0, 0, 5, 0);
        imageView.setImageDrawable(this.starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setStar(float starCount) {
        int i2 = (int) starCount;
        float fFloatValue = new BigDecimal(Float.toString(starCount)).subtract(new BigDecimal(Integer.toString(i2))).floatValue();
        int i3 = this.starCount;
        float f2 = i2 > i3 ? i3 : i2;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        for (int i4 = 0; i4 < f2; i4++) {
            ((ImageView) getChildAt(i4)).setImageDrawable(this.starFillDrawable);
        }
        if (fFloatValue > 0.0f) {
            ((ImageView) getChildAt(i2)).setImageDrawable(this.starHalfDrawable);
            int i5 = this.starCount;
            while (true) {
                i5--;
                if (i5 < 1.0f + f2) {
                    return;
                } else {
                    ((ImageView) getChildAt(i5)).setImageDrawable(this.starEmptyDrawable);
                }
            }
        } else {
            int i6 = this.starCount;
            while (true) {
                i6--;
                if (i6 < f2) {
                    return;
                } else {
                    ((ImageView) getChildAt(i6)).setImageDrawable(this.starEmptyDrawable);
                }
            }
        }
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setmClickable(boolean clickable) {
        this.mClickable = clickable;
    }
}
