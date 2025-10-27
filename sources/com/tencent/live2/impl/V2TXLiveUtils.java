package com.tencent.live2.impl;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.enums.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.impl.a;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class V2TXLiveUtils {
    private static final String TAG = "V2TXLiveUtils";
    public static final String TRTC_ADDRESS1 = "room://cloud.tencent.com/rtc";
    public static final String TRTC_ADDRESS2 = "room://rtc.tencent.com";

    /* renamed from: com.tencent.live2.impl.V2TXLiveUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f20398a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f20399b;

        /* renamed from: c, reason: collision with root package name */
        static final /* synthetic */ int[] f20400c;

        /* renamed from: d, reason: collision with root package name */
        static final /* synthetic */ int[] f20401d;

        static {
            int[] iArr = new int[V2TXLiveDef.V2TXLivePlayStatus.values().length];
            f20401d = iArr;
            try {
                iArr[V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f20401d[V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f20401d[V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[V2TXLiveDef.V2TXLiveVideoResolution.values().length];
            f20400c = iArr2;
            try {
                iArr2[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution160x160.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution270x270.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution480x480.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution320x240.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution480x360.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution640x480.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution320x180.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution480x270.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution640x360.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution960x540.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720.ordinal()] = 11;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f20400c[V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1920x1080.ordinal()] = 12;
            } catch (NoSuchFieldError unused15) {
            }
            int[] iArr3 = new int[V2TXLiveDef.V2TXLiveFillMode.values().length];
            f20399b = iArr3;
            try {
                iArr3[V2TXLiveDef.V2TXLiveFillMode.V2TXLiveFillModeFill.ordinal()] = 1;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f20399b[V2TXLiveDef.V2TXLiveFillMode.V2TXLiveFillModeFit.ordinal()] = 2;
            } catch (NoSuchFieldError unused17) {
            }
            int[] iArr4 = new int[V2TXLiveDef.V2TXLiveRotation.values().length];
            f20398a = iArr4;
            try {
                iArr4[V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation270.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f20398a[V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation180.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f20398a[V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation90.ordinal()] = 3;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f20398a[V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation0.ordinal()] = 4;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f20402a;

        /* renamed from: b, reason: collision with root package name */
        public int f20403b;

        public a(int i2, int i3) {
            this.f20402a = i2;
            this.f20403b = i3;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public int f20404a;

        /* renamed from: b, reason: collision with root package name */
        public int f20405b;

        public b(int i2, int i3) {
            this.f20404a = i2;
            this.f20405b = i3;
        }
    }

    public static c convertResolution(int i2) {
        if (i2 == 0) {
            return c.RESOLUTION_TYPE_360_640;
        }
        if (i2 == 1) {
            return c.RESOLUTION_TYPE_540_960;
        }
        if (i2 == 2) {
            return c.RESOLUTION_TYPE_720_1280;
        }
        if (i2 == 3) {
            return c.RESOLUTION_TYPE_640_360;
        }
        if (i2 == 4) {
            return c.RESOLUTION_TYPE_960_540;
        }
        if (i2 == 5) {
            return c.RESOLUTION_TYPE_1280_720;
        }
        if (i2 == 30) {
            return c.RESOLUTION_TYPE_1080_1920;
        }
        if (i2 == 31) {
            return c.RESOLUTION_TYPE_1920_1080;
        }
        switch (i2) {
            case 7:
                return c.RESOLUTION_TYPE_180_320;
            case 8:
                return c.RESOLUTION_TYPE_270_480;
            case 9:
                return c.RESOLUTION_TYPE_320_180;
            case 10:
                return c.RESOLUTION_TYPE_480_270;
            case 11:
                return c.RESOLUTION_TYPE_240_320;
            case 12:
                return c.RESOLUTION_TYPE_360_480;
            case 13:
                return c.RESOLUTION_TYPE_480_640;
            case 14:
                return c.RESOLUTION_TYPE_320_240;
            case 15:
                return c.RESOLUTION_TYPE_480_360;
            case 16:
                return c.RESOLUTION_TYPE_640_480;
            case 17:
                return c.RESOLUTION_TYPE_480_480;
            case 18:
                return c.RESOLUTION_TYPE_270_270;
            case 19:
                return c.RESOLUTION_TYPE_160_160;
            default:
                return c.RESOLUTION_TYPE_540_960;
        }
    }

    public static a getBitrateByResolution(V2TXLiveDef.V2TXLiveVideoResolution v2TXLiveVideoResolution) {
        int i2 = 900;
        int i3 = 600;
        switch (AnonymousClass1.f20400c[v2TXLiveVideoResolution.ordinal()]) {
            case 1:
                i3 = 100;
                i2 = 150;
                break;
            case 2:
                i3 = 200;
                i2 = 300;
                break;
            case 3:
                i2 = R2.attr.bl_checkable_gradient_centerX;
                i3 = 350;
                break;
            case 4:
                i2 = R2.attr.arcThumbRadiusEnlarges;
                i3 = 250;
                break;
            case 5:
                i2 = 600;
                i3 = 400;
                break;
            case 6:
                break;
            case 7:
                i2 = 400;
                i3 = 250;
                break;
            case 8:
                i2 = R2.attr.bl_corners_bottomRadius;
                i3 = 350;
                break;
            case 9:
                i3 = 500;
                break;
            case 10:
            default:
                i3 = 800;
                i2 = 1500;
                break;
            case 11:
                i3 = 1000;
                i2 = R2.attr.ic_knowledge_chart_data;
                break;
            case 12:
                i3 = 2500;
                i2 = 3000;
                break;
        }
        return new a(i3, i2);
    }

    public static int getFinalResolution(int i2, boolean z2, boolean z3) {
        int i3 = 1;
        if (z3) {
            z2 = true;
        }
        if (i2 == 0) {
            i3 = z2 ? 0 : 3;
        } else if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                i3 = 7;
                if (i2 != 7) {
                    i3 = 8;
                    if (i2 != 8) {
                        i3 = 30;
                        if (i2 != 30) {
                            switch (i2) {
                                case 11:
                                    if (!z2) {
                                        i3 = 14;
                                        break;
                                    } else {
                                        i3 = 11;
                                        break;
                                    }
                                case 12:
                                    if (!z2) {
                                        i3 = 15;
                                        break;
                                    } else {
                                        i3 = 12;
                                        break;
                                    }
                                case 13:
                                    if (!z2) {
                                        i3 = 16;
                                        break;
                                    } else {
                                        i3 = 13;
                                        break;
                                    }
                                default:
                                    switch (i2) {
                                        case 17:
                                        case 18:
                                        case 19:
                                            i3 = i2;
                                            break;
                                        default:
                                            i3 = -1;
                                            break;
                                    }
                            }
                        } else if (!z2) {
                            i3 = 31;
                        }
                    } else if (!z2) {
                        i3 = 10;
                    }
                } else if (!z2) {
                    i3 = 9;
                }
            } else if (!z2) {
                i3 = 5;
            }
        } else if (!z2) {
            i3 = 4;
        }
        if (i3 == -1) {
            TXCLog.e(TAG, "getFinalResolution: seriously error!!! can't map resolution, use original resolution.");
            i3 = i2;
        }
        TXCLog.i(TAG, "getFinalResolution: [old res:" + i2 + "][new res:" + i3 + StrPool.BRACKET_END);
        return i3;
    }

    public static int getRTMPFillMode(V2TXLiveDef.V2TXLiveFillMode v2TXLiveFillMode) {
        int i2;
        return (v2TXLiveFillMode == null || (i2 = AnonymousClass1.f20399b[v2TXLiveFillMode.ordinal()]) == 1 || i2 != 2) ? 0 : 1;
    }

    public static int getRTMPResolution(V2TXLiveDef.V2TXLiveVideoResolution v2TXLiveVideoResolution) {
        switch (AnonymousClass1.f20400c[v2TXLiveVideoResolution.ordinal()]) {
            case 1:
                return 19;
            case 2:
                return 18;
            case 3:
                return 17;
            case 4:
                return 11;
            case 5:
                return 12;
            case 6:
                return 13;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 0;
            case 10:
            default:
                return 1;
            case 11:
                return 2;
            case 12:
                return 30;
        }
    }

    public static int getRTMPRotation(V2TXLiveDef.V2TXLiveRotation v2TXLiveRotation) {
        if (v2TXLiveRotation == null) {
            return 0;
        }
        int i2 = AnonymousClass1.f20398a[v2TXLiveRotation.ordinal()];
        if (i2 == 1) {
            return 270;
        }
        if (i2 != 2) {
            return i2 != 3 ? 0 : 90;
        }
        return 180;
    }

    public static b getVideoSize(V2TXLiveDef.V2TXLiveVideoResolution v2TXLiveVideoResolution, V2TXLiveDef.V2TXLiveVideoResolutionMode v2TXLiveVideoResolutionMode) {
        b bVar;
        b bVar2 = new b(R2.attr.bl_checked_gradient_type, 960);
        switch (AnonymousClass1.f20400c[v2TXLiveVideoResolution.ordinal()]) {
            case 1:
                return new b(160, 160);
            case 2:
                return new b(270, 270);
            case 3:
                return new b(480, 480);
            case 4:
                if (v2TXLiveVideoResolutionMode != V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait) {
                    bVar = new b(320, 240);
                    break;
                } else {
                    bVar = new b(240, 320);
                    break;
                }
            case 5:
                return v2TXLiveVideoResolutionMode == V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait ? new b(R2.attr.arcStrokeCap, 480) : new b(480, R2.attr.arcStrokeCap);
            case 6:
                return v2TXLiveVideoResolutionMode == V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait ? new b(480, 640) : new b(640, 480);
            case 7:
                if (v2TXLiveVideoResolutionMode != V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait) {
                    bVar = new b(320, 192);
                    break;
                } else {
                    bVar = new b(192, 320);
                    break;
                }
            case 8:
                if (v2TXLiveVideoResolutionMode != V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait) {
                    bVar = new b(480, R2.attr.adScopeTextColor);
                    break;
                } else {
                    bVar = new b(R2.attr.adScopeTextColor, 480);
                    break;
                }
            case 9:
                return v2TXLiveVideoResolutionMode == V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait ? new b(R2.attr.arcStrokeCap, 640) : new b(640, R2.attr.arcStrokeCap);
            case 10:
                return v2TXLiveVideoResolutionMode == V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait ? new b(R2.attr.bl_checked_gradient_type, 960) : new b(960, R2.attr.bl_checked_gradient_type);
            case 11:
                if (v2TXLiveVideoResolutionMode != V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait) {
                    bVar = new b(1280, 720);
                    break;
                } else {
                    bVar = new b(720, 1280);
                    break;
                }
            case 12:
                if (v2TXLiveVideoResolutionMode != V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait) {
                    bVar = new b(R2.attr.iconTint, R2.attr.columnOrderPreserved);
                    break;
                } else {
                    bVar = new b(R2.attr.columnOrderPreserved, R2.attr.iconTint);
                    break;
                }
            default:
                return bVar2;
        }
        return bVar;
    }

    public static boolean isNextPlayerStatusValid(V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus2, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason) {
        int i2 = AnonymousClass1.f20401d[v2TXLivePlayStatus.ordinal()];
        boolean z2 = true;
        if (i2 == 1 ? (v2TXLivePlayStatus2 != V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading || v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingBegin) && (v2TXLivePlayStatus2 != V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped || (v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStopped && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteStopped && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteOffline)) : i2 == 2 ? (v2TXLivePlayStatus2 != V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying || v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingEnd) && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted && (v2TXLivePlayStatus2 != V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped || (v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStopped && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteStopped && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteOffline)) : i2 != 3 || v2TXLivePlayStatus2 != V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying || (v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted && v2TXLiveStatusChangeReason != V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteStarted)) {
            z2 = false;
        }
        if (!z2) {
            TXCLog.e(TAG, "play state invalid.[current:" + v2TXLivePlayStatus + "][next:" + v2TXLivePlayStatus2 + "][reason:" + v2TXLiveStatusChangeReason + StrPool.BRACKET_END);
        }
        return z2;
    }

    public static boolean isPortraitResolution(int i2) {
        if (i2 == 3 || i2 == 4 || i2 == 5 || i2 == 9 || i2 == 10 || i2 == 31) {
            return false;
        }
        switch (i2) {
            case 14:
            case 15:
            case 16:
                return false;
            default:
                return true;
        }
    }

    public static V2TXLiveDef.V2TXLiveMode parseLiveMode(String str) {
        if (str.startsWith("trtc://") || str.startsWith(TRTC_ADDRESS1) || str.startsWith(TRTC_ADDRESS2)) {
            TXCLog.i(TAG, "parseLiveMode: rtc.");
            return V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTC;
        }
        TXCLog.i(TAG, "parseLiveMode: rtmp.");
        return V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTMP;
    }

    public static a.c parsePlayerType(String str) {
        if (str.startsWith("trtc://")) {
            TXCLog.i(TAG, "parsePlayerType: rtc.");
            return a.c.V2TXLiveProtocolTypeTRTC;
        }
        if (str.startsWith(TRTC_ADDRESS1) || str.startsWith(TRTC_ADDRESS2)) {
            TXCLog.i(TAG, "parsePlayerType: room.");
            return a.c.V2TXLiveProtocolTypeROOM;
        }
        if (str.startsWith("webrtc://")) {
            TXCLog.i(TAG, "parsePlayerType: webrtc.");
            return a.c.V2TXLiveProtocolTypeWEBRTC;
        }
        TXCLog.i(TAG, "parsePlayerType: rtmp.");
        return a.c.V2TXLiveProtocolTypeRTMP;
    }

    public static String removeURLSensitiveInfo(String str) {
        int iIndexOf;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            String[] strArr = {"roomsig", "privatemapkey", "usersig"};
            for (int i2 = 0; i2 < 3; i2++) {
                if (str.contains(strArr[i2]) && (iIndexOf = str.indexOf(strArr[i2])) != -1) {
                    int iIndexOf2 = str.indexOf("&", iIndexOf);
                    str = iIndexOf2 == -1 ? str.substring(0, iIndexOf) : str.substring(0, iIndexOf) + str.substring(iIndexOf2);
                }
            }
        } catch (Exception e2) {
            TXCLog.e(TAG, "remove url sensitive info failed.", e2);
        }
        return str;
    }
}
