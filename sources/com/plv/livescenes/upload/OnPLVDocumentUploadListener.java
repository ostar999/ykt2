package com.plv.livescenes.upload;

import androidx.annotation.Nullable;
import com.plv.livescenes.document.model.PLVPPTInfo;

/* loaded from: classes5.dex */
public interface OnPLVDocumentUploadListener {
    void onConvertFailed(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th);

    void onConvertSuccess(PLVPPTInfo.DataBean.ContentsBean contentsBean);

    void onDocumentConverting(PLVPPTInfo.DataBean.ContentsBean contentsBean);

    void onDocumentExist(PLVPPTInfo.DataBean.ContentsBean contentsBean);

    void onPrepared(PLVPPTInfo.DataBean.ContentsBean contentsBean);

    void onUploadFailed(@Nullable PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th);

    void onUploadProgress(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2);

    void onUploadSuccess(PLVPPTInfo.DataBean.ContentsBean contentsBean);
}
