package com.aliyun.player.alivcplayerexpand.view.download;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.DensityUtil;
import com.aliyun.player.alivcplayerexpand.util.FixedToastUtils;
import com.aliyun.player.alivcplayerexpand.util.ImageLoader;
import com.aliyun.player.alivcplayerexpand.util.WrapCheckGroup;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AddDownloadView extends LinearLayout {
    private AliyunDownloadMediaInfo downLoadTag;
    private Button downloadBtn;
    private ImageView ivVideoCover;
    private OnShowNativeVideoBtnClickListener onShowVideoListLisener;
    private OnViewClickListener onViewClickListener;
    private Map<String, String> qualityList;
    private RadioGroup rgQualityList;
    private AliyunScreenMode screenMode;
    private TextView tvAddDownloadViewSize;
    private TextView tvAddDownloadViewTitle;
    private WrapCheckGroup wrapCheckGroup;

    public interface OnShowNativeVideoBtnClickListener {
        void onShowVideo();
    }

    public interface OnViewClickListener {
        void onCancel();

        void onDownload(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);
    }

    public AddDownloadView(Context context) {
        super(context);
        this.qualityList = new HashMap();
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadItem() {
        OnViewClickListener onViewClickListener = this.onViewClickListener;
        if (onViewClickListener != null) {
            onViewClickListener.onDownload(this.downLoadTag);
        }
    }

    private void findAllViews() {
        this.ivVideoCover = (ImageView) findViewById(R.id.iv_video_cover);
        this.tvAddDownloadViewTitle = (TextView) findViewById(R.id.tv_add_download_view_title);
        this.tvAddDownloadViewSize = (TextView) findViewById(R.id.tv_add_download_view_size);
        this.downloadBtn = (Button) findViewById(R.id.download);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_quality_list);
        this.rgQualityList = radioGroup;
        radioGroup.removeAllViews();
        this.downloadBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AddDownloadView.this.downloadItem();
            }
        });
        findViewById(R.id.iv_download_dialog_close).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AddDownloadView.this.onViewClickListener != null) {
                    AddDownloadView.this.onViewClickListener.onCancel();
                }
            }
        });
    }

    private void findAllViewsByFullScreen() {
        this.ivVideoCover = (ImageView) findViewById(R.id.iv_video_cover);
        this.tvAddDownloadViewTitle = (TextView) findViewById(R.id.tv_add_download_view_title);
        this.tvAddDownloadViewSize = (TextView) findViewById(R.id.tv_add_download_view_size);
        this.rgQualityList = (RadioGroup) findViewById(R.id.rg_quality_list);
        findViewById(R.id.iv_download_dialog_close).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AddDownloadView.this.onViewClickListener != null) {
                    AddDownloadView.this.onViewClickListener.onCancel();
                }
            }
        });
        findViewById(R.id.alivc_download_start).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AddDownloadView.this.onViewClickListener != null) {
                    AddDownloadView.this.onViewClickListener.onDownload(AddDownloadView.this.downLoadTag);
                }
            }
        });
        findViewById(R.id.alivc_current_download).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AddDownloadView.this.onShowVideoListLisener != null) {
                    AddDownloadView.this.onShowVideoListLisener.onShowVideo();
                }
            }
        });
    }

    private String formatSize(long j2) {
        int i2 = (int) (j2 / 1024.0f);
        if (i2 < 1024) {
            return i2 + "KB";
        }
        return ((int) (i2 / 1024.0f)) + "MB";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatSizeDecimal(long j2) {
        float f2 = (j2 / 1024) * 1.0f;
        BigDecimal bigDecimal = new BigDecimal(f2);
        if (f2 < 1024.0f) {
            return String.format("%.1f", bigDecimal.setScale(2, RoundingMode.HALF_UP)) + "KB";
        }
        return String.format("%.1f", new BigDecimal((f2 / 1024.0f) * 1.0f).setScale(2, RoundingMode.HALF_UP)) + "MB";
    }

    private void init() {
        if (this.screenMode == AliyunScreenMode.Small) {
            LayoutInflater.from(getContext()).inflate(R.layout.view_add_download, (ViewGroup) this, true);
            findAllViews();
        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.view_add_download_horizontal, (ViewGroup) this, true);
            findAllViewsByFullScreen();
        }
        this.qualityList.put(QualityValue.QUALITY_FLUENT, getContext().getString(R.string.alivc_fd_definition));
        this.qualityList.put(QualityValue.QUALITY_LOW, getContext().getString(R.string.alivc_ld_definition));
        this.qualityList.put(QualityValue.QUALITY_STAND, getContext().getString(R.string.alivc_sd_definition));
        this.qualityList.put(QualityValue.QUALITY_HIGH, getContext().getString(R.string.alivc_hd_definition));
        this.qualityList.put(QualityValue.QUALITY_2K, getContext().getString(R.string.alivc_k2_definition));
        this.qualityList.put(QualityValue.QUALITY_4K, getContext().getString(R.string.alivc_k4_definition));
        this.qualityList.put(QualityValue.QUALITY_ORIGINAL, getContext().getString(R.string.alivc_od_definition));
        this.qualityList.put(QualityValue.QUALITY_SQ, getContext().getString(R.string.alivc_sq_definition));
        this.qualityList.put(QualityValue.QUALITY_HQ, getContext().getString(R.string.alivc_hq_definition));
    }

    private void showAllDownloadItems(List<AliyunDownloadMediaInfo> list) {
        if (list == null || list.isEmpty()) {
            FixedToastUtils.show(getContext().getApplicationContext(), R.string.no_download_right);
            return;
        }
        Log.d("demo", "list size = " + list.size());
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(-2, -2, 1.0f);
        layoutParams.setMargins(0, 0, DensityUtil.px2dip(getContext(), 16.0f), 0);
        list.size();
        int i2 = 0;
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : list) {
            if (aliyunDownloadMediaInfo.isEncripted() == 1) {
                getContext().getString(R.string.encrypted);
            } else {
                getContext().getString(R.string.encrypted_no);
            }
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.view_item_quality, (ViewGroup) new FrameLayout(getContext()), false);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setText(this.qualityList.get(aliyunDownloadMediaInfo.getQuality()));
            radioButton.setTag(aliyunDownloadMediaInfo);
            radioButton.setId(R.id.custom_id_min + i2);
            this.rgQualityList.addView(radioButton);
            i2++;
        }
        if (this.rgQualityList.getChildCount() > 0) {
            int id = this.rgQualityList.getChildAt(0).getId();
            this.rgQualityList.check(id);
            this.downLoadTag = (AliyunDownloadMediaInfo) this.rgQualityList.findViewById(id).getTag();
        }
        this.rgQualityList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AddDownloadView.4
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i3) {
                RadioButton radioButton2 = (RadioButton) AddDownloadView.this.findViewById(i3);
                if (radioButton2 == null) {
                    FixedToastUtils.show(AddDownloadView.this.getContext().getApplicationContext(), R.string.choose_a_definition_to_download);
                    return;
                }
                AddDownloadView.this.downLoadTag = (AliyunDownloadMediaInfo) radioButton2.getTag();
                TextView textView = AddDownloadView.this.tvAddDownloadViewSize;
                AddDownloadView addDownloadView = AddDownloadView.this;
                textView.setText(addDownloadView.formatSizeDecimal(addDownloadView.downLoadTag.getSize()));
            }
        });
        new ImageLoader(this.ivVideoCover).loadAsync(list.get(0).getCoverUrl());
        this.tvAddDownloadViewTitle.setText(list.get(0).getTitle());
        this.tvAddDownloadViewSize.setText(formatSizeDecimal(list.get(0).getSize()));
    }

    public void onPrepared(List<AliyunDownloadMediaInfo> list) {
        showAllDownloadItems(list);
    }

    public void setOnShowVideoListLisener(OnShowNativeVideoBtnClickListener onShowNativeVideoBtnClickListener) {
        this.onShowVideoListLisener = onShowNativeVideoBtnClickListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public AddDownloadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.qualityList = new HashMap();
        init();
    }

    public AddDownloadView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.qualityList = new HashMap();
        init();
    }

    public AddDownloadView(Context context, AliyunScreenMode aliyunScreenMode) {
        super(context);
        this.qualityList = new HashMap();
        this.screenMode = aliyunScreenMode;
        init();
    }
}
