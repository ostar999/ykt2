package com.easefun.polyv.livecloudclass.modules.media.danmu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.easefun.polyv.livecloudclass.R;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.HashMap;
import net.polyv.danmaku.controller.DrawHandler;
import net.polyv.danmaku.controller.IDanmakuView;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.model.android.SpannedCacheStuffer;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;

/* loaded from: classes3.dex */
public class PLVLCDanmuFragment extends Fragment implements IPLVLCDanmuController {
    private DanmakuContext mContext;
    private IDanmakuView mDanmakuView;
    private BaseDanmakuParser mParser;
    private boolean statusCanautoResume = true;
    private boolean statusPauseFromUser = true;
    private View view;

    public class CustomSpannedCacheStuffer extends SpannedCacheStuffer {
        public CustomSpannedCacheStuffer() {
        }

        @Override // net.polyv.danmaku.danmaku.model.android.SpannedCacheStuffer, net.polyv.danmaku.danmaku.model.android.SimpleTextCacheStuffer
        public void drawStroke(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f2, float f3, Paint paint) {
            paint.setShadowLayer(ConvertUtils.dp2px(1.0f), 0.0f, 0.0f, Color.parseColor("#333333"));
        }
    }

    private void findIdAndNew() {
        IDanmakuView iDanmakuView = (IDanmakuView) this.view.findViewById(R.id.danmaku_dv);
        this.mDanmakuView = iDanmakuView;
        iDanmakuView.hide();
    }

    private void initView() {
        HashMap map = new HashMap();
        map.put(1, 2);
        map.put(5, 2);
        map.put(4, 2);
        HashMap map2 = new HashMap();
        Boolean bool = Boolean.TRUE;
        map2.put(1, bool);
        map2.put(5, bool);
        map2.put(4, bool);
        DanmakuContext danmakuContextCreate = DanmakuContext.create();
        this.mContext = danmakuContextCreate;
        danmakuContextCreate.setDanmakuStyle(2, 3.0f).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.0f).setCacheStuffer(new CustomSpannedCacheStuffer(), null).setMaximumLines(map).preventOverlapping(map2);
        if (getActivity() != null) {
            this.mContext.setFrameUpateRate((int) (1000.0f / getActivity().getWindowManager().getDefaultDisplay().getRefreshRate()));
        }
        this.mDanmakuView.showFPS(false);
        this.mDanmakuView.enableDanmakuDrawingCache(false);
        this.mDanmakuView.setCallback(new DrawHandler.Callback() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuFragment.1
            @Override // net.polyv.danmaku.controller.DrawHandler.Callback
            public void danmakuShown(BaseDanmaku baseDanmaku) {
            }

            @Override // net.polyv.danmaku.controller.DrawHandler.Callback
            public void drawingFinished() {
            }

            @Override // net.polyv.danmaku.controller.DrawHandler.Callback
            public void prepared() {
                PLVLCDanmuFragment.this.mDanmakuView.start();
            }

            @Override // net.polyv.danmaku.controller.DrawHandler.Callback
            public void updateTimer(DanmakuTimer danmakuTimer) {
            }
        });
        BaseDanmakuParser baseDanmakuParser = new BaseDanmakuParser() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuFragment.2
            @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
            public IDanmakus parse() {
                return new Danmakus();
            }
        };
        this.mParser = baseDanmakuParser;
        this.mDanmakuView.prepare(baseDanmakuParser, this.mContext);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController
    public void hide() {
        IDanmakuView iDanmakuView = this.mDanmakuView;
        if (iDanmakuView != null) {
            iDanmakuView.hide();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        findIdAndNew();
        initView();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.view == null) {
            this.view = layoutInflater.inflate(R.layout.plvlc_player_danmu_fragment, viewGroup, false);
        }
        return this.view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        release();
        this.mContext = null;
    }

    public void pause() {
        pause(true);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController
    public void release() {
        IDanmakuView iDanmakuView = this.mDanmakuView;
        if (iDanmakuView != null) {
            iDanmakuView.release();
            this.mDanmakuView = null;
        }
    }

    public void resume() {
        resume(true);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController
    public void sendDanmaku(CharSequence charSequence) {
        DanmakuContext danmakuContext = this.mContext;
        if (danmakuContext == null) {
            return;
        }
        BaseDanmaku baseDanmakuCreateDanmaku = danmakuContext.mDanmakuFactory.createDanmaku(1);
        baseDanmakuCreateDanmaku.text = charSequence;
        baseDanmakuCreateDanmaku.padding = 0;
        baseDanmakuCreateDanmaku.priority = (byte) 1;
        baseDanmakuCreateDanmaku.setTime(this.mDanmakuView.getCurrentTime() + 100);
        baseDanmakuCreateDanmaku.textColor = -1;
        baseDanmakuCreateDanmaku.textShadowColor = Color.parseColor("#333333");
        baseDanmakuCreateDanmaku.textSize = ConvertUtils.dp2px(14.0f);
        this.mDanmakuView.addDanmaku(baseDanmakuCreateDanmaku);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController
    public void show() {
        IDanmakuView iDanmakuView = this.mDanmakuView;
        if (iDanmakuView != null) {
            iDanmakuView.show();
        }
    }

    public void pause(boolean z2) {
        if (z2) {
            this.statusCanautoResume = false;
        } else {
            this.statusPauseFromUser = false;
        }
        IDanmakuView iDanmakuView = this.mDanmakuView;
        if (iDanmakuView == null || !iDanmakuView.isPrepared()) {
            return;
        }
        this.mDanmakuView.pause();
    }

    public void resume(boolean z2) {
        IDanmakuView iDanmakuView;
        boolean z3 = this.statusPauseFromUser;
        if ((!(z3 && z2) && (z3 || z2)) || (iDanmakuView = this.mDanmakuView) == null || !iDanmakuView.isPrepared() || !this.mDanmakuView.isPaused()) {
            return;
        }
        if (this.statusPauseFromUser) {
            this.statusCanautoResume = true;
            this.mDanmakuView.resume();
        } else {
            this.statusPauseFromUser = true;
            if (this.statusCanautoResume) {
                this.mDanmakuView.resume();
            }
        }
    }
}
