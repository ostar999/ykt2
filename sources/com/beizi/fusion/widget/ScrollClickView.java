package com.beizi.fusion.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beizi.fusion.R;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;

/* loaded from: classes2.dex */
public class ScrollClickView extends LinearLayout {
    public static final String DIR_DOWN = "down";
    public static final String DIR_LEFT = "left";
    public static final String DIR_RIGHT = "right";
    public static final String DIR_UP = "up";

    /* renamed from: a, reason: collision with root package name */
    ImageView f5396a;

    /* renamed from: b, reason: collision with root package name */
    ImageView f5397b;

    /* renamed from: c, reason: collision with root package name */
    TextView f5398c;

    /* renamed from: d, reason: collision with root package name */
    TextView f5399d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f5400e;

    /* renamed from: f, reason: collision with root package name */
    private String f5401f;

    /* renamed from: g, reason: collision with root package name */
    private String f5402g;

    /* renamed from: h, reason: collision with root package name */
    private int f5403h;

    /* renamed from: i, reason: collision with root package name */
    private int f5404i;

    /* renamed from: j, reason: collision with root package name */
    private String f5405j;

    /* renamed from: k, reason: collision with root package name */
    private int f5406k;

    /* renamed from: l, reason: collision with root package name */
    private int f5407l;

    /* renamed from: m, reason: collision with root package name */
    private ValueAnimator f5408m;

    /* renamed from: n, reason: collision with root package name */
    private Context f5409n;

    /* renamed from: o, reason: collision with root package name */
    private FrameLayout f5410o;

    /* renamed from: p, reason: collision with root package name */
    private FrameLayout f5411p;

    /* renamed from: q, reason: collision with root package name */
    private LinearLayout f5412q;

