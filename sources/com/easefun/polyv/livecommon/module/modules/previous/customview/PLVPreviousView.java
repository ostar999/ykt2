package com.easefun.polyv.livecommon.module.modules.previous.customview;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.livescenes.model.PLVPlaybackListVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.header.MaterialHeader;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes3.dex */
public class PLVPreviousView extends FrameLayout {
    private final List<PLVPlaybackListVO.DataBean.ContentsBean> dataList;
    private TextView errorTv;
    private PLVFootView footView;
    private MaterialHeader<IIndicator> header;
    private PLVPlaybackListVO plvPlaybackInfo;
    private PLVPreviousViewInterface.OnPrepareChangeVideoVidListener prepareChangeVideoVidListener;
    private PLVPreviousAdapter previousAdapter;
    private IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter previousPresenter;
    private final IPLVPreviousPlaybackContract.IPreviousPlaybackView previousView;
    private RecyclerView recyclerView;
    private SmoothRefreshLayout refreshLayout;

    public static class Builder {
        private PLVPreviousAdapter adapter;
        private final Context context;
        private RecyclerView.ItemDecoration itemDecoration;
        private RecyclerView.LayoutManager layoutManager;
        private PLVPreviousViewInterface.OnPrepareChangeVideoVidListener prepareChangeVideoVidListener;
        private String themeColor;

        public Builder(Context context) {
            this.context = context;
        }

        public PLVPreviousView create() {
            PLVPreviousView pLVPreviousView = new PLVPreviousView(this.context);
            pLVPreviousView.setParams(this);
            return pLVPreviousView;
        }

