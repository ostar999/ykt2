package com.umeng.socialize.shareboard;

import android.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.umeng.socialize.shareboard.widgets.SocializeViewPager;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.util.List;

/* loaded from: classes6.dex */
class UMActionFrame extends LinearLayout {
    private ShareBoardConfig mConfig;
    private PopupWindow.OnDismissListener mDismissListener;

    public UMActionFrame(Context context) {
        super(context);
    }

    private View createShareTitle() {
        TextView textView = new TextView(getContext());
        textView.setText(this.mConfig.mTitleText);
        textView.setTextColor(this.mConfig.mTitleTextColor);
        textView.setTextSize(16.0f);
        textView.setGravity(17);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = dip2px(20.0f);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private View createShareboardLayout(List<SnsPlatform> list) throws Resources.NotFoundException {
        final IndicatorView indicatorViewCreateIndicatorView;
        int i2;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackgroundColor(this.mConfig.mShareboardBgColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        ShareBoardConfig shareBoardConfig = this.mConfig;
        if (shareBoardConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER && (i2 = shareBoardConfig.mTopMargin) != 0) {
            layoutParams.topMargin = i2;
        }
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        if (this.mConfig.mTitleVisibility) {
            linearLayout.addView(createShareTitle());
        }
        int iCalculateMenuHeightInDp = this.mConfig.calculateMenuHeightInDp(list.size());
        ViewPager viewPagerCreateViewPagerInstance = createViewPagerInstance();
        if (viewPagerCreateViewPagerInstance != null) {
            SocializeMenuPagerAdapter socializeMenuPagerAdapter = new SocializeMenuPagerAdapter(getContext(), this.mConfig);
            socializeMenuPagerAdapter.setData(list);
            settingMenuLayout(viewPagerCreateViewPagerInstance, iCalculateMenuHeightInDp);
            linearLayout.addView(viewPagerCreateViewPagerInstance);
            viewPagerCreateViewPagerInstance.setAdapter(socializeMenuPagerAdapter);
            indicatorViewCreateIndicatorView = this.mConfig.mIndicatorVisibility ? createIndicatorView() : null;
            if (indicatorViewCreateIndicatorView != null) {
                indicatorViewCreateIndicatorView.setPageCount(socializeMenuPagerAdapter.getCount());
                linearLayout.addView(indicatorViewCreateIndicatorView);
            }
            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.umeng.socialize.shareboard.UMActionFrame.2
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i3) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i3, float f2, int i4) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i3) {
                    IndicatorView indicatorView = indicatorViewCreateIndicatorView;
                    if (indicatorView != null) {
                        indicatorView.setSelectedPosition(i3);
                    }
                }
            };
            if (verifyMethodExists()) {
                viewPagerCreateViewPagerInstance.addOnPageChangeListener(onPageChangeListener);
            } else {
                viewPagerCreateViewPagerInstance.setOnPageChangeListener(onPageChangeListener);
            }
        } else {
            View viewCreateSocializeViewPagerInstance = createSocializeViewPagerInstance();
            if (viewCreateSocializeViewPagerInstance == null) {
                return null;
            }
            SocializeMenuAdapter socializeMenuAdapter = new SocializeMenuAdapter(getContext(), this.mConfig);
            socializeMenuAdapter.setData(list);
            settingMenuLayout(viewCreateSocializeViewPagerInstance, iCalculateMenuHeightInDp);
            linearLayout.addView(viewCreateSocializeViewPagerInstance);
            viewCreateSocializeViewPagerInstance.setAdapter(socializeMenuAdapter);
            indicatorViewCreateIndicatorView = this.mConfig.mIndicatorVisibility ? createIndicatorView() : null;
            if (indicatorViewCreateIndicatorView != null) {
                indicatorViewCreateIndicatorView.setPageCount(socializeMenuAdapter.getCount());
                linearLayout.addView(indicatorViewCreateIndicatorView);
            }
            viewCreateSocializeViewPagerInstance.addOnPageChangeListener(new SocializeViewPager.OnPageChangeListener() { // from class: com.umeng.socialize.shareboard.UMActionFrame.3
                public void onPageScrollStateChanged(int i3) {
                }

                public void onPageScrolled(int i3, float f2, int i4) {
                }

                public void onPageSelected(int i3) {
                    IndicatorView indicatorView = indicatorViewCreateIndicatorView;
                    if (indicatorView != null) {
                        indicatorView.setSelectedPosition(i3);
                    }
                }
            });
        }
        if (this.mConfig.mCancelBtnVisibility) {
            linearLayout.addView(createCancelBtn());
        }
        return linearLayout;
    }

    private SocializeViewPager createSocializeViewPagerInstance() {
        try {
            return (SocializeViewPager) Class.forName("com.umeng.socialize.shareboard.widgets.SocializeViewPager").getConstructor(Context.class).newInstance(getContext());
        } catch (Exception e2) {
            SLog.error(UmengText.SHAREBOARD.NULLJAR, e2);
            return null;
        }
    }

    private ViewPager createViewPagerInstance() {
        try {
            return (ViewPager) Class.forName("androidx.viewpager.widget.ViewPager").getConstructor(Context.class).newInstance(getContext());
        } catch (Exception e2) {
            SLog.error(e2);
            return null;
        }
    }

    private int dip2px(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private StateListDrawable getBtnBg() {
        ColorDrawable colorDrawable = new ColorDrawable(this.mConfig.mCancelBtnBgColor);
        ColorDrawable colorDrawable2 = new ColorDrawable(this.mConfig.mCancelBtnBgPressedColor);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, colorDrawable2);
        stateListDrawable.addState(new int[0], colorDrawable);
        return stateListDrawable;
    }

    private void init(List<SnsPlatform> list) throws Resources.NotFoundException {
        setBackgroundColor(Color.argb(50, 0, 0, 0));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(100L);
        setAnimation(alphaAnimation);
        setOrientation(1);
        int i2 = this.mConfig.mShareboardPosition;
        if (i2 == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM) {
            setGravity(80);
        } else if (i2 == ShareBoardConfig.SHAREBOARD_POSITION_CENTER) {
            setGravity(17);
            int iDip2px = dip2px(36.0f);
            setPadding(iDip2px, 0, iDip2px, 0);
        }
        setOnClickListener(new View.OnClickListener() { // from class: com.umeng.socialize.shareboard.UMActionFrame.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (UMActionFrame.this.mDismissListener != null) {
                    UMActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        View viewCreateShareboardLayout = createShareboardLayout(list);
        if (viewCreateShareboardLayout == null) {
            return;
        }
        viewCreateShareboardLayout.setClickable(true);
        addView(viewCreateShareboardLayout);
    }

    private void settingMenuLayout(View view, int i2) {
        int iDip2px = dip2px(20.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dip2px(i2));
        layoutParams.topMargin = iDip2px;
        int iDip2px2 = dip2px(10.0f);
        layoutParams.rightMargin = iDip2px2;
        layoutParams.leftMargin = iDip2px2;
        view.setLayoutParams(layoutParams);
        view.setPadding(0, 0, 0, iDip2px);
    }

    private boolean verifyMethodExists() {
        try {
        } catch (Exception e2) {
            SLog.error(e2);
        }
        return Class.forName("androidx.viewpager.widget.ViewPager").getMethod("addOnPageChangeListener", ViewPager.OnPageChangeListener.class) != null;
    }

    public View createCancelBtn() {
        TextView textView = new TextView(getContext());
        textView.setText(this.mConfig.mCancelBtnText);
        textView.setTextColor(this.mConfig.mCancelBtnColor);
        textView.setClickable(true);
        textView.setTextSize(15.0f);
        textView.setGravity(17);
        ShareBoardConfig shareBoardConfig = this.mConfig;
        if (shareBoardConfig.mCancelBtnBgPressedColor != 0) {
            textView.setBackground(getBtnBg());
        } else {
            textView.setBackgroundColor(shareBoardConfig.mCancelBtnBgColor);
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.socialize.shareboard.UMActionFrame.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (UMActionFrame.this.mDismissListener != null) {
                    UMActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(50.0f)));
        return textView;
    }

    public IndicatorView createIndicatorView() {
        int iDip2px = dip2px(20.0f);
        IndicatorView indicatorView = new IndicatorView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = iDip2px;
        indicatorView.setLayoutParams(layoutParams);
        ShareBoardConfig shareBoardConfig = this.mConfig;
        indicatorView.setIndicatorColor(shareBoardConfig.mIndicatorNormalColor, shareBoardConfig.mIndicatorSelectedColor);
        indicatorView.setIndicator(3, 5);
        return indicatorView;
    }

    public void setDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    public void setSnsPlatformData(List<SnsPlatform> list) throws Resources.NotFoundException {
        setSnsPlatformData(list, new ShareBoardConfig());
    }

    public UMActionFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @TargetApi(11)
    public UMActionFrame(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public void setSnsPlatformData(List<SnsPlatform> list, ShareBoardConfig shareBoardConfig) throws Resources.NotFoundException {
        if (shareBoardConfig == null) {
            this.mConfig = new ShareBoardConfig();
        } else {
            this.mConfig = shareBoardConfig;
        }
        init(list);
    }

    @TargetApi(21)
    public UMActionFrame(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }
}
