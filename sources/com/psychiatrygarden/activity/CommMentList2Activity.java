package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.QuestionStatDataBean;
import com.psychiatrygarden.adapter.CommentListAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshQuestionBottomIconEvent;
import com.psychiatrygarden.event.UpdateCommentPraiseEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.MimeTypes;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CommMentList2Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String break_point;
    private TextView catview;
    private TextView comment_lou;
    private String comment_type;
    private View emptyView;
    private boolean ishavehot;
    public ImageView iv_topic_detail_msg;
    public ImageView iv_topic_detail_zan;
    private LinearLayout lineselect2;
    public CommentListAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SmartRefreshLayout mSwipeRefreshLayout;
    public ImageView pinglunlog;
    private long question_id;
    private CheckedTextView remen;
    public TextView tv_topic_detail_add_comment;
    private CheckedTextView zuixin;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private int pageNum = 1;
    private int module_type = 1;
    private String only_author = "0";
    private boolean isNewComzantong = false;
    private boolean isTrue = false;
    private String viewType = "";
    private int positionTab = 0;
    private boolean isLoadMore = false;

    /* renamed from: com.psychiatrygarden.activity.CommMentList2Activity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(CommentBean commentBean, View view) {
            Intent intent = new Intent(CommMentList2Activity.this, (Class<?>) QuestionCommentActivity.class);
            intent.putExtra("question_id", CommMentList2Activity.this.question_id + "");
            StringBuilder sb = new StringBuilder();
            sb.append("最热评论(");
            sb.append((CommMentList2Activity.this.hot == null || CommMentList2Activity.this.hot.isEmpty()) ? "0" : commentBean.getData().getHot_total());
            sb.append(")");
            intent.putExtra("title", sb.toString());
            intent.putExtra("module_type", CommMentList2Activity.this.module_type);
            CommMentList2Activity.this.startActivity(intent);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (CommMentList2Activity.this.pageNum != 1) {
                CommMentList2Activity.this.mSwipeRefreshLayout.finishLoadMore(false);
                return;
            }
            CommMentList2Activity.this.mSwipeRefreshLayout.finishRefresh(false);
            if (CommMentList2Activity.this.commlist.size() > 0) {
                CommMentList2Activity.this.AlertToast("请求服务器失败");
                return;
            }
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            hotBean.setType(1);
            hotBean.setName("最热评论");
            CommMentList2Activity.this.commlist.add(hotBean);
            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
            hotBean2.setId("");
            hotBean2.setContent("暂无最热评论");
            CommMentList2Activity.this.commlist.add(hotBean2);
            CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
            hotBean3.setType(1);
            hotBean3.setName("最新评论");
            CommMentList2Activity.this.commlist.add(hotBean3);
            CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
            hotBean4.setId("");
            hotBean4.setContent("暂无最新评论");
            CommMentList2Activity.this.commlist.add(hotBean4);
            CommMentList2Activity commMentList2Activity = CommMentList2Activity.this;
            CommMentList2Activity commMentList2Activity2 = CommMentList2Activity.this;
            commMentList2Activity.mCommListAdapter = new CommentListAdapter(commMentList2Activity2.mContext, commMentList2Activity2.commlist, CommMentList2Activity.this);
            CommMentList2Activity commMentList2Activity3 = CommMentList2Activity.this;
            commMentList2Activity3.mPinnedSecListView.setAdapter((ListAdapter) commMentList2Activity3.mCommListAdapter);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                final CommentBean commentBean = (CommentBean) new Gson().fromJson(s2, CommentBean.class);
                if (!commentBean.getCode().equals("200")) {
                    CommMentList2Activity.this.emptyView.setVisibility(CommMentList2Activity.this.commlist.isEmpty() ? 0 : 8);
                    CommMentList2Activity commMentList2Activity = CommMentList2Activity.this;
                    commMentList2Activity.mPinnedSecListView.setVisibility(!commMentList2Activity.commlist.isEmpty() ? 0 : 8);
                    if (CommMentList2Activity.this.pageNum != 1) {
                        CommMentList2Activity.this.AlertToast(commentBean.getMessage());
                        CommMentList2Activity.this.mSwipeRefreshLayout.finishLoadMore(false);
                        return;
                    }
                    CommMentList2Activity.this.mSwipeRefreshLayout.finishRefresh(false);
                    if (CommMentList2Activity.this.commlist.size() == 0) {
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setType(1);
                        hotBean.setName("最热评论");
                        CommMentList2Activity.this.commlist.add(hotBean);
                        CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                        hotBean2.setId("");
                        hotBean2.setContent("暂无最热评论");
                        CommMentList2Activity.this.commlist.add(hotBean2);
                        CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                        hotBean3.setType(1);
                        hotBean3.setName("最新评论");
                        CommMentList2Activity.this.commlist.add(hotBean3);
                        CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                        hotBean4.setId("");
                        hotBean4.setContent("暂无最新评论");
                        CommMentList2Activity.this.commlist.add(hotBean4);
                        CommMentList2Activity commMentList2Activity2 = CommMentList2Activity.this;
                        CommMentList2Activity commMentList2Activity3 = CommMentList2Activity.this;
                        commMentList2Activity2.mCommListAdapter = new CommentListAdapter(commMentList2Activity3.mContext, commMentList2Activity3.commlist, CommMentList2Activity.this);
                        CommMentList2Activity commMentList2Activity4 = CommMentList2Activity.this;
                        commMentList2Activity4.mPinnedSecListView.setAdapter((ListAdapter) commMentList2Activity4.mCommListAdapter);
                    }
                    CommMentList2Activity.this.AlertToast(commentBean.getMessage());
                    return;
                }
                CommMentList2Activity.this.hot = commentBean.getData().getHot();
                CommMentList2Activity.this.time_line = commentBean.getData().getTime_line();
                if (CommMentList2Activity.this.pageNum == 1) {
                    CommMentList2Activity.this.mSwipeRefreshLayout.finishRefresh(true);
                    CommMentList2Activity commMentList2Activity5 = CommMentList2Activity.this;
                    commMentList2Activity5.positionTab = commMentList2Activity5.hot.size();
                    CommMentList2Activity.this.commlist.clear();
                    CommMentList2Activity.this.time_lines.clear();
                    CommentBean.DataBean.HotBean hotBean5 = new CommentBean.DataBean.HotBean();
                    hotBean5.setType(1);
                    String time_line_total = "0";
                    if (CommMentList2Activity.this.module_type == 1 || CommMentList2Activity.this.module_type == 4 || CommMentList2Activity.this.module_type == 5) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("最热评论(");
                        sb.append((CommMentList2Activity.this.hot == null || CommMentList2Activity.this.hot.isEmpty()) ? "0" : commentBean.getData().getHot_total());
                        sb.append(")");
                        hotBean5.setName(sb.toString());
                    } else {
                        hotBean5.setName("最热评论");
                    }
                    CommMentList2Activity.this.commlist.add(hotBean5);
                    if (CommMentList2Activity.this.hot.size() > 0) {
                        CommMentList2Activity.this.commlist.addAll(CommMentList2Activity.this.hot);
                        if ((CommMentList2Activity.this.module_type == 1 || CommMentList2Activity.this.module_type == 4 || CommMentList2Activity.this.module_type == 5) && commentBean.getData().getMore_hot() != null && "1".equals(commentBean.getData().getMore_hot())) {
                            CommentBean.DataBean.HotBean hotBean6 = new CommentBean.DataBean.HotBean();
                            hotBean6.setOtherView(3);
                            hotBean6.setName("最热评论(" + commentBean.getData().getHot_total() + ")");
                            CommMentList2Activity.this.commlist.add(hotBean6);
                            CommMentList2Activity.this.ishavehot = true;
                        }
                    } else {
                        CommentBean.DataBean.HotBean hotBean7 = new CommentBean.DataBean.HotBean();
                        hotBean7.setId("");
                        hotBean7.setContent("暂无最热评论");
                        CommMentList2Activity.this.commlist.add(hotBean7);
                    }
                    CommentBean.DataBean.HotBean hotBean8 = new CommentBean.DataBean.HotBean();
                    hotBean8.setType(1);
                    if (CommMentList2Activity.this.module_type == 1 || CommMentList2Activity.this.module_type == 4 || CommMentList2Activity.this.module_type == 5) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("最新评论(");
                        if (CommMentList2Activity.this.time_line != null && !CommMentList2Activity.this.time_line.isEmpty()) {
                            time_line_total = commentBean.getData().getTime_line_total();
                        }
                        sb2.append(time_line_total);
                        sb2.append(")");
                        hotBean8.setName(sb2.toString());
                    } else {
                        hotBean8.setName("最新评论");
                    }
                    CommMentList2Activity.this.commlist.add(hotBean8);
                    if (CommMentList2Activity.this.time_line.size() > 0) {
                        CommMentList2Activity.this.commlist.addAll(CommMentList2Activity.this.time_line);
                    } else {
                        CommentBean.DataBean.HotBean hotBean9 = new CommentBean.DataBean.HotBean();
                        hotBean9.setId("");
                        hotBean9.setContent("暂无最新评论");
                        CommMentList2Activity.this.commlist.add(hotBean9);
                    }
                    CommMentList2Activity.this.time_lines.addAll(CommMentList2Activity.this.time_line);
                    CommMentList2Activity commMentList2Activity6 = CommMentList2Activity.this;
                    CommMentList2Activity commMentList2Activity7 = CommMentList2Activity.this;
                    Context context = commMentList2Activity7.mContext;
                    List list = commMentList2Activity7.commlist;
                    List list2 = CommMentList2Activity.this.time_line;
                    int i2 = CommMentList2Activity.this.module_type;
                    String str = CommMentList2Activity.this.comment_type;
                    String str2 = CommMentList2Activity.this.question_id + "";
                    CommMentList2Activity commMentList2Activity8 = CommMentList2Activity.this;
                    commMentList2Activity6.mCommListAdapter = new CommentListAdapter(context, list, list2, i2, str, str2, commMentList2Activity8, commMentList2Activity8.isNewComzantong, CommMentList2Activity.this.isTrue, false, "", "0", CommMentList2Activity.this.viewType);
                    CommMentList2Activity commMentList2Activity9 = CommMentList2Activity.this;
                    commMentList2Activity9.mPinnedSecListView.setAdapter((ListAdapter) commMentList2Activity9.mCommListAdapter);
                    if (CommMentList2Activity.this.module_type == 1 || CommMentList2Activity.this.module_type == 4 || CommMentList2Activity.this.module_type == 5) {
                        CommMentList2Activity.this.lineselect2.setVisibility(0);
                        if ("1".equals(commentBean.getData().getShow_tips())) {
                            CommMentList2Activity.this.catview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    this.f12464c.lambda$onSuccess$0(commentBean, view);
                                }
                            });
                        }
                    }
                    if (CommMentList2Activity.this.hot.size() > 0 || CommMentList2Activity.this.time_line.size() > 0) {
                        CommMentList2Activity.this.getCommentAd();
                    }
                } else {
                    CommMentList2Activity.this.mPinnedSecListView.invalidateViews();
                    CommMentList2Activity.this.mSwipeRefreshLayout.finishLoadMore(true);
                    if (CommMentList2Activity.this.time_line.size() == 0) {
                        CommMentList2Activity.this.AlertToast("已经是最后一条");
                        CommMentList2Activity.this.mSwipeRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        CommMentList2Activity.this.commlist.addAll(CommMentList2Activity.this.time_line);
                        CommMentList2Activity.this.time_lines.addAll(CommMentList2Activity.this.time_line);
                        CommMentList2Activity commMentList2Activity10 = CommMentList2Activity.this;
                        commMentList2Activity10.mCommListAdapter.setRefeault(commMentList2Activity10.time_line);
                        CommMentList2Activity.this.mCommListAdapter.notifyDataSetChanged();
                    }
                }
                CommMentList2Activity.this.emptyView.setVisibility(CommMentList2Activity.this.commlist.isEmpty() ? 0 : 8);
                CommMentList2Activity commMentList2Activity11 = CommMentList2Activity.this;
                commMentList2Activity11.mPinnedSecListView.setVisibility(!commMentList2Activity11.commlist.isEmpty() ? 0 : 8);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static /* synthetic */ int access$112(CommMentList2Activity commMentList2Activity, int i2) {
        int i3 = commMentList2Activity.pageNum + i2;
        commMentList2Activity.pageNum = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        if (this.pageNum == 1) {
            this.break_point = (System.currentTimeMillis() / 1000) + "";
        }
        AjaxParams ajaxParams = new AjaxParams();
        if (this.module_type == 3) {
            ajaxParams.put("only_author", this.only_author);
        }
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("obj_id", this.question_id + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
        if (getIntent().getBooleanExtra("isNewCom", false)) {
            ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        }
        if (getIntent().getBooleanExtra("isNewComzantong", false)) {
            ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        }
        ajaxParams.put("break_point", this.break_point);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(0, 0, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(View view) {
        if (this.only_author.equals("1")) {
            AlertToast("查看全部评论");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.comment_lou.setBackground(ContextCompat.getDrawable(this, R.drawable.stoke_kongwrite));
                this.comment_lou.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                this.comment_lou.setBackground(ContextCompat.getDrawable(this, R.drawable.stoke_kongwrite_night));
                this.comment_lou.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
            }
            this.only_author = "0";
        } else {
            AlertToast("只看作者评论");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.comment_lou.setBackground(ContextCompat.getDrawable(this, R.drawable.stoke_fullwrite));
                this.comment_lou.setTextColor(ContextCompat.getColor(this, R.color.app_theme_red));
            } else {
                this.comment_lou.setBackground(ContextCompat.getDrawable(this, R.drawable.stoke_fullwrite_night));
                this.comment_lou.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            this.only_author = "1";
        }
        this.pageNum = 1;
        getCommentListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$11() {
        this.mSwipeRefreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$12(View view) {
        Intent intent;
        int i2 = this.module_type;
        if (i2 == 1 || i2 == 3) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SYStem_Red_Dot, false, this.mContext);
            EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
        }
        if (this.module_type == 3) {
            intent = new Intent(this.mContext, (Class<?>) MyCommentListActivity.class);
            intent.putExtra("title", "评论我");
            intent.putExtra("type", 1);
            intent.putExtra("comment_type", this.comment_type);
            intent.putExtra("module_type", this.module_type);
        } else {
            intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
            intent.putExtra("question_id", this.question_id);
            intent.putExtra("module_type", this.module_type);
            intent.putExtra("comment_type", "2");
            intent.putExtra("isNewCom", true);
            intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
            intent.putExtra("iscomValu", true);
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$13(View view) {
        Intent intent;
        int i2 = this.module_type;
        if (i2 == 1 || i2 == 4 || i2 == 5) {
            intent = new Intent(this.mContext, (Class<?>) CommentSeachActivity.class);
            intent.putExtra("question_id", "" + this.question_id);
            intent.putExtra("module_type", this.module_type);
        } else {
            intent = new Intent(this.mContext, (Class<?>) WebViewActivity.class);
            intent.putExtra("title", "关于评论的说明");
            intent.putExtra("web_url", NetworkRequestsURL.mCommlogUrl);
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2() {
        this.mPinnedSecListView.setSelection(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        isSelect(true, false);
        this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.s2
            @Override // java.lang.Runnable
            public final void run() {
                this.f13816c.lambda$init$1();
            }
        });
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.t2
            @Override // java.lang.Runnable
            public final void run() {
                this.f13938c.lambda$init$2();
            }
        }, 130L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(int i2) {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(i2, 0, 50);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(int i2) {
        this.mPinnedSecListView.setSelection(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6() {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(this.positionTab + 2, 0, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7() {
        this.mPinnedSecListView.setSelection(this.positionTab + 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(View view) {
        isSelect(false, true);
        int i2 = this.positionTab;
        if (i2 <= 0) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.w2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14136c.lambda$init$6();
                }
            });
            this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.x2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14169c.lambda$init$7();
                }
            }, 130L);
        } else {
            final int i3 = this.ishavehot ? i2 + 2 : i2 + 1;
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.u2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13970c.lambda$init$4(i3);
                }
            });
            this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.v2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14008c.lambda$init$5(i3);
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15(View view) {
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.r2
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f13755a.lambda$setListenerForWidget$14(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCommentIcon(boolean isComment) {
        if (isComment) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun);
                return;
            } else {
                this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg);
        } else {
            this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg_night);
        }
    }

    private void showGrade_jingyan() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBottomView() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        ajaxParams.put("module_type", this.module_type + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getstatApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommMentList2Activity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                QuestionStatDataBean questionStatDataBean;
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if (!new JSONObject(s2).optString("code", "").equals("200") || (questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class)) == null) {
                        return;
                    }
                    String strValueOf = String.valueOf(questionStatDataBean.getData().getIs_praise());
                    if (TextUtils.isEmpty(strValueOf) || "0".equals(strValueOf)) {
                        if (SkinManager.getCurrentSkinType(CommMentList2Activity.this.mContext) == 0) {
                            CommMentList2Activity.this.iv_topic_detail_zan.setImageResource(R.drawable.dianzancourse);
                        } else {
                            CommMentList2Activity.this.iv_topic_detail_zan.setImageResource(R.drawable.dianzancourse_night);
                        }
                    } else if ("1".equals(strValueOf)) {
                        if (SkinManager.getCurrentSkinType(CommMentList2Activity.this.mContext) == 0) {
                            CommMentList2Activity.this.iv_topic_detail_zan.setImageResource(R.drawable.youdianzan);
                        } else {
                            CommMentList2Activity.this.iv_topic_detail_zan.setImageResource(R.drawable.youdianzan_night);
                        }
                    }
                    if (TextUtils.equals("1", String.valueOf(questionStatDataBean.getData().getIs_comment()))) {
                        CommMentList2Activity.this.refreshCommentIcon(true);
                    } else {
                        CommMentList2Activity.this.refreshCommentIcon(false);
                    }
                    EventBus.getDefault().post(new RefreshQuestionBottomIconEvent(CommMentList2Activity.this.question_id + "", questionStatDataBean.getData().getIs_praise(), questionStatDataBean.getData().getIs_comment()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getCommentAd() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        YJYHttpUtils.get(this, NetworkRequestsURL.adsInCommentApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommMentList2Activity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (t2 != null) {
                    t2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.optString("code").equals("200")) {
                        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, CommMentList2Activity.this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, CommMentList2Activity.this.mContext, 0L).longValue()) / 1000) - jSONObject.optJSONObject("data").optLong("time_interval", 0L) : 0L) < 0 || CommMentList2Activity.this.commlist.size() <= 1 || TextUtils.isEmpty(jSONObject.optJSONObject("data").optString("img"))) {
                            return;
                        }
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setAds(jSONObject.optString("data"));
                        if (CommMentList2Activity.this.hot.size() > 0) {
                            CommMentList2Activity.this.commlist.add(2, hotBean);
                            CommMentList2Activity commMentList2Activity = CommMentList2Activity.this;
                            commMentList2Activity.mCommListAdapter.setList(commMentList2Activity.commlist);
                            CommMentList2Activity.this.mCommListAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (CommMentList2Activity.this.time_line.size() > 0) {
                            CommMentList2Activity.this.commlist.add(4, hotBean);
                            CommMentList2Activity commMentList2Activity2 = CommMentList2Activity.this;
                            commMentList2Activity2.mCommListAdapter.setList(commMentList2Activity2.commlist);
                            CommMentList2Activity.this.mCommListAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0238  */
    @Override // com.psychiatrygarden.activity.BaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void init() {
        /*
            Method dump skipped, instructions count: 787
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.CommMentList2Activity.init():void");
    }

    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            this.mCommListAdapter.getputData(data.getBundleExtra("bundleIntent"));
        } else {
            if (requestCode != 1) {
                return;
            }
            putComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(CommonParameter.SYStem_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext)) {
                this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg_new);
            } else if (getIntent().getExtras().getBoolean("isCommentTrue", false)) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun);
                } else {
                    this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun_night);
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg);
            } else {
                this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg_night);
            }
        }
        if ("showVideo".equals(str)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.GET_CONTENT");
            intent.setType(MimeTypes.VIDEO_ALL);
            startActivityForResult(intent, 1000);
        }
        if ("mCommentResult".equals(str)) {
            getCommentListData();
            updateBottomView();
        } else if ("delReplyAndLoadData".equals(str)) {
            this.mSwipeRefreshLayout.autoRefresh();
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.pageNum = 1;
        getCommentListData();
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("obj_id", this.question_id + "");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommMentList2Activity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CommMentList2Activity.this.AlertToast("请求服务器超时！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals("401")) {
                            new CusomNewDialog(CommMentList2Activity.this.mContext).setMessage(jSONObject.optString("message")).show();
                            return;
                        } else {
                            NewToast.showShort(CommMentList2Activity.this.mContext, jSONObject.optString("message"), 0).show();
                            return;
                        }
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", CommMentList2Activity.this.mContext);
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        CommMentList2Activity.this.commlist.removeAll(CommMentList2Activity.this.time_lines);
                        if (CommMentList2Activity.this.time_lines.size() == 0) {
                            CommMentList2Activity.this.commlist.remove(3);
                        }
                        CommMentList2Activity.this.time_lines.add(0, hotBean);
                        CommMentList2Activity.this.commlist.addAll(CommMentList2Activity.this.time_lines);
                        for (CommentBean.DataBean.HotBean hotBean2 : CommMentList2Activity.this.mCommListAdapter.getList()) {
                            if (hotBean2.getType() == 1 && hotBean2.getName().contains("最新")) {
                                String name = hotBean2.getName();
                                int length = name.length();
                                StringBuilder sb = new StringBuilder();
                                for (int i2 = 0; i2 < length; i2++) {
                                    char cCharAt = name.charAt(i2);
                                    if (Character.isDigit(cCharAt)) {
                                        sb.append(cCharAt);
                                    }
                                }
                                String string3 = sb.toString();
                                if (string3.length() > 0) {
                                    hotBean2.setName(name.replace(string3, String.valueOf(Integer.parseInt(string3) + 1)));
                                }
                            }
                        }
                        CommMentList2Activity.this.mCommListAdapter.notifyDataSetChanged();
                    }
                    CommMentList2Activity.this.updateBottomView();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_COMMENT_NUM);
                    CommonUtil.showFristDialog(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_comm_ment_list2);
        this.module_type = getIntent().getIntExtra("module_type", 0);
        this.comment_type = getIntent().getStringExtra("comment_type");
        this.viewType = getIntent().getStringExtra("viewType");
        int i2 = this.module_type;
        if (i2 != 2) {
            if (i2 != 3) {
                this.question_id = getIntent().getLongExtra("question_id", 0L);
            } else {
                this.question_id = Long.parseLong(getIntent().getExtras().getString("article"));
            }
        }
        if (getIntent().getBooleanExtra("iscomValu", false)) {
            this.isNewComzantong = true;
            this.isTrue = true;
        } else if (this.module_type == 3) {
            this.isNewComzantong = false;
            this.isTrue = false;
        } else {
            this.isNewComzantong = true;
            this.isTrue = true;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12425c.lambda$setListenerForWidget$15(view);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateCommentPraiseEvent event) {
        if (TextUtils.isEmpty(event.getQuestionId())) {
            return;
        }
        if (event.getQuestionId().equals(this.question_id + "")) {
            updateBottomView();
        }
    }
}
