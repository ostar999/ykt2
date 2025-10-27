package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAMucShareFile;

/* loaded from: classes4.dex */
public class EMMucSharedFile extends EMBase<EMAMucShareFile> {
    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMAMucShareFile] */
    public EMMucSharedFile() {
        this.emaObject = new EMAMucShareFile();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMAMucShareFile] */
    public EMMucSharedFile(EMAMucShareFile eMAMucShareFile) {
        this.emaObject = new EMAMucShareFile(eMAMucShareFile);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFileId() {
        return ((EMAMucShareFile) this.emaObject).getFileId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFileName() {
        return ((EMAMucShareFile) this.emaObject).getFileName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFileOwner() {
        return ((EMAMucShareFile) this.emaObject).getFileOwner();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getFileSize() {
        return ((EMAMucShareFile) this.emaObject).getFileSize();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getFileUpdateTime() {
        return ((EMAMucShareFile) this.emaObject).getUpdateTime();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
