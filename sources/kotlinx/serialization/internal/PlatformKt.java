package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Polymorphic;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.SerializationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aO\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\"\u0010\u0006\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00010\u0007\"\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001H\u0002¢\u0006\u0002\u0010\b\u001a\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u0000\u001a\u0012\u0010\r\u001a\u0004\u0018\u00010\u0003*\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a$\u0010\u000e\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\fH\u0000\u001aM\u0010\u000f\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00052\"\u0010\u0006\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00010\u0007\"\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001H\u0000¢\u0006\u0002\u0010\b\u001aM\u0010\u000f\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\f2\"\u0010\u0006\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00010\u0007\"\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001H\u0000¢\u0006\u0002\u0010\u0010\u001a\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0002\u001a$\u0010\u0012\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0002\u001a&\u0010\u0013\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015H\u0080\b¢\u0006\u0002\u0010\u0016\u001a\u0015\u0010\u0013\u001a\u00020\n*\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0080\b\u001a$\u0010\u0018\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0002\u001a\u001c\u0010\u0019\u001a\u00020\n\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0002\u001a\u001c\u0010\u001a\u001a\u00020\n\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0002\u001a\u0010\u0010\u001b\u001a\u00020\u001c*\u0006\u0012\u0002\b\u00030\fH\u0000\u001a\u0010\u0010\u001d\u001a\u00020\u001c*\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001aK\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001f0\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\n\b\u0001\u0010\u001f*\u0004\u0018\u0001H\u0002*\u0012\u0012\u0004\u0012\u0002H\u001f0 j\b\u0012\u0004\u0012\u0002H\u001f`!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\fH\u0000¢\u0006\u0002\u0010#¨\u0006$"}, d2 = {"invokeSerializerOnCompanion", "Lkotlinx/serialization/KSerializer;", ExifInterface.GPS_DIRECTION_TRUE, "", "jClass", "Ljava/lang/Class;", AliyunLogKey.KEY_ARGS, "", "(Ljava/lang/Class;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "isReferenceArray", "", "rootClass", "Lkotlin/reflect/KClass;", "companionOrNull", "compiledSerializerImpl", "constructSerializerForGivenTypeArgs", "(Lkotlin/reflect/KClass;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "createEnumSerializer", "findObjectSerializer", "getChecked", "index", "", "([Ljava/lang/Object;I)Ljava/lang/Object;", "", "interfaceSerializer", "isNotAnnotated", "isPolymorphicSerializer", "platformSpecificSerializerNotRegistered", "", "serializerNotRegistered", "toNativeArrayImpl", "E", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "eClass", "(Ljava/util/ArrayList;Lkotlin/reflect/KClass;)[Ljava/lang/Object;", "kotlinx-serialization-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPlatform.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Platform.kt\nkotlinx/serialization/internal/PlatformKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,149:1\n1#2:150\n3133#3,11:151\n3133#3,11:163\n3133#3,11:174\n26#4:162\n*S KotlinDebug\n*F\n+ 1 Platform.kt\nkotlinx/serialization/internal/PlatformKt\n*L\n53#1:151,11\n136#1:163,11\n141#1:174,11\n107#1:162\n*E\n"})
/* loaded from: classes8.dex */
public final class PlatformKt {
    private static final Object companionOrNull(Class<?> cls) {
        try {
            Field declaredField = cls.getDeclaredField("Companion");
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Throwable unused) {
            return null;
        }
    }

    @Nullable
    public static final <T> KSerializer<T> compiledSerializerImpl(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        return constructSerializerForGivenTypeArgs(kClass, (KSerializer<Object>[]) new KSerializer[0]);
    }

