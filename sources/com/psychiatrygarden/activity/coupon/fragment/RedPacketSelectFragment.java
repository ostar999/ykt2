package com.psychiatrygarden.activity.coupon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.coupon.adapter.RedPacketAdapter;
import com.psychiatrygarden.bean.CouponData;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 02\u00020\u00012\u00020\u0002:\u00010B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J&\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0010\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&H\u0016J\u001a\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u001e\u0010*\u001a\u00020\u001a2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001a\u0010,\u001a\u00020\u001a2\b\b\u0002\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u00020.R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/RedPacketSelectFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/scwang/smartrefresh/layout/listener/OnRefreshLoadMoreListener;", "()V", "available", "", "defSelectId", "", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "goodId", "goodType", "isPromotion", "mAdapter", "Lcom/psychiatrygarden/activity/coupon/adapter/RedPacketAdapter;", "mListData", "", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "mRecycler", "Landroidx/recyclerview/widget/RecyclerView;", "mRefresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "orderAmount", "present", "Lcom/psychiatrygarden/activity/coupon/fragment/CouponAndRedPacketSelectDialogFragment;", "loadRedPacketList", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onLoadMore", "refreshLayout", "Lcom/scwang/smartrefresh/layout/api/RefreshLayout;", "onRefresh", "onViewCreated", "view", "setDefSelect", "list", "setListNum", "availableNum", "", "noAvailableNum", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRedPacketSelectFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RedPacketSelectFragment.kt\ncom/psychiatrygarden/activity/coupon/fragment/RedPacketSelectFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,243:1\n1864#2,3:244\n*S KotlinDebug\n*F\n+ 1 RedPacketSelectFragment.kt\ncom/psychiatrygarden/activity/coupon/fragment/RedPacketSelectFragment\n*L\n217#1:244,3\n*E\n"})
/* loaded from: classes5.dex */
public final class RedPacketSelectFragment extends Fragment implements OnRefreshLoadMoreListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private boolean available;
    private CustomEmptyView emptyView;
    private String goodId;
    private String goodType;
    private boolean isPromotion;
    private RedPacketAdapter mAdapter;
    private RecyclerView mRecycler;
    private SmartRefreshLayout mRefresh;
    private CouponAndRedPacketSelectDialogFragment present;

    @NotNull
    private final List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> mListData = new ArrayList();

    @NotNull
    private String orderAmount = "0";

    @NotNull
    private String defSelectId = "";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0006¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/RedPacketSelectFragment$Companion;", "", "()V", "newInstance", "Lcom/psychiatrygarden/activity/coupon/fragment/RedPacketSelectFragment;", "available", "", "goodId", "", "goodType", "orderAmount", "defSelectId", "isPromotion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final RedPacketSelectFragment newInstance(boolean available, @NotNull String goodId, @NotNull String goodType, @NotNull String orderAmount, @NotNull String defSelectId, boolean isPromotion) {
            Intrinsics.checkNotNullParameter(goodId, "goodId");
            Intrinsics.checkNotNullParameter(goodType, "goodType");
            Intrinsics.checkNotNullParameter(orderAmount, "orderAmount");
            Intrinsics.checkNotNullParameter(defSelectId, "defSelectId");
            Bundle bundle = new Bundle();
            bundle.putBoolean("available", available);
            bundle.putString("goodId", goodId);
            bundle.putString("goodType", goodType);
            bundle.putString("orderAmount", orderAmount);
            bundle.putString("defSelectId", defSelectId);
            RedPacketSelectFragment redPacketSelectFragment = new RedPacketSelectFragment();
            redPacketSelectFragment.setArguments(bundle);
            return redPacketSelectFragment;
        }
    }

    private final void loadRedPacketList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("is_available", this.available ? "1" : "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("order_price", this.orderAmount);
        ajaxParams.put("is_promotion", this.isPromotion ? "1" : "0");
        YJYHttpUtils.getMethod(getActivity(), NetworkRequestsURL.orderRedPacketListNew, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.loadRedPacketList.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = RedPacketSelectFragment.this.mRefresh;
                CustomEmptyView customEmptyView = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh();
                RedPacketAdapter redPacketAdapter = RedPacketSelectFragment.this.mAdapter;
                if (redPacketAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    redPacketAdapter = null;
                }
                redPacketAdapter.setList(new ArrayList());
                RedPacketAdapter redPacketAdapter2 = RedPacketSelectFragment.this.mAdapter;
                if (redPacketAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    redPacketAdapter2 = null;
                }
                CustomEmptyView customEmptyView2 = RedPacketSelectFragment.this.emptyView;
                if (customEmptyView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView = customEmptyView2;
                }
                redPacketAdapter2.setEmptyView(customEmptyView);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String s2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                super.onSuccess((AnonymousClass1) s2);
                SmartRefreshLayout smartRefreshLayout = RedPacketSelectFragment.this.mRefresh;
                CustomEmptyView customEmptyView = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("code");
                    String strOptString2 = jSONObject.optString("data");
                    if (!Intrinsics.areEqual(strOptString, "200")) {
                        SmartRefreshLayout smartRefreshLayout2 = RedPacketSelectFragment.this.mRefresh;
                        if (smartRefreshLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
                            smartRefreshLayout2 = null;
                        }
                        smartRefreshLayout2.finishRefresh();
                        RedPacketAdapter redPacketAdapter = RedPacketSelectFragment.this.mAdapter;
                        if (redPacketAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            redPacketAdapter = null;
                        }
                        redPacketAdapter.setList(new ArrayList());
                        RedPacketAdapter redPacketAdapter2 = RedPacketSelectFragment.this.mAdapter;
                        if (redPacketAdapter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            redPacketAdapter2 = null;
                        }
                        CustomEmptyView customEmptyView2 = RedPacketSelectFragment.this.emptyView;
                        if (customEmptyView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                            customEmptyView2 = null;
                        }
                        redPacketAdapter2.setEmptyView(customEmptyView2);
                        return;
                    }
                    CouponData couponData = (CouponData) new Gson().fromJson(strOptString2.toString(), CouponData.class);
                    List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list = couponData.getList();
                    RedPacketSelectFragment.this.mListData.clear();
                    RedPacketSelectFragment.this.setListNum(Integer.parseInt(couponData.getAvailable_count()), Integer.parseInt(couponData.getNot_available_count()));
                    List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list2 = list;
                    if (!(list2 == null || list2.isEmpty())) {
                        RedPacketSelectFragment.this.mListData.addAll(list);
                        RedPacketSelectFragment redPacketSelectFragment = RedPacketSelectFragment.this;
                        redPacketSelectFragment.setDefSelect(redPacketSelectFragment.mListData, RedPacketSelectFragment.this.defSelectId);
                        RedPacketAdapter redPacketAdapter3 = RedPacketSelectFragment.this.mAdapter;
                        if (redPacketAdapter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            redPacketAdapter3 = null;
                        }
                        redPacketAdapter3.setList(RedPacketSelectFragment.this.mListData);
                        return;
                    }
                    RedPacketAdapter redPacketAdapter4 = RedPacketSelectFragment.this.mAdapter;
                    if (redPacketAdapter4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        redPacketAdapter4 = null;
                    }
                    redPacketAdapter4.setList(new ArrayList());
                    RedPacketAdapter redPacketAdapter5 = RedPacketSelectFragment.this.mAdapter;
                    if (redPacketAdapter5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        redPacketAdapter5 = null;
                    }
                    CustomEmptyView customEmptyView3 = RedPacketSelectFragment.this.emptyView;
                    if (customEmptyView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        customEmptyView3 = null;
                    }
                    redPacketAdapter5.setEmptyView(customEmptyView3);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SmartRefreshLayout smartRefreshLayout3 = RedPacketSelectFragment.this.mRefresh;
                    if (smartRefreshLayout3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
                        smartRefreshLayout3 = null;
                    }
                    smartRefreshLayout3.finishRefresh();
                    RedPacketAdapter redPacketAdapter6 = RedPacketSelectFragment.this.mAdapter;
                    if (redPacketAdapter6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        redPacketAdapter6 = null;
                    }
                    redPacketAdapter6.setList(new ArrayList());
                    RedPacketAdapter redPacketAdapter7 = RedPacketSelectFragment.this.mAdapter;
                    if (redPacketAdapter7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        redPacketAdapter7 = null;
                    }
                    CustomEmptyView customEmptyView4 = RedPacketSelectFragment.this.emptyView;
                    if (customEmptyView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    } else {
                        customEmptyView = customEmptyView4;
                    }
                    redPacketAdapter7.setEmptyView(customEmptyView);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$1(RedPacketSelectFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loadRedPacketList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setDefSelect(List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> list, String defSelectId) {
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem = (RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj;
            if (Intrinsics.areEqual(redEnvelopeCouponsDataItem.getRed_packet_id(), defSelectId)) {
                redEnvelopeCouponsDataItem.setCheck(true);
                CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment = this.present;
                if (couponAndRedPacketSelectDialogFragment == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("present");
                    couponAndRedPacketSelectDialogFragment = null;
                }
                String price = redEnvelopeCouponsDataItem.getPrice();
                Intrinsics.checkNotNullExpressionValue(price, "item.price");
                couponAndRedPacketSelectDialogFragment.updateSelectCouponOrRedPacket(false, true, price);
            }
            i2 = i3;
        }
    }

    public static /* synthetic */ void setListNum$default(RedPacketSelectFragment redPacketSelectFragment, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        redPacketSelectFragment.setListNum(i2, i3);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.available = arguments.getBoolean("available");
            String string = arguments.getString("goodId", "");
            Intrinsics.checkNotNullExpressionValue(string, "it.getString(\"goodId\", \"\")");
            this.goodId = string;
            String string2 = arguments.getString("goodType", "");
            Intrinsics.checkNotNullExpressionValue(string2, "it.getString(\"goodType\", \"\")");
            this.goodType = string2;
            String string3 = arguments.getString("orderAmount", "");
            Intrinsics.checkNotNullExpressionValue(string3, "it.getString(\"orderAmount\", \"\")");
            this.orderAmount = string3;
            String string4 = arguments.getString("defSelectId", "");
            Intrinsics.checkNotNullExpressionValue(string4, "it.getString(\"defSelectId\", \"\")");
            this.defSelectId = string4;
            this.isPromotion = arguments.getBoolean("isPromotion", false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_available, container, false);
        View viewFindViewById = viewInflate.findViewById(R.id.recyclerview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "v.findViewById(R.id.recyclerview)");
        this.mRecycler = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "v.findViewById(R.id.refresh)");
        this.mRefresh = (SmartRefreshLayout) viewFindViewById2;
        return viewInflate;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        loadRedPacketList();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        RedPacketAdapter redPacketAdapter;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.mAdapter = new RedPacketAdapter(this.available, false, true, false, 10, null);
        SmartRefreshLayout smartRefreshLayout = this.mRefresh;
        RedPacketAdapter redPacketAdapter2 = null;
        if (smartRefreshLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
            smartRefreshLayout = null;
        }
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        SmartRefreshLayout smartRefreshLayout2 = this.mRefresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRefresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setEnableLoadMore(false);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RedPacketSelectFragment.onViewCreated$lambda$1(this.f11830c, view2);
            }
        });
        CustomEmptyView customEmptyView2 = this.emptyView;
        if (customEmptyView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView2 = null;
        }
        customEmptyView2.showEmptyView();
        RedPacketAdapter redPacketAdapter3 = this.mAdapter;
        if (redPacketAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            redPacketAdapter3 = null;
        }
        CustomEmptyView customEmptyView3 = this.emptyView;
        if (customEmptyView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView3 = null;
        }
        redPacketAdapter3.setEmptyView(customEmptyView3);
        View view2 = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = CommonUtil.dip2px(getActivity(), 16.0f);
        view2.setLayoutParams(layoutParams);
        RedPacketAdapter redPacketAdapter4 = this.mAdapter;
        if (redPacketAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            redPacketAdapter = null;
        } else {
            redPacketAdapter = redPacketAdapter4;
        }
        BaseQuickAdapter.addHeaderView$default(redPacketAdapter, view2, 0, 0, 6, null);
        RecyclerView recyclerView = this.mRecycler;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRecycler");
            recyclerView = null;
        }
        RedPacketAdapter redPacketAdapter5 = this.mAdapter;
        if (redPacketAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            redPacketAdapter5 = null;
        }
        recyclerView.setAdapter(redPacketAdapter5);
        RedPacketAdapter redPacketAdapter6 = this.mAdapter;
        if (redPacketAdapter6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            redPacketAdapter6 = null;
        }
        redPacketAdapter6.setList(this.mListData);
        loadRedPacketList();
        Fragment parentFragment = getParentFragment();
        Intrinsics.checkNotNull(parentFragment, "null cannot be cast to non-null type com.psychiatrygarden.activity.coupon.fragment.CouponAndRedPacketSelectDialogFragment");
        this.present = (CouponAndRedPacketSelectDialogFragment) parentFragment;
        RedPacketAdapter redPacketAdapter7 = this.mAdapter;
        if (redPacketAdapter7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            redPacketAdapter2 = redPacketAdapter7;
        }
        redPacketAdapter2.setClickListener(new Function1<RedPacketAdapter.ClickListenerBuild, Unit>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.onViewCreated.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(RedPacketAdapter.ClickListenerBuild clickListenerBuild) {
                invoke2(clickListenerBuild);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull RedPacketAdapter.ClickListenerBuild setClickListener) {
                Intrinsics.checkNotNullParameter(setClickListener, "$this$setClickListener");
                setClickListener.buttonClickGet(new Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.onViewCreated.2.1
                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
                        invoke(redEnvelopeCouponsDataItem, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem s2, int i2) {
                        Intrinsics.checkNotNullParameter(s2, "s");
                        StringBuilder sb = new StringBuilder();
                        sb.append(s2);
                        sb.append(',');
                        sb.append(i2);
                        System.out.println((Object) sb.toString());
                    }
                });
                setClickListener.buttonClickGoToUse(new Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.onViewCreated.2.2
                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
                        invoke(redEnvelopeCouponsDataItem, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem s2, int i2) {
                        Intrinsics.checkNotNullParameter(s2, "s");
                        StringBuilder sb = new StringBuilder();
                        sb.append(s2);
                        sb.append(',');
                        sb.append(i2);
                        System.out.println((Object) sb.toString());
                    }
                });
                setClickListener.itemClick(new Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.onViewCreated.2.3
                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
                        invoke(redEnvelopeCouponsDataItem, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem s2, int i2) {
                        Intrinsics.checkNotNullParameter(s2, "s");
                        StringBuilder sb = new StringBuilder();
                        sb.append(s2);
                        sb.append(',');
                        sb.append(i2);
                        System.out.println((Object) sb.toString());
                    }
                });
                final RedPacketSelectFragment redPacketSelectFragment = RedPacketSelectFragment.this;
                setClickListener.itemSelect(new Function3<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Boolean, Unit>() { // from class: com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment.onViewCreated.2.4
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num, Boolean bool) {
                        invoke(redEnvelopeCouponsDataItem, num.intValue(), bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, int i2, boolean z2) {
                        Intrinsics.checkNotNullParameter(item, "item");
                        RedPacketAdapter redPacketAdapter8 = null;
                        if (z2) {
                            CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment = redPacketSelectFragment.present;
                            if (couponAndRedPacketSelectDialogFragment == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("present");
                                couponAndRedPacketSelectDialogFragment = null;
                            }
                            String red_packet_id = item.getRed_packet_id();
                            Intrinsics.checkNotNullExpressionValue(red_packet_id, "item.red_packet_id");
                            couponAndRedPacketSelectDialogFragment.setSelectRedPacketId(red_packet_id);
                            RedPacketAdapter redPacketAdapter9 = redPacketSelectFragment.mAdapter;
                            if (redPacketAdapter9 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                                redPacketAdapter9 = null;
                            }
                            int i3 = 0;
                            for (Object obj : redPacketAdapter9.getData()) {
                                int i4 = i3 + 1;
                                if (i3 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem = (RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj;
                                redEnvelopeCouponsDataItem.setCheck(Intrinsics.areEqual(redEnvelopeCouponsDataItem.getRed_packet_id(), item.getRed_packet_id()));
                                i3 = i4;
                            }
                            CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment2 = redPacketSelectFragment.present;
                            if (couponAndRedPacketSelectDialogFragment2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("present");
                                couponAndRedPacketSelectDialogFragment2 = null;
                            }
                            String price = item.getPrice();
                            Intrinsics.checkNotNullExpressionValue(price, "item.price");
                            couponAndRedPacketSelectDialogFragment2.updateSelectCouponOrRedPacket(false, true, price);
                        } else {
                            CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment3 = redPacketSelectFragment.present;
                            if (couponAndRedPacketSelectDialogFragment3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("present");
                                couponAndRedPacketSelectDialogFragment3 = null;
                            }
                            couponAndRedPacketSelectDialogFragment3.setSelectRedPacketId("-1");
                            RedPacketAdapter redPacketAdapter10 = redPacketSelectFragment.mAdapter;
                            if (redPacketAdapter10 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                                redPacketAdapter10 = null;
                            }
                            int i5 = 0;
                            for (Object obj2 : redPacketAdapter10.getData()) {
                                int i6 = i5 + 1;
                                if (i5 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                ((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj2).setCheck(false);
                                i5 = i6;
                            }
                            CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment4 = redPacketSelectFragment.present;
                            if (couponAndRedPacketSelectDialogFragment4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("present");
                                couponAndRedPacketSelectDialogFragment4 = null;
                            }
                            couponAndRedPacketSelectDialogFragment4.updateSelectCouponOrRedPacket(false, false, "0");
                        }
                        RedPacketAdapter redPacketAdapter11 = redPacketSelectFragment.mAdapter;
                        if (redPacketAdapter11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        } else {
                            redPacketAdapter8 = redPacketAdapter11;
                        }
                        redPacketAdapter8.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public final void setListNum(int availableNum, int noAvailableNum) {
        Fragment parentFragment = getParentFragment();
        CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment = parentFragment instanceof CouponAndRedPacketSelectDialogFragment ? (CouponAndRedPacketSelectDialogFragment) parentFragment : null;
        if (couponAndRedPacketSelectDialogFragment != null) {
            couponAndRedPacketSelectDialogFragment.setListNum(false, true, availableNum);
        }
        Fragment parentFragment2 = getParentFragment();
        CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment2 = parentFragment2 instanceof CouponAndRedPacketSelectDialogFragment ? (CouponAndRedPacketSelectDialogFragment) parentFragment2 : null;
        if (couponAndRedPacketSelectDialogFragment2 != null) {
            couponAndRedPacketSelectDialogFragment2.setListNum(false, false, noAvailableNum);
        }
    }
}
