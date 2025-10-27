package com.easefun.polyv.livecloudclass.modules.download.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.download.adapter.PLVPlaybackCacheAdapter;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel;
import com.plv.foundationsdk.component.di.PLVDependManager;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheFragment extends Fragment {
    private boolean isShowDownloadingList;
    private PLVPlaybackCacheAdapter playbackCacheAdapter;
    private RecyclerView playbackCacheRv;
    private String title;
    private View view;

    public static PLVPlaybackCacheFragment create(String str, boolean z2) {
        PLVPlaybackCacheFragment pLVPlaybackCacheFragment = new PLVPlaybackCacheFragment();
        pLVPlaybackCacheFragment.title = str;
        pLVPlaybackCacheFragment.isShowDownloadingList = z2;
        return pLVPlaybackCacheFragment;
    }

    private void findView() {
        this.playbackCacheRv = (RecyclerView) this.view.findViewById(R.id.plv_playback_cache_rv);
    }

    private void initPlaybackCacheRv() {
        this.playbackCacheAdapter = new PLVPlaybackCacheAdapter(this.isShowDownloadingList ? 1 : 2);
        this.playbackCacheRv.setLayoutManager(new LinearLayoutManager(this.view.getContext()));
        this.playbackCacheRv.setAdapter(this.playbackCacheAdapter);
    }

    private void initView() {
        findView();
        initPlaybackCacheRv();
        observePlaybackCacheList();
    }

    private void observePlaybackCacheList() {
        Object context = getContext();
        if (context == null) {
            return;
        }
        PLVPlaybackCacheListViewModel pLVPlaybackCacheListViewModel = (PLVPlaybackCacheListViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheListViewModel.class);
        if (this.isShowDownloadingList) {
            pLVPlaybackCacheListViewModel.getDownloadingListLiveData().observe((LifecycleOwner) context, new Observer<List<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.download.fragment.PLVPlaybackCacheFragment.1
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable List<PLVPlaybackCacheVideoVO> list) {
                    if (list == null) {
                        return;
                    }
                    PLVPlaybackCacheFragment.this.playbackCacheAdapter.updateData(list);
                }
            });
        } else {
            pLVPlaybackCacheListViewModel.getDownloadedListLiveData().observe((LifecycleOwner) context, new Observer<List<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.download.fragment.PLVPlaybackCacheFragment.2
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable List<PLVPlaybackCacheVideoVO> list) {
                    if (list == null) {
                        return;
                    }
                    PLVPlaybackCacheFragment.this.playbackCacheAdapter.updateData(list);
                }
            });
        }
    }

    public String getTitle() {
        return this.title;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.view == null) {
            this.view = layoutInflater.inflate(R.layout.plv_playback_cache_fragment, (ViewGroup) null);
            initView();
        }
        return this.view;
    }
}
