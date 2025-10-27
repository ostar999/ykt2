package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.fragment.FollowMajorsFragment;
import com.psychiatrygarden.activity.chooseSchool.fragment.FollowSchoolFragment;
import com.psychiatrygarden.activity.rank.bean.ActivityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.ViewPagerFragmentAdapter;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import com.ykb.common_share_lib.CommonConfig;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public class SchoolMajorRankingActivity extends BaseActivity implements View.OnClickListener {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private FollowMajorsFragment followMajorsFragment;
    private FollowSchoolFragment followSchoolFragment;
    private ImageView ivActionbarShare;
    private ImageView ivBack;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private RelativeLayout rellogview;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private TextView txtTitle;
    private int defIndex = 0;
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolMajorRankingActivity.3
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            SchoolMajorRankingActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            SchoolMajorRankingActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private void initView() throws Resources.NotFoundException {
        this.defIndex = getIntent().getIntExtra("index", 0);
        this.mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.mViewPager = (ViewPager2) findViewById(R.id.viewpager);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.ivBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.txtTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.ivActionbarShare = (ImageView) findViewById(R.id.iv_actionbar_share);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.ivBack.setOnClickListener(this);
        this.ivActionbarShare.setOnClickListener(this);
        this.txtTitle.setText("医考帮排行");
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        this.followSchoolFragment = FollowSchoolFragment.newInstance("ranking");
        this.followMajorsFragment = FollowMajorsFragment.newInstance("ranking");
        viewPagerFragmentAdapter.addFragment(this.followSchoolFragment);
        viewPagerFragmentAdapter.addFragment(this.followMajorsFragment);
        this.mViewPager.setAdapter(viewPagerFragmentAdapter);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        ((FrameLayout.LayoutParams) layoutParams).height = UIUtil.dip2px(this.mContext, 44.0d) + statusBarHeight;
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        Toolbar.LayoutParams layoutParams2 = new Toolbar.LayoutParams(this.toolbars.getLayoutParams());
        layoutParams2.setMargins(0, statusBarHeight, 0, 0);
        this.toolbars.setLayoutParams(layoutParams2);
        final AppBarLayout.LayoutParams layoutParams3 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolMajorRankingActivity.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = SchoolMajorRankingActivity.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams3).height = measuredHeight;
                SchoolMajorRankingActivity.this.collapse.setLayoutParams(layoutParams3);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        UIUtil.dip2px(this.mContext, 44.0d);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u5
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f11427a.lambda$initView$0(appBarLayout, i2);
            }
        });
        setTabView(this.mTabLayout);
        this.mViewPager.setCurrentItem(this.defIndex);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.rellogview.setVisibility(8);
            this.toobars1.setBackgroundColor(SkinManager.getThemeColor(this, R.attr.app_bg));
        } else {
            this.rellogview.setVisibility(0);
            this.toobars1.setBackgroundColor(getColor(R.color.transparent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(String str, ActivityBean.DataBean.ShareInfoBean shareInfoBean, int i2) {
        if (i2 == 6) {
            CommonUtil.copyContent(this, str);
        } else if (i2 == 0) {
            shareToFriendMini(i2, shareInfoBean);
        } else {
            shareAppControl(i2, shareInfoBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setTabView$1(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            tab.setText("热门院校");
        } else {
            tab.setText("热门专业");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setTabView$2(View view) {
        return true;
    }

    public static Intent newIntent(Context context, int index) {
        Intent intent = new Intent(context, (Class<?>) SchoolMajorRankingActivity.class);
        intent.putExtra("index", index);
        return intent;
    }

    private void setTabView(TabLayout mTabLayout) throws Resources.NotFoundException {
        TabLayout.TabView tabView;
        new TabLayoutMediator(mTabLayout, this.mViewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.psychiatrygarden.activity.chooseSchool.r5
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
                SchoolMajorRankingActivity.lambda$setTabView$1(tab, i2);
            }
        }).attach();
        int i2 = 0;
        while (i2 < mTabLayout.getTabCount()) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i2);
            if (tabAt != null && (tabView = tabAt.view) != null) {
                tabView.setLongClickable(false);
                tabAt.view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s5
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return SchoolMajorRankingActivity.lambda$setTabView$2(view);
                    }
                });
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.myfollow_school_tab_item, (ViewGroup) null).findViewById(R.id.tv_tab);
                textView.setText(tabAt.getText());
                textView.setTextSize(i2 == 0 ? 16.0f : 14.0f);
                if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                    textView.setTextColor(getResources().getColor(i2 == 0 ? R.color.first_text_color_night : R.color.third_txt_color_night));
                } else {
                    textView.setTextColor(getResources().getColor(i2 == 0 ? R.color.first_text_color : R.color.third_txt_color));
                }
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                tabAt.setCustomView(textView);
            }
            i2++;
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolMajorRankingActivity.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(16.0f);
                    textView2.setTextColor(SchoolMajorRankingActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(SchoolMajorRankingActivity.this.mContext) == 1 ? R.color.first_text_color_night : R.color.first_text_color));
                    textView2.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(14.0f);
                    textView2.setTextColor(SchoolMajorRankingActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(SchoolMajorRankingActivity.this.mContext) == 1 ? R.color.third_txt_color_night : R.color.third_txt_color));
                    textView2.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    private void shareToFriendMini(int i2, ActivityBean.DataBean.ShareInfoBean shareBean) {
        UMImage uMImage = this.mViewPager.getCurrentItem() == 0 ? this.followSchoolFragment.getFirstLogo().isEmpty() ? new UMImage(this.mContext, R.drawable.default_school_logo) : new UMImage(this.mContext, this.followSchoolFragment.getFirstLogo()) : new UMImage(this.mContext, R.drawable.default_major_logo);
        UMMin uMMin = new UMMin(shareBean.getShare_url());
        uMMin.setThumb(uMImage);
        uMMin.setTitle(shareBean.getShare_title());
        uMMin.setDescription(shareBean.getShare_content());
        uMMin.setPath("pages/index/startPage");
        uMMin.setUserName("gh_14d59acf6877");
        if (CommonConfig.INSTANCE.getYI_KAO_BANG_NETWORK() == 0) {
            Config.setMiniPreView();
        }
        new ShareAction(this).withMedia(uMMin).setPlatform(BaseActivity.platforms.get(i2).mPlatform).share();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        initView();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_actionbar_back) {
            finish();
            return;
        }
        if (id != R.id.iv_actionbar_share) {
            return;
        }
        try {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
            String userId = UserConfig.getUserId();
            String str = AndroidBaseUtils.getAPPVersionCode(this) + "";
            String str2 = this.mViewPager.getCurrentItem() + "";
            final String str3 = NetworkRequestsURL.rankShareUrl + "&app_id=" + strConfig + "&user_id=" + userId + "&secret=" + UserConfig.getInstance().getUser().getSecret() + "&token=" + UserConfig.getInstance().getUser().getToken() + "&type=" + str2 + "&uuid=" + AndroidBaseUtils.getIMEI(this) + "&version=" + str;
            final ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
            shareInfoBean.setShare_url(str3);
            shareInfoBean.setShare_img("");
            String str4 = "院校排行";
            shareInfoBean.setShare_content(str2.equals("0") ? "院校排行" : "专业排行");
            if (!str2.equals("0")) {
                str4 = "专业排行";
            }
            shareInfoBean.setShare_title(str4);
            new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t5
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f11416a.lambda$onClick$3(str3, shareInfoBean, i2);
                }
            }, 1).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.school_major_rank_activity);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void shareAppControl(int i2, ActivityBean.DataBean.ShareInfoBean shareInfoBean) {
        String strTrim = shareInfoBean.getShare_url().trim();
        String share_title = shareInfoBean.getShare_title();
        String share_content = shareInfoBean.getShare_content();
        UMImage uMImage = this.mViewPager.getCurrentItem() == 0 ? this.followSchoolFragment.getFirstLogo().isEmpty() ? new UMImage(this.mContext, R.drawable.default_school_logo) : new UMImage(this.mContext, this.followSchoolFragment.getFirstLogo()) : new UMImage(this.mContext, R.drawable.default_major_logo);
        if (i2 == 3) {
            UMWeb uMWeb = new UMWeb(strTrim);
            uMWeb.setTitle(share_title);
            uMWeb.setDescription(strTrim);
            uMWeb.setThumb(uMImage);
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
            return;
        }
        UMWeb uMWeb2 = new UMWeb(strTrim);
        uMWeb2.setTitle(share_title);
        uMWeb2.setDescription(share_content);
        uMWeb2.setThumb(uMImage);
        new ShareAction(this).withMedia(uMWeb2).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
