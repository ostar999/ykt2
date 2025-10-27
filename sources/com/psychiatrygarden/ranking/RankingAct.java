package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.RankAllBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.RankItemView;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes6.dex */
public class RankingAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private ImageView mImgBack;
    private LinearLayout mLyAddView;
    private LinearLayout mLyNoDataView;
    private NestedScrollView mScrollView;
    private TabRankAdp mTabAdp;
    private RecyclerView mTabRecycler;
    private TextView mTvUpdateTime;
    private View mView;
    private int currentPositon = 0;
    private int childViewHeight = 0;
    private int laseChildViewTopHeight = 0;
    private List<String> mChildren = new ArrayList();

    /* renamed from: com.psychiatrygarden.ranking.RankingAct$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            int height = RankingAct.this.mLyAddView.getChildAt(RankingAct.this.mLyAddView.getChildCount() - 1).getHeight();
            int height2 = RankingAct.this.mScrollView.getHeight();
            int i2 = height2 - height;
            RankingAct rankingAct = RankingAct.this;
            rankingAct.laseChildViewTopHeight = rankingAct.mLyAddView.getChildAt(RankingAct.this.mLyAddView.getChildCount() - 1).getTop();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) RankingAct.this.mLyAddView.getLayoutParams();
            layoutParams.bottomMargin = i2;
            RankingAct.this.mLyAddView.setLayoutParams(layoutParams);
            LogUtils.e("view_height", "height=>" + height2 + ";endHeight=" + height + ";disHeight=>" + i2);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            RankingAct.this.emptyView.setVisibility(8);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            RankingAct.this.emptyView.showEmptyView();
            RankingAct.this.emptyView.setVisibility(8);
            try {
                RankAllBean rankAllBean = (RankAllBean) new Gson().fromJson(s2, RankAllBean.class);
                if (rankAllBean.getCode().equals("200")) {
                    if (rankAllBean.getData() == null) {
                        ToastUtil.shortToast(RankingAct.this, rankAllBean.getMessage());
                        return;
                    }
                    RankingAct.this.mLyAddView.removeAllViews();
                    RankingAct.this.mTvUpdateTime.setText("数据更新时间：" + rankAllBean.getData().getRefresh_time());
                    if (rankAllBean.getData().getUser_sheet_total() != null && !rankAllBean.getData().getUser_sheet_total().isEmpty()) {
                        RankingAct.this.mChildren.add("刷题榜");
                        RankItemView rankItemView = new RankItemView(RankingAct.this);
                        rankItemView.setData("刷题", rankAllBean.getData().getUser_sheet_total(), 1);
                        RankingAct.this.mLyAddView.addView(rankItemView);
                    }
                    if (rankAllBean.getData().getUser_bbs_article_total() != null && !rankAllBean.getData().getUser_bbs_article_total().isEmpty()) {
                        RankingAct.this.mChildren.add("发帖榜");
                        RankItemView rankItemView2 = new RankItemView(RankingAct.this);
                        rankItemView2.setData("发帖", rankAllBean.getData().getUser_bbs_article_total(), 2);
                        RankingAct.this.mLyAddView.addView(rankItemView2);
                    }
                    if (rankAllBean.getData().getUser_comment_total() != null && !rankAllBean.getData().getUser_comment_total().isEmpty()) {
                        RankingAct.this.mChildren.add("评论榜");
                        RankItemView rankItemView3 = new RankItemView(RankingAct.this);
                        rankItemView3.setData("评论", rankAllBean.getData().getUser_comment_total(), 3);
                        RankingAct.this.mLyAddView.addView(rankItemView3);
                    }
                    if (rankAllBean.getData().getUser_praise_total() != null && !rankAllBean.getData().getUser_praise_total().isEmpty()) {
                        RankingAct.this.mChildren.add("获赞榜");
                        RankItemView rankItemView4 = new RankItemView(RankingAct.this);
                        rankItemView4.setData("获赞", rankAllBean.getData().getUser_praise_total(), 4);
                        RankingAct.this.mLyAddView.addView(rankItemView4);
                    }
                    if (rankAllBean.getData().getUser_total_days() != null && !rankAllBean.getData().getUser_total_days().isEmpty()) {
                        RankingAct.this.mChildren.add("累计活跃");
                        RankItemView rankItemView5 = new RankItemView(RankingAct.this);
                        rankItemView5.setData("累计活跃", rankAllBean.getData().getUser_total_days(), 5);
                        RankingAct.this.mLyAddView.addView(rankItemView5);
                    }
                    if (rankAllBean.getData().getUser_continue_days() != null && !rankAllBean.getData().getUser_continue_days().isEmpty()) {
                        RankingAct.this.mChildren.add("连续活跃");
                        RankItemView rankItemView6 = new RankItemView(RankingAct.this);
                        rankItemView6.setData("连续活跃", rankAllBean.getData().getUser_continue_days(), 6);
                        RankingAct.this.mLyAddView.addView(rankItemView6);
                    }
                    RankingAct.this.mTabAdp.setNewData(RankingAct.this.mChildren);
                    if (RankingAct.this.mChildren.isEmpty()) {
                        RankingAct.this.mScrollView.setVisibility(8);
                        RankingAct.this.mTabRecycler.setVisibility(8);
                        RankingAct.this.mLyNoDataView.setVisibility(0);
                    } else {
                        RankingAct.this.mLyNoDataView.setVisibility(8);
                        RankingAct.this.mTabRecycler.setVisibility(0);
                        RankingAct.this.mScrollView.setVisibility(0);
                    }
                    RankingAct.this.mLyAddView.post(new Runnable() { // from class: com.psychiatrygarden.ranking.o
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16213c.lambda$onSuccess$0();
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class TabRankAdp extends BaseQuickAdapter<String, BaseViewHolder> {
        private int mSelectPos;

        public TabRankAdp() {
            super(R.layout.item_ranking_tab);
            this.mSelectPos = 0;
        }

        public void setmSelectPos(int mSelectPos) {
            this.mSelectPos = mSelectPos;
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(BaseViewHolder helper, String item) {
            TextView textView = (TextView) helper.getView(R.id.tab_name);
            ImageView imageView = (ImageView) helper.getView(R.id.img_line);
            textView.setText(item);
            if (this.mSelectPos == helper.getLayoutPosition()) {
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.setTextSize(16.0f);
                textView.setTextColor(Color.parseColor("#141516"));
                imageView.setVisibility(0);
                return;
            }
            textView.setTypeface(Typeface.DEFAULT);
            textView.setTextSize(14.0f);
            textView.setTextColor(Color.parseColor("#909499"));
            imageView.setVisibility(4);
        }
    }

    private void initData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getAllRankInfo, new AjaxParams(), new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        int top2 = this.mLyAddView.getChildAt(i2).getTop();
        int height = this.mLyAddView.getChildAt(i2).getHeight();
        this.mScrollView.scrollTo(0, top2);
        LogUtils.e("scroll_y", "hei====>" + height + ";top===>" + top2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
        scrollView(i3 + UIUtil.dip2px(this, 11.0d));
        LogUtils.e("view_height", "height=>" + UIUtil.dip2px(this, 11.0d));
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) RankingAct.class));
    }

    private void scrollView(int scrollY) {
        for (int i2 = 0; i2 < this.mLyAddView.getChildCount(); i2++) {
            View childAt = this.mLyAddView.getChildAt(i2);
            int top2 = childAt.getTop();
            int bottom = childAt.getBottom();
            if (top2 <= scrollY && bottom >= scrollY) {
                if (this.mTabAdp.mSelectPos != i2) {
                    LogUtils.e("scroll_view", "滑动选中tab：" + this.mChildren.get(i2));
                    this.mTabAdp.setmSelectPos(i2);
                    this.mTabRecycler.scrollToPosition(i2);
                    this.mTabAdp.notifyDataSetChanged();
                    return;
                }
                return;
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mTabRecycler = (RecyclerView) findViewById(R.id.tab_recycler);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        this.emptyView = (CustomEmptyView) findViewById(R.id.empty_view);
        this.mLyNoDataView = (LinearLayout) findViewById(R.id.ly_no_data_view);
        this.mView = findViewById(R.id.view_height);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mImgBack.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        this.mImgBack.setLayoutParams(layoutParams);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.mTabRecycler.setLayoutManager(linearLayoutManager);
        TabRankAdp tabRankAdp = new TabRankAdp();
        this.mTabAdp = tabRankAdp;
        this.mTabRecycler.setAdapter(tabRankAdp);
        this.mChildren.clear();
        this.mTabAdp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.ranking.n
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16212a.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16210c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.ranking.m
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f16211a.lambda$setListenerForWidget$2(nestedScrollView, i2, i3, i4, i5);
            }
        });
    }
}
