package splitties.exceptions;

import android.content.Intent;
import com.huawei.hms.support.api.entity.core.CommonCode;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u0010\u0010\u0006\u001a\u00020\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u001a\u0012\u0010\b\u001a\u00020\u00012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u000e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"illegalArg", "", "argument", "", "errorMessage", "", "unexpectedValue", "value", "unsupported", "unsupportedAction", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "splitties-exceptions_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class ExceptionsKt {
    @NotNull
    public static final Void illegalArg(@Nullable String str) {
        throw new IllegalArgumentException(str);
    }

    public static /* synthetic */ Void illegalArg$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = null;
        }
        return illegalArg(str);
    }

    @NotNull
    public static final Void unexpectedValue(@Nullable Object obj) {
        throw new IllegalStateException(Intrinsics.stringPlus("Unexpected value: ", obj));
    }

    @NotNull
    public static final Void unsupported(@Nullable String str) {
        throw new UnsupportedOperationException(str);
    }

    public static /* synthetic */ Void unsupported$default(String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = null;
        }
        return unsupported(str);
    }

    @NotNull
    public static final Void unsupportedAction(@NotNull Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        unsupported(Intrinsics.stringPlus("Unsupported action: ", intent.getAction()));
        throw new KotlinNothingValueException();
    }

    @NotNull
    public static final Void illegalArg(@Nullable Object obj) {
        throw new IllegalArgumentException(Intrinsics.stringPlus("Illegal argument: ", obj));
    }
}
