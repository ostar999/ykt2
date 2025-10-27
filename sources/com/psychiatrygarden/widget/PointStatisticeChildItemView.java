package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.KnowledgePointBean;
import com.psychiatrygarden.bean.MockLoulineStatisticsBean;
import com.psychiatrygarden.bean.MockStatisticsTreeBean;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PointStatisticeChildItemView extends LinearLayout {
    private boolean isExpand;
    private ImageView mImgArrow;
    private ImageView mImgEmpty;
    private ImageView mImgExpand;
    private LinearLayout mLyAddView;
    private LinearLayout mLyItem;
    private TextView mTvCategory;
    private TextView mTvFrequency;
    private TextView mTvName;
    private TextView mTvNum;
    private TextView mTvPercent;
    private TextView mTvStar;
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void jumpToQuestionDetail(String knowledgeId);
    }

    public PointStatisticeChildItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_point_child_item, this);
        this.mTvName = (TextView) findViewById(R.id.tv_title);
        this.mTvCategory = (TextView) findViewById(R.id.tv_category);
        this.mTvStar = (TextView) findViewById(R.id.tv_star);
        this.mTvNum = (TextView) findViewById(R.id.tv_num);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvFrequency = (TextView) findViewById(R.id.tv_frequency);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mImgExpand = (ImageView) findViewById(R.id.img_expand);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        this.mImgEmpty = (ImageView) findViewById(R.id.img_empty);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(KnowledgePointBean.KnowledgePointData knowledgePointData, View view) {
        if (knowledgePointData.getChild() == null || knowledgePointData.getChild().size() <= 0) {
            ToastUtil.shortToast(getContext(), "该考点下无数据");
            return;
        }
        boolean z2 = !this.isExpand;
        this.isExpand = z2;
        this.mImgExpand.setImageResource(z2 ? R.mipmap.arrow_question_expand : R.mipmap.arrow_question_collapse);
        this.mLyAddView.setVisibility(this.isExpand ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$1(MockStatisticsTreeBean.MockStatisticsData mockStatisticsData, View view) {
        if (mockStatisticsData.getChildren() == null || mockStatisticsData.getChildren().size() <= 0) {
            OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
            if (onItemActionLisenter != null) {
                onItemActionLisenter.jumpToQuestionDetail(mockStatisticsData.getId());
                return;
            }
            return;
        }
        boolean z2 = !this.isExpand;
        this.isExpand = z2;
        this.mImgExpand.setImageResource(z2 ? R.mipmap.arrow_question_expand : R.mipmap.arrow_question_collapse);
        this.mLyAddView.setVisibility(this.isExpand ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$2(MockLoulineStatisticsBean.MockOutlineData mockOutlineData, View view) {
        if (mockOutlineData.getChildren() == null || mockOutlineData.getChildren().size() <= 0) {
            OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
            if (onItemActionLisenter != null) {
                onItemActionLisenter.jumpToQuestionDetail(mockOutlineData.getChapter_id());
                return;
            }
            return;
        }
        boolean z2 = !this.isExpand;
        this.isExpand = z2;
        this.mImgExpand.setImageResource(z2 ? R.mipmap.arrow_question_expand : R.mipmap.arrow_question_collapse);
        this.mLyAddView.setVisibility(this.isExpand ? 0 : 8);
    }

    public void setData(final KnowledgePointBean.KnowledgePointData data, boolean isHadChild) {
        this.mTvName.setText(data.getName());
        if (TextUtils.isEmpty(data.getCategory_str())) {
            this.mTvCategory.setVisibility(8);
        } else {
            this.mTvCategory.setText(data.getCategory_str());
            this.mTvCategory.setVisibility(0);
        }
        if (TextUtils.isEmpty(data.getFrequency_str())) {
            this.mTvFrequency.setVisibility(8);
        } else {
            this.mTvFrequency.setText(data.getFrequency_str());
            this.mTvFrequency.setVisibility(0);
        }
        if (TextUtils.isEmpty(data.getStar_str())) {
            this.mTvStar.setVisibility(8);
        } else {
            this.mTvStar.setText(data.getStar_str());
            this.mTvStar.setVisibility(0);
        }
        this.mTvNum.setText(data.getPractice_num() + "/" + data.getQuestion_num());
        this.mTvPercent.setText("正确率  " + data.getRight_rate() + "%");
        if (isHadChild) {
            this.mImgEmpty.setVisibility(4);
            if (data.getChild() == null || data.getChild().size() <= 0) {
                this.mImgExpand.setVisibility(4);
            } else {
                this.mImgExpand.setVisibility(0);
                this.mLyAddView.removeAllViews();
                for (int i2 = 0; i2 < data.getChild().size(); i2++) {
                    PointStatisticeChildItemView pointStatisticeChildItemView = new PointStatisticeChildItemView(getContext());
                    pointStatisticeChildItemView.setData(data.getChild().get(i2), isHadChild);
                    this.mLyAddView.addView(pointStatisticeChildItemView);
                }
            }
        } else {
            this.mImgEmpty.setVisibility(8);
            this.mImgExpand.setVisibility(8);
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.vb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16994c.lambda$setData$0(data, view);
            }
        });
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    public PointStatisticeChildItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PointStatisticeChildItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setData(final MockStatisticsTreeBean.MockStatisticsData data, boolean isHadChild) {
        this.mTvName.setText(data.getName());
        if (!TextUtils.isEmpty(data.getCount())) {
            this.mTvCategory.setText("共" + data.getCount() + "题");
            this.mTvCategory.setVisibility(0);
        } else {
            this.mTvCategory.setVisibility(8);
        }
        if (!TextUtils.isEmpty(data.getRight_count())) {
            this.mTvFrequency.setText("对" + data.getRight_count() + "题");
            this.mTvFrequency.setVisibility(0);
        } else {
            this.mTvFrequency.setVisibility(8);
        }
        if (!TextUtils.isEmpty(data.getWrong_count())) {
            this.mTvStar.setText("错" + data.getWrong_count() + "题");
            this.mTvStar.setVisibility(0);
        } else {
            this.mTvStar.setVisibility(8);
        }
        this.mTvNum.setVisibility(8);
        this.mTvPercent.setText("正确率  " + data.getAccuracy());
        if (isHadChild) {
            this.mImgEmpty.setVisibility(4);
            if (data.getChildren() != null && data.getChildren().size() > 0) {
                this.mImgExpand.setVisibility(0);
                this.mImgArrow.setVisibility(8);
                this.mLyAddView.removeAllViews();
                for (int i2 = 0; i2 < data.getChildren().size(); i2++) {
                    PointStatisticeChildItemView pointStatisticeChildItemView = new PointStatisticeChildItemView(getContext());
                    pointStatisticeChildItemView.setOnItemActionLisenter(this.onItemActionLisenter);
                    pointStatisticeChildItemView.setData(data.getChildren().get(i2), isHadChild);
                    this.mLyAddView.addView(pointStatisticeChildItemView);
                }
            } else {
                this.mImgExpand.setVisibility(4);
                this.mImgArrow.setVisibility(0);
            }
        } else {
            this.mImgEmpty.setVisibility(8);
            this.mImgExpand.setVisibility(8);
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ub
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16957c.lambda$setData$1(data, view);
            }
        });
    }

    public void setData(final MockLoulineStatisticsBean.MockOutlineData data) {
        this.mTvName.setText(data.getTitle());
        if (!TextUtils.isEmpty(data.getCount())) {
            this.mTvCategory.setText("共" + data.getCount() + "题");
            this.mTvCategory.setVisibility(0);
        } else {
            this.mTvCategory.setVisibility(8);
        }
        if (!TextUtils.isEmpty(data.getRight_count())) {
            this.mTvFrequency.setText("对" + data.getRight_count() + "题");
            this.mTvFrequency.setVisibility(0);
        } else {
            this.mTvFrequency.setVisibility(8);
        }
        if (!TextUtils.isEmpty(data.getWrong_count())) {
            this.mTvStar.setText("错" + data.getWrong_count() + "题");
            this.mTvStar.setVisibility(0);
        } else {
            this.mTvStar.setVisibility(8);
        }
        this.mTvNum.setVisibility(8);
        this.mTvPercent.setText("正确率  " + data.getAccuracy());
        this.mImgEmpty.setVisibility(4);
        if (data.getChildren() != null && data.getChildren().size() > 0) {
            this.mImgExpand.setVisibility(0);
            this.mImgArrow.setVisibility(8);
            this.mLyAddView.removeAllViews();
            for (int i2 = 0; i2 < data.getChildren().size(); i2++) {
                PointStatisticeChildItemView pointStatisticeChildItemView = new PointStatisticeChildItemView(getContext());
                pointStatisticeChildItemView.setOnItemActionLisenter(this.onItemActionLisenter);
                pointStatisticeChildItemView.setData(data.getChildren().get(i2));
                this.mLyAddView.addView(pointStatisticeChildItemView);
            }
        } else {
            this.mImgExpand.setVisibility(4);
            this.mImgArrow.setVisibility(0);
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.wb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17046c.lambda$setData$2(data, view);
            }
        });
    }
}
