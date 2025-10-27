package com.yddmi.doctor.pages.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.noober.background.view.BLEditText;
import com.yddmi.doctor.R;
import com.yddmi.doctor.app.NoViewModel;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.databinding.LoginActivityServiceSetBinding;
import com.yddmi.doctor.entity.result.AppConfig;
import com.yddmi.doctor.pages.login.ServiceSetActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u0000 \u000e2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\b\u0010\n\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\b\u0010\f\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/login/ServiceSetActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/LoginActivityServiceSetBinding;", "Lcom/yddmi/doctor/app/NoViewModel;", "()V", "dealSave", "", "httpGetConfigWhiteF", "str", "", "initFlow", "initParam", "initView", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = false)
@SourceDebugExtension({"SMAP\nServiceSetActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServiceSetActivity.kt\ncom/yddmi/doctor/pages/login/ServiceSetActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,116:1\n18#2,2:117\n1#3:119\n*S KotlinDebug\n*F\n+ 1 ServiceSetActivity.kt\ncom/yddmi/doctor/pages/login/ServiceSetActivity\n*L\n33#1:117,2\n33#1:119\n*E\n"})
/* loaded from: classes6.dex */
public final class ServiceSetActivity extends BaseVMActivity<LoginActivityServiceSetBinding, NoViewModel> {

    @NotNull
    private static final String TAG = "ServiceSetActivity";

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/LoginActivityServiceSetBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.login.ServiceSetActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07711 extends Lambda implements Function1<LoginActivityServiceSetBinding, Unit> {
        public C07711() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ServiceSetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(ServiceSetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealSave();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(LoginActivityServiceSetBinding loginActivityServiceSetBinding) {
            invoke2(loginActivityServiceSetBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull LoginActivityServiceSetBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView appCompatImageView = bodyBinding.backImgv;
            final ServiceSetActivity serviceSetActivity = ServiceSetActivity.this;
            appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.d1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ServiceSetActivity.C07711.invoke$lambda$0(serviceSetActivity, view);
                }
            });
            TextView saveTv = bodyBinding.saveTv;
            Intrinsics.checkNotNullExpressionValue(saveTv, "saveTv");
            final ServiceSetActivity serviceSetActivity2 = ServiceSetActivity.this;
            ViewExtKt.setDebounceClickListener$default(saveTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.e1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ServiceSetActivity.C07711.invoke$lambda$1(serviceSetActivity2, view);
                }
            }, 0L, 2, null);
            bodyBinding.et.setText(YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
            BLEditText et = bodyBinding.et;
            Intrinsics.checkNotNullExpressionValue(et, "et");
            ViewExtKt.selectionEndGo(et);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealSave() {
        String string;
        Editable text = ((LoginActivityServiceSetBinding) getBodyBinding()).et.getText();
        String string2 = (text == null || (string = text.toString()) == null) ? null : StringsKt__StringsKt.trim((CharSequence) string).toString();
        if (string2 == null || string2.length() == 0) {
            Toaster.show(R.string.login_service_reset);
            YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
            companion.getInstance().servicePrivate("");
            companion.getInstance().dealChangeHost(YddHostConfig.YddHost.FORMAL);
            YddClinicRepository.INSTANCE.dealOtherService();
            BusUtils.post(GlobalAction.eventPrivateChange);
            BusUtils.post(GlobalAction.eventLogInClose);
            GlobalAction.INSTANCE.setHomeDataRefresh(true);
            closeActivity();
            return;
        }
        if (!StringsKt__StringsJVMKt.startsWith$default(string2, "http", false, 2, null)) {
            Toaster.show(R.string.login_service_error);
            return;
        }
        if (!StringsKt__StringsJVMKt.endsWith$default(string2, "/", false, 2, null)) {
            string2 = string2 + "/";
        }
        httpGetConfigWhiteF(string2);
    }

    private final void httpGetConfigWhiteF(final String str) {
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.getConfigWhiteF(str + "api/system/config/whiteList"), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.login.ServiceSetActivity.httpGetConfigWhiteF.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Toaster.show((CharSequence) "服务器连接失败");
            }
        }, new Function1<List<AppConfig>, Unit>() { // from class: com.yddmi.doctor.pages.login.ServiceSetActivity.httpGetConfigWhiteF.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<AppConfig> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable List<AppConfig> list) {
                LogExtKt.logd("89 httpGetConfigWhiteF 测试服务器配置成功 " + list, ServiceSetActivity.TAG);
                if (list != null) {
                    YddConfig.INSTANCE.getKvData().put(YddConfig.KV_CONFIG, list);
                }
                YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
                companion.getInstance().servicePrivate(str);
                companion.getInstance().dealChangeHost(YddHostConfig.YddHost.BASE);
                YddClinicRepository.INSTANCE.dealOtherService();
                Toaster.show(R.string.common_save_success);
                BusUtils.post(GlobalAction.eventPrivateChange);
                BusUtils.post(GlobalAction.eventLogInClose);
                GlobalAction.INSTANCE.setHomeActRefresh(true);
                ServiceSetActivity serviceSetActivity = this;
                Intent intent = new Intent();
                intent.setClass(serviceSetActivity, LoginPswActivity.class);
                serviceSetActivity.startActivity(intent);
                this.closeActivity();
            }
        });
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
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C07711());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
