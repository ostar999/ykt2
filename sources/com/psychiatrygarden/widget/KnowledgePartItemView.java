package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.activity.mine.knowledge.KnowledgeMapAct;
import com.psychiatrygarden.bean.KnowledgeMapBean;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgePartItemView extends LinearLayout {
    private ImageView mImgArrow;
    private boolean mIsHaveChild;
    private LinearLayout mLyItem;
    private LinearLayout mLyRightPercent;
    private TextView mTvAllNum;
    private TextView mTvName;
    private TextView mTvPercent;
    private TextView mTvRightPercent;
    private TextView mTvStudyNum;

    public KnowledgePartItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_knowledge_part_item, this);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mTvAllNum = (TextView) findViewById(R.id.tv_all_num);
        this.mTvStudyNum = (TextView) findViewById(R.id.tv_study_num);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mLyRightPercent = (LinearLayout) findViewById(R.id.ly_right_percent);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mTvRightPercent = (TextView) findViewById(R.id.tv_right_percent);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 48.0d)) / 6;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTvName.getLayoutParams();
        layoutParams.width = screenWidth * 2;
        this.mTvName.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTvAllNum.getLayoutParams();
        layoutParams2.width = screenWidth;
        this.mTvAllNum.setLayoutParams(layoutParams2);
        this.mTvStudyNum.setLayoutParams(layoutParams2);
        this.mTvPercent.setLayoutParams(layoutParams2);
        this.mLyRightPercent.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(int i2, KnowledgeMapBean.KnowledgeMapItemDataBean knowledgeMapItemDataBean, View view) {
        if (this.mIsHaveChild) {
            KnowledgeMapAct.newIntent(getContext(), i2, knowledgeMapItemDataBean.getId(), knowledgeMapItemDataBean.getPart_id(), knowledgeMapItemDataBean.getChapter_id());
        }
    }

    public void setData(int type, final KnowledgeMapBean.KnowledgeMapItemDataBean data) {
        final int i2 = type + 1;
        this.mIsHaveChild = data.getHas_children().equals("1");
        this.mImgArrow.setVisibility(data.getHas_children().equals("1") ? 0 : 8);
        this.mTvName.setText(data.getName());
        this.mTvAllNum.setText(data.getKnowledge_count());
        this.mTvStudyNum.setText(data.getPractice_count());
        this.mTvPercent.setText(data.getPractice_rate());
        this.mTvRightPercent.setText(data.getUser_correct_rate());
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ia
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16585c.lambda$setData$0(i2, data, view);
            }
        });
    }

    public KnowledgePartItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KnowledgePartItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
