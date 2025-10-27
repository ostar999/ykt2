package com.psychiatrygarden.activity.mine;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.C;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.adapter.BannerAdapter;
import com.psychiatrygarden.activity.mine.adapter.SignDayAdapter;
import com.psychiatrygarden.activity.mine.bean.SignAdBean;
import com.psychiatrygarden.activity.mine.bean.SignInBean;
import com.psychiatrygarden.activity.mine.bean.ToSignBean;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.ranking.SingleRankFragment;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.SignRulePop;
import com.psychiatrygarden.widget.ViewPagerFragmentAdapter;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.yikaobang.yixue.R;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.ebook.util.BitmapUtilsKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private EditText helpEt;
    private ImageView ivTopQuestion;
    private ImageView ivTopShare;
    private LinearLayout llcontent;
    private BannerAdapter mBannerAdapter;
    private ViewPager2 mBannerViewPager;
    private ImageView mIvBack;
    private LinearLayout mLlIndicator;
    private RecyclerView mRvSignDays;
    private SignDayAdapter mSignDayAdapter;
    private SignInBean mSignInBean;
    private TabLayout mTabLayout;
    private Timer mTimer;
    private TextView mTvDateStr;
    private TextView mTvDaysStr;
    private TextView mTvHelpFriend;
    private TextView mTvInviteFriend;
    private TextView mTvMemberDetail;
    private ViewPager2 mViewPager;
    private FrameLayout signBannerFl;
    private TextView signTopTitleTv;
    private TabLayout tabLayoutTop;
    private Toolbar titleBar;
    private ToSignBean toSignBean;
    private int mCurrentBannerPosition = 0;
    private List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> adsDTOList = new ArrayList();
    private List<ImageView> mIndicators = new ArrayList();
    boolean canscroll = true;

    /* renamed from: com.psychiatrygarden.activity.mine.SignInActivity$6, reason: invalid class name */
    public class AnonymousClass6 extends TimerTask {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            if (SignInActivity.this.adsDTOList.size() > 1) {
                SignInActivity signInActivity = SignInActivity.this;
                signInActivity.mCurrentBannerPosition = (signInActivity.mCurrentBannerPosition + 1) % SignInActivity.this.adsDTOList.size();
                SignInActivity.this.mBannerViewPager.setCurrentItem(SignInActivity.this.mCurrentBannerPosition, true);
            }
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            SignInActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.mine.v
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13018c.lambda$run$0();
                }
            });
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = this.space;
            if ((parent.getChildLayoutPosition(view) + 1) % 7 == 0) {
                outRect.right = 0;
            } else {
                outRect.right = this.space;
            }
        }
    }

    private void doRepairSign(String date) {
        if (TextUtils.isEmpty(date)) {
            NewToast.showShort(this, "补签日期不能为空", 0).show();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(CrashHianalyticsData.TIME, date);
        YJYHttpUtils.post(this, NetworkRequestsURL.generateHelpCode, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SignInActivity.this.hideProgressDialog();
                NewToast.showShort(SignInActivity.this, "网络连接失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SignInActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                SignInActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("code");
                    String strOptString2 = jSONObject.optString("message");
                    if ("200".equals(strOptString)) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            SignInActivity.this.showHelpCodeDialog(jSONObjectOptJSONObject.optString("code"), jSONObjectOptJSONObject.optString("desc"), jSONObjectOptJSONObject.optString("copy_txt"));
                        }
                    } else {
                        NewToast.showShort(SignInActivity.this, strOptString2, 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SignInActivity.this, "获取助力码失败", 0).show();
                }
            }
        });
    }

    private void doSign(String date) {
        if (TextUtils.isEmpty(date)) {
            NewToast.showShort(this, "签到日期不能为空", 0).show();
        } else {
            YJYHttpUtils.post(this, NetworkRequestsURL.signToSign, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.7
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    SignInActivity.this.hideProgressDialog();
                    NewToast.showShort(SignInActivity.this, "网络连接失败", 0).show();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                    SignInActivity.this.showProgressDialog("签到中...");
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass7) s2);
                    SignInActivity.this.hideProgressDialog();
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        String strOptString = jSONObject.optString("code");
                        String strOptString2 = jSONObject.optString("message");
                        if (!"200".equals(strOptString)) {
                            NewToast.showShort(SignInActivity.this, strOptString2, 0).show();
                            return;
                        }
                        SignInActivity.this.toSignBean = (ToSignBean) JSON.parseObject(s2, ToSignBean.class);
                        if (SignInActivity.this.toSignBean.getData().getPop_up_type().equals("0")) {
                            ToastUtil.shortToast(SignInActivity.this, "签到成功");
                        } else if (SignInActivity.this.toSignBean.getData().getPop_up_type().equals("1")) {
                            SignInActivity.this.showQrDialog(SignInActivity.this.toSignBean.getData().getQr_code().getQr_code_title(), SignInActivity.this.toSignBean.getData().getQr_code().getQr_code_desc(), SignInActivity.this.toSignBean.getData().getQr_code().getQr_code_url());
                            if (!TextUtils.isEmpty(SignInActivity.this.toSignBean.getData().getMsg())) {
                                SignInActivity signInActivity = SignInActivity.this;
                                ToastUtil.shortToast(signInActivity, signInActivity.toSignBean.getData().getMsg());
                            }
                        } else if (SignInActivity.this.toSignBean.getData().getPop_up_type().equals("2")) {
                            SignInActivity.this.showSignVipDialog();
                        }
                        SignInActivity.this.loadData();
                        EventBus.getDefault().post(EventBusConstant.EVENT_SIGN_SUCCESS);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        NewToast.showShort(SignInActivity.this, "签到失败", 0).show();
                    }
                }
            });
        }
    }

    private void initListener() {
        this.mIvBack.setOnClickListener(this);
        this.mSignDayAdapter.setOnItemClickListener(new SignDayAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.i
            @Override // com.psychiatrygarden.activity.mine.adapter.SignDayAdapter.OnItemClickListener
            public final void onItemClick(int i2, SignInBean.SignLogBean signLogBean) throws NumberFormatException {
                this.f12865a.lambda$initListener$3(i2, signLogBean);
            }
        });
        this.mTvInviteFriend.setOnClickListener(this);
        this.mTvHelpFriend.setOnClickListener(this);
        this.mTvMemberDetail.setOnClickListener(this);
    }

    private void initView() throws Resources.NotFoundException {
        this.mActionBar.hide();
        this.mTvDateStr = (TextView) findViewById(R.id.tv_date_str);
        this.signBannerFl = (FrameLayout) findViewById(R.id.sign_banner_fl);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        this.mTvDaysStr = (TextView) findViewById(R.id.tv_days_str);
        this.mRvSignDays = (RecyclerView) findViewById(R.id.rv_sign_days);
        this.mBannerViewPager = (ViewPager2) findViewById(R.id.banner_viewpager);
        this.mLlIndicator = (LinearLayout) findViewById(R.id.ll_indicator);
        this.mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.mViewPager = (ViewPager2) findViewById(R.id.viewpager);
        this.mIvBack = (ImageView) findViewById(R.id.iv_back);
        this.mTvInviteFriend = (TextView) findViewById(R.id.tv_invite_friend);
        this.mTvHelpFriend = (TextView) findViewById(R.id.tv_help_friend);
        this.mTvMemberDetail = (TextView) findViewById(R.id.tv_member_detail);
        this.titleBar = (Toolbar) findViewById(R.id.title_bar);
        this.helpEt = (EditText) findViewById(R.id.help_et);
        this.ivTopQuestion = (ImageView) findViewById(R.id.iv_top_question);
        this.ivTopShare = (ImageView) findViewById(R.id.iv_top_share);
        this.signTopTitleTv = (TextView) findViewById(R.id.sign_top_title_tv);
        this.llcontent = (LinearLayout) findViewById(R.id.ll_content);
        this.tabLayoutTop = (TabLayout) findViewById(R.id.tab_layout_top);
        this.ivTopQuestion.setOnClickListener(this);
        this.ivTopShare.setOnClickListener(this);
        this.mViewPager.setNestedScrollingEnabled(true);
        this.mViewPager.getChildAt(0).setOverScrollMode(2);
        this.helpEt.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.psychiatrygarden.activity.mine.l
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                this.f12918c.lambda$initView$0(view, z2);
            }
        });
        ViewGroup.LayoutParams layoutParams = this.mViewPager.getLayoutParams();
        layoutParams.height = CommonUtil.getScreenHeight(this.mContext) - CommonUtil.dip2px(this.mContext, 44.0f);
        layoutParams.width = CommonUtil.getScreenWidth(this.mContext) - CommonUtil.dip2px(this.mContext, 32.0f);
        this.mViewPager.setLayoutParams(layoutParams);
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.1
            @Override // android.view.View.OnScrollChangeListener
            public void onScrollChange(View view, int i2, int i12, int i22, int i3) {
                int[] iArr = new int[2];
                SignInActivity.this.mTabLayout.getLocationOnScreen(iArr);
                int i4 = iArr[1];
                if (i4 <= 1950) {
                    SignInActivity.this.getWindow().getDecorView().setSystemUiVisibility(8192);
                    SignInActivity signInActivity = SignInActivity.this;
                    StatusBarUtil.setColor(signInActivity, ContextCompat.getColor(signInActivity.mContext, R.color.new_bg_two_color), 0);
                    SignInActivity.this.titleBar.setBackgroundColor(ContextCompat.getColor(SignInActivity.this.mContext, R.color.new_bg_two_color));
                    SignInActivity.this.signTopTitleTv.setVisibility(0);
                } else {
                    SignInActivity signInActivity2 = SignInActivity.this;
                    StatusBarUtil.setColor(signInActivity2, ContextCompat.getColor(signInActivity2.mContext, R.color.corlor_FEE050), 0);
                    SignInActivity.this.titleBar.setBackgroundResource(R.drawable.sign_topbar_bg);
                    SignInActivity.this.getWindow().getDecorView().setSystemUiVisibility(8192);
                    SignInActivity.this.signTopTitleTv.setVisibility(8);
                }
                if (i4 > CommonUtil.dip2px(SignInActivity.this.mContext, 44.0f) + StatusBarUtil.getStatusBarHeight(SignInActivity.this.mContext)) {
                    if (SignInActivity.this.canscroll) {
                        EventBus.getDefault().post("noscroll");
                        SignInActivity.this.canscroll = false;
                    }
                    SignInActivity.this.tabLayoutTop.setVisibility(8);
                    return;
                }
                SignInActivity.this.tabLayoutTop.setVisibility(0);
                if (SignInActivity.this.canscroll) {
                    return;
                }
                EventBus.getDefault().post("canscroll");
                SignInActivity.this.canscroll = true;
            }
        });
        this.helpEt.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    SignInActivity.this.mTvHelpFriend.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.main_theme_four_deep_color));
                } else {
                    SignInActivity.this.mTvHelpFriend.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.new_fail_color));
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i12, int i22) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i12, int i22) {
            }
        });
        this.mRvSignDays.setLayoutManager(new GridLayoutManager(this, 7));
        this.mSignDayAdapter = new SignDayAdapter(this, new ArrayList());
        this.mRvSignDays.addItemDecoration(new SpacesItemDecoration(CommonUtil.dip2px(this, 7.0f)));
        this.mRvSignDays.setAdapter(this.mSignDayAdapter);
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        viewPagerFragmentAdapter.addFragment(SingleRankFragment.newInstance("sign_continuous"));
        viewPagerFragmentAdapter.addFragment(SingleRankFragment.newInstance("sign_total"));
        this.mViewPager.setAdapter(viewPagerFragmentAdapter);
        setTabView(this.mTabLayout);
        setTabView(this.tabLayoutTop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$3(int i2, SignInBean.SignLogBean signLogBean) throws NumberFormatException {
        int i3 = Integer.parseInt(signLogBean.getSign_type());
        if (i3 == 20) {
            doSign(signLogBean.getDate());
            return;
        }
        if (i3 == 22) {
            doRepairSign(signLogBean.getDate());
            return;
        }
        if ((i3 == 10 || i3 == 11) && signLogBean.getIs_full_sign().equals("1") && signLogBean.getHave_qr_code().equals("1") && signLogBean.getQr_code().getStatus().equals("1")) {
            showQrDialog(signLogBean.getQr_code().getQr_code_title(), signLogBean.getQr_code().getQr_code_desc(), signLogBean.getQr_code().getQr_code_url());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, boolean z2) {
        if (z2) {
            getWindow().setSoftInputMode(32);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(int i2) {
        shareToFriend(i2, this.mSignInBean.getData().getShare());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(int i2) {
        shareToFriend(i2, this.mSignInBean.getData().getShare());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setTabView$1(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            tab.setText("连签榜");
        } else {
            tab.setText("累签榜");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setTabView$2(View view) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupBanner$4(int i2) {
        if (this.adsDTOList.isEmpty()) {
            return;
        }
        pointCount(this.mContext, "1");
        try {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.adsDTOList.get(i2).getExtra()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showHelpCodeDialog$7(String str, Dialog dialog, View view) {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("助力码", str));
        try {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            if (launchIntentForPackage != null) {
                startActivity(launchIntentForPackage);
            } else {
                ToastUtil.shortToast(this.mContext, "未安装微信");
            }
        } catch (Exception unused) {
            ToastUtil.shortToast(this.mContext, "跳转微信失败");
        }
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showQrDialog$9(String str, Dialog dialog, View view) {
        Glide.with(this.mContext).asBitmap().load(str).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.11
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (BitmapUtilsKt.saveImageToGallery(SignInActivity.this.mContext, resource, "Downloaded_" + System.currentTimeMillis())) {
                    ToastUtil.shortToast(SignInActivity.this.mContext, "图片已保存到相册");
                } else {
                    ToastUtil.shortToast(SignInActivity.this.mContext, "保存失败");
                }
            }
        });
        try {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            if (launchIntentForPackage != null) {
                startActivity(launchIntentForPackage);
            } else {
                ToastUtil.shortToast(this.mContext, "未安装微信");
            }
        } catch (Exception unused) {
            ToastUtil.shortToast(this.mContext, "跳转微信失败");
        }
        dialog.dismiss();
    }

    private void loadAdData() {
        YJYHttpUtils.get(this, NetworkRequestsURL.signinAds, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(SignInActivity.this, "网络连接失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    SignAdBean signAdBean = (SignAdBean) new Gson().fromJson(s2, SignAdBean.class);
                    if ("200".equals(signAdBean.getCode())) {
                        SignInActivity.this.adsDTOList = signAdBean.getData();
                        if (SignInActivity.this.adsDTOList.isEmpty()) {
                            SignInActivity.this.signBannerFl.setVisibility(8);
                        } else {
                            SignInActivity.this.signBannerFl.setVisibility(0);
                            SignInActivity.this.setupBanner();
                        }
                    } else {
                        NewToast.showShort(SignInActivity.this, signAdBean.getMessage(), 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SignInActivity.this, "数据解析出错", 0).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        YJYHttpUtils.get(this, NetworkRequestsURL.getSignLog, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SignInActivity.this.hideProgressDialog();
                NewToast.showShort(SignInActivity.this, "网络连接失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                SignInActivity.this.hideProgressDialog();
                try {
                    SignInActivity.this.mSignInBean = (SignInBean) new Gson().fromJson(s2, SignInBean.class);
                    if ("200".equals(SignInActivity.this.mSignInBean.getCode())) {
                        SignInActivity signInActivity = SignInActivity.this;
                        signInActivity.updateUI(signInActivity.mSignInBean);
                    } else {
                        SignInActivity signInActivity2 = SignInActivity.this;
                        NewToast.showShort(signInActivity2, signInActivity2.mSignInBean.getMessage(), 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SignInActivity.this, "数据解析出错", 0).show();
                }
            }
        });
    }

    private void setTabView(TabLayout mTabLayout) throws Resources.NotFoundException {
        TabLayout.TabView tabView;
        new TabLayoutMediator(mTabLayout, this.mViewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.psychiatrygarden.activity.mine.j
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
                SignInActivity.lambda$setTabView$1(tab, i2);
            }
        }).attach();
        for (int i2 = 0; i2 < mTabLayout.getTabCount(); i2++) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i2);
            if (tabAt != null && (tabView = tabAt.view) != null) {
                tabView.setLongClickable(false);
                tabAt.view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.mine.k
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return SignInActivity.lambda$setTabView$2(view);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupBanner() throws Resources.NotFoundException {
        BannerAdapter bannerAdapter = new BannerAdapter(this, this.adsDTOList);
        this.mBannerAdapter = bannerAdapter;
        this.mBannerViewPager.setAdapter(bannerAdapter);
        setupIndicator();
        startAutoScroll();
        this.mBannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.q
            @Override // com.psychiatrygarden.activity.mine.adapter.BannerAdapter.OnItemClickListener
            public final void onItemClick(int i2) {
                this.f12993a.lambda$setupBanner$4(i2);
            }
        });
        this.mBannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.5
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                SignInActivity.this.mCurrentBannerPosition = position;
                SignInActivity.this.updateIndicator(position);
            }
        });
    }

    private void setupIndicator() throws Resources.NotFoundException {
        this.mLlIndicator.removeAllViews();
        this.mIndicators.clear();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.dp_8);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.dp_4);
        for (int i2 = 0; i2 < this.adsDTOList.size(); i2++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
            layoutParams.setMargins(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
            imageView.setLayoutParams(layoutParams);
            if (i2 == 0) {
                imageView.setImageResource(R.drawable.shape_indicator_selected);
            } else {
                imageView.setImageResource(R.drawable.shape_indicator_normal);
            }
            this.mIndicators.add(imageView);
            this.mLlIndicator.addView(imageView);
        }
    }

    private void shareToFriend(int i2, SignInBean.ShareBean shareBean) {
        signShareNet();
        UMImage uMImage = shareBean.getImg().equals("") ? new UMImage(this.mContext, R.drawable.ic_launcher) : new UMImage(this.mContext, shareBean.getImg());
        UMMin uMMin = new UMMin(TextUtils.isEmpty(shareBean.getLink()) ? shareBean.getImg() : shareBean.getLink());
        uMMin.setThumb(uMImage);
        uMMin.setTitle(shareBean.getTitle());
        uMMin.setDescription(shareBean.getContent());
        uMMin.setPath("pages/index/startPage?sourceType=sign&imgUrl=" + shareBean.getLink() + "&shareUserId=" + UserConfig.getUserId() + "&shareAppId=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        uMMin.setUserName("gh_14d59acf6877");
        if (CommonConfig.INSTANCE.getYI_KAO_BANG_NETWORK() == 0) {
            Config.setMiniPreView();
        }
        new ShareAction(this).withMedia(uMMin).setPlatform(BaseActivity.platforms.get(i2).mPlatform).share();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHelpCodeDialog(String helpCode, String desc, final String copy_txt) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_help_code, (ViewGroup) null);
        dialog.setContentView(viewInflate);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(17);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_help_code);
        linearLayout.removeAllViews();
        int iDip2px = CommonUtil.dip2px(this, CommonUtil.dip2px(this.mContext, 2.0f));
        if (!TextUtils.isEmpty(helpCode)) {
            for (int i2 = 0; i2 < helpCode.length(); i2++) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dip2px(this.mContext, 32.0f), CommonUtil.dip2px(this.mContext, 40.0f));
                if (i2 < helpCode.length() - 1) {
                    layoutParams.rightMargin = iDip2px;
                }
                textView.setLayoutParams(layoutParams);
                textView.setText(String.valueOf(helpCode.charAt(i2)));
                textView.setGravity(17);
                textView.setTextSize(2, 20.0f);
                textView.setTextColor(this.mContext.getColor(R.color.main_theme_color));
                textView.setBackgroundResource(R.drawable.shape_btn_light_red_radius_4);
                textView.setTypeface(null, 1);
                linearLayout.addView(textView);
            }
        }
        ((TextView) viewInflate.findViewById(R.id.tv_desc)).setText(desc);
        ((TextView) viewInflate.findViewById(R.id.tv_copy_and_open_wechat)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12919c.lambda$showHelpCodeDialog$7(copy_txt, dialog, view);
            }
        });
        viewInflate.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = -1;
        attributes.height = -2;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQrDialog(String qrCodeTitle, String qrCodeDesc, final String qrCodeUrl) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_sign_qr, (ViewGroup) null);
        dialog.setContentView(viewInflate);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(17);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_title);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_desc);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_qrcode);
        textView.setText(qrCodeTitle);
        textView2.setText(qrCodeDesc);
        GlideUtils.loadImage(this.mContext, qrCodeUrl, imageView);
        ((TextView) viewInflate.findViewById(R.id.tv_copy_and_open_wechat)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12938c.lambda$showQrDialog$9(qrCodeUrl, dialog, view);
            }
        });
        viewInflate.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = -1;
        attributes.height = -2;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSignVipDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_sign_vip, (ViewGroup) null);
        dialog.setContentView(viewInflate);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(17);
        TextView textView = (TextView) viewInflate.findViewById(R.id.sign_btn_tv);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_title);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_desc);
        textView2.setText(this.toSignBean.getData().getReward_str());
        textView3.setText(this.toSignBean.getData().getReward_notice());
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        viewInflate.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = -1;
        attributes.height = -2;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        dialog.show();
    }

    private void signShareNet() {
        YJYHttpUtils.post(this, NetworkRequestsURL.touchShareUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
            }
        });
    }

    private void startAutoScroll() {
        stopAutoScroll();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new AnonymousClass6(), C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    private void stopAutoScroll() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIndicator(int position) {
        for (int i2 = 0; i2 < this.mIndicators.size(); i2++) {
            if (i2 == position) {
                this.mIndicators.get(i2).setImageResource(R.drawable.shape_indicator_selected);
            } else {
                this.mIndicators.get(i2).setImageResource(R.drawable.shape_indicator_normal);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(SignInBean signInBean) {
        if (signInBean.getData() != null) {
            this.mTvDateStr.setText(signInBean.getData().getDate_str());
            String days_str = signInBean.getData().getDays_str();
            SpannableString spannableString = new SpannableString(days_str);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.first_txt_color)), 0, days_str.length(), 33);
            Matcher matcher = Pattern.compile(RegexPool.NUMBERS).matcher(days_str);
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.new_fail_color)), matcher.start(), matcher.end(), 33);
            }
            this.mTvDaysStr.setText(spannableString);
            if (signInBean.getData().getLog() != null) {
                this.mSignDayAdapter.setData(signInBean.getData().getLog());
            }
        }
    }

    private void useHelpCodeNet(String code) {
        if (TextUtils.isEmpty(code)) {
            NewToast.showShort(this, "助力码不能为空", 0).show();
            return;
        }
        CommonUtil.hideKeyboard(this);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("code", code);
        YJYHttpUtils.post(this, NetworkRequestsURL.useHelpCode, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.SignInActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SignInActivity.this.hideProgressDialog();
                NewToast.showShort(SignInActivity.this, "网络连接失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SignInActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                SignInActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("code");
                    String strOptString2 = jSONObject.optString("message");
                    if ("200".equals(strOptString)) {
                        NewToast.showShort(SignInActivity.this, "助力成功", 0).show();
                    } else {
                        NewToast.showShort(SignInActivity.this, strOptString2, 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SignInActivity.this, "助力失败", 0).show();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        initView();
        initListener();
        initStatusBar();
        loadData();
        loadAdData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.corlor_FEE050), 0);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back /* 2131364002 */:
                finish();
                break;
            case R.id.iv_top_question /* 2131364262 */:
                new XPopup.Builder(this).dismissOnTouchOutside(Boolean.TRUE).asCustom(new SignRulePop(this, this.mSignInBean.getData().getRule_content())).show();
                break;
            case R.id.iv_top_share /* 2131364263 */:
                new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.mine.r
                    @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                    public final void onclickIntBack(int i2) {
                        this.f12994a.lambda$onClick$5(i2);
                    }
                }, false, true).show();
                break;
            case R.id.tv_help_friend /* 2131368084 */:
                useHelpCodeNet(this.helpEt.getText().toString().trim());
                break;
            case R.id.tv_invite_friend /* 2131368124 */:
                new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.mine.s
                    @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                    public final void onclickIntBack(int i2) {
                        this.f13015a.lambda$onClick$6(i2);
                    }
                }, false, true).show();
                break;
            case R.id.tv_member_detail /* 2131368191 */:
                goActivity(MemberDetailActivity.class);
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        stopAutoScroll();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("refresh_sign_data".equals(str)) {
            loadData();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_sign_in);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
