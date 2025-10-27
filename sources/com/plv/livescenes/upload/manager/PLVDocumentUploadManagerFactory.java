package com.plv.livescenes.upload.manager;

import com.plv.livescenes.upload.IPLVDocumentUploadManager;

/* loaded from: classes5.dex */
public class PLVDocumentUploadManagerFactory {
    public static IPLVDocumentUploadManager createDocumentUploadManager() {
        return new PLVDocumentUploadClient();
    }
}
