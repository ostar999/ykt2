package com.psychiatrygarden.activity.purchase.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.ShopcartExpandableListViewAdapter;
import com.psychiatrygarden.activity.purchase.beans.GroupInfo;
import com.psychiatrygarden.activity.purchase.beans.ProductInfo;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class AddCartActivity extends BaseActivity implements ShopcartExpandableListViewAdapter.CheckInterface, ShopcartExpandableListViewAdapter.ModifyCountInterface, View.OnClickListener {
    private CheckBox cb_check_all;
    private Context context;
    private ExpandableListView exListView;
    private ShopcartExpandableListViewAdapter selva;
    private TextView tv_delete;
    private TextView tv_freight;
    private TextView tv_go_to_pay;
    private TextView tv_total_price;
    boolean flag = true;
    private double totalPrice = 0.0d;
    private int totalCount = 0;
    private List<GroupInfo> groups = new ArrayList();
    private Map<String, List<ProductInfo>> children = new HashMap();
    private List<ProductInfo> list_ProductInfo = new ArrayList();

    private void calculate() {
        this.totalCount = 0;
        this.totalPrice = 0.0d;
        this.list_ProductInfo.clear();
        int count = 0;
        for (int i2 = 0; i2 < this.groups.size(); i2++) {
            List<ProductInfo> list = this.children.get(this.groups.get(i2).getId());
            for (int i3 = 0; i3 < list.size(); i3++) {
                ProductInfo productInfo = list.get(i3);
                if (productInfo.isChoosed()) {
                    this.list_ProductInfo.add(productInfo);
                    this.totalCount++;
                    count += productInfo.getCount();
                    this.totalPrice += productInfo.getPrice() * productInfo.getCount();
                }
            }
        }
        if (count > Integer.parseInt(getIntent().getStringExtra("delivery_data"))) {
            this.tv_freight.setText(" 运费：￥0");
        } else {
            this.tv_freight.setText(" 运费：￥" + getIntent().getStringExtra("delivery_freight"));
        }
        this.tv_total_price.setText("￥" + CommonUtil.getNumber(this.totalPrice));
        this.tv_go_to_pay.setText("去支付(" + this.totalCount + ")");
    }

    private void doCheckAll() {
        for (int i2 = 0; i2 < this.groups.size(); i2++) {
            this.groups.get(i2).setChoosed(this.cb_check_all.isChecked());
            List<ProductInfo> list = this.children.get(this.groups.get(i2).getId());
            for (int i3 = 0; i3 < list.size(); i3++) {
                list.get(i3).setChoosed(this.cb_check_all.isChecked());
            }
        }
        this.selva.notifyDataSetChanged();
        calculate();
    }

    private void initEvents() {
        ShopcartExpandableListViewAdapter shopcartExpandableListViewAdapter = new ShopcartExpandableListViewAdapter(this.groups, this.children, this);
        this.selva = shopcartExpandableListViewAdapter;
        shopcartExpandableListViewAdapter.setCheckInterface(this);
        this.selva.setModifyCountInterface(this);
        this.exListView.setAdapter(this.selva);
        if (this.flag) {
            int count = this.exListView.getCount();
            for (int i2 = 0; i2 < count; i2++) {
                this.exListView.expandGroup(i2);
            }
            this.flag = false;
        }
        this.cb_check_all.setOnClickListener(this);
        this.tv_delete.setOnClickListener(this);
        this.tv_go_to_pay.setOnClickListener(this);
        this.cb_check_all.setChecked(true);
        doCheckAll();
    }

    private void initView() {
        this.context = this;
        virtualData();
        this.exListView = (ExpandableListView) findViewById(R.id.exListView);
        this.cb_check_all = (CheckBox) findViewById(R.id.all_chekbox);
        this.tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        TextView textView = (TextView) findViewById(R.id.tv_freight);
        this.tv_freight = textView;
        textView.setVisibility(8);
        this.tv_delete = (TextView) findViewById(R.id.tv_delete);
        this.tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
        this.tv_freight.setText(" 运费：￥" + getIntent().getStringExtra("delivery_freight"));
    }

    private boolean isAllCheck() {
        Iterator<GroupInfo> it = this.groups.iterator();
        while (it.hasNext()) {
            if (!it.next().isChoosed()) {
                return false;
            }
        }
        return true;
    }

    private void virtualData() {
        if (SharePreferencesUtils.getInfoList(this.mContext, "list") == null) {
            return;
        }
        this.groups.add(new GroupInfo("0", ""));
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < SharePreferencesUtils.getInfoList(this.mContext, "list").size(); i2++) {
            arrayList.add(SharePreferencesUtils.getInfoList(this.mContext, "list").get(i2));
        }
        this.children.put(this.groups.get(0).getId(), arrayList);
    }

    @Override // com.psychiatrygarden.activity.purchase.adapter.ShopcartExpandableListViewAdapter.CheckInterface
    public void checkChild(int groupPosition, int childPosiTion, boolean isChecked) {
        boolean z2;
        GroupInfo groupInfo = this.groups.get(groupPosition);
        List<ProductInfo> list = this.children.get(groupInfo.getId());
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                z2 = true;
                break;
            } else {
                if (list.get(i2).isChoosed() != isChecked) {
                    z2 = false;
                    break;
                }
                i2++;
            }
        }
        if (z2) {
            groupInfo.setChoosed(isChecked);
        } else {
            groupInfo.setChoosed(false);
        }
        if (isAllCheck()) {
            this.cb_check_all.setChecked(true);
        } else {
            this.cb_check_all.setChecked(false);
        }
        this.selva.notifyDataSetChanged();
        calculate();
    }

    @Override // com.psychiatrygarden.activity.purchase.adapter.ShopcartExpandableListViewAdapter.CheckInterface
    public void checkGroup(int groupPosition, boolean isChecked) {
        List<ProductInfo> list = this.children.get(this.groups.get(groupPosition).getId());
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.get(i2).setChoosed(isChecked);
        }
        if (isAllCheck()) {
            this.cb_check_all.setChecked(true);
        } else {
            this.cb_check_all.setChecked(false);
        }
        this.selva.notifyDataSetChanged();
        calculate();
    }

    @Override // com.psychiatrygarden.activity.purchase.adapter.ShopcartExpandableListViewAdapter.ModifyCountInterface
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo productInfo = (ProductInfo) this.selva.getChild(groupPosition, childPosition);
        int count = productInfo.getCount();
        if (count == 1) {
            return;
        }
        int i2 = count - 1;
        productInfo.setCount(i2);
        ((TextView) showCountView).setText(i2 + "");
        this.selva.notifyDataSetChanged();
        calculate();
    }

    public void doDelete() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.groups.size(); i2++) {
            GroupInfo groupInfo = this.groups.get(i2);
            if (groupInfo.isChoosed()) {
                arrayList.add(groupInfo);
            }
            ArrayList arrayList2 = new ArrayList();
            List<ProductInfo> list = this.children.get(groupInfo.getId());
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (list.get(i3).isChoosed()) {
                    arrayList2.add(list.get(i3));
                }
            }
            list.removeAll(arrayList2);
        }
        this.groups.removeAll(arrayList);
        this.selva.notifyDataSetChanged();
        calculate();
    }

    @Override // com.psychiatrygarden.activity.purchase.adapter.ShopcartExpandableListViewAdapter.ModifyCountInterface
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo productInfo = (ProductInfo) this.selva.getChild(groupPosition, childPosition);
        int count = productInfo.getCount() + 1;
        productInfo.setCount(count);
        ((TextView) showCountView).setText(count + "");
        this.selva.notifyDataSetChanged();
        calculate();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.all_chekbox) {
            doCheckAll();
            return;
        }
        if (id == R.id.tv_delete) {
            if (this.totalCount == 0) {
                NewToast.showShort(this.context, "请选择要移除的商品", 1).show();
                return;
            }
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.context, R.style.MyDialog).create();
            alertDialogCreate.setTitle("操作提示");
            alertDialogCreate.setMessage("您确定要将这些商品从购物车中移除吗？");
            alertDialogCreate.setButton(-2, "取消", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.AddCartActivity.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogCreate.setButton(-1, "确定", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.AddCartActivity.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    AddCartActivity.this.doDelete();
                }
            });
            alertDialogCreate.show();
            return;
        }
        if (id == R.id.tv_go_to_pay && !CommonUtil.isFastClick()) {
            if (this.totalCount == 0) {
                NewToast.showShort(this.context, "请选择要支付的商品", 1).show();
                return;
            }
            AlertDialog alertDialogCreate2 = new AlertDialog.Builder(this.context, R.style.MyDialog).create();
            alertDialogCreate2.setTitle("操作提示");
            alertDialogCreate2.setMessage("总计:\n" + this.totalCount + "种商品\n" + CommonUtil.getNumber(this.totalPrice) + "元\n运费：" + getIntent().getStringExtra("delivery_freight"));
            alertDialogCreate2.setButton(-2, "取消", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.AddCartActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogCreate2.setButton(-1, "确定", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.AddCartActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    new ArrayList();
                    List list = AddCartActivity.this.list_ProductInfo;
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ((ProductInfo) list.get(i2)).setChoosed(false);
                    }
                    SharePreferencesUtils.saveInfoList(AddCartActivity.this.mContext, "list", list);
                    Intent intent = new Intent(AddCartActivity.this.mContext, (Class<?>) QuenRenDingDanActivity.class);
                    intent.putExtra("good_list", (Serializable) AddCartActivity.this.list_ProductInfo);
                    intent.putExtra("good_price", AddCartActivity.this.totalPrice + "");
                    intent.putExtra("delivery_freight", AddCartActivity.this.getIntent().getStringExtra("delivery_freight"));
                    AddCartActivity.this.startActivity(intent);
                }
            });
            alertDialogCreate2.show();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        new ArrayList();
        List<ProductInfo> list = this.list_ProductInfo;
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.get(i2).setChoosed(false);
        }
        SharePreferencesUtils.saveInfoList(this.mContext, "list", list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.addcart);
        setTitle("购物车");
        initView();
        initEvents();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
