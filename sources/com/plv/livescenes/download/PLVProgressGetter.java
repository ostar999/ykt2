package com.plv.livescenes.download;

import androidx.annotation.IntRange;
import com.plv.livescenes.download.PLVProgressManager;

/* loaded from: classes4.dex */
public class PLVProgressGetter {
    private static final String PROGRESS_JS = "progress_js";
    private static final String PROGRESS_VIDEO_ = "progress_video";
    private static final String PROGRESS_ZIP = "progress_zip";
    private PLVProgressManager manager;

    public PLVProgressGetter(boolean z2) {
        if (z2) {
            this.manager = new PLVProgressManager.ProgressNodeBuilder().add(PROGRESS_VIDEO_, 80).add(PROGRESS_ZIP, 17).add(PROGRESS_JS, 3).build();
        } else {
            this.manager = new PLVProgressManager.ProgressNodeBuilder().add(PROGRESS_VIDEO_, 100).build();
        }
    }

    @IntRange(from = 0, to = 100)
    public int updateJsProgressAndGetInTotal(@IntRange(from = 0, to = 100) int i2) {
        return this.manager.updateAndGetProgressInTotal(PROGRESS_JS, i2);
    }

    @IntRange(from = 0, to = 100)
    public int updateVideoProgressAndGetInTotal(@IntRange(from = 0, to = 100) int i2) {
        return this.manager.updateAndGetProgressInTotal(PROGRESS_VIDEO_, i2);
    }

    @IntRange(from = 0, to = 100)
    public int updateZipProgressAndGetInTotal(@IntRange(from = 0, to = 100) int i2) {
        return this.manager.updateAndGetProgressInTotal(PROGRESS_ZIP, i2);
    }
}
