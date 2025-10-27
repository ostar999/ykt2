package com.arialyy.aria.util;

import android.annotation.SuppressLint;
import com.arialyy.aria.core.AriaConfig;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes2.dex */
public class ErrorHelp {
    @SuppressLint({"SimpleDateFormat"})
    private static String getData(String str) {
        return new SimpleDateFormat(str).format(new Date(System.currentTimeMillis()));
    }

    private static String getLogPath() {
        String str = String.format("%slog/AriaCrash_%s.log", CommonUtil.getAppPath(AriaConfig.getInstance().getAPP()), getData("yyyy-MM-dd_HH_mm_ss"));
        FileUtil.createFile(str);
        return str;
    }

    public static void saveError(String str, String str2) throws Throwable {
        writeLogToFile(String.format("\nmsg【%s】\nException：%s", str, str2));
    }

    private static int writeLogToFile(String str) throws Throwable {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getData("yyyy-MM-dd HH:mm:ss"));
        stringBuffer.append("    ");
        stringBuffer.append(str);
        stringBuffer.append("\n\n");
        PrintWriter printWriter = null;
        try {
            try {
                File file = new File(getLogPath());
                if (!file.exists()) {
                    FileUtil.createFile(file);
                }
                PrintWriter printWriter2 = new PrintWriter(new FileWriter(file.getPath(), true));
                try {
                    printWriter2.append((CharSequence) stringBuffer);
                    printWriter2.flush();
                    printWriter2.close();
                    return 0;
                } catch (Exception e2) {
                    e = e2;
                    printWriter = printWriter2;
                    e.printStackTrace();
                    if (printWriter == null) {
                        return 0;
                    }
                    printWriter.close();
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    printWriter = printWriter2;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }
}
