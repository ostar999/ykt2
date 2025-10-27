package com.beizi.fusion.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.Key;
import com.beizi.fusion.R;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ap;
import com.beizi.fusion.g.as;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class TwistView extends RelativeLayout {
    public static final long DELAY_TIME_TWIST = 100;
    private int A;
    private int B;
    private a C;
    private int D;
    private boolean E;
    private int F;
    private int G;
    private int H;
    private Handler I;

    /* renamed from: a, reason: collision with root package name */
    private View f5444a;

    /* renamed from: b, reason: collision with root package name */
    private View f5445b;

    /* renamed from: c, reason: collision with root package name */
    private View f5446c;

    /* renamed from: d, reason: collision with root package name */
    private View f5447d;

    /* renamed from: e, reason: collision with root package name */
    private BackArrowView f5448e;

    /* renamed from: f, reason: collision with root package name */
    private BackArrowView f5449f;

    /* renamed from: g, reason: collision with root package name */
    private BackArrowView f5450g;

    /* renamed from: h, reason: collision with root package name */
    private ShakeView f5451h;

    /* renamed from: i, reason: collision with root package name */
    private TextView f5452i;

    /* renamed from: j, reason: collision with root package name */
    private TextView f5453j;

    /* renamed from: k, reason: collision with root package name */
    private int f5454k;

    /* renamed from: l, reason: collision with root package name */
    private ObjectAnimator f5455l;

    /* renamed from: m, reason: collision with root package name */
    private final int f5456m;

    /* renamed from: n, reason: collision with root package name */
    private final int f5457n;

    /* renamed from: o, reason: collision with root package name */
    private final long f5458o;

    /* renamed from: p, reason: collision with root package name */
    private final long f5459p;

    /* renamed from: q, reason: collision with root package name */
    private final long f5460q;

    /* renamed from: r, reason: collision with root package name */
    private String f5461r;

    /* renamed from: s, reason: collision with root package name */
    private String f5462s;

    /* renamed from: t, reason: collision with root package name */
    private String f5463t;

    /* renamed from: u, reason: collision with root package name */
    private long f5464u;

    /* renamed from: v, reason: collision with root package name */
    private Timer f5465v;

    /* renamed from: w, reason: collision with root package name */
    private TimerTask f5466w;

    /* renamed from: x, reason: collision with root package name */
    private Timer f5467x;

    /* renamed from: y, reason: collision with root package name */
    private TimerTask f5468y;

    /* renamed from: z, reason: collision with root package name */
    private int f5469z;

    public interface a {
        void a();
    }

    public TwistView(Context context) {
        super(context);
        this.f5456m = 1000;
        this.f5457n = 2000;
        this.f5458o = 500L;
        this.f5459p = 0L;
        this.f5460q = 0L;
        this.f5461r = "#FFFFFFFF";
        this.f5462s = "#99FFFFFF";
        this.f5463t = "#33FFFFFF";
        this.f5464u = 1000L;
        this.f5469z = 0;
        this.A = 0;
        this.B = 0;
        this.D = 0;
        this.E = true;
        this.F = 3;
        this.G = 1;
        this.H = 95;
        this.I = new Handler(Looper.getMainLooper()) { // from class: com.beizi.fusion.widget.TwistView.1
            @Override // android.os.Handler
            @RequiresApi(api = 21)
            @SuppressLint({"LongLogTag"})
            public void handleMessage(@NonNull Message message) {
                super.handleMessage(message);
                try {
                    int i2 = message.what;
                    if (i2 != 1000) {
                        if (i2 == 2000) {
                            TwistView twistView = TwistView.this;
                            twistView.updateStatus(twistView.D);
                            return;
                        }
                        return;
                    }
                    if (TwistView.this.f5454k == 0) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                    } else if (TwistView.this.f5454k == 1) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                    } else if (TwistView.this.f5454k == 2) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                    }
                    if (TwistView.this.f5454k == 2) {
                        TwistView.this.f5454k = 0;
                    } else {
                        TwistView.h(TwistView.this);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        };
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCountAnimation() {
        return (int) (this.f5464u / 100);
    }

    public static /* synthetic */ int h(TwistView twistView) {
        int i2 = twistView.f5454k;
        twistView.f5454k = i2 + 1;
        return i2;
    }

    public void cancelArrowTimerTask() {
        Timer timer = this.f5467x;
        if (timer != null) {
            timer.cancel();
            this.f5467x = null;
        }
        TimerTask timerTask = this.f5468y;
        if (timerTask != null) {
            timerTask.cancel();
            this.f5468y = null;
        }
    }

    public void cancelTwistTimerTask() {
        Timer timer = this.f5465v;
        if (timer != null) {
            timer.cancel();
            this.f5465v = null;
        }
        TimerTask timerTask = this.f5466w;
        if (timerTask != null) {
            timerTask.cancel();
            this.f5466w = null;
        }
    }

    public void destroyView() {
        ShakeView shakeView = this.f5451h;
        if (shakeView != null) {
            shakeView.stopShake();
        }
        cancelTwistTimerTask();
        cancelArrowTimerTask();
        removeHandlerMsg();
    }

    @RequiresApi(api = 21)
    public void hideTargetView(View view, long j2, int i2) {
        int right = view.getRight();
        int top2 = (view.getTop() + view.getBottom()) / 2;
        float fMax = Math.max(view.getWidth(), view.getHeight());
        float countAnimation = fMax / getCountAnimation();
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(view, right, top2, fMax - (i2 * countAnimation), fMax - (countAnimation * (i2 + 1)));
        animatorCreateCircularReveal.setDuration(j2);
        view.clearAnimation();
        animatorCreateCircularReveal.addListener(new AnimatorListenerAdapter() { // from class: com.beizi.fusion.widget.TwistView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
            }
        });
        animatorCreateCircularReveal.start();
    }

    public void removeHandlerMsg() {
        Handler handler = this.I;
        if (handler != null) {
            handler.removeCallbacks(null);
            this.I = null;
        }
    }

    public void setDescribeText(String str) {
        TextView textView = this.f5453j;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setDurationAnimation(long j2) {
        this.f5464u = j2;
    }

    public void setJumpClickListener(View.OnClickListener onClickListener) {
        View view = this.f5447d;
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setJumpOnTouchListener(View.OnTouchListener onTouchListener) {
        View view = this.f5447d;
        if (view != null) {
            view.setOnTouchListener(onTouchListener);
        }
    }

    public void setMainTitleText(String str) {
        TextView textView = this.f5452i;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setRotationEndCallback(a aVar) {
        this.C = aVar;
    }

    public void setTwistTotalLayoutBg(String str) {
        View view = this.f5447d;
        if (view != null) {
            try {
                ap.a(view, str, 0, "", 100);
            } catch (Exception e2) {
                ac.b("TwistView", " e : " + e2);
            }
        }
    }

    public void setTwistTotalLayoutWidthAndHeight(int i2, int i3) {
        View view = this.f5447d;
        if (view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = i3 - as.a(getContext(), this.H);
            this.f5447d.setPadding(as.a(getContext(), 0.0f), as.a(getContext(), this.F), as.a(getContext(), 0.0f), as.a(getContext(), this.F));
            this.f5447d.setLayoutParams(layoutParams);
        }
    }

    @RequiresApi(api = 21)
    public void showTargetView(View view, long j2, final int i2) {
        float fMax = Math.max(view.getWidth(), view.getHeight()) / getCountAnimation();
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(view, view.getRight(), (view.getTop() + view.getBottom()) / 2, (i2 * fMax) + 0.0f, fMax * (i2 + 1));
        animatorCreateCircularReveal.setDuration(j2);
        view.clearAnimation();
        animatorCreateCircularReveal.addListener(new AnimatorListenerAdapter() { // from class: com.beizi.fusion.widget.TwistView.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (TwistView.this.e()) {
                    return;
                }
                TwistView.this.getCountAnimation();
            }
        });
        animatorCreateCircularReveal.start();
    }

    public void startTwistTimerTask() {
        if (this.f5465v == null) {
            this.f5465v = new Timer();
        }
        if (this.f5466w == null) {
            this.f5466w = new TimerTask() { // from class: com.beizi.fusion.widget.TwistView.2
                @Override // java.util.TimerTask, java.lang.Runnable
                @RequiresApi(api = 21)
                public void run() {
                    if (TwistView.this.I != null) {
                        Message messageObtainMessage = TwistView.this.I.obtainMessage();
                        messageObtainMessage.what = 2000;
                        TwistView.this.I.sendMessage(messageObtainMessage);
                    }
                }
            };
        }
        this.f5465v.scheduleAtFixedRate(this.f5466w, 0L, 100L);
    }

    public void updateRollStatus(int i2) {
        this.D = i2;
    }

    @RequiresApi(api = 21)
    public void updateStatus(int i2) {
        if (i2 == 0) {
            return;
        }
        try {
            int countAnimation = getCountAnimation();
            if (this.B != i2) {
                if (this.E) {
                    this.A = 0;
                } else {
                    this.A = countAnimation - this.A;
                }
                this.B = i2;
            }
            if (this.A < 0) {
                this.A = 0;
            }
            if (this.A >= countAnimation) {
                if (this.E) {
                    this.A = 0;
                } else {
                    this.A = countAnimation;
                }
            }
            int i3 = this.A;
            if (i3 < 0 || i3 > countAnimation) {
                return;
            }
            a(this.f5445b, this.f5444a, 100L, i2, i3);
            this.A++;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void b() {
        this.f5444a = findViewById(R.id.beizi_twist_go_imageview);
        this.f5447d = findViewById(R.id.beizi_twist_shake_total_layout);
        this.f5445b = findViewById(R.id.beizi_twist_total_layout);
        this.f5446c = findViewById(R.id.beizi_twist_right_total_layout);
        this.f5452i = (TextView) findViewById(R.id.beizi_twist_title_text);
        this.f5453j = (TextView) findViewById(R.id.beizi_twist_describe_text);
        this.f5448e = (BackArrowView) findViewById(R.id.beizi_twist_right_first_image);
        this.f5449f = (BackArrowView) findViewById(R.id.beizi_twist_right_second_image);
        this.f5450g = (BackArrowView) findViewById(R.id.beizi_twist_right_third_image);
        ShakeView shakeView = (ShakeView) findViewById(R.id.beizi_twist_top_view);
        this.f5451h = shakeView;
        shakeView.updateTwistRollAnim();
        setTwistTotalLayoutBg("#d9333333");
    }

    private void c() {
        ShakeView shakeView = this.f5451h;
        if (shakeView != null) {
            shakeView.startShake();
        }
    }

    private void d() {
        if (this.f5467x == null) {
            this.f5467x = new Timer();
        }
        if (this.f5468y == null) {
            this.f5468y = new TimerTask() { // from class: com.beizi.fusion.widget.TwistView.6
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    if (TwistView.this.I != null) {
                        TwistView.this.I.sendEmptyMessage(1000);
                    }
                }
            };
        }
        this.f5467x.scheduleAtFixedRate(this.f5468y, 0L, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        return this.B == 1;
    }

    private void a() {
        View.inflate(getContext(), R.layout.beizi_twist_view, this);
        b();
        c();
        d();
        startTwistTimerTask();
    }

    @RequiresApi(api = 21)
    private void a(View view, View view2, long j2, int i2, int i3) {
        if (view != null) {
            try {
                if (view.getVisibility() == 0 && view.getParent() != null && view.hasWindowFocus()) {
                    if (i2 == 1) {
                        hideTargetView(view, j2, i3);
                        a(view2, view.getRight(), j2, true, i3);
                    } else if (i2 == 2 && !this.E) {
                        showTargetView(view, j2, i3);
                        a(view2, view.getRight(), j2, false, i3);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void b(View view, float f2, long j2, boolean z2, final int i2) {
        if (z2) {
            float width = f2 - view.getWidth();
            float countAnimation = width / getCountAnimation();
            float f3 = i2 * countAnimation;
            float f4 = f3 + 0.0f;
            float f5 = countAnimation + f3;
            if (f4 > 0.0f || f5 > 0.0f) {
                this.E = false;
            }
            if ((f4 >= width || f5 >= width) && i2 >= getCountAnimation()) {
                return;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", f4, f5);
            this.f5455l = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(j2);
            this.f5455l.start();
            this.f5455l.addListener(new Animator.AnimatorListener() { // from class: com.beizi.fusion.widget.TwistView.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (i2 + 1 < TwistView.this.getCountAnimation() || TwistView.this.C == null) {
                        return;
                    }
                    TwistView.this.C.a();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }
            });
            return;
        }
        float width2 = f2 - view.getWidth();
        float countAnimation2 = width2 / getCountAnimation();
        float f6 = width2 - (i2 * countAnimation2);
        float f7 = width2 - ((i2 + 1) * countAnimation2);
        if (f6 >= 0.0f && f7 >= 0.0f) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationX", f6, f7);
            this.f5455l = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(j2);
            this.f5455l.start();
            return;
        }
        this.E = true;
    }

    private void a(View view, float f2, long j2, boolean z2, int i2) {
        b(view, f2, j2, z2, i2);
        a(view, j2, z2, i2);
    }

    private void a(View view, long j2, boolean z2, int i2) {
        if (z2) {
            float countAnimation = 360.0f / getCountAnimation();
            float f2 = (i2 * countAnimation) + 0.0f;
            float f3 = countAnimation * (i2 + 1);
            if (f2 > 360.0f || f3 > 360.0f) {
                return;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, Key.ROTATION, f2, f3);
            objectAnimatorOfFloat.setDuration(j2);
            objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.beizi.fusion.widget.TwistView.4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((Float) valueAnimator.getAnimatedValue()).floatValue();
                }
            });
            objectAnimatorOfFloat.start();
            return;
        }
        float countAnimation2 = (-360.0f) / getCountAnimation();
        float f4 = (i2 * countAnimation2) + 0.0f;
        float f5 = countAnimation2 * (i2 + 1);
        if (f4 < -360.0f || f5 < -360.0f) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, Key.ROTATION, f4, f5);
        objectAnimatorOfFloat2.setDuration(j2);
        objectAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.beizi.fusion.widget.TwistView.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((Float) valueAnimator.getAnimatedValue()).floatValue();
            }
        });
        objectAnimatorOfFloat2.start();
    }

    public TwistView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5456m = 1000;
        this.f5457n = 2000;
        this.f5458o = 500L;
        this.f5459p = 0L;
        this.f5460q = 0L;
        this.f5461r = "#FFFFFFFF";
        this.f5462s = "#99FFFFFF";
        this.f5463t = "#33FFFFFF";
        this.f5464u = 1000L;
        this.f5469z = 0;
        this.A = 0;
        this.B = 0;
        this.D = 0;
        this.E = true;
        this.F = 3;
        this.G = 1;
        this.H = 95;
        this.I = new Handler(Looper.getMainLooper()) { // from class: com.beizi.fusion.widget.TwistView.1
            @Override // android.os.Handler
            @RequiresApi(api = 21)
            @SuppressLint({"LongLogTag"})
            public void handleMessage(@NonNull Message message) {
                super.handleMessage(message);
                try {
                    int i2 = message.what;
                    if (i2 != 1000) {
                        if (i2 == 2000) {
                            TwistView twistView = TwistView.this;
                            twistView.updateStatus(twistView.D);
                            return;
                        }
                        return;
                    }
                    if (TwistView.this.f5454k == 0) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                    } else if (TwistView.this.f5454k == 1) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                    } else if (TwistView.this.f5454k == 2) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                    }
                    if (TwistView.this.f5454k == 2) {
                        TwistView.this.f5454k = 0;
                    } else {
                        TwistView.h(TwistView.this);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        };
        a();
    }

    public TwistView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5456m = 1000;
        this.f5457n = 2000;
        this.f5458o = 500L;
        this.f5459p = 0L;
        this.f5460q = 0L;
        this.f5461r = "#FFFFFFFF";
        this.f5462s = "#99FFFFFF";
        this.f5463t = "#33FFFFFF";
        this.f5464u = 1000L;
        this.f5469z = 0;
        this.A = 0;
        this.B = 0;
        this.D = 0;
        this.E = true;
        this.F = 3;
        this.G = 1;
        this.H = 95;
        this.I = new Handler(Looper.getMainLooper()) { // from class: com.beizi.fusion.widget.TwistView.1
            @Override // android.os.Handler
            @RequiresApi(api = 21)
            @SuppressLint({"LongLogTag"})
            public void handleMessage(@NonNull Message message) {
                super.handleMessage(message);
                try {
                    int i22 = message.what;
                    if (i22 != 1000) {
                        if (i22 == 2000) {
                            TwistView twistView = TwistView.this;
                            twistView.updateStatus(twistView.D);
                            return;
                        }
                        return;
                    }
                    if (TwistView.this.f5454k == 0) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                    } else if (TwistView.this.f5454k == 1) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                    } else if (TwistView.this.f5454k == 2) {
                        if (TwistView.this.f5448e != null) {
                            TwistView.this.f5448e.setViewColor(Color.parseColor(TwistView.this.f5462s));
                        }
                        if (TwistView.this.f5449f != null) {
                            TwistView.this.f5449f.setViewColor(Color.parseColor(TwistView.this.f5463t));
                        }
                        if (TwistView.this.f5450g != null) {
                            TwistView.this.f5450g.setViewColor(Color.parseColor(TwistView.this.f5461r));
                        }
                    }
                    if (TwistView.this.f5454k == 2) {
                        TwistView.this.f5454k = 0;
                    } else {
                        TwistView.h(TwistView.this);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        };
        a();
    }
}
