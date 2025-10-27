package com.yddmi.doctor.pages.u3d;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.core.app.NotificationCompat;
import com.catchpig.mvvm.manager.KTActivityManager;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\u001a\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u001a\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001b\u001a\u00020\u0011H\u0014J\u0006\u0010\u001c\u001a\u00020\u0011J\u0006\u0010\u001d\u001a\u00020\u0011J\u0010\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\bH\u0016J\b\u0010 \u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/pages/u3d/U3dGoActivity;", "Lcom/unity3d/player/UnityPlayerActivity;", "()V", "businessId", "", "downTime", "", "goFromExam", "", "isTask", "mCurrentProject", "mIsExam", "mIsPlay", "mSkillType", "mTypeId", "mUrls", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onKeyDown", "keyCode", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "onKeyUp", "onResume", "onUnityExit", "onUnityInit", "onWindowFocusChanged", "hasFocus", "viewSetImmersionBar", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nU3dGoActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 U3dGoActivity.kt\ncom/yddmi/doctor/pages/u3d/U3dGoActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,171:1\n1864#2,3:172\n1864#2,3:175\n18#3,2:178\n1#4:180\n*S KotlinDebug\n*F\n+ 1 U3dGoActivity.kt\ncom/yddmi/doctor/pages/u3d/U3dGoActivity\n*L\n112#1:172,3\n118#1:175,3\n159#1:178,2\n159#1:180\n*E\n"})
/* loaded from: classes6.dex */
public final class U3dGoActivity extends UnityPlayerActivity {

    @NotNull
    private static final String TAG = "U3dGoActivity";
    private int downTime;
    private boolean goFromExam;
    private boolean isTask;
    private boolean mIsExam;
    private boolean mIsPlay;
    private int mSkillType;

    @Nullable
    private String mTypeId = "";

    @Nullable
    private String mUrls = "";
    private int mCurrentProject = 2;

    @NotNull
    private String businessId = "";

    private final void viewSetImmersionBar() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_transparent);
        immersionBarWith.init();
        immersionBarWith.init();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        LogExtKt.logd("onBackPressed 返回点击", TAG);
        onUnityExit();
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewSetImmersionBar();
        KTActivityManager.INSTANCE.getInstance().addActivity(this);
        this.mTypeId = getIntent().getStringExtra("typeId");
        this.mSkillType = getIntent().getIntExtra("skillType", 0);
        this.mUrls = getIntent().getStringExtra("url");
        this.mIsExam = getIntent().getBooleanExtra("isExam", false);
        this.mIsPlay = getIntent().getBooleanExtra("isPlay", false);
        this.mCurrentProject = getIntent().getIntExtra("CurrentProject", 2);
        this.isTask = getIntent().getBooleanExtra("isTask", false);
        this.goFromExam = getIntent().getBooleanExtra("goFromExam", false);
        String stringExtra = getIntent().getStringExtra("businessId");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.businessId = stringExtra;
        this.downTime = getIntent().getIntExtra("downTime", 0);
        LogExtKt.logd("U3dGoActivity 传参数据：" + this.mTypeId + " -- " + this.mUrls + " -- " + this.mCurrentProject, TAG);
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    public void onDestroy() throws InterruptedException {
        super.onDestroy();
        KTActivityManager.INSTANCE.getInstance().removeActivity(this);
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, @Nullable KeyEvent event) {
        LogExtKt.logd("onKeyUp " + keyCode, TAG);
        if (keyCode == 4) {
            onUnityExit();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    public final void onUnityExit() {
        LogExtKt.logd("onUnityExit() ", TAG);
        UnityPlayer.UnitySendMessage("MobileCallUnity", "AppSendExitUnity", "");
        finish();
    }

    public final void onUnityInit() throws JSONException {
        LogExtKt.logd("onUnityInit() ", TAG);
        JSONObject jSONObject = new JSONObject();
        YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
        jSONObject.put("url", companion.getInstance().getHostBaseUrl());
        jSONObject.put("currentHostType", companion.getInstance().getCurrentHostNumber());
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        if (this.mIsExam) {
            String str = this.mTypeId;
            List listSplit$default = str != null ? StringsKt__StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null) : null;
            int i2 = 0;
            if (listSplit$default != null) {
                int i3 = 0;
                for (Object obj : listSplit$default) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    jSONArray.put((String) obj);
                    i3 = i4;
                }
            }
            jSONObject.put("typeId", jSONArray);
            String str2 = this.mUrls;
            List listSplit$default2 = str2 != null ? StringsKt__StringsKt.split$default((CharSequence) str2, new String[]{","}, false, 0, 6, (Object) null) : null;
            if (listSplit$default2 != null) {
                for (Object obj2 : listSplit$default2) {
                    int i5 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    jSONArray2.put((String) obj2);
                    i2 = i5;
                }
            }
            jSONObject.put("childProjectName", jSONArray2);
        } else {
            jSONArray.put(this.mTypeId);
            jSONObject.put("typeId", jSONArray);
            jSONArray2.put(this.mUrls);
            jSONObject.put("childProjectName", jSONArray2);
        }
        jSONObject.put("isExam", this.mIsExam);
        YddUserManager.Companion companion2 = YddUserManager.INSTANCE;
        jSONObject.put("token", companion2.getInstance().userToken());
        jSONObject.put("userId", companion2.getInstance().userId());
        jSONObject.put("os", "android");
        jSONObject.put("currentProject", this.mCurrentProject);
        jSONObject.put("type", this.mSkillType);
        jSONObject.put("recordType", 2);
        jSONObject.put("platformType", 1);
        jSONObject.put("isPlay", this.mIsPlay);
        jSONObject.put("isTask", this.isTask);
        jSONObject.put("businessId", this.businessId);
        jSONObject.put("downTime", this.downTime);
        LogExtKt.logd("u3d参数 初始化完成传参 " + jSONObject, TAG);
        UnityPlayer.UnitySendMessage("MobileCallUnity", "AppSendData", jSONObject.toString());
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus || Build.VERSION.SDK_INT < 30) {
            return;
        }
        viewSetImmersionBar();
    }
}
