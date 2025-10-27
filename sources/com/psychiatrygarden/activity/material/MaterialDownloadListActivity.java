package com.psychiatrygarden.activity.material;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.MaterialDownloadAdapter;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class MaterialDownloadListActivity extends BaseActivity implements ResourceDownloadService.DownloadCallBack {
    private CustomEmptyView emptyView;
    private ImageView ivBack;
    private MaterialDownloadAdapter mDownloadAdapter;
    private ResourceDownloadService.DownloadBinder mDownloadBinder;
    private TextView titleView;
    private TextView tvDelete;
    private TextView tvRight;
    private boolean isEditMode = false;
    private boolean selectAll = false;
    private int fileSelectCount = 0;
    private final Gson gson = new Gson();

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
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner_color_F95843_radius_12, R.attr.downloadBtn_no_select_bg});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (isSelect) {
            this.tvDelete.setBackground(drawable);
            this.tvDelete.setTextColor(getColor(R.color.white));
        } else {
            this.tvDelete.setBackground(drawable2);
            this.tvDelete.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.forth_txt_color_night : R.color.forth_txt_color));
        }
    }

    private void dataSort() {
        List<T> data = this.mDownloadAdapter.getData();
        if (data.isEmpty()) {
            return;
        }
        Collections.sort(data, new Comparator() { // from class: com.psychiatrygarden.activity.material.a1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MaterialDownloadListActivity.lambda$dataSort$7((DownloadItem) obj, (DownloadItem) obj2);
            }
        });
    }

    private void delete() {
        if (this.fileSelectCount == 0) {
            return;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.material.MaterialDownloadListActivity.1
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (MaterialDownloadListActivity.this.isLogin()) {
                    for (T t2 : MaterialDownloadListActivity.this.mDownloadAdapter.getData()) {
                        if (t2.isSelect() && !t2.isHeader()) {
                            MaterialDownloadListActivity.this.mDownloadBinder.delete(t2.getId());
                        }
                    }
                    MaterialDownloadListActivity.this.mDownloadAdapter.notifyDataSetChanged();
                    MaterialDownloadListActivity.this.exitEditMode();
                }
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("确定删除文件？"), "取消", "确定")).show();
    }

    private void deleteComplete() {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.material.z0
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
            public final void mClickIml() {
                this.f12759a.lambda$deleteComplete$6();
            }
        }, null, "取消", "确定")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEditMode() {
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        this.isEditMode = false;
        if (this.selectAll) {
            this.selectAll = false;
        }
        findViewById(R.id.tv_delete).setVisibility(8);
        findViewById(R.id.line).setVisibility(8);
        unSelectAll();
        this.tvRight.setText("编辑");
        this.ivBack.setImageResource(z2 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
        this.titleView.setText("传输列表");
    }

    private boolean fileExist(String fileName) {
        String strSaveFilePath = saveFilePath(fileName);
        Log.d(this.TAG, "fileExist: path:" + strSaveFilePath);
        return new File(strSaveFilePath).exists();
    }

    private void initDownloadData() {
        ArrayList arrayList = new ArrayList();
        List<DownloadEntity> allDownloadTask = this.mDownloadBinder.getService().getAllDownloadTask();
        this.emptyView.showEmptyView();
        if (allDownloadTask == null || allDownloadTask.size() <= 0) {
            this.tvRight.setVisibility(8);
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
        showEditButton();
        updateDownloadGroup();
    }

    private void initDownloadService() {
        bindService(new Intent(this, (Class<?>) ResourceDownloadService.class), new ServiceConnection() { // from class: com.psychiatrygarden.activity.material.MaterialDownloadListActivity.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                MaterialDownloadListActivity.this.mDownloadBinder = (ResourceDownloadService.DownloadBinder) service;
                MaterialDownloadListActivity.this.mDownloadBinder.getService().setCallBack(MaterialDownloadListActivity.this);
                MaterialDownloadListActivity.this.startService(new Intent(MaterialDownloadListActivity.this, (Class<?>) ResourceDownloadService.class));
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
            }
        }, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$dataSort$7(DownloadItem downloadItem, DownloadItem downloadItem2) {
        if (downloadItem.getState() == 1 && downloadItem2.getState() != 1) {
            return 1;
        }
        if (downloadItem.getState() == 1 || downloadItem2.getState() != 1) {
            return Integer.compare(downloadItem.getState(), downloadItem2.getState());
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComplete$6() {
        for (T t2 : this.mDownloadAdapter.getData()) {
            if (t2.getState() == 1) {
                this.mDownloadBinder.delete(t2.getId());
            }
        }
        this.mDownloadAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 1);
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
    public /* synthetic */ void lambda$init$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
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
            this.titleView.setText(String.format("已选择%d个文件", Integer.valueOf(i3)));
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
            InformationPreviewAct.newIntent(this, id, resourceBean.getUrl(), true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        if (this.isEditMode) {
            exitEditMode();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (this.isEditMode) {
            exitEditMode();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        if (this.mDownloadAdapter.getData().isEmpty()) {
            return;
        }
        if (this.isEditMode) {
            boolean z2 = !this.selectAll;
            this.selectAll = z2;
            this.tvRight.setText(z2 ? "取消全选" : "全选");
            if (this.selectAll) {
                selectAll();
                return;
            } else {
                unSelectAll();
                return;
            }
        }
        boolean z3 = SkinManager.getCurrentSkinType(this) == 1;
        this.isEditMode = true;
        findViewById(R.id.tv_delete).setVisibility(0);
        findViewById(R.id.line).setVisibility(0);
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), z3 ? R.mipmap.ic_download_close_night : R.mipmap.ic_download_close);
        if (!z3 && drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.first_txt_color), PorterDuff.Mode.SRC_IN);
        }
        this.ivBack.setImageDrawable(drawable);
        this.tvRight.setText(this.selectAll ? "取消全选" : "全选");
        unSelectAll();
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
        this.titleView.setText(String.format("已选择%d个文件", Integer.valueOf(i2)));
        changeDelButtonBgBySelectNum(true);
    }

    private void showEditButton() {
        MaterialDownloadAdapter materialDownloadAdapter = this.mDownloadAdapter;
        if (materialDownloadAdapter != null) {
            this.tvRight.setVisibility(materialDownloadAdapter.getData().size() == 0 ? 4 : 0);
        }
    }

    private void unSelectAll() {
        for (T t2 : this.mDownloadAdapter.getData()) {
            t2.setEditMode(this.isEditMode);
            t2.setSelect(false);
        }
        this.fileSelectCount = 0;
        this.titleView.setText(String.format("已选择%d个文件", 0));
        this.mDownloadAdapter.notifyDataSetChanged();
        changeDelButtonBgBySelectNum(false);
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
        showEditButton();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.mDownloadAdapter = new MaterialDownloadAdapter(new ArrayList());
        this.tvRight.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        if (recyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            recyclerView.setItemAnimator(null);
        }
        recyclerView.setAdapter(this.mDownloadAdapter);
        TextView textView = (TextView) findViewById(R.id.tv_delete);
        this.tvDelete = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.d1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12704c.lambda$init$1(view);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewWriteBg();
        this.mDownloadAdapter.setEmptyView(this.emptyView);
        this.mDownloadAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.material.e1
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12707c.lambda$init$2(baseQuickAdapter, view, i2);
            }
        });
        this.mDownloadAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.material.f1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12710c.lambda$init$3(baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        if (this.isEditMode) {
            exitEditMode();
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        setContentView(R.layout.act_material_download_list);
        this.ivBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.titleView = (TextView) findViewById(R.id.txt_actionbar_title);
        TextView textView = (TextView) findViewById(R.id.tv_actionbar_right);
        this.tvRight = textView;
        textView.setText("编辑");
        this.titleView.setText("传输列表");
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.g1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12713c.lambda$setContentView$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tvRight.setText("编辑");
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.b1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12698c.lambda$setListenerForWidget$4(view);
            }
        });
        this.tvRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.c1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12701c.lambda$setListenerForWidget$5(view);
            }
        });
        initDownloadService();
    }
}
