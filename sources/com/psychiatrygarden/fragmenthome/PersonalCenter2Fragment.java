package com.psychiatrygarden.fragmenthome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.huawei.hms.push.HmsMessageService;
import com.hyphenate.chat.EMClient;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ContactCustomerServiceNewActivity;
import com.psychiatrygarden.activity.CourseDownCacheActivity;
import com.psychiatrygarden.activity.DarkModeActivity;
import com.psychiatrygarden.activity.FeedbackActivity;
import com.psychiatrygarden.activity.FollowActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.SetNewAvtivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chat.ChatHomeActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity;
import com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.ebook.BookReadRecordActivity;
import com.psychiatrygarden.activity.ebook.BookShelfActivity;
import com.psychiatrygarden.activity.forum.GiveLogActivity;
import com.psychiatrygarden.activity.invite.InviteFriendActivity;
import com.psychiatrygarden.activity.mine.CouponsDateLineAdp;
import com.psychiatrygarden.activity.mine.PersonalMenuItemAdp;
import com.psychiatrygarden.activity.mine.SignInActivity;
import com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct;
import com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity;
import com.psychiatrygarden.activity.mine.myfile.MyFileAct;
import com.psychiatrygarden.activity.mine.order.OrderListActivity;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.activity.setting.AppTestInfoAct;
import com.psychiatrygarden.activity.setting.PraiseNoticeAct;
import com.psychiatrygarden.activity.setting.SystemCommentNoticeAct;
import com.psychiatrygarden.activity.setting.SystemNoticeAct;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.bean.VipTipsBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseChapterBean;
import com.psychiatrygarden.bean.GetCouponsBean;
import com.psychiatrygarden.bean.MessageCenterBean;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.bean.ShareTaskBean;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.bean.UserSignInfo;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.ranking.RankingAct;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ActiveItemView;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PersonalCenter2Fragment extends BaseFragment {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CODE_SCAN_QR_CODE = 999;
    public TextView anniuclickTv;
    public TextView anniusvipclickTv;
    private CouponsDateLineAdp couponsAdp;
    public TextView datelineTv;
    public TextView datesviplineTv;
    public FrameLayout fragview;
    public TextView isLoginBtn;
    private String joinStatus;
    private View line;
    public LinearLayout loginLineView;
    public LinearLayout logindetail;
    public LinearLayout loginsvidLineView;
    private LinearLayout lyAppSign;
    private TextView mBtnGuideKnowOne;
    private TextView mBtnGuideKnowTwo;
    private ImageView mBtnScan;
    private RecyclerView mCouponsRecycler;
    private View mEmptyViewMenu;
    private View mEmptyViewOne;
    private View mGuideOneView;
    private View mGuideTwoView;
    private ImageView mImgSVipBg;
    private ImageView mImgTriangleOne;
    private ImageView mImgTriangleTwo;
    private ImageView mImgVipBg;
    private RecyclerView mItemGridView;
    private LinearLayout mLyActive;
    private LinearLayout mLyActiveView;
    private LinearLayout mLyAppTest;
    private RelativeLayout mLyChat;
    private RelativeLayout mLyComment;
    private LinearLayout mLyContentOne;
    private LinearLayout mLyContentTwo;
    private RelativeLayout mLyDineCouponsView;
    private LinearLayout mLyItemOne;
    private RelativeLayout mLyLike;
    public LinearLayout mLyNoLogin;
    private RelativeLayout mLyNotice;
    private RelativeLayout mLyParent;
    private RelativeLayout mLyRank;
    private LinearLayout mLyRankView;
    private RelativeLayout mLyShare;
    private RelativeLayout mLyStatistics;
    private RelativeLayout mLyUnRedChatMsg;
    private RelativeLayout mLyUnRedCommentMsg;
    private RelativeLayout mLyUnRedNoticeMsg;
    private RelativeLayout mLyUnRedPraiseMsg;
    private NestedScrollView mScrollView;
    private TextView mTvAppVersion;
    private TextView mTvChatUnredNum;
    private TextView mTvCommentUnredNum;
    public TextView mTvExamDay;
    private TextView mTvInviteShare;
    private TextView mTvLikeUnredNum;
    private TextView mTvNoticeUnredNum;
    private TextView mTvPercent;
    private TextView mTvStatisticsInfo;
    public View marginsbgview;
    private PersonalMenuItemAdp menuItemAdp;
    public LinearLayout myContactTv;
    public CircleImageView mycenter_icon;
    public LinearLayout myfeedbackTv;
    public LinearLayout nologinLineView;
    public LinearLayout nologinsvidLineView;
    public ImageView prenimg;
    public TextView qNameTv;
    private BroadcastReceiver receiver;
    public RelativeLayout rel_memview;
    public RelativeLayout rel_svidmemview;
    public LinearLayout reldaojishi;
    private View signPointV;
    private RelativeLayout signRl;
    public TextView simplinesvip;
    public TextView simplinevip;
    public FrameLayout svipbgview;
    private TextView tvModeSwitch;
    public TextView tv_my_nickname;
    public TextView tv_school_kaosheng;
    public FrameLayout vipbgview;
    public HorizontalScrollView vipsHoriz;
    private String webLink;
    private boolean isShowShareInvite = false;
    private int scrollHeight = 0;
    private boolean isShowGuide = false;
    private int mCurrentCouponsPos = 0;
    private long mTempTime = 0;
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                if (PersonalCenter2Fragment.this.couponsAdp.getData().size() == 0) {
                    return;
                }
                long j2 = (!TextUtils.isEmpty(PersonalCenter2Fragment.this.couponsAdp.getData().get(PersonalCenter2Fragment.this.mCurrentCouponsPos).getCoupon_end()) ? Long.parseLong(PersonalCenter2Fragment.this.couponsAdp.getData().get(PersonalCenter2Fragment.this.mCurrentCouponsPos).getCoupon_end()) : 0L) - jCurrentTimeMillis;
                if (j2 <= 0) {
                    PersonalCenter2Fragment.this.couponsAdp.removeAt(PersonalCenter2Fragment.this.mCurrentCouponsPos);
                    PersonalCenter2Fragment.this.mLyParent.setVisibility(PersonalCenter2Fragment.this.couponsAdp.getData().size() > 1 ? 0 : 8);
                    if (PersonalCenter2Fragment.this.couponsAdp.getData().size() == 0) {
                        PersonalCenter2Fragment.this.mLyDineCouponsView.setVisibility(8);
                        PersonalCenter2Fragment.this.mHandler.removeCallbacksAndMessages(null);
                        return;
                    }
                    return;
                }
                LogUtils.e("person_recycler_vis", j2 + ";mCurrentCouponsPos=" + PersonalCenter2Fragment.this.mCurrentCouponsPos + ";resultTime=" + j2 + ";vh=" + PersonalCenter2Fragment.this.mCouponsRecycler.findViewHolderForAdapterPosition(PersonalCenter2Fragment.this.mCurrentCouponsPos));
                PersonalCenter2Fragment.this.couponsAdp.notifyItemChanged(PersonalCenter2Fragment.this.mCurrentCouponsPos, 1);
                PersonalCenter2Fragment.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
            }
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment$11, reason: invalid class name */
    public class AnonymousClass11 extends AjaxCallBack<String> {
        public AnonymousClass11() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(ShareTaskBean shareTaskBean, String str, String str2, String str3, String str4, String str5, String str6, View view) {
            Intent intent = new Intent(view.getContext(), (Class<?>) WebLongSaveActivity.class);
            intent.putExtra("title", PersonalCenter2Fragment.this.getResources().getString(R.string.inviteShareTitle));
            intent.putExtra("desc", shareTaskBean.getData().getDescribe());
            intent.putExtra("shareTitle", shareTaskBean.getData().getTask_title());
            intent.putExtra("shareUrl", shareTaskBean.getData().getLink() + "?taskId=" + str + "&inviterUserId=" + str2 + "&appId=" + str3);
            intent.putExtra("web_url", shareTaskBean.getData().getLink() + "?taskId=" + str + "&userId=" + str2 + "&token=" + str4 + "&joinRewardType=" + str5 + "&appId=" + str3 + "&secret=" + str6);
            view.getContext().startActivity(intent);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass11) s2);
            try {
                final ShareTaskBean shareTaskBean = (ShareTaskBean) new Gson().fromJson(s2, ShareTaskBean.class);
                if (!shareTaskBean.getCode().equals("200")) {
                    PersonalCenter2Fragment.this.updateItemGridData(false);
                    return;
                }
                if (shareTaskBean.getData() == null) {
                    PersonalCenter2Fragment.this.updateItemGridData(false);
                    PersonalCenter2Fragment.this.isShowShareInvite = false;
                    PersonalCenter2Fragment.this.mLyShare.setVisibility(8);
                    return;
                }
                if (TextUtils.isEmpty(shareTaskBean.getData().getId())) {
                    PersonalCenter2Fragment.this.isShowShareInvite = false;
                    PersonalCenter2Fragment.this.mLyShare.setVisibility(8);
                } else {
                    PersonalCenter2Fragment.this.isShowShareInvite = true;
                    PersonalCenter2Fragment.this.mLyShare.setVisibility(0);
                    PersonalCenter2Fragment.this.mTvInviteShare.setText(shareTaskBean.getData().getJoin_reward_name());
                    final String id = shareTaskBean.getData().getId();
                    final String userId = UserConfig.getUserId();
                    final String token = UserConfig.getInstance().getUser().getToken();
                    final String secret = UserConfig.getInstance().getUser().getSecret();
                    final String join_reward_type = shareTaskBean.getData().getJoin_reward_type();
                    final String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                    PersonalCenter2Fragment.this.mLyShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ba
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f15468c.lambda$onSuccess$0(shareTaskBean, id, userId, strConfig, token, join_reward_type, secret, view);
                        }
                    });
                }
                PersonalCenter2Fragment.this.updateItemGridData(shareTaskBean.getData().getBook_status().equals("1"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment$6, reason: invalid class name */
    public class AnonymousClass6 extends AjaxCallBack<String> {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(View view) {
            PersonalCenter2Fragment.this.startActivity(new Intent(PersonalCenter2Fragment.this.getActivity(), (Class<?>) CircleSchoolVerifyActivity.class));
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            PersonalCenter2Fragment.this.hideProgressDialog();
            UserInfoBean.DataBean user = UserConfig.getInstance().getUser();
            if (user != null) {
                PersonalCenter2Fragment.this.tv_my_nickname.setText(user.getNickname());
                PersonalCenter2Fragment.this.prenimg.setVisibility(user.getIs_authentication().equals("1") || user.getIdentity().equals("1") ? 0 : 8);
                if (PersonalCenter2Fragment.this.getActivity() != null) {
                    GlideApp.with(PersonalCenter2Fragment.this.getActivity()).load((Object) GlideUtils.generateUrl(user.getAvatar())).placeholder(R.drawable.weiloginappimg).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.weiloginappimg)).into(PersonalCenter2Fragment.this.mycenter_icon);
                }
                PersonalCenter2Fragment.this.tv_school_kaosheng.setVisibility(0);
                if (!user.getIdentity().equals("0") && !user.getIdentity().equals("")) {
                    PersonalCenter2Fragment.this.tv_school_kaosheng.setText(user.getIdentity_description());
                    return;
                }
                PersonalCenter2Fragment.this.tv_school_kaosheng.setText(String.format(Locale.CHINA, "%s | %s", user.getUser_type_judged() + "", user.getIdentity_judged() + ""));
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            try {
                UserInfoBean userInfoBean = (UserInfoBean) new Gson().fromJson(t2, UserInfoBean.class);
                String code = userInfoBean.getCode();
                int i2 = 0;
                if (code.equals("200")) {
                    UserConfig.getInstance().saveUser(userInfoBean.getData());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.REWARD_SHOW_WAY, userInfoBean.getData().getReward_show_way(), ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                    if (PersonalCenter2Fragment.this.getActivity() != null) {
                        GlideApp.with(PersonalCenter2Fragment.this.getActivity()).load((Object) GlideUtils.generateUrl(userInfoBean.getData().getAvatar())).into(PersonalCenter2Fragment.this.mycenter_icon);
                    }
                    PersonalCenter2Fragment.this.tv_my_nickname.setVisibility(0);
                    PersonalCenter2Fragment.this.tv_my_nickname.setText(userInfoBean.getData().getNickname());
                    boolean z2 = userInfoBean.getData().getIs_authentication().equals("1") || userInfoBean.getData().getIdentity().equals("1");
                    PersonalCenter2Fragment.this.prenimg.setVisibility(z2 ? 0 : 8);
                    PersonalCenter2Fragment.this.isLoginBtn.setVisibility(8);
                    if (!z2) {
                        PersonalCenter2Fragment.this.isLoginBtn.setText("认证");
                        PersonalCenter2Fragment.this.isLoginBtn.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ca
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f15507c.lambda$onSuccess$0(view);
                            }
                        });
                    }
                    PersonalCenter2Fragment.this.tv_school_kaosheng.setVisibility(0);
                    if (userInfoBean.getData().getIdentity().equals("0") || userInfoBean.getData().getIdentity().equals("")) {
                        PersonalCenter2Fragment.this.tv_school_kaosheng.setText(String.format(Locale.CHINA, "%s | %s", userInfoBean.getData().getUser_type_judged(), userInfoBean.getData().getIdentity_judged()));
                    } else {
                        PersonalCenter2Fragment.this.tv_school_kaosheng.setText(userInfoBean.getData().getIdentity_description());
                    }
                    String exam_count_down = userInfoBean.getData().getExam_count_down();
                    LinearLayout linearLayout = PersonalCenter2Fragment.this.reldaojishi;
                    if (TextUtils.isEmpty(exam_count_down)) {
                        i2 = 8;
                    }
                    linearLayout.setVisibility(i2);
                    if (!TextUtils.isEmpty(exam_count_down)) {
                        PersonalCenter2Fragment.this.mTvExamDay.setText(exam_count_down);
                    }
                    PersonalCenter2Fragment.this.getPopTips();
                    PersonalCenter2Fragment.this.getTodayStatistica();
                    UserInfoBean.UserShowBean invite_user_show = userInfoBean.getData().getInvite_user_show();
                    if (invite_user_show == null) {
                        PersonalCenter2Fragment.this.joinStatus = "";
                        PersonalCenter2Fragment.this.webLink = "";
                        Iterator<String> it = PersonalCenter2Fragment.this.menuItemAdp.getData().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            String next = it.next();
                            if (next.equals("邀请好友")) {
                                PersonalCenter2Fragment.this.menuItemAdp.remove((PersonalMenuItemAdp) next);
                                break;
                            }
                        }
                    } else {
                        PersonalCenter2Fragment.this.joinStatus = invite_user_show.getJoin_status();
                        PersonalCenter2Fragment.this.webLink = invite_user_show.getWeb_link();
                        if (invite_user_show.getStatus() != 1) {
                            Iterator<String> it2 = PersonalCenter2Fragment.this.menuItemAdp.getData().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                String next2 = it2.next();
                                if (next2.equals("邀请好友")) {
                                    PersonalCenter2Fragment.this.menuItemAdp.remove((PersonalMenuItemAdp) next2);
                                    break;
                                }
                            }
                        }
                    }
                } else if (code.equals("203")) {
                    SharePreferencesUtils.clearAppointData(PersonalCenter2Fragment.this.getActivity());
                    SharePreferencesUtils.removeConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, PersonalCenter2Fragment.this.getActivity());
                    SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) PersonalCenter2Fragment.this).mContext), PersonalCenter2Fragment.this.getActivity());
                    SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) PersonalCenter2Fragment.this).mContext), PersonalCenter2Fragment.this.getActivity());
                    EMClient.getInstance().logout(false);
                    PersonalCenter2Fragment.this.mToShowView();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            PersonalCenter2Fragment.this.hideProgressDialog();
        }
    }

    private void couponsScroll() {
        this.mCouponsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.15
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int iComputeHorizontalScrollExtent = recyclerView.computeHorizontalScrollExtent();
                int iComputeHorizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                PersonalCenter2Fragment.this.line.setTranslationX((PersonalCenter2Fragment.this.mLyParent.getWidth() - PersonalCenter2Fragment.this.line.getWidth()) * (recyclerView.computeHorizontalScrollOffset() / (iComputeHorizontalScrollRange - iComputeHorizontalScrollExtent)));
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) PersonalCenter2Fragment.this.mCouponsRecycler.getLayoutManager();
                int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                LogUtils.e("person_recycler_vis", "可见item_pos=>" + iFindFirstVisibleItemPosition + ";dx===>" + dx + ";lastVisibleItem=>" + iFindLastVisibleItemPosition);
                if (iFindFirstVisibleItemPosition == -1 || iFindFirstVisibleItemPosition != iFindLastVisibleItemPosition) {
                    return;
                }
                PersonalCenter2Fragment.this.mCurrentCouponsPos = iFindFirstVisibleItemPosition;
                if (iFindFirstVisibleItemPosition == -1) {
                    PersonalCenter2Fragment.this.mCurrentCouponsPos = 0;
                }
                if (TextUtils.isEmpty(PersonalCenter2Fragment.this.couponsAdp.getItem(PersonalCenter2Fragment.this.mCurrentCouponsPos).getCoupon_end())) {
                    return;
                }
                PersonalCenter2Fragment.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
                LogUtils.e("person_recycler_vis", "mHandler=>mCurrentCouponsPos=" + PersonalCenter2Fragment.this.mCurrentCouponsPos);
            }
        });
    }

    private void getMessageUnredCountData() {
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.messageCenter, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.8
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
                super.onSuccess((AnonymousClass8) s2);
                try {
                    MessageCenterBean messageCenterBean = (MessageCenterBean) new Gson().fromJson(s2, MessageCenterBean.class);
                    if (200 == messageCenterBean.getCode()) {
                        if (messageCenterBean.getData().size() > 4) {
                            PersonalCenter2Fragment.this.setTopData(messageCenterBean.getData().subList(0, 4));
                        } else {
                            PersonalCenter2Fragment.this.setTopData(messageCenterBean.getData());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPersonalDayCoupons() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.personalDayCouponsList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass14) s2);
                try {
                    GetCouponsBean getCouponsBean = (GetCouponsBean) new Gson().fromJson(s2, GetCouponsBean.class);
                    if (getCouponsBean.getCode().equals("200")) {
                        if (getCouponsBean.getData() == null || getCouponsBean.getData().size() <= 0) {
                            PersonalCenter2Fragment.this.mLyDineCouponsView.setVisibility(8);
                        } else {
                            PersonalCenter2Fragment.this.mLyDineCouponsView.setVisibility(0);
                            PersonalCenter2Fragment.this.couponsAdp.setList(getCouponsBean.getData());
                            PersonalCenter2Fragment.this.mLyParent.setVisibility(PersonalCenter2Fragment.this.couponsAdp.getData().size() > 1 ? 0 : 8);
                            PersonalCenter2Fragment.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getSignInfo() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mineGetIconInfo, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    UserSignInfo userSignInfo = (UserSignInfo) new Gson().fromJson(s2, UserSignInfo.class);
                    if (userSignInfo.getCode().equals("200")) {
                        if (userSignInfo.getData().getSign_is_open().equals("1")) {
                            PersonalCenter2Fragment.this.lyAppSign.setVisibility(0);
                            if (userSignInfo.getData().getIs_sign().equals("1")) {
                                PersonalCenter2Fragment.this.signPointV.setVisibility(8);
                            } else {
                                PersonalCenter2Fragment.this.signPointV.setVisibility(0);
                            }
                        } else {
                            PersonalCenter2Fragment.this.lyAppSign.setVisibility(8);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTodayStatistica() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(HmsMessageService.SUBJECT_ID, SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getTodayStatisticalData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(t2).optJSONObject("data").optString("question_all_num");
                        String strOptString2 = new JSONObject(t2).optJSONObject("data").optString("right_count");
                        if (TextUtils.isEmpty(strOptString)) {
                            strOptString = "0";
                        }
                        if (TextUtils.isEmpty(strOptString2)) {
                            strOptString2 = "0";
                        }
                        PersonalCenter2Fragment.this.mTvStatisticsInfo.setText(strOptString + "道/正确率" + strOptString2 + "%");
                        TextView textView = PersonalCenter2Fragment.this.mTvPercent;
                        StringBuilder sb = new StringBuilder();
                        sb.append(strOptString2);
                        sb.append("%");
                        textView.setText(sb.toString());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                PersonalCenter2Fragment.this.hideProgressDialog();
            }
        });
    }

    private void getUserInfo(String userid) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", userid);
        ajaxParams.put("target_user_uuid", userid);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AnonymousClass6());
    }

    private void initActiveView() {
        this.mLyActiveView.removeAllViews();
        for (int i2 = 0; i2 < 3; i2++) {
            this.mLyActiveView.addView(new ActiveItemView(this.mContext));
        }
        LogUtils.e("top_right", "活动高度===》" + this.mLyActive.getHeight());
    }

    private void initItemGridData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("关注");
        arrayList.add("已斩试题");
        arrayList.add("我的下载");
        arrayList.add("我的书架");
        arrayList.add("阅读历史");
        arrayList.add("我的订单");
        arrayList.add("红包卡券");
        arrayList.add("领券中心");
        arrayList.add("会员奖励");
        arrayList.add("邀请好友");
        this.menuItemAdp.setList(arrayList);
    }

    private void initwriteStatusBar() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(requireContext(), R.color.new_bg_two_color), 0);
            getActivity().getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        } else {
            StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(requireContext(), R.color.new_bg_two_color_night), 0);
            getActivity().getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (isLogin()) {
            DarkModeActivity.INSTANCE.newIntent(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (isLogin()) {
            Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
            intent.putExtra("user_id", UserConfig.getUserId());
            intent.addFlags(67108864);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$10(View view) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) MemberCenterActivity.class);
            intent.putExtra("psotision", 1);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$11(View view) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) MemberCenterActivity.class);
            intent.putExtra("psotision", 0);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$12(View view) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) MemberCenterActivity.class);
            intent.putExtra("psotision", 1);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$13(View view) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) MemberCenterActivity.class);
            intent.putExtra("psotision", 0);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$14(View view) {
        if (!isLogin() || this.mEmptyViewOne.getVisibility() == 0) {
            return;
        }
        goActivity(SetNewAvtivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$15(View view) {
        isLogin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$16(View view) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) AppTestInfoAct.class).putExtra("web_url", NetworkRequestsURL.appTestInfo).putExtra("title", "医考帮试用中心"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$17(View view) {
        if (isLogin()) {
            RankingAct.newIntent(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$18(View view) {
        if (isLogin()) {
            StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$19(View view) {
        if (isLogin()) {
            startActivity(new Intent(this.mContext, (Class<?>) SignInActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (!isLogin() || this.mEmptyViewOne.getVisibility() == 0) {
            return;
        }
        startActivity(new Intent(getActivity(), (Class<?>) ContactCustomerServiceNewActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$20(int i2, View view, int i3, int i4, int i5, int i6) {
        int left = this.svipbgview.getLeft();
        LogUtils.e("scroll_x", "滑动距离===》" + i3 + ";oldScrollx=" + i5 + ";left=" + left);
        if (i3 > i5) {
            LogUtils.e("scroll_x", "向右滑动");
            if (i3 >= i2 / 2) {
                if (this.mLyShare.getVisibility() == 0) {
                    this.mLyShare.setVisibility(8);
                }
                this.vipsHoriz.smoothScrollTo(left, 0);
                return;
            }
            return;
        }
        LogUtils.e("scroll_x", "向左滑动");
        if (i3 < i2 / 2) {
            this.vipsHoriz.smoothScrollTo(0, 0);
            if (this.isShowShareInvite && this.mLyShare.getVisibility() == 8) {
                this.mLyShare.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$21(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        String item = this.menuItemAdp.getItem(i2);
        if (isLogin()) {
            item.hashCode();
            switch (item) {
                case "关注":
                    String userId = UserConfig.getUserId();
                    if (!TextUtils.isEmpty(userId)) {
                        startActivity(new Intent(requireContext(), (Class<?>) FollowActivity.class).putExtra("user_id", userId));
                        break;
                    }
                    break;
                case "会员奖励":
                    startActivity(new Intent(getActivity(), (Class<?>) GiveLogActivity.class));
                    break;
                case "已斩试题":
                    MyCutQuestionNewActivity.INSTANCE.navigationToMyCutQuestion(getActivity());
                    break;
                case "我的书架":
                    BookShelfActivity.newIntent(this.mContext, "", "");
                    break;
                case "我的下载":
                    goActivity(CourseDownCacheActivity.class);
                    break;
                case "我的收藏":
                    MyFileAct.newIntent(this.mContext);
                    break;
                case "我的订单":
                    OrderListActivity.INSTANCE.goToOrderListActivity(getContext());
                    break;
                case "红包卡券":
                    startActivity(RedEnvelopeCouponsAct.newIntent(this.mContext, false));
                    break;
                case "邀请好友":
                    if (!"0".equals(this.joinStatus)) {
                        if (!"1".equals(this.joinStatus)) {
                            AlertToast("返回活动状态码错误:" + this.joinStatus);
                            break;
                        } else {
                            startActivity(new Intent(requireContext(), (Class<?>) InviteFriendActivity.class));
                            break;
                        }
                    } else if (!TextUtils.isEmpty(this.webLink)) {
                        Intent intent = new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class);
                        intent.putExtra("title", "邀请好友");
                        intent.putExtra("web_url", this.webLink);
                        this.mContext.startActivity(intent);
                        break;
                    }
                    break;
                case "阅读历史":
                    BookReadRecordActivity.newIntent(this.mContext);
                    break;
                case "领券中心":
                    startActivity(RedEnvelopeCouponsAct.newIntent(this.mContext, true));
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$22(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.showCouponsCenterGuid, false, this.mContext);
        this.mGuideOneView.setVisibility(8);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.showRedCouponsGuid, true, this.mContext)) {
            showRedCouponsGuid();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$23(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.showRedCouponsGuid, false, this.mContext);
        this.mScrollView.setEnabled(true);
        this.mGuideTwoView.setVisibility(8);
        this.mEmptyViewOne.setVisibility(8);
        this.mEmptyViewMenu.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$24(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$25(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (!isLogin() || this.mEmptyViewOne.getVisibility() == 0) {
            return;
        }
        goActivity(FeedbackActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        if (isLogin()) {
            startActivity(new Intent(getActivity(), (Class<?>) ChatHomeActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(View view) {
        if (isLogin()) {
            this.mLyUnRedNoticeMsg.setVisibility(8);
            startActivity(SystemNoticeAct.newIntent(getActivity()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        if (isLogin()) {
            this.mLyUnRedCommentMsg.setVisibility(8);
            startActivity(SystemCommentNoticeAct.newIntent(getActivity()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) {
        if (isLogin()) {
            this.mLyUnRedPraiseMsg.setVisibility(8);
            startActivity(PraiseNoticeAct.newIntent(getActivity()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) {
        if (isLogin()) {
            scanQRCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mToShowView$27(View view) {
        goActivity(LoginActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanQRCode$26() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA} : new String[]{Permission.CAMERA}, 1);
    }

    private void loadVideoInfo(String videoId, String questionId) {
        QuestionDataRequest.getIntance(this.mContext).questionVideo(questionId, videoId, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.13
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CourseChapterBean courseChapterBean = (CourseChapterBean) new Gson().fromJson(jSONObject.optString("data"), CourseChapterBean.class);
                        if (courseChapterBean != null) {
                            Intent intent = new Intent();
                            intent.setClass(PersonalCenter2Fragment.this.getContext(), AliperCommentActivity.class);
                            intent.putExtra("obj_id", courseChapterBean.getId());
                            intent.putExtra("free_watch_time", courseChapterBean.getFree_watch_time());
                            intent.putExtra("watch_permission", "1");
                            intent.putExtra("expire_str", courseChapterBean.getExpire_str());
                            intent.putExtra("realVideo", true);
                            intent.putExtra("module_type", 10);
                            intent.putExtra("vid", courseChapterBean.getVid());
                            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "real_question_video");
                            intent.putExtra("commentEnum", DiscussStatus.QuestionBankVideo);
                            PersonalCenter2Fragment.this.getContext().startActivity(intent);
                        } else {
                            NewToast.showShort(PersonalCenter2Fragment.this.getContext(), "获取视频源失败", 0).show();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static PersonalCenter2Fragment newInstance() {
        Bundle bundle = new Bundle();
        PersonalCenter2Fragment personalCenter2Fragment = new PersonalCenter2Fragment();
        personalCenter2Fragment.setArguments(bundle);
        return personalCenter2Fragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordPushLabel(String is_vip) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        ajaxParams.put("is_vip", is_vip);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.recordPushLabelApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass12) s2);
            }
        });
    }

    private void scanQRCode() {
        if (!CommonUtil.hasRequiredCameraPermissions(this.mContext)) {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.z8
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f16163a.lambda$scanQRCode$26();
                }
            })).show();
            return;
        }
        if (!(ContextCompat.checkSelfPermission(this.mContext, Permission.CAMERA) == 0)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.CAMERA}, 1);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.CAMERA}, 1);
        } else {
            startActivityForResult(new Intent(this.mContext, (Class<?>) CaptureActivity.class), 999, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void setTopData(List<MessageCenterBean.MessageCenterItemBean> datas) throws NumberFormatException {
        String strValueOf;
        for (MessageCenterBean.MessageCenterItemBean messageCenterItemBean : datas) {
            if (TextUtils.isEmpty(messageCenterItemBean.getCount())) {
                messageCenterItemBean.setCount("0");
            }
            int i2 = Integer.parseInt(messageCenterItemBean.getCount());
            String type = messageCenterItemBean.getType();
            type.hashCode();
            char c3 = 65535;
            switch (type.hashCode()) {
                case -980226692:
                    if (type.equals("praise")) {
                        c3 = 0;
                        break;
                    }
                    break;
                case -887328209:
                    if (type.equals("system")) {
                        c3 = 1;
                        break;
                    }
                    break;
                case 3052376:
                    if (type.equals("chat")) {
                        c3 = 2;
                        break;
                    }
                    break;
                case 950398559:
                    if (type.equals(ClientCookie.COMMENT_ATTR)) {
                        c3 = 3;
                        break;
                    }
                    break;
            }
            strValueOf = "···";
            switch (c3) {
                case 0:
                    this.mLyLike.setVisibility(0);
                    this.mLyUnRedPraiseMsg.setVisibility(i2 <= 0 ? 8 : 0);
                    this.mTvLikeUnredNum.setText(i2 <= 99 ? String.valueOf(messageCenterItemBean.getCount()) : "···");
                    break;
                case 1:
                    this.mLyNotice.setVisibility(0);
                    this.mLyUnRedNoticeMsg.setVisibility(i2 <= 0 ? 8 : 0);
                    this.mTvNoticeUnredNum.setText(i2 <= 99 ? String.valueOf(messageCenterItemBean.getCount()) : "···");
                    break;
                case 2:
                    this.mLyChat.setVisibility(0);
                    this.mLyUnRedChatMsg.setVisibility(ProjectApp.instance.getUnreadMessageCount() <= 0 ? 8 : 0);
                    int unreadMessageCount = ProjectApp.instance().getUnreadMessageCount();
                    TextView textView = this.mTvChatUnredNum;
                    if (unreadMessageCount <= 0) {
                        strValueOf = "";
                    } else if (unreadMessageCount <= 99) {
                        strValueOf = String.valueOf(unreadMessageCount);
                    }
                    textView.setText(strValueOf);
                    break;
                case 3:
                    this.mLyComment.setVisibility(0);
                    this.mLyUnRedCommentMsg.setVisibility(i2 <= 0 ? 8 : 0);
                    this.mTvCommentUnredNum.setText(i2 <= 99 ? String.valueOf(messageCenterItemBean.getCount()) : "···");
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCouponsCenterGuid() {
        this.mEmptyViewOne.setVisibility(0);
        this.mEmptyViewMenu.setVisibility(0);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 112.0d)) / 4;
        int i2 = 0;
        while (true) {
            if (i2 >= this.menuItemAdp.getData().size()) {
                i2 = 0;
                break;
            } else if (this.menuItemAdp.getData().get(i2).equals("领券中心")) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = i2 + 1;
        int height = this.mItemGridView.getHeight() / 3;
        LogUtils.e("adp_count", "count=====>" + this.menuItemAdp.getData().size() + ";position=" + i3);
        if (this.menuItemAdp.getData().size() <= 8) {
            height = this.mItemGridView.getHeight() / 2;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.topMargin = height - ScreenUtil.getPxByDp(this.mLyContentOne.getContext(), 48);
        if (i3 == 6) {
            layoutParams.addRule(20);
            layoutParams.setMarginStart(screenWidth + (screenWidth / 2) + ScreenUtil.getPxByDp(this.mContext, 16));
        } else if (i3 == 8) {
            layoutParams.addRule(21);
            layoutParams.setMarginEnd((screenWidth / 2) - ScreenUtil.getPxByDp(this.mLyContentOne.getContext(), 4));
        }
        this.mGuideOneView.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(this.mImgTriangleOne.getLayoutParams().width, this.mImgTriangleOne.getLayoutParams().height);
        layoutParams2.addRule(3, this.mLyContentOne.getId());
        layoutParams2.topMargin = -UIUtil.dip2px(this.mContext, 1.0d);
        if (i3 <= 4 || i3 >= 7) {
            layoutParams2.addRule(19, this.mLyContentOne.getId());
            layoutParams2.setMarginEnd(ScreenUtil.getPxByDp(this.mLyContentOne.getContext(), 12));
        } else {
            layoutParams2.addRule(20);
            layoutParams2.setMarginStart(ScreenUtil.getPxByDp(this.mLyContentOne.getContext(), 12));
        }
        this.mImgTriangleOne.setLayoutParams(layoutParams2);
        if (this.mGuideOneView.getVisibility() == 8) {
            this.mGuideOneView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRedCouponsGuid() {
        int height = this.mItemGridView.getHeight() / 3;
        if (this.menuItemAdp.getData().size() <= 8) {
            height = this.mItemGridView.getHeight() / 2;
        }
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 112.0d)) / 4;
        int i2 = 0;
        while (true) {
            if (i2 >= this.menuItemAdp.getData().size()) {
                i2 = 0;
                break;
            } else if (this.menuItemAdp.getData().get(i2).equals("红包卡券")) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = i2 + 1;
        int pxByDp = ScreenUtil.getPxByDp(this.mContext, 16);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        if (i3 == 5) {
            layoutParams.topMargin = this.mItemGridView.getHeight() - pxByDp;
            layoutParams.setMarginStart(screenWidth / 2);
        } else if (i3 == 7) {
            layoutParams.topMargin = (this.mItemGridView.getHeight() - height) - pxByDp;
            layoutParams.addRule(21);
            layoutParams.setMarginEnd(screenWidth + (screenWidth / 2) + ScreenUtil.getPxByDp(this.mContext, 12));
        }
        this.mGuideTwoView.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(this.mImgTriangleTwo.getLayoutParams().width, this.mImgTriangleTwo.getLayoutParams().height);
        if (i3 <= 4 || i3 >= 7) {
            layoutParams2.addRule(19, this.mLyContentTwo.getId());
            layoutParams2.setMarginEnd(ScreenUtil.getPxByDp(this.mContext, 12));
        } else {
            layoutParams2.addRule(20);
            layoutParams2.setMarginStart(ScreenUtil.getPxByDp(this.mContext, 12));
        }
        this.mImgTriangleTwo.setLayoutParams(layoutParams2);
        this.mGuideTwoView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateItemGridData(boolean addBook) {
        int iIndexOf = this.menuItemAdp.getData().indexOf("我的收藏");
        if (iIndexOf == -1) {
            iIndexOf = 1;
        }
        if (addBook) {
            if (!this.menuItemAdp.getData().contains("我的书架")) {
                this.menuItemAdp.getData().add(iIndexOf + 1, "我的书架");
                this.menuItemAdp.getData().add(iIndexOf + 2, "阅读历史");
            }
        } else if (this.menuItemAdp.getData().contains("我的书架")) {
            this.menuItemAdp.getData().remove("我的书架");
            this.menuItemAdp.getData().remove("阅读历史");
        }
        this.menuItemAdp.notifyDataSetChanged();
        this.mLyItemOne.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PersonalCenter2Fragment.this.mLyItemOne.getTop();
                LogUtils.e("top_right", "整个页面高度===》" + PersonalCenter2Fragment.this.mScrollView.getHeight() + ";hhh==>" + PersonalCenter2Fragment.this.mScrollView.getMeasuredHeight());
                if (SharePreferencesUtils.readBooleanConfig(CommonParameter.showCouponsCenterGuid, true, ((BaseFragment) PersonalCenter2Fragment.this).mContext)) {
                    PersonalCenter2Fragment.this.showCouponsCenterGuid();
                } else if (SharePreferencesUtils.readBooleanConfig(CommonParameter.showRedCouponsGuid, true, ((BaseFragment) PersonalCenter2Fragment.this).mContext)) {
                    PersonalCenter2Fragment.this.mEmptyViewOne.setVisibility(0);
                    PersonalCenter2Fragment.this.mEmptyViewMenu.setVisibility(0);
                    PersonalCenter2Fragment.this.showRedCouponsGuid();
                }
                PersonalCenter2Fragment.this.mLyItemOne.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_personal_detail;
    }

    public void getPopTips() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "notice_for_rules");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.vipnoticeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                JSONObject jSONObjectOptJSONObject;
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code")) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null || "1".equals(jSONObjectOptJSONObject.optString("display"))) {
                        return;
                    }
                    for (String str : PersonalCenter2Fragment.this.menuItemAdp.getData()) {
                        if (str.equals("会员奖励")) {
                            PersonalCenter2Fragment.this.menuItemAdp.remove((PersonalMenuItemAdp) str);
                            return;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getShareTask() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getShareTask, new AjaxParams(), new AnonymousClass11());
    }

    public void getVip() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.vipToApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str = "1";
                super.onSuccess((AnonymousClass10) s2);
                PersonalCenter2Fragment.this.getShareTask();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optJSONObject("data").optString("encryption");
                    if (TextUtils.isEmpty(strOptString)) {
                        return;
                    }
                    String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFYS, strOptString);
                    VipTipsBean vipTipsBean = new VipTipsBean();
                    vipTipsBean.setCode(jSONObject.optString("code"));
                    vipTipsBean.setData((VipTipsBean.DataDTO) new Gson().fromJson(strDecode, VipTipsBean.DataDTO.class));
                    vipTipsBean.setServer_time(jSONObject.optString("server_time"));
                    vipTipsBean.setMessage(jSONObject.optString("message"));
                    if (vipTipsBean.getCode().equals("200")) {
                        String available = vipTipsBean.getData().getVip().getAvailable();
                        if (TextUtils.isEmpty(available)) {
                            available = "0";
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.vip_available, available, ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                        String str2 = "立即续费";
                        if ("1".equals(vipTipsBean.getData().getVip().getAvailable())) {
                            PersonalCenter2Fragment.this.vipbgview.setVisibility(0);
                            boolean z2 = 1 == vipTipsBean.getData().getVip().getIs_vip();
                            PersonalCenter2Fragment.this.anniuclickTv.setText(z2 ? "立即续费" : "立即开通");
                            PersonalCenter2Fragment.this.nologinLineView.setVisibility(z2 ? 8 : 0);
                            PersonalCenter2Fragment.this.loginLineView.setVisibility(z2 ? 0 : 8);
                            UserConfig.getInstance().getUser().setIs_vip(z2 ? "1" : "0");
                            UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                            if (z2) {
                                if (PersonalCenter2Fragment.this.getActivity() != null) {
                                    CommonUtil.mDoDrawable(PersonalCenter2Fragment.this.getActivity(), PersonalCenter2Fragment.this.tv_my_nickname, R.drawable.vip_iconimg2, 2);
                                }
                                SharePreferencesUtils.writeStrConfig(CommonParameter.vip_due_soon, String.valueOf(vipTipsBean.getData().getVip().getVip_due_soon()), ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                                UserConfig.getInstance().getUser().setVip_deadline(vipTipsBean.getData().getVip().getVip_deadline());
                                UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                                PersonalCenter2Fragment.this.datelineTv.setText(vipTipsBean.getData().getVip().getSimple_declaration());
                            } else {
                                PersonalCenter2Fragment.this.simplinevip.setText(vipTipsBean.getData().getVip().getSimple_declaration());
                            }
                        } else {
                            PersonalCenter2Fragment.this.vipbgview.setVisibility(8);
                            PersonalCenter2Fragment.this.svipbgview.getLayoutParams().width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            PersonalCenter2Fragment.this.rel_svidmemview.getLayoutParams().width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            PersonalCenter2Fragment.this.mImgSVipBg.getLayoutParams().width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            UserConfig.getInstance().getUser().setIs_vip("2");
                            UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                        }
                        if ("1".equals(vipTipsBean.getData().getSvip().getAvailable())) {
                            PersonalCenter2Fragment.this.svipbgview.setVisibility(0);
                            boolean z3 = true;
                            if (1 != vipTipsBean.getData().getSvip().getIs_vip()) {
                                z3 = false;
                            }
                            TextView textView = PersonalCenter2Fragment.this.anniusvipclickTv;
                            if (!z3) {
                                str2 = "立即升级";
                            }
                            textView.setText(str2);
                            PersonalCenter2Fragment.this.nologinsvidLineView.setVisibility(z3 ? 8 : 0);
                            PersonalCenter2Fragment.this.loginsvidLineView.setVisibility(z3 ? 0 : 8);
                            if (!z3) {
                                str = "0";
                            }
                            SharePreferencesUtils.writeStrConfig(CommonParameter.issvip, str, ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                            if (z3) {
                                if (PersonalCenter2Fragment.this.getActivity() != null) {
                                    CommonUtil.mDoDrawable(PersonalCenter2Fragment.this.getActivity(), PersonalCenter2Fragment.this.tv_my_nickname, R.drawable.svip100_icon, 2);
                                }
                                PersonalCenter2Fragment.this.datesviplineTv.setText(vipTipsBean.getData().getSvip().getSimple_declaration());
                                SharePreferencesUtils.writeStrConfig(CommonParameter.svip_available, vipTipsBean.getData().getSvip().getAvailable() + "", ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                                SharePreferencesUtils.writeStrConfig(CommonParameter.svip_due_soon, vipTipsBean.getData().getSvip().getVip_due_soon() + "", ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                                SharePreferencesUtils.writeStrConfig(CommonParameter.show_line_svip, vipTipsBean.getData().getSvip().getVip_deadline() + "", ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                            } else {
                                PersonalCenter2Fragment.this.simplinesvip.setText(vipTipsBean.getData().getSvip().getSimple_declaration());
                            }
                        } else {
                            PersonalCenter2Fragment.this.svipbgview.setVisibility(8);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) PersonalCenter2Fragment.this.vipbgview.getLayoutParams();
                            layoutParams.width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            PersonalCenter2Fragment.this.vipbgview.setLayoutParams(layoutParams);
                            PersonalCenter2Fragment.this.rel_memview.getLayoutParams().width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            PersonalCenter2Fragment.this.mImgVipBg.getLayoutParams().width = UIUtil.getScreenWidth(PersonalCenter2Fragment.this.getContext()) - UIUtil.dip2px(PersonalCenter2Fragment.this.getContext(), 32.0d);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.issvip, "2", ((BaseFragment) PersonalCenter2Fragment.this).mContext);
                        }
                        if (PersonalCenter2Fragment.this.vipbgview.getVisibility() == 8 && PersonalCenter2Fragment.this.svipbgview.getVisibility() == 8) {
                            PersonalCenter2Fragment.this.vipsHoriz.setVisibility(8);
                            PersonalCenter2Fragment.this.marginsbgview.setVisibility(0);
                            PersonalCenter2Fragment.this.tv_my_nickname.setCompoundDrawables(null, null, null, null);
                        } else {
                            PersonalCenter2Fragment.this.vipsHoriz.setVisibility(0);
                            PersonalCenter2Fragment.this.marginsbgview.setVisibility(8);
                        }
                        PersonalCenter2Fragment.this.recordPushLabel(new JSONObject(s2).optJSONObject("data").optString("is_vip"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws IllegalStateException {
        this.simplinevip = (TextView) holder.get(R.id.simplinevip);
        this.simplinesvip = (TextView) holder.get(R.id.simplinesvip);
        this.anniusvipclickTv = (TextView) holder.get(R.id.anniusvipclickTv);
        this.datesviplineTv = (TextView) holder.get(R.id.datesviplineTv);
        this.loginsvidLineView = (LinearLayout) holder.get(R.id.loginsvidLineView);
        this.nologinsvidLineView = (LinearLayout) holder.get(R.id.nologinsvidLineView);
        this.mLyShare = (RelativeLayout) holder.get(R.id.ly_share);
        this.mTvInviteShare = (TextView) holder.get(R.id.tv_invite);
        this.vipsHoriz = (HorizontalScrollView) holder.get(R.id.vipsHoriz);
        this.mLyNoLogin = (LinearLayout) holder.get(R.id.ly_no_login);
        this.fragview = (FrameLayout) holder.get(R.id.fragview);
        this.rel_memview = (RelativeLayout) holder.get(R.id.rel_memview);
        this.rel_svidmemview = (RelativeLayout) holder.get(R.id.rel_svidmemview);
        this.marginsbgview = holder.get(R.id.marginsbgview);
        this.vipbgview = (FrameLayout) holder.get(R.id.vipbgview);
        this.svipbgview = (FrameLayout) holder.get(R.id.svipbgview);
        this.logindetail = (LinearLayout) holder.get(R.id.logindetail);
        this.myfeedbackTv = (LinearLayout) holder.get(R.id.myfeedbackTv);
        this.myContactTv = (LinearLayout) holder.get(R.id.myContactTv);
        this.qNameTv = (TextView) holder.get(R.id.qNameTv);
        this.reldaojishi = (LinearLayout) holder.get(R.id.reldaojishi);
        this.mycenter_icon = (CircleImageView) holder.get(R.id.mycenter_icon);
        this.prenimg = (ImageView) holder.get(R.id.prenimg);
        this.tv_my_nickname = (TextView) holder.get(R.id.tv_my_nickname);
        this.tv_school_kaosheng = (TextView) holder.get(R.id.tv_school_kaosheng);
        this.isLoginBtn = (TextView) holder.get(R.id.isLoginBtn);
        this.nologinLineView = (LinearLayout) holder.get(R.id.nologinLineView);
        this.loginLineView = (LinearLayout) holder.get(R.id.loginLineView);
        this.anniuclickTv = (TextView) holder.get(R.id.anniuclickTv);
        this.datelineTv = (TextView) holder.get(R.id.datelineTv);
        this.mTvExamDay = (TextView) holder.get(R.id.tv_exam_day);
        this.tvModeSwitch = (TextView) holder.get(R.id.tv_mode_switch);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ly_setting);
        this.mLyChat = (RelativeLayout) holder.get(R.id.ly_chat);
        this.mLyNotice = (RelativeLayout) holder.get(R.id.ly_notice);
        this.mLyComment = (RelativeLayout) holder.get(R.id.ly_comment);
        this.mLyLike = (RelativeLayout) holder.get(R.id.ly_like);
        this.mTvNoticeUnredNum = (TextView) holder.get(R.id.tv_notice_unred_number);
        this.mTvCommentUnredNum = (TextView) holder.get(R.id.tv_comment_unred_number);
        this.mTvLikeUnredNum = (TextView) holder.get(R.id.tv_like_unred_number);
        this.mTvChatUnredNum = (TextView) holder.get(R.id.tv_follow_unred_number);
        this.mLyUnRedNoticeMsg = (RelativeLayout) holder.get(R.id.ly_unred_notice_msg);
        this.mLyUnRedChatMsg = (RelativeLayout) holder.get(R.id.ly_unred_chat_msg);
        this.mLyUnRedCommentMsg = (RelativeLayout) holder.get(R.id.ly_unred_comment_msg);
        this.mLyUnRedPraiseMsg = (RelativeLayout) holder.get(R.id.ly_unred_praise_msg);
        this.mLyAppTest = (LinearLayout) holder.get(R.id.ly_app_test);
        this.mTvAppVersion = (TextView) holder.get(R.id.tv_app_version);
        this.mTvPercent = (TextView) holder.get(R.id.tv_percent);
        this.mTvStatisticsInfo = (TextView) holder.get(R.id.tv_statistics_info);
        this.mLyStatistics = (RelativeLayout) holder.get(R.id.ly_statistics);
        this.mLyRank = (RelativeLayout) holder.get(R.id.ly_rank);
        this.mImgVipBg = (ImageView) holder.get(R.id.img_vip_bg);
        this.mImgSVipBg = (ImageView) holder.get(R.id.img_svip_bg);
        LinearLayout linearLayout2 = (LinearLayout) holder.get(R.id.ly_item_other);
        this.mLyItemOne = (LinearLayout) holder.get(R.id.ly_item_one);
        this.mItemGridView = (RecyclerView) holder.get(R.id.item_grid);
        this.mBtnScan = (ImageView) holder.get(R.id.btn_scan);
        this.lyAppSign = (LinearLayout) holder.get(R.id.ly_app_sign);
        this.signRl = (RelativeLayout) holder.get(R.id.sign_rl);
        this.signPointV = holder.get(R.id.sign_point_v);
        this.mCouponsRecycler = (RecyclerView) holder.get(R.id.coupons_recycler);
        this.mLyParent = (RelativeLayout) holder.get(R.id.parent_layout);
        this.line = holder.get(R.id.main_line);
        this.mLyActiveView = (LinearLayout) holder.get(R.id.ly_active_view);
        this.mScrollView = (NestedScrollView) holder.get(R.id.scrollView);
        this.mLyRankView = (LinearLayout) holder.get(R.id.ly_rank_view);
        this.mLyActive = (LinearLayout) holder.get(R.id.ly_active);
        this.mGuideOneView = holder.get(R.id.ly_guide_one);
        this.mGuideTwoView = holder.get(R.id.ly_guide_two);
        this.mImgTriangleOne = (ImageView) holder.get(R.id.iv_down_triangle);
        this.mLyContentOne = (LinearLayout) holder.get(R.id.ll_content);
        this.mBtnGuideKnowOne = (TextView) holder.get(R.id.tv_ok);
        this.mImgTriangleTwo = (ImageView) holder.get(R.id.iv_down_triangle_two);
        this.mBtnGuideKnowTwo = (TextView) holder.get(R.id.tv_ok_two);
        this.mLyContentTwo = (LinearLayout) holder.get(R.id.ll_content_two);
        this.mEmptyViewOne = holder.get(R.id.view_one);
        this.mEmptyViewMenu = holder.get(R.id.view_menu);
        this.mLyDineCouponsView = (RelativeLayout) holder.get(R.id.ly_deline_coupons);
        this.mImgVipBg.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        this.vipbgview.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        this.rel_memview.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        this.svipbgview.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        this.rel_svidmemview.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        this.mImgSVipBg.getLayoutParams().width = UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 65.0d);
        int screenWidth = (UIUtil.getScreenWidth(requireContext()) - UIUtil.dip2px(requireContext(), 32.0d)) / 4;
        int iDip2px = UIUtil.dip2px(requireContext(), 16.0d);
        UIUtil.dip2px(requireContext(), 12.0d);
        int screenWidth2 = (UIUtil.getScreenWidth(requireContext()) - UIUtil.dip2px(requireContext(), 112.0d)) / 4;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyItemOne.getLayoutParams();
        layoutParams.setMarginEnd(iDip2px);
        layoutParams.setMarginStart(iDip2px);
        this.mLyItemOne.setLayoutParams(layoutParams);
        linearLayout2.setLayoutParams(layoutParams);
        LogUtils.e("item_width", "width====>" + screenWidth2);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams2.setMarginEnd(iDip2px);
        layoutParams2.width = screenWidth2;
        linearLayout.setLayoutParams(layoutParams2);
        this.myContactTv.setLayoutParams(layoutParams2);
        this.myfeedbackTv.setLayoutParams(layoutParams2);
        Log.e("layout_width", "width:" + screenWidth + ";widthDp=" + ScreenUtil.getDpByPx(requireContext(), screenWidth));
        this.mLyChat.getLayoutParams().width = screenWidth;
        this.mLyNotice.getLayoutParams().width = screenWidth;
        this.mLyComment.getLayoutParams().width = screenWidth;
        this.mLyLike.getLayoutParams().width = screenWidth;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        new PagerSnapHelper().attachToRecyclerView(this.mCouponsRecycler);
        this.mCouponsRecycler.setLayoutManager(linearLayoutManager);
        CouponsDateLineAdp couponsDateLineAdp = new CouponsDateLineAdp();
        this.couponsAdp = couponsDateLineAdp;
        this.mCouponsRecycler.setAdapter(couponsDateLineAdp);
        this.couponsAdp.setOnItemActionLisenter(new CouponsDateLineAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.2
            @Override // com.psychiatrygarden.activity.mine.CouponsDateLineAdp.OnItemActionLisenter
            public void btnToUs(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item) {
                PublicMethodActivity.getInstance().couponsJump(item.getJump());
            }
        });
        couponsScroll();
        this.tvModeSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15717c.lambda$initViews$0(view);
            }
        });
        this.logindetail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15506c.lambda$initViews$1(view);
            }
        });
        this.myContactTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.j9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15690c.lambda$initViews$2(view);
            }
        });
        this.myfeedbackTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.l9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15810c.lambda$initViews$3(view);
            }
        });
        this.mLyChat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.m9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15836c.lambda$initViews$4(view);
            }
        });
        this.mLyNotice.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.n9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15869c.lambda$initViews$5(view);
            }
        });
        this.mLyComment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.o9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15891c.lambda$initViews$6(view);
            }
        });
        this.mLyLike.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.p9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15914c.lambda$initViews$7(view);
            }
        });
        this.mBtnScan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.q9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15940c.lambda$initViews$8(view);
            }
        });
        this.qNameTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.r9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15967c.lambda$initViews$9(view);
            }
        });
        this.rel_svidmemview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.t9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16012c.lambda$initViews$10(view);
            }
        });
        this.rel_memview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.u9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16044c.lambda$initViews$11(view);
            }
        });
        this.anniusvipclickTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.v9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16071c.lambda$initViews$12(view);
            }
        });
        this.anniuclickTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.w9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16096c.lambda$initViews$13(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.x9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16116c.lambda$initViews$14(view);
            }
        });
        this.mLyNoLogin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.y9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16142c.lambda$initViews$15(view);
            }
        });
        this.mLyAppTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.z9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16164c.lambda$initViews$16(view);
            }
        });
        this.mLyRank.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.aa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15440c.lambda$initViews$17(view);
            }
        });
        this.mLyStatistics.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.a9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15439c.lambda$initViews$18(view);
            }
        });
        this.signRl.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15467c.lambda$initViews$19(view);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshPersonal");
        this.receiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("refreshPersonal".equals(intent.getAction())) {
                    PersonalCenter2Fragment.this.mToShowView();
                }
            }
        };
        final int screenWidth3 = UIUtil.getScreenWidth(requireContext()) - UIUtil.dip2px(requireContext(), 32.0d);
        this.vipsHoriz.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.fragmenthome.d9
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f15540c.lambda$initViews$20(screenWidth3, view, i2, i3, i4, i5);
            }
        });
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(this.receiver, intentFilter);
        PersonalMenuItemAdp personalMenuItemAdp = new PersonalMenuItemAdp();
        this.menuItemAdp = personalMenuItemAdp;
        this.mItemGridView.setAdapter(personalMenuItemAdp);
        initItemGridData();
        this.menuItemAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.e9
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15566c.lambda$initViews$21(baseQuickAdapter, view, i2);
            }
        });
        this.mBtnGuideKnowOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15592c.lambda$initViews$22(view);
            }
        });
        this.mBtnGuideKnowTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.g9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15622c.lambda$initViews$23(view);
            }
        });
        this.mEmptyViewOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.h9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PersonalCenter2Fragment.lambda$initViews$24(view);
            }
        });
        this.mEmptyViewMenu.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.i9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PersonalCenter2Fragment.lambda$initViews$25(view);
            }
        });
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:47:0x00cc
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public void mToShowView() {
        /*
            Method dump skipped, instructions count: 361
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.PersonalCenter2Fragment.mToShowView():void");
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || data == null || (extras = data.getExtras()) == null || extras.getInt(CodeUtils.RESULT_TYPE) != 1) {
            return;
        }
        String string = extras.getString(CodeUtils.RESULT_STRING);
        LogUtils.e("scan_code_res", "result=" + string);
        String[] strArrSplit = string.split("=");
        if (strArrSplit == null || strArrSplit.length != 3) {
            return;
        }
        String str = strArrSplit[1].split("&")[0];
        String str2 = strArrSplit[2];
        LogUtils.e("scan_code_res", "id=" + str + ";questionid=" + str2);
        loadVideoInfo(str, str2);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1) {
            int screenWidth = (UIUtil.getScreenWidth(requireContext()) - UIUtil.dip2px(requireContext(), 32.0d)) / 4;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyNotice.getLayoutParams();
            layoutParams.width = screenWidth;
            this.mLyChat.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLyNotice.getLayoutParams();
            layoutParams2.width = screenWidth;
            this.mLyNotice.setLayoutParams(layoutParams2);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLyNotice.getLayoutParams();
            layoutParams3.width = screenWidth;
            this.mLyComment.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mLyNotice.getLayoutParams();
            layoutParams4.width = screenWidth;
            this.mLyLike.setLayoutParams(layoutParams4);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(this.receiver);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (TextUtils.equals("UPDATE_MODE_SWITCH", str)) {
            this.tvModeSwitch.setVisibility(isLogin() ? 0 : 8);
            this.tvModeSwitch.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? "日间" : "夜间");
            return;
        }
        if (str.equals("PushIMReadEvent")) {
            if (this.mTvChatUnredNum == null) {
                return;
            }
            try {
                int unreadMessageCount = ProjectApp.instance().getUnreadMessageCount();
                RelativeLayout relativeLayout = this.mLyUnRedChatMsg;
                if (unreadMessageCount <= 0) {
                    i = 8;
                }
                relativeLayout.setVisibility(i);
                this.mTvChatUnredNum.setText(unreadMessageCount <= 0 ? "" : unreadMessageCount > 99 ? "···" : String.valueOf(unreadMessageCount));
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mTvChatUnredNum.setText("");
                this.mLyUnRedChatMsg.setVisibility(8);
                return;
            }
        }
        if ("qiehuan".equals(str) || str.equals(EventBusConstant.EVENT_VIP_CODE)) {
            getVip();
            return;
        }
        if (str.equals("PersonalCenterFragment")) {
            mToShowView();
            return;
        }
        if (str.equals(CommonParameter.SYStem_Red_Dot)) {
            return;
        }
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            mToShowView();
        } else if (str.equals("refreshInviteActive")) {
            getShareTask();
        } else if (str.equals(EventBusConstant.EVENT_SIGN_SUCCESS)) {
            getSignInfo();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LogUtils.e("person_recycler_vis", "页面隐藏不可见===》");
            this.mHandler.removeCallbacksAndMessages(null);
        } else {
            mToShowView();
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(8192);
            }
            initwriteStatusBar();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length == 1) {
            int i2 = grantResults[0];
            if (i2 == 0) {
                ActivityCompat.startActivityForResult(getActivity(), new Intent(this.mContext, (Class<?>) CaptureActivity.class), 999, null);
            } else {
                if (i2 != -1 || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Permission.CAMERA)) {
                    return;
                }
                ToastUtil.shortToast(this.mContext, "请检查app相机权限是否打开！");
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        CouponsDateLineAdp couponsDateLineAdp = this.couponsAdp;
        if (couponsDateLineAdp == null || couponsDateLineAdp.getData().size() <= 0) {
            return;
        }
        this.couponsAdp.notifyItemChanged(this.mCurrentCouponsPos, 1);
        this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }
}
