package com.aliyun.player.alivcplayerexpand.view.function;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/* loaded from: classes2.dex */
public class PlayerDanmakuView extends DanmakuView {
    private BaseDanmakuParser mBaseDanmakuParser;
    private int mDanmaType;
    private DanmakuContext mDanmakuContext;
    private Map<Integer, ArrayList<String>> mDanmakuList;
    private HashMap<Integer, Integer> mMaxLinesPair;
    private HashMap<Integer, Boolean> mOverLappingEnablePair;
    private AliyunScreenMode mScreenMode;
    private float mSpeed;
    private int mTextColor;
    private float mTextSize;

    public PlayerDanmakuView(Context context) {
        super(context);
        this.mSpeed = 1.0f;
        this.mTextSize = 8.0f;
        this.mTextColor = -1;
        this.mDanmaType = 1;
        this.mDanmakuList = new HashMap();
        this.mScreenMode = AliyunScreenMode.Small;
        this.mBaseDanmakuParser = new BaseDanmakuParser() { // from class: com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView.1
            @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
            public IDanmakus parse() {
                return new Danmakus();
            }
        };
        init();
    }

    private void init() {
        this.mDanmakuContext = DanmakuContext.create();
        this.mMaxLinesPair = new HashMap<>();
        this.mOverLappingEnablePair = new HashMap<>();
        this.mMaxLinesPair.put(1, 3);
        this.mOverLappingEnablePair.put(1, Boolean.TRUE);
        initDanmakuContext();
        initCallback();
    }

    private void initCallback() {
        setCallback(new DrawHandler.Callback() { // from class: com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView.2
            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void danmakuShown(BaseDanmaku baseDanmaku) {
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void drawingFinished() {
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void prepared() {
                PlayerDanmakuView.this.start();
            }

            @Override // master.flame.danmaku.controller.DrawHandler.Callback
            public void updateTimer(DanmakuTimer danmakuTimer) {
            }
        });
    }

    private void initDanmakuContext() {
        this.mDanmakuContext.setDanmakuStyle(-1, 3.0f).setDuplicateMergingEnabled(true).setScrollSpeedFactor(this.mSpeed).setScaleTextSize(this.mTextSize).setMaximumLines(this.mMaxLinesPair).setMaximumVisibleSizeInScreen(0).preventOverlapping(this.mOverLappingEnablePair).setDanmakuMargin(5);
        enableDanmakuDrawingCache(true);
        prepare(this.mBaseDanmakuParser, this.mDanmakuContext);
    }

    public void addDanmaku(String str, long j2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        BaseDanmaku baseDanmakuCreateDanmaku = this.mDanmakuContext.mDanmakuFactory.createDanmaku(this.mDanmaType);
        baseDanmakuCreateDanmaku.text = str;
        baseDanmakuCreateDanmaku.textSize = this.mTextSize;
        baseDanmakuCreateDanmaku.setTime(getCurrentTime());
        baseDanmakuCreateDanmaku.textColor = this.mTextColor;
        addDanmaku(baseDanmakuCreateDanmaku);
        int i2 = (int) (j2 / 1000);
        ArrayList<String> arrayList = this.mDanmakuList.get(Integer.valueOf(i2));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(str);
        this.mDanmakuList.put(Integer.valueOf(i2), arrayList);
    }

    public void clearDanmaList() {
        Map<Integer, ArrayList<String>> map = this.mDanmakuList;
        if (map == null || map.size() <= 0) {
            return;
        }
        this.mDanmakuList.clear();
    }

    public boolean danmuIsShown() {
        return isShown();
    }

    public void setCurrentPosition(int i2) {
        if (this.mScreenMode == AliyunScreenMode.Small || !isShown()) {
            return;
        }
        int i3 = i2 / 1000;
        if (i3 == 1) {
            addDanmaku(getResources().getString(R.string.alivc_danmaku_text_1));
        }
        if (i3 == 2) {
            addDanmaku(getResources().getString(R.string.alivc_danmaku_text_2));
        }
        if (i3 == 3) {
            addDanmaku(getResources().getString(R.string.alivc_danmaku_text_3));
        }
    }

    public void setDanmakuRegion(int i2) {
        HashMap<Integer, Integer> map = this.mMaxLinesPair;
        if (map != null) {
            if (i2 == 0) {
                if (this.mDanmakuContext != null) {
                    map.put(1, 3);
                    this.mDanmakuContext.setMaximumLines(this.mMaxLinesPair);
                    return;
                }
                return;
            }
            if (i2 == 1) {
                if (this.mDanmakuContext != null) {
                    map.put(1, 5);
                    this.mDanmakuContext.setMaximumLines(this.mMaxLinesPair);
                    return;
                }
                return;
            }
            if (i2 == 2) {
                if (this.mDanmakuContext != null) {
                    map.put(1, 7);
                    this.mDanmakuContext.setMaximumLines(null);
                    return;
                }
                return;
            }
            if (i2 != 3) {
                if (this.mDanmakuContext != null) {
                    map.put(1, 3);
                    this.mDanmakuContext.setMaximumLines(null);
                    return;
                }
                return;
            }
            DanmakuContext danmakuContext = this.mDanmakuContext;
            if (danmakuContext != null) {
                danmakuContext.setMaximumLines(null);
            }
        }
    }

    public void setDanmakuSpeed(float f2) {
        DanmakuContext danmakuContext = this.mDanmakuContext;
        if (danmakuContext != null) {
            if (f2 <= 0.01d) {
                f2 = 0.01f;
            }
            danmakuContext.setScrollSpeedFactor(f2);
        }
    }

    public void setScreenMode(AliyunScreenMode aliyunScreenMode) {
        this.mScreenMode = aliyunScreenMode;
    }

    public void switchDanmaku(boolean z2) {
        if (z2) {
            resume();
            show();
        } else {
            pause();
            hide();
        }
    }

    public PlayerDanmakuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSpeed = 1.0f;
        this.mTextSize = 8.0f;
        this.mTextColor = -1;
        this.mDanmaType = 1;
        this.mDanmakuList = new HashMap();
        this.mScreenMode = AliyunScreenMode.Small;
        this.mBaseDanmakuParser = new BaseDanmakuParser() { // from class: com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView.1
            @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
            public IDanmakus parse() {
                return new Danmakus();
            }
        };
        init();
    }

    public void addDanmaku(String str) {
        BaseDanmaku baseDanmakuCreateDanmaku;
        if (TextUtils.isEmpty(str) || (baseDanmakuCreateDanmaku = this.mDanmakuContext.mDanmakuFactory.createDanmaku(this.mDanmaType)) == null) {
            return;
        }
        baseDanmakuCreateDanmaku.text = str;
        baseDanmakuCreateDanmaku.textSize = this.mTextSize;
        baseDanmakuCreateDanmaku.textColor = this.mTextColor;
        baseDanmakuCreateDanmaku.setTime(getCurrentTime());
        addDanmaku(baseDanmakuCreateDanmaku);
    }

    public PlayerDanmakuView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mSpeed = 1.0f;
        this.mTextSize = 8.0f;
        this.mTextColor = -1;
        this.mDanmaType = 1;
        this.mDanmakuList = new HashMap();
        this.mScreenMode = AliyunScreenMode.Small;
        this.mBaseDanmakuParser = new BaseDanmakuParser() { // from class: com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView.1
            @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
            public IDanmakus parse() {
                return new Danmakus();
            }
        };
        init();
    }
}
