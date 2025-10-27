package com.psychiatrygarden.fragmenthome;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.psychiatrygarden.adapter.StoreListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ShopFragment extends BaseFragment {
    private static final int REFRESH_COMPLETE = 272;
    private int channel_id;
    private boolean hasMoreData;
    private boolean isLoad;
    private boolean isLoading;
    private LinearLayout liner_img;
    private ListView listView;
    private boolean loadSuccess;
    private StoreListAdapter mStoreListAdapter;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private String text;
    List<ShopInfoBean.DataBean> dataList = new ArrayList();
    private long lastLoadingTime = 0;
    private final int preloadThreshold = 3;
    private final int throttleTime = 500;
    private int page = 1;

    public static /* synthetic */ int access$310(ShopFragment shopFragment) {
        int i2 = shopFragment.page;
        shopFragment.page = i2 - 1;
        return i2;
    }

    public static /* synthetic */ int access$312(ShopFragment shopFragment, int i2) {
        int i3 = shopFragment.page + i2;
        shopFragment.page = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getShopDataList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cat_id", String.valueOf(this.channel_id));
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("page_size", "20");
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        this.isLoad = true;
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.goodsList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.ShopFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (!TextUtils.isEmpty(strMsg)) {
                    NewToast.showShort(((BaseFragment) ShopFragment.this).mContext, strMsg);
                }
                ShopFragment.this.mSwipeRefreshLayout.finishRefresh(false);
                ShopFragment.this.isLoading = false;
                if (ShopFragment.this.page > 1) {
                    ShopFragment.access$310(ShopFragment.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                ShopFragment.this.isLoading = false;
                try {
                    ShopInfoBean shopInfoBean = (ShopInfoBean) new Gson().fromJson(s2, ShopInfoBean.class);
                    if (!shopInfoBean.getCode().equals("200")) {
                        NewToast.showShort(((BaseFragment) ShopFragment.this).mContext, shopInfoBean.getMessage());
                        ShopFragment.this.mSwipeRefreshLayout.finishRefresh(false);
                        if (ShopFragment.this.page > 1) {
                            ShopFragment.access$310(ShopFragment.this);
                            return;
                        }
                        return;
                    }
                    ShopFragment.this.mSwipeRefreshLayout.finishRefresh(true);
                    ShopFragment.this.loadSuccess = true;
                    List<ShopInfoBean.DataBean> data = ((ShopInfoBean) new Gson().fromJson(s2, ShopInfoBean.class)).getData();
                    if (ShopFragment.this.mStoreListAdapter == null) {
                        ShopFragment.this.mStoreListAdapter = new StoreListAdapter(ShopFragment.this.getActivity(), ShopFragment.this.dataList);
                        ShopFragment.this.listView.setAdapter((ListAdapter) ShopFragment.this.mStoreListAdapter);
                    }
                    if (ShopFragment.this.page == 1) {
                        ShopFragment.this.dataList.clear();
                    }
                    ShopFragment.this.hasMoreData = data != null && data.size() > 0;
                    if (data != null && data.size() > 0) {
                        ShopFragment.this.dataList.addAll(data);
                        ShopFragment.this.mStoreListAdapter.notifyDataSetChanged();
                        ShopFragment.this.liner_img.setVisibility(8);
                    } else if (ShopFragment.this.mStoreListAdapter.getCount() == 0) {
                        ShopFragment.this.liner_img.setVisibility(0);
                    } else {
                        ShopFragment.this.liner_img.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ShopFragment.this.mSwipeRefreshLayout.finishRefresh(false);
                    if (ShopFragment.this.page > 1) {
                        ShopFragment.access$310(ShopFragment.this);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getShopDataList();
    }

    public static ShopFragment newInstance(String text, int channel_id) {
        ShopFragment shopFragment = new ShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", channel_id);
        bundle.putString("text", text);
        shopFragment.setArguments(bundle);
        return shopFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_goods_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.listView = (ListView) holder.get(R.id.pinnedSectionListView1);
        this.liner_img = (LinearLayout) holder.get(R.id.liner_img);
        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.ShopFragment.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int i2 = (totalItemCount - ((firstVisibleItem + visibleItemCount) - 1)) - 1;
                long jCurrentTimeMillis = System.currentTimeMillis();
                boolean z2 = jCurrentTimeMillis - ShopFragment.this.lastLoadingTime > 500;
                if (i2 > 3 || ShopFragment.this.isLoading || !ShopFragment.this.hasMoreData || !z2) {
                    return;
                }
                ShopFragment.this.isLoading = true;
                ShopFragment.this.lastLoadingTime = jCurrentTimeMillis;
                ShopFragment.access$312(ShopFragment.this, 1);
                ShopFragment.this.getShopDataList();
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.mSwipeRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(false);
        this.mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.hd
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15645c.lambda$initViews$0(refreshLayout);
            }
        });
        this.page = 1;
        getShopDataList();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle arguments = getArguments();
        this.text = arguments != null ? arguments.getString("text") : "";
        this.channel_id = arguments != null ? arguments.getInt("id", 0) : 0;
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !this.loadSuccess && this.isLoad) {
            this.page = 1;
            getShopDataList();
        }
    }
}
