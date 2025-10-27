package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.plv.socket.user.PLVAuthorizationBean;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.setting.PraiseNoticeAdp;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.MessageNoticeTypeBean;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateCommentInfoEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReplyCollectionAct;
import com.ykb.ebook.model.BookReview;
import de.greenrobot.event.EventBus;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PraiseNoticeAct extends BaseActivity {
    private PraiseNoticeAdp mAdapter;
    private TextView mBtnToSet;
    private LinearLayout mLyNotice;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;

    public static /* synthetic */ int access$008(PraiseNoticeAct praiseNoticeAct) {
        int i2 = praiseNoticeAct.mPage;
        praiseNoticeAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void circleOpporSuport(String id, int type, int flag) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", id);
        ajaxParams.put("type", "" + type);
        ajaxParams.put("flag", "" + flag);
        YJYHttpUtils.post(this.mContext, 16 == getIntent().getExtras().getInt("module_type", 12) ? NetworkRequestsURL.getforumDpraiseInfoApi : NetworkRequestsURL.interAction, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    PraiseNoticeAct.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void circleToSuport(String id, int type, int flag, int moduleType) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", id);
        ajaxParams.put("type", "" + type);
        ajaxParams.put("flag", "" + flag);
        YJYHttpUtils.post(this.mContext, 16 == moduleType ? NetworkRequestsURL.getforumDpraiseInfoApi : NetworkRequestsURL.interAction, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    PraiseNoticeAct.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("break_point", AndroidBaseUtils.GetUTCTime().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getPraiseNoticeList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PraiseNoticeAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                PraiseNoticeAct.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        MyMessageCommentBean myMessageCommentBean = (MyMessageCommentBean) new Gson().fromJson(t2, MyMessageCommentBean.class);
                        if (myMessageCommentBean.getData() != null) {
                            if (PraiseNoticeAct.this.mPage == 1) {
                                PraiseNoticeAct.this.mAdapter.setNewData(myMessageCommentBean.getData());
                                if (myMessageCommentBean.getData().size() < 10) {
                                    PraiseNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                }
                            } else if (myMessageCommentBean.getData() == null || myMessageCommentBean.getData().size() <= 0) {
                                PraiseNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                PraiseNoticeAct.this.mAdapter.addData((Collection) myMessageCommentBean.getData());
                                if (myMessageCommentBean.getData().size() < 10) {
                                    PraiseNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    PraiseNoticeAct.this.mRefresh.finishLoadMore();
                                }
                            }
                        }
                    } else {
                        PraiseNoticeAct.this.AlertToast(jSONObject.getString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getMessageNotice() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getMessageNotice, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PraiseNoticeAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                PraiseNoticeAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        MessageNoticeTypeBean messageNoticeTypeBean = (MessageNoticeTypeBean) new Gson().fromJson(s2, MessageNoticeTypeBean.class);
                        if (messageNoticeTypeBean.getData() != null) {
                            if (messageNoticeTypeBean.getData().getPraise().equals("1")) {
                                if (PraiseNoticeAct.this.mLyNotice.getVisibility() == 0) {
                                    PraiseNoticeAct.this.mLyNotice.setVisibility(8);
                                }
                            } else if (PraiseNoticeAct.this.mLyNotice.getVisibility() == 8) {
                                PraiseNoticeAct.this.mLyNotice.setVisibility(0);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                PraiseNoticeAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpToReplayListPage(MyMessageCommentBean.DataBean item) {
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
            boolean z2 = !TextUtils.isEmpty(item.getReply().get(0).getReview_type()) && item.getReply().get(0).getReview_type().equals("1");
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
            String admin = UserConfig.getInstance().getUser().getAdmin();
            String avatar = UserConfig.getInstance().getUser().getAvatar();
            if (z2) {
                ReplyCollectionAct.INSTANCE.newIntent(this, item.getTarget_params().getId(), bookReview, true, UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret());
                return;
            } else {
                ReplyCollectionAct.INSTANCE.newIntent(this, item.getTarget_params().getId(), item.getReply().get(0).getChapter_id(), item.getReply().get(0).getParagraph_id(), item.getReply().get(0).getParagraph_content(), bookReview, false, UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret());
                return;
            }
        }
        if (item.getModule_type() == 101 && item.getTarget_params() != null && item.getTarget_params().getIs_del().equals("1")) {
            ToastUtil.shortToast(ProjectApp.instance(), "抱歉，资料已隐藏或删除");
            return;
        }
        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
        hotBean.setId(item.getReply().get(0).getId());
        hotBean.setObj_id(item.getReply().get(0).getObj_id());
        hotBean.setUser_id(item.getReply().get(0).getUser_id());
        hotBean.setReply_primary_id(item.getReply().get(0).getReply_primary_id());
        hotBean.setParent_id(item.getReply().get(0).getId());
        hotBean.setNickname(item.getReply().get(0).getNickname());
        hotBean.setPraise_num(item.getReply().get(0).getPraise_num());
        hotBean.setOppose_num(item.getReply().get(0).getOppose_num());
        hotBean.setReply_num(item.getReply().get(0).getReply_num());
        hotBean.setModule_type(item.getModule_type() + "");
        hotBean.setAvatar(item.getReply().get(0).getAvatar());
        hotBean.setIs_praise(item.getReply().get(0).getIs_praise());
        hotBean.setIs_oppose(item.getReply().get(0).getIs_oppose());
        hotBean.setContent(item.getReply().get(0).getContent());
        hotBean.setCtime(item.getReply().get(0).getTimestr());
        hotBean.setC_imgs(item.getReply().get(0).getC_imgs());
        hotBean.setVideo_id(item.getReply().get(0).getVideo_id());
        hotBean.setSchool(item.getReply().get(0).getSchool());
        if (item.getModule_type() == 101) {
            hotBean.setFile_id(item.getReply().get(0).getObj_id());
        }
        hotBean.setSchool("");
        Intent intent = new Intent(this, (Class<?>) CommentReplyActivity.class);
        intent.putExtra("is_replybean", false);
        intent.putExtra("comment_type", item.getComment_type() + "");
        intent.putExtra("module_type", item.getModule_type());
        intent.putExtra("bean", hotBean);
        intent.putExtra("isVisiable", true);
        intent.putExtra("isShowVideo", "1");
        intent.putExtra("isSource", item.getModule_type() == 101);
        if (item.getTarget_params() != null) {
            intent.putExtra("isProhibit", item.getTarget_params().getIs_prohibit());
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        startActivity(MessageNoticeSetAct.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) PraiseNoticeAct.class);
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
        View viewFindViewById = findViewById(R.id.tabbar);
        textView.setText("点赞");
        viewFindViewById.setBackgroundColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#121622" : PLVAuthorizationBean.FCOLOR_DEFAULT));
        PraiseNoticeAdp praiseNoticeAdp = new PraiseNoticeAdp();
        this.mAdapter = praiseNoticeAdp;
        this.mRecyclerView.setAdapter(praiseNoticeAdp);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13850c.lambda$init$0(view);
            }
        });
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PraiseNoticeAct.access$008(PraiseNoticeAct.this);
                PraiseNoticeAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PraiseNoticeAct.this.mPage = 1;
                PraiseNoticeAct.this.getData();
            }
        });
        this.mAdapter.setOnItemActionLisenter(new PraiseNoticeAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.2
            @Override // com.psychiatrygarden.activity.setting.PraiseNoticeAdp.OnItemActionLisenter
            public void setDetailsAction(int pos, MyMessageCommentBean.DataBean item) {
                if (item.getModule_type() == 101 && item.getTarget_params() != null && item.getTarget_params().getIs_del().equals("1")) {
                    ToastUtil.shortToast(ProjectApp.instance(), "抱歉，资料已隐藏或删除");
                    return;
                }
                PublicMethodActivity.getInstance().mCommentMethod(item.getModule_type() + "", item.getTarget_params());
            }

            @Override // com.psychiatrygarden.activity.setting.PraiseNoticeAdp.OnItemActionLisenter
            public void setOpposeAction(int pos, MyMessageCommentBean.DataBean item, TextView textView2) {
                if (item.getIs_praise().equals("1")) {
                    return;
                }
                String str = "0";
                if (item.getIs_oppose().equals("1")) {
                    if (item.getType().equals(ClientCookie.COMMENT_ATTR)) {
                        PraiseNoticeAct.this.putOppose(item.getReply().get(0).getId(), item.getTarget_params().getId(), "0", item.getModule_type());
                    } else {
                        PraiseNoticeAct.this.circleOpporSuport("", 2, 2);
                    }
                    item.setIs_oppose("0");
                    try {
                        String oppose_num = item.getOppose_num();
                        if (TextUtils.isEmpty(oppose_num)) {
                            oppose_num = "0";
                        }
                        if (oppose_num.trim().equals("0")) {
                            item.setOppose_num("0");
                        } else {
                            item.setOppose_num((Integer.parseInt(oppose_num) - 1) + "");
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    CommonUtil.Toast_pop(textView2, 1);
                    if (item.getType().equals(ClientCookie.COMMENT_ATTR)) {
                        PraiseNoticeAct.this.putOppose(item.getReply().get(0).getId(), item.getTarget_params().getId(), "1", item.getModule_type());
                    } else {
                        PraiseNoticeAct.this.circleOpporSuport("", 2, 1);
                    }
                    item.setIs_oppose("1");
                    try {
                        String oppose_num2 = item.getOppose_num();
                        if (!TextUtils.isEmpty(oppose_num2)) {
                            str = oppose_num2;
                        }
                        item.setOppose_num((Integer.parseInt(str) + 1) + "");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                PraiseNoticeAct.this.mAdapter.notifyItemChanged(pos, 1);
            }

            @Override // com.psychiatrygarden.activity.setting.PraiseNoticeAdp.OnItemActionLisenter
            public void setReplayAction(int pos, MyMessageCommentBean.DataBean item) {
                if (item.getModule_type() != 12 && item.getModule_type() != 16) {
                    if (item.getModule_type() != 3) {
                        PraiseNoticeAct.this.jumpToReplayListPage(item);
                        return;
                    }
                    if (TextUtils.isEmpty(item.getTarget_params().getJson_path())) {
                        NewToast.showShort(ProjectApp.instance(), "原文已删除", 0).show();
                        return;
                    }
                    if (item.getReply() != null && item.getReply().size() > 0 && item.getReply().get(0) != null && !TextUtils.isEmpty(item.getReply().get(0).getNickname())) {
                        PraiseNoticeAct.this.jumpToReplayListPage(item);
                        return;
                    }
                    Intent intent = new Intent(ProjectApp.instance(), (Class<?>) HandoutsInfoActivity.class);
                    intent.putExtra("cat_id", "");
                    intent.putExtra("article", item.getTarget_params().getId());
                    intent.putExtra("json_path", item.getTarget_params().getJson_path());
                    intent.putExtra("html_path", "");
                    intent.putExtra("index", 0);
                    intent.putExtra("h5_path", item.getTarget_params().getH5_path());
                    intent.putExtra("is_rich_text", item.getTarget_params().getIs_rich_text());
                    intent.setFlags(268435456);
                    PraiseNoticeAct.this.startActivity(intent);
                    return;
                }
                if (item.getReply() != null && item.getReply().size() > 0 && item.getReply().get(0) != null && !TextUtils.isEmpty(item.getReply().get(0).getNickname())) {
                    PraiseNoticeAct.this.jumpToReplayListPage(item);
                    return;
                }
                if ("1".equals(item.getTarget_params().getIs_vip()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                    PraiseNoticeAct.this.startActivity(new Intent(PraiseNoticeAct.this, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                Intent intent2 = new Intent(PraiseNoticeAct.this.mContext, (Class<?>) CircleInfoActivity.class);
                intent2.putExtra("article_id", "" + item.getTarget_params().getId());
                intent2.putExtra("commentCount", "0");
                intent2.putExtra("channel_id", "0");
                intent2.putExtra("module_type", item.getModule_type() + "");
                if (item.getModule_type() == 16) {
                    intent2.putExtra("group_id", "" + item.getTarget_params().getGroup_id());
                }
                PraiseNoticeAct.this.startActivity(intent2);
            }

            @Override // com.psychiatrygarden.activity.setting.PraiseNoticeAdp.OnItemActionLisenter
            public void setSupportAction(int pos, MyMessageCommentBean.DataBean item, TextView textView2) {
                if (item.getIs_oppose().equals("1")) {
                    return;
                }
                String str = "0";
                if (item.getIs_praise().equals("1")) {
                    if (item.getType().equals(ClientCookie.COMMENT_ATTR)) {
                        PraiseNoticeAct.this.putPraise(item.getReply().get(0).getId(), item.getTarget_params().getId(), "0", item.getModule_type());
                    } else {
                        PraiseNoticeAct.this.circleToSuport(item.getTarget_params().getId(), 1, 2, item.getModule_type());
                    }
                    item.setIs_praise("0");
                    try {
                        String praise_num = item.getPraise_num();
                        if (TextUtils.isEmpty(praise_num)) {
                            praise_num = "0";
                        }
                        if (praise_num.trim().equals("0")) {
                            item.setPraise_num("0");
                        } else {
                            item.setPraise_num((Integer.parseInt(praise_num) - 1) + "");
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    CommonUtil.Toast_pop(textView2, 0);
                    if (item.getType().equals(ClientCookie.COMMENT_ATTR)) {
                        PraiseNoticeAct.this.putPraise(item.getReply().get(0).getId(), item.getTarget_params().getId(), "1", item.getModule_type());
                    } else {
                        PraiseNoticeAct.this.circleToSuport(item.getTarget_params().getId(), 1, 1, item.getModule_type());
                    }
                    item.setIs_praise("1");
                    try {
                        String praise_num2 = item.getPraise_num();
                        if (!TextUtils.isEmpty(praise_num2)) {
                            str = praise_num2;
                        }
                        item.setPraise_num((Integer.parseInt(str) + 1) + "");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                PraiseNoticeAct.this.mAdapter.notifyItemChanged(pos, 1);
            }

            @Override // com.psychiatrygarden.activity.setting.PraiseNoticeAdp.OnItemActionLisenter
            public void setToUserInfoAction(int pos, MyMessageCommentBean.DataBean item) {
                if (item.getIs_logout().equals("1")) {
                    ToastUtils.showShort("该用户已注销");
                    return;
                }
                if ("1".equals(item.getIs_anonymous())) {
                    return;
                }
                Intent intent = new Intent(PraiseNoticeAct.this, (Class<?>) UserCommentInfoActivity.class);
                intent.putExtra("user_id", item.getUser_id());
                intent.addFlags(67108864);
                PraiseNoticeAct.this.startActivity(intent);
                PraiseNoticeAct.this.overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {
            putComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateCommentInfoEvent event) {
        int i2 = 0;
        String str = "0";
        if (event.type.equals("praise")) {
            while (true) {
                if (i2 >= this.mAdapter.getData().size()) {
                    i2 = -1;
                    break;
                } else if (event.id.equals(this.mAdapter.getData().get(i2).getObj_id())) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 != -1) {
                if (event.isAdd) {
                    this.mAdapter.getData().get(i2).setIs_praise("1");
                    try {
                        String praise_num = this.mAdapter.getData().get(i2).getPraise_num();
                        if (!TextUtils.isEmpty(praise_num)) {
                            str = praise_num;
                        }
                        this.mAdapter.getData().get(i2).setPraise_num((Integer.parseInt(str) + 1) + "");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    this.mAdapter.getData().get(i2).setIs_praise("0");
                    try {
                        String praise_num2 = this.mAdapter.getData().get(i2).getPraise_num();
                        if (!TextUtils.isEmpty(praise_num2)) {
                            str = praise_num2;
                        }
                        MyMessageCommentBean.DataBean dataBean = this.mAdapter.getData().get(i2);
                        StringBuilder sb = new StringBuilder();
                        sb.append(Integer.parseInt(str) - 1);
                        sb.append("");
                        dataBean.setPraise_num(sb.toString());
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                this.mAdapter.notifyItemChanged(i2);
                return;
            }
            return;
        }
        if (event.type.equals("oppose")) {
            while (true) {
                if (i2 >= this.mAdapter.getData().size()) {
                    i2 = -1;
                    break;
                } else if (event.id.equals(this.mAdapter.getData().get(i2).getObj_id())) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 != -1) {
                if (event.isAdd) {
                    this.mAdapter.getData().get(i2).setIs_oppose("1");
                    try {
                        String oppose_num = this.mAdapter.getData().get(i2).getOppose_num();
                        if (!TextUtils.isEmpty(oppose_num)) {
                            str = oppose_num;
                        }
                        this.mAdapter.getData().get(i2).setOppose_num((Integer.parseInt(str) + 1) + "");
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } else {
                    this.mAdapter.getData().get(i2).setIs_oppose("0");
                    try {
                        String oppose_num2 = this.mAdapter.getData().get(i2).getOppose_num();
                        if (!TextUtils.isEmpty(oppose_num2)) {
                            str = oppose_num2;
                        }
                        MyMessageCommentBean.DataBean dataBean2 = this.mAdapter.getData().get(i2);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Integer.parseInt(str) - 1);
                        sb2.append("");
                        dataBean2.setOppose_num(sb2.toString());
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                this.mAdapter.notifyItemChanged(i2);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getMessageNotice();
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        if (!b3.getString("module_type").equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
            ajaxParams.put("parent_id", b3.getString("parent_id"));
            ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
            ajaxParams.put("to_user_id", b3.getString("to_user_id"));
        }
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", b3.getString("obj_id"));
        ajaxParams.put("module_type", b3.getString("module_type"));
        ajaxParams.put("comment_type", b3.getString("comment_type"));
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
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(PraiseNoticeAct.this, "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(PraiseNoticeAct.this, "回复成功", 0).show();
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    } else {
                        NewToast.showShort(PraiseNoticeAct.this, jSONObject.optString("message"), 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putOppose(String id, String question_id, String type, int module_type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", question_id);
        ajaxParams.put("module_type", module_type + "");
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("id", id);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutOpposeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.4
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
                super.onSuccess((AnonymousClass4) s2);
            }
        });
    }

    public void putPraise(String id, String question_id, String type, int module_type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", question_id);
        ajaxParams.put("module_type", module_type + "");
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("id", id);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutPraseUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.PraiseNoticeAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_common_notice_list);
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnToSet.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13845c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
