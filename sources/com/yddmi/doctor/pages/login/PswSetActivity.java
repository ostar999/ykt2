package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.LoginActivityPswSetBinding;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yddmi.doctor.pages.login.PswSetActivity;
import com.yddmi.doctor.pages.main.MainActivity;
import com.yddmi.doctor.utils.OtherUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\b\u0007\u0018\u0000 \u000e2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002J\b\u0010\b\u001a\u00020\u0006H\u0002J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\b\u0010\f\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/login/PswSetActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityPswSetBinding;", "Lcom/yddmi/doctor/pages/login/PswSetViewModel;", "()V", "dealBack", "", "dealCanGo", "httpSavePsw", "initFlow", "initParam", "initView", "onBackPressed", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = true)
@SourceDebugExtension({"SMAP\nPswSetActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PswSetActivity.kt\ncom/yddmi/doctor/pages/login/PswSetActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,175:1\n18#2,2:176\n1#3:178\n11#4,7:179\n*S KotlinDebug\n*F\n+ 1 PswSetActivity.kt\ncom/yddmi/doctor/pages/login/PswSetActivity\n*L\n39#1:176,2\n39#1:178\n102#1:179,7\n*E\n"})
/* loaded from: classes6.dex */
public final class PswSetActivity extends BaseVMActivity<LoginActivityPswSetBinding, PswSetViewModel> {

