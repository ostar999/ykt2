package com.beizi.fusion.widget.dialog.dislike;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class FlowLayoutManager extends RecyclerView.LayoutManager {

    /* renamed from: e, reason: collision with root package name */
    private static final String f5480e = "FlowLayoutManager";

    /* renamed from: b, reason: collision with root package name */
    protected int f5482b;

    /* renamed from: c, reason: collision with root package name */
    protected int f5483c;

    /* renamed from: f, reason: collision with root package name */
    private int f5485f;

    /* renamed from: g, reason: collision with root package name */
    private int f5486g;

    /* renamed from: h, reason: collision with root package name */
    private int f5487h;

    /* renamed from: i, reason: collision with root package name */
    private int f5488i;

    /* renamed from: a, reason: collision with root package name */
    final FlowLayoutManager f5481a = this;

    /* renamed from: j, reason: collision with root package name */
    private int f5489j = 0;

    /* renamed from: d, reason: collision with root package name */
    protected int f5484d = 0;

    /* renamed from: k, reason: collision with root package name */
    private b f5490k = new b();

    /* renamed from: l, reason: collision with root package name */
    private List<b> f5491l = new ArrayList();

    /* renamed from: m, reason: collision with root package name */
    private SparseArray<Rect> f5492m = new SparseArray<>();

    public class a {

        /* renamed from: a, reason: collision with root package name */
        int f5493a;

        /* renamed from: b, reason: collision with root package name */
        View f5494b;

        /* renamed from: c, reason: collision with root package name */
        Rect f5495c;

        public a(int i2, View view, Rect rect) {
            this.f5493a = i2;
            this.f5494b = view;
            this.f5495c = rect;
        }

        public void a(Rect rect) {
            this.f5495c = rect;
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        float f5497a;

        /* renamed from: b, reason: collision with root package name */
        float f5498b;

        /* renamed from: c, reason: collision with root package name */
        List<a> f5499c = new ArrayList();

        public b() {
        }

        public void a(float f2) {
            this.f5497a = f2;
        }

        public void b(float f2) {
            this.f5498b = f2;
        }

        public void a(a aVar) {
            this.f5499c.add(aVar);
        }
    }

    private void a(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout() || getItemCount() == 0) {
            return;
        }
        new Rect(getPaddingLeft(), getPaddingTop() + this.f5489j, getWidth() - getPaddingRight(), this.f5489j + (getHeight() - getPaddingBottom()));
        for (int i2 = 0; i2 < this.f5491l.size(); i2++) {
            b bVar = this.f5491l.get(i2);
            float f2 = bVar.f5497a;
            List<a> list = bVar.f5499c;
            for (int i3 = 0; i3 < list.size(); i3++) {
                View view = list.get(i3).f5494b;
                measureChildWithMargins(view, 0, 0);
                addView(view);
                Rect rect = list.get(i3).f5495c;
                int i4 = rect.left;
                int i5 = rect.top;
                int i6 = this.f5489j;
                layoutDecoratedWithMargins(view, i4, i5 - i6, rect.right, rect.bottom - i6);
            }
        }
    }

    private int b() {
        return (this.f5481a.getHeight() - this.f5481a.getPaddingBottom()) - this.f5481a.getPaddingTop();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.f5484d = 0;
        int i2 = this.f5486g;
        this.f5490k = new b();
        this.f5491l.clear();
        this.f5492m.clear();
        removeAllViews();
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            this.f5489j = 0;
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        if (getChildCount() == 0) {
            this.f5482b = getWidth();
            this.f5483c = getHeight();
            this.f5485f = getPaddingLeft();
            this.f5487h = getPaddingRight();
            this.f5486g = getPaddingTop();
            this.f5488i = (this.f5482b - this.f5485f) - this.f5487h;
        }
        int i3 = 0;
        int iMax = 0;
        for (int i4 = 0; i4 < getItemCount(); i4++) {
            View viewForPosition = recycler.getViewForPosition(i4);
            if (8 != viewForPosition.getVisibility()) {
                measureChildWithMargins(viewForPosition, 0, 0);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
                int i5 = i3 + decoratedMeasuredWidth;
                if (i5 <= this.f5488i) {
                    int i6 = this.f5485f + i3;
                    Rect rect = this.f5492m.get(i4);
                    if (rect == null) {
                        rect = new Rect();
                    }
                    rect.set(i6, i2, decoratedMeasuredWidth + i6, i2 + decoratedMeasuredHeight);
                    this.f5492m.put(i4, rect);
                    iMax = Math.max(iMax, decoratedMeasuredHeight);
                    this.f5490k.a(new a(decoratedMeasuredHeight, viewForPosition, rect));
                    this.f5490k.a(i2);
                    this.f5490k.b(iMax);
                    i3 = i5;
                } else {
                    a();
                    i2 += iMax;
                    this.f5484d += iMax;
                    int i7 = this.f5485f;
                    Rect rect2 = this.f5492m.get(i4);
                    if (rect2 == null) {
                        rect2 = new Rect();
                    }
                    rect2.set(i7, i2, i7 + decoratedMeasuredWidth, i2 + decoratedMeasuredHeight);
                    this.f5492m.put(i4, rect2);
                    this.f5490k.a(new a(decoratedMeasuredHeight, viewForPosition, rect2));
                    this.f5490k.a(i2);
                    this.f5490k.b(decoratedMeasuredHeight);
                    i3 = decoratedMeasuredWidth;
                    iMax = decoratedMeasuredHeight;
                }
                if (i4 == getItemCount() - 1) {
                    a();
                    this.f5484d += iMax;
                }
            }
        }
        this.f5484d = Math.max(this.f5484d, b());
        a(recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i3 = this.f5489j;
        if (i3 + i2 < 0) {
            i2 = -i3;
        } else if (i3 + i2 > this.f5484d - b()) {
            i2 = (this.f5484d - b()) - this.f5489j;
        }
        this.f5489j += i2;
        offsetChildrenVertical(-i2);
        a(recycler, state);
        return i2;
    }

    private void a() {
        List<a> list = this.f5490k.f5499c;
        for (int i2 = 0; i2 < list.size(); i2++) {
            a aVar = list.get(i2);
            int position = getPosition(aVar.f5494b);
            float f2 = this.f5492m.get(position).top;
            b bVar = this.f5490k;
            if (f2 < bVar.f5497a + ((bVar.f5498b - list.get(i2).f5493a) / 2.0f)) {
                Rect rect = this.f5492m.get(position);
                if (rect == null) {
                    rect = new Rect();
                }
                int i3 = this.f5492m.get(position).left;
                b bVar2 = this.f5490k;
                int i4 = (int) (bVar2.f5497a + ((bVar2.f5498b - list.get(i2).f5493a) / 2.0f));
                int i5 = this.f5492m.get(position).right;
                b bVar3 = this.f5490k;
                rect.set(i3, i4, i5, (int) (bVar3.f5497a + ((bVar3.f5498b - list.get(i2).f5493a) / 2.0f) + getDecoratedMeasuredHeight(r3)));
                this.f5492m.put(position, rect);
                aVar.a(rect);
                list.set(i2, aVar);
            }
        }
        b bVar4 = this.f5490k;
        bVar4.f5499c = list;
        this.f5491l.add(bVar4);
        this.f5490k = new b();
    }
}
