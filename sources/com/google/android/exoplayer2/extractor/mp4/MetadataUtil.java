package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes3.dex */
final class MetadataUtil {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int SHORT_TYPE_ALBUM = 6384738;
    private static final int SHORT_TYPE_ARTIST = 4280916;
    private static final int SHORT_TYPE_COMMENT = 6516084;
    private static final int SHORT_TYPE_COMPOSER_1 = 6516589;
    private static final int SHORT_TYPE_COMPOSER_2 = 7828084;
    private static final int SHORT_TYPE_ENCODER = 7630703;
    private static final int SHORT_TYPE_GENRE = 6776174;
    private static final int SHORT_TYPE_LYRICS = 7108978;
    private static final int SHORT_TYPE_NAME_1 = 7233901;
    private static final int SHORT_TYPE_NAME_2 = 7631467;
    private static final int SHORT_TYPE_YEAR = 6578553;

    @VisibleForTesting
    static final String[] STANDARD_GENRES = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio theatre", "Neue Deutsche Welle", "Podcast", "Indie-Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = 1631670868;
    private static final int TYPE_COMPILATION = 1668311404;
    private static final int TYPE_COVER_ART = 1668249202;
    private static final int TYPE_DISK_NUMBER = 1684632427;
    private static final int TYPE_GAPLESS_ALBUM = 1885823344;
    private static final int TYPE_GENRE = 1735291493;
    private static final int TYPE_GROUPING = 6779504;
    private static final int TYPE_INTERNAL = 757935405;
    private static final int TYPE_RATING = 1920233063;
    private static final int TYPE_SORT_ALBUM = 1936679276;
    private static final int TYPE_SORT_ALBUM_ARTIST = 1936679265;
    private static final int TYPE_SORT_ARTIST = 1936679282;
    private static final int TYPE_SORT_COMPOSER = 1936679791;
    private static final int TYPE_SORT_TRACK_NAME = 1936682605;
    private static final int TYPE_TEMPO = 1953329263;
    private static final int TYPE_TOP_BYTE_COPYRIGHT = 169;
    private static final int TYPE_TOP_BYTE_REPLACEMENT = 253;
    private static final int TYPE_TRACK_NUMBER = 1953655662;
    private static final int TYPE_TV_SHOW = 1953919848;
    private static final int TYPE_TV_SORT_SHOW = 1936683886;

    private MetadataUtil() {
    }

