package com.psychiatrygarden.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.bean.PurchaseCourseBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class PurchaseCourseAdapter extends BaseAdapter {
    private Context context;
    private List<PurchaseCourseBean> list_bean;
    Handler mHandler;
    ViewHoder viewHoder = null;

    public class ViewHoder {
        private LinearLayout llay_all_subject;
        private LinearLayout llay_subject;
        private RelativeLayout rl_single_subject;
        private TextView tv_all_name;
        private TextView tv_all_renew;
        private TextView tv_dis;
        private TextView tv_dis_price;
        private TextView tv_price;
        private TextView tv_single_name;
        private TextView tv_single_price;
        private TextView tv_single_renew;
        private TextView tv_subject_seconedtitle;
        private TextView tv_subject_type;
        private TextView view_heng_line;

        public ViewHoder(View view) {
            this.llay_subject = (LinearLayout) view.findViewById(R.id.llay_subject);
            this.rl_single_subject = (RelativeLayout) view.findViewById(R.id.rl_single_subject);
            this.llay_all_subject = (LinearLayout) view.findViewById(R.id.llay_all_subject);
            this.tv_subject_type = (TextView) view.findViewById(R.id.tv_subject_type);
            this.tv_all_name = (TextView) view.findViewById(R.id.tv_all_name);
            this.tv_dis_price = (TextView) view.findViewById(R.id.tv_dis_price);
            this.tv_price = (TextView) view.findViewById(R.id.tv_price);
            this.tv_dis = (TextView) view.findViewById(R.id.tv_dis);
            this.tv_all_renew = (TextView) view.findViewById(R.id.tv_all_renew);
            this.tv_single_name = (TextView) view.findViewById(R.id.tv_single_name);
            this.tv_single_price = (TextView) view.findViewById(R.id.tv_single_price);
            this.tv_single_renew = (TextView) view.findViewById(R.id.tv_single_renew);
            this.tv_subject_seconedtitle = (TextView) view.findViewById(R.id.tv_subject_seconedtitle);
            this.view_heng_line = (TextView) view.findViewById(R.id.view_heng_line);
        }
    }

    public PurchaseCourseAdapter(Context context, List<PurchaseCourseBean> list_bean, Handler mHandler) {
        this.list_bean = list_bean;
        this.context = context;
        this.mHandler = mHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(int i2, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!this.list_bean.get(i2).getStatus().equals("1")) {
            NewToast.showShort(this.context, this.list_bean.get(i2).getStatus_str(), 0).show();
            return;
        }
        Message message = new Message();
        message.what = 0;
        Bundle bundle = new Bundle();
        bundle.putInt("position", i2);
        message.setData(bundle);
        this.mHandler.sendMessage(message);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list_bean.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.list_bean.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_all_course, (ViewGroup) null);
            ViewHoder viewHoder = new ViewHoder(convertView);
            this.viewHoder = viewHoder;
            convertView.setTag(viewHoder);
        } else {
            this.viewHoder = (ViewHoder) convertView.getTag();
        }
        PurchaseCourseBean purchaseCourseBean = this.list_bean.get(position);
        if (purchaseCourseBean.isShow()) {
            this.viewHoder.llay_subject.setVisibility(0);
            this.viewHoder.tv_subject_type.setText(purchaseCourseBean.getSubjecet_name());
            this.viewHoder.tv_subject_seconedtitle.setText(purchaseCourseBean.getSeconedtitle());
        }
        if (purchaseCourseBean.getSubjecet_type() == 0) {
            this.viewHoder.llay_all_subject.setVisibility(0);
            this.viewHoder.rl_single_subject.setVisibility(8);
            this.viewHoder.tv_all_name.setText(purchaseCourseBean.getGoods_name());
            this.viewHoder.tv_dis_price.setText(purchaseCourseBean.getGoods_unit() + purchaseCourseBean.getGoods_discount_price_android());
            this.viewHoder.tv_price.setText(purchaseCourseBean.getGoods_unit() + purchaseCourseBean.getGoods_price_android());
            this.viewHoder.tv_price.getPaint().setFlags(17);
            this.viewHoder.tv_dis.setText(purchaseCourseBean.getGoods_desc());
        } else {
            this.viewHoder.llay_all_subject.setVisibility(8);
            this.viewHoder.rl_single_subject.setVisibility(0);
            this.viewHoder.tv_single_name.setText(purchaseCourseBean.getGoods_name());
            this.viewHoder.tv_single_price.setText(purchaseCourseBean.getGoods_unit() + purchaseCourseBean.getGoods_price_android());
        }
        this.viewHoder.tv_all_renew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.PurchaseCourseAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                if (!((PurchaseCourseBean) PurchaseCourseAdapter.this.list_bean.get(position)).getStatus().equals("1")) {
                    NewToast.showShort(PurchaseCourseAdapter.this.context, ((PurchaseCourseBean) PurchaseCourseAdapter.this.list_bean.get(position)).getStatus_str(), 0).show();
                    return;
                }
                Message message = new Message();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                message.setData(bundle);
                PurchaseCourseAdapter.this.mHandler.sendMessage(message);
            }
        });
        this.viewHoder.tv_single_renew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.kc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14684c.lambda$getView$0(position, view);
            }
        });
        return convertView;
    }
}