    @NotNull
    private static final String TAG = "PswSetActivity";

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityPswSetBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nPswSetActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PswSetActivity.kt\ncom/yddmi/doctor/pages/login/PswSetActivity$initView$1\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n+ 3 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,175:1\n65#2,16:176\n93#2,3:192\n65#2,16:195\n93#2,3:211\n11#3,7:214\n*S KotlinDebug\n*F\n+ 1 PswSetActivity.kt\ncom/yddmi/doctor/pages/login/PswSetActivity$initView$1\n*L\n77#1:176,16\n77#1:192,3\n80#1:195,16\n80#1:211,3\n88#1:214,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.login.PswSetActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07701 extends Lambda implements Function1<LoginActivityPswSetBinding, Unit> {
        public C07701() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(PswSetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealBack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(LoginActivityPswSetBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.pswEt.setText("");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(LoginActivityPswSetBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.pswAgainEt.setText("");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(PswSetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpSavePsw();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(PswSetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.setClass(this$0, HomeActivity.class);
            this$0.startActivity(intent);
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityPswSetBinding loginActivityPswSetBinding) {
            invoke2(loginActivityPswSetBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final LoginActivityPswSetBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            if (100 == PswSetActivity.this.getViewModel().getType()) {
                bodyBinding.f25736tv.setText(PswSetActivity.this.getString(R.string.login_psw));
                bodyBinding.tipTv.setText(PswSetActivity.this.getString(R.string.login_psw_tip));
            } else if (101 == PswSetActivity.this.getViewModel().getType()) {
                bodyBinding.f25736tv.setText(PswSetActivity.this.getString(R.string.login_psw1));
                bodyBinding.pswAgainEt.setVisibility(8);
                bodyBinding.skipTv.setVisibility(8);
                bodyBinding.againCl.setVisibility(8);
                bodyBinding.tipTv.setText(PswSetActivity.this.getString(R.string.login_psw_tip1));
            } else {
                LogExtKt.loge("type类型错误 " + PswSetActivity.this.getViewModel().getType(), PswSetActivity.TAG);
            }
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final PswSetActivity pswSetActivity = PswSetActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.x0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PswSetActivity.C07701.invoke$lambda$0(pswSetActivity, view);
                }
            });
            bodyBinding.xImgv.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.y0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PswSetActivity.C07701.invoke$lambda$1(bodyBinding, view);
                }
            });
            bodyBinding.x1Imgv.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.z0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PswSetActivity.C07701.invoke$lambda$2(bodyBinding, view);
                }
            });
            EditText pswEt = bodyBinding.pswEt;
            Intrinsics.checkNotNullExpressionValue(pswEt, "pswEt");
            final PswSetActivity pswSetActivity2 = PswSetActivity.this;
            pswEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.PswSetActivity$initView$1$invoke$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) {
                    pswSetActivity2.dealCanGo();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            EditText pswAgainEt = bodyBinding.pswAgainEt;
            Intrinsics.checkNotNullExpressionValue(pswAgainEt, "pswAgainEt");
            final PswSetActivity pswSetActivity3 = PswSetActivity.this;
            pswAgainEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.PswSetActivity$initView$1$invoke$$inlined$addTextChangedListener$default$2
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) {
                    pswSetActivity3.dealCanGo();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            BLTextView goTv = bodyBinding.goTv;
            Intrinsics.checkNotNullExpressionValue(goTv, "goTv");
            final PswSetActivity pswSetActivity4 = PswSetActivity.this;
            ViewExtKt.setDebounceClickListener$default(goTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.a1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PswSetActivity.C07701.invoke$lambda$5(pswSetActivity4, view);
                }
            }, 0L, 2, null);
            TextView skipTv = bodyBinding.skipTv;
            Intrinsics.checkNotNullExpressionValue(skipTv, "skipTv");
            final PswSetActivity pswSetActivity5 = PswSetActivity.this;
            ViewExtKt.setDebounceClickListener$default(skipTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.b1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PswSetActivity.C07701.invoke$lambda$6(pswSetActivity5, view);
                }
            }, 0L, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealBack() {
        if (100 == getViewModel().getType()) {
            Intent intent = new Intent();
            intent.setClass(this, HomeActivity.class);
            startActivity(intent);
        }
        closeActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealCanGo() {
        String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswSetBinding) getBodyBinding()).pswEt.getText().toString()).toString();
        String string2 = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswSetBinding) getBodyBinding()).pswAgainEt.getText().toString()).toString();
        if (string == null || string.length() == 0) {
            ((LoginActivityPswSetBinding) getBodyBinding()).xImgv.setVisibility(4);
        } else {
            ((LoginActivityPswSetBinding) getBodyBinding()).xImgv.setVisibility(0);
        }
        if (string2 == null || string2.length() == 0) {
            ((LoginActivityPswSetBinding) getBodyBinding()).x1Imgv.setVisibility(4);
        } else {
            ((LoginActivityPswSetBinding) getBodyBinding()).x1Imgv.setVisibility(0);
        }
        boolean zIsPswVerify = StringExtKt.isPswVerify(string);
        boolean zIsPswVerify2 = StringExtKt.isPswVerify(string2);
        if (100 == getViewModel().getType()) {
            zIsPswVerify = zIsPswVerify && zIsPswVerify2;
        }
        if (zIsPswVerify) {
            ((LoginActivityPswSetBinding) getBodyBinding()).goTv.setEnabled(true);
        } else {
            ((LoginActivityPswSetBinding) getBodyBinding()).goTv.setEnabled(false);
        }
        BLTextView bLTextView = ((LoginActivityPswSetBinding) getBodyBinding()).goTv;
        Intrinsics.checkNotNullExpressionValue(bLTextView, "bodyBinding.goTv");
        ViewExtKt.setBackgroundJellyBean16(bLTextView, OtherUtils.INSTANCE.getGoDrawable(zIsPswVerify, CommonExtKt.dp2px(this, 12)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpSavePsw() {
        String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswSetBinding) getBodyBinding()).pswEt.getText().toString()).toString();
        String string2 = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityPswSetBinding) getBodyBinding()).pswAgainEt.getText().toString()).toString();
        if (100 == getViewModel().getType() && !Intrinsics.areEqual(string, string2)) {
            Toaster.show(R.string.login_psw_different);
            return;
        }
        if (!StringExtKt.isPswVerify(string)) {
            Toaster.show(R.string.login_psw_tip);
            return;
        }
        if (100 == getViewModel().getType()) {
            FlowExtKt.lifecycleLoadingDialog(getViewModel().updatePwd(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.PswSetActivity.httpSavePsw.1
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
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.PswSetActivity.httpSavePsw.2
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
                    PswSetActivity pswSetActivity = PswSetActivity.this;
                    Intent intent = new Intent();
                    intent.setClass(pswSetActivity, MainActivity.class);
                    pswSetActivity.startActivity(intent);
                    PswSetActivity.this.closeActivity();
                }
            });
            return;
        }
        if (101 == getViewModel().getType()) {
            String code = getViewModel().getCode();
            boolean z2 = true;
            if (code == null || code.length() == 0) {
                return;
            }
            String phone = getViewModel().getPhone();
            if (phone != null && phone.length() != 0) {
                z2 = false;
            }
            if (z2) {
                return;
            }
            FlowExtKt.lifecycleLoadingDialog(getViewModel().forgetPwd(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.PswSetActivity.httpSavePsw.3
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
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.PswSetActivity.httpSavePsw.4
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
                    PswSetActivity.this.closeActivity();
                }
            });
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
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
            getViewModel().setCode(intent.getStringExtra("code"));
            getViewModel().setPhone(intent.getStringExtra(AliyunLogCommon.TERMINAL_TYPE));
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C07701());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        dealBack();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
