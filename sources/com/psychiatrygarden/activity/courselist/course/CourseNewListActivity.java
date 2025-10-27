package com.psychiatrygarden.activity.courselist.course;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ClipPagerTitleView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.svideo.common.utils.ScreenUtils;
import com.huawei.hms.push.HmsMessageService;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseIntroductionFragment;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumEvaluationFragment;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.CourseFreeBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CourseWxPopwindow;
import com.psychiatrygarden.widget.GlideForegroupImageView;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.transformation.BlurTransformation;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseNewListActivity extends BaseActivity {
    private int actionbar;
    private String activity_id;
    private String category_id;
    private Disposable disposable;
    private GlideForegroupImageView imgthume;
    private AliyunVodPlayerView mAliyunVodPlayerView;
    public MagicIndicator magic_indicator;
    public RelativeLayout relalivideo;
    private RelativeLayout rl_pay_view;
    private Timer timer;
    private TimerTask timerTask;
    public ViewPager viewpager;
    private String watch_permission;
    private final String[] mAId_strs = {"目录", "简介", "评价"};
    private int sWeight = 0;

    /* renamed from: com.psychiatrygarden.activity.courselist.course.CourseNewListActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CourseNewListActivity.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (CourseNewListActivity.this.mAId_strs == null) {
                return 0;
            }
            return CourseNewListActivity.this.mAId_strs.length;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 20.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(CourseNewListActivity.this.mContext.getResources().getColor(R.color.app_theme_red)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
            if (index == 0 || index == 2) {
                clipPagerTitleView.setmItemWidth(CourseNewListActivity.this.sWeight);
            } else {
                clipPagerTitleView.setmItemWidth(CourseNewListActivity.this.sWeight * 2);
            }
            clipPagerTitleView.setText(CourseNewListActivity.this.mAId_strs[index]);
            clipPagerTitleView.setTextColor(Color.parseColor("#555555"));
            clipPagerTitleView.setClipColor(Color.parseColor("#B2575C"));
            clipPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.w0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f11895c.lambda$getTitleView$0(index, view);
                }
            });
            return clipPagerTitleView;
        }
    }

    private void getInfo() {
        YJYHttpUtils.get(this, NetworkRequestsURL.getGroupCodeApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseNewListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseNewListActivity.this.AlertToast("请求失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        new XPopup.Builder(CourseNewListActivity.this).asCustom(new CourseWxPopwindow(CourseNewListActivity.this, new JSONObject(s2).optJSONObject("data").optString("img"))).toggle();
                    } else {
                        CourseNewListActivity.this.AlertToast("请求失败！");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private boolean isStrangePhone() {
        String str = Build.DEVICE;
        return str.equalsIgnoreCase("mx5") || str.equalsIgnoreCase("Redmi Note2") || str.equalsIgnoreCase("Z00A_1") || str.equalsIgnoreCase("hwH60-L02") || str.equalsIgnoreCase("hermes") || (str.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShareData$8() {
        this.rl_pay_view.setVisibility(8);
        this.watch_permission = "1";
        EventBus.getDefault().post("permission");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (!"1".equals(this.watch_permission)) {
            getShareData();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) CourseCalalogueDownLoadActivity.class);
        intent.putExtra("category_id", "" + this.category_id);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        getInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$6(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    private void updatePlayerViewMode() {
        if (this.mAliyunVodPlayerView != null) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 == 1) {
                getWindow().clearFlags(1024);
                this.mAliyunVodPlayerView.setSystemUiVisibility(0);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mAliyunVodPlayerView.getLayoutParams();
                layoutParams.height = (int) ((ScreenUtils.getWidth(this) * 9.0f) / 16.0f);
                layoutParams.width = -1;
                this.rl_pay_view.setLayoutParams(layoutParams);
                this.imgthume.setLayoutParams(layoutParams);
                FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(this.relalivideo.getLayoutParams());
                layoutParams2.setMargins(0, this.actionbar, 0, 0);
                this.relalivideo.setLayoutParams(layoutParams2);
                return;
            }
            if (i2 == 2) {
                if (!isStrangePhone()) {
                    getWindow().setFlags(1024, 1024);
                    this.mAliyunVodPlayerView.setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
                }
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mAliyunVodPlayerView.getLayoutParams();
                layoutParams3.height = -1;
                layoutParams3.width = -1;
                this.rl_pay_view.setLayoutParams(layoutParams3);
                FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(this.relalivideo.getLayoutParams());
                layoutParams4.setMargins(0, 0, 0, 0);
                this.relalivideo.setLayoutParams(layoutParams4);
            }
        }
    }

    public void getShareData() {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(HmsMessageService.SUBJECT_ID, "" + this.category_id);
        ajaxParams.put("am_pm", "" + SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this));
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        MemInterface.getInstance().setShowOrHideView(new MemInterface.ShowOrHideView() { // from class: com.psychiatrygarden.activity.courselist.course.CourseNewListActivity.3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShowOrHideView
            public void oncomplete() {
                CourseNewListActivity.this.hideProgressDialog();
            }
        });
        MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.courselist.course.n0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11877a.lambda$getShareData$8();
            }
        });
    }

    public void getmagicData() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass2());
        this.magic_indicator.setNavigator(commonNavigator);
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", "" + getIntent().getExtras().getString("category_id"));
        bundle.putString("discription_img", "" + getIntent().getExtras().getString("discription_img"));
        bundle.putInt("module_type", 24);
        int i2 = 0;
        while (true) {
            String[] strArr = this.mAId_strs;
            if (i2 >= strArr.length) {
                this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList));
                this.viewpager.setOffscreenPageLimit(5);
                this.viewpager.setCurrentItem(0);
                ViewPagerHelper.bind(this.magic_indicator, this.viewpager);
                return;
            }
            if (i2 == 0) {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[0], CourseCatalogueFragment.class, bundle));
            } else if (i2 == 1) {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[1], CourseIntroductionFragment.class, bundle));
            } else if (i2 == 2) {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[2], CurriculumEvaluationFragment.class, bundle));
            }
            i2++;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.category_id = getIntent().getExtras().getString("category_id");
        ImageView imageView = (ImageView) findViewById(R.id.jinqunw);
        ImageView imageView2 = (ImageView) findViewById(R.id.xiazaiw);
        TextView textView = (TextView) findViewById(R.id.kemutx);
        ((GlideImageView) findViewById(R.id.icon_left_back)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11879c.lambda$init$0(view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11881c.lambda$init$1(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11883c.lambda$init$2(view);
            }
        });
        TextView textView2 = (TextView) findViewById(R.id.letter);
        GlideForegroupImageView glideForegroupImageView = (GlideForegroupImageView) findViewById(R.id.imgthume);
        this.imgthume = glideForegroupImageView;
        glideForegroupImageView.fitCenter().load(getIntent().getStringExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER), R.drawable.imgplacehodel, new BlurTransformation(this, 25, 10));
        textView2.setText(getIntent().getExtras().getString("lecturer") + "");
        textView.setText(getIntent().getExtras().getString("title") + "");
        this.actionbar = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relTitle);
        this.relalivideo = (RelativeLayout) findViewById(R.id.relalivideo);
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpage);
        this.mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        ((LinearLayout) findViewById(R.id.llay_buy_course)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11885c.lambda$init$3(view);
            }
        });
        this.rl_pay_view = (RelativeLayout) findViewById(R.id.rl_pay_view);
        TextView textView3 = (TextView) findViewById(R.id.buy_text);
        TextView textView4 = (TextView) findViewById(R.id.back_tv);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11887c.lambda$init$4(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11890c.lambda$init$5(view);
            }
        });
        this.rl_pay_view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.u0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseNewListActivity.lambda$init$6(view);
            }
        });
        ((TextView) findViewById(R.id.tv_renew)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11893c.lambda$init$7(view);
            }
        });
        this.mAliyunVodPlayerView.setKeepScreenOn(true);
        this.mAliyunVodPlayerView.setTheme(Theme.Red);
        this.mAliyunVodPlayerView.setCirclePlay(false);
        this.sWeight = ScreenUtil.getScreenWidth(this) / 4;
        getmagicData();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(relativeLayout.getLayoutParams());
        layoutParams.setMargins(0, this.actionbar, 0, 0);
        relativeLayout.setLayoutParams(layoutParams);
    }

    public void mOnDestroy() {
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
        TimerTask timerTask = this.timerTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.timerTask = null;
        }
        if (this.mAliyunVodPlayerView != null) {
            mRefuse(3);
            this.mAliyunVodPlayerView.onDestroy();
            this.mAliyunVodPlayerView = null;
        }
        stopTimer();
    }

    public void mRefuse(int type) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Fragment fragment = getSupportFragmentManager().getFragments().get(3);
            if (fragment == null || !(fragment instanceof CurriculumEvaluationFragment)) {
                return;
            }
            ((CurriculumEvaluationFragment) fragment).onFragmentResult(requestCode, resultCode, data.getBundleExtra("bundleIntent"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        StatusBarUtil.setStatusBarTranslucent(this, false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        mOnDestroy();
        super.onDestroy();
    }

    public void onEventMainThread(CourseFreeBean courseFreeBean) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        "endVideo".equals(str);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null || aliyunVodPlayerView.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayerViewMode();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updatePlayerViewMode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_new_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void stopTimer() {
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
            this.disposable = null;
        }
    }

    public void onEventMainThread(CourseCalalogueBean.DataNewBean.PerMissionBean perMissionBean) {
        this.watch_permission = perMissionBean.getPermission() + "";
        this.activity_id = perMissionBean.getActivity_id();
    }
}
