package com.plv.livescenes.upload;

import android.content.Context;
import java.io.File;

/* loaded from: classes5.dex */
public interface IPLVDocumentUploadManager {
    void addUploadTask(Context context, String str, File file, OnPLVDocumentUploadListener onPLVDocumentUploadListener);

    void destroy();

    void init();

    void removeUploadTask(String str);

    void startPollingConvertStatus();

    void stopPollingConvertStatus();
}
