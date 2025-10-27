package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import cn.hutool.core.text.StrPool;
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
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gyf.immersionbar.ImmersionBar;
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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.open.SocialConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.LoginActivityPswBinding;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yddmi.doctor.pages.login.LoginPswActivity;
import com.yddmi.doctor.pages.login.PopupBottomAgreement;
import com.yddmi.doctor.pages.web.WebActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yikaobang.yixue.R2;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0007\u0018\u0000 -2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001-B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0006H\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0002J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0007J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0015H\u0002J\b\u0010!\u001a\u00020\u0010H\u0016J\b\u0010\"\u001a\u00020\u0010H\u0016J\b\u0010#\u001a\u00020\u0010H\u0016J\b\u0010$\u001a\u00020\u0010H\u0014J\b\u0010%\u001a\u00020\u0010H\u0014J\u0010\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020\u0006H\u0016J\b\u0010(\u001a\u00020\u0010H\u0016J\u0012\u0010)\u001a\u00020\u00102\b\b\u0002\u0010*\u001a\u00020\u0006H\u0002J\u0012\u0010+\u001a\u00020\u00102\b\b\u0002\u0010,\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginPswActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityPswBinding;", "Lcom/yddmi/doctor/pages/login/LoginPswViewModel;", "()V", "aliChckeBoxChecked", "", "aliPhoneAuthHelper", "Lcom/mobile/auth/gatewayauth/PhoneNumberAuthHelper;", "aliUiClickListener", "Lcom/mobile/auth/gatewayauth/AuthUIControlClickListener;", "mTokenResultListener", "Lcom/mobile/auth/gatewayauth/TokenResultListener;", "networkStatusChangeListener", "Lcom/blankj/utilcode/util/NetworkUtils$OnNetworkStatusChangedListener;", "dealAliOneLogin", "", "autoGo", "dealCanLogin", "dealGo", "data", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "meProfile", "Lcom/yddmi/doctor/entity/result/MeProfile;", "dealNetChange", "dealWxLogin", "eventDeal", "httpAliOneClickGo", "token", "", "httpDealGo", "httpGetPersonInfo", "loginResult", "initFlow", "initParam", "initView", "onDestroy", "onResume", "onWindowFocusChanged", "hasFocus", "screenOrientation", "viewOneShow", "phoneShow", "viewShowAgreementPop", "fromWx", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = true)
@SourceDebugExtension({"SMAP\nLoginPswActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginPswActivity.kt\ncom/yddmi/doctor/pages/login/LoginPswActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,602:1\n18#2,2:603\n1#3:605\n11#4,7:606\n*S KotlinDebug\n*F\n+ 1 LoginPswActivity.kt\ncom/yddmi/doctor/pages/login/LoginPswActivity\n*L\n110#1:603,2\n110#1:605\n284#1:606,7\n*E\n"})
/* loaded from: classes6.dex */
public final class LoginPswActivity extends BaseVMActivity<LoginActivityPswBinding, LoginPswViewModel> {

    @NotNull
    private static final String TAG = "LoginPswActivity";
    private boolean aliChckeBoxChecked;

    @Nullable
    private PhoneNumberAuthHelper aliPhoneAuthHelper;

    @Nullable
    private AuthUIControlClickListener aliUiClickListener;

    @Nullable
    private TokenResultListener mTokenResultListener;

