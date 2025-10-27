package com.psychiatrygarden.activity.forum;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.sortlist.CharacterParser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumUserListActivity extends BaseActivity {
    public BaseQuickAdapter<ForumInfoBean.DataBean.UserBean, BaseViewHolder> adapter;
    private CharacterParser characterParser;
    public ImageView icon_left2;
    public LinearLayout lineview;
    public SmartRefreshLayout mRefresh;
    public RecyclerView recycleview;
    public int page = 1;
    public List<ForumInfoBean.DataBean.UserBean> userList = new ArrayList();
    public List<ForumInfoBean.DataBean.UserBean> filterDateList = new ArrayList();
    public String keywords = "";

    /* JADX INFO: Access modifiers changed from: private */
    public void filterData(String filterStr) {
        if (TextUtils.isEmpty(filterStr)) {
            this.filterDateList.clear();
            this.adapter.setList(this.userList);
            return;
        }
        this.filterDateList.clear();
        for (ForumInfoBean.DataBean.UserBean userBean : this.userList) {
            String upperCase = userBean.getNickname().toUpperCase();
            if (upperCase.contains(filterStr.toUpperCase()) || this.characterParser.getSelling(upperCase.toUpperCase()).startsWith(filterStr.toUpperCase())) {
                this.filterDateList.add(userBean);
            }
        }
        this.adapter.setList(this.filterDateList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(RefreshLayout refreshLayout) {
        this.page = 1;
        getUserListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(RefreshLayout refreshLayout) {
        this.page++;
        getUserListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", "" + this.userList.get(i2).getUser_id());
        startActivity(intent);
    }

    public void addHeader() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.activity_search_header, (ViewGroup) null);
        ((ClearEditText) viewInflate.findViewById(R.id.editTxt)).addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.forum.ForumUserListActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                ForumUserListActivity.this.keywords = s2.toString();
                ForumUserListActivity forumUserListActivity = ForumUserListActivity.this;
                forumUserListActivity.filterData(forumUserListActivity.keywords);
            }
        });
        this.adapter.addHeaderView(viewInflate);
    }

    public void getUserListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(this, NetworkRequestsURL.getforummemberApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumUserListActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumUserListActivity forumUserListActivity = ForumUserListActivity.this;
                int i2 = forumUserListActivity.page;
                if (i2 > 1) {
                    forumUserListActivity.page = i2 - 1;
                    forumUserListActivity.mRefresh.finishLoadMore();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (!"200".equals(new JSONObject(s2).optString("code"))) {
                        ForumUserListActivity forumUserListActivity = ForumUserListActivity.this;
                        int i2 = forumUserListActivity.page;
                        if (i2 > 1) {
                            forumUserListActivity.page = i2 - 1;
                            forumUserListActivity.mRefresh.finishLoadMore();
                            return;
                        }
                        return;
                    }
                    List arrayList = (List) new Gson().fromJson(new JSONObject(s2).optString("data"), new TypeToken<List<ForumInfoBean.DataBean.UserBean>>() { // from class: com.psychiatrygarden.activity.forum.ForumUserListActivity.3.1
                    }.getType());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    if (arrayList.size() > 0) {
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            ((ForumInfoBean.DataBean.UserBean) arrayList.get(i3)).setSortLetters(ForumUserListActivity.this.characterParser.getSelling(((ForumInfoBean.DataBean.UserBean) arrayList.get(i3)).getNickname()).substring(0, 1).toUpperCase());
                        }
                    }
                    ForumUserListActivity forumUserListActivity2 = ForumUserListActivity.this;
                    if (forumUserListActivity2.page == 1) {
                        forumUserListActivity2.userList.clear();
                        ForumUserListActivity.this.userList.addAll(arrayList);
                        ForumUserListActivity.this.mRefresh.finishRefresh(true);
                    } else if (arrayList.size() > 0) {
                        ForumUserListActivity.this.userList.addAll(arrayList);
                        ForumUserListActivity.this.mRefresh.finishLoadMore();
                    } else {
                        ForumUserListActivity forumUserListActivity3 = ForumUserListActivity.this;
                        int i4 = forumUserListActivity3.page;
                        if (i4 > 1) {
                            forumUserListActivity3.page = i4 - 1;
                        }
                        forumUserListActivity3.mRefresh.finishLoadMoreWithNoMoreData();
                    }
                    ForumUserListActivity.this.adapter.notifyDataSetChanged();
                } catch (Exception unused) {
                    ForumUserListActivity forumUserListActivity4 = ForumUserListActivity.this;
                    int i5 = forumUserListActivity4.page;
                    if (i5 > 1) {
                        forumUserListActivity4.page = i5 - 1;
                        forumUserListActivity4.mRefresh.finishLoadMore();
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.characterParser = CharacterParser.getInstance();
        this.recycleview = (RecyclerView) findViewById(R.id.recycleview);
        this.lineview = (LinearLayout) findViewById(R.id.lineview);
        ImageView imageView = (ImageView) findViewById(R.id.icon_left2);
        this.icon_left2 = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12411c.lambda$init$0(view);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 2, 10, 0);
        this.recycleview.setLayoutManager(new GridLayoutManager(this, 5));
        this.recycleview.addItemDecoration(dividerItemDecoration);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        this.mRefresh = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(true);
        this.mRefresh.setEnableRefresh(true);
        this.mRefresh.autoRefresh();
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.forum.s
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12412c.lambda$init$1(refreshLayout);
            }
        });
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.forum.t
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12413c.lambda$init$2(refreshLayout);
            }
        });
        BaseQuickAdapter<ForumInfoBean.DataBean.UserBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumInfoBean.DataBean.UserBean, BaseViewHolder>(R.layout.layout_forum_user_item, this.userList) { // from class: com.psychiatrygarden.activity.forum.ForumUserListActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, ForumInfoBean.DataBean.UserBean item) {
                CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.moderatorimg);
                TextView textView = (TextView) helper.getView(R.id.name);
                if (TextUtils.isEmpty(ForumUserListActivity.this.keywords)) {
                    textView.setText(item.getNickname());
                } else {
                    CommonUtil.getImageData(ForumUserListActivity.this.mContext, item.getNickname(), textView, ForumUserListActivity.this.keywords);
                }
                GlideApp.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(item.getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.app_icon).placeholder(R.drawable.app_icon)).into(circleImageView);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.u
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12414c.lambda$init$3(baseQuickAdapter2, view, i2);
            }
        });
        this.recycleview.setAdapter(this.adapter);
        addHeader();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("成员列表");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.fragment_forum_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
