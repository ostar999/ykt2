package com.easefun.polyv.livecloudclass.modules.chatroom.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView;
import com.plv.socket.event.login.PLVLoginEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVLCGreetingTextView extends PLVMarqueeTextView {
    private Disposable acceptLoginDisposable;
    private boolean isStart;
    private List<PLVLoginEvent> loginEventList;
    private int rollDuration;
    private Runnable runnable;

    public PLVLCGreetingTextView(Context context) {
        super(context);
        this.loginEventList = new ArrayList();
    }

    private int getScrollTime() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getStayTime() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGreetingText() {
        final SpannableStringBuilder spannableStringBuilder;
        if (this.loginEventList.isEmpty()) {
            stopScroll();
            setVisibility(4);
            ((ViewGroup) getParent()).setVisibility(8);
            ((ViewGroup) getParent()).clearAnimation();
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
            scaleAnimation.setDuration(500L);
            ((ViewGroup) getParent()).startAnimation(scaleAnimation);
            this.isStart = !this.isStart;
            return;
        }
        final int scrollTime = getScrollTime();
        this.rollDuration = scrollTime * 1000;
        if (this.loginEventList.size() >= 10) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 <= 2; i2++) {
                PLVLoginEvent pLVLoginEvent = this.loginEventList.get(i2);
                if (i2 != 2) {
                    sb.append(pLVLoginEvent.getUser().getNick());
                    sb.append("、");
                } else {
                    sb.append(pLVLoginEvent.getUser().getNick());
                }
                if (i2 == 0) {
                    sb.toString().length();
                } else if (i2 == 1) {
                    sb.toString().length();
                }
            }
            spannableStringBuilder = new SpannableStringBuilder("欢迎 " + sb.toString() + " 等" + this.loginEventList.size() + "人加入");
            this.loginEventList.clear();
        } else {
            spannableStringBuilder = new SpannableStringBuilder("欢迎 " + this.loginEventList.remove(0).getUser().getNick() + " 加入");
        }
        this.acceptLoginDisposable = Observable.just(1).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Integer>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) throws Exception {
                ((ViewGroup) PLVLCGreetingTextView.this.getParent()).setVisibility(0);
                PLVLCGreetingTextView.this.setVisibility(4);
                PLVLCGreetingTextView.this.setText(spannableStringBuilder);
                PLVLCGreetingTextView.this.setStopToCenter(true);
                PLVLCGreetingTextView.this.setRndDuration(scrollTime * 1000);
                PLVLCGreetingTextView.this.setOnGetRollDurationListener(new PLVMarqueeTextView.OnGetRollDurationListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.4.1
                    @Override // com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView.OnGetRollDurationListener
                    public void onFirstGetRollDuration(int i3) {
                        PLVLCGreetingTextView.this.rollDuration = i3;
                    }
                });
                PLVLCGreetingTextView.this.stopScroll();
                PLVLCGreetingTextView.this.startScroll();
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.3
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(Integer num) throws Exception {
                return Observable.timer(PLVLCGreetingTextView.this.rollDuration + (PLVLCGreetingTextView.this.getStayTime() * 1000), TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                PLVLCGreetingTextView.this.showGreetingText();
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    public void acceptLoginEvent(PLVLoginEvent pLVLoginEvent) {
        this.loginEventList.add(pLVLoginEvent);
        boolean z2 = this.isStart;
        if (z2) {
            return;
        }
        this.isStart = !z2;
        if (getWidth() > 0) {
            showGreetingText();
            return;
        }
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.5
            @Override // java.lang.Runnable
            public void run() {
                ((ViewGroup) PLVLCGreetingTextView.this.getParent()).setVisibility(0);
                PLVLCGreetingTextView.this.runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLCGreetingTextView.this.showGreetingText();
                    }
                };
                PLVLCGreetingTextView pLVLCGreetingTextView = PLVLCGreetingTextView.this;
                pLVLCGreetingTextView.post(pLVLCGreetingTextView.runnable);
            }
        };
        this.runnable = runnable;
        post(runnable);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.loginEventList.clear();
        removeCallbacks(this.runnable);
        Disposable disposable = this.acceptLoginDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public PLVLCGreetingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loginEventList = new ArrayList();
    }

    public PLVLCGreetingTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        ArrayList arrayList = new ArrayList();
        this.loginEventList = arrayList;
        this.loginEventList = Collections.synchronizedList(arrayList);
    }
}
