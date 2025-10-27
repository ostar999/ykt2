package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final List<ReflectionAccessFilter> reflectionFilters;

    public static abstract class Adapter<T, A> extends TypeAdapter<T> {
        final Map<String, BoundField> boundFields;

        public Adapter(Map<String, BoundField> map) {
            this.boundFields = map;
        }

        public abstract A createAccumulator();

        public abstract T finalize(A a3);

        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public T read2(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            A aCreateAccumulator = createAccumulator();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = this.boundFields.get(jsonReader.nextName());
                    if (boundField == null || !boundField.deserialized) {
                        jsonReader.skipValue();
                    } else {
                        readField(aCreateAccumulator, jsonReader, boundField);
                    }
                }
                jsonReader.endObject();
                return finalize(aCreateAccumulator);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            } catch (IllegalStateException e3) {
                throw new JsonSyntaxException(e3);
            }
        }

        public abstract void readField(A a3, JsonReader jsonReader, BoundField boundField) throws IllegalAccessException, IOException;

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t2) throws IOException {
            if (t2 == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                Iterator<BoundField> it = this.boundFields.values().iterator();
                while (it.hasNext()) {
                    it.next().write(jsonWriter, t2);
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            }
        }
    }

    public static abstract class BoundField {
        final boolean deserialized;
        final String fieldName;
        final String name;
        final boolean serialized;

        public BoundField(String str, String str2, boolean z2, boolean z3) {
            this.name = str;
            this.fieldName = str2;
            this.serialized = z2;
            this.deserialized = z3;
        }

        public abstract void readIntoArray(JsonReader jsonReader, int i2, Object[] objArr) throws JsonParseException, IOException;

        public abstract void readIntoField(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException;

        public abstract void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException;
    }

    public static final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        private final ObjectConstructor<T> constructor;

        public FieldReflectionAdapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            super(map);
            this.constructor = objectConstructor;
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T createAccumulator() {
            return this.constructor.construct();
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T finalize(T t2) {
            return t2;
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public void readField(T t2, JsonReader jsonReader, BoundField boundField) throws IllegalAccessException, IOException {
            boundField.readIntoField(jsonReader, t2);
        }
    }

    public static final class RecordAdapter<T> extends Adapter<T, Object[]> {
        static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
        private final Map<String, Integer> componentIndices;
        private final Constructor<T> constructor;
        private final Object[] constructorArgsDefaults;

        public RecordAdapter(Class<T> cls, Map<String, BoundField> map, boolean z2) throws JsonIOException, SecurityException {
            super(map);
            this.componentIndices = new HashMap();
            Constructor<T> canonicalRecordConstructor = ReflectionHelper.getCanonicalRecordConstructor(cls);
            this.constructor = canonicalRecordConstructor;
            if (z2) {
                ReflectiveTypeAdapterFactory.checkAccessible(null, canonicalRecordConstructor);
            } else {
                ReflectionHelper.makeAccessible(canonicalRecordConstructor);
            }
            String[] recordComponentNames = ReflectionHelper.getRecordComponentNames(cls);
            for (int i2 = 0; i2 < recordComponentNames.length; i2++) {
                this.componentIndices.put(recordComponentNames[i2], Integer.valueOf(i2));
            }
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i3 = 0; i3 < parameterTypes.length; i3++) {
                this.constructorArgsDefaults[i3] = PRIMITIVE_DEFAULTS.get(parameterTypes[i3]);
            }
        }

        private static Map<Class<?>, Object> primitiveDefaults() {
            HashMap map = new HashMap();
            map.put(Byte.TYPE, (byte) 0);
            map.put(Short.TYPE, (short) 0);
            map.put(Integer.TYPE, 0);
            map.put(Long.TYPE, 0L);
            map.put(Float.TYPE, Float.valueOf(0.0f));
            map.put(Double.TYPE, Double.valueOf(0.0d));
            map.put(Character.TYPE, (char) 0);
            map.put(Boolean.TYPE, Boolean.FALSE);
            return map;
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public Object[] createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T finalize(Object[] objArr) {
            try {
                return this.constructor.newInstance(objArr);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            } catch (IllegalArgumentException e3) {
                e = e3;
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e);
            } catch (InstantiationException e4) {
                e = e4;
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e);
            } catch (InvocationTargetException e5) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e5.getCause());
            }
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public void readField(Object[] objArr, JsonReader jsonReader, BoundField boundField) throws JsonParseException, IOException {
            Integer num = this.componentIndices.get(boundField.fieldName);
            if (num != null) {
                boundField.readIntoArray(jsonReader, num.intValue(), objArr);
                return;
            }
            throw new IllegalStateException("Could not find the index in the constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + boundField.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
        }
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory, List<ReflectionAccessFilter> list) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        this.reflectionFilters = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <M extends AccessibleObject & Member> void checkAccessible(Object obj, M m2) {
        if (Modifier.isStatic(m2.getModifiers())) {
            obj = null;
        }
        if (ReflectionAccessFilterHelper.canAccess(m2, obj)) {
            return;
        }
        throw new JsonIOException(ReflectionHelper.getAccessibleObjectDescription(m2, true) + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
    }

    private BoundField createBoundField(final Gson gson, final Field field, final Method method, String str, final TypeToken<?> typeToken, boolean z2, boolean z3, final boolean z4) {
        final boolean zIsPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        int modifiers = field.getModifiers();
        final boolean z5 = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
        JsonAdapter jsonAdapter = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter) : null;
        final boolean z6 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter2 = typeAdapter;
        return new BoundField(str, field.getName(), z2, z3) { // from class: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.1
            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void readIntoArray(JsonReader jsonReader, int i2, Object[] objArr) throws JsonParseException, IOException {
                Object obj = typeAdapter2.read2(jsonReader);
                if (obj != null || !zIsPrimitive) {
                    objArr[i2] = obj;
                    return;
                }
                throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + jsonReader.getPath());
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void readIntoField(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException, IllegalArgumentException {
                Object obj2 = typeAdapter2.read2(jsonReader);
                if (obj2 == null && zIsPrimitive) {
                    return;
                }
                if (z4) {
                    ReflectiveTypeAdapterFactory.checkAccessible(obj, field);
                } else if (z5) {
                    throw new JsonIOException("Cannot set value of 'static final' " + ReflectionHelper.getAccessibleObjectDescription(field, false));
                }
                field.set(obj, obj2);
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
                Object objInvoke;
                if (this.serialized) {
                    if (z4) {
                        Method method2 = method;
                        if (method2 == null) {
                            ReflectiveTypeAdapterFactory.checkAccessible(obj, field);
                        } else {
                            ReflectiveTypeAdapterFactory.checkAccessible(obj, method2);
                        }
                    }
                    Method method3 = method;
                    if (method3 != null) {
                        try {
                            objInvoke = method3.invoke(obj, new Object[0]);
                        } catch (InvocationTargetException e2) {
                            throw new JsonIOException("Accessor " + ReflectionHelper.getAccessibleObjectDescription(method, false) + " threw exception", e2.getCause());
                        }
                    } else {
                        objInvoke = field.get(obj);
                    }
                    if (objInvoke == obj) {
                        return;
                    }
                    jsonWriter.name(this.name);
                    (z6 ? typeAdapter2 : new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, typeToken.getType())).write(jsonWriter, objInvoke);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0154 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [int] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Map<java.lang.String, com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField> getBoundFields(com.google.gson.Gson r29, com.google.gson.reflect.TypeToken<?> r30, java.lang.Class<?> r31, boolean r32, boolean r33) throws com.google.gson.JsonIOException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(com.google.gson.Gson, com.google.gson.reflect.TypeToken, java.lang.Class, boolean, boolean):java.util.Map");
    }

    private List<String> getFieldNames(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String strValue = serializedName.value();
        String[] strArrAlternate = serializedName.alternate();
        if (strArrAlternate.length == 0) {
            return Collections.singletonList(strValue);
        }
        ArrayList arrayList = new ArrayList(strArrAlternate.length + 1);
        arrayList.add(strValue);
        Collections.addAll(arrayList, strArrAlternate);
        return arrayList;
    }

    private boolean includeField(Field field, boolean z2) {
        return (this.excluder.excludeClass(field.getType(), z2) || this.excluder.excludeField(field, z2)) ? false : true;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
        if (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            boolean z2 = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            return ReflectionHelper.isRecord(rawType) ? new RecordAdapter(rawType, getBoundFields(gson, typeToken, rawType, z2, true), z2) : new FieldReflectionAdapter(this.constructorConstructor.get(typeToken), getBoundFields(gson, typeToken, rawType, z2, false));
        }
        throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + rawType + ". Register a TypeAdapter for this type or adjust the access filter.");
    }
}
