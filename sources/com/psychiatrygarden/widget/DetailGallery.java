package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;
import java.util.TimerTask;

@SuppressLint({"HandlerLeak"})
/* loaded from: classes6.dex */
public class DetailGallery extends Gallery {
    private MyHandler handler;
    public MyTimerTask task;

    public class MyHandler extends Handler {
        private MyHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                DetailGallery detailGallery = DetailGallery.this;
                detailGallery.setSelection(detailGallery.getSelectedItemPosition() + 1);
            }
        }
    }

    public class MyTimerTask extends TimerTask {
        public MyTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Message message = new Message();
            message.what = 1;
            DetailGallery.this.handler.sendMessage(message);
        }
    }

    public DetailGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.handler = new MyHandler();
    }

    public MyTimerTask initTask() {
        MyTimerTask myTimerTask = new MyTimerTask();
        this.task = myTimerTask;
        return myTimerTask;
    }

    @Override // android.widget.Gallery, android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent e12, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    public DetailGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.handler = new MyHandler();
    }

    public DetailGallery(Context context) {
        super(context);
        this.handler = new MyHandler();
    }
}