    @Nullable
    private static CommentFrame parseCommentAttribute(int i2, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            String nullTerminatedString = parsableByteArray.readNullTerminatedString(i3 - 16);
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, nullTerminatedString, nullTerminatedString);
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(i2));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse comment attribute: ".concat(strValueOf) : new String("Failed to parse comment attribute: "));
        return null;
    }

    @Nullable
    private static ApicFrame parseCoverArt(ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() != 1684108385) {
            Log.w(TAG, "Failed to parse cover art attribute");
            return null;
        }
        int fullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        String str = fullAtomFlags == 13 ? "image/jpeg" : fullAtomFlags == 14 ? "image/png" : null;
        if (str == null) {
            StringBuilder sb = new StringBuilder(41);
            sb.append("Unrecognized cover art flags: ");
            sb.append(fullAtomFlags);
            Log.w(TAG, sb.toString());
            return null;
        }
        parsableByteArray.skipBytes(4);
        int i3 = i2 - 16;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        return new ApicFrame(str, null, 3, bArr);
    }

    @Nullable
    public static Metadata.Entry parseIlstElement(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition() + parsableByteArray.readInt();
        int i2 = parsableByteArray.readInt();
        int i3 = (i2 >> 24) & 255;
        try {
            if (i3 == 169 || i3 == 253) {
                int i4 = 16777215 & i2;
                if (i4 == SHORT_TYPE_COMMENT) {
                    return parseCommentAttribute(i2, parsableByteArray);
                }
                if (i4 == SHORT_TYPE_NAME_1 || i4 == SHORT_TYPE_NAME_2) {
                    return parseTextAttribute(i2, "TIT2", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_COMPOSER_1 || i4 == SHORT_TYPE_COMPOSER_2) {
                    return parseTextAttribute(i2, "TCOM", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_YEAR) {
                    return parseTextAttribute(i2, "TDRC", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ARTIST) {
                    return parseTextAttribute(i2, "TPE1", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ENCODER) {
                    return parseTextAttribute(i2, "TSSE", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ALBUM) {
                    return parseTextAttribute(i2, "TALB", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_LYRICS) {
                    return parseTextAttribute(i2, "USLT", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_GENRE) {
                    return parseTextAttribute(i2, "TCON", parsableByteArray);
                }
                if (i4 == TYPE_GROUPING) {
                    return parseTextAttribute(i2, "TIT1", parsableByteArray);
                }
            } else {
                if (i2 == TYPE_GENRE) {
                    return parseStandardGenreAttribute(parsableByteArray);
                }
                if (i2 == TYPE_DISK_NUMBER) {
                    return parseIndexAndCountAttribute(i2, "TPOS", parsableByteArray);
                }
                if (i2 == TYPE_TRACK_NUMBER) {
                    return parseIndexAndCountAttribute(i2, "TRCK", parsableByteArray);
                }
                if (i2 == TYPE_TEMPO) {
                    return parseUint8Attribute(i2, "TBPM", parsableByteArray, true, false);
                }
                if (i2 == TYPE_COMPILATION) {
                    return parseUint8Attribute(i2, "TCMP", parsableByteArray, true, true);
                }
                if (i2 == TYPE_COVER_ART) {
                    return parseCoverArt(parsableByteArray);
                }
                if (i2 == TYPE_ALBUM_ARTIST) {
                    return parseTextAttribute(i2, "TPE2", parsableByteArray);
                }
                if (i2 == TYPE_SORT_TRACK_NAME) {
                    return parseTextAttribute(i2, "TSOT", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ALBUM) {
                    return parseTextAttribute(i2, "TSO2", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ARTIST) {
                    return parseTextAttribute(i2, "TSOA", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ALBUM_ARTIST) {
                    return parseTextAttribute(i2, "TSOP", parsableByteArray);
                }
                if (i2 == TYPE_SORT_COMPOSER) {
                    return parseTextAttribute(i2, "TSOC", parsableByteArray);
                }
                if (i2 == TYPE_RATING) {
                    return parseUint8Attribute(i2, "ITUNESADVISORY", parsableByteArray, false, false);
                }
                if (i2 == TYPE_GAPLESS_ALBUM) {
                    return parseUint8Attribute(i2, "ITUNESGAPLESS", parsableByteArray, false, true);
                }
                if (i2 == TYPE_TV_SORT_SHOW) {
                    return parseTextAttribute(i2, "TVSHOWSORT", parsableByteArray);
                }
                if (i2 == TYPE_TV_SHOW) {
                    return parseTextAttribute(i2, "TVSHOW", parsableByteArray);
                }
                if (i2 == TYPE_INTERNAL) {
                    return parseInternalAttribute(parsableByteArray, position);
                }
            }
            String strValueOf = String.valueOf(Atom.getAtomTypeString(i2));
            Log.d(TAG, strValueOf.length() != 0 ? "Skipped unknown metadata entry: ".concat(strValueOf) : new String("Skipped unknown metadata entry: "));
            parsableByteArray.setPosition(position);
            return null;
        } finally {
            parsableByteArray.setPosition(position);
        }
    }

    @Nullable
    private static TextInformationFrame parseIndexAndCountAttribute(int i2, String str, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385 && i3 >= 22) {
            parsableByteArray.skipBytes(10);
            int unsignedShort = parsableByteArray.readUnsignedShort();
            if (unsignedShort > 0) {
                StringBuilder sb = new StringBuilder(11);
                sb.append(unsignedShort);
                String string = sb.toString();
                int unsignedShort2 = parsableByteArray.readUnsignedShort();
                if (unsignedShort2 > 0) {
                    String strValueOf = String.valueOf(string);
                    StringBuilder sb2 = new StringBuilder(strValueOf.length() + 12);
                    sb2.append(strValueOf);
                    sb2.append("/");
                    sb2.append(unsignedShort2);
                    string = sb2.toString();
                }
                return new TextInformationFrame(str, null, string);
            }
        }
        String strValueOf2 = String.valueOf(Atom.getAtomTypeString(i2));
        Log.w(TAG, strValueOf2.length() != 0 ? "Failed to parse index/count attribute: ".concat(strValueOf2) : new String("Failed to parse index/count attribute: "));
        return null;
    }

    @Nullable
    private static Id3Frame parseInternalAttribute(ParsableByteArray parsableByteArray, int i2) {
        String nullTerminatedString = null;
        String nullTerminatedString2 = null;
        int i3 = -1;
        int i4 = -1;
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i5 = parsableByteArray.readInt();
            int i6 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            if (i6 == 1835360622) {
                nullTerminatedString = parsableByteArray.readNullTerminatedString(i5 - 12);
            } else if (i6 == 1851878757) {
                nullTerminatedString2 = parsableByteArray.readNullTerminatedString(i5 - 12);
            } else {
                if (i6 == 1684108385) {
                    i3 = position;
                    i4 = i5;
                }
                parsableByteArray.skipBytes(i5 - 12);
            }
        }
        if (nullTerminatedString == null || nullTerminatedString2 == null || i3 == -1) {
            return null;
        }
        parsableByteArray.setPosition(i3);
        parsableByteArray.skipBytes(16);
        return new InternalFrame(nullTerminatedString, nullTerminatedString2, parsableByteArray.readNullTerminatedString(i4 - 16));
    }

    @Nullable
    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray parsableByteArray, int i2, String str) {
        while (true) {
            int position = parsableByteArray.getPosition();
            if (position >= i2) {
                return null;
            }
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1684108385) {
                int i4 = parsableByteArray.readInt();
                int i5 = parsableByteArray.readInt();
                int i6 = i3 - 16;
                byte[] bArr = new byte[i6];
                parsableByteArray.readBytes(bArr, 0, i6);
                return new MdtaMetadataEntry(str, bArr, i5, i4);
            }
            parsableByteArray.setPosition(position + i3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.metadata.id3.TextInformationFrame parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray r3) {
        /*
            int r3 = parseUint8AttributeValue(r3)
            r0 = 0
            if (r3 <= 0) goto L11
            java.lang.String[] r1 = com.google.android.exoplayer2.extractor.mp4.MetadataUtil.STANDARD_GENRES
            int r2 = r1.length
            if (r3 > r2) goto L11
            int r3 = r3 + (-1)
            r3 = r1[r3]
            goto L12
        L11:
            r3 = r0
        L12:
            if (r3 == 0) goto L1c
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = new com.google.android.exoplayer2.metadata.id3.TextInformationFrame
            java.lang.String r2 = "TCON"
            r1.<init>(r2, r0, r3)
            return r1
        L1c:
            java.lang.String r3 = "MetadataUtil"
            java.lang.String r1 = "Failed to parse standard genre code"
            com.google.android.exoplayer2.util.Log.w(r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.MetadataUtil.parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray):com.google.android.exoplayer2.metadata.id3.TextInformationFrame");
    }

    @Nullable
    private static TextInformationFrame parseTextAttribute(int i2, String str, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, null, parsableByteArray.readNullTerminatedString(i3 - 16));
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(i2));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse text attribute: ".concat(strValueOf) : new String("Failed to parse text attribute: "));
        return null;
    }

    @Nullable
    private static Id3Frame parseUint8Attribute(int i2, String str, ParsableByteArray parsableByteArray, boolean z2, boolean z3) {
        int uint8AttributeValue = parseUint8AttributeValue(parsableByteArray);
        if (z3) {
            uint8AttributeValue = Math.min(1, uint8AttributeValue);
        }
        if (uint8AttributeValue >= 0) {
            return z2 ? new TextInformationFrame(str, null, Integer.toString(uint8AttributeValue)) : new CommentFrame(C.LANGUAGE_UNDETERMINED, str, Integer.toString(uint8AttributeValue));
        }
        String strValueOf = String.valueOf(Atom.getAtomTypeString(i2));
        Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse uint8 attribute: ".concat(strValueOf) : new String("Failed to parse uint8 attribute: "));
        return null;
    }

    private static int parseUint8AttributeValue(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return parsableByteArray.readUnsignedByte();
        }
        Log.w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }

    public static void setFormatGaplessInfo(int i2, GaplessInfoHolder gaplessInfoHolder, Format.Builder builder) {
        if (i2 == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            builder.setEncoderDelay(gaplessInfoHolder.encoderDelay).setEncoderPadding(gaplessInfoHolder.encoderPadding);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setFormatMetadata(int r5, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.Metadata r6, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.Metadata r7, com.google.android.exoplayer2.Format.Builder r8, com.google.android.exoplayer2.metadata.Metadata... r9) {
        /*
            com.google.android.exoplayer2.metadata.Metadata r0 = new com.google.android.exoplayer2.metadata.Metadata
            r1 = 0
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r2 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r1]
            r0.<init>(r2)
            r2 = 1
            if (r5 != r2) goto Le
            if (r6 == 0) goto L3c
            goto L3d
        Le:
            r6 = 2
            if (r5 != r6) goto L3c
            if (r7 == 0) goto L3c
            r5 = r1
        L14:
            int r6 = r7.length()
            if (r5 >= r6) goto L3c
            com.google.android.exoplayer2.metadata.Metadata$Entry r6 = r7.get(r5)
            boolean r3 = r6 instanceof com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry
            if (r3 == 0) goto L39
            com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry r6 = (com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry) r6
            java.lang.String r3 = r6.key
            java.lang.String r4 = "com.android.capture.fps"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L39
            com.google.android.exoplayer2.metadata.Metadata r5 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r7 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r2]
            r7[r1] = r6
            r5.<init>(r7)
            r6 = r5
            goto L3d
        L39:
            int r5 = r5 + 1
            goto L14
        L3c:
            r6 = r0
        L3d:
            int r5 = r9.length
        L3e:
            if (r1 >= r5) goto L49
            r7 = r9[r1]
            com.google.android.exoplayer2.metadata.Metadata r6 = r6.copyWithAppendedEntriesFrom(r7)
            int r1 = r1 + 1
            goto L3e
        L49:
            int r5 = r6.length()
            if (r5 <= 0) goto L52
            r8.setMetadata(r6)
        L52:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.MetadataUtil.setFormatMetadata(int, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.Format$Builder, com.google.android.exoplayer2.metadata.Metadata[]):void");
    }
}
