package org.eclipse.jetty.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.util.URLUtil;
import com.google.common.base.Ascii;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class TypeUtil {
    private static final HashMap<Class<?>, String> class2Name;
    private static final HashMap<Class<?>, Method> class2Value;
    private static final HashMap<String, Class<?>> name2Class;
    private static final Logger LOG = Log.getLogger((Class<?>) TypeUtil.class);
    public static int CR = 13;
    public static int LF = 10;

    static {
        HashMap<String, Class<?>> map = new HashMap<>();
        name2Class = map;
        Class<?> cls = Boolean.TYPE;
        map.put(TypedValues.Custom.S_BOOLEAN, cls);
        map.put("byte", Byte.TYPE);
        map.put("char", Character.TYPE);
        map.put("double", Double.TYPE);
        Class<?> cls2 = Float.TYPE;
        map.put(TypedValues.Custom.S_FLOAT, cls2);
        Class<?> cls3 = Integer.TYPE;
        map.put("int", cls3);
        Class<?> cls4 = Long.TYPE;
        map.put("long", cls4);
        map.put("short", Short.TYPE);
        map.put("void", Void.TYPE);
        map.put("java.lang.Boolean.TYPE", cls);
        map.put("java.lang.Byte.TYPE", Byte.TYPE);
        map.put("java.lang.Character.TYPE", Character.TYPE);
        map.put("java.lang.Double.TYPE", Double.TYPE);
        map.put("java.lang.Float.TYPE", cls2);
        map.put("java.lang.Integer.TYPE", cls3);
        map.put("java.lang.Long.TYPE", cls4);
        map.put("java.lang.Short.TYPE", Short.TYPE);
        map.put("java.lang.Void.TYPE", Void.TYPE);
        map.put("java.lang.Boolean", Boolean.class);
        map.put("java.lang.Byte", Byte.class);
        map.put("java.lang.Character", Character.class);
        map.put("java.lang.Double", Double.class);
        map.put("java.lang.Float", Float.class);
        map.put("java.lang.Integer", Integer.class);
        map.put("java.lang.Long", Long.class);
        map.put("java.lang.Short", Short.class);
        map.put("Boolean", Boolean.class);
        map.put("Byte", Byte.class);
        map.put("Character", Character.class);
        map.put("Double", Double.class);
        map.put("Float", Float.class);
        map.put("Integer", Integer.class);
        map.put("Long", Long.class);
        map.put("Short", Short.class);
        map.put(null, Void.TYPE);
        map.put(TypedValues.Custom.S_STRING, String.class);
        map.put("String", String.class);
        map.put("java.lang.String", String.class);
        HashMap<Class<?>, String> map2 = new HashMap<>();
        class2Name = map2;
        map2.put(cls, TypedValues.Custom.S_BOOLEAN);
        map2.put(Byte.TYPE, "byte");
        map2.put(Character.TYPE, "char");
        map2.put(Double.TYPE, "double");
        map2.put(cls2, TypedValues.Custom.S_FLOAT);
        map2.put(cls3, "int");
        map2.put(cls4, "long");
        map2.put(Short.TYPE, "short");
        map2.put(Void.TYPE, "void");
        map2.put(Boolean.class, "java.lang.Boolean");
        map2.put(Byte.class, "java.lang.Byte");
        map2.put(Character.class, "java.lang.Character");
        map2.put(Double.class, "java.lang.Double");
        map2.put(Float.class, "java.lang.Float");
        map2.put(Integer.class, "java.lang.Integer");
        map2.put(Long.class, "java.lang.Long");
        map2.put(Short.class, "java.lang.Short");
        map2.put(null, "void");
        map2.put(String.class, "java.lang.String");
        HashMap<Class<?>, Method> map3 = new HashMap<>();
        class2Value = map3;
        try {
            Class[] clsArr = {String.class};
            map3.put(cls, Boolean.class.getMethod("valueOf", clsArr));
            map3.put(Byte.TYPE, Byte.class.getMethod("valueOf", clsArr));
            map3.put(Double.TYPE, Double.class.getMethod("valueOf", clsArr));
            map3.put(cls2, Float.class.getMethod("valueOf", clsArr));
            map3.put(cls3, Integer.class.getMethod("valueOf", clsArr));
            map3.put(cls4, Long.class.getMethod("valueOf", clsArr));
            map3.put(Short.TYPE, Short.class.getMethod("valueOf", clsArr));
            map3.put(Boolean.class, Boolean.class.getMethod("valueOf", clsArr));
            map3.put(Byte.class, Byte.class.getMethod("valueOf", clsArr));
            map3.put(Double.class, Double.class.getMethod("valueOf", clsArr));
            map3.put(Float.class, Float.class.getMethod("valueOf", clsArr));
            map3.put(Integer.class, Integer.class.getMethod("valueOf", clsArr));
            map3.put(Long.class, Long.class.getMethod("valueOf", clsArr));
            map3.put(Short.class, Short.class.getMethod("valueOf", clsArr));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    public static <T> List<T> asList(T[] tArr) {
        return tArr == null ? Collections.emptyList() : Arrays.asList(tArr);
    }

    public static Object call(Class<?> cls, String str, Object obj, Object[] objArr) throws NoSuchMethodException, SecurityException, InvocationTargetException {
        Method[] methods = cls.getMethods();
        for (int i2 = 0; methods != null && i2 < methods.length; i2++) {
            if (methods[i2].getName().equals(str) && methods[i2].getParameterTypes().length == objArr.length) {
                if (Modifier.isStatic(methods[i2].getModifiers()) == (obj == null) && (obj != null || methods[i2].getDeclaringClass() == cls)) {
                    try {
                        return methods[i2].invoke(obj, objArr);
                    } catch (IllegalAccessException e2) {
                        LOG.ignore(e2);
                    } catch (IllegalArgumentException e3) {
                        LOG.ignore(e3);
                    }
                }
            }
        }
        throw new NoSuchMethodException(str);
    }

    public static byte convertHexDigit(byte b3) {
        byte b4 = (byte) (((b3 & Ascii.US) + ((b3 >> 6) * 25)) - 16);
        if (b4 >= 0 && b4 <= 15) {
            return b4;
        }
        throw new IllegalArgumentException("!hex " + ((int) b3));
    }

    public static void dump(Class<?> cls) {
        System.err.println("Dump: " + cls);
        dump(cls.getClassLoader());
    }

    public static byte[] fromHexString(String str) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException(str);
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (Integer.parseInt(str.substring(i3, i3 + 2), 16) & 255);
        }
        return bArr;
    }

    public static Class<?> fromName(String str) {
        return name2Class.get(str);
    }

    public static URL jarFor(String str) {
        try {
            String string = Loader.getResource(null, str.replace('.', '/') + ".class", false).toString();
            if (string.startsWith("jar:file:")) {
                return new URL(string.substring(4, string.indexOf(URLUtil.JAR_URL_SEPARATOR)));
            }
        } catch (Exception e2) {
            LOG.ignore(e2);
        }
        return null;
    }

    public static byte[] parseBytes(String str, int i2) {
        byte[] bArr = new byte[str.length() / 2];
        for (int i3 = 0; i3 < str.length(); i3 += 2) {
            bArr[i3 / 2] = (byte) parseInt(str, i3, 2, i2);
        }
        return bArr;
    }

    public static int parseInt(String str, int i2, int i3, int i4) throws NumberFormatException {
        if (i3 < 0) {
            i3 = str.length() - i2;
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int iConvertHexDigit = convertHexDigit(str.charAt(i2 + i6));
            if (iConvertHexDigit < 0 || iConvertHexDigit >= i4) {
                throw new NumberFormatException(str.substring(i2, i3 + i2));
            }
            i5 = (i5 * i4) + iConvertHexDigit;
        }
        return i5;
    }

    public static byte[] readLine(InputStream inputStream) throws IOException {
        int i2;
        byte[] bArr = new byte[256];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i2 = inputStream.read();
            if (i2 < 0) {
                break;
            }
            i3++;
            if (i3 != 1 || i2 != LF) {
                if (i2 == CR || i2 == LF) {
                    break;
                }
                if (i4 >= bArr.length) {
                    byte[] bArr2 = new byte[bArr.length + 256];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    bArr = bArr2;
                }
                bArr[i4] = (byte) i2;
                i4++;
            }
        }
        if (i2 == -1 && i4 == 0) {
            return null;
        }
        if (i2 == CR && inputStream.available() >= 1 && inputStream.markSupported()) {
            inputStream.mark(1);
            if (inputStream.read() != LF) {
                inputStream.reset();
            }
        }
        byte[] bArr3 = new byte[i4];
        System.arraycopy(bArr, 0, bArr3, 0, i4);
        return bArr3;
    }

    public static void toHex(byte b3, Appendable appendable) {
        int i2 = ((b3 & 240) >> 4) & 15;
        int i3 = 55;
        try {
            appendable.append((char) ((i2 > 9 ? 55 : 48) + i2));
            int i4 = b3 & 15;
            if (i4 <= 9) {
                i3 = 48;
            }
            appendable.append((char) (i3 + i4));
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String toHexString(byte b3) {
        return toHexString(new byte[]{b3}, 0, 1);
    }

    public static String toName(Class<?> cls) {
        return class2Name.get(cls);
    }

    public static String toString(byte[] bArr, int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 : bArr) {
            int i4 = i3 & 255;
            int i5 = ((i4 / i2) % i2) + 48;
            if (i5 > 57) {
                i5 = ((i5 - 48) - 10) + 97;
            }
            sb.append((char) i5);
            int i6 = (i4 % i2) + 48;
            if (i6 > 57) {
                i6 = ((i6 - 48) - 10) + 97;
            }
            sb.append((char) i6);
        }
        return sb.toString();
    }

    public static Object valueOf(Class<?> cls, String str) {
        try {
            if (cls.equals(String.class)) {
                return str;
            }
            Method method = class2Value.get(cls);
            if (method != null) {
                return method.invoke(null, str);
            }
            if (!cls.equals(Character.TYPE) && !cls.equals(Character.class)) {
                return cls.getConstructor(String.class).newInstance(str);
            }
            return new Character(str.charAt(0));
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException unused) {
            return null;
        } catch (InvocationTargetException e2) {
            if (e2.getTargetException() instanceof Error) {
                throw ((Error) e2.getTargetException());
            }
            return null;
        }
    }

    public static int convertHexDigit(int i2) {
        int i3 = ((i2 & 31) + ((i2 >> 6) * 25)) - 16;
        if (i3 >= 0 && i3 <= 15) {
            return i3;
        }
        throw new NumberFormatException("!hex " + i2);
    }

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static void dump(ClassLoader classLoader) {
        System.err.println("Dump Loaders:");
        while (classLoader != null) {
            System.err.println("  loader " + classLoader);
            classLoader = classLoader.getParent();
        }
    }

    public static String toHexString(byte[] bArr, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            int i5 = bArr[i4] & 255;
            int i6 = ((i5 / 16) % 16) + 48;
            if (i6 > 57) {
                i6 = ((i6 - 48) - 10) + 65;
            }
            sb.append((char) i6);
            int i7 = (i5 % 16) + 48;
            if (i7 > 57) {
                i7 = ((i7 - 48) - 10) + 97;
            }
            sb.append((char) i7);
        }
        return sb.toString();
    }

    public static void toHex(int i2, Appendable appendable) throws IOException {
        int i3 = (((-268435456) & i2) >> 28) & 15;
        appendable.append((char) ((i3 > 9 ? 55 : 48) + i3));
        int i4 = ((251658240 & i2) >> 24) & 15;
        appendable.append((char) ((i4 > 9 ? 55 : 48) + i4));
        int i5 = ((15728640 & i2) >> 20) & 15;
        appendable.append((char) ((i5 > 9 ? 55 : 48) + i5));
        int i6 = ((983040 & i2) >> 16) & 15;
        appendable.append((char) ((i6 > 9 ? 55 : 48) + i6));
        int i7 = ((61440 & i2) >> 12) & 15;
        appendable.append((char) ((i7 > 9 ? 55 : 48) + i7));
        int i8 = ((i2 & R2.attr.triangleHeight) >> 8) & 15;
        appendable.append((char) ((i8 > 9 ? 55 : 48) + i8));
        int i9 = ((i2 & 240) >> 4) & 15;
        appendable.append((char) ((i9 > 9 ? 55 : 48) + i9));
        int i10 = i2 & 15;
        appendable.append((char) ((i10 <= 9 ? 48 : 55) + i10));
        Integer.toString(0, 36);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SimplifyVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r2v4 int, still in use, count: 1, list:
          (r2v4 int) from 0x0021: ARITH (r3v2 int) = (r2v4 int) + (-97 int)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.utils.InsnRemover.unbindAllArgs(InsnRemover.java:106)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:90)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:141)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyArgs(SimplifyVisitor.java:116)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyInsn(SimplifyVisitor.java:132)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyBlock(SimplifyVisitor.java:86)
        	at jadx.core.dex.visitors.SimplifyVisitor.visit(SimplifyVisitor.java:71)
        */
    public static int parseInt(byte[] r5, int r6, int r7, int r8) throws java.lang.NumberFormatException {
        /*
            if (r7 >= 0) goto L4
            int r7 = r5.length
            int r7 = r7 - r6
        L4:
            r0 = 0
            r1 = r0
        L6:
            if (r0 >= r7) goto L37
            int r2 = r6 + r0
            r2 = r5[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            char r2 = (char) r2
            int r3 = r2 + (-48)
            r4 = 10
            if (r3 < 0) goto L19
            if (r3 >= r8) goto L19
            if (r3 < r4) goto L23
        L19:
            int r2 = r2 + 10
            int r3 = r2 + (-65)
            if (r3 < r4) goto L21
            if (r3 < r8) goto L23
        L21:
            int r3 = r2 + (-97)
        L23:
            if (r3 < 0) goto L2c
            if (r3 >= r8) goto L2c
            int r1 = r1 * r8
            int r1 = r1 + r3
            int r0 = r0 + 1
            goto L6
        L2c:
            java.lang.NumberFormatException r8 = new java.lang.NumberFormatException
            java.lang.String r0 = new java.lang.String
            r0.<init>(r5, r6, r7)
            r8.<init>(r0)
            throw r8
        L37:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.TypeUtil.parseInt(byte[], int, int, int):int");
    }

    public static Object valueOf(String str, String str2) {
        return valueOf(fromName(str), str2);
    }

    public static void toHex(long j2, Appendable appendable) throws IOException {
        toHex((int) (j2 >> 32), appendable);
        toHex((int) j2, appendable);
    }
}
