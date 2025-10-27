package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.bean.ChapterStatisticsBean;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChapterStatisticsChildItemView extends LinearLayout {
    private ImageView mImgArrow;
    private LinearLayout mLyAddView;
    private TextView mTvAllNum;
    private TextView mTvDoNum;
    private TextView mTvName;
    private TextView mTvPercent;

    public ChapterStatisticsChildItemView(Context context) {
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
        ImageView imageView = (ImageView) findViewById(R.id.img_arrow);
        this.mImgArrow = imageView;
        imageView.setVisibility(4);
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
    }

    public void setData(ChapterStatisticsBean.ChapterStatisticsData data) {
        this.mImgArrow.setVisibility(4);
        this.mTvName.setText(data.getTitle());
        this.mTvAllNum.setText(data.getQuestion_count());
        this.mTvDoNum.setText(data.getBrush_question_count());
        this.mTvPercent.setText(data.getRight_rate());
    }

    public ChapterStatisticsChildItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChapterStatisticsChildItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
