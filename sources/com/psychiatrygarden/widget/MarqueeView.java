package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import androidx.core.internal.view.SupportMenu;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MarqueeView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int ROLL_OVER = 100;
    private int ShadowColor;
    public int currentX;
    private SurfaceHolder holder;
    public Context mContext;
    private int mDirection;
    Handler mHandler;
    private boolean mIsRepeat;
    OnMargueeListener mOnMargueeListener;
    private int mSpeed;
    private int mStartPoint;
    private int mTextColor;
    private TextPaint mTextPaint;
    private float mTextSize;
    private MarqueeViewThread mThread;
    private String margueeString;
    public int sepX;
    private int textHeight;
    private int textWidth;

    public class MarqueeViewThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun = true;

        public MarqueeViewThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        public void onDraw() {
            try {
                synchronized (this.holder) {
                    if (TextUtils.isEmpty(MarqueeView.this.margueeString)) {
                        Thread.sleep(1000L);
                        return;
                    }
                    Canvas canvasLockCanvas = this.holder.lockCanvas();
                    int paddingLeft = MarqueeView.this.getPaddingLeft();
                    int paddingTop = MarqueeView.this.getPaddingTop();
                    int paddingRight = MarqueeView.this.getPaddingRight();
                    int paddingBottom = MarqueeView.this.getPaddingBottom();
                    int width = (MarqueeView.this.getWidth() - paddingLeft) - paddingRight;
                    int height = paddingTop + (((MarqueeView.this.getHeight() - paddingTop) - paddingBottom) / 2);
                    if (MarqueeView.this.mDirection == 0) {
                        MarqueeView marqueeView = MarqueeView.this;
                        if (marqueeView.currentX <= (-marqueeView.textWidth)) {
                            if (!MarqueeView.this.mIsRepeat) {
                                MarqueeView.this.mHandler.sendEmptyMessage(100);
                            }
                            MarqueeView.this.currentX = width;
                        } else {
                            MarqueeView marqueeView2 = MarqueeView.this;
                            marqueeView2.currentX -= marqueeView2.sepX;
                        }
                    } else {
                        MarqueeView marqueeView3 = MarqueeView.this;
                        int i2 = marqueeView3.currentX;
                        if (i2 >= width) {
                            if (!marqueeView3.mIsRepeat) {
                                MarqueeView.this.mHandler.sendEmptyMessage(100);
                            }
                            MarqueeView marqueeView4 = MarqueeView.this;
                            marqueeView4.currentX = -marqueeView4.textWidth;
                        } else {
                            marqueeView3.currentX = i2 + marqueeView3.sepX;
                        }
                    }
                    if (canvasLockCanvas != null) {
                        canvasLockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        String str = MarqueeView.this.margueeString;
                        MarqueeView marqueeView5 = MarqueeView.this;
                        canvasLockCanvas.drawText(str, marqueeView5.currentX, height + (MarqueeView.dip2px(marqueeView5.getContext(), MarqueeView.this.textHeight) / 2), MarqueeView.this.mTextPaint);
                        this.holder.unlockCanvasAndPost(canvasLockCanvas);
                    }
                    int length = MarqueeView.this.textWidth / MarqueeView.this.margueeString.trim().length();
                    MarqueeView marqueeView6 = MarqueeView.this;
                    try {
                        Thread.sleep(marqueeView6.mSpeed / (length / marqueeView6.sepX) == 0 ? 1 : MarqueeView.this.mSpeed / r1);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.isRun) {
                onDraw();
            }
        }
    }

    public interface OnMargueeListener {
        void onRollOver();
    }

    public MarqueeView(Context context) {
        this(context, null);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeView, defStyleAttr, 0);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(5, SupportMenu.CATEGORY_MASK);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(4, 48.0f);
        this.mIsRepeat = typedArrayObtainStyledAttributes.getBoolean(1, false);
        this.mStartPoint = typedArrayObtainStyledAttributes.getInt(3, 0);
        this.mDirection = typedArrayObtainStyledAttributes.getInt(0, 0);
        this.mSpeed = typedArrayObtainStyledAttributes.getInt(2, 20);
        typedArrayObtainStyledAttributes.recycle();
        SurfaceHolder holder = getHolder();
        this.holder = holder;
        holder.addCallback(this);
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setFlags(1);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        setZOrderOnTop(true);
        getHolder().setFormat(-3);
    }

    public void measurementsText(String msg) {
        this.margueeString = msg;
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setStrokeWidth(0.5f);
        this.mTextPaint.setFakeBoldText(true);
        this.textWidth = (int) this.mTextPaint.measureText(this.margueeString);
        this.textHeight = (int) this.mTextPaint.getFontMetrics().bottom;
        int width = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
        if (this.mStartPoint == 0) {
            this.currentX = 0;
        } else {
            this.currentX = (width - getPaddingLeft()) - getPaddingRight();
        }
    }

    public void reset() {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        if (this.mStartPoint == 0) {
            this.currentX = 0;
        } else {
            this.currentX = width;
        }
    }

    public void setOnMargueeListener(OnMargueeListener mOnMargueeListener) {
        this.mOnMargueeListener = mOnMargueeListener;
    }

    public void setText(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        measurementsText(msg);
    }

    public void startScroll() {
        MarqueeViewThread marqueeViewThread = this.mThread;
        if (marqueeViewThread == null || !marqueeViewThread.isRun) {
            MarqueeViewThread marqueeViewThread2 = new MarqueeViewThread(this.holder);
            this.mThread = marqueeViewThread2;
            marqueeViewThread2.start();
        }
    }

    public void stopScroll() {
        MarqueeViewThread marqueeViewThread = this.mThread;
        if (marqueeViewThread != null) {
            marqueeViewThread.isRun = false;
            marqueeViewThread.interrupt();
        }
        this.mThread = null;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        MarqueeViewThread marqueeViewThread = this.mThread;
        if (marqueeViewThread != null) {
            marqueeViewThread.isRun = true;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        MarqueeViewThread marqueeViewThread = this.mThread;
        if (marqueeViewThread != null) {
            marqueeViewThread.isRun = false;
        }
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextSize = 100.0f;
        this.mTextColor = SupportMenu.CATEGORY_MASK;
        this.textWidth = 0;
        this.textHeight = 0;
        this.ShadowColor = -16777216;
        this.currentX = 0;
        this.sepX = 5;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.MarqueeView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what != 100) {
                    return;
                }
                MarqueeView.this.stopScroll();
                OnMargueeListener onMargueeListener = MarqueeView.this.mOnMargueeListener;
                if (onMargueeListener != null) {
                    onMargueeListener.onRollOver();
                }
            }
        };
        this.mContext = context;
        init(attrs, defStyleAttr);
    }
}
