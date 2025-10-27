package kotlinx.serialization.json;

import com.umeng.analytics.pro.am;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.json.internal.PolymorphismValidator;
import kotlinx.serialization.modules.SerializersModule;
import kotlinx.serialization.modules.SerializersModuleBuildersKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\t"}, d2 = {"Lkotlinx/serialization/json/JsonImpl;", "Lkotlinx/serialization/json/Json;", "configuration", "Lkotlinx/serialization/json/JsonConfiguration;", am.f22442e, "Lkotlinx/serialization/modules/SerializersModule;", "(Lkotlinx/serialization/json/JsonConfiguration;Lkotlinx/serialization/modules/SerializersModule;)V", "validateConfiguration", "", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
final class JsonImpl extends Json {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonImpl(@NotNull JsonConfiguration configuration, @NotNull SerializersModule module) {
        super(configuration, module, null);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(module, "module");
        validateConfiguration();
    }

    private final void validateConfiguration() {
        if (Intrinsics.areEqual(getSerializersModule(), SerializersModuleBuildersKt.EmptySerializersModule())) {
            return;
        }
        getSerializersModule().dumpTo(new PolymorphismValidator(getConfiguration().getUseArrayPolymorphism(), getConfiguration().getClassDiscriminator()));
    }
}
