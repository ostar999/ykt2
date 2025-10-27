package com.bumptech.glide.integration.recyclerview;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestManager;

/* loaded from: classes2.dex */
public final class RecyclerViewPreloader<T> extends RecyclerView.OnScrollListener {
    private final RecyclerToListViewScrollListener recyclerScrollListener;

    public RecyclerViewPreloader(@NonNull Activity activity, @NonNull ListPreloader.PreloadModelProvider<T> preloadModelProvider, @NonNull ListPreloader.PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this(Glide.with(activity), preloadModelProvider, preloadSizeProvider, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
        this.recyclerScrollListener.onScrolled(recyclerView, i2, i3);
    }

    public RecyclerViewPreloader(@NonNull FragmentActivity fragmentActivity, @NonNull ListPreloader.PreloadModelProvider<T> preloadModelProvider, @NonNull ListPreloader.PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this(Glide.with(fragmentActivity), preloadModelProvider, preloadSizeProvider, i2);
    }

    public RecyclerViewPreloader(@NonNull Fragment fragment, @NonNull ListPreloader.PreloadModelProvider<T> preloadModelProvider, @NonNull ListPreloader.PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this(Glide.with(fragment), preloadModelProvider, preloadSizeProvider, i2);
    }

    @Deprecated
    public RecyclerViewPreloader(@NonNull android.app.Fragment fragment, @NonNull ListPreloader.PreloadModelProvider<T> preloadModelProvider, @NonNull ListPreloader.PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this(Glide.with(fragment), preloadModelProvider, preloadSizeProvider, i2);
    }

    public RecyclerViewPreloader(@NonNull RequestManager requestManager, @NonNull ListPreloader.PreloadModelProvider<T> preloadModelProvider, @NonNull ListPreloader.PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this.recyclerScrollListener = new RecyclerToListViewScrollListener(new ListPreloader(requestManager, preloadModelProvider, preloadSizeProvider, i2));
    }
}
