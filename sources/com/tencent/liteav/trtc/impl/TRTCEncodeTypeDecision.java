package com.tencent.liteav.trtc.impl;

import com.tencent.liteav.basic.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.g;
import com.tencent.liteav.videodecoder.e;
import com.tencent.liteav.videoencoder.d;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class TRTCEncodeTypeDecision {
    public static final int CODEC_ABILITY_VALUE_SUPPORT_264 = 3;
    public static final int CODEC_ABILITY_VALUE_SUPPORT_264_DECODE = 2;
    public static final int CODEC_ABILITY_VALUE_SUPPORT_264_ENCODE = 1;
    private static final int CODEC_ABILITY_VALUE_SUPPORT_265_DECODE_BITMASK = 3;
    private static final int CODEC_ABILITY_VALUE_SUPPORT_265_ENCODE_BITMASK = 2;
    private static final String TAG = "TRTCH265Decision";
    private static boolean sLifeCycleEnableH265Decoder = true;
    private static boolean sLifeCycleEnableH265Encode = true;
    private boolean mEnableH265EncodeByPrivateAPI = false;
    private boolean mEnableH265EncodeByServer = false;
    private TRTCCloudImpl mTrtcCloud;

    public enum ModifyCodecReason {
        REASON_NO_NEED_CARE(0),
        REASON_ENCODE_ERROR(1),
        REASON_ENTERROOM_RESPOND(2),
        REASON_QOS(3),
        REASON_DECODE_ERROR(4);

        private int value;

        ModifyCodecReason(int i2) {
            this.value = i2;
        }
    }

    public TRTCEncodeTypeDecision(TRTCCloudImpl tRTCCloudImpl) {
        this.mTrtcCloud = tRTCCloudImpl;
    }

    public static int getEnterRoomCodecSupportValue() {
        int h265DecoderValue = getH265DecoderValue() | 3 | ((d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20) ? 1 : 0) << 2);
        TXCLog.i(TAG, "codecability codecSupport : " + h265DecoderValue);
        return h265DecoderValue;
    }

    public static int getH265DecoderValue() {
        return ((sLifeCycleEnableH265Decoder && e.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20)) ? 1 : 0) << 3;
    }

    public static boolean isAppLifeCycleEnableH265Decoder() {
        return sLifeCycleEnableH265Decoder;
    }

    public static boolean isAppLifeCycleEnableH265Encoder() {
        return sLifeCycleEnableH265Encode;
    }

    public static void setAppLifeCycleEnableH265Decoder(boolean z2) {
        sLifeCycleEnableH265Decoder = z2;
    }

    public static void setAppLifeCycleEnableH265Encoder(boolean z2) {
        sLifeCycleEnableH265Encode = z2;
    }

    public int getExpectVideoCodecType(int i2) {
        g.a aVar = g.a.CODEC_TYPE_H264;
        int iA = aVar.a();
        if (i2 != 1) {
            return iA;
        }
        boolean z2 = this.mEnableH265EncodeByPrivateAPI && isAppLifeCycleEnableH265Encoder() && d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20);
        int iA2 = z2 ? g.a.CODEC_TYPE_H265.a() : aVar.a();
        TXCLog.i(TAG, "enableH265 = " + z2 + " expectCodecType=" + iA2);
        return iA2;
    }

    public boolean isVideoEncoderCodecUsingH265() {
        boolean zA = d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20);
        boolean z2 = this.mEnableH265EncodeByPrivateAPI && this.mEnableH265EncodeByServer && isAppLifeCycleEnableH265Encoder() && zA;
        TXCLog.i(TAG, "codecability getVideoEncoderCodec: " + this.mEnableH265EncodeByServer + " ,mEnableHighQualityEncode=" + this.mEnableH265EncodeByPrivateAPI + ", TRTCH265Decision.isAppLifeCycleEnableH265Encoder()= " + isAppLifeCycleEnableH265Encoder() + " supportEncoder = " + zA);
        return z2;
    }

    public boolean isVideoEncoderStartCodecUsingH265() {
        g.a aVarA = g.a.a(g.a().b("key_last_encode_type", g.a.CODEC_TYPE_H264.a()));
        boolean zA = d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20);
        boolean z2 = this.mEnableH265EncodeByPrivateAPI && isAppLifeCycleEnableH265Encoder() && zA;
        if (z2) {
            z2 = aVarA == g.a.CODEC_TYPE_H265;
        }
        TXCLog.i(TAG, "getVideoEncoderStartCodec enableH265 = " + z2 + " , lastEncodeType=" + aVarA.a() + " mEnableH265EncodeByPrivateAPI=" + this.mEnableH265EncodeByPrivateAPI + " supportEncoder=" + zA);
        return z2;
    }

    public void setEnableH265EncodeByPrivateAPI(boolean z2) {
        setEnableH265EncodeByPrivateAPI(z2, ModifyCodecReason.REASON_NO_NEED_CARE);
    }

    public void setEnableH265EncodeByServer(boolean z2) {
        setEnableH265EncodeByServer(z2, ModifyCodecReason.REASON_NO_NEED_CARE);
    }

    public void setEnableH265EncodeByPrivateAPI(boolean z2, ModifyCodecReason modifyCodecReason) {
        this.mEnableH265EncodeByPrivateAPI = z2;
        TXCLog.i(TAG, "setEnableH265EncodeByPrivateAPI: enableH265EncodeByPrivateAPI= " + z2 + ", reason =" + modifyCodecReason);
        if (modifyCodecReason == ModifyCodecReason.REASON_ENCODE_ERROR) {
            setAppLifeCycleEnableH265Encoder(false);
            this.mTrtcCloud.notifyCurrentEncodeType(false);
        } else {
            if (modifyCodecReason != ModifyCodecReason.REASON_DECODE_ERROR || a.a()) {
                return;
            }
            setAppLifeCycleEnableH265Decoder(false);
        }
    }

    public void setEnableH265EncodeByServer(boolean z2, ModifyCodecReason modifyCodecReason) {
        if (this.mEnableH265EncodeByServer != z2) {
            if (modifyCodecReason == ModifyCodecReason.REASON_QOS) {
                if (z2) {
                    return;
                }
                if (isVideoEncoderCodecUsingH265()) {
                    this.mTrtcCloud.notifyCurrentEncodeType(false);
                }
            }
            this.mEnableH265EncodeByServer = z2;
            if (modifyCodecReason == ModifyCodecReason.REASON_ENTERROOM_RESPOND) {
                boolean zIsVideoEncoderCodecUsingH265 = isVideoEncoderCodecUsingH265();
                g.a().a("key_last_encode_type", (zIsVideoEncoderCodecUsingH265 ? g.a.CODEC_TYPE_H265 : g.a.CODEC_TYPE_H264).a());
                this.mTrtcCloud.notifyCurrentEncodeType(zIsVideoEncoderCodecUsingH265);
            }
            TXCLog.i(TAG, "setEnableH265EncodeByServer: enableH265EncodeByServer= " + this.mEnableH265EncodeByServer + ", reason =" + modifyCodecReason);
        }
    }
}
