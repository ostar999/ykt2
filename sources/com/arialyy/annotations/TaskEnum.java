package com.arialyy.annotations;

/* loaded from: classes2.dex */
public enum TaskEnum {
    DOWNLOAD("com.arialyy.aria.core.task", "DownloadTask", "$$DownloadListenerProxy", "AptNormalTaskListener"),
    DOWNLOAD_GROUP("com.arialyy.aria.core.task", "DownloadGroupTask", "$$DownloadGroupListenerProxy", "AptNormalTaskListener"),
    DOWNLOAD_GROUP_SUB("com.arialyy.aria.core.task", "DownloadGroupTask", "$$DGSubListenerProxy", "AptSubTaskListener"),
    UPLOAD("com.arialyy.aria.core.task", "UploadTask", "$$UploadListenerProxy", "AptNormalTaskListener"),
    M3U8_PEER("com.arialyy.aria.core.task", "DownloadTask", "$$M3U8PeerListenerProxy", "AptM3U8PeerTaskListener");

    public String className;
    public String pkg;
    public String proxySuffix;
    public String proxySuperClass;

    TaskEnum(String str, String str2, String str3, String str4) {
        this.pkg = str;
        this.className = str2;
        this.proxySuffix = str3;
        this.proxySuperClass = str4;
    }
}
