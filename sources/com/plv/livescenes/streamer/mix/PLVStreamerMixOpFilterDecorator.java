package com.plv.livescenes.streamer.mix;

import androidx.annotation.NonNull;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.PLVLinkMicWrapper;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVStreamerMixOpFilterDecorator extends PLVStreamerMixOpManagerDecorator {
    private String cdnUrl;
    private final PLVLinkMicWrapper coreLinkMicWrapper;
    private final boolean isRTCSupport;
    private final boolean isSEISupport;
    private boolean liveTranscodingEnabled;

    public PLVStreamerMixOpFilterDecorator(@NonNull IPLVStreamerMixOpManager iPLVStreamerMixOpManager, PLVLinkMicWrapper pLVLinkMicWrapper, String str) {
        super(iPLVStreamerMixOpManager);
        this.coreLinkMicWrapper = pLVLinkMicWrapper;
        this.isRTCSupport = PLVLinkMicConstant.RtcType.RTC_TYPE_T.equals(str) || PLVLinkMicConstant.RtcType.RTC_TYPE_A.equals(str) || PLVLinkMicConstant.RtcType.RTC_TYPE_U.equals(str);
        this.isSEISupport = PLVLinkMicConstant.RtcType.RTC_TYPE_A.equals(str);
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void destroy() {
        if (this.isRTCSupport) {
            super.destroy();
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void init(String str, String str2, int i2, String str3, boolean z2, IPLVStreamerMixOpManager.EncodeParam encodeParam, String str4) {
        this.cdnUrl = str4;
        if (this.isRTCSupport) {
            super.init(str, str2, i2, str3, z2, encodeParam, str4);
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setLiveTranscodingEnable(boolean z2) {
        this.liveTranscodingEnabled = z2;
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setMixLayoutType(int i2) {
        if (this.isRTCSupport) {
            super.setMixLayoutType(i2);
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void startMix(IPLVStreamerMixOpManager.MixUser mixUser, IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        if (this.isRTCSupport) {
            super.startMix(mixUser, onMixActionListener);
            return;
        }
        int iAddPublishStreamUrl = this.coreLinkMicWrapper.addPublishStreamUrl(this.cdnUrl, this.liveTranscodingEnabled);
        if (iAddPublishStreamUrl != 0) {
            onMixActionListener.onFail(new PLVLinkMicHttpRequestException.Builder(3).msg("addPublishStreamUrl=" + iAddPublishStreamUrl).build());
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void stopMix(IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        if (this.isRTCSupport) {
            super.stopMix(onMixActionListener);
        } else {
            this.coreLinkMicWrapper.removePublishStreamUrl(this.cdnUrl);
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateMixUser(List<IPLVStreamerMixOpManager.MixUser> list) {
        if (this.isRTCSupport) {
            super.updateMixUser(list);
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateSEIFrameTimeStamp(String str) {
        if (this.isRTCSupport && this.isSEISupport) {
            super.updateSEIFrameTimeStamp(str);
        }
    }

    @Override // com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerDecorator, com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateVideoEncodeParam(int i2, int i3, int i4) {
        if (this.isRTCSupport) {
            super.updateVideoEncodeParam(i2, i3, i4);
        }
    }
}
