package androidx.datastore.preferences.protobuf;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
final class Protobuf {
    private static final Protobuf INSTANCE = new Protobuf();
    private final ConcurrentMap<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap();
    private final SchemaFactory schemaFactory = new ManifestSchemaFactory();

    private Protobuf() {
    }

    public static Protobuf getInstance() {
        return INSTANCE;
    }

    public int getTotalSchemaSize() {
        int schemaSize = 0;
        for (Schema<?> schema : this.schemaCache.values()) {
            if (schema instanceof MessageSchema) {
                schemaSize += ((MessageSchema) schema).getSchemaSize();
            }
        }
        return schemaSize;
    }

    public <T> boolean isInitialized(T t2) {
        return schemaFor((Protobuf) t2).isInitialized(t2);
    }

    public <T> void makeImmutable(T t2) {
        schemaFor((Protobuf) t2).makeImmutable(t2);
    }

    public <T> void mergeFrom(T t2, Reader reader) throws IOException {
        mergeFrom(t2, reader, ExtensionRegistryLite.getEmptyRegistry());
    }

    public Schema<?> registerSchema(Class<?> cls, Schema<?> schema) {
        Internal.checkNotNull(cls, "messageType");
        Internal.checkNotNull(schema, "schema");
        return this.schemaCache.putIfAbsent(cls, schema);
    }

    public Schema<?> registerSchemaOverride(Class<?> cls, Schema<?> schema) {
        Internal.checkNotNull(cls, "messageType");
        Internal.checkNotNull(schema, "schema");
        return this.schemaCache.put(cls, schema);
    }

    public <T> Schema<T> schemaFor(Class<T> cls) {
        Internal.checkNotNull(cls, "messageType");
        Schema<T> schema = (Schema) this.schemaCache.get(cls);
        if (schema != null) {
            return schema;
        }
        Schema<T> schemaCreateSchema = this.schemaFactory.createSchema(cls);
        Schema<T> schema2 = (Schema<T>) registerSchema(cls, schemaCreateSchema);
        return schema2 != null ? schema2 : schemaCreateSchema;
    }

    public <T> void writeTo(T t2, Writer writer) throws IOException {
        schemaFor((Protobuf) t2).writeTo(t2, writer);
    }

    public <T> void mergeFrom(T t2, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        schemaFor((Protobuf) t2).mergeFrom(t2, reader, extensionRegistryLite);
    }

    public <T> Schema<T> schemaFor(T t2) {
        return schemaFor((Class) t2.getClass());
    }
}
