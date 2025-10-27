package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Pid;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* loaded from: classes.dex */
public class RuntimeUtil {
    public static void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(runnable instanceof Thread ? (Thread) runnable : new Thread(runnable));
    }

    private static String[] cmdSplit(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        Stack stack = new Stack();
        StrBuilder strBuilder = StrUtil.strBuilder();
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != ' ') {
                if (cCharAt != '\"' && cCharAt != '\'') {
                    strBuilder.append(cCharAt);
                } else if (z2) {
                    if (cCharAt == ((Character) stack.peek()).charValue()) {
                        stack.pop();
                        z2 = false;
                    }
                    strBuilder.append(cCharAt);
                } else {
                    stack.push(Character.valueOf(cCharAt));
                    strBuilder.append(cCharAt);
                    z2 = true;
                }
            } else if (z2) {
                strBuilder.append(cCharAt);
            } else {
                arrayList.add(strBuilder.toString());
                strBuilder.reset();
            }
        }
        if (strBuilder.hasContent()) {
            arrayList.add(strBuilder.toString());
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static void destroy(Process process) {
        if (process != null) {
            process.destroy();
        }
    }

    public static Process exec(String... strArr) {
        try {
            return new ProcessBuilder(handleCmds(strArr)).redirectErrorStream(true).start();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static List<String> execForLines(String... strArr) throws IORuntimeException {
        return execForLines(CharsetUtil.systemCharset(), strArr);
    }

    public static String execForStr(String... strArr) throws IORuntimeException {
        return execForStr(CharsetUtil.systemCharset(), strArr);
    }

    public static String getErrorResult(Process process) {
        return getErrorResult(process, CharsetUtil.systemCharset());
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static int getPid() throws UtilException {
        return Pid.INSTANCE.get();
    }

    public static int getProcessorCount() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        if (iAvailableProcessors <= 0) {
            return 7;
        }
        return iAvailableProcessors;
    }

    public static String getResult(Process process) {
        return getResult(process, CharsetUtil.systemCharset());
    }

    public static List<String> getResultLines(Process process) {
        return getResultLines(process, CharsetUtil.systemCharset());
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getUsableMemory() {
        return (getMaxMemory() - getTotalMemory()) + getFreeMemory();
    }

    private static String[] handleCmds(String... strArr) {
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            throw new NullPointerException("Command is empty !");
        }
        if (1 != strArr.length) {
            return strArr;
        }
        String str = strArr[0];
        if (CharSequenceUtil.isBlank(str)) {
            throw new NullPointerException("Command is blank !");
        }
        return cmdSplit(str);
    }

    public static List<String> execForLines(Charset charset, String... strArr) throws IORuntimeException {
        return getResultLines(exec(strArr), charset);
    }

    public static String execForStr(Charset charset, String... strArr) throws IORuntimeException {
        return getResult(exec(strArr), charset);
    }

    public static String getErrorResult(Process process, Charset charset) throws Throwable {
        InputStream errorStream;
        try {
            errorStream = process.getErrorStream();
            try {
                String str = IoUtil.read(errorStream, charset);
                IoUtil.close((Closeable) errorStream);
                destroy(process);
                return str;
            } catch (Throwable th) {
                th = th;
                IoUtil.close((Closeable) errorStream);
                destroy(process);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            errorStream = null;
        }
    }

    public static String getResult(Process process, Charset charset) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = process.getInputStream();
            try {
                String str = IoUtil.read(inputStream, charset);
                IoUtil.close((Closeable) inputStream);
                destroy(process);
                return str;
            } catch (Throwable th) {
                th = th;
                IoUtil.close((Closeable) inputStream);
                destroy(process);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
    }

    public static List<String> getResultLines(Process process, Charset charset) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = process.getInputStream();
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            List<String> list = (List) IoUtil.readLines(inputStream, charset, new ArrayList());
            IoUtil.close((Closeable) inputStream);
            destroy(process);
            return list;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            destroy(process);
            throw th;
        }
    }

    public static Process exec(String[] strArr, String... strArr2) {
        return exec(strArr, null, strArr2);
    }

    public static Process exec(String[] strArr, File file, String... strArr2) {
        try {
            return Runtime.getRuntime().exec(handleCmds(strArr2), strArr, file);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
