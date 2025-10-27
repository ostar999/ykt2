package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class FlowLayoutUtil {
    private static void changeShapeDrawable(View view, Context context, @ColorRes int color) {
        ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(color));
    }

    public static void initChildLabelViews(FlowLayout mFlowLayout, List<CurriculumItemBean.CourseLabel> labels, Context activity) {
        if (labels == null || labels.isEmpty()) {
            mFlowLayout.setVisibility(8);
            return;
        }
        int i2 = 0;
        mFlowLayout.setVisibility(0);
        int iDip2px = DpOrPxUtils.dip2px(activity, 8.0f);
        DpOrPxUtils.dip2px(activity, 6.0f);
        int iDip2px2 = DpOrPxUtils.dip2px(activity, 4.0f);
        int iDip2px3 = DpOrPxUtils.dip2px(activity, 3.0f);
        int iDip2px4 = DpOrPxUtils.dip2px(activity, 1.0f);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.leftMargin = 0;
        marginLayoutParams.rightMargin = iDip2px;
        marginLayoutParams.topMargin = 0;
        marginLayoutParams.bottomMargin = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < labels.size(); i4++) {
            if (!TextUtils.isEmpty(labels.get(i4).getValue())) {
                TextView textView = new TextView(activity);
                textView.setText(labels.get(i4).getValue());
                textView.setTextSize(11.0f);
                textView.setPadding(iDip2px2, iDip2px4, iDip2px2, iDip2px4);
                textView.setBackground(activity.getResources().getDrawable(R.drawable.shape_course_tags_bg, null));
                GradientDrawable gradientDrawable = (GradientDrawable) textView.getBackground();
                String color6 = toColor6(labels.get(i4).getBackground_color());
                String color62 = toColor6(labels.get(i4).getFont_color());
                if (color6.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")) {
                    gradientDrawable.setColor(Color.parseColor(color6));
                }
                if (color62.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")) {
                    textView.setTextColor(Color.parseColor(color62));
                }
                if ("促".equals(labels.get(i4).getValue()) || "赠".equals(labels.get(i4).getValue()) || "券".equals(labels.get(i4).getValue())) {
                    textView.setPadding(iDip2px3, iDip2px4, iDip2px3, iDip2px4);
                    i2 = 0;
                    gradientDrawable.setStroke(0, Color.parseColor(color6));
                    textView.getPaint().setFakeBoldText(true);
                } else {
                    gradientDrawable.setStroke((int) Math.ceil(TypedValue.applyDimension(1, 0.5f, activity.getResources().getDisplayMetrics())), Color.parseColor(color62));
                    textView.getPaint().setFakeBoldText(true);
                    i2 = 0;
                }
                mFlowLayout.addView(textView, marginLayoutParams);
                i3++;
            }
        }
        mFlowLayout.setVisibility(i3 == 0 ? 8 : i2);
    }

    public static void initChildLabelViewsSource(FlowLayout mFlowLayout, List<String> labels, Context activity) {
        if (labels == null || labels.isEmpty()) {
            mFlowLayout.setVisibility(8);
            return;
        }
        mFlowLayout.setVisibility(0);
        int iDip2px = DpOrPxUtils.dip2px(activity, 8.0f);
        DpOrPxUtils.dip2px(activity, 6.0f);
        DpOrPxUtils.dip2px(activity, 4.0f);
        DpOrPxUtils.dip2px(activity, 3.0f);
        DpOrPxUtils.dip2px(activity, 1.0f);
        int iDip2px2 = DpOrPxUtils.dip2px(activity, 24.0f);
        DpOrPxUtils.dip2px(activity, 12.0f);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, iDip2px2);
        marginLayoutParams.leftMargin = 0;
        marginLayoutParams.rightMargin = iDip2px;
        marginLayoutParams.topMargin = 0;
        marginLayoutParams.bottomMargin = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < labels.size(); i3++) {
            View viewInflate = View.inflate(activity, R.layout.item_label_choose_school_source, null);
            ((TextView) viewInflate.findViewById(R.id.tvSourceName)).setText(labels.get(i3));
            mFlowLayout.addView(viewInflate, marginLayoutParams);
            i2++;
        }
        mFlowLayout.setVisibility(i2 != 0 ? 0 : 8);
    }

    public static void initChildLabelViewsTargetSchool(FlowLayout mFlowLayout, List<String> labels, Context activity) {
        if (labels == null || labels.isEmpty()) {
            mFlowLayout.setVisibility(8);
            return;
        }
        mFlowLayout.setVisibility(0);
        int iDip2px = DpOrPxUtils.dip2px(activity, 8.0f);
        DpOrPxUtils.dip2px(activity, 6.0f);
        int iDip2px2 = DpOrPxUtils.dip2px(activity, 4.0f);
        DpOrPxUtils.dip2px(activity, 3.0f);
        int iDip2px3 = DpOrPxUtils.dip2px(activity, 1.0f);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.leftMargin = 0;
        marginLayoutParams.rightMargin = iDip2px;
        marginLayoutParams.topMargin = 0;
        marginLayoutParams.bottomMargin = 0;
        boolean z2 = SkinManager.getCurrentSkinType(activity) == 1;
        int i2 = 0;
        for (int i3 = 0; i3 < labels.size(); i3++) {
            if (!TextUtils.isEmpty(labels.get(i3))) {
                TextView textView = new TextView(activity);
                textView.setText(labels.get(i3));
                textView.setTextSize(11.0f);
                textView.setPadding(iDip2px2, iDip2px3, iDip2px2, iDip2px3);
                textView.setBackground(activity.getResources().getDrawable(R.drawable.shape_course_tags_bg, null));
                GradientDrawable gradientDrawable = (GradientDrawable) textView.getBackground();
                Resources resources = activity.getResources();
                gradientDrawable.setColor(z2 ? resources.getColor(R.color.main_theme_five_deep_color_night) : resources.getColor(R.color.main_theme_five_deep_color));
                textView.setTextColor(z2 ? activity.getResources().getColor(R.color.main_theme_color_night) : activity.getResources().getColor(R.color.main_theme_color));
                Resources resources2 = activity.getResources();
                gradientDrawable.setStroke(1, z2 ? resources2.getColor(R.color.main_theme_five_deep_color_night) : resources2.getColor(R.color.main_theme_five_deep_color));
                textView.getPaint().setFakeBoldText(true);
                mFlowLayout.addView(textView, marginLayoutParams);
                i2++;
            }
        }
        mFlowLayout.setVisibility(i2 == 0 ? 8 : 0);
    }

    public static void initChildViews(FlowLayout mFlowLayout, List<String> mNames, Context activity) {
        if (mNames == null || mNames.isEmpty()) {
            mFlowLayout.setVisibility(8);
        } else {
            mFlowLayout.setVisibility(0);
        }
        int iDip2px = DpOrPxUtils.dip2px(activity, 8.0f);
        DpOrPxUtils.dip2px(activity, 6.0f);
        int iDip2px2 = DpOrPxUtils.dip2px(activity, 4.0f);
        int iDip2px3 = DpOrPxUtils.dip2px(activity, 1.0f);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.leftMargin = 0;
        marginLayoutParams.rightMargin = iDip2px;
        marginLayoutParams.topMargin = 0;
        marginLayoutParams.bottomMargin = iDip2px2;
        int i2 = 0;
        for (int i3 = 0; i3 < mNames.size(); i3++) {
            if (!TextUtils.isEmpty(mNames.get(i3))) {
                TextView textView = new TextView(activity);
                textView.setText(mNames.get(i3));
                textView.setTextSize(10.0f);
                textView.setPadding(iDip2px2, iDip2px3, iDip2px2, iDip2px3);
                textView.setBackground(activity.getResources().getDrawable(R.drawable.shape_course_tags_bg));
                int i4 = i3 % 3;
                if (i4 != 0) {
                    if (i4 != 1) {
                        if (i4 == 2) {
                            if (isNight(activity)) {
                                changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_night_color3);
                                textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_night_color3));
                            } else {
                                changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_color3);
                                textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_color3));
                            }
                        }
                    } else if (isNight(activity)) {
                        changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_night_color2);
                        textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_night_color2));
                    } else {
                        changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_color2);
                        textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_color2));
                    }
                } else if (isNight(activity)) {
                    changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_night_color1);
                    textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_night_color1));
                } else {
                    changeShapeDrawable(textView, activity, R.color.all_course_tag_bg_color1);
                    textView.setTextColor(activity.getResources().getColor(R.color.all_course_tag_text_color1));
                }
                mFlowLayout.addView(textView, marginLayoutParams);
                i2++;
            }
        }
        mFlowLayout.setVisibility(i2 != 0 ? 0 : 8);
    }

    private static boolean isNight(Context context) {
        return SkinManager.getCurrentSkinType(context) == 1;
    }

    private static String toColor6(String color3) {
        if (color3.charAt(0) != '#' || color3.length() != 4) {
            return color3;
        }
        return DictionaryFactory.SHARP + color3.charAt(1) + color3.charAt(1) + color3.charAt(2) + color3.charAt(2) + color3.charAt(3) + color3.charAt(3);
    }
}
