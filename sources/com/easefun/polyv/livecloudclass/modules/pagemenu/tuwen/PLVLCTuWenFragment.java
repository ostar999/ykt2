package com.easefun.polyv.livecloudclass.modules.pagemenu.tuwen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.easefun.polyv.livescenes.video.PolyvTuWenWebView;
import com.google.android.exoplayer2.C;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVLCTuWenFragment extends PLVBaseFragment {
    private static final String REPLACEMENT = "\\\\u0027";
    private String channelId;
    private PLVSocketIOObservable.OnConnectStatusListener onConnectStatusListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private ViewGroup parentLy;
    private PolyvTuWenWebView tuWenWebView;

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.parent_ly);
        this.parentLy = viewGroup;
        viewGroup.setBackgroundColor(Color.parseColor("#141518"));
        this.tuWenWebView = new PolyvTuWenWebView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.bottomMargin = ConvertUtils.dp2px(8.0f);
        this.tuWenWebView.setLayoutParams(layoutParams);
        this.parentLy.addView(this.tuWenWebView);
        this.tuWenWebView.loadWeb();
        this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.tuwen.PLVLCTuWenFragment.1
            @Override // java.lang.Runnable
            public void run() {
                PLVLCTuWenFragment.this.tuWenWebView.callInit(PLVLCTuWenFragment.this.channelId);
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        observeDataChangedWithSocket();
    }

    private void observeDataChangedWithSocket() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.tuwen.PLVLCTuWenFragment.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (PLVEventConstant.TuWen.EVENT_CREATE_IMAGE_TEXT.equals(str2)) {
                    PLVLCTuWenFragment.this.tuWenWebView.callCreate(str3.replaceAll("'", PLVLCTuWenFragment.REPLACEMENT));
                    return;
                }
                if (PLVEventConstant.TuWen.EVENT_DELETE_IMAGE_TEXT.equals(str2)) {
                    PLVLCTuWenFragment.this.tuWenWebView.callDelete(str3.replaceAll("'", PLVLCTuWenFragment.REPLACEMENT));
                } else if (PLVEventConstant.TuWen.EVENT_SET_TOP_IMAGE_TEXT.equals(str2)) {
                    PLVLCTuWenFragment.this.tuWenWebView.callSetTop(str3.replaceAll("'", PLVLCTuWenFragment.REPLACEMENT));
                } else if (PLVEventConstant.TuWen.EVENT_SET_IMAGE_TEXT_MSG.equals(str2)) {
                    PLVLCTuWenFragment.this.tuWenWebView.callUpdate(str3.replaceAll("'", PLVLCTuWenFragment.REPLACEMENT));
                }
            }
        };
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
        this.onConnectStatusListener = new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.tuwen.PLVLCTuWenFragment.3
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus pLVSocketStatus) {
                if (4 == pLVSocketStatus.getStatus()) {
                    PLVLCTuWenFragment.this.tuWenWebView.callRefresh(PLVLCTuWenFragment.this.channelId);
                }
            }
        };
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(this.onConnectStatusListener);
    }

    public void init(String str) {
        this.channelId = str;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(com.easefun.polyv.livecloudclass.R.layout.plv_horizontal_linear_layout, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnConnectStatusListener(this.onConnectStatusListener);
        PolyvTuWenWebView polyvTuWenWebView = this.tuWenWebView;
        if (polyvTuWenWebView != null) {
            if (polyvTuWenWebView.getParent() != null) {
                ((ViewGroup) this.tuWenWebView.getParent()).removeView(this.tuWenWebView);
            }
            this.tuWenWebView.removeAllViews();
            this.tuWenWebView.destroy();
            this.tuWenWebView = null;
        }
    }
}
