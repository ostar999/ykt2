package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.constants.EaseConstant;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.IdentityAuthPopWindow;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class UserCommentInfoActivity extends BaseActivity implements QuestionDataCallBack<String>, View.OnClickListener {
    private CircleImageView avaimg;
    private TextView beiguanzhu;
    private TextView cusrenzheng;
    private TextView guanzhu;
    private GlideImageView icon_remove_member;
    private TextView identity_description;
    private TextView identity_judged_textview;
    private String is_banned;
    private String is_owner;
    private ImageView isvipimg;
    private CircleImageView jiav;
    private LinearLayout liner1;
    private LinearLayout liner2;
    private LinearLayout mLyBlueIdentity;
    private LinearLayout mLyIdentity;
    private RelativeLayout mLyUserHead;
    private LinearLayout mLyYellowIdentity;
    private TextView mTvBlueIdentity;
    private TextView mTvCollectionNumber;
    private TextView mTvCommentNumber;
    private TextView mTvGetPraise;
    private TextView mTvPostNumber;
    private TextView mTvPostRedNumber;
    private TextView mTvPraiseNumber;
    private TextView mTvYellowIdentity;
    private TextView mycommnum;
    private TextView myjingyannum;
    private TextView mytiezinum;
    private TextView name;
    private RelativeLayout rl_comment_jingyan;
    private RelativeLayout rl_comment_kecheng;
    private RelativeLayout rl_comment_tiku;
    private TextView schoolname;
    private ImageView seximg;
    private String to_user_uuid;
    private TextView tvGuanzhuimg;
    private TextView tvSixinimg;
    private String user_id;
    private String user_identity;
    private ImageView verimg;
    private View view_line;

    /* renamed from: com.psychiatrygarden.activity.UserCommentInfoActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            UserCommentInfoActivity.this.startActivity(new Intent(UserCommentInfoActivity.this, (Class<?>) CircleSchoolVerifyActivity.class));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(UserInfoBean userInfoBean, View view) {
            if (userInfoBean.getData().getIs_authentication().equals("1")) {
                new XPopup.Builder(UserCommentInfoActivity.this).asCustom(new IdentityAuthPopWindow(UserCommentInfoActivity.this, new IdentityAuthPopWindow.IdentityAuthClickIml() { // from class: com.psychiatrygarden.activity.iq
                    @Override // com.psychiatrygarden.widget.IdentityAuthPopWindow.IdentityAuthClickIml
                    public final void mIdentityClick() {
                        this.f12537a.lambda$onSuccess$0();
                    }
                })).show();
            } else {
                UserCommentInfoActivity.this.startActivity(new Intent(UserCommentInfoActivity.this, (Class<?>) CircleSchoolVerifyActivity.class));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2(View view) {
            ToastUtil.shortToast(UserCommentInfoActivity.this.mContext, "已认证");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$3(View view) {
            UserCommentInfoActivity.this.CancelFriends();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$4(View view) {
            UserCommentInfoActivity.this.ADDFriendsList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$5(UserInfoBean userInfoBean, View view) {
            if (TextUtils.isEmpty(userInfoBean.getData().getUser_uuid())) {
                UserCommentInfoActivity.this.AlertToast("获取用户失败,请稍后！");
                return;
            }
            Intent intent = new Intent(UserCommentInfoActivity.this, (Class<?>) ChatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
            intent.putExtra("name", userInfoBean.getData().getNickname());
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, userInfoBean.getData().getUser_uuid());
            UserCommentInfoActivity.this.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$6(UserInfoBean userInfoBean, View view) {
            if (UserCommentInfoActivity.this.isLogin()) {
                Intent intent = new Intent(UserCommentInfoActivity.this.mContext, (Class<?>) DiscussPublicActivity.class);
                String str = UserCommentInfoActivity.this.user_id;
                String sex = userInfoBean.getData().getSex();
                if (str.equals(UserConfig.getUserId())) {
                    intent.putExtra("title", "题库评论");
                } else if (!sex.equals("1") && sex.equals("2")) {
                    intent.putExtra("title", "她的评论");
                } else {
                    intent.putExtra("title", "他的评论");
                }
                intent.putExtra("target_user_id", str);
                intent.putExtra("comment_type", "2");
                intent.putExtra("module_type", 1);
                intent.putExtra("commentEnum", DiscussStatus.IPostAQuestionBankComment);
                UserCommentInfoActivity.this.startActivity(intent);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$7(UserInfoBean userInfoBean, View view) {
            if (UserCommentInfoActivity.this.isLogin()) {
                Intent intent = new Intent(UserCommentInfoActivity.this.mContext, (Class<?>) DiscussPublicActivity.class);
                String str = UserCommentInfoActivity.this.user_id;
                String sex = userInfoBean.getData().getSex();
                if (str.equals(UserConfig.getUserId())) {
                    intent.putExtra("title", "经验评论");
                } else if (!sex.equals("1") && sex.equals("2")) {
                    intent.putExtra("title", "她的评论");
                } else {
                    intent.putExtra("title", "他的评论");
                }
                intent.putExtra("target_user_id", str);
                intent.putExtra("comment_type", "2");
                intent.putExtra("module_type", 3);
                intent.putExtra("commentEnum", DiscussStatus.ICommentOnMyExperience);
                UserCommentInfoActivity.this.startActivity(intent);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$8(View view) {
            if (UserCommentInfoActivity.this.isLogin()) {
                Intent intent = new Intent(UserCommentInfoActivity.this.mContext, (Class<?>) DiscussPublicActivity.class);
                intent.putExtra("target_user_id", UserCommentInfoActivity.this.user_id);
                intent.putExtra("title", "帖子评论");
                intent.putExtra("comment_type", "2");
                intent.putExtra("module_type", 12);
                intent.putExtra("commentEnum", DiscussStatus.IPostForumComments);
                UserCommentInfoActivity.this.startActivity(intent);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            UserCommentInfoActivity.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
            UserCommentInfoActivity.this.showProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            try {
                final UserInfoBean userInfoBean = (UserInfoBean) new Gson().fromJson(t2, UserInfoBean.class);
                if (userInfoBean.getCode().equals("200")) {
                    UserCommentInfoActivity.this.user_id = userInfoBean.getData().getUser_id();
                    UserCommentInfoActivity.this.to_user_uuid = userInfoBean.getData().getUser_uuid();
                    GlideApp.with(UserCommentInfoActivity.this.getApplicationContext()).load((Object) GlideUtils.generateUrl(userInfoBean.getData().getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.weiloginappimg)).into(UserCommentInfoActivity.this.avaimg);
                    UserCommentInfoActivity.this.name.setText(userInfoBean.getData().getNickname());
                    if (userInfoBean.getData().getIs_svip().equals("1")) {
                        UserCommentInfoActivity.this.isvipimg.setVisibility(0);
                        UserCommentInfoActivity.this.isvipimg.setImageResource(R.drawable.svip333img);
                    } else if (userInfoBean.getData().getIs_vip().equals("1")) {
                        UserCommentInfoActivity.this.isvipimg.setVisibility(0);
                        UserCommentInfoActivity.this.isvipimg.setImageResource(R.drawable.vipimg);
                    } else {
                        UserCommentInfoActivity.this.isvipimg.setVisibility(8);
                    }
                    if (userInfoBean.getData().getSex().equals("1")) {
                        GlideApp.with(UserCommentInfoActivity.this.getApplicationContext()).load(Integer.valueOf(R.drawable.male_icon)).into(UserCommentInfoActivity.this.seximg);
                    } else if (userInfoBean.getData().getSex().equals("2")) {
                        GlideApp.with(UserCommentInfoActivity.this.getApplicationContext()).load(Integer.valueOf(R.drawable.female_icon)).into(UserCommentInfoActivity.this.seximg);
                    } else {
                        UserCommentInfoActivity.this.seximg.setVisibility(8);
                    }
                    if (TextUtils.isEmpty(userInfoBean.getData().getIdentity_judged())) {
                        UserCommentInfoActivity.this.schoolname.setVisibility(8);
                    } else {
                        UserCommentInfoActivity.this.schoolname.setVisibility(0);
                    }
                    UserCommentInfoActivity.this.schoolname.setText(userInfoBean.getData().getIdentity_judged());
                    UserCommentInfoActivity.this.identity_judged_textview.setText(userInfoBean.getData().getUser_type_judged());
                    String str = SkinManager.getCurrentSkinType(UserCommentInfoActivity.this) == 1 ? "#7380A9" : "#303030";
                    String str2 = userInfoBean.getData().getFollow_user() + " 关注 · ";
                    if (userInfoBean.getData().getFollow_user().equals("0")) {
                        UserCommentInfoActivity.this.guanzhu.setText(str2);
                    } else {
                        UserCommentInfoActivity.this.guanzhu.setText(CharacterParser.getSpannableText(str2, 0, userInfoBean.getData().getFollow_user().length(), str));
                    }
                    if (userInfoBean.getData().getTo_follow_user().equals("0")) {
                        UserCommentInfoActivity.this.beiguanzhu.setText(userInfoBean.getData().getTo_follow_user() + " 被关注 · ");
                    } else {
                        UserCommentInfoActivity.this.beiguanzhu.setText(CharacterParser.getSpannableText(userInfoBean.getData().getTo_follow_user() + " 被关注 · ", 0, userInfoBean.getData().getTo_follow_user().length(), str));
                    }
                    if (userInfoBean.getData().getBe_praise_count().equals("0")) {
                        UserCommentInfoActivity.this.mTvGetPraise.setText(userInfoBean.getData().getBe_praise_count() + " 获赞");
                    } else {
                        UserCommentInfoActivity.this.mTvGetPraise.setText(CharacterParser.getSpannableText(userInfoBean.getData().getBe_praise_count() + " 获赞", 0, userInfoBean.getData().getBe_praise_count().length(), str));
                    }
                    UserCommentInfoActivity.this.cusrenzheng.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.aq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11087c.lambda$onSuccess$1(userInfoBean, view);
                        }
                    });
                    if (userInfoBean.getData().getIs_authentication().equals("1") || userInfoBean.getData().getIdentity().equals("1")) {
                        UserCommentInfoActivity.this.verimg.setVisibility(0);
                    } else {
                        UserCommentInfoActivity.this.verimg.setVisibility(8);
                    }
                    UserCommentInfoActivity.this.verimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11128c.lambda$onSuccess$2(view);
                        }
                    });
                    if (TextUtils.isEmpty(userInfoBean.getData().getIdentity()) || userInfoBean.getData().getIdentity().equals("0")) {
                        UserCommentInfoActivity.this.mLyIdentity.setVisibility(8);
                    } else if (userInfoBean.getData().getIdentity().equals("1")) {
                        UserCommentInfoActivity.this.mLyIdentity.setVisibility(0);
                        UserCommentInfoActivity.this.mLyYellowIdentity.setVisibility(0);
                        UserCommentInfoActivity.this.mTvYellowIdentity.setText(userInfoBean.getData().getIdentity_description());
                    } else if (userInfoBean.getData().getIdentity().equals("2")) {
                        UserCommentInfoActivity.this.mLyIdentity.setVisibility(0);
                        UserCommentInfoActivity.this.mLyBlueIdentity.setVisibility(0);
                        UserCommentInfoActivity.this.mTvBlueIdentity.setText(userInfoBean.getData().getIdentity_description());
                    }
                    if (UserCommentInfoActivity.this.user_id.equals(UserConfig.getUserId())) {
                        UserCommentInfoActivity.this.liner2.setVisibility(8);
                        UserCommentInfoActivity.this.liner1.setVisibility(0);
                    } else {
                        UserCommentInfoActivity.this.liner2.setVisibility(0);
                        UserCommentInfoActivity.this.liner1.setVisibility(8);
                    }
                    if (userInfoBean.getData().getIs_follow().equals("1") || userInfoBean.getData().getIs_follow().equals("2")) {
                        if (SkinManager.getCurrentSkinType(UserCommentInfoActivity.this) == 1) {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor("#606A8A"));
                        } else {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor("#606060"));
                        }
                        if (userInfoBean.getData().getIs_follow().equals("2")) {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setText("互关");
                            UserCommentInfoActivity.this.tvGuanzhuimg.setBackgroundResource(R.drawable.fff5f5f5_14);
                        } else {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setText("已关注");
                            UserCommentInfoActivity.this.tvGuanzhuimg.setBackgroundResource(R.drawable.shape_round_a4a1a0);
                        }
                        UserCommentInfoActivity.this.tvGuanzhuimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cq
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f12220c.lambda$onSuccess$3(view);
                            }
                        });
                    } else {
                        UserCommentInfoActivity.this.tvGuanzhuimg.setText("关注");
                        UserCommentInfoActivity.this.tvGuanzhuimg.setBackgroundResource(R.drawable.shape_full_red);
                        UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                        UserCommentInfoActivity.this.tvGuanzhuimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dq
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f12259c.lambda$onSuccess$4(view);
                            }
                        });
                    }
                    UserCommentInfoActivity.this.tvSixinimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.eq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12329c.lambda$onSuccess$5(userInfoBean, view);
                        }
                    });
                    UserCommentInfoActivity.this.rl_comment_tiku.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12417c.lambda$onSuccess$6(userInfoBean, view);
                        }
                    });
                    UserCommentInfoActivity.this.rl_comment_jingyan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12455c.lambda$onSuccess$7(userInfoBean, view);
                        }
                    });
                    UserCommentInfoActivity.this.rl_comment_kecheng.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hq
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12487c.lambda$onSuccess$8(view);
                        }
                    });
                } else if (userInfoBean.getCode().equals("203")) {
                    ToastUtil.shortToast(UserCommentInfoActivity.this, userInfoBean.getMessage());
                    UserCommentInfoActivity.this.finish();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            UserCommentInfoActivity.this.hideProgressDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ADDFriendsList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", this.user_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatFollow, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.UserCommentInfoActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                UserCommentInfoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                UserCommentInfoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                UserCommentInfoActivity.this.hideProgressDialog();
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        if (SkinManager.getCurrentSkinType(UserCommentInfoActivity.this) == 1) {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor("#606A8A"));
                        } else {
                            UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor("#606060"));
                        }
                        UserCommentInfoActivity.this.tvGuanzhuimg.setText("已关注");
                        UserCommentInfoActivity.this.tvGuanzhuimg.setBackgroundResource(R.drawable.shape_round_a4a1a0);
                        UserCommentInfoActivity.this.getUserInfo();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void CancelFriends() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", this.user_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatUnFollow, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.UserCommentInfoActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                UserCommentInfoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                UserCommentInfoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                UserCommentInfoActivity.this.hideProgressDialog();
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        UserCommentInfoActivity.this.tvGuanzhuimg.setText("关注");
                        UserCommentInfoActivity.this.tvGuanzhuimg.setBackgroundResource(R.drawable.shape_full_red);
                        UserCommentInfoActivity.this.tvGuanzhuimg.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                        UserCommentInfoActivity.this.getUserInfo();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getUserDynamicInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", this.user_id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.userDynamicInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.UserCommentInfoActivity.2
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
                    UserInfoBean userInfoBean = (UserInfoBean) new Gson().fromJson(t2, UserInfoBean.class);
                    if (userInfoBean.getCode().equals("200")) {
                        UserCommentInfoActivity.this.mTvPostNumber.setText(userInfoBean.getData().getArticle_publish_count());
                        UserCommentInfoActivity.this.mTvPostRedNumber.setText(userInfoBean.getData().getArticle_read_count());
                        UserCommentInfoActivity.this.mTvPraiseNumber.setText(userInfoBean.getData().getComment_like_count());
                        UserCommentInfoActivity.this.mTvCommentNumber.setText(userInfoBean.getData().getComment_count());
                        UserCommentInfoActivity.this.mTvCollectionNumber.setText(userInfoBean.getData().getCollect_count());
                    } else if (userInfoBean.getCode().equals("203")) {
                        ToastUtil.shortToast(UserCommentInfoActivity.this, userInfoBean.getMessage());
                        UserCommentInfoActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", this.user_id);
        ajaxParams.put("target_user_uuid", this.to_user_uuid);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(new Intent(this, (Class<?>) PersonalInfoEditActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        startActivity(new Intent(this, (Class<?>) CircleSchoolVerifyActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.mLyUserHead.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.mLyUserHead.setVisibility(8);
        } else {
            this.mLyUserHead.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        Intent intent = new Intent(this, (Class<?>) FollowActivity.class);
        intent.putExtra("user_id", "" + this.user_id);
        intent.putExtra("position", 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        Intent intent = new Intent(this, (Class<?>) FollowActivity.class);
        intent.putExtra("user_id", "" + this.user_id);
        intent.putExtra("position", 1);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatMemberDialog$6(int i2, String str) {
        if (2 == i2) {
            AjaxParams ajaxParams = new AjaxParams();
            if (getIntent().getExtras() != null) {
                ajaxParams.put("community_id", getIntent().getExtras().getString("community_id", ""));
                ajaxParams.put("id", getIntent().getExtras().getString("id", ""));
            }
            ajaxParams.put("to_user_id", this.user_id);
            ajaxParams.put("to_user_uuid", this.to_user_uuid);
            if (this.is_owner.equals("1")) {
                ChatRequest.getIntance(this).removeManager(ajaxParams, this);
                return;
            } else {
                if (this.is_owner.equals("0")) {
                    ChatRequest.getIntance(this).addManager(ajaxParams, this);
                    return;
                }
                return;
            }
        }
        if (i2 != 0) {
            if (1 == i2) {
                AjaxParams ajaxParams2 = new AjaxParams();
                if (getIntent().getExtras() != null) {
                    ajaxParams2.put("community_id", getIntent().getExtras().getString("community_id", ""));
                    ajaxParams2.put("id", getIntent().getExtras().getString("id", ""));
                }
                ajaxParams2.put("to_user_id", this.user_id);
                ajaxParams2.put("to_user_uuid", this.to_user_uuid);
                ChatRequest.getIntance(this).removeSingleMember(ajaxParams2, this);
                return;
            }
            return;
        }
        AjaxParams ajaxParams3 = new AjaxParams();
        if (getIntent().getExtras() != null) {
            ajaxParams3.put("community_id", getIntent().getExtras().getString("community_id", ""));
            ajaxParams3.put("id", getIntent().getExtras().getString("id", ""));
        }
        ajaxParams3.put("to_user_id", this.user_id);
        ajaxParams3.put("to_user_uuid", this.to_user_uuid);
        if (this.is_banned.equals("1")) {
            ChatRequest.getIntance(this).unBannedMember(ajaxParams3, this);
        } else {
            ChatRequest.getIntance(this).bannedSingleMember(ajaxParams3, this);
        }
    }

    private void showChatMemberDialog(String[] strings) {
        new XPopup.Builder(this).asCenterList("", strings, new OnSelectListener() { // from class: com.psychiatrygarden.activity.tp
            @Override // com.lxj.xpopup.interfaces.OnSelectListener
            public final void onSelect(int i2, String str) {
                this.f13965a.lambda$showChatMemberDialog$6(i2, str);
            }
        }).show();
    }

    public void getCommnumData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", "" + this.user_id);
        ajaxParams.put("target_user_uuid", this.to_user_uuid);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.userCommentStatUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.UserCommentInfoActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String o2) {
                JSONObject jSONObjectOptJSONObject;
                super.onSuccess((AnonymousClass5) o2);
                try {
                    JSONObject jSONObject = new JSONObject(o2);
                    if (!jSONObject.optString("code").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    if (!TextUtils.isEmpty(jSONObjectOptJSONObject.optString("question")) && UserCommentInfoActivity.this.mycommnum != null) {
                        UserCommentInfoActivity.this.mycommnum.setText(jSONObjectOptJSONObject.optString("question"));
                    }
                    if (!TextUtils.isEmpty(jSONObjectOptJSONObject.optString("experience")) && UserCommentInfoActivity.this.myjingyannum != null) {
                        UserCommentInfoActivity.this.myjingyannum.setText(jSONObjectOptJSONObject.optString("experience"));
                    }
                    if (TextUtils.isEmpty(jSONObjectOptJSONObject.optString(TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE)) || UserCommentInfoActivity.this.mytiezinum == null) {
                        return;
                    }
                    UserCommentInfoActivity.this.mytiezinum.setText(jSONObjectOptJSONObject.optString(TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.verimg = (ImageView) findViewById(R.id.verimg);
        this.mLyUserHead = (RelativeLayout) findViewById(R.id.ly_user_head);
        TextView textView = (TextView) findViewById(R.id.cusbianji);
        this.cusrenzheng = (TextView) findViewById(R.id.cusrenzheng);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.up
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14000c.lambda$init$0(view);
            }
        });
        this.cusrenzheng.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14130c.lambda$init$1(view);
            }
        });
        this.liner1 = (LinearLayout) findViewById(R.id.liner1);
        this.liner2 = (LinearLayout) findViewById(R.id.liner2);
        ((GlideImageView) findViewById(R.id.icon_left_back)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14163c.lambda$init$2(view);
            }
        });
        this.identity_description = (TextView) findViewById(R.id.identity_description);
        this.view_line = findViewById(R.id.view_line);
        this.mycommnum = (TextView) findViewById(R.id.mycommnum);
        this.myjingyannum = (TextView) findViewById(R.id.myjingyannum);
        this.mytiezinum = (TextView) findViewById(R.id.mytiezinum);
        this.jiav = (CircleImageView) findViewById(R.id.jiav);
        this.mLyIdentity = (LinearLayout) findViewById(R.id.ly_identity);
        this.mLyYellowIdentity = (LinearLayout) findViewById(R.id.ly_yellow_identity);
        this.mLyBlueIdentity = (LinearLayout) findViewById(R.id.ly_blue_identity);
        this.mTvYellowIdentity = (TextView) findViewById(R.id.tv_yellow_identity);
        this.mTvBlueIdentity = (TextView) findViewById(R.id.tv_blue_identity);
        this.icon_remove_member = (GlideImageView) findViewById(R.id.icon_remove_member);
        this.mTvPostNumber = (TextView) findViewById(R.id.tv_tips);
        this.mTvPostRedNumber = (TextView) findViewById(R.id.tv_red);
        this.mTvPraiseNumber = (TextView) findViewById(R.id.tv_like);
        this.mTvCommentNumber = (TextView) findViewById(R.id.tv_comment);
        this.mTvCollectionNumber = (TextView) findViewById(R.id.tv_collection);
        this.mTvGetPraise = (TextView) findViewById(R.id.tv_get_praise);
        this.avaimg = (CircleImageView) findViewById(R.id.avaimg);
        this.tvSixinimg = (TextView) findViewById(R.id.tv_sixinimg);
        this.tvGuanzhuimg = (TextView) findViewById(R.id.tv_guanzhuimg);
        this.name = (TextView) findViewById(R.id.name);
        this.isvipimg = (ImageView) findViewById(R.id.isvipimg);
        this.identity_judged_textview = (TextView) findViewById(R.id.identity_judged_textview);
        this.seximg = (ImageView) findViewById(R.id.seximg);
        this.schoolname = (TextView) findViewById(R.id.schoolname);
        this.guanzhu = (TextView) findViewById(R.id.guanzhu);
        this.beiguanzhu = (TextView) findViewById(R.id.beiguanzhu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        this.rl_comment_tiku = (RelativeLayout) findViewById(R.id.rl_comment_tiku);
        this.rl_comment_jingyan = (RelativeLayout) findViewById(R.id.rl_comment_jingyan);
        this.rl_comment_kecheng = (RelativeLayout) findViewById(R.id.rl_comment_kecheng);
        StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(toolbar.getLayoutParams());
        layoutParams.setCollapseMode(1);
        toolbar.setLayoutParams(layoutParams);
        appBarLayout.getLayoutParams().height = ScreenUtil.getPxByDp((Context) this, 80);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.xp
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f14196a.lambda$init$3(appBarLayout2, i2);
            }
        });
        if (getIntent().getExtras() != null) {
            this.user_id = getIntent().getExtras().getString("user_id");
            this.user_identity = getIntent().getExtras().getString("user_identity", "");
            this.to_user_uuid = getIntent().getExtras().getString("to_user_uuid");
            this.is_owner = getIntent().getExtras().getString("is_owner", "0");
            this.is_banned = getIntent().getExtras().getString("is_banned", "0");
        }
        Uri data = getIntent().getData();
        if (data != null && "ebook".equals(data.getScheme())) {
            String queryParameter = data.getQueryParameter("user_id");
            if (!TextUtils.isEmpty(queryParameter)) {
                this.user_id = queryParameter;
            }
        }
        if (TextUtils.isEmpty(this.user_identity) || this.user_identity.equals("0") || this.user_id.equals(UserConfig.getUserId())) {
            this.icon_remove_member.setVisibility(8);
        } else if (this.user_identity.equals("2")) {
            this.icon_remove_member.setVisibility(0);
        } else if (this.is_owner.equals("2") || this.is_owner.equals("1")) {
            this.icon_remove_member.setVisibility(8);
        } else {
            this.icon_remove_member.setVisibility(0);
        }
        getUserInfo();
        this.guanzhu.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14226c.lambda$init$4(view);
            }
        });
        this.beiguanzhu.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14259c.lambda$init$5(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        if (SkinManager.getCurrentSkinType(this) == 1) {
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_two_color_night), 0);
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_two_color), 0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        String[] strArr;
        if (v2.getId() == R.id.icon_remove_member) {
            if (this.user_identity.equals("2")) {
                strArr = new String[3];
                if (this.is_banned.equals("1")) {
                    strArr[0] = "解除禁言";
                } else {
                    strArr[0] = "禁言";
                }
                strArr[1] = "移除群成员";
                if (this.is_owner.equals("1")) {
                    strArr[2] = "解除管理员";
                } else if (this.is_owner.equals("2")) {
                    strArr[2] = "群主";
                } else {
                    strArr[2] = "设为管理员";
                }
            } else {
                strArr = new String[2];
                if (this.is_banned.equals("1")) {
                    strArr[0] = "解除禁言";
                } else {
                    strArr[0] = "禁言";
                }
                strArr[1] = "移除群成员";
            }
            showChatMemberDialog(strArr);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        String str;
        super.onResume();
        getCommnumData();
        if (UserConfig.getInstance().getUser() == null || (str = this.user_id) == null || !str.equals(UserConfig.getInstance().getUser().getUser_id())) {
            return;
        }
        this.name.setText(UserConfig.getInstance().getUser().getNickname());
        GlideApp.with((FragmentActivity) this).load(UserConfig.getInstance().getUser().getAvatar()).into(this.avaimg);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_user_center);
        this.mActionBar.hide();
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.icon_remove_member.setOnClickListener(this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            JSONObject jSONObject = new JSONObject(s2);
            if (jSONObject.optString("code").equals("200")) {
                EventBus.getDefault().post(EventBusConstant.EVENT_UPDATA_GROUP_INFO);
                finish();
            } else {
                ToastUtil.shortToast(this, jSONObject.optString("message"));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
