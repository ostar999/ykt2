package com.bumptech.glide;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.psychiatrygarden.widget.glideUtil.progress.GlideRequests;

/* loaded from: classes2.dex */
final class GeneratedRequestManagerFactory implements RequestManagerRetriever.RequestManagerFactory {
    @Override // com.bumptech.glide.manager.RequestManagerRetriever.RequestManagerFactory
    @NonNull
    public RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode treeNode, @NonNull Context context) {
        return new GlideRequests(glide, lifecycle, treeNode, context);
    }
}
