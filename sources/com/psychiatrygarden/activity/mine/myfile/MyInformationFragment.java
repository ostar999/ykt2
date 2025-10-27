package com.psychiatrygarden.activity.mine.myfile;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.task.DownloadTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.adapter.MaterialDownloadAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.DownloadItem;
import com.psychiatrygarden.bean.ResourceBean;
import com.psychiatrygarden.service.ResourceDownloadService;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class MyInformationFragment extends BaseFragment implements ResourceDownloadService.DownloadCallBack {
    private CustomEmptyView emptyView;
    private ImageView ivBack;
    private MaterialDownloadAdapter mDownloadAdapter;
    private ResourceDownloadService.DownloadBinder mDownloadBinder;
    private View mLine;
    private View mTabbar;
    private TextView mTvTitle;
    private TextView tvDelete;
    private TextView tvRight;
    private final Gson gson = new Gson();
    private int fileSelectCount = 0;
    private boolean selectAll = false;
    private boolean isEditMode = false;

    private void cancelTask(DownloadTask task) {
        String key = task.getKey();
        List<T> data = this.mDownloadAdapter.getData();
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (TextUtils.equals(key, ((DownloadItem) data.get(i2)).getKey())) {
                this.mDownloadAdapter.remove((MaterialDownloadAdapter) data.get(i2));
                List<DownloadEntity> allNotCompleteTask = this.mDownloadBinder.getService().getAllNotCompleteTask();
                if (allNotCompleteTask != null && allNotCompleteTask.isEmpty()) {
                    Iterator it = data.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        DownloadItem downloadItem = (DownloadItem) it.next();
                        if (downloadItem.isHeader() && downloadItem.getState() == -1) {
                            this.mDownloadAdapter.remove((MaterialDownloadAdapter) downloadItem);
                            break;
                        }
                    }
                }
                if (data.size() == 1 && ((DownloadItem) data.get(0)).isHeader()) {
                    data.clear();
                    this.mDownloadAdapter.notifyDataSetChanged();
                }
                if (data.size() == 0) {
                    exitEditMode();
                    return;
                }
                return;
            }
        }
    }

    private void changeDelButtonBgBySelectNum(boolean isSelect) {
        FragmentActivity activity;
        int i2;
        TypedArray typedArrayObtainStyledAttributes = getActivity().getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner_color_F95843_radius_12, R.attr.downloadBtn_no_select_bg});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (isSelect) {
            this.tvDelete.setBackground(drawable);
            this.tvDelete.setTextColor(getActivity().getColor(R.color.white));
            return;
        }
        this.tvDelete.setBackground(drawable2);
        TextView textView = this.tvDelete;
        if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            activity = getActivity();
            i2 = R.color.forth_txt_color_night;
        } else {
            activity = getActivity();
            i2 = R.color.forth_txt_color;
        }
        textView.setTextColor(activity.getColor(i2));
    }

    private void dataSort() {
        List<T> data = this.mDownloadAdapter.getData();
        if (data.isEmpty()) {
            return;
        }
        Collections.sort(data, new Comparator() { // from class: com.psychiatrygarden.activity.mine.myfile.n
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MyInformationFragment.lambda$dataSort$6((DownloadItem) obj, (DownloadItem) obj2);
            }
        });
    }

    private void delete() {
        if (this.fileSelectCount == 0) {
            return;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.myfile.MyInformationFragment.2
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (MyInformationFragment.this.isLogin()) {
                    for (T t2 : MyInformationFragment.this.mDownloadAdapter.getData()) {
                        if (t2.isSelect() && !t2.isHeader()) {
                            MyInformationFragment.this.mDownloadBinder.delete(t2.getId());
                        }
                    }
                    MyInformationFragment.this.mDownloadAdapter.notifyDataSetChanged();
                    if (MyInformationFragment.this.mDownloadAdapter.getData().size() == 0) {
                        MyInformationFragment.this.mTabbar.setVisibility(8);
                        EventBus.getDefault().post("exitEdit");
                    }
                }
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("确定删除文件？"), "取消", "确定")).show();
    }

    private void deleteComplete() {
        new XPopup.Builder(getActivity()).asCustom(new DeleteDownloadDialog(getActivity(), new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.myfile.m
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
            public final void mClickIml() {
                this.f12936a.lambda$deleteComplete$5();
            }
        }, null, "取消", "确定")).show();
    }

    private void exitEditMode() {
        this.isEditMode = false;
        if (this.selectAll) {
            this.selectAll = false;
        }
        unSelectAll();
    }

    private boolean fileExist(String fileName) {
        return new File(saveFilePath(fileName)).exists();
    }

    private void initDownloadData() {
        ArrayList arrayList = new ArrayList();
        List<DownloadEntity> allDownloadTask = this.mDownloadBinder.getService().getAllDownloadTask();
        this.emptyView.showEmptyView();
        if (allDownloadTask == null || allDownloadTask.size() <= 0) {
            return;
        }
        ArrayMap<String, String> extFields = this.mDownloadBinder.getService().getExtFields();
        if (extFields != null) {
            Gson gson = new Gson();
            for (DownloadEntity downloadEntity : allDownloadTask) {
                DownloadItem downloadItem = (DownloadItem) gson.fromJson(gson.toJson(downloadEntity), DownloadItem.class);
                String fileName = downloadItem.getFileName();
                if (downloadItem.getState() != 1 || fileExist(fileName)) {
                    arrayList.add(downloadItem);
                } else {
                    this.mDownloadBinder.delete(downloadItem.getId());
                }
                String str = extFields.get(downloadEntity.getKey());
                if (!TextUtils.isEmpty(str)) {
                    ResourceBean resourceBean = (ResourceBean) gson.fromJson(str, ResourceBean.class);
                    downloadItem.setAuthor(resourceBean.getAuthor());
                    downloadItem.setDownloadCount(resourceBean.getDownloadCount());
                    downloadItem.setFileName(resourceBean.getName());
                }
            }
        }
        this.mDownloadAdapter.setList(arrayList);
        updateDownloadGroup();
    }

    private void initDownloadService() {
        getContext().bindService(new Intent(getContext(), (Class<?>) ResourceDownloadService.class), new ServiceConnection() { // from class: com.psychiatrygarden.activity.mine.myfile.MyInformationFragment.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyInformationFragment.this.mDownloadBinder = (ResourceDownloadService.DownloadBinder) service;
                MyInformationFragment.this.mDownloadBinder.getService().setCallBack(MyInformationFragment.this);
                MyInformationFragment.this.getContext().startService(new Intent(MyInformationFragment.this.getContext(), (Class<?>) ResourceDownloadService.class));
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
            }
        }, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$dataSort$6(DownloadItem downloadItem, DownloadItem downloadItem2) {
        if (downloadItem.getState() == 1 && downloadItem2.getState() != 1) {
            return 1;
        }
        if (downloadItem.getState() == 1 || downloadItem2.getState() != 1) {
            return Integer.compare(downloadItem.getState(), downloadItem2.getState());
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComplete$5() {
        for (T t2 : this.mDownloadAdapter.getData()) {
            if (t2.getState() == 1) {
                this.mDownloadBinder.delete(t2.getId());
            }
        }
        this.mDownloadAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        if (view.getId() == R.id.tv_operate) {
            int iIntValue = ((Integer) view.getTag()).intValue();
            if (iIntValue == 1) {
                deleteComplete();
            } else if (iIntValue == 2) {
                this.mDownloadBinder.resumeAllTask();
            } else {
                this.mDownloadBinder.stopAllTask();
            }
            this.mDownloadAdapter.notifyItemChanged(i2, 1);
            return;
        }
        if (view.getId() == R.id.iv_operate) {
            DownloadItem downloadItem = (DownloadItem) this.mDownloadAdapter.getItem(i2);
            if (downloadItem.getState() == 0) {
                resetState(i2);
                this.mDownloadBinder.retryTask(downloadItem.getId());
                return;
            }
            if (downloadItem.getState() == 2) {
                resetState(i2);
                this.mDownloadBinder.resumeTask(downloadItem.getId());
            } else if (downloadItem.getState() == 3) {
                resetState(i2);
                this.mDownloadBinder.resumeTask(downloadItem.getId());
            } else if (downloadItem.getState() == 4) {
                downloadItem.setState(2);
                baseQuickAdapter.notifyItemChanged(i2);
                this.mDownloadBinder.pauseTask(downloadItem.getId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.isEditMode) {
            ((DownloadItem) this.mDownloadAdapter.getItem(i2)).setSelect(!((DownloadItem) this.mDownloadAdapter.getItem(i2)).isSelect());
            baseQuickAdapter.notifyItemChanged(i2);
            int i3 = 0;
            for (T t2 : this.mDownloadAdapter.getData()) {
                if (!t2.isHeader()) {
                    i3 += t2.isSelect() ? 1 : 0;
                }
            }
            this.fileSelectCount = i3;
            this.mTvTitle.setText(String.format("已选择%d个文件", Integer.valueOf(i3)));
            changeDelButtonBgBySelectNum(i3 > 0);
            return;
        }
        String str = ((DownloadItem) this.mDownloadAdapter.getItem(i2)).getStr();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            ResourceBean resourceBean = (ResourceBean) new Gson().fromJson(str, ResourceBean.class);
            resourceBean.getAuthor();
            String id = resourceBean.getId();
            resourceBean.getShowFileName();
            resourceBean.getDownloadCount();
            InformationPreviewAct.newIntent(getContext(), id, resourceBean.getUrl(), true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        this.isEditMode = false;
        if (this.selectAll) {
            this.selectAll = false;
        }
        unSelectAll();
        this.mTabbar.setVisibility(8);
        this.tvDelete.setVisibility(8);
        this.mLine.setVisibility(8);
        EventBus.getDefault().post("exitEdit");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        if (this.mDownloadAdapter.getData().isEmpty()) {
            return;
        }
        boolean z2 = !this.selectAll;
        this.selectAll = z2;
        this.tvRight.setText(z2 ? "取消全选" : "全选");
        if (this.selectAll) {
            selectAll();
        } else {
            unSelectAll();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void resetState(int position) {
        DownloadItem downloadItem = (DownloadItem) this.mDownloadAdapter.getItem(position);
        List<DownloadEntity> allNotCompleteTask = this.mDownloadBinder.getService().getAllNotCompleteTask();
        if (allNotCompleteTask != null && allNotCompleteTask.size() > 0) {
            if (allNotCompleteTask.size() < 2) {
                downloadItem.setState(4);
            } else {
                downloadItem.setState(3);
            }
        }
        this.mDownloadAdapter.notifyItemChanged(position);
    }

    private String saveFilePath(String fileName) {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string + str + fileName;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string + str + fileName;
    }

    private void selectAll() {
        List<T> data = this.mDownloadAdapter.getData();
        for (T t2 : data) {
            t2.setEditMode(this.isEditMode);
            t2.setSelect(true);
        }
        this.mDownloadAdapter.notifyDataSetChanged();
        int i2 = 0;
        for (T t3 : data) {
            if (fileExist(t3.getFileName())) {
                i2 += t3.isSelect() ? 1 : 0;
            }
        }
        this.fileSelectCount = i2;
        this.mTvTitle.setText(String.format("已选择%d个文件", Integer.valueOf(i2)));
        changeDelButtonBgBySelectNum(true);
    }

    private void unSelectAll() {
        for (T t2 : this.mDownloadAdapter.getData()) {
            t2.setEditMode(this.isEditMode);
            t2.setSelect(false);
        }
        this.fileSelectCount = 0;
        this.mDownloadAdapter.notifyDataSetChanged();
        changeDelButtonBgBySelectNum(false);
        this.mTvTitle.setText(String.format("已选择%d个文件", 0));
    }

    private void updateDownloadGroup() {
        dataSort();
        List<T> data = this.mDownloadAdapter.getData();
        ArrayList<DownloadItem> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (T t2 : data) {
            if (t2.getState() != 1 && !t2.isHeader()) {
                arrayList.add(t2);
            } else if (!t2.isHeader()) {
                arrayList2.add(t2);
            }
        }
        if (arrayList.size() > 0) {
            DownloadItem downloadItem = new DownloadItem();
            downloadItem.setHeader(true);
            downloadItem.setState(-1);
            downloadItem.setStateCount(arrayList.size());
            arrayList.add(0, downloadItem);
            int i2 = 0;
            int i3 = 0;
            for (DownloadItem downloadItem2 : arrayList) {
                if (downloadItem2.getState() == 2) {
                    i2++;
                }
                if (downloadItem2.getState() == 4) {
                    i3++;
                }
            }
            if (i2 == arrayList.size() - 1) {
                downloadItem.setState(2);
            }
            if (i3 > 0) {
                downloadItem.setState(4);
            }
        }
        if (arrayList2.size() > 0) {
            DownloadItem downloadItem3 = new DownloadItem();
            downloadItem3.setHeader(true);
            downloadItem3.setStateCount(arrayList2.size());
            downloadItem3.setState(1);
            arrayList2.add(0, downloadItem3);
        }
        ArrayList arrayList3 = new ArrayList();
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        this.mDownloadAdapter.setList(arrayList3);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.act_material_download_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        View view = holder.get(R.id.tabbar);
        this.mTabbar = view;
        view.setVisibility(8);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.rvList);
        this.tvDelete = (TextView) holder.get(R.id.tv_delete);
        this.ivBack = (ImageView) holder.get(R.id.iv_actionbar_back);
        this.mTvTitle = (TextView) holder.get(R.id.txt_actionbar_title);
        this.tvRight = (TextView) holder.get(R.id.tv_actionbar_right);
        this.mLine = holder.get(R.id.line);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewWriteBg();
        MaterialDownloadAdapter materialDownloadAdapter = new MaterialDownloadAdapter(new ArrayList());
        this.mDownloadAdapter = materialDownloadAdapter;
        materialDownloadAdapter.setEmptyView(this.emptyView);
        if (recyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            recyclerView.setItemAnimator(null);
        }
        recyclerView.setAdapter(this.mDownloadAdapter);
        this.mTvTitle.setText(String.format("已选择%d个文件", 0));
        boolean z2 = SkinManager.getCurrentSkinType(getActivity()) == 1;
        Drawable drawable = ContextCompat.getDrawable(getContext(), z2 ? R.mipmap.ic_download_close_night : R.mipmap.ic_download_close);
        if (!z2 && drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.first_txt_color), PorterDuff.Mode.SRC_IN);
        }
        this.ivBack.setImageDrawable(drawable);
        this.tvRight.setText("全选");
        this.mDownloadAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.h
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i2) {
                this.f12931c.lambda$initViews$0(baseQuickAdapter, view2, i2);
            }
        });
        this.mDownloadAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.i
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i2) {
                this.f12932c.lambda$initViews$1(baseQuickAdapter, view2, i2);
            }
        });
        this.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12933c.lambda$initViews$2(view2);
            }
        });
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12934c.lambda$initViews$3(view2);
            }
        });
        this.tvRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12935c.lambda$initViews$4(view2);
            }
        });
        initDownloadService();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (!str.equals("openEdit")) {
            if (str.equals("exitEdit")) {
                unSelectAll();
            }
        } else {
            this.isEditMode = true;
            this.mTabbar.setVisibility(0);
            unSelectAll();
            this.tvDelete.setVisibility(0);
            this.mLine.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.service.ResourceDownloadService.DownloadCallBack
    public void onServiceStartSuccess() {
        initDownloadData();
    }

    @Override // com.psychiatrygarden.service.ResourceDownloadService.DownloadCallBack
    public void onStatusChange(DownloadTask task) {
        int i2;
        boolean z2;
        if (task == null) {
            return;
        }
        if (task.getState() == 7) {
            cancelTask(task);
            updateDownloadGroup();
            return;
        }
        List<T> data = this.mDownloadAdapter.getData();
        Iterator it = data.iterator();
        while (true) {
            if (it.hasNext()) {
                if (TextUtils.equals(((DownloadItem) it.next()).getKey(), task.getKey())) {
                    z2 = true;
                    break;
                }
            } else {
                z2 = false;
                break;
            }
        }
        if (data.isEmpty() || !z2) {
            Gson gson = this.gson;
            DownloadItem downloadItem = (DownloadItem) gson.fromJson(gson.toJson(task.getDownloadEntity()), DownloadItem.class);
            ResourceBean resourceBean = (ResourceBean) this.gson.fromJson(task.getExtendField(), ResourceBean.class);
            downloadItem.setAuthor(resourceBean.getAuthor());
            downloadItem.setConveredCurrentProgress(task.getConvertCurrentProgress());
            downloadItem.setDownloadCount(resourceBean.getDownloadCount());
            data.add(downloadItem);
            dataSort();
            updateDownloadGroup();
            return;
        }
        for (i2 = 0; i2 < data.size(); i2++) {
            DownloadItem downloadItem2 = (DownloadItem) data.get(i2);
            if (TextUtils.equals(downloadItem2.getKey(), task.getKey())) {
                downloadItem2.setState(task.getState());
                downloadItem2.setConveredCurrentProgress(task.getConvertCurrentProgress());
                downloadItem2.setPercent(task.getPercent());
                downloadItem2.setCurrentProgress(task.getCurrentProgress());
                downloadItem2.setCompleteTime(task.getEntity().getCompleteTime());
                downloadItem2.setComplete(task.isComplete());
                downloadItem2.setSpeed(task.getSpeed());
                downloadItem2.setConvertSpeed(task.getConvertSpeed());
                downloadItem2.setConvertFileSize(task.getConvertFileSize());
                if (task.getState() == 1) {
                    updateDownloadGroup();
                    return;
                }
                if (task.getState() == 2) {
                    updateDownloadGroup();
                    return;
                } else if (task.getState() == 4) {
                    updateDownloadGroup();
                    return;
                } else {
                    this.mDownloadAdapter.notifyItemChanged(i2);
                    return;
                }
            }
        }
    }
}
