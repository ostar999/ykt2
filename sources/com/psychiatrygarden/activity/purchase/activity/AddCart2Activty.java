package com.psychiatrygarden.activity.purchase.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeModel;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.activity.AddCart2Activty;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.purchase.beans.ProductInfo;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class AddCart2Activty extends BaseActivity {
    public CommAdapter<ProductInfo> adapter;
    public ListView exListView;

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.AddCart2Activty.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                AddCart2Activty.this.getCount(msg.arg1, msg.arg2);
            }
        }
    };
    public List<ProductInfo> plist;
    public TextView tv_total_price;

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.AddCart2Activty$2, reason: invalid class name */
    public class AnonymousClass2 extends CommAdapter<ProductInfo> {
        public AnonymousClass2(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TextView textView, ProductInfo productInfo, View view) {
            if (Integer.parseInt(textView.getText().toString()) == 1) {
                textView.setText(String.valueOf(1));
                return;
            }
            textView.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(Integer.parseInt(textView.getText().toString()) - 1)));
            Message message = new Message();
            message.what = 1;
            message.arg1 = Integer.parseInt(textView.getText().toString());
            message.arg2 = Integer.parseInt(productInfo.getPrice() + "");
            AddCart2Activty.this.mHandler.sendMessage(message);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TextView textView, ProductInfo productInfo, View view) {
            textView.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(Integer.parseInt(textView.getText().toString()) + 1)));
            Message message = new Message();
            message.what = 1;
            message.arg1 = Integer.parseInt(textView.getText().toString());
            message.arg2 = Integer.parseInt(productInfo.getPrice() + "");
            AddCart2Activty.this.mHandler.sendMessage(message);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$2(CheckBox checkBox, CompoundButton compoundButton, boolean z2) {
            if (z2) {
                checkBox.setChecked(true);
            }
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final ProductInfo t2, final int position) {
            vHolder.setText(R.id.tv_intro, t2.getName()).setText(R.id.tv_type, t2.getDesc()).setText(R.id.tv_price, t2.getPrice() + "").setImageUrl(R.id.iv_adapter_list_pic, t2.getImageUrl()).setText(R.id.tv_num_txt, t2.getCount() + "");
            final TextView textView = (TextView) vHolder.getView(R.id.tv_num);
            textView.setText("1");
            ((TextView) vHolder.getView(R.id.tv_reduce)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13589c.lambda$convert$0(textView, t2, view);
                }
            });
            ((TextView) vHolder.getView(R.id.tv_add)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13594c.lambda$convert$1(textView, t2, view);
                }
            });
            final CheckBox checkBox = (CheckBox) vHolder.getView(R.id.check_box);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.purchase.activity.h
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    AddCart2Activty.AnonymousClass2.lambda$convert$2(checkBox, compoundButton, z2);
                }
            });
        }
    }

    public void getCount(int num, int p2) {
        this.tv_total_price.setText(String.format("%s", "" + Double.parseDouble(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(num * p2)))));
    }

    public void getProductList() {
        for (int i2 = 0; i2 < 3; i2++) {
            this.plist.add(new ProductInfo(i2 + "", "商品" + i2, "", "这是个啥东西", 201.0d * i2, 1000));
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.plist, this.mContext, R.layout.item_shopcart_product);
        this.adapter = anonymousClass2;
        this.exListView.setAdapter((ListAdapter) anonymousClass2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        this.exListView = (ListView) findViewById(R.id.exListView);
        this.plist = new ArrayList();
        getProductList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.addcart);
        setTitle("购物车");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
