package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SchoolOpenMajorAdp extends BaseQuickAdapter<FollowSchoolBean.DataBean, BaseViewHolder> {
    public SchoolOpenMajorAdp() {
        super(R.layout.item_open_major);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, FollowSchoolBean.DataBean item) {
        Context context;
        int i2;
        Context context2;
        int i3;
        TextView textView = (TextView) helper.getView(R.id.tv_follow_count);
        TextView textView2 = (TextView) helper.getView(R.id.tv_follow_count_7);
        TextView textView3 = (TextView) helper.getView(R.id.tv_view_count);
        TextView textView4 = (TextView) helper.getView(R.id.tv_student_count);
        TextView textView5 = (TextView) helper.getView(R.id.tv_name);
        TextView textView6 = (TextView) helper.getView(R.id.tv_type);
        String major_code = item.getMajor_code();
        if (!TextUtils.isEmpty(item.getMajor_code())) {
            major_code = major_code + "-";
        }
        textView5.setText(major_code + item.getTitle());
        textView.setText(item.getFollow_count());
        textView2.setText(item.getRecent_7days_follow());
        textView3.setText(item.getView_count());
        textView4.setText(item.getStudent_count());
        if (TextUtils.isEmpty(item.getMajor_type())) {
            textView6.setVisibility(8);
            return;
        }
        textView6.setVisibility(0);
        if (item.getMajor_type().equals("1")) {
            textView6.setText("专硕");
            if (SkinManager.getCurrentSkinType(textView6.getContext()) == 1) {
                context2 = textView6.getContext();
                i3 = R.color.new_success_color_night;
            } else {
                context2 = textView6.getContext();
                i3 = R.color.new_success_color;
            }
            textView6.setTextColor(context2.getColor(i3));
            textView6.setBackgroundResource(R.drawable.shape_major_type_zhuan_bg);
            return;
        }
        textView6.setText("学硕");
        if (SkinManager.getCurrentSkinType(textView6.getContext()) == 1) {
            context = textView6.getContext();
            i2 = R.color.orange_color_night;
        } else {
            context = textView6.getContext();
            i2 = R.color.orange_color;
        }
        textView6.setTextColor(context.getColor(i2));
        textView6.setBackgroundResource(R.drawable.shape_major_type_xue_bg);
    }
}
