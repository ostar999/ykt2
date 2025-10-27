package com.azhon.appupdate.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import com.umeng.analytics.pro.d;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/azhon/appupdate/util/ApkUtil;", "", "()V", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ApkUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u0014"}, d2 = {"Lcom/azhon/appupdate/util/ApkUtil$Companion;", "", "()V", "createInstallIntent", "Landroid/content/Intent;", d.R, "Landroid/content/Context;", "authorities", "", "apk", "Ljava/io/File;", "deleteOldApk", "", "oldApkPath", "getVersionCode", "", "getVersionCodeByPath", "path", "installApk", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final long getVersionCodeByPath(Context context, String path) {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(path, 1);
            if (Build.VERSION.SDK_INT >= 28) {
                if (packageArchiveInfo != null) {
                    return packageArchiveInfo.getLongVersionCode();
                }
                return 1L;
            }
            if (packageArchiveInfo != null) {
                return packageArchiveInfo.versionCode;
            }
            return 1L;
        }

        @NotNull
        public final Intent createInstallIntent(@NotNull Context context, @NotNull String authorities, @NotNull File apk) {
            Uri uriFromFile;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(authorities, "authorities");
            Intrinsics.checkNotNullParameter(apk, "apk");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.addCategory("android.intent.category.DEFAULT");
            if (Build.VERSION.SDK_INT >= 24) {
                uriFromFile = FileProvider.getUriForFile(context, authorities, apk);
                Intrinsics.checkNotNullExpressionValue(uriFromFile, "getUriForFile(context, authorities, apk)");
                intent.addFlags(1);
            } else {
                uriFromFile = Uri.fromFile(apk);
                Intrinsics.checkNotNullExpressionValue(uriFromFile, "fromFile(apk)");
            }
            intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
            return intent;
        }

        public final boolean deleteOldApk(@NotNull Context context, @NotNull String oldApkPath) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(oldApkPath, "oldApkPath");
            long versionCode = getVersionCode(context);
            try {
                File file = new File(oldApkPath);
                if (!file.exists() || versionCode <= getVersionCodeByPath(context, oldApkPath)) {
                    return false;
                }
                return file.delete();
            } catch (Exception unused) {
                return false;
            }
        }

        public final long getVersionCode(@NotNull Context context) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(context, "context");
            return Build.VERSION.SDK_INT >= 28 ? context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode() : r3.versionCode;
        }

        public final void installApk(@NotNull Context context, @NotNull String authorities, @NotNull File apk) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(authorities, "authorities");
            Intrinsics.checkNotNullParameter(apk, "apk");
            context.startActivity(createInstallIntent(context, authorities, apk));
        }
    }
}
