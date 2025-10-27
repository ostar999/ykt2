package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.CommentSearchBean;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.MyExpendView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CommentSeachActivity extends BaseActivity {
    private ClearEditText ed_search;
    private CommAdapter<CommentSearchBean.DataBean> mCommListAdapter;
    public RefreshLayout mRefresh;
    private int module_type;
    public String question_id;
    public ListView recyclelist;
    public int page = 1;
    private final List<CommentSearchBean.DataBean> time_lines = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.CommentSeachActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<CommentSearchBean.DataBean> {
        public AnonymousClass1(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(View view) {
            ToastUtil.shortToast(CommentSeachActivity.this.mContext, "已认证");
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final CommentSearchBean.DataBean mCommentBean, final int position) {
            CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.commentList_item_userIcon);
            CircleImageView circleImageView2 = (CircleImageView) vHolder.getView(R.id.jiav);
            TextView textView = (TextView) vHolder.getView(R.id.commentList_item_tv_userName);
            TextView textView2 = (TextView) vHolder.getView(R.id.commentList_item_tv_school);
            ImageView imageView = (ImageView) vHolder.getView(R.id.vipimgid);
            ImageView imageView2 = (ImageView) vHolder.getView(R.id.isverauth);
            MyExpendView myExpendView = (MyExpendView) vHolder.getView(R.id.myexptervew);
            NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) vHolder.getView(R.id.ningrids);
            if (TextUtils.isEmpty(mCommentBean.getAvatar() + "")) {
                circleImageView.setImageResource(R.drawable.empty_photo);
            } else {
                GlideUtils.loadImage(circleImageView.getContext(), mCommentBean.getAvatar(), circleImageView);
            }
            if (mCommentBean.getUser_identity().equals("1") || mCommentBean.getIs_authentication().equals("1")) {
                circleImageView2.setVisibility(8);
                imageView2.setVisibility(0);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13942c.lambda$convert$0(view);
                    }
                });
            } else {
                circleImageView2.setVisibility(8);
                imageView2.setVisibility(8);
            }
            textView.setTextColor(Color.parseColor(mCommentBean.getUser_identity_color()));
            textView.setText(mCommentBean.getNickname());
            textView2.setText(String.format("%s %s", mCommentBean.getSchool(), mCommentBean.getCtime()));
            if (mCommentBean.getIs_vip().equals("1")) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            myExpendView.setIs_del("0");
            myExpendView.setText(mCommentBean.getContent(), mCommentBean.isIs_showValue());
            myExpendView.setListener(new MyExpendView.OnExpandStateChangeListener() { // from class: com.psychiatrygarden.activity.CommentSeachActivity.1.1
                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onClickStateChange(final View v2) {
                    CommentSeachActivity.this.goToMyComment(position);
                }

                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onExpandStateChanged(boolean isExpanded) {
                    mCommentBean.setIs_showValue(isExpanded);
                }
            });
            nineGridTestLayout.setmImagesBean(mCommentBean.getC_imgs(), 1);
            nineGridTestLayout.setDownImgUrl(mCommentBean.getImg_watermark() + "");
            nineGridTestLayout.setIsShowAll(true);
        }
    }

    private void getCommentListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", "" + this.module_type);
        ajaxParams.put("obj_id", this.question_id + "");
        ajaxParams.put("comment_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("search", "" + this.ed_search.getText().toString());
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.commsearchApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentSeachActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CommentSeachActivity.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    CommentSearchBean commentSearchBean = (CommentSearchBean) new Gson().fromJson(s2, CommentSearchBean.class);
                    if (commentSearchBean.getCode().equals("200")) {
                        List<CommentSearchBean.DataBean> data = commentSearchBean.getData();
                        CommentSeachActivity commentSeachActivity = CommentSeachActivity.this;
                        if (commentSeachActivity.page == 1) {
                            commentSeachActivity.time_lines.clear();
                            CommentSeachActivity.this.time_lines.addAll(data);
                            CommentSeachActivity.this.mCommListAdapter.notifyDataSetChanged();
                            CommentSeachActivity.this.mRefresh.finishRefresh();
                        } else if (data.size() == 0) {
                            CommentSeachActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            CommentSeachActivity.this.time_lines.addAll(data);
                            CommentSeachActivity.this.mCommListAdapter.notifyDataSetChanged();
                            CommentSeachActivity.this.mRefresh.finishLoadMore();
                        }
                    } else {
                        CommentSeachActivity commentSeachActivity2 = CommentSeachActivity.this;
                        if (commentSeachActivity2.page == 1) {
                            commentSeachActivity2.time_lines.clear();
                            CommentSeachActivity.this.time_lines.addAll(new ArrayList());
                            CommentSeachActivity.this.mCommListAdapter.notifyDataSetChanged();
                        } else {
                            commentSeachActivity2.mRefresh.finishLoadMore(true);
                        }
                    }
                    ToastUtil.shortToast(CommentSeachActivity.this.mContext, commentSearchBean.getMessage());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.ed_search.clearFocus();
        this.ed_search.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            this.page = 1;
            this.mRefresh.resetNoMoreData();
            this.mRefresh.setEnableLoadMore(true);
            getCommentListData();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(AdapterView adapterView, View view, int i2, long j2) {
        goToMyComment(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshView$3(RefreshLayout refreshLayout) {
        this.page = 1;
        getCommentListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshView$4(RefreshLayout refreshLayout) {
        this.page++;
        getCommentListData();
    }

    public void goToMyComment(int position) {
        try {
            Intent intent = new Intent(this.mContext, (Class<?>) DiscussPublicActivity.class);
            intent.putExtra("title", "" + this.time_lines.get(position).getNickname() + "的评论");
            intent.putExtra("comment_type", "2");
            intent.putExtra("module_type", Integer.parseInt(this.time_lines.get(position).getModule_type()));
            intent.putExtra("obj_id", this.time_lines.get(position).getId() + "");
            intent.putExtra("commentEnum", DiscussStatus.CommentOnMy);
            startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        LayerDrawable layerDrawable;
        this.module_type = getIntent().getExtras().getInt("module_type");
        this.question_id = getIntent().getExtras().getString("question_id");
        this.ed_search = (ClearEditText) findViewById(R.id.ed_search);
        this.mRefresh = (RefreshLayout) findViewById(R.id.refreshLayout);
        this.recyclelist = (ListView) findViewById(R.id.recyclelist);
        if (SkinManager.getCurrentSkinType(this) == 1 && (layerDrawable = (LayerDrawable) this.ed_search.getBackground()) != null) {
            layerDrawable.setColorFilter(Color.parseColor("#121622"), PorterDuff.Mode.SRC_IN);
            this.ed_search.setBackground(layerDrawable);
        }
        this.ed_search.post(new Runnable() { // from class: com.psychiatrygarden.activity.o4
            @Override // java.lang.Runnable
            public final void run() {
                this.f13070c.lambda$init$0();
            }
        });
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.p4
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f13531c.lambda$init$1(textView, i2, keyEvent);
            }
        });
        initRefreshView();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.time_lines, this.mContext, R.layout.layout_commnet_search_item);
        this.mCommListAdapter = anonymousClass1;
        this.recyclelist.setAdapter((ListAdapter) anonymousClass1);
        this.recyclelist.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.q4
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13727c.lambda$init$2(adapterView, view, i2, j2);
            }
        });
    }

    public void initRefreshView() {
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.r4
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13758c.lambda$initRefreshView$3(refreshLayout);
            }
        });
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.s4
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13818c.lambda$initRefreshView$4(refreshLayout);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_comment_search);
        setTitle("搜索");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
