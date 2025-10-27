package com.nirvana.tools.jsoner;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public final class JsonHelper {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    public static final class a implements Serializable, GenericArrayType {

        /* renamed from: a, reason: collision with root package name */
        private final Type f10657a;

        public a(Type type) {
            this.f10657a = JsonHelper.canonicalize(type);
        }

        public final boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && JsonHelper.equals(this, (GenericArrayType) obj);
        }

        @Override // java.lang.reflect.GenericArrayType
        public final Type getGenericComponentType() {
            return this.f10657a;
        }

        public final int hashCode() {
            return this.f10657a.hashCode();
        }

        public final String toString() {
            return JsonHelper.typeToString(this.f10657a) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
    }

    public static final class b implements Serializable, ParameterizedType {

        /* renamed from: a, reason: collision with root package name */
        private final Type f10658a;

        /* renamed from: b, reason: collision with root package name */
        private final Type f10659b;

        /* renamed from: c, reason: collision with root package name */
        private final Type[] f10660c;

        public b(Type type, Type type2, Type... typeArr) {
            if (type2 instanceof Class) {
                Class cls = (Class) type2;
                boolean z2 = true;
                boolean z3 = Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null;
                if (type == null && !z3) {
                    z2 = false;
                }
                JsonPreconditions.checkArgument(z2);
            }
            this.f10658a = type == null ? null : JsonHelper.canonicalize(type);
            this.f10659b = JsonHelper.canonicalize(type2);
            Type[] typeArr2 = (Type[]) typeArr.clone();
            this.f10660c = typeArr2;
            int length = typeArr2.length;
            for (int i2 = 0; i2 < length; i2++) {
                JsonPreconditions.checkNotNull(this.f10660c[i2]);
                JsonHelper.checkNotPrimitive(this.f10660c[i2]);
                Type[] typeArr3 = this.f10660c;
                typeArr3[i2] = JsonHelper.canonicalize(typeArr3[i2]);
            }
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && JsonHelper.equals(this, (ParameterizedType) obj);
        }

        @Override // java.lang.reflect.ParameterizedType
        public final Type[] getActualTypeArguments() {
            return (Type[]) this.f10660c.clone();
        }

        @Override // java.lang.reflect.ParameterizedType
        public final Type getOwnerType() {
            return this.f10658a;
        }

        @Override // java.lang.reflect.ParameterizedType
        public final Type getRawType() {
            return this.f10659b;
        }

        public final int hashCode() {
            return (Arrays.hashCode(this.f10660c) ^ this.f10659b.hashCode()) ^ JsonHelper.hashCodeOrZero(this.f10658a);
        }

        public final String toString() {
            int length = this.f10660c.length;
            if (length == 0) {
                return JsonHelper.typeToString(this.f10659b);
            }
            StringBuilder sb = new StringBuilder((length + 1) * 30);
            sb.append(JsonHelper.typeToString(this.f10659b));
            sb.append("<");
            sb.append(JsonHelper.typeToString(this.f10660c[0]));
            for (int i2 = 1; i2 < length; i2++) {
                sb.append(", ");
                sb.append(JsonHelper.typeToString(this.f10660c[i2]));
            }
            sb.append(">");
            return sb.toString();
        }
    }

    public static final class c implements Serializable, WildcardType {

        /* renamed from: a, reason: collision with root package name */
        private final Type f10661a;

        /* renamed from: b, reason: collision with root package name */
        private final Type f10662b;

        public c(Type[] typeArr, Type[] typeArr2) {
            JsonPreconditions.checkArgument(typeArr2.length <= 1);
            JsonPreconditions.checkArgument(typeArr.length == 1);
            if (typeArr2.length != 1) {
                JsonPreconditions.checkNotNull(typeArr[0]);
                JsonHelper.checkNotPrimitive(typeArr[0]);
                this.f10662b = null;
                this.f10661a = JsonHelper.canonicalize(typeArr[0]);
                return;
            }
            JsonPreconditions.checkNotNull(typeArr2[0]);
            JsonHelper.checkNotPrimitive(typeArr2[0]);
            JsonPreconditions.checkArgument(typeArr[0] == Object.class);
            this.f10662b = JsonHelper.canonicalize(typeArr2[0]);
            this.f10661a = Object.class;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof WildcardType) && JsonHelper.equals(this, (WildcardType) obj);
        }

        @Override // java.lang.reflect.WildcardType
        public final Type[] getLowerBounds() {
            Type type = this.f10662b;
            return type != null ? new Type[]{type} : JsonHelper.EMPTY_TYPE_ARRAY;
        }

        @Override // java.lang.reflect.WildcardType
        public final Type[] getUpperBounds() {
            return new Type[]{this.f10661a};
        }

        public final int hashCode() {
            Type type = this.f10662b;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.f10661a.hashCode() + 31);
        }

        public final String toString() {
            StringBuilder sb;
            Type type;
            if (this.f10662b != null) {
                sb = new StringBuilder("? super ");
                type = this.f10662b;
            } else {
                if (this.f10661a == Object.class) {
                    return "?";
                }
                sb = new StringBuilder("? extends ");
                type = this.f10661a;
            }
            sb.append(JsonHelper.typeToString(type));
            return sb.toString();
        }
    }

    private JsonHelper() {
        throw new UnsupportedOperationException();
    }

    public static GenericArrayType arrayOf(Type type) {
        return new a(type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.nirvana.tools.jsoner.JsonHelper$a] */
    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class aVar = (Class) type;
            if (aVar.isArray()) {
                aVar = new a(canonicalize(aVar.getComponentType()));
            }
            return aVar;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new b(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new a(((GenericArrayType) type).getGenericComponentType());
        }
        if (!(type instanceof WildcardType)) {
            return type;
        }
        WildcardType wildcardType = (WildcardType) type;
        return new c(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
    }

    public static void checkNotPrimitive(Type type) {
        JsonPreconditions.checkArgument(((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true);
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    public static boolean equal(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static boolean equals(Type type, Type type2) {
        while (type != type2) {
            if (type instanceof Class) {
                return type.equals(type2);
            }
            if (type instanceof ParameterizedType) {
                if (!(type2 instanceof ParameterizedType)) {
                    return false;
                }
                ParameterizedType parameterizedType = (ParameterizedType) type;
                ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                return equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
            }
            if (!(type instanceof GenericArrayType)) {
                if (type instanceof WildcardType) {
                    if (!(type2 instanceof WildcardType)) {
                        return false;
                    }
                    WildcardType wildcardType = (WildcardType) type;
                    WildcardType wildcardType2 = (WildcardType) type2;
                    return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
                }
                if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
                    return false;
                }
                TypeVariable typeVariable = (TypeVariable) type;
                TypeVariable typeVariable2 = (TypeVariable) type2;
                return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
            }
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            type = ((GenericArrayType) type).getGenericComponentType();
            type2 = ((GenericArrayType) type2).getGenericComponentType();
        }
        return true;
    }

    public static Type getArrayComponentType(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }

    public static Type getCollectionElementType(Type type, Class<?> cls) {
        Type supertype = getSupertype(type, cls, Collection.class);
        if (supertype instanceof WildcardType) {
            supertype = ((WildcardType) supertype).getUpperBounds()[0];
        }
        return supertype instanceof ParameterizedType ? ((ParameterizedType) supertype).getActualTypeArguments()[0] : Object.class;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034 A[LOOP:2: B:18:0x0034->B:28:0x0051, LOOP_START, PHI: r5
      0x0034: PHI (r5v2 java.lang.Class<?>) = (r5v1 java.lang.Class<?>), (r5v4 java.lang.Class<?>) binds: [B:17:0x0032, B:28:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0053 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.reflect.Type getGenericSupertype(java.lang.reflect.Type r4, java.lang.Class<?> r5, java.lang.Class<?> r6) {
        /*
        L0:
            if (r6 != r5) goto L3
            return r4
        L3:
            boolean r4 = r6.isInterface()
            if (r4 == 0) goto L2e
            java.lang.Class[] r4 = r5.getInterfaces()
            int r0 = r4.length
            r1 = 0
        Lf:
            if (r1 >= r0) goto L2e
            r2 = r4[r1]
            if (r2 != r6) goto L1c
            java.lang.reflect.Type[] r4 = r5.getGenericInterfaces()
            r4 = r4[r1]
            return r4
        L1c:
            boolean r2 = r6.isAssignableFrom(r2)
            if (r2 == 0) goto L2b
            java.lang.reflect.Type[] r5 = r5.getGenericInterfaces()
            r5 = r5[r1]
            r4 = r4[r1]
            goto L4d
        L2b:
            int r1 = r1 + 1
            goto Lf
        L2e:
            boolean r4 = r5.isInterface()
            if (r4 != 0) goto L53
        L34:
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            if (r5 == r4) goto L53
            java.lang.Class r4 = r5.getSuperclass()
            if (r4 != r6) goto L43
            java.lang.reflect.Type r4 = r5.getGenericSuperclass()
            return r4
        L43:
            boolean r0 = r6.isAssignableFrom(r4)
            if (r0 == 0) goto L51
            java.lang.reflect.Type r5 = r5.getGenericSuperclass()
        L4d:
            r3 = r5
            r5 = r4
            r4 = r3
            goto L0
        L51:
            r5 = r4
            goto L34
        L53:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nirvana.tools.jsoner.JsonHelper.getGenericSupertype(java.lang.reflect.Type, java.lang.Class, java.lang.Class):java.lang.reflect.Type");
    }

    public static Type[] getMapKeyAndValueTypes(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type supertype = getSupertype(type, cls, Map.class);
        return supertype instanceof ParameterizedType ? ((ParameterizedType) supertype).getActualTypeArguments() : new Type[]{Object.class, Object.class};
    }

    public static Class<?> getRawType(Type type) {
        while (!(type instanceof Class)) {
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                JsonPreconditions.checkArgument(rawType instanceof Class);
                return (Class) rawType;
            }
            if (type instanceof GenericArrayType) {
                return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
            }
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (!(type instanceof WildcardType)) {
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
            }
            type = ((WildcardType) type).getUpperBounds()[0];
        }
        return (Class) type;
    }

    public static Type getSupertype(Type type, Class<?> cls, Class<?> cls2) {
        if (type instanceof WildcardType) {
            type = ((WildcardType) type).getUpperBounds()[0];
        }
        JsonPreconditions.checkArgument(cls2.isAssignableFrom(cls));
        return resolve(type, cls, getGenericSupertype(type, cls, cls2));
    }

    public static int hashCodeOrZero(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    private static int indexOf(Object[] objArr, Object obj) {
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (obj.equals(objArr[i2])) {
                return i2;
            }
        }
        throw new NoSuchElementException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type type, Type type2, Type... typeArr) {
        return new b(type, type2, typeArr);
    }

    public static Type resolve(Type type, Class<?> cls, Type type2) {
        return resolve(type, cls, type2, new HashSet());
    }

    private static Type resolve(Type type, Class<?> cls, Type type2, Collection<TypeVariable> collection) {
        while (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            if (collection.contains(typeVariable)) {
                return type2;
            }
            collection.add(typeVariable);
            type2 = resolveTypeVariable(type, cls, typeVariable);
            if (type2 == typeVariable) {
                return type2;
            }
        }
        if (type2 instanceof Class) {
            Class cls2 = (Class) type2;
            if (cls2.isArray()) {
                Class<?> componentType = cls2.getComponentType();
                Type typeResolve = resolve(type, cls, componentType, collection);
                return componentType == typeResolve ? cls2 : arrayOf(typeResolve);
            }
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type2;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            Type typeResolve2 = resolve(type, cls, genericComponentType, collection);
            return genericComponentType == typeResolve2 ? genericArrayType : arrayOf(typeResolve2);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            Type ownerType = parameterizedType.getOwnerType();
            Type typeResolve3 = resolve(type, cls, ownerType, collection);
            boolean z2 = typeResolve3 != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int length = actualTypeArguments.length;
            for (int i2 = 0; i2 < length; i2++) {
                Type typeResolve4 = resolve(type, cls, actualTypeArguments[i2], collection);
                if (typeResolve4 != actualTypeArguments[i2]) {
                    if (!z2) {
                        actualTypeArguments = (Type[]) actualTypeArguments.clone();
                        z2 = true;
                    }
                    actualTypeArguments[i2] = typeResolve4;
                }
            }
            return z2 ? newParameterizedTypeWithOwner(typeResolve3, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
        }
        boolean z3 = type2 instanceof WildcardType;
        Type type3 = type2;
        if (z3) {
            WildcardType wildcardType = (WildcardType) type2;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                Type typeResolve5 = resolve(type, cls, lowerBounds[0], collection);
                type3 = wildcardType;
                if (typeResolve5 != lowerBounds[0]) {
                    return supertypeOf(typeResolve5);
                }
            } else {
                type3 = wildcardType;
                if (upperBounds.length == 1) {
                    Type typeResolve6 = resolve(type, cls, upperBounds[0], collection);
                    type3 = wildcardType;
                    if (typeResolve6 != upperBounds[0]) {
                        return subtypeOf(typeResolve6);
                    }
                }
            }
        }
        return type3;
    }

    public static Type resolveTypeVariable(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class<?> clsDeclaringClassOf = declaringClassOf(typeVariable);
        if (clsDeclaringClassOf == null) {
            return typeVariable;
        }
        Type genericSupertype = getGenericSupertype(type, cls, clsDeclaringClassOf);
        if (!(genericSupertype instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) genericSupertype).getActualTypeArguments()[indexOf(clsDeclaringClassOf.getTypeParameters(), typeVariable)];
    }

    public static WildcardType subtypeOf(Type type) {
        return new c(type instanceof WildcardType ? ((WildcardType) type).getUpperBounds() : new Type[]{type}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type type) {
        return new c(new Type[]{Object.class}, type instanceof WildcardType ? ((WildcardType) type).getLowerBounds() : new Type[]{type});
    }

    public static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }
}
