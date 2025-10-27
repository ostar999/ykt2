package com.yddmi.doctor.pages.u3d;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import androidx.core.app.NotificationCompat;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.ActivityU3dBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000;\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0005\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\bH\u0016J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\bH\u0014J\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u001a\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0015\u001a\u00020\bH\u0014J\b\u0010\u0016\u001a\u00020\bH\u0002R\u0010\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0018"}, d2 = {"Lcom/yddmi/doctor/pages/u3d/U3dActivity;", "Lcom/catchpig/mvvm/base/activity/BaseActivity;", "Lcom/yddmi/doctor/databinding/ActivityU3dBinding;", "()V", "mHandler", "com/yddmi/doctor/pages/u3d/U3dActivity$mHandler$1", "Lcom/yddmi/doctor/pages/u3d/U3dActivity$mHandler$1;", "dealFinish", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onKeyDown", "", "keyCode", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "onKeyUp", "onResume", "viewSetImmersionBar", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nU3dActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 U3dActivity.kt\ncom/yddmi/doctor/pages/u3d/U3dActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,90:1\n18#2,2:91\n1#3:93\n*S KotlinDebug\n*F\n+ 1 U3dActivity.kt\ncom/yddmi/doctor/pages/u3d/U3dActivity\n*L\n76#1:91,2\n76#1:93\n*E\n"})
/* loaded from: classes6.dex */
public final class U3dActivity extends BaseActivity<ActivityU3dBinding> {

    @NotNull
    private static final String TAG = "U3dActivity";

    @NotNull
    private final U3dActivity$mHandler$1 mHandler;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.u3d.U3dActivity$mHandler$1] */
    public U3dActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.u3d.U3dActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 100) {
                    this.this$0.dealFinish();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealFinish() {
        closeActivity();
    }

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

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        LogExtKt.logd("onBackPressed 返回点击", TAG);
        dealFinish();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, InstantiationException {
        super.onCreate(savedInstanceState);
        viewSetImmersionBar();
        removeMessages(100);
        sendEmptyMessageDelayed(100, 1200L);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        removeCallbacksAndMessages(null);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, @Nullable KeyEvent event) {
        LogExtKt.logd("onKeyUp " + keyCode, TAG);
        if (keyCode == 4) {
            dealFinish();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }
}
