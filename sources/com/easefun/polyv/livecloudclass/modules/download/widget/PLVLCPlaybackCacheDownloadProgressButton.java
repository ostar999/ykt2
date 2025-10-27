package com.easefun.polyv.livecloudclass.modules.download.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.ui.widget.PLVRoundRectGradientTextView;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;

/* loaded from: classes3.dex */
public class PLVLCPlaybackCacheDownloadProgressButton extends FrameLayout {
    private TextView playbackCacheDownloadProgressBackgroundTv;
    private TextView playbackCacheDownloadProgressForegroundTv;
    private PLVRoundRectGradientTextView playbackCacheDownloadStatusTv;
    private int progress;
    private PLVPlaybackCacheDownloadStatusEnum statusEnum;
    private static final int COLOR_DOWNLOAD_STATUS_BACKGROUND_NOT_DOWNLOAD = PLVFormatUtils.parseColor("#3082FE");
    private static final int COLOR_DOWNLOAD_STATUS_BACKGROUND_DOWNLOADED = PLVFormatUtils.parseColor("#98c1ff");

    public PLVLCPlaybackCacheDownloadProgressButton(@NonNull Context context) {
        super(context);
        this.statusEnum = PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST;
        this.progress = 0;
        initView();
    }

    private void findView() {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.plvlc_playback_cache_download_progress_button, (ViewGroup) this, false);
        this.playbackCacheDownloadStatusTv = (PLVRoundRectGradientTextView) viewGroup.findViewById(R.id.plvlc_playback_cache_download_status_tv);
        this.playbackCacheDownloadProgressBackgroundTv = (TextView) viewGroup.findViewById(R.id.plvlc_playback_cache_download_progress_background_tv);
        this.playbackCacheDownloadProgressForegroundTv = (TextView) viewGroup.findViewById(R.id.plvlc_playback_cache_download_progress_foreground_tv);
        viewGroup.removeAllViews();
        addView(this.playbackCacheDownloadStatusTv);
        addView(this.playbackCacheDownloadProgressBackgroundTv);
        addView(this.playbackCacheDownloadProgressForegroundTv);
    }

    private void initView() {
        findView();
    }

    private void updateViewProperties() {
        PLVPlaybackCacheDownloadStatusEnum pLVPlaybackCacheDownloadStatusEnum = PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING;
        boolean zContains = PLVSugarUtil.listOf(PLVPlaybackCacheDownloadStatusEnum.WAITING, PLVPlaybackCacheDownloadStatusEnum.PAUSING, pLVPlaybackCacheDownloadStatusEnum).contains(this.statusEnum);
        this.playbackCacheDownloadStatusTv.setVisibility(zContains ? 8 : 0);
        this.playbackCacheDownloadProgressBackgroundTv.setVisibility(zContains ? 0 : 8);
        this.playbackCacheDownloadProgressForegroundTv.setVisibility(zContains ? 0 : 8);
        if (!zContains) {
            PLVPlaybackCacheDownloadStatusEnum pLVPlaybackCacheDownloadStatusEnum2 = this.statusEnum;
            if (pLVPlaybackCacheDownloadStatusEnum2 == PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST) {
                this.playbackCacheDownloadStatusTv.setText("立即下载");
                this.playbackCacheDownloadStatusTv.updateBackgroundColor(COLOR_DOWNLOAD_STATUS_BACKGROUND_NOT_DOWNLOAD);
            } else if (pLVPlaybackCacheDownloadStatusEnum2 == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED) {
                this.playbackCacheDownloadStatusTv.setText("已下载");
                this.playbackCacheDownloadStatusTv.updateBackgroundColor(COLOR_DOWNLOAD_STATUS_BACKGROUND_DOWNLOADED);
            } else if (pLVPlaybackCacheDownloadStatusEnum2 == PLVPlaybackCacheDownloadStatusEnum.DOWNLOAD_FAIL) {
                this.playbackCacheDownloadStatusTv.setText("下载失败");
                this.playbackCacheDownloadStatusTv.updateBackgroundColor(COLOR_DOWNLOAD_STATUS_BACKGROUND_DOWNLOADED);
            }
        }
        if (zContains) {
            if (pLVPlaybackCacheDownloadStatusEnum.equals(this.statusEnum)) {
                this.playbackCacheDownloadProgressBackgroundTv.setText(PLVSugarUtil.format("下载中 {}%", Integer.valueOf(this.progress)));
                this.playbackCacheDownloadProgressForegroundTv.setText(PLVSugarUtil.format("下载中 {}%", Integer.valueOf(this.progress)));
            } else {
                this.playbackCacheDownloadProgressBackgroundTv.setText(this.statusEnum.getStatusName());
                this.playbackCacheDownloadProgressForegroundTv.setText(this.statusEnum.getStatusName());
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j2) {
        if (view != this.playbackCacheDownloadProgressForegroundTv) {
            return super.drawChild(canvas, view, j2);
        }
        int width = (int) ((getWidth() * this.progress) / 100.0f);
        int iSave = canvas.save();
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipOutRect(width, 0, getWidth(), getHeight());
        } else {
            canvas.clipRect(width, 0.0f, getWidth(), getHeight(), Region.Op.DIFFERENCE);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(iSave);
        return zDrawChild;
    }

    public void update(PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
        this.progress = ((Integer) PLVSugarUtil.getOrDefault(pLVPlaybackCacheVideoVO.getProgress(), 0)).intValue();
        this.statusEnum = pLVPlaybackCacheVideoVO.getDownloadStatusEnum();
        updateViewProperties();
        invalidate();
    }

    public PLVLCPlaybackCacheDownloadProgressButton(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.statusEnum = PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST;
        this.progress = 0;
        initView();
    }

    public PLVLCPlaybackCacheDownloadProgressButton(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.statusEnum = PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST;
        this.progress = 0;
        initView();
    }
}
