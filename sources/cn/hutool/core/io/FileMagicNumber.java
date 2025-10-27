package cn.hutool.core.io;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.common.base.Ascii;
import com.luck.picture.lib.config.PictureMimeType;
import com.psychiatrygarden.utils.MimeTypes;
import com.yikaobang.yixue.R2;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import okio.Utf8;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.eclipse.jetty.http.HttpTokens;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'UNKNOWN' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public abstract class FileMagicNumber {
    private static final /* synthetic */ FileMagicNumber[] $VALUES;
    public static final FileMagicNumber AAC;
    public static final FileMagicNumber AC3;
    public static final FileMagicNumber AIFF;
    public static final FileMagicNumber AMR;
    public static final FileMagicNumber APNG;
    public static final FileMagicNumber AR;
    public static final FileMagicNumber AVI;
    public static final FileMagicNumber BMP;
    public static final FileMagicNumber BR;
    public static final FileMagicNumber BZ2;
    public static final FileMagicNumber CAB;
    public static final FileMagicNumber CHM;
    public static final FileMagicNumber CLASS;
    public static final FileMagicNumber CRX;
    public static final FileMagicNumber DBX;
    public static final FileMagicNumber DCM;
    public static final FileMagicNumber DEB;
    public static final FileMagicNumber DEX;
    public static final FileMagicNumber DEY;
    public static final FileMagicNumber DOC;
    public static final FileMagicNumber DOCX;
    public static final FileMagicNumber DWG;
    public static final FileMagicNumber ELF;
    public static final FileMagicNumber EML;
    public static final FileMagicNumber EPUB;
    public static final FileMagicNumber EXE;
    public static final FileMagicNumber FLAC;
    public static final FileMagicNumber FLV;
    public static final FileMagicNumber GIF;
    public static final FileMagicNumber GZ;
    public static final FileMagicNumber ICO;
    public static final FileMagicNumber JPEG;
    public static final FileMagicNumber JXR;
    public static final FileMagicNumber LZ;
    public static final FileMagicNumber LZ4;
    public static final FileMagicNumber LZOP;
    public static final FileMagicNumber M3GP;
    public static final FileMagicNumber M4A;
    public static final FileMagicNumber M4V;
    public static final FileMagicNumber MDB;
    public static final FileMagicNumber MIDI;
    public static final FileMagicNumber MKV;
    public static final FileMagicNumber MOV;
    public static final FileMagicNumber MP3;
    public static final FileMagicNumber MP4;
    public static final FileMagicNumber MPEG;
    public static final FileMagicNumber NES;
    public static final FileMagicNumber OGG;
    public static final FileMagicNumber OTF;
    public static final FileMagicNumber PDF;
    public static final FileMagicNumber PNG;
    public static final FileMagicNumber PPT;
    public static final FileMagicNumber PPTX;
    public static final FileMagicNumber PS;
    public static final FileMagicNumber PSD;
    public static final FileMagicNumber PST;
    public static final FileMagicNumber RAM;
    public static final FileMagicNumber RAR;
    public static final FileMagicNumber RMVB;
    public static final FileMagicNumber RPM;
    public static final FileMagicNumber RTF;
    public static final FileMagicNumber SQLITE;
    public static final FileMagicNumber SWF;
    public static final FileMagicNumber SevenZ;
    public static final FileMagicNumber TAR;
    public static final FileMagicNumber TIFF;
    public static final FileMagicNumber TORRENT;
    public static final FileMagicNumber TTF;
    public static final FileMagicNumber UNKNOWN;
    public static final FileMagicNumber WASM;
    public static final FileMagicNumber WAV;
    public static final FileMagicNumber WEBM;
    public static final FileMagicNumber WEBP;
    public static final FileMagicNumber WMV;
    public static final FileMagicNumber WOFF;
    public static final FileMagicNumber WOFF2;
    public static final FileMagicNumber WPD;
    public static final FileMagicNumber XCF;
    public static final FileMagicNumber XLS;
    public static final FileMagicNumber XLSX;
    public static final FileMagicNumber XZ;
    public static final FileMagicNumber ZIP;
    public static final FileMagicNumber ZSTD;
    private final String extension;
    private final String mimeType;

    static {
        String str = null;
        FileMagicNumber fileMagicNumber = new FileMagicNumber("UNKNOWN", 0, str, str) { // from class: cn.hutool.core.io.FileMagicNumber.1
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return false;
            }
        };
        UNKNOWN = fileMagicNumber;
        FileMagicNumber fileMagicNumber2 = new FileMagicNumber("JPEG", 1, "image/jpeg", "jpg") { // from class: cn.hutool.core.io.FileMagicNumber.2
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -40) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -1);
            }
        };
        JPEG = fileMagicNumber2;
        FileMagicNumber fileMagicNumber3 = new FileMagicNumber("JXR", 2, "image/vnd.ms-photo", "jxr") { // from class: cn.hutool.core.io.FileMagicNumber.3
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(PSSSigner.TRAILER_IMPLICIT));
            }
        };
        JXR = fileMagicNumber3;
        FileMagicNumber fileMagicNumber4 = new FileMagicNumber("APNG", 3, "image/apng", "apng") { // from class: cn.hutool.core.io.FileMagicNumber.4
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                int i2 = 8;
                if (bArr.length > 8 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -119) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 78) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 71) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 13) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 10) && Objects.equals(Byte.valueOf(bArr[6]), Byte.valueOf(Ascii.SUB)) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 10)) {
                    while (i2 < bArr.length) {
                        try {
                            int i3 = i2 + 4;
                            int iIntValue = new BigInteger(1, Arrays.copyOfRange(bArr, i2, i3)).intValue();
                            int i4 = i3 + 4;
                            String str2 = new String(Arrays.copyOfRange(bArr, i3, i4));
                            if (str2.equals("IDAT") || str2.equals("IEND")) {
                                break;
                            }
                            if (str2.equals("acTL")) {
                                return true;
                            }
                            i2 = i4 + iIntValue + 4;
                        } catch (Exception unused) {
                        }
                    }
                }
                return false;
            }
        };
        APNG = fileMagicNumber4;
        FileMagicNumber fileMagicNumber5 = new FileMagicNumber("PNG", 4, "image/png", "png") { // from class: cn.hutool.core.io.FileMagicNumber.5
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -119) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 78) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 71);
            }
        };
        PNG = fileMagicNumber5;
        FileMagicNumber fileMagicNumber6 = new FileMagicNumber("GIF", 5, MimeTypes.IMAGE_GIF, ImgUtil.IMAGE_TYPE_GIF) { // from class: cn.hutool.core.io.FileMagicNumber.6
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 71) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 70);
            }
        };
        GIF = fileMagicNumber6;
        FileMagicNumber fileMagicNumber7 = new FileMagicNumber("BMP", 6, MimeTypes.IMAGE_BMP, ImgUtil.IMAGE_TYPE_BMP) { // from class: cn.hutool.core.io.FileMagicNumber.7
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 1 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 66) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 77);
            }
        };
        BMP = fileMagicNumber7;
        FileMagicNumber fileMagicNumber8 = new FileMagicNumber("TIFF", 7, "image/tiff", "tiff") { // from class: cn.hutool.core.io.FileMagicNumber.8
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 4) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 42) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 0)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 42));
            }
        };
        TIFF = fileMagicNumber8;
        FileMagicNumber fileMagicNumber9 = new FileMagicNumber("DWG", 8, "image/vnd.dwg", "dwg") { // from class: cn.hutool.core.io.FileMagicNumber.9
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 10 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 67) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_LINK)) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_NORMAL));
            }
        };
        DWG = fileMagicNumber9;
        FileMagicNumber fileMagicNumber10 = new FileMagicNumber("WEBP", 9, MimeTypes.IMAGE_WEBP, "webp") { // from class: cn.hutool.core.io.FileMagicNumber.10
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 11 && Objects.equals(Byte.valueOf(bArr[8]), (byte) 87) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 69) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 66) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 80);
            }
        };
        WEBP = fileMagicNumber10;
        FileMagicNumber fileMagicNumber11 = new FileMagicNumber("PSD", 10, "image/vnd.adobe.photoshop", ImgUtil.IMAGE_TYPE_PSD) { // from class: cn.hutool.core.io.FileMagicNumber.11
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 56) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 66) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE));
            }
        };
        PSD = fileMagicNumber11;
        FileMagicNumber fileMagicNumber12 = new FileMagicNumber("ICO", 11, "image/x-icon", "ico") { // from class: cn.hutool.core.io.FileMagicNumber.12
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 1) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 0);
            }
        };
        ICO = fileMagicNumber12;
        FileMagicNumber fileMagicNumber13 = new FileMagicNumber("XCF", 12, "image/x-xcf", "xcf") { // from class: cn.hutool.core.io.FileMagicNumber.13
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 9 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 32) && Objects.equals(Byte.valueOf(bArr[5]), Byte.valueOf(TarConstants.LF_PAX_EXTENDED_HEADER_LC)) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 99) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 32) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 118);
            }
        };
        XCF = fileMagicNumber13;
        FileMagicNumber fileMagicNumber14 = new FileMagicNumber("WAV", 13, PictureMimeType.WAV_Q, "wav") { // from class: cn.hutool.core.io.FileMagicNumber.14
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 11 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 87) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 86) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 69);
            }
        };
        WAV = fileMagicNumber14;
        FileMagicNumber fileMagicNumber15 = new FileMagicNumber("MIDI", 14, "audio/midi", "midi") { // from class: cn.hutool.core.io.FileMagicNumber.15
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 104) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 100);
            }
        };
        MIDI = fileMagicNumber15;
        FileMagicNumber fileMagicNumber16 = new FileMagicNumber("MP3", 15, "audio/mpeg", "mp3") { // from class: cn.hutool.core.io.FileMagicNumber.16
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 2) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 68) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_CHR))) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -5)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -13)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -14));
            }
        };
        MP3 = fileMagicNumber16;
        FileMagicNumber fileMagicNumber17 = new FileMagicNumber("OGG", 16, "audio/ogg", "ogg") { // from class: cn.hutool.core.io.FileMagicNumber.17
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER)) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER)) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE));
            }
        };
        OGG = fileMagicNumber17;
        FileMagicNumber fileMagicNumber18 = new FileMagicNumber("FLAC", 17, "audio/x-flac", "flac") { // from class: cn.hutool.core.io.FileMagicNumber.18
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 67);
            }
        };
        FLAC = fileMagicNumber18;
        FileMagicNumber fileMagicNumber19 = new FileMagicNumber("M4A", 18, com.google.android.exoplayer2.util.MimeTypes.AUDIO_MP4, "m4a") { // from class: cn.hutool.core.io.FileMagicNumber.19
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                int length = bArr.length;
                Byte bValueOf = Byte.valueOf(TarConstants.LF_BLK);
                if (length > 10 && Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[9]), bValueOf) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 65)) {
                    return true;
                }
                return Objects.equals(Byte.valueOf(bArr[0]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[1]), bValueOf) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 32);
            }
        };
        M4A = fileMagicNumber19;
        FileMagicNumber fileMagicNumber20 = new FileMagicNumber("AAC", 19, MimeTypes.AUDIO_AAC, "aac") { // from class: cn.hutool.core.io.FileMagicNumber.20
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 1) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -15)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) -1) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -7));
            }
        };
        AAC = fileMagicNumber20;
        FileMagicNumber fileMagicNumber21 = new FileMagicNumber("AMR", 20, "audio/amr", "amr") { // from class: cn.hutool.core.io.FileMagicNumber.21
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 11) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) 35) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 33) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 10)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) 35) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 33) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 95) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 67) && Objects.equals(Byte.valueOf(bArr[8]), Byte.valueOf(TarConstants.LF_LINK)) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 46) && Objects.equals(Byte.valueOf(bArr[10]), Byte.valueOf(TarConstants.LF_NORMAL)) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 10));
            }
        };
        AMR = fileMagicNumber21;
        FileMagicNumber fileMagicNumber22 = new FileMagicNumber("AC3", 21, com.google.android.exoplayer2.util.MimeTypes.AUDIO_AC3, "ac3") { // from class: cn.hutool.core.io.FileMagicNumber.22
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 11) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 119);
            }
        };
        AC3 = fileMagicNumber22;
        FileMagicNumber fileMagicNumber23 = new FileMagicNumber("AIFF", 22, "audio/x-aiff", "aiff") { // from class: cn.hutool.core.io.FileMagicNumber.23
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 11 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 70);
            }
        };
        AIFF = fileMagicNumber23;
        FileMagicNumber fileMagicNumber24 = new FileMagicNumber("WOFF", 23, "font/woff", "woff") { // from class: cn.hutool.core.io.FileMagicNumber.24
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 8) {
                    return false;
                }
                boolean z2 = Objects.equals(Byte.valueOf(bArr[0]), (byte) 119) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70);
                boolean z3 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 1) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 0);
                boolean z4 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 79);
                boolean z5 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 117) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 101);
                if (z2) {
                    return z3 || z4 || z5;
                }
                return false;
            }
        };
        WOFF = fileMagicNumber24;
        FileMagicNumber fileMagicNumber25 = new FileMagicNumber("WOFF2", 24, "font/woff2", "woff2") { // from class: cn.hutool.core.io.FileMagicNumber.25
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 8) {
                    return false;
                }
                boolean z2 = Objects.equals(Byte.valueOf(bArr[0]), (byte) 119) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_SYMLINK));
                boolean z3 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 1) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 0);
                boolean z4 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 79);
                boolean z5 = Objects.equals(Byte.valueOf(bArr[4]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 117) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 101);
                if (z2) {
                    return z3 || z4 || z5;
                }
                return false;
            }
        };
        WOFF2 = fileMagicNumber25;
        FileMagicNumber fileMagicNumber26 = new FileMagicNumber("TTF", 25, "font/ttf", "ttf") { // from class: cn.hutool.core.io.FileMagicNumber.26
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 1) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 0);
            }
        };
        TTF = fileMagicNumber26;
        FileMagicNumber fileMagicNumber27 = new FileMagicNumber("OTF", 26, "font/otf", "otf") { // from class: cn.hutool.core.io.FileMagicNumber.27
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 84) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 0);
            }
        };
        OTF = fileMagicNumber27;
        FileMagicNumber fileMagicNumber28 = new FileMagicNumber("EPUB", 27, "application/epub+zip", "epub") { // from class: cn.hutool.core.io.FileMagicNumber.28
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 58 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGLINK)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 3) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 4) && Objects.equals(Byte.valueOf(bArr[30]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[31]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[32]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[33]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[34]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[35]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[36]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[37]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[38]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[39]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[40]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[41]), (byte) 108) && Objects.equals(Byte.valueOf(bArr[42]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[43]), (byte) 99) && Objects.equals(Byte.valueOf(bArr[44]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[45]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[46]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[47]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[48]), (byte) 110) && Objects.equals(Byte.valueOf(bArr[49]), (byte) 47) && Objects.equals(Byte.valueOf(bArr[50]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[51]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[52]), (byte) 117) && Objects.equals(Byte.valueOf(bArr[53]), (byte) 98) && Objects.equals(Byte.valueOf(bArr[54]), (byte) 43) && Objects.equals(Byte.valueOf(bArr[55]), (byte) 122) && Objects.equals(Byte.valueOf(bArr[56]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[57]), (byte) 112);
            }
        };
        EPUB = fileMagicNumber28;
        FileMagicNumber fileMagicNumber29 = new FileMagicNumber("ZIP", 28, MimeTypes.APP_ZIP, "zip") { // from class: cn.hutool.core.io.FileMagicNumber.29
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 4) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGLINK))) && (Objects.equals(Byte.valueOf(bArr[2]), (byte) 3) || Objects.equals(Byte.valueOf(bArr[2]), (byte) 5) || Objects.equals(Byte.valueOf(bArr[2]), (byte) 7)) && (Objects.equals(Byte.valueOf(bArr[3]), (byte) 4) || Objects.equals(Byte.valueOf(bArr[3]), (byte) 6) || Objects.equals(Byte.valueOf(bArr[3]), (byte) 8));
            }
        };
        ZIP = fileMagicNumber29;
        FileMagicNumber fileMagicNumber30 = new FileMagicNumber("TAR", 29, "application/x-tar", ArchiveStreamFactory.TAR) { // from class: cn.hutool.core.io.FileMagicNumber.30
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 261 && Objects.equals(Byte.valueOf(bArr[257]), (byte) 117) && Objects.equals(Byte.valueOf(bArr[258]), (byte) 115) && Objects.equals(Byte.valueOf(bArr[259]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[260]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[261]), (byte) 114);
            }
        };
        TAR = fileMagicNumber30;
        FileMagicNumber fileMagicNumber31 = new FileMagicNumber("RAR", 30, "application/x-rar-compressed", "rar") { // from class: cn.hutool.core.io.FileMagicNumber.31
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length > 6 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 33) && Objects.equals(Byte.valueOf(bArr[4]), Byte.valueOf(Ascii.SUB)) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 7)) {
                    return Objects.equals(Byte.valueOf(bArr[6]), (byte) 0) || Objects.equals(Byte.valueOf(bArr[6]), (byte) 1);
                }
                return false;
            }
        };
        RAR = fileMagicNumber31;
        FileMagicNumber fileMagicNumber32 = new FileMagicNumber("GZ", 31, "application/gzip", CompressorStreamFactory.GZIP) { // from class: cn.hutool.core.io.FileMagicNumber.32
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(Ascii.US)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -117) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 8);
            }
        };
        GZ = fileMagicNumber32;
        FileMagicNumber fileMagicNumber33 = new FileMagicNumber("BZ2", 32, "application/x-bzip2", "bz2") { // from class: cn.hutool.core.io.FileMagicNumber.33
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 2 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 66) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 90) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 104);
            }
        };
        BZ2 = fileMagicNumber33;
        FileMagicNumber fileMagicNumber34 = new FileMagicNumber("SevenZ", 33, "application/x-7z-compressed", ArchiveStreamFactory.SEVEN_Z) { // from class: cn.hutool.core.io.FileMagicNumber.34
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 6 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(TarConstants.LF_CONTIG)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 122) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(PSSSigner.TRAILER_IMPLICIT)) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -81) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 39) && Objects.equals(Byte.valueOf(bArr[5]), Byte.valueOf(Ascii.FS)) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 0);
            }
        };
        SevenZ = fileMagicNumber34;
        FileMagicNumber fileMagicNumber35 = new FileMagicNumber("PDF", 34, MimeTypes.APP_PDF, "pdf") { // from class: cn.hutool.core.io.FileMagicNumber.35
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -17) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -69) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -65)) {
                    bArr = Arrays.copyOfRange(bArr, 3, bArr.length);
                }
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 37) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 80) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 68) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70);
            }
        };
        PDF = fileMagicNumber35;
        FileMagicNumber fileMagicNumber36 = new FileMagicNumber("EXE", 35, "application/x-msdownload", "exe") { // from class: cn.hutool.core.io.FileMagicNumber.36
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 1 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 90);
            }
        };
        EXE = fileMagicNumber36;
        FileMagicNumber fileMagicNumber37 = new FileMagicNumber("SWF", 36, "application/x-shockwave-flash", "swf") { // from class: cn.hutool.core.io.FileMagicNumber.37
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length > 2) {
                    return (Objects.equals(Byte.valueOf(bArr[0]), 67) || Objects.equals(Byte.valueOf(bArr[0]), (byte) 70)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 87) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE));
                }
                return false;
            }
        };
        SWF = fileMagicNumber37;
        FileMagicNumber fileMagicNumber38 = new FileMagicNumber("RTF", 37, "application/rtf", "rtf") { // from class: cn.hutool.core.io.FileMagicNumber.38
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 123) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 92) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 102);
            }
        };
        RTF = fileMagicNumber38;
        FileMagicNumber fileMagicNumber39 = new FileMagicNumber("NES", 38, "application/x-nintendo-nes-rom", "nes") { // from class: cn.hutool.core.io.FileMagicNumber.39
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 78) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 69) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE)) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(Ascii.SUB));
            }
        };
        NES = fileMagicNumber39;
        FileMagicNumber fileMagicNumber40 = new FileMagicNumber("CRX", 39, "application/x-google-chrome-extension", "crx") { // from class: cn.hutool.core.io.FileMagicNumber.40
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 67) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_SYMLINK)) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_BLK));
            }
        };
        CRX = fileMagicNumber40;
        FileMagicNumber fileMagicNumber41 = new FileMagicNumber("CAB", 40, "application/vnd.ms-cab-compressed", "cab") { // from class: cn.hutool.core.io.FileMagicNumber.41
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 4) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[0]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 67) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70)) || (Objects.equals(Byte.valueOf(bArr[0]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 99) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 40));
            }
        };
        CAB = fileMagicNumber41;
        FileMagicNumber fileMagicNumber42 = new FileMagicNumber("PS", 41, "application/postscript", AliyunLogKey.KEY_PART_SIZE) { // from class: cn.hutool.core.io.FileMagicNumber.42
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 1 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 37) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 33);
            }
        };
        PS = fileMagicNumber42;
        FileMagicNumber fileMagicNumber43 = new FileMagicNumber("XZ", 42, "application/x-xz", CompressorStreamFactory.XZ) { // from class: cn.hutool.core.io.FileMagicNumber.43
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 5 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -3) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_CONTIG)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 122) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(TarConstants.LF_PAX_EXTENDED_HEADER_UC)) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 90) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 0);
            }
        };
        XZ = fileMagicNumber43;
        FileMagicNumber fileMagicNumber44 = new FileMagicNumber("SQLITE", 43, "application/x-sqlite3", "sqlite") { // from class: cn.hutool.core.io.FileMagicNumber.44
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 15 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 81) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 32) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[12]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[13]), (byte) 32) && Objects.equals(Byte.valueOf(bArr[14]), Byte.valueOf(TarConstants.LF_CHR)) && Objects.equals(Byte.valueOf(bArr[15]), (byte) 0);
            }
        };
        SQLITE = fileMagicNumber44;
        FileMagicNumber fileMagicNumber45 = new FileMagicNumber("DEB", 44, "application/x-deb", "deb") { // from class: cn.hutool.core.io.FileMagicNumber.45
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 20 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 33) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 60) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 99) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 104) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 62) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 10) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 100) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 98) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[12]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[13]), (byte) 110) && Objects.equals(Byte.valueOf(bArr[14]), (byte) 45) && Objects.equals(Byte.valueOf(bArr[15]), (byte) 98) && Objects.equals(Byte.valueOf(bArr[16]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[17]), (byte) 110) && Objects.equals(Byte.valueOf(bArr[18]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[19]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[20]), (byte) 121);
            }
        };
        DEB = fileMagicNumber45;
        FileMagicNumber fileMagicNumber46 = new FileMagicNumber("AR", 45, "application/x-unix-archive", ArchiveStreamFactory.AR) { // from class: cn.hutool.core.io.FileMagicNumber.46
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 6 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 33) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 60) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 99) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 104) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 62);
            }
        };
        AR = fileMagicNumber46;
        FileMagicNumber fileMagicNumber47 = new FileMagicNumber("LZOP", 46, "application/x-lzop", "lzo") { // from class: cn.hutool.core.io.FileMagicNumber.47
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 7 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -119) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 90) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 79) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 13) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 10) && Objects.equals(Byte.valueOf(bArr[7]), Byte.valueOf(Ascii.SUB));
            }
        };
        LZOP = fileMagicNumber47;
        FileMagicNumber fileMagicNumber48 = new FileMagicNumber("LZ", 47, "application/x-lzip", "lz") { // from class: cn.hutool.core.io.FileMagicNumber.48
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 90) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 80);
            }
        };
        LZ = fileMagicNumber48;
        FileMagicNumber fileMagicNumber49 = new FileMagicNumber("ELF", 48, "application/x-executable", "elf") { // from class: cn.hutool.core.io.FileMagicNumber.49
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 52 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 127) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 69) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70);
            }
        };
        ELF = fileMagicNumber49;
        FileMagicNumber fileMagicNumber50 = new FileMagicNumber("LZ4", 49, "application/x-lz4", "lz4") { // from class: cn.hutool.core.io.FileMagicNumber.50
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 4) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 34) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[3]), Byte.valueOf(Ascii.CAN));
            }
        };
        LZ4 = fileMagicNumber50;
        FileMagicNumber fileMagicNumber51 = new FileMagicNumber("BR", 50, "application/x-brotli", "br") { // from class: cn.hutool.core.io.FileMagicNumber.51
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -50) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -78) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -49) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -127);
            }
        };
        BR = fileMagicNumber51;
        FileMagicNumber fileMagicNumber52 = new FileMagicNumber("DCM", 51, "application/x-dicom", "dcm") { // from class: cn.hutool.core.io.FileMagicNumber.52
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 128 && Objects.equals(Byte.valueOf(bArr[128]), (byte) 68) && Objects.equals(Byte.valueOf(bArr[129]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[130]), (byte) 67) && Objects.equals(Byte.valueOf(bArr[131]), (byte) 77);
            }
        };
        DCM = fileMagicNumber52;
        FileMagicNumber fileMagicNumber53 = new FileMagicNumber("RPM", 52, "application/x-rpm", "rpm") { // from class: cn.hutool.core.io.FileMagicNumber.53
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) -19) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -85) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -18) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -37);
            }
        };
        RPM = fileMagicNumber53;
        FileMagicNumber fileMagicNumber54 = new FileMagicNumber("ZSTD", 53, "application/x-zstd", "zst") { // from class: cn.hutool.core.io.FileMagicNumber.54
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 5) {
                    return false;
                }
                if (PrimitiveArrayUtil.contains(new byte[]{34, 35, 36, 37, 38, 39, 40}, bArr[0]) && Objects.equals(Byte.valueOf(bArr[1]), (byte) -75) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 47) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -3)) {
                    return true;
                }
                return (bArr[0] & 240) == 80 && bArr[1] == 42 && bArr[2] == 77 && bArr[3] == 24;
            }
        };
        ZSTD = fileMagicNumber54;
        FileMagicNumber fileMagicNumber55 = new FileMagicNumber("MP4", 54, "video/mp4", "mp4") { // from class: cn.hutool.core.io.FileMagicNumber.55
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 13) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[9]), Byte.valueOf(TarConstants.LF_GNUTYPE_SPARSE)) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 78) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 86)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 115) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 109));
            }
        };
        MP4 = fileMagicNumber55;
        FileMagicNumber fileMagicNumber56 = new FileMagicNumber("AVI", 55, "video/x-msvideo", "avi") { // from class: cn.hutool.core.io.FileMagicNumber.56
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 11 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 65) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 86) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 73) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 32);
            }
        };
        AVI = fileMagicNumber56;
        FileMagicNumber fileMagicNumber57 = new FileMagicNumber("WMV", 56, "video/x-ms-wmv", "wmv") { // from class: cn.hutool.core.io.FileMagicNumber.57
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 9 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(TarConstants.LF_NORMAL)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 38) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -78) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 117) && Objects.equals(Byte.valueOf(bArr[4]), (byte) -114) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[6]), (byte) -49) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 17) && Objects.equals(Byte.valueOf(bArr[8]), (byte) -90) && Objects.equals(Byte.valueOf(bArr[9]), (byte) -39);
            }
        };
        WMV = fileMagicNumber57;
        FileMagicNumber fileMagicNumber58 = new FileMagicNumber("M4V", 57, "video/x-m4v", "m4v") { // from class: cn.hutool.core.io.FileMagicNumber.58
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 12) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[9]), Byte.valueOf(TarConstants.LF_BLK)) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 86) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 32)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[10]), Byte.valueOf(TarConstants.LF_BLK)) && Objects.equals(Byte.valueOf(bArr[11]), Byte.valueOf(TarConstants.LF_SYMLINK)));
            }
        };
        M4V = fileMagicNumber58;
        FileMagicNumber fileMagicNumber59 = new FileMagicNumber("FLV", 58, com.google.android.exoplayer2.util.MimeTypes.VIDEO_FLV, "flv") { // from class: cn.hutool.core.io.FileMagicNumber.59
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 70) && Objects.equals(Byte.valueOf(bArr[1]), Byte.valueOf(TarConstants.LF_GNUTYPE_LONGNAME)) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 86) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 1);
            }
        };
        FLV = fileMagicNumber59;
        FileMagicNumber fileMagicNumber60 = new FileMagicNumber("MKV", 59, com.google.android.exoplayer2.util.MimeTypes.VIDEO_MATROSKA, "mkv") { // from class: cn.hutool.core.io.FileMagicNumber.60
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return (bArr.length > 11 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(Ascii.SUB)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 69) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -33) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -93)) && FileMagicNumber.indexOf(bArr, new byte[]{66, -126, -120, 109, 97, 116, 114, 111, 115, 107, 97}) > 0;
            }
        };
        MKV = fileMagicNumber60;
        FileMagicNumber fileMagicNumber61 = new FileMagicNumber("WEBM", 60, "video/webm", "webm") { // from class: cn.hutool.core.io.FileMagicNumber.61
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return (bArr.length > 8 && Objects.equals(Byte.valueOf(bArr[0]), Byte.valueOf(Ascii.SUB)) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 69) && Objects.equals(Byte.valueOf(bArr[2]), (byte) -33) && Objects.equals(Byte.valueOf(bArr[3]), (byte) -93)) && FileMagicNumber.indexOf(bArr, new byte[]{66, -126, -120, 119, 101, 98, 109}) > 0;
            }
        };
        WEBM = fileMagicNumber61;
        FileMagicNumber fileMagicNumber62 = new FileMagicNumber("MOV", 61, "video/quicktime", "mov") { // from class: cn.hutool.core.io.FileMagicNumber.62
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 12) {
                    return false;
                }
                return (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), (byte) 113) && Objects.equals(Byte.valueOf(bArr[9]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 32) && Objects.equals(Byte.valueOf(bArr[11]), (byte) 32)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 118)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 114) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 101)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 100) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 116)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 119) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 100) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 101)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 110) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 111) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 116)) || (Objects.equals(Byte.valueOf(bArr[4]), (byte) 115) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 107) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 105) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112));
            }
        };
        MOV = fileMagicNumber62;
        FileMagicNumber fileMagicNumber63 = new FileMagicNumber("MPEG", 62, com.google.android.exoplayer2.util.MimeTypes.VIDEO_MPEG, "mpg") { // from class: cn.hutool.core.io.FileMagicNumber.63
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                byte b3;
                return bArr.length > 3 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 1) && (b3 = bArr[3]) >= -80 && b3 <= -65;
            }
        };
        MPEG = fileMagicNumber63;
        FileMagicNumber fileMagicNumber64 = new FileMagicNumber("RMVB", 63, "video/vnd.rn-realvideo", "rmvb") { // from class: cn.hutool.core.io.FileMagicNumber.64
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 46) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 82) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 77) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 70);
            }
        };
        RMVB = fileMagicNumber64;
        FileMagicNumber fileMagicNumber65 = new FileMagicNumber("M3GP", 64, "video/3gpp", "3gp") { // from class: cn.hutool.core.io.FileMagicNumber.65
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 10 && Objects.equals(Byte.valueOf(bArr[4]), (byte) 102) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 116) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 112) && Objects.equals(Byte.valueOf(bArr[8]), Byte.valueOf(TarConstants.LF_CHR)) && Objects.equals(Byte.valueOf(bArr[9]), Byte.valueOf(TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER)) && Objects.equals(Byte.valueOf(bArr[10]), (byte) 112);
            }
        };
        M3GP = fileMagicNumber65;
        FileMagicNumber fileMagicNumber66 = new FileMagicNumber("DOC", 65, MimeTypes.APP_MSWORD, "doc") { // from class: cn.hutool.core.io.FileMagicNumber.66
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (!(bArr.length > 515 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), new byte[]{-48, -49, 17, -32, -95, -79, Ascii.SUB, -31}))) {
                    return false;
                }
                return Arrays.equals(Arrays.copyOfRange(bArr, 512, R2.attr.biaozianzi), new byte[]{-20, -91, -63, 0}) || (bArr.length > 2142 && FileMagicNumber.indexOf(Arrays.copyOfRange(bArr, R2.attr.isAppendMode, R2.attr.jiav), new byte[]{0, 10, 0, 0, 0, 77, TarConstants.LF_GNUTYPE_SPARSE, 87, 111, 114, 100, 68, 111, 99, 0, 16, 0, 0, 0, 87, 111, 114, 100, 46, 68, 111, 99, 117, 109, 101, 110, 116, 46, 56, 0, -12, 57, -78, 113}) > 0);
            }
        };
        DOC = fileMagicNumber66;
        FileMagicNumber fileMagicNumber67 = new FileMagicNumber("XLS", 66, MimeTypes.APP_EXCEL, "xls") { // from class: cn.hutool.core.io.FileMagicNumber.67
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                byte b3;
                if (bArr.length > 520 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), new byte[]{-48, -49, 17, -32, -95, -79, Ascii.SUB, -31})) {
                    return (Arrays.equals(Arrays.copyOfRange(bArr, 512, R2.attr.biaozianzi), new byte[]{-3, -1, -1, -1}) && ((b3 = bArr[518]) == 0 || b3 == 2)) || Arrays.equals(Arrays.copyOfRange(bArr, 512, R2.attr.bl_active_textColor), new byte[]{9, 8, 16, 0, 0, 6, 5, 0}) || (bArr.length > 2095 && Arrays.equals(Arrays.copyOfRange(bArr, R2.attr.floatingActionButtonStyle, R2.attr.isTipViewClippingEnabled), new byte[]{-30, 0, 0, 0, 92, 0, 112, 0, 4, 0, 0, 67, 97, 108, 99}));
                }
                return false;
            }
        };
        XLS = fileMagicNumber67;
        FileMagicNumber fileMagicNumber68 = new FileMagicNumber("PPT", 67, "application/vnd.ms-powerpoint", "ppt") { // from class: cn.hutool.core.io.FileMagicNumber.68
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (!(bArr.length > 524 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), new byte[]{-48, -49, 17, -32, -95, -79, Ascii.SUB, -31}))) {
                    return false;
                }
                byte[] bArr2 = {-96, 70, Ascii.GS, -16};
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 512, R2.attr.biaozianzi);
                return Arrays.equals(bArrCopyOfRange, bArr2) || Arrays.equals(bArrCopyOfRange, new byte[]{0, 110, Ascii.RS, -16}) || Arrays.equals(bArrCopyOfRange, new byte[]{15, 0, -24, 3}) || (Arrays.equals(bArrCopyOfRange, new byte[]{-3, -1, -1, -1}) && bArr[522] == 0 && bArr[523] == 0) || (bArr.length > 2096 && Arrays.equals(Arrays.copyOfRange(bArr, R2.attr.invite_status_no_pass_color, R2.attr.is_circle), new byte[]{0, -71, 41, -24, 17, 0, 0, 0, 77, TarConstants.LF_GNUTYPE_SPARSE, 32, 80, 111, 119, 101, 114, 80, 111, 105, 110, 116, 32, 57, TarConstants.LF_CONTIG}));
            }
        };
        PPT = fileMagicNumber68;
        FileMagicNumber fileMagicNumber69 = new FileMagicNumber("DOCX", 68, MimeTypes.APP_DOCX, "docx") { // from class: cn.hutool.core.io.FileMagicNumber.69
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return Objects.equals(FileMagicNumber.matchDocument(bArr), FileMagicNumber.DOCX);
            }
        };
        DOCX = fileMagicNumber69;
        FileMagicNumber fileMagicNumber70 = new FileMagicNumber("PPTX", 69, "application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx") { // from class: cn.hutool.core.io.FileMagicNumber.70
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return Objects.equals(FileMagicNumber.matchDocument(bArr), FileMagicNumber.PPTX);
            }
        };
        PPTX = fileMagicNumber70;
        FileMagicNumber fileMagicNumber71 = new FileMagicNumber("XLSX", 70, MimeTypes.APP_XLSX, "xlsx") { // from class: cn.hutool.core.io.FileMagicNumber.71
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return Objects.equals(FileMagicNumber.matchDocument(bArr), FileMagicNumber.XLSX);
            }
        };
        XLSX = fileMagicNumber71;
        FileMagicNumber fileMagicNumber72 = new FileMagicNumber("WASM", 71, "application/wasm", "wasm") { // from class: cn.hutool.core.io.FileMagicNumber.72
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 7 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 97) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 115) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 109) && Objects.equals(Byte.valueOf(bArr[4]), (byte) 1) && Objects.equals(Byte.valueOf(bArr[5]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[6]), (byte) 0) && Objects.equals(Byte.valueOf(bArr[7]), (byte) 0);
            }
        };
        WASM = fileMagicNumber72;
        FileMagicNumber fileMagicNumber73 = new FileMagicNumber("DEX", 72, "application/vnd.android.dex", "dex") { // from class: cn.hutool.core.io.FileMagicNumber.73
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 36 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 100) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[2]), Byte.valueOf(TarConstants.LF_PAX_EXTENDED_HEADER_LC)) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 10) && Objects.equals(Byte.valueOf(bArr[36]), (byte) 112);
            }
        };
        DEX = fileMagicNumber73;
        FileMagicNumber fileMagicNumber74 = new FileMagicNumber("DEY", 73, "application/vnd.android.dey", "dey") { // from class: cn.hutool.core.io.FileMagicNumber.74
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 100 && Objects.equals(Byte.valueOf(bArr[0]), (byte) 100) && Objects.equals(Byte.valueOf(bArr[1]), (byte) 101) && Objects.equals(Byte.valueOf(bArr[2]), (byte) 121) && Objects.equals(Byte.valueOf(bArr[3]), (byte) 10) && FileMagicNumber.DEX.match(Arrays.copyOfRange(bArr, 40, 100));
            }
        };
        DEY = fileMagicNumber74;
        FileMagicNumber fileMagicNumber75 = new FileMagicNumber("EML", 74, "message/rfc822", "eml") { // from class: cn.hutool.core.io.FileMagicNumber.75
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                if (bArr.length < 8) {
                    return false;
                }
                return Arrays.equals(Arrays.copyOfRange(bArr, 0, 7), new byte[]{70, 114, 111, 109, 32, 32, 32}) || Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), new byte[]{70, 114, 111, 109, 32, Utf8.REPLACEMENT_BYTE, Utf8.REPLACEMENT_BYTE, Utf8.REPLACEMENT_BYTE}) || Arrays.equals(Arrays.copyOfRange(bArr, 0, 6), new byte[]{70, 114, 111, 109, HttpTokens.COLON, 32}) || (bArr.length > 13 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 13), new byte[]{82, 101, 116, 117, 114, 110, 45, 80, 97, 116, 104, HttpTokens.COLON, 32}));
            }
        };
        EML = fileMagicNumber75;
        FileMagicNumber fileMagicNumber76 = new FileMagicNumber("MDB", 75, "application/vnd.ms-access", "mdb") { // from class: cn.hutool.core.io.FileMagicNumber.76
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 18 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 18), new byte[]{0, 1, 0, 0, TarConstants.LF_GNUTYPE_SPARSE, 116, 97, 110, 100, 97, 114, 100, 32, 74, 101, 116, 32, 68, 66});
            }
        };
        MDB = fileMagicNumber76;
        FileMagicNumber fileMagicNumber77 = new FileMagicNumber("CHM", 76, "application/vnd.ms-htmlhelp", "chm") { // from class: cn.hutool.core.io.FileMagicNumber.77
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), new byte[]{73, 84, TarConstants.LF_GNUTYPE_SPARSE, 70});
            }
        };
        CHM = fileMagicNumber77;
        FileMagicNumber fileMagicNumber78 = new FileMagicNumber("CLASS", 77, "application/java-vm", "class") { // from class: cn.hutool.core.io.FileMagicNumber.78
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), new byte[]{-54, -2, -70, -66});
            }
        };
        CLASS = fileMagicNumber78;
        FileMagicNumber fileMagicNumber79 = new FileMagicNumber("TORRENT", 78, "application/x-bittorrent", "torrent") { // from class: cn.hutool.core.io.FileMagicNumber.79
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 11 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 11), new byte[]{100, 56, HttpTokens.COLON, 97, 110, 110, 111, 117, 110, 99, 101});
            }
        };
        TORRENT = fileMagicNumber79;
        FileMagicNumber fileMagicNumber80 = new FileMagicNumber("WPD", 79, "application/vnd.wordperfect", "wpd") { // from class: cn.hutool.core.io.FileMagicNumber.80
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), new byte[]{-1, 87, 80, 67});
            }
        };
        WPD = fileMagicNumber80;
        FileMagicNumber fileMagicNumber81 = new FileMagicNumber("DBX", 80, "", "dbx") { // from class: cn.hutool.core.io.FileMagicNumber.81
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), new byte[]{-49, -83, Ascii.DC2, -2});
            }
        };
        DBX = fileMagicNumber81;
        FileMagicNumber fileMagicNumber82 = new FileMagicNumber("PST", 81, "application/vnd.ms-outlook-pst", "pst") { // from class: cn.hutool.core.io.FileMagicNumber.82
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 4 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), new byte[]{33, 66, 68, 78});
            }
        };
        PST = fileMagicNumber82;
        FileMagicNumber fileMagicNumber83 = new FileMagicNumber("RAM", 82, "audio/x-pn-realaudio", "ram") { // from class: cn.hutool.core.io.FileMagicNumber.83
            @Override // cn.hutool.core.io.FileMagicNumber
            public boolean match(byte[] bArr) {
                return bArr.length > 5 && Arrays.equals(Arrays.copyOfRange(bArr, 0, 5), new byte[]{46, 114, 97, -3, 0});
            }
        };
        RAM = fileMagicNumber83;
        $VALUES = new FileMagicNumber[]{fileMagicNumber, fileMagicNumber2, fileMagicNumber3, fileMagicNumber4, fileMagicNumber5, fileMagicNumber6, fileMagicNumber7, fileMagicNumber8, fileMagicNumber9, fileMagicNumber10, fileMagicNumber11, fileMagicNumber12, fileMagicNumber13, fileMagicNumber14, fileMagicNumber15, fileMagicNumber16, fileMagicNumber17, fileMagicNumber18, fileMagicNumber19, fileMagicNumber20, fileMagicNumber21, fileMagicNumber22, fileMagicNumber23, fileMagicNumber24, fileMagicNumber25, fileMagicNumber26, fileMagicNumber27, fileMagicNumber28, fileMagicNumber29, fileMagicNumber30, fileMagicNumber31, fileMagicNumber32, fileMagicNumber33, fileMagicNumber34, fileMagicNumber35, fileMagicNumber36, fileMagicNumber37, fileMagicNumber38, fileMagicNumber39, fileMagicNumber40, fileMagicNumber41, fileMagicNumber42, fileMagicNumber43, fileMagicNumber44, fileMagicNumber45, fileMagicNumber46, fileMagicNumber47, fileMagicNumber48, fileMagicNumber49, fileMagicNumber50, fileMagicNumber51, fileMagicNumber52, fileMagicNumber53, fileMagicNumber54, fileMagicNumber55, fileMagicNumber56, fileMagicNumber57, fileMagicNumber58, fileMagicNumber59, fileMagicNumber60, fileMagicNumber61, fileMagicNumber62, fileMagicNumber63, fileMagicNumber64, fileMagicNumber65, fileMagicNumber66, fileMagicNumber67, fileMagicNumber68, fileMagicNumber69, fileMagicNumber70, fileMagicNumber71, fileMagicNumber72, fileMagicNumber73, fileMagicNumber74, fileMagicNumber75, fileMagicNumber76, fileMagicNumber77, fileMagicNumber78, fileMagicNumber79, fileMagicNumber80, fileMagicNumber81, fileMagicNumber82, fileMagicNumber83};
    }

    private static boolean compareBytes(byte[] bArr, byte[] bArr2, int i2) {
        int length = bArr2.length + i2;
        if (length > bArr.length) {
            return false;
        }
        return Arrays.equals(Arrays.copyOfRange(bArr, i2, length), bArr2);
    }

    public static FileMagicNumber getMagicNumber(final byte[] bArr) {
        Optional optionalFindFirst = Arrays.stream(values()).filter(new Predicate() { // from class: cn.hutool.core.io.a
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FileMagicNumber.lambda$getMagicNumber$0(bArr, (FileMagicNumber) obj);
            }
        }).findFirst();
        FileMagicNumber fileMagicNumber = UNKNOWN;
        FileMagicNumber fileMagicNumber2 = (FileMagicNumber) optionalFindFirst.orElse(fileMagicNumber);
        FileMagicNumber fileMagicNumber3 = ZIP;
        if (!fileMagicNumber2.equals(fileMagicNumber3)) {
            return fileMagicNumber2;
        }
        FileMagicNumber fileMagicNumberMatchDocument = matchDocument(bArr);
        return fileMagicNumberMatchDocument == fileMagicNumber ? fileMagicNumber3 : fileMagicNumberMatchDocument;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0023, code lost:
    
        r1 = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(byte[] r6, byte[] r7) {
        /*
            r0 = -1
            if (r6 == 0) goto L2a
            if (r7 == 0) goto L2a
            int r1 = r6.length
            int r2 = r7.length
            if (r1 >= r2) goto La
            goto L2a
        La:
            int r1 = r7.length
            r2 = 0
            if (r1 != 0) goto Lf
            return r2
        Lf:
            r1 = r2
        L10:
            int r3 = r6.length
            int r4 = r7.length
            int r3 = r3 - r4
            int r3 = r3 + 1
            if (r1 >= r3) goto L2a
            r3 = r2
        L18:
            int r4 = r7.length
            if (r3 >= r4) goto L29
            int r4 = r1 + r3
            r4 = r6[r4]
            r5 = r7[r3]
            if (r4 == r5) goto L26
            int r1 = r1 + 1
            goto L10
        L26:
            int r3 = r3 + 1
            goto L18
        L29:
            return r1
        L2a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.io.FileMagicNumber.indexOf(byte[], byte[]):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getMagicNumber$0(byte[] bArr, FileMagicNumber fileMagicNumber) {
        return fileMagicNumber.match(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FileMagicNumber matchDocument(byte[] bArr) {
        FileMagicNumber fileMagicNumberMatchOpenXmlMime = matchOpenXmlMime(bArr, 30);
        FileMagicNumber fileMagicNumber = UNKNOWN;
        if (!fileMagicNumberMatchOpenXmlMime.equals(fileMagicNumber)) {
            return fileMagicNumberMatchOpenXmlMime;
        }
        if (!(compareBytes(bArr, new byte[]{91, 67, 111, 110, 116, 101, 110, 116, 95, 84, 121, 112, 101, 115, 93, 46, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 109, 108}, 30) || compareBytes(bArr, new byte[]{95, 114, 101, 108, 115, 47, 46, 114, 101, 108, 115}, 30) || compareBytes(bArr, new byte[]{100, 111, 99, 80, 114, 111, 112, 115}, 30))) {
            return fileMagicNumber;
        }
        int iSearchSignature = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            iSearchSignature = searchSignature(bArr, iSearchSignature + 4, 6000);
            if (iSearchSignature != -1) {
                FileMagicNumber fileMagicNumberMatchOpenXmlMime2 = matchOpenXmlMime(bArr, iSearchSignature + 30);
                if (!fileMagicNumberMatchOpenXmlMime2.equals(UNKNOWN)) {
                    return fileMagicNumberMatchOpenXmlMime2;
                }
            }
        }
        return UNKNOWN;
    }

    private static FileMagicNumber matchOpenXmlMime(byte[] bArr, int i2) {
        return compareBytes(bArr, new byte[]{119, 111, 114, 100, 47}, i2) ? DOCX : compareBytes(bArr, new byte[]{112, 112, 116, 47}, i2) ? PPTX : compareBytes(bArr, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_LC, 108, 47}, i2) ? XLSX : UNKNOWN;
    }

    private static int searchSignature(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = {80, TarConstants.LF_GNUTYPE_LONGLINK, 3, 4};
        int length = bArr.length;
        int i4 = i3 + i2;
        if (i4 <= length) {
            length = i4;
        }
        int iIndexOf = indexOf(Arrays.copyOfRange(bArr, i2, length), bArr2);
        if (iIndexOf == -1) {
            return -1;
        }
        return i2 + iIndexOf;
    }

    public static FileMagicNumber valueOf(String str) {
        return (FileMagicNumber) Enum.valueOf(FileMagicNumber.class, str);
    }

    public static FileMagicNumber[] values() {
        return (FileMagicNumber[]) $VALUES.clone();
    }

    public String getExtension() {
        return this.extension;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public abstract boolean match(byte[] bArr);

    private FileMagicNumber(String str, int i2, String str2, String str3) {
        this.mimeType = str2;
        this.extension = str3;
    }
}
