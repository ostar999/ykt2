package cn.hutool.core.img;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class ColorUtil {
    private static final int RGB_COLOR_BOUND = 256;

    public static Color add(Color color, Color color2) {
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();
        double alpha = color.getAlpha();
        double red2 = color2.getRed();
        double green2 = color2.getGreen();
        double blue2 = color2.getBlue();
        double alpha2 = color2.getAlpha();
        double d3 = ((red * alpha) / 255.0d) + ((red2 * alpha2) / 255.0d);
        double d4 = (alpha / 255.0d) + (alpha2 / 255.0d);
        return new Color((int) (d3 / d4), (int) ((((green * alpha) / 255.0d) + ((green2 * alpha2) / 255.0d)) / d4), (int) ((((blue * alpha) / 255.0d) + ((blue2 * alpha2) / 255.0d)) / d4));
    }

    public static Color getColor(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return null;
        }
        String upperCase = str.toUpperCase();
        if ("BLACK".equals(upperCase)) {
            return Color.BLACK;
        }
        if ("WHITE".equals(upperCase)) {
            return Color.WHITE;
        }
        if ("LIGHTGRAY".equals(upperCase) || "LIGHT_GRAY".equals(upperCase)) {
            return Color.LIGHT_GRAY;
        }
        if ("GRAY".equals(upperCase)) {
            return Color.GRAY;
        }
        if ("DARKGRAY".equals(upperCase) || "DARK_GRAY".equals(upperCase)) {
            return Color.DARK_GRAY;
        }
        if ("RED".equals(upperCase)) {
            return Color.RED;
        }
        if ("PINK".equals(upperCase)) {
            return Color.PINK;
        }
        if ("ORANGE".equals(upperCase)) {
            return Color.ORANGE;
        }
        if ("YELLOW".equals(upperCase)) {
            return Color.YELLOW;
        }
        if ("GREEN".equals(upperCase)) {
            return Color.GREEN;
        }
        if ("MAGENTA".equals(upperCase)) {
            return Color.MAGENTA;
        }
        if ("CYAN".equals(upperCase)) {
            return Color.CYAN;
        }
        if ("BLUE".equals(upperCase)) {
            return Color.BLUE;
        }
        if ("DARKGOLD".equals(upperCase)) {
            return hexToColor("#9e7e67");
        }
        if ("LIGHTGOLD".equals(upperCase)) {
            return hexToColor("#ac9c85");
        }
        if (CharSequenceUtil.startWith((CharSequence) upperCase, '#')) {
            return hexToColor(upperCase);
        }
        if (CharSequenceUtil.startWith(upperCase, Typography.dollar)) {
            return hexToColor(DictionaryFactory.SHARP + upperCase.substring(1));
        }
        List<String> listSplit = CharSequenceUtil.split((CharSequence) upperCase, ',');
        if (3 == listSplit.size()) {
            Integer num = Convert.toInt(listSplit.get(0));
            Integer num2 = Convert.toInt(listSplit.get(1));
            Integer num3 = Convert.toInt(listSplit.get(2));
            if (!ArrayUtil.hasNull(num, num2, num3)) {
                return new Color(num.intValue(), num2.intValue(), num3.intValue());
            }
        }
        return null;
    }

    public static String getMainColor(BufferedImage bufferedImage, int[]... iArr) {
        HashMap map = new HashMap();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minY = bufferedImage.getMinY();
        for (int minX = bufferedImage.getMinX(); minX < width; minX++) {
            for (int i2 = minY; i2 < height; i2++) {
                int rgb = bufferedImage.getRGB(minX, i2);
                int i3 = (16711680 & rgb) >> 16;
                int i4 = (65280 & rgb) >> 8;
                int i5 = rgb & 255;
                if (!matchFilters(i3, i4, i5, iArr)) {
                    map.merge(i3 + "-" + i4 + "-" + i5, 1L, new BiFunction() { // from class: y.b
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return Long.valueOf(((Long) obj).longValue() + ((Long) obj2).longValue());
                        }
                    });
                }
            }
        }
        String str = null;
        long jLongValue = 0;
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            Long l2 = (Long) entry.getValue();
            if (l2.longValue() > jLongValue) {
                jLongValue = l2.longValue();
                str = str2;
            }
        }
        String[] strArrSplitToArray = CharSequenceUtil.splitToArray(str, CharPool.DASHED);
        String hexString = Integer.toHexString(Integer.parseInt(strArrSplitToArray[0]));
        String hexString2 = Integer.toHexString(Integer.parseInt(strArrSplitToArray[1]));
        String hexString3 = Integer.toHexString(Integer.parseInt(strArrSplitToArray[2]));
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        if (hexString2.length() == 1) {
            hexString2 = "0" + hexString2;
        }
        if (hexString3.length() == 1) {
            hexString3 = "0" + hexString3;
        }
        return DictionaryFactory.SHARP + hexString + hexString2 + hexString3;
    }

    public static Color hexToColor(String str) {
        return getColor(Integer.parseInt(CharSequenceUtil.removePrefix(str, DictionaryFactory.SHARP), 16));
    }

    private static boolean matchFilters(int i2, int i3, int i4, int[]... iArr) {
        if (iArr != null && iArr.length > 0) {
            for (int[] iArr2 : iArr) {
                if (i2 == iArr2[0] && i3 == iArr2[1] && i4 == iArr2[2]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Color randomColor() {
        return randomColor(null);
    }

    public static String toHex(Color color) {
        return toHex(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color randomColor(Random random) {
        if (random == null) {
            random = RandomUtil.getRandom();
        }
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static String toHex(int i2, int i3, int i4) {
        if (i2 < 0 || i2 > 255 || i3 < 0 || i3 > 255 || i4 < 0 || i4 > 255) {
            throw new IllegalArgumentException("RGB must be 0~255!");
        }
        return String.format("#%02X%02X%02X", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public static Color getColor(int i2) {
        return new Color(i2);
    }
}
