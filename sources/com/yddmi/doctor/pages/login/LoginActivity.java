package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import cn.hutool.core.text.StrPool;
import com.al.open.OnInputListener;
import com.al.open.SplitEditTextView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.mobile.auth.gatewayauth.AuthRegisterXmlConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.ui.AbstractPnsViewDelegate;
import com.noober.background.view.BLTextView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.open.SocialConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.LoginActivityBinding;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yddmi.doctor.pages.login.LoginActivity;
import com.yddmi.doctor.pages.login.PopupBottomAgreement;
import com.yddmi.doctor.pages.login.PopupNotice;
import com.yddmi.doctor.pages.web.WebActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.OtherUtils;
import com.yddmi.doctor.utils.PermissionInterceptor;
import com.yikaobang.yixue.R2;
import java.util.LinkedHashMap;
import java.util.List;
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

@Metadata(d1 = {"\u0000_\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u001d*\u0001\u000f\b\u0007\u0018\u0000 ?2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001?B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0006H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\u0018\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u0016H\u0002J\b\u0010 \u001a\u00020\u0016H\u0002J\u0012\u0010!\u001a\u00020\u00162\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\b\u0010$\u001a\u00020\u0016H\u0002J\b\u0010%\u001a\u00020\u0016H\u0007J\u0010\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020#H\u0002J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020#H\u0002J\b\u0010*\u001a\u00020\u0016H\u0002J\u0010\u0010+\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u001cH\u0002J\b\u0010-\u001a\u00020\u0016H\u0016J\b\u0010.\u001a\u00020\u0016H\u0016J\b\u0010/\u001a\u00020\u0016H\u0016J\b\u00100\u001a\u00020\u0016H\u0016J\b\u00101\u001a\u00020\u0016H\u0014J\b\u00102\u001a\u00020\u0016H\u0014J\b\u00103\u001a\u00020\u0016H\u0014J\b\u00104\u001a\u00020\u0016H\u0014J\u0010\u00105\u001a\u00020\u00162\u0006\u00106\u001a\u00020\u0006H\u0016J\b\u00107\u001a\u00020\u0016H\u0016J\u0012\u00108\u001a\u00020\u00162\b\b\u0002\u00109\u001a\u00020\u0006H\u0002J\b\u0010:\u001a\u00020\u0016H\u0002J\u0012\u0010;\u001a\u00020\u00162\b\b\u0002\u0010<\u001a\u00020\u0006H\u0002J\b\u0010=\u001a\u00020\u0016H\u0002J\b\u0010>\u001a\u00020\u0016H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityBinding;", "Lcom/yddmi/doctor/pages/login/LoginViewModel;", "()V", "aliChckeBoxChecked", "", "aliPhoneAuthHelper", "Lcom/mobile/auth/gatewayauth/PhoneNumberAuthHelper;", "aliUiClickListener", "Lcom/mobile/auth/gatewayauth/AuthUIControlClickListener;", "hostFastClickNum", "", "mAutoGoPhoneLogin", "mHandler", "com/yddmi/doctor/pages/login/LoginActivity$mHandler$1", "Lcom/yddmi/doctor/pages/login/LoginActivity$mHandler$1;", "mTokenResultListener", "Lcom/mobile/auth/gatewayauth/TokenResultListener;", "networkStatusChangeListener", "Lcom/blankj/utilcode/util/NetworkUtils$OnNetworkStatusChangedListener;", "dealAliOneLogin", "", "autoGo", "dealBack", "dealCanLogin", "dealGo", "data", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "meProfile", "Lcom/yddmi/doctor/entity/result/MeProfile;", "dealNetChange", "dealNoticeSet", "dealPhoneNumber", "str", "", "dealWxLogin", "eventDeal", "httpAliOneClickGo", "token", "httpDealGo", "code", "httpDealSendCode", "httpGetPersonInfo", "loginResult", "initFlow", "initParam", "initView", "onBackPressed", "onDestroy", "onResume", "onStart", "onStop", "onWindowFocusChanged", "hasFocus", "screenOrientation", "viewOneShow", "phoneShow", "viewPopNotice", "viewShowAgreementPop", "fromWx", "viewShowChangeHostPop", "viewYunan", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = true)
@SourceDebugExtension({"SMAP\nLoginActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginActivity.kt\ncom/yddmi/doctor/pages/login/LoginActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,776:1\n18#2,2:777\n1#3:779\n11#4,7:780\n*S KotlinDebug\n*F\n+ 1 LoginActivity.kt\ncom/yddmi/doctor/pages/login/LoginActivity\n*L\n164#1:777,2\n164#1:779\n475#1:780,7\n*E\n"})
/* loaded from: classes6.dex */
public final class LoginActivity extends BaseVMActivity<LoginActivityBinding, LoginViewModel> {

    @NotNull
    private static final String TAG = "LoginActivity";
    private boolean aliChckeBoxChecked;

    @Nullable
    private PhoneNumberAuthHelper aliPhoneAuthHelper;

    @Nullable
    private AuthUIControlClickListener aliUiClickListener;
    private int hostFastClickNum;
    private boolean mAutoGoPhoneLogin = true;

    @NotNull
    private final LoginActivity$mHandler$1 mHandler;

    @Nullable
    private TokenResultListener mTokenResultListener;

    @Nullable
    private NetworkUtils.OnNetworkStatusChangedListener networkStatusChangeListener;

    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/yddmi/doctor/pages/login/LoginActivity$dealAliOneLogin$3", "Lcom/mobile/auth/gatewayauth/ui/AbstractPnsViewDelegate;", "onViewCreated", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.login.LoginActivity$dealAliOneLogin$3, reason: invalid class name */
    public static final class AnonymousClass3 extends AbstractPnsViewDelegate {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onViewCreated$lambda$0(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            PhoneNumberAuthHelper phoneNumberAuthHelper = this$0.aliPhoneAuthHelper;
            if (phoneNumberAuthHelper != null) {
                phoneNumberAuthHelper.quitLoginPage();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onViewCreated$lambda$1(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (!this$0.aliChckeBoxChecked) {
                Toaster.show(R.string.login_agreement_title);
                return;
            }
            PhoneNumberAuthHelper phoneNumberAuthHelper = this$0.aliPhoneAuthHelper;
            if (phoneNumberAuthHelper != null) {
                phoneNumberAuthHelper.quitLoginPage();
            }
            this$0.dealWxLogin();
        }

        @Override // com.mobile.auth.gatewayauth.ui.AbstractPnsViewDelegate
        public void onViewCreated(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            ImageView phoneImgv = (ImageView) view.findViewById(R.id.phoneImgv);
            ImageView wxImgv = (ImageView) view.findViewById(R.id.wxImgv);
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                wxImgv.setVisibility(0);
            } else {
                wxImgv.setVisibility(8);
            }
            Intrinsics.checkNotNullExpressionValue(phoneImgv, "phoneImgv");
            final LoginActivity loginActivity = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(phoneImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginActivity.AnonymousClass3.onViewCreated$lambda$0(loginActivity, view2);
                }
            }, 0L, 2, null);
            Intrinsics.checkNotNullExpressionValue(wxImgv, "wxImgv");
            final LoginActivity loginActivity2 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(wxImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginActivity.AnonymousClass3.onViewCreated$lambda$1(loginActivity2, view2);
                }
            }, 0L, 2, null);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginActivity$initFlow$1", f = "LoginActivity.kt", i = {}, l = {307}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.login.LoginActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07491(Continuation<? super C07491> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return LoginActivity.this.new C07491(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Integer> timeCost = LoginActivity.this.getViewModel().getTimeCost();
                final LoginActivity loginActivity = LoginActivity.this;
                FlowCollector<? super Integer> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.login.LoginActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).intValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(int i3, @NotNull Continuation<? super Unit> continuation) {
                        if (i3 == 0) {
                            LoginActivity.access$getBodyBinding(loginActivity).sendNumTv.setText(loginActivity.getString(R.string.login_send_num_again));
                        } else {
                            LoginActivity.access$getBodyBinding(loginActivity).sendNumTv.setText(i3 + loginActivity.getString(R.string.login_again));
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nLoginActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginActivity.kt\ncom/yddmi/doctor/pages/login/LoginActivity$initView$1\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n+ 3 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,776:1\n65#2,16:777\n93#2,3:793\n11#3,7:796\n11#3,7:803\n*S KotlinDebug\n*F\n+ 1 LoginActivity.kt\ncom/yddmi/doctor/pages/login/LoginActivity$initView$1\n*L\n198#1:777,16\n198#1:793,3\n192#1:796,7\n223#1:803,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.login.LoginActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07501 extends Lambda implements Function1<LoginActivityBinding, Unit> {
        public C07501() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealBack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.setClass(this$0, ServiceSetActivity.class);
            this$0.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$10(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealAliOneLogin(false);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-LOGIN-ONECLICK", "一键登录", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$11(LoginActivity this$0, View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (motionEvent.getAction() == 1) {
                if (this$0.hostFastClickNum == 0) {
                    this$0.mHandler.removeMessages(100);
                    this$0.mHandler.sendEmptyMessageDelayed(100, 1000L);
                }
                this$0.hostFastClickNum++;
                if (this$0.hostFastClickNum > 5) {
                    this$0.viewShowChangeHostPop();
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(LoginActivityBinding this_bodyBinding, LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.phoneEt.setText("");
            this$0.dealCanLogin();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(LoginActivity this$0, LoginActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            if (!YddConfig.INSTANCE.getConfig(YddConfig.ConfigNodeLogin)) {
                Toaster.show((CharSequence) "请使用密码登录");
                return;
            }
            if (this$0.getViewModel().getMSelected()) {
                this_bodyBinding.set.setText("");
                this$0.httpDealSendCode();
                String string = StringsKt__StringsKt.trim((CharSequence) LoginActivity.access$getBodyBinding(this$0).phoneEt.getText().toString()).toString();
                this_bodyBinding.codeTipTv.setText(this$0.getString(R.string.login_send2_num) + "+86 " + string);
                this_bodyBinding.codeCl.setVisibility(0);
                this_bodyBinding.phoneCl.setVisibility(4);
                KeyboardUtils.showSoftInput(this_bodyBinding.set);
            } else {
                LoginActivity.viewShowAgreementPop$default(this$0, false, 1, null);
            }
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-LOGIN-GETAUTHCODE", "获取验证码", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpDealSendCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.setClass(this$0, LoginPswActivity.class);
            this$0.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(LoginActivity this$0, LoginActivityBinding this_bodyBinding, View view) {
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
        public static final boolean invoke$lambda$8(View view, MotionEvent motionEvent) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(LoginActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.getViewModel().getMSelected()) {
                this$0.dealWxLogin();
            } else {
                this$0.viewShowAgreementPop(true);
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityBinding loginActivityBinding) {
            invoke2(loginActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final LoginActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final LoginActivity loginActivity = LoginActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$0(loginActivity, view);
                }
            });
            AppCompatImageView serviceImgv = bodyBinding.serviceImgv;
            Intrinsics.checkNotNullExpressionValue(serviceImgv, "serviceImgv");
            final LoginActivity loginActivity2 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(serviceImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$1(loginActivity2, view);
                }
            }, 0L, 2, null);
            AppCompatImageView appCompatImageView2 = bodyBinding.xImgv;
            final LoginActivity loginActivity3 = LoginActivity.this;
            appCompatImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$2(bodyBinding, loginActivity3, view);
                }
            });
            EditText phoneEt = bodyBinding.phoneEt;
            Intrinsics.checkNotNullExpressionValue(phoneEt, "phoneEt");
            final LoginActivity loginActivity4 = LoginActivity.this;
            phoneEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.LoginActivity$initView$1$invoke$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) {
                    loginActivity4.dealCanLogin();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            BLTextView sendNumG0Tv = bodyBinding.sendNumG0Tv;
            Intrinsics.checkNotNullExpressionValue(sendNumG0Tv, "sendNumG0Tv");
            final LoginActivity loginActivity5 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumG0Tv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$4(loginActivity5, bodyBinding, view);
                }
            }, 0L, 2, null);
            TextView sendNumTv = bodyBinding.sendNumTv;
            Intrinsics.checkNotNullExpressionValue(sendNumTv, "sendNumTv");
            final LoginActivity loginActivity6 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$5(loginActivity6, view);
                }
            }, 0L, 2, null);
            TextView pswTv = bodyBinding.pswTv;
            Intrinsics.checkNotNullExpressionValue(pswTv, "pswTv");
            final LoginActivity loginActivity7 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(pswTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$6(loginActivity7, view);
                }
            }, 0L, 2, null);
            LoginActivity.this.dealCanLogin();
            AppCompatImageView selectImgv = bodyBinding.selectImgv;
            Intrinsics.checkNotNullExpressionValue(selectImgv, "selectImgv");
            final LoginActivity loginActivity8 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(selectImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$7(loginActivity8, bodyBinding, view);
                }
            }, 0L, 2, null);
            bodyBinding.tipTv.setText(LoginActivity.this.getString(R.string.login_tip1));
            TextView tipTv = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv, "tipTv");
            String string = LoginActivity.this.getString(R.string.startup_tip1);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.startup_tip1)");
            LoginActivity loginActivity9 = LoginActivity.this;
            int i2 = R.color.c_3a78ff;
            int colorM = ContextExtKt.getColorM(loginActivity9, i2);
            final LoginActivity loginActivity10 = LoginActivity.this;
            SpanExtKt.appendClickSpan(tipTv, string, colorM, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.initView.1.9
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
                    WebActivity.INSTANCE.startWebActivity(loginActivity10, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : loginActivity10.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            }).append(StrPool.TAB);
            TextView tipTv2 = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv2, "tipTv");
            String string2 = LoginActivity.this.getString(R.string.startup_tip3);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.startup_tip3)");
            int colorM2 = ContextExtKt.getColorM(LoginActivity.this, i2);
            final LoginActivity loginActivity11 = LoginActivity.this;
            SpanExtKt.appendClickSpan(tipTv2, string2, colorM2, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.initView.1.10
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
                    WebActivity.INSTANCE.startWebActivity(loginActivity11, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : loginActivity11.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            });
            bodyBinding.codeCl.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.login.l
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return LoginActivity.C07501.invoke$lambda$8(view, motionEvent);
                }
            });
            SplitEditTextView splitEditTextView = bodyBinding.set;
            final LoginActivity loginActivity12 = LoginActivity.this;
            splitEditTextView.setOnInputListener(new OnInputListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.initView.1.12
                @Override // com.al.open.OnInputListener
                public void onInputFinished(@Nullable String content) {
                    if (content != null) {
                        loginActivity12.httpDealGo(content);
                    }
                }
            });
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                bodyBinding.wxImgv.setVisibility(0);
            } else {
                bodyBinding.wxImgv.setVisibility(8);
            }
            AppCompatImageView wxImgv = bodyBinding.wxImgv;
            Intrinsics.checkNotNullExpressionValue(wxImgv, "wxImgv");
            final LoginActivity loginActivity13 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(wxImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$9(loginActivity13, view);
                }
            }, 0L, 2, null);
            AppCompatImageView phoneImgv = bodyBinding.phoneImgv;
            Intrinsics.checkNotNullExpressionValue(phoneImgv, "phoneImgv");
            final LoginActivity loginActivity14 = LoginActivity.this;
            ViewExtKt.setDebounceClickListener$default(phoneImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.C07501.invoke$lambda$10(loginActivity14, view);
                }
            }, 0L, 2, null);
            FrameLayout frameLayout = bodyBinding.hostFl;
            final LoginActivity loginActivity15 = LoginActivity.this;
            frameLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.login.e
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return LoginActivity.C07501.invoke$lambda$11(loginActivity15, view, motionEvent);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.login.LoginActivity$mHandler$1] */
    public LoginActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.login.LoginActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) throws NoSuchMethodException, SecurityException {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100:
                        this.this$0.hostFastClickNum = 0;
                        break;
                    case 101:
                        this.this$0.dealAliOneLogin(true);
                        break;
                    case 102:
                        OtherUtils.INSTANCE.dealClipboard();
                        break;
                    case 103:
                        this.this$0.dealNoticeSet();
                        break;
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ LoginActivityBinding access$getBodyBinding(LoginActivity loginActivity) {
        return (LoginActivityBinding) loginActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealAliOneLogin(final boolean autoGo) {
        if (!autoGo && getViewModel().getMSelected()) {
            Toaster.show(R.string.login_agreement_title);
            return;
        }
        if (this.mTokenResultListener == null) {
            this.mTokenResultListener = new TokenResultListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.dealAliOneLogin.1
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(@Nullable String str) {
                    LogExtKt.logd("onTokenFailed " + str, LoginActivity.TAG);
                    try {
                        LoginActivity.this.hideLoading();
                        PhoneNumberAuthHelper phoneNumberAuthHelper = LoginActivity.this.aliPhoneAuthHelper;
                        if (phoneNumberAuthHelper != null) {
                            phoneNumberAuthHelper.quitLoginPage();
                        }
                        if (str == null || str.length() == 0) {
                            return;
                        }
                        JsonObject asJsonObject = JsonParser.parseString(str).getAsJsonObject();
                        if (Intrinsics.areEqual(asJsonObject.get("code").getAsString(), ResultCode.CODE_ERROR_USER_CANCEL)) {
                            LogExtKt.logd("阿里一键登录，用户取消", LoginActivity.TAG);
                            BusUtils.post(GlobalAction.eventLogInClose);
                            PhoneNumberAuthHelper phoneNumberAuthHelper2 = LoginActivity.this.aliPhoneAuthHelper;
                            if (phoneNumberAuthHelper2 != null) {
                                phoneNumberAuthHelper2.quitLoginPage();
                            }
                        }
                        if (autoGo) {
                            return;
                        }
                        if (!asJsonObject.has("carrierFailedResultData") || !asJsonObject.has("msg")) {
                            Toaster.show(R.string.login_error2);
                            return;
                        }
                        String msg = asJsonObject.get("msg").getAsString();
                        Intrinsics.checkNotNullExpressionValue(msg, "msg");
                        if (msg.length() > 0) {
                            Toaster.show((CharSequence) msg);
                        } else {
                            Toaster.show(R.string.login_error2);
                        }
                    } catch (Throwable th) {
                        LogExtKt.loge(String.valueOf(th), LoginActivity.TAG);
                        Toaster.show(R.string.login_error2);
                    }
                }

                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(@Nullable String str) {
                    LogExtKt.logd("onTokenSuccess " + str, LoginActivity.TAG);
                    if (str == null || str.length() == 0) {
                        Toaster.show(R.string.login_error2);
                        return;
                    }
                    try {
                        TokenRet tokenRetFromJson = TokenRet.fromJson(str);
                        String code = tokenRetFromJson.getCode();
                        if (code != null) {
                            switch (code.hashCode()) {
                                case 1591780794:
                                    if (!code.equals("600000")) {
                                        break;
                                    } else {
                                        LogExtKt.logd("阿里一键登录获取token成功" + tokenRetFromJson.getToken(), LoginActivity.TAG);
                                        PhoneNumberAuthHelper phoneNumberAuthHelper = LoginActivity.this.aliPhoneAuthHelper;
                                        if (phoneNumberAuthHelper != null) {
                                            phoneNumberAuthHelper.quitLoginPage();
                                        }
                                        LoginActivity loginActivity = LoginActivity.this;
                                        String token = tokenRetFromJson.getToken();
                                        Intrinsics.checkNotNullExpressionValue(token, "tokenRet.token");
                                        loginActivity.httpAliOneClickGo(token);
                                        return;
                                    }
                                case 1591780795:
                                    if (!code.equals(ResultCode.CODE_START_AUTHPAGE_SUCCESS)) {
                                        break;
                                    } else {
                                        LogExtKt.logd("阿里一键登录唤起授权页成功", LoginActivity.TAG);
                                        return;
                                    }
                                case 1591780860:
                                    if (code.equals(ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS)) {
                                        LogExtKt.logd("阿里一键环境支持唤起授权页，拉起一键登录", LoginActivity.TAG);
                                        LoginActivity.this.viewOneShow(true);
                                        PhoneNumberAuthHelper phoneNumberAuthHelper2 = LoginActivity.this.aliPhoneAuthHelper;
                                        if (phoneNumberAuthHelper2 != null) {
                                            phoneNumberAuthHelper2.getLoginToken(LoginActivity.this, 3000);
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                            }
                        }
                        LogExtKt.logd("阿里一键环境不支持一键登录", LoginActivity.TAG);
                        LoginActivity.this.viewOneShow(false);
                    } catch (Exception e2) {
                        PhoneNumberAuthHelper phoneNumberAuthHelper3 = LoginActivity.this.aliPhoneAuthHelper;
                        if (phoneNumberAuthHelper3 != null) {
                            phoneNumberAuthHelper3.quitLoginPage();
                        }
                        e2.printStackTrace();
                    }
                }
            };
        }
        if (this.aliUiClickListener == null) {
            this.aliUiClickListener = new AuthUIControlClickListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.dealAliOneLogin.2
                @Override // com.mobile.auth.gatewayauth.AuthUIControlClickListener
                public void onClick(@Nullable String code, @Nullable Context con, @Nullable String json) {
                    boolean z2;
                    if (code != null) {
                        try {
                            z2 = code.length() == 0;
                        } catch (Throwable th) {
                            LogExtKt.loge("540 " + th, LoginActivity.TAG);
                            return;
                        }
                    }
                    if (z2) {
                        return;
                    }
                    if (json == null || json.length() == 0) {
                        return;
                    }
                    JsonObject asJsonObject = JsonParser.parseString(json).getAsJsonObject();
                    if (Intrinsics.areEqual(code, ResultCode.CODE_ERROR_USER_CHECKBOX)) {
                        LoginActivity.this.aliChckeBoxChecked = asJsonObject.get("isChecked").getAsBoolean();
                        LogExtKt.logd("570 ali登录checkBox是否勾选 " + LoginActivity.this.aliChckeBoxChecked, LoginActivity.TAG);
                    }
                }
            };
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(this, this.mTokenResultListener);
        this.aliPhoneAuthHelper = phoneNumberAuthHelper;
        if (phoneNumberAuthHelper != null) {
            phoneNumberAuthHelper.setUIClickListener(this.aliUiClickListener);
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper2 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper2 != null) {
            phoneNumberAuthHelper2.setAuthSDKInfo(YddConfig.aliOneLoginSecret);
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper3 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper3 != null) {
            phoneNumberAuthHelper3.setAuthPageUseDayLight(false);
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper4 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper4 != null) {
            phoneNumberAuthHelper4.keepAuthPageLandscapeFullSreen(true);
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper5 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper5 != null) {
            AuthUIConfig.Builder lightColor = new AuthUIConfig.Builder().setScreenOrientation(7).setLightColor(false);
            int i2 = R.color.color_white;
            AuthUIConfig.Builder logBtnBackgroundDrawable = lightColor.setStatusBarColor(ContextExtKt.getColorM(this, i2)).setStatusBarHidden(false).setNavReturnImgDrawable(getDrawable(R.drawable.clinic_back_black)).setNavColor(ContextExtKt.getColorM(this, i2)).setNavText(getString(R.string.login_one_phone1)).setNavTextColor(ContextExtKt.getColorM(this, R.color.c_121212)).setSwitchAccHidden(true).setLogoImgDrawable(getDrawable(R.drawable.common_share)).setLogBtnBackgroundDrawable(getDrawable(R.drawable.login_go1));
            int i3 = R.color.color_black;
            AuthUIConfig.Builder privacyOperatorColor = logBtnBackgroundDrawable.setPrivacyOperatorColor(ContextExtKt.getColorM(this, i3));
            String string = getString(R.string.startup_tip1);
            YddConfig yddConfig = YddConfig.INSTANCE;
            phoneNumberAuthHelper5.setAuthUIConfig(privacyOperatorColor.setAppPrivacyOne(string, yddConfig.getWebAgreeUrl()).setAppPrivacyTwo(getString(R.string.startup_tip3), yddConfig.getWebPrivacyUrl()).setAppPrivacyColor(ContextExtKt.getColorM(this, i3), ContextExtKt.getColorM(this, R.color.c_3776ff)).create());
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper6 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper6 != null) {
            phoneNumberAuthHelper6.addAuthRegisterXmlConfig(new AuthRegisterXmlConfig.Builder().setLayout(R.layout.login_ali_switch_other_go, new AnonymousClass3()).build());
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper7 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper7 != null) {
            phoneNumberAuthHelper7.checkEnvAvailable(2);
        }
    }

    public static /* synthetic */ void dealAliOneLogin$default(LoginActivity loginActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginActivity.dealAliOneLogin(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealBack() {
        if (((LoginActivityBinding) getBodyBinding()).codeCl.getVisibility() != 0) {
            closeActivity();
            return;
        }
        KeyboardUtils.hideSoftInput(((LoginActivityBinding) getBodyBinding()).set);
        ((LoginActivityBinding) getBodyBinding()).codeCl.setVisibility(8);
        ((LoginActivityBinding) getBodyBinding()).phoneCl.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dealCanLogin() {
        /*
            r5 = this;
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityBinding r0 = (com.yddmi.doctor.databinding.LoginActivityBinding) r0
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
            com.yddmi.doctor.databinding.LoginActivityBinding r3 = (com.yddmi.doctor.databinding.LoginActivityBinding) r3
            androidx.appcompat.widget.AppCompatImageView r3 = r3.xImgv
            r4 = 4
            r3.setVisibility(r4)
            goto L3f
        L35:
            int r3 = r0.length()
            r4 = 11
            if (r3 != r4) goto L3f
            r3 = r1
            goto L40
        L3f:
            r3 = r2
        L40:
            r5.dealPhoneNumber(r0)
            if (r3 == 0) goto L51
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityBinding r0 = (com.yddmi.doctor.databinding.LoginActivityBinding) r0
            com.noober.background.view.BLTextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r1)
            goto L5c
        L51:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityBinding r0 = (com.yddmi.doctor.databinding.LoginActivityBinding) r0
            com.noober.background.view.BLTextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r2)
        L5c:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityBinding r0 = (com.yddmi.doctor.databinding.LoginActivityBinding) r0
            com.noober.background.view.BLTextView r0 = r0.sendNumG0Tv
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
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginActivity.dealCanLogin():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGo(AuthLoginResult data, MeProfile meProfile) {
        YddUserManager.INSTANCE.getInstance().userInfoSave(data, meProfile);
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        closeActivity();
    }

    private final void dealNetChange() {
        NetworkUtils.OnNetworkStatusChangedListener onNetworkStatusChangedListener = new NetworkUtils.OnNetworkStatusChangedListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.dealNetChange.1
            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onConnected(@Nullable NetworkUtils.NetworkType networkType) {
                LoginActivity.viewOneShow$default(LoginActivity.this, false, 1, null);
            }

            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onDisconnected() {
                LoginActivity.viewOneShow$default(LoginActivity.this, false, 1, null);
            }
        };
        this.networkStatusChangeListener = onNetworkStatusChangedListener;
        NetworkUtils.registerNetworkStatusChangedListener(onNetworkStatusChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealNoticeSet() throws NoSuchMethodException, SecurityException {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (!companion.getInstance().noticeTipGet()) {
            companion.getInstance().noticeTipSet(true);
            return;
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 33) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                return;
            }
            viewPopNotice();
        } else if (i2 >= 33) {
            XXPermissions.with(this).permission(Permission.POST_NOTIFICATIONS).interceptor(new PermissionInterceptor(107)).request(new OnPermissionCallback() { // from class: com.yddmi.doctor.pages.login.LoginActivity.dealNoticeSet.1
                @Override // com.hjq.permissions.OnPermissionCallback
                public void onDenied(@NotNull List<String> permissions, boolean doNotAskAgain) {
                    Intrinsics.checkNotNullParameter(permissions, "permissions");
                    LogExtKt.logd("通知权限否已拒绝 onDenied() 永久拒绝：" + doNotAskAgain, LoginActivity.TAG);
                    LoginActivity.this.viewPopNotice();
                }

                @Override // com.hjq.permissions.OnPermissionCallback
                public void onGranted(@NotNull List<String> permissions, boolean allGranted) {
                    Intrinsics.checkNotNullParameter(permissions, "permissions");
                    LogExtKt.logd("通知权限是否已获取onGranted() " + allGranted, LoginActivity.TAG);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void dealPhoneNumber(String str) {
        if (str == null || str.length() == 0) {
            ((LoginActivityBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            ((LoginActivityBinding) getBodyBinding()).sendNumTv.setEnabled(false);
        } else {
            if (StringExtKt.isPhoneNumber(str)) {
                ((LoginActivityBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_3776ff));
            } else {
                ((LoginActivityBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            }
            ((LoginActivityBinding) getBodyBinding()).sendNumTv.setEnabled(StringExtKt.isPhoneNumber(str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealWxLogin() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (!companion.getInstance().getmWxApi().isWXAppInstalled()) {
            Toaster.show(R.string.me_wx_no);
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        companion.getInstance().getmWxApi().sendReq(req);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpAliOneClickGo(String token) {
        KeyboardUtils.hideSoftInput(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("phoneSystem", "android");
        linkedHashMap.put(SocialConstants.PARAM_SOURCE, "skill");
        linkedHashMap.put("logToken", token);
        linkedHashMap.put("orgId", "0");
        YddConfig yddConfig = YddConfig.INSTANCE;
        linkedHashMap.put("oaId", yddConfig.getAndroidInfo(100));
        linkedHashMap.put("mobileDeviceId", yddConfig.getAndroidInfo(100));
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("mobileBrand", BRAND);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("mobileModel", MODEL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        linkedHashMap.put("mobileVersion", RELEASE);
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        linkedHashMap.put("inviteSign", companion.getInstance().loginShareCodeGet());
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", AppInformationUtil.isHarmonyOS() ? YddConfig.harmonyOs : "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        if (AppInformationUtil.isApkDebugable()) {
            Toaster.showLong((CharSequence) ("ali手机号登录邀请码:" + companion.getInstance().loginShareCodeGet()));
        }
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postOneclickLogin(linkedHashMap), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpAliOneClickGo.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message == null) {
                    message = LoginActivity.this.getString(R.string.login_error1);
                    Intrinsics.checkNotNullExpressionValue(message, "getString(R.string.login_error1)");
                }
                Toaster.show((CharSequence) message);
            }
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpAliOneClickGo.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AuthLoginResult authLoginResult) {
                invoke2(authLoginResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull AuthLoginResult lifecycleLoadingDialog) {
                Intrinsics.checkNotNullParameter(lifecycleLoadingDialog, "$this$lifecycleLoadingDialog");
                YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), lifecycleLoadingDialog, null, 2, null);
                LoginActivity.this.httpGetPersonInfo(lifecycleLoadingDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealGo(String code) {
        KeyboardUtils.hideSoftInput(this);
        FlowExtKt.lifecycleLoadingDialog(getViewModel().codeLogin(StringsKt__StringsKt.trim((CharSequence) ((LoginActivityBinding) getBodyBinding()).phoneEt.getText().toString()).toString(), code), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpDealGo.1
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
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpDealGo.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AuthLoginResult authLoginResult) {
                invoke2(authLoginResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull AuthLoginResult lifecycleLoadingDialog) {
                Intrinsics.checkNotNullParameter(lifecycleLoadingDialog, "$this$lifecycleLoadingDialog");
                YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), lifecycleLoadingDialog, null, 2, null);
                LoginActivity.this.httpGetPersonInfo(lifecycleLoadingDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealSendCode() {
        if (60 == getViewModel().getTimeCost().getValue().intValue() || getViewModel().getTimeCost().getValue().intValue() == 0) {
            String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
            if (StringExtKt.isPhoneNumber(string)) {
                FlowExtKt.lifecycleLoadingDialog(getViewModel().getPushCodeRegister(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpDealSendCode.1
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
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpDealSendCode.2
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
                        LoginActivity.this.getViewModel().dealTimeGo();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo(final AuthLoginResult loginResult) {
        FlowExtKt.lifecycle(getViewModel().getPersonInfo(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpGetPersonInfo.1
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
        }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginActivity.httpGetPersonInfo.2
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
                    LoginActivity.this.dealGo(loginResult, meProfile);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewOneShow(boolean phoneShow) {
        try {
            if (phoneShow) {
                ((LoginActivityBinding) getBodyBinding()).phoneImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigNodeLogin) ? 0 : 8);
            } else if (NetworkUtils.getMobileDataEnabled()) {
                ((LoginActivityBinding) getBodyBinding()).phoneImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigNodeLogin) ? 0 : 8);
            } else {
                ((LoginActivityBinding) getBodyBinding()).phoneImgv.setVisibility(8);
            }
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                ((LoginActivityBinding) getBodyBinding()).wxImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigWachat) ? 0 : 8);
            } else {
                ((LoginActivityBinding) getBodyBinding()).wxImgv.setVisibility(8);
            }
            if (((LoginActivityBinding) getBodyBinding()).wxImgv.getVisibility() != 0 && ((LoginActivityBinding) getBodyBinding()).phoneImgv.getVisibility() != 0 && ((LoginActivityBinding) getBodyBinding()).serviceImgv.getVisibility() != 0) {
                ((LoginActivityBinding) getBodyBinding()).otherTv.setVisibility(4);
                return;
            }
            ((LoginActivityBinding) getBodyBinding()).otherTv.setVisibility(0);
        } catch (Throwable th) {
            LogExtKt.loge("viewOneShow " + th, TAG);
        }
    }

    public static /* synthetic */ void viewOneShow$default(LoginActivity loginActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginActivity.viewOneShow(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopNotice() {
        PopupNotice popupNotice = new PopupNotice(this);
        popupNotice.setOnPopupNoticeClickListener(new PopupNotice.OnPopupNoticeClickListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.viewPopNotice.1
            @Override // com.yddmi.doctor.pages.login.PopupNotice.OnPopupNoticeClickListener
            public void onClick(@Nullable View view) {
                OtherUtils.INSTANCE.enableNotification(LoginActivity.this);
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupNotice).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowAgreementPop(final boolean fromWx) {
        final PopupBottomAgreement popupBottomAgreement = new PopupBottomAgreement(this);
        popupBottomAgreement.setOnPopupAgreementClickListener(new PopupBottomAgreement.OnPopupLoginAgreementClickListener() { // from class: com.yddmi.doctor.pages.login.LoginActivity.viewShowAgreementPop.1
            @Override // com.yddmi.doctor.pages.login.PopupBottomAgreement.OnPopupLoginAgreementClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                int id = view.getId();
                if (id == R.id.f25733tv) {
                    WebActivity.INSTANCE.startWebActivity(LoginActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginActivity.this.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                    return;
                }
                if (id == R.id.tv1) {
                    WebActivity.INSTANCE.startWebActivity(LoginActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginActivity.this.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                    return;
                }
                if (id == R.id.goTv) {
                    LoginActivity.this.getViewModel().setMSelected(true);
                    LoginActivity.access$getBodyBinding(LoginActivity.this).selectImgv.setImageResource(R.drawable.common_select1);
                    popupBottomAgreement.dismiss();
                    if (fromWx) {
                        LoginActivity.this.dealWxLogin();
                    }
                }
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupBottomAgreement).show();
    }

    public static /* synthetic */ void viewShowAgreementPop$default(LoginActivity loginActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginActivity.viewShowAgreementPop(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowChangeHostPop() {
        this.hostFastClickNum = 0;
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupHostChange(this)).show();
    }

    private final void viewYunan() {
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogInClose)
    public final void eventDeal() {
        closeActivity();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07491(null), 3, null);
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
        Intent intent = getIntent();
        if (intent != null) {
            this.mAutoGoPhoneLogin = intent.getBooleanExtra("autoGoPhoneLogin", true);
        }
        removeMessages(101);
        if (this.mAutoGoPhoneLogin) {
            sendEmptyMessageDelayed(101, 200L);
        }
        sendEmptyMessageDelayed(103, 1200L);
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-LOGIN-AUTHCODE", "验证码登录", null, 4, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        viewYunan();
        dealNetChange();
        bodyBinding(new C07501());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        dealBack();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getViewModel().setMSelected(false);
        LogExtKt.logd("onDestroy 销毁...", TAG);
        BusUtils.unregister(this);
        removeCallbacksAndMessages(null);
        KeyboardUtils.hideSoftInput(this);
        NetworkUtils.OnNetworkStatusChangedListener onNetworkStatusChangedListener = this.networkStatusChangeListener;
        if (onNetworkStatusChangedListener != null) {
            NetworkUtils.unregisterNetworkStatusChangedListener(onNetworkStatusChangedListener);
            this.networkStatusChangeListener = null;
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper != null) {
            phoneNumberAuthHelper.setAuthListener(null);
        }
        PhoneNumberAuthHelper phoneNumberAuthHelper2 = this.aliPhoneAuthHelper;
        if (phoneNumberAuthHelper2 != null) {
            phoneNumberAuthHelper2.setUIClickListener(null);
        }
        this.mTokenResultListener = null;
        this.aliUiClickListener = null;
        this.aliPhoneAuthHelper = null;
        LogExtKt.logd("150 onDestroy()", TAG);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        viewYunan();
        sendEmptyMessageDelayed(102, 100L);
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
        viewOneShow$default(this, false, 1, null);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
