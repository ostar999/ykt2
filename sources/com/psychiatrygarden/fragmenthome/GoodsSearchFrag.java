package com.psychiatrygarden.fragmenthome;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.adapter.StoreListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GoodsSearchFrag extends BaseFragment {
    private CustomEmptyView emptyView;
    private StoreListAdapter mAdapter;
    private ListView mListView;
    private List<ShopInfoBean.DataBean> resultList = new ArrayList();
    private int page = 1;
    private boolean hasMoreData = false;
    private boolean isLoading = false;
    private int preloadThreshold = 3;
    private int throttleTime = 500;
    private long lastLoadTime = 0;
    private String keyWord = "";

    public static /* synthetic */ int access$510(GoodsSearchFrag goodsSearchFrag) {
        int i2 = goodsSearchFrag.page;
        goodsSearchFrag.page = i2 - 1;
        return i2;
    }

    public static /* synthetic */ int access$512(GoodsSearchFrag goodsSearchFrag, int i2) {
        int i3 = goodsSearchFrag.page + i2;
        goodsSearchFrag.page = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData(String keyword) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", "20");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, keyword);
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.goodsSearch, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.GoodsSearchFrag.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (GoodsSearchFrag.this.page > 1) {
                    GoodsSearchFrag.access$510(GoodsSearchFrag.this);
                }
                if (GoodsSearchFrag.this.isLoading) {
                    GoodsSearchFrag.this.isLoading = false;
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                if (GoodsSearchFrag.this.isLoading) {
                    GoodsSearchFrag.this.isLoading = false;
                }
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        if (GoodsSearchFrag.this.page > 1) {
                            GoodsSearchFrag.access$510(GoodsSearchFrag.this);
                            return;
                        }
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<ShopInfoBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.GoodsSearchFrag.2.1
                    }.getType());
                    if (list != null && list.size() > 0) {
                        if (GoodsSearchFrag.this.mAdapter.getCount() == 0 || GoodsSearchFrag.this.page == 1) {
                            GoodsSearchFrag.this.emptyView.setVisibility(8);
                            GoodsSearchFrag.this.resultList.clear();
                        }
                        GoodsSearchFrag.this.resultList.addAll(list);
                        GoodsSearchFrag.this.mAdapter.notifyDataSetChanged();
                    } else if (GoodsSearchFrag.this.mAdapter.getCount() == 0) {
                        GoodsSearchFrag.this.emptyView.setVisibility(0);
                    } else {
                        if (GoodsSearchFrag.this.page == 1) {
                            GoodsSearchFrag.this.resultList.clear();
                        }
                        GoodsSearchFrag.this.emptyView.setVisibility(8);
                        GoodsSearchFrag.this.mAdapter.notifyDataSetChanged();
                    }
                    GoodsSearchFrag.this.hasMoreData = list.size() >= 5;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (GoodsSearchFrag.this.page > 1) {
                        GoodsSearchFrag.access$510(GoodsSearchFrag.this);
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.act_goods_search;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        holder.get(R.id.titlebar_layout).setVisibility(8);
        this.mListView = (ListView) holder.get(R.id.lvGoods);
        this.emptyView = (CustomEmptyView) holder.get(R.id.empty_view);
        StoreListAdapter storeListAdapter = new StoreListAdapter(this.mContext, this.resultList, true);
        this.mAdapter = storeListAdapter;
        this.mListView.setAdapter((ListAdapter) storeListAdapter);
        this.emptyView.stopAnim();
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.GoodsSearchFrag.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((totalItemCount - ((firstVisibleItem + visibleItemCount) - 1)) - 1 > GoodsSearchFrag.this.preloadThreshold || GoodsSearchFrag.this.isLoading || !GoodsSearchFrag.this.hasMoreData) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - GoodsSearchFrag.this.lastLoadTime > GoodsSearchFrag.this.throttleTime) {
                    GoodsSearchFrag.this.lastLoadTime = jCurrentTimeMillis;
                    GoodsSearchFrag.this.isLoading = true;
                    GoodsSearchFrag.access$512(GoodsSearchFrag.this, 1);
                    GoodsSearchFrag goodsSearchFrag = GoodsSearchFrag.this;
                    goodsSearchFrag.getData(goodsSearchFrag.keyWord);
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

    public void setSearchKeyWord(String word, boolean isReload) {
        this.keyWord = word;
        this.mAdapter.setSearchWord(word);
        this.page = 1;
        if (isReload) {
            getData(word);
        } else if (this.mAdapter.getCount() == 0) {
            getData(word);
        }
    }
}
