package com.google.android.exoplayer2.ui;

import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.util.Locale;

/* loaded from: classes3.dex */
public class DefaultTrackNameProvider implements TrackNameProvider {
    private final Resources resources;

    public DefaultTrackNameProvider(Resources resources) {
        this.resources = (Resources) Assertions.checkNotNull(resources);
    }

    private String buildAudioChannelString(Format format) {
        int i2 = format.channelCount;
        return (i2 == -1 || i2 < 1) ? "" : i2 != 1 ? i2 != 2 ? (i2 == 6 || i2 == 7) ? this.resources.getString(R.string.exo_track_surround_5_point_1) : i2 != 8 ? this.resources.getString(R.string.exo_track_surround) : this.resources.getString(R.string.exo_track_surround_7_point_1) : this.resources.getString(R.string.exo_track_stereo) : this.resources.getString(R.string.exo_track_mono);
    }

    private String buildBitrateString(Format format) {
        int i2 = format.bitrate;
        return i2 == -1 ? "" : this.resources.getString(R.string.exo_track_bitrate, Float.valueOf(i2 / 1000000.0f));
    }

    private String buildLabelString(Format format) {
        return TextUtils.isEmpty(format.label) ? "" : format.label;
    }

    private String buildLanguageOrLabelString(Format format) {
        String strJoinWithSeparator = joinWithSeparator(buildLanguageString(format), buildRoleString(format));
        return TextUtils.isEmpty(strJoinWithSeparator) ? buildLabelString(format) : strJoinWithSeparator;
    }

    private String buildLanguageString(Format format) {
        String str = format.language;
        if (TextUtils.isEmpty(str) || C.LANGUAGE_UNDETERMINED.equals(str)) {
            return "";
        }
        Locale localeForLanguageTag = Util.SDK_INT >= 21 ? Locale.forLanguageTag(str) : new Locale(str);
        Locale defaultDisplayLocale = Util.getDefaultDisplayLocale();
        String displayName = localeForLanguageTag.getDisplayName(defaultDisplayLocale);
        if (TextUtils.isEmpty(displayName)) {
            return "";
        }
        try {
            int iOffsetByCodePoints = displayName.offsetByCodePoints(0, 1);
            String strValueOf = String.valueOf(displayName.substring(0, iOffsetByCodePoints).toUpperCase(defaultDisplayLocale));
            String strValueOf2 = String.valueOf(displayName.substring(iOffsetByCodePoints));
            displayName = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        } catch (IndexOutOfBoundsException unused) {
        }
        return displayName;
    }

    private String buildResolutionString(Format format) {
        int i2 = format.width;
        int i3 = format.height;
        return (i2 == -1 || i3 == -1) ? "" : this.resources.getString(R.string.exo_track_resolution, Integer.valueOf(i2), Integer.valueOf(i3));
    }

    private String buildRoleString(Format format) {
        String string = (format.roleFlags & 2) != 0 ? this.resources.getString(R.string.exo_track_role_alternate) : "";
        if ((format.roleFlags & 4) != 0) {
            string = joinWithSeparator(string, this.resources.getString(R.string.exo_track_role_supplementary));
        }
        if ((format.roleFlags & 8) != 0) {
            string = joinWithSeparator(string, this.resources.getString(R.string.exo_track_role_commentary));
        }
        return (format.roleFlags & R2.attr.columnOrderPreserved) != 0 ? joinWithSeparator(string, this.resources.getString(R.string.exo_track_role_closed_captions)) : string;
    }

    private static int inferPrimaryTrackType(Format format) {
        int trackType = MimeTypes.getTrackType(format.sampleMimeType);
        if (trackType != -1) {
            return trackType;
        }
        if (MimeTypes.getVideoMediaMimeType(format.codecs) != null) {
            return 2;
        }
        if (MimeTypes.getAudioMediaMimeType(format.codecs) != null) {
            return 1;
        }
        if (format.width == -1 && format.height == -1) {
            return (format.channelCount == -1 && format.sampleRate == -1) ? -1 : 1;
        }
        return 2;
    }

    private String joinWithSeparator(String... strArr) {
        String string = "";
        for (String str : strArr) {
            if (str.length() > 0) {
                string = TextUtils.isEmpty(string) ? str : this.resources.getString(R.string.exo_item_list, string, str);
            }
        }
        return string;
    }

    @Override // com.google.android.exoplayer2.ui.TrackNameProvider
    public String getTrackName(Format format) {
        int iInferPrimaryTrackType = inferPrimaryTrackType(format);
        String strJoinWithSeparator = iInferPrimaryTrackType == 2 ? joinWithSeparator(buildRoleString(format), buildResolutionString(format), buildBitrateString(format)) : iInferPrimaryTrackType == 1 ? joinWithSeparator(buildLanguageOrLabelString(format), buildAudioChannelString(format), buildBitrateString(format)) : buildLanguageOrLabelString(format);
        return strJoinWithSeparator.length() == 0 ? this.resources.getString(R.string.exo_track_unknown) : strJoinWithSeparator;
    }
}
