package com.plv.livescenes.streamer.mix;

import androidx.annotation.NonNull;
import com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class PLVStreamerMixOpManagerDecorator implements IPLVStreamerMixOpManager {

    @NonNull
    private final IPLVStreamerMixOpManager target;

    public PLVStreamerMixOpManagerDecorator(@NonNull IPLVStreamerMixOpManager iPLVStreamerMixOpManager) {
        this.target = iPLVStreamerMixOpManager;
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void destroy() {
        this.target.destroy();
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void init(String str, String str2, int i2, String str3, boolean z2, IPLVStreamerMixOpManager.EncodeParam encodeParam, String str4) {
        this.target.init(str, str2, i2, str3, z2, encodeParam, str4);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setLiveTranscodingEnable(boolean z2) {
        this.target.setLiveTranscodingEnable(z2);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setMixLayoutType(int i2) {
        this.target.setMixLayoutType(i2);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void startMix(IPLVStreamerMixOpManager.MixUser mixUser, IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        this.target.startMix(mixUser, onMixActionListener);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void stopMix(IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        this.target.stopMix(onMixActionListener);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateMixUser(List<IPLVStreamerMixOpManager.MixUser> list) {
        this.target.updateMixUser(list);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateSEIFrameTimeStamp(String str) {
        this.target.updateSEIFrameTimeStamp(str);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateVideoEncodeParam(int i2, int i3, int i4) {
        this.target.updateVideoEncodeParam(i2, i3, i4);
    }
}
