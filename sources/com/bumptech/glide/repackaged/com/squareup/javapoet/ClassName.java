package com.bumptech.glide.repackaged.com.squareup.javapoet;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public final class ClassName extends TypeName implements Comparable<ClassName> {
    public static final ClassName OBJECT = get((Class<?>) Object.class);
    final String canonicalName;
    final List<String> names;

    private ClassName(List<String> list) {
        this(list, new ArrayList());
    }

    public static ClassName bestGuess(String str) {
        ArrayList arrayList = new ArrayList();
        int iIndexOf = 0;
        while (iIndexOf < str.length() && Character.isLowerCase(str.codePointAt(iIndexOf))) {
            iIndexOf = str.indexOf(46, iIndexOf) + 1;
            Util.checkArgument(iIndexOf != 0, "couldn't make a guess for %s", str);
        }
        arrayList.add(iIndexOf != 0 ? str.substring(0, iIndexOf - 1) : "");
        for (String str2 : str.substring(iIndexOf).split("\\.", -1)) {
            Util.checkArgument(!str2.isEmpty() && Character.isUpperCase(str2.codePointAt(0)), "couldn't make a guess for %s", str);
            arrayList.add(str2);
        }
        Util.checkArgument(arrayList.size() >= 2, "couldn't make a guess for %s", str);
        return new ClassName(arrayList);
    }

    public static ClassName get(Class<?> cls) {
        Util.checkNotNull(cls, "clazz == null", new Object[0]);
        Util.checkArgument(!cls.isPrimitive(), "primitive types cannot be represented as a ClassName", new Object[0]);
        Util.checkArgument(!Void.TYPE.equals(cls), "'void' type cannot be represented as a ClassName", new Object[0]);
        Util.checkArgument(!cls.isArray(), "array types cannot be represented as a ClassName", new Object[0]);
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (cls.isAnonymousClass()) {
                int iLastIndexOf = cls.getName().lastIndexOf(46);
                if (iLastIndexOf != -1) {
                    String strSubstring = cls.getName().substring(iLastIndexOf + 1);
                    arrayList.add(strSubstring.substring(strSubstring.lastIndexOf(36)));
                }
            } else {
                arrayList.add(cls.getSimpleName());
            }
            Class<?> enclosingClass = cls.getEnclosingClass();
            if (enclosingClass == null) {
                break;
            }
            cls = enclosingClass;
        }
        int iLastIndexOf2 = cls.getName().lastIndexOf(46);
        if (iLastIndexOf2 != -1) {
            arrayList.add(cls.getName().substring(0, iLastIndexOf2));
        }
        Collections.reverse(arrayList);
        return new ClassName(arrayList);
    }

    private static PackageElement getPackage(Element element) {
        while (element.getKind() != ElementKind.PACKAGE) {
            element = element.getEnclosingElement();
        }
        return (PackageElement) element;
    }

    private static boolean isClassOrInterface(Element element) {
        return element.getKind().isClass() || element.getKind().isInterface();
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        return codeWriter.emitAndIndent(codeWriter.lookupName(this));
    }

    public ClassName enclosingClassName() {
        if (this.names.size() == 2) {
            return null;
        }
        return new ClassName(this.names.subList(0, r1.size() - 1));
    }

    public ClassName nestedClass(String str) {
        Util.checkNotNull(str, "name == null", new Object[0]);
        ArrayList arrayList = new ArrayList(this.names.size() + 1);
        arrayList.addAll(this.names);
        arrayList.add(str);
        return new ClassName(arrayList);
    }

    public String packageName() {
        return this.names.get(0);
    }

    public String reflectionName() {
        if (this.names.size() == 2) {
            String strPackageName = packageName();
            if (strPackageName.isEmpty()) {
                return this.names.get(1);
            }
            return strPackageName + StrPool.DOT + this.names.get(1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(topLevelClassName());
        for (String str : simpleNames().subList(1, simpleNames().size())) {
            sb.append(Typography.dollar);
            sb.append(str);
        }
        return sb.toString();
    }

    public String simpleName() {
        return this.names.get(r0.size() - 1);
    }

    public List<String> simpleNames() {
        List<String> list = this.names;
        return list.subList(1, list.size());
    }

    public ClassName topLevelClassName() {
        return new ClassName(this.names.subList(0, 2));
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public TypeName withoutAnnotations() {
        return new ClassName(this.names);
    }

    private ClassName(List<String> list, List<AnnotationSpec> list2) {
        super(list2);
        for (int i2 = 1; i2 < list.size(); i2++) {
            Util.checkArgument(SourceVersion.isName(list.get(i2)), "part '%s' is keyword", list.get(i2));
        }
        this.names = Util.immutableList(list);
        this.canonicalName = (list.get(0).isEmpty() ? Util.join(StrPool.DOT, list.subList(1, list.size())) : Util.join(StrPool.DOT, list)).replace(".$", "$");
    }

    @Override // com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName
    public ClassName annotated(List<AnnotationSpec> list) {
        return new ClassName(this.names, concatAnnotations(list));
    }

    @Override // java.lang.Comparable
    public int compareTo(ClassName className) {
        return this.canonicalName.compareTo(className.canonicalName);
    }

    public static ClassName get(String str, String str2, String... strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        Collections.addAll(arrayList, strArr);
        return new ClassName(arrayList);
    }

    public static ClassName get(TypeElement typeElement) {
        Util.checkNotNull(typeElement, "element == null", new Object[0]);
        ArrayList arrayList = new ArrayList();
        for (TypeElement enclosingElement = typeElement; isClassOrInterface(enclosingElement); enclosingElement = enclosingElement.getEnclosingElement()) {
            Util.checkArgument(typeElement.getNestingKind() == NestingKind.TOP_LEVEL || typeElement.getNestingKind() == NestingKind.MEMBER, "unexpected type testing", new Object[0]);
            arrayList.add(enclosingElement.getSimpleName().toString());
        }
        arrayList.add(getPackage(typeElement).getQualifiedName().toString());
        Collections.reverse(arrayList);
        return new ClassName(arrayList);
    }
}
