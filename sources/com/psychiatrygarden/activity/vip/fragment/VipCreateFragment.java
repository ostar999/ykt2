package com.psychiatrygarden.activity.vip.fragment;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.order.OrderConfirmActivity;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.bean.VipIntroductionBean;
import com.psychiatrygarden.activity.vip.pop.GoBuyVipPopwindow;
import com.psychiatrygarden.activity.vip.pop.RulePopwindow;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.OrderConfirmParams;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CentetLayoutManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.core.Arrays;
import v.h1;

/* loaded from: classes5.dex */
public class VipCreateFragment extends BaseFragment {
    public BaseQuickAdapter<String, BaseViewHolder> adapterGZ;
    public BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO, BaseViewHolder> adapterPurchase;
    public CustomAliPlayerView aplibo;
    public LinearLayout btview;
    public TextView buttomviewtv;
    private TimerCount countDownTimer;
    public View gzlineview;
    public RecyclerView gzrecyid;
    public TextView hygutv;
    public boolean isDestroyed;
    public TextView kefu3;
    private ImageView mImgTriangle;
    private RelativeLayout mLyCoupons;
    private RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem mTempCouponBean;
    private TextView mTvCouponsName;
    private TextView mTvCouponsTime;
    public NestedScrollView nesview;
    public BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> privilegeadapter;
    public BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> privilegedetailadapter;
    public RecyclerView privilegedetaillist;
    public RecyclerView privilegelist;
    public RecyclerView recymenvip;
    public BaseQuickAdapter<List<String>, BaseViewHolder> tablayoutAdapter;
    public RecyclerView tablayoutView;

