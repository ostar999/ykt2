package com.catchpig.utils.ext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.alipay.sdk.authjs.a;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.mobile.auth.gatewayauth.Constant;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u001a#\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0086\b\u001a<\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00072\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0014\b\u0004\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\tH\u0086\bø\u0001\u0000\u001a#\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u000b2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0086\b\u001a<\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u000b2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0014\b\u0004\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\tH\u0086\bø\u0001\u0000\u001a+\u0010\f\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0087\b\u001a+\u0010\f\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0087\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"startKtActivity", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/app/Activity;", "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "Landroidx/appcompat/app/AppCompatActivity;", a.f3170c, "Lkotlin/Function1;", "Landroidx/activity/result/ActivityResult;", "Landroidx/fragment/app/Fragment;", "startKtActivityForResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ActivityExtKt {

    @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u000e\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/app/Activity;", AdvanceSetting.NETWORK_TYPE, "Landroidx/activity/result/ActivityResult;", "kotlin.jvm.PlatformType", "onActivityResult"}, k = 3, mv = {1, 8, 0}, xi = 176)
    @SourceDebugExtension({"SMAP\nActivityExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt$startKtActivity$2\n*L\n1#1,92:1\n*E\n"})
    /* renamed from: com.catchpig.utils.ext.ActivityExtKt$startKtActivity$2, reason: invalid class name */
    public static final class AnonymousClass2<O> implements ActivityResultCallback {
        final /* synthetic */ Function1<ActivityResult, Unit> $callback;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Function1<? super ActivityResult, Unit> function1) {
            this.$callback = function1;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(ActivityResult it) {
            Function1<ActivityResult, Unit> function1 = this.$callback;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            function1.invoke(it);
        }
    }

    public static final /* synthetic */ <T extends Activity> void startKtActivity(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(context, Activity.class);
        context.startActivity(intent);
    }

    public static /* synthetic */ void startKtActivity$default(Context context, Intent intent, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(context, Activity.class);
        context.startActivity(intent);
    }

    @Deprecated(message = "当前方法已废弃,请使用startKtActivity", replaceWith = @ReplaceWith(expression = "startKtActivity<T>(intent){\n}", imports = {}))
    public static final /* synthetic */ <T extends Activity> void startKtActivityForResult(Activity activity, int i2, Intent intent) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(activity, Activity.class);
        activity.startActivityForResult(intent, i2);
    }

    public static /* synthetic */ void startKtActivityForResult$default(Activity activity, int i2, Intent intent, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(activity, Activity.class);
        activity.startActivityForResult(intent, i2);
    }

    public static final /* synthetic */ <T extends Activity> void startKtActivity(Fragment fragment, Intent intent) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            activity.startActivity(intent);
        }
    }

    @Deprecated(message = "当前方法已废弃,请使用startKtActivity", replaceWith = @ReplaceWith(expression = "startKtActivity<T>(intent){\n}", imports = {}))
    public static final /* synthetic */ <T extends Activity> void startKtActivityForResult(Fragment fragment, int i2, Intent intent) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            activity.startActivityForResult(intent, i2);
        }
    }

    public static /* synthetic */ void startKtActivity$default(Fragment fragment, Intent intent, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            activity.startActivity(intent);
        }
    }

    public static /* synthetic */ void startKtActivityForResult$default(Fragment fragment, int i2, Intent intent, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            activity.startActivityForResult(intent, i2);
        }
    }

    public static final /* synthetic */ <T extends Activity> void startKtActivity(AppCompatActivity appCompatActivity, Intent intent, Function1<? super ActivityResult, Unit> callback) {
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(appCompatActivity, Activity.class);
        appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new AnonymousClass2(callback)).launch(intent);
    }

    public static final /* synthetic */ <T extends Activity> void startKtActivity(Fragment fragment, Intent intent, Function1<? super ActivityResult, Unit> callback) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityExtKt$startKtActivity$3$1(callback)).launch(intent);
        }
    }

    public static /* synthetic */ void startKtActivity$default(AppCompatActivity appCompatActivity, Intent intent, Function1 callback, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        intent.setClass(appCompatActivity, Activity.class);
        appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new AnonymousClass2(callback)).launch(intent);
    }

    public static /* synthetic */ void startKtActivity$default(Fragment fragment, Intent intent, Function1 callback, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            intent = new Intent();
        }
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(callback, "callback");
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            intent.setClass(activity, Activity.class);
            fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityExtKt$startKtActivity$3$1(callback)).launch(intent);
        }
    }
}
