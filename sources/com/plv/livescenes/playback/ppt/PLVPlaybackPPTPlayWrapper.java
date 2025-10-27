package com.plv.livescenes.playback.ppt;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.plv.business.api.common.player.microplayer.PLVCommonVideoView;
import com.plv.business.api.common.ppt.IPLVPPTView;
import com.plv.business.api.common.ppt.IPLVPPTWarpper;
import com.plv.business.api.common.ppt.vo.PLVPPTLocalCacheVO;
import com.plv.business.model.ppt.PLVPPTInfo;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;
import io.reactivex.disposables.Disposable;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVPlaybackPPTPlayWrapper implements IPLVPPTWarpper<PLVPPTInfo> {
    private static final int DEVIATION = 800;
    private static final String TAG = "PLVPlaybackPPTPlayWrapper";
    private PLVCommonVideoView commonVideoView;
    private Disposable currentDispose;
    private int currentPos;
    private boolean isPause;
    private List<PLVPPTInfo> pptInfos;
    private IPLVPPTView pptView;
    private float speed = 1.0f;
    private long videoStartTimeStamp;

    public PLVPlaybackPPTPlayWrapper(PLVCommonVideoView pLVCommonVideoView) {
        this.commonVideoView = pLVCommonVideoView;
    }

    private int binarySearch(int i2, int i3, int i4) {
        while (i2 <= i3) {
            int i5 = (i3 + i2) / 2;
            PLVPPTInfo pLVPPTInfo = this.pptInfos.get(i5);
            if (pLVPPTInfo == null) {
                return i5;
            }
            long time = pLVPPTInfo.getTime() - this.videoStartTimeStamp;
            PLVCommonLog.d(TAG, "search time :" + time + "   seek to " + i4);
            if (checkCollide(time, i4)) {
                return i5;
            }
            if (time < i4) {
                i2 = i5 + 1;
            } else {
                i3 = i5 - 1;
            }
        }
        return -1;
    }

    private void cancelTask() {
        Disposable disposable = this.currentDispose;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private boolean checkCollide(long j2, int i2) {
        return Math.abs(j2 - ((long) i2)) <= 800;
    }

    private String createPPTMessage(long j2) {
        return "{\"time\":" + j2 + "}";
    }

    private void play(List<PLVPPTInfo> list, int i2) {
        PLVPPTInfo pLVPPTInfo;
        PLVCommonLog.d(TAG, "play");
        if (list == null || list.isEmpty() || i2 >= list.size() || (pLVPPTInfo = list.get(i2)) == null) {
            return;
        }
        long time = pLVPPTInfo.getTime() - this.videoStartTimeStamp;
        PLVCommonLog.d(TAG, "play delay :" + time + "  time :" + pLVPPTInfo.getTime());
        if (time < 0) {
            play(list, i2 + 1);
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void bindPPTView(IPLVPPTView iPLVPPTView) {
        this.pptView = iPLVPPTView;
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void destory() {
        Disposable disposable = this.currentDispose;
        if (disposable != null) {
            disposable.dispose();
            this.currentDispose = null;
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void pause() {
        this.isPause = true;
        if (this.pptView != null) {
            this.pptView.pause(createPPTMessage(this.commonVideoView.getCurrentPosition()));
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void restart() {
        if (this.pptView != null) {
            this.pptView.play(createPPTMessage(this.commonVideoView.getCurrentPosition()));
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public synchronized void seekTo(int i2) {
        if (this.pptView != null) {
            this.pptView.seek(createPPTMessage(this.commonVideoView.getCurrentPosition()));
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void speedUp(int i2) {
        this.speed = i2;
    }

    public void startLocalPlay(@NonNull PLVPPTLocalCacheVO pLVPPTLocalCacheVO) {
        IPLVPPTView iPLVPPTView = this.pptView;
        if (iPLVPPTView != null) {
            iPLVPPTView.onLoadLocalPpt(pLVPPTLocalCacheVO);
        }
    }

    @Override // com.plv.business.api.common.ppt.IPLVPPTWarpper
    public void startPlay(String str, String str2) {
        if (this.pptView != null) {
            HashMap map = new HashMap(3);
            map.put("type", "playback");
            map.put(PLVLinkMicManager.ROOM_ID, str);
            map.put("id", str2);
            this.pptView.pptPrepare(new Gson().toJson(map));
        }
    }

    public void startPlay(String str, @NonNull PLVPlaybackDataVO pLVPlaybackDataVO) {
        if (this.pptView != null) {
            HashMap map = new HashMap(3);
            map.put("type", pLVPlaybackDataVO.getPptPlayType());
            map.put(PLVLinkMicManager.ROOM_ID, str);
            map.put("id", pLVPlaybackDataVO.getPptPlayId());
            this.pptView.pptPrepare(new Gson().toJson(map));
        }
    }
}
