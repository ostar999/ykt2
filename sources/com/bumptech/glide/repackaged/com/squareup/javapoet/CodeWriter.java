package com.bumptech.glide.repackaged.com.squareup.javapoet;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;

/* loaded from: classes2.dex */
final class CodeWriter {
    private static final String NO_PACKAGE = new String();
    private boolean comment;
    private final Map<String, ClassName> importableTypes;
    private final Map<String, ClassName> importedTypes;
    private final String indent;
    private int indentLevel;
    private boolean javadoc;
    private final LineWrapper out;
    private String packageName;
    private final Set<String> referencedNames;
    int statementLine;
    private final Set<String> staticImportClassNames;
    private final Set<String> staticImports;
    private boolean trailingNewline;
    private final List<TypeSpec> typeSpecStack;

    public CodeWriter(Appendable appendable) {
        this(appendable, "  ", Collections.emptySet());
    }

    private void emitIndentation() throws IOException {
        for (int i2 = 0; i2 < this.indentLevel; i2++) {
            this.out.append(this.indent);
        }
    }

    private void emitLiteral(Object obj) throws IOException {
        if (obj instanceof TypeSpec) {
            ((TypeSpec) obj).emit(this, null, Collections.emptySet());
            return;
        }
        if (obj instanceof AnnotationSpec) {
            ((AnnotationSpec) obj).emit(this, true);
        } else if (obj instanceof CodeBlock) {
            emit((CodeBlock) obj);
        } else {
            emitAndIndent(String.valueOf(obj));
        }
    }

    private boolean emitStaticImportMember(String str, String str2) throws IOException {
        String strSubstring = str2.substring(1);
        if (strSubstring.isEmpty() || !Character.isJavaIdentifierStart(strSubstring.charAt(0))) {
            return false;
        }
        String str3 = str + StrPool.DOT + extractMemberName(strSubstring);
        String str4 = str + ".*";
        if (!this.staticImports.contains(str3) && !this.staticImports.contains(str4)) {
            return false;
        }
        emitAndIndent(strSubstring);
        return true;
    }

    private static String extractMemberName(String str) {
        Util.checkArgument(Character.isJavaIdentifierStart(str.charAt(0)), "not an identifier: %s", str);
        for (int i2 = 1; i2 <= str.length(); i2++) {
            if (!SourceVersion.isIdentifier(str.substring(0, i2))) {
                return str.substring(0, i2 - 1);
            }
        }
        return str;
    }

    private void importableType(ClassName className) {
        ClassName className2;
        String strSimpleName;
        ClassName classNamePut;
        if (className.packageName().isEmpty() || (classNamePut = this.importableTypes.put((strSimpleName = (className2 = className.topLevelClassName()).simpleName()), className2)) == null) {
            return;
        }
        this.importableTypes.put(strSimpleName, classNamePut);
    }

    private ClassName resolve(String str) {
        for (int size = this.typeSpecStack.size() - 1; size >= 0; size--) {
            Iterator<TypeSpec> it = this.typeSpecStack.get(size).typeSpecs.iterator();
            while (it.hasNext()) {
                if (Objects.equals(it.next().name, str)) {
                    return stackClassName(size, str);
                }
            }
        }
        if (this.typeSpecStack.size() > 0 && Objects.equals(this.typeSpecStack.get(0).name, str)) {
            return ClassName.get(this.packageName, str, new String[0]);
        }
        ClassName className = this.importedTypes.get(str);
        if (className != null) {
            return className;
        }
        return null;
    }

    private ClassName stackClassName(int i2, String str) {
        ClassName classNameNestedClass = ClassName.get(this.packageName, this.typeSpecStack.get(0).name, new String[0]);
        for (int i3 = 1; i3 <= i2; i3++) {
            classNameNestedClass = classNameNestedClass.nestedClass(this.typeSpecStack.get(i3).name);
        }
        return classNameNestedClass.nestedClass(str);
    }

    public CodeWriter emit(String str) throws IOException {
        return emitAndIndent(str);
    }

    public CodeWriter emitAndIndent(String str) throws IOException {
        String[] strArrSplit = str.split("\n", -1);
        int length = strArrSplit.length;
        boolean z2 = true;
        int i2 = 0;
        while (i2 < length) {
            String str2 = strArrSplit[i2];
            if (!z2) {
                if ((this.javadoc || this.comment) && this.trailingNewline) {
                    emitIndentation();
                    this.out.append(this.javadoc ? " *" : "//");
                }
                this.out.append("\n");
                this.trailingNewline = true;
                int i3 = this.statementLine;
                if (i3 != -1) {
                    if (i3 == 0) {
                        indent(2);
                    }
                    this.statementLine++;
                }
            }
            if (!str2.isEmpty()) {
                if (this.trailingNewline) {
                    emitIndentation();
                    if (this.javadoc) {
                        this.out.append(" * ");
                    } else if (this.comment) {
                        this.out.append("// ");
                    }
                }
                this.out.append(str2);
                this.trailingNewline = false;
            }
            i2++;
            z2 = false;
        }
        return this;
    }

    public void emitAnnotations(List<AnnotationSpec> list, boolean z2) throws IOException {
        Iterator<AnnotationSpec> it = list.iterator();
        while (it.hasNext()) {
            it.next().emit(this, z2);
            emit(z2 ? " " : "\n");
        }
    }

    public void emitComment(CodeBlock codeBlock) throws IOException {
        this.trailingNewline = true;
        this.comment = true;
        try {
            emit(codeBlock);
            emit("\n");
        } finally {
            this.comment = false;
        }
    }

