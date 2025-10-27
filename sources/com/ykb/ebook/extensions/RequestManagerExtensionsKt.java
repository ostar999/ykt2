package com.ykb.ebook.extensions;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.bumptech.glide.RequestManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0000\u001a\u00020\u0002¨\u0006\u0003"}, d2 = {RequestParameters.SUBRESOURCE_LIFECYCLE, "Lcom/bumptech/glide/RequestManager;", "Landroidx/lifecycle/Lifecycle;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class RequestManagerExtensionsKt {
    @NotNull
    public static final RequestManager lifecycle(@NotNull final RequestManager requestManager, @NotNull Lifecycle lifecycle) {
        Intrinsics.checkNotNullParameter(requestManager, "<this>");
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        lifecycle.addObserver(new DefaultLifecycleObserver() { // from class: com.ykb.ebook.extensions.RequestManagerExtensionsKt$lifecycle$observer$1
            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
                androidx.lifecycle.b.a(this, lifecycleOwner);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public void onDestroy(@NotNull LifecycleOwner owner) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                owner.getLifecycle().removeObserver(this);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public void onPause(@NotNull LifecycleOwner owner) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                requestManager.onStop();
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public void onResume(@NotNull LifecycleOwner owner) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                requestManager.onStart();
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
                androidx.lifecycle.b.e(this, lifecycleOwner);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
                androidx.lifecycle.b.f(this, lifecycleOwner);
            }
        });
        return requestManager;
    }
}
