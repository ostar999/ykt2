package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwnerKt;
import com.al.open.OnInputListener;
import com.al.open.SplitEditTextView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.blankj.utilcode.util.KeyboardUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.LoginActivityVerifyBinding;
import com.yddmi.doctor.pages.login.VerifyActivity;
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

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\b\u0007\u0018\u0000 \u00132\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\b\u0010\u000b\u001a\u00020\u0006H\u0002J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\nH\u0002J\b\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0016J\b\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u0006H\u0016¨\u0006\u0014"}, d2 = {"Lcom/yddmi/doctor/pages/login/VerifyActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityVerifyBinding;", "Lcom/yddmi/doctor/pages/login/VerifyViewModel;", "()V", "dealBack", "", "dealCanNext", "dealPhoneNumber", "str", "", "httpDealSendCode", "httpGetVeriFyMsg", "codeStr", "initFlow", "initParam", "initView", "onBackPressed", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = true)
@SourceDebugExtension({"SMAP\nVerifyActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VerifyActivity.kt\ncom/yddmi/doctor/pages/login/VerifyActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,190:1\n18#2,2:191\n1#3:193\n*S KotlinDebug\n*F\n+ 1 VerifyActivity.kt\ncom/yddmi/doctor/pages/login/VerifyActivity\n*L\n42#1:191,2\n42#1:193\n*E\n"})
/* loaded from: classes6.dex */
public final class VerifyActivity extends BaseVMActivity<LoginActivityVerifyBinding, VerifyViewModel> {

