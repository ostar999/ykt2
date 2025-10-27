package com.ykb.ebook.extensions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.ykb.ebook.R;
import java.io.File;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import splitties.systemservices.SystemServicesKt;

@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0015\u001a\u00020\u000e*\u00020\u00022\b\b\u0001\u0010\u0016\u001a\u00020\u000e\u001a\u001c\u0010\u0017\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u0018\u001a\u001c\u0010\u001c\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u000e\u001a\u001c\u0010\u001d\u001a\u00020\u001e*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001e\u001a \u0010\u001f\u001a\u0004\u0018\u00010\u001a*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001a\u001a,\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010!*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010!\u001a\u001c\u0010\"\u001a\u00020#*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010$\u001a\u00020\u0018\u001a\u001a\u0010%\u001a\u00020#*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u000e\u001a\u001a\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u001e\u001a\u001c\u0010'\u001a\u00020#*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010\u001a\u001a \u0010(\u001a\u00020#*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001a0!\u001a\u0012\u0010)\u001a\u00020#*\u00020\u00022\u0006\u0010*\u001a\u00020\u001a\u001a7\u0010+\u001a\u00020#\"\n\b\u0000\u0010,\u0018\u0001*\u00020-*\u00020\u00022\u0019\b\u0002\u0010.\u001a\u0013\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020#0/¢\u0006\u0002\b1H\u0086\bø\u0001\u0000\"\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u0015\u0010\u000b\u001a\u00020\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\n\"\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00028G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0015\u0010\u0011\u001a\u00020\u000e*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010\"\u0015\u0010\u0013\u001a\u00020\u000e*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00062"}, d2 = {"defaultSharedPreferences", "Landroid/content/SharedPreferences;", "Landroid/content/Context;", "getDefaultSharedPreferences$annotations", "(Landroid/content/Context;)V", "getDefaultSharedPreferences", "(Landroid/content/Context;)Landroid/content/SharedPreferences;", "externalCache", "Ljava/io/File;", "getExternalCache", "(Landroid/content/Context;)Ljava/io/File;", "externalFiles", "getExternalFiles", "statusBarHeight", "", "getStatusBarHeight", "(Landroid/content/Context;)I", "sysScreenOffTime", "getSysScreenOffTime", "windowWidth", "getWindowWidth", "getCompatColor", "id", "getPrefBoolean", "", "key", "", "defValue", "getPrefInt", "getPrefLong", "", "getPrefString", "getPrefStringSet", "", "putPrefBoolean", "", "value", "putPrefInt", "putPrefLong", "putPrefString", "putPrefStringSet", "sendToClip", "text", "startActivity", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "Landroid/app/Activity;", "configIntent", "Lkotlin/Function1;", "Landroid/content/Intent;", "Lkotlin/ExtensionFunctionType;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nContextExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextExtensions.kt\ncom/ykb/ebook/extensions/ContextExtensionsKt\n+ 2 SharedPreferences.kt\nandroidx/core/content/SharedPreferencesKt\n+ 3 SystemServices.kt\nsplitties/systemservices/SystemServicesKt\n*L\n1#1,107:1\n39#2,12:108\n39#2,12:120\n39#2,12:132\n39#2,12:144\n39#2,12:156\n188#3:168\n*S KotlinDebug\n*F\n+ 1 ContextExtensions.kt\ncom/ykb/ebook/extensions/ContextExtensionsKt\n*L\n47#1:108,12\n53#1:120,12\n59#1:132,12\n67#1:144,12\n73#1:156,12\n87#1:168\n*E\n"})
/* loaded from: classes7.dex */
public final class ContextExtensionsKt {
    public static final int getCompatColor(@NotNull Context context, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return ContextCompat.getColor(context, i2);
    }

    @NotNull
    public static final SharedPreferences getDefaultSharedPreferences(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Intrinsics.checkNotNullExpressionValue(defaultSharedPreferences, "getDefaultSharedPreferences(this)");
        return defaultSharedPreferences;
    }

    public static /* synthetic */ void getDefaultSharedPreferences$annotations(Context context) {
    }

