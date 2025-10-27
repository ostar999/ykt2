package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.CommentListAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.open.SocialConstants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CommentNewActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int REFRESH_COMPLETE = 272;
    private View addFooterView;
    private String break_point;
    private TextView comment_lou;
    private String comment_type;
    private View emptyView;
    private boolean isLoadMore;
    private String isProhibit;
    public ImageView iv_topic_detail_msg;
    private String mAppId;
    public CommentListAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SmartRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    public ImageView pinglunlog;
    private long question_id;
    public TextView tv_topic_detail_add_comment;
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private int pageNum = 1;
    private String only_author = "0";
    private boolean isNewComzantong = false;
    private String fromType = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.CommentNewActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                CommentNewActivity.this.pageNum = 1;
                CommentNewActivity.this.getCommentListData();
            }
        }
    };

    public static /* synthetic */ int access$012(CommentNewActivity commentNewActivity, int i2) {
        int i3 = commentNewActivity.pageNum + i2;
        commentNewActivity.pageNum = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
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
    public /* synthetic */ void lambda$init$2() {
        this.mSwipeRefreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(this.mContext, "本帖已被锁定，不支持回帖");
            return;
        }
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.i3
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f12494a.lambda$setListenerForWidget$3(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    public void getCommentListData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.fromType.equals(SocialConstants.PARAM_SOURCE)) {
            str = NetworkRequestsURL.soutceCommentList;
            ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
            ajaxParams.put("search_type", getIntent().getStringExtra("search_type"));
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
        } else {
            str = NetworkRequestsURL.mCommentList;
            if (this.module_type == 3) {
                ajaxParams.put("only_author", this.only_author);
            }
            ajaxParams.put("module_type", this.module_type + "");
            ajaxParams.put("obj_id", this.question_id + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
            if (this.pageNum == 1) {
                this.break_point = (System.currentTimeMillis() / 1000) + "";
            }
            ajaxParams.put("break_point", this.break_point);
            if (getIntent().getBooleanExtra("isNewCom", false)) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mycomment");
            }
            if (getIntent().getBooleanExtra("isNewComzantong", false)) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mypraise");
            }
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentNewActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CommentNewActivity.this.isLoadMore) {
                    CommentNewActivity.this.mSwipeRefreshLayout.finishLoadMore(false);
                } else {
                    CommentNewActivity.this.mSwipeRefreshLayout.finishRefresh(false);
                }
                if (CommentNewActivity.this.pageNum == 1) {
                    if (CommentNewActivity.this.commlist.size() > 0) {
                        CommentNewActivity.this.AlertToast("请求服务器失败");
                        return;
                    }
                    CommentNewActivity commentNewActivity = CommentNewActivity.this;
                    CommentNewActivity commentNewActivity2 = CommentNewActivity.this;
                    Context context = commentNewActivity2.mContext;
                    List list = commentNewActivity2.commlist;
                    CommentNewActivity commentNewActivity3 = CommentNewActivity.this;
                    commentNewActivity.mCommListAdapter = new CommentListAdapter(context, list, commentNewActivity3, commentNewActivity3.fromType.equals(SocialConstants.PARAM_SOURCE), CommentNewActivity.this.mAppId);
                    CommentNewActivity commentNewActivity4 = CommentNewActivity.this;
                    commentNewActivity4.mPinnedSecListView.setAdapter((ListAdapter) commentNewActivity4.mCommListAdapter);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String arg0) {
                super.onSuccess((AnonymousClass3) arg0);
                try {
                    CommentBean commentBean = (CommentBean) new Gson().fromJson(arg0, CommentBean.class);
                    if (!commentBean.getCode().equals("200")) {
                        if (CommentNewActivity.this.isLoadMore) {
                            CommentNewActivity.this.mSwipeRefreshLayout.finishLoadMore(false);
                        } else {
                            CommentNewActivity.this.mSwipeRefreshLayout.finishRefresh(false);
                        }
                        if (CommentNewActivity.this.isLoadMore) {
                            CommentNewActivity.this.AlertToast(commentBean.getMessage());
                            return;
                        }
                        if (CommentNewActivity.this.commlist.size() != 0) {
                            CommentNewActivity.this.AlertToast(commentBean.getMessage());
                            return;
                        }
                        CommentNewActivity commentNewActivity = CommentNewActivity.this;
                        if (commentNewActivity.mCommListAdapter == null) {
                            CommentNewActivity commentNewActivity2 = CommentNewActivity.this;
                            Context context = commentNewActivity2.mContext;
                            List list = commentNewActivity2.commlist;
                            CommentNewActivity commentNewActivity3 = CommentNewActivity.this;
                            commentNewActivity.mCommListAdapter = new CommentListAdapter(context, list, commentNewActivity3, commentNewActivity3.fromType.equals(SocialConstants.PARAM_SOURCE), CommentNewActivity.this.mAppId);
                        }
                        CommentNewActivity commentNewActivity4 = CommentNewActivity.this;
                        commentNewActivity4.mPinnedSecListView.setAdapter((ListAdapter) commentNewActivity4.mCommListAdapter);
                        return;
                    }
                    if (CommentNewActivity.this.isLoadMore) {
                        CommentNewActivity.this.mSwipeRefreshLayout.finishLoadMore(true);
                    } else {
                        CommentNewActivity.this.mSwipeRefreshLayout.finishRefresh(true);
                    }
                    CommentNewActivity.this.time_line = commentBean.getData().getTime_line();
                    int i2 = 8;
                    if (CommentNewActivity.this.isLoadMore) {
                        CommentNewActivity.this.mPinnedSecListView.setVisibility(0);
                        CommentNewActivity commentNewActivity5 = CommentNewActivity.this;
                        commentNewActivity5.mPinnedSecListView.removeFooterView(commentNewActivity5.addFooterView);
                        CommentNewActivity.this.addFooterView.setVisibility(8);
                        CommentNewActivity.this.mPinnedSecListView.invalidateViews();
                        if (CommentNewActivity.this.time_line.size() == 0) {
                            CommentNewActivity.this.mSwipeRefreshLayout.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        CommentNewActivity.this.commlist.addAll(CommentNewActivity.this.time_line);
                        CommentNewActivity.this.time_lines.addAll(CommentNewActivity.this.time_line);
                        CommentNewActivity commentNewActivity6 = CommentNewActivity.this;
                        commentNewActivity6.mCommListAdapter.setRefeault(commentNewActivity6.time_line);
                        CommentNewActivity.this.mCommListAdapter.notifyDataSetChanged();
                        return;
                    }
                    CommentNewActivity.this.time_lines.clear();
                    CommentNewActivity.this.commlist.clear();
                    CommentNewActivity commentNewActivity7 = CommentNewActivity.this;
                    commentNewActivity7.mPinnedSecListView.setVisibility(commentNewActivity7.time_line.size() > 0 ? 0 : 8);
                    View view = CommentNewActivity.this.emptyView;
                    if (CommentNewActivity.this.time_line.size() <= 0) {
                        i2 = 0;
                    }
                    view.setVisibility(i2);
                    if (CommentNewActivity.this.time_line.size() > 0) {
                        CommentNewActivity.this.commlist.addAll(CommentNewActivity.this.time_line);
                    }
                    CommentNewActivity.this.time_lines.addAll(CommentNewActivity.this.time_line);
                    CommentNewActivity commentNewActivity8 = CommentNewActivity.this;
                    if (commentNewActivity8.mCommListAdapter == null) {
                        CommentNewActivity commentNewActivity9 = CommentNewActivity.this;
                        Context context2 = commentNewActivity9.mContext;
                        List list2 = commentNewActivity9.commlist;
                        List list3 = CommentNewActivity.this.time_line;
                        int i3 = CommentNewActivity.this.module_type;
                        String str2 = CommentNewActivity.this.comment_type;
                        String str3 = CommentNewActivity.this.question_id + "";
                        CommentNewActivity commentNewActivity10 = CommentNewActivity.this;
                        commentNewActivity8.mCommListAdapter = new CommentListAdapter(context2, list2, list3, i3, str2, str3, commentNewActivity10, commentNewActivity10.isNewComzantong, true, CommentNewActivity.this.fromType.equals(SocialConstants.PARAM_SOURCE), CommentNewActivity.this.mAppId, CommentNewActivity.this.isProhibit, "");
                    }
                    CommentNewActivity commentNewActivity11 = CommentNewActivity.this;
                    commentNewActivity11.mPinnedSecListView.setAdapter((ListAdapter) commentNewActivity11.mCommListAdapter);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CommentNewActivity.this.mSwipeRefreshLayout.finishRefresh(false);
                    CommentNewActivity.this.mSwipeRefreshLayout.finishLoadMore(false);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x01a9  */
    @Override // com.psychiatrygarden.activity.BaseActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void init() throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.CommentNewActivity.init():void");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(CommonParameter.SYStem_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext)) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
                return;
            } else {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg));
                return;
            }
        }
        if (str.equals(CommonParameter.COMMENT_BG_SHOW)) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.alpha = 0.4f;
            getWindow().addFlags(2);
            getWindow().setAttributes(attributes);
            return;
        }
        if (str.equals(CommonParameter.COMMENT_BG_DISMISS)) {
            WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
            attributes2.alpha = 1.0f;
            getWindow().addFlags(2);
            getWindow().setAttributes(attributes2);
            return;
        }
        if ("delReplyAndLoadData".equals(str)) {
            this.pageNum = 1;
            getCommentListData();
        } else if ("mCommentResult".equals(str)) {
            this.pageNum = 1;
            getCommentListData();
        }
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutComment;
        if (this.fromType.equals(SocialConstants.PARAM_SOURCE)) {
            str = NetworkRequestsURL.submitComment;
            ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
        } else {
            ajaxParams.put("module_type", this.module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("obj_id", this.question_id + "");
        }
        ajaxParams.put("content", b3.getString("content"));
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentNewActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals("401")) {
                            new CusomNewDialog(CommentNewActivity.this.mContext).setMessage(jSONObject.optString("message")).show();
                            return;
                        } else {
                            NewToast.showShort(CommentNewActivity.this.mContext, jSONObject.optString("message"), 0).show();
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
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        CommentNewActivity.this.commlist.removeAll(CommentNewActivity.this.time_lines);
                        if (CommentNewActivity.this.time_lines.size() == 0 && CommentNewActivity.this.commlist.size() > 2) {
                            CommentNewActivity.this.commlist.remove(3);
                        }
                        CommentNewActivity.this.time_lines.add(0, hotBean);
                        CommentNewActivity.this.commlist.addAll(CommentNewActivity.this.time_lines);
                        CommentNewActivity commentNewActivity = CommentNewActivity.this;
                        commentNewActivity.mPinnedSecListView.setVisibility(commentNewActivity.time_lines.size() > 0 ? 0 : 8);
                        CommentNewActivity.this.emptyView.setVisibility(CommentNewActivity.this.time_lines.size() > 0 ? 8 : 0);
                        CommentNewActivity.this.mCommListAdapter.notifyDataSetChanged();
                    }
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_COMMENT_NUM);
                    EventBus.getDefault().post("delReplyAndLoadData");
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
        setNewStyleStatusBarColor();
        setContentView(R.layout.activity_comm_ment_list2);
        String string = getIntent().getExtras().getString(com.alipay.sdk.authjs.a.f3174g);
        this.fromType = string;
        if (string.equals(SocialConstants.PARAM_SOURCE)) {
            this.question_id = getIntent().getLongExtra("question_id", 0L);
        } else {
            this.module_type = getIntent().getIntExtra("module_type", 0);
            this.comment_type = getIntent().getStringExtra("comment_type");
            int i2 = this.module_type;
            if (i2 == 3) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String string2 = extras.getString("article");
                    if (!TextUtils.isEmpty(string2) && string2.matches("-?[0-9]+")) {
                        this.question_id = Long.parseLong(string2);
                    }
                }
            } else if (i2 == 12 || i2 == 16) {
                this.question_id = getIntent().getLongExtra("circle_id", 0L);
            } else {
                this.question_id = getIntent().getLongExtra("question_id", 0L);
            }
        }
        this.mAppId = getIntent().getStringExtra("app_id");
        String stringExtra = getIntent().getStringExtra("isProhibit");
        this.isProhibit = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            this.isProhibit = "0";
        }
        if (getIntent().getBooleanExtra("iscomValu", false)) {
            this.isNewComzantong = true;
        } else {
            this.isNewComzantong = this.module_type != 3;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.j3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12545c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
