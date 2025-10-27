package com.yddmi.doctor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.permissions.IPermissionInterceptor;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.OnPermissionPageCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.PermissionFragment;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.yddmi.doctor.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 %2\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J>\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0012\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0002J0\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0017\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J>\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001d\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J(\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J8\u0010\u001f\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J*\u0010 \u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00102\b\b\u0002\u0010$\u001a\u00020\u0010H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/utils/PermissionInterceptor;", "Lcom/hjq/permissions/IPermissionInterceptor;", "type", "", "(I)V", "mPermissionPopup", "Landroid/widget/PopupWindow;", "mRequestFlag", "", "mType", "deniedPermissionRequest", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "allPermissions", "", "", "deniedPermissions", "doNotAskAgain", com.alipay.sdk.authjs.a.f3170c, "Lcom/hjq/permissions/OnPermissionCallback;", "dismissPopupWindow", "finishPermissionRequest", "skipRequest", "getBackgroundPermissionOptionLabel", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "grantedPermissionRequest", "grantedPermissions", "allGranted", "launchPermissionRequest", "showPermissionSettingDialog", "showPopupWindow", "decorView", "Landroid/view/ViewGroup;", "message", "title", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PermissionInterceptor implements IPermissionInterceptor {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    @Nullable
    private PopupWindow mPermissionPopup;
    private boolean mRequestFlag;
    private int mType;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/utils/PermissionInterceptor$Companion;", "", "()V", "HANDLER", "Landroid/os/Handler;", "getHANDLER", "()Landroid/os/Handler;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Handler getHANDLER() {
            return PermissionInterceptor.HANDLER;
        }
    }

    public PermissionInterceptor(int i2) {
        this.mType = i2;
    }

    private final void dismissPopupWindow() {
        PopupWindow popupWindow = this.mPermissionPopup;
        if (popupWindow == null) {
            return;
        }
        Intrinsics.checkNotNull(popupWindow);
        if (popupWindow.isShowing()) {
            PopupWindow popupWindow2 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow2);
            popupWindow2.dismiss();
        }
    }

    private final String getBackgroundPermissionOptionLabel(Context context) {
        String string = Build.VERSION.SDK_INT >= 30 ? context.getPackageManager().getBackgroundPermissionOptionLabel().toString() : "";
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String string2 = context.getString(R.string.common_permission_background_default_option_label);
        Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.stri…und_default_option_label)");
        return string2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void launchPermissionRequest$lambda$0(PermissionInterceptor this$0, Activity activity, ViewGroup decorView, Ref.ObjectRef message, Ref.ObjectRef title) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(decorView, "$decorView");
        Intrinsics.checkNotNullParameter(message, "$message");
        Intrinsics.checkNotNullParameter(title, "$title");
        if (!this$0.mRequestFlag || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        this$0.showPopupWindow(activity, decorView, (String) message.element, (String) title.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showPermissionSettingDialog(final android.app.Activity r10, final java.util.List<java.lang.String> r11, final java.util.List<java.lang.String> r12, final com.hjq.permissions.OnPermissionCallback r13) {
        /*
            r9 = this;
            if (r10 == 0) goto L97
            boolean r0 = r10.isFinishing()
            if (r0 != 0) goto L97
            boolean r0 = r10.isDestroyed()
            if (r0 == 0) goto L10
            goto L97
        L10:
            com.yddmi.doctor.utils.PermissionNameConvert r0 = com.yddmi.doctor.utils.PermissionNameConvert.INSTANCE
            java.util.List r1 = r0.permissionsToNames(r10, r12)
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L6e
            int r2 = r12.size()
            r3 = 0
            r4 = 1
            if (r2 != r4) goto L58
            java.lang.Object r2 = r12.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r5 = "android.permission.ACCESS_BACKGROUND_LOCATION"
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r2)
            if (r5 == 0) goto L41
            int r2 = com.yddmi.doctor.R.string.common_permission_manual_assign_fail_background_location_hint
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r6 = r9.getBackgroundPermissionOptionLabel(r10)
            r5[r3] = r6
            java.lang.String r2 = r10.getString(r2, r5)
            goto L59
        L41:
            java.lang.String r5 = "android.permission.BODY_SENSORS_BACKGROUND"
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r2)
            if (r2 == 0) goto L58
            int r2 = com.yddmi.doctor.R.string.common_permission_manual_assign_fail_background_sensors_hint
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r6 = r9.getBackgroundPermissionOptionLabel(r10)
            r5[r3] = r6
            java.lang.String r2 = r10.getString(r2, r5)
            goto L59
        L58:
            r2 = 0
        L59:
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            if (r5 == 0) goto L74
            int r2 = com.yddmi.doctor.R.string.common_permission_manual_assign_fail_hint
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r0 = r0.listToString(r10, r1)
            r4[r3] = r0
            java.lang.String r2 = r10.getString(r2, r4)
            goto L74
        L6e:
            int r0 = com.yddmi.doctor.R.string.common_permission_manual_fail_hint
            java.lang.String r2 = r10.getString(r0)
        L74:
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r0.<init>(r10)
            int r1 = com.yddmi.doctor.R.string.common_permission_alert
            android.app.AlertDialog$Builder r0 = r0.setTitle(r1)
            android.app.AlertDialog$Builder r0 = r0.setMessage(r2)
            int r1 = com.yddmi.doctor.R.string.common_permission_goto_setting_page
            com.yddmi.doctor.utils.d r8 = new com.yddmi.doctor.utils.d
            r2 = r8
            r3 = r10
            r4 = r12
            r5 = r13
            r6 = r11
            r7 = r9
            r2.<init>()
            android.app.AlertDialog$Builder r10 = r0.setPositiveButton(r1, r8)
            r10.show()
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.utils.PermissionInterceptor.showPermissionSettingDialog(android.app.Activity, java.util.List, java.util.List, com.hjq.permissions.OnPermissionCallback):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPermissionSettingDialog$lambda$1(final Activity activity, List deniedPermissions, final OnPermissionCallback onPermissionCallback, final List allPermissions, final PermissionInterceptor this$0, DialogInterface dialog, int i2) {
        Intrinsics.checkNotNullParameter(deniedPermissions, "$deniedPermissions");
        Intrinsics.checkNotNullParameter(allPermissions, "$allPermissions");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        dialog.dismiss();
        XXPermissions.startPermissionActivity(activity, (List<String>) deniedPermissions, new OnPermissionPageCallback() { // from class: com.yddmi.doctor.utils.PermissionInterceptor$showPermissionSettingDialog$1$1
            @Override // com.hjq.permissions.OnPermissionPageCallback
            public void onDenied() {
                PermissionInterceptor permissionInterceptor = this$0;
                Activity activity2 = activity;
                List<String> list = allPermissions;
                List<String> denied = XXPermissions.getDenied(activity2, list);
                Intrinsics.checkNotNullExpressionValue(denied, "getDenied(activity, allPermissions)");
                permissionInterceptor.showPermissionSettingDialog(activity2, list, denied, onPermissionCallback);
            }

            @Override // com.hjq.permissions.OnPermissionPageCallback
            public void onGranted() {
                OnPermissionCallback onPermissionCallback2 = onPermissionCallback;
                if (onPermissionCallback2 != null) {
                    onPermissionCallback2.onGranted(allPermissions, true);
                }
            }
        });
    }

    private final void showPopupWindow(Activity activity, ViewGroup decorView, String message, String title) {
        if (this.mPermissionPopup == null) {
            View viewInflate = LayoutInflater.from(activity).inflate(R.layout.permission_description_popup, decorView, false);
            PopupWindow popupWindow = new PopupWindow(activity);
            this.mPermissionPopup = popupWindow;
            Intrinsics.checkNotNull(popupWindow);
            popupWindow.setContentView(viewInflate);
            PopupWindow popupWindow2 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow2);
            popupWindow2.setWidth(-1);
            PopupWindow popupWindow3 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow3);
            popupWindow3.setHeight(-2);
            PopupWindow popupWindow4 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow4);
            popupWindow4.setAnimationStyle(android.R.style.Animation.Dialog);
            PopupWindow popupWindow5 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow5);
            popupWindow5.setBackgroundDrawable(new ColorDrawable(0));
            PopupWindow popupWindow6 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow6);
            popupWindow6.setTouchable(true);
            PopupWindow popupWindow7 = this.mPermissionPopup;
            Intrinsics.checkNotNull(popupWindow7);
            popupWindow7.setOutsideTouchable(true);
        }
        PopupWindow popupWindow8 = this.mPermissionPopup;
        Intrinsics.checkNotNull(popupWindow8);
        TextView textView = (TextView) popupWindow8.getContentView().findViewById(R.id.tv_title);
        if (title.length() == 0) {
            textView.setText(ContextManager.INSTANCE.getInstance().getContext().getString(R.string.common_permission_description));
        } else {
            textView.setText(title);
        }
        PopupWindow popupWindow9 = this.mPermissionPopup;
        Intrinsics.checkNotNull(popupWindow9);
        ((TextView) popupWindow9.getContentView().findViewById(R.id.tv_permission_description_message)).setText(message);
        PopupWindow popupWindow10 = this.mPermissionPopup;
        Intrinsics.checkNotNull(popupWindow10);
        popupWindow10.showAtLocation(decorView, 48, 0, 0);
    }

    public static /* synthetic */ void showPopupWindow$default(PermissionInterceptor permissionInterceptor, Activity activity, ViewGroup viewGroup, String str, String str2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            str2 = "";
        }
        permissionInterceptor.showPopupWindow(activity, viewGroup, str, str2);
    }

    @Override // com.hjq.permissions.IPermissionInterceptor
    public void deniedPermissionRequest(@NotNull Activity activity, @NotNull List<String> allPermissions, @NotNull List<String> deniedPermissions, boolean doNotAskAgain, @Nullable OnPermissionCallback callback) {
        String string;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(allPermissions, "allPermissions");
        Intrinsics.checkNotNullParameter(deniedPermissions, "deniedPermissions");
        if (callback != null) {
            callback.onDenied(deniedPermissions, doNotAskAgain);
        }
        if (doNotAskAgain) {
            if (deniedPermissions.size() == 1 && Intrinsics.areEqual(Permission.ACCESS_MEDIA_LOCATION, deniedPermissions.get(0))) {
                Toaster.show(R.string.common_permission_media_location_hint_fail);
                return;
            } else {
                showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback);
                return;
            }
        }
        if (deniedPermissions.size() == 1) {
            String str = deniedPermissions.get(0);
            String backgroundPermissionOptionLabel = getBackgroundPermissionOptionLabel(activity);
            if (Intrinsics.areEqual(Permission.ACCESS_BACKGROUND_LOCATION, str)) {
                Toaster.show((CharSequence) activity.getString(R.string.common_permission_background_location_fail_hint, backgroundPermissionOptionLabel));
                return;
            } else if (Intrinsics.areEqual(Permission.BODY_SENSORS_BACKGROUND, str)) {
                Toaster.show((CharSequence) activity.getString(R.string.common_permission_background_sensors_fail_hint, backgroundPermissionOptionLabel));
                return;
            }
        }
        PermissionNameConvert permissionNameConvert = PermissionNameConvert.INSTANCE;
        List<String> listPermissionsToNames = permissionNameConvert.permissionsToNames(activity, deniedPermissions);
        if (listPermissionsToNames.isEmpty()) {
            string = activity.getString(R.string.common_permission_fail_hint);
            Intrinsics.checkNotNullExpressionValue(string, "{\n            activity.g…sion_fail_hint)\n        }");
        } else {
            string = activity.getString(R.string.common_permission_fail_assign_hint, permissionNameConvert.listToString(activity, listPermissionsToNames));
            Intrinsics.checkNotNullExpressionValue(string, "{\n            activity.g…)\n            )\n        }");
        }
        Toaster.show((CharSequence) string);
    }

    @Override // com.hjq.permissions.IPermissionInterceptor
    public void finishPermissionRequest(@NotNull Activity activity, @NotNull List<String> allPermissions, boolean skipRequest, @Nullable OnPermissionCallback callback) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(allPermissions, "allPermissions");
        this.mRequestFlag = false;
        dismissPopupWindow();
    }

    @Override // com.hjq.permissions.IPermissionInterceptor
    public void grantedPermissionRequest(@NotNull Activity activity, @NotNull List<String> allPermissions, @NotNull List<String> grantedPermissions, boolean allGranted, @Nullable OnPermissionCallback callback) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(allPermissions, "allPermissions");
        Intrinsics.checkNotNullParameter(grantedPermissions, "grantedPermissions");
        if (callback == null) {
            return;
        }
        callback.onGranted(grantedPermissions, allGranted);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v14, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v18, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v22, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v26, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v30, types: [T, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object, java.lang.String] */
    @Override // com.hjq.permissions.IPermissionInterceptor
    public void launchPermissionRequest(@NotNull final Activity activity, @NotNull List<String> allPermissions, @Nullable OnPermissionCallback callback) {
        boolean z2;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(allPermissions, "allPermissions");
        this.mRequestFlag = true;
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        final ViewGroup viewGroup = (ViewGroup) decorView;
        for (String str : allPermissions) {
            if (XXPermissions.isSpecial(str) && !XXPermissions.isGranted(activity, str) && (Build.VERSION.SDK_INT >= 30 || !TextUtils.equals(Permission.MANAGE_EXTERNAL_STORAGE, str))) {
                z2 = false;
                break;
            }
        }
        z2 = true;
        if (z2) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = "";
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = "";
            switch (this.mType) {
                case 101:
                    ?? string = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_101);
                    Intrinsics.checkNotNullExpressionValue(string, "ContextManager.getInstan…(R.string.permission_101)");
                    objectRef2.element = string;
                    break;
                case 102:
                    ?? string2 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_102);
                    Intrinsics.checkNotNullExpressionValue(string2, "ContextManager.getInstan…(R.string.permission_102)");
                    objectRef2.element = string2;
                    break;
                case 103:
                    ?? string3 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_103);
                    Intrinsics.checkNotNullExpressionValue(string3, "ContextManager.getInstan…(R.string.permission_103)");
                    objectRef2.element = string3;
                    break;
                case 104:
                default:
                    ?? string4 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.common_permission_message, PermissionNameConvert.INSTANCE.getPermissionString(activity, XXPermissions.getDenied(activity, allPermissions)));
                    Intrinsics.checkNotNullExpressionValue(string4, "ContextManager.getInstan…ns)\n                    )");
                    objectRef2.element = string4;
                    break;
                case 105:
                    ?? string5 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_105);
                    Intrinsics.checkNotNullExpressionValue(string5, "ContextManager.getInstan…(R.string.permission_105)");
                    objectRef2.element = string5;
                    break;
                case 106:
                    ?? string6 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_106);
                    Intrinsics.checkNotNullExpressionValue(string6, "ContextManager.getInstan…(R.string.permission_106)");
                    objectRef2.element = string6;
                    break;
                case 107:
                    ?? string7 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.permission_107);
                    Intrinsics.checkNotNullExpressionValue(string7, "ContextManager.getInstan…(R.string.permission_107)");
                    objectRef2.element = string7;
                    break;
            }
            PermissionFragment.launch(activity, new ArrayList(allPermissions), this, callback);
            HANDLER.postDelayed(new Runnable() { // from class: com.yddmi.doctor.utils.c
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionInterceptor.launchPermissionRequest$lambda$0(this.f26049c, activity, viewGroup, objectRef2, objectRef);
                }
            }, 600L);
        }
    }
}
