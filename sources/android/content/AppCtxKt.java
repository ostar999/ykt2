package android.content;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.app.backup.BackupAgent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\b\u0010\u0007\u001a\u00020\bH\u0002\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0001\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\u0001\"\u0011\u0010\u0000\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u00018\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"appCtx", "Landroid/content/Context;", "getAppCtx", "()Landroid/content/Context;", "internalCtx", "getProcessName", "", "internalCtxUninitialized", "", "canLeakMemory", "", "injectAsAppCtx", "", "splitties-appctx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class AppCtxKt {

    @SuppressLint({"StaticFieldLeak"})
    @Nullable
    private static Context internalCtx;

    public static final boolean canLeakMemory(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (context instanceof Application) {
            return false;
        }
        if (!(context instanceof Activity ? true : context instanceof Service ? true : context instanceof BackupAgent)) {
            if (context instanceof ContextWrapper) {
                ContextWrapper contextWrapper = (ContextWrapper) context;
                if (contextWrapper.getBaseContext() != context) {
                    Context baseContext = contextWrapper.getBaseContext();
                    Intrinsics.checkNotNullExpressionValue(baseContext, "baseContext");
                    return canLeakMemory(baseContext);
                }
            } else if (context.getApplicationContext() != null) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static final Context getAppCtx() {
        Context context = internalCtx;
        if (context != null) {
            return context;
        }
        internalCtxUninitialized();
        throw new KotlinNothingValueException();
    }

    private static final String getProcessName() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 28) {
            String processName = Application.getProcessName();
            Intrinsics.checkNotNullExpressionValue(processName, "getProcessName()");
            return processName;
        }
        Object objInvoke = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentProcessName", new Class[0]).invoke(null, new Object[0]);
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
    }

    public static final void injectAsAppCtx(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (!canLeakMemory(context)) {
            internalCtx = context;
            return;
        }
        throw new IllegalArgumentException(("The passed Context(" + context + ") would leak memory!").toString());
    }

    private static final Void internalCtxUninitialized() {
        int i2 = 0;
        Pair pair = StringsKt__StringsKt.contains$default((CharSequence) getProcessName(), ':', false, 2, (Object) null) ^ true ? TuplesKt.to("App Startup didn't run", CollectionsKt__CollectionsKt.listOf((Object[]) new String[]{"If App Startup has been disabled, enable it back in the AndroidManifest.xml file of the app.", "For other cases, call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."})) : TuplesKt.to("App Startup is not enabled for non default processes", CollectionsKt__CollectionsJVMKt.listOf("Call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."));
        String str = (String) pair.component1();
        List list = (List) pair.component2();
        StringBuilder sb = new StringBuilder();
        sb.append("appCtx has not been initialized!");
        Intrinsics.checkNotNullExpressionValue(sb, "append(value)");
        sb.append('\n');
        Intrinsics.checkNotNullExpressionValue(sb, "append('\\n')");
        if (list.size() != 1) {
            sb.append(Intrinsics.stringPlus(str, ". Possible solutions:"));
            Intrinsics.checkNotNullExpressionValue(sb, "append(value)");
            sb.append('\n');
            Intrinsics.checkNotNullExpressionValue(sb, "append('\\n')");
            for (Object obj : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                sb.append(i3);
                sb.append(". ");
                sb.append((String) obj);
                i2 = i3;
            }
        } else {
            sb.append(Intrinsics.stringPlus("Possible solution: ", CollectionsKt___CollectionsKt.single(list)));
            Intrinsics.checkNotNullExpressionValue(sb, "append(value)");
            sb.append('\n');
            Intrinsics.checkNotNullExpressionValue(sb, "append('\\n')");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        throw new IllegalStateException(string.toString());
    }
}
