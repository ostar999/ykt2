package com.psychiatrygarden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.bean.VestReplyBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class VestReplyActivity extends BaseActivity {
    public BaseQuickAdapter<VestReplyBean.DataDTO.ListDTO, BaseViewHolder> adapter;
    private CustomEmptyView mEmptyView;
    public int page = 1;
    public RecyclerView recycleView;
    public SmartRefreshLayout refresh;

    /* renamed from: com.psychiatrygarden.activity.VestReplyActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<VestReplyBean.DataDTO.ListDTO, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(VestReplyBean.DataDTO.ListDTO listDTO, View view) {
            listDTO.setRed_dot(0);
            notifyDataSetChanged();
            Intent intent = new Intent(VestReplyActivity.this, (Class<?>) VestCommentActivity.class);
            intent.putExtra("title", "" + listDTO.getNickname() + "的评论");
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(listDTO.getUser_id());
            intent.putExtra("target_user_id", sb.toString());
            VestReplyActivity.this.startActivity(intent);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, final VestReplyBean.DataDTO.ListDTO item) {
            CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.headerpart);
            TextView textView = (TextView) holder.getView(R.id.nickname);
            TextView textView2 = (TextView) holder.getView(R.id.replynum2);
            GlideApp.with((FragmentActivity) VestReplyActivity.this).load((Object) GlideUtils.generateUrl(item.getAvatar())).into(circleImageView);
            textView.setText(item.getNickname());
            if (item.getRed_dot() == 0) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.uq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14001c.lambda$convert$0(item, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getDatalist();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(RefreshLayout refreshLayout) {
        this.page++;
        getDatalist();
    }

    public void getDatalist() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.get(this, NetworkRequestsURL.virtualUserWithReplyStatusApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.VestReplyActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                VestReplyActivity.this.refresh.finishRefresh(false);
                VestReplyActivity.this.refresh.finishLoadMore(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                VestReplyActivity.this.refresh.finishRefresh(true);
                try {
                    VestReplyBean vestReplyBean = (VestReplyBean) new Gson().fromJson(s2, VestReplyBean.class);
                    if (vestReplyBean.getCode() == 200) {
                        VestReplyActivity vestReplyActivity = VestReplyActivity.this;
                        if (vestReplyActivity.page == 1) {
                            vestReplyActivity.adapter.setList(vestReplyBean.getData().getList());
                        } else if (vestReplyBean.getData().getList().isEmpty()) {
                            VestReplyActivity.this.refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            VestReplyActivity.this.refresh.finishLoadMore(true);
                            VestReplyActivity.this.adapter.addData(vestReplyBean.getData().getList());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.refresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        this.recycleView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new AnonymousClass1(R.layout.layout_vest_reply);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "暂无数据");
        this.mEmptyView = customEmptyView;
        customEmptyView.showEmptyView();
        this.adapter.setEmptyView(this.mEmptyView);
        this.recycleView.setAdapter(this.adapter);
        this.refresh.setEnableLoadMore(true);
        this.refresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.sq
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13931c.lambda$init$0(refreshLayout);
            }
        });
        this.refresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.tq
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13966c.lambda$init$1(refreshLayout);
            }
        });
        getDatalist();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_vest_reply);
        setTitle("马甲回复");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
