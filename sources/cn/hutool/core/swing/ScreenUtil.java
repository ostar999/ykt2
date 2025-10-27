package cn.hutool.core.swing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

/* loaded from: classes.dex */
public class ScreenUtil {
    public static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    public static BufferedImage captureScreen() {
        return RobotUtil.captureScreen();
    }

    public static int getHeight() {
        return (int) dimension.getHeight();
    }

    public static Rectangle getRectangle() {
        return new Rectangle(getWidth(), getHeight());
    }

    public static int getWidth() {
        return (int) dimension.getWidth();
    }

    public static File captureScreen(File file) {
        return RobotUtil.captureScreen(file);
    }

    public static BufferedImage captureScreen(Rectangle rectangle) {
        return RobotUtil.captureScreen(rectangle);
    }

    public static File captureScreen(Rectangle rectangle, File file) {
        return RobotUtil.captureScreen(rectangle, file);
    }
}
