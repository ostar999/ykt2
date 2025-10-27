package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Typeface;
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
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.widget.MockPointStatisticeChildItemTwoView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MockPointStatisticeChildItemView extends LinearLayout {
    private boolean isExpand;
    private View line;
    private ImageView mImgArrow;
    private ImageView mImgEmpty;
    private ImageView mImgExpand;
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

    public MockPointStatisticeChildItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_mock_point_child_item, this);
        this.mTvName = (TextView) findViewById(R.id.tv_title);
        this.mTvCategory = (TextView) findViewById(R.id.tv_category);
        this.mTvStar = (TextView) findViewById(R.id.tv_star);
        this.mTvNum = (TextView) findViewById(R.id.tv_num);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvFrequency = (TextView) findViewById(R.id.tv_frequency);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mImgExpand = (ImageView) findViewById(R.id.img_expand);
        this.mLyItem = (RelativeLayout) findViewById(R.id.ly_item);
        this.mImgEmpty = (ImageView) findViewById(R.id.img_empty);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.line = findViewById(R.id.line);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(MockStatisticsTreeBean.MockStatisticsData mockStatisticsData, boolean z2, int i2, View view) {
        if (mockStatisticsData.getChildren() == null || mockStatisticsData.getChildren().size() <= 0) {
            OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
            if (onItemActionLisenter != null) {
                onItemActionLisenter.jumpToQuestionDetail(mockStatisticsData.getId());
                return;
            }
            return;
        }
        this.isExpand = !this.isExpand;
        if (this.mLyAddView.getChildCount() == 0 && mockStatisticsData.getChildren() != null && mockStatisticsData.getChildren().size() > 0) {
            for (int i3 = 0; i3 < mockStatisticsData.getChildren().size(); i3++) {
                MockPointStatisticeChildItemView mockPointStatisticeChildItemView = new MockPointStatisticeChildItemView(getContext());
                mockPointStatisticeChildItemView.setOnItemActionLisenter(this.onItemActionLisenter);
                mockPointStatisticeChildItemView.setData(mockStatisticsData.getChildren().get(i3), z2, i2 + 1);
                this.mLyAddView.addView(mockPointStatisticeChildItemView);
            }
        }
        this.mImgExpand.setImageResource(this.isExpand ? R.mipmap.ic_outline_expand : R.mipmap.ic_outline_collapse);
        this.mLyAddView.setVisibility(this.isExpand ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$1(MockLoulineStatisticsBean.MockOutlineData mockOutlineData, View view) {
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
        this.mImgExpand.setImageResource(z2 ? R.mipmap.ic_outline_expand : R.mipmap.ic_outline_collapse);
        this.mLyAddView.setVisibility(this.isExpand ? 0 : 8);
    }

    public void setData(final MockStatisticsTreeBean.MockStatisticsData data, final boolean isHadChild, final int floor) {
        int pxByDp = ScreenUtil.getPxByDp(getContext(), 16);
        if (isHadChild) {
            this.mImgEmpty.setVisibility(4);
            if (floor == 1) {
                this.mTvName.setTypeface(Typeface.DEFAULT_BOLD);
                this.mTvName.setTextSize(2, 15.0f);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyAddView.getLayoutParams();
                layoutParams.setMargins(pxByDp, 0, pxByDp, 0);
                this.mLyAddView.setLayoutParams(layoutParams);
            } else if (floor == 2) {
                this.mTvName.setTypeface(Typeface.DEFAULT);
                this.mTvName.setTextSize(2, 15.0f);
                this.mImgExpand.setVisibility(0);
                this.mImgArrow.setVisibility(8);
                if (data.getChildren() != null && data.getChildren().size() > 0) {
                    this.mLyAddView.setBackgroundResource(R.drawable.shape_new_bg_two_color_no_night_corners_8);
                    this.line.setVisibility(8);
                    int i2 = 0;
                    while (i2 < data.getChildren().size()) {
                        MockPointStatisticeChildItemTwoView mockPointStatisticeChildItemTwoView = new MockPointStatisticeChildItemTwoView(getContext());
                        mockPointStatisticeChildItemTwoView.setOnItemActionLisenter(new MockPointStatisticeChildItemTwoView.OnItemActionLisenter() { // from class: com.psychiatrygarden.widget.MockPointStatisticeChildItemView.1
                            @Override // com.psychiatrygarden.widget.MockPointStatisticeChildItemTwoView.OnItemActionLisenter
                            public void jumpToQuestionDetail(String knowledgeId) {
                                MockPointStatisticeChildItemView.this.onItemActionLisenter.jumpToQuestionDetail(knowledgeId);
                            }
                        });
                        mockPointStatisticeChildItemTwoView.setData(data.getChildren().get(i2), i2 == data.getChildren().size() - 1, floor + 1);
                        this.mLyAddView.addView(mockPointStatisticeChildItemTwoView);
                        i2++;
                    }
                }
            } else {
                this.mImgExpand.setVisibility(8);
                this.mImgArrow.setVisibility(0);
                this.mTvName.setTextSize(2, 14.0f);
            }
        } else {
            this.mImgEmpty.setVisibility(8);
            this.mImgExpand.setVisibility(8);
        }
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
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.cb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16368c.lambda$setData$0(data, isHadChild, floor, view);
            }
        });
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    public MockPointStatisticeChildItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MockPointStatisticeChildItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setData(final MockLoulineStatisticsBean.MockOutlineData data, int floor) {
        this.mTvName.setTypeface(Typeface.DEFAULT_BOLD);
        this.mTvName.setTextSize(2, 15.0f);
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
            this.mLyAddView.setBackgroundResource(R.drawable.shape_new_bg_two_color_no_night_corners_8);
            this.line.setVisibility(8);
            this.mImgExpand.setVisibility(0);
            this.mImgArrow.setVisibility(8);
            this.mLyAddView.removeAllViews();
            int i2 = 0;
            while (i2 < data.getChildren().size()) {
                MockPointStatisticeChildItemTwoView mockPointStatisticeChildItemTwoView = new MockPointStatisticeChildItemTwoView(getContext());
                mockPointStatisticeChildItemTwoView.setOnItemActionLisenter(new MockPointStatisticeChildItemTwoView.OnItemActionLisenter() { // from class: com.psychiatrygarden.widget.MockPointStatisticeChildItemView.2
                    @Override // com.psychiatrygarden.widget.MockPointStatisticeChildItemTwoView.OnItemActionLisenter
                    public void jumpToQuestionDetail(String knowledgeId) {
                        MockPointStatisticeChildItemView.this.onItemActionLisenter.jumpToQuestionDetail(knowledgeId);
                    }
                });
                mockPointStatisticeChildItemTwoView.setData(data.getChildren().get(i2), i2 == data.getChildren().size() - 1, 1 + floor);
                this.mLyAddView.addView(mockPointStatisticeChildItemTwoView);
                i2++;
            }
        } else {
            this.mImgExpand.setVisibility(4);
            this.mImgArrow.setVisibility(0);
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.bb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16341c.lambda$setData$1(data, view);
            }
        });
    }
}
