package com.psychiatrygarden.activity;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\fH\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/activity/DarkModeActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "isChooseDayMode", "", "isChooseFollowBySystem", "mImgDarkMode", "Landroid/widget/ImageView;", "mImgNormalMode", "rl_follow_system", "Landroid/widget/RelativeLayout;", "init", "", "onEventMainThread", "str", "", "setCheckStatus", "uiMode", "", "uiModeManager", "Landroid/app/UiModeManager;", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DarkModeActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private boolean isChooseDayMode = true;
    private boolean isChooseFollowBySystem;
    private ImageView mImgDarkMode;
    private ImageView mImgNormalMode;
    private RelativeLayout rl_follow_system;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/DarkModeActivity$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) DarkModeActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(DarkModeActivity this$0, LinearLayout mLyView, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(mLyView, "$mLyView");
        this$0.isChooseFollowBySystem = z2;
        mLyView.setVisibility(z2 ? 8 : 0);
        this$0.isChooseDayMode = SkinManager.getCurrentSkinType(this$0) != 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(DarkModeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isChooseDayMode = true;
        ImageView imageView = this$0.mImgNormalMode;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
            imageView = null;
        }
        imageView.setVisibility(0);
        ImageView imageView3 = this$0.mImgDarkMode;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
        } else {
            imageView2 = imageView3;
        }
        imageView2.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$2(DarkModeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isChooseDayMode = false;
        ImageView imageView = this$0.mImgNormalMode;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
            imageView = null;
        }
        imageView.setVisibility(8);
        ImageView imageView3 = this$0.mImgDarkMode;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
        } else {
            imageView2 = imageView3;
        }
        imageView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$3(DarkModeActivity this$0, UiModeManager uiModeManager, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(uiModeManager, "$uiModeManager");
        LogUtils.e("app_color_mode", "当前模式勾选：" + this$0.isChooseDayMode + ";是否勾选跟随系统：" + this$0.isChooseFollowBySystem);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_BY_SYS_SKIN_MODE, this$0.isChooseFollowBySystem, this$0);
        if (this$0.isChooseFollowBySystem) {
            if (Build.VERSION.SDK_INT >= 24) {
                this$0.setCheckStatus(this$0.getResources().getConfiguration().uiMode, uiModeManager);
            } else {
                SkinManager.changeSkin(this$0, SkinManager.getCurrentSkinType(this$0));
            }
            this$0.finish();
        } else {
            LogUtils.e("app_color_mode", "更改当前主题为：" + SkinManager.getCurrentSkinType(this$0));
            if (this$0.isChooseDayMode) {
                if (SkinManager.getCurrentSkinType(this$0) == 1) {
                    LogUtils.e("app_color_mode", "更改当前主题为正常模式");
                    uiModeManager.setNightMode(1);
                    SkinManager.changeSkin(this$0, 0);
                } else {
                    this$0.finish();
                }
            } else if (SkinManager.getCurrentSkinType(this$0) == 0) {
                LogUtils.e("app_color_mode", "更改当前主题为暗黑模式");
                uiModeManager.setNightMode(2);
                SkinManager.changeSkin(this$0, 1);
            } else {
                this$0.finish();
            }
        }
        EventBus.getDefault().post("UPDATE_MODE_SWITCH");
    }

    private final void setCheckStatus(int uiMode, UiModeManager uiModeManager) {
        if ((uiMode & 48) != 32 && uiModeManager.getNightMode() != 2) {
            uiModeManager.setNightMode(1);
            if (SkinManager.getCurrentSkinType(this) != 0) {
                SkinManager.changeSkin(this, 0);
                return;
            }
            return;
        }
        uiModeManager.setNightMode(2);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 1, this);
            SkinManager.changeSkin(this, 1);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("夜间模式");
        this.mTvActionbarRight.setVisibility(0);
        this.mTvActionbarRight.setText("完成");
        this.mBtnActionbarRight.setVisibility(8);
        View viewFindViewById = findViewById(R.id.rl_follow_system);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rl_follow_system)");
        this.rl_follow_system = (RelativeLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.ly_normal_mode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.ly_normal_mode)");
        LinearLayout linearLayout = (LinearLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.ly_dark_mode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ly_dark_mode)");
        LinearLayout linearLayout2 = (LinearLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.img_normal_mode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.img_normal_mode)");
        this.mImgNormalMode = (ImageView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.img_dark_mode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.img_dark_mode)");
        this.mImgDarkMode = (ImageView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.switch_darkmode);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.switch_darkmode)");
        Switch r4 = (Switch) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ly_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.ly_view)");
        final LinearLayout linearLayout3 = (LinearLayout) viewFindViewById7;
        Object systemService = getSystemService("uimode");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.UiModeManager");
        final UiModeManager uiModeManager = (UiModeManager) systemService;
        int i2 = getResources().getConfiguration().uiMode;
        boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_BY_SYS_SKIN_MODE, false, this);
        r4.setChecked(booleanConfig);
        this.isChooseFollowBySystem = booleanConfig;
        RelativeLayout relativeLayout = null;
        if (booleanConfig) {
            linearLayout3.setVisibility(8);
            if ((i2 & 48) == 32 || uiModeManager.getNightMode() == 2) {
                SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 1, this);
                ImageView imageView = this.mImgNormalMode;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
                    imageView = null;
                }
                imageView.setVisibility(8);
                ImageView imageView2 = this.mImgDarkMode;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
                    imageView2 = null;
                }
                imageView2.setVisibility(0);
            } else {
                SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 0, this);
                ImageView imageView3 = this.mImgNormalMode;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
                    imageView3 = null;
                }
                imageView3.setVisibility(0);
                ImageView imageView4 = this.mImgDarkMode;
                if (imageView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
                    imageView4 = null;
                }
                imageView4.setVisibility(8);
            }
        } else {
            linearLayout3.setVisibility(0);
            if (SkinManager.getCurrentSkinType(this) == 1) {
                this.isChooseDayMode = false;
                ImageView imageView5 = this.mImgNormalMode;
                if (imageView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
                    imageView5 = null;
                }
                imageView5.setVisibility(8);
                ImageView imageView6 = this.mImgDarkMode;
                if (imageView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
                    imageView6 = null;
                }
                imageView6.setVisibility(0);
            } else {
                this.isChooseDayMode = true;
                ImageView imageView7 = this.mImgNormalMode;
                if (imageView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgNormalMode");
                    imageView7 = null;
                }
                imageView7.setVisibility(0);
                ImageView imageView8 = this.mImgDarkMode;
                if (imageView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mImgDarkMode");
                    imageView8 = null;
                }
                imageView8.setVisibility(8);
            }
        }
        RelativeLayout relativeLayout2 = this.rl_follow_system;
        if (relativeLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rl_follow_system");
        } else {
            relativeLayout = relativeLayout2;
        }
        relativeLayout.setVisibility(Build.VERSION.SDK_INT < 24 ? 8 : 0);
        LogUtils.e("app_color_mode", "当前模式勾选：" + this.isChooseDayMode + ";是否勾选跟随系统：" + this.isChooseFollowBySystem);
        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.u8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                DarkModeActivity.init$lambda$0(this.f13979c, linearLayout3, compoundButton, z2);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DarkModeActivity.init$lambda$1(this.f14015c, view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.w8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DarkModeActivity.init$lambda$2(this.f14141c, view);
            }
        });
        this.mTvActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.x8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DarkModeActivity.init$lambda$3(this.f14174c, uiModeManager, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_dark_mode);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
