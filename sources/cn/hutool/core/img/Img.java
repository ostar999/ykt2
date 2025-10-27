package cn.hutool.core.img;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/* loaded from: classes.dex */
public class Img implements Serializable {
    private static final long serialVersionUID = 1;
    private Color backgroundColor;
    private boolean positionBaseCentre;
    private float quality;
    private final BufferedImage srcImage;
    private Image targetImage;
    private String targetImageType;

    public Img(BufferedImage bufferedImage) {
        this(bufferedImage, null);
    }

    private static Rectangle calcRotatedSize(int i2, int i3, int i4) {
        if (i4 < 0) {
            i4 += 360;
        }
        if (i4 >= 90) {
            if ((i4 / 90) % 2 == 1) {
                i3 = i2;
                i2 = i3;
            }
            i4 %= 90;
        }
        double d3 = i4;
        double dSin = Math.sin(Math.toRadians(d3) / 2.0d) * 2.0d * (Math.sqrt((i3 * i3) + (i2 * i2)) / 2.0d);
        double radians = (3.141592653589793d - Math.toRadians(d3)) / 2.0d;
        double d4 = i3;
        double d5 = i2;
        double d6 = 3.141592653589793d - radians;
        return new Rectangle(i2 + (((int) (Math.cos(d6 - Math.atan(d4 / d5)) * dSin)) * 2), i3 + (((int) (dSin * Math.cos(d6 - Math.atan(d5 / d4)))) * 2));
    }

    private BufferedImage draw(BufferedImage bufferedImage, Image image, Rectangle rectangle, float f2) {
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        GraphicsUtil.setAlpha(graphics2DCreateGraphics, f2);
        fixRectangle(rectangle, bufferedImage.getWidth(), bufferedImage.getHeight());
        GraphicsUtil.drawImg((Graphics) graphics2DCreateGraphics, image, rectangle);
        graphics2DCreateGraphics.dispose();
        return bufferedImage;
    }

    private Rectangle fixRectangle(Rectangle rectangle, int i2, int i3) {
        if (this.positionBaseCentre) {
            Point pointBaseCentre = ImgUtil.getPointBaseCentre(rectangle, i2, i3);
            rectangle.setLocation(pointBaseCentre.x, pointBaseCentre.y);
        }
        return rectangle;
    }

    public static Img from(Path path) {
        return from(path.toFile());
    }

    private int getTypeInt() {
        String str = this.targetImageType;
        str.hashCode();
        return !str.equals("png") ? 1 : 2;
    }

    private BufferedImage getValidSrcBufferedImg() {
        return ImgUtil.toBufferedImage(getValidSrcImg(), this.targetImageType);
    }

    private Image getValidSrcImg() {
        return (Image) ObjectUtil.defaultIfNull((BufferedImage) this.targetImage, this.srcImage);
    }

    public Img binary() {
        this.targetImage = ImgUtil.copyImage(getValidSrcImg(), 12);
        return this;
    }

    public Img cut(Rectangle rectangle) {
        Image validSrcImg = getValidSrcImg();
        fixRectangle(rectangle, validSrcImg.getWidth((ImageObserver) null), validSrcImg.getHeight((ImageObserver) null));
        this.targetImage = ImgUtil.filter((ImageFilter) new CropImageFilter(rectangle.x, rectangle.y, rectangle.width, rectangle.height), validSrcImg);
        return this;
    }

