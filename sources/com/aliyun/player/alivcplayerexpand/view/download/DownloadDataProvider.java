package com.aliyun.player.alivcplayerexpand.view.download;

import android.content.Context;
import com.aliyun.player.alivcplayerexpand.util.database.LoadDbDatasListener;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DownloadDataProvider {
    private static volatile DownloadDataProvider instance;
    private List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos;
    private WeakReference<Context> contextWeakReference;
    private AliyunDownloadManager downloadManager;

    public DownloadDataProvider(Context context) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        this.contextWeakReference = weakReference;
        this.downloadManager = AliyunDownloadManager.getInstance(weakReference.get());
    }

    public static DownloadDataProvider getSingleton(Context context) {
        if (instance == null) {
            synchronized (DownloadDataProvider.class) {
                if (instance == null) {
                    instance = new DownloadDataProvider(context);
                }
            }
        }
        return instance;
    }

    public void addDownloadMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        List<AliyunDownloadMediaInfo> list;
        if (hasAdded(aliyunDownloadMediaInfo) || (list = this.aliyunDownloadMediaInfos) == null) {
            return;
        }
        list.add(aliyunDownloadMediaInfo);
    }

    public void deleteAllDownloadInfo(ArrayList<AlivcDownloadMediaInfo> arrayList) {
        List<AliyunDownloadMediaInfo> list = this.aliyunDownloadMediaInfos;
        if (list != null) {
            list.clear();
        }
        Iterator<AlivcDownloadMediaInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            deleteDownloadMediaInfo(it.next().getAliyunDownloadMediaInfo());
        }
    }

    public void deleteDownloadMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (this.aliyunDownloadMediaInfos != null) {
            this.downloadManager.deleteFile(aliyunDownloadMediaInfo);
            this.aliyunDownloadMediaInfos.remove(aliyunDownloadMediaInfo);
        }
    }

    public void deleteDumpData() {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : this.aliyunDownloadMediaInfos) {
            if (hashSet.add(aliyunDownloadMediaInfo)) {
                arrayList.add(aliyunDownloadMediaInfo);
            }
        }
        this.aliyunDownloadMediaInfos.clear();
        this.aliyunDownloadMediaInfos.addAll(arrayList);
    }

    public List<AliyunDownloadMediaInfo> getAllDownloadMediaInfo() {
        if (this.aliyunDownloadMediaInfos == null) {
            this.aliyunDownloadMediaInfos = new ArrayList();
        }
        return this.aliyunDownloadMediaInfos;
    }

    public boolean hasAdded(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 : this.aliyunDownloadMediaInfos) {
            if (aliyunDownloadMediaInfo != null && aliyunDownloadMediaInfo.getFormat().equals(aliyunDownloadMediaInfo2.getFormat()) && aliyunDownloadMediaInfo.getQuality().equals(aliyunDownloadMediaInfo2.getQuality()) && aliyunDownloadMediaInfo.getVid().equals(aliyunDownloadMediaInfo2.getVid()) && aliyunDownloadMediaInfo.isEncripted() == aliyunDownloadMediaInfo2.isEncripted()) {
                return true;
            }
        }
        return false;
    }

    public void restoreMediaInfo(final LoadDbDatasListener loadDbDatasListener) {
        this.aliyunDownloadMediaInfos = new ArrayList();
        this.downloadManager.findDatasByDb(new LoadDbDatasListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.DownloadDataProvider.1
            @Override // com.aliyun.player.alivcplayerexpand.util.database.LoadDbDatasListener
            public void onLoadSuccess(List<AliyunDownloadMediaInfo> list) {
                DownloadDataProvider.this.aliyunDownloadMediaInfos.addAll(list);
                DownloadDataProvider.this.deleteDumpData();
                loadDbDatasListener.onLoadSuccess(DownloadDataProvider.this.aliyunDownloadMediaInfos);
            }
        });
    }
}
