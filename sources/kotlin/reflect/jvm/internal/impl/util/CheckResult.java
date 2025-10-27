package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public abstract class CheckResult {
    private final boolean isSuccess;

    public static final class IllegalFunctionName extends CheckResult {

        @NotNull
        public static final IllegalFunctionName INSTANCE = new IllegalFunctionName();

        private IllegalFunctionName() {
            super(false, null);
        }
    }

    public static final class IllegalSignature extends CheckResult {

        @NotNull
        private final String error;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalSignature(@NotNull String error) {
            super(false, null);
            Intrinsics.checkNotNullParameter(error, "error");
            this.error = error;
        }
    }

    public static final class SuccessCheck extends CheckResult {

        @NotNull
        public static final SuccessCheck INSTANCE = new SuccessCheck();

        private SuccessCheck() {
            super(true, null);
        }
    }

    private CheckResult(boolean z2) {
        this.isSuccess = z2;
    }

    public /* synthetic */ CheckResult(boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z2);
    }

    public final boolean isSuccess() {
        return this.isSuccess;
    }
}
