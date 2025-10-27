package com.catchpig.utils.ext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0003H\u0007\u001a\u0012\u0010\u0006\u001a\u00020\u0007*\u00020\u00042\u0006\u0010\b\u001a\u00020\u0001\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"TAG", "", "colorResToInt", "", "Landroid/content/Context;", "colorRes", "installApk", "", "apkPath", "layoutInflater", "Landroid/view/LayoutInflater;", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ContextExtKt {

    @NotNull
    private static final String TAG = "ContextExt";

    @ColorInt
    public static final int colorResToInt(@NotNull Context context, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return ContextCompat.getColor(context, i2);
    }

    public static final void installApk(@NotNull Context context, @NotNull String apkPath) {
        Uri uriFromFile;
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        LogExtKt.logi("apk本地路径:" + apkPath, TAG);
        File file = new File(apkPath);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            uriFromFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
            Intrinsics.checkNotNullExpressionValue(uriFromFile, "getUriForFile(this, \"$pa…ame.fileProvider\", pFile)");
        } else {
            uriFromFile = Uri.fromFile(file);
            Intrinsics.checkNotNullExpressionValue(uriFromFile, "fromFile(pFile)");
            intent.setFlags(268435456);
        }
        intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @NotNull
    public static final LayoutInflater layoutInflater(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        Intrinsics.checkNotNullExpressionValue(layoutInflaterFrom, "from(this)");
        return layoutInflaterFrom;
    }
}
