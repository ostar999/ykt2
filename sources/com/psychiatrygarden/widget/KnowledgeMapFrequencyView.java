package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgeMapFrequencyView extends LinearLayout {
    private LinearLayout mLyItem;
    private TextView mTvAllCount;
    private TextView mTvDoCount;
    private TextView mTvName;
    private TextView mTvPercent;
    private KnowledgeCircleProgressView progressCircle;

    public KnowledgeMapFrequencyView(Context context, boolean isEnd) {
        super(context);
        initView(isEnd);
    }

    private void initView(boolean isEnd) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_knowledge_map_frequency, this);
        this.progressCircle = (KnowledgeCircleProgressView) findViewById(R.id.progress_circle);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mTvDoCount = (TextView) findViewById(R.id.tv_do_count);
        this.mTvAllCount = (TextView) findViewById(R.id.tv_all_count);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 68.0d)) / 4;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyItem.getLayoutParams();
        if (isEnd) {
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.setMarginEnd(UIUtil.dip2px(getContext(), 12.0d));
        }
        this.mLyItem.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setData(com.psychiatrygarden.bean.KnowledgeMapPointBean.ChildItemData r7) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.KnowledgeMapFrequencyView.setData(com.psychiatrygarden.bean.KnowledgeMapPointBean$ChildItemData):void");
    }

    public KnowledgeMapFrequencyView(Context context, @Nullable AttributeSet attrs, boolean isEnd) {
        super(context, attrs);
        initView(isEnd);
    }

    public KnowledgeMapFrequencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean isEnd) {
        super(context, attrs, defStyleAttr);
        initView(isEnd);
    }
}
