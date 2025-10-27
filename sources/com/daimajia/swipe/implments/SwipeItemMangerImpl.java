package com.daimajia.swipe.implments;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class SwipeItemMangerImpl implements SwipeItemMangerInterface {
    protected BaseAdapter mBaseAdapter;
    protected RecyclerView.Adapter mRecyclerAdapter;
    private Attributes.Mode mode = Attributes.Mode.Single;
    public final int INVALID_POSITION = -1;
    protected int mOpenPosition = -1;
    protected Set<Integer> mOpenPositions = new HashSet();
    protected Set<SwipeLayout> mShownLayouts = new HashSet();

    public class OnLayoutListener implements SwipeLayout.OnLayout {
        private int position;

        public OnLayoutListener(int i2) {
            this.position = i2;
        }

        @Override // com.daimajia.swipe.SwipeLayout.OnLayout
        public void onLayout(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.isOpen(this.position)) {
                swipeLayout.open(false, false);
            } else {
                swipeLayout.close(false, false);
            }
        }

        public void setPosition(int i2) {
            this.position = i2;
        }
    }

    public class SwipeMemory extends SimpleSwipeListener {
        private int position;

        public SwipeMemory(int i2) {
            this.position = i2;
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onClose(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.remove(Integer.valueOf(this.position));
            } else {
                SwipeItemMangerImpl.this.mOpenPosition = -1;
            }
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onOpen(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.add(Integer.valueOf(this.position));
                return;
            }
            SwipeItemMangerImpl.this.closeAllExcept(swipeLayout);
            SwipeItemMangerImpl.this.mOpenPosition = this.position;
        }

        @Override // com.daimajia.swipe.SimpleSwipeListener, com.daimajia.swipe.SwipeLayout.SwipeListener
        public void onStartOpen(SwipeLayout swipeLayout) {
            if (SwipeItemMangerImpl.this.mode == Attributes.Mode.Single) {
                SwipeItemMangerImpl.this.closeAllExcept(swipeLayout);
            }
        }

        public void setPosition(int i2) {
            this.position = i2;
        }
    }

    public class ValueBox {
        OnLayoutListener onLayoutListener;
        int position;
        SwipeMemory swipeMemory;

        public ValueBox(int i2, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = i2;
        }
    }

    public SwipeItemMangerImpl(BaseAdapter baseAdapter) {
        if (baseAdapter == null) {
            throw new IllegalArgumentException("Adapter can not be null");
        }
        if (!(baseAdapter instanceof SwipeItemMangerInterface)) {
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
        this.mBaseAdapter = baseAdapter;
    }

    public abstract void bindView(View view, int i2);

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        for (SwipeLayout swipeLayout2 : this.mShownLayouts) {
            if (swipeLayout2 != swipeLayout) {
                swipeLayout2.close();
            }
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllItems() {
        if (this.mode == Attributes.Mode.Multiple) {
            this.mOpenPositions.clear();
        } else {
            this.mOpenPosition = -1;
        }
        Iterator<SwipeLayout> it = this.mShownLayouts.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeItem(int i2) {
        if (this.mode == Attributes.Mode.Multiple) {
            this.mOpenPositions.remove(Integer.valueOf(i2));
        } else if (this.mOpenPosition == i2) {
            this.mOpenPosition = -1;
        }
        BaseAdapter baseAdapter = this.mBaseAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
            return;
        }
        RecyclerView.Adapter adapter = this.mRecyclerAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public Attributes.Mode getMode() {
        return this.mode;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mode == Attributes.Mode.Multiple ? new ArrayList(this.mOpenPositions) : Arrays.asList(Integer.valueOf(this.mOpenPosition));
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList(this.mShownLayouts);
    }

    public int getSwipeLayoutId(int i2) {
        SpinnerAdapter spinnerAdapter = this.mBaseAdapter;
        if (spinnerAdapter != null) {
            return ((SwipeAdapterInterface) spinnerAdapter).getSwipeLayoutResourceId(i2);
        }
        Object obj = this.mRecyclerAdapter;
        if (obj != null) {
            return ((SwipeAdapterInterface) obj).getSwipeLayoutResourceId(i2);
        }
        return -1;
    }

    public abstract void initialize(View view, int i2);

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public boolean isOpen(int i2) {
        return this.mode == Attributes.Mode.Multiple ? this.mOpenPositions.contains(Integer.valueOf(i2)) : this.mOpenPosition == i2;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void openItem(int i2) {
        if (this.mode != Attributes.Mode.Multiple) {
            this.mOpenPosition = i2;
        } else if (!this.mOpenPositions.contains(Integer.valueOf(i2))) {
            this.mOpenPositions.add(Integer.valueOf(i2));
        }
        BaseAdapter baseAdapter = this.mBaseAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
            return;
        }
        RecyclerView.Adapter adapter = this.mRecyclerAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mShownLayouts.remove(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void setMode(Attributes.Mode mode) {
        this.mode = mode;
        this.mOpenPositions.clear();
        this.mShownLayouts.clear();
        this.mOpenPosition = -1;
    }

    public abstract void updateConvertView(View view, int i2);

    public SwipeItemMangerImpl(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            if (adapter instanceof SwipeItemMangerInterface) {
                this.mRecyclerAdapter = adapter;
                return;
            }
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
        throw new IllegalArgumentException("Adapter can not be null");
    }
}