    /* renamed from: tv, reason: collision with root package name */
    public TextView f14047tv;
    public CardView videoviewid;
    public RequestOptions requestOptions = new RequestOptions().error(R.mipmap.icon_vip_small_default).placeholder(R.mipmap.icon_vip_small_default).skipMemoryCache(true);
    public int itemw = 0;
    public String upgrade_type = "";
    public String deduction_id = "";
    public String goods_id = "";
    public String price = "";
    public String quantity = "1";
    private String is_promotion = "";
    public int typew = 1;
    int itemCount = 3;
    private boolean isDeafult = true;
    private boolean isInited = false;
    private long mTempTime = 0;
    private long mTempEndTime = 0;
    private int mTempPosition = -1;
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                long jCurrentTimeMillis = (VipCreateFragment.this.mTempEndTime * 1000) - System.currentTimeMillis();
                if (VipCreateFragment.this.countDownTimer == null) {
                    VipCreateFragment.this.countDownTimer = VipCreateFragment.this.new TimerCount(jCurrentTimeMillis, 1000L);
                } else {
                    VipCreateFragment.this.countDownTimer.cancel();
                }
                VipCreateFragment.this.countDownTimer.start();
            }
        }
    };
    public List<GoodsBean.DataBean.MealBean> rMealBeans = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO, BaseViewHolder> {
        public AnonymousClass2(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(LinearLayout linearLayout) {
            if (VipCreateFragment.this.mLyCoupons.getVisibility() == 8) {
                VipCreateFragment.this.mLyCoupons.setVisibility(0);
            }
            int height = linearLayout.getHeight();
            int width = linearLayout.getWidth();
            LogUtils.e("recycler_height", "child_h====>" + height);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) VipCreateFragment.this.mLyCoupons.getLayoutParams();
            layoutParams.topMargin = height + ScreenUtil.getPxByDp(((BaseFragment) VipCreateFragment.this).mContext, 28);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) VipCreateFragment.this.mImgTriangle.getLayoutParams();
            layoutParams2.setMarginStart(width / 2);
            VipCreateFragment.this.mImgTriangle.setLayoutParams(layoutParams2);
            VipCreateFragment.this.mLyCoupons.setLayoutParams(layoutParams);
            VipCreateFragment.this.mHandler.sendEmptyMessage(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, int i2, LinearLayout linearLayout, View view, BaseViewHolder baseViewHolder, View view2) {
            if (goodsDTO.getCoupon() != null) {
                VipCreateFragment.this.mTempPosition = i2;
                VipCreateFragment.this.mTempCouponBean = goodsDTO.getCoupon();
                VipCreateFragment.this.mTempEndTime = Long.parseLong(goodsDTO.getCoupon().getCoupon_end());
                if (VipCreateFragment.this.countDownTimer != null) {
                    VipCreateFragment.this.countDownTimer.cancel();
                    VipCreateFragment.this.countDownTimer = null;
                    VipCreateFragment.this.mHandler.removeCallbacksAndMessages(null);
                }
                VipCreateFragment.this.mHandler.sendEmptyMessage(1);
                VipCreateFragment.this.isDeafult = false;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) VipCreateFragment.this.mLyCoupons.getLayoutParams();
                int height = linearLayout.getHeight();
                int width = linearLayout.getWidth();
                int i3 = (i2 - 1) / 3;
                layoutParams.topMargin = (i3 * height) + height + ScreenUtil.getPxByDp(((BaseFragment) VipCreateFragment.this).mContext, 28) + (ScreenUtil.getPxByDp(((BaseFragment) VipCreateFragment.this).mContext, 16) * i3);
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) VipCreateFragment.this.mImgTriangle.getLayoutParams();
                int i4 = i2 % 3;
                if (i4 == 1) {
                    layoutParams2.setMarginStart(width / 2);
                } else if (i4 == 2) {
                    layoutParams2.setMarginStart(width + (width / 2) + ScreenUtil.getPxByDp(((BaseFragment) VipCreateFragment.this).mContext, 16));
                } else {
                    layoutParams2.setMarginStart((width * 2) + (width / 2) + (ScreenUtil.getPxByDp(((BaseFragment) VipCreateFragment.this).mContext, 16) * 2));
                }
                VipCreateFragment.this.mImgTriangle.setLayoutParams(layoutParams2);
                VipCreateFragment.this.mLyCoupons.setLayoutParams(layoutParams);
                view.setVisibility(0);
                VipCreateFragment.this.mTvCouponsName.setText(goodsDTO.getCoupon().getTitle());
                VipCreateFragment.this.mLyCoupons.setVisibility(0);
            } else {
                if (VipCreateFragment.this.countDownTimer != null) {
                    VipCreateFragment.this.countDownTimer.cancel();
                    VipCreateFragment.this.countDownTimer = null;
                }
                VipCreateFragment.this.mHandler.removeCallbacksAndMessages(null);
                VipCreateFragment.this.mLyCoupons.setVisibility(8);
                view.setVisibility(8);
            }
            VipCreateFragment.this.buttomviewtv.setText(goodsDTO.getButton().get(0).getLabel());
            VipCreateFragment.this.upgrade_type = goodsDTO.getButton().get(0).getUpgrade_type() + "";
            VipCreateFragment.this.deduction_id = goodsDTO.getButton().get(0).getDeduction_id() + "";
            VipCreateFragment.this.goods_id = "" + goodsDTO.getGoods_id();
            VipCreateFragment.this.price = "" + goodsDTO.getCent_price();
            VipCreateFragment vipCreateFragment = VipCreateFragment.this;
            vipCreateFragment.quantity = "1";
            vipCreateFragment.is_promotion = goodsDTO.getIs_promotion();
            if (getData() != null && getData().size() > 0) {
                for (int i5 = 0; i5 < getData().size(); i5++) {
                    getData().get(i5).setIsSelect(0);
                }
            }
            goodsDTO.setIsSelect(goodsDTO.getIsSelect() == 1 ? 0 : 1);
            if (VipCreateFragment.this.getActivity() instanceof MemberCenterActivity) {
                ((MemberCenterActivity) VipCreateFragment.this.getActivity()).showBtnData(goodsDTO);
                ((MemberCenterActivity) VipCreateFragment.this.getActivity()).setVipGoodsPosition(baseViewHolder.getBindingAdapterPosition());
            }
            notifyDataSetChanged();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder holder, final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO item) {
            final LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.lineview);
            TextView textView = (TextView) holder.getView(R.id.year);
            TextView textView2 = (TextView) holder.getView(R.id.account);
            TextView textView3 = (TextView) holder.getView(R.id.average);
            TextView textView4 = (TextView) holder.getView(R.id.more);
            TextView textView5 = (TextView) holder.getView(R.id.label);
            final View view = holder.getView(R.id.empty_view);
            if (TextUtils.isEmpty(item.getLabel())) {
                textView5.setVisibility(8);
            } else {
                textView5.setVisibility(0);
                textView5.setText(item.getLabel() + "");
            }
            textView5.setSelected(false);
            textView.setText(item.getGoods_name() + "");
            if (item.getIsSelect() == 1) {
                linearLayout.setSelected(true);
            } else {
                linearLayout.setSelected(false);
            }
            textView2.setText(item.getPrice() + "");
            textView3.setText(item.getPer_month() + "");
            if (TextUtils.isEmpty(item.getSaving())) {
                textView4.setVisibility(8);
            } else {
                textView4.setVisibility(0);
            }
            textView4.setText(item.getSaving() + "");
            final int bindingAdapterPosition = holder.getBindingAdapterPosition() + 1;
            if (item.getCoupon() == null || item.getIsSelect() != 1) {
                view.setVisibility(8);
            } else {
                view.setVisibility(0);
            }
            if (VipCreateFragment.this.isDeafult && bindingAdapterPosition == getData().size() - 1 && VipCreateFragment.this.mTempCouponBean != null) {
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.m
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f14076c.lambda$convert$0(linearLayout);
                    }
                }, 200L);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f14078c.lambda$convert$1(item, bindingAdapterPosition, linearLayout, view, holder, view2);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> {
        final /* synthetic */ CentetLayoutManager val$centetLayoutManager;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int layoutResId, final CentetLayoutManager val$centetLayoutManager) {
            super(layoutResId);
            this.val$centetLayoutManager = val$centetLayoutManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, CentetLayoutManager centetLayoutManager) {
            centetLayoutManager.smoothScrollToPosition(VipCreateFragment.this.privilegedetaillist, new RecyclerView.State(), baseViewHolder.getBindingAdapterPosition());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1() {
            VipCreateFragment vipCreateFragment = VipCreateFragment.this;
            vipCreateFragment.nesview.smoothScrollTo(0, vipCreateFragment.privilegedetaillist.getTop());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(final BaseViewHolder baseViewHolder, final CentetLayoutManager centetLayoutManager, View view) {
            VipCreateFragment.this.nesview.post(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.o
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14084c.lambda$convert$0(baseViewHolder, centetLayoutManager);
                }
            });
            VipCreateFragment.this.nesview.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.p
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14087c.lambda$convert$1();
                }
            }, 50L);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder helper, VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO item) {
            ImageView imageView = (ImageView) helper.getView(R.id.img);
            TextView textView = (TextView) helper.getView(R.id.name);
            RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.relbgvip);
            textView.setText(item.getTitle());
            relativeLayout.setBackground(ContextCompat.getDrawable(((BaseFragment) VipCreateFragment.this).mContext, R.drawable.shape_vip_bg_dbab55));
            Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.mipmap.icon_vip_big_default).placeholder(R.mipmap.icon_vip_big_default).skipMemoryCache(true)).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
            View view = helper.itemView;
            final CentetLayoutManager centetLayoutManager = this.val$centetLayoutManager;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f14088c.lambda$convert$2(helper, centetLayoutManager, view2);
                }
            });
        }
    }

    public class TimerCount extends CountDownTimer {
        public TimerCount(long millisInfuture, long countDownInterval) {
            super(millisInfuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            VipCreateFragment.this.adapterPurchase.getData().get(VipCreateFragment.this.mTempPosition).setCoupon(null);
            VipCreateFragment.this.adapterPurchase.notifyDataSetChanged();
            VipCreateFragment.this.mHandler.removeCallbacksAndMessages(null);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            VipCreateFragment.this.updateTime(millisUntilFinished);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAssertsData$0(VipIntroductionBean vipIntroductionBean, View view) {
        if (1 == vipIntroductionBean.getData().getSvip().getIs_vip()) {
            new XPopup.Builder(getActivity()).asCustom(new GoBuyVipPopwindow(getActivity(), new GoBuyVipPopwindow.GoBuyClickIml() { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.7
                @Override // com.psychiatrygarden.activity.vip.pop.GoBuyVipPopwindow.GoBuyClickIml
                public void mGoBuyClick(int type) {
                    if (type != 0 && type == 1) {
                        VipCreateFragment.this.CombMethod();
                    }
                }
            }, "", "", "", "vip")).show();
        } else {
            CombMethod();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime(long millisUntilFinished) {
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        this.mTempTime = millisUntilFinished;
        int i2 = (int) (millisUntilFinished / com.heytap.mcssdk.constant.a.f7141e);
        int i3 = (int) ((millisUntilFinished / 60000) % 60);
        int i4 = (int) ((millisUntilFinished / 1000) % 60);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        } else {
            strValueOf = String.valueOf(i2);
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        } else {
            strValueOf2 = String.valueOf(i3);
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        } else {
            strValueOf3 = String.valueOf(i4);
        }
        this.mTvCouponsTime.setText(strValueOf + ":" + strValueOf2 + ":" + strValueOf3 + "后失效");
    }

    public void CombMethod() {
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        orderConfirmParams.setGoods_id(this.goods_id).setPrice(this.price).setUpgrade_type(this.upgrade_type).setDeduction_id(this.deduction_id).setGoodType("1").setIs_promotion("1".equals(this.is_promotion));
        OrderConfirmActivity.goToOrderConfirmEntrance(getActivity(), orderConfirmParams);
    }

    public void changeDesTroy() {
        CustomAliPlayerView customAliPlayerView;
        if (this.isDestroyed || (customAliPlayerView = this.aplibo) == null) {
            return;
        }
        customAliPlayerView.onDestory();
        this.isDestroyed = true;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_vip_create;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        int i2 = getArguments().getInt("typew", 1);
        this.typew = i2;
        if (i2 == 1) {
            this.itemCount = 3;
        } else {
            this.itemCount = 4;
        }
        this.itemw = (ScreenUtil.getScreenWidth(getActivity()) - (CommonUtil.dip2px(getActivity(), 18.0f) * 2)) / this.itemCount;
        this.gzlineview = holder.get(R.id.gzlineview);
        this.hygutv = (TextView) holder.get(R.id.hygutv);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.gzrecyid);
        this.gzrecyid = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.nesview = (NestedScrollView) holder.get(R.id.nesview);
        this.kefu3 = (TextView) holder.get(R.id.kefu3);
        this.videoviewid = (CardView) holder.get(R.id.videoviewid);
        this.tablayoutView = (RecyclerView) holder.get(R.id.tablayoutView);
        this.btview = (LinearLayout) holder.get(R.id.btview);
        this.buttomviewtv = (TextView) holder.get(R.id.buttomviewtv);
        this.mLyCoupons = (RelativeLayout) holder.get(R.id.ly_coupons);
        this.mImgTriangle = (ImageView) holder.get(R.id.img_triangle);
        this.mTvCouponsName = (TextView) holder.get(R.id.tv_coupons_name);
        this.mTvCouponsTime = (TextView) holder.get(R.id.tv_coupons_time);
        this.tablayoutView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.tablayoutView.setHasFixedSize(true);
        this.tablayoutView.setNestedScrollingEnabled(false);
        this.privilegedetaillist = (RecyclerView) holder.get(R.id.privilegedetaillist);
        CentetLayoutManager centetLayoutManager = new CentetLayoutManager(getActivity(), 0, false);
        this.privilegedetaillist.setLayoutManager(centetLayoutManager);
        this.privilegedetaillist.setHasFixedSize(true);
        this.privilegedetaillist.setNestedScrollingEnabled(false);
        RecyclerView recyclerView2 = (RecyclerView) holder.get(R.id.privilegelist);
        this.privilegelist = recyclerView2;
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        this.privilegelist.setHasFixedSize(true);
        this.privilegelist.setNestedScrollingEnabled(false);
        this.aplibo = (CustomAliPlayerView) holder.get(R.id.aplibo);
        RecyclerView recyclerView3 = (RecyclerView) holder.get(R.id.recymenvip);
        this.recymenvip = recyclerView3;
        recyclerView3.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        this.recymenvip.setHasFixedSize(true);
        this.recymenvip.setNestedScrollingEnabled(false);
        this.f14047tv = (TextView) holder.get(R.id.f26077tv);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, getContext());
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.layout_vip_purchase);
        this.adapterPurchase = anonymousClass2;
        this.recymenvip.setAdapter(anonymousClass2);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(R.layout.layout_memdetal_item, centetLayoutManager);
        this.privilegeadapter = anonymousClass3;
        this.privilegelist.setAdapter(anonymousClass3);
        BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder>(R.layout.vip_introduce_info) { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder2, VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO item) {
                ImageView imageView = (ImageView) holder2.getView(R.id.iv_pop_vip_icon);
                TextView textView = (TextView) holder2.getView(R.id.tv_pop_vip_title);
                TextView textView2 = (TextView) holder2.getView(R.id.tv_rights_description);
                TextView textView3 = (TextView) holder2.getView(R.id.tv_instructions);
                textView.setText(item.getTitle() + "");
                textView2.setText(item.getExplain() + "");
                textView3.setText(item.getUsage());
                Glide.with(VipCreateFragment.this.getActivity()).load((Object) GlideUtils.generateUrl(item.getRelated_icon())).apply((BaseRequestOptions<?>) VipCreateFragment.this.requestOptions).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
            }
        };
        this.privilegedetailadapter = baseQuickAdapter;
        this.privilegedetaillist.setAdapter(baseQuickAdapter);
        BaseQuickAdapter<List<String>, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<List<String>, BaseViewHolder>(R.layout.layout_tablayout_list) { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull final BaseViewHolder holder2, List<String> item) {
                View view = holder2.getView(R.id.viewline);
                RecyclerView recyclerView4 = (RecyclerView) holder2.getView(R.id.childview);
                recyclerView4.setLayoutManager(new LinearLayoutManager(VipCreateFragment.this.getActivity(), 0, false));
                recyclerView4.setHasFixedSize(true);
                recyclerView4.setNestedScrollingEnabled(false);
                if (holder2.getBindingAdapterPosition() == 0) {
                    recyclerView4.setBackgroundResource(R.drawable.shape_vip_gray);
                } else {
                    recyclerView4.setBackgroundColor(VipCreateFragment.this.getActivity().getResources().getColor(R.color.pop_frame_backgroup_color));
                }
                view.setBackgroundColor(VipCreateFragment.this.getActivity().getResources().getColor(R.color.fourth_line_backgroup_color));
                if (holder2.getBindingAdapterPosition() == getData().size() - 1) {
                    view.setVisibility(8);
                } else {
                    view.setVisibility(0);
                }
                recyclerView4.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_tab_child_view, item) { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.5.1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(@NonNull BaseViewHolder holderview, String tvs) throws Resources.NotFoundException {
                        ((LinearLayout) holderview.getView(R.id.linetq)).getLayoutParams().width = VipCreateFragment.this.itemw;
                        TextView textView = (TextView) holderview.getView(R.id.tqName);
                        if (holder2.getBindingAdapterPosition() == 0) {
                            textView.setTypeface(Typeface.DEFAULT_BOLD);
                        } else {
                            textView.setTypeface(Typeface.DEFAULT);
                        }
                        if ("0".equals(tvs)) {
                            CommonUtil.mDoDrawable(VipCreateFragment.this.getActivity(), textView, R.drawable.vipw, 0);
                            return;
                        }
                        if ("1".equals(tvs)) {
                            CommonUtil.mDoDrawable(VipCreateFragment.this.getActivity(), textView, R.drawable.vipd, 0);
                            return;
                        }
                        textView.setCompoundDrawables(null, null, null, null);
                        textView.setText(tvs + "");
                    }
                });
            }
        };
        this.tablayoutAdapter = baseQuickAdapter2;
        this.tablayoutView.setAdapter(baseQuickAdapter2);
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter3 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_vip_gz) { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder2, String item) {
                ((TextView) holder2.getView(R.id.contenttv)).setText(item);
            }
        };
        this.adapterGZ = baseQuickAdapter3;
        this.gzrecyid.setAdapter(baseQuickAdapter3);
        openAssertsData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        changeDesTroy();
        TimerCount timerCount = this.countDownTimer;
        if (timerCount != null) {
            timerCount.cancel();
            this.countDownTimer = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        LogUtils.e("is_hidden", "页面是否hidden=" + hidden);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (getActivity().isFinishing()) {
            changeDesTroy();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        CustomAliPlayerView customAliPlayerView = this.aplibo;
        if (customAliPlayerView != null) {
            customAliPlayerView.onPause();
        }
    }

    public void openAssertsData() {
        final VipIntroductionBean vipIntroductionBean = (VipIntroductionBean) getArguments().getSerializable("vipdata");
        if (vipIntroductionBean.getCode() == 200) {
            if (vipIntroductionBean.getData().getVip().getGoods().size() > 0) {
                vipIntroductionBean.getData().getVip().getGoods().get(0).setIsSelect(1);
                this.buttomviewtv.setText(vipIntroductionBean.getData().getVip().getGoods().get(0).getButton().get(0).getLabel());
                this.upgrade_type = vipIntroductionBean.getData().getVip().getGoods().get(0).getButton().get(0).getUpgrade_type() + "";
                this.deduction_id = vipIntroductionBean.getData().getVip().getGoods().get(0).getButton().get(0).getDeduction_id() + "";
                this.goods_id = "" + vipIntroductionBean.getData().getVip().getGoods().get(0).getGoods_id();
                this.price = "" + vipIntroductionBean.getData().getVip().getGoods().get(0).getCent_price();
                this.quantity = "1";
                this.is_promotion = vipIntroductionBean.getData().getVip().getGoods().get(0).getIs_promotion();
                if (vipIntroductionBean.getData().getVip().getGoods().get(0).getCoupon() != null) {
                    RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem coupon = vipIntroductionBean.getData().getVip().getGoods().get(0).getCoupon();
                    this.mTempCouponBean = coupon;
                    this.mTvCouponsName.setText(coupon.getTitle());
                    this.mTempEndTime = Long.parseLong(this.mTempCouponBean.getEnd_time());
                }
            }
            this.adapterPurchase.setList(vipIntroductionBean.getData().getVip().getGoods());
            this.isInited = true;
            this.privilegeadapter.setList(vipIntroductionBean.getData().getVip().getPrivilege());
            this.privilegedetailadapter.setList(vipIntroductionBean.getData().getVip().getPrivilege());
            if (TextUtils.isEmpty(vipIntroductionBean.getData().getRule())) {
                this.hygutv.setVisibility(8);
                this.gzrecyid.setVisibility(8);
                this.gzlineview.setVisibility(8);
            } else {
                this.gzlineview.setVisibility(0);
                this.hygutv.setVisibility(0);
                this.gzrecyid.setVisibility(0);
                this.adapterGZ.setList(Arrays.asList(vipIntroductionBean.getData().getRule().split("\n")));
            }
            this.buttomviewtv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14074c.lambda$openAssertsData$0(vipIntroductionBean, view);
                }
            });
            this.tablayoutAdapter.setList(vipIntroductionBean.getData().getRights_vs_table());
            this.kefu3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    OnlineServiceBean onlineServiceBean = new OnlineServiceBean();
                    onlineServiceBean.setCs_type("wechat_enterprise_personal");
                    onlineServiceBean.setWechat_corpid(vipIntroductionBean.getData().getWechat_corpid());
                    onlineServiceBean.setContact(vipIntroductionBean.getData().getWechat_enterprise_url());
                    CommonUtil.onlineService(VipCreateFragment.this.getActivity(), onlineServiceBean);
                }
            });
            if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getVideo().getUrl())) {
                this.videoviewid.setVisibility(8);
            } else {
                this.videoviewid.setVisibility(0);
                this.aplibo.setFullForbbtie(true);
                this.aplibo.setWatch_permission("1");
                this.aplibo.setTag("vip");
                this.aplibo.setWatch_permission("1");
                this.aplibo.setVideoTitle(vipIntroductionBean.getData().getVip().getVideo().getDescription());
                this.aplibo.setMp4Url(vipIntroductionBean.getData().getVip().getVideo().getUrl());
                this.aplibo.initView(getActivity(), "", UserConfig.isCanPlayBy4g(this.mContext));
                this.aplibo.setMp4UrlSource();
                this.aplibo.showHideTitle(false);
                this.aplibo.mAliyunVodPlayerView.setCoverUri(vipIntroductionBean.getData().getVip().getVideo().getCover());
            }
            this.f14047tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.VipCreateFragment.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    new XPopup.Builder(VipCreateFragment.this.getActivity()).asCustom(new RulePopwindow(VipCreateFragment.this.getActivity(), vipIntroductionBean.getData().getRule() + "")).show();
                }
            });
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        AliyunVodPlayerView aliyunVodPlayerView;
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("is_hidden", "页面是否可见=" + isVisibleToUser);
        if (isVisibleToUser) {
            if (!this.isInited || this.mTempCouponBean == null) {
                return;
            }
            this.mHandler.sendEmptyMessage(1);
            return;
        }
        CustomAliPlayerView customAliPlayerView = this.aplibo;
        if (customAliPlayerView != null && (aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView) != null && aliyunVodPlayerView.isPlaying()) {
            this.aplibo.onPause();
        }
        if (this.isInited) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.countDownTimer = null;
        }
    }

    public void onEventMainThread(EventBusMessage<Long> str) {
        CustomAliPlayerView customAliPlayerView;
        AliyunVodPlayerView aliyunVodPlayerView;
        try {
            if (!str.getKey().equals("vip") || (customAliPlayerView = this.aplibo) == null || (aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView) == null) {
                return;
            }
            aliyunVodPlayerView.seekTo(h1.a(str.getValueObj().longValue()));
            this.aplibo.hideRelPlayer();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
