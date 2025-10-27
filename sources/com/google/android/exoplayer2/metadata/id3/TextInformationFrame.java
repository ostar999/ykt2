package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class TextInformationFrame extends Id3Frame {
    public static final Parcelable.Creator<TextInformationFrame> CREATOR = new Parcelable.Creator<TextInformationFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.TextInformationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextInformationFrame createFromParcel(Parcel parcel) {
            return new TextInformationFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextInformationFrame[] newArray(int i2) {
            return new TextInformationFrame[i2];
        }
    };

    @Nullable
    public final String description;
    public final String value;

    public TextInformationFrame(String str, @Nullable String str2, String str3) {
        super(str);
        this.description = str2;
        this.value = str3;
    }

    private static List<Integer> parseId3v2point4TimestampFrameForDate(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            if (str.length() >= 10) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(5, 7))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(8, 10))));
            } else if (str.length() >= 7) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(5, 7))));
            } else if (str.length() >= 4) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
            }
            return arrayList;
        } catch (NumberFormatException unused) {
            return new ArrayList();
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TextInformationFrame.class != obj.getClass()) {
            return false;
        }
        TextInformationFrame textInformationFrame = (TextInformationFrame) obj;
        return Util.areEqual(this.id, textInformationFrame.id) && Util.areEqual(this.description, textInformationFrame.description) && Util.areEqual(this.value, textInformationFrame.value);
    }

    public int hashCode() {
        int iHashCode = (R2.attr.bl_checkable_gradient_endColor + this.id.hashCode()) * 31;
        String str = this.description;
        int iHashCode2 = (iHashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.value;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame, com.google.android.exoplayer2.metadata.Metadata.Entry
    public void populateMediaMetadata(MediaMetadata.Builder builder) throws NumberFormatException {
        String str = this.id;
        str.hashCode();
        char c3 = 65535;
        switch (str.hashCode()) {
            case 82815:
                if (str.equals("TAL")) {
                    c3 = 0;
                    break;
                }
                break;
            case 82878:
                if (str.equals("TCM")) {
                    c3 = 1;
                    break;
                }
                break;
            case 82897:
                if (str.equals("TDA")) {
                    c3 = 2;
                    break;
                }
                break;
            case 83253:
                if (str.equals("TP1")) {
                    c3 = 3;
                    break;
                }
                break;
            case 83254:
                if (str.equals("TP2")) {
                    c3 = 4;
                    break;
                }
                break;
            case 83255:
                if (str.equals("TP3")) {
                    c3 = 5;
                    break;
                }
                break;
            case 83341:
                if (str.equals("TRK")) {
                    c3 = 6;
                    break;
                }
                break;
            case 83378:
                if (str.equals("TT2")) {
                    c3 = 7;
                    break;
                }
                break;
            case 83536:
                if (str.equals("TXT")) {
                    c3 = '\b';
                    break;
                }
                break;
            case 83552:
                if (str.equals("TYE")) {
                    c3 = '\t';
                    break;
                }
                break;
            case 2567331:
                if (str.equals("TALB")) {
                    c3 = '\n';
                    break;
                }
                break;
            case 2569357:
                if (str.equals("TCOM")) {
                    c3 = 11;
                    break;
                }
                break;
            case 2569891:
                if (str.equals("TDAT")) {
                    c3 = '\f';
                    break;
                }
                break;
            case 2570401:
                if (str.equals("TDRC")) {
                    c3 = '\r';
                    break;
                }
                break;
            case 2570410:
                if (str.equals("TDRL")) {
                    c3 = 14;
                    break;
                }
                break;
            case 2571565:
                if (str.equals("TEXT")) {
                    c3 = 15;
                    break;
                }
                break;
            case 2575251:
                if (str.equals("TIT2")) {
                    c3 = 16;
                    break;
                }
                break;
            case 2581512:
                if (str.equals("TPE1")) {
                    c3 = 17;
                    break;
                }
                break;
            case 2581513:
                if (str.equals("TPE2")) {
                    c3 = 18;
                    break;
                }
                break;
            case 2581514:
                if (str.equals("TPE3")) {
                    c3 = 19;
                    break;
                }
                break;
            case 2583398:
                if (str.equals("TRCK")) {
                    c3 = 20;
                    break;
                }
                break;
            case 2590194:
                if (str.equals("TYER")) {
                    c3 = 21;
                    break;
                }
                break;
        }
        try {
            switch (c3) {
                case 0:
                case '\n':
                    builder.setAlbumTitle(this.value);
                    break;
                case 1:
                case 11:
                    builder.setComposer(this.value);
                    break;
                case 2:
                case '\f':
                    builder.setRecordingMonth(Integer.valueOf(Integer.parseInt(this.value.substring(2, 4)))).setRecordingDay(Integer.valueOf(Integer.parseInt(this.value.substring(0, 2))));
                    break;
                case 3:
                case 17:
                    builder.setArtist(this.value);
                    break;
                case 4:
                case 18:
                    builder.setAlbumArtist(this.value);
                    break;
                case 5:
                case 19:
                    builder.setConductor(this.value);
                    break;
                case 6:
                case 20:
                    String[] strArrSplit = Util.split(this.value, "/");
                    builder.setTrackNumber(Integer.valueOf(Integer.parseInt(strArrSplit[0]))).setTotalTrackCount(strArrSplit.length > 1 ? Integer.valueOf(Integer.parseInt(strArrSplit[1])) : null);
                    break;
                case 7:
                case 16:
                    builder.setTitle(this.value);
                    break;
                case '\b':
                case 15:
                    builder.setWriter(this.value);
                    break;
                case '\t':
                case 21:
                    builder.setRecordingYear(Integer.valueOf(Integer.parseInt(this.value)));
                    break;
                case '\r':
                    List<Integer> id3v2point4TimestampFrameForDate = parseId3v2point4TimestampFrameForDate(this.value);
                    int size = id3v2point4TimestampFrameForDate.size();
                    if (size != 1) {
                        if (size != 2) {
                            if (size == 3) {
                                builder.setRecordingDay(id3v2point4TimestampFrameForDate.get(2));
                            }
                        }
                        builder.setRecordingMonth(id3v2point4TimestampFrameForDate.get(1));
                    }
                    builder.setRecordingYear(id3v2point4TimestampFrameForDate.get(0));
                    break;
                case 14:
                    List<Integer> id3v2point4TimestampFrameForDate2 = parseId3v2point4TimestampFrameForDate(this.value);
                    int size2 = id3v2point4TimestampFrameForDate2.size();
                    if (size2 != 1) {
                        if (size2 != 2) {
                            if (size2 == 3) {
                                builder.setReleaseDay(id3v2point4TimestampFrameForDate2.get(2));
                            }
                        }
                        builder.setReleaseMonth(id3v2point4TimestampFrameForDate2.get(1));
                    }
                    builder.setReleaseYear(id3v2point4TimestampFrameForDate2.get(0));
                    break;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException unused) {
        }
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame
    public String toString() {
        String str = this.id;
        String str2 = this.description;
        String str3 = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(": description=");
        sb.append(str2);
        sb.append(": value=");
        sb.append(str3);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.id);
        parcel.writeString(this.description);
        parcel.writeString(this.value);
    }

    public TextInformationFrame(Parcel parcel) {
        super((String) Util.castNonNull(parcel.readString()));
        this.description = parcel.readString();
        this.value = (String) Util.castNonNull(parcel.readString());
    }
}
