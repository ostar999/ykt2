package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ExtensionRegistryLite {
    static final String EXTENSION_CLASS_NAME = "androidx.datastore.preferences.protobuf.Extension";
    private static boolean doFullRuntimeInheritanceCheck = true;
    private static volatile boolean eagerlyParseMessageSets = false;
    private static volatile ExtensionRegistryLite emptyRegistry;
    private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;
    private static final Class<?> extensionClass = resolveExtensionClass();
    static final ExtensionRegistryLite EMPTY_REGISTRY_LITE = new ExtensionRegistryLite(true);

    public static final class ObjectIntPair {
        private final int number;
        private final Object object;

        public ObjectIntPair(Object obj, int i2) {
            this.object = obj;
            this.number = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ObjectIntPair)) {
                return false;
            }
            ObjectIntPair objectIntPair = (ObjectIntPair) obj;
            return this.object == objectIntPair.object && this.number == objectIntPair.number;
        }

        public int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }
    }

    public ExtensionRegistryLite() {
        this.extensionsByNumber = new HashMap();
    }

    public static ExtensionRegistryLite getEmptyRegistry() {
        ExtensionRegistryLite extensionRegistryLiteCreateEmpty = emptyRegistry;
        if (extensionRegistryLiteCreateEmpty == null) {
            synchronized (ExtensionRegistryLite.class) {
                extensionRegistryLiteCreateEmpty = emptyRegistry;
                if (extensionRegistryLiteCreateEmpty == null) {
                    extensionRegistryLiteCreateEmpty = doFullRuntimeInheritanceCheck ? ExtensionRegistryFactory.createEmpty() : EMPTY_REGISTRY_LITE;
                    emptyRegistry = extensionRegistryLiteCreateEmpty;
                }
            }
        }
        return extensionRegistryLiteCreateEmpty;
    }

    public static boolean isEagerlyParseMessageSets() {
        return eagerlyParseMessageSets;
    }

    public static ExtensionRegistryLite newInstance() {
        return doFullRuntimeInheritanceCheck ? ExtensionRegistryFactory.create() : new ExtensionRegistryLite();
    }

    public static Class<?> resolveExtensionClass() {
        try {
            return Class.forName(EXTENSION_CLASS_NAME);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static void setEagerlyParseMessageSets(boolean z2) {
        eagerlyParseMessageSets = z2;
    }

    public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension) {
        this.extensionsByNumber.put(new ObjectIntPair(generatedExtension.getContainingTypeDefaultInstance(), generatedExtension.getNumber()), generatedExtension);
    }

    public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType containingtype, int i2) {
        return (GeneratedMessageLite.GeneratedExtension) this.extensionsByNumber.get(new ObjectIntPair(containingtype, i2));
    }

    public ExtensionRegistryLite getUnmodifiable() {
        return new ExtensionRegistryLite(this);
    }

    public ExtensionRegistryLite(ExtensionRegistryLite extensionRegistryLite) {
        if (extensionRegistryLite == EMPTY_REGISTRY_LITE) {
            this.extensionsByNumber = Collections.emptyMap();
        } else {
            this.extensionsByNumber = Collections.unmodifiableMap(extensionRegistryLite.extensionsByNumber);
        }
    }

    public final void add(ExtensionLite<?, ?> extensionLite) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(extensionLite.getClass())) {
            add((GeneratedMessageLite.GeneratedExtension<?, ?>) extensionLite);
        }
        if (doFullRuntimeInheritanceCheck && ExtensionRegistryFactory.isFullRegistry(this)) {
            try {
                getClass().getMethod("add", extensionClass).invoke(this, extensionLite);
            } catch (Exception e2) {
                throw new IllegalArgumentException(String.format("Could not invoke ExtensionRegistry#add for %s", extensionLite), e2);
            }
        }
    }

    public ExtensionRegistryLite(boolean z2) {
        this.extensionsByNumber = Collections.emptyMap();
    }
}
