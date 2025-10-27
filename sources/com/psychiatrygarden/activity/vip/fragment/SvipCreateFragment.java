package com.psychiatrygarden.activity.vip.fragment;

import android.content.res.Resources;
import android.graphics.Color;
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
import com.psychiatrygarden.activity.vip.pop.RulePopwindow;
import com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow;
import com.psychiatrygarden.activity.vip.pop.ValidPopwondow;
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
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.core.Arrays;
import v.h1;

/* loaded from: classes5.dex */
public class SvipCreateFragment extends BaseFragment {
    public BaseQuickAdapter<String, BaseViewHolder> adapterGZ;
    public BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO, BaseViewHolder> adapterPurchase;
    public CustomAliPlayerView aplibo;
    public LinearLayout btnlineview;
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
    public TextView sjantv;
    public BaseQuickAdapter<List<String>, BaseViewHolder> tablayoutAdapter;
    public RecyclerView tablayoutView;
    public TextView tqdbtv;
    public TextView tqxqtv;

    /* renamed from: tv, reason: collision with root package name */
    TextView f14046tv;
    public CardView videoviewid;
    public TextView xfantv;
    public TextView zxtqtv;
    public RequestOptions requestOptions = new RequestOptions().error(R.mipmap.icon_vip_small_default).placeholder(R.mipmap.icon_vip_small_default).skipMemoryCache(true);
    public int itemw = 0;
    public String upgrade_type = "";
    public String deduction_id = "";
    public String goods_id = "";
    public String price = "";
    public String quantity = "1";
    private String is_promotion = "";
    public int position = 0;
    public int isOpen = 0;
    public String upVipTxt = "";
    public int typew = 1;
    int itemCount = 3;
    private boolean isDeafult = true;
    private long mTempTime = 0;
    private long mTempEndTime = 0;
    private boolean isInited = false;
    private int mTempPosition = -1;
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                long jCurrentTimeMillis = (SvipCreateFragment.this.mTempEndTime * 1000) - System.currentTimeMillis();
                if (SvipCreateFragment.this.countDownTimer == null) {
                    SvipCreateFragment.this.countDownTimer = SvipCreateFragment.this.new TimerCount(jCurrentTimeMillis, 1000L);
                } else {
                    SvipCreateFragment.this.countDownTimer.cancel();
                }
                SvipCreateFragment.this.countDownTimer.start();
            }
        }
    };
    public List<GoodsBean.DataBean.MealBean> rMealBeans = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO, BaseViewHolder> {
        public AnonymousClass2(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(LinearLayout linearLayout) {
            if (SvipCreateFragment.this.mLyCoupons.getVisibility() == 8) {
                SvipCreateFragment.this.mLyCoupons.setVisibility(0);
            }
            int height = linearLayout.getHeight();
            int width = linearLayout.getWidth();
            LogUtils.e("recycler_height", "child_h====>" + height);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) SvipCreateFragment.this.mLyCoupons.getLayoutParams();
            layoutParams.topMargin = height + ScreenUtil.getPxByDp(((BaseFragment) SvipCreateFragment.this).mContext, 28);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) SvipCreateFragment.this.mImgTriangle.getLayoutParams();
            layoutParams2.setMarginStart(width / 2);
            SvipCreateFragment.this.mImgTriangle.setLayoutParams(layoutParams2);
            SvipCreateFragment.this.mLyCoupons.setLayoutParams(layoutParams);
            SvipCreateFragment.this.mHandler.sendEmptyMessage(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, int i2, LinearLayout linearLayout, View view, BaseViewHolder baseViewHolder, View view2) {
            if (goodsDTO.getCoupon() != null) {
                SvipCreateFragment.this.mTempPosition = i2;
                SvipCreateFragment.this.mTempCouponBean = goodsDTO.getCoupon();
                SvipCreateFragment.this.mTempEndTime = Long.parseLong(goodsDTO.getCoupon().getCoupon_end());
                if (SvipCreateFragment.this.countDownTimer != null) {
                    SvipCreateFragment.this.countDownTimer = null;
                }
                SvipCreateFragment.this.mHandler.sendEmptyMessage(1);
                SvipCreateFragment.this.isDeafult = false;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) SvipCreateFragment.this.mLyCoupons.getLayoutParams();
                int height = linearLayout.getHeight();
                int width = linearLayout.getWidth();
                int i3 = (i2 - 1) / 3;
                layoutParams.topMargin = (i3 * height) + height + ScreenUtil.getPxByDp(((BaseFragment) SvipCreateFragment.this).mContext, 28) + (ScreenUtil.getPxByDp(((BaseFragment) SvipCreateFragment.this).mContext, 16) * i3);
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) SvipCreateFragment.this.mImgTriangle.getLayoutParams();
                int i4 = i2 % 3;
                if (i4 == 1) {
                    layoutParams2.setMarginStart(width / 2);
                } else if (i4 == 2) {
                    layoutParams2.setMarginStart(width + (width / 2) + ScreenUtil.getPxByDp(((BaseFragment) SvipCreateFragment.this).mContext, 16));
                } else {
                    layoutParams2.setMarginStart((width * 2) + (width / 2) + (ScreenUtil.getPxByDp(((BaseFragment) SvipCreateFragment.this).mContext, 16) * 2));
                }
                SvipCreateFragment.this.mImgTriangle.setLayoutParams(layoutParams2);
                SvipCreateFragment.this.mLyCoupons.setLayoutParams(layoutParams);
                view.setVisibility(0);
                SvipCreateFragment.this.mTvCouponsName.setText(goodsDTO.getCoupon().getTitle());
                SvipCreateFragment.this.mLyCoupons.setVisibility(0);
            } else {
                SvipCreateFragment.this.mHandler.removeCallbacksAndMessages(null);
                SvipCreateFragment.this.mLyCoupons.setVisibility(8);
                view.setVisibility(8);
            }
            SvipCreateFragment.this.showBtnData(baseViewHolder.getBindingAdapterPosition(), goodsDTO);
            if (getData().size() > 0) {
                for (int i5 = 0; i5 < getData().size(); i5++) {
                    getData().get(i5).setIsSelect(0);
                }
            }
            goodsDTO.setIsSelect(goodsDTO.getIsSelect() != 1 ? 1 : 0);
            if (SvipCreateFragment.this.getActivity() instanceof MemberCenterActivity) {
                ((MemberCenterActivity) SvipCreateFragment.this.getActivity()).showBtnData(goodsDTO);
                ((MemberCenterActivity) SvipCreateFragment.this.getActivity()).setSvipGoodsposition(baseViewHolder.getBindingAdapterPosition());
            }
            notifyDataSetChanged();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder holder, final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO item) {
            final LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.lineview);
            linearLayout.setBackground(ContextCompat.getDrawable(((BaseFragment) SvipCreateFragment.this).mContext, R.drawable.select_svip_purchase));
            TextView textView = (TextView) holder.getView(R.id.year);
            TextView textView2 = (TextView) holder.getView(R.id.account);
            TextView textView3 = (TextView) holder.getView(R.id.average);
            TextView textView4 = (TextView) holder.getView(R.id.more);
            TextView textView5 = (TextView) holder.getView(R.id.label);
            TextView textView6 = (TextView) holder.getView(R.id.jinetv);
            final View view = holder.getView(R.id.empty_view);
            textView5.setSelected(false);
            textView4.setBackgroundResource(R.drawable.shape_svip_lable_bttom);
            textView4.setTextColor(Color.parseColor("#DD594A"));
            textView6.setTextColor(Color.parseColor("#DD594A"));
            textView2.setTextColor(Color.parseColor("#DD594A"));
            if (TextUtils.isEmpty(item.getLabel())) {
                textView5.setVisibility(8);
            } else {
                textView5.setVisibility(0);
                textView5.setText(item.getLabel());
            }
            textView.setText(item.getGoods_name());
            linearLayout.setSelected(item.getIsSelect() == 1);
            textView2.setText(item.getPrice());
            textView3.setText(item.getPer_month());
            if (TextUtils.isEmpty(item.getSaving())) {
                textView4.setVisibility(8);
            } else {
                textView4.setVisibility(0);
            }
            textView4.setText(item.getSaving());
            final int bindingAdapterPosition = holder.getBindingAdapterPosition() + 1;
            if (item.getCoupon() == null || item.getIsSelect() != 1) {
                view.setVisibility(8);
            } else {
                view.setVisibility(0);
            }
            if (SvipCreateFragment.this.isDeafult && bindingAdapterPosition == getData().size() - 1 && SvipCreateFragment.this.mTempCouponBean != null) {
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f14059c.lambda$convert$0(linearLayout);
                    }
                }, 200L);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f14061c.lambda$convert$1(item, bindingAdapterPosition, linearLayout, view, holder, view2);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> {
        final /* synthetic */ CentetLayoutManager val$centetLayoutManager;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int layoutResId, final CentetLayoutManager val$centetLayoutManager) {
            super(layoutResId);
            this.val$centetLayoutManager = val$centetLayoutManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, CentetLayoutManager centetLayoutManager) {
            centetLayoutManager.smoothScrollToPosition(SvipCreateFragment.this.privilegedetaillist, new RecyclerView.State(), baseViewHolder.getBindingAdapterPosition());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1() {
            SvipCreateFragment svipCreateFragment = SvipCreateFragment.this;
            svipCreateFragment.nesview.smoothScrollTo(0, svipCreateFragment.privilegedetaillist.getTop());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(final BaseViewHolder baseViewHolder, final CentetLayoutManager centetLayoutManager, View view) {
            SvipCreateFragment.this.nesview.post(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14067c.lambda$convert$0(baseViewHolder, centetLayoutManager);
                }
            });
            SvipCreateFragment.this.nesview.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vip.fragment.j
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14070c.lambda$convert$1();
                }
            }, 50L);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder helper, VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO item) {
            ImageView imageView = (ImageView) helper.getView(R.id.img);
            TextView textView = (TextView) helper.getView(R.id.name);
            RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.relbgvip);
            textView.setText(item.getTitle());
            RequestOptions requestOptionsSkipMemoryCache = new RequestOptions().error(R.mipmap.icon_vip_big_default).placeholder(R.mipmap.icon_vip_big_default).skipMemoryCache(true);
            relativeLayout.setBackground(ContextCompat.getDrawable(((BaseFragment) SvipCreateFragment.this).mContext, R.drawable.shape_svip_bg_dd594a));
            Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).apply((BaseRequestOptions<?>) requestOptionsSkipMemoryCache).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
            View view = helper.itemView;
            final CentetLayoutManager centetLayoutManager = this.val$centetLayoutManager;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f14071c.lambda$convert$2(helper, centetLayoutManager, view2);
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
            SvipCreateFragment.this.adapterPurchase.getData().get(SvipCreateFragment.this.mTempPosition).setCoupon(null);
            SvipCreateFragment.this.adapterPurchase.notifyDataSetChanged();
            SvipCreateFragment.this.mHandler.removeCallbacksAndMessages(null);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            SvipCreateFragment.this.updateTime(millisUntilFinished);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAssertsData$3(VipIntroductionBean vipIntroductionBean, View view) {
        OnlineServiceBean onlineServiceBean = new OnlineServiceBean();
        onlineServiceBean.setCs_type("wechat_enterprise_personal");
        onlineServiceBean.setWechat_corpid(vipIntroductionBean.getData().getWechat_corpid());
        onlineServiceBean.setContact(vipIntroductionBean.getData().getWechat_enterprise_url());
        CommonUtil.onlineService(getActivity(), onlineServiceBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAssertsData$4(VipIntroductionBean vipIntroductionBean, View view) {
        new XPopup.Builder(getActivity()).asCustom(new RulePopwindow(this.mContext, vipIntroductionBean.getData().getRule() + "")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$0(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        if (goodsDTO.getButton().get(0).getValid() != 1) {
            ToastUtil.shortToast(getActivity(), "此商品无法购买！");
            return;
        }
        this.upgrade_type = goodsDTO.getButton().get(0).getUpgrade_type() + "";
        this.deduction_id = goodsDTO.getButton().get(0).getDeduction_id() + "";
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        orderConfirmParams.setGoods_id(this.goods_id).setPrice(this.price).setUpgrade_type(this.upgrade_type).setDeduction_id(this.deduction_id).setGoodType("1").setIs_promotion("1".equals(this.is_promotion));
        OrderConfirmActivity.goToOrderConfirmEntrance(getActivity(), orderConfirmParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$1(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        if (goodsDTO.getButton().get(1).getValid() != 1) {
            new XPopup.Builder(getActivity()).asCustom(new ValidPopwondow(this.mContext)).show();
            return;
        }
        this.upgrade_type = goodsDTO.getButton().get(1).getUpgrade_type() + "";
        this.deduction_id = goodsDTO.getButton().get(1).getDeduction_id() + "";
        new XPopup.Builder(getActivity()).asCustom(new ToSvipPopWindow(this.mContext, this.upVipTxt, new ToSvipPopWindow.ToSvipClickIml() { // from class: com.psychiatrygarden.activity.vip.fragment.a
            @Override // com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow.ToSvipClickIml
            public final void mToSvipClick() {
                this.f14048a.toSViPPop();
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$2(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        this.upgrade_type = goodsDTO.getButton().get(0).getUpgrade_type() + "";
        this.deduction_id = goodsDTO.getButton().get(0).getDeduction_id() + "";
        if (goodsDTO.getButton().get(0).getValid() != 1) {
            ToastUtil.shortToast(getActivity(), "此商品无法购买！");
            return;
        }
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        orderConfirmParams.setGoods_id(this.goods_id).setPrice(this.price).setUpgrade_type(this.upgrade_type).setDeduction_id(this.deduction_id).setGoodType("1").setIs_promotion("1".equals(this.is_promotion));
        OrderConfirmActivity.goToOrderConfirmEntrance(getActivity(), orderConfirmParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toSViPPop() {
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        orderConfirmParams.setGoods_id(this.goods_id).setPrice(this.price).setUpgrade_type(this.upgrade_type).setDeduction_id(this.deduction_id).setGoodType("1").setIs_promotion("1".equals(this.is_promotion));
        OrderConfirmActivity.goToOrderConfirmEntrance(getActivity(), orderConfirmParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime(long millisUntilFinished) {
        this.mTempTime = millisUntilFinished;
        int i2 = (int) (millisUntilFinished / com.heytap.mcssdk.constant.a.f7141e);
        int i3 = (int) ((millisUntilFinished / 60000) % 60);
        int i4 = (int) ((millisUntilFinished / 1000) % 60);
        this.mTvCouponsTime.setText(i2 + ":" + i3 + ":" + i4 + "后失效");
        LogUtils.e("current_time", "转换的结果=" + i2 + ":" + i3 + ":" + i4);
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
        this.btnlineview = (LinearLayout) holder.get(R.id.btnlineview);
        this.sjantv = (TextView) holder.get(R.id.sjantv);
        this.xfantv = (TextView) holder.get(R.id.xfantv);
        this.zxtqtv = (TextView) holder.get(R.id.zxtqtv);
        this.tqxqtv = (TextView) holder.get(R.id.tqxqtv);
        this.tqdbtv = (TextView) holder.get(R.id.tqdbtv);
        this.mLyCoupons = (RelativeLayout) holder.get(R.id.ly_coupons);
        this.mImgTriangle = (ImageView) holder.get(R.id.img_triangle);
        this.mTvCouponsName = (TextView) holder.get(R.id.tv_coupons_name);
        this.mTvCouponsTime = (TextView) holder.get(R.id.tv_coupons_time);
        this.zxtqtv.setSelected(true);
        this.tqxqtv.setSelected(true);
        this.tqdbtv.setSelected(true);
        this.kefu3 = (TextView) holder.get(R.id.kefu3);
        this.videoviewid = (CardView) holder.get(R.id.videoviewid);
        this.tablayoutView = (RecyclerView) holder.get(R.id.tablayoutView);
        this.btview = (LinearLayout) holder.get(R.id.btview);
        this.buttomviewtv = (TextView) holder.get(R.id.buttomviewtv);
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
        this.f14046tv = (TextView) holder.get(R.id.f26077tv);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, getContext());
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.layout_vip_purchase);
        this.adapterPurchase = anonymousClass2;
        this.recymenvip.setAdapter(anonymousClass2);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(R.layout.layout_memdetal_item, centetLayoutManager);
        this.privilegeadapter = anonymousClass3;
        this.privilegelist.setAdapter(anonymousClass3);
        BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO, BaseViewHolder>(R.layout.vip_introduce_info) { // from class: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder2, VipIntroductionBean.DataDTO.VipDTO.PrivilegeDTO item) {
                TextView textView = (TextView) holder2.getView(R.id.qysmtv);
                TextView textView2 = (TextView) holder2.getView(R.id.sysmtv);
                textView.setSelected(true);
                textView2.setSelected(true);
                ImageView imageView = (ImageView) holder2.getView(R.id.iv_pop_vip_icon);
                ((ImageView) holder2.getView(R.id.icon_pop_vip_bg)).setImageResource(R.drawable.icon_pop_svip_bg);
                TextView textView3 = (TextView) holder2.getView(R.id.tv_pop_vip_title);
                TextView textView4 = (TextView) holder2.getView(R.id.tv_rights_description);
                TextView textView5 = (TextView) holder2.getView(R.id.tv_instructions);
                textView3.setText(item.getTitle());
                textView4.setText(item.getExplain());
                textView5.setText(item.getUsage());
                Glide.with(SvipCreateFragment.this.getActivity()).load((Object) GlideUtils.generateUrl(item.getRelated_icon())).apply((BaseRequestOptions<?>) SvipCreateFragment.this.requestOptions).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
            }
        };
        this.privilegedetailadapter = baseQuickAdapter;
        this.privilegedetaillist.setAdapter(baseQuickAdapter);
        BaseQuickAdapter<List<String>, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<List<String>, BaseViewHolder>(R.layout.layout_tablayout_list) { // from class: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull final BaseViewHolder holder2, List<String> item) {
                View view = holder2.getView(R.id.viewline);
                RecyclerView recyclerView4 = (RecyclerView) holder2.getView(R.id.childview);
                recyclerView4.setLayoutManager(new LinearLayoutManager(SvipCreateFragment.this.getActivity(), 0, false));
                recyclerView4.setHasFixedSize(true);
                recyclerView4.setNestedScrollingEnabled(false);
                if (holder2.getBindingAdapterPosition() == 0) {
                    recyclerView4.setBackgroundResource(R.drawable.shape_vip_gray);
                } else {
                    recyclerView4.setBackgroundColor(ContextCompat.getColor(((BaseFragment) SvipCreateFragment.this).mContext, R.color.pop_frame_backgroup_color));
                }
                view.setBackgroundColor(ContextCompat.getColor(((BaseFragment) SvipCreateFragment.this).mContext, R.color.fourth_line_backgroup_color));
                if (holder2.getBindingAdapterPosition() == getData().size() - 1) {
                    view.setVisibility(8);
                } else {
                    view.setVisibility(0);
                }
                recyclerView4.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_tab_child_view, item) { // from class: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment.5.1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(@NonNull BaseViewHolder holderview, String tvs) throws Resources.NotFoundException {
                        ((LinearLayout) holderview.getView(R.id.linetq)).getLayoutParams().width = SvipCreateFragment.this.itemw;
                        TextView textView = (TextView) holderview.getView(R.id.tqName);
                        if (holder2.getBindingAdapterPosition() == 0) {
                            textView.setTypeface(Typeface.DEFAULT_BOLD);
                        } else {
                            textView.setTypeface(Typeface.DEFAULT);
                        }
                        if ("0".equals(tvs)) {
                            CommonUtil.mDoDrawable(SvipCreateFragment.this.getActivity(), textView, R.drawable.vipw, 0);
                        } else if ("1".equals(tvs)) {
                            CommonUtil.mDoDrawable(SvipCreateFragment.this.getActivity(), textView, R.drawable.svipd, 0);
                        } else {
                            textView.setCompoundDrawables(null, null, null, null);
                            textView.setText(tvs);
                        }
                    }
                });
            }
        };
        this.tablayoutAdapter = baseQuickAdapter2;
        this.tablayoutView.setAdapter(baseQuickAdapter2);
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter3 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_vip_gz) { // from class: com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment.6
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
            if (vipIntroductionBean.getData().getSvip().getGoods().size() > 0) {
                vipIntroductionBean.getData().getSvip().getGoods().get(0).setIsSelect(1);
                showBtnData(0, vipIntroductionBean.getData().getSvip().getGoods().get(0));
                if (vipIntroductionBean.getData().getVip().getGoods().get(0).getCoupon() != null) {
                    RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem coupon = vipIntroductionBean.getData().getVip().getGoods().get(0).getCoupon();
                    this.mTempCouponBean = coupon;
                    this.mTvCouponsName.setText(coupon.getTitle());
                    this.mTempEndTime = Long.parseLong(this.mTempCouponBean.getEnd_time());
                }
            }
            this.upVipTxt = vipIntroductionBean.getData().getDiscount_description() + "";
            this.isInited = true;
            this.adapterPurchase.setList(vipIntroductionBean.getData().getSvip().getGoods());
            this.privilegeadapter.setList(vipIntroductionBean.getData().getSvip().getPrivilege());
            this.privilegedetailadapter.setList(vipIntroductionBean.getData().getSvip().getPrivilege());
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
            this.tablayoutAdapter.setList(vipIntroductionBean.getData().getRights_vs_table());
            this.kefu3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14055c.lambda$openAssertsData$3(vipIntroductionBean, view);
                }
            });
            if (TextUtils.isEmpty(vipIntroductionBean.getData().getSvip().getVideo().getUrl())) {
                this.videoviewid.setVisibility(8);
            } else {
                this.videoviewid.setVisibility(0);
                this.aplibo.setFullForbbtie(true);
                this.aplibo.setWatch_permission("1");
                this.aplibo.setTag("svip");
                this.aplibo.setWatch_permission("1");
                this.aplibo.setVideoTitle(vipIntroductionBean.getData().getVip().getVideo().getDescription());
                this.aplibo.setMp4Url(vipIntroductionBean.getData().getSvip().getVideo().getUrl());
                this.aplibo.initView(getActivity(), "", UserConfig.isCanPlayBy4g(this.mContext));
                this.aplibo.setMp4UrlSource();
                this.aplibo.showHideTitle(false);
                this.aplibo.mAliyunVodPlayerView.setCoverUri(vipIntroductionBean.getData().getSvip().getVideo().getCover());
            }
            this.f14046tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14057c.lambda$openAssertsData$4(vipIntroductionBean, view);
                }
            });
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        AliyunVodPlayerView aliyunVodPlayerView;
        super.setUserVisibleHint(isVisibleToUser);
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

    public void showBtnData(int p2, final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO item) {
        this.position = p2;
        this.goods_id = "" + item.getGoods_id();
        this.price = "" + item.getCent_price();
        this.quantity = "1";
        this.is_promotion = item.getIs_promotion();
        if (item.getButton().size() > 1) {
            this.btnlineview.setVisibility(0);
            this.buttomviewtv.setVisibility(8);
            this.xfantv.setText(item.getButton().get(0).getLabel());
            this.sjantv.setText(item.getButton().get(1).getLabel());
            this.xfantv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14049c.lambda$showBtnData$0(item, view);
                }
            });
            this.sjantv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14051c.lambda$showBtnData$1(item, view);
                }
            });
            return;
        }
        if (item.getButton().size() == 1) {
            this.btnlineview.setVisibility(8);
            this.buttomviewtv.setVisibility(0);
            this.buttomviewtv.setText(item.getButton().get(0).getLabel());
            this.buttomviewtv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.fragment.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14053c.lambda$showBtnData$2(item, view);
                }
            });
        }
    }

    public void onEventMainThread(EventBusMessage<Long> str) {
        CustomAliPlayerView customAliPlayerView;
        AliyunVodPlayerView aliyunVodPlayerView;
        try {
            if (!str.getKey().equals("svip") || (customAliPlayerView = this.aplibo) == null || (aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView) == null) {
                return;
            }
            aliyunVodPlayerView.seekTo(h1.a(str.getValueObj().longValue()));
            this.aplibo.hideRelPlayer();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
