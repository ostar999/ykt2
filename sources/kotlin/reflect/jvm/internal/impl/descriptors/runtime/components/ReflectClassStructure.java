package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
final class ReflectClassStructure {

    @NotNull
    public static final ReflectClassStructure INSTANCE = new ReflectClassStructure();

    private ReflectClassStructure() {
    }

    private final ClassLiteralValue classLiteralValue(Class<?> cls) {
        int i2 = 0;
        while (cls.isArray()) {
            i2++;
            cls = cls.getComponentType();
            Intrinsics.checkNotNullExpressionValue(cls, "currentClass.componentType");
        }
        if (!cls.isPrimitive()) {
            ClassId classId = ReflectClassUtilKt.getClassId(cls);
            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
            FqName fqNameAsSingleFqName = classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "javaClassId.asSingleFqName()");
            ClassId classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlin(fqNameAsSingleFqName);
            if (classIdMapJavaToKotlin != null) {
                classId = classIdMapJavaToKotlin;
            }
            return new ClassLiteralValue(classId, i2);
        }
        if (Intrinsics.areEqual(cls, Void.TYPE)) {
            ClassId classId2 = ClassId.topLevel(StandardNames.FqNames.unit.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(StandardNames.FqNames.unit.toSafe())");
            return new ClassLiteralValue(classId2, i2);
        }
        PrimitiveType primitiveType = JvmPrimitiveType.get(cls.getName()).getPrimitiveType();
        Intrinsics.checkNotNullExpressionValue(primitiveType, "get(currentClass.name).primitiveType");
        if (i2 > 0) {
            ClassId classId3 = ClassId.topLevel(primitiveType.getArrayTypeFqName());
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(primitiveType.arrayTypeFqName)");
            return new ClassLiteralValue(classId3, i2 - 1);
        }
        ClassId classId4 = ClassId.topLevel(primitiveType.getTypeFqName());
        Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(primitiveType.typeFqName)");
        return new ClassLiteralValue(classId4, i2);
    }

