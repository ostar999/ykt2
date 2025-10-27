package com.yddmi.doctor.pages.physical;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.PhysicalTipActivityBinding;
import com.yddmi.doctor.pages.physical.PhysicalTipActivity;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00112\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0014J\b\u0010\n\u001a\u00020\u0006H\u0014J\b\u0010\u000b\u001a\u00020\u0006H\u0014J\b\u0010\f\u001a\u00020\u0006H\u0014J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0002¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/pages/physical/PhysicalTipActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/PhysicalTipActivityBinding;", "Lcom/yddmi/doctor/pages/physical/PhysicalViewModel;", "()V", "initFlow", "", "initParam", "initView", "onDestroy", "onResume", "onStart", "onStop", "onWindowFocusChanged", "hasFocus", "", "viewSetImmersionBar", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(message = "已废")
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nPhysicalTipActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PhysicalTipActivity.kt\ncom/yddmi/doctor/pages/physical/PhysicalTipActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,96:1\n18#2,2:97\n1#3:99\n*S KotlinDebug\n*F\n+ 1 PhysicalTipActivity.kt\ncom/yddmi/doctor/pages/physical/PhysicalTipActivity\n*L\n84#1:97,2\n84#1:99\n*E\n"})
/* loaded from: classes6.dex */
public final class PhysicalTipActivity extends BaseVMActivity<PhysicalTipActivityBinding, PhysicalViewModel> {

    @NotNull
    private static final String TAG = "PhysicalTipActivity";

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/PhysicalTipActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalTipActivity$initView$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function1<PhysicalTipActivityBinding, Unit> {
        public AnonymousClass1() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(PhysicalTipActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(PhysicalTipActivityBinding physicalTipActivityBinding) {
            invoke2(physicalTipActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull PhysicalTipActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView iconImgv = bodyBinding.iconImgv;
            Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
            final PhysicalTipActivity physicalTipActivity = PhysicalTipActivity.this;
            ViewExtKt.setDebounceClickListener$default(iconImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.physical.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PhysicalTipActivity.AnonymousClass1.invoke$lambda$0(physicalTipActivity, view);
                }
            }, 0L, 2, null);
            ImageView tipImgv = bodyBinding.tipImgv;
            Intrinsics.checkNotNullExpressionValue(tipImgv, "tipImgv");
            ImageViewExtKt.load(tipImgv, "", (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : true, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        }
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

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        viewSetImmersionBar();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        updateTitleLine(false);
        updateStatusBarTransparent();
        bodyBinding(new AnonymousClass1());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        BusUtils.register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogExtKt.logd("焦点切换 onWindowFocusChanged " + hasFocus, TAG);
        if (!hasFocus || Build.VERSION.SDK_INT < 30) {
            return;
        }
        viewSetImmersionBar();
    }
}
