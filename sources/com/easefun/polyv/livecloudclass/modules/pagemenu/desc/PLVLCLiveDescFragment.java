package com.easefun.polyv.livecloudclass.modules.pagemenu.desc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.catchpig.mvvm.utils.DateUtil;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSafeWebView;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewContentUtils;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewHelper;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVLCLiveDescFragment extends PLVBaseFragment {
    private static final SimpleDateFormat DATE_FORMAT;
    private static final Map<PLVLiveStateEnum, Integer> STATUS_BACKGROUND_MAP;
    private static final Map<PLVLiveStateEnum, Integer> STATUS_COLOR_MAP;
    private PolyvLiveClassDetailVO classDetailVO;
    private PLVLiveStateEnum currentLiveState;
    private PLVSafeWebView descWebView;
    private boolean isCachedPlaybackVideo = false;
    private long likesCount;
    private TextView likesTv;
    private ImageView liveCoverIV;
    private ViewGroup parentLy;
    private TextView publisherTv;
    private TextView startTimeTv;
    private TextView statusTv;
    private TextView titleTv;
    private long viewerCount;
    private TextView viewerCountTv;

    static {
        PLVLiveStateEnum pLVLiveStateEnum = PLVLiveStateEnum.UNSTART;
        int i2 = R.color.text_gray;
        PLVLiveStateEnum pLVLiveStateEnum2 = PLVLiveStateEnum.LIVE;
        int i3 = R.color.text_red;
        PLVLiveStateEnum pLVLiveStateEnum3 = PLVLiveStateEnum.STOP;
        PLVLiveStateEnum pLVLiveStateEnum4 = PLVLiveStateEnum.END;
        PLVLiveStateEnum pLVLiveStateEnum5 = PLVLiveStateEnum.WAITING;
        PLVLiveStateEnum pLVLiveStateEnum6 = PLVLiveStateEnum.PLAYBACK;
        PLVLiveStateEnum pLVLiveStateEnum7 = PLVLiveStateEnum.PLAYBACK_CACHED;
        STATUS_COLOR_MAP = PLVSugarUtil.mapOf(PLVSugarUtil.pair(pLVLiveStateEnum, Integer.valueOf(i2)), PLVSugarUtil.pair(pLVLiveStateEnum2, Integer.valueOf(i3)), PLVSugarUtil.pair(pLVLiveStateEnum3, Integer.valueOf(R.color.text_green)), PLVSugarUtil.pair(pLVLiveStateEnum4, Integer.valueOf(i2)), PLVSugarUtil.pair(pLVLiveStateEnum5, Integer.valueOf(R.color.colorPortage)), PLVSugarUtil.pair(pLVLiveStateEnum6, Integer.valueOf(i3)), PLVSugarUtil.pair(pLVLiveStateEnum7, Integer.valueOf(R.color.plvlc_live_desc_playback_cached_text_color)));
        int i4 = R.drawable.plvlc_live_status_noactive;
        int i5 = R.drawable.plvlc_live_status_live;
        STATUS_BACKGROUND_MAP = PLVSugarUtil.mapOf(PLVSugarUtil.pair(pLVLiveStateEnum, Integer.valueOf(i4)), PLVSugarUtil.pair(pLVLiveStateEnum2, Integer.valueOf(i5)), PLVSugarUtil.pair(pLVLiveStateEnum3, Integer.valueOf(R.drawable.plvlc_live_status_stop)), PLVSugarUtil.pair(pLVLiveStateEnum4, Integer.valueOf(i4)), PLVSugarUtil.pair(pLVLiveStateEnum5, Integer.valueOf(R.drawable.plvlc_live_status_waitting)), PLVSugarUtil.pair(pLVLiveStateEnum6, Integer.valueOf(i5)), PLVSugarUtil.pair(pLVLiveStateEnum7, Integer.valueOf(R.drawable.plvlc_live_status_playback_cached)));
        DATE_FORMAT = new SimpleDateFormat(DateUtil.TIME_FORMAT_YMDHMS);
    }

    private void acceptIntroMsg(String str) {
        if (!TextUtils.isEmpty(str)) {
            loadWebView(PLVWebViewContentUtils.toWebViewContent(str, "#ADADC0"));
            return;
        }
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView == null || pLVSafeWebView.getParent() == null) {
            return;
        }
        ((ViewGroup) this.descWebView.getParent()).removeView(this.descWebView);
    }

    private void initView() {
        this.parentLy = (ViewGroup) findViewById(R.id.parent_ly);
        this.viewerCountTv = (TextView) findViewById(R.id.viewer_count_tv);
        this.titleTv = (TextView) findViewById(R.id.title_tv);
        this.liveCoverIV = (ImageView) findViewById(R.id.live_cover_iv);
        this.publisherTv = (TextView) findViewById(R.id.publisher_tv);
        this.likesTv = (TextView) findViewById(R.id.likes_tv);
        this.startTimeTv = (TextView) findViewById(R.id.start_time_tv);
        this.statusTv = (TextView) findViewById(R.id.status_tv);
        PolyvLiveClassDetailVO polyvLiveClassDetailVO = this.classDetailVO;
        if (polyvLiveClassDetailVO != null) {
            this.titleTv.setText(polyvLiveClassDetailVO.getData().getName());
            String publisher = this.classDetailVO.getData().getPublisher();
            TextView textView = this.publisherTv;
            if (TextUtils.isEmpty(this.classDetailVO.getData().getPublisher())) {
                publisher = "主持人";
            }
            textView.setText(publisher);
            PLVImageLoader.getInstance().loadImage(this.classDetailVO.getData().getCoverImage(), this.liveCoverIV);
            updateStatusViewWithClassDetail();
            StringBuilder sb = new StringBuilder();
            sb.append("直播时间：");
            sb.append(StringUtils.isEmpty(this.classDetailVO.getData().getStartTime()) ? "无" : this.classDetailVO.getData().getStartTime());
            this.startTimeTv.setText(sb.toString());
            if (this.likesCount == 0) {
                updateLikesCount(this.classDetailVO.getData().getLikes());
            } else {
                updateLikesCount(0L);
            }
            setDescContent();
        }
        observePlaybackCacheStatus();
    }

    private void loadWebView(String str) {
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView != null) {
            if (pLVSafeWebView.getParent() != null) {
                ((ViewGroup) this.descWebView.getParent()).removeView(this.descWebView);
            }
            this.parentLy.addView(this.descWebView);
            this.descWebView.loadDataWithBaseURL(null, str, "text/html; charset=UTF-8", null, null);
            return;
        }
        PLVSafeWebView pLVSafeWebView2 = new PLVSafeWebView(getContext());
        this.descWebView = pLVSafeWebView2;
        pLVSafeWebView2.clearFocus();
        this.descWebView.setFocusable(false);
        this.descWebView.setFocusableInTouchMode(false);
        this.descWebView.setBackgroundColor(0);
        this.descWebView.setHorizontalScrollBarEnabled(false);
        this.descWebView.setVerticalScrollBarEnabled(false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(3, R.id.top_ly);
        layoutParams.leftMargin = ConvertUtils.dp2px(6.0f);
        layoutParams.topMargin = ConvertUtils.dp2px(6.0f);
        layoutParams.rightMargin = ConvertUtils.dp2px(6.0f);
        layoutParams.bottomMargin = ConvertUtils.dp2px(6.0f);
        this.descWebView.setLayoutParams(layoutParams);
        this.parentLy.addView(this.descWebView);
        PLVWebViewHelper.initWebView(getContext(), this.descWebView);
        this.descWebView.loadDataWithBaseURL(null, str, "text/html; charset=UTF-8", null, null);
    }

    private static PLVLiveStateEnum mergeEndOrWaitingState(PLVLiveStateEnum pLVLiveStateEnum, String str) {
        PLVLiveStateEnum pLVLiveStateEnum2 = PLVLiveStateEnum.END;
        if (pLVLiveStateEnum != pLVLiveStateEnum2 && pLVLiveStateEnum != PLVLiveStateEnum.WAITING && pLVLiveStateEnum != PLVLiveStateEnum.PLAYBACK) {
            return pLVLiveStateEnum;
        }
        if (TextUtils.isEmpty(str)) {
            return PLVLiveStateEnum.WAITING;
        }
        try {
            return new Date().after(DATE_FORMAT.parse(str)) ? pLVLiveStateEnum2 : PLVLiveStateEnum.WAITING;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return pLVLiveStateEnum;
        }
    }

    private void observePlaybackCacheStatus() {
        ((PLVPlaybackCacheVideoViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheVideoViewModel.class)).getPlaybackCacheUpdateLiveData().observe((LifecycleOwner) this.view.getContext(), new Observer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.desc.PLVLCLiveDescFragment.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
                if (pLVPlaybackCacheVideoVO == null) {
                    return;
                }
                PLVLCLiveDescFragment.this.isCachedPlaybackVideo = pLVPlaybackCacheVideoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED;
                PLVLCLiveDescFragment pLVLCLiveDescFragment = PLVLCLiveDescFragment.this;
                pLVLCLiveDescFragment.updateLiveStatus(pLVLCLiveDescFragment.currentLiveState);
            }
        });
    }

    private void setDescContent() {
        PolyvLiveClassDetailVO polyvLiveClassDetailVO = this.classDetailVO;
        if (polyvLiveClassDetailVO == null) {
            return;
        }
        for (PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean : polyvLiveClassDetailVO.getData().getChannelMenus()) {
            if ("desc".equals(channelMenusBean.getMenuType())) {
                acceptIntroMsg(channelMenusBean.getContent());
                return;
            }
        }
    }

    private void updateLikesCount(long j2) {
        long j3 = this.likesCount + j2;
        this.likesCount = j3;
        String wString = StringUtils.toWString(j3);
        TextView textView = this.likesTv;
        if (textView != null) {
            textView.setText(wString);
        }
    }

    private void updateStatusInner(PLVLiveStateEnum pLVLiveStateEnum) {
        PLVLiveStateEnum pLVLiveStateEnumMergeEndOrWaitingState = mergeEndOrWaitingState(pLVLiveStateEnum, this.classDetailVO.getData().getStartTime());
        PLVLiveStateEnum pLVLiveStateEnum2 = this.currentLiveState;
        if (pLVLiveStateEnum2 == null) {
            this.currentLiveState = pLVLiveStateEnumMergeEndOrWaitingState;
        } else {
            this.currentLiveState = pLVLiveStateEnum2.toState(pLVLiveStateEnumMergeEndOrWaitingState);
        }
        if (this.statusTv == null) {
            return;
        }
        PLVLiveStateEnum pLVLiveStateEnum3 = this.isCachedPlaybackVideo ? PLVLiveStateEnum.PLAYBACK_CACHED : this.currentLiveState;
        String str = (String) PLVSugarUtil.getOrDefault(pLVLiveStateEnum3.getDescription(), pLVLiveStateEnum3.getStatus());
        int iIntValue = ((Integer) PLVSugarUtil.getOrDefault(STATUS_COLOR_MAP.get(pLVLiveStateEnum3), Integer.valueOf(R.color.text_gray))).intValue();
        int iIntValue2 = ((Integer) PLVSugarUtil.getOrDefault(STATUS_BACKGROUND_MAP.get(pLVLiveStateEnum3), Integer.valueOf(R.drawable.plvlc_live_status_noactive))).intValue();
        this.statusTv.setText(str);
        this.statusTv.setTextColor(getResources().getColor(iIntValue));
        this.statusTv.setBackgroundResource(iIntValue2);
    }

    private void updateStatusViewWithClassDetail() {
        PolyvLiveClassDetailVO polyvLiveClassDetailVO = this.classDetailVO;
        if (polyvLiveClassDetailVO == null || polyvLiveClassDetailVO.getData() == null) {
            return;
        }
        updateStatusInner(PLVLiveStateEnum.parse(this.classDetailVO.getData().getWatchStatus()));
    }

    private void updateViewerCount(long j2) {
        long j3 = this.viewerCount + j2;
        this.viewerCount = j3;
        TextView textView = this.viewerCountTv;
        if (textView != null) {
            textView.setText(StringUtils.toWString(j3));
        }
    }

    public void init(PolyvLiveClassDetailVO polyvLiveClassDetailVO) {
        this.classDetailVO = polyvLiveClassDetailVO;
    }

    public boolean onBackPressed() {
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView == null || !pLVSafeWebView.canGoBack()) {
            return false;
        }
        this.descWebView.goBack();
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.plvlc_page_menu_desc_fragment, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.destroy();
            this.descWebView = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onPause();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        PLVSafeWebView pLVSafeWebView = this.descWebView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onResume();
        }
    }

    public void setLikesCount(long j2) {
        this.likesCount = j2;
        updateLikesCount(0L);
    }

    public void setViewerCount(long j2) {
        this.viewerCount = j2;
        updateViewerCount(0L);
    }

    public void updateLiveStatus(PLVLiveStateEnum pLVLiveStateEnum) {
        updateStatusInner(pLVLiveStateEnum);
    }
}
