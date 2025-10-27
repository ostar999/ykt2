package com.easefun.polyv.livecommon.module.modules.previous.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChapterView extends FrameLayout {
    private PLVChapterAdapter chapterAdapter;
    private final IPLVPreviousPlaybackContract.IPreviousPlaybackView chapterView;
    private final List<PLVChapterDataVO> dataList;
    private IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter previousPresenter;
    private RecyclerView recyclerView;

    public static class Builder {
        private PLVChapterAdapter adapter;
        private final Context context;
        private RecyclerView.ItemDecoration itemDecoration;
        private RecyclerView.LayoutManager layoutManager;

        public Builder(Context context) {
            this.context = context;
        }

        public PLVChapterView create() {
            PLVChapterView pLVChapterView = new PLVChapterView(this.context);
            pLVChapterView.setParams(this);
            return pLVChapterView;
        }

        public Builder setAdapter(PLVChapterAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setRecyclerViewItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
            this.itemDecoration = itemDecoration;
            return this;
        }

        public Builder setRecyclerViewLayoutManager(RecyclerView.LayoutManager manager) {
            this.layoutManager = manager;
            return this;
        }
    }

    public PLVChapterView(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findIndex2(List<PLVChapterDataVO> list, int time) {
        int size = this.dataList.size() - 1;
        int i2 = (size + 0) / 2;
        int i3 = 0;
        while (i3 < size) {
            if (time == list.get(i2).getTimeStamp()) {
                return i2;
            }
            if (time >= list.get(size).getTimeStamp()) {
                return size;
            }
            if (time > list.get(i2).getTimeStamp() && i2 + 1 == size) {
                return i2;
            }
            if (time >= list.get(i3).getTimeStamp() && i3 + 1 == size) {
                return i3;
            }
            if (time > list.get(i2).getTimeStamp()) {
                i3 = i2;
            } else if (time < list.get(i2).getTimeStamp()) {
                size = i2;
            }
            i2 = (i3 + size) / 2;
        }
        return i2;
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_playback_chapter_layout, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.plv_chapter_rv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVBaseViewData> toPlayBackList(List<PLVChapterDataVO> listVO) {
        ArrayList arrayList = new ArrayList();
        if (listVO != null) {
            Iterator<PLVChapterDataVO> it = listVO.iterator();
            while (it.hasNext()) {
                arrayList.add(new PLVBaseViewData(it.next(), 0));
            }
        }
        return arrayList;
    }

    public void changePlaybackVideoSeek(int timeStamp) {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.changePlaybackVideoSeek(timeStamp);
        }
    }

    public IPLVPreviousPlaybackContract.IPreviousPlaybackView getPreviousView() {
        return this.chapterView;
    }

    public void onDestroy() {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.unregisterView(this.chapterView);
            this.previousPresenter.onDestroy();
        }
    }

    public void requestChapterList() {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.requestChapterDetail();
        }
    }

    public void setParams(Builder builder) {
        if (builder.layoutManager != null) {
            this.recyclerView.setLayoutManager(builder.layoutManager);
        }
        if (builder.itemDecoration != null) {
            this.recyclerView.addItemDecoration(builder.itemDecoration);
        }
        if (builder.adapter != null) {
            PLVChapterAdapter pLVChapterAdapter = builder.adapter;
            this.chapterAdapter = pLVChapterAdapter;
            this.recyclerView.setAdapter(pLVChapterAdapter);
        }
    }

    public PLVChapterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVChapterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dataList = new ArrayList();
        this.chapterView = new PLVAbsPreviousView() { // from class: com.easefun.polyv.livecommon.module.modules.previous.customview.PLVChapterView.1
            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void onSeekChange(int time) {
                if (PLVChapterView.this.chapterAdapter != null) {
                    PLVChapterView pLVChapterView = PLVChapterView.this;
                    PLVChapterView.this.chapterAdapter.updataItmeTime(pLVChapterView.findIndex2(pLVChapterView.dataList, time));
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void requestChapterError() {
                super.requestChapterError();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void setPresenter(IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter presenter) {
                PLVChapterView.this.previousPresenter = presenter;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void updateChapterList(List<PLVChapterDataVO> datas) {
                if (PLVChapterView.this.previousPresenter != null) {
                    PLVChapterView.this.dataList.clear();
                    PLVChapterView.this.dataList.addAll(datas);
                    if (PLVChapterView.this.chapterAdapter != null) {
                        PLVChapterView.this.chapterAdapter.setDataList(PLVChapterView.this.toPlayBackList(datas));
                    }
                }
            }
        };
        initView();
    }
}