    public ScrollClickView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5400e = false;
        this.f5405j = "up";
        this.f5406k = 45;
        this.f5407l = 180;
        this.f5412q = null;
        init(context);
    }

    public void buildRealView() {
        try {
            if ("up".equalsIgnoreCase(this.f5405j)) {
                this.f5412q = (LinearLayout) LayoutInflater.from(this.f5409n).inflate(R.layout.layout_scrollview_up, this);
            } else if (DIR_DOWN.equalsIgnoreCase(this.f5405j)) {
                this.f5412q = (LinearLayout) LayoutInflater.from(this.f5409n).inflate(R.layout.layout_scrollview_down, this);
            }
        } catch (Throwable th) {
            th.printStackTrace();
            if ("up".equalsIgnoreCase(this.f5405j)) {
                this.f5412q = (LinearLayout) LayoutInflater.from(this.f5409n.getApplicationContext()).inflate(R.layout.layout_scrollview_up, this);
            } else if (DIR_DOWN.equalsIgnoreCase(this.f5405j)) {
                this.f5412q = (LinearLayout) LayoutInflater.from(this.f5409n.getApplicationContext()).inflate(R.layout.layout_scrollview_down, this);
            }
        }
        LinearLayout linearLayout = this.f5412q;
        if (linearLayout == null) {
            return;
        }
        this.f5396a = (ImageView) linearLayout.findViewById(R.id.hand);
        this.f5397b = (ImageView) this.f5412q.findViewById(R.id.scrollbar);
        this.f5398c = (TextView) this.f5412q.findViewById(R.id.title);
        this.f5399d = (TextView) this.f5412q.findViewById(R.id.details);
        this.f5410o = (FrameLayout) this.f5412q.findViewById(R.id.scroll_container);
        this.f5411p = (FrameLayout) this.f5412q.findViewById(R.id.scrollbar_container);
        this.f5406k = as.a(this.f5409n, this.f5406k);
        this.f5407l = as.a(this.f5409n, this.f5407l) + this.f5406k;
        TextView textView = this.f5398c;
        if (textView != null) {
            textView.setText(this.f5401f);
            this.f5398c.setTextSize(2, this.f5403h);
        }
        TextView textView2 = this.f5399d;
        if (textView2 != null) {
            textView2.setText(this.f5402g);
            this.f5399d.setTextSize(2, this.f5404i);
        }
        ImageView imageView = this.f5396a;
        if (imageView == null || this.f5397b == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.f5397b.getLayoutParams();
        if (layoutParams != null) {
            int i2 = this.f5406k;
            layoutParams.width = i2;
            layoutParams.height = i2;
            if (layoutParams2 != null) {
                layoutParams2.height = this.f5407l;
                layoutParams2.width = (int) (i2 * 0.55f);
            }
        }
        if (DIR_DOWN.equalsIgnoreCase(this.f5405j)) {
            b();
        } else if ("up".equalsIgnoreCase(this.f5405j)) {
            a();
        } else {
            if ("left".equalsIgnoreCase(this.f5405j)) {
                return;
            }
            "right".equalsIgnoreCase(this.f5405j);
        }
    }

    public void init(Context context) {
        if (this.f5400e) {
            return;
        }
        this.f5409n = context;
        this.f5400e = true;
    }

    public void setDetailText(String str) {
        this.f5402g = str;
    }

    public void setDetailsFont(int i2) {
        this.f5404i = i2;
    }

    public void setHandWidth(int i2) {
        this.f5406k = i2;
    }

    public void setScrollDirection(String str) {
        this.f5405j = str;
    }

    public void setScrollbarHeight(int i2) {
        this.f5407l = i2;
    }

    public void setTitleFont(int i2) {
        this.f5403h = i2;
    }

    public void setTitleText(String str) {
        this.f5401f = str;
    }

    public void startAnim() {
        StringBuilder sb = new StringBuilder();
        sb.append("startAnim animator != null ? ");
        sb.append(this.f5408m != null);
        ac.b("ScrollClickUtil", sb.toString());
        ValueAnimator valueAnimator = this.f5408m;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
    }

    public void stopAnim() {
        ValueAnimator valueAnimator = this.f5408m;
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            this.f5408m.cancel();
        }
    }

    private void b() {
        this.f5396a.post(new Runnable() { // from class: com.beizi.fusion.widget.ScrollClickView.2
            @Override // java.lang.Runnable
            public void run() {
                if (ScrollClickView.this.f5410o == null || ScrollClickView.this.f5411p == null) {
                    ac.b("ScrollClickUtil", "scrollContainer or scrollBarContainer is null , please check !");
                    return;
                }
                if (ScrollClickView.this.f5396a.getLayoutParams() == null) {
                    return;
                }
                final int i2 = ScrollClickView.this.f5396a.getLayoutParams().height;
                ScrollClickView scrollClickView = ScrollClickView.this;
                scrollClickView.f5408m = ValueAnimator.ofInt(i2, scrollClickView.f5407l);
                ViewGroup.LayoutParams layoutParams = ScrollClickView.this.f5397b.getLayoutParams();
                ac.b("ScrollClickUtil", "handHeight = " + i2);
                if (layoutParams != null) {
                    layoutParams.height = ScrollClickView.this.f5407l;
                }
                ScrollClickView.this.f5408m.setDuration(1000L);
                ScrollClickView.this.f5408m.setRepeatCount(-1);
                ScrollClickView.this.f5408m.setRepeatMode(1);
                ScrollClickView.this.f5408m.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.beizi.fusion.widget.ScrollClickView.2.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ViewGroup.LayoutParams layoutParams2 = ScrollClickView.this.f5410o.getLayoutParams();
                        if (layoutParams2 != null) {
                            layoutParams2.height = iIntValue;
                        }
                        ViewGroup.LayoutParams layoutParams3 = ScrollClickView.this.f5411p.getLayoutParams();
                        if (layoutParams3 != null) {
                            layoutParams3.height = iIntValue - (i2 / 3);
                        }
                        ScrollClickView.this.f5410o.requestLayout();
                    }
                });
            }
        });
    }

    private void a() {
        this.f5396a.post(new Runnable() { // from class: com.beizi.fusion.widget.ScrollClickView.1
            @Override // java.lang.Runnable
            public void run() {
                if (ScrollClickView.this.f5410o == null || ScrollClickView.this.f5411p == null) {
                    ac.b("ScrollClickUtil", "scrollContainer or scrollBarContainer is null , please check !");
                    return;
                }
                if (ScrollClickView.this.f5396a.getLayoutParams() == null) {
                    return;
                }
                final int i2 = ScrollClickView.this.f5396a.getLayoutParams().height;
                ScrollClickView scrollClickView = ScrollClickView.this;
                scrollClickView.f5408m = ValueAnimator.ofInt(i2, scrollClickView.f5407l);
                ac.b("ScrollClickUtil", "handHeight = " + i2 + ",scrollbarHeight = " + ScrollClickView.this.f5407l);
                ViewGroup.LayoutParams layoutParams = ScrollClickView.this.f5397b.getLayoutParams();
                StringBuilder sb = new StringBuilder();
                sb.append("handHeight = ");
                sb.append(i2);
                ac.b("ScrollClickUtil", sb.toString());
                if (layoutParams != null) {
                    layoutParams.height = ScrollClickView.this.f5407l;
                }
                ScrollClickView.this.f5408m.setDuration(1000L);
                ScrollClickView.this.f5408m.setRepeatCount(-1);
                ScrollClickView.this.f5408m.setRepeatMode(1);
                ScrollClickView.this.f5408m.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.beizi.fusion.widget.ScrollClickView.1.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ViewGroup.LayoutParams layoutParams2 = ScrollClickView.this.f5396a.getLayoutParams();
                        if (layoutParams2 instanceof FrameLayout.LayoutParams) {
                            ((FrameLayout.LayoutParams) layoutParams2).topMargin = ScrollClickView.this.f5407l - iIntValue;
                        }
                        ViewGroup.LayoutParams layoutParams3 = ScrollClickView.this.f5411p.getLayoutParams();
                        if (layoutParams3 instanceof FrameLayout.LayoutParams) {
                            layoutParams3.height = iIntValue - (i2 / 3);
                            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                            layoutParams4.topMargin = ScrollClickView.this.f5407l - layoutParams4.height;
                        }
                        ScrollClickView.this.f5410o.requestLayout();
                    }
                });
            }
        });
    }

    public ScrollClickView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5400e = false;
        this.f5405j = "up";
        this.f5406k = 45;
        this.f5407l = 180;
        this.f5412q = null;
        init(context);
    }

    public ScrollClickView(Context context) {
        super(context);
        this.f5400e = false;
        this.f5405j = "up";
        this.f5406k = 45;
        this.f5407l = 180;
        this.f5412q = null;
        init(context);
    }
}
