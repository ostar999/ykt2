package com.easefun.polyv.livescenes.upload.manager;

import com.plv.livescenes.upload.manager.PLVDocumentUploadManagerFactory;

@Deprecated
/* loaded from: classes3.dex */
public class PLVSDocumentUploadManagerFactory extends PLVDocumentUploadManagerFactory {
    public static PLVSDocumentUploadClient createDocumentUploadManager() {
        return new PLVSDocumentUploadClient();
    }
}
