package com.aliyun.player.alivcplayerexpand.view.download;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.util.FixedToastUtils;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.view.download.DownloadSection;
import com.aliyun.player.alivcplayerexpand.view.sectionlist.SectionedRecyclerViewAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DownloadView extends FrameLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos;
    private ArrayList<AlivcDownloadMediaInfo> alivcDownloadingMediaInfos;
    private CheckBox cbAllDownloadCheck;
    private WeakReference<Context> context;
    private LinearLayout downloadEmptyView;
    private RecyclerView downloadListView;
    private boolean isEditeState;
    private boolean itemFollowCheckBox;
    private ImageView ivCloseEdit;
    private ImageView ivDownloadDelete;
    private LinearLayoutManager linearLayoutManager;
    private OnDownloadItemClickListener onDownloadItemClickListener;
    private OnDownloadViewListener onDownloadViewListener;
    private OnNotifyItemCheckedListener onItemCheckAllListener;
    private RelativeLayout rlDownloadManagerContent;
    private RelativeLayout rlDownloadManagerEdit;
    private RelativeLayout rlDownloadManagerEditDefault;
    private DownloadSection section;
    private SectionedRecyclerViewAdapter sectionAdapter;

    public interface OnDownloadItemClickListener {
        void onDownloadedItemClick(int i2);

        void onDownloadingItemClick(ArrayList<AlivcDownloadMediaInfo> arrayList, int i2);
    }

    public interface OnDownloadViewListener {
        void onDeleteDownloadInfo(ArrayList<AlivcDownloadMediaInfo> arrayList);

        void onStart(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);

        void onStop(AliyunDownloadMediaInfo aliyunDownloadMediaInfo);
    }

    public interface OnNotifyItemCheckedListener {
        void onItemCheck(boolean z2);
    }

    public DownloadView(@NonNull Context context) {
        super(context);
        this.itemFollowCheckBox = true;
        this.isEditeState = false;
        this.context = new WeakReference<>(context);
        initView();
    }

    private void addSection(String str, String str2, final ArrayList<AlivcDownloadMediaInfo> arrayList) {
        if (this.sectionAdapter.getSection(str) == null) {
            DownloadSection downloadSection = new DownloadSection(this.context.get(), str, str2, arrayList);
            this.section = downloadSection;
            downloadSection.setOnSectionItemClickListener(new DownloadSection.OnSectionItemClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.DownloadView.2
                @Override // com.aliyun.player.alivcplayerexpand.view.download.DownloadSection.OnSectionItemClickListener
                public void onItemClick(int i2, String str3) {
                    int positionInSection = DownloadView.this.sectionAdapter.getPositionInSection(i2);
                    if (str3.equals(DownloadSection.DOWNLOADING_TAG)) {
                        AlivcDownloadMediaInfo alivcDownloadMediaInfo = (AlivcDownloadMediaInfo) DownloadView.this.alivcDownloadingMediaInfos.get(positionInSection);
                        if (alivcDownloadMediaInfo.isEditState()) {
                            alivcDownloadMediaInfo.setCheckedState(!alivcDownloadMediaInfo.isCheckedState());
                            DownloadView.this.sectionAdapter.notifyItemChangedInSection(str3, positionInSection);
                            return;
                        }
                    } else if (str3.equals(DownloadSection.DOWNLOADED_TAG)) {
                        AlivcDownloadMediaInfo alivcDownloadMediaInfo2 = (AlivcDownloadMediaInfo) arrayList.get(positionInSection);
                        if (alivcDownloadMediaInfo2.isEditState()) {
                            alivcDownloadMediaInfo2.setCheckedState(!alivcDownloadMediaInfo2.isCheckedState());
                            DownloadView.this.sectionAdapter.notifyItemChangedInSection(str3, positionInSection);
                            return;
                        }
                    }
                    if (str3.equals(DownloadSection.DOWNLOADING_TAG)) {
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = ((AlivcDownloadMediaInfo) DownloadView.this.alivcDownloadingMediaInfos.get(positionInSection)).getAliyunDownloadMediaInfo();
                        if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Start) {
                            if (DownloadView.this.onDownloadViewListener != null) {
                                DownloadView.this.onDownloadViewListener.onStop(aliyunDownloadMediaInfo);
                                DownloadView.this.sectionAdapter.notifyItemChangedInSection(str3, positionInSection);
                            }
                        } else if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Stop && aliyunDownloadMediaInfo.getStatus() != AliyunDownloadMediaInfo.Status.Complete) {
                            DownloadView.this.onDownloadViewListener.onStart(aliyunDownloadMediaInfo);
                            DownloadView.this.sectionAdapter.notifyItemChangedInSection(str3, positionInSection);
                        }
                    }
                    if (str3.equals(DownloadSection.DOWNLOADED_TAG) && DownloadView.this.onDownloadItemClickListener != null) {
                        DownloadView.this.onDownloadItemClickListener.onDownloadedItemClick(positionInSection);
                    }
                    if (!str3.equals(DownloadSection.DOWNLOADING_TAG) || DownloadView.this.onDownloadItemClickListener == null) {
                        return;
                    }
                    DownloadView.this.onDownloadItemClickListener.onDownloadingItemClick(arrayList, positionInSection);
                }
            });
            this.sectionAdapter.addSection(str, this.section);
        }
    }

    private void initView() {
        LayoutInflater.from(this.context.get()).inflate(R.layout.alivc_download_view_layout, (ViewGroup) this, true);
        this.downloadEmptyView = (LinearLayout) findViewById(R.id.alivc_layout_empty_view);
        this.downloadListView = (RecyclerView) findViewById(R.id.download_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.linearLayoutManager = linearLayoutManager;
        this.downloadListView.setLayoutManager(linearLayoutManager);
        this.rlDownloadManagerContent = (RelativeLayout) findViewById(R.id.rl_download_manager_content);
        this.rlDownloadManagerEdit = (RelativeLayout) findViewById(R.id.rl_download_manager_edit);
        this.rlDownloadManagerEditDefault = (RelativeLayout) findViewById(R.id.rl_download_manager_edit_default);
        this.ivDownloadDelete = (ImageView) findViewById(R.id.iv_download_delete);
        this.ivCloseEdit = (ImageView) findViewById(R.id.iv_close_edit);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_all_select);
        this.cbAllDownloadCheck = checkBox;
        checkBox.setOnCheckedChangeListener(this);
        this.ivDownloadDelete.setOnClickListener(this);
        this.rlDownloadManagerEditDefault.setOnClickListener(this);
        this.ivCloseEdit.setOnClickListener(this);
        this.sectionAdapter = new SectionedRecyclerViewAdapter();
        this.alivcDownloadMediaInfos = new ArrayList<>();
        this.alivcDownloadingMediaInfos = new ArrayList<>();
        this.downloadListView.setItemAnimator(null);
        this.downloadListView.setAdapter(this.sectionAdapter);
        this.sectionAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.aliyun.player.alivcplayerexpand.view.download.DownloadView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3) {
                super.onItemRangeChanged(i2, i3);
                if (DownloadView.this.isEditeState()) {
                    boolean zIsChecked = DownloadView.this.cbAllDownloadCheck.isChecked();
                    Iterator it = DownloadView.this.alivcDownloadMediaInfos.iterator();
                    while (it.hasNext()) {
                        if (!((AlivcDownloadMediaInfo) it.next()).isCheckedState()) {
                            if (zIsChecked) {
                                DownloadView.this.itemFollowCheckBox = false;
                                DownloadView.this.cbAllDownloadCheck.setChecked(false);
                                return;
                            }
                            return;
                        }
                    }
                    Iterator it2 = DownloadView.this.alivcDownloadingMediaInfos.iterator();
                    while (it2.hasNext()) {
                        if (!((AlivcDownloadMediaInfo) it2.next()).isCheckedState()) {
                            if (zIsChecked) {
                                DownloadView.this.itemFollowCheckBox = false;
                                DownloadView.this.cbAllDownloadCheck.setChecked(false);
                                return;
                            }
                            return;
                        }
                    }
                    DownloadView.this.cbAllDownloadCheck.setChecked(true);
                }
            }
        });
    }

    public void addAllDownloadMediaInfo(List<AliyunDownloadMediaInfo> list) {
        if (list == null) {
            return;
        }
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : list) {
            AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo.getStatus();
            AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Complete;
            String str = status == status2 ? DownloadSection.DOWNLOADED_TAG : DownloadSection.DOWNLOADING_TAG;
            String string = aliyunDownloadMediaInfo.getStatus() == status2 ? getResources().getString(R.string.already_downloaded) : getResources().getString(R.string.download_caching);
            if (aliyunDownloadMediaInfo.getStatus() == status2 || (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Start && aliyunDownloadMediaInfo.getProgress() == 100)) {
                AlivcDownloadMediaInfo alivcDownloadMediaInfo = new AlivcDownloadMediaInfo();
                alivcDownloadMediaInfo.setAliyunDownloadMediaInfo(aliyunDownloadMediaInfo);
                this.alivcDownloadMediaInfos.add(0, alivcDownloadMediaInfo);
                addSection(str, string, this.alivcDownloadMediaInfos);
            } else {
                AlivcDownloadMediaInfo alivcDownloadMediaInfo2 = new AlivcDownloadMediaInfo();
                alivcDownloadMediaInfo2.setAliyunDownloadMediaInfo(aliyunDownloadMediaInfo);
                this.alivcDownloadingMediaInfos.add(0, alivcDownloadMediaInfo2);
                addSection(str, string, this.alivcDownloadingMediaInfos);
            }
        }
        this.sectionAdapter.notifyDataSetChanged();
        showDownloadContentView();
    }

    public void addDownloadMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (hasAdded(aliyunDownloadMediaInfo)) {
            return;
        }
        AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo.getStatus();
        AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Complete;
        String str = status == status2 ? DownloadSection.DOWNLOADED_TAG : DownloadSection.DOWNLOADING_TAG;
        String string = aliyunDownloadMediaInfo.getStatus() == status2 ? getResources().getString(R.string.already_downloaded) : getResources().getString(R.string.download_caching);
        if (aliyunDownloadMediaInfo.getStatus() == status2) {
            AlivcDownloadMediaInfo alivcDownloadMediaInfo = new AlivcDownloadMediaInfo();
            alivcDownloadMediaInfo.setEditState(this.isEditeState);
            alivcDownloadMediaInfo.setAliyunDownloadMediaInfo(aliyunDownloadMediaInfo);
            this.alivcDownloadMediaInfos.add(0, alivcDownloadMediaInfo);
            addSection(str, string, this.alivcDownloadMediaInfos);
        } else {
            AlivcDownloadMediaInfo alivcDownloadMediaInfo2 = new AlivcDownloadMediaInfo();
            alivcDownloadMediaInfo2.setEditState(this.isEditeState);
            alivcDownloadMediaInfo2.setAliyunDownloadMediaInfo(aliyunDownloadMediaInfo);
            this.alivcDownloadingMediaInfos.add(0, alivcDownloadMediaInfo2);
            addSection(str, string, this.alivcDownloadingMediaInfos);
        }
        showDownloadContentView();
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void changeDownloadEditState(boolean z2) {
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadMediaInfos.iterator();
        while (it.hasNext()) {
            AlivcDownloadMediaInfo next = it.next();
            if (next != null) {
                next.setEditState(z2);
            }
        }
        Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadingMediaInfos.iterator();
        while (it2.hasNext()) {
            AlivcDownloadMediaInfo next2 = it2.next();
            if (next2 != null) {
                next2.setEditState(z2);
            }
        }
        this.rlDownloadManagerEdit.setVisibility(z2 ? 0 : 8);
        this.rlDownloadManagerEditDefault.setVisibility(z2 ? 8 : 0);
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void deleteDownloadInfo() {
        changeDownloadEditState(false);
        this.isEditeState = false;
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (it.hasNext()) {
            if (it.next().isCheckedState()) {
                it.remove();
            }
        }
        Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadMediaInfos.iterator();
        while (it2.hasNext()) {
            if (it2.next().isCheckedState()) {
                it2.remove();
            }
        }
        if (this.alivcDownloadingMediaInfos.size() <= 0) {
            this.sectionAdapter.removeSection(DownloadSection.DOWNLOADING_TAG);
        }
        if (this.alivcDownloadMediaInfos.size() <= 0) {
            this.sectionAdapter.removeSection(DownloadSection.DOWNLOADED_TAG);
        }
        this.sectionAdapter.notifyDataSetChanged();
        showDownloadContentView();
    }

    public ArrayList<AlivcDownloadMediaInfo> getAllDownloadMediaInfo() {
        if (this.alivcDownloadMediaInfos == null) {
            this.alivcDownloadMediaInfos = new ArrayList<>();
        }
        return this.alivcDownloadMediaInfos;
    }

    public ArrayList<AlivcDownloadMediaInfo> getDownloadMediaInfo() {
        ArrayList<AlivcDownloadMediaInfo> arrayList = new ArrayList<>();
        if (this.alivcDownloadMediaInfos == null) {
            this.alivcDownloadMediaInfos = new ArrayList<>();
        }
        if (this.alivcDownloadingMediaInfos == null) {
            this.alivcDownloadMediaInfos = new ArrayList<>();
        }
        arrayList.addAll(this.alivcDownloadMediaInfos);
        arrayList.addAll(this.alivcDownloadingMediaInfos);
        return arrayList;
    }

    public boolean hasAdded(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) throws IllegalAccessException, IllegalArgumentException {
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (it.hasNext()) {
            AlivcDownloadMediaInfo next = it.next();
            if (aliyunDownloadMediaInfo.getFormat().equals(next.getAliyunDownloadMediaInfo().getFormat()) && aliyunDownloadMediaInfo.getQuality().equals(next.getAliyunDownloadMediaInfo().getQuality()) && aliyunDownloadMediaInfo.getVid().equals(next.getAliyunDownloadMediaInfo().getVid()) && aliyunDownloadMediaInfo.isEncripted() == next.getAliyunDownloadMediaInfo().isEncripted()) {
                Context context = this.context.get();
                if (context != null) {
                    FixedToastUtils.show(context, context.getResources().getString(R.string.alivc_video_downloading_tips));
                }
                return true;
            }
        }
        Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadMediaInfos.iterator();
        while (it2.hasNext()) {
            AlivcDownloadMediaInfo next2 = it2.next();
            if (aliyunDownloadMediaInfo.getFormat().equals(next2.getAliyunDownloadMediaInfo().getFormat()) && aliyunDownloadMediaInfo.getQuality().equals(next2.getAliyunDownloadMediaInfo().getQuality()) && aliyunDownloadMediaInfo.getVid().equals(next2.getAliyunDownloadMediaInfo().getVid()) && aliyunDownloadMediaInfo.isEncripted() == next2.getAliyunDownloadMediaInfo().isEncripted()) {
                Context context2 = this.context.get();
                if (context2 != null) {
                    FixedToastUtils.show(context2, context2.getResources().getString(R.string.alivc_video_download_finish_tips));
                }
                return true;
            }
        }
        return false;
    }

    public boolean isEditeState() {
        return this.isEditeState;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
        if (compoundButton.getId() == R.id.checkbox_all_select) {
            if (!this.itemFollowCheckBox) {
                this.itemFollowCheckBox = true;
                return;
            }
            Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadMediaInfos.iterator();
            while (it.hasNext()) {
                AlivcDownloadMediaInfo next = it.next();
                if (next != null) {
                    next.setCheckedState(z2);
                }
            }
            Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadingMediaInfos.iterator();
            while (it2.hasNext()) {
                AlivcDownloadMediaInfo next2 = it2.next();
                if (next2 != null) {
                    next2.setCheckedState(z2);
                }
            }
            this.sectionAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws IllegalAccessException, IllegalArgumentException {
        int id = view.getId();
        if (id == R.id.rl_download_manager_edit_default) {
            changeDownloadEditState(true);
            this.isEditeState = true;
            this.cbAllDownloadCheck.setChecked(false);
            return;
        }
        if (id != R.id.iv_download_delete) {
            if (id == R.id.iv_close_edit) {
                setEditeState(false);
                changeDownloadEditState(false);
                this.isEditeState = false;
                return;
            }
            return;
        }
        ArrayList<AlivcDownloadMediaInfo> arrayList = new ArrayList<>();
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (it.hasNext()) {
            AlivcDownloadMediaInfo next = it.next();
            if (next.isCheckedState()) {
                arrayList.add(next);
            }
        }
        Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadMediaInfos.iterator();
        while (it2.hasNext()) {
            AlivcDownloadMediaInfo next2 = it2.next();
            if (next2.isCheckedState()) {
                arrayList.add(next2);
            }
        }
        if (this.onDownloadViewListener != null) {
            if (arrayList.size() > 0) {
                this.onDownloadViewListener.onDeleteDownloadInfo(arrayList);
                return;
            }
            Context context = this.context.get();
            if (context != null) {
                FixedToastUtils.show(context, getResources().getString(R.string.alivc_not_choose_video));
            }
        }
    }

    public void removeDownloadingMeiaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AlivcDownloadMediaInfo next = it.next();
            next.getAliyunDownloadMediaInfo();
            if (next.getAliyunDownloadMediaInfo().getVid().equals(aliyunDownloadMediaInfo.getVid()) && next.getAliyunDownloadMediaInfo().getFormat().equals(aliyunDownloadMediaInfo.getFormat()) && next.getAliyunDownloadMediaInfo().getQuality().equals(aliyunDownloadMediaInfo.getQuality())) {
                this.alivcDownloadingMediaInfos.remove(next);
                break;
            }
        }
        if (this.alivcDownloadingMediaInfos.size() <= 0) {
            this.sectionAdapter.removeSection(DownloadSection.DOWNLOADING_TAG);
        }
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void setEditeState(boolean z2) {
        this.isEditeState = z2;
    }

    public void setOnDownloadViewListener(OnDownloadViewListener onDownloadViewListener) {
        this.onDownloadViewListener = onDownloadViewListener;
    }

    public void setOnDownloadedItemClickListener(OnDownloadItemClickListener onDownloadItemClickListener) {
        this.onDownloadItemClickListener = onDownloadItemClickListener;
    }

    public void setOnItemCheckAllListener(OnNotifyItemCheckedListener onNotifyItemCheckedListener) {
        this.onItemCheckAllListener = onNotifyItemCheckedListener;
    }

    public void showDownloadContentView() {
        if (this.alivcDownloadMediaInfos.size() > 0 || this.alivcDownloadingMediaInfos.size() > 0) {
            this.downloadEmptyView.setVisibility(8);
            this.rlDownloadManagerContent.setVisibility(0);
        } else if (this.alivcDownloadMediaInfos.size() <= 0 || this.alivcDownloadingMediaInfos.size() <= 0) {
            this.downloadEmptyView.setVisibility(0);
            this.rlDownloadManagerContent.setVisibility(8);
        }
        if (isEditeState()) {
            this.rlDownloadManagerEdit.setVisibility(0);
            this.rlDownloadManagerEditDefault.setVisibility(8);
        } else {
            this.rlDownloadManagerEdit.setVisibility(8);
            this.rlDownloadManagerEditDefault.setVisibility(0);
        }
    }

    public void updateInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AlivcDownloadMediaInfo next;
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.getAliyunDownloadMediaInfo().getVid().equals(aliyunDownloadMediaInfo.getVid()) && next.getAliyunDownloadMediaInfo().getQuality().equals(aliyunDownloadMediaInfo.getQuality()) && next.getAliyunDownloadMediaInfo().getFormat().equals(aliyunDownloadMediaInfo.getFormat())) {
                break;
            }
        }
        if (next != null) {
            next.getAliyunDownloadMediaInfo().setProgress(aliyunDownloadMediaInfo.getProgress());
            next.getAliyunDownloadMediaInfo().setStatus(aliyunDownloadMediaInfo.getStatus());
        }
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void updateInfoByComplete(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
            removeDownloadingMeiaInfo(aliyunDownloadMediaInfo);
            addDownloadMediaInfo(aliyunDownloadMediaInfo);
        }
        showDownloadContentView();
        this.sectionAdapter.notifyDataSetChanged();
    }

    public void updateInfoByError(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (aliyunDownloadMediaInfo == null || aliyunDownloadMediaInfo.getStatus() != AliyunDownloadMediaInfo.Status.Error) {
            return;
        }
        this.sectionAdapter.notifyDataSetChanged();
        showDownloadContentView();
    }

    public void updateProgress() {
    }

    public DownloadView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.itemFollowCheckBox = true;
        this.isEditeState = false;
        this.context = new WeakReference<>(context);
        initView();
    }

    public DownloadView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.itemFollowCheckBox = true;
        this.isEditeState = false;
        this.context = new WeakReference<>(context);
        initView();
    }

    public void deleteDownloadInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        changeDownloadEditState(false);
        this.isEditeState = false;
        Iterator<AlivcDownloadMediaInfo> it = this.alivcDownloadingMediaInfos.iterator();
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 = it.next().getAliyunDownloadMediaInfo();
            if (aliyunDownloadMediaInfo2.getQuality().equals(aliyunDownloadMediaInfo.getQuality()) && aliyunDownloadMediaInfo2.getFormat().equals(aliyunDownloadMediaInfo.getFormat()) && aliyunDownloadMediaInfo2.getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                it.remove();
            }
        }
        Iterator<AlivcDownloadMediaInfo> it2 = this.alivcDownloadMediaInfos.iterator();
        while (it2.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo3 = it2.next().getAliyunDownloadMediaInfo();
            if (aliyunDownloadMediaInfo3.getQuality().equals(aliyunDownloadMediaInfo.getQuality()) && aliyunDownloadMediaInfo3.getFormat().equals(aliyunDownloadMediaInfo.getFormat()) && aliyunDownloadMediaInfo3.getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                it2.remove();
            }
        }
        if (this.alivcDownloadingMediaInfos.size() <= 0) {
            this.sectionAdapter.removeSection(DownloadSection.DOWNLOADING_TAG);
        }
        if (this.alivcDownloadMediaInfos.size() <= 0) {
            this.sectionAdapter.removeSection(DownloadSection.DOWNLOADED_TAG);
        }
        this.sectionAdapter.notifyDataSetChanged();
        showDownloadContentView();
    }
}