    public Img flip() {
        Image validSrcImg = getValidSrcImg();
        int width = validSrcImg.getWidth((ImageObserver) null);
        int height = validSrcImg.getHeight((ImageObserver) null);
        BufferedImage bufferedImage = new BufferedImage(width, height, getTypeInt());
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.drawImage(validSrcImg, 0, 0, width, height, width, 0, 0, height, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public Image getImg() {
        return getValidSrcImg();
    }

    public Img gray() {
        this.targetImage = ImgUtil.colorConvert(ColorSpace.getInstance(1003), getValidSrcBufferedImg());
        return this;
    }

    public Img pressImage(Image image, int i2, int i3, float f2) {
        return pressImage(image, new Rectangle(i2, i3, image.getWidth((ImageObserver) null), image.getHeight((ImageObserver) null)), f2);
    }

    public Img pressText(String str, Color color, Font font, int i2, int i3, float f2) {
        return pressText(str, color, font, new Point(i2, i3), f2);
    }

    public Img pressTextFull(String str, Color color, Font font, int i2, int i3, float f2) {
        Dimension dimension;
        Dimension dimension2;
        int i4;
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(getValidSrcImg(), this.targetImageType);
        Font fontCreateSansSerifFont = font == null ? FontUtil.createSansSerifFont((int) (bufferedImage.getHeight() * 0.75d)) : font;
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.setColor(color);
        graphics2DCreateGraphics.rotate(Math.toRadians(i3), width >> 1, height >> 1);
        graphics2DCreateGraphics.setComposite(AlphaComposite.getInstance(10, f2));
        try {
            dimension = FontUtil.getDimension(graphics2DCreateGraphics.getFontMetrics(fontCreateSansSerifFont), str);
        } catch (Exception unused) {
            dimension = new Dimension(width / 3, height / 3);
        }
        int i5 = dimension.height * i2;
        int i6 = (-height) >> 1;
        while (i6 < height * 1.5d) {
            int i7 = (-width) >> 1;
            while (true) {
                dimension2 = dimension;
                i4 = i5;
                if (i7 < width * 1.5d) {
                    GraphicsUtil.drawString((Graphics) graphics2DCreateGraphics, str, fontCreateSansSerifFont, color, new Point(i7, i6));
                    dimension = dimension2;
                    i7 += dimension.width;
                    i5 = i4;
                }
            }
            dimension = dimension2;
            i6 += i4;
            i5 = i4;
        }
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public Img rotate(int i2) {
        Image validSrcImg = getValidSrcImg();
        int width = validSrcImg.getWidth((ImageObserver) null);
        int height = validSrcImg.getHeight((ImageObserver) null);
        Rectangle rectangleCalcRotatedSize = calcRotatedSize(width, height, i2);
        BufferedImage bufferedImage = new BufferedImage(rectangleCalcRotatedSize.width, rectangleCalcRotatedSize.height, getTypeInt());
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2DCreateGraphics.translate((rectangleCalcRotatedSize.width - width) / 2.0d, (rectangleCalcRotatedSize.height - height) / 2.0d);
        graphics2DCreateGraphics.rotate(Math.toRadians(i2), width / 2.0d, height / 2.0d);
        graphics2DCreateGraphics.drawImage(validSrcImg, 0, 0, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public Img round(double d3) {
        Image validSrcImg = getValidSrcImg();
        int width = validSrcImg.getWidth((ImageObserver) null);
        int height = validSrcImg.getHeight((ImageObserver) null);
        double dMul = NumberUtil.mul(d3, Math.min(width, height));
        BufferedImage bufferedImage = new BufferedImage(width, height, 2);
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.setComposite(AlphaComposite.Src);
        graphics2DCreateGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2DCreateGraphics.fill(new RoundRectangle2D.Double(0.0d, 0.0d, width, height, dMul, dMul));
        graphics2DCreateGraphics.setComposite(AlphaComposite.SrcAtop);
        graphics2DCreateGraphics.drawImage(validSrcImg, 0, 0, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public Img scale(float f2) {
        if (f2 < 0.0f) {
            f2 = -f2;
        }
        Image validSrcImg = getValidSrcImg();
        if ("png".equals(this.targetImageType)) {
            double d3 = NumberUtil.toDouble(Float.valueOf(f2));
            this.targetImage = ImgUtil.transform(AffineTransform.getScaleInstance(d3, d3), ImgUtil.toBufferedImage(validSrcImg, this.targetImageType));
        } else {
            scale(NumberUtil.mul(Integer.valueOf(validSrcImg.getWidth((ImageObserver) null)), Float.valueOf(f2)).intValue(), NumberUtil.mul(Integer.valueOf(validSrcImg.getHeight((ImageObserver) null)), Float.valueOf(f2)).intValue());
        }
        return this;
    }

    public Img setBackgroundColor(Color color) {
        this.backgroundColor = color;
        return this;
    }

    public Img setPositionBaseCentre(boolean z2) {
        this.positionBaseCentre = z2;
        return this;
    }

    public Img setQuality(double d3) {
        return setQuality((float) d3);
    }

    public Img setTargetImageType(String str) {
        this.targetImageType = str;
        return this;
    }

    public Img stroke(Color color, float f2) {
        return stroke(color, (Stroke) new BasicStroke(f2));
    }

    public boolean write(OutputStream outputStream) throws IORuntimeException {
        return write(ImgUtil.getImageOutputStream(outputStream));
    }

    public Img(BufferedImage bufferedImage, String str) {
        this.positionBaseCentre = true;
        this.quality = -1.0f;
        this.srcImage = bufferedImage;
        this.targetImageType = str == null ? (bufferedImage.getType() == 2 || bufferedImage.getType() == 3 || bufferedImage.getType() == 6 || bufferedImage.getType() == 7) ? "png" : "jpg" : str;
    }

    public static Img from(File file) {
        return new Img(ImgUtil.read(file));
    }

    public Img pressText(String str, Color color, Font font, Point point, float f2) {
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(getValidSrcImg(), this.targetImageType);
        if (font == null) {
            font = FontUtil.createSansSerifFont((int) (bufferedImage.getHeight() * 0.75d));
        }
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.setComposite(AlphaComposite.getInstance(10, f2));
        if (this.positionBaseCentre) {
            GraphicsUtil.drawString((Graphics) graphics2DCreateGraphics, str, font, color, new Rectangle(point.x, point.y, bufferedImage.getWidth(), bufferedImage.getHeight()));
        } else {
            GraphicsUtil.drawString((Graphics) graphics2DCreateGraphics, str, font, color, point);
        }
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public Img setQuality(float f2) {
        if (f2 <= 0.0f || f2 >= 1.0f) {
            this.quality = 1.0f;
        } else {
            this.quality = f2;
        }
        return this;
    }

    public Img stroke(Color color, Stroke stroke) {
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(getValidSrcImg(), this.targetImageType);
        int width = bufferedImage.getWidth((ImageObserver) null);
        int height = bufferedImage.getHeight((ImageObserver) null);
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        graphics2DCreateGraphics.setColor((Color) ObjectUtil.defaultIfNull(color, Color.BLACK));
        if (stroke != null) {
            graphics2DCreateGraphics.setStroke(stroke);
        }
        graphics2DCreateGraphics.drawRect(0, 0, width - 1, height - 1);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public boolean write(ImageOutputStream imageOutputStream) throws IllegalArgumentException, IORuntimeException {
        Assert.notBlank(this.targetImageType, "Target image type is blank !", new Object[0]);
        Assert.notNull(imageOutputStream, "Target output stream is null !", new Object[0]);
        BufferedImage bufferedImage = this.targetImage;
        if (bufferedImage == null) {
            bufferedImage = this.srcImage;
        }
        Assert.notNull(bufferedImage, "Target image is null !", new Object[0]);
        return ImgUtil.write(bufferedImage, this.targetImageType, imageOutputStream, this.quality, this.backgroundColor);
    }

    public static Img from(Resource resource) {
        return from(resource.getStream());
    }

    public static Img from(InputStream inputStream) {
        return new Img(ImgUtil.read(inputStream));
    }

    public Img pressImage(Image image, Rectangle rectangle, float f2) {
        this.targetImage = draw(ImgUtil.toBufferedImage(getValidSrcImg(), this.targetImageType), image, rectangle, f2);
        return this;
    }

    public static Img from(ImageInputStream imageInputStream) {
        return new Img(ImgUtil.read(imageInputStream));
    }

    public Img cut(int i2, int i3) {
        return cut(i2, i3, -1);
    }

    public static Img from(URL url) {
        return new Img(ImgUtil.read(url));
    }

    public Img cut(int i2, int i3, int i4) {
        int i5;
        int i6;
        Image validSrcImg = getValidSrcImg();
        int width = validSrcImg.getWidth((ImageObserver) null);
        int height = validSrcImg.getHeight((ImageObserver) null);
        int iMin = i4 > 0 ? i4 * 2 : Math.min(width, height);
        BufferedImage bufferedImage = new BufferedImage(iMin, iMin, 2);
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        double d3 = iMin;
        graphics2DCreateGraphics.setClip(new Ellipse2D.Double(0.0d, 0.0d, d3, d3));
        if (this.positionBaseCentre) {
            int i7 = iMin / 2;
            i5 = (i2 - (width / 2)) + i7;
            i6 = (i3 - (height / 2)) + i7;
        } else {
            i5 = i2;
            i6 = i3;
        }
        graphics2DCreateGraphics.drawImage(validSrcImg, i5, i6, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }

    public static Img from(Image image) {
        return new Img(ImgUtil.toBufferedImage(image));
    }

    public boolean write(File file) throws IORuntimeException {
        ImageOutputStream imageOutputStream;
        String strExtName = FileUtil.extName(file);
        if (CharSequenceUtil.isNotBlank(strExtName)) {
            this.targetImageType = strExtName;
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            imageOutputStream = ImgUtil.getImageOutputStream(file);
        } catch (Throwable th) {
            th = th;
            imageOutputStream = null;
        }
        try {
            boolean zWrite = write(imageOutputStream);
            IoUtil.close((Closeable) imageOutputStream);
            return zWrite;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) imageOutputStream);
            throw th;
        }
    }

    public Img scale(int i2, int i3) {
        return scale(i2, i3, 4);
    }

    public Img scale(int i2, int i3, int i4) {
        Image validSrcImg = getValidSrcImg();
        int height = validSrcImg.getHeight((ImageObserver) null);
        int width = validSrcImg.getWidth((ImageObserver) null);
        if (height == i3 && width == i2) {
            this.targetImage = validSrcImg;
            return this;
        }
        if ("png".equals(this.targetImageType)) {
            this.targetImage = ImgUtil.transform(AffineTransform.getScaleInstance(NumberUtil.div(i2, width), NumberUtil.div(i3, height)), ImgUtil.toBufferedImage(validSrcImg, this.targetImageType));
        } else {
            this.targetImage = validSrcImg.getScaledInstance(i2, i3, i4);
        }
        return this;
    }

    public Img scale(int i2, int i3, Color color) {
        Image validSrcImg = getValidSrcImg();
        int height = validSrcImg.getHeight((ImageObserver) null);
        int width = validSrcImg.getWidth((ImageObserver) null);
        double dDiv = NumberUtil.div(i3, height);
        double dDiv2 = NumberUtil.div(i2, width);
        if (NumberUtil.equals(dDiv, dDiv2)) {
            scale(i2, i3);
        } else if (dDiv2 < dDiv) {
            scale(i2, (int) (height * dDiv2));
        } else {
            scale((int) (width * dDiv), i3);
        }
        Image validSrcImg2 = getValidSrcImg();
        int height2 = validSrcImg2.getHeight((ImageObserver) null);
        int width2 = validSrcImg2.getWidth((ImageObserver) null);
        BufferedImage bufferedImage = new BufferedImage(i2, i3, getTypeInt());
        Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
        if (color != null) {
            graphics2DCreateGraphics.setBackground(color);
            graphics2DCreateGraphics.clearRect(0, 0, i2, i3);
        }
        graphics2DCreateGraphics.drawImage(validSrcImg2, (i2 - width2) / 2, (i3 - height2) / 2, width2, height2, color, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        this.targetImage = bufferedImage;
        return this;
    }
}
