package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.auth.core.AliyunVodKey;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final int[] MPEG_CHANNEL_CONFIGURATION_MAPPING = {-1, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 8, 24, 8, 12, 10, 12, 14, 12, 14};

    public static final class RepresentationInfo {
        public final ImmutableList<BaseUrl> baseUrls;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;

        @Nullable
        public final String drmSchemeType;
        public final List<Descriptor> essentialProperties;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;
        public final List<Descriptor> supplementalProperties;

        public RepresentationInfo(Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable String str, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2, List<Descriptor> list2, List<Descriptor> list3, long j2) {
            this.format = format;
            this.baseUrls = ImmutableList.copyOf((Collection) list);
            this.segmentBase = segmentBase;
            this.drmSchemeType = str;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.essentialProperties = list2;
            this.supplementalProperties = list3;
            this.revisionId = j2;
        }
    }

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    private long addSegmentTimelineElementsToList(List<SegmentBase.SegmentTimelineElement> list, long j2, long j3, int i2, long j4) {
        int iCeilDivide = i2 >= 0 ? i2 + 1 : (int) Util.ceilDivide(j4 - j2, j3);
        for (int i3 = 0; i3 < iCeilDivide; i3++) {
            list.add(buildSegmentTimelineElement(j2, j3));
            j2 += j3;
        }
        return j2;
    }

    private static int checkContentTypeConsistency(int i2, int i3) {
        if (i2 == -1) {
            return i3;
        }
        if (i3 == -1) {
            return i2;
        }
        Assertions.checkState(i2 == i3);
        return i2;
    }

    @Nullable
    private static String checkLanguageConsistency(@Nullable String str, @Nullable String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = arrayList.get(size);
            if (!schemeData.hasData()) {
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (arrayList.get(i2).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    }
                    i2++;
                }
            }
        }
    }

    private static long getFinalAvailabilityTimeOffset(long j2, long j3) {
        if (j3 != C.TIME_UNSET) {
            j2 = j3;
        }
        return j2 == Long.MAX_VALUE ? C.TIME_UNSET : j2;
    }

    @Nullable
    private static String getSampleMimeType(@Nullable String str, @Nullable String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (MimeTypes.isText(str)) {
            return MimeTypes.APPLICATION_RAWCC.equals(str) ? MimeTypes.getTextMediaMimeType(str2) : str;
        }
        if (MimeTypes.isImage(str)) {
            return str;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(str)) {
            return null;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str2);
        return MimeTypes.TEXT_VTT.equals(mediaMimeType) ? MimeTypes.APPLICATION_MP4VTT : mediaMimeType;
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i2 = 1;
            while (i2 != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i2++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i2--;
                }
            }
        }
    }

    public static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        String str;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && (str = descriptor.value) != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(str);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String strValueOf = String.valueOf(descriptor.value);
                Log.w(TAG, strValueOf.length() != 0 ? "Unable to parse CEA-608 channel number from: ".concat(strValueOf) : new String("Unable to parse CEA-608 channel number from: "));
            }
        }
        return -1;
    }

    public static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        String str;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && (str = descriptor.value) != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(str);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String strValueOf = String.valueOf(descriptor.value);
                Log.w(TAG, strValueOf.length() != 0 ? "Unable to parse CEA-708 service block number from: ".concat(strValueOf) : new String("Unable to parse CEA-708 service block number from: "));
            }
        }
        return -1;
    }

    public static long parseDateTime(XmlPullParser xmlPullParser, String str, long j2) throws ParserException {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Util.parseXsDateTime(attributeValue);
    }

    public static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String string = parseString(xmlPullParser, "schemeIdUri", "");
        String string2 = parseString(xmlPullParser, "value", null);
        String string3 = parseString(xmlPullParser, "id", null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(string, string2, string3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser r4) {
        /*
            r0 = 0
            java.lang.String r1 = "value"
            java.lang.String r4 = r4.getAttributeValue(r0, r1)
            r0 = -1
            if (r4 != 0) goto Lb
            return r0
        Lb:
            java.lang.String r4 = com.google.common.base.Ascii.toLowerCase(r4)
            r4.hashCode()
            int r1 = r4.hashCode()
            r2 = 2
            r3 = 1
            switch(r1) {
                case 1596796: goto L3e;
                case 2937391: goto L33;
                case 3094035: goto L28;
                case 3133436: goto L1d;
                default: goto L1b;
            }
        L1b:
            r4 = r0
            goto L48
        L1d:
            java.lang.String r1 = "fa01"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L26
            goto L1b
        L26:
            r4 = 3
            goto L48
        L28:
            java.lang.String r1 = "f801"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L31
            goto L1b
        L31:
            r4 = r2
            goto L48
        L33:
            java.lang.String r1 = "a000"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L3c
            goto L1b
        L3c:
            r4 = r3
            goto L48
        L3e:
            java.lang.String r1 = "4000"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L47
            goto L1b
        L47:
            r4 = 0
        L48:
            switch(r4) {
                case 0: goto L52;
                case 1: goto L51;
                case 2: goto L4f;
                case 3: goto L4c;
                default: goto L4b;
            }
        L4b:
            return r0
        L4c:
            r4 = 8
            return r4
        L4f:
            r4 = 6
            return r4
        L51:
            return r2
        L52:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    public static long parseDuration(XmlPullParser xmlPullParser, String str, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Util.parseXsDuration(attributeValue);
    }

    public static String parseEac3SupplementalProperties(List<Descriptor> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            String str = descriptor.schemeIdUri;
            if ("tag:dolby.com,2018:dash:EC3_ExtensionType:2018".equals(str) && "JOC".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(str) && MimeTypes.CODEC_E_AC3_JOC.equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    public static float parseFloat(XmlPullParser xmlPullParser, String str, float f2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? f2 : Float.parseFloat(attributeValue);
    }

    public static float parseFrameRate(XmlPullParser xmlPullParser, float f2) throws NumberFormatException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f2;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f2;
        }
        int i2 = Integer.parseInt(matcher.group(1));
        return !TextUtils.isEmpty(matcher.group(2)) ? i2 / Integer.parseInt(r2) : i2;
    }

    public static int parseInt(XmlPullParser xmlPullParser, String str, int i2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i2 : Integer.parseInt(attributeValue);
    }

    public static long parseLastSegmentNumberSupplementalProperty(List<Descriptor> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/last-segment-number", descriptor.schemeIdUri)) {
                return Long.parseLong(descriptor.value);
            }
        }
        return -1L;
    }

    public static long parseLong(XmlPullParser xmlPullParser, String str, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Long.parseLong(attributeValue);
    }

    public static int parseMpegChannelConfiguration(XmlPullParser xmlPullParser) {
        int i2 = parseInt(xmlPullParser, "value", -1);
        if (i2 < 0) {
            return -1;
        }
        int[] iArr = MPEG_CHANNEL_CONFIGURATION_MAPPING;
        if (i2 < iArr.length) {
            return iArr[i2];
        }
        return -1;
    }

    public static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    public static String parseText(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String text = "";
        do {
            xmlPullParser.next();
            if (xmlPullParser.getEventType() == 4) {
                text = xmlPullParser.getText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return text;
    }

    public AdaptationSet buildAdaptationSet(int i2, int i3, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4) {
        return new AdaptationSet(i2, i3, list, list2, list3, list4);
    }

    public EventMessage buildEvent(String str, String str2, long j2, long j3, byte[] bArr) {
        return new EventMessage(str, str2, j3, j2, bArr);
    }

    public EventStream buildEventStream(String str, String str2, long j2, long[] jArr, EventMessage[] eventMessageArr) {
        return new EventStream(str, str2, j2, jArr, eventMessageArr);
    }

    public Format buildFormat(@Nullable String str, @Nullable String str2, int i2, int i3, float f2, int i4, int i5, int i6, @Nullable String str3, List<Descriptor> list, List<Descriptor> list2, @Nullable String str4, List<Descriptor> list3, List<Descriptor> list4) {
        String str5 = str4;
        String sampleMimeType = getSampleMimeType(str2, str5);
        if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType)) {
            sampleMimeType = parseEac3SupplementalProperties(list4);
            if (MimeTypes.AUDIO_E_AC3_JOC.equals(sampleMimeType)) {
                str5 = MimeTypes.CODEC_E_AC3_JOC;
            }
        }
        Format.Builder language = new Format.Builder().setId(str).setContainerMimeType(str2).setSampleMimeType(sampleMimeType).setCodecs(str5).setPeakBitrate(i6).setSelectionFlags(parseSelectionFlagsFromRoleDescriptors(list)).setRoleFlags(parseRoleFlagsFromRoleDescriptors(list) | parseRoleFlagsFromAccessibilityDescriptors(list2) | parseRoleFlagsFromProperties(list3) | parseRoleFlagsFromProperties(list4)).setLanguage(str3);
        if (MimeTypes.isVideo(sampleMimeType)) {
            language.setWidth(i2).setHeight(i3).setFrameRate(f2);
        } else if (MimeTypes.isAudio(sampleMimeType)) {
            language.setChannelCount(i4).setSampleRate(i5);
        } else if (MimeTypes.isText(sampleMimeType)) {
            language.setAccessibilityChannel(MimeTypes.APPLICATION_CEA608.equals(sampleMimeType) ? parseCea608AccessibilityChannel(list2) : MimeTypes.APPLICATION_CEA708.equals(sampleMimeType) ? parseCea708AccessibilityChannel(list2) : -1);
        } else if (MimeTypes.isImage(sampleMimeType)) {
            language.setWidth(i2).setHeight(i3);
        }
        return language.build();
    }

    public DashManifest buildMediaPresentationDescription(long j2, long j3, long j4, boolean z2, long j5, long j6, long j7, long j8, @Nullable ProgramInformation programInformation, @Nullable UtcTimingElement utcTimingElement, @Nullable ServiceDescriptionElement serviceDescriptionElement, @Nullable Uri uri, List<Period> list) {
        return new DashManifest(j2, j3, j4, z2, j5, j6, j7, j8, programInformation, utcTimingElement, serviceDescriptionElement, uri, list);
    }

    public Period buildPeriod(@Nullable String str, long j2, List<AdaptationSet> list, List<EventStream> list2, @Nullable Descriptor descriptor) {
        return new Period(str, j2, list, list2, descriptor);
    }

    public RangedUri buildRangedUri(String str, long j2, long j3) {
        return new RangedUri(str, j2, j3);
    }

    public Representation buildRepresentation(RepresentationInfo representationInfo, @Nullable String str, @Nullable String str2, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format.Builder builderBuildUpon = representationInfo.format.buildUpon();
        if (str != null) {
            builderBuildUpon.setLabel(str);
        }
        String str3 = representationInfo.drmSchemeType;
        if (str3 != null) {
            str2 = str3;
        }
        ArrayList<DrmInitData.SchemeData> arrayList3 = representationInfo.drmSchemeDatas;
        arrayList3.addAll(arrayList);
        if (!arrayList3.isEmpty()) {
            filterRedundantIncompleteSchemeDatas(arrayList3);
            builderBuildUpon.setDrmInitData(new DrmInitData(str2, arrayList3));
        }
        ArrayList<Descriptor> arrayList4 = representationInfo.inbandEventStreams;
        arrayList4.addAll(arrayList2);
        return Representation.newInstance(representationInfo.revisionId, builderBuildUpon.build(), representationInfo.baseUrls, representationInfo.segmentBase, arrayList4, representationInfo.essentialProperties, representationInfo.supplementalProperties, null);
    }

    public SegmentBase.SegmentList buildSegmentList(RangedUri rangedUri, long j2, long j3, long j4, long j5, @Nullable List<SegmentBase.SegmentTimelineElement> list, long j6, @Nullable List<RangedUri> list2, long j7, long j8) {
        return new SegmentBase.SegmentList(rangedUri, j2, j3, j4, j5, list, j6, list2, Util.msToUs(j7), Util.msToUs(j8));
    }

    public SegmentBase.SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j2, long j3, long j4, long j5, long j6, List<SegmentBase.SegmentTimelineElement> list, long j7, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j8, long j9) {
        return new SegmentBase.SegmentTemplate(rangedUri, j2, j3, j4, j5, j6, list, j7, urlTemplate, urlTemplate2, Util.msToUs(j8), Util.msToUs(j9));
    }

    public SegmentBase.SegmentTimelineElement buildSegmentTimelineElement(long j2, long j3) {
        return new SegmentBase.SegmentTimelineElement(j2, j3);
    }

    public SegmentBase.SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j2, long j3, long j4, long j5) {
        return new SegmentBase.SingleSegmentBase(rangedUri, j2, j3, j4, j5);
    }

    public UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x031a A[LOOP:0: B:3:0x007c->B:74:0x031a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02da A[EDGE_INSN: B:75:0x02da->B:68:0x02da BREAK  A[LOOP:0: B:3:0x007c->B:74:0x031a], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.source.dash.manifest.AdaptationSet parseAdaptationSet(org.xmlpull.v1.XmlPullParser r54, java.util.List<com.google.android.exoplayer2.source.dash.manifest.BaseUrl> r55, @androidx.annotation.Nullable com.google.android.exoplayer2.source.dash.manifest.SegmentBase r56, long r57, long r59, long r61, long r63, long r65) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 820
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAdaptationSet(org.xmlpull.v1.XmlPullParser, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long, long, long, long, long):com.google.android.exoplayer2.source.dash.manifest.AdaptationSet");
    }

    public void parseAdaptationSetChild(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        maybeSkipTag(xmlPullParser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int parseAudioChannelConfiguration(org.xmlpull.v1.XmlPullParser r4) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r3 = this;
            java.lang.String r0 = "schemeIdUri"
            r1 = 0
            java.lang.String r0 = parseString(r4, r0, r1)
            r0.hashCode()
            int r1 = r0.hashCode()
            r2 = -1
            switch(r1) {
                case -1352850286: goto L35;
                case -1138141449: goto L2a;
                case -986633423: goto L1f;
                case 2036691300: goto L14;
                default: goto L12;
            }
        L12:
            r0 = r2
            goto L3f
        L14:
            java.lang.String r1 = "urn:dolby:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L1d
            goto L12
        L1d:
            r0 = 3
            goto L3f
        L1f:
            java.lang.String r1 = "urn:mpeg:mpegB:cicp:ChannelConfiguration"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L28
            goto L12
        L28:
            r0 = 2
            goto L3f
        L2a:
            java.lang.String r1 = "tag:dolby.com,2014:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L33
            goto L12
        L33:
            r0 = 1
            goto L3f
        L35:
            java.lang.String r1 = "urn:mpeg:dash:23003:3:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L3e
            goto L12
        L3e:
            r0 = 0
        L3f:
            switch(r0) {
                case 0: goto L4d;
                case 1: goto L48;
                case 2: goto L43;
                case 3: goto L48;
                default: goto L42;
            }
        L42:
            goto L53
        L43:
            int r2 = parseMpegChannelConfiguration(r4)
            goto L53
        L48:
            int r2 = parseDolbyChannelConfiguration(r4)
            goto L53
        L4d:
            java.lang.String r0 = "value"
            int r2 = parseInt(r4, r0, r2)
        L53:
            r4.next()
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r4, r0)
            if (r0 == 0) goto L53
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAudioChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    public long parseAvailabilityTimeOffsetUs(XmlPullParser xmlPullParser, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "availabilityTimeOffset");
        if (attributeValue == null) {
            return j2;
        }
        if ("INF".equals(attributeValue)) {
            return Long.MAX_VALUE;
        }
        return (long) (Float.parseFloat(attributeValue) * 1000000.0f);
    }

    public List<BaseUrl> parseBaseUrl(XmlPullParser xmlPullParser, List<BaseUrl> list) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "dvb:priority");
        int i2 = attributeValue != null ? Integer.parseInt(attributeValue) : 1;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "dvb:weight");
        int i3 = attributeValue2 != null ? Integer.parseInt(attributeValue2) : 1;
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "serviceLocation");
        String text = parseText(xmlPullParser, "BaseURL");
        if (attributeValue3 == null) {
            attributeValue3 = text;
        }
        if (UriUtil.isAbsolute(text)) {
            return Lists.newArrayList(new BaseUrl(text, attributeValue3, i2, i3));
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < list.size(); i4++) {
            BaseUrl baseUrl = list.get(i4);
            arrayList.add(new BaseUrl(UriUtil.resolve(baseUrl.url, text), baseUrl.serviceLocation, baseUrl.priority, baseUrl.weight));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ea  */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.util.UUID] */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.util.UUID] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.util.UUID] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v19, types: [java.util.UUID] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v10, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.util.UUID] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    public int parseContentType(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if ("audio".equals(attributeValue)) {
            return 1;
        }
        if ("video".equals(attributeValue)) {
            return 2;
        }
        return "text".equals(attributeValue) ? 3 : -1;
    }

    public Pair<Long, EventMessage> parseEvent(XmlPullParser xmlPullParser, String str, String str2, long j2, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        long j3 = parseLong(xmlPullParser, "id", 0L);
        long j4 = parseLong(xmlPullParser, "duration", C.TIME_UNSET);
        long j5 = parseLong(xmlPullParser, "presentationTime", 0L);
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(j4, 1000L, j2);
        long jScaleLargeTimestamp2 = Util.scaleLargeTimestamp(j5, 1000000L, j2);
        String string = parseString(xmlPullParser, "messageData", null);
        byte[] eventObject = parseEventObject(xmlPullParser, byteArrayOutputStream);
        Long lValueOf = Long.valueOf(jScaleLargeTimestamp2);
        if (string != null) {
            eventObject = Util.getUtf8Bytes(string);
        }
        return Pair.create(lValueOf, buildEvent(str, str2, j3, jScaleLargeTimestamp, eventObject));
    }

    public byte[] parseEventObject(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        byteArrayOutputStream.reset();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        xmlSerializerNewSerializer.setOutput(byteArrayOutputStream, Charsets.UTF_8.name());
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            switch (xmlPullParser.getEventType()) {
                case 0:
                    xmlSerializerNewSerializer.startDocument(null, Boolean.FALSE);
                    break;
                case 1:
                    xmlSerializerNewSerializer.endDocument();
                    break;
                case 2:
                    xmlSerializerNewSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
                        xmlSerializerNewSerializer.attribute(xmlPullParser.getAttributeNamespace(i2), xmlPullParser.getAttributeName(i2), xmlPullParser.getAttributeValue(i2));
                    }
                    break;
                case 3:
                    xmlSerializerNewSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    xmlSerializerNewSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    xmlSerializerNewSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    xmlSerializerNewSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    xmlSerializerNewSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    xmlSerializerNewSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    xmlSerializerNewSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    xmlSerializerNewSerializer.docdecl(xmlPullParser.getText());
                    break;
            }
            xmlPullParser.nextToken();
        }
        xmlSerializerNewSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public EventStream parseEventStream(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String string = parseString(xmlPullParser, "schemeIdUri", "");
        String string2 = parseString(xmlPullParser, "value", "");
        long j2 = parseLong(xmlPullParser, "timescale", 1L);
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xmlPullParser, string, string2, j2, byteArrayOutputStream));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Pair pair = (Pair) arrayList.get(i2);
            jArr[i2] = ((Long) pair.first).longValue();
            eventMessageArr[i2] = (EventMessage) pair.second;
        }
        return buildEventStream(string, string2, j2, jArr, eventMessageArr);
    }

    public RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", SessionDescription.ATTR_RANGE);
    }

    public String parseLabel(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return parseText(xmlPullParser, "Label");
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x01b9 A[LOOP:0: B:20:0x0082->B:76:0x01b9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0175 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r44, com.google.android.exoplayer2.source.dash.manifest.BaseUrl r45) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.source.dash.manifest.BaseUrl):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    public Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, List<BaseUrl> list, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException, NumberFormatException {
        long j6;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Object obj;
        long j7;
        SegmentBase segmentTemplate;
        DashManifestParser dashManifestParser = this;
        Object obj2 = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        long duration = parseDuration(xmlPullParser, "start", j2);
        long j8 = C.TIME_UNSET;
        long j9 = j4 != C.TIME_UNSET ? j4 + duration : -9223372036854775807L;
        long duration2 = parseDuration(xmlPullParser, "duration", C.TIME_UNSET);
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        long availabilityTimeOffsetUs = j3;
        boolean z2 = false;
        long j10 = -9223372036854775807L;
        SegmentBase segmentBase = null;
        Descriptor descriptor = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (!z2) {
                    availabilityTimeOffsetUs = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser, availabilityTimeOffsetUs);
                    z2 = true;
                }
                arrayList6.addAll(parseBaseUrl(xmlPullParser, list));
                arrayList3 = arrayList5;
                arrayList = arrayList6;
                j7 = j8;
                obj = obj2;
                arrayList2 = arrayList4;
            } else {
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "AdaptationSet")) {
                    j6 = availabilityTimeOffsetUs;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    arrayList2.add(parseAdaptationSet(xmlPullParser, !arrayList6.isEmpty() ? arrayList6 : list, segmentBase, duration2, availabilityTimeOffsetUs, j10, j9, j5));
                    arrayList3 = arrayList5;
                } else {
                    j6 = availabilityTimeOffsetUs;
                    ArrayList arrayList7 = arrayList5;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "EventStream")) {
                        arrayList7.add(parseEventStream(xmlPullParser));
                        arrayList3 = arrayList7;
                    } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                        arrayList3 = arrayList7;
                        segmentBase = parseSegmentBase(xmlPullParser, null);
                        obj = null;
                        availabilityTimeOffsetUs = j6;
                        j7 = C.TIME_UNSET;
                    } else {
                        arrayList3 = arrayList7;
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                            long availabilityTimeOffsetUs2 = parseAvailabilityTimeOffsetUs(xmlPullParser, C.TIME_UNSET);
                            obj = null;
                            segmentTemplate = parseSegmentList(xmlPullParser, null, j9, duration2, j6, availabilityTimeOffsetUs2, j5);
                            j10 = availabilityTimeOffsetUs2;
                            availabilityTimeOffsetUs = j6;
                            j7 = C.TIME_UNSET;
                        } else {
                            obj = null;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                                long availabilityTimeOffsetUs3 = parseAvailabilityTimeOffsetUs(xmlPullParser, C.TIME_UNSET);
                                j7 = -9223372036854775807L;
                                segmentTemplate = parseSegmentTemplate(xmlPullParser, null, ImmutableList.of(), j9, duration2, j6, availabilityTimeOffsetUs3, j5);
                                j10 = availabilityTimeOffsetUs3;
                                availabilityTimeOffsetUs = j6;
                            } else {
                                j7 = C.TIME_UNSET;
                                if (XmlPullParserUtil.isStartTag(xmlPullParser, "AssetIdentifier")) {
                                    descriptor = parseDescriptor(xmlPullParser, "AssetIdentifier");
                                } else {
                                    maybeSkipTag(xmlPullParser);
                                }
                                availabilityTimeOffsetUs = j6;
                            }
                        }
                        segmentBase = segmentTemplate;
                    }
                }
                obj = null;
                j7 = C.TIME_UNSET;
                availabilityTimeOffsetUs = j6;
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "Period")) {
                return Pair.create(buildPeriod(attributeValue, duration, arrayList2, arrayList3, descriptor), Long.valueOf(duration2));
            }
            arrayList4 = arrayList2;
            arrayList6 = arrayList;
            obj2 = obj;
            arrayList5 = arrayList3;
            j8 = j7;
            dashManifestParser = this;
        }
    }

    public ProgramInformation parseProgramInformation(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String strNextText = null;
        String string = parseString(xmlPullParser, "moreInformationURL", null);
        String string2 = parseString(xmlPullParser, "lang", null);
        String strNextText2 = null;
        String strNextText3 = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, AliyunVodKey.KEY_VOD_TITLE)) {
                strNextText = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "Source")) {
                strNextText2 = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.TAG_COPYRIGHT)) {
                strNextText3 = xmlPullParser.nextText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
            String str = strNextText3;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ProgramInformation")) {
                return new ProgramInformation(strNextText, strNextText2, str, string, string2);
            }
            strNextText3 = str;
        }
    }

    public RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) throws NumberFormatException {
        long j2;
        long j3;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] strArrSplit = attributeValue2.split("-");
            j2 = Long.parseLong(strArrSplit[0]);
            if (strArrSplit.length == 2) {
                j3 = (Long.parseLong(strArrSplit[1]) - j2) + 1;
            }
            return buildRangedUri(attributeValue, j2, j3);
        }
        j2 = 0;
        j3 = -1;
        return buildRangedUri(attributeValue, j2, j3);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01e6 A[LOOP:0: B:3:0x006a->B:57:0x01e6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0190 A[EDGE_INSN: B:58:0x0190->B:47:0x0190 BREAK  A[LOOP:0: B:3:0x006a->B:57:0x01e6], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo parseRepresentation(org.xmlpull.v1.XmlPullParser r36, java.util.List<com.google.android.exoplayer2.source.dash.manifest.BaseUrl> r37, @androidx.annotation.Nullable java.lang.String r38, @androidx.annotation.Nullable java.lang.String r39, int r40, int r41, float r42, int r43, int r44, @androidx.annotation.Nullable java.lang.String r45, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r46, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r47, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r48, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r49, @androidx.annotation.Nullable com.google.android.exoplayer2.source.dash.manifest.SegmentBase r50, long r51, long r53, long r55, long r57, long r59) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 501
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseRepresentation(org.xmlpull.v1.XmlPullParser, java.util.List, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, java.util.List, java.util.List, java.util.List, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long, long, long, long, long):com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    public int parseRoleFlagsFromAccessibilityDescriptors(List<Descriptor> list) {
        int tvaAudioPurposeCsValue;
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            Descriptor descriptor = list.get(i3);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                tvaAudioPurposeCsValue = parseRoleFlagsFromDashRoleScheme(descriptor.value);
            } else if (Ascii.equalsIgnoreCase("urn:tva:metadata:cs:AudioPurposeCS:2007", descriptor.schemeIdUri)) {
                tvaAudioPurposeCsValue = parseTvaAudioPurposeCsValue(descriptor.value);
            }
            i2 |= tvaAudioPurposeCsValue;
        }
        return i2;
    }

    public int parseRoleFlagsFromDashRoleScheme(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
        }
        return 0;
    }

    public int parseRoleFlagsFromProperties(List<Descriptor> list) {
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/trickmode", list.get(i3).schemeIdUri)) {
                i2 |= 16384;
            }
        }
        return i2;
    }

    public int parseRoleFlagsFromRoleDescriptors(List<Descriptor> list) {
        int roleFlagsFromDashRoleScheme = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                roleFlagsFromDashRoleScheme |= parseRoleFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return roleFlagsFromDashRoleScheme;
    }

    public SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, @Nullable SegmentBase.SingleSegmentBase singleSegmentBase) throws XmlPullParserException, NumberFormatException, IOException {
        long j2;
        long j3;
        long j4 = parseLong(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.timescale : 1L);
        long j5 = parseLong(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.presentationTimeOffset : 0L);
        long j6 = singleSegmentBase != null ? singleSegmentBase.indexStart : 0L;
        long j7 = singleSegmentBase != null ? singleSegmentBase.indexLength : 0L;
        String attributeValue = xmlPullParser.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] strArrSplit = attributeValue.split("-");
            j3 = Long.parseLong(strArrSplit[0]);
            j2 = (Long.parseLong(strArrSplit[1]) - j3) + 1;
        } else {
            j2 = j7;
            j3 = j6;
        }
        RangedUri initialization = singleSegmentBase != null ? singleSegmentBase.initialization : null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization = parseInitialization(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase"));
        return buildSingleSegmentBase(initialization, j4, j5, j3, j2);
    }

    public SegmentBase.SegmentList parseSegmentList(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentList segmentList, long j2, long j3, long j4, long j5, long j6) throws XmlPullParserException, IOException {
        long j7 = parseLong(xmlPullParser, "timescale", segmentList != null ? segmentList.timescale : 1L);
        long j8 = parseLong(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.presentationTimeOffset : 0L);
        long j9 = parseLong(xmlPullParser, "duration", segmentList != null ? segmentList.duration : C.TIME_UNSET);
        long j10 = parseLong(xmlPullParser, "startNumber", segmentList != null ? segmentList.startNumber : 1L);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j4, j5);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        List<RangedUri> arrayList = null;
        RangedUri initialization = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xmlPullParser, j7, j3);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(parseSegmentUrl(xmlPullParser));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList != null) {
            if (initialization == null) {
                initialization = segmentList.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = segmentList.segmentTimeline;
            }
            if (arrayList == null) {
                arrayList = segmentList.mediaSegments;
            }
        }
        return buildSegmentList(initialization, j7, j8, j10, j9, segmentTimeline, finalAvailabilityTimeOffset, arrayList, j6, j2);
    }

    public SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentTemplate segmentTemplate, List<Descriptor> list, long j2, long j3, long j4, long j5, long j6) throws XmlPullParserException, IOException {
        long j7 = parseLong(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.timescale : 1L);
        long j8 = parseLong(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.presentationTimeOffset : 0L);
        long j9 = parseLong(xmlPullParser, "duration", segmentTemplate != null ? segmentTemplate.duration : C.TIME_UNSET);
        long j10 = parseLong(xmlPullParser, "startNumber", segmentTemplate != null ? segmentTemplate.startNumber : 1L);
        long lastSegmentNumberSupplementalProperty = parseLastSegmentNumberSupplementalProperty(list);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j4, j5);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        UrlTemplate urlTemplate = parseUrlTemplate(xmlPullParser, "media", segmentTemplate != null ? segmentTemplate.mediaTemplate : null);
        UrlTemplate urlTemplate2 = parseUrlTemplate(xmlPullParser, "initialization", segmentTemplate != null ? segmentTemplate.initializationTemplate : null);
        RangedUri initialization = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xmlPullParser, j7, j3);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate"));
        if (segmentTemplate != null) {
            if (initialization == null) {
                initialization = segmentTemplate.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = segmentTemplate.segmentTimeline;
            }
        }
        return buildSegmentTemplate(initialization, j7, j8, j10, lastSegmentNumberSupplementalProperty, j9, segmentTimeline, finalAvailabilityTimeOffset, urlTemplate2, urlTemplate, j6, j2);
    }

    public List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser, long j2, long j3) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long jAddSegmentTimelineElementsToList = 0;
        long j4 = -9223372036854775807L;
        boolean z2 = false;
        int i2 = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.LATITUDE_SOUTH)) {
                long j5 = parseLong(xmlPullParser, "t", C.TIME_UNSET);
                if (z2) {
                    jAddSegmentTimelineElementsToList = addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j4, i2, j5);
                }
                if (j5 == C.TIME_UNSET) {
                    j5 = jAddSegmentTimelineElementsToList;
                }
                j4 = parseLong(xmlPullParser, "d", C.TIME_UNSET);
                i2 = parseInt(xmlPullParser, "r", 0);
                z2 = true;
                jAddSegmentTimelineElementsToList = j5;
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        if (z2) {
            addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j4, i2, Util.scaleLargeTimestamp(j3, j2, 1000L));
        }
        return arrayList;
    }

    public RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "media", "mediaRange");
    }

    public int parseSelectionFlagsFromDashRoleScheme(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        if (str.equals("forced_subtitle")) {
            return 2;
        }
        return !str.equals("main") ? 0 : 1;
    }

    public int parseSelectionFlagsFromRoleDescriptors(List<Descriptor> list) {
        int selectionFlagsFromDashRoleScheme = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                selectionFlagsFromDashRoleScheme |= parseSelectionFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return selectionFlagsFromDashRoleScheme;
    }

    public ServiceDescriptionElement parseServiceDescription(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        long j2 = -9223372036854775807L;
        long j3 = -9223372036854775807L;
        long j4 = -9223372036854775807L;
        float f2 = -3.4028235E38f;
        float f3 = -3.4028235E38f;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Latency")) {
                j2 = parseLong(xmlPullParser, TypedValues.AttributesType.S_TARGET, C.TIME_UNSET);
                j3 = parseLong(xmlPullParser, "min", C.TIME_UNSET);
                j4 = parseLong(xmlPullParser, "max", C.TIME_UNSET);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "PlaybackRate")) {
                f2 = parseFloat(xmlPullParser, "min", -3.4028235E38f);
                f3 = parseFloat(xmlPullParser, "max", -3.4028235E38f);
            }
            long j5 = j2;
            long j6 = j3;
            long j7 = j4;
            float f4 = f2;
            float f5 = f3;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ServiceDescription")) {
                return new ServiceDescriptionElement(j5, j6, j7, f4, f5);
            }
            j2 = j5;
            j3 = j6;
            j4 = j7;
            f2 = f4;
            f3 = f5;
        }
    }

    public int parseTvaAudioPurposeCsValue(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
        }
        return 0;
    }

    @Nullable
    public UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, @Nullable UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    public UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, "value"));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public DashManifest parse(Uri uri, InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            if (xmlPullParserNewPullParser.next() == 2 && "MPD".equals(xmlPullParserNewPullParser.getName())) {
                return parseMediaPresentationDescription(xmlPullParserNewPullParser, new BaseUrl(uri.toString()));
            }
            throw ParserException.createForMalformedManifest("inputStream does not contain a valid media presentation description", null);
        } catch (XmlPullParserException e2) {
            throw ParserException.createForMalformedManifest(null, e2);
        }
    }
}
