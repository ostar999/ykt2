package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.widget.LabelsView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ExperienceFilterView extends LinearLayout {
    private boolean isExpandSchool;
    private LabelsView mLbView;
    private LinearLayout mLyShowMore;
    private int mSchoolCollapseHeight;
    private int mSchoolExpandHeight;
    private TextView mTvTitle;

    public interface FilterItemChoosed {
        void mItemChoosedLinsenter(int childPos, ForumFilterBean.FilterDataBean item, String itemValue);
    }

    public ExperienceFilterView(Context context) {
        this(context, null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_experience_filter, this);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLbView = (LabelsView) findViewById(R.id.lb_item);
        this.mLyShowMore = (LinearLayout) findViewById(R.id.ly_show_more);
        final TextView textView = (TextView) findViewById(R.id.tv_more_one);
        final ImageView imageView = (ImageView) findViewById(R.id.img_arrow_one);
        this.mLbView.setScreenWidth(UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d));
        this.mLbView.setLabelWidth((UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 44.0d)) / 2);
        this.mLyShowMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16643c.lambda$init$2(imageView, textView, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLbView.getLayoutParams();
        layoutParams.height = iIntValue;
        this.mLbView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(final ImageView imageView, final TextView textView, View view) {
        this.isExpandSchool = !this.isExpandSchool;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(this.isExpandSchool ? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300L);
        int[] iArr = new int[2];
        boolean z2 = this.isExpandSchool;
        iArr[0] = z2 ? this.mSchoolCollapseHeight : this.mSchoolExpandHeight;
        iArr[1] = !z2 ? this.mSchoolCollapseHeight : this.mSchoolExpandHeight;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iArr);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.i9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f16584c.lambda$init$0(valueAnimator);
            }
        });
        int[] iArr2 = new int[2];
        boolean z3 = this.isExpandSchool;
        iArr2[0] = z3 ? 0 : 180;
        iArr2[1] = z3 ? 180 : 0;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(iArr2);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.j9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ExperienceFilterView.lambda$init$1(imageView, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfInt2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.widget.ExperienceFilterView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                textView.setText(ExperienceFilterView.this.isExpandSchool ? "收起" : "展开");
            }
        });
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$setData$3(List list, ForumFilterBean.FilterDataBean filterDataBean, TextView textView, int i2, ForumFilterBean.FilterDataBean filterDataBean2) {
        if (list.contains(filterDataBean2.getmTempKey())) {
            this.mLbView.setSelects(i2);
        }
        return filterDataBean.getList().get(i2).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setData$4(ForumFilterBean.FilterDataBean filterDataBean, FilterItemChoosed filterItemChoosed, TextView textView, Object obj, boolean z2, int i2) {
        filterDataBean.getList().get(i2).setSelected(z2);
        if (z2) {
            filterItemChoosed.mItemChoosedLinsenter(i2, filterDataBean, filterDataBean.getList().get(i2).getKey());
        } else {
            filterItemChoosed.mItemChoosedLinsenter(i2, filterDataBean, "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$5(int i2) {
        this.mSchoolCollapseHeight = this.mLbView.getMeasuredHeight();
        this.mLbView.setMaxLines(i2);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLbView.getLayoutParams();
        layoutParams.height = this.mSchoolCollapseHeight;
        this.mLbView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$6() {
        this.mSchoolExpandHeight = this.mLbView.getMeasuredHeight();
        final int lines = this.mLbView.getLines();
        this.mLbView.setMaxLines(3);
        this.mLbView.post(new Runnable() { // from class: com.psychiatrygarden.widget.h9
            @Override // java.lang.Runnable
            public final void run() {
                this.f16545c.lambda$setData$5(lines);
            }
        });
    }

    public List<ForumFilterBean.FilterDataBean> getSelectedItem() {
        ArrayList arrayList = new ArrayList();
        LabelsView labelsView = this.mLbView;
        return labelsView != null ? labelsView.getSelectLabelDatas() : arrayList;
    }

    public void resetChoose() {
        LabelsView labelsView = this.mLbView;
        if (labelsView != null) {
            labelsView.clearAllSelect();
        }
    }

    public void setData(final ForumFilterBean.FilterDataBean item, final List<String> choodedKey, final FilterItemChoosed itemChoosedLis) {
        TextView textView = this.mTvTitle;
        if (textView != null) {
            textView.setText(item.getTitle());
        }
        for (ForumFilterBean.FilterDataBean filterDataBean : item.getList()) {
            filterDataBean.setType(item.getType());
            filterDataBean.setmTempKey(item.getType() + StrPool.UNDERLINE + filterDataBean.getKey());
        }
        this.mLyShowMore.setVisibility(item.getList().size() > 6 ? 0 : 8);
        LabelsView labelsView = this.mLbView;
        if (labelsView != null) {
            labelsView.setLabels(item.getList(), new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.widget.l9
                @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
                public final CharSequence getLabelText(TextView textView2, int i2, Object obj) {
                    return this.f16675a.lambda$setData$3(choodedKey, item, textView2, i2, (ForumFilterBean.FilterDataBean) obj);
                }
            });
            this.mLbView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() { // from class: com.psychiatrygarden.widget.m9
                @Override // com.psychiatrygarden.widget.LabelsView.OnLabelSelectChangeListener
                public final void onLabelSelectChange(TextView textView2, Object obj, boolean z2, int i2) {
                    ExperienceFilterView.lambda$setData$4(item, itemChoosedLis, textView2, obj, z2, i2);
                }
            });
            this.mLbView.post(new Runnable() { // from class: com.psychiatrygarden.widget.n9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16731c.lambda$setData$6();
                }
            });
            this.mLbView.getSelectLabelDatas();
        }
    }

    public ExperienceFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExperienceFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