    @NotNull
    public static final File getExternalCache(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            return externalCacheDir;
        }
        File cacheDir = context.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "this.cacheDir");
        return cacheDir;
    }

    @NotNull
    public static final File getExternalFiles(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir;
        }
        File filesDir = context.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "this.filesDir");
        return filesDir;
    }

    public static final boolean getPrefBoolean(@NotNull Context context, @NotNull String key, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return getDefaultSharedPreferences(context).getBoolean(key, z2);
    }

    public static /* synthetic */ boolean getPrefBoolean$default(Context context, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return getPrefBoolean(context, str, z2);
    }

    public static final int getPrefInt(@NotNull Context context, @NotNull String key, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return getDefaultSharedPreferences(context).getInt(key, i2);
    }

    public static /* synthetic */ int getPrefInt$default(Context context, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return getPrefInt(context, str, i2);
    }

    public static final long getPrefLong(@NotNull Context context, @NotNull String key, long j2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return getDefaultSharedPreferences(context).getLong(key, j2);
    }

    public static /* synthetic */ long getPrefLong$default(Context context, String str, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        return getPrefLong(context, str, j2);
    }

    @Nullable
    public static final String getPrefString(@NotNull Context context, @NotNull String key, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return getDefaultSharedPreferences(context).getString(key, str);
    }

    public static /* synthetic */ String getPrefString$default(Context context, String str, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = null;
        }
        return getPrefString(context, str, str2);
    }

    @Nullable
    public static final Set<String> getPrefStringSet(@NotNull Context context, @NotNull String key, @Nullable Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return getDefaultSharedPreferences(context).getStringSet(key, set);
    }

    public static /* synthetic */ Set getPrefStringSet$default(Context context, String str, Set set, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            set = null;
        }
        return getPrefStringSet(context, str, set);
    }

    @SuppressLint({"DiscouragedApi", "InternalInsetResource"})
    public static final int getStatusBarHeight(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (Intrinsics.areEqual(Build.BOARD, "windows")) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static final int getSysScreenOffTime(@NotNull Context context) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(context, "<this>");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Integer.valueOf(Settings.System.getInt(context.getContentResolver(), "screen_off_timeout")));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM786exceptionOrNullimpl = Result.m786exceptionOrNullimpl(objM783constructorimpl);
        if (thM786exceptionOrNullimpl != null) {
            thM786exceptionOrNullimpl.printStackTrace();
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = 0;
        }
        return ((Number) objM783constructorimpl).intValue();
    }

    public static final int getWindowWidth(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return ConvertExtensionsKt.dpToPx(context.getResources().getConfiguration().screenWidthDp);
    }

    public static final void putPrefBoolean(@NotNull Context context, @NotNull String key, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putBoolean(key, z2);
        editor.apply();
    }

    public static /* synthetic */ void putPrefBoolean$default(Context context, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        putPrefBoolean(context, str, z2);
    }

    public static final void putPrefInt(@NotNull Context context, @NotNull String key, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putInt(key, i2);
        editor.apply();
    }

    public static final void putPrefLong(@NotNull Context context, @NotNull String key, long j2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putLong(key, j2);
        editor.apply();
    }

    public static final void putPrefString(@NotNull Context context, @NotNull String key, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putString(key, str);
        editor.apply();
    }

    public static final void putPrefStringSet(@NotNull Context context, @NotNull String key, @NotNull Set<String> value) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static final void sendToClip(@NotNull Context context, @NotNull String text) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        ((ClipboardManager) SystemServicesKt.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, text));
        Toast.makeText(context, R.string.copy_complete, 1).show();
    }

    public static final /* synthetic */ <A extends Activity> void startActivity(Context context, Function1<? super Intent, Unit> configIntent) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(configIntent, "configIntent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        Intent intent = new Intent(context, (Class<?>) Activity.class);
        configIntent.invoke(intent);
        context.startActivity(intent);
    }

    public static /* synthetic */ void startActivity$default(Context context, Function1 configIntent, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            configIntent = new Function1<Intent, Unit>() { // from class: com.ykb.ebook.extensions.ContextExtensionsKt.startActivity.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Intent intent) {
                    invoke2(intent);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Intent intent) {
                    Intrinsics.checkNotNullParameter(intent, "$this$null");
                }
            };
        }
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(configIntent, "configIntent");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        Intent intent = new Intent(context, (Class<?>) Activity.class);
        configIntent.invoke(intent);
        context.startActivity(intent);
    }
}
