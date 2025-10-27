package com.psychiatrygarden.fragmenthome.material;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.material.MaterialListActivity;
import com.psychiatrygarden.activity.material.MaterialListAdp;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.event.MaterialSearchEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class MaterialSearchFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private String category;
    private CustomEmptyView emptyView;
    private MaterialListAdp mAdapter;
    private String mAppId;
    private ClearEditText mEtContent;
    private SmartRefreshLayout mRefreshLayout;
    private String rank;
    private RecyclerView rvList;
    private String searchWord;
    private String type;
    private boolean showTopSearhBar = true;
    private int page = 1;
    private String searchKeyword = "";

    public static MaterialSearchFragment getInstance(boolean showTopSearhBar, String appId) {
        MaterialSearchFragment materialSearchFragment = new MaterialSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showTopSearhBar", showTopSearhBar);
        bundle.putString("appId", appId);
        materialSearchFragment.setArguments(bundle);
        return materialSearchFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (TextUtils.isEmpty(this.searchWord)) {
            return;
        }
        this.mRefreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        onRefresh(this.mRefreshLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(MaterialListBean.MaterialListData materialListData, int i2) {
        materialListData.setIs_rights("1");
        this.mAdapter.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        final MaterialListBean.MaterialListData item = this.mAdapter.getItem(i2);
        if (item.getFile_type().equals("2")) {
            MaterialListActivity.newIntent(getActivity(), item.getId(), this.mAppId, item.getTitle(), false);
            return;
        }
        if (!TextUtils.isEmpty(item.getIs_rights()) && item.getIs_rights().equals("1")) {
            InformationPreviewAct.newIntent(getActivity(), item.getId(), item.getUrl(), false);
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("enclosure_id", item.getId());
        MemInterface.getInstance().getFilePermission(getActivity(), ajaxParams);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.material.a
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f15838a.lambda$initViews$3(item, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$5(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.mEtContent.getText().toString().equals("")) {
            this.page = 1;
            loadData();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        if (this.mEtContent.getText().toString().equals("")) {
            return;
        }
        this.page = 1;
        loadData();
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("search_app_id", this.mAppId);
        }
        ajaxParams.put("page_size", "20");
        if (TextUtils.isEmpty(this.mEtContent.getText().toString())) {
            this.mAdapter.setList(new ArrayList());
            this.mRefreshLayout.finishRefresh();
        } else {
            ajaxParams.put("title", this.mEtContent.getText().toString());
            this.searchKeyword = this.mEtContent.getText().toString();
            YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getInformationList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.material.MaterialSearchFragment.1
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    MaterialSearchFragment.this.mRefreshLayout.finishRefresh();
                    if (MaterialSearchFragment.this.page == 1) {
                        MaterialSearchFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                        MaterialSearchFragment.this.emptyView.setLoadFileResUi(MaterialSearchFragment.this.getActivity());
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    MaterialSearchFragment.this.mRefreshLayout.finishRefresh();
                    MaterialSearchFragment.this.emptyView.showEmptyView();
                    super.onSuccess((AnonymousClass1) s2);
                    try {
                        MaterialListBean materialListBean = (MaterialListBean) new Gson().fromJson(s2, MaterialListBean.class);
                        if (materialListBean.getCode().equals("200")) {
                            if (materialListBean.getData() == null || materialListBean.getData().getList() == null || materialListBean.getData().getList().isEmpty()) {
                                if (MaterialSearchFragment.this.page == 1) {
                                    MaterialSearchFragment.this.mAdapter.setList(new ArrayList());
                                    MaterialSearchFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                                }
                                MaterialSearchFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                                return;
                            }
                            MaterialSearchFragment.this.mAdapter.setSearchContent(MaterialSearchFragment.this.searchKeyword);
                            if (MaterialSearchFragment.this.page == 1) {
                                MaterialSearchFragment.this.mAdapter.setList(materialListBean.getData().getList());
                                if (materialListBean.getData().getList().size() < 20) {
                                    MaterialSearchFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                                    return;
                                }
                                return;
                            }
                            if (materialListBean.getData().getList().isEmpty()) {
                                return;
                            }
                            MaterialSearchFragment.this.mAdapter.addData((Collection) materialListBean.getData().getList());
                            if (materialListBean.getData().getList().size() < 20) {
                                MaterialSearchFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                MaterialSearchFragment.this.mRefreshLayout.finishLoadMore();
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        if (MaterialSearchFragment.this.page == 1) {
                            MaterialSearchFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                            MaterialSearchFragment.this.emptyView.setLoadFileResUi(MaterialSearchFragment.this.getActivity());
                        }
                    }
                }
            });
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_material_search;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.showTopSearhBar = arguments.getBoolean("showTopSearhBar", true);
            this.mAppId = arguments.getString("appId");
        }
        holder.get(R.id.iv_actionbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.material.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15841c.lambda$initViews$0(view);
            }
        });
        holder.get(R.id.tv_search).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.material.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15842c.lambda$initViews$1(view);
            }
        });
        ClearEditText clearEditText = (ClearEditText) holder.get(R.id.et_search);
        this.mEtContent = clearEditText;
        clearEditText.setHint("输入关键词搜索资料");
        showSoftInput(this.mEtContent);
        holder.get(R.id.titlebar_layout).setVisibility(this.showTopSearhBar ? 0 : 8);
        TextView textView = (TextView) holder.get(R.id.tv_search);
        ImageView imageView = (ImageView) holder.get(R.id.iv_download);
        textView.setVisibility(this.showTopSearhBar ? 0 : 8);
        imageView.setVisibility(this.showTopSearhBar ? 8 : 0);
        this.mRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.rvList = (RecyclerView) holder.get(R.id.rvList);
        MaterialListAdp materialListAdp = new MaterialListAdp(true);
        this.mAdapter = materialListAdp;
        this.rvList.setAdapter(materialListAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.material.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15843c.lambda$initViews$2(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.getEmptyLayout().setVisibility(8);
        if (!this.showTopSearhBar) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rvList.getLayoutParams();
            layoutParams.topMargin = 0;
            this.rvList.setLayoutParams(layoutParams);
        }
        this.mRefreshLayout.setOnRefreshLoadMoreListener(this);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.material.e
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15844c.lambda$initViews$4(baseQuickAdapter, view, i2);
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.fragmenthome.material.f
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f15845c.lambda$initViews$5(textView2, i2, keyEvent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.material.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15846c.lambda$initViews$6(view);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(MaterialSearchEvent event) {
        if (this.showTopSearhBar) {
            if (TextUtils.isEmpty(event.getKeyword())) {
                getViewHolder().get(R.id.tv_search).setVisibility(8);
                getViewHolder().get(R.id.iv_download).setVisibility(0);
                this.searchWord = null;
            } else {
                this.searchWord = event.getKeyword();
                getViewHolder().get(R.id.tv_search).setVisibility(0);
                getViewHolder().get(R.id.iv_download).setVisibility(8);
            }
        }
        this.rank = event.getRank();
        this.type = event.getType();
        this.category = event.getCategory();
        if (TextUtils.isEmpty(event.getKeyword())) {
            return;
        }
        this.mRefreshLayout.autoRefresh();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("BuySuccess")) {
            this.page = 1;
            loadData();
        }
    }
}
