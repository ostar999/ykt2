package com.yddmi.doctor.pages.set;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.utils.PermissionInterceptor;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\nH\u0002J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/yddmi/doctor/pages/set/PopupWxQr;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "mQrUrl", "", "getImplLayoutId", "", "onCreate", "", "saveQr2Photo", PLVRxEncryptDataFunction.SET_DATA_METHOD, "qr", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupWxQr extends FullScreenPopupView {

    @NotNull
    private String mQrUrl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupWxQr(@NotNull Activity activity) {
        super(activity);
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.mQrUrl = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupWxQr this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupWxQr this$0, View view) throws NoSuchMethodException, SecurityException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveQr2Photo();
        this$0.dismiss();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (companion.getInstance().getmWxApi().isWXAppInstalled()) {
            companion.getInstance().getmWxApi().openWXApp();
        }
    }

    private final void saveQr2Photo() throws NoSuchMethodException, SecurityException {
        XXPermissions.with(getContext()).permission(Permission.WRITE_EXTERNAL_STORAGE).interceptor(new PermissionInterceptor(105)).request(new OnPermissionCallback() { // from class: com.yddmi.doctor.pages.set.PopupWxQr.saveQr2Photo.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(@NotNull List<String> permissions, boolean doNotAskAgain) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!doNotAskAgain) {
                    Toaster.show(R.string.common_permissions);
                } else {
                    Toaster.show(R.string.notification_file);
                    XXPermissions.startPermissionActivity(PopupWxQr.this.getContext(), permissions);
                }
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(@NotNull List<String> permissions, boolean allGranted) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (allGranted) {
                } else {
                    Toaster.show(R.string.common_permissions);
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.set_popup_wxqr;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.qrImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.qrImgv)");
        ImageViewExtKt.load((ImageView) viewFindViewById, YddHostConfig.INSTANCE.getInstance().getFileFullUrl(this.mQrUrl), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        TextView textView = (TextView) findViewById(R.id.cancleTv);
        if (textView != null) {
            ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupWxQr.onCreate$lambda$0(this.f26014c, view);
                }
            }, 0L, 2, null);
        }
        TextView textView2 = (TextView) findViewById(R.id.saveTv);
        if (textView2 != null) {
            ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws NoSuchMethodException, SecurityException {
                    PopupWxQr.onCreate$lambda$1(this.f26016c, view);
                }
            }, 0L, 2, null);
        }
    }

    @NotNull
    public final PopupWxQr setData(@NotNull String qr) {
        Intrinsics.checkNotNullParameter(qr, "qr");
        this.mQrUrl = qr;
        return this;
    }
}
