package com.easefun.polyv.livecommon.module.modules.reward.view.effect;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.plv.socket.event.chat.PLVRewardEvent;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PLVRewardSVGAHelper {
    private RewardHandler handler;
    private WeakReference<SVGAImageView> imageViewRef;
    private SVGAParser parser;
    private final String TAG = getClass().getSimpleName();
    private Queue<PLVRewardEvent> eventQueue = new LinkedList();
    String[] svgaFile = null;

    public static class RewardHandler extends Handler {
        private static final int ADD_EVENT = 2;
        private static final int FETCH_EVENT = 1;
        private WeakReference<PLVRewardSVGAHelper> helperWeakReference;
        private volatile boolean isFetching;

        public RewardHandler(Looper looper, PLVRewardSVGAHelper helper) {
            super(looper);
            this.isFetching = false;
            this.helperWeakReference = new WeakReference<>(helper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void makeRewardSvga() {
            synchronized (PLVRewardSVGAHelper.class) {
                final PLVRewardSVGAHelper pLVRewardSVGAHelper = this.helperWeakReference.get();
                PLVRewardEvent pLVRewardEvent = (PLVRewardEvent) pLVRewardSVGAHelper.eventQueue.poll();
                if (pLVRewardEvent != null) {
                    synchronized (this) {
                        this.isFetching = true;
                    }
                    String gimg = pLVRewardEvent.getContent().getGimg();
                    if (!TextUtils.isEmpty(gimg) && gimg.contains("/")) {
                        String strHitSvgaFile = pLVRewardSVGAHelper.hitSvgaFile(gimg.substring(gimg.lastIndexOf("-") + 1, gimg.lastIndexOf(StrPool.DOT)));
                        if (TextUtils.isEmpty(strHitSvgaFile)) {
                            pLVRewardSVGAHelper.handler.sendEmptyMessage(1);
                        } else {
                            pLVRewardSVGAHelper.parser.decodeFromAssets("svg/" + strHitSvgaFile, new SVGAParser.ParseCompletion() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper.RewardHandler.2
                                @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                                    ((SVGAImageView) pLVRewardSVGAHelper.imageViewRef.get()).setVideoItem(svgaVideoEntity);
                                    ((SVGAImageView) pLVRewardSVGAHelper.imageViewRef.get()).startAnimation();
                                }

                                @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                                public void onError() {
                                }
                            }, null);
                            ((SVGAImageView) pLVRewardSVGAHelper.imageViewRef.get()).setCallback(new SVGACallback() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper.RewardHandler.3
                                @Override // com.opensource.svgaplayer.SVGACallback
                                public void onFinished() {
                                    pLVRewardSVGAHelper.handler.sendEmptyMessage(1);
                                }

                                @Override // com.opensource.svgaplayer.SVGACallback
                                public void onPause() {
                                }

                                @Override // com.opensource.svgaplayer.SVGACallback
                                public void onRepeat() {
                                }

                                @Override // com.opensource.svgaplayer.SVGACallback
                                public void onStep(int i2, double v2) {
                                }
                            });
                        }
                    }
                } else {
                    synchronized (this) {
                        this.isFetching = false;
                    }
                }
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 1) {
                post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper.RewardHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RewardHandler.this.makeRewardSvga();
                    }
                });
            } else if (i2 == 2 && !this.isFetching) {
                synchronized (this) {
                    this.isFetching = true;
                }
                sendEmptyMessage(1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String hitSvgaFile(String name) {
        if (this.svgaFile == null) {
            return "";
        }
        int i2 = 0;
        while (true) {
            String[] strArr = this.svgaFile;
            if (i2 >= strArr.length) {
                return "";
            }
            if (strArr[i2].replace(".svga", "").equals(name)) {
                return this.svgaFile[i2];
            }
            i2++;
        }
    }

    public void addEvent(PLVRewardEvent event) {
        synchronized (PLVRewardSVGAHelper.class) {
            this.eventQueue.add(event);
            this.handler.sendEmptyMessage(2);
        }
    }

    public void clear() {
        synchronized (PLVRewardSVGAHelper.class) {
            this.eventQueue.clear();
            this.handler.removeCallbacksAndMessages(null);
            WeakReference<SVGAImageView> weakReference = this.imageViewRef;
            if (weakReference != null) {
                weakReference.get().clearAnimation();
                this.imageViewRef.get().clear();
            }
        }
    }

    public void init(SVGAImageView imageView, SVGAParser parser) {
        this.parser = parser;
        imageView.setLoops(1);
        imageView.setFillMode(SVGAImageView.FillMode.Clear);
        this.imageViewRef = new WeakReference<>(imageView);
        if (this.handler == null) {
            this.handler = new RewardHandler(Looper.getMainLooper(), this);
        }
        try {
            this.svgaFile = imageView.getContext().getAssets().list("svg");
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.e(this.TAG, "svga file list failed in assets");
        }
    }
}
