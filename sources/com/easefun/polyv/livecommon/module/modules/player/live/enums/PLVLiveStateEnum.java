package com.easefun.polyv.livecommon.module.modules.player.live.enums;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.socket.event.sclass.PLVInLiveAckResult;
import java.util.List;

/* loaded from: classes3.dex */
public enum PLVLiveStateEnum {
    UNSTART("unStart", PLVAppUtils.getString(R.string.plv_live_state_un_start)),
    LIVE(PLVInLiveAckResult.STATUS_LIVE, PLVAppUtils.getString(R.string.plv_live_state_live)) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum.1
        @Override // com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum
        public List<PLVLiveStateEnum> getSpecNextStates() {
            return PLVSugarUtil.listOf(PLVLiveStateEnum.STOP, PLVLiveStateEnum.END, PLVLiveStateEnum.WAITING, PLVLiveStateEnum.PLAYBACK, PLVLiveStateEnum.PLAYBACK_CACHED);
        }
    },
    STOP("stop", PLVAppUtils.getString(R.string.plv_live_state_stop)) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum.2
        @Override // com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum
        public List<PLVLiveStateEnum> getSpecNextStates() {
            return PLVSugarUtil.listOf(PLVLiveStateEnum.LIVE, PLVLiveStateEnum.END, PLVLiveStateEnum.WAITING, PLVLiveStateEnum.PLAYBACK, PLVLiveStateEnum.PLAYBACK_CACHED);
        }
    },
    END("end", PLVAppUtils.getString(R.string.plv_live_state_end)) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum.3
        @Override // com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum
        public List<PLVLiveStateEnum> getSpecNextStates() {
            return PLVSugarUtil.listOf(PLVLiveStateEnum.LIVE, PLVLiveStateEnum.WAITING, PLVLiveStateEnum.PLAYBACK, PLVLiveStateEnum.PLAYBACK_CACHED);
        }
    },
    WAITING("waiting", PLVAppUtils.getString(R.string.plv_live_state_waiting)) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum.4
        @Override // com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum
        public List<PLVLiveStateEnum> getSpecNextStates() {
            return PLVSugarUtil.listOf(PLVLiveStateEnum.LIVE, PLVLiveStateEnum.END, PLVLiveStateEnum.PLAYBACK, PLVLiveStateEnum.PLAYBACK_CACHED);
        }
    },
    PLAYBACK("playback", PLVAppUtils.getString(R.string.plv_live_state_playback)) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum.5
        @Override // com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum
        public List<PLVLiveStateEnum> getSpecNextStates() {
            return PLVSugarUtil.listOf(PLVLiveStateEnum.LIVE, PLVLiveStateEnum.PLAYBACK_CACHED);
        }
    },
    PLAYBACK_CACHED("playback_cached", PLVAppUtils.getString(R.string.plv_live_state_playback_cached));

    private final String description;
    private final String status;

    public static PLVLiveStateEnum parse(@NonNull String status) {
        for (PLVLiveStateEnum pLVLiveStateEnum : values()) {
            if (pLVLiveStateEnum.getStatus().equals(status)) {
                return pLVLiveStateEnum;
            }
        }
        return WAITING;
    }

    public String getDescription() {
        return this.description;
    }

    public List<PLVLiveStateEnum> getSpecNextStates() {
        return null;
    }

    public String getStatus() {
        return this.status;
    }

    public PLVLiveStateEnum toState(PLVLiveStateEnum preferNextState) {
        List<PLVLiveStateEnum> specNextStates = getSpecNextStates();
        return specNextStates == null ? preferNextState == null ? this : preferNextState : specNextStates.contains(preferNextState) ? preferNextState : this;
    }

    PLVLiveStateEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
