package com.psychiatrygarden.adapter;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.bean.DownloadItem;
import com.psychiatrygarden.bean.ResourceBean;
import com.psychiatrygarden.utils.DownloadIconUtil;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.List;

/* loaded from: classes5.dex */
public class MaterialDownloadAdapter extends BaseSectionQuickAdapter<DownloadItem, BaseViewHolder> {
    private int failedColor;
    private Gson mGson;
    private int normalColor;

    public MaterialDownloadAdapter(@Nullable List<DownloadItem> data) {
        super(R.layout.item_material_download_header, data);
        this.mGson = new Gson();
        setNormalLayout(R.layout.item_material_download);
        addChildClickViewIds(R.id.tv_operate, R.id.iv_operate);
    }

    private String getProgress(int progress, String fileSize) {
        try {
            if (!fileSize.endsWith("kb") && !fileSize.endsWith("mb")) {
                return "";
            }
            return String.format("%.2f", Double.valueOf((progress * Double.parseDouble(fileSize.substring(0, fileSize.length() - 2))) / 100.0d)) + fileSize.substring(fileSize.length() - 2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, DownloadItem item) {
        int i2 = 0;
        if (this.failedColor == 0) {
            TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.forth_txt_color, R.attr.main_theme_color});
            this.failedColor = typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#F95843"));
            this.normalColor = typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#909499"));
            typedArrayObtainStyledAttributes.recycle();
        }
        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.progress_bar);
        TextView textView = (TextView) holder.getView(R.id.tv_status);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_success_local);
        if (item.getState() == 1 && new File(item.getFilePath()).exists()) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        progressBar.setProgress(item.getPercent());
        String str = item.getStr();
        String fileName = item.getFileName();
        if (!TextUtils.isEmpty(str)) {
            String showFileName = ((ResourceBean) this.mGson.fromJson(str, ResourceBean.class)).getShowFileName();
            if (!TextUtils.isEmpty(showFileName)) {
                fileName = showFileName;
            }
        }
        holder.setImageResource(R.id.iv_item_type, DownloadIconUtil.getIcon(item.getFileName())).setText(R.id.tv_file_name, fileName).setText(R.id.tv_author, item.getAuthor()).setText(R.id.tv_download_count, String.format("%d次下载", Integer.valueOf(item.getDownloadCount()))).setGone(R.id.iv_select, !item.isEditMode()).setTextColor(R.id.tv_status, (item.getState() == 0 || item.getState() == -1) ? this.failedColor : this.normalColor).setGone(R.id.iv_operate, item.isEditMode() || item.getState() == 1);
        holder.setGone(R.id.tv_author, TextUtils.isEmpty(item.getAuthor()));
        textView.setText(item.getStatusText());
        ((ImageView) holder.getView(R.id.iv_select)).setSelected(item.isEditMode() && item.isSelect());
        String convertFileSize = item.getConvertFileSize();
        Log.e("file_download", "下载=》" + convertFileSize + ";progress=" + item.getConveredCurrentProgress());
        if (TextUtils.isEmpty(convertFileSize) || "null".equals(convertFileSize)) {
            convertFileSize = "0MB";
        }
        if (TextUtils.isEmpty(item.getConveredCurrentProgress())) {
            item.setConveredCurrentProgress(getProgress(item.getPercent(), convertFileSize));
        }
        if (item.getState() == 1) {
            holder.setText(R.id.tv_file_size, convertFileSize.toUpperCase());
        } else {
            holder.setText(R.id.tv_file_size, String.format("%s/%s", item.getConveredCurrentProgress().toUpperCase(), convertFileSize.toUpperCase()));
        }
        progressBar.setVisibility(0);
        textView.setVisibility(0);
        if (item.getState() == 4) {
            i2 = R.drawable.ic_pause_download;
        } else if (item.getState() == 0) {
            i2 = R.drawable.ic_resume_download;
        } else if (item.getState() == 3 || item.getState() == 2) {
            i2 = R.drawable.ic_start_download;
        } else if (item.getState() == 1) {
            progressBar.setVisibility(4);
            textView.setVisibility(8);
        }
        if (i2 != 0) {
            holder.setImageResource(R.id.iv_operate, i2);
        }
    }

    @Override // com.chad.library.adapter.base.BaseSectionQuickAdapter
    public /* bridge */ /* synthetic */ void convertHeader(@NonNull BaseViewHolder helper, @NonNull SectionEntity item, @NonNull List payloads) {
        convertHeader(helper, (DownloadItem) item, (List<Object>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseSectionQuickAdapter
    public void convertHeader(@NonNull BaseViewHolder helper, @NonNull DownloadItem item) {
        int state = item.getState();
        if (state == 1) {
            helper.getView(R.id.tv_operate).setTag(1);
        } else if (state == 2) {
            helper.getView(R.id.tv_operate).setTag(2);
        } else {
            helper.getView(R.id.tv_operate).setTag(0);
        }
        if (state == 1) {
            helper.setText(R.id.tv_operate, "删除");
        } else {
            helper.setText(R.id.tv_operate, state == 2 ? "全部继续" : "全部暂停");
        }
        helper.setGone(R.id.tv_operate, item.isEditMode());
        if (state == 1) {
            helper.setText(R.id.tv_desc, String.format("已下载(%d)", Integer.valueOf(item.getStateCount())));
        } else {
            helper.setText(R.id.tv_desc, String.format("下载中(%d)", Integer.valueOf(item.getStateCount())));
        }
    }

    public void convertHeader(@NonNull BaseViewHolder helper, @NonNull DownloadItem item, @NonNull List<Object> payloads) {
        super.convertHeader((MaterialDownloadAdapter) helper, (BaseViewHolder) item, payloads);
        if (payloads == null || ((Integer) payloads.get(0)).intValue() != 1) {
            return;
        }
        int state = item.getState();
        if (state == 1) {
            helper.getView(R.id.tv_operate).setTag(1);
        } else if (state == 2) {
            helper.getView(R.id.tv_operate).setTag(2);
        } else {
            helper.getView(R.id.tv_operate).setTag(0);
        }
        if (state == 1) {
            helper.setText(R.id.tv_operate, "删除");
        } else {
            helper.setText(R.id.tv_operate, state == 2 ? "全部继续" : "全部暂停");
        }
    }
}
