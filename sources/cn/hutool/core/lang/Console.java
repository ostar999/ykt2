package cn.hutool.core.lang;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import java.util.Scanner;

/* loaded from: classes.dex */
public class Console {
    private static final String TEMPLATE_VAR = "{}";

    private static String buildTemplateSplitBySpace(int i2) {
        return CharSequenceUtil.repeatAndJoin("{}", i2, " ");
    }

    public static void error() {
        System.err.println();
    }

    private static void errorInternal(String str, Object... objArr) {
        error(null, str, objArr);
    }

    public static String input() {
        return scanner().nextLine();
    }

    public static Integer lineNumber() {
        return Integer.valueOf(new Throwable().getStackTrace()[1].getLineNumber());
    }

    public static void log() {
        System.out.println();
    }

    private static void logInternal(String str, Object... objArr) {
        log(null, str, objArr);
    }

    public static void print(Object obj) {
        print("{}", obj);
    }

    private static void printInternal(String str, Object... objArr) {
        System.out.print(CharSequenceUtil.format(str, objArr));
    }

    public static void printProgress(char c3, int i2) {
        print("{}{}", '\r', CharSequenceUtil.repeat(c3, i2));
    }

    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static void table(ConsoleTable consoleTable) {
        print(consoleTable.toString());
    }

    public static String where() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        return String.format("%s.%s(%s:%s)", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getFileName(), Integer.valueOf(stackTraceElement.getLineNumber()));
    }

    public static void error(Object obj) {
        if (!(obj instanceof Throwable)) {
            error("{}", obj);
        } else {
            Throwable th = (Throwable) obj;
            error(th, th.getMessage(), new Object[0]);
        }
    }

    public static void log(Object obj) {
        if (!(obj instanceof Throwable)) {
            log("{}", obj);
        } else {
            Throwable th = (Throwable) obj;
            log(th, th.getMessage(), new Object[0]);
        }
    }

    public static void print(Object obj, Object... objArr) {
        if (ArrayUtil.isEmpty(objArr)) {
            print(obj);
        } else {
            print(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, obj));
        }
    }

    public static void printProgress(char c3, int i2, double d3) throws Throwable {
        Assert.isTrue(d3 >= 0.0d && d3 <= 1.0d, "Rate must between 0 and 1 (both include)", new Object[0]);
        printProgress(c3, (int) (i2 * d3));
    }

    public static void print(String str, Object... objArr) {
        if (!ArrayUtil.isEmpty(objArr) && !CharSequenceUtil.contains(str, "{}")) {
            printInternal(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, str));
        } else {
            printInternal(str, objArr);
        }
    }

    public static void error(Object obj, Object... objArr) {
        if (ArrayUtil.isEmpty(objArr)) {
            error(obj);
        } else {
            error(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, obj));
        }
    }

    public static void log(Object obj, Object... objArr) {
        if (ArrayUtil.isEmpty(objArr)) {
            log(obj);
        } else {
            log(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, obj));
        }
    }

    public static void error(String str, Object... objArr) {
        if (!ArrayUtil.isEmpty(objArr) && !CharSequenceUtil.contains(str, "{}")) {
            errorInternal(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, str));
        } else {
            errorInternal(str, objArr);
        }
    }

    public static void log(String str, Object... objArr) {
        if (!ArrayUtil.isEmpty(objArr) && !CharSequenceUtil.contains(str, "{}")) {
            logInternal(buildTemplateSplitBySpace(objArr.length + 1), ArrayUtil.insert(objArr, 0, str));
        } else {
            logInternal(str, objArr);
        }
    }

    public static void error(Throwable th, String str, Object... objArr) {
        System.err.println(CharSequenceUtil.format(str, objArr));
        if (th != null) {
            th.printStackTrace(System.err);
            System.err.flush();
        }
    }

    public static void log(Throwable th, String str, Object... objArr) {
        System.out.println(CharSequenceUtil.format(str, objArr));
        if (th != null) {
            th.printStackTrace(System.out);
            System.out.flush();
        }
    }
}
