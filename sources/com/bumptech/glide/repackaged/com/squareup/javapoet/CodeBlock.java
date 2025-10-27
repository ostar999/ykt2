package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes2.dex */
public final class CodeBlock {
    final List<Object> args;
    final List<String> formatParts;
    private static final Pattern NAMED_ARGUMENT = Pattern.compile("\\$(?<argumentName>[\\w_]+):(?<typeChar>[\\w]).*");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]+[\\w_]*");

    public static final class Builder {
        final List<Object> args;
        final List<String> formatParts;

        private void addArgument(String str, char c3, Object obj) {
            if (c3 == 'L') {
                this.args.add(argToLiteral(obj));
                return;
            }
            if (c3 == 'N') {
                this.args.add(argToName(obj));
            } else if (c3 == 'S') {
                this.args.add(argToString(obj));
            } else {
                if (c3 != 'T') {
                    throw new IllegalArgumentException(String.format("invalid format string: '%s'", str));
                }
                this.args.add(argToType(obj));
            }
        }

        private Object argToLiteral(Object obj) {
            return obj;
        }

        private String argToName(Object obj) {
            if (obj instanceof CharSequence) {
                return obj.toString();
            }
            if (obj instanceof ParameterSpec) {
                return ((ParameterSpec) obj).name;
            }
            if (obj instanceof FieldSpec) {
                return ((FieldSpec) obj).name;
            }
            if (obj instanceof MethodSpec) {
                return ((MethodSpec) obj).name;
            }
            if (obj instanceof TypeSpec) {
                return ((TypeSpec) obj).name;
            }
            throw new IllegalArgumentException("expected name but was " + obj);
        }

        private String argToString(Object obj) {
            if (obj != null) {
                return String.valueOf(obj);
            }
            return null;
        }

        private TypeName argToType(Object obj) {
            if (obj instanceof TypeName) {
                return (TypeName) obj;
            }
            if (obj instanceof TypeMirror) {
                return TypeName.get((TypeMirror) obj);
            }
            if (obj instanceof Element) {
                return TypeName.get(((Element) obj).asType());
            }
            if (obj instanceof Type) {
                return TypeName.get((Type) obj);
            }
            throw new IllegalArgumentException("expected type but was " + obj);
        }

        private boolean isNoArgPlaceholder(char c3) {
            return c3 == '$' || c3 == '>' || c3 == '<' || c3 == '[' || c3 == ']' || c3 == 'W';
        }

        public Builder add(String str, Object... objArr) {
            boolean z2;
            int i2;
            int i3;
            char cCharAt;
            boolean z3;
            int i4;
            int[] iArr = new int[objArr.length];
            int i5 = 0;
            boolean z4 = false;
            int i6 = 0;
            boolean z5 = false;
            while (true) {
                if (i5 >= str.length()) {
                    break;
                }
                if (str.charAt(i5) != '$') {
                    int iIndexOf = str.indexOf(36, i5 + 1);
                    if (iIndexOf == -1) {
                        iIndexOf = str.length();
                    }
                    this.formatParts.add(str.substring(i5, iIndexOf));
                    i5 = iIndexOf;
                } else {
                    int i7 = i5 + 1;
                    int i8 = i7;
                    while (true) {
                        Util.checkArgument(i8 < str.length(), "dangling format characters in '%s'", str);
                        i3 = i8 + 1;
                        cCharAt = str.charAt(i8);
                        if (cCharAt < '0' || cCharAt > '9') {
                            break;
                        }
                        i8 = i3;
                    }
                    int i9 = i3 - 1;
                    if (isNoArgPlaceholder(cCharAt)) {
                        Util.checkArgument(i7 == i9, "$$, $>, $<, $[, $], and $W may not have an index", new Object[0]);
                        this.formatParts.add("$" + cCharAt);
                        i5 = i3;
                    } else {
                        if (i7 < i9) {
                            int i10 = Integer.parseInt(str.substring(i7, i9)) - 1;
                            if (objArr.length > 0) {
                                int length = i10 % objArr.length;
                                iArr[length] = iArr[length] + 1;
                            }
                            z3 = true;
                            i4 = i6;
                            i6 = i10;
                        } else {
                            z3 = z5;
                            i4 = i6 + 1;
                            z4 = true;
                        }
                        Util.checkArgument(i6 >= 0 && i6 < objArr.length, "index %d for '%s' not in range (received %s arguments)", Integer.valueOf(i6 + 1), str.substring(i7 - 1, i9 + 1), Integer.valueOf(objArr.length));
                        Util.checkArgument((z3 && z4) ? false : true, "cannot mix indexed and positional parameters", new Object[0]);
                        addArgument(str, cCharAt, objArr[i6]);
                        this.formatParts.add("$" + cCharAt);
                        i6 = i4;
                        i5 = i3;
                        z5 = z3;
                    }
                }
            }
            if (z4) {
                if (i6 >= objArr.length) {
                    i2 = 2;
                    z2 = true;
                } else {
                    z2 = false;
                    i2 = 2;
                }
                Object[] objArr2 = new Object[i2];
                objArr2[0] = Integer.valueOf(i6);
                objArr2[1] = Integer.valueOf(objArr.length);
                Util.checkArgument(z2, "unused arguments: expected %s, received %s", objArr2);
            }
            if (z5) {
                ArrayList arrayList = new ArrayList();
                for (int i11 = 0; i11 < objArr.length; i11++) {
                    if (iArr[i11] == 0) {
                        arrayList.add("$" + (i11 + 1));
                    }
                }
                Util.checkArgument(arrayList.isEmpty(), "unused argument%s: %s", arrayList.size() == 1 ? "" : "s", Util.join(", ", arrayList));
            }
            return this;
        }

        public Builder addStatement(String str, Object... objArr) {
            add("$[", new Object[0]);
            add(str, objArr);
            add(";\n$]", new Object[0]);
            return this;
        }

        public Builder beginControlFlow(String str, Object... objArr) {
            add(str + " {\n", objArr);
            indent();
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }

        public Builder endControlFlow() {
            unindent();
            add("}\n", new Object[0]);
            return this;
        }

        public Builder indent() {
            this.formatParts.add("$>");
            return this;
        }

        public Builder nextControlFlow(String str, Object... objArr) {
            unindent();
            add("} " + str + " {\n", objArr);
            indent();
            return this;
        }

        public Builder unindent() {
            this.formatParts.add("$<");
            return this;
        }

        private Builder() {
            this.formatParts = new ArrayList();
            this.args = new ArrayList();
        }

        public Builder add(CodeBlock codeBlock) {
            this.formatParts.addAll(codeBlock.formatParts);
            this.args.addAll(codeBlock.args);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CodeBlock of(String str, Object... objArr) {
        return new Builder().add(str, objArr).build();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && CodeBlock.class == obj.getClass()) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isEmpty() {
        return this.formatParts.isEmpty();
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        try {
            new CodeWriter(stringWriter).emit(this);
            return stringWriter.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    private CodeBlock(Builder builder) {
        this.formatParts = Util.immutableList(builder.formatParts);
        this.args = Util.immutableList(builder.args);
    }
}