    @NotNull
    private static final String TAG = "VerifyActivity";

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.VerifyActivity$initFlow$1", f = "VerifyActivity.kt", i = {}, l = {94}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.login.VerifyActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07741(Continuation<? super C07741> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return VerifyActivity.this.new C07741(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Integer> timeCost = VerifyActivity.this.getViewModel().getTimeCost();
                final VerifyActivity verifyActivity = VerifyActivity.this;
                FlowCollector<? super Integer> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).intValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(int i3, @NotNull Continuation<? super Unit> continuation) {
                        if (i3 == 0) {
                            VerifyActivity.access$getBodyBinding(verifyActivity).sendNumTv.setText(verifyActivity.getString(R.string.login_send_num_again));
                        } else {
                            VerifyActivity.access$getBodyBinding(verifyActivity).sendNumTv.setText(i3 + verifyActivity.getString(R.string.login_again));
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityVerifyBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nVerifyActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VerifyActivity.kt\ncom/yddmi/doctor/pages/login/VerifyActivity$initView$1\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n*L\n1#1,190:1\n65#2,16:191\n93#2,3:207\n*S KotlinDebug\n*F\n+ 1 VerifyActivity.kt\ncom/yddmi/doctor/pages/login/VerifyActivity$initView$1\n*L\n58#1:191,16\n58#1:207,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.login.VerifyActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07751 extends Lambda implements Function1<LoginActivityVerifyBinding, Unit> {
        public C07751() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(VerifyActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealBack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(LoginActivityVerifyBinding this_bodyBinding, VerifyActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.phoneEt.setText("");
            this$0.dealCanNext();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(VerifyActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpDealSendCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(LoginActivityVerifyBinding this_bodyBinding, VerifyActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this_bodyBinding.set.setText("");
            this$0.httpDealSendCode();
            String string = StringsKt__StringsKt.trim((CharSequence) VerifyActivity.access$getBodyBinding(this$0).phoneEt.getText().toString()).toString();
            this_bodyBinding.codeTipTv.setText(this$0.getString(R.string.login_send2_num) + "+86 " + string);
            this_bodyBinding.codeCl.setVisibility(0);
            this_bodyBinding.phoneCl.setVisibility(8);
            KeyboardUtils.showSoftInput(this_bodyBinding.set);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$5(View view, MotionEvent motionEvent) {
            return true;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityVerifyBinding loginActivityVerifyBinding) {
            invoke2(loginActivityVerifyBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final LoginActivityVerifyBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final VerifyActivity verifyActivity = VerifyActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.g1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VerifyActivity.C07751.invoke$lambda$0(verifyActivity, view);
                }
            });
            EditText phoneEt = bodyBinding.phoneEt;
            Intrinsics.checkNotNullExpressionValue(phoneEt, "phoneEt");
            final VerifyActivity verifyActivity2 = VerifyActivity.this;
            phoneEt.addTextChangedListener(new TextWatcher() { // from class: com.yddmi.doctor.pages.login.VerifyActivity$initView$1$invoke$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void afterTextChanged(@Nullable Editable s2) {
                    verifyActivity2.dealCanNext();
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                }
            });
            AppCompatImageView appCompatImageView2 = bodyBinding.xImgv;
            final VerifyActivity verifyActivity3 = VerifyActivity.this;
            appCompatImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.h1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VerifyActivity.C07751.invoke$lambda$2(bodyBinding, verifyActivity3, view);
                }
            });
            TextView sendNumTv = bodyBinding.sendNumTv;
            Intrinsics.checkNotNullExpressionValue(sendNumTv, "sendNumTv");
            final VerifyActivity verifyActivity4 = VerifyActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.i1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VerifyActivity.C07751.invoke$lambda$3(verifyActivity4, view);
                }
            }, 0L, 2, null);
            BLTextView sendNumG0Tv = bodyBinding.sendNumG0Tv;
            Intrinsics.checkNotNullExpressionValue(sendNumG0Tv, "sendNumG0Tv");
            final VerifyActivity verifyActivity5 = VerifyActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendNumG0Tv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.j1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VerifyActivity.C07751.invoke$lambda$4(bodyBinding, verifyActivity5, view);
                }
            }, 0L, 2, null);
            bodyBinding.codeCl.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.login.k1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return VerifyActivity.C07751.invoke$lambda$5(view, motionEvent);
                }
            });
            SplitEditTextView splitEditTextView = bodyBinding.set;
            final VerifyActivity verifyActivity6 = VerifyActivity.this;
            splitEditTextView.setOnInputListener(new OnInputListener() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.initView.1.7
                @Override // com.al.open.OnInputListener
                public void onInputFinished(@Nullable String content) {
                    if (content != null) {
                        verifyActivity6.httpGetVeriFyMsg(content);
                    }
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ LoginActivityVerifyBinding access$getBodyBinding(VerifyActivity verifyActivity) {
        return (LoginActivityVerifyBinding) verifyActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealBack() {
        if (((LoginActivityVerifyBinding) getBodyBinding()).codeCl.getVisibility() != 0) {
            closeActivity();
            return;
        }
        KeyboardUtils.hideSoftInput(((LoginActivityVerifyBinding) getBodyBinding()).set);
        ((LoginActivityVerifyBinding) getBodyBinding()).codeCl.setVisibility(8);
        ((LoginActivityVerifyBinding) getBodyBinding()).phoneCl.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dealCanNext() {
        /*
            r5 = this;
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r0 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r0
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
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r3 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r3
            androidx.appcompat.widget.AppCompatImageView r3 = r3.xImgv
            r4 = 4
            r3.setVisibility(r4)
            goto L4a
        L35:
            androidx.viewbinding.ViewBinding r3 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r3 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r3
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
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r0 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r0
            com.noober.background.view.BLTextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r1)
            goto L67
        L5c:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r0 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r0
            com.noober.background.view.BLTextView r0 = r0.sendNumG0Tv
            r0.setEnabled(r2)
        L67:
            androidx.viewbinding.ViewBinding r0 = r5.getBodyBinding()
            com.yddmi.doctor.databinding.LoginActivityVerifyBinding r0 = (com.yddmi.doctor.databinding.LoginActivityVerifyBinding) r0
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
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.VerifyActivity.dealCanNext():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void dealPhoneNumber(String str) {
        if (str == null || str.length() == 0) {
            ((LoginActivityVerifyBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            ((LoginActivityVerifyBinding) getBodyBinding()).sendNumTv.setEnabled(false);
        } else {
            if (StringExtKt.isPhoneNumber(str)) {
                ((LoginActivityVerifyBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_3776ff));
            } else {
                ((LoginActivityVerifyBinding) getBodyBinding()).sendNumTv.setTextColor(ContextExtKt.getColorM(this, R.color.c_cccccc));
            }
            ((LoginActivityVerifyBinding) getBodyBinding()).sendNumTv.setEnabled(StringExtKt.isPhoneNumber(str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealSendCode() {
        if (60 == getViewModel().getTimeCost().getValue().intValue() || getViewModel().getTimeCost().getValue().intValue() == 0) {
            String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityVerifyBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
            if (StringExtKt.isPhoneNumber(string)) {
                FlowExtKt.lifecycleLoadingDialog(getViewModel().getPushCodeForgetPwd(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.httpDealSendCode.1
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
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.httpDealSendCode.2
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
                        VerifyActivity.this.getViewModel().dealTimeGo();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpGetVeriFyMsg(final String codeStr) {
        final String string = StringsKt__StringsKt.trim((CharSequence) ((LoginActivityVerifyBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
        FlowExtKt.lifecycleLoadingDialog(getViewModel().getVeriFyMsg(string, codeStr), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.httpGetVeriFyMsg.1
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
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.login.VerifyActivity.httpGetVeriFyMsg.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                Intent intent = new Intent();
                intent.putExtra("type", 101);
                intent.putExtra("code", codeStr);
                intent.putExtra(AliyunLogCommon.TERMINAL_TYPE, string);
                VerifyActivity verifyActivity = this;
                intent.setClass(verifyActivity, PswSetActivity.class);
                verifyActivity.startActivity(intent);
                this.closeActivity();
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07741(null), 3, null);
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
        bodyBinding(new C07751());
        dealCanNext();
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
