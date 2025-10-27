package androidx.camera.core.impl.utils;

import android.os.Build;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.core.util.Preconditions;
import androidx.exifinterface.media.ExifInterface;
import com.yikaobang.yixue.R2;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.compress.archivers.cpio.CpioConstants;

@RequiresApi(21)
/* loaded from: classes.dex */
public class ExifData {
    private static final boolean DEBUG = false;
    static final ExifTag[] EXIF_POINTER_TAGS;
    static final ExifTag[][] EXIF_TAGS;
    private static final ExifTag[] IFD_EXIF_TAGS;
    static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    static final int IFD_TYPE_EXIF = 1;
    static final int IFD_TYPE_GPS = 2;
    static final int IFD_TYPE_INTEROPERABILITY = 3;
    static final int IFD_TYPE_PRIMARY = 0;
    private static final int MM_IN_MICRONS = 1000;
    private static final String TAG = "ExifData";
    static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    static final HashSet<String> sTagSetForCompatibility;
    private final List<Map<String, ExifAttribute>> mAttributes;
    private final ByteOrder mByteOrder;

    /* renamed from: androidx.camera.core.impl.utils.ExifData$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$impl$CameraCaptureMetaData$FlashState;
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$impl$utils$ExifData$WhiteBalanceMode;

        static {
            int[] iArr = new int[WhiteBalanceMode.values().length];
            $SwitchMap$androidx$camera$core$impl$utils$ExifData$WhiteBalanceMode = iArr;
            try {
                iArr[WhiteBalanceMode.AUTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$impl$utils$ExifData$WhiteBalanceMode[WhiteBalanceMode.MANUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[CameraCaptureMetaData.FlashState.values().length];
            $SwitchMap$androidx$camera$core$impl$CameraCaptureMetaData$FlashState = iArr2;
            try {
                iArr2[CameraCaptureMetaData.FlashState.READY.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$core$impl$CameraCaptureMetaData$FlashState[CameraCaptureMetaData.FlashState.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$core$impl$CameraCaptureMetaData$FlashState[CameraCaptureMetaData.FlashState.FIRED.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static final class Builder {
        private static final int DATETIME_VALUE_STRING_LENGTH = 19;
        final List<Map<String, ExifAttribute>> mAttributes = Collections.list(new Enumeration<Map<String, ExifAttribute>>() { // from class: androidx.camera.core.impl.utils.ExifData.Builder.2
            int mIfdIndex = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.mIfdIndex < ExifData.EXIF_TAGS.length;
            }

            @Override // java.util.Enumeration
            public Map<String, ExifAttribute> nextElement() {
                this.mIfdIndex++;
                return new HashMap();
            }
        });
        private final ByteOrder mByteOrder;
        private static final Pattern GPS_TIMESTAMP_PATTERN = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
        private static final Pattern DATETIME_PRIMARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
        private static final Pattern DATETIME_SECONDARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
        static final List<HashMap<String, ExifTag>> sExifTagMapsForWriting = Collections.list(new Enumeration<HashMap<String, ExifTag>>() { // from class: androidx.camera.core.impl.utils.ExifData.Builder.1
            int mIfdIndex = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.mIfdIndex < ExifData.EXIF_TAGS.length;
            }

            @Override // java.util.Enumeration
            public HashMap<String, ExifTag> nextElement() {
                HashMap<String, ExifTag> map = new HashMap<>();
                for (ExifTag exifTag : ExifData.EXIF_TAGS[this.mIfdIndex]) {
                    map.put(exifTag.name, exifTag);
                }
                this.mIfdIndex++;
                return map;
            }
        });

        public Builder(@NonNull ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        private static Pair<Integer, Integer> guessDataFormat(String str) throws NumberFormatException {
            if (str.contains(",")) {
                String[] strArrSplit = str.split(",", -1);
                Pair<Integer, Integer> pairGuessDataFormat = guessDataFormat(strArrSplit[0]);
                if (((Integer) pairGuessDataFormat.first).intValue() == 2) {
                    return pairGuessDataFormat;
                }
                for (int i2 = 1; i2 < strArrSplit.length; i2++) {
                    Pair<Integer, Integer> pairGuessDataFormat2 = guessDataFormat(strArrSplit[i2]);
                    int iIntValue = (((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.first) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.first)) ? ((Integer) pairGuessDataFormat.first).intValue() : -1;
                    int iIntValue2 = (((Integer) pairGuessDataFormat.second).intValue() == -1 || !(((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.second) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.second))) ? -1 : ((Integer) pairGuessDataFormat.second).intValue();
                    if (iIntValue == -1 && iIntValue2 == -1) {
                        return new Pair<>(2, -1);
                    }
                    if (iIntValue == -1) {
                        pairGuessDataFormat = new Pair<>(Integer.valueOf(iIntValue2), -1);
                    } else if (iIntValue2 == -1) {
                        pairGuessDataFormat = new Pair<>(Integer.valueOf(iIntValue), -1);
                    }
                }
                return pairGuessDataFormat;
            }
            if (!str.contains("/")) {
                try {
                    try {
                        long j2 = Long.parseLong(str);
                        return (j2 < 0 || j2 > WebSocketProtocol.PAYLOAD_SHORT_MAX) ? j2 < 0 ? new Pair<>(9, -1) : new Pair<>(4, -1) : new Pair<>(3, 4);
                    } catch (NumberFormatException unused) {
                        Double.parseDouble(str);
                        return new Pair<>(12, -1);
                    }
                } catch (NumberFormatException unused2) {
                    return new Pair<>(2, -1);
                }
            }
            String[] strArrSplit2 = str.split("/", -1);
            if (strArrSplit2.length == 2) {
                try {
                    long j3 = (long) Double.parseDouble(strArrSplit2[0]);
                    long j4 = (long) Double.parseDouble(strArrSplit2[1]);
                    if (j3 >= 0 && j4 >= 0) {
                        if (j3 <= 2147483647L && j4 <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException unused3) {
                }
            }
            return new Pair<>(2, -1);
        }

        private void setAttributeIfMissing(@NonNull String str, @NonNull String str2, @NonNull List<Map<String, ExifAttribute>> list) throws NumberFormatException {
            Iterator<Map<String, ExifAttribute>> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().containsKey(str)) {
                    return;
                }
            }
            setAttributeInternal(str, str2, list);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void setAttributeInternal(@NonNull String str, @Nullable String str2, @NonNull List<Map<String, ExifAttribute>> list) throws NumberFormatException {
            int i2;
            int i3;
            int i4;
            Builder builder = this;
            String str3 = str;
            String strReplaceAll = str2;
            if ((ExifInterface.TAG_DATETIME.equals(str3) || ExifInterface.TAG_DATETIME_ORIGINAL.equals(str3) || ExifInterface.TAG_DATETIME_DIGITIZED.equals(str3)) && strReplaceAll != null) {
                boolean zFind = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
                boolean zFind2 = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
                if (str2.length() != 19 || (!zFind && !zFind2)) {
                    Logger.w(ExifData.TAG, "Invalid value for " + str3 + " : " + strReplaceAll);
                    return;
                }
                if (zFind2) {
                    strReplaceAll = strReplaceAll.replaceAll("-", ":");
                }
            }
            if (ExifInterface.TAG_ISO_SPEED_RATINGS.equals(str3)) {
                str3 = ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY;
            }
            String str4 = str3;
            int i5 = 2;
            int i6 = 1;
            if (strReplaceAll != null && ExifData.sTagSetForCompatibility.contains(str4)) {
                if (str4.equals(ExifInterface.TAG_GPS_TIMESTAMP)) {
                    Matcher matcher = GPS_TIMESTAMP_PATTERN.matcher(strReplaceAll);
                    if (!matcher.find()) {
                        Logger.w(ExifData.TAG, "Invalid value for " + str4 + " : " + strReplaceAll);
                        return;
                    }
                    strReplaceAll = Integer.parseInt((String) Preconditions.checkNotNull(matcher.group(1))) + "/1," + Integer.parseInt((String) Preconditions.checkNotNull(matcher.group(2))) + "/1," + Integer.parseInt((String) Preconditions.checkNotNull(matcher.group(3))) + "/1";
                } else {
                    try {
                        strReplaceAll = new LongRational(Double.parseDouble(strReplaceAll)).toString();
                    } catch (NumberFormatException e2) {
                        Logger.w(ExifData.TAG, "Invalid value for " + str4 + " : " + strReplaceAll, e2);
                        return;
                    }
                }
            }
            int i7 = 0;
            int i8 = 0;
            while (i8 < ExifData.EXIF_TAGS.length) {
                ExifTag exifTag = sExifTagMapsForWriting.get(i8).get(str4);
                if (exifTag == null) {
                    i2 = i8;
                    i3 = i6;
                } else {
                    if (strReplaceAll != null) {
                        Pair<Integer, Integer> pairGuessDataFormat = guessDataFormat(strReplaceAll);
                        if (exifTag.primaryFormat == ((Integer) pairGuessDataFormat.first).intValue() || exifTag.primaryFormat == ((Integer) pairGuessDataFormat.second).intValue()) {
                            i4 = exifTag.primaryFormat;
                        } else {
                            int i9 = exifTag.secondaryFormat;
                            if (i9 == -1 || !(i9 == ((Integer) pairGuessDataFormat.first).intValue() || exifTag.secondaryFormat == ((Integer) pairGuessDataFormat.second).intValue())) {
                                i4 = exifTag.primaryFormat;
                                if (i4 == i6 || i4 == 7 || i4 == i5) {
                                }
                            } else {
                                i4 = exifTag.secondaryFormat;
                            }
                        }
                        String str5 = "/";
                        switch (i4) {
                            case 1:
                                i2 = i8;
                                i3 = i6;
                                list.get(i2).put(str4, ExifAttribute.createByte(strReplaceAll));
                                break;
                            case 2:
                            case 7:
                                i2 = i8;
                                i3 = i6;
                                list.get(i2).put(str4, ExifAttribute.createString(strReplaceAll));
                                break;
                            case 3:
                                i2 = i8;
                                i3 = i6;
                                String[] strArrSplit = strReplaceAll.split(",", -1);
                                int[] iArr = new int[strArrSplit.length];
                                for (int i10 = 0; i10 < strArrSplit.length; i10++) {
                                    iArr[i10] = Integer.parseInt(strArrSplit[i10]);
                                }
                                list.get(i2).put(str4, ExifAttribute.createUShort(iArr, builder.mByteOrder));
                                break;
                            case 4:
                                i2 = i8;
                                i3 = i6;
                                String[] strArrSplit2 = strReplaceAll.split(",", -1);
                                long[] jArr = new long[strArrSplit2.length];
                                for (int i11 = 0; i11 < strArrSplit2.length; i11++) {
                                    jArr[i11] = Long.parseLong(strArrSplit2[i11]);
                                }
                                list.get(i2).put(str4, ExifAttribute.createULong(jArr, builder.mByteOrder));
                                break;
                            case 5:
                                String str6 = "/";
                                String[] strArrSplit3 = strReplaceAll.split(",", -1);
                                LongRational[] longRationalArr = new LongRational[strArrSplit3.length];
                                int i12 = i7;
                                while (i12 < strArrSplit3.length) {
                                    String str7 = str6;
                                    String[] strArrSplit4 = strArrSplit3[i12].split(str7, -1);
                                    longRationalArr[i12] = new LongRational((long) Double.parseDouble(strArrSplit4[i7]), (long) Double.parseDouble(strArrSplit4[1]));
                                    i12++;
                                    str6 = str7;
                                    i8 = i8;
                                    i7 = 0;
                                }
                                i2 = i8;
                                i3 = 1;
                                list.get(i2).put(str4, ExifAttribute.createURational(longRationalArr, builder.mByteOrder));
                                break;
                            case 9:
                                String[] strArrSplit5 = strReplaceAll.split(",", -1);
                                int[] iArr2 = new int[strArrSplit5.length];
                                for (int i13 = i7; i13 < strArrSplit5.length; i13++) {
                                    iArr2[i13] = Integer.parseInt(strArrSplit5[i13]);
                                }
                                list.get(i8).put(str4, ExifAttribute.createSLong(iArr2, builder.mByteOrder));
                                i2 = i8;
                                i3 = 1;
                                break;
                            case 10:
                                String[] strArrSplit6 = strReplaceAll.split(",", -1);
                                LongRational[] longRationalArr2 = new LongRational[strArrSplit6.length];
                                int i14 = i7;
                                while (i14 < strArrSplit6.length) {
                                    String[] strArrSplit7 = strArrSplit6[i14].split(str5, -1);
                                    longRationalArr2[i14] = new LongRational((long) Double.parseDouble(strArrSplit7[i7]), (long) Double.parseDouble(strArrSplit7[i6]));
                                    i14++;
                                    str5 = str5;
                                    i6 = 1;
                                }
                                builder = this;
                                list.get(i8).put(str4, ExifAttribute.createSRational(longRationalArr2, builder.mByteOrder));
                                i2 = i8;
                                i3 = 1;
                                break;
                            case 12:
                                String[] strArrSplit8 = strReplaceAll.split(",", -1);
                                double[] dArr = new double[strArrSplit8.length];
                                for (int i15 = i7; i15 < strArrSplit8.length; i15++) {
                                    dArr[i15] = Double.parseDouble(strArrSplit8[i15]);
                                }
                                list.get(i8).put(str4, ExifAttribute.createDouble(dArr, builder.mByteOrder));
                                break;
                        }
                    } else {
                        list.get(i8).remove(str4);
                    }
                    i2 = i8;
                    i3 = i6;
                }
                i8 = i2 + 1;
                i6 = i3;
                i5 = 2;
                i7 = 0;
            }
        }

        @NonNull
        public ExifData build() throws NumberFormatException {
            ArrayList list = Collections.list(new Enumeration<Map<String, ExifAttribute>>() { // from class: androidx.camera.core.impl.utils.ExifData.Builder.3
                final Enumeration<Map<String, ExifAttribute>> mMapEnumeration;

                {
                    this.mMapEnumeration = Collections.enumeration(Builder.this.mAttributes);
                }

                @Override // java.util.Enumeration
                public boolean hasMoreElements() {
                    return this.mMapEnumeration.hasMoreElements();
                }

                @Override // java.util.Enumeration
                public Map<String, ExifAttribute> nextElement() {
                    return new HashMap(this.mMapEnumeration.nextElement());
                }
            });
            if (!list.get(1).isEmpty()) {
                setAttributeIfMissing(ExifInterface.TAG_EXPOSURE_PROGRAM, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_EXIF_VERSION, "0230", list);
                setAttributeIfMissing(ExifInterface.TAG_COMPONENTS_CONFIGURATION, "1,2,3,0", list);
                setAttributeIfMissing(ExifInterface.TAG_METERING_MODE, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_LIGHT_SOURCE, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_FLASHPIX_VERSION, "0100", list);
                setAttributeIfMissing(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, String.valueOf(2), list);
                setAttributeIfMissing(ExifInterface.TAG_FILE_SOURCE, String.valueOf(3), list);
                setAttributeIfMissing(ExifInterface.TAG_SCENE_TYPE, String.valueOf(1), list);
                setAttributeIfMissing(ExifInterface.TAG_CUSTOM_RENDERED, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_SCENE_CAPTURE_TYPE, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_CONTRAST, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_SATURATION, String.valueOf(0), list);
                setAttributeIfMissing(ExifInterface.TAG_SHARPNESS, String.valueOf(0), list);
            }
            if (!list.get(2).isEmpty()) {
                setAttributeIfMissing(ExifInterface.TAG_GPS_VERSION_ID, "2300", list);
                setAttributeIfMissing(ExifInterface.TAG_GPS_SPEED_REF, "K", list);
                setAttributeIfMissing(ExifInterface.TAG_GPS_TRACK_REF, ExifInterface.GPS_DIRECTION_TRUE, list);
                setAttributeIfMissing(ExifInterface.TAG_GPS_IMG_DIRECTION_REF, ExifInterface.GPS_DIRECTION_TRUE, list);
                setAttributeIfMissing(ExifInterface.TAG_GPS_DEST_BEARING_REF, ExifInterface.GPS_DIRECTION_TRUE, list);
                setAttributeIfMissing(ExifInterface.TAG_GPS_DEST_DISTANCE_REF, "K", list);
            }
            return new ExifData(this.mByteOrder, list);
        }

        @NonNull
        public Builder removeAttribute(@NonNull String str) throws NumberFormatException {
            setAttributeInternal(str, null, this.mAttributes);
            return this;
        }

        @NonNull
        public Builder setAttribute(@NonNull String str, @NonNull String str2) throws NumberFormatException {
            setAttributeInternal(str, str2, this.mAttributes);
            return this;
        }

        @NonNull
        public Builder setExposureTimeNanos(long j2) {
            return setAttribute(ExifInterface.TAG_EXPOSURE_TIME, String.valueOf(j2 / TimeUnit.SECONDS.toNanos(1L)));
        }

        @NonNull
        public Builder setFlashState(@NonNull CameraCaptureMetaData.FlashState flashState) throws NumberFormatException {
            int i2;
            if (flashState == CameraCaptureMetaData.FlashState.UNKNOWN) {
                return this;
            }
            int i3 = AnonymousClass1.$SwitchMap$androidx$camera$core$impl$CameraCaptureMetaData$FlashState[flashState.ordinal()];
            if (i3 == 1) {
                i2 = 0;
            } else if (i3 == 2) {
                i2 = 32;
            } else {
                if (i3 != 3) {
                    Logger.w(ExifData.TAG, "Unknown flash state: " + flashState);
                    return this;
                }
                i2 = 1;
            }
            if ((i2 & 1) == 1) {
                setAttribute(ExifInterface.TAG_LIGHT_SOURCE, String.valueOf(4));
            }
            return setAttribute(ExifInterface.TAG_FLASH, String.valueOf(i2));
        }

        @NonNull
        public Builder setFocalLength(float f2) {
            return setAttribute(ExifInterface.TAG_FOCAL_LENGTH, new LongRational((long) (f2 * 1000.0f), 1000L).toString());
        }

        @NonNull
        public Builder setImageHeight(int i2) {
            return setAttribute(ExifInterface.TAG_IMAGE_LENGTH, String.valueOf(i2));
        }

        @NonNull
        public Builder setImageWidth(int i2) {
            return setAttribute(ExifInterface.TAG_IMAGE_WIDTH, String.valueOf(i2));
        }

        @NonNull
        public Builder setIso(int i2) {
            return setAttribute(ExifInterface.TAG_SENSITIVITY_TYPE, String.valueOf(3)).setAttribute(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, String.valueOf(Math.min(65535, i2)));
        }

        @NonNull
        public Builder setLensFNumber(float f2) {
            return setAttribute(ExifInterface.TAG_F_NUMBER, String.valueOf(f2));
        }

        @NonNull
        public Builder setOrientationDegrees(int i2) {
            int i3;
            if (i2 == 0) {
                i3 = 1;
            } else if (i2 == 90) {
                i3 = 6;
            } else if (i2 == 180) {
                i3 = 3;
            } else if (i2 != 270) {
                Logger.w(ExifData.TAG, "Unexpected orientation value: " + i2 + ". Must be one of 0, 90, 180, 270.");
                i3 = 0;
            } else {
                i3 = 8;
            }
            return setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(i3));
        }

        @NonNull
        public Builder setWhiteBalanceMode(@NonNull WhiteBalanceMode whiteBalanceMode) {
            int i2 = AnonymousClass1.$SwitchMap$androidx$camera$core$impl$utils$ExifData$WhiteBalanceMode[whiteBalanceMode.ordinal()];
            return setAttribute(ExifInterface.TAG_WHITE_BALANCE, i2 != 1 ? i2 != 2 ? null : String.valueOf(1) : String.valueOf(0));
        }
    }

    public enum WhiteBalanceMode {
        AUTO,
        MANUAL
    }

    static {
        ExifTag[] exifTagArr = {new ExifTag(ExifInterface.TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(ExifInterface.TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(ExifInterface.TAG_MAKE, R2.attr.adScopeStrokeWidth, 2), new ExifTag(ExifInterface.TAG_MODEL, R2.attr.adScopeTextColor, 2), new ExifTag(ExifInterface.TAG_ORIENTATION, R2.attr.adSizes, 3), new ExifTag(ExifInterface.TAG_X_RESOLUTION, R2.attr.adjustable, 5), new ExifTag(ExifInterface.TAG_Y_RESOLUTION, R2.attr.alertDialogButtonGroupStyle, 5), new ExifTag(ExifInterface.TAG_RESOLUTION_UNIT, R2.attr.all_course_search_icon, 3), new ExifTag(ExifInterface.TAG_SOFTWARE, 305, 2), new ExifTag(ExifInterface.TAG_DATETIME, 306, 2), new ExifTag(ExifInterface.TAG_Y_CB_CR_POSITIONING, R2.attr.bl_checkable_gradient_useLevel, 3), new ExifTag(TAG_SUB_IFD_POINTER, R2.attr.appSix, 4), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4)};
        IFD_TIFF_TAGS = exifTagArr;
        ExifTag[] exifTagArr2 = {new ExifTag(ExifInterface.TAG_EXPOSURE_TIME, 33434, 5), new ExifTag(ExifInterface.TAG_F_NUMBER, 33437, 5), new ExifTag(ExifInterface.TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, 3), new ExifTag(ExifInterface.TAG_SENSITIVITY_TYPE, 34864, 3), new ExifTag(ExifInterface.TAG_EXIF_VERSION, CpioConstants.C_ISNWK, 2), new ExifTag(ExifInterface.TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag(ExifInterface.TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag(ExifInterface.TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag(ExifInterface.TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag(ExifInterface.TAG_APERTURE_VALUE, 37378, 5), new ExifTag(ExifInterface.TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag(ExifInterface.TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag(ExifInterface.TAG_METERING_MODE, 37383, 3), new ExifTag(ExifInterface.TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(ExifInterface.TAG_FLASH, 37385, 3), new ExifTag(ExifInterface.TAG_FOCAL_LENGTH, 37386, 5), new ExifTag(ExifInterface.TAG_SUBSEC_TIME, 37520, 2), new ExifTag(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, 37521, 2), new ExifTag(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, 37522, 2), new ExifTag(ExifInterface.TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag(ExifInterface.TAG_COLOR_SPACE, 40961, 3), new ExifTag(ExifInterface.TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(ExifInterface.TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag(ExifInterface.TAG_SENSING_METHOD, 41495, 3), new ExifTag(ExifInterface.TAG_FILE_SOURCE, 41728, 7), new ExifTag(ExifInterface.TAG_SCENE_TYPE, 41729, 7), new ExifTag(ExifInterface.TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag(ExifInterface.TAG_EXPOSURE_MODE, 41986, 3), new ExifTag(ExifInterface.TAG_WHITE_BALANCE, 41987, 3), new ExifTag(ExifInterface.TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag(ExifInterface.TAG_CONTRAST, 41992, 3), new ExifTag(ExifInterface.TAG_SATURATION, 41993, 3), new ExifTag(ExifInterface.TAG_SHARPNESS, 41994, 3)};
        IFD_EXIF_TAGS = exifTagArr2;
        ExifTag[] exifTagArr3 = {new ExifTag(ExifInterface.TAG_GPS_VERSION_ID, 0, 1), new ExifTag(ExifInterface.TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(ExifInterface.TAG_GPS_LATITUDE, 2, 5, 10), new ExifTag(ExifInterface.TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(ExifInterface.TAG_GPS_LONGITUDE, 4, 5, 10), new ExifTag(ExifInterface.TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(ExifInterface.TAG_GPS_ALTITUDE, 6, 5), new ExifTag(ExifInterface.TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(ExifInterface.TAG_GPS_SPEED_REF, 12, 2), new ExifTag(ExifInterface.TAG_GPS_TRACK_REF, 14, 2), new ExifTag(ExifInterface.TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(ExifInterface.TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(ExifInterface.TAG_GPS_DEST_DISTANCE_REF, 25, 2)};
        IFD_GPS_TAGS = exifTagArr3;
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, R2.attr.appSix, 4), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4)};
        ExifTag[] exifTagArr4 = {new ExifTag(ExifInterface.TAG_INTEROPERABILITY_INDEX, 1, 2)};
        IFD_INTEROPERABILITY_TAGS = exifTagArr4;
        EXIF_TAGS = new ExifTag[][]{exifTagArr, exifTagArr2, exifTagArr3, exifTagArr4};
        sTagSetForCompatibility = new HashSet<>(Arrays.asList(ExifInterface.TAG_F_NUMBER, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_GPS_TIMESTAMP));
    }

    public ExifData(ByteOrder byteOrder, List<Map<String, ExifAttribute>> list) {
        Preconditions.checkState(list.size() == EXIF_TAGS.length, "Malformed attributes list. Number of IFDs mismatch.");
        this.mByteOrder = byteOrder;
        this.mAttributes = list;
    }

    @NonNull
    public static Builder builderForDevice() {
        return new Builder(ByteOrder.BIG_ENDIAN).setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(1)).setAttribute(ExifInterface.TAG_X_RESOLUTION, "72/1").setAttribute(ExifInterface.TAG_Y_RESOLUTION, "72/1").setAttribute(ExifInterface.TAG_RESOLUTION_UNIT, String.valueOf(2)).setAttribute(ExifInterface.TAG_Y_CB_CR_POSITIONING, String.valueOf(1)).setAttribute(ExifInterface.TAG_MAKE, Build.MANUFACTURER).setAttribute(ExifInterface.TAG_MODEL, Build.MODEL);
    }

    @NonNull
    public static ExifData create(@NonNull ImageProxy imageProxy, int i2) {
        Builder builderBuilderForDevice = builderForDevice();
        imageProxy.getImageInfo().populateExifData(builderBuilderForDevice);
        builderBuilderForDevice.setOrientationDegrees(i2);
        return builderBuilderForDevice.setImageWidth(imageProxy.getWidth()).setImageHeight(imageProxy.getHeight()).build();
    }

    @Nullable
    private ExifAttribute getExifAttribute(@NonNull String str) {
        if (ExifInterface.TAG_ISO_SPEED_RATINGS.equals(str)) {
            str = ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        for (int i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            ExifAttribute exifAttribute = this.mAttributes.get(i2).get(str);
            if (exifAttribute != null) {
                return exifAttribute;
            }
        }
        return null;
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                return exifAttribute.getStringValue(this.mByteOrder);
            }
            if (str.equals(ExifInterface.TAG_GPS_TIMESTAMP)) {
                int i2 = exifAttribute.format;
                if (i2 != 5 && i2 != 10) {
                    Logger.w(TAG, "GPS Timestamp format is not rational. format=" + exifAttribute.format);
                    return null;
                }
                LongRational[] longRationalArr = (LongRational[]) exifAttribute.getValue(this.mByteOrder);
                if (longRationalArr != null && longRationalArr.length == 3) {
                    return String.format(Locale.US, "%02d:%02d:%02d", Integer.valueOf((int) (longRationalArr[0].getNumerator() / longRationalArr[0].getDenominator())), Integer.valueOf((int) (longRationalArr[1].getNumerator() / longRationalArr[1].getDenominator())), Integer.valueOf((int) (longRationalArr[2].getNumerator() / longRationalArr[2].getDenominator())));
                }
                Logger.w(TAG, "Invalid GPS Timestamp array. array=" + Arrays.toString(longRationalArr));
                return null;
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    @NonNull
    public Map<String, ExifAttribute> getAttributes(int i2) {
        Preconditions.checkArgumentInRange(i2, 0, EXIF_TAGS.length, "Invalid IFD index: " + i2 + ". Index should be between [0, EXIF_TAGS.length] ");
        return this.mAttributes.get(i2);
    }

    @NonNull
    public ByteOrder getByteOrder() {
        return this.mByteOrder;
    }
}
