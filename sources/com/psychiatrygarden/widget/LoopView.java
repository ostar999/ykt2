package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes6.dex */
public class LoopView extends View {
    private int F;
    ArrayList arrayList;
    int colorBlack;
    int colorGray;
    int colorGrayLight;
    Context context;

    /* renamed from: g, reason: collision with root package name */
    int f16266g;
    private GestureDetector gestureDetector;

    /* renamed from: h, reason: collision with root package name */
    int f16267h;
    Handler handler;
    boolean isLoop;

    /* renamed from: l, reason: collision with root package name */
    float f16268l;
    LoopListener loopListener;
    Timer mTimer;

    /* renamed from: n, reason: collision with root package name */
    int f16269n;

    /* renamed from: o, reason: collision with root package name */
    int f16270o;

    /* renamed from: p, reason: collision with root package name */
    int f16271p;
    Paint paintA;
    Paint paintB;
    Paint paintC;
    int positon;

    /* renamed from: r, reason: collision with root package name */
    int f16272r;
    Rect rect;

    /* renamed from: s, reason: collision with root package name */
    int f16273s;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    /* renamed from: t, reason: collision with root package name */
    int f16274t;
    int tempP;
    int tempV;
    int textSize;
    int totalScrollY;

    /* renamed from: u, reason: collision with root package name */
    int f16275u;

    /* renamed from: v, reason: collision with root package name */
    int f16276v;

    /* renamed from: w, reason: collision with root package name */
    int f16277w;

    /* renamed from: x, reason: collision with root package name */
    float f16278x;

    /* renamed from: y, reason: collision with root package name */
    float f16279y;

    /* renamed from: z, reason: collision with root package name */
    float f16280z;

    public interface LoopListener {
        void onItemSelect(LoopView loopView, int item);
    }

    public final class LoopTimerTask extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        float f16281a = 2.147484E9f;

        /* renamed from: b, reason: collision with root package name */
        final float f16282b;
        final LoopView loopView;
        final Timer timer;

