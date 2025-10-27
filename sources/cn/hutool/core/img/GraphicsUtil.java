package cn.hutool.core.img;

import cn.hutool.core.util.ObjectUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/* loaded from: classes.dex */
public class GraphicsUtil {
    public static Graphics2D createGraphics(BufferedImage bufferedImage, Color color) {
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        if (color != null) {
            graphics2DCreateGraphics.setColor(color);
            graphics2DCreateGraphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        }
        return graphics2DCreateGraphics;
    }

    public static Graphics drawImg(Graphics graphics, Image image, Point point) {
        return drawImg(graphics, image, new Rectangle(point.x, point.y, image.getWidth((ImageObserver) null), image.getHeight((ImageObserver) null)));
    }

    public static Graphics drawString(Graphics graphics, String str, Font font, Color color, int i2, int i3) {
        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setFont(font);
        int centerY = getCenterY(graphics, i3);
        if (color != null) {
            graphics.setColor(color);
        }
        int length = str.length();
        int i4 = i2 / length;
        for (int i5 = 0; i5 < length; i5++) {
            if (color == null) {
                graphics.setColor(ImgUtil.randomColor());
            }
            graphics.drawString(String.valueOf(str.charAt(i5)), i5 * i4, centerY);
        }
        return graphics;
    }

    public static Graphics drawStringColourful(Graphics graphics, String str, Font font, int i2, int i3) {
        return drawString(graphics, str, font, null, i2, i3);
    }

    public static int getCenterY(Graphics graphics, int i2) {
        FontMetrics fontMetrics;
        try {
            fontMetrics = graphics.getFontMetrics();
        } catch (Exception unused) {
            fontMetrics = null;
        }
        return fontMetrics != null ? ((i2 - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent() : i2 / 3;
    }

    public static Graphics2D setAlpha(Graphics2D graphics2D, float f2) {
        graphics2D.setComposite(AlphaComposite.getInstance(10, f2));
        return graphics2D;
    }

    public static Graphics drawImg(Graphics graphics, Image image, Rectangle rectangle) {
        graphics.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, (ImageObserver) null);
        return graphics;
    }

    public static Graphics drawString(Graphics graphics, String str, Font font, Color color, Rectangle rectangle) {
        Dimension dimension;
        int i2 = rectangle.width;
        int i3 = rectangle.height;
        try {
            dimension = FontUtil.getDimension(graphics.getFontMetrics(font), str);
        } catch (Exception unused) {
            dimension = new Dimension(i2 / 3, i3 / 3);
        }
        rectangle.setSize(dimension.width, dimension.height);
        return drawString(graphics, str, font, color, ImgUtil.getPointBaseCentre(rectangle, i2, i3));
    }

    public static Graphics drawString(Graphics graphics, String str, Font font, Color color, Point point) {
        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setFont(font);
        graphics.setColor((Color) ObjectUtil.defaultIfNull(color, Color.BLACK));
        graphics.drawString(str, point.x, point.y);
        return graphics;
    }
}
