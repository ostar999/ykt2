package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.activity.mine.report.WeekOrMonthReportAct;
import com.psychiatrygarden.bean.EstimatedScoreChangeItemChildView;
import com.psychiatrygarden.bean.ScoreTrendBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class EstimatedScoreChangeItemView extends LinearLayout {
    private boolean isExpand;
    private ImageView mImgExpand;
    private LinearLayout mLyAddChildExpandView;
    private LinearLayout mLyAddChildView;
    private LinearLayout mLyExpand;
    private LinearLayout mLyToDetail;
    private TextView mTvExpand;
    private TextView mTvScore;
    private TextView mTvTime;
    private TextView mTvWeekName;

    public EstimatedScoreChangeItemView(Context context) {
        super(context);
        this.isExpand = false;
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_estimated_score_change_item, this);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvTime = (TextView) findViewById(R.id.tv_time);
        this.mLyToDetail = (LinearLayout) findViewById(R.id.ly_to_detail);
        this.mLyAddChildView = (LinearLayout) findViewById(R.id.ly_add_child_view);
        this.mLyAddChildExpandView = (LinearLayout) findViewById(R.id.ly_add_child_view_expand);
        this.mLyExpand = (LinearLayout) findViewById(R.id.ly_expand);
        this.mImgExpand = (ImageView) findViewById(R.id.img_expand);
        this.mTvExpand = (TextView) findViewById(R.id.tv_expand);
        this.mTvWeekName = (TextView) findViewById(R.id.btn_week_name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(View view) {
        boolean z2 = !this.isExpand;
        this.isExpand = z2;
        this.mTvExpand.setText(z2 ? "收起" : "展开");
        if (this.isExpand) {
            this.mImgExpand.setRotation(180.0f);
            this.mLyAddChildExpandView.setVisibility(0);
        } else {
            this.mImgExpand.setRotation(0.0f);
            this.mLyAddChildExpandView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$1(ScoreTrendBean.TrendItemBean trendItemBean, String str, boolean z2, View view) {
        ScoreTrendBean.TrendItemBean trendItemBean2 = new ScoreTrendBean.TrendItemBean();
        trendItemBean2.setEstimated_score(trendItemBean.getEstimated_score());
        trendItemBean2.setStart_time(trendItemBean.getStart_time());
        trendItemBean2.setEnd_time(trendItemBean.getEnd_time());
        trendItemBean2.setId(trendItemBean.getId());
        trendItemBean2.setTrend(trendItemBean.getTrend());
        trendItemBean2.setAllScore(str);
        trendItemBean2.setEstimated_score_trend(trendItemBean.getEstimated_score_trend());
        getContext().startActivity(WeekOrMonthReportAct.newIntent(getContext(), z2, trendItemBean2));
    }

    public void setData(final ScoreTrendBean.TrendItemBean data, final boolean isWeek, final String allScore) {
        this.mTvWeekName.setText(isWeek ? "查看周报" : "查看月报");
        this.mTvScore.setText(data.getEstimated_score());
        this.mTvTime.setText("统计周期：" + CommonUtil.getDataStr(data.getStart_time()) + "至" + CommonUtil.getDataStr(data.getEnd_time()));
        if (data.getTrend_data() == null || data.getTrend_data().size() <= 0) {
            this.mLyExpand.setVisibility(8);
        } else {
            int size = data.getTrend_data().size();
            if (data.getTrend_data().size() > 2) {
                this.mLyExpand.setVisibility(0);
                this.mLyAddChildExpandView.removeAllViews();
                for (int i2 = 2; i2 < data.getTrend_data().size(); i2++) {
                    EstimatedScoreChangeItemChildView estimatedScoreChangeItemChildView = new EstimatedScoreChangeItemChildView(getContext());
                    estimatedScoreChangeItemChildView.setData(data.getTrend_data().get(i2));
                    this.mLyAddChildExpandView.addView(estimatedScoreChangeItemChildView);
                }
                this.mLyExpand.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c9
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f16365c.lambda$setData$0(view);
                    }
                });
                size = 2;
            } else {
                this.mLyExpand.setVisibility(8);
            }
            this.mLyExpand.setVisibility(0);
            this.mLyAddChildView.removeAllViews();
            for (int i3 = 0; i3 < size; i3++) {
                EstimatedScoreChangeItemChildView estimatedScoreChangeItemChildView2 = new EstimatedScoreChangeItemChildView(getContext());
                estimatedScoreChangeItemChildView2.setData(data.getTrend_data().get(i3));
                this.mLyAddChildView.addView(estimatedScoreChangeItemChildView2);
            }
        }
        this.mLyToDetail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16401c.lambda$setData$1(data, allScore, isWeek, view);
            }
        });
    }

    public EstimatedScoreChangeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.isExpand = false;
        initView();
    }

    public EstimatedScoreChangeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isExpand = false;
        initView();
    }
}
