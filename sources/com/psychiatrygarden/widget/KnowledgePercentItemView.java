package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.bean.KnowledgeMapPointBean;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgePercentItemView extends LinearLayout {
    private CircleProgressView dotView;
    private LinearLayout mLyNumber;
    private LinearLayout mLyProgress;
    private ProgressBar mProgress;
    private TextView mTvAllNum;
    private TextView mTvDoNum;
    private TextView mTvName;
    private TextView mTvPercent;

    public KnowledgePercentItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_knowledge_hor_progress, this);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mLyNumber = (LinearLayout) findViewById(R.id.ly_number);
        this.mTvDoNum = (TextView) findViewById(R.id.tv_number);
        this.mTvAllNum = (TextView) findViewById(R.id.tv_all_number);
        this.mProgress = (ProgressBar) findViewById(R.id.do_progress);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mLyProgress = (LinearLayout) findViewById(R.id.ly_progress);
        this.dotView = (CircleProgressView) findViewById(R.id.dot_view);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 5;
        int i2 = screenWidth * 2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTvName.getLayoutParams();
        layoutParams.width = (screenWidth * 3) / 2;
        this.mTvName.setLayoutParams(layoutParams);
        this.mLyNumber.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLyProgress.getLayoutParams();
        layoutParams2.width = i2;
        this.mLyProgress.setLayoutParams(layoutParams2);
    }

    public void setData(int pos, KnowledgeMapPointBean.ChildItemData data) {
        if (pos == 0) {
            this.mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.knowledge_hor_progress_bar_a));
            this.dotView.setColor(Color.parseColor("#f95843"));
        } else if (pos == 1) {
            this.mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.knowledge_hor_progress_bar_b));
            this.dotView.setColor(Color.parseColor("#FF8A1D"));
        } else if (pos == 2) {
            this.mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.knowledge_hor_progress_bar_c));
            this.dotView.setColor(Color.parseColor("#FFD814"));
        } else if (pos == 3) {
            this.mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.knowledge_hor_progress_bar_d));
            this.dotView.setColor(Color.parseColor("#99D517"));
        } else if (pos == 4) {
            this.mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.knowledge_hor_progress_bar_e));
            this.dotView.setColor(Color.parseColor("#59C4FF"));
        }
        this.mTvName.setText(data.getTitle());
        this.mTvDoNum.setText(data.getPractice_count());
        this.mTvAllNum.setText("/" + data.getCount());
        int i2 = !TextUtils.isEmpty(data.getCount()) ? Integer.parseInt(data.getCount()) : 100;
        int i3 = !TextUtils.isEmpty(data.getPractice_count()) ? Integer.parseInt(data.getPractice_count()) : 0;
        this.dotView.setVisibility(i3 > 0 ? 8 : 0);
        this.mProgress.setMax(i2);
        this.mProgress.setProgress(i3);
        this.mTvPercent.setText(data.getRate());
    }

    public KnowledgePercentItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KnowledgePercentItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
