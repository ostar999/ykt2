package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.xiaomi.mipush.sdk.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GoodsListAdapter extends BaseQuickAdapter<ShopInfoBean.DataBean, BaseViewHolder> {
    private Context context;
    private boolean homeRecommend;
    private boolean search;
    private String searchWord;

    public GoodsListAdapter(final Context context, List<ShopInfoBean.DataBean> list_StoreListBean, boolean homeRecommend) {
        super(R.layout.item_home_goods, list_StoreListBean);
        this.homeRecommend = homeRecommend;
        this.context = context;
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.adapter.q6
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f14910c.lambda$new$0(context, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getValidColor(String color, boolean fontColor) {
        if (TextUtils.isEmpty(color) || !color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")) {
            return fontColor ? "#F95843" : "#FFF1F0";
        }
        if (color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$") || color.charAt(0) != '#' || color.length() != 4) {
            return color;
        }
        return DictionaryFactory.SHARP + color.charAt(1) + color.charAt(1) + color.charAt(2) + color.charAt(2) + color.charAt(3) + color.charAt(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Context context, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (UserConfig.isLogin()) {
            context.startActivity(new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("detailType", 2).putExtra("goods_id", getItem(i2).getGoods_id()));
        } else {
            context.startActivity(new Intent(context, (Class<?>) LoginActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Context context, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (UserConfig.isLogin()) {
            context.startActivity(new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("detailType", 2).putExtra("goods_id", getItem(i2).getGoods_id()));
        } else {
            context.startActivity(new Intent(context, (Class<?>) LoginActivity.class));
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder viewHoder, ShopInfoBean.DataBean mStoreListBean) {
        viewHoder.setGone(R.id.line, viewHoder.getLayoutPosition() == getData().size() - 1);
        Glide.with(this.context).load((Object) GlideUtils.generateUrl(mStoreListBean.getGoods_thumbnail())).placeholder(new ColorDrawable(ContextCompat.getColor(viewHoder.getView(R.id.iv_store_img).getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into((ImageView) viewHoder.getView(R.id.iv_store_img));
        if (TextUtils.isEmpty(this.searchWord)) {
            viewHoder.setText(R.id.tv_store_title, mStoreListBean.getGoods_name());
        } else {
            String goods_name = mStoreListBean.getGoods_name();
            SpannableString spannableString = new SpannableString(goods_name);
            int color = Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#F95843" : "#B2575C");
            int iIndexOf = goods_name.indexOf(this.searchWord);
            while (iIndexOf >= 0) {
                spannableString.setSpan(new ForegroundColorSpan(color), iIndexOf, this.searchWord.length() + iIndexOf, 33);
                String str = this.searchWord;
                iIndexOf = goods_name.indexOf(str, iIndexOf + str.length());
            }
            viewHoder.setText(R.id.tv_store_title, spannableString);
        }
        String price = mStoreListBean.getPrice();
        String str2 = SkinManager.getCurrentSkinType(this.context) == 1 ? "#B2575C" : "#F95843";
        TextView textView = (TextView) viewHoder.getView(R.id.tv_stroke_line_price);
        TextView textView2 = (TextView) viewHoder.getView(R.id.tv_store_price);
        TextView textView3 = (TextView) viewHoder.getView(R.id.tv_store_num);
        textView.setVisibility((TextUtils.isEmpty(mStoreListBean.getOriginal_price()) || !TextUtils.isEmpty(mStoreListBean.getPrice_label())) ? 8 : 0);
        if (TextUtils.isEmpty(mStoreListBean.getOriginal_price())) {
            textView.setText("");
        } else {
            SpannableString spannableString2 = new SpannableString("¥" + mStoreListBean.getOriginal_price());
            spannableString2.setSpan(new StrikethroughSpan(), 0, spannableString2.length(), 18);
            textView.setText(spannableString2);
        }
        textView.setVisibility("0".equals(mStoreListBean.getOriginal_price()) ? 8 : 0);
        textView2.setText(CharacterParser.getSpannableColorSizew2(price, 1, price.length(), str2, 17));
        textView3.setText(mStoreListBean.getSales_volume());
        TypedArray typedArrayObtainStyledAttributes = this.context.obtainStyledAttributes(new int[]{"1".equals(mStoreListBean.getPrice_gray()) ? R.attr.forth_txt_color : R.attr.main_theme_color});
        int color2 = typedArrayObtainStyledAttributes.getColor(0, this.context.getColor(R.color.main_theme_color));
        textView2.setTextColor(color2);
        typedArrayObtainStyledAttributes.recycle();
        if (TextUtils.isEmpty(mStoreListBean.getPrice_label())) {
            String min_price = mStoreListBean.getMin_price();
            String max_price = mStoreListBean.getMax_price();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("¥");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 11)), 0, spannableStringBuilder.length(), 18);
            if (!TextUtils.isEmpty(min_price) && TextUtils.equals(min_price, max_price)) {
                if (TextUtils.equals(min_price, "0")) {
                    min_price = mStoreListBean.getPrice();
                }
                spannableStringBuilder.append((CharSequence) min_price);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), 1, spannableStringBuilder.length(), 18);
                textView2.setText(spannableStringBuilder);
            } else if (!TextUtils.isEmpty(min_price) && !TextUtils.equals(min_price, max_price)) {
                spannableStringBuilder.append((CharSequence) min_price);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), 1, spannableStringBuilder.length(), 34);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.append((CharSequence) Constants.WAVE_SEPARATOR);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 12)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.append((CharSequence) "¥");
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 11)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) mStoreListBean.getMax_price());
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), length, spannableStringBuilder.length(), 34);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), length, spannableStringBuilder.length(), 18);
                textView2.setText(spannableStringBuilder);
            } else if (!TextUtils.isEmpty(mStoreListBean.getPrice())) {
                int length2 = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) mStoreListBean.getPrice());
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), length2, spannableStringBuilder.length(), 34);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), length2, spannableStringBuilder.length(), 18);
                textView2.setText(spannableStringBuilder);
            }
        } else {
            textView2.setText(mStoreListBean.getPrice_label());
        }
        CharSequence text = textView.getText();
        CharSequence text2 = textView2.getText();
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(text2)) {
            if (TextUtils.equals(text.toString().replace("¥", ""), text2.toString().replace("¥", ""))) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
        }
        if (!TextUtils.isEmpty(mStoreListBean.getPrice_label()) || TextUtils.equals("0", mStoreListBean.getOriginal_price()) || "1".equals(mStoreListBean.getIs_stop()) || ("1".equals(mStoreListBean.getGoods_type()) && "0".equals(mStoreListBean.getInventory()))) {
            textView.setVisibility(8);
        }
        try {
            List<ShopInfoBean.DataBean.LabelBean> label = mStoreListBean.getLabel();
            ArrayList arrayList = new ArrayList();
            RecyclerView recyclerView = (RecyclerView) viewHoder.getView(R.id.rvLabels);
            if (label == null || label.size() <= 0) {
                recyclerView.setVisibility(8);
                return;
            }
            for (ShopInfoBean.DataBean.LabelBean labelBean : label) {
                if (!TextUtils.isEmpty(labelBean.getLabel())) {
                    labelBean.setGift("赠".equals(labelBean.getLabel()));
                    labelBean.setPromotion("促".equals(labelBean.getLabel()));
                    labelBean.setCoupon("券".equals(labelBean.getLabel()));
                    arrayList.add(labelBean);
                }
            }
            BaseQuickAdapter<ShopInfoBean.DataBean.LabelBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ShopInfoBean.DataBean.LabelBean, BaseViewHolder>(R.layout.item_sku_detail_tag) { // from class: com.psychiatrygarden.adapter.GoodsListAdapter.1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NonNull BaseViewHolder holder, ShopInfoBean.DataBean.LabelBean labelBean2) {
                    TextView textView4 = (TextView) holder.itemView;
                    textView4.setText(labelBean2.getLabel());
                    textView4.getPaint().setFakeBoldText(true);
                    textView4.setTextColor(Color.parseColor(GoodsListAdapter.this.getValidColor(labelBean2.getFont_color(), true)));
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) textView4.getLayoutParams();
                    ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(GoodsListAdapter.this.context, 8);
                    textView4.setLayoutParams(layoutParams);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setCornerRadius(SizeUtil.dp2px(GoodsListAdapter.this.context, 2) * 1.0f);
                    gradientDrawable.setColor(Color.parseColor(GoodsListAdapter.this.getValidColor(labelBean2.getColor(), false)));
                    if (!labelBean2.isPromotion() && !labelBean2.isCoupon() && !labelBean2.isGift()) {
                        gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 0.5f, GoodsListAdapter.this.context.getResources().getDisplayMetrics()), Color.parseColor(GoodsListAdapter.this.getValidColor(labelBean2.getFont_color(), true)));
                    }
                    textView4.setBackground(gradientDrawable);
                }
            };
            recyclerView.setAdapter(baseQuickAdapter);
            baseQuickAdapter.setList(arrayList);
            recyclerView.setVisibility(0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public GoodsListAdapter(final Context context, List<ShopInfoBean.DataBean> list_StoreListBean) {
        super(R.layout.adapter_store_list_new_home, list_StoreListBean);
        this.homeRecommend = false;
        this.context = context;
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.adapter.r6
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f14954c.lambda$new$1(context, baseQuickAdapter, view, i2);
            }
        });
    }
}
