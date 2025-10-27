package com.psychiatrygarden.activity.mine.coupons;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.coupon.adapter.CouponAdapter;
import com.psychiatrygarden.activity.coupon.adapter.RedPacketAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.GetCouponsBean;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class FragRedEnvelopeCoupons extends BaseFragment implements OnRefreshListener {
    private View addFooterView;
    private CustomEmptyView emptyView;
    private boolean isCanLoadNextPage;
    private boolean isCenter;
    private String last_id;
    private boolean loadDataSuccess;
    private CouponAdapter mAdapter;
    private RelativeLayout mLyLoading;
    private RecyclerView mRecyclerView;
    private RedPacketAdapter mRedAdapter;
    private SmartRefreshLayout mRefresh;
    private TextView mTvNoData;
    private String couponType = "";
    private int mPage = 1;
    private int mUseType = 1;
    private boolean isNoMoreData = false;

    public static /* synthetic */ int access$312(FragRedEnvelopeCoupons fragRedEnvelopeCoupons, int i2) {
        int i3 = fragRedEnvelopeCoupons.mPage + i2;
        fragRedEnvelopeCoupons.mPage = i3;
        return i3;
    }

    private void clickToUse(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item) {
        if (item.getUse_type().equals("1")) {
            if (this.couponType.equals("0")) {
                PublicMethodActivity.getInstance().couponsJump(item.getJump());
            } else if (ProjectApp.newHomeStyle) {
                EventBus.getDefault().post("jumpToNewHome");
                ProjectApp.instance().closeAllActivityWithoutHome();
            } else {
                EventBus.getDefault().post("jumpToHome");
                ProjectApp.instance().closeAllActivityWithoutHome();
            }
        }
    }

    private void getCoupons(final RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, final int position) {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.couponType.equals("0")) {
            ajaxParams.put("coupon", item.getId());
        } else {
            ajaxParams.put("red_envelope", item.getId());
        }
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.getCoupon, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.coupons.FragRedEnvelopeCoupons.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    GetCouponsBean getCouponsBean = (GetCouponsBean) new Gson().fromJson(s2, GetCouponsBean.class);
                    if (!getCouponsBean.getCode().equals("200")) {
                        ToastUtil.shortToast(((BaseFragment) FragRedEnvelopeCoupons.this).mContext, getCouponsBean.getMessage());
                        return;
                    }
                    if (getCouponsBean.getData() == null || getCouponsBean.getData().size() <= 0) {
                        return;
                    }
                    item.setCoupon_start(getCouponsBean.getData().get(0).getCoupon_start());
                    item.setCoupon_end(getCouponsBean.getData().get(0).getCoupon_end());
                    if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                        FragRedEnvelopeCoupons.this.mAdapter.notifyItemChanged(position, 0);
                    } else {
                        FragRedEnvelopeCoupons.this.mRedAdapter.notifyItemChanged(position, 0);
                    }
                    ToastUtil.shortToast(((BaseFragment) FragRedEnvelopeCoupons.this).mContext, "领取成功");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public void lambda$setUserVisibleHint$0() {
        StringBuilder sb;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.isCenter) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        } else if (this.mPage == 1) {
            ajaxParams.put("last_min_id", "0");
        } else {
            ajaxParams.put("last_min_id", this.last_id);
        }
        if (this.mPage <= 1) {
            if (this.couponType.equals("0")) {
                this.mAdapter.removeFooterView(this.addFooterView);
            } else {
                this.mRedAdapter.removeFooterView(this.addFooterView);
            }
            this.mLyLoading.setVisibility(0);
            this.mTvNoData.setVisibility(8);
        } else if (this.couponType.equals("0")) {
            if (this.mAdapter.getFooterLayout() == null) {
                this.mAdapter.addFooterView(this.addFooterView);
            }
        } else if (this.mRedAdapter.getFooterLayout() == null) {
            this.mRedAdapter.addFooterView(this.addFooterView);
        }
        if (this.isCenter) {
            sb = new StringBuilder();
            sb.append(NetworkRequestsURL.couponsList);
            sb.append(this.couponType);
            sb.append("/center");
        } else {
            sb = new StringBuilder();
            sb.append(NetworkRequestsURL.couponsList);
            sb.append(this.couponType);
            sb.append("/list/");
            sb.append(this.mUseType);
        }
        YJYHttpUtils.post(getActivity(), sb.toString(), ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.coupons.FragRedEnvelopeCoupons.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FragRedEnvelopeCoupons.this.mRefresh.finishRefresh(false);
                if (FragRedEnvelopeCoupons.this.mPage == 1) {
                    FragRedEnvelopeCoupons.this.emptyView.showEmptyView();
                    FragRedEnvelopeCoupons.this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
                    if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                        FragRedEnvelopeCoupons.this.mAdapter.removeFooterView(FragRedEnvelopeCoupons.this.addFooterView);
                    } else {
                        FragRedEnvelopeCoupons.this.mRedAdapter.removeFooterView(FragRedEnvelopeCoupons.this.addFooterView);
                    }
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                FragRedEnvelopeCoupons.this.loadDataSuccess = true;
                FragRedEnvelopeCoupons.this.mRefresh.finishRefresh();
                FragRedEnvelopeCoupons.this.emptyView.showEmptyView();
                super.onSuccess((AnonymousClass2) s2);
                try {
                    RedEnvelopeCouponsBean redEnvelopeCouponsBean = (RedEnvelopeCouponsBean) new Gson().fromJson(s2, RedEnvelopeCouponsBean.class);
                    if (redEnvelopeCouponsBean.getCode().equals("200")) {
                        if (redEnvelopeCouponsBean.getData() == null || redEnvelopeCouponsBean.getData().getItems() == null || redEnvelopeCouponsBean.getData().getItems().size() <= 0) {
                            FragRedEnvelopeCoupons.this.isNoMoreData = true;
                            FragRedEnvelopeCoupons.this.mLyLoading.setVisibility(8);
                            FragRedEnvelopeCoupons.this.mTvNoData.setVisibility(0);
                            if (FragRedEnvelopeCoupons.this.mPage == 1) {
                                if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                                    FragRedEnvelopeCoupons.this.mAdapter.setList(new ArrayList());
                                } else {
                                    FragRedEnvelopeCoupons.this.mRedAdapter.setList(new ArrayList());
                                }
                            }
                            FragRedEnvelopeCoupons.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            int size = redEnvelopeCouponsBean.getData().getItems().size();
                            if (FragRedEnvelopeCoupons.this.mPage == 1) {
                                if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                                    FragRedEnvelopeCoupons.this.mAdapter.setList(redEnvelopeCouponsBean.getData().getItems());
                                } else {
                                    FragRedEnvelopeCoupons.this.mRedAdapter.setList(redEnvelopeCouponsBean.getData().getItems());
                                }
                                FragRedEnvelopeCoupons.this.last_id = redEnvelopeCouponsBean.getData().getItems().get(size - 1).getRecord_id();
                                if (redEnvelopeCouponsBean.getData().getMore().equals("0")) {
                                    FragRedEnvelopeCoupons.this.mRefresh.finishLoadMoreWithNoMoreData();
                                    FragRedEnvelopeCoupons.this.isNoMoreData = true;
                                    FragRedEnvelopeCoupons.this.mLyLoading.setVisibility(8);
                                    FragRedEnvelopeCoupons.this.mTvNoData.setVisibility(0);
                                }
                            } else {
                                if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                                    FragRedEnvelopeCoupons.this.mAdapter.addData((Collection) redEnvelopeCouponsBean.getData().getItems());
                                } else {
                                    FragRedEnvelopeCoupons.this.mRedAdapter.addData((Collection) redEnvelopeCouponsBean.getData().getItems());
                                }
                                FragRedEnvelopeCoupons.this.last_id = redEnvelopeCouponsBean.getData().getItems().get(size - 1).getRecord_id();
                                if (redEnvelopeCouponsBean.getData().getMore().equals("0")) {
                                    FragRedEnvelopeCoupons.this.isNoMoreData = true;
                                    FragRedEnvelopeCoupons.this.mLyLoading.setVisibility(8);
                                    FragRedEnvelopeCoupons.this.mTvNoData.setVisibility(0);
                                    FragRedEnvelopeCoupons.this.mRefresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    FragRedEnvelopeCoupons.this.mRefresh.finishLoadMore();
                                }
                            }
                        }
                        FragRedEnvelopeCoupons.this.isCanLoadNextPage = false;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (FragRedEnvelopeCoupons.this.mPage == 1) {
                        FragRedEnvelopeCoupons.this.emptyView.setLoadFileResUi(((BaseFragment) FragRedEnvelopeCoupons.this).mContext);
                    }
                    if (FragRedEnvelopeCoupons.this.couponType.equals("0")) {
                        FragRedEnvelopeCoupons.this.mAdapter.removeFooterView(FragRedEnvelopeCoupons.this.addFooterView);
                    } else {
                        FragRedEnvelopeCoupons.this.mRedAdapter.removeFooterView(FragRedEnvelopeCoupons.this.addFooterView);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        onRefresh(this.mRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$2(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        getCoupons(redEnvelopeCouponsDataItem, num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$3(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        clickToUse(redEnvelopeCouponsDataItem);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Unit lambda$initViews$4(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$5(CouponAdapter.ClickListenerBuild clickListenerBuild) {
        clickListenerBuild.buttonClickGet(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.e
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f12782c.lambda$initViews$2((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        clickListenerBuild.buttonClickGoToUse(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.f
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f12783c.lambda$initViews$3((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        clickListenerBuild.itemClick(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.g
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return FragRedEnvelopeCoupons.lambda$initViews$4((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$6(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        getCoupons(redEnvelopeCouponsDataItem, num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$7(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        clickToUse(redEnvelopeCouponsDataItem);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Unit lambda$initViews$8(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initViews$9(RedPacketAdapter.ClickListenerBuild clickListenerBuild) {
        clickListenerBuild.buttonClickGet(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.h
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f12784c.lambda$initViews$6((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        clickListenerBuild.buttonClickGoToUse(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.i
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f12785c.lambda$initViews$7((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        clickListenerBuild.itemClick(new Function2() { // from class: com.psychiatrygarden.activity.mine.coupons.j
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return FragRedEnvelopeCoupons.lambda$initViews$8((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj, (Integer) obj2);
            }
        });
        return null;
    }

    public static FragRedEnvelopeCoupons newInstance() {
        Bundle bundle = new Bundle();
        FragRedEnvelopeCoupons fragRedEnvelopeCoupons = new FragRedEnvelopeCoupons();
        fragRedEnvelopeCoupons.setArguments(bundle);
        return fragRedEnvelopeCoupons;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_red_envelope;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        int i2 = getArguments().getInt("position");
        this.isCenter = getArguments().getBoolean("isCenter");
        this.mUseType = getArguments().getInt("useType");
        boolean z2 = getArguments().getBoolean("available");
        this.couponType = i2 == 0 ? "0" : "2";
        this.mRefresh = (SmartRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recyclerview);
        boolean z3 = getArguments().getBoolean("needTopMargin", false);
        View view = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = CommonUtil.dip2px(this.mContext, 16.0f);
        view.setLayoutParams(layoutParams);
        if (this.couponType.equals("0")) {
            CouponAdapter couponAdapter = new CouponAdapter(z2, this.isCenter, this.couponType.equals("0"), false, z3);
            this.mAdapter = couponAdapter;
            this.mRecyclerView.setAdapter(couponAdapter);
        } else {
            RedPacketAdapter redPacketAdapter = new RedPacketAdapter(z2, this.isCenter, false, z3);
            this.mRedAdapter = redPacketAdapter;
            this.mRecyclerView.setAdapter(redPacketAdapter);
        }
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.coupons.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12778c.lambda$initViews$1(view2);
            }
        });
        if (this.couponType.equals("0")) {
            this.mAdapter.setEmptyView(this.emptyView);
        } else {
            this.mRedAdapter.setEmptyView(this.emptyView);
        }
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setOnRefreshListener(this);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.mine.coupons.FragRedEnvelopeCoupons.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) FragRedEnvelopeCoupons.this.mRecyclerView.getLayoutManager();
                int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    int itemCount = linearLayoutManager.getItemCount() - iFindFirstVisibleItemPosition;
                    LogUtils.e("dis_count", "count====>" + itemCount);
                    if (itemCount > 11 || FragRedEnvelopeCoupons.this.isCanLoadNextPage || FragRedEnvelopeCoupons.this.isNoMoreData) {
                        return;
                    }
                    FragRedEnvelopeCoupons.this.isCanLoadNextPage = true;
                    FragRedEnvelopeCoupons.access$312(FragRedEnvelopeCoupons.this, 1);
                    FragRedEnvelopeCoupons.this.lambda$setUserVisibleHint$0();
                    LogUtils.e("load_next", "加载下一页数据:" + FragRedEnvelopeCoupons.this.mPage);
                }
            }
        });
        if (this.couponType.equals("0")) {
            this.mAdapter.setClickListener(new Function1() { // from class: com.psychiatrygarden.activity.mine.coupons.b
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f12779c.lambda$initViews$5((CouponAdapter.ClickListenerBuild) obj);
                }
            });
        } else {
            this.mRedAdapter.setClickListener(new Function1() { // from class: com.psychiatrygarden.activity.mine.coupons.c
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f12780c.lambda$initViews$9((RedPacketAdapter.ClickListenerBuild) obj);
                }
            });
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.mPage = 1;
        this.isNoMoreData = false;
        lambda$setUserVisibleHint$0();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser || this.loadDataSuccess) {
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.coupons.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f12781c.lambda$setUserVisibleHint$0();
            }
        }, 500L);
    }
}