        public LoopTimerTask(LoopView loopview, float f2, Timer timer) {
            this.loopView = loopview;
            this.f16282b = f2;
            this.timer = timer;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            if (this.f16281a == 2.147484E9f) {
                if (Math.abs(this.f16282b) <= 2000.0f) {
                    this.f16281a = this.f16282b;
                } else if (this.f16282b > 0.0f) {
                    this.f16281a = 2000.0f;
                } else {
                    this.f16281a = -2000.0f;
                }
            }
            if (Math.abs(this.f16281a) >= 0.0f && Math.abs(this.f16281a) <= 20.0f) {
                this.timer.cancel();
                this.loopView.handler.sendEmptyMessage(2000);
                return;
            }
            int i2 = (int) ((this.f16281a * 10.0f) / 1000.0f);
            LoopView loopView = this.loopView;
            int i3 = loopView.totalScrollY - i2;
            loopView.totalScrollY = i3;
            if (!loopView.isLoop) {
                int i4 = loopView.positon;
                float f2 = loopView.f16268l;
                int i5 = loopView.f16267h;
                if (i3 <= ((int) ((-i4) * i5 * f2))) {
                    this.f16281a = 40.0f;
                    loopView.totalScrollY = (int) ((-i4) * f2 * i5);
                } else {
                    int size = loopView.arrayList.size() - 1;
                    LoopView loopView2 = this.loopView;
                    if (i3 >= ((int) ((size - loopView2.positon) * loopView2.f16268l * loopView2.f16267h))) {
                        int size2 = loopView2.arrayList.size() - 1;
                        loopView2.totalScrollY = (int) ((size2 - r4.positon) * this.loopView.f16268l * r4.f16267h);
                        this.f16281a = -40.0f;
                    }
                }
            }
            float f3 = this.f16281a;
            if (f3 < 0.0f) {
                this.f16281a = f3 + 20.0f;
            } else {
                this.f16281a = f3 - 20.0f;
            }
            this.loopView.handler.sendEmptyMessage(1000);
        }
    }

    public final class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        final LoopView loopView;

        public LoopViewGestureListener(LoopView loopview) {
            this.loopView = loopview;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionevent) {
            Timer timer = this.loopView.mTimer;
            if (timer == null) {
                return true;
            }
            timer.cancel();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f2, float f12) {
            this.loopView.b(f12);
            return true;
        }
    }

    public final class MTimer extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        int f16283a = Integer.MAX_VALUE;

        /* renamed from: b, reason: collision with root package name */
        int f16284b = 0;

        /* renamed from: c, reason: collision with root package name */
        final int f16285c;
        final LoopView loopView;
        final Timer timer;

        public MTimer(LoopView loopview, int i2, Timer timer) {
            this.loopView = loopview;
            this.f16285c = i2;
            this.timer = timer;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            if (this.f16283a == Integer.MAX_VALUE) {
                int i2 = this.f16285c;
                if (i2 < 0) {
                    float f2 = -i2;
                    LoopView loopView = this.loopView;
                    float f3 = loopView.f16268l;
                    int i3 = loopView.f16267h;
                    if (f2 > (i3 * f3) / 2.0f) {
                        this.f16283a = (int) (((-f3) * i3) - i2);
                    } else {
                        this.f16283a = -i2;
                    }
                } else {
                    float f4 = i2;
                    LoopView loopView2 = this.loopView;
                    float f5 = loopView2.f16268l;
                    int i4 = loopView2.f16267h;
                    if (f4 > (i4 * f5) / 2.0f) {
                        this.f16283a = (int) ((f5 * i4) - i2);
                    } else {
                        this.f16283a = -i2;
                    }
                }
            }
            int i5 = this.f16283a;
            int i6 = (int) (i5 * 0.1f);
            this.f16284b = i6;
            if (i6 == 0) {
                if (i5 < 0) {
                    this.f16284b = -1;
                } else {
                    this.f16284b = 1;
                }
            }
            if (Math.abs(i5) <= 0) {
                this.timer.cancel();
                this.loopView.handler.sendEmptyMessage(3000);
            } else {
                LoopView loopView3 = this.loopView;
                loopView3.totalScrollY += this.f16284b;
                loopView3.handler.sendEmptyMessage(1000);
                this.f16283a -= this.f16284b;
            }
        }
    }

    public final class MessageHandler extends Handler {

        /* renamed from: a, reason: collision with root package name */
        final LoopView f16286a;

        public MessageHandler(LoopView loopview) {
            this.f16286a = loopview;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message paramMessage) {
            if (paramMessage.what == 1000) {
                this.f16286a.invalidate();
            }
            int i2 = paramMessage.what;
            if (i2 == 2000) {
                LoopView.b(this.f16286a);
            } else if (i2 == 3000) {
                this.f16286a.c();
            }
            super.handleMessage(paramMessage);
        }
    }

    public final class MyTimerTask extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        float f16287a = 2.147484E9f;

        /* renamed from: b, reason: collision with root package name */
        float f16288b = 0.0f;

        /* renamed from: c, reason: collision with root package name */
        final int f16289c;
        final LoopView loopView;
        final Timer timer;

        public MyTimerTask(LoopView loopview, int i2, Timer timer) {
            this.loopView = loopview;
            this.f16289c = i2;
            this.timer = timer;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            if (this.f16287a == 2.147484E9f) {
                float fA = this.f16289c - LoopView.a(this.loopView);
                LoopView loopView = this.loopView;
                this.f16287a = fA * loopView.f16268l * loopView.f16267h;
                if (this.f16289c > LoopView.a(loopView)) {
                    this.f16288b = -1000.0f;
                } else {
                    this.f16288b = 1000.0f;
                }
            }
            if (Math.abs(this.f16287a) < 1.0f) {
                this.timer.cancel();
                this.loopView.handler.sendEmptyMessage(2000);
                return;
            }
            int i2 = (int) ((this.f16288b * 10.0f) / 1000.0f);
            if (Math.abs(this.f16287a) < Math.abs(i2)) {
                i2 = (int) (-this.f16287a);
            }
            LoopView loopView2 = this.loopView;
            loopView2.totalScrollY -= i2;
            this.f16287a = i2 + this.f16287a;
            loopView2.handler.sendEmptyMessage(1000);
        }
    }

    public LoopView(Context context) {
        super(context);
        this.tempP = -1;
        initLoopView(context);
    }

    public static int a(LoopView loopview) {
        return loopview.F;
    }

    public static void b(LoopView loopview) {
        loopview.f();
    }

    @SuppressLint({"NewApi"})
    private void d() {
        if (this.arrayList == null) {
            return;
        }
        Paint paint = new Paint();
        this.paintA = paint;
        paint.setColor(this.colorGray);
        this.paintA.setAntiAlias(true);
        this.paintA.setTypeface(Typeface.MONOSPACE);
        this.paintA.setTextSize(this.textSize);
        Paint paint2 = new Paint();
        this.paintB = paint2;
        paint2.setColor(this.colorBlack);
        this.paintB.setAntiAlias(true);
        this.paintB.setTextScaleX(1.05f);
        this.paintB.setTypeface(Typeface.MONOSPACE);
        this.paintB.setTextSize(this.textSize);
        Paint paint3 = new Paint();
        this.paintC = paint3;
        paint3.setColor(this.colorGrayLight);
        this.paintC.setAntiAlias(true);
        this.paintC.setTypeface(Typeface.MONOSPACE);
        this.paintC.setTextSize(this.textSize);
        setLayerType(1, null);
        GestureDetector gestureDetector = new GestureDetector(this.context, this.simpleOnGestureListener);
        this.gestureDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        e();
        int i2 = this.f16267h;
        float f2 = this.f16268l;
        int i3 = (int) (i2 * f2 * (this.f16272r - 1));
        this.f16274t = i3;
        int i4 = (int) ((i3 * 2) / 3.141592653589793d);
        this.f16273s = i4;
        this.f16275u = (int) (i3 / 3.141592653589793d);
        this.f16276v = this.f16266g + this.textSize;
        this.f16269n = (int) ((i4 - (i2 * f2)) / 2.0f);
        this.f16270o = (int) ((i4 + (f2 * i2)) / 2.0f);
        if (this.positon == -1) {
            if (this.isLoop) {
                this.positon = (this.arrayList.size() + 1) / 2;
            } else {
                this.positon = 0;
            }
        }
        this.f16271p = this.positon;
    }

    private void e() {
        Rect rect = new Rect();
        for (int i2 = 0; i2 < this.arrayList.size(); i2++) {
            String str = (String) this.arrayList.get(i2);
            this.paintB.getTextBounds(str, 0, str.length(), rect);
            int iWidth = rect.width();
            if (iWidth > this.f16266g) {
                this.f16266g = iWidth;
            }
            this.paintB.getTextBounds("星期", 0, 2, rect);
            int iHeight = rect.height();
            if (iHeight > this.f16267h) {
                this.f16267h = iHeight;
            }
        }
    }

    private void f() {
        int i2 = (int) (this.totalScrollY % (this.f16268l * this.f16267h));
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MTimer(this, i2, timer), 0L, 10L);
    }

    private void initLoopView(Context context) {
        this.textSize = 0;
        this.f16268l = 2.0f;
        this.isLoop = false;
        this.positon = -1;
        this.f16272r = 9;
        this.f16278x = 0.0f;
        this.f16279y = 0.0f;
        this.f16280z = 0.0f;
        this.totalScrollY = 0;
        this.simpleOnGestureListener = new LoopViewGestureListener(this);
        this.handler = new MessageHandler(this);
        this.context = context;
        setTextSize(16.0f);
        this.rect = new Rect();
    }

    private int measureHeight(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == Integer.MIN_VALUE) {
            d();
        } else if (mode == 1073741824) {
            this.f16273s = size;
        }
        return this.f16273s;
    }

    private int measureWidth(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == Integer.MIN_VALUE) {
            d();
            this.tempV = this.f16276v;
        } else if (mode == 1073741824) {
            this.tempV = size;
        }
        return this.tempV;
    }

    public final void c() {
    }

    public int getCurrentPosition() {
        return this.f16271p;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0226  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r16) {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.LoopView.onDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    public void onMeasure(int i12, int j12) {
        setMeasuredDimension(measureWidth(i12), measureHeight(j12));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionevent) {
        int action = motionevent.getAction();
        if (action == 0) {
            this.f16278x = motionevent.getRawY();
        } else {
            if (action != 2) {
                if (!this.gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
                    f();
                }
                return true;
            }
            float rawY = motionevent.getRawY();
            this.f16279y = rawY;
            float f2 = this.f16278x - rawY;
            this.f16280z = f2;
            this.f16278x = rawY;
            int i2 = (int) (this.totalScrollY + f2);
            this.totalScrollY = i2;
            if (!this.isLoop) {
                int i3 = this.positon;
                float f3 = this.f16268l;
                int i4 = this.f16267h;
                if (i2 <= ((int) ((-i3) * i4 * f3))) {
                    this.totalScrollY = (int) ((-i3) * f3 * i4);
                }
            }
        }
        if (this.totalScrollY < ((int) (((this.arrayList.size() - 1) - this.positon) * this.f16268l * this.f16267h))) {
            invalidate();
        } else {
            this.totalScrollY = (int) (((this.arrayList.size() - 1) - this.positon) * this.f16268l * this.f16267h);
            invalidate();
        }
        if (!this.gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
            f();
        }
        return true;
    }

    public final void setArrayList(ArrayList arraylist) {
        this.arrayList = arraylist;
        d();
        invalidate();
    }

    public final void setListener(LoopListener LoopListener2) {
        this.loopListener = LoopListener2;
    }

    public final void setNotLoop() {
        this.isLoop = false;
    }

    public final void setPosition(int position) {
        this.positon = position;
    }

    public final void setTextSize(float size) {
        if (size > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * size);
        }
    }

    public void setTotalScrollY(int totalScrollY) {
        this.totalScrollY = totalScrollY;
    }

    public final int b() {
        return this.F;
    }

    public final void b(float f12) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new LoopTimerTask(this, f12, timer), 0L, 20L);
    }

    public LoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tempP = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.loopViewColor);
        this.colorGray = typedArrayObtainStyledAttributes.getColor(1, -16777216);
        this.colorGrayLight = typedArrayObtainStyledAttributes.getColor(2, -16777216);
        this.colorBlack = typedArrayObtainStyledAttributes.getColor(0, -16777216);
        typedArrayObtainStyledAttributes.recycle();
        initLoopView(context);
    }

    public final void b(int i12) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MyTimerTask(this, i12, timer), 0L, 20L);
    }

    public LoopView(Context context, AttributeSet attrs, int i12) {
        super(context, attrs, i12);
        this.tempP = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.loopViewColor, i12, 0);
        this.colorGray = typedArrayObtainStyledAttributes.getColor(1, -16777216);
        this.colorGrayLight = typedArrayObtainStyledAttributes.getColor(2, -16777216);
        this.colorBlack = typedArrayObtainStyledAttributes.getColor(0, -16777216);
        typedArrayObtainStyledAttributes.recycle();
        initLoopView(context);
    }
}
