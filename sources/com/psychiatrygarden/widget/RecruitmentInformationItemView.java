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
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class RecruitmentInformationItemView extends LinearLayout {
    private ImageView mImgArrow;
    private boolean mIsSchoolLine;
    private View mLine;
    private View mLineFive;
    private View mLineFour;
    private RelativeLayout mLyArrow;
    private LinearLayout mLyItem;
    private LinearLayout mLyMajorType;
    private LinearLayout mLyView;
    private View mMajorTypeLine;
    private TextView mTvMajor;
    private TextView mTvMajorType;
    private TextView mTvNameFive;
    private TextView mTvNameFour;
    private TextView mTvNameOne;
    private TextView mTvNameThree;
    private TextView mTvNameTwo;
    private TextView mTvScore;

    public RecruitmentInformationItemView(Context context, boolean isSchoolLine, boolean isHavDir) {
        super(context);
        initView(isSchoolLine, isHavDir);
    }

    private void initView(boolean isSchoolLine, boolean isHavDir) {
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
        this.mLyArrow = (RelativeLayout) findViewById(R.id.ly_arrow);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mLyMajorType = (LinearLayout) findViewById(R.id.ly_major_type);
        this.mTvMajorType = (TextView) findViewById(R.id.tv_type);
        this.mMajorTypeLine = findViewById(R.id.type_line);
        relativeLayout.getLayoutParams().width = this.mIsSchoolLine ? ScreenUtil.getPxByDp(getContext(), 72) : ScreenUtil.getPxByDp(getContext(), 136);
        this.mTvScore.getLayoutParams().width = !isHavDir ? ScreenUtil.getPxByDp(getContext(), 72) : ScreenUtil.getPxByDp(getContext(), 136);
    }

    public void setData(int position, EnrollmentData data, boolean isChild, boolean isShowDirection, View.OnClickListener clickListener) {
        Context context;
        int i2;
        Context context2;
        int i3;
        Context context3;
        int i4;
        Context context4;
        int i5;
        this.mTvMajor.setText(TextUtils.isEmpty(data.getMajor_title_local()) ? data.getMajor_title() : data.getMajor_title_local());
        if (isChild) {
            if (isShowDirection) {
                this.mTvScore.setText(data.getMajor_direction_title());
                this.mTvNameOne.setText(data.getRecruit_count());
                this.mTvNameTwo.setText(data.getMax_score());
                this.mTvNameThree.setText(data.getMin_score());
                this.mTvNameFour.setText(data.getAvg_score());
                this.mTvNameFour.setVisibility(0);
            } else {
                this.mTvScore.setText(data.getRecruit_count());
                this.mTvNameOne.setText(data.getMax_score());
                this.mTvNameTwo.setText(data.getMin_score());
                this.mTvNameThree.setText(data.getAvg_score());
                this.mTvNameFour.setVisibility(8);
            }
            this.mTvNameFive.setVisibility(8);
            this.mLineFour.setVisibility(8);
            this.mLineFive.setVisibility(8);
        } else {
            if (data.getMajor_type().equals("1")) {
                this.mTvMajorType.setText("专硕");
            } else if (data.getMajor_type().equals("2")) {
                this.mTvMajorType.setText("学硕");
            } else {
                this.mTvMajorType.setText(data.getMajor_type());
            }
            this.mLyMajorType.setVisibility(0);
            this.mMajorTypeLine.setVisibility(0);
            this.mTvScore.setText(data.getRecruit_count());
            this.mTvNameOne.setText(data.getMax_score());
            this.mTvNameTwo.setText(data.getMin_score());
            this.mTvNameThree.setText(data.getAvg_score());
            this.mTvNameFour.setVisibility(8);
            this.mTvNameFive.setVisibility(8);
            this.mLineFour.setVisibility(8);
            this.mLineFive.setVisibility(8);
            this.mLyArrow.setVisibility(0);
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
            this.mImgArrow.setVisibility(8);
            this.mTvMajorType.setTextSize(2, 12.0f);
            this.mTvMajor.setTextSize(2, 12.0f);
            this.mTvScore.setTextSize(2, 12.0f);
            this.mTvNameOne.setTextSize(2, 12.0f);
            this.mTvNameTwo.setTextSize(2, 12.0f);
            this.mTvNameThree.setTextSize(2, 12.0f);
            this.mTvScore.setTextColor(color);
            this.mTvMajorType.setTextColor(color);
            this.mTvNameOne.setTextColor(color);
            this.mTvNameTwo.setTextColor(color);
            this.mTvNameThree.setTextColor(color);
            this.mLine.setVisibility(8);
            this.mLyView.setBackgroundResource(R.drawable.shape_catalogue_form_title);
            return;
        }
        this.mImgArrow.setVisibility(0);
        this.mTvMajor.setTextSize(2, 13.0f);
        this.mTvScore.setTextSize(2, 13.0f);
        this.mTvNameOne.setTextSize(2, 13.0f);
        this.mTvNameTwo.setTextSize(2, 13.0f);
        this.mTvNameThree.setTextSize(2, 13.0f);
        this.mTvScore.setTextColor(color2);
        this.mTvNameOne.setTextColor(color2);
        this.mTvNameTwo.setTextColor(color2);
        this.mTvNameThree.setTextColor(color2);
        this.mLine.setVisibility(0);
        this.mLyView.setBackgroundResource(R.color.transparent);
        if (isChild) {
            return;
        }
        if (data.getMajor_type().equals("1")) {
            TextView textView = this.mTvMajorType;
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                context4 = getContext();
                i5 = R.color.new_success_color_night;
            } else {
                context4 = getContext();
                i5 = R.color.new_success_color;
            }
            textView.setTextColor(context4.getColor(i5));
            this.mTvMajorType.setBackgroundResource(R.drawable.shape_major_type_zhuan_bg);
        } else {
            TextView textView2 = this.mTvMajorType;
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                context3 = getContext();
                i4 = R.color.orange_color_night;
            } else {
                context3 = getContext();
                i4 = R.color.orange_color;
            }
            textView2.setTextColor(context3.getColor(i4));
            this.mTvMajorType.setBackgroundResource(R.drawable.shape_major_type_xue_bg);
        }
        this.mLyView.setOnClickListener(clickListener);
    }

    public RecruitmentInformationItemView(Context context, @Nullable AttributeSet attrs, boolean isSchoolLine, boolean isHavDir) {
        super(context, attrs);
        initView(isSchoolLine, isHavDir);
    }

    public RecruitmentInformationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean isSchoolLine, boolean isHavDir) {
        super(context, attrs, defStyleAttr);
        initView(isSchoolLine, isHavDir);
    }
}
