package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChooseSchoolFormLeftItemView extends LinearLayout {
    private boolean mIsSchoolLine;
    private View mLine;
    private LinearLayout mLyView;
    private TextView mTvName;

    public ChooseSchoolFormLeftItemView(Context context, boolean isSchoolLine) {
        super(context);
        initView(isSchoolLine);
    }

    private void initView(boolean isSchoolLine) {
        Context context;
        int i2;
        this.mIsSchoolLine = isSchoolLine;
        LayoutInflater.from(getContext()).inflate(R.layout.item_school_left_form_title, this);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mLine = findViewById(R.id.line);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_view);
        this.mLyView = linearLayout;
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        if (this.mIsSchoolLine) {
            context = getContext();
            i2 = 72;
        } else {
            context = getContext();
            i2 = 136;
        }
        layoutParams.width = ScreenUtil.getPxByDp(context, i2);
    }

    public void setData(int position, EnrollmentData data, boolean isMajor) {
        Context context;
        int i2;
        Context context2;
        int i3;
        this.mTvName.setText(isMajor ? data.getMajor_title() : data.getYear());
        if (position == 0) {
            this.mTvName.setTextSize(2, 12.0f);
            TextView textView = this.mTvName;
            if (SkinManager.getCurrentSkinType(textView.getContext()) == 1) {
                context2 = getContext();
                i3 = R.color.third_txt_color_night;
            } else {
                context2 = getContext();
                i3 = R.color.third_txt_color;
            }
            textView.setTextColor(context2.getColor(i3));
            this.mLine.setVisibility(8);
            this.mLyView.setBackgroundResource(R.drawable.shape_left_catalogue_form_title);
            return;
        }
        this.mTvName.setTextSize(2, 13.0f);
        TextView textView2 = this.mTvName;
        if (SkinManager.getCurrentSkinType(textView2.getContext()) == 1) {
            context = getContext();
            i2 = R.color.first_text_color_night;
        } else {
            context = getContext();
            i2 = R.color.first_txt_color;
        }
        textView2.setTextColor(context.getColor(i2));
        this.mLine.setVisibility(0);
        this.mLyView.setBackgroundResource(R.color.transparent);
    }

    public ChooseSchoolFormLeftItemView(Context context, @Nullable AttributeSet attrs, boolean isSchoolLine) {
        super(context, attrs);
        initView(isSchoolLine);
    }

    public ChooseSchoolFormLeftItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean isSchoolLine) {
        super(context, attrs, defStyleAttr);
        initView(isSchoolLine);
    }

    public void setData(int position, EnrollmentData data) {
        Context context;
        int i2;
        Context context2;
        int i3;
        this.mTvName.setText(!TextUtils.isEmpty(data.getMajor_title_local()) ? data.getMajor_title_local() : data.getMajor_title());
        if (position == 0) {
            this.mTvName.setTextSize(2, 12.0f);
            TextView textView = this.mTvName;
            if (SkinManager.getCurrentSkinType(textView.getContext()) == 1) {
                context2 = getContext();
                i3 = R.color.third_txt_color_night;
            } else {
                context2 = getContext();
                i3 = R.color.third_txt_color;
            }
            textView.setTextColor(context2.getColor(i3));
            this.mLine.setVisibility(8);
            this.mLyView.setBackgroundResource(R.drawable.shape_left_catalogue_form_title);
            return;
        }
        this.mTvName.setTextSize(2, 13.0f);
        TextView textView2 = this.mTvName;
        if (SkinManager.getCurrentSkinType(textView2.getContext()) == 1) {
            context = getContext();
            i2 = R.color.first_text_color_night;
        } else {
            context = getContext();
            i2 = R.color.first_txt_color;
        }
        textView2.setTextColor(context.getColor(i2));
        this.mLine.setVisibility(0);
        this.mLyView.setBackgroundResource(R.color.transparent);
    }

    public void setData(int position, EnrollmentData data, View.OnClickListener listener) {
        Context context;
        int i2;
        Context context2;
        int i3;
        this.mTvName.setText(data.getMajor_title());
        if (position == 0) {
            this.mTvName.setTextSize(2, 12.0f);
            TextView textView = this.mTvName;
            if (SkinManager.getCurrentSkinType(textView.getContext()) == 1) {
                context2 = getContext();
                i3 = R.color.third_txt_color_night;
            } else {
                context2 = getContext();
                i3 = R.color.third_txt_color;
            }
            textView.setTextColor(context2.getColor(i3));
            this.mLine.setVisibility(8);
            this.mLyView.setBackgroundResource(R.drawable.shape_left_catalogue_form_title);
            return;
        }
        this.mTvName.setTextSize(2, 13.0f);
        TextView textView2 = this.mTvName;
        if (SkinManager.getCurrentSkinType(textView2.getContext()) == 1) {
            context = getContext();
            i2 = R.color.first_text_color_night;
        } else {
            context = getContext();
            i2 = R.color.first_txt_color;
        }
        textView2.setTextColor(context.getColor(i2));
        this.mLine.setVisibility(0);
        this.mLyView.setBackgroundResource(R.color.transparent);
        this.mLyView.setOnClickListener(listener);
    }
}
