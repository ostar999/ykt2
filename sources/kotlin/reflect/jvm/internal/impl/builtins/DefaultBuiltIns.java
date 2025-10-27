package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class DefaultBuiltIns extends KotlinBuiltIns {

    @NotNull
    public static final Companion Companion;

    @NotNull
    private static final DefaultBuiltIns Instance;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final DefaultBuiltIns getInstance() {
            return DefaultBuiltIns.Instance;
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Instance = new DefaultBuiltIns(false, 1, defaultConstructorMarker);
    }

    public DefaultBuiltIns() {
        this(false, 1, null);
    }

    public DefaultBuiltIns(boolean z2) {
        super(new LockBasedStorageManager("DefaultBuiltIns"));
        if (z2) {
            createBuiltInsModule(false);
        }
    }

    public /* synthetic */ DefaultBuiltIns(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? true : z2);
    }
}
