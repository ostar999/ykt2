package com.psychiatrygarden.activity.material;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MaterialListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private MaterialListAdp mAdapter;
    private String mAppId;
    private ImageView mImgBack;
    private ImageView mImgDownload;
    private ImageView mImgSearch;
    private String mParentId;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView rvList = null;
    private boolean isLoadMore = false;
    private int page = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        onRefresh(this.mRefreshLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MaterialListBean.MaterialListData item = this.mAdapter.getItem(i2);
        if (item.getFile_type().equals("2")) {
            newIntent(this, item.getId(), this.mAppId, item.getTitle(), false);
        } else {
            InformationPreviewAct.newIntent(this, item.getId(), item.getUrl(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) MaterialDownloadListActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        ForumSearchAct.newIntent(this.mContext, "", null, 2);
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("search_app_id", this.mAppId);
        ajaxParams.put("parent_id", this.mParentId);
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getInformationList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.MaterialListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MaterialListActivity.this.mRefreshLayout.finishRefresh();
                if (MaterialListActivity.this.page == 1) {
                    MaterialListActivity.this.emptyView.setLoadFileResUi(MaterialListActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                MaterialListActivity.this.emptyView.showEmptyView();
                MaterialListActivity.this.mRefreshLayout.finishRefresh();
                super.onSuccess((AnonymousClass1) s2);
                try {
                    MaterialListBean materialListBean = (MaterialListBean) new Gson().fromJson(s2, MaterialListBean.class);
                    if (materialListBean.getCode().equals("200")) {
                        if (materialListBean.getData() == null || materialListBean.getData().getList() == null || materialListBean.getData().getList().isEmpty()) {
                            MaterialListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else if (MaterialListActivity.this.page == 1) {
                            MaterialListActivity.this.mAdapter.setList(materialListBean.getData().getList());
                            if (materialListBean.getData().getList().size() < 10) {
                                MaterialListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else if (materialListBean.getData().getList().isEmpty()) {
                            MaterialListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            MaterialListActivity.this.mAdapter.addData((Collection) materialListBean.getData().getList());
                            if (materialListBean.getData().getList().size() < 10) {
                                MaterialListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                MaterialListActivity.this.mRefreshLayout.finishLoadMore();
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (MaterialListActivity.this.page == 1) {
                        MaterialListActivity.this.emptyView.setLoadFileResUi(MaterialListActivity.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context, String id, String appId, String title, boolean isFirst) {
        Intent intent = new Intent(context, (Class<?>) MaterialListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("appId", appId);
        intent.putExtra("title", title);
        intent.putExtra("isFirst", isFirst);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mAppId = getIntent().getStringExtra("appId");
        this.mParentId = getIntent().getStringExtra("id");
        String stringExtra = getIntent().getStringExtra("title");
        boolean booleanExtra = getIntent().getBooleanExtra("isFirst", false);
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.rvList = (RecyclerView) findViewById(R.id.rvList);
        this.mImgSearch = (ImageView) findViewById(R.id.iv_search);
        this.mImgDownload = (ImageView) findViewById(R.id.iv_download);
        textView.setText(stringExtra);
        this.mImgDownload.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_information_transmission_night : R.mipmap.ic_information_transmission);
        this.mImgSearch.setVisibility(booleanExtra ? 0 : 8);
        this.mImgDownload.setVisibility(booleanExtra ? 0 : 8);
        MaterialListAdp materialListAdp = new MaterialListAdp(false);
        this.mAdapter = materialListAdp;
        this.rvList.setAdapter(materialListAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12726c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mRefreshLayout.setOnRefreshLoadMoreListener(this);
        loadData();
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.material.l1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12729c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("BuySuccess")) {
            this.page = 1;
            loadData();
        }
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_material_list);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.h1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12717c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mImgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12720c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mImgSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.j1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12723c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
