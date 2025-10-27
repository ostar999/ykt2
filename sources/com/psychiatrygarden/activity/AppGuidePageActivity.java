package com.psychiatrygarden.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.widget.ViewPagerAdapter;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AppGuidePageActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final int[] guideLayoutResIds = {R.layout.layout_guide_img_1_new, R.layout.layout_guide_img_2_new, R.layout.layout_guide_img_3_new, R.layout.layout_guide_img_4_new, R.layout.layout_guide_img_5_new};
    private int currentIndex;
    private List<ImageView> dots;
    private TextView tvJump;
    private ViewPager vp;

    private void initDots() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
        this.dots = new ArrayList(guideLayoutResIds.length);
        for (int i2 = 0; i2 < guideLayoutResIds.length; i2++) {
            ImageView imageView = (ImageView) View.inflate(this, R.layout.layout_app_guide_page_dot, null);
            imageView.setEnabled(false);
            imageView.setOnClickListener(this);
            imageView.setTag(Integer.valueOf(i2));
            this.dots.add(imageView);
            linearLayout.addView(imageView);
        }
        this.currentIndex = 0;
        this.dots.get(0).setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.tvJump.clearAnimation();
        SurveyAct.startAct(this);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPageSelected$1(ValueAnimator valueAnimator) {
        this.tvJump.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) AppGuidePageActivity.class);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > guideLayoutResIds.length - 1 || this.currentIndex == positon) {
            return;
        }
        int i2 = 0;
        while (i2 < this.dots.size()) {
            this.dots.get(i2).setEnabled(i2 == positon);
            i2++;
        }
        this.currentIndex = positon;
    }

    private void setCurView(int position) throws Resources.NotFoundException {
        if (position < 0 || position >= guideLayoutResIds.length) {
            return;
        }
        this.vp.setCurrentItem(position);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws Resources.NotFoundException {
        int iIntValue = ((Integer) v2.getTag()).intValue();
        setCurView(iIntValue);
        setCurDot(iIntValue);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        setContentView(R.layout.layout_guide_page);
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.clearFlags(134217728);
        window.getDecorView().setSystemUiVisibility(R2.drawable.ee_19);
        window.setStatusBarColor(0);
        this.mActionBar.hide();
        this.tvJump = (TextView) findViewById(R.id.tv_jump);
        ArrayList arrayList = new ArrayList();
        for (int i2 : guideLayoutResIds) {
            arrayList.add(View.inflate(this, i2, null));
        }
        initDots();
        this.vp = (ViewPager) findViewById(R.id.viewpager);
        this.vp.setAdapter(new ViewPagerAdapter(arrayList));
        this.vp.addOnPageChangeListener(this);
        this.tvJump.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11093c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int arg0) {
        setCurDot(arg0);
        if (arg0 == guideLayoutResIds.length - 1) {
            this.tvJump.animate().alpha(1.0f).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.a1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f10980c.lambda$onPageSelected$1(valueAnimator);
                }
            }).setInterpolator(new LinearInterpolator()).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.activity.AppGuidePageActivity.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    AppGuidePageActivity.this.tvJump.setEnabled(true);
                    AppGuidePageActivity.this.tvJump.setClickable(true);
                }
            }).start();
            return;
        }
        this.tvJump.setAlpha(0.0f);
        this.tvJump.setEnabled(false);
        this.tvJump.setClickable(false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
