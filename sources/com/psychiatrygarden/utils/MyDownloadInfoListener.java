package com.psychiatrygarden.utils;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.bean.EventBusMessage;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class MyDownloadInfoListener implements AliyunDownloadInfoListener {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCompletionDemo$3(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.database.getVideoDownDao().updataModel(aliyunDownloadMediaInfo.getQuality(), aliyunDownloadMediaInfo.getProgress() + "", aliyunDownloadMediaInfo.getSavePath(), 5, (int) aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getFormat(), aliyunDownloadMediaInfo.isEncripted(), aliyunDownloadMediaInfo.getVid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onError$0() {
        NewToast.showShort(ProjectApp.instance(), "连接超时请换网重试！", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onErrorDemo$4(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ObservableEmitter observableEmitter) throws Exception {
        String vid = aliyunDownloadMediaInfo.getVid();
        if (!TextUtils.isEmpty(vid) && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (int i2 = 0; i2 < YkBManager.getInstance().mDownloadMediaLists.size(); i2++) {
                if (vid.equals(YkBManager.getInstance().mDownloadMediaLists.get(i2).getVid())) {
                    ProjectApp.database.getVideoDownDao().updataModel(YkBManager.getInstance().mDownloadMediaLists.get(i2).getQuality(), "", "", 2, (int) YkBManager.getInstance().mDownloadMediaLists.get(i2).getSize(), YkBManager.getInstance().mDownloadMediaLists.get(i2).getFormat(), YkBManager.getInstance().mDownloadMediaLists.get(i2).isEncripted(), YkBManager.getInstance().mDownloadMediaLists.get(i2).getVid());
                    observableEmitter.onNext(YkBManager.getInstance().mDownloadMediaLists.get(i2));
                    observableEmitter.onComplete();
                    return;
                }
            }
        }
        observableEmitter.onNext(aliyunDownloadMediaInfo);
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onErrorDemo$5(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) throws Exception {
        YkBManager.getInstance().videoDownBeanMediatorLiveData.postValue(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onStartV$1(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.database.getVideoDownDao().updataModel(aliyunDownloadMediaInfo.getQuality(), "0", "", 1, (int) aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getFormat(), aliyunDownloadMediaInfo.isEncripted(), aliyunDownloadMediaInfo.getVid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onStopDemo$2(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.database.getVideoDownDao().updataModel(aliyunDownloadMediaInfo.getQuality(), aliyunDownloadMediaInfo.getProgress() + "", aliyunDownloadMediaInfo.getSavePath(), 4, (int) aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getFormat(), aliyunDownloadMediaInfo.isEncripted(), aliyunDownloadMediaInfo.getVid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onWaitDemo$6(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.database.getVideoDownDao().updataModel(aliyunDownloadMediaInfo.getQuality(), "", "", 3, (int) aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getFormat(), aliyunDownloadMediaInfo.isEncripted(), aliyunDownloadMediaInfo.getVid());
    }

    private void onCompletionDemo(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.contains(aliyunDownloadMediaInfo)) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 = YkBManager.getInstance().mDownloadMediaLists.get(YkBManager.getInstance().mDownloadMediaLists.indexOf(aliyunDownloadMediaInfo));
            aliyunDownloadMediaInfo2.setSavePath(aliyunDownloadMediaInfo.getSavePath());
            aliyunDownloadMediaInfo2.setStatus(AliyunDownloadMediaInfo.Status.Complete);
            aliyunDownloadMediaInfo2.setProgress(100);
        }
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.utils.l
            @Override // java.lang.Runnable
            public final void run() {
                MyDownloadInfoListener.lambda$onCompletionDemo$3(aliyunDownloadMediaInfo);
            }
        });
        YkBManager.getInstance().videoDownBeanMediatorLiveData.postValue(aliyunDownloadMediaInfo);
        EventBus.getDefault().post(new EventBusMessage("completion", aliyunDownloadMediaInfo.getVid()));
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        Iterator<VideoDownTempBean> it = ProjectApp.vids.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (it.next().vid.equals(aliyunDownloadMediaInfo.getVid())) {
                it.remove();
                break;
            }
        }
        if (ProjectApp.vids.size() > 0) {
            CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, ProjectApp.vids.get(0).acId, ProjectApp.vids.get(0).akSceret, ProjectApp.vids.get(0).securityToken);
        }
    }

    private void onErrorDemo(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.utils.n
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                MyDownloadInfoListener.lambda$onErrorDemo$4(aliyunDownloadMediaInfo, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.utils.o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MyDownloadInfoListener.lambda$onErrorDemo$5((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    private void onStopDemo(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.utils.p
            @Override // java.lang.Runnable
            public final void run() {
                MyDownloadInfoListener.lambda$onStopDemo$2(aliyunDownloadMediaInfo);
            }
        });
        YkBManager.getInstance().videoDownBeanMediatorLiveData.postValue(aliyunDownloadMediaInfo);
    }

    private void onWaitDemo(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.utils.m
            @Override // java.lang.Runnable
            public final void run() {
                MyDownloadInfoListener.lambda$onWaitDemo$6(aliyunDownloadMediaInfo);
            }
        });
        YkBManager.getInstance().videoDownBeanMediatorLiveData.postValue(aliyunDownloadMediaInfo);
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onAdd(AliyunDownloadMediaInfo info) {
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onCompletion(AliyunDownloadMediaInfo info) {
        onCompletionDemo(info);
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onDelete(AliyunDownloadMediaInfo info) {
        if (info == null || YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0 || !YkBManager.getInstance().mDownloadMediaLists.contains(info)) {
            return;
        }
        YkBManager.getInstance().mDownloadMediaLists.remove(info);
        EventBus.getDefault().post(new EventBusMessage(RequestParameters.SUBRESOURCE_DELETE, info.getVid()));
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onDeleteAll() {
        if (YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            YkBManager.getInstance().mDownloadMediaLists.clear();
        }
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
        if (info == null || code == null) {
            return;
        }
        if (code.getValue() != ErrorCode.ERROR_SERVER_POP_TOKEN_EXPIRED.getValue() && code.getValue() != ErrorCode.ERROR_SERVER_VOD_INVALIDAUTHINFO_EXPIRETIME.getValue()) {
            if (code.getValue() == ErrorCode.DOWNLOAD_ERROR_URL_CANNOT_REACH.getValue()) {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.utils.q
                    @Override // java.lang.Runnable
                    public final void run() {
                        MyDownloadInfoListener.lambda$onError$0();
                    }
                });
            }
            onErrorDemo(info);
        } else if (!TextUtils.isEmpty(info.getVid())) {
            YkBManager.getInstance().getCourseAk(info.getVid());
        }
        Log.d("sadasdqwwdsaa", code + "onError: " + new Gson().toJson(info));
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onFileProgress(AliyunDownloadMediaInfo info) {
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onPrepared(List<AliyunDownloadMediaInfo> infos) {
        if (infos == null || infos.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < infos.size(); i2++) {
            if (QualityValue.QUALITY_FLUENT.equals(infos.get(i2).getQuality())) {
                if (!YkBManager.getInstance().mDownloadMediaLists.contains(infos.get(i2))) {
                    ProjectApp.downloadManager.startDownload(infos.get(i2));
                    if (YkBManager.getInstance().mDownloadMediaLists.contains(infos.get(i2))) {
                        return;
                    }
                    YkBManager.getInstance().mDownloadMediaLists.add(0, infos.get(i2));
                    return;
                }
                AliyunDownloadMediaInfo aliyunDownloadMediaInfo = YkBManager.getInstance().mDownloadMediaLists.get(YkBManager.getInstance().mDownloadMediaLists.indexOf(infos.get(i2)));
                String savePath = aliyunDownloadMediaInfo.getSavePath();
                if (TextUtils.isEmpty(savePath)) {
                    savePath = "";
                }
                if (new File(savePath).exists()) {
                    onCompletionDemo(aliyunDownloadMediaInfo);
                    return;
                } else {
                    ProjectApp.downloadManager.startDownload(infos.get(i2));
                    return;
                }
            }
        }
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onProgress(AliyunDownloadMediaInfo info, int percent) {
        YkBManager.getInstance().videoDownBeanMediatorLiveData.postValue(info);
        if (YkBManager.getInstance().mDownloadMediaLists == null || !YkBManager.getInstance().mDownloadMediaLists.contains(info)) {
            return;
        }
        YkBManager.getInstance().mDownloadMediaLists.get(YkBManager.getInstance().mDownloadMediaLists.indexOf(info)).setProgress(percent);
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onStart(AliyunDownloadMediaInfo info) {
        onStartV(info);
    }

    public void onStartV(final AliyunDownloadMediaInfo info) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.utils.r
            @Override // java.lang.Runnable
            public final void run() {
                MyDownloadInfoListener.lambda$onStartV$1(info);
            }
        });
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onStop(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        onStopDemo(aliyunDownloadMediaInfo);
    }

    @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
    public void onWait(AliyunDownloadMediaInfo outMediaInfo) {
        onWaitDemo(outMediaInfo);
    }
}
