package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwnerKt;
import cn.hutool.core.text.StrPool;
import com.al.open.OnInputListener;
import com.al.open.SplitEditTextView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.LoginActivityWxBinding;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yddmi.doctor.pages.login.LoginWxActivity;
import com.yddmi.doctor.pages.login.PopupBottomAgreement;
import com.yddmi.doctor.pages.web.WebActivity;
import com.yikaobang.yixue.R2;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
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

@Metadata(d1 = {"\u0000?\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e*\u0001\b\b\u0007\u0018\u0000 !2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\u0018\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\u000bH\u0002J\u0010\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000fH\u0002J\b\u0010\u001a\u001a\u00020\u000bH\u0016J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\b\u0010\u001c\u001a\u00020\u000bH\u0016J\b\u0010\u001d\u001a\u00020\u000bH\u0016J\b\u0010\u001e\u001a\u00020\u000bH\u0014J\b\u0010\u001f\u001a\u00020\u000bH\u0016J\b\u0010 \u001a\u00020\u000bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginWxActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityWxBinding;", "Lcom/yddmi/doctor/pages/login/LoginWxViewModel;", "()V", "hostFastClickNum", "", "mHandler", "com/yddmi/doctor/pages/login/LoginWxActivity$mHandler$1", "Lcom/yddmi/doctor/pages/login/LoginWxActivity$mHandler$1;", "dealBack", "", "dealCanLogin", "dealGo", "data", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "meProfile", "Lcom/yddmi/doctor/entity/result/MeProfile;", "dealPhoneNumber", "str", "", "httpDealGo", "code", "httpDealSendCode", "httpGetPersonInfo", "loginResult", "initFlow", "initParam", "initView", "onBackPressed", "onDestroy", "screenOrientation", "viewShowAgreementPop", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = true)
@SourceDebugExtension({"SMAP\nLoginWxActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginWxActivity.kt\ncom/yddmi/doctor/pages/login/LoginWxActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,322:1\n18#2,2:323\n1#3:325\n11#4,7:326\n*S KotlinDebug\n*F\n+ 1 LoginWxActivity.kt\ncom/yddmi/doctor/pages/login/LoginWxActivity\n*L\n74#1:323,2\n74#1:325\n279#1:326,7\n*E\n"})
/* loaded from: classes6.dex */
public final class LoginWxActivity extends BaseVMActivity<LoginActivityWxBinding, LoginWxViewModel> {

    @NotNull
    private static final String TAG = "LoginActivity";
    private int hostFastClickNum;

    @NotNull
    private final LoginWxActivity$mHandler$1 mHandler;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginWxActivity$initFlow$1", f = "LoginWxActivity.kt", i = {}, l = {158}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.login.LoginWxActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07651 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07651(Continuation<? super C07651> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return LoginWxActivity.this.new C07651(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07651) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Integer> timeCost = LoginWxActivity.this.getViewModel().getTimeCost();
                final LoginWxActivity loginWxActivity = LoginWxActivity.this;
                FlowCollector<? super Integer> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).intValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(int i3, @NotNull Continuation<? super Unit> continuation) {
                        if (i3 == 0) {
                            LoginWxActivity.access$getBodyBinding(loginWxActivity).sendNumTv.setText(loginWxActivity.getString(R.string.login_send_num_again));
                        } else {
                            LoginWxActivity.access$getBodyBinding(loginWxActivity).sendNumTv.setText(i3 + loginWxActivity.getString(R.string.login_again));
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (timeCost.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginWxActivity$initFlow$2", f = "LoginWxActivity.kt", i = {}, l = {167}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.login.LoginWxActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C07662 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07662(Continuation<? super C07662> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return LoginWxActivity.this.new C07662(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07662) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> wxLoginMsf = LoginWxActivity.this.getViewModel().getWxLoginMsf();
                final LoginWxActivity loginWxActivity = LoginWxActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            if (100 == j2 || loginWxActivity.getViewModel().getLoginResult() == null) {
                                Toaster.show(R.string.login_binding_error1);
                            } else {
                                YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), loginWxActivity.getViewModel().getLoginResult(), null, 2, null);
                                LoginWxActivity loginWxActivity2 = loginWxActivity;
                                AuthLoginResult loginResult = loginWxActivity2.getViewModel().getLoginResult();
                                Intrinsics.checkNotNull(loginResult);
                                loginWxActivity2.httpGetPersonInfo(loginResult);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (wxLoginMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityWxBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nLoginWxActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginWxActivity.kt\ncom/yddmi/doctor/pages/login/LoginWxActivity$initView$1\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n*L\n1#1,322:1\n65#2,16:323\n93#2,3:339\n*S KotlinDebug\n*F\n+ 1 LoginWxActivity.kt\ncom/yddmi/doctor/pages/login/LoginWxActivity$initView$1\n*L\n92#1:323,16\n92#1:339,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.login.LoginWxActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07671 extends Lambda implements Function1<LoginActivityWxBinding, Unit> {
        public C07671() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(LoginWxActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealBack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(LoginActivityWxBinding this_bodyBinding, LoginWxActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.phoneEt.setText("");
            this$0.dealCanLogin();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(LoginWxActivity this$0, LoginActivityWxBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            if (!this$0.getViewModel().getMSelected()) {
                this$0.viewShowAgreementPop();
                return;
            }
            this_bodyBinding.set.setText("");
            this$0.httpDealSendCode();
            String string = StringsKt__StringsKt.trim((CharSequence) LoginWxActivity.access$getBodyBinding(this$0).phoneEt.getText().toString()).toString();
            this_bodyBinding.codeTipTv.setText(this$0.getString(R.string.login_send2_num) + "+86 " + string);
            this_bodyBinding.codeCl.setVisibility(0);
            KeyboardUtils.showSoftInput(this_bodyBinding.set);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(LoginWxActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpDealSendCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(LoginWxActivity this$0, LoginActivityWxBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            if (this$0.getViewModel().getMSelected()) {
                this_bodyBinding.selectImgv.setImageResource(R.drawable.common_unselect);
            } else {
                this_bodyBinding.selectImgv.setImageResource(R.drawable.common_select1);
            }
            this$0.getViewModel().setMSelected(!this$0.getViewModel().getMSelected());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$6(View view, MotionEvent motionEvent) {
            return true;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityWxBinding loginActivityWxBinding) {
            invoke2(loginActivityWxBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final LoginActivityWxBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final LoginWxActivity loginWxActivity = LoginWxActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.c0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginWxActivity.C07671.invoke$lambda$0(loginWxActivity, view);
                }
            });
            AppCompatImageView appCompatImageView2 = bodyBinding.xImgv;
            final LoginWxActivity loginWxActivity2 = LoginWxActivity.this;
            appCompatImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.d0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginWxActivity.C07671.invoke$lambda$1(bodyBinding, loginWxActivity2, view);
                }
            });
            EditText phoneEt = bodyBinding.phoneEt;
            Intrinsics.checkNotNullExpressionValue(phoneEt, "phoneEt");
            final LoginWxActivity loginWxActivity3 = LoginWxActivity.this;
            phoneEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity$initView$1$invoke$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) {
                    loginWxActivity3.dealCanLogin();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            TextView sendNumG0Tv = bodyBinding.sendNumG0Tv;
            Intrinsics.checkNotNullExpressionValue(sendNumG0Tv, "sendNumG0Tv");
            final LoginWxActivity loginWxActivity4 = LoginWxActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumG0Tv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.e0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginWxActivity.C07671.invoke$lambda$3(loginWxActivity4, bodyBinding, view);
                }
            }, 0L, 2, null);
            TextView sendNumTv = bodyBinding.sendNumTv;
            Intrinsics.checkNotNullExpressionValue(sendNumTv, "sendNumTv");
            final LoginWxActivity loginWxActivity5 = LoginWxActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.f0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginWxActivity.C07671.invoke$lambda$4(loginWxActivity5, view);
                }
            }, 0L, 2, null);
            LoginWxActivity.this.dealCanLogin();
            AppCompatImageView selectImgv = bodyBinding.selectImgv;
            Intrinsics.checkNotNullExpressionValue(selectImgv, "selectImgv");
            final LoginWxActivity loginWxActivity6 = LoginWxActivity.this;
            ViewExtKt.setDebounceClickListener$default(selectImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.g0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginWxActivity.C07671.invoke$lambda$5(loginWxActivity6, bodyBinding, view);
                }
            }, 0L, 2, null);
            bodyBinding.tipTv.setText(LoginWxActivity.this.getString(R.string.login_tip1));
            TextView tipTv = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv, "tipTv");
            String string = LoginWxActivity.this.getString(R.string.startup_tip1);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.startup_tip1)");
            LoginWxActivity loginWxActivity7 = LoginWxActivity.this;
            int i2 = R.color.c_3776ff;
            int colorM = ContextExtKt.getColorM(loginWxActivity7, i2);
            final LoginWxActivity loginWxActivity8 = LoginWxActivity.this;
            SpanExtKt.appendClickSpan(tipTv, string, colorM, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.initView.1.7
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    WebActivity.INSTANCE.startWebActivity(loginWxActivity8, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : loginWxActivity8.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            }).append(StrPool.TAB);
            TextView tipTv2 = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv2, "tipTv");
            String string2 = LoginWxActivity.this.getString(R.string.startup_tip3);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.startup_tip3)");
            int colorM2 = ContextExtKt.getColorM(LoginWxActivity.this, i2);
            final LoginWxActivity loginWxActivity9 = LoginWxActivity.this;
            SpanExtKt.appendClickSpan(tipTv2, string2, colorM2, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.initView.1.8
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    WebActivity.INSTANCE.startWebActivity(loginWxActivity9, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : loginWxActivity9.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            });
            bodyBinding.codeCl.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.login.h0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return LoginWxActivity.C07671.invoke$lambda$6(view, motionEvent);
                }
            });
            SplitEditTextView splitEditTextView = bodyBinding.set;
            final LoginWxActivity loginWxActivity10 = LoginWxActivity.this;
            splitEditTextView.setOnInputListener(new OnInputListener() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.initView.1.10
                @Override // com.al.open.OnInputListener
                public void onInputFinished(@Nullable String content) {
                    if (content != null) {
                        loginWxActivity10.httpDealGo(content);
                    }
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.login.LoginWxActivity$mHandler$1] */
    public LoginWxActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.login.LoginWxActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 100) {
                    this.this$0.hostFastClickNum = 0;
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ LoginActivityWxBinding access$getBodyBinding(LoginWxActivity loginWxActivity) {
        return (LoginActivityWxBinding) loginWxActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealBack() {
        if (((LoginActivityWxBinding) getBodyBinding()).codeCl.getVisibility() != 0) {
            closeActivity();
        } else {
            KeyboardUtils.hideSoftInput(((LoginActivityWxBinding) getBodyBinding()).set);
            ((LoginActivityWxBinding) getBodyBinding()).codeCl.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dealCanLogin() {
        /*
            r5 = this;
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r0 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r0
            android.widget.EditText r0 = r0.phoneEt
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.CharSequence r0 = kotlin.text.StringsKt.trim(r0)
            java.lang.String r0 = r0.toString()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L25
            int r3 = r0.length()
            if (r3 != 0) goto L23
            goto L25
        L23:
            r3 = r2
            goto L26
        L25:
            r3 = r1
        L26:
            if (r3 == 0) goto L35
            androidx.viewbinding.ViewBinding r3 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r3 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r3
            androidx.appcompat.widget.AppCompatImageView r3 = r3.xImgv
            r4 = 4
            r3.setVisibility(r4)
            goto L4a
        L35:
            androidx.viewbinding.ViewBinding r3 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r3 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r3
            androidx.appcompat.widget.AppCompatImageView r3 = r3.xImgv
            r3.setVisibility(r2)
            int r3 = r0.length()
            r4 = 11
            if (r3 != r4) goto L4a
            r3 = r1
            goto L4b
        L4a:
            r3 = r2
        L4b:
            r5.dealPhoneNumber(r0)
            if (r3 == 0) goto L5c
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r0 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r0
            android.widget.TextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r1)
            goto L67
        L5c:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r0 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r0
            android.widget.TextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r2)
        L67:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityWxBinding r0 = (com.yddmi.doctor.databinding.LoginActivityWxBinding) r0
            android.widget.TextView r0 = r0.sendNumG0Tv
            java.lang.String r1 = "bodyBinding.sendNumG0Tv"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            com.yddmi.doctor.utils.OtherUtils r1 = com.yddmi.doctor.utils.OtherUtils.INSTANCE
            r2 = 12
            int r2 = com.catchpig.utils.ext.CommonExtKt.dp2px(r5, r2)
            float r2 = (float) r2
            android.graphics.drawable.Drawable r1 = r1.getGoDrawable(r3, r2)
            com.catchpig.mvvm.ext.ViewExtKt.setBackgroundJellyBean16(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginWxActivity.dealCanLogin():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGo(AuthLoginResult data, MeProfile meProfile) {
        BusUtils.post(GlobalAction.eventLogInClose);
        YddUserManager.INSTANCE.getInstance().userInfoSave(data, meProfile);
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        GlobalAction.INSTANCE.setHomeDataRefresh(true);
        closeActivity();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void dealPhoneNumber(String str) {
        if (str == null || str.length() == 0) {
            ((LoginActivityWxBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            ((LoginActivityWxBinding) getBodyBinding()).sendNumTv.setEnabled(false);
        } else {
            if (StringExtKt.isPhoneNumber(str)) {
                ((LoginActivityWxBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_3776ff));
            } else {
                ((LoginActivityWxBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            }
            ((LoginActivityWxBinding) getBodyBinding()).sendNumTv.setEnabled(StringExtKt.isPhoneNumber(str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealGo(String code) {
        KeyboardUtils.hideSoftInput(this);
        getViewModel().httpWxLogin(StringsKt__StringsKt.trim((CharSequence) ((LoginActivityWxBinding) getBodyBinding()).phoneEt.getText().toString()).toString(), code);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealSendCode() {
        if (60 == getViewModel().getTimeCost().getValue().intValue() || getViewModel().getTimeCost().getValue().intValue() == 0) {
            String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityWxBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
            if (StringExtKt.isPhoneNumber(string)) {
                FlowExtKt.lifecycleLoadingDialog(getViewModel().getPushCodeRegister(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.httpDealSendCode.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull Throwable it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        String message = it.getMessage();
                        if (message != null) {
                            Toaster.show((CharSequence) message);
                        }
                    }
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.httpDealSendCode.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@Nullable String str) {
                        LoginWxActivity.this.getViewModel().dealTimeGo();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo(final AuthLoginResult loginResult) {
        FlowExtKt.lifecycle(getViewModel().getPersonInfo(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.httpGetPersonInfo.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message != null) {
                    Toaster.show((CharSequence) message);
                }
            }
        }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.httpGetPersonInfo.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeProfile meProfile) {
                invoke2(meProfile);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable MeProfile meProfile) {
                if (meProfile != null) {
                    LoginWxActivity.this.dealGo(loginResult, meProfile);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowAgreementPop() {
        final PopupBottomAgreement popupBottomAgreement = new PopupBottomAgreement(this);
        popupBottomAgreement.setOnPopupAgreementClickListener(new PopupBottomAgreement.OnPopupLoginAgreementClickListener() { // from class: com.yddmi.doctor.pages.login.LoginWxActivity.viewShowAgreementPop.1
            @Override // com.yddmi.doctor.pages.login.PopupBottomAgreement.OnPopupLoginAgreementClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                int id = view.getId();
                if (id == R.id.f25733tv) {
                    WebActivity.INSTANCE.startWebActivity(LoginWxActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginWxActivity.this.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                    return;
                }
                if (id == R.id.tv1) {
                    WebActivity.INSTANCE.startWebActivity(LoginWxActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginWxActivity.this.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                } else if (id == R.id.goTv) {
                    LoginWxActivity.this.getViewModel().setMSelected(true);
                    LoginWxActivity.access$getBodyBinding(LoginWxActivity.this).selectImgv.setImageResource(R.drawable.common_select1);
                    popupBottomAgreement.dismiss();
                }
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupBottomAgreement).show();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07651(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07662(null), 3, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.statusBarColor(R.color.color_transparent);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_white);
        immersionBarWith.init();
        immersionBarWith.init();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C07671());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        dealBack();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getViewModel().setMSelected(false);
        removeCallbacksAndMessages(null);
        KeyboardUtils.hideSoftInput(this);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
