package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class MemberSignature {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final String signature;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromFieldNameAndDesc(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name + '#' + desc, null);
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromJvmMemberSignature(@NotNull JvmMemberSignature signature) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            if (signature instanceof JvmMemberSignature.Method) {
                return fromMethodNameAndDesc(signature.getName(), signature.getDesc());
            }
            if (signature instanceof JvmMemberSignature.Field) {
                return fromFieldNameAndDesc(signature.getName(), signature.getDesc());
            }
            throw new NoWhenBranchMatchedException();
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromMethod(@NotNull NameResolver nameResolver, @NotNull JvmProtoBuf.JvmMethodSignature signature) {
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(signature, "signature");
            return fromMethodNameAndDesc(nameResolver.getString(signature.getName()), nameResolver.getString(signature.getDesc()));
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromMethodNameAndDesc(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name + desc, null);
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromMethodSignatureAndParameterIndex(@NotNull MemberSignature signature, int i2) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            return new MemberSignature(signature.getSignature() + '@' + i2, null);
        }
    }

    private MemberSignature(String str) {
        this.signature = str;
    }

    public /* synthetic */ MemberSignature(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MemberSignature) && Intrinsics.areEqual(this.signature, ((MemberSignature) obj).signature);
    }

    @NotNull
    public final String getSignature() {
        return this.signature;
    }

    public int hashCode() {
        return this.signature.hashCode();
    }

    @NotNull
    public String toString() {
        return "MemberSignature(signature=" + this.signature + ')';
    }
}