    @Nullable
    public static final <T> KSerializer<T> constructSerializerForGivenTypeArgs(@NotNull KClass<T> kClass, @NotNull KSerializer<Object>... args) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        return constructSerializerForGivenTypeArgs(JvmClassMappingKt.getJavaClass((KClass) kClass), (KSerializer<Object>[]) Arrays.copyOf(args, args.length));
    }

    private static final <T> KSerializer<T> createEnumSerializer(Class<T> cls) {
        T[] enumConstants = cls.getEnumConstants();
        String canonicalName = cls.getCanonicalName();
        Intrinsics.checkNotNullExpressionValue(canonicalName, "canonicalName");
        Intrinsics.checkNotNull(enumConstants, "null cannot be cast to non-null type kotlin.Array<out kotlin.Enum<*>>");
        return new EnumSerializer(canonicalName, (Enum[]) enumConstants);
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0096, code lost:
    
        r6 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final <T> kotlinx.serialization.KSerializer<T> findObjectSerializer(java.lang.Class<T> r11) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            java.lang.reflect.Field[] r0 = r11.getDeclaredFields()
            java.lang.String r1 = "declaredFields"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            int r1 = r0.length
            r2 = 0
            r3 = 0
            r6 = r2
            r4 = r3
            r5 = r4
        Lf:
            r7 = 1
            if (r4 >= r1) goto L41
            r8 = r0[r4]
            java.lang.String r9 = r8.getName()
            java.lang.String r10 = "INSTANCE"
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)
            if (r9 == 0) goto L36
            java.lang.Class r9 = r8.getType()
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r11)
            if (r9 == 0) goto L36
            int r9 = r8.getModifiers()
            boolean r9 = java.lang.reflect.Modifier.isStatic(r9)
            if (r9 == 0) goto L36
            r9 = r7
            goto L37
        L36:
            r9 = r3
        L37:
            if (r9 == 0) goto L3e
            if (r5 == 0) goto L3c
            goto L43
        L3c:
            r5 = r7
            r6 = r8
        L3e:
            int r4 = r4 + 1
            goto Lf
        L41:
            if (r5 != 0) goto L44
        L43:
            r6 = r2
        L44:
            java.lang.reflect.Field r6 = (java.lang.reflect.Field) r6
            if (r6 != 0) goto L49
            return r2
        L49:
            java.lang.Object r0 = r6.get(r2)
            java.lang.reflect.Method[] r11 = r11.getMethods()
            java.lang.String r1 = "methods"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r1)
            int r1 = r11.length
            r6 = r2
            r4 = r3
            r5 = r4
        L5a:
            if (r4 >= r1) goto L94
            r8 = r11[r4]
            java.lang.String r9 = r8.getName()
            java.lang.String r10 = "serializer"
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)
            if (r9 == 0) goto L89
            java.lang.Class[] r9 = r8.getParameterTypes()
            java.lang.String r10 = "it.parameterTypes"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r10)
            int r9 = r9.length
            if (r9 != 0) goto L78
            r9 = r7
            goto L79
        L78:
            r9 = r3
        L79:
            if (r9 == 0) goto L89
            java.lang.Class r9 = r8.getReturnType()
            java.lang.Class<kotlinx.serialization.KSerializer> r10 = kotlinx.serialization.KSerializer.class
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)
            if (r9 == 0) goto L89
            r9 = r7
            goto L8a
        L89:
            r9 = r3
        L8a:
            if (r9 == 0) goto L91
            if (r5 == 0) goto L8f
            goto L96
        L8f:
            r5 = r7
            r6 = r8
        L91:
            int r4 = r4 + 1
            goto L5a
        L94:
            if (r5 != 0) goto L97
        L96:
            r6 = r2
        L97:
            java.lang.reflect.Method r6 = (java.lang.reflect.Method) r6
            if (r6 != 0) goto L9c
            return r2
        L9c:
            java.lang.Object[] r11 = new java.lang.Object[r3]
            java.lang.Object r11 = r6.invoke(r0, r11)
            boolean r0 = r11 instanceof kotlinx.serialization.KSerializer
            if (r0 == 0) goto La9
            r2 = r11
            kotlinx.serialization.KSerializer r2 = (kotlinx.serialization.KSerializer) r2
        La9:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.serialization.internal.PlatformKt.findObjectSerializer(java.lang.Class):kotlinx.serialization.KSerializer");
    }

    public static final <T> T getChecked(@NotNull T[] tArr, int i2) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return tArr[i2];
    }

    private static final <T> KSerializer<T> interfaceSerializer(Class<T> cls) {
        Serializable serializable = (Serializable) cls.getAnnotation(Serializable.class);
        if (serializable == null || Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(serializable.with()), Reflection.getOrCreateKotlinClass(PolymorphicSerializer.class))) {
            return new PolymorphicSerializer(JvmClassMappingKt.getKotlinClass(cls));
        }
        return null;
    }

    private static final <T> KSerializer<T> invokeSerializerOnCompanion(Class<?> cls, KSerializer<Object>... kSerializerArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class[] clsArr;
        Object objCompanionOrNull = companionOrNull(cls);
        if (objCompanionOrNull == null) {
            return null;
        }
        try {
            if (kSerializerArr.length == 0) {
                clsArr = new Class[0];
            } else {
                int length = kSerializerArr.length;
                Class[] clsArr2 = new Class[length];
                for (int i2 = 0; i2 < length; i2++) {
                    clsArr2[i2] = KSerializer.class;
                }
                clsArr = clsArr2;
            }
            Object objInvoke = objCompanionOrNull.getClass().getDeclaredMethod("serializer", (Class[]) Arrays.copyOf(clsArr, clsArr.length)).invoke(objCompanionOrNull, Arrays.copyOf(kSerializerArr, kSerializerArr.length));
            if (objInvoke instanceof KSerializer) {
                return (KSerializer) objInvoke;
            }
            return null;
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause == null) {
                throw e2;
            }
            String message = cause.getMessage();
            if (message == null) {
                message = e2.getMessage();
            }
            throw new InvocationTargetException(cause, message);
        }
    }

    private static final <T> boolean isNotAnnotated(Class<T> cls) {
        return cls.getAnnotation(Serializable.class) == null && cls.getAnnotation(Polymorphic.class) == null;
    }

    private static final <T> boolean isPolymorphicSerializer(Class<T> cls) {
        if (cls.getAnnotation(Polymorphic.class) != null) {
            return true;
        }
        Serializable serializable = (Serializable) cls.getAnnotation(Serializable.class);
        return serializable != null && Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(serializable.with()), Reflection.getOrCreateKotlinClass(PolymorphicSerializer.class));
    }

    public static final boolean isReferenceArray(@NotNull KClass<Object> rootClass) {
        Intrinsics.checkNotNullParameter(rootClass, "rootClass");
        return JvmClassMappingKt.getJavaClass((KClass) rootClass).isArray();
    }

    @NotNull
    public static final Void platformSpecificSerializerNotRegistered(@NotNull KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Platform_commonKt.serializerNotRegistered(kClass);
        throw new KotlinNothingValueException();
    }

    @NotNull
    public static final Void serializerNotRegistered(@NotNull Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        throw new SerializationException(Platform_commonKt.notRegisteredMessage((KClass<?>) JvmClassMappingKt.getKotlinClass(cls)));
    }

    @NotNull
    public static final <T, E extends T> E[] toNativeArrayImpl(@NotNull ArrayList<E> arrayList, @NotNull KClass<T> eClass) throws NegativeArraySizeException {
        Intrinsics.checkNotNullParameter(arrayList, "<this>");
        Intrinsics.checkNotNullParameter(eClass, "eClass");
        Object objNewInstance = Array.newInstance((Class<?>) JvmClassMappingKt.getJavaClass((KClass) eClass), arrayList.size());
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Array<E of kotlinx.serialization.internal.PlatformKt.toNativeArrayImpl>");
        E[] eArr = (E[]) arrayList.toArray((Object[]) objNewInstance);
        Intrinsics.checkNotNullExpressionValue(eArr, "toArray(java.lang.reflec….java, size) as Array<E>)");
        return eArr;
    }

    @Nullable
    public static final <T> KSerializer<T> constructSerializerForGivenTypeArgs(@NotNull Class<T> cls, @NotNull KSerializer<Object>... args) {
        Object obj;
        Field field;
        KSerializer<T> kSerializerInterfaceSerializer;
        Intrinsics.checkNotNullParameter(cls, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        if (cls.isEnum() && isNotAnnotated(cls)) {
            return createEnumSerializer(cls);
        }
        if (cls.isInterface() && (kSerializerInterfaceSerializer = interfaceSerializer(cls)) != null) {
            return kSerializerInterfaceSerializer;
        }
        KSerializer<T> kSerializerInvokeSerializerOnCompanion = invokeSerializerOnCompanion(cls, (KSerializer[]) Arrays.copyOf(args, args.length));
        if (kSerializerInvokeSerializerOnCompanion != null) {
            return kSerializerInvokeSerializerOnCompanion;
        }
        KSerializer<T> kSerializerFindObjectSerializer = findObjectSerializer(cls);
        if (kSerializerFindObjectSerializer != null) {
            return kSerializerFindObjectSerializer;
        }
        try {
            Class<?>[] declaredClasses = cls.getDeclaredClasses();
            Intrinsics.checkNotNullExpressionValue(declaredClasses, "declaredClasses");
            int length = declaredClasses.length;
            int i2 = 0;
            Class<?> cls2 = null;
            boolean z2 = false;
            while (true) {
                if (i2 >= length) {
                    if (!z2) {
                        break;
                    }
                } else {
                    Class<?> cls3 = declaredClasses[i2];
                    if (Intrinsics.areEqual(cls3.getSimpleName(), "$serializer")) {
                        if (z2) {
                            break;
                        }
                        z2 = true;
                        cls2 = cls3;
                    }
                    i2++;
                }
            }
            cls2 = null;
            Class<?> cls4 = cls2;
            obj = (cls4 == null || (field = cls4.getField("INSTANCE")) == null) ? null : field.get(null);
        } catch (NoSuchFieldException unused) {
        }
        KSerializer<T> kSerializer = obj instanceof KSerializer ? (KSerializer) obj : null;
        if (kSerializer != null) {
            return kSerializer;
        }
        if (isPolymorphicSerializer(cls)) {
            return new PolymorphicSerializer(JvmClassMappingKt.getKotlinClass(cls));
        }
        return null;
    }

    public static final boolean getChecked(@NotNull boolean[] zArr, int i2) {
        Intrinsics.checkNotNullParameter(zArr, "<this>");
        return zArr[i2];
    }
}