    private final void loadConstructorAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Constructor<?>[] constructorArr;
        int i2;
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        Intrinsics.checkNotNullExpressionValue(declaredConstructors, "klass.declaredConstructors");
        int length = declaredConstructors.length;
        int i3 = 0;
        while (i3 < length) {
            Constructor<?> constructor = declaredConstructors[i3];
            Name name = SpecialNames.INIT;
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(constructor, "constructor");
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(name, signatureSerializer.constructorDesc(constructor));
            if (methodAnnotationVisitorVisitMethod == null) {
                constructorArr = declaredConstructors;
                i2 = length;
            } else {
                Annotation[] declaredAnnotations = constructor.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "constructor.declaredAnnotations");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "parameterAnnotations");
                if (!(parameterAnnotations.length == 0)) {
                    int length2 = constructor.getParameterTypes().length - parameterAnnotations.length;
                    int length3 = parameterAnnotations.length;
                    for (int i4 = 0; i4 < length3; i4++) {
                        Annotation[] annotations = parameterAnnotations[i4];
                        Intrinsics.checkNotNullExpressionValue(annotations, "annotations");
                        int length4 = annotations.length;
                        int i5 = 0;
                        while (i5 < length4) {
                            Annotation annotation2 = annotations[i5];
                            Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                            Constructor<?>[] constructorArr2 = declaredConstructors;
                            int i6 = length;
                            ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                            int i7 = length2;
                            Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i4 + length2, classId, new ReflectAnnotationSource(annotation2));
                            if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                                INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                            }
                            i5++;
                            declaredConstructors = constructorArr2;
                            length = i6;
                            length2 = i7;
                        }
                    }
                }
                constructorArr = declaredConstructors;
                i2 = length;
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
            i3++;
            declaredConstructors = constructorArr;
            length = i2;
        }
    }

    private final void loadFieldAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Field[] declaredFields = cls.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "klass.declaredFields");
        for (Field field : declaredFields) {
            Name nameIdentifier = Name.identifier(field.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(field.name)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(field, "field");
            KotlinJvmBinaryClass.AnnotationVisitor annotationVisitorVisitField = memberVisitor.visitField(nameIdentifier, signatureSerializer.fieldDesc(field), null);
            if (annotationVisitorVisitField != null) {
                Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "field.declaredAnnotations");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(annotationVisitorVisitField, annotation);
                }
                annotationVisitorVisitField.visitEnd();
            }
        }
    }

    private final void loadMethodAnnotations(Class<?> cls, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method[] methodArr;
        Method[] declaredMethods = cls.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "klass.declaredMethods");
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            Name nameIdentifier = Name.identifier(method.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(method.name)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(method, "method");
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(nameIdentifier, signatureSerializer.methodDesc(method));
            if (methodAnnotationVisitorVisitMethod == null) {
                methodArr = declaredMethods;
            } else {
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "method.declaredAnnotations");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "method.parameterAnnotations");
                Annotation[][] annotationArr = parameterAnnotations;
                int length2 = annotationArr.length;
                for (int i3 = 0; i3 < length2; i3++) {
                    Annotation[] annotations = annotationArr[i3];
                    Intrinsics.checkNotNullExpressionValue(annotations, "annotations");
                    int length3 = annotations.length;
                    int i4 = 0;
                    while (i4 < length3) {
                        Annotation annotation2 = annotations[i4];
                        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                        ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                        Method[] methodArr2 = declaredMethods;
                        Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i3, classId, new ReflectAnnotationSource(annotation2));
                        if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                            INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                        }
                        i4++;
                        declaredMethods = methodArr2;
                    }
                }
                methodArr = declaredMethods;
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
            i2++;
            declaredMethods = methodArr;
        }
    }

    private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor, Annotation annotation) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationVisitor.visitAnnotation(ReflectClassUtilKt.getClassId(javaClass), new ReflectAnnotationSource(annotation));
        if (annotationArgumentVisitorVisitAnnotation != null) {
            INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, annotation, javaClass);
        }
    }

    private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor, Name name, Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> enclosingClass = obj.getClass();
        if (Intrinsics.areEqual(enclosingClass, Class.class)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.lang.Class<*>");
            annotationArgumentVisitor.visitClassLiteral(name, classLiteralValue((Class) obj));
            return;
        }
        if (ReflectKotlinClassKt.TYPES_ELIGIBLE_FOR_SIMPLE_VISIT.contains(enclosingClass)) {
            annotationArgumentVisitor.visit(name, obj);
            return;
        }
        if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(enclosingClass)) {
            if (!enclosingClass.isEnum()) {
                enclosingClass = enclosingClass.getEnclosingClass();
            }
            Intrinsics.checkNotNullExpressionValue(enclosingClass, "if (clazz.isEnum) clazz else clazz.enclosingClass");
            ClassId classId = ReflectClassUtilKt.getClassId(enclosingClass);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Enum<*>");
            Name nameIdentifier = Name.identifier(((Enum) obj).name());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier((value as Enum<*>).name)");
            annotationArgumentVisitor.visitEnum(name, classId, nameIdentifier);
            return;
        }
        if (Annotation.class.isAssignableFrom(enclosingClass)) {
            Class<?>[] interfaces = enclosingClass.getInterfaces();
            Intrinsics.checkNotNullExpressionValue(interfaces, "clazz.interfaces");
            Class<?> annotationClass = (Class) ArraysKt___ArraysKt.single(interfaces);
            Intrinsics.checkNotNullExpressionValue(annotationClass, "annotationClass");
            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationArgumentVisitor.visitAnnotation(name, ReflectClassUtilKt.getClassId(annotationClass));
            if (annotationArgumentVisitorVisitAnnotation == null) {
                return;
            }
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Annotation");
            processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, (Annotation) obj, annotationClass);
            return;
        }
        if (!enclosingClass.isArray()) {
            throw new UnsupportedOperationException("Unsupported annotation argument value (" + enclosingClass + "): " + obj);
        }
        KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor annotationArrayArgumentVisitorVisitArray = annotationArgumentVisitor.visitArray(name);
        if (annotationArrayArgumentVisitorVisitArray == null) {
            return;
        }
        Class<?> componentType = enclosingClass.getComponentType();
        int i2 = 0;
        if (componentType.isEnum()) {
            Intrinsics.checkNotNullExpressionValue(componentType, "componentType");
            ClassId classId2 = ReflectClassUtilKt.getClassId(componentType);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<*>");
            Object[] objArr = (Object[]) obj;
            int length = objArr.length;
            while (i2 < length) {
                Object obj2 = objArr[i2];
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Enum<*>");
                Name nameIdentifier2 = Name.identifier(((Enum) obj2).name());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier((element as Enum<*>).name)");
                annotationArrayArgumentVisitorVisitArray.visitEnum(classId2, nameIdentifier2);
                i2++;
            }
        } else if (Intrinsics.areEqual(componentType, Class.class)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<*>");
            Object[] objArr2 = (Object[]) obj;
            int length2 = objArr2.length;
            while (i2 < length2) {
                Object obj3 = objArr2[i2];
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type java.lang.Class<*>");
                annotationArrayArgumentVisitorVisitArray.visitClassLiteral(classLiteralValue((Class) obj3));
                i2++;
            }
        } else if (Annotation.class.isAssignableFrom(componentType)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<*>");
            Object[] objArr3 = (Object[]) obj;
            int length3 = objArr3.length;
            while (i2 < length3) {
                Object obj4 = objArr3[i2];
                Intrinsics.checkNotNullExpressionValue(componentType, "componentType");
                KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation2 = annotationArrayArgumentVisitorVisitArray.visitAnnotation(ReflectClassUtilKt.getClassId(componentType));
                if (annotationArgumentVisitorVisitAnnotation2 != null) {
                    Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Annotation");
                    processAnnotationArguments(annotationArgumentVisitorVisitAnnotation2, (Annotation) obj4, componentType);
                }
                i2++;
            }
        } else {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<*>");
            Object[] objArr4 = (Object[]) obj;
            int length4 = objArr4.length;
            while (i2 < length4) {
                annotationArrayArgumentVisitorVisitArray.visit(objArr4[i2]);
                i2++;
            }
        }
        annotationArrayArgumentVisitorVisitArray.visitEnd();
    }

    private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor, Annotation annotation, Class<?> cls) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method[] declaredMethods = cls.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "annotationType.declaredMethods");
        for (Method method : declaredMethods) {
            try {
                Object objInvoke = method.invoke(annotation, new Object[0]);
                Intrinsics.checkNotNull(objInvoke);
                Name nameIdentifier = Name.identifier(method.getName());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(method.name)");
                processAnnotationArgumentValue(annotationArgumentVisitor, nameIdentifier, objInvoke);
            } catch (IllegalAccessException unused) {
            }
        }
        annotationArgumentVisitor.visitEnd();
    }

    public final void loadClassAnnotations(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.AnnotationVisitor visitor) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Annotation[] declaredAnnotations = klass.getDeclaredAnnotations();
        Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "klass.declaredAnnotations");
        for (Annotation annotation : declaredAnnotations) {
            Intrinsics.checkNotNullExpressionValue(annotation, "annotation");
            processAnnotation(visitor, annotation);
        }
        visitor.visitEnd();
    }

    public final void visitMembers(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(memberVisitor, "memberVisitor");
        loadMethodAnnotations(klass, memberVisitor);
        loadConstructorAnnotations(klass, memberVisitor);
        loadFieldAnnotations(klass, memberVisitor);
    }
}
