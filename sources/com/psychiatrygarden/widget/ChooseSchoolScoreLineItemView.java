package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChooseSchoolScoreLineItemView extends LinearLayout {
    private boolean mIsSchoolLine;
    private View mLine;
    private View mLineFive;
    private View mLineFour;
    private LinearLayout mLyItem;
    private LinearLayout mLyView;
    private TextView mTvMajor;
    private TextView mTvNameFive;
    private TextView mTvNameFour;
    private TextView mTvNameOne;
    private TextView mTvNameThree;
    private TextView mTvNameTwo;
    private TextView mTvScore;

    public ChooseSchoolScoreLineItemView(Context context, boolean isSchoolLine) {
        super(context);
        initView(isSchoolLine);
    }

    private void initView(boolean isSchoolLine) {
        Context context;
        int i2;
        this.mIsSchoolLine = isSchoolLine;
        LayoutInflater.from(getContext()).inflate(R.layout.item_school_score_line, this);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ly_major);
        this.mTvMajor = (TextView) findViewById(R.id.tv_major);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvNameOne = (TextView) findViewById(R.id.tv_name_one);
        this.mTvNameTwo = (TextView) findViewById(R.id.tv_name_two);
        this.mTvNameThree = (TextView) findViewById(R.id.tv_name_three);
        this.mTvNameFour = (TextView) findViewById(R.id.tv_name_four);
        this.mTvNameFive = (TextView) findViewById(R.id.tv_name_five);
        this.mLine = findViewById(R.id.line);
        this.mLyView = (LinearLayout) findViewById(R.id.ly_view);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        this.mLineFour = findViewById(R.id.line_four);
        this.mLineFive = findViewById(R.id.line_five);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        if (this.mIsSchoolLine) {
            context = getContext();
            i2 = 72;
        } else {
            context = getContext();
            i2 = 136;
        }
        layoutParams.width = ScreenUtil.getPxByDp(context, i2);
    }

    public void setData(int position, EnrollmentData data, boolean isPersonNumber, boolean isMajor) {
        Context context;
        int i2;
        Context context2;
        int i3;
        this.mTvMajor.setText(isMajor ? data.getMajor_title() : data.getYear());
        if (isPersonNumber) {
            this.mTvScore.setText(data.getTotal());
            this.mTvNameOne.setText(data.getUnified_enrollment());
            this.mTvNameTwo.setText(data.getExemption_exam());
            this.mTvNameThree.setText(data.getRemark());
            this.mTvNameFour.setVisibility(8);
            this.mTvNameFive.setVisibility(8);
            this.mLineFour.setVisibility(8);
            this.mLineFive.setVisibility(8);
        } else {
            this.mTvScore.setText(data.getTotal());
            this.mTvNameOne.setText(data.getPolitics());
            this.mTvNameTwo.setText(data.getEnglish());
            this.mTvNameThree.setText(data.getMajor_one());
            this.mTvNameFour.setText(data.getMajor_two());
            this.mTvNameFive.setText(data.getRemark());
            this.mTvNameFour.setVisibility(0);
            this.mTvNameFive.setVisibility(0);
            this.mLineFour.setVisibility(0);
            this.mLineFive.setVisibility(0);
        }
        if (SkinManager.getCurrentSkinType(this.mTvScore.getContext()) == 1) {
            context = getContext();
            i2 = R.color.third_txt_color_night;
        } else {
            context = getContext();
            i2 = R.color.third_txt_color;
        }
        int color = context.getColor(i2);
        if (SkinManager.getCurrentSkinType(this.mTvScore.getContext()) == 1) {
            context2 = getContext();
            i3 = R.color.first_text_color_night;
        } else {
            context2 = getContext();
            i3 = R.color.first_txt_color;
        }
        int color2 = context2.getColor(i3);
        if (position == 0) {
            this.mTvMajor.setTextSize(2, 12.0f);
            this.mTvScore.setTextSize(2, 12.0f);
            this.mTvNameOne.setTextSize(2, 12.0f);
            this.mTvNameTwo.setTextSize(2, 12.0f);
            this.mTvNameThree.setTextSize(2, 12.0f);
            this.mTvNameFour.setTextSize(2, 12.0f);
            this.mTvNameFive.setTextSize(2, 12.0f);
            this.mTvScore.setTextColor(color);
            this.mTvNameOne.setTextColor(color);
            this.mTvNameTwo.setTextColor(color);
            this.mTvNameThree.setTextColor(color);
            this.mTvNameFour.setTextColor(color);
            this.mTvNameFive.setTextColor(color);
            this.mLine.setVisibility(8);
            this.mLyView.setBackgroundResource(R.drawable.shape_catalogue_form_title);
            return;
        }
        this.mTvMajor.setTextSize(2, 13.0f);
        this.mTvScore.setTextSize(2, 13.0f);
        this.mTvNameOne.setTextSize(2, 13.0f);
        this.mTvNameTwo.setTextSize(2, 13.0f);
        this.mTvNameThree.setTextSize(2, 13.0f);
        this.mTvNameFour.setTextSize(2, 13.0f);
        this.mTvNameFive.setTextSize(2, 13.0f);
        this.mTvScore.setTextColor(color2);
        this.mTvNameOne.setTextColor(color2);
        this.mTvNameTwo.setTextColor(color2);
        this.mTvNameThree.setTextColor(color2);
        this.mTvNameFour.setTextColor(color2);
        this.mTvNameFive.setTextColor(color2);
        this.mLine.setVisibility(0);
        this.mLyView.setBackgroundResource(R.color.transparent);
    }

    public ChooseSchoolScoreLineItemView(Context context, @Nullable AttributeSet attrs, boolean isSchoolLine) {
        super(context, attrs);
        initView(isSchoolLine);
    }

    public ChooseSchoolScoreLineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean isSchoolLine) {
        super(context, attrs, defStyleAttr);
        initView(isSchoolLine);
    }
}
