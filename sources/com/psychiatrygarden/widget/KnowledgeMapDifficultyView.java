package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgeMapDifficultyView extends LinearLayout {
    private CircleProgressView dotView;
    private LinearLayout mLyItem;
    private ProgressBar mProgress;
    private TextView mTvAllCount;
    private TextView mTvDifficulty;
    private TextView mTvDoCount;
    private TextView mTvPercent;

    public KnowledgeMapDifficultyView(Context context, boolean isEnd) {
        super(context);
        initView(isEnd);
    }

    private void initView(boolean isEnd) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_knowledge_map_difficulty, this);
        this.mProgress = (ProgressBar) findViewById(R.id.do_progress);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvDifficulty = (TextView) findViewById(R.id.tv_difficutly);
        this.mTvDoCount = (TextView) findViewById(R.id.tv_do_count);
        this.mTvAllCount = (TextView) findViewById(R.id.tv_all_count);
        this.dotView = (CircleProgressView) findViewById(R.id.dot_view);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_item);
        this.mLyItem = linearLayout;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        if (isEnd) {
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.setMarginEnd(UIUtil.dip2px(getContext(), 12.0d));
        }
        this.mLyItem.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setData(com.psychiatrygarden.bean.KnowledgeMapPointBean.ChildItemData r6) {
        /*
            Method dump skipped, instructions count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.KnowledgeMapDifficultyView.setData(com.psychiatrygarden.bean.KnowledgeMapPointBean$ChildItemData):void");
    }

    public KnowledgeMapDifficultyView(Context context, @Nullable AttributeSet attrs, boolean isEnd) {
        super(context, attrs);
        initView(isEnd);
    }

    public KnowledgeMapDifficultyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean isEnd) {
        super(context, attrs, defStyleAttr);
        initView(isEnd);
    }
}