    public void emitJavadoc(CodeBlock codeBlock) throws IOException {
        if (codeBlock.isEmpty()) {
            return;
        }
        emit("/**\n");
        this.javadoc = true;
        try {
            emit(codeBlock);
            this.javadoc = false;
            emit(" */\n");
        } catch (Throwable th) {
            this.javadoc = false;
            throw th;
        }
    }

    public void emitModifiers(Set<Modifier> set, Set<Modifier> set2) throws IOException {
        if (set.isEmpty()) {
            return;
        }
        Iterator it = EnumSet.copyOf((Collection) set).iterator();
        while (it.hasNext()) {
            Modifier modifier = (Modifier) it.next();
            if (!set2.contains(modifier)) {
                emitAndIndent(modifier.name().toLowerCase(Locale.US));
                emitAndIndent(" ");
            }
        }
    }

    public void emitTypeVariables(List<TypeVariableName> list) throws IOException {
        if (list.isEmpty()) {
            return;
        }
        emit("<");
        boolean z2 = true;
        for (TypeVariableName typeVariableName : list) {
            if (!z2) {
                emit(", ");
            }
            emit("$L", typeVariableName.name);
            Iterator<TypeName> it = typeVariableName.bounds.iterator();
            boolean z3 = true;
            while (it.hasNext()) {
                emit(z3 ? " extends $T" : " & $T", it.next());
                z3 = false;
            }
            z2 = false;
        }
        emit(">");
    }

    public CodeWriter emitWrappingSpace() throws IOException {
        this.out.wrappingSpace(this.indentLevel + 2);
        return this;
    }

    public Map<String, ClassName> importedTypes() {
        return this.importedTypes;
    }

    public CodeWriter indent() {
        return indent(1);
    }

    public String lookupName(ClassName className) {
        ClassName classNameEnclosingClassName = className;
        boolean z2 = false;
        while (classNameEnclosingClassName != null) {
            ClassName classNameResolve = resolve(classNameEnclosingClassName.simpleName());
            boolean z3 = classNameResolve != null;
            if (Objects.equals(classNameResolve, classNameEnclosingClassName)) {
                return Util.join(StrPool.DOT, className.simpleNames().subList(classNameEnclosingClassName.simpleNames().size() - 1, className.simpleNames().size()));
            }
            classNameEnclosingClassName = classNameEnclosingClassName.enclosingClassName();
            z2 = z3;
        }
        if (z2) {
            return className.canonicalName;
        }
        if (Objects.equals(this.packageName, className.packageName())) {
            this.referencedNames.add(className.topLevelClassName().simpleName());
            return Util.join(StrPool.DOT, className.simpleNames());
        }
        if (!this.javadoc) {
            importableType(className);
        }
        return className.canonicalName;
    }

    public CodeWriter popPackage() {
        String str = this.packageName;
        String str2 = NO_PACKAGE;
        Util.checkState(str != str2, "package already set: %s", str);
        this.packageName = str2;
        return this;
    }

    public CodeWriter popType() {
        this.typeSpecStack.remove(r0.size() - 1);
        return this;
    }

    public CodeWriter pushPackage(String str) {
        String str2 = this.packageName;
        Util.checkState(str2 == NO_PACKAGE, "package already set: %s", str2);
        this.packageName = (String) Util.checkNotNull(str, "packageName == null", new Object[0]);
        return this;
    }

    public CodeWriter pushType(TypeSpec typeSpec) {
        this.typeSpecStack.add(typeSpec);
        return this;
    }

    public Map<String, ClassName> suggestedImports() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.importableTypes);
        linkedHashMap.keySet().removeAll(this.referencedNames);
        return linkedHashMap;
    }

    public CodeWriter unindent() {
        return unindent(1);
    }

    public CodeWriter(Appendable appendable, String str, Set<String> set) {
        this(appendable, str, Collections.emptyMap(), set);
    }

    public CodeWriter emit(String str, Object... objArr) throws IOException {
        return emit(CodeBlock.of(str, objArr));
    }

    public CodeWriter indent(int i2) {
        this.indentLevel += i2;
        return this;
    }

    public CodeWriter unindent(int i2) {
        Util.checkArgument(this.indentLevel - i2 >= 0, "cannot unindent %s from %s", Integer.valueOf(i2), Integer.valueOf(this.indentLevel));
        this.indentLevel -= i2;
        return this;
    }

    public CodeWriter(Appendable appendable, String str, Map<String, ClassName> map, Set<String> set) {
        this.javadoc = false;
        this.comment = false;
        this.packageName = NO_PACKAGE;
        this.typeSpecStack = new ArrayList();
        this.importableTypes = new LinkedHashMap();
        this.referencedNames = new LinkedHashSet();
        this.statementLine = -1;
        this.out = new LineWrapper(appendable, str, 100);
        this.indent = (String) Util.checkNotNull(str, "indent == null", new Object[0]);
        this.importedTypes = (Map) Util.checkNotNull(map, "importedTypes == null", new Object[0]);
        this.staticImports = (Set) Util.checkNotNull(set, "staticImports == null", new Object[0]);
        this.staticImportClassNames = new LinkedHashSet();
        for (String str2 : set) {
            this.staticImportClassNames.add(str2.substring(0, str2.lastIndexOf(46)));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:50:0x0099. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0010  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName] */
    /* JADX WARN: Type inference failed for: r3v16, types: [com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.bumptech.glide.repackaged.com.squareup.javapoet.CodeWriter emit(com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.repackaged.com.squareup.javapoet.CodeWriter.emit(com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock):com.bumptech.glide.repackaged.com.squareup.javapoet.CodeWriter");
    }

    public void emitModifiers(Set<Modifier> set) throws IOException {
        emitModifiers(set, Collections.emptySet());
    }
}
