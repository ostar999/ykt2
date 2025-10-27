package cn.hutool.core.img;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IORuntimeException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/* loaded from: classes.dex */
public class FontUtil {
    public static Font createFont() {
        return new Font((Map) null);
    }

    public static Font createSansSerifFont(int i2) {
        return createFont("SansSerif", i2);
    }

    public static Dimension getDimension(FontMetrics fontMetrics, String str) {
        return new Dimension(fontMetrics.stringWidth(str), (fontMetrics.getAscent() - fontMetrics.getLeading()) - fontMetrics.getDescent());
    }

    public static Font createFont(String str, int i2) {
        return new Font(str, 0, i2);
    }

    public static Font createFont(File file) {
        try {
            return Font.createFont(0, file);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        } catch (FontFormatException e3) {
            try {
                return Font.createFont(1, file);
            } catch (Exception unused) {
                throw new UtilException((Throwable) e3);
            }
        }
    }

    public static Font createFont(InputStream inputStream) {
        try {
            return Font.createFont(0, inputStream);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        } catch (FontFormatException unused) {
            try {
                return Font.createFont(1, inputStream);
            } catch (Exception e3) {
                throw new UtilException(e3);
            }
        }
    }
}
