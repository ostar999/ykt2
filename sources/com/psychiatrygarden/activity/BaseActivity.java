package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.base.SupportActivity;
import com.lxj.xpopup.XPopup;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity;
import com.psychiatrygarden.activity.mine.setting.CaculatorAct;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ScreenShotEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.receiver.ScreenShotListenManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DialogLoading;
import com.psychiatrygarden.widget.LoadDialogFragment;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes5.dex */
public abstract class BaseActivity extends SupportActivity {
    public static final int TYPE_COUPON_ALL = 1;
    public static final int TYPE_ONLY_COUPON = 3;
    public static final int TYPE_ONLY_RED_PACKET = 2;
    public static ArrayList<SnsPlatform> platforms = new ArrayList<>();
    public ImageView ImageView;
    protected ImageView iv_actionbar_right;
    protected ImageView iv_right_img;
    private LoadDialogFragment loadingPopWindow;
    public ActionBar mActionBar;
    protected int mBaseTheme;
    protected ImageView mBtnActionbarBack;
    protected Button mBtnActionbarRight;
    public Context mContext;
    public View mCustomView;
    private DialogLoading mLoadingDig;
    protected RelativeLayout mLyCommentMsg;
    protected TextView mTvActionbarRight;
    protected TextView mTvCommemtUnredMsg;
    protected TextView mTxtActionbarTitle;
    String pageSimpleName;
    private ScreenShotListenManager screenShotListenManager;
    String start_timestamp;
    protected RelativeLayout titlebar_layout;
    public String TAG = "";
    private int type = 1;
    private Map<String, String> PAGE_ALIAS_MAP = new HashMap();

    private int getIconBackRes() {
        return SkinManager.getCurrentSkinType(this) == 0 ? R.mipmap.ic_black_back : R.mipmap.ic_black_back_night;
    }

    private void initLogPages() {
        this.PAGE_ALIAS_MAP.put("QuestionWrongCollectionNoteActivity", "我的错题");
        this.PAGE_ALIAS_MAP.put("QuestionColumnSortActivity", "栏目调整页面");
    }

