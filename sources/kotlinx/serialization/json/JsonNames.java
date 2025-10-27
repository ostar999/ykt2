package kotlinx.serialization.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.annotation.AnnotationTarget;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.ExperimentalSerializationApi;
import kotlinx.serialization.SerialInfo;
import org.jetbrains.annotations.NotNull;

@Target({})
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\u0006B\u0014\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004R\u0017\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0007"}, d2 = {"Lkotlinx/serialization/json/JsonNames;", "", "names", "", "", "()[Ljava/lang/String;", "Impl", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@ExperimentalSerializationApi
@SerialInfo
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.PROPERTY})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes8.dex */
public @interface JsonNames {

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004XÆ\u0005¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlinx/serialization/json/JsonNames$Impl;", "Lkotlinx/serialization/json/JsonNames;", "()V", "names", "", "", "()[Ljava/lang/String;", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Impl implements JsonNames {
        private final /* synthetic */ String[] _names;

        private Impl() {
        }

        public Impl(@NotNull String[] names) {
            Intrinsics.checkNotNullParameter(names, "names");
            this._names = names;
        }

        @Override // kotlinx.serialization.json.JsonNames
        @JvmName(name = "names")
        /* renamed from: names, reason: from getter */
        public final /* synthetic */ String[] get_names() {
            return this._names;
        }
    }

    /* renamed from: names */
    String[] get_names();
}
