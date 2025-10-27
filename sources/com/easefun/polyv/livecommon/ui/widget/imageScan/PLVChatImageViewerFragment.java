package com.easefun.polyv.livecommon.ui.widget.imageScan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVUrlTag;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.livescenes.playback.chat.PLVChatPlaybackData;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.IPLVQuoteEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChatImageViewerFragment extends PLVBaseFragment {
    private PLVChatImageViewer imageViewer;
    private List<PLVUrlTag> imgTagList;
    private int position;

    private void initView() {
        PLVChatImageViewer pLVChatImageViewer = (PLVChatImageViewer) findViewById(R.id.image_viewer);
        this.imageViewer = pLVChatImageViewer;
        pLVChatImageViewer.setOnClickImgListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewerFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVChatImageViewerFragment.this.hide();
            }
        });
        this.imageViewer.setDataList(this.imgTagList, this.position);
    }

    public static PLVChatImageViewerFragment newInstance() {
        return new PLVChatImageViewerFragment();
    }

    public static PLVChatImageViewerFragment show(AppCompatActivity activity, List<PLVBaseViewData> dataList, PLVBaseViewData<PLVBaseEvent> selData, int containerViewId) {
        if (activity == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        return show(activity, arrayList, toImgList(dataList, selData, arrayList), containerViewId);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0121 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x012d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int toImgList(java.util.List<com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData> r7, com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData<com.plv.socket.event.PLVBaseEvent> r8, java.util.List<com.easefun.polyv.livecommon.module.utils.imageloader.PLVUrlTag> r9) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewerFragment.toImgList(java.util.List, com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData, java.util.List):int");
    }

    public void hide() {
        if (getActivity() == null || isHidden()) {
            return;
        }
        getActivity().getSupportFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        if (ScreenUtils.isPortrait()) {
            PLVScreenUtils.exitFullScreen(getActivity());
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.plv_image_viewer_fragment, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVMyProgressManager.removeModuleListener(PLVChatImageContainerWidget.LOADIMG_MOUDLE_TAG);
    }

    public void setImgList(List<PLVUrlTag> imgList, int position) {
        this.imgTagList = imgList;
        this.position = position;
        PLVChatImageViewer pLVChatImageViewer = this.imageViewer;
        if (pLVChatImageViewer != null) {
            pLVChatImageViewer.setDataList(imgList, position);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PLVChatImageViewerFragment show(AppCompatActivity activity, PLVBaseViewData selData, int containerViewId) {
        ArrayList arrayList = new ArrayList();
        if (selData.getData() instanceof PLVBaseEvent) {
            PLVBaseEvent pLVBaseEvent = (PLVBaseEvent) selData.getData();
            if (pLVBaseEvent instanceof IPLVQuoteEvent) {
                IPLVQuoteEvent iPLVQuoteEvent = (IPLVQuoteEvent) pLVBaseEvent;
                if (iPLVQuoteEvent.getQuote() != null && iPLVQuoteEvent.getQuote().getImage() != null) {
                    if (pLVBaseEvent.getObj3() instanceof PLVUrlTag) {
                        arrayList.add((PLVUrlTag) pLVBaseEvent.getObj3());
                    } else {
                        PLVUrlTag pLVUrlTag = new PLVUrlTag(iPLVQuoteEvent.getQuote().getImage().getUrl(), pLVBaseEvent);
                        pLVBaseEvent.setObj3(pLVUrlTag);
                        arrayList.add(pLVUrlTag);
                    }
                }
            }
        } else if (selData.getData() instanceof PLVChatPlaybackData) {
            PLVChatPlaybackData pLVChatPlaybackData = (PLVChatPlaybackData) selData.getData();
            PLVChatQuoteVO chatQuoteVO = ((PLVChatPlaybackData) selData.getData()).getChatQuoteVO();
            if (chatQuoteVO != null && chatQuoteVO.getImage() != null) {
                if (pLVChatPlaybackData.getObj3() instanceof PLVUrlTag) {
                    arrayList.add((PLVUrlTag) pLVChatPlaybackData.getObj3());
                } else {
                    PLVUrlTag pLVUrlTag2 = new PLVUrlTag(chatQuoteVO.getImage().getUrl(), pLVChatPlaybackData);
                    pLVChatPlaybackData.setObj3(pLVUrlTag2);
                    arrayList.add(pLVUrlTag2);
                }
            }
        }
        return show(activity, arrayList, 0, containerViewId);
    }

    private static PLVChatImageViewerFragment show(AppCompatActivity activity, List<PLVUrlTag> imgTagList, int position, int containerViewId) {
        if (ScreenUtils.isPortrait()) {
            PLVScreenUtils.enterLandscape(activity);
        }
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        PLVBaseFragment pLVBaseFragment = (PLVBaseFragment) supportFragmentManager.findFragmentByTag("PLVChatImageViewerFragment");
        if ((pLVBaseFragment instanceof PLVChatImageViewerFragment) && pLVBaseFragment.isAdded()) {
            PLVChatImageViewerFragment pLVChatImageViewerFragment = (PLVChatImageViewerFragment) pLVBaseFragment;
            pLVChatImageViewerFragment.setImgList(imgTagList, position);
            fragmentTransactionBeginTransaction.show(pLVBaseFragment).commitAllowingStateLoss();
            return pLVChatImageViewerFragment;
        }
        PLVChatImageViewerFragment pLVChatImageViewerFragmentNewInstance = newInstance();
        pLVChatImageViewerFragmentNewInstance.setImgList(imgTagList, position);
        fragmentTransactionBeginTransaction.add(containerViewId, pLVChatImageViewerFragmentNewInstance, "PLVChatImageViewerFragment").commitAllowingStateLoss();
        return pLVChatImageViewerFragmentNewInstance;
    }
}
