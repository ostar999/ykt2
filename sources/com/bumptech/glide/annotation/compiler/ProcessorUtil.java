package com.bumptech.glide.annotation.compiler;

import cn.hutool.core.text.StrPool;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.repackaged.com.google.common.base.Function;
import com.bumptech.glide.repackaged.com.google.common.base.Joiner;
import com.bumptech.glide.repackaged.com.google.common.base.Predicate;
import com.bumptech.glide.repackaged.com.google.common.collect.FluentIterable;
import com.bumptech.glide.repackaged.com.google.common.collect.Lists;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import com.bumptech.glide.repackaged.com.squareup.javapoet.JavaFile;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeVariableName;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Type;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import okhttp3.HttpUrl;

/* loaded from: classes2.dex */
final class ProcessorUtil {
    private final TypeElement appGlideModuleType;
    private final TypeElement libraryGlideModuleType;
    private final ProcessingEnvironment processingEnv;
    private int round;
    private static final String COMPILER_PACKAGE_NAME = GlideAnnotationProcessor.class.getPackage().getName();
    private static final ClassName NONNULL_ANNOTATION = ClassName.get("android.support.annotation", "NonNull", new String[0]);
    private static final ClassName JETBRAINS_NOTNULL_ANNOTATION = ClassName.get("org.jetbrains.annotations", "NotNull", new String[0]);
    private static final ClassName ANDROIDX_NONNULL_ANNOTATION = ClassName.get("androidx.annotation", "NonNull", new String[0]);
    private static final ClassName CHECK_RESULT_ANNOTATION = ClassName.get("android.support.annotation", "CheckResult", new String[0]);
    private static final ClassName ANDROIDX_CHECK_RESULT_ANNOTATION = ClassName.get("androidx.annotation", "CheckResult", new String[0]);

    public final class FilterPublicMethods implements Predicate<Element> {
        private final MethodType methodType;
        private final TypeMirror returnType;

        public FilterPublicMethods(TypeMirror typeMirror, MethodType methodType) {
            this.returnType = typeMirror;
            this.methodType = methodType;
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
        public boolean apply(Element element) {
            if (element == null || element.getKind() != ElementKind.METHOD || !element.getModifiers().contains(Modifier.PUBLIC)) {
                return false;
            }
            boolean zContains = element.getModifiers().contains(Modifier.STATIC);
            MethodType methodType = this.methodType;
            if (methodType == MethodType.STATIC && !zContains) {
                return false;
            }
            if (methodType == MethodType.INSTANCE && zContains) {
                return false;
            }
            ExecutableElement executableElement = (ExecutableElement) element;
            TypeMirror typeMirror = this.returnType;
            return typeMirror == null || ProcessorUtil.this.isReturnValueTypeMatching(executableElement, typeMirror);
        }

        public FilterPublicMethods(ProcessorUtil processorUtil, TypeElement typeElement, MethodType methodType) {
            this(typeElement != null ? typeElement.asType() : null, methodType);
        }
    }

    public enum MethodType {
        STATIC,
        INSTANCE
    }

    public static final class ToMethod implements Function<Element, ExecutableElement> {
        private ToMethod() {
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
        public ExecutableElement apply(Element element) {
            return (ExecutableElement) element;
        }
    }

    public ProcessorUtil(ProcessingEnvironment processingEnvironment) {
        this.processingEnv = processingEnvironment;
        this.appGlideModuleType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.module.AppGlideModule");
        this.libraryGlideModuleType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.module.LibraryGlideModule");
    }

    private static String applySmartParameterNameReplacements(String str) {
        return str.replace(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, "s").replace(Class.class.getSimpleName(), "clazz").replace(Object.class.getSimpleName(), "o");
    }

    public static ClassName checkResult() throws ClassNotFoundException {
        try {
            ClassName className = ANDROIDX_CHECK_RESULT_ANNOTATION;
            Class.forName(className.reflectionName());
            return className;
        } catch (ClassNotFoundException unused) {
            return CHECK_RESULT_ANNOTATION;
        }
    }

