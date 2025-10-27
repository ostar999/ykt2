package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class ShopBottomPopwindow extends BottomPopupView {
    public TextView buygoodsView;
    public ImageView closeimg;
    public StackLayout laybelview;
    public TextView liceneview;
    public List<GoodsBean.DataBean.MealBean> meal;
    public TextView priceTv;
    public ShopBottomClickIml sShopBottomClickIml;
    public TextView saleTv;
    public RoundedImageView shopimg;
    public String statement;

    public interface ShopBottomClickIml {
        void mShopBottomClickIml(GoodsBean.DataBean.MealBean mealBean);
    }

    public ShopBottomPopwindow(@NonNull @NotNull Context context, List<GoodsBean.DataBean.MealBean> meal, String statement, ShopBottomClickIml sShopBottomClickIml) {
        super(context);
        this.meal = meal == null ? new ArrayList<>() : meal;
        this.statement = statement;
        this.sShopBottomClickIml = sShopBottomClickIml;
    }

    private void addStack(final List<GoodsBean.DataBean.MealBean> meal) {
        this.laybelview.removeAllViews();
        for (final int i2 = 0; i2 < meal.size(); i2++) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_shop_curriculum_label_item, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.laeblTv);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.tjimg);
            if ("1".equals(meal.get(i2).getRecommend())) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            final RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.relview);
            if (i2 == 0) {
                relativeLayout.setSelected(true);
            }
            textView.setText(meal.get(i2).getGoods_name() + "");
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.jh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws NumberFormatException {
                    this.f16623c.lambda$addStack$2(relativeLayout, meal, i2, view);
                }
            });
            this.laybelview.addView(viewInflate);
        }
    }

    private void initLabelView() {
        if (this.laybelview != null) {
            for (int i2 = 0; i2 < this.laybelview.getChildCount(); i2++) {
                if (this.laybelview.getChildAt(i2) instanceof RelativeLayout) {
                    ((RelativeLayout) this.laybelview.getChildAt(i2)).setSelected(false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addStack$2(RelativeLayout relativeLayout, List list, int i2, View view) throws NumberFormatException {
        initLabelView();
        relativeLayout.setSelected(true);
        assignmentData((GoodsBean.DataBean.MealBean) list.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$assignmentData$1(int i2, String str, GoodsBean.DataBean.MealBean mealBean, View view) {
        if (i2 <= 0) {
            ToastUtils.showShort("暂时无货");
        } else if (str.equals("0")) {
            ToastUtils.showShort("已购买");
        } else {
            this.sShopBottomClickIml.mShopBottomClickIml(mealBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    public void assignmentData(final GoodsBean.DataBean.MealBean mealBean) throws NumberFormatException {
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(mealBean.getGoods_thumbnail())).into(this.shopimg);
        this.priceTv.setText(mealBean.getNow_price());
        this.saleTv.setText(mealBean.getSales_volume());
        final int i2 = Integer.parseInt(mealBean.getInventory());
        final String buy_permission = mealBean.getBuy_permission();
        if (i2 <= 0) {
            this.buygoodsView.setText("暂时无货");
            this.buygoodsView.setBackgroundResource(R.drawable.shape_bound_gray);
        } else if (buy_permission.equals("0")) {
            this.buygoodsView.setText("已购买");
            this.buygoodsView.setBackgroundResource(R.drawable.shape_bound_gray);
        } else {
            this.buygoodsView.setText("立即购买");
            this.buygoodsView.setBackgroundResource(R.drawable.shape_round_red);
        }
        this.buygoodsView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ih
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16593c.lambda$assignmentData$1(i2, buy_permission, mealBean, view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_shop_buttom_view;
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

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws NumberFormatException {
        super.onCreate();
        this.shopimg = (RoundedImageView) findViewById(R.id.shopimg);
        this.priceTv = (TextView) findViewById(R.id.priceTv);
        this.saleTv = (TextView) findViewById(R.id.saleTv);
        this.closeimg = (ImageView) findViewById(R.id.closeimg);
        this.laybelview = (StackLayout) findViewById(R.id.laybelview);
        this.buygoodsView = (TextView) findViewById(R.id.buygoodsView);
        TextView textView = (TextView) findViewById(R.id.liceneview);
        this.liceneview = textView;
        textView.setText("".equals(this.statement) ? "商品分类" : this.statement);
        this.closeimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.kh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16655c.lambda$onCreate$0(view);
            }
        });
        List<GoodsBean.DataBean.MealBean> list = this.meal;
        if (list == null || list.size() <= 0) {
            return;
        }
        assignmentData(this.meal.get(0));
        addStack(this.meal);
    }
}
