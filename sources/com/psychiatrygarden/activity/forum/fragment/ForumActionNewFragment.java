package com.psychiatrygarden.activity.forum.fragment;

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
import com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity;
import com.psychiatrygarden.activity.forum.ForumIntroductionActivity;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ForumActionNewFragment extends BaseFragment implements View.OnClickListener {
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$0(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) ForumIntroductionActivity.class);
        intent.putExtra("group_id", this.group_id + "");
        intent.putExtra("introduction", "" + this.dataBean.getIntroduction());
        intent.putExtra("edit_introduction_permission", "" + this.dataBean.getEdit_introduction_permission());
        startActivity(intent);
    }

    public static ForumActionNewFragment newInstance(String group_id) {
        Bundle bundle = new Bundle();
        bundle.putString("group_id", "" + group_id);
        ForumActionNewFragment forumActionNewFragment = new ForumActionNewFragment();
        forumActionNewFragment.setArguments(bundle);
        return forumActionNewFragment;
    }

    public void addModerator(List<ForumInfoBean.DataBean.ModeratorsBean> moderators) {
        if (moderators != null) {
            try {
                if (moderators.size() > 0) {
                    this.line_maderoate.setVisibility(0);
                    this.moderatonum.setText(String.format(Locale.CHINA, "版主（%d）", Integer.valueOf(moderators.size())));
                    for (int i2 = 0; i2 < moderators.size(); i2++) {
                        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_forum_moderator, (ViewGroup) null);
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
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumActionNewFragment.1
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
                        ForumActionNewFragment.this.dataBean = forumInfoBean.getData();
                        ForumActionNewFragment.this.putViewDataShow();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_circle_school_info;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if (getArguments() != null) {
            this.group_id = getArguments().getString("group_id");
        }
        this.iv_back = (ImageView) holder.get(R.id.iv_back);
        ImageView imageView = (ImageView) holder.get(R.id.iv_circle_school_search);
        this.iv_circle_school_search = imageView;
        imageView.setVisibility(8);
        ImageView imageView2 = (ImageView) holder.get(R.id.iv_circle_school_menu);
        this.iv_circle_school_menu = imageView2;
        imageView2.setVisibility(8);
        this.tv_join_school = (TextView) holder.get(R.id.tv_join_school);
        this.tv_school_name = (AppCompatTextView) holder.get(R.id.tv_school_name);
        this.tv_school_info = (TextView) holder.get(R.id.tv_school_info);
        this.iv_circle_school = (CircleImageView) holder.get(R.id.iv_circle_school);
        this.tv_post_num = (TextView) holder.get(R.id.tv_post_num);
        this.tv_discuss_num = (TextView) holder.get(R.id.tv_discuss_num);
        this.tv_member_num = (TextView) holder.get(R.id.tv_member_num);
        this.banzhuline = (LinearLayout) holder.get(R.id.banzhuline);
        this.jointxt = (TextView) holder.get(R.id.jointxt);
        this.moderatonum = (TextView) holder.get(R.id.moderatonum);
        this.line_maderoate = (LinearLayout) holder.get(R.id.line_maderoate);
        setListenerForWidget();
        getForumInfoData();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_back) {
            if (getActivity() != null) {
                getActivity().finish();
            }
        } else {
            if (id == R.id.iv_circle_school_menu) {
                startActivity(new Intent(getActivity(), (Class<?>) CircleSchoolSectionCenterActivity.class));
                return;
            }
            if (id != R.id.tv_join_school || this.dataBean == null) {
                return;
            }
            Intent intent = new Intent(getActivity(), (Class<?>) CircleSchoolVerifyActivity.class);
            intent.putExtra("group_id", "" + this.group_id);
            startActivity(intent);
            this._mActivity.finish();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
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
                this.tv_school_info.setText(String.format(Locale.CHINA, "版块简介：%s", this.dataBean.getIntroduction()));
            }
            this.jointxt.setText(this.dataBean.getAdd_note());
            addModerator(this.dataBean.getModerators());
            this.tv_join_school.setText("申请加入");
            this.tv_school_info.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12371c.lambda$putViewDataShow$0(view);
                }
            });
        }
    }

    public void setListenerForWidget() {
        this.iv_back.setOnClickListener(this);
        this.iv_circle_school_search.setOnClickListener(this);
        this.iv_circle_school_menu.setOnClickListener(this);
        this.tv_join_school.setOnClickListener(this);
    }
}