    private static String computeParameterName(VariableElement variableElement, TypeName typeName) {
        boolean z2;
        String string = typeName.withoutAnnotations().toString();
        if (typeName.isPrimitive() || typeName.isBoxedPrimitive()) {
            return getSmartPrimitiveParameterName(variableElement);
        }
        if (string.contains("<") && string.contains(">")) {
            String str = string.split("<")[0];
            String[] strArrSplit = string.split(">");
            string = strArrSplit.length > 1 ? str + strArrSplit[strArrSplit.length - 1] : str;
        }
        String[] strArrSplit2 = string.split("\\.");
        String strApplySmartParameterNameReplacements = applySmartParameterNameReplacements(strArrSplit2[strArrSplit2.length - 1]);
        char[] charArray = strApplySmartParameterNameReplacements.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z2 = true;
                break;
            }
            if (Character.isLowerCase(charArray[i2])) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (z2) {
            return strApplySmartParameterNameReplacements.toLowerCase();
        }
        char[] charArray2 = strApplySmartParameterNameReplacements.toCharArray();
        int length2 = charArray2.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length2; i4++) {
            if (Character.isUpperCase(charArray2[i4])) {
                i3 = i4;
            }
        }
        String strSubstring = strApplySmartParameterNameReplacements.substring(i3, strApplySmartParameterNameReplacements.length());
        return Character.toLowerCase(strSubstring.charAt(0)) + strSubstring.substring(1, strSubstring.length());
    }

