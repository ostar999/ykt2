package com.psychiatrygarden.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.ScoreTrendBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class EstimatedScoreChangeItemChildView extends LinearLayout {
    private TextView mTvName;
    private TextView mTvScore;

    public EstimatedScoreChangeItemChildView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_change_item_child, this);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
    }

    public void setData(ScoreTrendBean.TrendItemChildBean data) {
        this.mTvName.setText(data.getName());
        this.mTvScore.setText(data.getValue());
    }

    public EstimatedScoreChangeItemChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EstimatedScoreChangeItemChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
