package com.yddmi.doctor.pages.heartlung;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.databinding.GarbledHeartlungActivityIconBinding;
import com.yddmi.doctor.pages.heartlung.HeartlungIconActivity;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00102\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0002¨\u0006\u0011"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungIconActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityIconBinding;", "Lcom/yddmi/doctor/pages/heartlung/HeartlungViewModel;", "()V", "dealIconClick", "", "code", "", "initFlow", "initParam", "initView", "onWindowFocusChanged", "hasFocus", "", "viewSetImmersionBar", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nHeartlungIconActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungIconActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungIconActivity\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n+ 3 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,101:1\n15#2,3:102\n18#3,2:105\n1#4:107\n*S KotlinDebug\n*F\n+ 1 HeartlungIconActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungIconActivity\n*L\n82#1:102,3\n91#1:105,2\n91#1:107\n*E\n"})
/* loaded from: classes6.dex */
public final class HeartlungIconActivity extends BaseVMActivity<GarbledHeartlungActivityIconBinding, HeartlungViewModel> {

    @NotNull
    private static final String TAG = "HeartlungIconActivity";

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungIconActivity$initFlow$1", f = "HeartlungIconActivity.kt", i = {}, l = {57}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungIconActivity$initFlow$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungIconActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataChangeMsf = HeartlungIconActivity.this.getViewModel().getDataChangeMsf();
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungIconActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityIconBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungIconActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06791 extends Lambda implements Function1<GarbledHeartlungActivityIconBinding, Unit> {
        public C06791() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealIconClick(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealIconClick(2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealIconClick(3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealIconClick(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(HeartlungIconActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealIconClick(5);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityIconBinding garbledHeartlungActivityIconBinding) {
            invoke2(garbledHeartlungActivityIconBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungActivityIconBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final HeartlungIconActivity heartlungIconActivity = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$0(heartlungIconActivity, view);
                }
            }, 0L, 2, null);
            ConstraintLayout icon1Cl = bodyBinding.icon1Cl;
            Intrinsics.checkNotNullExpressionValue(icon1Cl, "icon1Cl");
            final HeartlungIconActivity heartlungIconActivity2 = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(icon1Cl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.x
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$1(heartlungIconActivity2, view);
                }
            }, 0L, 2, null);
            ConstraintLayout icon2Cl = bodyBinding.icon2Cl;
            Intrinsics.checkNotNullExpressionValue(icon2Cl, "icon2Cl");
            final HeartlungIconActivity heartlungIconActivity3 = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(icon2Cl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.y
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$2(heartlungIconActivity3, view);
                }
            }, 0L, 2, null);
            ConstraintLayout icon3Cl = bodyBinding.icon3Cl;
            Intrinsics.checkNotNullExpressionValue(icon3Cl, "icon3Cl");
            final HeartlungIconActivity heartlungIconActivity4 = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(icon3Cl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.z
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$3(heartlungIconActivity4, view);
                }
            }, 0L, 2, null);
            ConstraintLayout icon4Cl = bodyBinding.icon4Cl;
            Intrinsics.checkNotNullExpressionValue(icon4Cl, "icon4Cl");
            final HeartlungIconActivity heartlungIconActivity5 = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(icon4Cl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$4(heartlungIconActivity5, view);
                }
            }, 0L, 2, null);
            ConstraintLayout icon5Cl = bodyBinding.icon5Cl;
            Intrinsics.checkNotNullExpressionValue(icon5Cl, "icon5Cl");
            final HeartlungIconActivity heartlungIconActivity6 = HeartlungIconActivity.this;
            ViewExtKt.setDebounceClickListener$default(icon5Cl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.b0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungIconActivity.C06791.invoke$lambda$5(heartlungIconActivity6, view);
                }
            }, 0L, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealIconClick(int code) {
        if (!YddConfig.INSTANCE.getConfig(YddConfig.ConfigJtjyZydyz)) {
            Toaster.show((CharSequence) "暂无开放，请稍后再试");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("code", code);
        intent.setClass(this, HeartlungActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealIconClick$default(HeartlungIconActivity heartlungIconActivity, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        heartlungIconActivity.dealIconClick(i2);
    }

    private final void viewSetImmersionBar() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.statusBarDarkFont(false);
        immersionBarWith.navigationBarDarkIcon(false);
        immersionBarWith.init();
        immersionBarWith.init();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        viewSetImmersionBar();
        getViewModel().httpGetConfigWhite();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06791());
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
