package cn.hutool.core.swing;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.URLUtil;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/* loaded from: classes.dex */
public class DesktopUtil {
    public static void browse(String str) {
        browse(URLUtil.toURI(str));
    }

    public static void edit(File file) {
        try {
            getDsktop().edit(file);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Desktop getDsktop() {
        return Desktop.getDesktop();
    }

    public static void mail(String str) {
        try {
            getDsktop().mail(URLUtil.toURI(str));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void open(File file) {
        try {
            getDsktop().open(file);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void print(File file) {
        try {
            getDsktop().print(file);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void browse(URI uri) {
        try {
            getDsktop().browse(uri);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
