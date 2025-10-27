package com.psychiatrygarden.activity.courselist.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.player.aliyunplayerbase.util.Formatter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseCoverDownloadAdapter extends BaseSectionQuickAdapter<CourseCoverBean, BaseViewHolder> {
    public CourseCoverDownloadAdapter(@Nullable List<CourseCoverBean> data) {
        super(R.layout.item_course_download_header, data);
        setNormalLayout(R.layout.item_cover_course_download);
        addChildClickViewIds(R.id.tv_operate, R.id.iv_start_download);
    }

    private void convertItem(@NonNull BaseViewHolder holder, CourseCoverBean item, boolean incrementUpdate) {
        if (!incrementUpdate) {
            GlideUtils.loadImage(getContext(), item.getCover(), (ImageView) holder.getView(R.id.iv_course_cover));
            holder.setText(R.id.tv_course_title, item.title);
        }
        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.progress_bar);
        boolean z2 = true;
        holder.setText(R.id.tv_current_size_progress, Formatter.getFileSizeDescription(item.getSizeprocess()) + " / " + Formatter.getFileSizeDescription(item.getSize())).setVisible(R.id.tv_current_size_progress, true).setVisible(R.id.tv_download_status, true).setTextColor(R.id.tv_current_count_progress, getContext().getColor(R.color.third_txt_color));
        if (item.getSizeprocess() <= 0 || item.getSize() <= 0) {
            progressBar.setProgress(0);
        } else {
            int sizeprocess = (int) ((item.getSizeprocess() * 100) / item.getSize());
            LogUtils.d("course_progress", String.valueOf(sizeprocess));
            progressBar.setProgress(sizeprocess);
        }
        List<Integer> status = item.getStatus();
        String str = "下载失败";
        if (status.contains(1)) {
            holder.setText(R.id.tv_download_status, "下载中");
            holder.setImageResource(R.id.iv_start_download, R.drawable.ic_pause_download);
        } else if (status.contains(2) || status.contains(6)) {
            holder.setText(R.id.tv_download_status, "下载失败");
            holder.setImageResource(R.id.iv_start_download, R.drawable.ic_resume_download).setGone(R.id.tv_download_status, true);
        } else if (status.contains(4)) {
            holder.setText(R.id.tv_download_status, "暂停下载");
            holder.setImageResource(R.id.iv_start_download, R.drawable.ic_start_download);
        } else if (status.contains(3)) {
            holder.setText(R.id.tv_download_status, "队列中");
            holder.setImageResource(R.id.iv_start_download, R.drawable.ic_start_download);
        } else if (status.contains(5) && item.isAllComplete()) {
            holder.setText(R.id.tv_download_status, "已下载");
        }
        if (item.isAllComplete()) {
            holder.setGone(R.id.iv_start_download, true).setVisible(R.id.progress_bar, false).setVisible(R.id.iv_local, true).setGone(R.id.tv_download_status, true);
            holder.setText(R.id.tv_current_count_progress, "已下载");
            holder.setText(R.id.tv_current_size_progress, String.format("%d/%d", Integer.valueOf(item.getCount()), Integer.valueOf(item.getCount())));
            return;
        }
        holder.setVisible(R.id.iv_start_download, true).setGone(R.id.iv_local, true).setVisible(R.id.tv_download_status, (status.contains(2) || status.contains(6)) ? false : true).setGone(R.id.progress_bar, false);
        int downloadingCount = item.getDownloadingCount();
        int count = item.getCount();
        if (downloadingCount > count) {
            downloadingCount = count;
        }
        if (!status.contains(2) && !status.contains(6)) {
            str = String.format("%d/%d", Integer.valueOf(downloadingCount), Integer.valueOf(count));
        }
        BaseViewHolder text = holder.setText(R.id.tv_current_count_progress, str);
        if (!status.contains(2) && !status.contains(6)) {
            z2 = false;
        }
        text.setGone(R.id.tv_current_size_progress, z2).setTextColor(R.id.tv_current_count_progress, getContext().getColor((status.contains(2) || status.contains(6)) ? R.color.app_theme_red : R.color.third_txt_color));
        if (status.contains(2) || status.contains(6)) {
            holder.setImageResource(R.id.iv_start_download, R.drawable.ic_resume_download);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, Object item, @NonNull List payloads) {
        convert(holder, (CourseCoverBean) item, (List<?>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseSectionQuickAdapter
    public void convertHeader(@NonNull BaseViewHolder holder, @NonNull CourseCoverBean item) {
        holder.setText(R.id.tv_desc, item.getType() == 1 ? String.format("已下载(%d)", Integer.valueOf(item.getCount())) : String.format("下载中(%d)", Integer.valueOf(item.getCount())));
        int state = item.getState();
        if (state == 1) {
            holder.getView(R.id.tv_operate).setTag("DELETE");
            holder.setText(R.id.tv_operate, "全部删除").setVisible(R.id.tv_operate, item.getCount() > 0);
        } else if (state == 0) {
            holder.getView(R.id.tv_operate).setTag("PAUSE");
            holder.setText(R.id.tv_operate, "全部暂停").setVisible(R.id.tv_operate, item.getCount() > 0);
        } else if (state == 2) {
            holder.getView(R.id.tv_operate).setTag("START");
            holder.setText(R.id.tv_operate, "全部继续").setVisible(R.id.tv_operate, item.getCount() > 0);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, CourseCoverBean item) {
        convertItem(holder, item, false);
    }

    public void convert(@NonNull BaseViewHolder holder, CourseCoverBean item, @NonNull List<?> payloads) {
        super.convert((CourseCoverDownloadAdapter) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
        if (payloads.size() > 0) {
            for (Object obj : payloads) {
                if ((obj instanceof String) && TextUtils.equals("UPDATE_TEXT", (String) obj)) {
                    List<Integer> status = item.getStatus();
                    int i2 = !item.isHeader() ? R.id.tv_download_status : R.id.tv_operate;
                    if (status.contains(3)) {
                        holder.setText(i2, !item.isHeader() ? "下载中" : "全部暂停");
                    } else if (status.contains(4)) {
                        holder.setText(i2, !item.isHeader() ? "暂停下载" : "全部继续");
                    }
                } else {
                    convertItem(holder, item, true);
                }
            }
        }
    }
}
