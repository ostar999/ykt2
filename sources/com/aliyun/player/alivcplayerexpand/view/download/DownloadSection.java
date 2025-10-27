package com.aliyun.player.alivcplayerexpand.view.download;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.view.sectionlist.SectionParameters;
import com.aliyun.player.alivcplayerexpand.view.sectionlist.StatelessSection;
import com.aliyun.svideo.common.utils.image.ImageLoaderImpl;
import com.aliyun.svideo.common.utils.image.ImageLoaderOptions;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class DownloadSection extends StatelessSection {
    public static final String DOWNLOADED_TAG = "DownloadedTag";
    public static final String DOWNLOADING_TAG = "DownloadingTag";
    private ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos;
    private WeakReference<Context> context;
    private OnSectionItemClickListener onSectionItemClickListener;
    private final String tag;
    private final String title;

    public static class DownloadInfoItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbSelect;
        private ImageView ivVideoCover;
        private ImageView ivVideoState;
        private LinearLayout llDownloadItemRootView;
        private ProgressBar progressDownloadVideo;
        private TextView tvDownloadVideoCurrentSpeed;
        private TextView tvDownloadVideoStats;
        private TextView tvDownloadVideoTotalSize;
        private TextView tvVideoTitle;

        public DownloadInfoItemViewHolder(View view) {
            super(view);
            this.llDownloadItemRootView = (LinearLayout) view.findViewById(R.id.ll_download_item_root_view);
            this.cbSelect = (CheckBox) view.findViewById(R.id.cb_select);
            this.ivVideoCover = (ImageView) view.findViewById(R.id.iv_video_cover);
            this.ivVideoState = (ImageView) view.findViewById(R.id.iv_video_state);
            this.tvVideoTitle = (TextView) view.findViewById(R.id.tv_video_title);
            this.tvDownloadVideoStats = (TextView) view.findViewById(R.id.tv_download_video_stats);
            this.tvDownloadVideoCurrentSpeed = (TextView) view.findViewById(R.id.tv_download_video_current_speed);
            this.tvDownloadVideoTotalSize = (TextView) view.findViewById(R.id.tv_video_total_size);
            this.progressDownloadVideo = (ProgressBar) view.findViewById(R.id.progress_download_video);
        }
    }

    public interface OnSectionItemClickListener {
        void onItemClick(int i2, String str);
    }

    public static class SectionItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSectionItemTitle;

        public SectionItemViewHolder(View view) {
            super(view);
            this.tvSectionItemTitle = (TextView) view.findViewById(R.id.tv_section_item_title);
        }
    }

    public DownloadSection(Context context, String str, String str2, ArrayList<AlivcDownloadMediaInfo> arrayList) {
        super(SectionParameters.builder().itemResourceId(R.layout.alivc_download_item).headerResourceId(R.layout.alivc_download_section_item).build());
        this.alivcDownloadMediaInfos = new ArrayList<>();
        this.context = new WeakReference<>(context);
        this.tag = str;
        this.title = str2;
        this.alivcDownloadMediaInfos = arrayList;
    }

    private String formatSize(long j2) {
        int i2 = (int) (j2 / 1024.0f);
        if (i2 < 1024) {
            return i2 + "KB";
        }
        return ((int) (i2 / 1024.0f)) + "MB";
    }

    private String formatSizeDecimal(long j2) {
        float f2 = (j2 / 1024) * 1.0f;
        BigDecimal bigDecimal = new BigDecimal(f2);
        if (f2 < 1024.0f) {
            return String.format("%.1f", bigDecimal.setScale(2, RoundingMode.HALF_UP)) + "KB";
        }
        return String.format("%.1f", new BigDecimal((f2 / 1024.0f) * 1.0f).setScale(2, RoundingMode.HALF_UP)) + "MB";
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public int getContentItemsTotal() {
        return this.alivcDownloadMediaInfos.size();
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionItemViewHolder(view);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new DownloadInfoItemViewHolder(view);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
        ((SectionItemViewHolder) viewHolder).tvSectionItemTitle.setText(this.title);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        final DownloadInfoItemViewHolder downloadInfoItemViewHolder = (DownloadInfoItemViewHolder) viewHolder;
        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = this.alivcDownloadMediaInfos.get(i2).getAliyunDownloadMediaInfo();
        new ImageLoaderImpl().loadImage(this.context.get(), aliyunDownloadMediaInfo.getCoverUrl(), new ImageLoaderOptions.Builder().crossFade().centerCrop().placeholder(R.color.alivc_common_bg_cyan_light).build()).into(downloadInfoItemViewHolder.ivVideoCover);
        downloadInfoItemViewHolder.tvVideoTitle.setText(aliyunDownloadMediaInfo.getTitle());
        downloadInfoItemViewHolder.cbSelect.setVisibility(this.alivcDownloadMediaInfos.get(i2).isEditState() ? 0 : 8);
        downloadInfoItemViewHolder.cbSelect.setChecked(this.alivcDownloadMediaInfos.get(i2).isCheckedState());
        AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo.getStatus();
        AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Start;
        if (status == status2 && aliyunDownloadMediaInfo.getProgress() == 100) {
            status = AliyunDownloadMediaInfo.Status.Complete;
            aliyunDownloadMediaInfo.setStatus(status);
        }
        if (status == AliyunDownloadMediaInfo.Status.Prepare) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setText(this.context.get().getResources().getString(R.string.download_prepare));
        } else if (status == AliyunDownloadMediaInfo.Status.Wait) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setText(this.context.get().getResources().getString(R.string.download_wait));
        } else if (status == status2) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setText(this.context.get().getResources().getString(R.string.download_downloading));
            downloadInfoItemViewHolder.ivVideoState.setBackgroundResource(R.drawable.alivc_download_pause);
            downloadInfoItemViewHolder.ivVideoState.setVisibility(0);
        } else if (status == AliyunDownloadMediaInfo.Status.Stop) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setText(this.context.get().getResources().getString(R.string.download_pause));
            downloadInfoItemViewHolder.ivVideoState.setBackgroundResource(R.drawable.alivc_download_downloading);
            downloadInfoItemViewHolder.ivVideoState.setVisibility(0);
        } else if (status == AliyunDownloadMediaInfo.Status.Complete) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setVisibility(8);
            downloadInfoItemViewHolder.ivVideoState.setVisibility(8);
            downloadInfoItemViewHolder.progressDownloadVideo.setVisibility(8);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(9, -1);
            downloadInfoItemViewHolder.tvDownloadVideoTotalSize.setLayoutParams(layoutParams);
        } else if (status == AliyunDownloadMediaInfo.Status.Error) {
            downloadInfoItemViewHolder.tvDownloadVideoStats.setText(this.context.get().getResources().getString(R.string.download_error));
            downloadInfoItemViewHolder.ivVideoState.setVisibility(0);
            downloadInfoItemViewHolder.ivVideoState.setBackgroundResource(R.drawable.alivc_download_downloading);
        }
        downloadInfoItemViewHolder.progressDownloadVideo.setProgress(aliyunDownloadMediaInfo.getProgress());
        downloadInfoItemViewHolder.tvDownloadVideoTotalSize.setText(formatSizeDecimal(aliyunDownloadMediaInfo.getSize()));
        downloadInfoItemViewHolder.llDownloadItemRootView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.DownloadSection.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int adapterPosition = downloadInfoItemViewHolder.getAdapterPosition();
                if (DownloadSection.this.onSectionItemClickListener == null || adapterPosition < 0) {
                    return;
                }
                DownloadSection.this.onSectionItemClickListener.onItemClick(adapterPosition, DownloadSection.this.tag);
            }
        });
    }

    public void setOnSectionItemClickListener(OnSectionItemClickListener onSectionItemClickListener) {
        this.onSectionItemClickListener = onSectionItemClickListener;
    }
}
