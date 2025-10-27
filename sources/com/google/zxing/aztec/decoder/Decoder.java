package com.google.zxing.aztec.decoder;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.StrPool;
import com.alipay.sdk.util.h;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.rtmp.sharp.jni.QLog;
import com.umeng.analytics.pro.am;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public final class Decoder {
    private AztecDetectorResult ddata;
    private static final String[] UPPER_TABLE = {"CTRL_PS", " ", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", QLog.TAG_REPORTLEVEL_DEVELOPER, "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private static final String[] LOWER_TABLE = {"CTRL_PS", " ", am.av, "b", am.aF, "d", AliyunLogKey.KEY_EVENT, "f", "g", "h", am.aC, "j", "k", NotifyType.LIGHTS, "m", "n", "o", "p", "q", "r", "s", "t", am.aG, "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private static final String[] MIXED_TABLE = {"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", StrPool.TAB, "\n", "\u000b", "\f", StrPool.CR, "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", StrPool.BACKSLASH, "^", StrPool.UNDERLINE, "`", HiAnalyticsConstant.REPORT_VAL_SEPARATOR, Constants.WAVE_SEPARATOR, "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
    private static final String[] PUNCT_TABLE = {"", StrPool.CR, "\r\n", ". ", ", ", ": ", "!", "\"", DictionaryFactory.SHARP, "$", "%", "&", "'", "(", ")", "*", Marker.ANY_NON_NULL_MARKER, ",", "-", StrPool.DOT, "/", ":", h.f3376b, "<", "=", ">", "?", StrPool.BRACKET_START, StrPool.BRACKET_END, StrPool.DELIM_START, "}", "CTRL_UL"};
    private static final String[] DIGIT_TABLE = {"CTRL_PS", " ", "0", "1", "2", "3", "4", "5", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO, "7", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM, ",", StrPool.DOT, "CTRL_UL", "CTRL_US"};

    /* renamed from: com.google.zxing.aztec.decoder.Decoder$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table;

        static {
            int[] iArr = new int[Table.values().length];
            $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table = iArr;
            try {
                iArr[Table.UPPER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Table.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Table.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Table.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Table.DIGIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Table {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY
    }

    private boolean[] correctBits(boolean[] zArr) throws FormatException {
        int i2;
        GenericGF genericGF;
        if (this.ddata.getNbLayers() <= 2) {
            genericGF = GenericGF.AZTEC_DATA_6;
            i2 = 6;
        } else {
            i2 = 8;
            if (this.ddata.getNbLayers() <= 8) {
                genericGF = GenericGF.AZTEC_DATA_8;
            } else if (this.ddata.getNbLayers() <= 22) {
                genericGF = GenericGF.AZTEC_DATA_10;
                i2 = 10;
            } else {
                genericGF = GenericGF.AZTEC_DATA_12;
                i2 = 12;
            }
        }
        int nbDatablocks = this.ddata.getNbDatablocks();
        int length = zArr.length / i2;
        int length2 = zArr.length % i2;
        int i3 = length - nbDatablocks;
        int[] iArr = new int[length];
        int i4 = 0;
        while (i4 < length) {
            iArr[i4] = readCode(zArr, length2, i2);
            i4++;
            length2 += i2;
        }
        try {
            new ReedSolomonDecoder(genericGF).decode(iArr, i3);
            int i5 = (1 << i2) - 1;
            int i6 = 0;
            for (int i7 = 0; i7 < nbDatablocks; i7++) {
                int i8 = iArr[i7];
                if (i8 == 0 || i8 == i5) {
                    throw FormatException.getFormatInstance();
                }
                if (i8 == 1 || i8 == i5 - 1) {
                    i6++;
                }
            }
            boolean[] zArr2 = new boolean[(nbDatablocks * i2) - i6];
            int i9 = 0;
            for (int i10 = 0; i10 < nbDatablocks; i10++) {
                int i11 = iArr[i10];
                if (i11 == 1 || i11 == i5 - 1) {
                    Arrays.fill(zArr2, i9, (i9 + i2) - 1, i11 > 1);
                    i9 += i2 - 1;
                } else {
                    int i12 = i2 - 1;
                    while (i12 >= 0) {
                        int i13 = i9 + 1;
                        zArr2[i9] = ((1 << i12) & i11) != 0;
                        i12--;
                        i9 = i13;
                    }
                }
            }
            return zArr2;
        } catch (ReedSolomonException unused) {
            throw FormatException.getFormatInstance();
        }
    }

    private static String getCharacter(Table table, int i2) {
        int i3 = AnonymousClass1.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[table.ordinal()];
        if (i3 == 1) {
            return UPPER_TABLE[i2];
        }
        if (i3 == 2) {
            return LOWER_TABLE[i2];
        }
        if (i3 == 3) {
            return MIXED_TABLE[i2];
        }
        if (i3 == 4) {
            return PUNCT_TABLE[i2];
        }
        if (i3 == 5) {
            return DIGIT_TABLE[i2];
        }
        throw new IllegalStateException("Bad table");
    }

    private static String getEncodedData(boolean[] zArr) {
        int length = zArr.length;
        Table table = Table.UPPER;
        StringBuilder sb = new StringBuilder(20);
        Table table2 = table;
        int i2 = 0;
        while (i2 < length) {
            if (table != Table.BINARY) {
                int i3 = table == Table.DIGIT ? 4 : 5;
                if (length - i2 < i3) {
                    break;
                }
                int code = readCode(zArr, i2, i3);
                i2 += i3;
                String character = getCharacter(table, code);
                if (character.startsWith("CTRL_")) {
                    Table table3 = getTable(character.charAt(5));
                    if (character.charAt(6) == 'L') {
                        table = table3;
                        table2 = table;
                    } else {
                        table = table3;
                    }
                } else {
                    sb.append(character);
                    table = table2;
                }
            } else {
                if (length - i2 < 5) {
                    break;
                }
                int code2 = readCode(zArr, i2, 5);
                i2 += 5;
                if (code2 == 0) {
                    if (length - i2 < 11) {
                        break;
                    }
                    code2 = readCode(zArr, i2, 11) + 31;
                    i2 += 11;
                }
                int i4 = 0;
                while (true) {
                    if (i4 >= code2) {
                        break;
                    }
                    if (length - i2 < 8) {
                        i2 = length;
                        break;
                    }
                    sb.append((char) readCode(zArr, i2, 8));
                    i2 += 8;
                    i4++;
                }
                table = table2;
            }
        }
        return sb.toString();
    }

    private static Table getTable(char c3) {
        return c3 != 'B' ? c3 != 'D' ? c3 != 'P' ? c3 != 'L' ? c3 != 'M' ? Table.UPPER : Table.MIXED : Table.LOWER : Table.PUNCT : Table.DIGIT : Table.BINARY;
    }

    public static String highLevelDecode(boolean[] zArr) {
        return getEncodedData(zArr);
    }

    private static int readCode(boolean[] zArr, int i2, int i3) {
        int i4 = 0;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 <<= 1;
            if (zArr[i5]) {
                i4++;
            }
        }
        return i4;
    }

    private static int totalBitsInLayer(int i2, boolean z2) {
        return ((z2 ? 88 : 112) + (i2 * 16)) * i2;
    }

    public DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        this.ddata = aztecDetectorResult;
        return new DecoderResult(null, getEncodedData(correctBits(extractBits(aztecDetectorResult.getBits()))), null, null);
    }

    public boolean[] extractBits(BitMatrix bitMatrix) {
        boolean zIsCompact = this.ddata.isCompact();
        int nbLayers = this.ddata.getNbLayers();
        int i2 = nbLayers * 4;
        int i3 = zIsCompact ? i2 + 11 : i2 + 14;
        int[] iArr = new int[i3];
        boolean[] zArr = new boolean[totalBitsInLayer(nbLayers, zIsCompact)];
        int i4 = 2;
        if (zIsCompact) {
            for (int i5 = 0; i5 < i3; i5++) {
                iArr[i5] = i5;
            }
        } else {
            int i6 = i3 / 2;
            int i7 = ((i3 + 1) + (((i6 - 1) / 15) * 2)) / 2;
            for (int i8 = 0; i8 < i6; i8++) {
                iArr[(i6 - i8) - 1] = (i7 - r12) - 1;
                iArr[i6 + i8] = (i8 / 15) + i8 + i7 + 1;
            }
        }
        int i9 = 0;
        int i10 = 0;
        while (i9 < nbLayers) {
            int i11 = (nbLayers - i9) * 4;
            int i12 = zIsCompact ? i11 + 9 : i11 + 12;
            int i13 = i9 * 2;
            int i14 = (i3 - 1) - i13;
            int i15 = 0;
            while (i15 < i12) {
                int i16 = i15 * 2;
                int i17 = 0;
                while (i17 < i4) {
                    int i18 = i13 + i17;
                    int i19 = i13 + i15;
                    zArr[i10 + i16 + i17] = bitMatrix.get(iArr[i18], iArr[i19]);
                    int i20 = iArr[i19];
                    int i21 = i14 - i17;
                    zArr[(i12 * 2) + i10 + i16 + i17] = bitMatrix.get(i20, iArr[i21]);
                    int i22 = i14 - i15;
                    zArr[(i12 * 4) + i10 + i16 + i17] = bitMatrix.get(iArr[i21], iArr[i22]);
                    zArr[(i12 * 6) + i10 + i16 + i17] = bitMatrix.get(iArr[i22], iArr[i18]);
                    i17++;
                    nbLayers = nbLayers;
                    zIsCompact = zIsCompact;
                    i4 = 2;
                }
                i15++;
                i4 = 2;
            }
            i10 += i12 * 8;
            i9++;
            i4 = 2;
        }
        return zArr;
    }
}
