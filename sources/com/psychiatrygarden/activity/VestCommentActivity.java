package com.psychiatrygarden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.VestCommentActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class VestCommentActivity extends BaseActivity {
    public BaseQuickAdapter<MyMessageCommentBean.DataBean, BaseViewHolder> adapter;
    public RecyclerView recyid;
    public SmartRefreshLayout refreshView;
    public int pageNum = 1;
    public String target_user_id = "";

    /* renamed from: com.psychiatrygarden.activity.VestCommentActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<MyMessageCommentBean.DataBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$0(MyMessageCommentBean.DataBean dataBean, View view) {
            PublicMethodActivity.getInstance().mCommentMethod(dataBean.getModule_type() + "", dataBean.getTarget_params());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$1(MyMessageCommentBean.DataBean dataBean, View view) {
            try {
                PublicMethodActivity.getInstance().mCommentMethod(dataBean.getModule_type() + "", dataBean.getTarget_params());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(MyMessageCommentBean.DataBean dataBean, View view) {
            dataBean.setIs_read("1");
            notifyDataSetChanged();
            int module_type = dataBean.getModule_type();
            int comment_id = dataBean.getComment_id();
            Intent intent = new Intent(VestCommentActivity.this.mContext, (Class<?>) DiscussPublicActivity.class);
            intent.putExtra("title", "评论我");
            intent.putExtra("comment_type", "2");
            intent.putExtra("module_type", module_type);
            intent.putExtra("obj_id", comment_id + "");
            intent.putExtra("commentEnum", DiscussStatus.CommentOnMy);
            VestCommentActivity.this.startActivity(intent);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, final MyMessageCommentBean.DataBean item) {
            TextView textView = (TextView) holder.getView(R.id.title);
            TextView textView2 = (TextView) holder.getView(R.id.redyuan);
            TextView textView3 = (TextView) holder.getView(R.id.ctime);
            TextView textView4 = (TextView) holder.getView(R.id.description);
            TextView textView5 = (TextView) holder.getView(R.id.tofrom);
            TextView textView6 = (TextView) holder.getView(R.id.atmostlun);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(item.getNickname() + "：" + item.getContent());
            if (SkinManager.getCurrentSkinType(VestCommentActivity.this.mContext) == 0) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#145FB3")), 0, item.getNickname().length(), 34);
            } else {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#104C8F")), 0, item.getNickname().length(), 34);
            }
            textView4.setText(spannableStringBuilder);
            textView.setText(item.getTitle());
            textView3.setText(item.getTimestr());
            textView5.setText(item.getTo_from());
            if (item.getModule_type() == 3) {
                textView6.setText("查看原文");
            } else if (item.getModule_type() == 12 || item.getModule_type() == 16) {
                textView6.setText("查看帖子");
            } else {
                textView6.setText("查看原题");
            }
            textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VestCommentActivity.AnonymousClass1.lambda$convert$0(item, view);
                }
            });
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VestCommentActivity.AnonymousClass1.lambda$convert$1(item, view);
                }
            });
            if (item.getIs_read().equals("0")) {
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13808c.lambda$convert$2(item, view);
                }
            });
        }
    }

    private void getMessage() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.pageNum + "");
        ajaxParams.put("target_user_id", this.target_user_id + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.virtualUserReplyApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.VestCommentActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                VestCommentActivity.this.refreshView.finishRefresh(false);
                VestCommentActivity.this.refreshView.finishLoadMore(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    VestCommentActivity.this.refreshView.finishRefresh(true);
                    MyMessageCommentBean myMessageCommentBean = (MyMessageCommentBean) new Gson().fromJson(t2, MyMessageCommentBean.class);
                    if (myMessageCommentBean.getCode() == 200) {
                        VestCommentActivity vestCommentActivity = VestCommentActivity.this;
                        if (vestCommentActivity.pageNum == 1) {
                            vestCommentActivity.adapter.setList(myMessageCommentBean.getData());
                        } else if (myMessageCommentBean.getData() == null || myMessageCommentBean.getData().size() <= 0) {
                            VestCommentActivity.this.refreshView.finishLoadMoreWithNoMoreData();
                        } else {
                            VestCommentActivity.this.refreshView.finishLoadMore(true);
                            VestCommentActivity.this.adapter.addData(myMessageCommentBean.getData());
                        }
                    } else {
                        ToastUtil.shortToast(VestCommentActivity.this, myMessageCommentBean.getMessage() + "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(RefreshLayout refreshLayout) {
        this.pageNum = 1;
        getMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(RefreshLayout refreshLayout) {
        this.pageNum++;
        getMessage();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.target_user_id = getIntent().getExtras().getString("target_user_id");
        this.refreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyid);
        this.recyid = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.myadapter);
        this.adapter = anonymousClass1;
        this.recyid.setAdapter(anonymousClass1);
        this.refreshView.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.nq
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13059c.lambda$init$0(refreshLayout);
            }
        });
        this.refreshView.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.oq
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13513c.lambda$init$1(refreshLayout);
            }
        });
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_empty)).setVisibility(0);
        this.adapter.setEmptyView(viewInflate);
        getMessage();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_vest_comment);
        setTitle("" + getIntent().getExtras().getString("title"));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
