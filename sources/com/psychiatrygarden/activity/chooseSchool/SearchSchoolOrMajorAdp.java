package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.chip.ChipGroup;
import com.psychiatrygarden.bean.SearchSchoolOrMajorBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class SearchSchoolOrMajorAdp extends BaseMultiItemQuickAdapter<SearchSchoolOrMajorBean, BaseViewHolder> {
    private String searchContent;

    public SearchSchoolOrMajorAdp() {
        addItemType(1, R.layout.item_targetschool);
        addItemType(2, R.layout.item_open_major);
    }

    private void getImageDataDesc(String content, TextView mTextView) {
        String str = "\\s+";
        try {
            ColorStateList colorStateList = SkinManager.getCurrentSkinType(getContext()) == 1 ? getContext().getResources().getColorStateList(R.color.main_theme_color_night) : getContext().getResources().getColorStateList(R.color.main_theme_color);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str2 = this.searchContent;
            if (str2 != null && !"".equals(str2)) {
                String[] strArrSplit = this.searchContent.split("\\s+");
                int length = strArrSplit.length;
                int i2 = 0;
                while (i2 < length) {
                    String strReplace = strArrSplit[i2].replace(str, "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            String str3 = str;
                            Matcher matcher2 = matcher;
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher2.start(0), matcher2.end(0), 34);
                            matcher = matcher2;
                            str = str3;
                        }
                    }
                    i2++;
                    str = str;
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, SearchSchoolOrMajorBean item) {
        Context context;
        int i2;
        Context context2;
        int i3;
        Context context3;
        int i4;
        TextView textView = (TextView) helper.getView(R.id.tv_follow_count);
        TextView textView2 = (TextView) helper.getView(R.id.tv_follow_count_7);
        TextView textView3 = (TextView) helper.getView(R.id.tv_view_count);
        TextView textView4 = (TextView) helper.getView(R.id.tv_student_count);
        int itemType = item.getItemType();
        if (itemType != 1) {
            if (itemType != 2) {
                return;
            }
            TextView textView5 = (TextView) helper.getView(R.id.tv_name);
            TextView textView6 = (TextView) helper.getView(R.id.tv_type);
            textView.setText(item.getInfo().getFollow_count());
            textView2.setText(item.getInfo().getRecent_7days_follow());
            textView3.setText(item.getInfo().getView_count());
            textView4.setText(item.getInfo().getStudent_count());
            String major_code = item.getInfo().getMajor_code();
            if (!TextUtils.isEmpty(item.getInfo().getMajor_code())) {
                major_code = major_code + "-";
            }
            getImageDataDesc(major_code + item.getInfo().getTitle(), textView5);
            if (TextUtils.isEmpty(item.getInfo().getMajor_type())) {
                textView6.setVisibility(8);
                return;
            }
            textView6.setVisibility(0);
            if (item.getInfo().getMajor_type().equals("1")) {
                textView6.setText("专硕");
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    context3 = getContext();
                    i4 = R.color.new_success_color_night;
                } else {
                    context3 = getContext();
                    i4 = R.color.new_success_color;
                }
                textView6.setTextColor(context3.getColor(i4));
                textView6.setBackgroundResource(R.drawable.shape_major_type_zhuan_bg);
                return;
            }
            textView6.setText("学硕");
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                context2 = getContext();
                i3 = R.color.orange_color_night;
            } else {
                context2 = getContext();
                i3 = R.color.orange_color;
            }
            textView6.setTextColor(context2.getColor(i3));
            textView6.setBackgroundResource(R.drawable.shape_major_type_xue_bg);
            return;
        }
        int pxByDp = ScreenUtil.getPxByDp(getContext(), 4);
        int pxByDp2 = ScreenUtil.getPxByDp(getContext(), 1);
        CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.iv_school_icon);
        TextView textView7 = (TextView) helper.getView(R.id.school_title_tv);
        ChipGroup chipGroup = (ChipGroup) helper.getView(R.id.chipGroup);
        TextView textView8 = (TextView) helper.getView(R.id.tv_address_code);
        TextView textView9 = (TextView) helper.getView(R.id.tv_major_count);
        getImageDataDesc(item.getInfo().getTitle(), textView7);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getInfo().getCover())).placeholder(R.mipmap.ic_avatar_def).into(circleImageView);
        textView8.setText("地址： " + item.getInfo().getLocation() + "代码：" + item.getInfo().getCode());
        textView.setText(item.getInfo().getFollow_count());
        textView2.setText(item.getInfo().getRecent_7days_follow());
        textView3.setText(item.getInfo().getView_count());
        textView4.setText(item.getInfo().getStudent_count());
        textView9.setText(item.getInfo().getMajor_count());
        List<String> attr = item.getInfo().getAttr();
        if (attr == null) {
            chipGroup.setVisibility(4);
            return;
        }
        chipGroup.removeAllViews();
        chipGroup.setVisibility(0);
        for (String str : attr) {
            TextView textView10 = new TextView(getContext());
            textView10.setTextSize(10.0f);
            textView10.setText(str);
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                context = getContext();
                i2 = R.color.main_theme_color_night;
            } else {
                context = getContext();
                i2 = R.color.main_theme_color;
            }
            textView10.setTextColor(context.getColor(i2));
            textView10.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
            textView10.setBackgroundResource(R.drawable.shape_computer_time_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = ScreenUtil.getPxByDp(getContext(), 8);
            textView10.setLayoutParams(layoutParams);
            chipGroup.addView(textView10);
        }
    }
}