    @Nullable
    private NetworkUtils.OnNetworkStatusChangedListener networkStatusChangeListener;

    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/yddmi/doctor/pages/login/LoginPswActivity$dealAliOneLogin$3", "Lcom/mobile/auth/gatewayauth/ui/AbstractPnsViewDelegate;", "onViewCreated", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.login.LoginPswActivity$dealAliOneLogin$3, reason: invalid class name */
    public static final class AnonymousClass3 extends AbstractPnsViewDelegate {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onViewCreated$lambda$0(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            PhoneNumberAuthHelper phoneNumberAuthHelper = this$0.aliPhoneAuthHelper;
            if (phoneNumberAuthHelper != null) {
                phoneNumberAuthHelper.quitLoginPage();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onViewCreated$lambda$1(LoginPswActivity this$0, View view) {
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
            final LoginPswActivity loginPswActivity = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(phoneImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginPswActivity.AnonymousClass3.onViewCreated$lambda$0(loginPswActivity, view2);
                }
            }, 0L, 2, null);
            Intrinsics.checkNotNullExpressionValue(wxImgv, "wxImgv");
            final LoginPswActivity loginPswActivity2 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(wxImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginPswActivity.AnonymousClass3.onViewCreated$lambda$1(loginPswActivity2, view2);
                }
            }, 0L, 2, null);
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityPswBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nLoginPswActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LoginPswActivity.kt\ncom/yddmi/doctor/pages/login/LoginPswActivity$initView$1\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n+ 3 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,602:1\n65#2,16:603\n93#2,3:619\n65#2,16:622\n93#2,3:638\n11#3,7:641\n11#3,7:648\n*S KotlinDebug\n*F\n+ 1 LoginPswActivity.kt\ncom/yddmi/doctor/pages/login/LoginPswActivity$initView$1\n*L\n156#1:603,16\n156#1:619,3\n159#1:622,16\n159#1:638,3\n146#1:641,7\n175#1:648,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.login.LoginPswActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07611 extends Lambda implements Function1<LoginActivityPswBinding, Unit> {
        public C07611() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            BusUtils.post(GlobalAction.eventLogInClose);
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.setClass(this$0, ServiceSetActivity.class);
            this$0.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$10(LoginPswActivity this$0, LoginActivityPswBinding this_bodyBinding, View view) {
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
        public static final void invoke$lambda$11(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.getViewModel().getMSelected()) {
                this$0.dealWxLogin();
            } else {
                this$0.viewShowAgreementPop(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$12(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            LoginPswActivity.dealAliOneLogin$default(this$0, false, 1, null);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-LOGIN-ONECLICK", "一键登录", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(LoginActivityPswBinding this_bodyBinding, LoginPswActivity this$0, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.phoneEt.setText("");
            this$0.dealCanLogin();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(LoginActivityPswBinding this_bodyBinding, LoginPswActivity this$0, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.pswEt.setText("");
            this$0.dealCanLogin();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(LoginPswActivity this$0, LoginActivityPswBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            if (this$0.getViewModel().getMShowPsw()) {
                this_bodyBinding.eyeImgv.setImageResource(R.drawable.common_eye_close);
                this_bodyBinding.pswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                EditText pswEt = this_bodyBinding.pswEt;
                Intrinsics.checkNotNullExpressionValue(pswEt, "pswEt");
                ViewExtKt.selectionEndGo(pswEt);
            } else {
                this_bodyBinding.pswEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                EditText pswEt2 = this_bodyBinding.pswEt;
                Intrinsics.checkNotNullExpressionValue(pswEt2, "pswEt");
                ViewExtKt.selectionEndGo(pswEt2);
                this_bodyBinding.eyeImgv.setImageResource(R.drawable.common_eye_open);
            }
            this$0.getViewModel().setMShowPsw(!this$0.getViewModel().getMShowPsw());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.setClass(this$0, VerifyActivity.class);
            this$0.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$8(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpDealGo();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(LoginPswActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityPswBinding loginActivityPswBinding) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            invoke2(loginActivityPswBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final LoginActivityPswBinding bodyBinding) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final LoginPswActivity loginPswActivity = LoginPswActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$0(loginPswActivity, view);
                }
            });
            AppCompatImageView serviceImgv = bodyBinding.serviceImgv;
            Intrinsics.checkNotNullExpressionValue(serviceImgv, "serviceImgv");
            final LoginPswActivity loginPswActivity2 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(serviceImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$1(loginPswActivity2, view);
                }
            }, 0L, 2, null);
            AppCompatImageView appCompatImageView2 = bodyBinding.xImgv;
            final LoginPswActivity loginPswActivity3 = LoginPswActivity.this;
            appCompatImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    LoginPswActivity.C07611.invoke$lambda$2(bodyBinding, loginPswActivity3, view);
                }
            });
            AppCompatImageView appCompatImageView3 = bodyBinding.x1Imgv;
            final LoginPswActivity loginPswActivity4 = LoginPswActivity.this;
            appCompatImageView3.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    LoginPswActivity.C07611.invoke$lambda$3(bodyBinding, loginPswActivity4, view);
                }
            });
            EditText phoneEt = bodyBinding.phoneEt;
            Intrinsics.checkNotNullExpressionValue(phoneEt, "phoneEt");
            final LoginPswActivity loginPswActivity5 = LoginPswActivity.this;
            phoneEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity$initView$1$invoke$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    loginPswActivity5.dealCanLogin();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            EditText pswEt = bodyBinding.pswEt;
            Intrinsics.checkNotNullExpressionValue(pswEt, "pswEt");
            final LoginPswActivity loginPswActivity6 = LoginPswActivity.this;
            pswEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity$initView$1$invoke$$inlined$addTextChangedListener$default$2
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    loginPswActivity6.dealCanLogin();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            AppCompatImageView eyeImgv = bodyBinding.eyeImgv;
            Intrinsics.checkNotNullExpressionValue(eyeImgv, "eyeImgv");
            final LoginPswActivity loginPswActivity7 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(eyeImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$6(loginPswActivity7, bodyBinding, view);
                }
            }, 0L, 2, null);
            TextView forgetTv = bodyBinding.forgetTv;
            Intrinsics.checkNotNullExpressionValue(forgetTv, "forgetTv");
            final LoginPswActivity loginPswActivity8 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(forgetTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.x
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$7(loginPswActivity8, view);
                }
            }, 0L, 2, null);
            TextView goLoginTv = bodyBinding.goLoginTv;
            Intrinsics.checkNotNullExpressionValue(goLoginTv, "goLoginTv");
            final LoginPswActivity loginPswActivity9 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(goLoginTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.y
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$8(loginPswActivity9, view);
                }
            }, 0L, 2, null);
            TextView numTv = bodyBinding.numTv;
            Intrinsics.checkNotNullExpressionValue(numTv, "numTv");
            final LoginPswActivity loginPswActivity10 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(numTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.z
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$9(loginPswActivity10, view);
                }
            }, 0L, 2, null);
            AppCompatImageView selectImgv = bodyBinding.selectImgv;
            Intrinsics.checkNotNullExpressionValue(selectImgv, "selectImgv");
            final LoginPswActivity loginPswActivity11 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(selectImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$10(loginPswActivity11, bodyBinding, view);
                }
            }, 0L, 2, null);
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                bodyBinding.wxImgv.setVisibility(0);
            } else {
                bodyBinding.wxImgv.setVisibility(8);
            }
            AppCompatImageView wxImgv = bodyBinding.wxImgv;
            Intrinsics.checkNotNullExpressionValue(wxImgv, "wxImgv");
            final LoginPswActivity loginPswActivity12 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(wxImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$11(loginPswActivity12, view);
                }
            }, 0L, 2, null);
            AppCompatImageView phoneImgv = bodyBinding.phoneImgv;
            Intrinsics.checkNotNullExpressionValue(phoneImgv, "phoneImgv");
            final LoginPswActivity loginPswActivity13 = LoginPswActivity.this;
            ViewExtKt.setDebounceClickListener$default(phoneImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginPswActivity.C07611.invoke$lambda$12(loginPswActivity13, view);
                }
            }, 0L, 2, null);
            LoginPswActivity.this.dealCanLogin();
            bodyBinding.tipTv.setText(LoginPswActivity.this.getString(R.string.login_tip1));
            TextView tipTv = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv, "tipTv");
            String string = LoginPswActivity.this.getString(R.string.startup_tip1);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.startup_tip1)");
            LoginPswActivity loginPswActivity14 = LoginPswActivity.this;
            int i2 = R.color.c_3a78ff;
            int colorM = ContextExtKt.getColorM(loginPswActivity14, i2);
            final LoginPswActivity loginPswActivity15 = LoginPswActivity.this;
            SpanExtKt.appendClickSpan(tipTv, string, colorM, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.initView.1.14
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
                    WebActivity.INSTANCE.startWebActivity(loginPswActivity15, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : loginPswActivity15.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            }).append(StrPool.TAB);
            TextView tipTv2 = bodyBinding.tipTv;
            Intrinsics.checkNotNullExpressionValue(tipTv2, "tipTv");
            String string2 = LoginPswActivity.this.getString(R.string.startup_tip3);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.startup_tip3)");
            int colorM2 = ContextExtKt.getColorM(LoginPswActivity.this, i2);
            final LoginPswActivity loginPswActivity16 = LoginPswActivity.this;
            SpanExtKt.appendClickSpan(tipTv2, string2, colorM2, false, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.initView.1.15
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
                    WebActivity.INSTANCE.startWebActivity(loginPswActivity16, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : loginPswActivity16.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ LoginActivityPswBinding access$getBodyBinding(LoginPswActivity loginPswActivity) {
        return (LoginActivityPswBinding) loginPswActivity.getBodyBinding();
    }

    private final void dealAliOneLogin(final boolean autoGo) {
        if (!autoGo && getViewModel().getMSelected()) {
            Toaster.show(R.string.login_agreement_title);
            return;
        }
        if (this.aliPhoneAuthHelper == null) {
            this.mTokenResultListener = new TokenResultListener() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.dealAliOneLogin.1
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(@Nullable String str) {
                    LogExtKt.logd("onTokenFailed " + str, LoginPswActivity.TAG);
                    try {
                        LoginPswActivity.this.hideLoading();
                        PhoneNumberAuthHelper phoneNumberAuthHelper = LoginPswActivity.this.aliPhoneAuthHelper;
                        if (phoneNumberAuthHelper != null) {
                            phoneNumberAuthHelper.quitLoginPage();
                        }
                        if (str == null || str.length() == 0) {
                            return;
                        }
                        JsonObject asJsonObject = JsonParser.parseString(str).getAsJsonObject();
                        if (Intrinsics.areEqual(asJsonObject.get("code").getAsString(), ResultCode.CODE_ERROR_USER_CANCEL)) {
                            LogExtKt.logd("阿里一键登录，用户取消", LoginPswActivity.TAG);
                            BusUtils.post(GlobalAction.eventLogInClose);
                            PhoneNumberAuthHelper phoneNumberAuthHelper2 = LoginPswActivity.this.aliPhoneAuthHelper;
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
                        LogExtKt.loge(String.valueOf(th), LoginPswActivity.TAG);
                        Toaster.show(R.string.login_error2);
                    }
                }

                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(@Nullable String str) {
                    TokenRet tokenRetFromJson;
                    String code;
                    LogExtKt.logd("onTokenSuccess " + str, LoginPswActivity.TAG);
                    if (str == null || str.length() == 0) {
                        Toaster.show(R.string.login_error2);
                        return;
                    }
                    try {
                        tokenRetFromJson = TokenRet.fromJson(str);
                        code = tokenRetFromJson.getCode();
                    } catch (Exception e2) {
                        PhoneNumberAuthHelper phoneNumberAuthHelper = LoginPswActivity.this.aliPhoneAuthHelper;
                        if (phoneNumberAuthHelper != null) {
                            phoneNumberAuthHelper.quitLoginPage();
                        }
                        e2.printStackTrace();
                    }
                    if (code != null) {
                        switch (code.hashCode()) {
                            case 1591780794:
                                if (!code.equals("600000")) {
                                    break;
                                } else {
                                    LogExtKt.logd("阿里一键登录获取token成功" + tokenRetFromJson.getToken(), LoginPswActivity.TAG);
                                    PhoneNumberAuthHelper phoneNumberAuthHelper2 = LoginPswActivity.this.aliPhoneAuthHelper;
                                    if (phoneNumberAuthHelper2 != null) {
                                        phoneNumberAuthHelper2.quitLoginPage();
                                    }
                                    LoginPswActivity loginPswActivity = LoginPswActivity.this;
                                    String token = tokenRetFromJson.getToken();
                                    Intrinsics.checkNotNullExpressionValue(token, "tokenRet.token");
                                    loginPswActivity.httpAliOneClickGo(token);
                                    break;
                                }
                            case 1591780795:
                                if (!code.equals(ResultCode.CODE_START_AUTHPAGE_SUCCESS)) {
                                    break;
                                } else {
                                    LogExtKt.logd("阿里一键登录唤起授权页成功", LoginPswActivity.TAG);
                                    break;
                                }
                            case 1591780860:
                                if (code.equals(ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS)) {
                                    LogExtKt.logd("阿里一键环境支持唤起授权页，拉起一键登录", LoginPswActivity.TAG);
                                    PhoneNumberAuthHelper phoneNumberAuthHelper3 = LoginPswActivity.this.aliPhoneAuthHelper;
                                    if (phoneNumberAuthHelper3 != null) {
                                        phoneNumberAuthHelper3.getLoginToken(LoginPswActivity.this, 3000);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }
            };
        }
        if (this.aliUiClickListener == null) {
            this.aliUiClickListener = new AuthUIControlClickListener() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.dealAliOneLogin.2
                @Override // com.mobile.auth.gatewayauth.AuthUIControlClickListener
                public void onClick(@Nullable String code, @Nullable Context con, @Nullable String json) {
                    boolean z2;
                    if (code != null) {
                        try {
                            z2 = code.length() == 0;
                        } catch (Throwable th) {
                            LogExtKt.loge("540 " + th, LoginPswActivity.TAG);
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
                        LoginPswActivity.this.aliChckeBoxChecked = asJsonObject.get("isChecked").getAsBoolean();
                        LogExtKt.logd("372 ali登录checkBox是否勾选 " + LoginPswActivity.this.aliChckeBoxChecked, LoginPswActivity.TAG);
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

    public static /* synthetic */ void dealAliOneLogin$default(LoginPswActivity loginPswActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginPswActivity.dealAliOneLogin(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dealCanLogin() throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            r6 = this;
            androidx.viewbinding.ViewBinding r0 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r0 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r0
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
            r4 = 4
            if (r3 == 0) goto L35
            androidx.viewbinding.ViewBinding r0 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r0 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r0
            androidx.appcompat.widget.AppCompatImageView r0 = r0.xImgv
            r0.setVisibility(r4)
            goto L3f
        L35:
            int r0 = r0.length()
            r3 = 11
            if (r0 != r3) goto L3f
            r0 = r1
            goto L40
        L3f:
            r0 = r2
        L40:
            androidx.viewbinding.ViewBinding r3 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r3 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r3
            android.widget.EditText r3 = r3.pswEt
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            java.lang.CharSequence r3 = kotlin.text.StringsKt.trim(r3)
            java.lang.String r3 = r3.toString()
            if (r3 == 0) goto L63
            int r5 = r3.length()
            if (r5 != 0) goto L61
            goto L63
        L61:
            r5 = r2
            goto L64
        L63:
            r5 = r1
        L64:
            if (r5 == 0) goto L72
            androidx.viewbinding.ViewBinding r3 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r3 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r3
            androidx.appcompat.widget.AppCompatImageView r3 = r3.x1Imgv
            r3.setVisibility(r4)
            goto L7a
        L72:
            boolean r3 = com.catchpig.utils.ext.StringExtKt.isPswVerify(r3)
            if (r3 == 0) goto L7a
            r3 = r1
            goto L7b
        L7a:
            r3 = r2
        L7b:
            r4 = 12
            if (r0 == 0) goto Lae
            if (r3 == 0) goto Lae
            androidx.viewbinding.ViewBinding r0 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r0 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r0
            android.widget.TextView r0 = r0.goLoginTv
            r0.setEnabled(r1)
            com.noober.background.drawable.DrawableCreator$Builder r0 = new com.noober.background.drawable.DrawableCreator$Builder
            r0.<init>()
            int r1 = com.catchpig.utils.ext.CommonExtKt.dp2px(r6, r4)
            float r1 = (float) r1
            com.noober.background.drawable.DrawableCreator$Builder r0 = r0.setCornersRadius(r1)
            int r1 = com.yddmi.doctor.R.color.c_3776ff
            int r1 = com.catchpig.mvvm.ext.ContextExtKt.getColorM(r6, r1)
            com.noober.background.drawable.DrawableCreator$Builder r0 = r0.setSolidColor(r1)
            android.graphics.drawable.Drawable r0 = r0.build()
            java.lang.String r1 = "Builder().setCornersRadi….color.c_3776ff)).build()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            goto Lda
        Lae:
            androidx.viewbinding.ViewBinding r0 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r0 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r0
            android.widget.TextView r0 = r0.goLoginTv
            r0.setEnabled(r2)
            com.noober.background.drawable.DrawableCreator$Builder r0 = new com.noober.background.drawable.DrawableCreator$Builder
            r0.<init>()
            int r1 = com.catchpig.utils.ext.CommonExtKt.dp2px(r6, r4)
            float r1 = (float) r1
            com.noober.background.drawable.DrawableCreator$Builder r0 = r0.setCornersRadius(r1)
            int r1 = com.yddmi.doctor.R.color.c_dfe0e6
            int r1 = com.catchpig.mvvm.ext.ContextExtKt.getColorM(r6, r1)
            com.noober.background.drawable.DrawableCreator$Builder r0 = r0.setSolidColor(r1)
            android.graphics.drawable.Drawable r0 = r0.build()
            java.lang.String r1 = "Builder().setCornersRadi….color.c_dfe0e6)).build()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
        Lda:
            androidx.viewbinding.ViewBinding r1 = r6.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityPswBinding r1 = (com.yddmi.doctor.databinding.LoginActivityPswBinding) r1
            android.widget.TextView r1 = r1.goLoginTv
            java.lang.String r2 = "bodyBinding.goLoginTv"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            com.catchpig.mvvm.ext.ViewExtKt.setBackgroundJellyBean16(r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginPswActivity.dealCanLogin():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGo(AuthLoginResult data, MeProfile meProfile) {
        YddUserManager.INSTANCE.getInstance().userInfoSave(data, meProfile);
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_TOKEN_TIME, Long.valueOf(DateUtil.getTimeInMillisLong()));
        BusUtils.post(GlobalAction.eventLogInClose);
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        closeActivity();
    }

    private final void dealNetChange() {
        NetworkUtils.OnNetworkStatusChangedListener onNetworkStatusChangedListener = new NetworkUtils.OnNetworkStatusChangedListener() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.dealNetChange.1
            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onConnected(@Nullable NetworkUtils.NetworkType networkType) {
                LoginPswActivity.viewOneShow$default(LoginPswActivity.this, false, 1, null);
            }

            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onDisconnected() {
                LoginPswActivity.viewOneShow$default(LoginPswActivity.this, false, 1, null);
            }
        };
        this.networkStatusChangeListener = onNetworkStatusChangedListener;
        NetworkUtils.registerNetworkStatusChangedListener(onNetworkStatusChangedListener);
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
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        linkedHashMap.put("inviteSign", companion.getInstance().loginShareCodeGet());
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
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postOneclickLogin(linkedHashMap), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpAliOneClickGo.1
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
                    message = LoginPswActivity.this.getString(R.string.login_error1);
                    Intrinsics.checkNotNullExpressionValue(message, "getString(R.string.login_error1)");
                }
                Toaster.show((CharSequence) message);
            }
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpAliOneClickGo.2
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
                LoginPswActivity.this.httpGetPersonInfo(lifecycleLoadingDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealGo() {
        KeyboardUtils.hideSoftInput(this);
        if (!getViewModel().getMSelected()) {
            viewShowAgreementPop$default(this, false, 1, null);
            return;
        }
        FlowExtKt.lifecycleLoadingDialog(getViewModel().authLogin(StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswBinding) getBodyBinding()).phoneEt.getText().toString()).toString(), StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswBinding) getBodyBinding()).pswEt.getText().toString()).toString()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpDealGo.1
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
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpDealGo.2
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
                LoginPswActivity.this.httpGetPersonInfo(lifecycleLoadingDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo(final AuthLoginResult loginResult) {
        FlowExtKt.lifecycle(getViewModel().getPersonInfo(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpGetPersonInfo.1
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
        }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.httpGetPersonInfo.2
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
                    LoginPswActivity.this.dealGo(loginResult, meProfile);
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void viewOneShow(boolean phoneShow) {
        try {
            if (phoneShow) {
                ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigNodeLogin) ? 0 : 8);
            } else if (NetworkUtils.getMobileDataEnabled()) {
                ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigNodeLogin) ? 0 : 8);
            } else {
                ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.setVisibility(8);
            }
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                ((LoginActivityPswBinding) getBodyBinding()).wxImgv.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigWachat) ? 0 : 8);
            } else {
                ((LoginActivityPswBinding) getBodyBinding()).wxImgv.setVisibility(8);
            }
            if (((LoginActivityPswBinding) getBodyBinding()).wxImgv.getVisibility() != 0 && ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.getVisibility() != 0 && ((LoginActivityPswBinding) getBodyBinding()).serviceImgv.getVisibility() != 0) {
                ((LoginActivityPswBinding) getBodyBinding()).otherTv.setVisibility(4);
                return;
            }
            ((LoginActivityPswBinding) getBodyBinding()).otherTv.setVisibility(0);
        } catch (Throwable th) {
            LogExtKt.loge("viewOneShow " + th, TAG);
        }
    }

    public static /* synthetic */ void viewOneShow$default(LoginPswActivity loginPswActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginPswActivity.viewOneShow(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowAgreementPop(final boolean fromWx) {
        final PopupBottomAgreement popupBottomAgreement = new PopupBottomAgreement(this);
        popupBottomAgreement.setOnPopupAgreementClickListener(new PopupBottomAgreement.OnPopupLoginAgreementClickListener() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.viewShowAgreementPop.1
            @Override // com.yddmi.doctor.pages.login.PopupBottomAgreement.OnPopupLoginAgreementClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                int id = view.getId();
                if (id == R.id.f25733tv) {
                    WebActivity.INSTANCE.startWebActivity(LoginPswActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebAgreeUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginPswActivity.this.getString(R.string.startup_tip1), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                    return;
                }
                if (id == R.id.tv1) {
                    WebActivity.INSTANCE.startWebActivity(LoginPswActivity.this, (R2.dimen.preference_icon_minWidth & 2) != 0 ? "" : YddConfig.INSTANCE.getWebPrivacyUrl(), (R2.dimen.preference_icon_minWidth & 4) != 0 ? "" : null, (R2.dimen.preference_icon_minWidth & 8) != 0 ? "" : LoginPswActivity.this.getString(R.string.startup_tip3), (R2.dimen.preference_icon_minWidth & 16) == 0 ? null : "", (R2.dimen.preference_icon_minWidth & 32) != 0 ? 0 : null, (R2.dimen.preference_icon_minWidth & 64) != 0, (R2.dimen.preference_icon_minWidth & 128) != 0, (R2.dimen.preference_icon_minWidth & 256) == 0 ? false : true, (R2.dimen.preference_icon_minWidth & 512) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 1024) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 2048) != 0 ? -1 : null, (R2.dimen.preference_icon_minWidth & 4096) != 0 ? -1L : 0L);
                    return;
                }
                if (id == R.id.goTv) {
                    LoginPswActivity.this.getViewModel().setMSelected(true);
                    LoginPswActivity.access$getBodyBinding(LoginPswActivity.this).selectImgv.setImageResource(R.drawable.common_select1);
                    popupBottomAgreement.dismiss();
                    if (fromWx) {
                        LoginPswActivity.this.dealWxLogin();
                    }
                }
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupBottomAgreement).show();
    }

    public static /* synthetic */ void viewShowAgreementPop$default(LoginPswActivity loginPswActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        loginPswActivity.viewShowAgreementPop(z2);
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogInPswClose)
    public final void eventDeal() {
        closeActivity();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
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
        NetworkUtils.registerNetworkStatusChangedListener(new NetworkUtils.OnNetworkStatusChangedListener() { // from class: com.yddmi.doctor.pages.login.LoginPswActivity.initParam.2
            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onConnected(@Nullable NetworkUtils.NetworkType networkType) {
                if (NetworkUtils.getMobileDataEnabled()) {
                    LoginPswActivity.access$getBodyBinding(LoginPswActivity.this).phoneImgv.setVisibility(0);
                } else {
                    LoginPswActivity.access$getBodyBinding(LoginPswActivity.this).phoneImgv.setVisibility(8);
                }
            }

            @Override // com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
            public void onDisconnected() {
                if (NetworkUtils.getMobileDataEnabled()) {
                    LoginPswActivity.access$getBodyBinding(LoginPswActivity.this).phoneImgv.setVisibility(0);
                } else {
                    LoginPswActivity.access$getBodyBinding(LoginPswActivity.this).phoneImgv.setVisibility(8);
                }
            }
        });
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-LOGIN-PASSWORD", "账号密码登录", null, 4, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        dealNetChange();
        bodyBinding(new C07611());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getViewModel().setMSelected(false);
        getViewModel().setMShowPsw(false);
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
        LogExtKt.logd("95 onDestroy()", TAG);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getMobileDataEnabled()) {
            ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.setVisibility(0);
        } else {
            ((LoginActivityPswBinding) getBodyBinding()).phoneImgv.setVisibility(8);
        }
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
