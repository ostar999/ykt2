package com.psychiatrygarden.activity.chooseSchool;

import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.AdmissionBrochureBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class AdmissionBrochureAdp extends BaseQuickAdapter<AdmissionBrochureBean.AdmissionBrochureData, BaseViewHolder> {
    public AdmissionBrochureAdp() {
        super(R.layout.item_admission_brochure);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, AdmissionBrochureBean.AdmissionBrochureData item) {
        TextView textView = (TextView) helper.getView(R.id.tv_title);
        TextView textView2 = (TextView) helper.getView(R.id.tv_browse);
        textView.setText(item.getTitle());
        textView2.setText(item.getView_count() + " 浏览");
    }
}
