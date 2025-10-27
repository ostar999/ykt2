package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.VideoHandout;
import com.psychiatrygarden.utils.DownloadIconUtil;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.List;

/* loaded from: classes6.dex */
public class VideoHandoutPop extends BottomPopupView {
    private List<VideoHandout> handoutList;
    private int height;
    private final PreviewFileListener mPreviewFileListener;

    public interface PreviewFileListener {
        void preview(int position);
    }

    public VideoHandoutPop(@NonNull Context context, List<VideoHandout> handoutList, PreviewFileListener listener) {
        super(context);
        this.height = 0;
        this.mPreviewFileListener = listener;
        this.handoutList = handoutList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDownloadPath() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload/");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFileName(String url, String fileName) {
        if (fileName.contains(getFileType(url))) {
            return fileName;
        }
        return fileName + StrPool.DOT + getFileType(url);
    }

    private String getFileType(String paramString) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(paramString) || (iLastIndexOf = paramString.lastIndexOf(46)) == -1) ? "" : paramString.substring(iLastIndexOf + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        PreviewFileListener previewFileListener = this.mPreviewFileListener;
        if (previewFileListener != null) {
            previewFileListener.preview(i2);
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_video_handout;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return SizeUtil.dp2px(getContext(), 500);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close_handout).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ij
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16599c.lambda$onCreate$0(view);
            }
        });
        if (this.height > 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_root);
            ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
            layoutParams.height = this.height;
            linearLayout.setLayoutParams(layoutParams);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        BaseQuickAdapter<VideoHandout, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VideoHandout, BaseViewHolder>(R.layout.item_video_handout) { // from class: com.psychiatrygarden.widget.VideoHandoutPop.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, VideoHandout item) {
                holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_file_size, item.getSize_info()).setImageResource(R.id.iv_type, DownloadIconUtil.getIcon(item.getSuffix()));
                if (new File(VideoHandoutPop.this.getDownloadPath() + VideoHandoutPop.this.getFileName(item.getUrl(), item.getTitle())).length() >= Integer.parseInt(item.getSize_byte())) {
                    holder.setVisible(R.id.tv_status, true).setVisible(R.id.iv_local_file, true);
                    item.setLocalFile(true);
                } else {
                    holder.setGone(R.id.tv_status, true).setGone(R.id.iv_local_file, true);
                    item.setLocalFile(false);
                }
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.jj
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16629c.lambda$onCreate$1(baseQuickAdapter2, view, i2);
            }
        });
        baseQuickAdapter.setList(this.handoutList);
    }

    public VideoHandoutPop(@NonNull Context context, List<VideoHandout> handoutList, PreviewFileListener listener, int height) {
        super(context);
        this.mPreviewFileListener = listener;
        this.handoutList = handoutList;
        this.height = height;
    }
}
