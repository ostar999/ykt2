package com.tencent.liteav.videoencoder;

import org.json.JSONArray;

/* loaded from: classes6.dex */
public class TXSVideoEncoderParam {
    public int width = 0;
    public int height = 0;
    public int fps = 20;
    public int gop = 3;
    public int encoderProfile = 1;
    public int encoderMode = 1;
    public boolean enableBFrame = false;
    public Object glContext = null;
    public boolean realTime = false;
    public boolean annexb = false;
    public boolean appendSpsPps = true;
    public boolean fullIFrame = false;
    public boolean syncOutput = false;
    public boolean enableEGL14 = false;
    public boolean enableBlackList = true;
    public boolean record = false;
    public long baseFrameIndex = 0;
    public long baseGopIndex = 0;
    public int streamType = 0;
    public boolean bMultiRef = false;
    public int bitrate = 0;
    public boolean bLimitFps = false;
    public int encodeType = 0;
    public boolean forceSetBitrateMode = false;
    public JSONArray encFmt = null;
    public boolean isH265EncoderEnabled = false;
    public int usageType = 0;

    public String toString() {
        return "TXSVideoEncoderParam{width=" + this.width + ", height=" + this.height + ", fps=" + this.fps + ", gop=" + this.gop + ", encoderProfile=" + this.encoderProfile + ", encoderMode=" + this.encoderMode + ", enableBFrame=" + this.enableBFrame + ", glContext=" + this.glContext + ", realTime=" + this.realTime + ", annexb=" + this.annexb + ", appendSpsPps=" + this.appendSpsPps + ", fullIFrame=" + this.fullIFrame + ", syncOutput=" + this.syncOutput + ", enableEGL14=" + this.enableEGL14 + ", enableBlackList=" + this.enableBlackList + ", record=" + this.record + ", baseFrameIndex=" + this.baseFrameIndex + ", baseGopIndex=" + this.baseGopIndex + ", streamType=" + this.streamType + ", bMultiRef=" + this.bMultiRef + ", bitrate=" + this.bitrate + ", bLimitFps=" + this.bLimitFps + ", encodeType=" + this.encodeType + ", forceSetBitrateMode=" + this.forceSetBitrateMode + ", encFmt=" + this.encFmt + ", isH265EncoderEnabled=" + this.isH265EncoderEnabled + ", usageType=" + this.usageType + '}';
    }
}
