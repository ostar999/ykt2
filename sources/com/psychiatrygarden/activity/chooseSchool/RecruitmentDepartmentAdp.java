package com.psychiatrygarden.activity.chooseSchool;

import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.SchoolDepartmentBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class RecruitmentDepartmentAdp extends BaseQuickAdapter<SchoolDepartmentBean.DepartmentData, BaseViewHolder> {
    public RecruitmentDepartmentAdp() {
        super(R.layout.item_recruitment_department);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, SchoolDepartmentBean.DepartmentData item) {
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_number);
        textView.setText(item.getTitle());
        textView2.setText("专业数" + item.getMajor_count());
    }
}