        public Builder setAdapter(PLVPreviousAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setOnPrepareChangeVidListener(PLVPreviousViewInterface.OnPrepareChangeVideoVidListener listener) {
            this.prepareChangeVideoVidListener = listener;
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

        public Builder setThemeColor(String color) {
            this.themeColor = color;
            return this;
        }
    }

    public interface PLVPreviousViewInterface {

        public interface OnPrepareChangeVideoVidListener {
            void onPrepareChangeVideoVid(String vid);
        }
    }

    public PLVPreviousView(Context context) {
        this(context, null);
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.plv_playback_previous_layout, (ViewGroup) this, true);
        this.refreshLayout = (SmoothRefreshLayout) viewInflate.findViewById(R.id.plv_previous_refreshLy);
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.plv_previous_rv);
        this.errorTv = (TextView) viewInflate.findViewById(R.id.plv_previous_request_error_tv);
        PLVFootView pLVFootView = new PLVFootView(getContext());
        this.footView = pLVFootView;
        pLVFootView.setVisibility(4);
        this.refreshLayout.setDisableRefresh(true);
        this.refreshLayout.setDisableLoadMore(false);
        this.refreshLayout.setEnableOverScroll(false);
        this.refreshLayout.setDisableLoadMoreWhenContentNotFull(false);
        this.refreshLayout.setFooterView(this.footView);
        MaterialHeader<IIndicator> materialHeader = new MaterialHeader<>(getContext());
        this.header = materialHeader;
        materialHeader.setColorSchemeColors(new int[]{getContext().getResources().getColor(R.color.dodgerBlue)});
        this.refreshLayout.setHeaderView(this.header);
        this.refreshLayout.setOnRefreshListener(new RefreshingListenerAdapter() { // from class: com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousView.1
            @Override // me.dkzwm.widget.srl.RefreshingListenerAdapter, me.dkzwm.widget.srl.SmoothRefreshLayout.OnRefreshListener
            public void onLoadingMore() {
                PLVPreviousView.this.footView.setVisibility(0);
                if (PLVPreviousView.this.previousPresenter != null) {
                    PLVPreviousView.this.previousPresenter.requestLoadMorePreviousVideo();
                }
            }

            @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnRefreshListener
            public void onRefreshing() {
                if (PLVPreviousView.this.previousPresenter != null) {
                    PLVPreviousView.this.previousPresenter.requestPreviousList();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNextOne() {
        PLVPreviousAdapter pLVPreviousAdapter;
        if (this.dataList.isEmpty() || (pLVPreviousAdapter = this.previousAdapter) == null || this.previousPresenter == null) {
            return;
        }
        int currentPosition = pLVPreviousAdapter.getCurrentPosition();
        int i2 = currentPosition >= this.dataList.size() + (-1) ? 0 : currentPosition + 1;
        this.previousAdapter.setCurrentPosition(i2);
        this.previousAdapter.notifyItemChanged(currentPosition);
        this.previousAdapter.notifyItemChanged(i2);
        changePlaybackVideoVid(this.dataList.get(i2).getVideoPoolId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVBaseViewData> toPlayBackList(PLVPlaybackListVO listVO) {
        List<PLVPlaybackListVO.DataBean.ContentsBean> contents;
        ArrayList arrayList = new ArrayList();
        if (listVO != null && listVO.getData() != null && (contents = listVO.getData().getContents()) != null) {
            Iterator<PLVPlaybackListVO.DataBean.ContentsBean> it = contents.iterator();
            while (it.hasNext()) {
                arrayList.add(new PLVBaseViewData(it.next(), 0));
            }
        }
        return arrayList;
    }

    public void changePlaybackVideoVid(String vid) {
        PLVPreviousViewInterface.OnPrepareChangeVideoVidListener onPrepareChangeVideoVidListener = this.prepareChangeVideoVidListener;
        if (onPrepareChangeVideoVidListener != null) {
            onPrepareChangeVideoVidListener.onPrepareChangeVideoVid(vid);
        }
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.changePlaybackVideoVid(vid);
        }
    }

    public IPLVPreviousPlaybackContract.IPreviousPlaybackView getPreviousView() {
        return this.previousView;
    }

    public void onDestroy() {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.unregisterView(this.previousView);
            this.previousPresenter.onDestroy();
        }
    }

    public void requestPreviousList() {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.requestPreviousList();
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
            PLVPreviousAdapter pLVPreviousAdapter = builder.adapter;
            this.previousAdapter = pLVPreviousAdapter;
            this.recyclerView.setAdapter(pLVPreviousAdapter);
        }
        if (builder.prepareChangeVideoVidListener != null) {
            this.prepareChangeVideoVidListener = builder.prepareChangeVideoVidListener;
        }
        if (TextUtils.isEmpty(builder.themeColor)) {
            return;
        }
        this.errorTv.setTextColor(Color.parseColor(builder.themeColor));
        this.header.setColorSchemeColors(new int[]{Color.parseColor(builder.themeColor)});
    }

    public PLVPreviousView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPreviousView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dataList = new ArrayList();
        this.previousView = new PLVAbsPreviousView() { // from class: com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousView.2
            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void onPlayComplete() {
                PLVPreviousView.this.playNextOne();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void previousLoadModreData(PLVPlaybackListVO listVO) {
                if (PLVPreviousView.this.previousAdapter != null) {
                    PLVPreviousView.this.dataList.addAll(listVO.getData().getContents());
                    PLVPreviousView.this.previousAdapter.loadMore(PLVPreviousView.this.toPlayBackList(listVO));
                    PLVPreviousView.this.refreshLayout.refreshComplete();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void previousLoadMoreError() {
                requestPreviousError();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void previousNoMoreData() {
                PLVPreviousView.this.refreshLayout.setEnableNoMoreData(true);
                PLVPreviousView.this.footView.setText(PLVPreviousView.this.getResources().getString(R.string.plv_previous_no_more_data));
                PLVPreviousView.this.footView.setVisibility(0);
                PLVPreviousView.this.refreshLayout.refreshComplete(true);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void requestPreviousError() {
                PLVPreviousView.this.errorTv.setVisibility(0);
                PLVPreviousView.this.recyclerView.setVisibility(8);
                PLVPreviousView.this.refreshLayout.setDisableRefresh(false);
                PLVPreviousView.this.refreshLayout.setDisablePerformRefresh(false);
                PLVPreviousView.this.refreshLayout.setDisableLoadMore(true);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void setPresenter(IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter presenter) {
                PLVPreviousView.this.previousPresenter = presenter;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.previous.view.PLVAbsPreviousView, com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
            public void updatePreviousVideoList(PLVPlaybackListVO playbackList) {
                PLVPreviousView.this.errorTv.setVisibility(8);
                PLVPreviousView.this.recyclerView.setVisibility(0);
                PLVPreviousView.this.footView.setVisibility(4);
                PLVPreviousView.this.refreshLayout.setEnableAutoRefresh(false);
                PLVPreviousView.this.refreshLayout.setDisablePerformRefresh(true);
                PLVPreviousView.this.refreshLayout.setDisableRefresh(true);
                PLVPreviousView.this.refreshLayout.setDisableLoadMore(false);
                if (playbackList == null || playbackList.getData().getContents().size() <= 0) {
                    previousNoMoreData();
                    return;
                }
                if (PLVPreviousView.this.previousPresenter != null) {
                    PLVPreviousView.this.plvPlaybackInfo = playbackList;
                    if (PLVPreviousView.this.dataList.isEmpty()) {
                        PLVPreviousView.this.dataList.addAll(playbackList.getData().getContents());
                        PLVPreviousView pLVPreviousView = PLVPreviousView.this;
                        pLVPreviousView.changePlaybackVideoVid(((PLVPlaybackListVO.DataBean.ContentsBean) pLVPreviousView.dataList.get(0)).getVideoPoolId());
                    } else {
                        PLVPreviousView.this.dataList.clear();
                        PLVPreviousView.this.dataList.addAll(playbackList.getData().getContents());
                    }
                }
                if (PLVPreviousView.this.previousAdapter != null) {
                    PLVPreviousView.this.previousAdapter.setDataList(PLVPreviousView.this.toPlayBackList(playbackList));
                }
            }
        };
        initView();
    }
}
