package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.MockLoulineStatisticsBean;
import com.psychiatrygarden.bean.MockStatisticsTreeBean;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MockPointStatisticeChildItemTwoView extends LinearLayout {
    private View emptyView;
    private boolean isExpand;
    private View line;
    private ImageView mImgArrow;
    private LinearLayout mLyAddView;
    private RelativeLayout mLyItem;
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

    public MockPointStatisticeChildItemTwoView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_mock_point_child_two_item, this);
        this.mTvName = (TextView) findViewById(R.id.tv_title);
        this.mTvCategory = (TextView) findViewById(R.id.tv_category);
        this.mTvStar = (TextView) findViewById(R.id.tv_star);
        this.mTvNum = (TextView) findViewById(R.id.tv_num);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvFrequency = (TextView) findViewById(R.id.tv_frequency);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mLyItem = (RelativeLayout) findViewById(R.id.ly_item);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.line = findViewById(R.id.line);
        this.emptyView = findViewById(R.id.empty_view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(MockStatisticsTreeBean.MockStatisticsData mockStatisticsData, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.jumpToQuestionDetail(mockStatisticsData.getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$1(MockLoulineStatisticsBean.MockOutlineData mockOutlineData, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.jumpToQuestionDetail(mockOutlineData.getChapter_id());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$2(MockLoulineStatisticsBean.MockOutlineData mockOutlineData, View view) {
        if (mockOutlineData.getChildren() != null && mockOutlineData.getChildren().size() > 0) {
            boolean z2 = !this.isExpand;
            this.isExpand = z2;
            this.mLyAddView.setVisibility(z2 ? 0 : 8);
        } else {
            OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
            if (onItemActionLisenter != null) {
                onItemActionLisenter.jumpToQuestionDetail(mockOutlineData.getChapter_id());
            }
        }
    }

    public void setData(final MockStatisticsTreeBean.MockStatisticsData data, boolean isEnd, int floor) {
        this.mImgArrow.setVisibility(0);
        this.mTvName.setTextSize(2, 14.0f);
        this.line.setVisibility(isEnd ? 8 : 0);
        this.mTvName.setText(data.getName());
        if (TextUtils.isEmpty(data.getCount())) {
            this.mTvCategory.setVisibility(8);
        } else {
            this.mTvCategory.setText("共" + data.getCount() + "题");
            this.mTvCategory.setVisibility(0);
        }
        if (TextUtils.isEmpty(data.getRight_count())) {
            this.mTvFrequency.setVisibility(8);
        } else {
            this.mTvFrequency.setText("对" + data.getRight_count() + "题");
            this.mTvFrequency.setVisibility(0);
        }
        if (TextUtils.isEmpty(data.getWrong_count())) {
            this.mTvStar.setVisibility(8);
        } else {
            this.mTvStar.setText("错" + data.getWrong_count() + "题");
            this.mTvStar.setVisibility(0);
        }
        this.mTvNum.setVisibility(8);
        this.mTvPercent.setText("正确率  " + data.getAccuracy());
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ab
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16314c.lambda$setData$0(data, view);
            }
        });
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    public MockPointStatisticeChildItemTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MockPointStatisticeChildItemTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setData(final MockLoulineStatisticsBean.MockOutlineData data, boolean isEnd, int floor) {
        this.mImgArrow.setVisibility(0);
        this.mTvName.setTextSize(2, 14.0f);
        this.line.setVisibility(isEnd ? 8 : 0);
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
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.za
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17150c.lambda$setData$1(data, view);
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
        if (data.getChildren() != null && data.getChildren().size() > 0) {
            this.mImgArrow.setVisibility(8);
            this.mLyAddView.removeAllViews();
            for (int i2 = 0; i2 < data.getChildren().size(); i2++) {
                MockPointStatisticeChildItemTwoView mockPointStatisticeChildItemTwoView = new MockPointStatisticeChildItemTwoView(getContext());
                mockPointStatisticeChildItemTwoView.setOnItemActionLisenter(this.onItemActionLisenter);
                mockPointStatisticeChildItemTwoView.setData(data.getChildren().get(i2));
                this.mLyAddView.addView(mockPointStatisticeChildItemTwoView);
            }
        } else {
            this.mImgArrow.setVisibility(0);
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ya
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17123c.lambda$setData$2(data, view);
            }
        });
    }
}
