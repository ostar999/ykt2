package com.psychiatrygarden.activity.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.ExoPlayer;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.TryAndSeeBean;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TagAdapter;
import com.psychiatrygarden.utils.TagFlowLayout;
import com.psychiatrygarden.utils.UILoader;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CommodityController implements ViewPager.OnPageChangeListener, OnPageClickListener {
    public BaseQuickAdapter<TryAndSeeBean.DataDTO, BaseViewHolder> adapter;
    public Context context;
    public RecyclerView coursevideo;
    public GoodsBean.DataBean data;
    public BaseViewHolder holder;
    private InfiniteIndicator infinite_anim_circle;
    public LinearLayout line_content;
    private List<Object> pageListImgs;
    private ArrayList<Page> pageViews;
    public TextView pagenum2;
    TagFlowLayout tagView;
    public WebView tv_content_des;
    public TextView tv_money1;
    int tv_num_goumai = 1;
    public TextView tv_title;
    TextView tv_youfei;
    TextView tv_zhekoujia;
    public CustomAliPlayerView videoview;

    /* renamed from: com.psychiatrygarden.activity.purchase.CommodityController$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<TryAndSeeBean.DataDTO, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TryAndSeeBean.DataDTO dataDTO, View view) {
            if (TextUtils.isEmpty(dataDTO.getVid())) {
                return;
            }
            CommodityController commodityController = CommodityController.this;
            if (commodityController.videoview == null || commodityController.infinite_anim_circle == null) {
                return;
            }
            CommodityController.this.videoview.setVisibility(0);
            CommodityController.this.infinite_anim_circle.setVisibility(8);
            if (CommodityController.this.videoview.getVids().equals(dataDTO.getVid())) {
                return;
            }
            CommodityController.this.videoview.setVids(dataDTO.getVid());
            CommonUtil.getCourseDownAk(dataDTO.getVid(), CommodityController.this.videoview, true);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, final TryAndSeeBean.DataDTO dataDTO) {
            ((TextView) baseViewHolder.getView(R.id.tryvid)).setText(dataDTO.getTitle());
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13559c.lambda$convert$0(dataDTO, view);
                }
            });
        }
    }

    public CommodityController(Context context, BaseViewHolder baseViewHolder, GoodsBean.DataBean data) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        this.holder = baseViewHolder;
        this.data = data;
        this.context = context;
        initView();
    }

    public void initView() throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        this.pageListImgs = new ArrayList();
        this.tv_num_goumai = 1;
        this.videoview = (CustomAliPlayerView) this.holder.getView(R.id.videoview);
        RecyclerView recyclerView = (RecyclerView) this.holder.getView(R.id.coursevideo);
        this.coursevideo = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.videoview.setExpire_str("");
        this.videoview.setFree_watch_time(0L);
        this.videoview.setWatch_permission("1");
        this.videoview.setSeeDuration("0");
        this.videoview.setFullForbbtie(true);
        CustomAliPlayerView customAliPlayerView = this.videoview;
        Context context = this.context;
        customAliPlayerView.initView(context, "", UserConfig.isCanPlayBy4g(context));
        this.videoview.showHideTitle(false);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, this.context);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_curriculum_try_video);
        this.adapter = anonymousClass1;
        this.coursevideo.setAdapter(anonymousClass1);
        this.pagenum2 = (TextView) this.holder.getView(R.id.pagenum2);
        this.tv_zhekoujia = (TextView) this.holder.getView(R.id.tv_zhekoujia);
        this.tv_youfei = (TextView) this.holder.getView(R.id.tv_youfei);
        this.tv_title = (TextView) this.holder.getView(R.id.tv_title);
        this.tv_content_des = (WebView) this.holder.getView(R.id.tv_content_des);
        this.tv_money1 = (TextView) this.holder.getView(R.id.tv_money1);
        this.line_content = (LinearLayout) this.holder.getView(R.id.line_content);
        this.tagView = (TagFlowLayout) this.holder.getView(R.id.test);
        this.pageViews = new ArrayList<>();
        InfiniteIndicator infiniteIndicator = (InfiniteIndicator) this.holder.getView(R.id.infinite_anim_circle);
        this.infinite_anim_circle = infiniteIndicator;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) infiniteIndicator.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenWidth((Activity) this.context);
        this.infinite_anim_circle.setLayoutParams(layoutParams);
        this.infinite_anim_circle.init(new IndicatorConfiguration.Builder().imageLoader(new UILoader().getImageLoader(this.context)).isStopWhileTouch(true).onPageChangeListener(this).onPageClickListener(this).direction(0).internal(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS).position(IndicatorConfiguration.IndicatorPosition.Left_Bottom).mThemeSkin(SkinManager.getCurrentSkinType(this.context) != 0 ? 1 : 0).isDrawIndicator(false).build());
    }

    @Override // cn.lightsky.infiniteindicator.OnPageClickListener
    public void onPageClick(int position, Page page) {
        List<Object> list = this.pageListImgs;
        if (list == null || list.size() <= 0) {
            return;
        }
        ImageViewerPopupViewCustom longPressListener = new ImageViewerPopupViewCustom(this.context).setSrcView(null, position).setImageUrls(this.pageListImgs).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(null).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
        longPressListener.popupInfo = new PopupInfo();
        longPressListener.show();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        try {
            this.pagenum2.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(position + 1), Integer.valueOf(this.pageViews.size())));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void showGoodsDetileView(GoodsBean mgBeans) {
        try {
            if (mgBeans.getCode().equals("200")) {
                if ("2".equals(mgBeans.getData().getCat_id())) {
                    this.tv_title.setText(CommonUtil.setSpanImg(this.context, SkinManager.getCurrentSkinType(this.context) == 1 ? R.drawable.dataimg_night : R.drawable.dataimg, 0, 1, 33, "&  " + mgBeans.getData().getGoods_name()));
                } else {
                    this.tv_title.setText(mgBeans.getData().getGoods_name());
                }
                this.tv_content_des.loadDataWithBaseURL(null, CommonUtil.getHtmlData(this.context, mgBeans.getData().getDescription()), "text/html; charset=utf-8", "utf-8", null);
                try {
                    String now_price = mgBeans.getData().getNow_price();
                    if (!now_price.contains("¥")) {
                        now_price = "¥" + mgBeans.getData().getNow_price();
                    }
                    this.tv_money1.setText(CharacterParser.getSpannableColorSizew2(now_price, 1, now_price.length(), SkinManager.getCurrentSkinType(this.context) == 1 ? "#B2575C" : "#DD594A", 17));
                } catch (Exception unused) {
                    this.tv_money1.setText(mgBeans.getData().getNow_price());
                }
                this.tv_zhekoujia.setText(mgBeans.getData().getOriginal_price());
                this.tv_zhekoujia.getPaint().setFlags(16);
                this.tv_youfei.setText(mgBeans.getData().getSales_volume());
                if (mgBeans.getData().getLabel() == null || mgBeans.getData().getLabel().size() <= 0) {
                    this.line_content.setVisibility(8);
                } else {
                    this.tagView.setAdapter(new TagAdapter<String>(mgBeans.getData().getLabel()) { // from class: com.psychiatrygarden.activity.purchase.CommodityController.2
                        @Override // com.psychiatrygarden.utils.TagAdapter
                        public View getView(FlowLayout parent, int position, String mealBeans) {
                            TextView textView = (TextView) LayoutInflater.from(CommodityController.this.context).inflate(R.layout.tag_txt, (ViewGroup) CommodityController.this.tagView, false);
                            textView.setText(mealBeans);
                            return textView;
                        }
                    });
                    this.line_content.setVisibility(8);
                }
                if (mgBeans.getData().getGoods_img() != null) {
                    for (int i2 = 0; i2 < mgBeans.getData().getGoods_img().size(); i2++) {
                        try {
                            if (TextUtils.equals(mgBeans.getData().getGoods_video().getImg(), mgBeans.getData().getGoods_img().get(i2))) {
                                this.pageViews.add(new Page("video", mgBeans.getData().getGoods_img().get(i2)));
                            } else {
                                this.pageViews.add(new Page("img", mgBeans.getData().getGoods_img().get(i2)));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        this.pageListImgs.add(mgBeans.getData().getGoods_img().get(i2));
                    }
                }
                this.infinite_anim_circle.notifyDataChange(this.pageViews);
                this.pagenum2.setText(String.format(Locale.CHINA, "1/%d", Integer.valueOf(this.pageViews.size())));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void showTryAndSee(List<TryAndSeeBean.DataDTO> data) {
        BaseQuickAdapter<TryAndSeeBean.DataDTO, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setList(data);
        }
    }
}