    private void initPlatforms() {
        platforms.clear();
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SINA.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TENCENT.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        platforms.add(SHARE_MEDIA.DINGTALK.toSnsPlatform());
        platforms.add(SHARE_MEDIA.RENREN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.DOUBAN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SMS.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EMAIL.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YNOTE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LAIWANG.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LAIWANG_DYNAMIC.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LINKEDIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FACEBOOK.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FACEBOOK_MESSAGER.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TWITTER.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WHATSAPP.toSnsPlatform());
        platforms.add(SHARE_MEDIA.GOOGLEPLUS.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LINE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.INSTAGRAM.toSnsPlatform());
        platforms.add(SHARE_MEDIA.KAKAO.toSnsPlatform());
        platforms.add(SHARE_MEDIA.PINTEREST.toSnsPlatform());
        platforms.add(SHARE_MEDIA.POCKET.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TUMBLR.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FLICKR.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FOURSQUARE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.MORE.toSnsPlatform());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionBarWidgets$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ WindowInsetsCompat lambda$setContentFillFromStatusBar$7(View view, WindowInsetsCompat windowInsetsCompat) {
        view.setPadding(0, 0, 0, windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom);
        return windowInsetsCompat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTitle$2(int i2) {
        this.mTxtActionbarTitle.setText(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTitle$3(String str) {
        this.mTxtActionbarTitle.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUnredCommentNum$4(String str) {
        this.mTvCommemtUnredMsg.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$5(String str) {
        showProgressDialogByTxt(str);
        LoadDialogFragment loadDialogFragment = this.loadingPopWindow;
        if (loadDialogFragment == null || loadDialogFragment.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.setDialog_text(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$6(boolean z2, String str) {
        showProgressDialog(z2);
        LoadDialogFragment loadDialogFragment = this.loadingPopWindow;
        if (loadDialogFragment == null || loadDialogFragment.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.setDialog_text(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startScreenShotListen$0(String str, int i2) {
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_SCREEN_PLAT_VIEW, false, this) || TextUtils.isEmpty(str)) {
            return;
        }
        if (ProjectApp.instance().topActivityIsScreenShot() == 1) {
            EventBus.getDefault().post(new ScreenShotEvent(str, i2));
            return;
        }
        if (ProjectApp.instance().topActivityIsScreenShot() == 2) {
            Intent intent = new Intent(this, (Class<?>) ScreenShotDialogNewActivity.class);
            intent.putExtra("imagePath", str);
            intent.putExtra("imgHeight", i2);
            intent.setFlags(270532608);
            startActivity(intent);
        }
    }

    private void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        NewToast.showShort(this, msg, 0).show();
    }

    private void startScreenShotListen() {
        this.screenShotListenManager.setListener(new ScreenShotListenManager.OnScreenShotListener() { // from class: com.psychiatrygarden.activity.e1
            @Override // com.psychiatrygarden.receiver.ScreenShotListenManager.OnScreenShotListener
            public final void onShot(String str, int i2) {
                this.f12265a.lambda$startScreenShotListen$0(str, i2);
            }
        });
        this.screenShotListenManager.startListen();
    }

    public void AlertToast(String message) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        NewToast.showShort(this, message);
    }

    public void dismissCouponPop() {
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        hideInputMethod();
    }

    public int getActionBarLayout() {
        return R.layout.actionbar_main_custom;
    }

    public String getPageAlias(String className) {
        if (className != null && !className.isEmpty()) {
            String strSubstring = className.substring(className.lastIndexOf(46) + 1);
            if (this.PAGE_ALIAS_MAP.containsKey(strSubstring)) {
                if (!strSubstring.equals("QuestionWrongCollectionNoteActivity")) {
                    return this.PAGE_ALIAS_MAP.get(strSubstring);
                }
                String stringExtra = getIntent().getStringExtra("type");
                if (stringExtra.equals("error")) {
                    return "我的错题";
                }
                if (stringExtra.equals("collection")) {
                    return "我的收藏";
                }
                if (stringExtra.equals("note")) {
                    return "我的笔记";
                }
                if (stringExtra.equals("praise")) {
                    return "我的点赞";
                }
                if (stringExtra.equals(ClientCookie.COMMENT_ATTR)) {
                    return "我的评论";
                }
            }
        }
        return "";
    }

    public String getTilte() {
        return this.mTxtActionbarTitle.getText().toString();
    }

    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivity(intent);
    }

    public void hideInputMethod() {
        View currentFocus = getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager == null || currentFocus == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public void hideProgressDialog() {
        LoadDialogFragment loadDialogFragment = this.loadingPopWindow;
        if (loadDialogFragment != null && loadDialogFragment.isAdded() && this.loadingPopWindow.isVisible()) {
            this.loadingPopWindow.dismissAllowingStateLoss();
            this.loadingPopWindow = null;
        }
    }

    public abstract void init();

    @SuppressLint({"InflateParams"})
    public void initActionBar() {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-1, -1, 17);
        View viewInflate = getLayoutInflater().inflate(getActionBarLayout(), (ViewGroup) null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0.0f);
        }
        ActionBar supportActionBar = getSupportActionBar();
        this.mActionBar = supportActionBar;
        supportActionBar.setDisplayOptions(16);
        this.mActionBar.setDisplayShowHomeEnabled(true);
        this.mActionBar.setDisplayShowCustomEnabled(true);
        this.mActionBar.setDisplayShowTitleEnabled(true);
        this.mActionBar.setCustomView(viewInflate, layoutParams);
        Toolbar toolbar = (Toolbar) viewInflate.getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0, 0, 0, 0);
        this.mCustomView = this.mActionBar.getCustomView();
    }

    public void initActionBarWidgets() {
        this.titlebar_layout = (RelativeLayout) this.mCustomView.findViewById(R.id.titlebar_layout);
        this.mBtnActionbarBack = (ImageView) this.mCustomView.findViewById(R.id.iv_actionbar_back);
        this.mTxtActionbarTitle = (TextView) this.mCustomView.findViewById(R.id.txt_actionbar_title);
        this.mBtnActionbarRight = (Button) this.mCustomView.findViewById(R.id.btn_actionbar_right);
        this.mTvActionbarRight = (TextView) this.mCustomView.findViewById(R.id.tv_actionbar_right);
        this.iv_actionbar_right = (ImageView) this.mCustomView.findViewById(R.id.iv_actionbar_right);
        this.iv_right_img = (ImageView) this.mCustomView.findViewById(R.id.iv_right_img);
        this.mLyCommentMsg = (RelativeLayout) this.mCustomView.findViewById(R.id.ly_comment_msg);
        this.mTvCommemtUnredMsg = (TextView) this.mCustomView.findViewById(R.id.comment_unreadnum);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12462c.lambda$initActionBarWidgets$1(view);
            }
        });
        ContextCompat.getDrawable(this, getIconBackRes());
        this.mBtnActionbarBack.setImageResource(getIconBackRes());
    }

    public void initStatusBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    public void initwriteStatusBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    public boolean isLogin() {
        if (!UserConfig.getUserId().equals("")) {
            return true;
        }
        startActivity(new Intent(this, (Class<?>) LoginActivity.class));
        return false;
    }

    public boolean isPortrait() {
        return getApplicationContext().getResources().getConfiguration().orientation != 2;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.d("PHONE_INFO", Build.BRAND + "," + Build.MANUFACTURER);
        LogUtils.d("CurrentActivity", getClass().getName());
        UiModeManager uiModeManager = (UiModeManager) getSystemService("uimode");
        int i2 = getResources().getConfiguration().uiMode;
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_BY_SYS_SKIN_MODE, false, this)) {
            if ((i2 & 48) == 32 || uiModeManager.getNightMode() == 2) {
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 1, this);
                }
            } else if (SkinManager.getCurrentSkinType(this) == 1) {
                SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 0, this);
            }
            ((UiModeManager) getSystemService("uimode")).setNightMode(SkinManager.getCurrentSkinType(this) == 1 ? 2 : 1);
        } else {
            uiModeManager.setNightMode(1);
        }
        initStatusBar();
        SkinManager.onActivityCreateSetSkin(this);
        if (ScreenUtil.isTablet(this)) {
            if (Build.VERSION.SDK_INT > 26) {
                if (getResources().getConfiguration().orientation != 2) {
                    setRequestedOrientation(1);
                } else if (!(this instanceof ExitLoginDialogActivity)) {
                    setRequestedOrientation(0);
                }
            }
        } else if (Build.VERSION.SDK_INT > 26) {
            if ((this instanceof ComputerMockTestAct) || (this instanceof ComputerMockTestLoginAct) || (this instanceof ComputerMockTestLoginSureAct) || (this instanceof ComputerMockTestModeTwoAct) || (this instanceof ComputerStatisticsAct) || (this instanceof ComputerModeTwoStatisticsAct) || (this instanceof ComputerMockTestFinishAct) || (this instanceof CaculatorAct)) {
                setRequestedOrientation(0);
            } else if (!(this instanceof AliPlayerVideoPlayActivity) && !(this instanceof AliperCommentActivity)) {
                setRequestedOrientation(1);
            }
        }
        super.onCreate(savedInstanceState);
        initPlatforms();
        this.screenShotListenManager = ScreenShotListenManager.newInstance(this);
        EventBus.getDefault().register(this);
        this.TAG = getClass().toString();
        this.mContext = this;
        setActionBarContent();
        ProjectApp.instance().addActivity(this);
        setContentView();
        init();
        setListenerForWidget();
        XPopup.setShadowBgColor(Color.parseColor("#50000000"));
        if (SkinManager.getCurrentSkinType(this) == 1) {
            XPopup.setNavigationBarColor(Color.parseColor("#121622"));
        } else {
            XPopup.setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
        initLogPages();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ProjectApp.instance().removeActivity(this);
        DialogLoading dialogLoading = this.mLoadingDig;
        if (dialogLoading != null && dialogLoading.isShowing()) {
            this.mLoadingDig.dismiss();
        }
        if (this.mCustomView != null) {
            this.mCustomView = null;
        }
        super.onDestroy();
    }

    public abstract void onEventMainThread(String str);

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        String str;
        super.onPause();
        ProjectApp.isForeground = false;
        this.screenShotListenManager.stopListen();
        String simpleName = getClass().getSimpleName();
        this.pageSimpleName = simpleName;
        if (getPageAlias(simpleName).isEmpty()) {
            return;
        }
        String stringExtra = getIntent().getStringExtra("type");
        StringBuilder sb = new StringBuilder();
        sb.append(this.pageSimpleName);
        if (TextUtils.isEmpty(stringExtra)) {
            str = "";
        } else {
            str = StrPool.UNDERLINE + stringExtra;
        }
        sb.append(str);
        CommonUtil.addLog(sb.toString(), getPageAlias(this.pageSimpleName), this.start_timestamp, System.currentTimeMillis() + "");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        ProjectApp.isForeground = true;
        startScreenShotListen();
        String simpleName = getClass().getSimpleName();
        this.pageSimpleName = simpleName;
        if (getPageAlias(simpleName).isEmpty()) {
            return;
        }
        this.start_timestamp = System.currentTimeMillis() + "";
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        return getCurrentFocus() != null ? ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0) : super.onTouchEvent(event);
    }

    public void pointCount(Context mContext, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        if ("error".equals(type)) {
            ajaxParams.put("type", Constants.VIA_SHARE_TYPE_INFO);
        } else if ("collection".equals(type)) {
            ajaxParams.put("type", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        } else if ("note".equals(type)) {
            ajaxParams.put("type", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        } else if (ClientCookie.COMMENT_ATTR.equals(type)) {
            ajaxParams.put("type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        }
        YJYHttpUtils.get(mContext, NetworkRequestsURL.pointCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.BaseActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
            }
        });
    }

    public void setActionBarContent() {
        initActionBar();
        initActionBarWidgets();
    }

    public void setContentFillFromStatusBar(boolean reverse) {
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 0 ? "#FBFBFB" : "#1c2134"));
        window.setStatusBarColor(0);
        window.clearFlags(134217728);
        window.addFlags(Integer.MIN_VALUE);
        if (Build.VERSION.SDK_INT >= 30) {
            window.setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                if (reverse ? true : SkinManager.getCurrentSkinType(this) == 1) {
                    insetsController.setSystemBarsAppearance(0, 8);
                    insetsController.setSystemBarsAppearance(0, 16);
                } else {
                    insetsController.setSystemBarsAppearance(8, 8);
                }
            }
        } else {
            window.clearFlags(67108864);
            window.getDecorView().setSystemUiVisibility(R2.drawable.ee_19);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), new OnApplyWindowInsetsListener() { // from class: com.psychiatrygarden.activity.c1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return BaseActivity.lambda$setContentFillFromStatusBar$7(view, windowInsetsCompat);
            }
        });
    }

    public abstract void setContentView();

    public abstract void setListenerForWidget();

    public void setNavBarColor() {
        RelativeLayout relativeLayout = this.titlebar_layout;
        if (relativeLayout != null) {
            relativeLayout.setBackgroundColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#121622" : PLVAuthorizationBean.FCOLOR_DEFAULT));
            this.mBtnActionbarBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
            this.mTxtActionbarTitle.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#7380A9" : "#141516"));
        }
    }

    public void setNewStyleStatusBarColor() {
        this.mBaseTheme = SkinManager.getCurrentSkinType(this);
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.app_bg});
        StatusBarUtil.setColor(this, typedArrayObtainStyledAttributes.getColor(0, -1), 0);
        if (this.mBaseTheme == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        getWindow().setNavigationBarColor(Color.parseColor(this.mBaseTheme == 0 ? "#FBFBFB" : "#1C2134"));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setNewStyleStatusBarColor2() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, Color.parseColor("#121622"), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        } else {
            StatusBarUtil.setColor(this, Color.parseColor("#FFFFFF"), 0);
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        }
    }

    public void setNewStyleStatusBarColor3() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, Color.parseColor("#0D111D"), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#0D111D"));
        } else {
            StatusBarUtil.setColor(this, Color.parseColor("#F9FAFB"), 0);
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setNavigationBarColor(Color.parseColor("#F9FAFB"));
        }
    }

    public void setSwipeBackEnable(boolean istrue) {
    }

    @Override // android.app.Activity
    public void setTitle(final int res) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.g1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12422c.lambda$setTitle$2(res);
            }
        });
    }

    public void setUnredCommentNum(final String res) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.d1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12224c.lambda$setUnredCommentNum$4(res);
            }
        });
    }

    public void showProgressDialog() {
        if (this.loadingPopWindow == null) {
            this.loadingPopWindow = new LoadDialogFragment();
        }
        if (this.loadingPopWindow.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.show(getSupportFragmentManager(), "loadingfragment");
    }

    public void showProgressDialogByTxt(String txt) {
        if (this.loadingPopWindow == null) {
            this.loadingPopWindow = new LoadDialogFragment(txt);
        }
        if (this.loadingPopWindow.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.show(getSupportFragmentManager(), "loadingfragment");
    }

    public void setTitle(final String res) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.j1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12542c.lambda$setTitle$3(res);
            }
        });
    }

    public void AlertToast(int strid) {
        NewToast.showShort(this, getString(strid));
    }

    public void goActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showProgressDialog(final String txt) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.i1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12491c.lambda$showProgressDialog$5(txt);
            }
        });
    }

    public void showProgressDialog(boolean is_cancel) {
        getSupportFragmentManager();
        if (this.loadingPopWindow == null) {
            this.loadingPopWindow = new LoadDialogFragment(is_cancel);
        }
        if (this.loadingPopWindow.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.show(getSupportFragmentManager(), "loadingfragment");
    }

    public void showProgressDialog(final String txt, final boolean is_cancel) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.f1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12334c.lambda$showProgressDialog$6(is_cancel, txt);
            }
        });
    }
}
