package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.VestReplyActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.MessageNoticeTypeBean;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReplyCollectionAct;
import com.ykb.ebook.model.BookReview;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SystemCommentNoticeAct extends BaseActivity {
    private SystemCommentNoticeAdp mAdapter;
    private TextView mBtnToSet;
    private LinearLayout mLyNotice;
    private RelativeLayout mLyUnReadMsgView;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView mTvUnReadMsgCount;

    public static /* synthetic */ int access$008(SystemCommentNoticeAct systemCommentNoticeAct) {
        int i2 = systemCommentNoticeAct.mPage;
        systemCommentNoticeAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("token", UserConfig.getInstance().getUser().getToken());
        ajaxParams.put("secret", UserConfig.getInstance().getUser().getSecret());
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        ajaxParams.put("break_point", AndroidBaseUtils.GetUTCTime().toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mGetreplyMessageURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemCommentNoticeAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SystemCommentNoticeAct.this.mRefresh.finishRefresh();
                NewToast.showShort(SystemCommentNoticeAct.this.mContext, "请求失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                SystemCommentNoticeAct.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        MyMessageCommentBean myMessageCommentBean = (MyMessageCommentBean) new Gson().fromJson(t2, MyMessageCommentBean.class);
                        if (SystemCommentNoticeAct.this.mPage == 1) {
                            SystemCommentNoticeAct.this.mAdapter.setNewData(myMessageCommentBean.getData());
                            if (myMessageCommentBean.getData().size() < 10) {
                                SystemCommentNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            }
                        } else if (myMessageCommentBean.getData() == null || myMessageCommentBean.getData().size() <= 0) {
                            SystemCommentNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            SystemCommentNoticeAct.this.mAdapter.addData((Collection) myMessageCommentBean.getData());
                            if (myMessageCommentBean.getData().size() < 10) {
                                SystemCommentNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                SystemCommentNoticeAct.this.mRefresh.finishLoadMore();
                            }
                        }
                    } else {
                        NewToast.showShort(SystemCommentNoticeAct.this.mContext, jSONObject.getString("message") + "", 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getMessageNotice() {
        AjaxParams ajaxParams = new AjaxParams();
        hideProgressDialog();
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getMessageNotice, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemCommentNoticeAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SystemCommentNoticeAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        MessageNoticeTypeBean messageNoticeTypeBean = (MessageNoticeTypeBean) new Gson().fromJson(s2, MessageNoticeTypeBean.class);
                        if (messageNoticeTypeBean.getData() != null) {
                            if (messageNoticeTypeBean.getData().getComment().equals("1")) {
                                if (SystemCommentNoticeAct.this.mLyNotice.getVisibility() == 0) {
                                    SystemCommentNoticeAct.this.mLyNotice.setVisibility(8);
                                }
                            } else if (SystemCommentNoticeAct.this.mLyNotice.getVisibility() == 8) {
                                SystemCommentNoticeAct.this.mLyNotice.setVisibility(0);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SystemCommentNoticeAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        goActivity(VestReplyActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MyMessageCommentBean.DataBean item = this.mAdapter.getItem(i2);
        try {
            item.setIs_read("1");
            this.mAdapter.notifyItemChanged(i2);
            boolean z2 = true;
            if (item.getModule_type() == 100) {
                if (item.getTarget_params() != null && item.getTarget_params().getIs_del().equals("1")) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，图书已隐藏或删除");
                    return;
                }
                BookReview bookReview = new BookReview();
                bookReview.setId(item.getReply().get(0).getId());
                bookReview.setSupport(Integer.parseInt(item.getReply().get(0).getIs_support()));
                bookReview.setOpposition(Integer.parseInt(item.getReply().get(0).getIs_oppose()));
                bookReview.setReplyCount(Integer.parseInt(item.getReply().get(0).getReply_count()));
                bookReview.setAvatar(item.getReply().get(0).getAvatar());
                bookReview.setSupportCount(item.getReply().get(0).getSupport_count());
                bookReview.setOppositionCount(item.getReply().get(0).getOpposition_count());
                bookReview.setSchool(item.getReply().get(0).getSchool());
                bookReview.setComment(item.getReply().get(0).getComment());
                bookReview.setCtime(item.getReply().get(0).getCtime());
                bookReview.setNickname(item.getTarget_params().getAuthor_name());
                if (TextUtils.isEmpty(item.getReply().get(0).getReview_type()) || !item.getReply().get(0).getReview_type().equals("1")) {
                    z2 = false;
                }
                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                String admin = UserConfig.getInstance().getUser().getAdmin();
                String avatar = UserConfig.getInstance().getUser().getAvatar();
                if (z2) {
                    ReplyCollectionAct.INSTANCE.newIntent(this, item.getTarget_params().getId(), bookReview, true, UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret());
                    return;
                }
                ReplyCollectionAct.INSTANCE.newIntent(this, item.getTarget_params().getId(), item.getReply().get(0).getChapter_id(), item.getReply().get(0).getParagraph_id(), item.getReply().get(0).getParagraph_content(), bookReview, false, UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret());
                return;
            }
            if (item.getModule_type() == 101) {
                if (item.getTarget_params() != null && item.getTarget_params().getIs_del().equals("1")) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，资料已隐藏或删除");
                    return;
                }
                Intent intent = new Intent(this.mContext, (Class<?>) DiscussPublicActivity.class);
                intent.putExtra("title", "评论我");
                intent.putExtra("comment_type", "2");
                intent.putExtra("isReplyCollection", true);
                intent.putExtra("module_type", item.getModule_type());
                intent.putExtra("obj_id", item.getObj_id());
                intent.putExtra("comment_id", item.getComment_id() + "");
                intent.putExtra("commentEnum", DiscussStatus.CommentOnMy);
                startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(this.mContext, (Class<?>) DiscussPublicActivity.class);
            intent2.putExtra("title", "评论我");
            intent2.putExtra("comment_type", "2");
            intent2.putExtra("isReplyCollection", true);
            intent2.putExtra("module_type", item.getModule_type());
            intent2.putExtra("obj_id", item.getComment_id() + "");
            intent2.putExtra("commentEnum", DiscussStatus.CommentOnMy);
            intent2.putExtra("isProhibit", item.getTarget_params().getIs_prohibit());
            if (item.getModule_type() == 12 && item.getTarget_params() != null && item.getTarget_params().getIs_vip().equals("1") && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                intent2.putExtra("noAccess", true);
            }
            startActivity(intent2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        startActivity(MessageNoticeSetAct.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) SystemCommentNoticeAct.class);
    }

    public void getReadot() {
        YJYHttpUtils.get(this, NetworkRequestsURL.virtualUserReplyRedDotApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemCommentNoticeAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    String strOptString = new JSONObject(s2).optJSONObject("data").optString("show_dot");
                    String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("count");
                    if ("1".equals(strOptString)) {
                        SystemCommentNoticeAct.this.mTvUnReadMsgCount.setVisibility(0);
                        SystemCommentNoticeAct.this.mTvUnReadMsgCount.setText(strOptString2);
                    } else {
                        SystemCommentNoticeAct.this.mTvUnReadMsgCount.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mLyNotice = (LinearLayout) findViewById(R.id.ly_set_notice);
        this.mBtnToSet = (TextView) findViewById(R.id.btn_to_set);
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLyUnReadMsgView = (RelativeLayout) findViewById(R.id.ly_comment_msg);
        this.mTvUnReadMsgCount = (TextView) findViewById(R.id.comment_unreadnum);
        View viewFindViewById = findViewById(R.id.tabbar);
        textView.setText("评论");
        viewFindViewById.setBackgroundColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#121622" : PLVAuthorizationBean.FCOLOR_DEFAULT));
        this.mBtnActionbarRight.setVisibility(8);
        SystemCommentNoticeAdp systemCommentNoticeAdp = new SystemCommentNoticeAdp(this);
        this.mAdapter = systemCommentNoticeAdp;
        this.mRecyclerView.setAdapter(systemCommentNoticeAdp);
        if (UserConfig.isLogin() && "1".equals(UserConfig.getInstance().getUser().getAdmin())) {
            this.mLyUnReadMsgView.setVisibility(0);
        } else {
            this.mLyUnReadMsgView.setVisibility(8);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13901c.lambda$init$0(view);
            }
        });
        this.mLyUnReadMsgView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13903c.lambda$init$1(view);
            }
        });
        getReadot();
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.SystemCommentNoticeAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                SystemCommentNoticeAct.access$008(SystemCommentNoticeAct.this);
                SystemCommentNoticeAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                SystemCommentNoticeAct.this.mPage = 1;
                SystemCommentNoticeAct.this.getData();
            }
        });
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_empty)).setVisibility(0);
        this.mAdapter.setEmptyView(viewInflate);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.t0
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13905a.lambda$init$2(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getMessageNotice();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_common_notice_list);
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnToSet.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13899c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
