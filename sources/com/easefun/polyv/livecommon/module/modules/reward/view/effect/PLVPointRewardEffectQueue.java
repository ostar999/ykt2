package com.easefun.polyv.livecommon.module.modules.reward.view.effect;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class PLVPointRewardEffectQueue implements IPLVPointRewardEventProducer {
    private static final String TAG = "PLVPointRewardEffectQueue";
    private Queue<PLVRewardEvent> eventQueue = new LinkedList();
    private Condition eventQueueEmptyCondition;
    private HandlerThread handlerThread;
    private ReentrantLock lock;
    private Handler mainHandler;
    private Handler workerThreadHandler;

    public PLVPointRewardEffectQueue() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.eventQueueEmptyCondition = reentrantLock.newCondition();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer
    public void addEvent(PLVRewardEvent rewardEvent) {
        this.lock.lock();
        try {
            try {
                this.eventQueue.offer(rewardEvent);
                this.eventQueueEmptyCondition.signal();
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer
    public void destroy() {
        this.handlerThread.quit();
        this.handlerThread.interrupt();
        this.lock.lock();
        try {
            try {
                LogUtils.d("destroy, 清空eventQueue, eventQueue.size=" + this.eventQueue.size() + " eventQueue.clear");
                this.eventQueue.clear();
                this.eventQueueEmptyCondition.signal();
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer
    public void fetchEvent(final IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener onFetchRewardEventListener) {
        this.workerThreadHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue.1
            @Override // java.lang.Runnable
            public void run() {
                final PLVRewardEvent pLVRewardEvent;
                PLVPointRewardEffectQueue.this.lock.lock();
                try {
                    try {
                        pLVRewardEvent = (PLVRewardEvent) PLVPointRewardEffectQueue.this.eventQueue.poll();
                        while (pLVRewardEvent == null) {
                            PLVPointRewardEffectQueue.this.eventQueueEmptyCondition.await();
                            pLVRewardEvent = (PLVRewardEvent) PLVPointRewardEffectQueue.this.eventQueue.poll();
                            LogUtils.d("eventQueue.size=" + PLVPointRewardEffectQueue.this.eventQueue.size() + "  poll=" + pLVRewardEvent);
                        }
                        LogUtils.d("从循环中跳出");
                    } catch (InterruptedException unused) {
                        PLVCommonLog.i(PLVPointRewardEffectQueue.TAG, PLVPointRewardEffectQueue.this.workerThreadHandler.toString() + "被中断");
                    } catch (Exception e2) {
                        PLVCommonLog.exception(e2);
                    }
                    if (PLVPointRewardEffectQueue.this.handlerThread.isInterrupted()) {
                        LogUtils.d("线程被中断了，返回");
                    } else {
                        LogUtils.d("发送event");
                        PLVPointRewardEffectQueue.this.mainHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener iPLVOnFetchRewardEventListener = onFetchRewardEventListener;
                                if (iPLVOnFetchRewardEventListener != null) {
                                    iPLVOnFetchRewardEventListener.onFetchSucceed(pLVRewardEvent);
                                }
                            }
                        });
                    }
                } finally {
                    PLVPointRewardEffectQueue.this.lock.unlock();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer
    public void prepare(final IPLVPointRewardEventProducer.OnPreparedListener onPreparedListener) {
        final WeakReference weakReference = new WeakReference(onPreparedListener);
        HandlerThread handlerThread = new HandlerThread("PolyvPointRewardEffectQueue-HandlerThread") { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue.2
            @Override // android.os.HandlerThread
            public void onLooperPrepared() {
                PLVPointRewardEffectQueue.this.workerThreadHandler = new Handler(PLVPointRewardEffectQueue.this.handlerThread.getLooper());
                PLVPointRewardEffectQueue.this.mainHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        IPLVPointRewardEventProducer.OnPreparedListener onPreparedListener2 = (IPLVPointRewardEventProducer.OnPreparedListener) weakReference.get();
                        if (onPreparedListener2 != null) {
                            onPreparedListener2.onPrepared();
                        }
                    }
                });
            }
        };
        this.handlerThread = handlerThread;
        handlerThread.start();
    }
}
