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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.xiaomi.mipush.sdk.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class StoreListAdapter extends BaseAdapter {
    private Context context;
    private List<ShopInfoBean.DataBean> list_StoreListBean;
    private boolean search;
    private String searchWord;
    ViewHolder viewHoder = null;

    public static class ViewHolder {
        private ImageView iv_store_img;
        private RecyclerView rvLabels;
        private TextView tv_store_num;
        private TextView tv_store_price;
        private TextView tv_store_title;
        private TextView tv_stroke_line_price;
        private View view1;

        public ViewHolder(View view) {
            this.rvLabels = (RecyclerView) view.findViewById(R.id.rvLabels);
            this.iv_store_img = (ImageView) view.findViewById(R.id.iv_store_img);
            this.tv_store_title = (TextView) view.findViewById(R.id.tv_store_title);
            this.tv_store_price = (TextView) view.findViewById(R.id.tv_store_price);
            this.tv_store_num = (TextView) view.findViewById(R.id.tv_store_num);
            this.tv_stroke_line_price = (TextView) view.findViewById(R.id.tv_stroke_line_price);
            this.view1 = view.findViewById(R.id.view1);
        }
    }

    public StoreListAdapter(Context context, List<ShopInfoBean.DataBean> list_StoreListBean) {
        this.list_StoreListBean = list_StoreListBean;
        this.context = context;
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
    public /* synthetic */ void lambda$getView$0(ShopInfoBean.DataBean dataBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (UserConfig.isLogin()) {
            this.context.startActivity(new Intent(this.context, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("detailType", 2).putExtra("goods_id", dataBean.getGoods_id()));
        } else {
            this.context.startActivity(new Intent(this.context, (Class<?>) LoginActivity.class));
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list_StoreListBean.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.list_StoreListBean.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.adapter_store_list_new, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder(viewInflate);
            this.viewHoder = viewHolder;
            viewInflate.setTag(viewHolder);
            view = viewInflate;
        } else {
            this.viewHoder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        try {
            if (position == this.list_StoreListBean.size() - 1) {
                this.viewHoder.view1.setVisibility(8);
            } else {
                this.viewHoder.view1.setVisibility(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        final ShopInfoBean.DataBean dataBean = this.list_StoreListBean.get(position);
        Glide.with(this.context).load((Object) GlideUtils.generateUrl(dataBean.getGoods_thumbnail())).placeholder(new ColorDrawable(ContextCompat.getColor(this.viewHoder.iv_store_img.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.viewHoder.iv_store_img);
        if (TextUtils.isEmpty(this.searchWord)) {
            this.viewHoder.tv_store_title.setText(dataBean.getGoods_name());
        } else {
            String goods_name = dataBean.getGoods_name();
            SpannableString spannableString = new SpannableString(goods_name);
            int color = Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#F95843" : "#B2575C");
            int iIndexOf = goods_name.indexOf(this.searchWord);
            while (iIndexOf >= 0) {
                spannableString.setSpan(new ForegroundColorSpan(color), iIndexOf, this.searchWord.length() + iIndexOf, 33);
                String str = this.searchWord;
                iIndexOf = goods_name.indexOf(str, iIndexOf + str.length());
            }
            this.viewHoder.tv_store_title.setText(spannableString);
        }
        this.viewHoder.tv_store_title.getPaint().setFakeBoldText(true);
        String price = dataBean.getPrice();
        String str2 = SkinManager.getCurrentSkinType(this.context) == 1 ? "#B2575C" : "#F95843";
        String price2 = dataBean.getPrice();
        String original_price = dataBean.getOriginal_price();
        this.viewHoder.tv_stroke_line_price.setVisibility((TextUtils.isEmpty(original_price) || !TextUtils.isEmpty(dataBean.getPrice_label())) ? 8 : 0);
        if (TextUtils.isEmpty(original_price) || "0".equals(price2) || TextUtils.equals(original_price, price2) || "0".equals(original_price)) {
            this.viewHoder.tv_stroke_line_price.setText("");
        } else {
            SpannableString spannableString2 = new SpannableString("¥" + dataBean.getOriginal_price());
            spannableString2.setSpan(new StrikethroughSpan(), 0, spannableString2.length(), 18);
            this.viewHoder.tv_stroke_line_price.setText(spannableString2);
        }
        this.viewHoder.tv_stroke_line_price.setVisibility("0".equals(original_price) ? 8 : 0);
        this.viewHoder.tv_store_price.setText(CharacterParser.getSpannableColorSizew2(price, 1, price.length(), str2, 17));
        this.viewHoder.tv_store_num.setText(dataBean.getSales_volume());
        TypedArray typedArrayObtainStyledAttributes = this.context.obtainStyledAttributes(new int[]{"1".equals(dataBean.getPrice_gray()) ? R.attr.forth_txt_color : R.attr.main_theme_color});
        int color2 = typedArrayObtainStyledAttributes.getColor(0, this.context.getColor(R.color.main_theme_color));
        this.viewHoder.tv_store_price.setTextColor(color2);
        typedArrayObtainStyledAttributes.recycle();
        String min_price = dataBean.getMin_price();
        String max_price = dataBean.getMax_price();
        if (TextUtils.isEmpty(dataBean.getPrice_label())) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("¥");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 11)), 0, spannableStringBuilder.length(), 18);
            if (!TextUtils.isEmpty(min_price) && TextUtils.equals(min_price, max_price)) {
                spannableStringBuilder.append((CharSequence) (TextUtils.equals(min_price, "0") ? dataBean.getPrice() : min_price));
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), 1, spannableStringBuilder.length(), 18);
                this.viewHoder.tv_store_price.setText(spannableStringBuilder);
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
                spannableStringBuilder.append((CharSequence) dataBean.getMax_price());
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), length, spannableStringBuilder.length(), 34);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), length, spannableStringBuilder.length(), 18);
                this.viewHoder.tv_store_price.setText(spannableStringBuilder);
            } else if (!TextUtils.isEmpty(dataBean.getPrice())) {
                int length2 = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) dataBean.getPrice());
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), length2, spannableStringBuilder.length(), 34);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(this.context, 16)), length2, spannableStringBuilder.length(), 18);
                this.viewHoder.tv_store_price.setText(spannableStringBuilder);
            }
        } else {
            this.viewHoder.tv_store_price.setText(dataBean.getPrice_label());
        }
        CharSequence text = this.viewHoder.tv_stroke_line_price.getText();
        CharSequence text2 = this.viewHoder.tv_store_price.getText();
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(text2)) {
            if (TextUtils.equals(text.toString().replace("¥", ""), text2.toString().replace("¥", ""))) {
                this.viewHoder.tv_stroke_line_price.setVisibility(8);
            } else {
                this.viewHoder.tv_stroke_line_price.setVisibility(0);
            }
        }
        if ((!TextUtils.isEmpty(min_price) && !TextUtils.isEmpty(max_price)) || !TextUtils.isEmpty(dataBean.getPrice_label()) || TextUtils.equals("0", dataBean.getOriginal_price()) || "1".equals(dataBean.getIs_stop()) || ("1".equals(dataBean.getGoods_type()) && "0".equals(dataBean.getInventory()))) {
            this.viewHoder.tv_stroke_line_price.setVisibility(8);
        }
        if (!TextUtils.isEmpty(dataBean.getPrice()) && !TextUtils.isEmpty(dataBean.getOriginal_price()) && !dataBean.getPrice().equals("0")) {
            this.viewHoder.tv_stroke_line_price.setVisibility(0);
        }
        try {
            List<ShopInfoBean.DataBean.LabelBean> label = dataBean.getLabel();
            ArrayList arrayList = new ArrayList();
            if (label == null || label.size() <= 0) {
                this.viewHoder.rvLabels.setVisibility(8);
            } else {
                for (ShopInfoBean.DataBean.LabelBean labelBean : label) {
                    if (!TextUtils.isEmpty(labelBean.getLabel())) {
                        labelBean.setGift("赠".equals(labelBean.getLabel()));
                        labelBean.setPromotion("促".equals(labelBean.getLabel()));
                        labelBean.setCoupon("券".equals(labelBean.getLabel()));
                        arrayList.add(labelBean);
                    }
                }
                BaseQuickAdapter<ShopInfoBean.DataBean.LabelBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ShopInfoBean.DataBean.LabelBean, BaseViewHolder>(R.layout.item_sku_detail_tag) { // from class: com.psychiatrygarden.adapter.StoreListAdapter.1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(@NonNull BaseViewHolder holder, ShopInfoBean.DataBean.LabelBean labelBean2) {
                        TextView textView = (TextView) holder.itemView;
                        textView.setText(labelBean2.getLabel());
                        textView.getPaint().setFakeBoldText(true);
                        textView.setTextColor(Color.parseColor(StoreListAdapter.this.getValidColor(labelBean2.getFont_color(), true)));
                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) textView.getLayoutParams();
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(StoreListAdapter.this.context, 8);
                        textView.setLayoutParams(layoutParams);
                        GradientDrawable gradientDrawable = new GradientDrawable();
                        gradientDrawable.setCornerRadius(SizeUtil.dp2px(StoreListAdapter.this.context, 2) * 1.0f);
                        gradientDrawable.setColor(Color.parseColor(StoreListAdapter.this.getValidColor(labelBean2.getColor(), false)));
                        if (!labelBean2.isPromotion() && !labelBean2.isCoupon() && !labelBean2.isGift()) {
                            gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 0.5f, StoreListAdapter.this.context.getResources().getDisplayMetrics()), Color.parseColor(StoreListAdapter.this.getValidColor(labelBean2.getFont_color(), true)));
                        }
                        textView.setBackground(gradientDrawable);
                    }
                };
                this.viewHoder.rvLabels.setAdapter(baseQuickAdapter);
                baseQuickAdapter.setList(arrayList);
                this.viewHoder.rvLabels.setVisibility(0);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.te
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15047c.lambda$getView$0(dataBean, view2);
            }
        });
        return view;
    }

    public void setSearchWord(String word) {
        this.searchWord = word;
    }

    public StoreListAdapter(Context context, List<ShopInfoBean.DataBean> list_StoreListBean, boolean search) {
        this.list_StoreListBean = list_StoreListBean;
        this.context = context;
        this.search = search;
    }
}
