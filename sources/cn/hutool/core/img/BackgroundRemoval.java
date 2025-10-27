package cn.hutool.core.img;

import androidx.core.view.MotionEventCompat;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/* loaded from: classes.dex */
public class BackgroundRemoval {
    public static String[] IMAGES_TYPE = {"jpg", "png"};

    public static boolean areColorsWithinTolerance(Color color, Color color2, int i2) {
        return areColorsWithinTolerance(color, color2, new Color(i2, i2, i2));
    }

    public static boolean backgroundRemoval(String str, String str2, int i2) {
        return backgroundRemoval(new File(str), new File(str2), i2);
    }

    private static boolean fileTypeValidation(File file, String[] strArr) throws IORuntimeException {
        if (!file.exists()) {
            throw new IllegalArgumentException("给定文件为空");
        }
        String type = FileTypeUtil.getType(file);
        if (ArrayUtil.contains(strArr, type)) {
            return false;
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("文件类型{}不支持", type));
    }

    public static String getMainColor(String str) {
        return getMainColor(new File(str));
    }

    private static String[] getRemoveRgb(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth() - 1;
        int height = bufferedImage.getHeight() - 1;
        int rgb = bufferedImage.getRGB(1, 1);
        String hex = ImgUtil.toHex((rgb & 16711680) >> 16, (rgb & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb & 255);
        int i2 = width / 2;
        int rgb2 = bufferedImage.getRGB(i2, 1);
        String hex2 = ImgUtil.toHex((rgb2 & 16711680) >> 16, (rgb2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb2 & 255);
        int rgb3 = bufferedImage.getRGB(width, 1);
        String hex3 = ImgUtil.toHex((rgb3 & 16711680) >> 16, (rgb3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb3 & 255);
        int i3 = height / 2;
        int rgb4 = bufferedImage.getRGB(width, i3);
        String hex4 = ImgUtil.toHex((rgb4 & 16711680) >> 16, (rgb4 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb4 & 255);
        int rgb5 = bufferedImage.getRGB(width, height);
        String hex5 = ImgUtil.toHex((rgb5 & 16711680) >> 16, (rgb5 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb5 & 255);
        int rgb6 = bufferedImage.getRGB(i2, height);
        String hex6 = ImgUtil.toHex((rgb6 & 16711680) >> 16, (rgb6 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb6 & 255);
        int rgb7 = bufferedImage.getRGB(1, height);
        String hex7 = ImgUtil.toHex((rgb7 & 16711680) >> 16, (rgb7 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, rgb7 & 255);
        int rgb8 = bufferedImage.getRGB(1, i3);
        return new String[]{hex, hex2, hex3, hex4, hex5, hex6, hex7, ImgUtil.toHex((16711680 & rgb8) >> 16, (65280 & rgb8) >> 8, rgb8 & 255)};
    }

    public static Color hexToRgb(String str) {
        return new Color(Integer.parseInt(str.substring(1), 16));
    }

    public static boolean areColorsWithinTolerance(Color color, Color color2, Color color3) {
        return color.getRed() - color2.getRed() < color3.getRed() && color.getRed() - color2.getRed() > (-color3.getRed()) && color.getBlue() - color2.getBlue() < color3.getBlue() && color.getBlue() - color2.getBlue() > (-color3.getBlue()) && color.getGreen() - color2.getGreen() < color3.getGreen() && color.getGreen() - color2.getGreen() > (-color3.getGreen());
    }

    public static boolean backgroundRemoval(File file, File file2, int i2) {
        return backgroundRemoval(file, file2, null, i2);
    }

    public static String getMainColor(File file) {
        try {
            return getMainColor(ImageIO.read(file));
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean backgroundRemoval(File file, File file2, Color color, int i2) {
        if (fileTypeValidation(file, IMAGES_TYPE)) {
            return false;
        }
        try {
            return ImageIO.write(backgroundRemoval(ImageIO.read(file), color, i2), "png", file2);
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String getMainColor(BufferedImage bufferedImage) {
        int iValueOf;
        if (bufferedImage != null) {
            ArrayList<String> arrayList = new ArrayList();
            for (int minY = bufferedImage.getMinY(); minY < bufferedImage.getHeight(); minY++) {
                for (int minX = bufferedImage.getMinX(); minX < bufferedImage.getWidth(); minX++) {
                    int rgb = bufferedImage.getRGB(minX, minY);
                    arrayList.add(((16711680 & rgb) >> 16) + "-" + ((65280 & rgb) >> 8) + "-" + (rgb & 255));
                }
            }
            HashMap map = new HashMap(arrayList.size(), 1.0f);
            for (String str : arrayList) {
                Integer num = (Integer) map.get(str);
                if (num == null) {
                    iValueOf = 1;
                } else {
                    iValueOf = Integer.valueOf(num.intValue() + 1);
                }
                map.put(str, iValueOf);
            }
            long jIntValue = 0;
            String str2 = "";
            for (Map.Entry entry : map.entrySet()) {
                String str3 = (String) entry.getKey();
                Integer num2 = (Integer) entry.getValue();
                if (CharSequenceUtil.isBlank(str2) || num2.intValue() > jIntValue) {
                    jIntValue = num2.intValue();
                    str2 = str3;
                }
            }
            String[] strArrSplit = str2.split("-");
            return strArrSplit.length == 3 ? ImgUtil.toHex(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2])) : "";
        }
        throw new IllegalArgumentException("图片流是空的");
    }

    public static BufferedImage backgroundRemoval(BufferedImage bufferedImage, Color color, int i2) {
        int iMin = Math.min(255, Math.max(i2, 0));
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        BufferedImage bufferedImage2 = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), 6);
        Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
        String[] removeRgb = getRemoveRgb(bufferedImage);
        String mainColor = getMainColor(bufferedImage);
        for (int minY = bufferedImage2.getMinY(); minY < bufferedImage2.getHeight(); minY++) {
            for (int minX = bufferedImage2.getMinX(); minX < bufferedImage2.getWidth(); minX++) {
                int rgb = bufferedImage2.getRGB(minX, minY);
                String hex = ImgUtil.toHex((16711680 & rgb) >> 16, (65280 & rgb) >> 8, rgb & 255);
                boolean z2 = true;
                if (!ArrayUtil.contains(removeRgb, hex) && !areColorsWithinTolerance(hexToRgb(mainColor), new Color(Integer.parseInt(hex.substring(1), 16)), iMin)) {
                    z2 = false;
                }
                if (z2) {
                    rgb = color == null ? (rgb & 16777215) | 16777216 : color.getRGB();
                }
                bufferedImage2.setRGB(minX, minY, rgb);
            }
        }
        graphics.drawImage(bufferedImage2, 0, 0, imageIcon.getImageObserver());
        return bufferedImage2;
    }

    public static BufferedImage backgroundRemoval(ByteArrayOutputStream byteArrayOutputStream, Color color, int i2) {
        try {
            return backgroundRemoval(ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), color, i2);
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
