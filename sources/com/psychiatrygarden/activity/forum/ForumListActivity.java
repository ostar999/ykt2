package com.psychiatrygarden.activity.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CircleSearchNewActivity;
import com.psychiatrygarden.activity.circleactivity.CirclePushActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity;
import com.psychiatrygarden.activity.forum.bean.ForumChannelBean;
import com.psychiatrygarden.activity.forum.bean.ForumIndexBean;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.activity.forum.fragment.ForumListFragment;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.StackLayout;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumListActivity extends BaseActivity {
    private LinearLayout bartoolview;
    private ForumInfoBean.DataBean dataBean;
    public String group_id;
    private CircleImageView iv_circle_school;
    private MagicIndicator mMagicIndicator;
    private StackLayout sl_school_flag_join;
    private TextView tv_school_info;
    private AppCompatTextView tv_school_name;
    private ViewPager viewpager;
    private List<ForumChannelBean.DataBean> dataChannel = new ArrayList();
    private ForumIndexBean.DataBean.ListBean mForumBean = new ForumIndexBean.DataBean.ListBean();

    /* renamed from: com.psychiatrygarden.activity.forum.ForumListActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            ForumListActivity.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return ForumListActivity.this.dataChannel.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 4.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 13.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(ForumListActivity.this, R.color.app_theme_red)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = CommonUtil.px2dip(ForumListActivity.this.mContext, 10.0f);
            layoutParams.rightMargin = CommonUtil.dip2px(ForumListActivity.this.mContext, 20.0f);
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setText(((ForumChannelBean.DataBean) ForumListActivity.this.dataChannel.get(index)).getTitle());
            colorTransitionPagerTitleView.setLayoutParams(layoutParams);
            colorTransitionPagerTitleView.setTextSize(2, 14.0f);
            if (SkinManager.getCurrentSkinType(ForumListActivity.this.mContext) == 1) {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#39456D"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#6472A1"));
            } else {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#464646"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FF1C1A15"));
            }
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12404c.lambda$getTitleView$0(index, view);
                }
            });
            return colorTransitionPagerTitleView;
        }
    }

    private void addStack(List<ForumInfoBean.DataBean.LabelBean> tags) {
        if (tags != null) {
            try {
                if (tags.size() <= 0) {
                    return;
                }
                this.sl_school_flag_join.removeAllViews();
                for (int i2 = 0; i2 < tags.size(); i2++) {
                    View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_forum_tag_item2, (ViewGroup) null);
                    ((TextView) viewInflate.findViewById(R.id.tubName)).setText(tags.get(i2).getLabel());
                    ImageView imageView = (ImageView) viewInflate.findViewById(R.id.tubimg);
                    if ("".equals(tags.get(i2).getIcon())) {
                        imageView.setImageResource(R.drawable.app_icon);
                    } else {
                        GlideApp.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(tags.get(i2).getIcon())).into(imageView);
                    }
                    viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.i
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ForumListActivity.lambda$addStack$5(view);
                        }
                    });
                    this.sl_school_flag_join.addView(viewInflate);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn() throws Resources.NotFoundException {
        List<ForumChannelBean.DataBean> list = this.dataChannel;
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataChannel.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("group_id", this.group_id + "");
            bundle.putString("id", this.dataChannel.get(i2).getId());
            bundle.putString("text", this.dataChannel.get(i2).getTitle());
            bundle.putInt("module_type", 16);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.dataChannel.get(i2).getTitle(), ForumListFragment.class, bundle));
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList));
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass3());
        this.mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addStack$5(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        Intent intent = new Intent(this, (Class<?>) CircleSchoolSectionCenterActivity.class);
        intent.putExtra("group_id", "" + this.group_id);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (isLogin()) {
            Intent intent = new Intent(this.mContext, (Class<?>) CirclePushActivity.class);
            intent.putExtra("group_id", "" + this.group_id);
            intent.putExtra("htmlContent", "");
            intent.putExtra("htmlTitle", "");
            intent.putExtra("module_type", 16);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        Intent intent = new Intent(this, (Class<?>) CircleSearchNewActivity.class);
        intent.putExtra("editTextData", "");
        intent.putExtra("flagEdit", false);
        intent.putExtra("group_id", "" + this.group_id);
        intent.putExtra("module_type", 16);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(AppBarLayout appBarLayout, int i2) {
        this.bartoolview.setAlpha(1.0f - Math.abs((i2 * 1.0f) / appBarLayout.getTotalScrollRange()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$4(View view) {
        Intent intent = new Intent(this, (Class<?>) ForumIntroductionActivity.class);
        intent.putExtra("group_id", "" + this.group_id);
        intent.putExtra("introduction", "" + this.dataBean.getIntroduction());
        intent.putExtra("edit_introduction_permission", "" + this.dataBean.getEdit_introduction_permission());
        startActivity(intent);
    }

    public void getFollowData(String pid) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", pid + "");
        YJYHttpUtils.post(this, NetworkRequestsURL.getforumfollowApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumListActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post(ForumListActivity.this.mForumBean);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getForumInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + this.group_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumListActivity.1
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
                        ForumListActivity.this.dataBean = forumInfoBean.getData();
                        ForumListActivity.this.mForumBean = new ForumIndexBean.DataBean.ListBean();
                        ForumListActivity.this.mForumBean.setName(ForumListActivity.this.dataBean.getName());
                        ForumListActivity.this.mForumBean.setId(ForumListActivity.this.dataBean.getId());
                        ForumListActivity.this.mForumBean.setIs_follow(ForumListActivity.this.dataBean.getIs_follow());
                        ForumListActivity.this.mForumBean.setLogo(ForumListActivity.this.dataBean.getLogo());
                        ForumListActivity.this.mForumBean.setAccess("1");
                        ForumListActivity.this.mForumBean.setArticle_count(ForumListActivity.this.dataBean.getArticle_count() + "帖子");
                        ForumListActivity.this.mForumBean.setUser_count(ForumListActivity.this.dataBean.getUser_count() + "关注");
                        ForumListActivity.this.mForumBean.setComment_count(ForumListActivity.this.dataBean.getComment_count() + "讨论");
                        ForumListActivity.this.mForumBean.setNew_message("0");
                        ForumListActivity.this.putViewDataShow();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getForumLabelListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + this.group_id);
        ajaxParams.put("type", "list");
        YJYHttpUtils.get(this, NetworkRequestsURL.getforumlabellistApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ForumChannelBean forumChannelBean = (ForumChannelBean) new Gson().fromJson(s2, ForumChannelBean.class);
                    if (forumChannelBean.getCode().equals("200")) {
                        ForumListActivity.this.dataChannel = forumChannelBean.getData();
                        ForumListActivity.this.initTabColumn();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getrecordingData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", this.group_id + "");
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getforumrecordingTimeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumListActivity.5
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
    @SuppressLint({"WrongViewCast"})
    public void init() {
        this.group_id = getIntent().getExtras().getString("group_id");
        ImageView imageView = (ImageView) findViewById(R.id.iv_circle_school_search);
        this.tv_school_name = (AppCompatTextView) findViewById(R.id.tv_school_name);
        this.tv_school_info = (TextView) findViewById(R.id.tv_school_info);
        this.iv_circle_school = (CircleImageView) findViewById(R.id.iv_circle_school);
        this.sl_school_flag_join = (StackLayout) findViewById(R.id.sl_school_flag_join);
        this.mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.viewpager = (ViewPager) findViewById(R.id.listf);
        this.bartoolview = (LinearLayout) findViewById(R.id.bartoolview);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatButton);
        ((ImageView) findViewById(R.id.iv_circle_school_menu)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12369c.lambda$init$0(view);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12370c.lambda$init$1(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12401c.lambda$init$2(view);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.forum.h
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f12402a.lambda$init$3(appBarLayout2, i2);
            }
        });
        getForumInfoData();
        getForumLabelListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_theme_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#171D2D"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        getrecordingData();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("exitSchool")) {
            finish();
        }
    }

    public void putViewDataShow() {
        String str;
        ForumInfoBean.DataBean dataBean = this.dataBean;
        if (dataBean != null) {
            this.tv_school_name.setText(dataBean.getName());
            if ("".equals(this.dataBean.getIntroduction())) {
                str = "版块简介: 无";
            } else {
                str = "版块简介: " + this.dataBean.getIntroduction();
            }
            this.tv_school_info.setText(str);
            this.tv_school_info.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12403c.lambda$putViewDataShow$4(view);
                }
            });
            GlideApp.with(this.iv_circle_school.getContext()).load((Object) GlideUtils.generateUrl(this.dataBean.getLogo())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.schooldefaultimg)).placeholder(R.drawable.schooldefaultimg).into(this.iv_circle_school);
            if (this.dataBean.getLabels() == null || this.dataBean.getLabels().size() <= 0) {
                this.sl_school_flag_join.setVisibility(8);
            } else {
                this.sl_school_flag_join.setVisibility(0);
                addStack(this.dataBean.getLabels());
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_forum_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
