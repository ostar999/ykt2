package com.plv.rtc.urtc.a;

import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkCaptureMode;
import com.plv.rtc.urtc.enummeration.URTCSdkLogLevel;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaServerStatus;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkMode;
import com.plv.rtc.urtc.enummeration.URTCSdkNetWorkQuality;
import com.plv.rtc.urtc.enummeration.URTCSdkPushEncode;
import com.plv.rtc.urtc.enummeration.URTCSdkPushOrientation;
import com.plv.rtc.urtc.enummeration.URTCSdkRoomType;
import com.plv.rtc.urtc.enummeration.URTCSdkScaleType;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamRole;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamType;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.enummeration.URTCSdkVideoProfile;
import com.plv.rtc.urtc.model.URTCSdkStats;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkCaptureMode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkLogLevel;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaServiceStatus;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkNetWorkQuality;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkPushEncode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkPushOrientation;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkRoomType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkScaleType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStats;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamRole;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkTrackType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkVideoProfile;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes5.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final EnumMap<URTCSdkAudioDevice, UCloudRtcSdkAudioDevice> f10929a;

    /* renamed from: b, reason: collision with root package name */
    private static final EnumMap<URTCSdkMediaType, UCloudRtcSdkMediaType> f10930b;

    /* renamed from: c, reason: collision with root package name */
    private static final EnumMap<URTCSdkNetWorkQuality, UCloudRtcSdkNetWorkQuality> f10931c;

    /* renamed from: d, reason: collision with root package name */
    private static final EnumMap<URTCSdkRoomType, UCloudRtcSdkRoomType> f10932d;

    /* renamed from: e, reason: collision with root package name */
    private static final EnumMap<URTCSdkScaleType, UCloudRtcSdkScaleType> f10933e;

    /* renamed from: f, reason: collision with root package name */
    private static final EnumMap<URTCSdkStreamRole, UCloudRtcSdkStreamRole> f10934f;

    /* renamed from: g, reason: collision with root package name */
    private static final EnumMap<URTCSdkTrackType, UCloudRtcSdkTrackType> f10935g;

    /* renamed from: h, reason: collision with root package name */
    private static final EnumMap<URTCSdkVideoProfile, UCloudRtcSdkVideoProfile> f10936h;

    /* renamed from: i, reason: collision with root package name */
    private static final EnumMap<URTCSdkStreamType, UCloudRtcSdkStreamType> f10937i;

    /* renamed from: j, reason: collision with root package name */
    private static final EnumMap<URTCSdkLogLevel, UCloudRtcSdkLogLevel> f10938j;

    /* renamed from: k, reason: collision with root package name */
    private static final EnumMap<URTCSdkPushEncode, UCloudRtcSdkPushEncode> f10939k;

    /* renamed from: l, reason: collision with root package name */
    private static final EnumMap<URTCSdkPushOrientation, UCloudRtcSdkPushOrientation> f10940l;

    /* renamed from: m, reason: collision with root package name */
    private static final EnumMap<URTCSdkMode, UCloudRtcSdkMode> f10941m;

    /* renamed from: n, reason: collision with root package name */
    private static final EnumMap<URTCSdkCaptureMode, UCloudRtcSdkCaptureMode> f10942n;

    /* renamed from: o, reason: collision with root package name */
    private static final EnumMap<URTCSdkMediaServerStatus, UCloudRtcSdkMediaServiceStatus> f10943o;

    static {
        EnumMap<URTCSdkAudioDevice, UCloudRtcSdkAudioDevice> enumMap = new EnumMap<>(URTCSdkAudioDevice.class);
        f10929a = enumMap;
        EnumMap<URTCSdkMediaType, UCloudRtcSdkMediaType> enumMap2 = new EnumMap<>(URTCSdkMediaType.class);
        f10930b = enumMap2;
        EnumMap<URTCSdkNetWorkQuality, UCloudRtcSdkNetWorkQuality> enumMap3 = new EnumMap<>(URTCSdkNetWorkQuality.class);
        f10931c = enumMap3;
        EnumMap<URTCSdkRoomType, UCloudRtcSdkRoomType> enumMap4 = new EnumMap<>(URTCSdkRoomType.class);
        f10932d = enumMap4;
        EnumMap<URTCSdkScaleType, UCloudRtcSdkScaleType> enumMap5 = new EnumMap<>(URTCSdkScaleType.class);
        f10933e = enumMap5;
        EnumMap<URTCSdkStreamRole, UCloudRtcSdkStreamRole> enumMap6 = new EnumMap<>(URTCSdkStreamRole.class);
        f10934f = enumMap6;
        EnumMap<URTCSdkTrackType, UCloudRtcSdkTrackType> enumMap7 = new EnumMap<>(URTCSdkTrackType.class);
        f10935g = enumMap7;
        EnumMap<URTCSdkVideoProfile, UCloudRtcSdkVideoProfile> enumMap8 = new EnumMap<>(URTCSdkVideoProfile.class);
        f10936h = enumMap8;
        EnumMap<URTCSdkStreamType, UCloudRtcSdkStreamType> enumMap9 = new EnumMap<>(URTCSdkStreamType.class);
        f10937i = enumMap9;
        EnumMap<URTCSdkLogLevel, UCloudRtcSdkLogLevel> enumMap10 = new EnumMap<>(URTCSdkLogLevel.class);
        f10938j = enumMap10;
        EnumMap<URTCSdkPushEncode, UCloudRtcSdkPushEncode> enumMap11 = new EnumMap<>(URTCSdkPushEncode.class);
        f10939k = enumMap11;
        EnumMap<URTCSdkPushOrientation, UCloudRtcSdkPushOrientation> enumMap12 = new EnumMap<>(URTCSdkPushOrientation.class);
        f10940l = enumMap12;
        EnumMap<URTCSdkMode, UCloudRtcSdkMode> enumMap13 = new EnumMap<>(URTCSdkMode.class);
        f10941m = enumMap13;
        EnumMap<URTCSdkCaptureMode, UCloudRtcSdkCaptureMode> enumMap14 = new EnumMap<>(URTCSdkCaptureMode.class);
        f10942n = enumMap14;
        EnumMap<URTCSdkMediaServerStatus, UCloudRtcSdkMediaServiceStatus> enumMap15 = new EnumMap<>(URTCSdkMediaServerStatus.class);
        f10943o = enumMap15;
        a(URTCSdkAudioDevice.values(), UCloudRtcSdkAudioDevice.values(), enumMap);
        a(URTCSdkMediaType.values(), UCloudRtcSdkMediaType.values(), enumMap2);
        a(URTCSdkNetWorkQuality.values(), UCloudRtcSdkNetWorkQuality.values(), enumMap3);
        a(URTCSdkRoomType.values(), UCloudRtcSdkRoomType.values(), enumMap4);
        a(URTCSdkScaleType.values(), UCloudRtcSdkScaleType.values(), enumMap5);
        a(URTCSdkStreamRole.values(), UCloudRtcSdkStreamRole.values(), enumMap6);
        a(URTCSdkTrackType.values(), UCloudRtcSdkTrackType.values(), enumMap7);
        a(URTCSdkVideoProfile.values(), UCloudRtcSdkVideoProfile.values(), enumMap8);
        a(URTCSdkStreamType.values(), UCloudRtcSdkStreamType.values(), enumMap9);
        a(URTCSdkLogLevel.values(), UCloudRtcSdkLogLevel.values(), enumMap10);
        a(URTCSdkPushEncode.values(), UCloudRtcSdkPushEncode.values(), enumMap11);
        a(URTCSdkPushOrientation.values(), UCloudRtcSdkPushOrientation.values(), enumMap12);
        a(URTCSdkMode.values(), UCloudRtcSdkMode.values(), enumMap13);
        a(URTCSdkCaptureMode.values(), UCloudRtcSdkCaptureMode.values(), enumMap14);
        a(URTCSdkMediaServerStatus.values(), UCloudRtcSdkMediaServiceStatus.values(), enumMap15);
    }

    public static URTCSdkStreamInfo a(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        URTCSdkStreamInfo uRTCSdkStreamInfo = new URTCSdkStreamInfo();
        uRTCSdkStreamInfo.setHasAudio(uCloudRtcSdkStreamInfo.isHasAudio());
        uRTCSdkStreamInfo.setHasData(uCloudRtcSdkStreamInfo.isHasData());
        uRTCSdkStreamInfo.setHasVideo(uCloudRtcSdkStreamInfo.isHasVideo());
        uRTCSdkStreamInfo.setUid(uCloudRtcSdkStreamInfo.getUId());
        uRTCSdkStreamInfo.setMuteAudio(uCloudRtcSdkStreamInfo.isMuteAudio());
        uRTCSdkStreamInfo.setMuteVideo(uCloudRtcSdkStreamInfo.isMuteVideo());
        URTCSdkMediaType uRTCSdkMediaType = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
        Iterator it = f10930b.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkStreamInfo.getMediaType()) {
                uRTCSdkMediaType = (URTCSdkMediaType) entry.getKey();
            }
        }
        uRTCSdkStreamInfo.setMediaType(uRTCSdkMediaType);
        return uRTCSdkStreamInfo;
    }

    public static UCloudRtcSdkStreamInfo a(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
        uCloudRtcSdkStreamInfo.setHasAudio(uRTCSdkStreamInfo.isHasAudio());
        uCloudRtcSdkStreamInfo.setHasData(uRTCSdkStreamInfo.isHasData());
        uCloudRtcSdkStreamInfo.setHasVideo(uRTCSdkStreamInfo.isHasVideo());
        uCloudRtcSdkStreamInfo.setUid(uRTCSdkStreamInfo.getUId());
        uCloudRtcSdkStreamInfo.setMuteAudio(uRTCSdkStreamInfo.isMuteAudio());
        uCloudRtcSdkStreamInfo.setMuteVideo(uRTCSdkStreamInfo.isMuteVideo());
        uCloudRtcSdkStreamInfo.setMediaType(f10930b.get(uRTCSdkStreamInfo.getMediaType()));
        return uCloudRtcSdkStreamInfo;
    }

    public static URTCSdkStats a(UCloudRtcSdkStats uCloudRtcSdkStats) {
        URTCSdkStats uRTCSdkStats = new URTCSdkStats();
        uRTCSdkStats.setBitrate(uCloudRtcSdkStats.getBitrate());
        uRTCSdkStats.setDelayMs(uCloudRtcSdkStats.getDelayMs());
        uRTCSdkStats.setLostPercent(uCloudRtcSdkStats.getLostPercent());
        uRTCSdkStats.setRttMs(uCloudRtcSdkStats.getRttMs());
        uRTCSdkStats.setUId(uCloudRtcSdkStats.getUId());
        uRTCSdkStats.setMediaType(a(uCloudRtcSdkStats.getMediaType()));
        uRTCSdkStats.setTrackType(a(uCloudRtcSdkStats.getTrackType()));
        return uRTCSdkStats;
    }

    public static UCloudRtcSdkStats a(URTCSdkStats uRTCSdkStats) {
        UCloudRtcSdkStats uCloudRtcSdkStats = new UCloudRtcSdkStats();
        uCloudRtcSdkStats.setBitrate(uRTCSdkStats.getBitrate());
        uCloudRtcSdkStats.setDelayMs(uRTCSdkStats.getDelayMs());
        uCloudRtcSdkStats.setLostPercent(uRTCSdkStats.getLostPercent());
        uCloudRtcSdkStats.setRttMs(uRTCSdkStats.getRttMs());
        uCloudRtcSdkStats.setUId(uRTCSdkStats.getUId());
        uCloudRtcSdkStats.setMediaType(a(uRTCSdkStats.getMediaType()));
        uCloudRtcSdkStats.setTrackType(a(uRTCSdkStats.getTrackType()));
        return uCloudRtcSdkStats;
    }

    public static URTCSdkAudioDevice a(UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice) {
        URTCSdkAudioDevice uRTCSdkAudioDevice = URTCSdkAudioDevice.UCLOUD_RTC_SDK_AUDIODEVICE_NONE;
        Iterator it = f10929a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkAudioDevice) {
                uRTCSdkAudioDevice = (URTCSdkAudioDevice) entry.getKey();
            }
        }
        return uRTCSdkAudioDevice;
    }

    public static UCloudRtcSdkAudioDevice a(URTCSdkAudioDevice uRTCSdkAudioDevice) {
        return f10929a.get(uRTCSdkAudioDevice);
    }

    public static URTCSdkStreamRole a(UCloudRtcSdkStreamRole uCloudRtcSdkStreamRole) {
        URTCSdkStreamRole uRTCSdkStreamRole = URTCSdkStreamRole.UCLOUD_RTC_SDK_STREAM_ROLE_BOTH;
        Iterator it = f10934f.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkStreamRole) {
                uRTCSdkStreamRole = (URTCSdkStreamRole) entry.getKey();
            }
        }
        return uRTCSdkStreamRole;
    }

    public static UCloudRtcSdkStreamRole a(URTCSdkStreamRole uRTCSdkStreamRole) {
        return f10934f.get(uRTCSdkStreamRole);
    }

    public static URTCSdkRoomType a(UCloudRtcSdkRoomType uCloudRtcSdkRoomType) {
        URTCSdkRoomType uRTCSdkRoomType = URTCSdkRoomType.UCLOUD_RTC_SDK_ROOM_LARGE;
        Iterator it = f10932d.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkRoomType) {
                uRTCSdkRoomType = (URTCSdkRoomType) entry.getKey();
            }
        }
        return uRTCSdkRoomType;
    }

    public static UCloudRtcSdkRoomType a(URTCSdkRoomType uRTCSdkRoomType) {
        return f10932d.get(uRTCSdkRoomType);
    }

    public static URTCSdkVideoProfile a(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        URTCSdkVideoProfile uRTCSdkVideoProfile = URTCSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_320_180;
        Iterator it = f10936h.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkVideoProfile) {
                uRTCSdkVideoProfile = (URTCSdkVideoProfile) entry.getKey();
            }
        }
        return uRTCSdkVideoProfile;
    }

    public static UCloudRtcSdkVideoProfile a(URTCSdkVideoProfile uRTCSdkVideoProfile) {
        return f10936h.get(uRTCSdkVideoProfile);
    }

    public static URTCSdkMediaType a(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        URTCSdkMediaType uRTCSdkMediaType = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
        Iterator it = f10930b.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkMediaType) {
                uRTCSdkMediaType = (URTCSdkMediaType) entry.getKey();
            }
        }
        return uRTCSdkMediaType;
    }

    public static UCloudRtcSdkMediaType a(URTCSdkMediaType uRTCSdkMediaType) {
        return f10930b.get(uRTCSdkMediaType);
    }

    public static URTCSdkScaleType a(UCloudRtcSdkScaleType uCloudRtcSdkScaleType) {
        URTCSdkScaleType uRTCSdkScaleType = URTCSdkScaleType.UCLOUD_RTC_SDK_SCALE_ASPECT_FILL;
        Iterator it = f10933e.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkScaleType) {
                uRTCSdkScaleType = (URTCSdkScaleType) entry.getKey();
            }
        }
        return uRTCSdkScaleType;
    }

    public static UCloudRtcSdkScaleType a(URTCSdkScaleType uRTCSdkScaleType) {
        return f10933e.get(uRTCSdkScaleType);
    }

    public static URTCSdkTrackType a(UCloudRtcSdkTrackType uCloudRtcSdkTrackType) {
        URTCSdkTrackType uRTCSdkTrackType = URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_NULL;
        Iterator it = f10935g.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkTrackType) {
                uRTCSdkTrackType = (URTCSdkTrackType) entry.getKey();
            }
        }
        return uRTCSdkTrackType;
    }

    public static UCloudRtcSdkTrackType a(URTCSdkTrackType uRTCSdkTrackType) {
        return f10935g.get(uRTCSdkTrackType);
    }

    public static URTCSdkStreamType a(UCloudRtcSdkStreamType uCloudRtcSdkStreamType) {
        URTCSdkStreamType uRTCSdkStreamType = URTCSdkStreamType.UCLOUD_RTC_SDK_STREAM_TYPE_NULL;
        Iterator it = f10937i.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkStreamType) {
                uRTCSdkStreamType = (URTCSdkStreamType) entry.getKey();
            }
        }
        return uRTCSdkStreamType;
    }

    public static UCloudRtcSdkStreamType a(URTCSdkStreamType uRTCSdkStreamType) {
        return f10937i.get(uRTCSdkStreamType);
    }

    public static URTCSdkNetWorkQuality a(UCloudRtcSdkNetWorkQuality uCloudRtcSdkNetWorkQuality) {
        URTCSdkNetWorkQuality uRTCSdkNetWorkQuality = URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_BAD;
        Iterator it = f10931c.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkNetWorkQuality) {
                uRTCSdkNetWorkQuality = (URTCSdkNetWorkQuality) entry.getKey();
            }
        }
        return uRTCSdkNetWorkQuality;
    }

    public static UCloudRtcSdkNetWorkQuality a(URTCSdkNetWorkQuality uRTCSdkNetWorkQuality) {
        return f10931c.get(uRTCSdkNetWorkQuality);
    }

    public static UCloudRtcSdkLogLevel a(URTCSdkLogLevel uRTCSdkLogLevel) {
        return f10938j.get(uRTCSdkLogLevel);
    }

    public static UCloudRtcSdkPushEncode a(URTCSdkPushEncode uRTCSdkPushEncode) {
        return f10939k.get(uRTCSdkPushEncode);
    }

    public static UCloudRtcSdkPushOrientation a(URTCSdkPushOrientation uRTCSdkPushOrientation) {
        return f10940l.get(uRTCSdkPushOrientation);
    }

    public static UCloudRtcSdkMode a(URTCSdkMode uRTCSdkMode) {
        return f10941m.get(uRTCSdkMode);
    }

    public static UCloudRtcSdkCaptureMode a(URTCSdkCaptureMode uRTCSdkCaptureMode) {
        return f10942n.get(uRTCSdkCaptureMode);
    }

    public static URTCSdkMediaServerStatus a(UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus) {
        URTCSdkMediaServerStatus uRTCSdkMediaServerStatus = URTCSdkMediaServerStatus.RECORD_STATUS_ERROR;
        Iterator it = f10943o.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == uCloudRtcSdkMediaServiceStatus) {
                uRTCSdkMediaServerStatus = (URTCSdkMediaServerStatus) entry.getKey();
            }
        }
        return uRTCSdkMediaServerStatus;
    }

    public static UCloudRtcSdkMediaServiceStatus a(URTCSdkMediaServerStatus uRTCSdkMediaServerStatus) {
        return f10943o.get(uRTCSdkMediaServerStatus);
    }

    private static <K extends Enum<K>, V extends Enum<V>> void a(Enum<K>[] enumArr, Enum<V>[] enumArr2, EnumMap enumMap) {
        for (Enum<K> r3 : enumArr) {
            for (Enum<V> r6 : enumArr2) {
                if (r3.toString().equals(r6.toString())) {
                    enumMap.put((EnumMap) r3, (Enum<K>) r6);
                }
            }
        }
    }
}