    private static List<ParameterSpec> dedupedParameters(List<ParameterSpec> list) {
        HashSet hashSet = new HashSet();
        Iterator<ParameterSpec> it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            String str = it.next().name;
            if (hashSet.contains(str)) {
                z2 = true;
            } else {
                hashSet.add(str);
            }
        }
        if (!z2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ParameterSpec parameterSpec = list.get(i2);
            arrayList.add(ParameterSpec.builder(parameterSpec.type, parameterSpec.name + i2, new Modifier[0]).addModifiers(parameterSpec.modifiers).addAnnotations(parameterSpec.annotations).build());
        }
        return arrayList;
    }

    public static CodeBlock generateCastingSuperCall(TypeName typeName, MethodSpec methodSpec) {
        return CodeBlock.builder().add("return ($T) super.$N(", typeName, methodSpec.name).add(FluentIterable.from(methodSpec.parameters).transform(new Function<ParameterSpec, String>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.3
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public String apply(ParameterSpec parameterSpec) {
                return parameterSpec.name;
            }
        }).join(Joiner.on(",")), new Object[0]).add(");\n", new Object[0]).build();
    }

    private CodeBlock generateSeeMethodJavadocInternal(TypeName typeName, String str, List<Object> list) {
        StringBuilder sb = new StringBuilder("@see $T#$L(");
        ArrayList arrayList = new ArrayList();
        arrayList.add(typeName);
        arrayList.add(str);
        for (Object obj : list) {
            sb.append("$T, ");
            arrayList.add(obj);
        }
        if (arrayList.size() > 2) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        sb.append(")\n");
        return CodeBlock.of(sb.toString(), arrayList.toArray(new Object[0]));
    }

    private static List<AnnotationSpec> getAnnotations(VariableElement variableElement) {
        ArrayList arrayList = new ArrayList();
        Iterator it = variableElement.getAnnotationMirrors().iterator();
        while (it.hasNext()) {
            arrayList.add(AnnotationSpec.get((AnnotationMirror) it.next()));
        }
        return arrayList;
    }

    private static String getExcludedModuleClassFromAnnotationAttribute(Element element, Object obj) throws SecurityException {
        if (obj.getClass().getSimpleName().equals("UnresolvedClass")) {
            throw new IllegalArgumentException("Failed to parse @Excludes for: " + element + ", one or more excluded Modules could not be found at compile time. Ensure that allexcluded Modules are included in your classpath.");
        }
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();
        if (declaredMethods == null || declaredMethods.length == 0) {
            throw new IllegalArgumentException("Failed to parse @Excludes for: " + element + ", invalid exclude: " + obj);
        }
        for (Method method : declaredMethods) {
            if (method.getName().equals("getValue")) {
                try {
                    return method.invoke(obj, new Object[0]).toString();
                } catch (IllegalAccessException | InvocationTargetException e2) {
                    throw new IllegalArgumentException("Failed to parse @Excludes for: " + element, e2);
                }
            }
        }
        throw new IllegalArgumentException("Failed to parse @Excludes for: " + element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TypeName getJavadocSafeName(Element element) {
        Types typeUtils = this.processingEnv.getTypeUtils();
        TypeMirror typeMirrorAsType = element.asType();
        return typeUtils.asElement(typeMirrorAsType) == null ? TypeName.get(element.asType()) : ClassName.bestGuess(typeUtils.asElement(typeMirrorAsType).getSimpleName().toString());
    }

    private static ParameterSpec getParameter(VariableElement variableElement) {
        TypeName typeName = TypeName.get(variableElement.asType());
        return ParameterSpec.builder(typeName, computeParameterName(variableElement, typeName), new Modifier[0]).addModifiers(variableElement.getModifiers()).addAnnotations(getAnnotations(variableElement)).build();
    }

    public static List<ParameterSpec> getParameters(ExecutableElement executableElement) {
        return getParameters((List<? extends VariableElement>) executableElement.getParameters());
    }

    private static String getSmartPrimitiveParameterName(VariableElement variableElement) {
        Iterator it = variableElement.getAnnotationMirrors().iterator();
        while (it.hasNext()) {
            String upperCase = ((AnnotationMirror) it.next()).getAnnotationType().toString().toUpperCase();
            if (upperCase.endsWith("RES")) {
                return "id";
            }
            if (upperCase.endsWith("RANGE")) {
                return "value";
            }
        }
        return variableElement.getSimpleName().toString();
    }

    public static ClassName nonNull() throws ClassNotFoundException {
        try {
            ClassName className = ANDROIDX_NONNULL_ANNOTATION;
            Class.forName(className.reflectionName());
            return className;
        } catch (ClassNotFoundException unused) {
            return NONNULL_ANNOTATION;
        }
    }

    public static List<ClassName> nonNulls() {
        return Arrays.asList(NONNULL_ANNOTATION, JETBRAINS_NOTNULL_ANNOTATION, ANDROIDX_NONNULL_ANNOTATION);
    }

    public static MethodSpec.Builder overriding(ExecutableElement executableElement) {
        Modifier modifierValueOf;
        MethodSpec.Builder builderAddAnnotation = MethodSpec.methodBuilder(executableElement.getSimpleName().toString()).addAnnotation(Override.class);
        LinkedHashSet linkedHashSet = new LinkedHashSet(executableElement.getModifiers());
        linkedHashSet.remove(Modifier.ABSTRACT);
        try {
            modifierValueOf = Modifier.valueOf("DEFAULT");
        } catch (IllegalArgumentException unused) {
            modifierValueOf = null;
        }
        linkedHashSet.remove(modifierValueOf);
        MethodSpec.Builder builderAddModifiers = builderAddAnnotation.addModifiers(linkedHashSet);
        Iterator it = executableElement.getTypeParameters().iterator();
        while (it.hasNext()) {
            builderAddModifiers = builderAddModifiers.addTypeVariable(TypeVariableName.get(((TypeParameterElement) it.next()).asType()));
        }
        MethodSpec.Builder builderVarargs = builderAddModifiers.returns(TypeName.get(executableElement.getReturnType())).addParameters(getParameters(executableElement)).varargs(executableElement.isVarArgs());
        Iterator it2 = executableElement.getThrownTypes().iterator();
        while (it2.hasNext()) {
            builderVarargs = builderVarargs.addException(TypeName.get((TypeMirror) it2.next()));
        }
        return builderVarargs;
    }

    public void debugLog(String str) {
    }

    public List<ExecutableElement> findAnnotatedElementsInClasses(Set<String> set, Class<? extends Annotation> cls) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            for (ExecutableElement executableElement : this.processingEnv.getElementUtils().getTypeElement(it.next()).getEnclosedElements()) {
                if (executableElement.getAnnotation(cls) != null) {
                    arrayList.add(executableElement);
                }
            }
        }
        return arrayList;
    }

    public Set<String> findClassValuesFromAnnotationOnClassAsNames(Element element, Class<? extends Annotation> cls) {
        String name = cls.getName();
        AnnotationValue annotationValue = null;
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (name.equals(annotationMirror.getAnnotationType().toString())) {
                Set setEntrySet = annotationMirror.getElementValues().entrySet();
                if (setEntrySet.size() != 1) {
                    throw new IllegalArgumentException("Expected single value, but found: " + setEntrySet);
                }
                annotationValue = (AnnotationValue) ((Map.Entry) setEntrySet.iterator().next()).getValue();
                if (annotationValue == null || (annotationValue instanceof Attribute.UnresolvedClass)) {
                    throw new IllegalArgumentException("Failed to find value for: " + cls + " from mirrors: " + element.getAnnotationMirrors());
                }
            }
        }
        if (annotationValue == null) {
            return Collections.emptySet();
        }
        Object value = annotationValue.getValue();
        if (!(value instanceof List)) {
            return Collections.singleton(((Type.ClassType) value).toString());
        }
        List list = (List) value;
        HashSet hashSet = new HashSet(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            hashSet.add(getExcludedModuleClassFromAnnotationAttribute(element, it.next()));
        }
        return hashSet;
    }

    public List<ExecutableElement> findInstanceMethodsReturning(TypeElement typeElement, TypeMirror typeMirror) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(typeMirror, MethodType.INSTANCE)).transform(new ToMethod()).toList();
    }

    public List<ExecutableElement> findStaticMethods(TypeElement typeElement) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods((TypeMirror) null, MethodType.STATIC)).transform(new ToMethod()).toList();
    }

    public List<ExecutableElement> findStaticMethodsReturning(TypeElement typeElement, TypeElement typeElement2) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(this, typeElement2, MethodType.STATIC)).transform(new ToMethod()).toList();
    }

    public CodeBlock generateSeeMethodJavadoc(ExecutableElement executableElement) {
        return generateSeeMethodJavadoc(getJavadocSafeName(executableElement.getEnclosingElement()), executableElement.getSimpleName().toString(), executableElement.getParameters());
    }

    public List<TypeElement> getElementsFor(Class<? extends Annotation> cls, RoundEnvironment roundEnvironment) {
        return ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(cls));
    }

    public int getOverrideType(ExecutableElement executableElement) {
        return ((GlideOption) executableElement.getAnnotation(GlideOption.class)).override();
    }

    public void infoLog(String str) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, StrPool.BRACKET_START + this.round + "] " + str);
    }

    public boolean isAppGlideModule(TypeElement typeElement) {
        return this.processingEnv.getTypeUtils().isAssignable(typeElement.asType(), this.appGlideModuleType.asType());
    }

    public boolean isExtension(TypeElement typeElement) {
        return typeElement.getAnnotation(GlideExtension.class) != null;
    }

    public boolean isLibraryGlideModule(TypeElement typeElement) {
        return this.processingEnv.getTypeUtils().isAssignable(typeElement.asType(), this.libraryGlideModuleType.asType());
    }

    public boolean isReturnValueTypeMatching(ExecutableElement executableElement, TypeElement typeElement) {
        return isReturnValueTypeMatching(executableElement, typeElement.asType());
    }

    public void process() {
        this.round++;
    }

    public void writeClass(String str, TypeSpec typeSpec) {
        try {
            debugLog("Writing class:\n" + typeSpec);
            JavaFile.builder(str, typeSpec).build().writeTo(this.processingEnv.getFiler());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public void writeIndexer(TypeSpec typeSpec) {
        writeClass(COMPILER_PACKAGE_NAME, typeSpec);
    }

    public static List<ParameterSpec> getParameters(List<? extends VariableElement> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends VariableElement> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getParameter(it.next()));
        }
        return dedupedParameters(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReturnValueTypeMatching(ExecutableElement executableElement, TypeMirror typeMirror) {
        return this.processingEnv.getTypeUtils().isAssignable(executableElement.getReturnType(), typeMirror);
    }

    public CodeBlock generateSeeMethodJavadoc(TypeName typeName, String str, List<? extends VariableElement> list) {
        return generateSeeMethodJavadocInternal(typeName, str, Lists.transform(list, new Function<VariableElement, Object>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public Object apply(VariableElement variableElement) {
                return ProcessorUtil.this.getJavadocSafeName(variableElement);
            }
        }));
    }

    public List<ExecutableElement> findInstanceMethodsReturning(TypeElement typeElement, TypeElement typeElement2) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(this, typeElement2, MethodType.INSTANCE)).transform(new ToMethod()).toList();
    }

    public CodeBlock generateSeeMethodJavadoc(TypeName typeName, MethodSpec methodSpec) {
        return generateSeeMethodJavadocInternal(typeName, methodSpec.name, Lists.transform(methodSpec.parameters, new Function<ParameterSpec, Object>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.2
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public Object apply(ParameterSpec parameterSpec) {
                return parameterSpec.type;
            }
        }));
    }
}
