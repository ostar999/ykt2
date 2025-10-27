package cn.hutool.core.swing;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

/* loaded from: classes.dex */
public class RobotUtil {
    private static final Robot ROBOT;
    private static int delay;

    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e2) {
            throw new UtilException((Throwable) e2);
        }
    }

    public static BufferedImage captureScreen() {
        return captureScreen(ScreenUtil.getRectangle());
    }

    public static void click() {
        Robot robot = ROBOT;
        robot.mousePress(16);
        robot.mouseRelease(16);
        delay();
    }

    public static void delay() {
        int i2 = delay;
        if (i2 > 0) {
            ROBOT.delay(i2);
        }
    }

    public static int getDelay() {
        return delay;
    }

    public static Robot getRobot() {
        return ROBOT;
    }

    public static void keyClick(int... iArr) {
        for (int i2 : iArr) {
            Robot robot = ROBOT;
            robot.keyPress(i2);
            robot.keyRelease(i2);
        }
        delay();
    }

    public static void keyPressString(String str) {
        ClipboardUtil.setStr(str);
        keyPressWithCtrl(86);
        delay();
    }

    public static void keyPressWithAlt(int i2) {
        Robot robot = ROBOT;
        robot.keyPress(18);
        robot.keyPress(i2);
        robot.keyRelease(i2);
        robot.keyRelease(18);
        delay();
    }

    public static void keyPressWithCtrl(int i2) {
        Robot robot = ROBOT;
        robot.keyPress(17);
        robot.keyPress(i2);
        robot.keyRelease(i2);
        robot.keyRelease(17);
        delay();
    }

    public static void keyPressWithShift(int i2) {
        Robot robot = ROBOT;
        robot.keyPress(16);
        robot.keyPress(i2);
        robot.keyRelease(i2);
        robot.keyRelease(16);
        delay();
    }

    public static void mouseMove(int i2, int i3) {
        ROBOT.mouseMove(i2, i3);
    }

    public static void mouseWheel(int i2) {
        ROBOT.mouseWheel(i2);
        delay();
    }

    public static void rightClick() {
        Robot robot = ROBOT;
        robot.mousePress(4);
        robot.mouseRelease(4);
        delay();
    }

    public static void setDelay(int i2) {
        delay = i2;
    }

    public static File captureScreen(File file) throws Throwable {
        ImgUtil.write(captureScreen(), file);
        return file;
    }

    public static BufferedImage captureScreen(Rectangle rectangle) {
        return ROBOT.createScreenCapture(rectangle);
    }

    public static File captureScreen(Rectangle rectangle, File file) throws Throwable {
        ImgUtil.write(captureScreen(rectangle), file);
        return file;
    }
}
