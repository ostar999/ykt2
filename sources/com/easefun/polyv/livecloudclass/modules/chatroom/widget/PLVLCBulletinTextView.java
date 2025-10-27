package com.easefun.polyv.livecloudclass.modules.chatroom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVLCBulletinTextView extends PLVMarqueeTextView {
    private Disposable bulletinCdDisposable;

    public PLVLCBulletinTextView(Context context) {
        super(context);
    }

    private void disposeBulletinCd() {
        Disposable disposable = this.bulletinCdDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.bulletinCdDisposable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountDown(long j2) {
        disposeBulletinCd();
        this.bulletinCdDisposable = Observable.timer(j2, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVLCBulletinTextView.this.setVisibility(4);
                PLVLCBulletinTextView.this.stopScroll();
                ((ViewGroup) PLVLCBulletinTextView.this.getParent()).setVisibility(8);
                ((ViewGroup) PLVLCBulletinTextView.this.getParent()).clearAnimation();
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
                scaleAnimation.setDuration(500L);
                ((ViewGroup) PLVLCBulletinTextView.this.getParent()).startAnimation(scaleAnimation);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposeBulletinCd();
    }

    public void startMarquee(final CharSequence charSequence) {
        disposeBulletinCd();
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView.1
            @Override // java.lang.Runnable
            public void run() {
                ((ViewGroup) PLVLCBulletinTextView.this.getParent()).setVisibility(0);
                PLVLCBulletinTextView.this.setText(charSequence);
                PLVLCBulletinTextView.this.setOnGetRollDurationListener(new PLVMarqueeTextView.OnGetRollDurationListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView.1.1
                    @Override // com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView.OnGetRollDurationListener
                    public void onFirstGetRollDuration(int i2) {
                        PLVLCBulletinTextView.this.startCountDown((i2 * 3) + r0.getScrollFirstDelay());
                    }
                });
                PLVLCBulletinTextView.this.stopScroll();
                PLVLCBulletinTextView.this.startScroll();
            }
        });
    }

    public PLVLCBulletinTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PLVLCBulletinTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
