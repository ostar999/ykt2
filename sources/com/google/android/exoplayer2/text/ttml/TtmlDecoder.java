package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes3.dex */
public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_IMAGE = "backgroundImage";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    static final Pattern SIGNED_PERCENTAGE = Pattern.compile("^([-+]?\\d+\\.?\\d*?)%$");
    static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern PIXEL_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    private static final Pattern CELL_RESOLUTION = Pattern.compile("^(\\d+) (\\d+)$");
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final CellResolution DEFAULT_CELL_RESOLUTION = new CellResolution(32, 15);

    public static final class CellResolution {
        final int columns;
        final int rows;

        public CellResolution(int i2, int i3) {
            this.columns = i2;
            this.rows = i3;
        }
    }

    public static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        public FrameAndTickRate(float f2, int i2, int i3) {
            this.effectiveFrameRate = f2;
            this.subFrameRate = i2;
            this.tickRate = i3;
        }
    }

    public static final class TtsExtent {
        final int height;
        final int width;

        public TtsExtent(int i2, int i3) {
            this.width = i2;
            this.height = i3;
        }
    }

    public TtmlDecoder() throws XmlPullParserException {
        super(TAG);
        try {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            this.xmlParserFactory = xmlPullParserFactoryNewInstance;
            xmlPullParserFactoryNewInstance.setNamespaceAware(true);
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    private static TtmlStyle createIfNull(@Nullable TtmlStyle ttmlStyle) {
        return ttmlStyle == null ? new TtmlStyle() : ttmlStyle;
    }

    private static boolean isSupportedTag(String str) {
        return str.equals("tt") || str.equals(TtmlNode.TAG_HEAD) || str.equals(TtmlNode.TAG_BODY) || str.equals(TtmlNode.TAG_DIV) || str.equals("p") || str.equals(TtmlNode.TAG_SPAN) || str.equals("br") || str.equals("style") || str.equals(TtmlNode.TAG_STYLING) || str.equals(TtmlNode.TAG_LAYOUT) || str.equals("region") || str.equals(TtmlNode.TAG_METADATA) || str.equals("image") || str.equals("data") || str.equals(TtmlNode.TAG_INFORMATION);
    }

    @Nullable
    private static Layout.Alignment parseAlignment(String str) {
        String lowerCase = Ascii.toLowerCase(str);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "center":
                return Layout.Alignment.ALIGN_CENTER;
            case "end":
            case "right":
                return Layout.Alignment.ALIGN_OPPOSITE;
            case "left":
            case "start":
                return Layout.Alignment.ALIGN_NORMAL;
            default:
                return null;
        }
    }

    private static CellResolution parseCellResolution(XmlPullParser xmlPullParser, CellResolution cellResolution) throws SubtitleDecoderException, NumberFormatException {
        String attributeValue = xmlPullParser.getAttributeValue(TTP, "cellResolution");
        if (attributeValue == null) {
            return cellResolution;
        }
        Matcher matcher = CELL_RESOLUTION.matcher(attributeValue);
        if (!matcher.matches()) {
            Log.w(TAG, attributeValue.length() != 0 ? "Ignoring malformed cell resolution: ".concat(attributeValue) : new String("Ignoring malformed cell resolution: "));
            return cellResolution;
        }
        try {
            int i2 = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
            int i3 = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)));
            if (i2 != 0 && i3 != 0) {
                return new CellResolution(i2, i3);
            }
            StringBuilder sb = new StringBuilder(47);
            sb.append("Invalid cell resolution ");
            sb.append(i2);
            sb.append(" ");
            sb.append(i3);
            throw new SubtitleDecoderException(sb.toString());
        } catch (NumberFormatException unused) {
            Log.w(TAG, attributeValue.length() != 0 ? "Ignoring malformed cell resolution: ".concat(attributeValue) : new String("Ignoring malformed cell resolution: "));
            return cellResolution;
        }
    }

    private static void parseFontSize(String str, TtmlStyle ttmlStyle) throws SubtitleDecoderException {
        Matcher matcher;
        String str2;
        String[] strArrSplit = Util.split(str, "\\s+");
        if (strArrSplit.length == 1) {
            matcher = FONT_SIZE.matcher(str);
        } else {
            if (strArrSplit.length != 2) {
                int length = strArrSplit.length;
                StringBuilder sb = new StringBuilder(52);
                sb.append("Invalid number of entries for fontSize: ");
                sb.append(length);
                sb.append(StrPool.DOT);
                throw new SubtitleDecoderException(sb.toString());
            }
            matcher = FONT_SIZE.matcher(strArrSplit[1]);
            Log.w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        }
        if (!matcher.matches()) {
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 36);
            sb2.append("Invalid expression for fontSize: '");
            sb2.append(str);
            sb2.append("'.");
            throw new SubtitleDecoderException(sb2.toString());
        }
        str2 = (String) Assertions.checkNotNull(matcher.group(3));
        str2.hashCode();
        switch (str2) {
            case "%":
                ttmlStyle.setFontSizeUnit(3);
                break;
            case "em":
                ttmlStyle.setFontSizeUnit(2);
                break;
            case "px":
                ttmlStyle.setFontSizeUnit(1);
                break;
            default:
                StringBuilder sb3 = new StringBuilder(str2.length() + 30);
                sb3.append("Invalid unit for fontSize: '");
                sb3.append(str2);
                sb3.append("'.");
                throw new SubtitleDecoderException(sb3.toString());
        }
        ttmlStyle.setFontSize(Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1))));
    }

    private static FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlPullParser) throws SubtitleDecoderException, NumberFormatException {
        float f2;
        String attributeValue = xmlPullParser.getAttributeValue(TTP, "frameRate");
        int i2 = attributeValue != null ? Integer.parseInt(attributeValue) : 30;
        String attributeValue2 = xmlPullParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (attributeValue2 != null) {
            if (Util.split(attributeValue2, " ").length != 2) {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
            f2 = Integer.parseInt(r2[0]) / Integer.parseInt(r2[1]);
        } else {
            f2 = 1.0f;
        }
        FrameAndTickRate frameAndTickRate = DEFAULT_FRAME_AND_TICK_RATE;
        int i3 = frameAndTickRate.subFrameRate;
        String attributeValue3 = xmlPullParser.getAttributeValue(TTP, "subFrameRate");
        if (attributeValue3 != null) {
            i3 = Integer.parseInt(attributeValue3);
        }
        int i4 = frameAndTickRate.tickRate;
        String attributeValue4 = xmlPullParser.getAttributeValue(TTP, "tickRate");
        if (attributeValue4 != null) {
            i4 = Integer.parseInt(attributeValue4);
        }
        return new FrameAndTickRate(i2 * f2, i3, i4);
    }

    private static Map<String, TtmlStyle> parseHeader(XmlPullParser xmlPullParser, Map<String, TtmlStyle> map, CellResolution cellResolution, @Nullable TtsExtent ttsExtent, Map<String, TtmlRegion> map2, Map<String, String> map3) throws XmlPullParserException, IOException, NumberFormatException {
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "style")) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "style");
                TtmlStyle styleAttributes = parseStyleAttributes(xmlPullParser, new TtmlStyle());
                if (attributeValue != null) {
                    for (String str : parseStyleIds(attributeValue)) {
                        styleAttributes.chain(map.get(str));
                    }
                }
                String id = styleAttributes.getId();
                if (id != null) {
                    map.put(id, styleAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "region")) {
                TtmlRegion regionAttributes = parseRegionAttributes(xmlPullParser, cellResolution, ttsExtent);
                if (regionAttributes != null) {
                    map2.put(regionAttributes.id, regionAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, TtmlNode.TAG_METADATA)) {
                parseMetadata(xmlPullParser, map3);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, TtmlNode.TAG_HEAD));
        return map;
    }

    private static void parseMetadata(XmlPullParser xmlPullParser, Map<String, String> map) throws XmlPullParserException, IOException {
        String attributeValue;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "image") && (attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "id")) != null) {
                map.put(attributeValue, xmlPullParser.nextText());
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, TtmlNode.TAG_METADATA));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlNode parseNode(org.xmlpull.v1.XmlPullParser r20, @androidx.annotation.Nullable com.google.android.exoplayer2.text.ttml.TtmlNode r21, java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlRegion> r22, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r23) throws com.google.android.exoplayer2.text.SubtitleDecoderException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseNode(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlNode, java.util.Map, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):com.google.android.exoplayer2.text.ttml.TtmlNode");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01b8  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlRegion parseRegionAttributes(org.xmlpull.v1.XmlPullParser r17, com.google.android.exoplayer2.text.ttml.TtmlDecoder.CellResolution r18, @androidx.annotation.Nullable com.google.android.exoplayer2.text.ttml.TtmlDecoder.TtsExtent r19) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 576
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseRegionAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlDecoder$CellResolution, com.google.android.exoplayer2.text.ttml.TtmlDecoder$TtsExtent):com.google.android.exoplayer2.text.ttml.TtmlRegion");
    }

    private static float parseShear(String str) {
        Matcher matcher = SIGNED_PERCENTAGE.matcher(str);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(str);
            Log.w(TAG, strValueOf.length() != 0 ? "Invalid value for shear: ".concat(strValueOf) : new String("Invalid value for shear: "));
            return Float.MAX_VALUE;
        }
        try {
            return Math.min(100.0f, Math.max(-100.0f, Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1)))));
        } catch (NumberFormatException e2) {
            String strValueOf2 = String.valueOf(str);
            Log.w(TAG, strValueOf2.length() != 0 ? "Failed to parse shear: ".concat(strValueOf2) : new String("Failed to parse shear: "), e2);
            return Float.MAX_VALUE;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlStyle parseStyleAttributes(org.xmlpull.v1.XmlPullParser r12, com.google.android.exoplayer2.text.ttml.TtmlStyle r13) {
        /*
            Method dump skipped, instructions count: 942
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseStyleAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlStyle):com.google.android.exoplayer2.text.ttml.TtmlStyle");
    }

    private static String[] parseStyleIds(String str) {
        String strTrim = str.trim();
        return strTrim.isEmpty() ? new String[0] : Util.split(strTrim, "\\s+");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static long parseTimeExpression(java.lang.String r13, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r14) throws com.google.android.exoplayer2.text.SubtitleDecoderException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseTimeExpression(java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):long");
    }

    @Nullable
    private static TtsExtent parseTtsExtent(XmlPullParser xmlPullParser) {
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_TTS_EXTENT);
        if (attributeValue == null) {
            return null;
        }
        Matcher matcher = PIXEL_COORDINATES.matcher(attributeValue);
        if (!matcher.matches()) {
            Log.w(TAG, attributeValue.length() != 0 ? "Ignoring non-pixel tts extent: ".concat(attributeValue) : new String("Ignoring non-pixel tts extent: "));
            return null;
        }
        try {
            return new TtsExtent(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2))));
        } catch (NumberFormatException unused) {
            Log.w(TAG, attributeValue.length() != 0 ? "Ignoring malformed tts extent: ".concat(attributeValue) : new String("Ignoring malformed tts extent: "));
            return null;
        }
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    public Subtitle decode(byte[] bArr, int i2, boolean z2) throws XmlPullParserException, SubtitleDecoderException, NumberFormatException, IOException {
        FrameAndTickRate frameAndTickRate;
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            HashMap map3 = new HashMap();
            map2.put("", new TtmlRegion(""));
            TtsExtent ttsExtent = null;
            xmlPullParserNewPullParser.setInput(new ByteArrayInputStream(bArr, 0, i2), null);
            ArrayDeque arrayDeque = new ArrayDeque();
            FrameAndTickRate frameAndTickRates = DEFAULT_FRAME_AND_TICK_RATE;
            CellResolution cellResolution = DEFAULT_CELL_RESOLUTION;
            int i3 = 0;
            TtmlSubtitle ttmlSubtitle = null;
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.getEventType()) {
                TtmlNode ttmlNode = (TtmlNode) arrayDeque.peek();
                if (i3 == 0) {
                    String name = xmlPullParserNewPullParser.getName();
                    if (eventType == 2) {
                        if ("tt".equals(name)) {
                            frameAndTickRates = parseFrameAndTickRates(xmlPullParserNewPullParser);
                            cellResolution = parseCellResolution(xmlPullParserNewPullParser, DEFAULT_CELL_RESOLUTION);
                            ttsExtent = parseTtsExtent(xmlPullParserNewPullParser);
                        }
                        TtsExtent ttsExtent2 = ttsExtent;
                        FrameAndTickRate frameAndTickRate2 = frameAndTickRates;
                        CellResolution cellResolution2 = cellResolution;
                        if (isSupportedTag(name)) {
                            if (TtmlNode.TAG_HEAD.equals(name)) {
                                frameAndTickRate = frameAndTickRate2;
                                parseHeader(xmlPullParserNewPullParser, map, cellResolution2, ttsExtent2, map2, map3);
                            } else {
                                frameAndTickRate = frameAndTickRate2;
                                try {
                                    TtmlNode node = parseNode(xmlPullParserNewPullParser, ttmlNode, map2, frameAndTickRate);
                                    arrayDeque.push(node);
                                    if (ttmlNode != null) {
                                        ttmlNode.addChild(node);
                                    }
                                } catch (SubtitleDecoderException e2) {
                                    Log.w(TAG, "Suppressing parser error", e2);
                                    i3++;
                                }
                            }
                            frameAndTickRates = frameAndTickRate;
                        } else {
                            String strValueOf = String.valueOf(xmlPullParserNewPullParser.getName());
                            Log.i(TAG, strValueOf.length() != 0 ? "Ignoring unsupported tag: ".concat(strValueOf) : new String("Ignoring unsupported tag: "));
                            i3++;
                            frameAndTickRates = frameAndTickRate2;
                        }
                        ttsExtent = ttsExtent2;
                        cellResolution = cellResolution2;
                    } else if (eventType == 4) {
                        ((TtmlNode) Assertions.checkNotNull(ttmlNode)).addChild(TtmlNode.buildTextNode(xmlPullParserNewPullParser.getText()));
                    } else if (eventType == 3) {
                        if (xmlPullParserNewPullParser.getName().equals("tt")) {
                            ttmlSubtitle = new TtmlSubtitle((TtmlNode) Assertions.checkNotNull((TtmlNode) arrayDeque.peek()), map, map2, map3);
                        }
                        arrayDeque.pop();
                    }
                } else if (eventType == 2) {
                    i3++;
                } else if (eventType == 3) {
                    i3--;
                }
                xmlPullParserNewPullParser.next();
            }
            if (ttmlSubtitle != null) {
                return ttmlSubtitle;
            }
            throw new SubtitleDecoderException("No TTML subtitles found");
        } catch (IOException e3) {
            throw new IllegalStateException("Unexpected error when reading input.", e3);
        } catch (XmlPullParserException e4) {
            throw new SubtitleDecoderException("Unable to decode source", e4);
        }
    }
}
