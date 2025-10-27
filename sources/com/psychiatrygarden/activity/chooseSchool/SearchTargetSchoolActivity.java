package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.adapter.SearchTargetSchoolHeaderAdapter;
import com.psychiatrygarden.adapter.TargetSchoolListAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SearchTargetSchoolActivity extends BaseActivity implements View.OnClickListener {
    private TextView addTargetSchoolBtnTv;
    private ConcatAdapter concatAdapter;
    private ClearEditText etHeader;
    private SearchTargetSchoolHeaderAdapter headerAdapter;
    private TargetSchoolListAdapter mAdapter;
    private CustomEmptyView mEmptyView;
    private ImageView mIvActionbarBack;
    private LinearLayout mLlEmpty;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvMemberDetail;
    private boolean mToSearch;
    private TextView searchTitle;
    private LinearLayout topLlSearch;
    private ClearEditText topSearch;
    private TextView topSearchTv;
    private int page = 1;
    private int pageSize = 20;
    private List<FollowSchoolBean.DataBean> mDetailList = new ArrayList();
    private String sharedText = "";
    private boolean isUpdating = false;
    String ids = "";
    private List<String> selectSchoolNameList = new ArrayList();
    private List<String> selectSchoolId = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity$8, reason: invalid class name */
    public class AnonymousClass8 extends AjaxCallBack<String> {
        public AnonymousClass8() {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            SearchTargetSchoolActivity.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
            SearchTargetSchoolActivity.this.showProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass8) s2);
            SearchTargetSchoolActivity.this.hideProgressDialog();
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (jSONObject.optInt("code") != 200) {
                    Toast.makeText(SearchTargetSchoolActivity.this, jSONObject.optString("message"), 0).show();
                    return;
                }
                ToastUtil.shortToast(SearchTargetSchoolActivity.this.mContext, "添加成功！");
                if (Build.VERSION.SDK_INT >= 24) {
                    SearchTargetSchoolActivity.this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.c7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((FollowSchoolBean.DataBean) obj).setItem_select(false);
                        }
                    });
                }
                SearchTargetSchoolActivity.this.mAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(EventBusConstant.EVENT_ADD_TARGET_SCHOOL_SUCCESS);
                AliYunLogUtil.getInstance().addLogParamsIsList(AliyunEvent.AddSchool, SearchTargetSchoolActivity.this.selectSchoolId, SearchTargetSchoolActivity.this.selectSchoolNameList);
                SearchTargetSchoolActivity.this.finish();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static /* synthetic */ int access$810(SearchTargetSchoolActivity searchTargetSchoolActivity) {
        int i2 = searchTargetSchoolActivity.page;
        searchTargetSchoolActivity.page = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager;
        View currentFocus = getCurrentFocus();
        if (currentFocus == null || (inputMethodManager = (InputMethodManager) getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    private void initRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z6
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11482c.lambda$initRefreshLayout$1(refreshLayout);
            }
        });
        this.mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a7
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11190c.lambda$initRefreshLayout$2(refreshLayout);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.mEmptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11224c.lambda$initRefreshLayout$3(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, FollowSchoolBean.DataBean dataBean) {
        SchoolDetailsAct.newIntent(this.mContext, dataBean.getSchool_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$1(RefreshLayout refreshLayout) {
        this.page = 1;
        loadData(false);
        this.topSearch.setText("");
        this.etHeader.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$2(RefreshLayout refreshLayout) {
        this.page++;
        loadData(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$3(View view) {
        this.page = 1;
        loadData(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(FollowSchoolBean.DataBean dataBean) {
        if (dataBean.isItem_select()) {
            this.ids += dataBean.getSchool_id() + ",";
            this.selectSchoolNameList.add(dataBean.getTitle());
            this.selectSchoolId.add(dataBean.getSchool_id());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData(boolean toSearch) {
        this.mToSearch = toSearch;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", this.pageSize + "");
        String str = NetworkRequestsURL.targetSchoolSearchList;
        if (toSearch) {
            str = NetworkRequestsURL.searchSchoolOrMajor;
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.sharedText);
            ajaxParams.put("is_target", "1");
            ajaxParams.put("search_type", "1");
            this.mAdapter.setSearchContent(this.sharedText);
        }
        YJYHttpUtils.get(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SearchTargetSchoolActivity.this.hideProgressDialog();
                SearchTargetSchoolActivity.this.mRefreshLayout.finishRefresh();
                NewToast.showShort(SearchTargetSchoolActivity.this, "网络连接失败", 0).show();
                if (SearchTargetSchoolActivity.this.page == 1) {
                    SearchTargetSchoolActivity.this.showEmptyView();
                } else {
                    SearchTargetSchoolActivity.access$810(SearchTargetSchoolActivity.this);
                    SearchTargetSchoolActivity.this.mRefreshLayout.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SearchTargetSchoolActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                SearchTargetSchoolActivity.this.hideProgressDialog();
                SearchTargetSchoolActivity.this.mRefreshLayout.finishRefresh();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if ("200".equals(followSchoolBean.getCode())) {
                        SearchTargetSchoolActivity.this.updateUI(followSchoolBean);
                    } else {
                        NewToast.showShort(SearchTargetSchoolActivity.this, followSchoolBean.getMessage(), 0).show();
                        if (SearchTargetSchoolActivity.this.page == 1) {
                            SearchTargetSchoolActivity.this.showEmptyView();
                        } else {
                            SearchTargetSchoolActivity.access$810(SearchTargetSchoolActivity.this);
                            SearchTargetSchoolActivity.this.mRefreshLayout.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SearchTargetSchoolActivity.this, "数据解析出错", 0).show();
                    if (SearchTargetSchoolActivity.this.page == 1) {
                        SearchTargetSchoolActivity.this.showEmptyView();
                    } else {
                        SearchTargetSchoolActivity.access$810(SearchTargetSchoolActivity.this);
                        SearchTargetSchoolActivity.this.mRefreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) SearchTargetSchoolActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupSync() {
        this.topSearch.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (SearchTargetSchoolActivity.this.isUpdating) {
                    return;
                }
                SearchTargetSchoolActivity.this.isUpdating = true;
                SearchTargetSchoolActivity.this.sharedText = s2.toString();
                SearchTargetSchoolActivity.this.etHeader.setText(SearchTargetSchoolActivity.this.sharedText);
                SearchTargetSchoolActivity.this.etHeader.setSelection(SearchTargetSchoolActivity.this.sharedText.length());
                SearchTargetSchoolActivity.this.isUpdating = false;
                Log.e("wwwwwwww", SearchTargetSchoolActivity.this.sharedText);
            }
        });
        this.topSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.4
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v2, int actionId, KeyEvent event) {
                if (actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                    return false;
                }
                if (TextUtils.isEmpty(SearchTargetSchoolActivity.this.sharedText.trim())) {
                    ToastUtil.shortToast(SearchTargetSchoolActivity.this.mContext, "请输入搜索内容！");
                } else {
                    SearchTargetSchoolActivity.this.page = 1;
                    SearchTargetSchoolActivity.this.loadData(true);
                }
                SearchTargetSchoolActivity.this.hideKeyboard();
                return true;
            }
        });
        this.etHeader.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (SearchTargetSchoolActivity.this.isUpdating) {
                    return;
                }
                SearchTargetSchoolActivity.this.isUpdating = true;
                SearchTargetSchoolActivity.this.sharedText = s2.toString();
                SearchTargetSchoolActivity.this.topSearch.setText(SearchTargetSchoolActivity.this.sharedText);
                SearchTargetSchoolActivity.this.topSearch.setSelection(SearchTargetSchoolActivity.this.sharedText.length());
                SearchTargetSchoolActivity.this.isUpdating = false;
                Log.e("wwwwwwww===", SearchTargetSchoolActivity.this.sharedText);
            }
        });
        this.etHeader.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v2, int actionId, KeyEvent event) {
                if (actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                    return false;
                }
                if (TextUtils.isEmpty(SearchTargetSchoolActivity.this.sharedText.trim())) {
                    ToastUtil.shortToast(SearchTargetSchoolActivity.this.mContext, "请输入搜索内容！");
                } else {
                    SearchTargetSchoolActivity.this.page = 1;
                    SearchTargetSchoolActivity.this.loadData(true);
                }
                SearchTargetSchoolActivity.this.hideKeyboard();
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mDetailList.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    private void toAddTarget(String ids) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", ids);
        ajaxParams.put("operate", "add");
        YJYHttpUtils.post(this, NetworkRequestsURL.saveDelTargetSchool, ajaxParams, new AnonymousClass8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(FollowSchoolBean detailBean) {
        if (detailBean.getData() == null || detailBean.getData().isEmpty()) {
            if (this.page != 1) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                showEmptyView();
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
        }
        List<FollowSchoolBean.DataBean> data = detailBean.getData();
        if (this.page != 1) {
            int size = this.mDetailList.size();
            this.mDetailList.addAll(data);
            if (Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.w6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setEditing_state(true);
                    }
                });
            }
            this.mAdapter.notifyItemRangeInserted(size, data.size());
            if (data.size() < this.pageSize) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.mRefreshLayout.finishLoadMore();
                return;
            }
        }
        this.mDetailList.clear();
        this.mDetailList.addAll(data);
        if (Build.VERSION.SDK_INT >= 24) {
            this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.v6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((FollowSchoolBean.DataBean) obj).setEditing_state(true);
                }
            });
        }
        this.mAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRefreshLayout.setVisibility(0);
        if (data.size() < this.pageSize) {
            this.mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            this.mRefreshLayout.finishLoadMore();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, android.app.Activity, android.view.Window.Callback, cn.webdemo.com.supporfragment.ISupportActivity
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (currentFocus instanceof ClearEditText) {
                int[] iArr = {0, 0};
                currentFocus.getLocationInWindow(iArr);
                int i2 = iArr[0];
                int i3 = iArr[1];
                int height = currentFocus.getHeight() + i3;
                int width = currentFocus.getWidth() + i2;
                if (ev.getX() <= i2 || ev.getX() >= width || ev.getY() <= i3 || ev.getY() >= height) {
                    hideKeyboard();
                    currentFocus.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.mRvMemberDetail = (RecyclerView) findViewById(R.id.rv_member_detail);
        this.mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        this.addTargetSchoolBtnTv = (TextView) findViewById(R.id.add_target_school_btn_tv);
        this.topLlSearch = (LinearLayout) findViewById(R.id.top_ll_search);
        this.topSearchTv = (TextView) findViewById(R.id.top_search_tv);
        this.topSearch = (ClearEditText) findViewById(R.id.top_search);
        this.mIvActionbarBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.searchTitle = (TextView) findViewById(R.id.search_title);
        this.addTargetSchoolBtnTv.setOnClickListener(this);
        this.mIvActionbarBack.setOnClickListener(this);
        this.topSearchTv.setOnClickListener(this);
        this.mAdapter = new TargetSchoolListAdapter(this, this.mDetailList);
        this.headerAdapter = new SearchTargetSchoolHeaderAdapter();
        this.mRvMemberDetail.setLayoutManager(new LinearLayoutManager(this));
        ConcatAdapter concatAdapter = new ConcatAdapter((RecyclerView.Adapter<? extends RecyclerView.ViewHolder>[]) new RecyclerView.Adapter[]{this.headerAdapter, this.mAdapter});
        this.concatAdapter = concatAdapter;
        this.mRvMemberDetail.setAdapter(concatAdapter);
        this.mAdapter.setOnItemClickListener(new TargetSchoolListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y6
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.OnItemClickListener
            public final void onItemClick(int i2, FollowSchoolBean.DataBean dataBean) {
                this.f11474a.lambda$init$0(i2, dataBean);
            }
        });
        initRefreshLayout();
        loadData(false);
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SearchTargetSchoolActivity searchTargetSchoolActivity = SearchTargetSchoolActivity.this;
                searchTargetSchoolActivity.etHeader = searchTargetSchoolActivity.headerAdapter.getEditText();
                SearchTargetSchoolActivity.this.setupSync();
            }
        }, 1000L);
        this.mRvMemberDetail.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    SearchTargetSchoolActivity.this.topLlSearch.setVisibility(0);
                    SearchTargetSchoolActivity.this.topSearchTv.setVisibility(0);
                    SearchTargetSchoolActivity.this.searchTitle.setVisibility(8);
                } else {
                    SearchTargetSchoolActivity.this.topLlSearch.setVisibility(8);
                    SearchTargetSchoolActivity.this.topSearchTv.setVisibility(8);
                    SearchTargetSchoolActivity.this.searchTitle.setVisibility(0);
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.add_target_school_btn_tv) {
            this.selectSchoolNameList.clear();
            this.selectSchoolId.clear();
            if (Build.VERSION.SDK_INT >= 24) {
                this.mAdapter.getList().forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.x6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f11465c.lambda$onClick$6((FollowSchoolBean.DataBean) obj);
                    }
                });
            }
            if (this.ids.isEmpty()) {
                ToastUtil.shortToast(this.mContext, "请选择院校！");
                return;
            } else {
                String str = this.ids;
                toAddTarget(str.substring(0, str.length() - 1));
                return;
            }
        }
        if (id == R.id.iv_actionbar_back) {
            finish();
            return;
        }
        if (id != R.id.top_search) {
            return;
        }
        if (TextUtils.isEmpty(this.sharedText.trim())) {
            ToastUtil.shortToast(this.mContext, "请输入搜索内容！");
        } else {
            this.page = 1;
            loadData(true);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.page = 1;
        loadData(this.mToSearch);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_search_target_school);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
