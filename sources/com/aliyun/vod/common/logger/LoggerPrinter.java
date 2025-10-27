package com.aliyun.vod.common.logger;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class LoggerPrinter implements Printer {
    private static final int ASSERT = 7;
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final char BOTTOM_LEFT_CORNER = 9562;
    private static final int CHUNK_SIZE = 4000;
    private static final int DEBUG = 3;
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final int ERROR = 6;
    private static final char HORIZONTAL_DOUBLE_LINE = 9553;
    private static final int INFO = 4;
    private static final int JSON_INDENT = 4;
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static final char MIDDLE_CORNER = 9567;
    private static final int MIN_STACK_OFFSET = 3;
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final char TOP_LEFT_CORNER = 9556;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;
    private Settings settings;
    private String tag;
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();

    private String createMessage(String str, Object... objArr) {
        return objArr.length == 0 ? str : String.format(str, objArr);
    }

    private String formatTag(String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(this.tag, str)) {
            return this.tag;
        }
        return this.tag + "-" + str;
    }

    private int getMethodCount() {
        Integer num = this.localMethodCount.get();
        int methodCount = this.settings.getMethodCount();
        if (num != null) {
            this.localMethodCount.remove();
            methodCount = num.intValue();
        }
        if (methodCount >= 0) {
            return methodCount;
        }
        throw new IllegalStateException("methodCount cannot be negative");
    }

    private String getSimpleClassName(String str) {
        return str.substring(str.lastIndexOf(StrPool.DOT) + 1);
    }

    private int getStackOffset(StackTraceElement[] stackTraceElementArr) {
        for (int i2 = 3; i2 < stackTraceElementArr.length; i2++) {
            String className = stackTraceElementArr[i2].getClassName();
            if (!className.equals(LoggerPrinter.class.getName()) && !className.equals(Logger.class.getName())) {
                return i2 - 1;
            }
        }
        return -1;
    }

    private String getTag() {
        String str = this.localTag.get();
        if (str == null) {
            return this.tag;
        }
        this.localTag.remove();
        return str;
    }

    private synchronized void log(int i2, String str, Object... objArr) {
        if (this.settings.getLogLevel() == LogLevel.NONE) {
            return;
        }
        String tag = getTag();
        String strCreateMessage = createMessage(str, objArr);
        int methodCount = getMethodCount();
        logTopBorder(i2, tag);
        logHeaderContent(i2, tag, methodCount);
        byte[] bytes = strCreateMessage.getBytes();
        int length = bytes.length;
        if (length <= 4000) {
            if (methodCount > 0) {
                logDivider(i2, tag);
            }
            logContent(i2, tag, strCreateMessage);
            logBottomBorder(i2, tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(i2, tag);
        }
        for (int i3 = 0; i3 < length; i3 += 4000) {
            logContent(i2, tag, new String(bytes, i3, Math.min(length - i3, 4000)));
        }
        logBottomBorder(i2, tag);
    }

    private void logBottomBorder(int i2, String str) {
        logChunk(i2, str, BOTTOM_BORDER);
    }

    private void logChunk(int i2, String str, String str2) {
        String tag = formatTag(str);
        if (i2 == 2) {
            this.settings.getLogTool().v(tag, str2);
            return;
        }
        if (i2 == 4) {
            this.settings.getLogTool().i(tag, str2);
            return;
        }
        if (i2 == 5) {
            this.settings.getLogTool().w(tag, str2);
            return;
        }
        if (i2 == 6) {
            this.settings.getLogTool().e(tag, str2);
        } else if (i2 != 7) {
            this.settings.getLogTool().d(tag, str2);
        } else {
            this.settings.getLogTool().wtf(tag, str2);
        }
    }

    private void logContent(int i2, String str, String str2) {
        for (String str3 : str2.split(System.getProperty("line.separator"))) {
            logChunk(i2, str, "║ " + str3);
        }
    }

    private void logDivider(int i2, String str) {
        logChunk(i2, str, MIDDLE_BORDER);
    }

    private void logHeaderContent(int i2, String str, int i3) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (this.settings.isShowThreadInfo()) {
            logChunk(i2, str, "║ Thread: " + Thread.currentThread().getName());
            logDivider(i2, str);
        }
        int stackOffset = getStackOffset(stackTrace) + this.settings.getMethodOffset();
        if (i3 + stackOffset > stackTrace.length) {
            i3 = (stackTrace.length - stackOffset) - 1;
        }
        String str2 = "";
        while (i3 > 0) {
            int i4 = i3 + stackOffset;
            if (i4 < stackTrace.length) {
                str2 = str2 + "   ";
                logChunk(i2, str, "║ " + str2 + getSimpleClassName(stackTrace[i4].getClassName()) + StrPool.DOT + stackTrace[i4].getMethodName() + "  (" + stackTrace[i4].getFileName() + ":" + stackTrace[i4].getLineNumber() + ")");
            }
            i3--;
        }
    }

    private void logTopBorder(int i2, String str) {
        logChunk(i2, str, TOP_BORDER);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void clear() {
        this.settings = null;
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void d(String str, Object... objArr) {
        log(3, str, objArr);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void e(Throwable th) {
        e(th, null, new Object[0]);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public Settings getSettings() {
        return this.settings;
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void i(String str, Object... objArr) {
        log(4, str, objArr);
    }

    public Settings init(String str) {
        if (str == null) {
            throw new NullPointerException("tag may not be null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        }
        this.tag = str;
        Settings settings = new Settings();
        this.settings = settings;
        return settings;
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void json(String str) {
        if (TextUtils.isEmpty(str)) {
            d("Empty/Null json content", new Object[0]);
            return;
        }
        try {
            if (str.startsWith(StrPool.DELIM_START)) {
                d(new JSONObject(str).toString(4), new Object[0]);
            } else if (str.startsWith(StrPool.BRACKET_START)) {
                d(new JSONArray(str).toString(4), new Object[0]);
            }
        } catch (JSONException e2) {
            e(e2.getCause().getMessage() + "\n" + str, new Object[0]);
        }
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public Printer t(String str, int i2) {
        if (str != null) {
            this.localTag.set(str);
        }
        this.localMethodCount.set(Integer.valueOf(i2));
        return this;
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void v(String str, Object... objArr) {
        log(2, str, objArr);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void w(String str, Object... objArr) {
        log(5, str, objArr);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void wtf(String str, Object... objArr) {
        log(7, str, objArr);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void xml(String str) throws TransformerException, IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            d("Empty/Null xml content", new Object[0]);
            return;
        }
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty("indent", "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformerNewTransformer.transform(streamSource, streamResult);
            d(streamResult.getWriter().toString().replaceFirst(">", ">\n"), new Object[0]);
        } catch (TransformerException e2) {
            e(e2.getCause().getMessage() + "\n" + str, new Object[0]);
        }
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void e(String str, Object... objArr) {
        e(null, str, objArr);
    }

    @Override // com.aliyun.vod.common.logger.Printer
    public void e(Throwable th, String str, Object... objArr) {
        if (th != null && str != null) {
            str = str + " : " + th.toString();
        }
        if (th != null && str == null) {
            str = th.toString();
        }
        if (str == null) {
            str = "No message/exception is set";
        }
        log(6, str, objArr);
    }
}
