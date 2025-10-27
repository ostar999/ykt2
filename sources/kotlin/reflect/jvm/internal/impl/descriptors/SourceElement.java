package kotlin.reflect.jvm.internal.impl.descriptors;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface SourceElement {
    public static final SourceElement NO_SOURCE = new SourceElement() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.SourceElement.1
        private static /* synthetic */ void $$$reportNull$$$0(int i2) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/descriptors/SourceElement$1", "getContainingFile"));
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.SourceElement
        @NotNull
        public SourceFile getContainingFile() {
            SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
            if (sourceFile == null) {
                $$$reportNull$$$0(0);
            }
            return sourceFile;
        }

        public String toString() {
            return "NO_SOURCE";
        }
    };

    @NotNull
    SourceFile getContainingFile();
}
