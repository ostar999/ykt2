package com.psychiatrygarden.utils.viewfilter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.psychiatrygarden.utils.viewfilter.XMarqueeViewAdapter;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class XMarqueeView extends ViewFlipper implements XMarqueeViewAdapter.OnDataChangedListener {
    private int animDuration;
    private int interval;
    private boolean isFlippingLessCount;
    private boolean isSetAnimDuration;
    private boolean isSingleLine;
    private int itemCount;
    private XMarqueeViewAdapter mMarqueeViewAdapter;
    private int textColor;
    private int textSize;

    public XMarqueeView(Context context, AttributeSet attrs) throws Resources.NotFoundException {
        super(context, attrs);
        this.isSetAnimDuration = false;
        this.isSingleLine = true;
        this.interval = 3000;
        this.animDuration = 1000;
        this.textSize = 14;
        this.textColor = Color.parseColor("#888888");
        this.itemCount = 1;
        this.isFlippingLessCount = true;
        init(context, attrs, 0);
    }

    private int getRealPosition(int index, int currentIndex) {
        if ((index == 0 && currentIndex == 0) || currentIndex == this.mMarqueeViewAdapter.getItemCount() - 1) {
            return 0;
        }
        return currentIndex + 1;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.XMarqueeView, defStyleAttr, 0);
        if (typedArrayObtainStyledAttributes != null) {
            this.isSetAnimDuration = typedArrayObtainStyledAttributes.getBoolean(1, false);
            this.isSingleLine = typedArrayObtainStyledAttributes.getBoolean(2, true);
            this.isFlippingLessCount = typedArrayObtainStyledAttributes.getBoolean(0, true);
            this.interval = typedArrayObtainStyledAttributes.getInteger(5, this.interval);
            this.animDuration = typedArrayObtainStyledAttributes.getInteger(3, this.animDuration);
            if (typedArrayObtainStyledAttributes.hasValue(7)) {
                int dimension = (int) typedArrayObtainStyledAttributes.getDimension(7, this.textSize);
                this.textSize = dimension;
                this.textSize = Utils.px2sp(context, dimension);
            }
            this.textColor = typedArrayObtainStyledAttributes.getColor(6, this.textColor);
            this.itemCount = typedArrayObtainStyledAttributes.getInt(4, this.itemCount);
            typedArrayObtainStyledAttributes.recycle();
        }
        this.isSingleLine = this.itemCount == 1;
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);
        if (this.isSetAnimDuration) {
            animationLoadAnimation.setDuration(this.animDuration);
            animationLoadAnimation2.setDuration(this.animDuration);
        }
        setInAnimation(animationLoadAnimation);
        setOutAnimation(animationLoadAnimation2);
        setFlipInterval(this.interval);
        setMeasureAllChildren(false);
    }

    private void setData() {
        removeAllViews();
        int itemCount = this.mMarqueeViewAdapter.getItemCount() % this.itemCount == 0 ? this.mMarqueeViewAdapter.getItemCount() / this.itemCount : (this.mMarqueeViewAdapter.getItemCount() / this.itemCount) + 1;
        int realPosition = 0;
        for (int i2 = 0; i2 < itemCount; i2++) {
            if (this.isSingleLine) {
                View viewOnCreateView = this.mMarqueeViewAdapter.onCreateView(this);
                if (realPosition < this.mMarqueeViewAdapter.getItemCount()) {
                    this.mMarqueeViewAdapter.onBindView(viewOnCreateView, viewOnCreateView, realPosition);
                }
                realPosition++;
                addView(viewOnCreateView);
            } else {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                linearLayout.setGravity(17);
                linearLayout.removeAllViews();
                for (int i3 = 0; i3 < this.itemCount; i3++) {
                    View viewOnCreateView2 = this.mMarqueeViewAdapter.onCreateView(this);
                    linearLayout.addView(viewOnCreateView2);
                    realPosition = getRealPosition(i3, realPosition);
                    if (realPosition < this.mMarqueeViewAdapter.getItemCount()) {
                        this.mMarqueeViewAdapter.onBindView(linearLayout, viewOnCreateView2, realPosition);
                    }
                }
                addView(linearLayout);
            }
        }
        if (this.isFlippingLessCount || this.itemCount >= this.mMarqueeViewAdapter.getItemCount()) {
            startFlipping();
        }
    }

    @Override // com.psychiatrygarden.utils.viewfilter.XMarqueeViewAdapter.OnDataChangedListener
    public void onChanged() {
        setData();
    }

    public void setAdapter(XMarqueeViewAdapter adapter) {
        if (adapter == null) {
            throw new RuntimeException("adapter must not be null");
        }
        if (this.mMarqueeViewAdapter != null) {
            throw new RuntimeException("you have already set an Adapter");
        }
        this.mMarqueeViewAdapter = adapter;
        adapter.setOnDataChangedListener(this);
        setData();
    }

    public void setFlippingLessCount(boolean flippingLessCount) {
        this.isFlippingLessCount = flippingLessCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setSingleLine(boolean singleLine) {
        this.isSingleLine = singleLine;
    }
}
