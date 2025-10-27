package cn.hutool.core.img;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

/* loaded from: classes.dex */
public class ImgUtil {
    public static final String IMAGE_TYPE_BMP = "bmp";
    public static final String IMAGE_TYPE_GIF = "gif";
    public static final String IMAGE_TYPE_JPEG = "jpeg";
    public static final String IMAGE_TYPE_JPG = "jpg";
    public static final String IMAGE_TYPE_PNG = "png";
    public static final String IMAGE_TYPE_PSD = "psd";

    public static boolean backgroundRemoval(String str, String str2, int i2) {
        return BackgroundRemoval.backgroundRemoval(str, str2, i2);
    }

    public static void binary(File file, File file2) throws Throwable {
        binary((Image) read(file), file2);
    }

    public static BufferedImage colorConvert(ColorSpace colorSpace, BufferedImage bufferedImage) {
        return filter((BufferedImageOp) new ColorConvertOp(colorSpace, (RenderingHints) null), bufferedImage);
    }

    public static void compress(File file, File file2, float f2) throws IORuntimeException {
        Img.from(file).setQuality(f2).write(file2);
    }

    public static void convert(File file, File file2) throws Throwable {
        ImageOutputStream imageOutputStream;
        Assert.notNull(file);
        Assert.notNull(file2);
        Assert.isFalse(file.equals(file2), "Src file is equals to dest file!", new Object[0]);
        String strExtName = FileUtil.extName(file);
        String strExtName2 = FileUtil.extName(file2);
        if (CharSequenceUtil.equalsIgnoreCase(strExtName, strExtName2)) {
            FileUtil.copy(file, file2, true);
        }
        try {
            imageOutputStream = getImageOutputStream(file2);
        } catch (Throwable th) {
            th = th;
            imageOutputStream = null;
        }
        try {
            convert(read(file), strExtName2, imageOutputStream, CharSequenceUtil.equalsIgnoreCase("png", strExtName));
            IoUtil.close((Closeable) imageOutputStream);
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) imageOutputStream);
            throw th;
        }
    }

    public static BufferedImage copyImage(Image image, int i2) {
        return copyImage(image, i2, null);
    }

    public static BufferedImage createCompatibleImage(int i2, int i3, int i4) throws HeadlessException {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(i2, i3, i4);
    }

    public static Font createFont(File file) {
        return FontUtil.createFont(file);
    }

    public static Graphics2D createGraphics(BufferedImage bufferedImage, Color color) {
        return GraphicsUtil.createGraphics(bufferedImage, color);
    }

    public static void createImage(String str, Font font, Color color, Color color2, ImageOutputStream imageOutputStream) throws IORuntimeException {
        writePng((Image) createImage(str, font, color, color2, 2), imageOutputStream);
    }

    public static void createTransparentImage(String str, Font font, Color color, ImageOutputStream imageOutputStream) throws IORuntimeException {
        writePng((Image) createImage(str, font, (Color) null, color, 2), imageOutputStream);
    }

    public static void cut(File file, File file2, Rectangle rectangle) throws Throwable {
        cut((Image) read(file), file2, rectangle);
    }

    public static BufferedImage filter(BufferedImageOp bufferedImageOp, BufferedImage bufferedImage) {
        return bufferedImageOp.filter(bufferedImage, (BufferedImage) null);
    }

    public static void flip(File file, File file2) throws Throwable {
        flip((Image) read(file), file2);
    }

    public static Color getColor(int i2) {
        return ColorUtil.getColor(i2);
    }

    public static Image getImage(URL url) {
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static ImageInputStream getImageInputStream(InputStream inputStream) throws IORuntimeException {
        try {
            ImageOutputStream imageOutputStreamCreateImageOutputStream = ImageIO.createImageOutputStream(inputStream);
            if (imageOutputStreamCreateImageOutputStream != null) {
                return imageOutputStreamCreateImageOutputStream;
            }
            throw new IllegalArgumentException("Image type is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ImageOutputStream getImageOutputStream(OutputStream outputStream) throws IORuntimeException {
        try {
            ImageOutputStream imageOutputStreamCreateImageOutputStream = ImageIO.createImageOutputStream(outputStream);
            if (imageOutputStreamCreateImageOutputStream != null) {
                return imageOutputStreamCreateImageOutputStream;
            }
            throw new IllegalArgumentException("Image type is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getMainColor(BufferedImage bufferedImage, int[]... iArr) {
        return ColorUtil.getMainColor(bufferedImage, iArr);
    }

    public static Point getPointBaseCentre(Rectangle rectangle, int i2, int i3) {
        return new Point(rectangle.x + (Math.abs(i2 - rectangle.width) / 2), rectangle.y + (Math.abs(i3 - rectangle.height) / 2));
    }

    public static ImageReader getReader(String str) {
        Iterator imageReadersByFormatName = ImageIO.getImageReadersByFormatName(str);
        if (imageReadersByFormatName.hasNext()) {
            return (ImageReader) imageReadersByFormatName.next();
        }
        return null;
    }

    public static Rectangle2D getRectangle(String str, Font font) {
        return font.getStringBounds(str, new FontRenderContext(AffineTransform.getScaleInstance(1.0d, 1.0d), false, false));
    }

    public static ImageWriter getWriter(Image image, String str) {
        Iterator imageWriters = ImageIO.getImageWriters(ImageTypeSpecifier.createFromRenderedImage(toBufferedImage(image, str)), str);
        if (imageWriters.hasNext()) {
            return (ImageWriter) imageWriters.next();
        }
        return null;
    }

    public static void gray(File file, File file2) throws Throwable {
        gray((Image) read(file), file2);
    }

    public static Color hexToColor(String str) {
        return ColorUtil.hexToColor(str);
    }

    public static void pressImage(File file, File file2, Image image, int i2, int i3, float f2) throws Throwable {
        pressImage((Image) read(file), file2, image, i2, i3, f2);
    }

    public static void pressText(File file, File file2, String str, Color color, Font font, int i2, int i3, float f2) throws Throwable {
        pressText((Image) read(file), file2, str, color, font, i2, i3, f2);
    }

    public static Color randomColor() {
        return ColorUtil.randomColor();
    }

    public static BufferedImage read(String str) {
        return read(FileUtil.file(str));
    }

    public static void rotate(File file, int i2, File file2) throws Throwable {
        rotate((Image) read(file), i2, file2);
    }

    public static void scale(File file, File file2, float f2) throws IORuntimeException {
        scale((Image) read(file), file2, f2);
    }

    public static void slice(File file, File file2, int i2, int i3) throws Throwable {
        slice((Image) read(file), file2, i2, i3);
    }

    public static void sliceByRowsAndCols(File file, File file2, int i2, int i3) {
        sliceByRowsAndCols(file, file2, "jpeg", i2, i3);
    }

    public static String toBase64(Image image, String str) {
        return Base64.encode(toBytes(image, str));
    }

    public static String toBase64DataUri(Image image, String str) {
        return URLUtil.getDataUri("image/" + str, "base64", toBase64(image, str));
    }

    public static BufferedImage toBufferedImage(Image image) {
        return image instanceof BufferedImage ? (BufferedImage) image : copyImage(image, 1);
    }

    public static byte[] toBytes(Image image, String str) throws IORuntimeException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        write(image, str, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String toHex(Color color) {
        return ColorUtil.toHex(color);
    }

    public static BufferedImage toImage(String str) throws IORuntimeException {
        return toImage(Base64.decode(str));
    }

    public static RenderedImage toRenderedImage(Image image) {
        return image instanceof RenderedImage ? (RenderedImage) image : copyImage(image, 1);
    }

    public static ByteArrayInputStream toStream(Image image, String str) {
        return IoUtil.toStream(toBytes(image, str));
    }

    public static BufferedImage transform(AffineTransform affineTransform, BufferedImage bufferedImage) {
        return filter((BufferedImageOp) new AffineTransformOp(affineTransform, (RenderingHints) null), bufferedImage);
    }

    public static void write(ImageInputStream imageInputStream, String str, ImageOutputStream imageOutputStream) throws IORuntimeException {
        write((Image) read(imageInputStream), str, imageOutputStream);
    }

    public static void writeJpg(Image image, ImageOutputStream imageOutputStream) throws IORuntimeException {
        write(image, "jpg", imageOutputStream);
    }

    public static void writePng(Image image, ImageOutputStream imageOutputStream) throws IORuntimeException {
        write(image, "png", imageOutputStream);
    }

    public static boolean backgroundRemoval(File file, File file2, int i2) {
        return BackgroundRemoval.backgroundRemoval(file, file2, i2);
    }

    public static void binary(InputStream inputStream, OutputStream outputStream, String str) throws IORuntimeException {
        binary((Image) read(inputStream), getImageOutputStream(outputStream), str);
    }

    public static BufferedImage copyImage(Image image, int i2, Color color) {
        Image image2 = new ImageIcon(image).getImage();
        BufferedImage bufferedImage = new BufferedImage(image2.getWidth((ImageObserver) null), image2.getHeight((ImageObserver) null), i2);
        Graphics2D graphics2DCreateGraphics = GraphicsUtil.createGraphics(bufferedImage, color);
        graphics2DCreateGraphics.drawImage(image2, 0, 0, (ImageObserver) null);
        graphics2DCreateGraphics.dispose();
        return bufferedImage;
    }

    public static Font createFont(InputStream inputStream) {
        return FontUtil.createFont(inputStream);
    }

    public static BufferedImage createImage(String str, Font font, Color color, Color color2, int i2) throws IORuntimeException {
        Rectangle2D rectangle = getRectangle(str, font);
        int iFloor = (int) Math.floor(rectangle.getHeight());
        int iRound = ((int) Math.round(rectangle.getWidth())) + 1;
        int i3 = iFloor + 3;
        BufferedImage bufferedImage = new BufferedImage(iRound, i3, i2);
        Graphics graphics = bufferedImage.getGraphics();
        if (color != null) {
            graphics.setColor(color);
            graphics.fillRect(0, 0, iRound, i3);
        }
        graphics.setColor((Color) ObjectUtil.defaultIfNull(color2, Color.BLACK));
        graphics.setFont(font);
        graphics.drawString(str, 0, font.getSize());
        graphics.dispose();
        return bufferedImage;
    }

    public static void cut(InputStream inputStream, OutputStream outputStream, Rectangle rectangle) throws IORuntimeException {
        cut((Image) read(inputStream), outputStream, rectangle);
    }

    public static Image filter(ImageFilter imageFilter, Image image) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), imageFilter));
    }

    public static void flip(Image image, File file) throws Throwable {
        write(flip(image), file);
    }

    public static Color getColor(String str) {
        return ColorUtil.getColor(str);
    }

    public static void gray(InputStream inputStream, OutputStream outputStream) throws IORuntimeException {
        gray((Image) read(inputStream), getImageOutputStream(outputStream));
    }

    public static void pressImage(InputStream inputStream, OutputStream outputStream, Image image, int i2, int i3, float f2) throws IORuntimeException {
        pressImage((Image) read(inputStream), getImageOutputStream(outputStream), image, i2, i3, f2);
    }

    public static void pressText(InputStream inputStream, OutputStream outputStream, String str, Color color, Font font, int i2, int i3, float f2) throws IORuntimeException {
        pressText((Image) read(inputStream), getImageOutputStream(outputStream), str, color, font, i2, i3, f2);
    }

    public static Color randomColor(Random random) {
        return ColorUtil.randomColor(random);
    }

    public static BufferedImage read(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            throw new IllegalArgumentException("Image type of file [" + file.getName() + "] is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void rotate(Image image, int i2, File file) throws Throwable {
        write(rotate(image, i2), file);
    }

    public static void scale(InputStream inputStream, OutputStream outputStream, float f2) throws IORuntimeException {
        scale((Image) read(inputStream), outputStream, f2);
    }

    public static void slice(Image image, File file, int i2, int i3) throws Throwable {
        if (i2 <= 0) {
            i2 = 200;
        }
        if (i3 <= 0) {
            i3 = 150;
        }
        int width = image.getWidth((ImageObserver) null);
        int height = image.getHeight((ImageObserver) null);
        if (width < i2) {
            i2 = width;
        }
        if (height < i3) {
            i3 = height;
        }
        int iFloor = width % i2 == 0 ? width / i2 : ((int) Math.floor(width / i2)) + 1;
        int iFloor2 = height % i3 == 0 ? height / i3 : ((int) Math.floor(height / i3)) + 1;
        for (int i4 = 0; i4 < iFloor2; i4++) {
            for (int i5 = 0; i5 < iFloor; i5++) {
                write(cut(image, new Rectangle(i5 * i2, i4 * i3, i2, i3)), FileUtil.file(file, "_r" + i4 + "_c" + i5 + ".jpg"));
            }
        }
    }

    public static void sliceByRowsAndCols(File file, File file2, String str, int i2, int i3) {
        try {
            sliceByRowsAndCols((Image) ImageIO.read(file), file2, str, i2, i3);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String toHex(int i2, int i3, int i4) {
        return ColorUtil.toHex(i2, i3, i4);
    }

    public static BufferedImage toImage(byte[] bArr) throws IORuntimeException {
        return read(new ByteArrayInputStream(bArr));
    }

    public static void write(Image image, String str, OutputStream outputStream) throws IORuntimeException {
        write(image, str, getImageOutputStream(outputStream));
    }

    public static void writeJpg(Image image, OutputStream outputStream) throws IORuntimeException {
        write(image, "jpg", outputStream);
    }

    public static void writePng(Image image, OutputStream outputStream) throws IORuntimeException {
        write(image, "png", outputStream);
    }

    public static boolean backgroundRemoval(File file, File file2, Color color, int i2) {
        return BackgroundRemoval.backgroundRemoval(file, file2, color, i2);
    }

    public static void binary(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, String str) throws IORuntimeException {
        binary((Image) read(imageInputStream), imageOutputStream, str);
    }

    public static void cut(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, Rectangle rectangle) throws IORuntimeException {
        cut((Image) read(imageInputStream), imageOutputStream, rectangle);
    }

    public static void flip(Image image, OutputStream outputStream) throws IORuntimeException {
        flip(image, getImageOutputStream(outputStream));
    }

    public static void gray(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream) throws IORuntimeException {
        gray((Image) read(imageInputStream), imageOutputStream);
    }

    public static void pressImage(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, Image image, int i2, int i3, float f2) throws IORuntimeException {
        pressImage((Image) read(imageInputStream), imageOutputStream, image, i2, i3, f2);
    }

    public static void pressText(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, String str, Color color, Font font, int i2, int i3, float f2) throws IORuntimeException {
        pressText((Image) read(imageInputStream), imageOutputStream, str, color, font, i2, i3, f2);
    }

    public static void rotate(Image image, int i2, OutputStream outputStream) throws IORuntimeException {
        writeJpg(rotate(image, i2), getImageOutputStream(outputStream));
    }

    public static void scale(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, float f2) throws IORuntimeException {
        scale((Image) read(imageInputStream), imageOutputStream, f2);
    }

    public static boolean write(Image image, String str, ImageOutputStream imageOutputStream) throws IORuntimeException {
        return write(image, str, imageOutputStream, 1.0f);
    }

    public static BufferedImage backgroundRemoval(BufferedImage bufferedImage, Color color, int i2) {
        return BackgroundRemoval.backgroundRemoval(bufferedImage, color, i2);
    }

    public static void binary(Image image, File file) throws Throwable {
        write(binary(image), file);
    }

    public static void cut(Image image, File file, Rectangle rectangle) throws Throwable {
        write(cut(image, rectangle), file);
    }

    public static void flip(Image image, ImageOutputStream imageOutputStream) throws IORuntimeException {
        writeJpg(flip(image), imageOutputStream);
    }

    public static ImageOutputStream getImageOutputStream(File file) throws IORuntimeException {
        try {
            ImageOutputStream imageOutputStreamCreateImageOutputStream = ImageIO.createImageOutputStream(file);
            if (imageOutputStreamCreateImageOutputStream != null) {
                return imageOutputStreamCreateImageOutputStream;
            }
            throw new IllegalArgumentException("Image type of file [" + file.getName() + "] is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ImageWriter getWriter(String str) {
        Iterator imageWritersByFormatName = ImageIO.getImageWritersByFormatName(str);
        ImageWriter imageWriter = imageWritersByFormatName.hasNext() ? (ImageWriter) imageWritersByFormatName.next() : null;
        if (imageWriter != null) {
            return imageWriter;
        }
        Iterator imageWritersBySuffix = ImageIO.getImageWritersBySuffix(str);
        return imageWritersBySuffix.hasNext() ? (ImageWriter) imageWritersBySuffix.next() : imageWriter;
    }

    public static void gray(Image image, File file) throws Throwable {
        write(gray(image), file);
    }

    public static void pressImage(Image image, File file, Image image2, int i2, int i3, float f2) throws Throwable {
        write(pressImage(image, image2, i2, i3, f2), file);
    }

    public static void pressText(Image image, File file, String str, Color color, Font font, int i2, int i3, float f2) throws Throwable {
        write(pressText(image, str, color, font, i2, i3, f2), file);
    }

    public static void rotate(Image image, int i2, ImageOutputStream imageOutputStream) throws IORuntimeException {
        writeJpg(rotate(image, i2), imageOutputStream);
    }

    public static void scale(Image image, File file, float f2) throws IORuntimeException {
        Img.from(image).setTargetImageType(FileUtil.extName(file)).scale(f2).write(file);
    }

    public static void sliceByRowsAndCols(Image image, File file, int i2, int i3) {
        sliceByRowsAndCols(image, file, "jpeg", i2, i3);
    }

    public static BufferedImage toBufferedImage(Image image, String str) {
        return toBufferedImage(image, str, (Color) null);
    }

    public static boolean write(Image image, String str, ImageOutputStream imageOutputStream, float f2) throws IORuntimeException {
        return write(image, str, imageOutputStream, f2, null);
    }

    public static BufferedImage backgroundRemoval(ByteArrayOutputStream byteArrayOutputStream, Color color, int i2) {
        return BackgroundRemoval.backgroundRemoval(byteArrayOutputStream, color, i2);
    }

    public static void binary(Image image, OutputStream outputStream, String str) throws IORuntimeException {
        binary(image, getImageOutputStream(outputStream), str);
    }

    public static void cut(Image image, OutputStream outputStream, Rectangle rectangle) throws IORuntimeException {
        cut(image, getImageOutputStream(outputStream), rectangle);
    }

    public static Image flip(Image image) {
        return Img.from(image).flip().getImg();
    }

    public static void gray(Image image, OutputStream outputStream) throws IORuntimeException {
        gray(image, getImageOutputStream(outputStream));
    }

    public static void pressImage(Image image, OutputStream outputStream, Image image2, int i2, int i3, float f2) throws IORuntimeException {
        pressImage(image, getImageOutputStream(outputStream), image2, i2, i3, f2);
    }

    public static void pressText(Image image, OutputStream outputStream, String str, Color color, Font font, int i2, int i3, float f2) throws IORuntimeException {
        pressText(image, getImageOutputStream(outputStream), str, color, font, i2, i3, f2);
    }

    public static BufferedImage read(Resource resource) {
        return read(resource.getStream());
    }

    public static Image rotate(Image image, int i2) {
        return Img.from(image).rotate(i2).getImg();
    }

    public static void scale(Image image, OutputStream outputStream, float f2) throws IORuntimeException {
        scale(image, getImageOutputStream(outputStream), f2);
    }

    public static void sliceByRowsAndCols(Image image, File file, String str, int i2, int i3) {
        if (!file.exists()) {
            FileUtil.mkdir(file);
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException("Destination Dir must be a Directory !");
        }
        if (i2 <= 0 || i2 > 20) {
            i2 = 2;
        }
        if (i3 <= 0 || i3 > 20) {
            i3 = 2;
        }
        try {
            BufferedImage bufferedImage = toBufferedImage(image);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int iPartValue = NumberUtil.partValue(width, i3);
            int iPartValue2 = NumberUtil.partValue(height, i2);
            for (int i4 = 0; i4 < i2; i4++) {
                for (int i5 = 0; i5 < i3; i5++) {
                    ImageIO.write(toRenderedImage(cut(bufferedImage, new Rectangle(i5 * iPartValue, i4 * iPartValue2, iPartValue, iPartValue2))), str, new File(file, "_r" + i4 + "_c" + i5 + StrPool.DOT + str));
                }
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedImage toBufferedImage(Image image, String str, Color color) {
        return toBufferedImage(image, "png".equalsIgnoreCase(str) ? 2 : 1, color);
    }

    public static boolean write(Image image, String str, ImageOutputStream imageOutputStream, float f2, Color color) throws IORuntimeException {
        if (CharSequenceUtil.isBlank(str)) {
            str = "jpg";
        }
        BufferedImage bufferedImage = toBufferedImage(image, str, color);
        return write((Image) bufferedImage, getWriter(bufferedImage, str), imageOutputStream, f2);
    }

    public static void binary(Image image, ImageOutputStream imageOutputStream, String str) throws IORuntimeException {
        write(binary(image), str, imageOutputStream);
    }

    public static void cut(Image image, ImageOutputStream imageOutputStream, Rectangle rectangle) throws IORuntimeException {
        writeJpg(cut(image, rectangle), imageOutputStream);
    }

    public static void gray(Image image, ImageOutputStream imageOutputStream) throws IORuntimeException {
        writeJpg(gray(image), imageOutputStream);
    }

    public static void pressImage(Image image, ImageOutputStream imageOutputStream, Image image2, int i2, int i3, float f2) throws IORuntimeException {
        writeJpg(pressImage(image, image2, i2, i3, f2), imageOutputStream);
    }

    public static void pressText(Image image, ImageOutputStream imageOutputStream, String str, Color color, Font font, int i2, int i3, float f2) throws IORuntimeException {
        writeJpg(pressText(image, str, color, font, i2, i3, f2), imageOutputStream);
    }

    public static BufferedImage read(InputStream inputStream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            throw new IllegalArgumentException("Image type is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void scale(Image image, ImageOutputStream imageOutputStream, float f2) throws IORuntimeException {
        writeJpg(scale(image, f2), imageOutputStream);
    }

    public static Image binary(Image image) {
        return Img.from(image).binary().getImg();
    }

    public static Image cut(Image image, Rectangle rectangle) {
        return Img.from(image).setPositionBaseCentre(false).cut(rectangle).getImg();
    }

    public static Image gray(Image image) {
        return Img.from(image).gray().getImg();
    }

    public static Image pressImage(Image image, Image image2, int i2, int i3, float f2) {
        return Img.from(image).pressImage(image2, i2, i3, f2).getImg();
    }

    public static Image pressText(Image image, String str, Color color, Font font, int i2, int i3, float f2) {
        return Img.from(image).pressText(str, color, font, i2, i3, f2).getImg();
    }

    public static Image scale(Image image, float f2) {
        return Img.from(image).scale(f2).getImg();
    }

    public static BufferedImage toBufferedImage(Image image, int i2) {
        if (image instanceof BufferedImage) {
            BufferedImage bufferedImage = (BufferedImage) image;
            return i2 != bufferedImage.getType() ? copyImage(image, i2) : bufferedImage;
        }
        return copyImage(image, i2);
    }

    public static Image cut(Image image, int i2, int i3) {
        return cut(image, i2, i3, -1);
    }

    public static Image pressImage(Image image, Image image2, Rectangle rectangle, float f2) {
        return Img.from(image).pressImage(image2, rectangle, f2).getImg();
    }

    public static Image scale(Image image, int i2, int i3) {
        return Img.from(image).scale(i2, i3).getImg();
    }

    public static Image cut(Image image, int i2, int i3, int i4) {
        return Img.from(image).cut(i2, i3, i4).getImg();
    }

    public static BufferedImage read(ImageInputStream imageInputStream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageInputStream);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            throw new IllegalArgumentException("Image type is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void scale(File file, File file2, int i2, int i3, Color color) throws IORuntimeException {
        Img.from(file).setTargetImageType(FileUtil.extName(file2)).scale(i2, i3, color).write(file2);
    }

    public static void write(Image image, File file) throws Throwable {
        ImageOutputStream imageOutputStream;
        FileUtil.touch(file);
        try {
            imageOutputStream = getImageOutputStream(file);
        } catch (Throwable th) {
            th = th;
            imageOutputStream = null;
        }
        try {
            write(image, FileUtil.extName(file), imageOutputStream);
            IoUtil.close((Closeable) imageOutputStream);
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) imageOutputStream);
            throw th;
        }
    }

    public static void convert(InputStream inputStream, String str, OutputStream outputStream) throws IORuntimeException {
        write((Image) read(inputStream), str, getImageOutputStream(outputStream));
    }

    public static BufferedImage read(URL url) {
        try {
            BufferedImage bufferedImage = ImageIO.read(url);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            throw new IllegalArgumentException("Image type of [" + url + "] is not supported!");
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedImage toBufferedImage(Image image, int i2, Color color) {
        if (image instanceof BufferedImage) {
            BufferedImage bufferedImage = (BufferedImage) image;
            return i2 != bufferedImage.getType() ? copyImage(image, i2, color) : bufferedImage;
        }
        return copyImage(image, i2, color);
    }

    public static void convert(Image image, String str, ImageOutputStream imageOutputStream, boolean z2) {
        try {
            ImageIO.write(z2 ? copyImage(image, 1) : toBufferedImage(image), str, imageOutputStream);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void scale(InputStream inputStream, OutputStream outputStream, int i2, int i3, Color color) throws IORuntimeException {
        scale((Image) read(inputStream), getImageOutputStream(outputStream), i2, i3, color);
    }

    public static void scale(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, int i2, int i3, Color color) throws IORuntimeException {
        scale((Image) read(imageInputStream), imageOutputStream, i2, i3, color);
    }

    public static boolean write(Image image, ImageWriter imageWriter, ImageOutputStream imageOutputStream, float f2) {
        ImageWriteParam defaultWriteParam;
        if (imageWriter == null) {
            return false;
        }
        imageWriter.setOutput(imageOutputStream);
        RenderedImage renderedImage = toRenderedImage(image);
        if (f2 <= 0.0f || f2 >= 1.0f) {
            defaultWriteParam = null;
        } else {
            defaultWriteParam = imageWriter.getDefaultWriteParam();
            if (defaultWriteParam.canWriteCompressed()) {
                defaultWriteParam.setCompressionMode(2);
                defaultWriteParam.setCompressionQuality(f2);
                ColorModel colorModel = renderedImage.getColorModel();
                defaultWriteParam.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
            }
        }
        try {
            try {
                if (defaultWriteParam != null) {
                    imageWriter.write((IIOMetadata) null, new IIOImage(renderedImage, (List) null, (IIOMetadata) null), defaultWriteParam);
                } else {
                    imageWriter.write(renderedImage);
                }
                imageOutputStream.flush();
                imageWriter.dispose();
                return true;
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } catch (Throwable th) {
            imageWriter.dispose();
            throw th;
        }
    }

    public static void scale(Image image, ImageOutputStream imageOutputStream, int i2, int i3, Color color) throws IORuntimeException {
        writeJpg(scale(image, i2, i3, color), imageOutputStream);
    }

    public static Image scale(Image image, int i2, int i3, Color color) {
        return Img.from(image).scale(i2, i3, color).getImg();
    }
}
