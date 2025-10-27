package com.easefun.polyv.livecloudclass.modules.pagemenu.commodity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo.PLVCommodityUiState;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVRoundRectGradientTextView;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundRectLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.socket.event.commodity.PLVProductContentBean;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCCommodityPushLayout extends FrameLayout implements View.OnClickListener {
    private ImageView commodityCoverIv;
    private PLVRoundRectLayout commodityCoverLy;
    private TextView commodityCoverNumberTv;
    private ImageView commodityDialogCloseIv;
    private ImageView commodityEnterIv;
    private LinearLayout commodityFeatureTagLl;
    private LinearLayout commodityNameLl;
    private PLVRoundRectGradientTextView commodityNameNumberTv;
    private TextView commodityNameTv;
    private TextView commodityProductDescTv;
    private TextView commodityRealPriceTv;
    private TextView commoditySrcPriceTv;
    private final PLVCommodityViewModel commodityViewModel;
    private boolean isLandscape;
    private boolean isNeedShow;

    @Nullable
    private PLVProductContentBean productContentBean;
    private boolean showOnLandscape;
    private boolean showOnPortrait;

    public PLVLCCommodityPushLayout(@NonNull Context context) {
        this(context, null);
    }

    private void addFeatureTagView(LinearLayout linearLayout, String str) {
        int i2 = -2;
        linearLayout.addView(new PLVRoundRectGradientTextView(getContext(), str) { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.commodity.PLVLCCommodityPushLayout.3
            final /* synthetic */ String val$tag;

            {
                this.val$tag = str;
                setText(str);
                setTextSize(10.0f);
                setTextColor(PLVFormatUtils.parseColor("#FF8F11"));
                setSingleLine(true);
                setPadding(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(2.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(2.0f));
                updateBackgroundColor(PLVFormatUtils.parseColor("#14FF8F11"));
                updateRadius(ConvertUtils.dp2px(4.0f), ConvertUtils.dp2px(4.0f), ConvertUtils.dp2px(4.0f), ConvertUtils.dp2px(4.0f));
            }
        }, new LinearLayout.LayoutParams(i2, i2) { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.commodity.PLVLCCommodityPushLayout.4
            {
                setMargins(0, 0, ConvertUtils.dp2px(8.0f), 0);
            }
        });
    }

    private void bindCover(PLVProductContentBean pLVProductContentBean) {
        if (!(!TextUtils.isEmpty(pLVProductContentBean.getCover()))) {
            this.commodityCoverLy.setVisibility(8);
            return;
        }
        this.commodityCoverLy.setVisibility(0);
        this.commodityCoverNumberTv.setText(String.valueOf(pLVProductContentBean.getShowId()));
        PLVImageLoader.getInstance().loadImage(pLVProductContentBean.getCover(), this.commodityCoverIv);
    }

    private void bindEnterIcon(PLVProductContentBean pLVProductContentBean) {
        this.commodityEnterIv.setImageResource(TextUtils.isEmpty(getProductLink(pLVProductContentBean)) ? R.drawable.plvlc_commodity_enter_disabled : R.drawable.plvlc_commodity_enter);
    }

    private void bindPrice(PLVProductContentBean pLVProductContentBean) {
        String str;
        this.commoditySrcPriceTv.setVisibility(pLVProductContentBean.isRealPriceEqualsPrice() || pLVProductContentBean.isSrcPriceZero() || pLVProductContentBean.isFinanceProduct() ? 8 : 0);
        if (!pLVProductContentBean.isNormalProduct()) {
            if (pLVProductContentBean.isFinanceProduct()) {
                this.commodityRealPriceTv.setText(pLVProductContentBean.getYield());
                return;
            }
            return;
        }
        this.commoditySrcPriceTv.setText("¥" + pLVProductContentBean.getPrice());
        TextView textView = this.commodityRealPriceTv;
        if (pLVProductContentBean.isFreeForPay()) {
            str = "免费";
        } else {
            str = "¥" + pLVProductContentBean.getRealPrice();
        }
        textView.setText(str);
    }

    private void bindProductName(PLVProductContentBean pLVProductContentBean) {
        boolean z2 = !TextUtils.isEmpty(pLVProductContentBean.getCover());
        this.commodityNameNumberTv.setText(String.valueOf(pLVProductContentBean.getShowId()));
        this.commodityNameNumberTv.setVisibility(z2 ? 8 : 0);
        this.commodityNameTv.setText(pLVProductContentBean.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public void checkUpdateVisibility() {
        boolean z2 = this.isNeedShow;
        boolean z3 = this.isLandscape;
        setVisibility(z2 && ((!z3 && this.showOnPortrait) || (z3 && this.showOnLandscape)) ? 0 : 8);
    }

    private boolean enterCommodity() {
        String productLink = getProductLink(this.productContentBean);
        if (TextUtils.isEmpty(productLink)) {
            PLVToast.Builder.context(getContext()).setText(R.string.plv_commodity_toast_empty_link).show();
            return false;
        }
        PLVLCCommodityDetailActivity.start(getContext(), productLink);
        return true;
    }

    private void findView() {
        this.commodityCoverLy = (PLVRoundRectLayout) findViewById(R.id.plvlc_commodity_cover_ly);
        this.commodityCoverIv = (ImageView) findViewById(R.id.plvlc_commodity_cover_iv);
        this.commodityCoverNumberTv = (TextView) findViewById(R.id.plvlc_commodity_cover_number_tv);
        this.commodityNameLl = (LinearLayout) findViewById(R.id.plvlc_commodity_name_ll);
        this.commodityNameNumberTv = (PLVRoundRectGradientTextView) findViewById(R.id.plvlc_commodity_name_number_tv);
        this.commodityNameTv = (TextView) findViewById(R.id.plvlc_commodity_name_tv);
        this.commodityFeatureTagLl = (LinearLayout) findViewById(R.id.plvlc_commodity_feature_tag_ll);
        this.commodityProductDescTv = (TextView) findViewById(R.id.plvlc_commodity_product_desc_tv);
        this.commodityRealPriceTv = (TextView) findViewById(R.id.plvlc_commodity_real_price_tv);
        this.commoditySrcPriceTv = (TextView) findViewById(R.id.plvlc_commodity_src_price_tv);
        this.commodityDialogCloseIv = (ImageView) findViewById(R.id.plvlc_commodity_dialog_close_iv);
        this.commodityEnterIv = (ImageView) findViewById(R.id.plvlc_commodity_enter_iv);
        this.commodityDialogCloseIv.setOnClickListener(this);
        this.commodityEnterIv.setOnClickListener(this);
        setOnClickListener(this);
    }

    @Nullable
    private static String getProductLink(@Nullable PLVProductContentBean pLVProductContentBean) {
        if (pLVProductContentBean == null) {
            return null;
        }
        return pLVProductContentBean.getLinkByType();
    }

    private void initView(@Nullable AttributeSet attributeSet) {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_page_menu_commodity_push_layout, (ViewGroup) this, true);
        findView();
        parseAttrs(attributeSet);
        TextView textView = this.commoditySrcPriceTv;
        textView.setPaintFlags(textView.getPaintFlags() | 16);
        observeCommodityViewModel();
    }

    private void observeCommodityViewModel() {
        this.commodityViewModel.getCommodityUiStateLiveData().observe((LifecycleOwner) getContext(), new Observer<PLVCommodityUiState>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.commodity.PLVLCCommodityPushLayout.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVCommodityUiState pLVCommodityUiState) {
                if (pLVCommodityUiState == null) {
                    return;
                }
                PLVProductContentBean pLVProductContentBean = pLVCommodityUiState.productContentBeanPushToShow;
                if (pLVProductContentBean != null) {
                    PLVLCCommodityPushLayout.this.updateProduct(pLVProductContentBean);
                }
                PLVLCCommodityPushLayout.this.isNeedShow = pLVCommodityUiState.productContentBeanPushToShow != null;
                PLVLCCommodityPushLayout.this.checkUpdateVisibility();
            }
        });
    }

    private void parseAttrs(@Nullable AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.PLVLCCommodityPushLayout);
        this.showOnPortrait = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVLCCommodityPushLayout_plv_show_on_portrait, this.showOnPortrait);
        this.showOnLandscape = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVLCCommodityPushLayout_plv_show_on_landscape, this.showOnLandscape);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void parseProductFeatureTag(PLVProductContentBean pLVProductContentBean) {
        if (!(!TextUtils.isEmpty(pLVProductContentBean.getFeatures()))) {
            this.commodityFeatureTagLl.setVisibility(8);
            return;
        }
        List<String> list = (List) new Gson().fromJson(pLVProductContentBean.getFeatures(), new TypeToken<List<String>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.commodity.PLVLCCommodityPushLayout.2
        }.getType());
        if (list == null || list.size() <= 0) {
            this.commodityFeatureTagLl.setVisibility(8);
            return;
        }
        ArrayList arrayList = new ArrayList(2);
        for (String str : list) {
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(str);
                if (arrayList.size() >= 2) {
                    break;
                }
            }
        }
        if (arrayList.isEmpty()) {
            this.commodityFeatureTagLl.setVisibility(8);
            return;
        }
        this.commodityFeatureTagLl.setVisibility(0);
        this.commodityFeatureTagLl.removeAllViews();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            addFeatureTagView(this.commodityFeatureTagLl, (String) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public void updateProduct(PLVProductContentBean pLVProductContentBean) {
        this.productContentBean = pLVProductContentBean;
        bindCover(pLVProductContentBean);
        bindProductName(pLVProductContentBean);
        this.commodityProductDescTv.setVisibility(TextUtils.isEmpty(pLVProductContentBean.getProductDesc()) ^ true ? 0 : 8);
        this.commodityProductDescTv.setText(pLVProductContentBean.getProductDesc());
        parseProductFeatureTag(pLVProductContentBean);
        bindPrice(pLVProductContentBean);
        bindEnterIcon(pLVProductContentBean);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == this.commodityDialogCloseIv.getId()) {
            this.isNeedShow = false;
            this.commodityViewModel.onCloseProductPush();
            checkUpdateVisibility();
        } else if ((id == this.commodityEnterIv.getId() || view == this) && enterCommodity()) {
            this.isNeedShow = false;
            this.commodityViewModel.onCloseProductPush();
            checkUpdateVisibility();
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isLandscape = configuration.orientation == 2;
        checkUpdateVisibility();
    }

    public PLVLCCommodityPushLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCCommodityPushLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.commodityViewModel = (PLVCommodityViewModel) PLVDependManager.getInstance().get(PLVCommodityViewModel.class);
        this.showOnPortrait = true;
        this.showOnLandscape = true;
        this.isNeedShow = false;
        this.isLandscape = false;
        initView(attributeSet);
    }
}
