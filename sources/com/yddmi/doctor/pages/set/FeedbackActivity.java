package com.yddmi.doctor.pages.set;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwnerKt;
import com.catchpig.annotation.StatusBar;
import com.catchpig.annotation.Title;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.databinding.FeedbackActivityBinding;
import com.yddmi.doctor.pages.set.FeedbackActivity;
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
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \u000b2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/pages/set/FeedbackActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/FeedbackActivityBinding;", "Lcom/yddmi/doctor/pages/set/FeedbackViewModel;", "()V", "dealFeedBack", "", "initFlow", "initParam", "initView", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = false)
@Title
@SourceDebugExtension({"SMAP\nFeedbackActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FeedbackActivity.kt\ncom/yddmi/doctor/pages/set/FeedbackActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,84:1\n18#2,2:85\n1#3:87\n*S KotlinDebug\n*F\n+ 1 FeedbackActivity.kt\ncom/yddmi/doctor/pages/set/FeedbackActivity\n*L\n25#1:85,2\n25#1:87\n*E\n"})
/* loaded from: classes6.dex */
public final class FeedbackActivity extends BaseVMActivity<FeedbackActivityBinding, FeedbackViewModel> {

    @NotNull
    private static final String TAG = "FeedbackActivity";

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.FeedbackActivity$initFlow$1", f = "FeedbackActivity.kt", i = {}, l = {49}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.FeedbackActivity$initFlow$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return FeedbackActivity.this.new AnonymousClass1(continuation);
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
                MutableStateFlow<Long> feedbackMsf = FeedbackActivity.this.getViewModel().getFeedbackMsf();
                final FeedbackActivity feedbackActivity = FeedbackActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.FeedbackActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            feedbackActivity.hideLoading();
                            if (j2 > 200) {
                                Toaster.show(R.string.common_commit_success);
                                FeedbackActivity.access$getBodyBinding(feedbackActivity).et.setText("");
                                ImageView imageView = FeedbackActivity.access$getBodyBinding(feedbackActivity).imgv;
                                Intrinsics.checkNotNullExpressionValue(imageView, "bodyBinding.imgv");
                                ImageViewExtKt.load(imageView, "", (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                                GlobalAction.INSTANCE.setCapturePath("");
                            } else {
                                Toaster.show(R.string.common_commit_error);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (feedbackMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/FeedbackActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.set.FeedbackActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08671 extends Lambda implements Function1<FeedbackActivityBinding, Unit> {
        public C08671() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(FeedbackActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealFeedBack();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FeedbackActivityBinding feedbackActivityBinding) {
            invoke2(feedbackActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull FeedbackActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView imgv = bodyBinding.imgv;
            Intrinsics.checkNotNullExpressionValue(imgv, "imgv");
            ImageViewExtKt.load(imgv, GlobalAction.INSTANCE.getCapturePath(), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
            TextView sendTv = bodyBinding.sendTv;
            Intrinsics.checkNotNullExpressionValue(sendTv, "sendTv");
            final FeedbackActivity feedbackActivity = FeedbackActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FeedbackActivity.C08671.invoke$lambda$0(feedbackActivity, view);
                }
            }, 0L, 2, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ FeedbackActivityBinding access$getBodyBinding(FeedbackActivity feedbackActivity) {
        return (FeedbackActivityBinding) feedbackActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealFeedBack() {
        String string = StringsKt__StringsKt.trim((CharSequence) ((FeedbackActivityBinding) getBodyBinding()).et.getText().toString()).toString();
        if ((string.length() == 0) || string.length() < 5) {
            Toaster.show(R.string.me_feedback_tip2);
        } else {
            loadingDialog();
            FeedbackViewModel.httpPostReplySave$default(getViewModel(), string, GlobalAction.INSTANCE.getCapturePath(), null, null, 12, null);
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_white);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setType(intent.getIntExtra("type", 100));
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C08671());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
