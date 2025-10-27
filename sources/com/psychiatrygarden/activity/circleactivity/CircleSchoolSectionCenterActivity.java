package com.psychiatrygarden.activity.circleactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.ForumIntroductionActivity;
import com.psychiatrygarden.activity.forum.ForumUserListActivity;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CourseWxPopwindow;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleSchoolSectionCenterActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout banzhuline;
    private ForumInfoBean.DataBean dataBean;
    private ImageView iv_back;
    private Switch switch_stick;
    private TextView tv_exit_section;
    private TextView tv_section_content;
    private TextView tv_section_question;
    private LinearLayout userline;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addUser$3(View view) {
        Intent intent = new Intent(this, (Class<?>) ForumUserListActivity.class);
        intent.putExtra("group_id", "" + getIntent().getExtras().getString("group_id"));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$0(View view) {
        getFollowTopData(this.dataBean.getId() + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$1(View view) {
        Intent intent = new Intent(this, (Class<?>) ForumIntroductionActivity.class);
        intent.putExtra("group_id", getIntent().getExtras().getString("group_id") + "");
        intent.putExtra("introduction", "" + this.dataBean.getIntroduction());
        intent.putExtra("edit_introduction_permission", "" + this.dataBean.getEdit_introduction_permission());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$2(View view) {
        new XPopup.Builder(this).asCustom(new CourseWxPopwindow(this, this.dataBean.getWechat().getQrcode())).toggle();
    }

    public void addModerator(List<ForumInfoBean.DataBean.ModeratorsBean> moderators) {
        if (moderators != null) {
            try {
                if (moderators.size() > 0) {
                    this.banzhuline.removeAllViews();
                    int size = moderators.size();
                    if (size > 6) {
                        size = 6;
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        CircleImageView circleImageView = (CircleImageView) LayoutInflater.from(this).inflate(R.layout.layout_forum_useravar, (ViewGroup) null);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        if (i2 == moderators.size() - 1) {
                            layoutParams.setMargins(0, 0, 0, 0);
                        } else {
                            layoutParams.setMargins(0, 0, -20, 0);
                        }
                        layoutParams.width = ScreenUtil.getPxByDp((Context) this, 25);
                        layoutParams.height = ScreenUtil.getPxByDp((Context) this, 25);
                        circleImageView.setLayoutParams(layoutParams);
                        GlideApp.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(moderators.get(i2).getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.app_icon).placeholder(R.drawable.app_icon)).into(circleImageView);
                        this.banzhuline.addView(circleImageView);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void addUser(List<ForumInfoBean.DataBean.UserBean> userBeans) {
        if (userBeans != null) {
            try {
                if (userBeans.size() > 0) {
                    this.userline.removeAllViews();
                    int size = userBeans.size();
                    if (size > 6) {
                        size = 6;
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        CircleImageView circleImageView = (CircleImageView) LayoutInflater.from(this).inflate(R.layout.layout_forum_useravar, (ViewGroup) null);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        if (i2 == userBeans.size() - 1) {
                            layoutParams.setMargins(0, 0, 0, 0);
                        } else {
                            layoutParams.setMargins(0, 0, -20, 0);
                        }
                        layoutParams.width = ScreenUtil.getPxByDp((Context) this, 25);
                        layoutParams.height = ScreenUtil.getPxByDp((Context) this, 25);
                        circleImageView.setLayoutParams(layoutParams);
                        GlideApp.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(userBeans.get(i2).getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.app_icon).placeholder(R.drawable.app_icon)).into(circleImageView);
                        this.userline.addView(circleImageView);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        this.userline.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.p2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11578c.lambda$addUser$3(view);
            }
        });
    }

    public void getFollowTopData(String pid) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", getIntent().getExtras().getString("group_id") + "");
        YJYHttpUtils.post(this, NetworkRequestsURL.getforumTopApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("200"))) {
                        EventBus.getDefault().post("followTop");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getForumInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        YJYHttpUtils.get(this, NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        CircleSchoolSectionCenterActivity.this.dataBean = forumInfoBean.getData();
                        CircleSchoolSectionCenterActivity.this.putViewDataShow();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getForumexitData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        YJYHttpUtils.post(this, NetworkRequestsURL.getforumeexitApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleSchoolSectionCenterActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CircleSchoolSectionCenterActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        EventBus.getDefault().post("exitSchool");
                        CircleSchoolSectionCenterActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CircleSchoolSectionCenterActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_exit_section = (TextView) findViewById(R.id.tv_exit_section);
        this.switch_stick = (Switch) findViewById(R.id.switch_stick);
        this.tv_section_question = (TextView) findViewById(R.id.tv_section_question);
        this.tv_section_content = (TextView) findViewById(R.id.tv_section_content);
        this.banzhuline = (LinearLayout) findViewById(R.id.banzhuline);
        this.userline = (LinearLayout) findViewById(R.id.userline);
        getForumInfoData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#171D2D"));
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_exit_section) {
            getForumexitData();
        } else {
            if (id != R.id.tv_section_question) {
                return;
            }
            startActivity(new Intent(this, (Class<?>) CircleSchoolSectionQuestionActivity.class));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public void putViewDataShow() {
        ForumInfoBean.DataBean dataBean = this.dataBean;
        if (dataBean != null) {
            this.switch_stick.setChecked("1".equals(dataBean.getIs_top()));
            this.switch_stick.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.q2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11582c.lambda$putViewDataShow$0(view);
                }
            });
            addModerator(this.dataBean.getModerators());
            addUser(this.dataBean.getUser());
            this.tv_section_content.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.r2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11587c.lambda$putViewDataShow$1(view);
                }
            });
            this.banzhuline.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.s2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11591c.lambda$putViewDataShow$2(view);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_circle_school_section_center);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_back.setOnClickListener(this);
        this.tv_section_question.setOnClickListener(this);
        this.tv_exit_section.setOnClickListener(this);
    }
}
