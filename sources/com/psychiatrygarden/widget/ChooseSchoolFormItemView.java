package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChooseSchoolFormItemView extends LinearLayout {
    private ImageView mImgQuestion;
    private View mLine;
    private LinearLayout mLyItem;
    private LinearLayout mLyQuestion;
    private LinearLayout mLyView;
    private TextView mTvAvgScore;
    private TextView mTvHeightScore;
    private TextView mTvLowScore;
    private TextView mTvMajor;
    private TextView mTvPassScore;

    public ChooseSchoolFormItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_recruitment_information, this);
        this.mTvMajor = (TextView) findViewById(R.id.tv_major);
        this.mTvPassScore = (TextView) findViewById(R.id.tv_pass_score);
        this.mTvHeightScore = (TextView) findViewById(R.id.tv_height_score);
        this.mTvLowScore = (TextView) findViewById(R.id.tv_low_score);
        this.mTvAvgScore = (TextView) findViewById(R.id.tv_avg_score);
        this.mLine = findViewById(R.id.line);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        this.mLyView = (LinearLayout) findViewById(R.id.ly_view);
        this.mImgQuestion = (ImageView) findViewById(R.id.img_question);
        this.mLyQuestion = (LinearLayout) findViewById(R.id.ly_question);
        this.mTvMajor.getLayoutParams().width = ScreenUtil.getPxByDp(getContext(), 136);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(View view) {
        ToastUtil.shortToast(getContext(), "点击了录取分");
    }

    public void setData(int position, String content) {
        Context context;
        int i2;
        Context context2;
        int i3;
        this.mTvMajor.setText(content);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            context = getContext();
            i2 = R.color.third_txt_color_night;
        } else {
            context = getContext();
            i2 = R.color.third_txt_color;
        }
        int color = context.getColor(i2);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            context2 = getContext();
            i3 = R.color.first_text_color_night;
        } else {
            context2 = getContext();
            i3 = R.color.first_txt_color;
        }
        int color2 = context2.getColor(i3);
        if (position != 0) {
            this.mTvMajor.setTextSize(2, 13.0f);
            this.mTvPassScore.setTextSize(2, 13.0f);
            this.mTvHeightScore.setTextSize(2, 13.0f);
            this.mTvLowScore.setTextSize(2, 13.0f);
            this.mTvAvgScore.setTextSize(2, 13.0f);
            this.mTvPassScore.setTextColor(color2);
            this.mTvHeightScore.setTextColor(color2);
            this.mTvLowScore.setTextColor(color2);
            this.mTvAvgScore.setTextColor(color2);
            this.mLine.setVisibility(0);
            this.mImgQuestion.setVisibility(8);
            this.mLyView.setBackgroundResource(R.color.transparent);
            return;
        }
        this.mTvMajor.setTextSize(2, 12.0f);
        this.mTvPassScore.setTextSize(2, 12.0f);
        this.mTvHeightScore.setTextSize(2, 12.0f);
        this.mTvLowScore.setTextSize(2, 12.0f);
        this.mTvAvgScore.setTextSize(2, 12.0f);
        this.mTvPassScore.setTextColor(color);
        this.mTvHeightScore.setTextColor(color);
        this.mTvLowScore.setTextColor(color);
        this.mTvAvgScore.setTextColor(color);
        this.mLine.setVisibility(8);
        this.mImgQuestion.setVisibility(0);
        this.mLyView.setBackgroundResource(R.drawable.shape_catalogue_form_title);
        this.mLyQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.h2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16538c.lambda$setData$0(view);
            }
        });
    }

    public ChooseSchoolFormItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChooseSchoolFormItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
