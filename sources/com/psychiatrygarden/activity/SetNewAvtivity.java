package com.psychiatrygarden.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.hjq.permissions.Permission;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.activity.mine.setting.PlayerSetting;
import com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity;
import com.psychiatrygarden.activity.setting.AccountWithPasswordActivity;
import com.psychiatrygarden.activity.setting.FriendPermissionAct;
import com.psychiatrygarden.activity.setting.MessageNoticeSetAct;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.ClearData;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearDataPopWidow;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ExitLoginPopWidow;
import com.psychiatrygarden.widget.PopClearAnswerRecord;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.MoreSetAct;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SetNewAvtivity extends BaseActivity {
    private final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 1;
    private ImageView mImgBack;
    private TextView mTvDeepColor;
    private TextView mTvErrorSetValue;
    private TextView mTvTitle;
    private Switch swShowScreenPlat;
    private Switch sw_error_question;
    private TextView tvNewVersion;
    private TextView tv_app_clear;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exitDialog$19(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        EMClient.getInstance().logout(true);
        SharePreferencesUtils.clearAppointData(this);
        Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
        intent.addFlags(268468224);
        startActivity(intent);
        overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        goActivity(AccountWithPasswordActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(View view) {
        NavigationUtilKt.goToShouHuoDiZhiActivity(this.mContext, ShouhuodizhiActivity.FROM_TYPE_VALUE_SETTING, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$11(View view) {
        startActivity(FriendPermissionAct.newIntent(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$12(View view) {
        startActivity(MessageNoticeSetAct.newIntent(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$13(CompoundButton compoundButton, boolean z2) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.CUT_QUESTION_VOICE, z2, compoundButton.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$14() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$15(CompoundButton compoundButton, boolean z2) {
        if (!z2) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_SCREEN_PLAT_VIEW, false, compoundButton.getContext());
        } else if (CommonUtil.hasRequiredPermissions(this)) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_SCREEN_PLAT_VIEW, true, compoundButton.getContext());
        } else {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.mj
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13019a.lambda$init$14();
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$16(View view) {
        DownloadSetting.startActivity(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$17(View view) {
        PlayerSetting.startActivity(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$18(CompoundButton compoundButton, boolean z2) {
        if (z2) {
            settingErrorConfig("1");
        } else {
            settingErrorConfig("2");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        Intent intent = new Intent(view.getContext(), (Class<?>) MoreSetAct.class);
        intent.putExtra("modeType", 0);
        intent.putExtra("mBaseTheme", this.mBaseTheme);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3() {
        try {
            this.tv_app_clear.setText("0B");
            new ClearData().clearInternalOrExCache(this);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        new XPopup.Builder(this).asCustom(new ClearDataPopWidow(this, new ClearDataPopWidow.ClickIml() { // from class: com.psychiatrygarden.activity.jj
            @Override // com.psychiatrygarden.widget.ClearDataPopWidow.ClickIml
            public final void mClickIml() {
                this.f12563a.lambda$init$3();
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        new XPopup.Builder(this).moveUpToKeyboard(Boolean.FALSE).asCustom(new PopClearAnswerRecord(this)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        EMClient.getInstance().logout(true, new EMCallBack() { // from class: com.psychiatrygarden.activity.SetNewAvtivity.1
            @Override // com.hyphenate.EMCallBack
            public void onError(int code, String error) {
                Log.e("logout_huanxin", "退出失败");
            }

            @Override // com.hyphenate.EMCallBack
            public /* synthetic */ void onProgress(int i2, String str) {
                d1.a.a(this, i2, str);
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                Log.e("logout_huanxin", "退出成功");
            }
        });
        try {
            LocalBroadcastManager.getInstance(view.getContext()).sendBroadcast(new Intent().setAction("EXIT_LOGIN"));
        } catch (Exception unused) {
        }
        SharePreferencesUtils.clearAppointData(this);
        Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
        intent.addFlags(268468224);
        startActivity(intent);
        overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(final View view) {
        new XPopup.Builder(this).asCustom(new ExitLoginPopWidow(this, new ExitLoginPopWidow.ClickIml() { // from class: com.psychiatrygarden.activity.kj
            @Override // com.psychiatrygarden.widget.ExitLoginPopWidow.ClickIml
            public final void mClickIml() {
                this.f12592a.lambda$init$6(view);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(View view) {
        goActivity(AbourtYKBActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9(View view) {
        DarkModeActivity.INSTANCE.newIntent(this);
    }

    private void settingErrorConfig(final String status) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("status", status);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.settingErrorConfig, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SetNewAvtivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SetNewAvtivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SetNewAvtivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        UserConfig.getInstance().getUser().setError_set(status);
                    } else {
                        ToastUtil.shortToast(SetNewAvtivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SetNewAvtivity.this.hideProgressDialog();
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.ErrorQuestionSetting;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    public void exitDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage("是否确定退出登录");
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12669c.lambda$exitDialog$19(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.tvNewVersion = (TextView) findViewById(R.id.tvNewVersion);
        this.mTvTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        TextView textView = (TextView) findViewById(R.id.changePhoneNum);
        TextView textView2 = (TextView) findViewById(R.id.tv_address);
        TextView textView3 = (TextView) findViewById(R.id.tv_frind_permission);
        TextView textView4 = (TextView) findViewById(R.id.btn_message_notice);
        View viewFindViewById = findViewById(R.id.view_new_version);
        this.mTvErrorSetValue = (TextView) findViewById(R.id.tv_wrong_question_set);
        Switch r7 = (Switch) findViewById(R.id.sw_cut_question_voice);
        this.swShowScreenPlat = (Switch) findViewById(R.id.sw_show_screen_plat);
        this.sw_error_question = (Switch) findViewById(R.id.sw_error_question);
        TextView textView5 = (TextView) findViewById(R.id.tv_download_set);
        TextView textView6 = (TextView) findViewById(R.id.tv_video_player_set);
        this.mTvTitle.setText("设置");
        this.mTvErrorSetValue.setText(UserConfig.getInstance().getUser().getError_set().equals("1") ? "做对移除" : "做对不移除");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_clear);
        TextView textView7 = (TextView) findViewById(R.id.clearQuestion);
        TextView textView8 = (TextView) findViewById(R.id.aboutView);
        TextView textView9 = (TextView) findViewById(R.id.outLogin);
        this.tv_app_clear = (TextView) findViewById(R.id.tv_app_clear);
        this.mTvDeepColor = (TextView) findViewById(R.id.tv_deepnight);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.ly_deepnight);
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11626c.lambda$init$0(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.tj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13956c.lambda$init$1(view);
            }
        });
        findViewById(R.id.tv_read_set).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.uj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13992c.lambda$init$2(view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14121c.lambda$init$4(view);
            }
        });
        try {
            this.tv_app_clear.setText("" + ClearData.getCacheSize(this));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        textView7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12248c.lambda$init$5(view);
            }
        });
        textView9.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ej
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12323c.lambda$init$7(view);
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12359c.lambda$init$8(view);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12447c.lambda$init$9(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12480c.lambda$init$10(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ij
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12514c.lambda$init$11(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.nj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13052c.lambda$init$12(view);
            }
        });
        boolean booleanConfig = SharePreferencesUtils.readBooleanConfig("haveNewVersion", false, this);
        viewFindViewById.setVisibility(8);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.NEW_VERSION_CODE, this, "");
        if (booleanConfig) {
            this.tvNewVersion.setVisibility(0);
            this.tvNewVersion.setText("发现新版本 V" + strConfig);
        } else {
            this.tvNewVersion.setVisibility(8);
        }
        r7.setChecked(SharePreferencesUtils.readBooleanConfig(CommonParameter.CUT_QUESTION_VOICE, true, this));
        r7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.oj
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                SetNewAvtivity.lambda$init$13(compoundButton, z2);
            }
        });
        this.swShowScreenPlat.setChecked(SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_SCREEN_PLAT_VIEW, false, this));
        this.swShowScreenPlat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.pj
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13546c.lambda$init$15(compoundButton, z2);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13738c.lambda$init$16(view);
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13801c.lambda$init$17(view);
            }
        });
        this.sw_error_question.setChecked(UserConfig.getInstance().getUser().getError_set().equals("1"));
        this.sw_error_question.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.sj
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13924c.lambda$init$18(compoundButton, z2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_theme), 0);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_theme_night), 0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            finish();
        } else if (str.equals("closePermission")) {
            this.swShowScreenPlat.setChecked(false);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            int i2 = grantResults[0];
            if (i2 != -1) {
                if (i2 == 0) {
                    this.swShowScreenPlat.setChecked(true);
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_SCREEN_PLAT_VIEW, true, this);
                    return;
                }
                return;
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
                this.swShowScreenPlat.setChecked(false);
            } else {
                ToastUtil.shortToast(this, "请检查app存储权限是否打开，否则会影响查看！");
                this.swShowScreenPlat.setChecked(false);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_BY_SYS_SKIN_MODE, false, this);
        TextView textView = this.mTvDeepColor;
        if (textView != null) {
            if (booleanConfig) {
                textView.setText("跟随系统");
            } else if (SkinManager.getCurrentSkinType(this) == 1) {
                this.mTvDeepColor.setText("已开启");
            } else {
                this.mTvDeepColor.setText("已关闭");
            }
        }
        if (this.mTvErrorSetValue == null || !UserConfig.isLogin()) {
            return;
        }
        if (TextUtils.isEmpty(UserConfig.getInstance().getUser().getError_set())) {
            UserConfig.getInstance().getUser().setError_set("2");
            UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
        }
        this.mTvErrorSetValue.setText(UserConfig.getInstance().getUser().getError_set().equals("1") ? "做对移除" : "做对不移除");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        setContentView(R.layout.layout_set_new_activity);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
