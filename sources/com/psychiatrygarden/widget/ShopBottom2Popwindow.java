package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class ShopBottom2Popwindow extends BottomPopupView {
    public BaseQuickAdapter<GoodsBean.DataBean.AttributesBean, BaseViewHolder> adapter;
    public TextView buygoodsView;
    public ImageView closeimg;
    public ConfirmPurchaseClickIml confirmPurchaseClickIml;
    public int inventory;
    public List<GoodsBean.DataBean.AttributesBean> meal;
    public String priceStr;
    public TextView priceTv;
    public RecyclerView recycleview;
    public TextView saleTv;
    public String salenum;
    public RoundedImageView shopimg;
    public List<GoodsBean.DataBean.SkusBean> skusBeans;
    public String thumil;

    public interface ConfirmPurchaseClickIml {
        void mConfirmPurchaseClickIml(GoodsBean.DataBean.SkusBean skusBean);
    }

    public ShopBottom2Popwindow(@NonNull @NotNull Context context, List<GoodsBean.DataBean.AttributesBean> meal, List<GoodsBean.DataBean.SkusBean> skusBeans, String thumil, ConfirmPurchaseClickIml confirmPurchaseClickIml) {
        super(context);
        this.priceStr = "";
        this.salenum = "";
        this.meal = meal == null ? new ArrayList<>() : meal;
        this.skusBeans = skusBeans == null ? new ArrayList<>() : skusBeans;
        this.thumil = thumil;
        this.confirmPurchaseClickIml = confirmPurchaseClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStack(final List<GoodsBean.DataBean.AttributesBean.AttributesChildBean> meal, final StackLayout laybelview) {
        if (laybelview == null || meal == null) {
            return;
        }
        laybelview.removeAllViews();
        for (int i2 = 0; i2 < meal.size(); i2++) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_shop_curriculum_label_item, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.laeblTv);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.tjimg);
            if ("1".equals(meal.get(i2).getRecommend())) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            final RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.relview);
            if (meal.get(i2).getSelected() == 1) {
                relativeLayout.setSelected(true);
            }
            textView.setText(meal.get(i2).getName() + "");
            final int i3 = i2;
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.hh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16560c.lambda$addStack$1(laybelview, meal, relativeLayout, i3, view);
                }
            });
            laybelview.addView(viewInflate);
        }
    }

    private boolean hasRecommed(String recommentid, List<String> recommends) {
        Iterator<String> it = recommends.iterator();
        while (it.hasNext()) {
            if (recommentid.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    private void initLabelView(StackLayout laybelview, List<GoodsBean.DataBean.AttributesBean.AttributesChildBean> meal) {
        if (meal != null) {
            for (int i2 = 0; i2 < meal.size(); i2++) {
                meal.get(i2).setSelected(0);
            }
        }
        if (laybelview != null) {
            for (int i3 = 0; i3 < laybelview.getChildCount(); i3++) {
                if (laybelview.getChildAt(i3) instanceof RelativeLayout) {
                    ((RelativeLayout) laybelview.getChildAt(i3)).setSelected(false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addStack$1(StackLayout stackLayout, List list, RelativeLayout relativeLayout, int i2, View view) {
        initLabelView(stackLayout, list);
        relativeLayout.setSelected(true);
        ((GoodsBean.DataBean.AttributesBean.AttributesChildBean) list.get(i2)).setSelected(1);
        confirmPurchase(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (this.inventory <= 0) {
            ToastUtils.showShort("暂时无货");
        } else {
            confirmPurchase(this.confirmPurchaseClickIml);
        }
    }

    public void addItemData() {
        List<GoodsBean.DataBean.AttributesBean> list = this.meal;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.meal.size(); i2++) {
            if (this.meal.get(i2).getList() != null && this.meal.get(i2).getList().size() > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < this.meal.get(i2).getList().size(); i3++) {
                    GoodsBean.DataBean.AttributesBean.AttributesChildBean attributesChildBean = new GoodsBean.DataBean.AttributesBean.AttributesChildBean();
                    attributesChildBean.setName(this.meal.get(i2).getList().get(i3));
                    if (i3 == 0) {
                        attributesChildBean.setSelected(1);
                    }
                    if (hasRecommed(this.meal.get(i2).getList().get(i3), this.meal.get(i2).getRecommend())) {
                        attributesChildBean.setRecommend("1");
                    } else {
                        attributesChildBean.setRecommend("0");
                    }
                    arrayList.add(attributesChildBean);
                }
                this.meal.get(i2).setLabelList(arrayList);
            }
        }
    }

    public void assignmentTxt() {
        String str;
        if (getContext() == null) {
            return;
        }
        this.priceTv.setText(this.priceStr);
        TextView textView = this.saleTv;
        if ("".equals(this.salenum)) {
            str = "已销售0";
        } else {
            str = "已销售" + this.salenum;
        }
        textView.setText(str);
        if (this.inventory <= 0) {
            this.buygoodsView.setBackgroundResource(R.drawable.shape_goods_gray_action);
        } else {
            this.buygoodsView.setBackgroundResource(R.drawable.shape_round_red);
        }
    }

    public void confirmPurchase(ConfirmPurchaseClickIml confirmPurchaseClickIml) {
        GoodsBean.DataBean.AttributesBean attributesBean;
        BaseQuickAdapter<GoodsBean.DataBean.AttributesBean, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter == null || baseQuickAdapter.getData() == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
            if ((this.adapter.getData().get(i2) instanceof GoodsBean.DataBean.AttributesBean) && (attributesBean = this.adapter.getData().get(i2)) != null && attributesBean.getLabelList() != null) {
                for (int i3 = 0; i3 < attributesBean.getLabelList().size(); i3++) {
                    if (attributesBean.getLabelList().get(i3).getSelected() == 1) {
                        sb.append(attributesBean.getLabelList().get(i3).getName());
                    }
                }
            }
        }
        if (TextUtils.isEmpty(sb.toString())) {
            return;
        }
        skusMuthod(sb.toString(), confirmPurchaseClickIml);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_shop_bottom2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        try {
            int screenHeight = (ScreenUtil.getScreenHeight((Activity) getContext()) / 4) * 3;
            if (screenHeight > 0) {
                return screenHeight;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return super.getMaxHeight();
    }

    public void getPriceOrSaleNum() {
        List<GoodsBean.DataBean.AttributesBean> list = this.meal;
        if (list == null || list.size() <= 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.meal.size(); i2++) {
            if (this.meal.get(i2).getList() != null && this.meal.get(i2).getList().size() > 0) {
                for (int i3 = 0; i3 < this.meal.get(i2).getList().size(); i3++) {
                    if (i3 == 0) {
                        sb.append(this.meal.get(i2).getList().get(0));
                    }
                }
            }
        }
        skusMuthod(sb.toString(), null);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.shopimg = (RoundedImageView) findViewById(R.id.shopimg);
        this.priceTv = (TextView) findViewById(R.id.priceTv);
        this.saleTv = (TextView) findViewById(R.id.saleTv);
        ImageView imageView = (ImageView) findViewById(R.id.closeimg);
        this.closeimg = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ShopBottom2Popwindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                ShopBottom2Popwindow.this.dismiss();
            }
        });
        this.buygoodsView = (TextView) findViewById(R.id.buygoodsView);
        this.recycleview = (RecyclerView) findViewById(R.id.recycleview);
        addItemData();
        if (TextUtils.isEmpty(this.thumil)) {
            this.shopimg.setImageResource(R.drawable.app_icon);
        } else {
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(this.thumil)).into(this.shopimg);
        }
        this.recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        BaseQuickAdapter<GoodsBean.DataBean.AttributesBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoodsBean.DataBean.AttributesBean, BaseViewHolder>(R.layout.layout_shop_curriculum_item) { // from class: com.psychiatrygarden.widget.ShopBottom2Popwindow.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, GoodsBean.DataBean.AttributesBean attributesBean) {
                TextView textView = (TextView) baseViewHolder.findView(R.id.labelTv);
                StackLayout stackLayout = (StackLayout) baseViewHolder.findView(R.id.laybelview);
                textView.setText(attributesBean.getAttr_name() + "");
                ShopBottom2Popwindow.this.addStack(attributesBean.getLabelList(), stackLayout);
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
        this.adapter.setList(this.meal);
        getPriceOrSaleNum();
        this.buygoodsView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.gh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16526c.lambda$onCreate$0(view);
            }
        });
    }

    public void skusMuthod(String value, ConfirmPurchaseClickIml confirmPurchaseClickIml) {
        List<GoodsBean.DataBean.SkusBean> list;
        if (TextUtils.isEmpty(value) || (list = this.skusBeans) == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.skusBeans.size(); i2++) {
            StringBuilder sb = new StringBuilder();
            if (this.skusBeans.get(i2).getAttr_values() != null && this.skusBeans.get(i2).getAttr_values().size() > 0) {
                for (int i3 = 0; i3 < this.skusBeans.get(i2).getAttr_values().size(); i3++) {
                    sb.append(this.skusBeans.get(i2).getAttr_values().get(i3));
                }
            }
            if (!TextUtils.isEmpty(sb.toString()) && TextUtils.equals(value, sb.toString())) {
                this.priceStr = this.skusBeans.get(i2).getNow_price();
                this.salenum = this.skusBeans.get(i2).getSales_volume();
                this.inventory = this.skusBeans.get(i2).getInventory();
                assignmentTxt();
                if (confirmPurchaseClickIml != null) {
                    confirmPurchaseClickIml.mConfirmPurchaseClickIml(this.skusBeans.get(i2));
                    return;
                }
                return;
            }
        }
    }
}
