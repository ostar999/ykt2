package com.plv.livescenes.log.player;

import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.PLVStatisticsBaseLive;

/* loaded from: classes4.dex */
public class PLVPlayerElog extends PLVStatisticsBaseLive {
    private static final String PLAYER_MODULE = "play";

    public interface Event {
        public static final String SWITCH_TO_DELAY = "switchToNoDelayWatchMode:NO";
        public static final String SWITCH_TO_NO_DELAY = "switchToNoDelayWatchMode:YES";
    }

    public PLVPlayerElog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
        this.module = "play";
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }
}
