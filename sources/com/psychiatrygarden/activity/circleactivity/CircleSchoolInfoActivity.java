package com.psychiatrygarden.activity.circleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.ForumIntroductionActivity;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CircleSchoolInfoActivity extends BaseActivity implements View.OnClickListener {
    public LinearLayout banzhuline;
    private ForumInfoBean.DataBean dataBean;
    private String group_id;
    private ImageView iv_back;
    private CircleImageView iv_circle_school;
    private ImageView iv_circle_school_menu;
    private ImageView iv_circle_school_search;
    private TextView jointxt;
    public LinearLayout line_maderoate;
    public TextView moderatonum;
    private TextView tv_discuss_num;
    private TextView tv_join_school;
    private TextView tv_member_num;
    private TextView tv_post_num;
    private TextView tv_school_info;
    private AppCompatTextView tv_school_name;

    public void addModerator(List<ForumInfoBean.DataBean.ModeratorsBean> moderators) {
        if (moderators != null) {
            try {
                if (moderators.size() > 0) {
                    this.line_maderoate.setVisibility(0);
                    this.moderatonum.setText("版主（" + moderators.size() + "）");
                    for (int i2 = 0; i2 < moderators.size(); i2++) {
                        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_forum_moderator, (ViewGroup) null);
                        CircleImageView circleImageView = (CircleImageView) viewInflate.findViewById(R.id.moderatorimg);
                        TextView textView = (TextView) viewInflate.findViewById(R.id.name);
                        GlideApp.with(circleImageView.getContext()).load((Object) GlideUtils.generateUrl(moderators.get(i2).getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.app_icon).placeholder(R.drawable.app_icon)).into(circleImageView);
                        textView.setText(moderators.get(i2).getNickname());
                        this.banzhuline.addView(viewInflate);
                    }
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        this.line_maderoate.setVisibility(8);
    }

    public void getForumInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + this.group_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        CircleSchoolInfoActivity.this.dataBean = forumInfoBean.getData();
                        CircleSchoolInfoActivity.this.putViewDataShow();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.group_id = getIntent().getExtras().getString("group_id");
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        ImageView imageView = (ImageView) findViewById(R.id.iv_circle_school_search);
        this.iv_circle_school_search = imageView;
        imageView.setVisibility(8);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv_circle_school_menu);
        this.iv_circle_school_menu = imageView2;
        imageView2.setVisibility(8);
        this.tv_join_school = (TextView) findViewById(R.id.tv_join_school);
        this.tv_school_name = (AppCompatTextView) findViewById(R.id.tv_school_name);
        this.tv_school_info = (TextView) findViewById(R.id.tv_school_info);
        this.iv_circle_school = (CircleImageView) findViewById(R.id.iv_circle_school);
        this.tv_post_num = (TextView) findViewById(R.id.tv_post_num);
        this.tv_discuss_num = (TextView) findViewById(R.id.tv_discuss_num);
        this.tv_member_num = (TextView) findViewById(R.id.tv_member_num);
        this.banzhuline = (LinearLayout) findViewById(R.id.banzhuline);
        this.jointxt = (TextView) findViewById(R.id.jointxt);
        this.moderatonum = (TextView) findViewById(R.id.moderatonum);
        this.line_maderoate = (LinearLayout) findViewById(R.id.line_maderoate);
        if (getIntent().getExtras().getSerializable("dataBean") == null) {
            getForumInfoData();
        } else {
            this.dataBean = (ForumInfoBean.DataBean) getIntent().getExtras().getSerializable("dataBean");
            putViewDataShow();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_back) {
            finish();
            return;
        }
        if (id == R.id.iv_circle_school_menu) {
            startActivity(new Intent(this, (Class<?>) CircleSchoolSectionCenterActivity.class));
            return;
        }
        if (id == R.id.tv_join_school && this.dataBean != null) {
            Intent intent = new Intent(this, (Class<?>) CircleSchoolVerifyActivity.class);
            intent.putExtra("group_id", "" + this.group_id);
            startActivity(intent);
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("版块申请");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public void putViewDataShow() {
        ForumInfoBean.DataBean dataBean = this.dataBean;
        if (dataBean != null) {
            this.tv_school_name.setText(dataBean.getName());
            GlideApp.with(this.iv_circle_school.getContext()).load((Object) GlideUtils.generateUrl(this.dataBean.getLogo())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.schooldefaultimg)).placeholder(R.drawable.schooldefaultimg).into(this.iv_circle_school);
            this.tv_post_num.setText(this.dataBean.getArticle_count());
            this.tv_discuss_num.setText(this.dataBean.getComment_count());
            this.tv_member_num.setText(this.dataBean.getFollow_count());
            if ("".equals(this.dataBean.getIntroduction())) {
                this.tv_school_info.setText("版块简介：无");
            } else {
                this.tv_school_info.setText("版块简介：" + this.dataBean.getIntroduction());
            }
            this.jointxt.setText(this.dataBean.getAdd_note());
            addModerator(this.dataBean.getModerators());
            this.tv_join_school.setText("申请加入");
            this.tv_school_info.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    Intent intent = new Intent(CircleSchoolInfoActivity.this, (Class<?>) ForumIntroductionActivity.class);
                    intent.putExtra("group_id", CircleSchoolInfoActivity.this.getIntent().getExtras().getString("group_id") + "");
                    intent.putExtra("introduction", "" + CircleSchoolInfoActivity.this.dataBean.getIntroduction());
                    intent.putExtra("edit_introduction_permission", "" + CircleSchoolInfoActivity.this.dataBean.getEdit_introduction_permission());
                    CircleSchoolInfoActivity.this.startActivity(intent);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_school_info);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_back.setOnClickListener(this);
        this.iv_circle_school_search.setOnClickListener(this);
        this.iv_circle_school_menu.setOnClickListener(this);
        this.tv_join_school.setOnClickListener(this);
    }
}
