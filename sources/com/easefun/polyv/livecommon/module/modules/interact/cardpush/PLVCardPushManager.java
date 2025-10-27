package com.easefun.polyv.livecommon.module.modules.interact.cardpush;

import android.graphics.Color;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVTimeUtils;
import com.plv.livescenes.model.interact.PLVCardPushVO;
import com.plv.socket.event.interact.PLVNewsPushStartEvent;
import com.plv.socket.event.interact.PLVShowPushCardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVCardPushManager {
    private boolean canSendCardPushEvent;
    private Disposable cardLookCountdownTask;
    private OnCardEnterClickListener onCardEnterClickListener;
    private Disposable requestCardPushInfoTask;
    private PLVShowPushCardEvent showPushCardEvent;
    private List<ImageView> cardEnterViews = new ArrayList();
    private List<TextView> cardEnterCdTvs = new ArrayList();
    private List<PLVTriangleIndicateTextView> cardEnterTipsViews = new ArrayList();

    public interface OnCardEnterClickListener {
        void onClick(PLVShowPushCardEvent event);
    }

    public interface RunnableT<T> {
        void run(@NonNull T t2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCardPushVO(final PLVCardPushVO cardPushVO, PLVNewsPushStartEvent newsPushStartEvent) {
        String id = newsPushStartEvent.getId();
        final int lookTime = newsPushStartEvent.getLookTime();
        int cache = PLVCardLookTimeLocalRepository.getCache(id);
        final int i2 = lookTime - cache;
        boolean zIsEntrance = newsPushStartEvent.isEntrance();
        final boolean z2 = zIsEntrance || i2 > 0;
        this.canSendCardPushEvent = i2 <= 0;
        forView(new RunnableT<ImageView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.6
            @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
            public void run(@NonNull ImageView cardEnterView) {
                cardEnterView.setVisibility(z2 ? 0 : 8);
                if (cardPushVO.isRedpackType()) {
                    cardEnterView.setImageResource(R.drawable.plv_interact_redpack_gain);
                } else if (cardPushVO.isGiftboxType()) {
                    cardEnterView.setImageResource(R.drawable.plv_interact_giftbox_gain);
                } else if (cardPushVO.isCustomType()) {
                    PLVImageLoader.getInstance().loadImage(cardPushVO.getData().getEnterImage(), cardEnterView);
                }
            }
        }, this.cardEnterViews);
        forView(new RunnableT<TextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.7
            @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
            public void run(@NonNull TextView cardEnterCdTv) {
                cardEnterCdTv.setVisibility((!z2 || i2 <= 0) ? 8 : 0);
                int i3 = i2;
                if (i3 > 0) {
                    cardEnterCdTv.setText(PLVTimeUtils.generateTime(i3, true));
                }
            }
        }, this.cardEnterCdTvs);
        forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.8
            @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
            public void run(@NonNull final PLVTriangleIndicateTextView cardEnterTipsView) {
                cardEnterTipsView.removeCallbacks(PLVCardPushManager.this.getTag(cardEnterTipsView));
                cardEnterTipsView.setVisibility((!z2 || lookTime <= 0) ? 8 : 0);
                cardEnterTipsView.setText(cardPushVO.getTipsMsg());
                if (cardPushVO.isGiftboxType()) {
                    cardEnterTipsView.setColor(Color.parseColor("#F6A125"), Color.parseColor("#FD8121"));
                } else {
                    cardEnterTipsView.setColor(Color.parseColor("#FF9D4D"), Color.parseColor("#F65F49"));
                }
                if (cardEnterTipsView.getTrianglePosition() == 3) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) cardEnterTipsView.getLayoutParams();
                    marginLayoutParams.bottomMargin = ConvertUtils.dp2px(i2 > 0 ? 108.0f : 98.0f);
                    cardEnterTipsView.setLayoutParams(marginLayoutParams);
                }
                if (i2 > 0) {
                    Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            cardEnterTipsView.setVisibility(8);
                        }
                    };
                    cardEnterTipsView.setTag(runnable);
                    cardEnterTipsView.postDelayed(runnable, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                }
                if (cardEnterTipsView.getVisibility() == 0 && cardEnterTipsView.getTrianglePosition() == 1) {
                    cardEnterTipsView.setTranslationX(-((ConvertUtils.dp2px(36.0f) - (Layout.getDesiredWidth(cardEnterTipsView.getText(), cardEnterTipsView.getPaint()) + (cardEnterTipsView.getPaddingStart() * 2))) / 2.0f));
                }
            }
        }, this.cardEnterTipsViews);
        this.showPushCardEvent = new PLVShowPushCardEvent(newsPushStartEvent);
        if (i2 > 0) {
            startCardLookCountdownTask(id, i2, cache, zIsEntrance);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void forView(RunnableT<T> runnable, List<T> views) {
        for (T t2 : views) {
            if (t2 != null) {
                runnable.run(t2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Runnable getTag(PLVTriangleIndicateTextView view) {
        if (view.getTag() instanceof Runnable) {
            return (Runnable) view.getTag();
        }
        return null;
    }

    private void startCardLookCountdownTask(final String id, final int needLookTime, final int alreadyLookTime, final boolean isEntrance) {
        disposeCardPushAllTask(false);
        this.cardLookCountdownTask = Observable.intervalRange(1L, needLookTime / 1000, 1000L, 1000L, TimeUnit.MILLISECONDS).doOnNext(new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.11
            @Override // io.reactivex.functions.Consumer
            public void accept(Long aLong) throws Exception {
                PLVCardLookTimeLocalRepository.saveCache(id, (int) (alreadyLookTime + (aLong.longValue() * 1000)));
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Long aLong) throws Exception {
                final int iLongValue = (int) (needLookTime - (aLong.longValue() * 1000));
                PLVCardPushManager.this.forView(new RunnableT<TextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.9.1
                    @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                    public void run(@NonNull TextView textView) {
                        textView.setText(PLVTimeUtils.generateTime(iLongValue, true));
                        if (iLongValue <= 0) {
                            textView.setVisibility(8);
                            PLVCardPushManager.this.forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.9.1.1
                                @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                                public void run(@NonNull PLVTriangleIndicateTextView plvTriangleIndicateTextView) {
                                    if (plvTriangleIndicateTextView.getTrianglePosition() == 3) {
                                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) plvTriangleIndicateTextView.getLayoutParams();
                                        marginLayoutParams.bottomMargin = ConvertUtils.dp2px(98.0f);
                                        plvTriangleIndicateTextView.setLayoutParams(marginLayoutParams);
                                    }
                                }
                            }, PLVCardPushManager.this.cardEnterTipsViews);
                        }
                    }
                }, PLVCardPushManager.this.cardEnterCdTvs);
                if (iLongValue <= 0) {
                    PLVCardPushManager.this.canSendCardPushEvent = true;
                    if (PLVCardPushManager.this.onCardEnterClickListener != null && PLVCardPushManager.this.showPushCardEvent != null) {
                        PLVCardPushManager.this.onCardEnterClickListener.onClick(PLVCardPushManager.this.showPushCardEvent);
                    }
                    PLVCardPushManager.this.forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.9.2
                        @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                        public void run(@NonNull PLVTriangleIndicateTextView plvTriangleIndicateTextView) {
                            plvTriangleIndicateTextView.removeCallbacks(PLVCardPushManager.this.getTag(plvTriangleIndicateTextView));
                            plvTriangleIndicateTextView.setVisibility(8);
                        }
                    }, PLVCardPushManager.this.cardEnterTipsViews);
                    if (isEntrance) {
                        return;
                    }
                    PLVCardPushManager.this.forView(new RunnableT<ImageView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.9.3
                        @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                        public void run(@NonNull ImageView imageView) {
                            imageView.setVisibility(8);
                        }
                    }, PLVCardPushManager.this.cardEnterViews);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.10
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    public void acceptNewsPushCancelMessage() {
        disposeCardPushAllTask();
        AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.4
            @Override // java.lang.Runnable
            public void run() {
                PLVCardPushManager.this.forView(new RunnableT<ImageView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.4.1
                    @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                    public void run(@NonNull ImageView imageView) {
                        imageView.setVisibility(8);
                    }
                }, PLVCardPushManager.this.cardEnterViews);
                PLVCardPushManager.this.forView(new RunnableT<TextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.4.2
                    @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                    public void run(@NonNull TextView textView) {
                        textView.setVisibility(8);
                    }
                }, PLVCardPushManager.this.cardEnterCdTvs);
                PLVCardPushManager.this.forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.4.3
                    @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                    public void run(@NonNull PLVTriangleIndicateTextView plvTriangleIndicateTextView) {
                        plvTriangleIndicateTextView.setVisibility(8);
                    }
                }, PLVCardPushManager.this.cardEnterTipsViews);
            }
        });
    }

    public void acceptNewsPushStartMessage(IPLVChatroomContract.IChatroomPresenter chatroomPresenter, final PLVNewsPushStartEvent newsPushStartEvent) {
        disposeCardPushAllTask();
        if (chatroomPresenter == null) {
            return;
        }
        this.requestCardPushInfoTask = chatroomPresenter.getCardPushInfo(newsPushStartEvent.getId()).subscribe(new Consumer<PLVCardPushVO>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.2
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVCardPushVO plvCardPushVO) throws Exception {
                PLVCardPushManager.this.acceptCardPushVO(plvCardPushVO, newsPushStartEvent);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    public void disposeCardPushAllTask() {
        disposeCardPushAllTask(true);
    }

    public void registerView(ImageView cardEnterView, TextView cardEnterCdTv, final PLVTriangleIndicateTextView cardEnterTipsView) {
        this.cardEnterViews.add(cardEnterView);
        if (cardEnterView != null) {
            cardEnterView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (PLVCardPushManager.this.canSendCardPushEvent && PLVCardPushManager.this.onCardEnterClickListener != null && PLVCardPushManager.this.showPushCardEvent != null) {
                        PLVCardPushManager.this.onCardEnterClickListener.onClick(PLVCardPushManager.this.showPushCardEvent);
                    }
                    final boolean[] zArr = {false};
                    PLVCardPushManager.this.forView(new RunnableT<TextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.1.1
                        @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                        public void run(@NonNull TextView textView) {
                            if (textView.getVisibility() == 0) {
                                zArr[0] = true;
                            }
                        }
                    }, PLVCardPushManager.this.cardEnterCdTvs);
                    if (zArr[0]) {
                        PLVCardPushManager.this.forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.1.2
                            @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                            public void run(@NonNull final PLVTriangleIndicateTextView plvTriangleIndicateTextView) {
                                plvTriangleIndicateTextView.setVisibility(0);
                                plvTriangleIndicateTextView.removeCallbacks(PLVCardPushManager.this.getTag(plvTriangleIndicateTextView));
                                Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.1.2.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        plvTriangleIndicateTextView.setVisibility(8);
                                    }
                                };
                                plvTriangleIndicateTextView.setTag(runnable);
                                plvTriangleIndicateTextView.postDelayed(runnable, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                            }
                        }, PLVCardPushManager.this.cardEnterTipsViews);
                    }
                }
            });
        }
        this.cardEnterCdTvs.add(cardEnterCdTv);
        this.cardEnterTipsViews.add(cardEnterTipsView);
    }

    public void setOnCardEnterClickListener(OnCardEnterClickListener listener) {
        this.onCardEnterClickListener = listener;
    }

    public void disposeCardPushAllTask(boolean isRemoveTipsViewTask) {
        Disposable disposable = this.cardLookCountdownTask;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.requestCardPushInfoTask;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        if (isRemoveTipsViewTask) {
            forView(new RunnableT<PLVTriangleIndicateTextView>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.5
                @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.RunnableT
                public void run(@NonNull PLVTriangleIndicateTextView view) {
                    view.removeCallbacks(PLVCardPushManager.this.getTag(view));
                }
            }, this.cardEnterTipsViews);
        }
    }
}
