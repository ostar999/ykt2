package com.catchpig.mvvm.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.utils.DateUtil;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class CountDownView extends LinearLayout {
    private static final int UPDATE_UI_CODE = 101;
    private Context context;
    private CountDownEndListener countDownEndListener;
    private TextView hourColonTv;
    private TextView hourTv;
    private boolean isContinue;
    private ExecutorService mExecutorService;
    private TextView minuteColonTv;
    private TextView minuteTv;
    private Handler myHandler;
    private TextView secondTv;
    private long timeStamp;

    public interface CountDownEndListener {
        void onCountDownEnd();
    }

    public enum CountDownViewGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public static class MyHandler extends Handler {
        private final WeakReference<CountDownView> mCountDownView;

        public MyHandler(CountDownView countDownView) {
            this.mCountDownView = new WeakReference<>(countDownView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            CountDownView countDownView = this.mCountDownView.get();
            if (message.what != 101) {
                return;
            }
            Object obj = message.obj;
            if (obj != null) {
                String[] strArr = (String[]) obj;
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    if (i2 == 0) {
                        countDownView.updateTvText(strArr[0], countDownView.hourTv);
                    } else if (i2 == 1) {
                        countDownView.updateTvText(strArr[1], countDownView.minuteTv);
                    } else if (i2 == 2) {
                        countDownView.updateTvText(strArr[2], countDownView.secondTv);
                    }
                }
            }
            if (countDownView.isContinue || countDownView.countDownEndListener == null) {
                return;
            }
            countDownView.countDownEndListener.onCountDownEnd();
        }
    }

    public CountDownView(Context context) {
        this(context, null);
    }

    private void countDown() {
        Thread thread = new Thread(new Runnable() { // from class: com.catchpig.mvvm.widget.CountDownView.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                while (true) {
                    try {
                        boolean z2 = true;
                        if (!CountDownView.this.isContinue) {
                            CountDownView.this.isContinue = true;
                            return;
                        }
                        CountDownView countDownView = CountDownView.this;
                        long j2 = countDownView.timeStamp;
                        countDownView.timeStamp = j2 - 1;
                        if (j2 <= 1) {
                            z2 = false;
                        }
                        countDownView.isContinue = z2;
                        String[] strArrSecToTimes = DateUtil.secToTimes(CountDownView.this.timeStamp);
                        Message message = new Message();
                        message.obj = strArrSecToTimes;
                        message.what = 101;
                        CountDownView.this.myHandler.sendMessage(message);
                        Thread.sleep(1000L);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
            }
        });
        ExecutorService executorService = this.mExecutorService;
        if (executorService == null || executorService.isShutdown()) {
            this.mExecutorService = Executors.newCachedThreadPool();
        }
        this.mExecutorService.execute(thread);
    }

    private void init() {
        setOrientation(0);
        setGravity(16);
        TextView textView = new TextView(this.context);
        this.hourTv = textView;
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        this.hourTv.setBackgroundColor(Color.parseColor("#FF7198"));
        this.hourTv.setTextSize(12.0f);
        this.hourTv.setGravity(17);
        addView(this.hourTv);
        TextView textView2 = new TextView(this.context);
        this.hourColonTv = textView2;
        textView2.setTextColor(Color.parseColor("#FF7198"));
        this.hourColonTv.setTextSize(12.0f);
        TextView textView3 = this.hourColonTv;
        int i2 = R.string.time_colon;
        textView3.setText(i2);
        this.hourColonTv.setGravity(17);
        addView(this.hourColonTv);
        TextView textView4 = new TextView(this.context);
        this.minuteTv = textView4;
        textView4.setTextColor(Color.parseColor("#FFFFFF"));
        this.minuteTv.setBackgroundColor(Color.parseColor("#FF7198"));
        this.minuteTv.setTextSize(12.0f);
        this.minuteTv.setGravity(17);
        addView(this.minuteTv);
        TextView textView5 = new TextView(this.context);
        this.minuteColonTv = textView5;
        textView5.setTextColor(Color.parseColor("#FF7198"));
        this.minuteColonTv.setTextSize(12.0f);
        this.minuteColonTv.setText(i2);
        this.minuteColonTv.setGravity(17);
        addView(this.minuteColonTv);
        TextView textView6 = new TextView(this.context);
        this.secondTv = textView6;
        textView6.setTextColor(Color.parseColor("#FFFFFF"));
        this.secondTv.setBackgroundColor(Color.parseColor("#FF7198"));
        this.secondTv.setTextSize(12.0f);
        this.secondTv.setGravity(17);
        addView(this.secondTv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTvText(String str, TextView textView) {
        textView.setText(str);
    }

    public void destoryCountDownView() {
        ExecutorService executorService = this.mExecutorService;
        if (executorService != null) {
            executorService.shutdownNow();
        }
        Handler handler = this.myHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.myHandler = null;
        }
    }

    public CountDownView pauseCountDown() {
        this.isContinue = false;
        return this;
    }

    public CountDownView setColonTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.hourColonTv.setBackground(drawable);
            this.minuteColonTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setColonTvBackgroundColorHex(String str) {
        int color = Color.parseColor(str);
        this.hourColonTv.setBackgroundColor(color);
        this.minuteColonTv.setBackgroundColor(color);
        return this;
    }

    public CountDownView setColonTvBackgroundRes(int i2) {
        this.hourColonTv.setBackgroundResource(i2);
        this.minuteColonTv.setBackgroundResource(i2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setColonTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.hourColonTv
            r0.setGravity(r3)
            android.widget.TextView r0 = r2.minuteColonTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setColonTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setColonTvSize(float f2) {
        this.hourColonTv.setTextSize(f2);
        this.minuteColonTv.setTextSize(f2);
        return this;
    }

    public CountDownView setColonTvTextColorHex(String str) {
        int color = Color.parseColor(str);
        this.hourColonTv.setTextColor(color);
        this.minuteColonTv.setTextColor(color);
        return this;
    }

    public CountDownView setColonTvWH(int i2, int i3) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i3);
        this.hourColonTv.setLayoutParams(layoutParams);
        this.minuteColonTv.setLayoutParams(layoutParams);
        return this;
    }

    public void setCountDownEndListener(CountDownEndListener countDownEndListener) {
        this.countDownEndListener = countDownEndListener;
    }

    public CountDownView setCountTime(long j2) {
        this.timeStamp = j2;
        return this;
    }

    public CountDownView setHourColonTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.hourColonTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setHourColonTvBackgroundColorHex(String str) {
        this.hourColonTv.setBackgroundColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setHourColonTvBackgroundRes(int i2) {
        this.hourColonTv.setBackgroundResource(i2);
        return this;
    }

    public CountDownView setHourColonTvBold(boolean z2) {
        this.hourColonTv.getPaint().setFakeBoldText(z2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setHourColonTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.hourColonTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setHourColonTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setHourColonTvMargins(int i2, int i3, int i4, int i5) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.hourColonTv.setLayoutParams(layoutParams);
        return this;
    }

    public CountDownView setHourColonTvPadding(int i2, int i3, int i4, int i5) {
        this.hourColonTv.setPadding(i2, i3, i4, i5);
        return this;
    }

    public CountDownView setHourColonTvSize(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.hourColonTv.getLayoutParams();
        if (layoutParams != null) {
            if (i2 > 0) {
                layoutParams.width = i2;
            }
            if (i3 > 0) {
                layoutParams.height = i3;
            }
            this.hourColonTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView setHourColonTvTextColorHex(String str) {
        this.hourColonTv.setTextColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setHourColonTvTextSize(float f2) {
        this.hourColonTv.setTextSize(f2);
        return this;
    }

    public CountDownView setHourTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.hourTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setHourTvBackgroundColorHex(String str) {
        this.hourTv.setBackgroundColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setHourTvBackgroundRes(int i2) {
        this.hourTv.setBackgroundResource(i2);
        return this;
    }

    public CountDownView setHourTvBold(boolean z2) {
        this.hourTv.getPaint().setFakeBoldText(z2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setHourTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.hourTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setHourTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setHourTvMargins(int i2, int i3, int i4, int i5) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.minuteTv.setLayoutParams(layoutParams);
        return this;
    }

    public CountDownView setHourTvPadding(int i2, int i3, int i4, int i5) {
        this.hourTv.setPadding(i2, i3, i4, i5);
        return this;
    }

    public CountDownView setHourTvSize(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.hourTv.getLayoutParams();
        if (layoutParams != null) {
            if (i2 > 0) {
                layoutParams.width = i2;
            }
            if (i3 > 0) {
                layoutParams.height = i3;
            }
            this.hourTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView setHourTvTextColorHex(String str) {
        this.hourTv.setTextColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setHourTvTextSize(float f2) {
        this.hourTv.setTextSize(f2);
        return this;
    }

    public CountDownView setMinuteColonTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.minuteColonTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setMinuteColonTvBackgroundColorHex(String str) {
        this.minuteColonTv.setBackgroundColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setMinuteColonTvBackgroundRes(int i2) {
        this.minuteColonTv.setBackgroundResource(i2);
        return this;
    }

    public CountDownView setMinuteColonTvBold(boolean z2) {
        this.minuteColonTv.getPaint().setFakeBoldText(z2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setMinuteColonTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.minuteColonTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setMinuteColonTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setMinuteColonTvMargins(int i2, int i3, int i4, int i5) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.minuteColonTv.setLayoutParams(layoutParams);
        return this;
    }

    public CountDownView setMinuteColonTvPadding(int i2, int i3, int i4, int i5) {
        this.minuteColonTv.setPadding(i2, i3, i4, i5);
        return this;
    }

    public CountDownView setMinuteColonTvSize(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.minuteColonTv.getLayoutParams();
        if (layoutParams != null) {
            if (i2 > 0) {
                layoutParams.width = i2;
            }
            if (i3 > 0) {
                layoutParams.height = i3;
            }
            this.minuteColonTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView setMinuteColonTvTextColorHex(String str) {
        this.minuteColonTv.setTextColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setMinuteColonTvTextSize(float f2) {
        this.minuteColonTv.setTextSize(f2);
        return this;
    }

    public CountDownView setMinuteTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.minuteTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setMinuteTvBackgroundColorHex(String str) {
        this.minuteTv.setBackgroundColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setMinuteTvBackgroundRes(int i2) {
        this.minuteTv.setBackgroundResource(i2);
        return this;
    }

    public CountDownView setMinuteTvBold(boolean z2) {
        this.minuteTv.getPaint().setFakeBoldText(z2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setMinuteTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.minuteTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setMinuteTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setMinuteTvMargins(int i2, int i3, int i4, int i5) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.minuteTv.setLayoutParams(layoutParams);
        return this;
    }

    public CountDownView setMinuteTvPadding(int i2, int i3, int i4, int i5) {
        this.minuteTv.setPadding(i2, i3, i4, i5);
        return this;
    }

    public CountDownView setMinuteTvSize(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.minuteTv.getLayoutParams();
        if (layoutParams != null) {
            if (i2 > 0) {
                layoutParams.width = i2;
            }
            if (i3 > 0) {
                layoutParams.height = i3;
            }
            this.minuteTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView setMinuteTvTextColorHex(String str) {
        this.minuteTv.setTextColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setMinuteTvTextSize(float f2) {
        this.minuteTv.setTextSize(f2);
        return this;
    }

    public CountDownView setSecondTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.secondTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setSecondTvBackgroundColorHex(String str) {
        this.secondTv.setBackgroundColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setSecondTvBackgroundRes(int i2) {
        this.secondTv.setBackgroundResource(i2);
        return this;
    }

    public CountDownView setSecondTvBold(boolean z2) {
        this.secondTv.getPaint().setFakeBoldText(z2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setSecondTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.secondTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setSecondTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setSecondTvMargins(int i2, int i3, int i4, int i5) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(i2, i3, i4, i5);
        this.secondTv.setLayoutParams(layoutParams);
        return this;
    }

    public CountDownView setSecondTvPadding(int i2, int i3, int i4, int i5) {
        this.secondTv.setPadding(i2, i3, i4, i5);
        return this;
    }

    public CountDownView setSecondTvSize(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.secondTv.getLayoutParams();
        if (layoutParams != null) {
            if (i2 > 0) {
                layoutParams.width = i2;
            }
            if (i3 > 0) {
                layoutParams.height = i3;
            }
            this.secondTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView setSecondTvTextColorHex(String str) {
        this.secondTv.setTextColor(Color.parseColor(str));
        return this;
    }

    public CountDownView setSecondTvTextSize(float f2) {
        this.secondTv.setTextSize(f2);
        return this;
    }

    public CountDownView setTimeTvBackground(Drawable drawable) {
        if (drawable != null) {
            this.hourTv.setBackground(drawable);
            this.minuteTv.setBackground(drawable);
            this.secondTv.setBackground(drawable);
        }
        return this;
    }

    public CountDownView setTimeTvBackgroundColorHex(String str) {
        int color = Color.parseColor(str);
        this.hourTv.setBackgroundColor(color);
        this.minuteTv.setBackgroundColor(color);
        this.secondTv.setBackgroundColor(color);
        return this;
    }

    public CountDownView setTimeTvBackgroundRes(int i2) {
        this.hourTv.setBackgroundResource(i2);
        this.minuteTv.setBackgroundResource(i2);
        this.secondTv.setBackgroundResource(i2);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.catchpig.mvvm.widget.CountDownView setTimeTvGravity(com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity r3) {
        /*
            r2 = this;
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_BOTTOM
            if (r3 != r0) goto L7
            r3 = 80
            goto L25
        L7:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_CENTER
            r1 = 17
            if (r3 != r0) goto Lf
        Ld:
            r3 = r1
            goto L25
        Lf:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_LEFT
            if (r3 != r0) goto L17
            r3 = 8388611(0x800003, float:1.1754948E-38)
            goto L25
        L17:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_RIGHT
            if (r3 != r0) goto L1f
            r3 = 8388613(0x800005, float:1.175495E-38)
            goto L25
        L1f:
            com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity r0 = com.catchpig.mvvm.widget.CountDownView.CountDownViewGravity.GRAVITY_TOP
            if (r3 != r0) goto Ld
            r3 = 48
        L25:
            android.widget.TextView r0 = r2.hourTv
            r0.setGravity(r3)
            android.widget.TextView r0 = r2.minuteTv
            r0.setGravity(r3)
            android.widget.TextView r0 = r2.secondTv
            r0.setGravity(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.CountDownView.setTimeTvGravity(com.catchpig.mvvm.widget.CountDownView$CountDownViewGravity):com.catchpig.mvvm.widget.CountDownView");
    }

    public CountDownView setTimeTvSize(float f2) {
        this.hourTv.setTextSize(f2);
        this.minuteTv.setTextSize(f2);
        this.secondTv.setTextSize(f2);
        return this;
    }

    public CountDownView setTimeTvTextColorHex(String str) {
        int color = Color.parseColor(str);
        this.hourTv.setTextColor(color);
        this.minuteTv.setTextColor(color);
        this.secondTv.setTextColor(color);
        return this;
    }

    public CountDownView setTimeTvWH(int i2, int i3) {
        if (i2 > 0 && i3 > 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i3);
            this.hourTv.setLayoutParams(layoutParams);
            this.minuteTv.setLayoutParams(layoutParams);
            this.secondTv.setLayoutParams(layoutParams);
        }
        return this;
    }

    public CountDownView startCountDown() {
        if (this.timeStamp <= 1) {
            this.isContinue = false;
        } else {
            this.isContinue = true;
            countDown();
        }
        return this;
    }

    public CountDownView stopCountDown() {
        this.timeStamp = 0L;
        return this;
    }

    public CountDownView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isContinue = false;
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.myHandler = new MyHandler(this);
        this.context = context;
        init();
    }
}
