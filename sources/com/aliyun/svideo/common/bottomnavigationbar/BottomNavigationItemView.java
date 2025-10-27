package com.aliyun.svideo.common.bottomnavigationbar;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
class BottomNavigationItemView extends LinearLayout {
    private static final float SCALE_MAX = 1.1f;
    private boolean isAnim;
    private BottomNavigationEntity mBottomNavigationEntity;
    private TextView mItemBadge;
    private ImageView mItemIcon;
    private TextView mItemText;
    private int mLayoutId;
    private int mTextSelectedColor;
    private int mTextUnSelectedColor;
    private float scaleRatio;
    private ValueAnimator valueAnimator;

    public BottomNavigationItemView(Context context) {
        this(context, null);
    }

    private void init() {
        setOrientation(1);
        setGravity(17);
    }

    @SuppressLint({"SetTextI18n"})
    private void rendingItemBadge() {
        BottomNavigationEntity bottomNavigationEntity;
        if (this.mItemBadge == null || (bottomNavigationEntity = this.mBottomNavigationEntity) == null) {
            return;
        }
        int badgeNum = bottomNavigationEntity.getBadgeNum();
        if (badgeNum <= 0) {
            this.mItemBadge.setVisibility(4);
            return;
        }
        if (badgeNum < 99) {
            this.mItemBadge.setText(String.valueOf(badgeNum));
        } else {
            this.mItemBadge.setText("99+");
        }
        this.mItemBadge.setVisibility(0);
    }

    private void rendingItemIcon(boolean z2) {
        BottomNavigationEntity bottomNavigationEntity;
        ImageView imageView = this.mItemIcon;
        if (imageView == null || (bottomNavigationEntity = this.mBottomNavigationEntity) == null) {
            return;
        }
        if (z2) {
            imageView.setImageResource(bottomNavigationEntity.getSelectedIcon());
        } else {
            imageView.setImageResource(bottomNavigationEntity.getUnSelectIcon());
        }
    }

    private void rendingItemText(boolean z2) {
        BottomNavigationEntity bottomNavigationEntity;
        if (this.mItemText == null || (bottomNavigationEntity = this.mBottomNavigationEntity) == null) {
            return;
        }
        String text = bottomNavigationEntity.getText();
        if (TextUtils.isEmpty(text)) {
            this.mItemText.setVisibility(8);
            return;
        }
        this.mItemText.setText(text);
        this.mItemText.setVisibility(0);
        if (z2) {
            this.mItemText.setTextColor(this.mTextSelectedColor);
        } else {
            this.mItemText.setTextColor(this.mTextUnSelectedColor);
        }
    }

    private void scale(float f2, float f3) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f3);
        this.valueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(200L);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.svideo.common.bottomnavigationbar.BottomNavigationItemView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BottomNavigationItemView.this.setScaleX(fFloatValue);
                BottomNavigationItemView.this.setScaleY(fFloatValue);
            }
        });
        this.valueAnimator.start();
    }

    public float getScaleRatio() {
        return this.scaleRatio;
    }

    public boolean isAnim() {
        return this.isAnim;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ValueAnimator valueAnimator = this.valueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }

    public void refresh() {
        rendingItemBadge();
    }

    public void setAnim(boolean z2) {
        this.isAnim = z2;
    }

    public void setBottomNavigationEntity(BottomNavigationEntity bottomNavigationEntity) {
        this.mBottomNavigationEntity = bottomNavigationEntity;
        setDefaultState();
    }

    public void setDefaultState() {
        rendingItemText(false);
        rendingItemIcon(false);
        rendingItemBadge();
    }

    public void setLayoutId(int i2) {
        this.mLayoutId = i2;
        LayoutInflater.from(getContext()).inflate(this.mLayoutId, (ViewGroup) this, true);
        this.mItemIcon = (ImageView) findViewById(R.id.bnb_item_icon);
        this.mItemText = (TextView) findViewById(R.id.bnb_item_text);
        this.mItemBadge = (TextView) findViewById(R.id.bnb_item_badge);
    }

    public void setScaleRatio(float f2) {
        this.scaleRatio = Math.abs(f2);
    }

    @Override // android.view.View
    public void setSelected(boolean z2) {
        super.setSelected(z2);
        rendingItemText(z2);
        rendingItemIcon(z2);
        if (this.isAnim) {
            float f2 = SCALE_MAX;
            if (z2) {
                float f3 = this.scaleRatio;
                if (f3 > SCALE_MAX) {
                    f2 = f3;
                }
                scale(1.0f, f2);
                return;
            }
            float f4 = this.scaleRatio;
            if (f4 > SCALE_MAX) {
                f2 = f4;
            }
            scale(f2, 1.0f);
        }
    }

    public void setTextSelectedColor(int i2) {
        this.mTextSelectedColor = i2;
    }

    public void setTextUnSelectedColor(int i2) {
        this.mTextUnSelectedColor = i2;
    }

    public BottomNavigationItemView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationItemView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
