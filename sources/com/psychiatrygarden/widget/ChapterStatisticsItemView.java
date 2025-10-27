package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.bean.ChapterStatisticsBean;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChapterStatisticsItemView extends LinearLayout {
    private ImageView mImgArrow;
    private LinearLayout mLyAddView;
    private LinearLayout mLyItem;
    private TextView mTvAllNum;
    private TextView mTvDoNum;
    private TextView mTvName;
    private TextView mTvPercent;

    public ChapterStatisticsItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_chapter_statistics_item, this);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mTvAllNum = (TextView) findViewById(R.id.tv_all_num);
        this.mTvDoNum = (TextView) findViewById(R.id.tv_do_num);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_name);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 5;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.width = screenWidth * 2;
        linearLayout.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTvAllNum.getLayoutParams();
        layoutParams2.width = screenWidth;
        this.mTvAllNum.setLayoutParams(layoutParams2);
        layoutParams2.width = screenWidth;
        this.mTvDoNum.setLayoutParams(layoutParams2);
        layoutParams2.width = screenWidth;
        this.mTvPercent.setLayoutParams(layoutParams2);
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.b1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16330c.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.mLyAddView.getVisibility() == 8) {
            this.mLyAddView.setVisibility(0);
            showOrHiddenArrow(true, this.mImgArrow);
        } else {
            this.mLyAddView.setVisibility(8);
            showOrHiddenArrow(false, this.mImgArrow);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$1(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$2(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    private void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(90, 270);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.z0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ChapterStatisticsItemView.lambda$showOrHiddenArrow$1(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(270, 90);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.a1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ChapterStatisticsItemView.lambda$showOrHiddenArrow$2(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    public void setData(int pos, boolean isForm, ChapterStatisticsBean.ChapterStatisticsData data) {
        if (isForm) {
            this.mImgArrow.setVisibility(8);
        } else if (data.getChildren() == null || data.getChildren().isEmpty()) {
            this.mImgArrow.setVisibility(8);
            this.mLyAddView.setVisibility(8);
        } else {
            this.mLyAddView.setVisibility(0);
            this.mImgArrow.setVisibility(0);
            for (int i2 = 0; i2 < data.getChildren().size(); i2++) {
                ChapterStatisticsChildItemView chapterStatisticsChildItemView = new ChapterStatisticsChildItemView(getContext());
                chapterStatisticsChildItemView.setData(data.getChildren().get(i2));
                this.mLyAddView.addView(chapterStatisticsChildItemView);
            }
        }
        this.mLyAddView.setVisibility(8);
        this.mTvName.setText(data.getTitle());
        this.mTvAllNum.setText(data.getQuestion_count());
        this.mTvDoNum.setText(data.getBrush_question_count());
        this.mTvPercent.setText(data.getRight_rate());
    }

    public ChapterStatisticsItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChapterStatisticsItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
